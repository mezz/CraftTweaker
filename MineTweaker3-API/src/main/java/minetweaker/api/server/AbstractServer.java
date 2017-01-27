package minetweaker.api.server;

import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.handlers.*;

/**
 * Abstract server implementation. Implements call of which the implementation
 * is already fixed by the API.
 *
 * @author Stan Hebben
 */
public abstract class AbstractServer implements IServer {
	
	@Override
	public final void addMineTweakerCommand(String name, String[] usage, ICommandFunction function) {
		MineTweakerImplementationAPI.addMineTweakerCommand(name, usage, function);
	}
	
	@Override
	public final void onPlayerLoggedIn(PlayerLoggedInHandler ev) {
		MineTweakerImplementationAPI.events.onPlayerLoggedIn(ev);
	}
	
	@Override
	public final void onPlayerLoggedOut(PlayerLoggedOutHandler ev) {
		MineTweakerImplementationAPI.events.onPlayerLoggedOut(ev);
	}
}
