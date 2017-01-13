package minetweaker;

public interface IUndoableAction extends IAction {
	/**
	 * Checks if this action can be undone. If this method returns true, it must
	 * implement undo() properly. If this method returns false, the action is
	 * considered permanent, undo() will never be called, and the action cannot
	 * be executed as part of a server script. Additionally, the
	 * minetweaker.clear() function will not work and minetweaker.canClear will
	 * return false.
	 *
	 * @return undoable status
	 */
	boolean canUndo();
	
	/**
	 * Undoes the action. Will only be called after apply() has been executed.
	 * After an undo, apply may be called again. They could be done multiple
	 * times in certain scenarios.
	 */
	void undo();
	
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
	Object getOverrideKey();
	
	/**
	 * Describes what this action does if it is undone. Similar to the
	 * describe() method. No implementation is needed if the action cannot be
	 * undone.
	 *
	 * @return the description of this action, when undone
	 */
	String describeUndo();
}
