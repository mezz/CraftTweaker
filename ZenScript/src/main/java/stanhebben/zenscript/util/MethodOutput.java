/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.LocalVariablesSorter;
import stanhebben.zenscript.FileLogger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 *
 * @author Stanneke
 */
public class MethodOutput {
	// private static final boolean debug = true;

	private final LocalVariablesSorter visitor;

	private boolean debug = true;
	private int labelIndex = 1;
	private Map<Label, String> labelNames;

	public MethodOutput(ClassVisitor cls, int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = cls.visitMethod(access, name, descriptor, signature, exceptions);
		visitor = new LocalVariablesSorter(access, descriptor, methodVisitor);
	}

	public MethodOutput(LocalVariablesSorter visitor) {
		this.visitor = visitor;
	}

	public void enableDebug() {
		debug = true;
	}

	public void start() {
		if (debug)
			FileLogger.INSTANCE.logInfo("--start--");

		visitor.visitCode();
	}

	public void end() {
		if (debug)
			FileLogger.INSTANCE.logInfo("--end--");

		visitor.visitMaxs(0, 0);
		visitor.visitEnd();
	}

	public void label(Label label) {
		if (debug)
			FileLogger.INSTANCE.logInfo("Label " + getLabelName(label));

		visitor.visitLabel(label);
	}

	public int local(Type type) {
		if (debug)
			FileLogger.INSTANCE.logInfo("local " + type.getClassName());
		return visitor.newLocal(type);
	}

	public int local(Class cls) {
		if (debug)
			FileLogger.INSTANCE.logInfo("local " + Type.getType(cls).getClassName());
		return visitor.newLocal(Type.getType(cls));
	}

	public void iConst0() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iconst0");

