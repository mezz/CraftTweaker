package minetweaker.api.biome;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("minetweaker.biome.IBiome")
public interface IBiome {
	
	String getName();
	
	boolean isSnowyBiome();
	
	boolean canRain();
	
	boolean isHighHumidity();
	
	float getSpawningChance();
	
	float getBaseHeight();
	
	float getRainfall();
	
	float getTemperature();
	
	float getHeightVariation();
	
}
