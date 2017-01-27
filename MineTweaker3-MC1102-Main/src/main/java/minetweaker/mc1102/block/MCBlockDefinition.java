/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.block;

import minetweaker.api.block.IBlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

/**
 * @author Stan
 */
public class MCBlockDefinition implements IBlockDefinition{
    private final Block block;

    private final int meta;
    public MCBlockDefinition(Block block, int meta){
        this.block = block;
        this.meta = meta;
    }

    public Block getInternalBlock(){
        return block;
    }

    @Override
    public String getId(){
        return Block.REGISTRY.getNameForObject(block).toString();
    }

    @Override
    public String getDisplayName(){
        return block.getLocalizedName();
    }
    
    @Override
    public int getMeta() {
        return meta;
    }
    
    @Override
    public void setUnbreakable() {
        block.setBlockUnbreakable();
    }
    
    @Override
    public void setHardness(float hardness) {
        block.setHardness(hardness);
    }
    
    @Override
    public void setHarvestLevel(String tool, int level) {
        block.setHarvestLevel(tool, level);
    }
    
    @Override
    public void setLightLevel(float level) {
        block.setLightLevel(level);
    }
    
    @Override
    public void setLightOpacity(int opacity) {
        block.setLightOpacity(opacity);
    }
    
    @Override
    public void setResistance(float resistance) {
        block.setResistance(resistance);
    }
    
    @Override
    public Object getInternal() {
        return block;
    }
}
