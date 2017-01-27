package minetweaker.api.block;

import stanhebben.zenscript.annotations.*;

/**
 * Blocks definitions provide additional information about blocks.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.block.IBlockDefinition")
public interface IBlockDefinition {
	
	@ZenGetter("id")
	String getId();
	
	@ZenGetter("displayName")
	String getDisplayName();
	
	@ZenGetter("meta")
	int getMeta();
	
	@ZenMethod
	void setUnbreakable();
	
	@ZenMethod
	void setHardness(float hardness);
	
	@ZenMethod
	void setHarvestLevel(String tool, int level);
	
	@ZenMethod
	void setLightLevel(float level);
	
	@ZenMethod
	void setLightOpacity(int opacity);
	
	@ZenMethod
	void setResistance(float resistance);
	
	Object getInternal();
	
}
