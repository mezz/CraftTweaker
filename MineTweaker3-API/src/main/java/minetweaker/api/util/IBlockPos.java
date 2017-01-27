package minetweaker.api.util;

import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.util.IBlockPos")
public interface IBlockPos {
	
	@ZenMethod
	IBlockPos setX(int x);
	
	@ZenMethod
	IBlockPos setY(int y);
	
	@ZenMethod
	IBlockPos setZ(int z);
	
	@ZenMethod
	IBlockPos setPos(int x, int y, int z);
	
	@ZenMethod
	IBlockPos up(@Optional int distance);
	
	@ZenMethod
	IBlockPos down(@Optional int distance);
	
	@ZenMethod
	IBlockPos north(@Optional int distance);
	
	@ZenMethod
	IBlockPos east(@Optional int distance);
	
	@ZenMethod
	IBlockPos south(@Optional int distance);
	
	@ZenMethod
	IBlockPos west(@Optional int distance);
	
	@ZenMethod
	String asString();
	
	Object getInternal();
}
