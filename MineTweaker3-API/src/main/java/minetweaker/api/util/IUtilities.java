package minetweaker.api.util;

import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.api.IUtilities")
public interface IUtilities {
	
	@ZenMethod
	IBlockPos pos(int x, int y, int z);
	
}
