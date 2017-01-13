package minetweaker;

import minetweaker.api.loadstages.EnumLoadingStage;

/**
 * Defines a (possibly) undoable action.
 * <p>
 * Every modification made by a command must be registered to the Tweaker with
 * the Tweaker.apply method. This enables MineTweaker to keep track of actions
 * that have been performed, and enables them to be rolled back later on.
 * <p>
 * Actions exist in two flavors: undoable and permanent. If an action returns
 * canUndo(), the action is undoable, otherwise it is permanent. Permanent
 * actions cannot be used in server scripts.
 * <p>
 * There also exist semi-permanent actions. Those actions can return an override
 * key; if a newer action has the same override key as an old action, the new
 * action is considered to override the old one.
 * <p>
 * Likewise, actions can implement hashCode and equals methods to indicate that
 * they are equal. If an action is equal to a non-undoable stuck action, it will
 * be omitted from execution.
 *
 * @author Stan Hebben
 */
public interface IAction {
	
	/**
	 * Executes what the action is supposed to do. This method can be called
	 * again if undo() has been called in between.
	 */
	void apply();
	
	
	/**
	 * Describes, in a single human-readable sentence, what this specific action
	 * is doing. Used in logging messages, lists, ...
	 * <p>
	 * Try to be as descriptive as possible without being too verbose.
	 * <p>
	 * Examples: - Adding Peach planks to the woodPlanks ore dictionary entry -
	 * Removing a recipe for Iron Ore
	 *
	 * @return the description of this action
	 */
	String describe();
	
	/**
	 * Returns the override key. Two actions are considered to override each
	 * other if their override key is equal. You can return null to indicate
	 * that an action can never be overridden.
	 * <p>
	 * This value only makes sense for recipes that are not undoable. For
	 * undoable recipes, you should return null.
	 *
	 * @return override key (null for actions that are undoable or which can
	 * never be overridden by another action)
	 */
	default Object getOverrideKey(){
		return null;
	}
	
	/**
	 * Returns the loading stage that this action should run in, this can be used to lock an action to a certain stage.
	 * <p>
	 * For example, Blocks should only be added in PREINIT, so if your action adds a block, it should return EnumLoadingStage.PREINIT and it will never be ran outside of PREINIT.
	 * <p>
	 * If your Action acts like the old "legacy" actions, then it should return EnumLoadingStage.SERVER_STARTING which will allow it to be reloaded
	 *
	 * @return Loading stage for this action to run in
	 */
	default EnumLoadingStage getLoadingStage(){
		return EnumLoadingStage.SERVER_STARTING;
	}
}
