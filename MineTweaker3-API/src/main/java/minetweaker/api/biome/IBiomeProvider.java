package minetweaker.api.biome;

import minetweaker.api.util.IBlockPos;
import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.biome.IBiomeProvider")
public interface IBiomeProvider {
	
	@ZenMethod
	IBiome getBiome(IBlockPos pos);
	
}
