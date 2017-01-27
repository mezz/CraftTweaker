/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.brackets;

import minetweaker.*;
import minetweaker.annotations.BracketHandler;
import minetweaker.api.util.IBlockPos;
import minetweaker.mc1102.util.MCBlockPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

/**
 * @author Jared
 */
@BracketHandler(priority = 100)
public class BlockPosBracketHandler implements IBracketHandler {
	
	private final IJavaMethod method;
	
	public BlockPosBracketHandler() {
		method = MineTweakerAPI.getJavaMethod(BlockPosBracketHandler.class, "getPos", int.class, int.class, int.class);
	}
	
	public static IBlockPos getPos(int x, int y, int z) {
		return new MCBlockPos(new BlockPos(x, y, z));
	}
	
	@Override
	public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
		if(tokens.size() == 7) {
			if(tokens.get(0).getValue().equals("pos") && tokens.get(1).getValue().equals(":")) {
				return find(environment, tokens, 2, tokens.size());
			}
		}
		
		return null;
	}
	
	private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
		StringBuilder valueBuilder = new StringBuilder();
		for(int i = startIndex; i < endIndex; i++) {
			Token token = tokens.get(i);
			valueBuilder.append(token.getValue());
		}
		
		return new EntityReferenceSymbol(environment, valueBuilder.toString());
	}
	
	private class EntityReferenceSymbol implements IZenSymbol {
		
		private final IEnvironmentGlobal environment;
		private final String values;
		
		public EntityReferenceSymbol(IEnvironmentGlobal environment, String values) {
			this.environment = environment;
			this.values = values;
		}
		
		@Override
		public IPartialExpression instance(ZenPosition position) {
			return new ExpressionCallStatic(position, environment, method, new ExpressionInt(position, Integer.parseInt(values.split(",")[0]), ZenType.INT), new ExpressionInt(position, Integer.parseInt(values.split(",")[1]), ZenType.INT), new ExpressionInt(position, Integer.parseInt(values.split(",")[2]), ZenType.INT));
		}
	}
}
