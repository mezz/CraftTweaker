package minetweaker;

/**
 * Base implementation for a non-undoable action.
 * 
 * @author Stan Hebben
 */
public abstract class OneWayAction implements IUndoableAction {
	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void undo() {
		
	}

	@Override
	public String describeUndo() {
		return "";
	}
}
