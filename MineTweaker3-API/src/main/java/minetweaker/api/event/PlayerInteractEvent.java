/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.block.IBlock;
import minetweaker.api.player.IPlayer;
import minetweaker.api.util.IBlockPos;
import minetweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerInteractEvent")
public class PlayerInteractEvent {
	
	private final IPlayer player;
	private final IBlockGroup blocks;
	
	private final IBlockPos pos;
	private boolean canceled;
	private boolean useBlock;
	private boolean useItem;
	
	public PlayerInteractEvent(IPlayer player, IBlockGroup blocks, IBlockPos pos) {
		this.player = player;
		this.blocks = blocks;
		this.pos = pos;
		
		canceled = false;
		useBlock = false;
		useItem = false;
	}
	
	@ZenMethod
	public void cancel() {
		canceled = true;
	}
	
	@ZenMethod
	public void useBlock() {
		useBlock = true;
	}
	
	@ZenMethod
	public void useItem() {
		useItem = true;
	}
	
	@ZenGetter("canceled")
	public boolean isCanceled() {
		return canceled;
	}
	
	@ZenGetter("usingBlock")
	public boolean isUsingBlock() {
		return useBlock;
	}
	
	@ZenGetter("usingItem")
	public boolean isUsingItem() {
		return useItem;
	}
	
	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}
	
	@ZenGetter("blocks")
	public IBlockGroup getBlocks() {
		return blocks;
	}
	
	@ZenGetter("pos")
	public IBlockPos getPos() {
		return pos;
	}
	
	@ZenGetter("block")
	public IBlock getBlock() {
		return blocks.getBlock(pos);
	}
	
	@ZenGetter("dimension")
	public IDimension getDimension() {
		return blocks.getDimension();
	}
}
