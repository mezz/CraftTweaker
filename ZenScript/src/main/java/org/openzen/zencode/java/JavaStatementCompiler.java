/*
 * This file is part of ZenCode, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 openzen.org <http://zencode.openzen.org>
 */
package org.openzen.zencode.java;

import org.objectweb.asm.Label;
import org.openzen.zencode.java.expression.IJavaExpression;
import org.openzen.zencode.java.iterator.IJavaIterator;
import org.openzen.zencode.java.util.MethodOutput;
import org.openzen.zencode.symbolic.statement.IStatementProcessor;
import org.openzen.zencode.symbolic.statement.ImportStatement;
import org.openzen.zencode.symbolic.statement.Statement;
import org.openzen.zencode.symbolic.statement.StatementBlock;
import org.openzen.zencode.symbolic.statement.StatementBreak;
import org.openzen.zencode.symbolic.statement.StatementContinue;
import org.openzen.zencode.symbolic.statement.StatementDoWhile;
import org.openzen.zencode.symbolic.statement.StatementExpression;
import org.openzen.zencode.symbolic.statement.StatementForeach;
import org.openzen.zencode.symbolic.statement.StatementIf;
import org.openzen.zencode.symbolic.statement.StatementNull;
import org.openzen.zencode.symbolic.statement.StatementReturn;
import org.openzen.zencode.symbolic.statement.StatementSwitch;
import org.openzen.zencode.symbolic.statement.StatementVar;
import org.openzen.zencode.symbolic.statement.StatementWhile;
import org.openzen.zencode.symbolic.statement.SynchronizedStatement;
import org.openzen.zencode.symbolic.statement.ThrowStatement;
import org.openzen.zencode.symbolic.statement.TryStatement;
import org.openzen.zencode.symbolic.type.IGenericType;

/**
 *
 * @author Stan
 */
public class JavaStatementCompiler implements IStatementProcessor<IJavaExpression, Void>
{
	private final MethodOutput output;
	
	public JavaStatementCompiler(MethodOutput output)
	{
		this.output = output;
	}
	
	@Override
	public Void onBlock(StatementBlock<IJavaExpression> statement)
	{
		for (Statement<IJavaExpression> element : statement.getStatements()) {
			element.process(this);
			
			if (element.isReturn())
				return null;
		}
		
		return null;
	}

	@Override
	public Void onBreak(StatementBreak<IJavaExpression> statement)
	{
		MethodOutput.ControlLabels labels = output.getControlLabels(statement.getTarget());
		if (labels == null)
			throw new AssertionError("missing control labels");
		if (labels.breakLabel == null)
			statement.getScope().getErrorLogger().errorNoBreakableControlStatement(statement.getPosition());
		else
			output.goTo(labels.breakLabel);
		
		return null;
	}

	@Override
	public Void onContinue(StatementContinue<IJavaExpression> statement)
	{
		MethodOutput.ControlLabels controls = output.getControlLabels(statement.getTarget());
		if (controls == null)
			throw new AssertionError("control labels missing");
		
		if (controls.continueLabel == null) {
			statement.getScope().getErrorLogger().errorNoContinuableControlStatement(statement.getPosition());
		} else {
			output.goTo(controls.continueLabel);
		}
		
		return null;
	}

	@Override
	public Void onDoWhile(StatementDoWhile<IJavaExpression> statement)
	{
		Label lblRepeat = new Label();
		Label lblContinue = new Label();
		Label lblBreak = new Label();
		output.putControlLabels(statement, new MethodOutput.ControlLabels(lblContinue, lblBreak));
		
		output.label(lblRepeat);
		statement.getContents().process(this);
		output.label(lblContinue);
		statement.getCondition().compileIf(lblRepeat, output);
		output.label(lblBreak);
		
		return null;
	}

	@Override
	public Void onExpression(StatementExpression<IJavaExpression> statement)
	{
		output.position(statement.getPosition());
		statement.getExpression().compileStatement(output);
		
		return null;
	}

	@Override
	public Void onForeach(StatementForeach<IJavaExpression> statement)
	{
		output.position(statement.getPosition());
		
		int[] localVariables = new int[statement.getVariables().size()];
		for (int i = 0; i < localVariables.length; i++) {
			localVariables[i] = output.getLocal(statement.getVariables().get(i));
		}
		
		IJavaIterator iterator = output.getCompileState()
				.getTypeInfo(statement.getList().getType())
				.getIterator(statement.getVariables().size());
		
		statement.getList().compileValue(output);
		iterator.compileStart(output, localVariables);
		
		Label lblRepeat = new Label();
		Label lblBreak = new Label();
		output.putControlLabels(statement, new MethodOutput.ControlLabels(lblRepeat, lblBreak));
		
		output.label(lblRepeat);
		iterator.compilePreIterate(output, localVariables, lblBreak);
		statement.getBody().process(this);
		iterator.compilePostIterate(output, localVariables, lblBreak, lblRepeat);
		output.label(lblBreak);
		iterator.compileEnd(output);
		
		return null;
	}

	@Override
	public Void onIf(StatementIf<IJavaExpression> statement)
	{
		output.position(statement.getPosition());
		
		IGenericType<IJavaExpression> expressionType = statement.getCondition().getType();
		if (expressionType != statement.getScope().getTypeCompiler().bool)
			throw new RuntimeException("condition is not a boolean");
		
		Label labelEnd = new Label();
		Label labelElse = statement.getElse() == null ? labelEnd : new Label();

		statement.getCondition().compileElse(labelElse, output);
		statement.getThen().process(this);
		
		if (statement.getElse() != null) {
			output.goTo(labelEnd);
			output.label(labelElse);
			statement.getElse().process(this);
		}
		
		output.label(labelEnd);
		
		return null;
	}

	@Override
	public Void onEmpty(StatementNull<IJavaExpression> statement)
	{
		// nothing to do
		return null;
	}

	@Override
	public Void onReturn(StatementReturn<IJavaExpression> statement)
	{
		output.position(statement.getPosition());

		if (statement.getExpression() == null)
			output.ret();
		else {
			statement.getExpression().compileValue(output);
			
			output.returnType(statement.getExpression().getType());
		}
		
		return null;
	}

	@Override
	public Void onSwitch(StatementSwitch<IJavaExpression> statement)
	{
		// TODO: implement
		
		return null;
	}

	@Override
	public Void onVar(StatementVar<IJavaExpression> statement)
	{
		output.position(statement.getPosition());

		if (statement.getInitializer() != null) {
			statement.getInitializer().compileValue(output);
			output.store(
					statement.getSymbol().getType(),
					output.getLocal(statement.getSymbol()));
		}
		
		return null;
	}

	@Override
	public Void onWhile(StatementWhile<IJavaExpression> statement)
	{
		Label lblRepeat = new Label();
		Label lblBreak = new Label();

		output.label(lblRepeat);
		statement.getCondition().compileElse(lblBreak, output);
		statement.getContents().process(this);
		output.goTo(lblRepeat);
		output.label(lblBreak);
		
		return null;
	}

	@Override
	public Void onTryCatch(TryStatement<IJavaExpression> statement)
	{
		// TODO: implement
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Void onThrow(ThrowStatement<IJavaExpression> statement)
	{
		statement.getValue().compileValue(output);
		output.aThrow();
		
		return null;
	}

	@Override
	public Void onSynchronized(SynchronizedStatement<IJavaExpression> statement)
	{
		// TODO: implement
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Void onImport(ImportStatement<IJavaExpression> statement)
	{
		// nothing to do
		return null;
	}
}