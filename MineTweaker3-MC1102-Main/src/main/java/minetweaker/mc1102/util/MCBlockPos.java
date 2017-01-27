package minetweaker.mc1102.util;

import minetweaker.api.util.IBlockPos;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.*;

public class MCBlockPos implements IBlockPos {
	
	private BlockPos pos;
	
	public MCBlockPos(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public IBlockPos setX(int x) {
		return new MCBlockPos(new BlockPos(x, pos.getY(), pos.getZ()));
	}
	
	@Override
	public IBlockPos setY(int y) {
		return new MCBlockPos(new BlockPos(pos.getX(), y, pos.getZ()));
	}
	
	@Override
	public IBlockPos setZ(int z) {
		return new MCBlockPos(new BlockPos(pos.getX(), pos.getY(), z));
	}
	
	@Override
	public IBlockPos setPos(int x, int y, int z) {
		return new MCBlockPos(new BlockPos(x,y,z));
	}
	
	@Override
	public IBlockPos up(@Optional int distance) {
		return new MCBlockPos(pos.up(distance == 0 ? 1 : distance));
	}
	
	@Override
	public IBlockPos down(@Optional int distance) {
		return new MCBlockPos(pos.down(distance == 0 ? 1 : distance));
	}
	
	@Override
	public IBlockPos north(@Optional int distance) {
		return new MCBlockPos(pos.north(distance == 0 ? 1 : distance));
	}
	
	@Override
	public IBlockPos east(@Optional int distance) {
		return new MCBlockPos(pos.east(distance == 0 ? 1 : distance));
	}
	
	@Override
	public IBlockPos south(@Optional int distance) {
		return new MCBlockPos(pos.south(distance == 0 ? 1 : distance));
	}
	
	@Override
	public IBlockPos west(@Optional int distance) {
		return new MCBlockPos(pos.west(distance == 0 ? 1 : distance));
	}
	
	@Override
	public String asString() {
		return pos.toString();
	}
	
	@Override
	public Object getInternal() {
		return pos;
	}
}
