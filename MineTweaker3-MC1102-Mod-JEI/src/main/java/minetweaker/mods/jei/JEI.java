package minetweaker.mods.jei;

import minetweaker.*;
import minetweaker.api.item.IItemStack;
import minetweaker.api.loadstages.EnumLoadingStage;
import stanhebben.zenscript.annotations.*;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import static minetweaker.mods.jei.JEIAddonPlugin.jeiHelpers;

/**
 * MineTweaker JEI support.
 * <p>
 * Enables hiding JEI items as well as adding new item stacks. These item stacks
 * can then show a custom message or contain NBT data. Can be used to show a
 * custom message or lore with certain items, or to provide spawnable items with
 * specific NBT tags.
 *
 * @author Jared
 */
@ZenClass("mods.jei.JEI")
public class JEI {
	
	/**
	 * Hides a specific item in JEI. Will take into account metadata values, if
	 * any.
	 *
	 * @param item item to be hidden
	 */
	@ZenMethod
	public static void hide(@NotNull IItemStack item) {
		MineTweakerAPI.apply(new JEIHideItemAction(item));
	}
	
	private static class JEIHideItemAction implements IUndoableAction {
		
		private final IItemStack stack;
		
		public JEIHideItemAction(IItemStack stack) {
			this.stack = stack;
		}
		
		@Override
		public void apply() {
			jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(getItemStack(stack));
		}
		
		@Override
		public boolean canUndo() {
			return true;
		}
		
		@Override
		public void undo() {
			jeiHelpers.getIngredientBlacklist().removeIngredientFromBlacklist(getItemStack(stack));
		}
		
		@Override
		public String describe() {
			return "Hiding " + stack + " in JEI";
		}
		
		@Override
		public String describeUndo() {
			return "Displaying " + stack + " in JEI";
		}
		
		@Override
		public Object getOverrideKey() {
			return null;
		}
		
		@Override
		public EnumLoadingStage getLoadingStage() {
			return EnumLoadingStage.SERVER_STARTING;
		}
	}
}
