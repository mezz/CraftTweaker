package minetweaker.api.world.type;

import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.world.IWorldType")
public interface IWorldType {
	
	@ZenGetter("name")
	String getWorldTypeName();
}
