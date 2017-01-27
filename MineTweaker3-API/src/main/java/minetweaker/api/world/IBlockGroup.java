/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.world;

import minetweaker.api.block.IBlock;
import minetweaker.api.util.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.world.IBlockGroup")
public interface IBlockGroup {
	@ZenGetter("dimension")
    IDimension getDimension();

	@ZenMethod
	IBlock getBlock(IBlockPos pos);
}
