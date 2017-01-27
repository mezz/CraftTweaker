package minetweaker.api.world.provider;

import minetweaker.api.biome.IBiomeProvider;
import minetweaker.api.util.IBlockPos;
import minetweaker.api.world.type.IWorldType;
import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.world.IWorldProvider")
public interface IWorldProvider {
	
	@ZenGetter("moonPhase")
	int getMoonPhase();
	
	@ZenGetter("overworld")
	boolean isOverworld();
	
	@ZenGetter("spawnCoordinates")
	IBlockPos getSpawnCoordinates();
	
	@ZenGetter("averageGroundLevel")
	int getAverageGroundLevel();
	
	@ZenGetter("type")
	IWorldType getWorldType();
	
	@ZenGetter("biomeProvider")
	IBiomeProvider getBiomeProvider();
	
	@ZenGetter("vaporizeWater")
	boolean doesWaterVaporize();
	
	@ZenGetter("hasNoSky")
	boolean hasNoSky();
	
	@ZenGetter("dimensionID")
	int getDimensionID();
	
	@ZenGetter("dayTime")
	boolean isDayTime();
}
