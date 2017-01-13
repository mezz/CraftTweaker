package minetweaker.api.loadstages;

public enum EnumLoadingStage {
	PREINITIALIZATION(0),
	INITIALIZATION(1),
	POSTINITIALIZATION(2),
	AVAILABLE(3),
	SERVER_ABOUT_TO_START(4),
	SERVER_STARTING(5),
	SERVER_STARTED(6),
	SERVER_STOPPING(7),
	SERVER_STOPPED(8);
	
	int priority;
	
	EnumLoadingStage(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}
}
