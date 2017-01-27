package minetweaker.api.block;

import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.block.IMaterial")
public interface IMaterial {
	
	@ZenGetter("liquid")
	boolean isLiquid();
	
	@ZenGetter("solid")
	boolean isSolid();
	
	@ZenGetter("seaLevel")
	boolean blocksLight();
	
	@ZenGetter("seaLevel")
	boolean blocksMovement();
	
	@ZenGetter("seaLevel")
	boolean canBurn();
	
	@ZenGetter("seaLevel")
	boolean isReplaceable();
	
	@ZenGetter("seaLevel")
	boolean isToolNotRequired();
}
