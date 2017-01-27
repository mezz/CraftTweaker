/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.block.IBlock;
import minetweaker.api.player.IPlayer;
import minetweaker.api.util.IBlockPos;
import minetweaker.api.world.IBlockGroup;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerBonemealEvent")
public class PlayerBonemealEvent {
	private final IPlayer player;
	private final IBlockGroup blocks;
	private final IBlockPos pos;
	private boolean canceled;
	private boolean processed;

	public PlayerBonemealEvent(IPlayer player, IBlockGroup blocks, IBlockPos pos) {
		this.player = player;
		this.blocks = blocks;
		this.pos = pos;
		
		canceled = false;
		processed = false;
	}

	@ZenMethod
	public void cancel() {
		canceled = true;
	}

	@ZenMethod
	public void process() {
		processed = true;
	}

	@ZenGetter("canceled")
	public boolean isCanceled() {
		return canceled;
	}

	@ZenGetter("processed")
	public boolean isProcessed() {
		return processed;
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