		visitor.visitInsn(ICONST_0);
	}

	public void iConst1() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iconst1");

		visitor.visitInsn(ICONST_1);
	}

	public void biPush(byte value) {
		if (debug)
			FileLogger.INSTANCE.logInfo("bipush");

		visitor.visitIntInsn(BIPUSH, value);
	}

	public void siPush(short value) {
		if (debug)
			FileLogger.INSTANCE.logInfo("sipush");

		visitor.visitIntInsn(SIPUSH, value);
	}

	public void aConstNull() {
		if (debug)
			FileLogger.INSTANCE.logInfo("null");

		visitor.visitInsn(ACONST_NULL);
	}

	public void constant(Object value) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ldc " + value);

		visitor.visitLdcInsn(value);
	}

	public void pop() {
		if (debug)
			FileLogger.INSTANCE.logInfo("pop");

		visitor.visitInsn(POP);
	}

	public void pop(boolean large) {
		if (debug)
			FileLogger.INSTANCE.logInfo("pop");

		visitor.visitInsn(large ? POP2 : POP);
	}

	public void dup() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dup");

		visitor.visitInsn(DUP);
	}

	public void dup(boolean large) {
		if (debug)
			FileLogger.INSTANCE.logInfo("dup");

		visitor.visitInsn(large ? DUP2 : DUP);
	}

	public void dup2() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dup2");

		visitor.visitInsn(DUP2);
	}

	public void dupX1() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dupx1");

		visitor.visitInsn(DUP_X1);
	}

	public void dupX2() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dupx2");

		visitor.visitInsn(DUP_X2);
	}

	public void dup2X1() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dup2_x1");

		visitor.visitInsn(DUP2_X1);
	}

	public void dup2X2() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dup2_x2");

		visitor.visitInsn(DUP2_X2);
	}

	public void store(Type type, int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("store " + local);

		visitor.visitVarInsn(type.getOpcode(ISTORE), local);
	}

	public void load(Type type, int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("load " + local);

		visitor.visitVarInsn(type.getOpcode(ILOAD), local);
	}

	public void storeInt(int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("storeInt " + local);

		visitor.visitVarInsn(ISTORE, local);
	}

	public void loadInt(int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("loadInt " + local);

		visitor.visitVarInsn(ILOAD, local);
	}

	public void storeObject(int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("storeObject " + local);

		visitor.visitVarInsn(ASTORE, local);
	}

	public void loadObject(int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("loadObject " + local);

		visitor.visitVarInsn(ALOAD, local);
	}

	public void arrayLength() {
		if (debug)
			FileLogger.INSTANCE.logInfo("arrayLength");

		visitor.visitInsn(ARRAYLENGTH);
	}

	public void arrayLoad(Type type) {
		if (debug)
			FileLogger.INSTANCE.logInfo("arrayLoad");

		visitor.visitInsn(type.getOpcode(IALOAD));
	}

	public void arrayStore(Type type) {
		if (debug)
			FileLogger.INSTANCE.logInfo("arrayStore");

		visitor.visitInsn(type.getOpcode(IASTORE));
	}

	public void newArray(Type componentType) {
		if (debug)
			FileLogger.INSTANCE.logInfo("newArray");

		int sort = componentType.getSort();
		if (sort == Type.METHOD) {
			throw new RuntimeException("Unsupported array type: " + componentType);
		} else if (sort == Type.OBJECT || sort == Type.ARRAY) {
			visitor.visitTypeInsn(ANEWARRAY, componentType.getInternalName());
		} else {
			int type;
			switch (sort) {
				case Type.BOOLEAN:
					type = Opcodes.T_BOOLEAN;
					break;
				case Type.BYTE:
					type = Opcodes.T_BYTE;
					break;
				case Type.SHORT:
					type = Opcodes.T_SHORT;
					break;
				case Type.INT:
					type = Opcodes.T_INT;
					break;
				case Type.LONG:
					type = Opcodes.T_LONG;
					break;
				case Type.FLOAT:
					type = Opcodes.T_FLOAT;
					break;
				case Type.DOUBLE:
					type = Opcodes.T_DOUBLE;
					break;
				default:
					throw new RuntimeException("Unsupported array type: " + componentType);
			}
			visitor.visitIntInsn(NEWARRAY, type);
		}
	}

	public void newArray(Class componentType) {
		if (debug)
			FileLogger.INSTANCE.logInfo("newArray " + componentType.getName());

		visitor.visitTypeInsn(NEWARRAY, internal(componentType));
	}

	public void checkCast(Class newClass) {
		if (debug)
			FileLogger.INSTANCE.logInfo("checkCast " + newClass.getName());

		visitor.visitTypeInsn(CHECKCAST, signature(newClass));
	}

	public void checkCast(String newClass) {
		if (debug)
			FileLogger.INSTANCE.logInfo("checkCast " + newClass);

		visitor.visitTypeInsn(CHECKCAST, newClass);
	}

	public void iNeg() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iNeg");

		visitor.visitInsn(INEG);
	}

	public void iAdd() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iAdd");

		visitor.visitInsn(IADD);
	}

	public void iSub() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iSub");

		visitor.visitInsn(ISUB);
	}

	public void iMul() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iMul");

		visitor.visitInsn(IMUL);
	}

	public void iDiv() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iDiv");

		visitor.visitInsn(IDIV);
	}

	public void iRem() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iRem");

		visitor.visitInsn(IREM);
	}

	public void iAnd() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iAnd");

		visitor.visitInsn(IAND);
	}

	public void iOr() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iOr");

		visitor.visitInsn(IOR);
	}

	public void iXor() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iXor");

		visitor.visitInsn(IXOR);
	}

	public void iNot() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iNot");

		visitor.visitInsn(ICONST_M1);
		visitor.visitInsn(IXOR);
	}

	public void iShr() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iShr");

		visitor.visitInsn(ISHR);
	}

	public void iShl() {
		if (debug)
			FileLogger.INSTANCE.logInfo("iShl");

		visitor.visitInsn(ISHL);
	}

	public void lNeg() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lNeg");

		visitor.visitInsn(LNEG);
	}

	public void lAdd() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lAdd");

		visitor.visitInsn(LADD);
	}

	public void lSub() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lSub");

		visitor.visitInsn(LSUB);
	}

	public void lMul() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lMul");

		visitor.visitInsn(LMUL);
	}

	public void lDiv() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lDiv");

		visitor.visitInsn(LDIV);
	}

	public void lRem() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lRem");

		visitor.visitInsn(LREM);
	}

	public void lAnd() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lAnd");

		visitor.visitInsn(LAND);
	}

	public void lOr() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lOr");

		visitor.visitInsn(LOR);
	}

	public void lXor() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lXor");

		visitor.visitInsn(LXOR);
	}

	public void lNot() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lNot");

		constant((long) -1);
		lXor();
	}

	public void lShr() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lShr");

		visitor.visitInsn(LSHR);
	}

	public void lShl() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lShl");

		visitor.visitInsn(LSHL);
	}

	public void fNeg() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fNeg");

		visitor.visitInsn(FNEG);
	}

	public void fAdd() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fAdd");

		visitor.visitInsn(FADD);
	}

	public void fSub() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fSub");

		visitor.visitInsn(FSUB);
	}

	public void fMul() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fMul");

		visitor.visitInsn(FMUL);
	}

	public void fDiv() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fDiv");

		visitor.visitInsn(FDIV);
	}

	public void fRem() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fRem");

		visitor.visitInsn(FREM);
	}

	public void dNeg() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dNeg");

		visitor.visitInsn(DNEG);
	}

	public void dAdd() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dAdd");

		visitor.visitInsn(DADD);
	}

	public void dSub() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dSub");

		visitor.visitInsn(DSUB);
	}

	public void dMul() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dMul");

		visitor.visitInsn(DMUL);
	}

	public void dDiv() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dDiv");

		visitor.visitInsn(DDIV);
	}

	public void dRem() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dRem");

		visitor.visitInsn(DREM);
	}

	public void iinc(int local) {
		if (debug)
			FileLogger.INSTANCE.logInfo("iinc " + local);

		visitor.visitIincInsn(local, 1);
	}

	public void iinc(int local, int increment) {
		if (debug)
			FileLogger.INSTANCE.logInfo("iinc " + local + " + " + increment);

		visitor.visitIincInsn(local, increment);
	}

	public void i2b() {
		if (debug)
			FileLogger.INSTANCE.logInfo("i2b");

		visitor.visitInsn(I2B);
	}

	public void i2s() {
		if (debug)
			FileLogger.INSTANCE.logInfo("i2s");

		visitor.visitInsn(I2S);
	}

	public void i2l() {
		if (debug)
			FileLogger.INSTANCE.logInfo("i2l");

		visitor.visitInsn(I2L);
	}

	public void i2f() {
		if (debug)
			FileLogger.INSTANCE.logInfo("i2f");

		visitor.visitInsn(I2F);
	}

	public void i2d() {
		if (debug)
			FileLogger.INSTANCE.logInfo("i2d");

		visitor.visitInsn(I2D);
	}

	public void l2i() {
		if (debug)
			FileLogger.INSTANCE.logInfo("l2i");

		visitor.visitInsn(L2I);
	}

	public void l2f() {
		if (debug)
			FileLogger.INSTANCE.logInfo("l2f");

		visitor.visitInsn(L2F);
	}

	public void l2d() {
		if (debug)
			FileLogger.INSTANCE.logInfo("l2d");

		visitor.visitInsn(L2D);
	}

	public void f2i() {
		if (debug)
			FileLogger.INSTANCE.logInfo("f2i");

		visitor.visitInsn(F2I);
	}

	public void f2l() {
		if (debug)
			FileLogger.INSTANCE.logInfo("f2l");

		visitor.visitInsn(F2L);
	}

	public void f2d() {
		if (debug)
			FileLogger.INSTANCE.logInfo("f2d");

		visitor.visitInsn(F2D);
	}

	public void d2i() {
		if (debug)
			FileLogger.INSTANCE.logInfo("d2i");

		visitor.visitInsn(D2I);
	}

	public void d2l() {
		if (debug)
			FileLogger.INSTANCE.logInfo("d2l");

		visitor.visitInsn(D2L);
	}

	public void d2f() {
		if (debug)
			FileLogger.INSTANCE.logInfo("d2f");

		visitor.visitInsn(D2F);
	}

	public void lCmp() {
		if (debug)
			FileLogger.INSTANCE.logInfo("lCmp");

		visitor.visitInsn(LCMP);
	}

	public void fCmp() {
		if (debug)
			FileLogger.INSTANCE.logInfo("fCmp");

		visitor.visitInsn(FCMPL);
	}

	public void dCmp() {
		if (debug)
			FileLogger.INSTANCE.logInfo("dCmp");

		visitor.visitInsn(DCMPL);
	}

	public void instanceOf(String clsName) {
		if (debug)
			FileLogger.INSTANCE.logInfo("instanceOf " + clsName);

		visitor.visitTypeInsn(INSTANCEOF, clsName);
	}

	public void invokeStatic(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("invokeStatic " + owner + '.' + name + descriptor);

		if (owner == null)
			throw new IllegalArgumentException("owner cannot be null");
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		if (descriptor == null)
			throw new IllegalArgumentException("descriptor cannot be null");

		visitor.visitMethodInsn(INVOKESTATIC, owner, name, descriptor);
	}

	public void invokeStatic(Class owner, String name, Class result, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(result == null ? 'V' : signature(result));

		if (debug)
			FileLogger.INSTANCE.logInfo("invokeStatic " + internal(owner) + '.' + name + descriptor);

		visitor.visitMethodInsn(INVOKESTATIC, internal(owner), name, descriptor.toString());
	}

	public void invokeSpecial(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("invokeSpecial " + owner + '.' + name + descriptor);

		visitor.visitMethodInsn(INVOKESPECIAL, owner, name, descriptor);
	}

	public void invoke(Class owner, String name, Class result, Class... arguments) {
		if (owner.isInterface()) {
			invokeInterface(owner, name, result, arguments);
		} else {
			invokeVirtual(owner, name, result, arguments);
		}
	}

	public void invokeVirtual(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("invokeVirtual " + owner + '.' + name + descriptor);

		visitor.visitMethodInsn(INVOKEVIRTUAL, owner, name, descriptor);
	}

	public void invokeVirtual(Class owner, String name, Class result, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(result == null ? 'V' : signature(result));

		if (debug)
			FileLogger.INSTANCE.logInfo("invokeVirtual " + owner + '.' + name + descriptor);

		visitor.visitMethodInsn(INVOKEVIRTUAL, internal(owner), name, descriptor.toString());
	}

	public void invokeInterface(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("invokeInterface " + owner + '.' + name + descriptor);

		visitor.visitMethodInsn(INVOKEINTERFACE, owner, name, descriptor);
	}

	public void invokeInterface(Class owner, String name, Class result, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(result == null ? 'V' : signature(result));

		if (debug)
			FileLogger.INSTANCE.logInfo("invokeInterface " + owner + '.' + name + descriptor);

		visitor.visitMethodInsn(INVOKEINTERFACE, internal(owner), name, descriptor.toString());
	}

	public void newObject(Class type) {
		if (debug)
			FileLogger.INSTANCE.logInfo("newObject " + type.getName());

		visitor.visitTypeInsn(NEW, internal(type));
	}

	public void newObject(String type) {
		if (debug)
			FileLogger.INSTANCE.logInfo("newObject " + type);

		visitor.visitTypeInsn(NEW, type);
	}

	public void construct(Class type, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(")V");

		if (debug)
			FileLogger.INSTANCE.logInfo("invokeSpecial " + internal(type) + ".<init>" + descriptor);

		visitor.visitMethodInsn(INVOKESPECIAL, internal(type), "<init>", descriptor.toString());
	}

	public void construct(String type, String... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (String argument : arguments) {
			descriptor.append(argument);
		}
		descriptor.append(")V");

		if (debug)
			FileLogger.INSTANCE.logInfo("invokeSpecial " + type + ".<init>" + descriptor);

		visitor.visitMethodInsn(INVOKESPECIAL, type, "<init>", descriptor.toString());
	}

	public void goTo(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("goTo " + getLabelName(lbl));

		visitor.visitJumpInsn(GOTO, lbl);
	}

	/**
	 * Jump if TOS == 0.
	 * 
	 * @param lbl target label
	 */
	public void ifEQ(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifEQ " + getLabelName(lbl));

		visitor.visitJumpInsn(IFEQ, lbl);
	}

	public void ifNE(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifNE " + getLabelName(lbl));

		visitor.visitJumpInsn(IFNE, lbl);
	}

	public void ifLT(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifLT " + getLabelName(lbl));

		visitor.visitJumpInsn(IFLT, lbl);
	}

	public void ifGT(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifGT " + getLabelName(lbl));

		visitor.visitJumpInsn(IFGT, lbl);
	}

	public void ifGE(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifGE " + getLabelName(lbl));

		visitor.visitJumpInsn(IFGE, lbl);
	}

	public void ifLE(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifLE " + getLabelName(lbl));

		visitor.visitJumpInsn(IFLE, lbl);
	}

	public void ifICmpLE(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpLE " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ICMPLE, lbl);
	}

	public void ifICmpGE(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpGE " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ICMPGE, lbl);
	}

	public void ifICmpEQ(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpEQ " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ICMPEQ, lbl);
	}

	public void ifICmpNE(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpNE " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ICMPNE, lbl);
	}

	public void ifICmpGT(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpGT " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ICMPGT, lbl);
	}

	public void ifICmpLT(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpLT " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ICMPLT, lbl);
	}

	public void ifACmpEq(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifICmpEQ " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ACMPEQ, lbl);
	}

	public void ifACmpNe(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifACmpNE " + getLabelName(lbl));

		visitor.visitJumpInsn(IF_ACMPNE, lbl);
	}

	public void ifNull(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifNull " + getLabelName(lbl));

		visitor.visitJumpInsn(IFNULL, lbl);
	}

	public void ifNonNull(Label lbl) {
		if (debug)
			FileLogger.INSTANCE.logInfo("ifNonNull " + getLabelName(lbl));

		visitor.visitJumpInsn(IFNONNULL, lbl);
	}

	public void ret() {
		if (debug)
			FileLogger.INSTANCE.logInfo("ret");

		visitor.visitInsn(RETURN);
	}

	public void returnType(Type type) {
		if (debug)
			FileLogger.INSTANCE.logInfo("return " + type.getDescriptor());

		visitor.visitInsn(type.getOpcode(IRETURN));
	}

	public void returnInt() {
		if (debug)
			FileLogger.INSTANCE.logInfo("ireturn");

		visitor.visitInsn(IRETURN);
	}

	public void returnObject() {
		if (debug)
			FileLogger.INSTANCE.logInfo("areturn");

		visitor.visitInsn(ARETURN);
	}

	public void getField(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("getField " + owner + '.' + name + ":" + descriptor);

		visitor.visitFieldInsn(GETFIELD, owner, name, descriptor);
	}

	public void getField(Class owner, String name, Class descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("getField " + owner.getName() + '.' + name + ":" + descriptor.getName());

		visitor.visitFieldInsn(GETFIELD, internal(owner), name, signature(descriptor));
	}

	public void putField(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("putField " + owner + '.' + name + ":" + descriptor);

		visitor.visitFieldInsn(PUTFIELD, owner, name, descriptor);
	}

	public void putField(Class owner, String name, Class descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("putField " + owner.getName() + '.' + name + ":" + descriptor.getName());

		visitor.visitFieldInsn(PUTFIELD, internal(owner), name, signature(descriptor));
	}

	public void getStaticField(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("getStatic " + owner + '.' + name + ":" + descriptor);

		visitor.visitFieldInsn(GETSTATIC, owner, name, descriptor);
	}

	public void getStaticField(Class owner, Field field) {
		if (debug)
			FileLogger.INSTANCE.logInfo("getField " + owner.getName() + '.' + field.getName() + ":" + signature(field.getType()));

		visitor.visitFieldInsn(GETSTATIC, internal(owner), field.getName(), signature(field.getType()));
	}

	public void putStaticField(String owner, String name, String descriptor) {
		if (debug)
			FileLogger.INSTANCE.logInfo("putStatic " + owner + '.' + name + ":" + descriptor);

		visitor.visitFieldInsn(PUTSTATIC, owner, name, descriptor);
	}

	public void putStaticField(Class owner, Field field) {
		if (debug)
			FileLogger.INSTANCE.logInfo("putStatic " + owner.getName() + '.' + field.getName() + ":" + signature(field.getType()));

		visitor.visitFieldInsn(PUTSTATIC, internal(owner), field.getName(), signature(field.getType()));
	}

	public void aThrow() {
		if (debug)
			FileLogger.INSTANCE.logInfo("athrow");
		visitor.visitInsn(ATHROW);
	}

	public void position(ZenPosition position) {
		if (debug)
			FileLogger.INSTANCE.logInfo("position " + position.getLine() + "L" + position.getLineOffset());
		Label label = new Label();
		visitor.visitLabel(label);
		visitor.visitLineNumber(position.getLine(), label);
	}

	private String getLabelName(Label lbl) {
		if (labelNames == null)
			labelNames = new HashMap<>();

		if (!labelNames.containsKey(lbl)) {
			labelNames.put(lbl, "L" + labelIndex++);
		}

		return labelNames.get(lbl);
	}
}
