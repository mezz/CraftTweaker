package minetweaker.api.world.info;

import minetweaker.api.util.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("minetweaker.world.IWorldInfo")
public interface IWorldInfo {
	
	long getSeed();
	
	IBlockPos getSpawnPos();
	
	void setWorldTime(long time);
	
	void setSpawn(IBlockPos spawn);
	
	String getWorldName();
	
	String getDifficulty();
	
	void setDifficulty(String difficulty);
}
