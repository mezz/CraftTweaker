package minetweaker.mc1102.util;

import minetweaker.api.util.*;
import net.minecraft.util.math.BlockPos;

public class MCUtilities implements IUtilities {
	
	@Override
	public IBlockPos pos(int x, int y, int z) {
		return new MCBlockPos(new BlockPos(x,y,z));
	}
}
