package minetweaker.api.world;

import minetweaker.api.block.*;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.util.IBlockPos;
import minetweaker.api.world.info.IWorldInfo;
import minetweaker.api.world.provider.IWorldProvider;
import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.world.IWorld")
public interface IWorld {
	
	@ZenGetter("seaLevel")
	int getSeaLevel();
	
	@ZenGetter("rainingStrength")
	float getRainingStrength();
	
	@ZenGetter("thunderingStrength")
	float getThunderingStrength();
	
	@ZenGetter("provider")
	IWorldProvider getProvider();
	
	@ZenGetter("info")
	IWorldInfo getWorldInfo();
	
	@ZenGetter("clientSide")
	boolean isClientSide();
	
	@ZenGetter("serverSide")
	boolean isServerSide();
	
	@ZenGetter("worldTime")
	long getWorldTime();
	
	@ZenGetter("worldTimeTotal")
	long getTotalWorldTime();
	
	@ZenGetter("spawnPoint")
	IBlockPos getSpawnPoint();
	
	
	@ZenMethod
	boolean isSpawnChunk(int x, int z);
	
	@ZenMethod
	boolean isAirBlock(IBlockPos pos);
	
	@ZenMethod
	void setBlock(IBlockPos pos, IBlockDefinition blockDefinition, int flags);
	
	@ZenMethod
	void setBlockToAir(IBlockPos pos);
	
	@ZenMethod
	void destroyBlock(IBlockPos pos, boolean dropBlock);
	
	@ZenMethod
	boolean canSeeSky(IBlockPos pos);
	
	@ZenMethod
	int getLight(IBlockPos pos, @Optional boolean checkNeighbors);
	
	@ZenMethod
	IBlockPos getHeight(int x, int z);
	
	@ZenMethod
	IBlockPos getHeight(IBlockPos pos);
	
	@ZenMethod
	IBlock getBlock(IBlockPos pos);
	
	@ZenMethod
	void spawnEntity(IEntityDefinition entity, double x, double y, double z);
	
	@ZenMethod
	void createExplosion(double x, double y, double z, float strength, @Optional boolean isSmoking);
	
	@ZenMethod
	boolean isWater(IBlockPos pos);
	
	
}
