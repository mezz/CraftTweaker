/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.world;

import minetweaker.api.block.IBlock;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.util.IBlockPos;
import minetweaker.api.world.IDimension;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Stan
 */
public class MCDimension implements IDimension{
    private final World world;

    public MCDimension(World world){
        this.world = world;
    }

    @Override
    public boolean isDay(){
        return world.isDaytime();
    }

    @Override
    public int getBrightness(IBlockPos pos){
        return world.getLight((BlockPos) pos.getInternal());
    }

    @Override
    public IDimension getDimension(){
        return this;
    }

    @Override
    public IBlock getBlock(IBlockPos pos){
        return MineTweakerMC.getBlock(world, (BlockPos) pos.getInternal());
    }
}
