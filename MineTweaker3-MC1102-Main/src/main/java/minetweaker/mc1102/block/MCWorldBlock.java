/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.block;

import minetweaker.api.block.BlockPatternOr;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.block.IBlockPattern;
import minetweaker.api.data.IData;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

/**
 * @author Stan
 */
public class MCWorldBlock implements IBlock {
    private final IBlockAccess blocks;
    private final BlockPos pos;

    public MCWorldBlock(IBlockAccess blocks, BlockPos pos) {
        this.blocks = blocks;
        this.pos = pos;
    }

    @Override
    public IBlockDefinition getDefinition() {
        return MineTweakerMC.getBlockDefinition(blocks.getBlockState(pos).getBlock(), blocks.getBlockState(pos).getBlock().getMetaFromState(blocks.getBlockState(pos)));
    }

    @Override
    public int getMeta() {
        return blocks.getBlockState(pos).getBlock().getMetaFromState(blocks.getBlockState(pos));
    }

    @Override
    public IData getTileData() {
        TileEntity tileEntity = blocks.getTileEntity(pos);

        if(tileEntity == null)
            return null;

        NBTTagCompound nbt = new NBTTagCompound();
        tileEntity.writeToNBT(nbt);
        return MineTweakerMC.getIData(nbt);
    }
    
    @Override
    public boolean isBlock(IBlockDefinition blockDefinition) {
        return blocks.getBlockState(pos).getBlock() == ((Block)blockDefinition.getInternal()).getStateFromMeta(blockDefinition.getMeta()).getBlock();
    }
    
    @Override
    public String getDisplayName() {
        Block block = blocks.getBlockState(pos).getBlock();
        Item item = Item.getItemFromBlock(block);
        if(item != null) {
            return (new ItemStack(item, 1, getMeta())).getDisplayName();
        } else {
            return block.getLocalizedName();
        }
    }

    @Override
    public List<IBlock> getBlocks() {
        return Collections.singletonList(this);
    }

    @Override
    public boolean matches(IBlock block) {
        return getDefinition() == block.getDefinition() && (getMeta() == OreDictionary.WILDCARD_VALUE || getMeta() == block.getMeta()) && (getTileData() == null || (block.getTileData() != null && block.getTileData().contains(getTileData())));
    }

    @Override
    public IBlockPattern or(IBlockPattern pattern) {
        return new BlockPatternOr(this, pattern);
    }
}
