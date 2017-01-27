/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.world;

import minetweaker.api.biome.IBiome;
import net.minecraft.world.biome.Biome;

/**
 * @author Stan
 */
public class MCBiome implements IBiome {
    private final Biome biome;

    public MCBiome(Biome biome){
        this.biome = biome;
    }

    @Override
    public String getName(){
        return biome.getBiomeName();
    }
    
    @Override
    public boolean isSnowyBiome() {
        return biome.isSnowyBiome();
    }
    
    @Override
    public boolean canRain() {
        return biome.canRain();
    }
    
    @Override
    public boolean isHighHumidity() {
        return biome.isHighHumidity();
    }
    
    @Override
    public float getSpawningChance() {
        return biome.getSpawningChance();
    }
    
    @Override
    public float getBaseHeight() {
        return biome.getBaseHeight();
    }
    
    @Override
    public float getRainfall() {
        return biome.getRainfall();
    }
    
    @Override
    public float getTemperature() {
        return biome.getTemperature();
    }
    
    @Override
    public float getHeightVariation() {
        return biome.getHeightVariation();
    }
}
