/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.server;

import minetweaker.api.event.handlers.*;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.server.IServer")
public interface IServer {
	@ZenMethod
    void addCommand(
            String name,
            String usage,
            String[] aliases,
            ICommandFunction function,
            @Optional ICommandValidator validator,
            @Optional ICommandTabCompletion completion);

	@ZenMethod
    void addMineTweakerCommand(String name, String[] usage, ICommandFunction function);

	@ZenMethod
    boolean isOp(IPlayer player);

	@ZenMethod
	void onPlayerLoggedIn(PlayerLoggedInHandler ev);

	@ZenMethod
	void onPlayerLoggedOut(PlayerLoggedOutHandler ev);

	@ZenMethod
    boolean isCommandAdded(String name);
}
