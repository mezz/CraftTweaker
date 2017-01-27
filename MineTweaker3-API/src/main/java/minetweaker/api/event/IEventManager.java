package minetweaker.api.event;


import minetweaker.api.event.handlers.*;
import stanhebben.zenscript.annotations.*;

/**
 * 37 kinds of event handlers!
 * <p>
 * NOTE: not all of these are implemented yet, but will be later on.
 *
 * @author Stan
 */
@ZenClass("minetweaker.api.IEventManager")
public interface IEventManager {
	
	
	void clear();
	
	@ZenMethod
	void onPlayerLoggedIn(PlayerLoggedInHandler ev);
	
	void publishPlayerLoggedIn(PlayerLoggedInEvent ev);
	
	@ZenMethod
	void onPlayerLoggedOut(PlayerLoggedOutHandler ev);
	
	void publishPlayerLoggedOut(PlayerLoggedOutEvent ev);
	
	@ZenMethod
	void onPlayerSmelted(PlayerSmeltedHandler ev);
	
	void publishPlayerSmelted(PlayerSmeltedEvent ev);
	
	@ZenMethod
	void onPlayerCrafted(PlayerCraftedHandler ev);
	
	void publishPlayerCrafted(PlayerCraftedEvent ev);
	
	@ZenMethod
	void onPlayerRespawn(PlayerRespawnHandler ev);
	
	void publishPlayerRespawn(PlayerRespawnEvent ev);
	
	
	//TODO the rest of these
	@ZenMethod
	void onPlayerInteractEntity(PlayerInteractEntityHandler ev);
	
	void publishPlayerInteractEntity(PlayerInteractEntityEvent ev);
	
	@ZenMethod
	void onPlayerInteract(PlayerInteractHandler ev);
	
	void publishPlayerInteract(PlayerInteractEvent ev);
	
	@ZenMethod
	void onPlayerChangedDimension(PlayerChangedDimensionHandler ev);
	
	void publishPlayerChangedDimension(PlayerChangedDimensionEvent ev);
	
	
	// implemented
	
	
	void onPlayerAttackEntity(PlayerAttackEntityEvent ev);
	
	void onPlayerBonemeal(PlayerBonemealEvent ev);
	
	void onPlayerPickup(PlayerPickupEvent ev);
	
	void onPlayerPickupItem(PlayerPickupItemEvent ev);
	
	void onPlayerFillBucket(PlayerFillBucketEvent ev);
	
	void onPlayerDeathDrops(PlayerDeathDropsEvent ev);
	
	void onPlayerOpenContainer(PlayerOpenContainerEvent ev);
	
	void onPlayerPickupXp(PlayerPickupXpEvent ev);
	
	void onPlayerSleepInBed(PlayerSleepInBedEvent ev);
	
	void onPlayerUseHoe(PlayerUseHoeEvent ev);
	
	void onPlayerUseItemStart(PlayerUseItemStartEvent ev);
	
	void onPlayerUseItemTick(PlayerUseItemTickEvent ev);

	/*
	 * void
	 * onPlayerUseItemStop(PlayerUseItemStopEvent ev);
	 * 
	 * void
	 * onPlayerUseItemFinish(IPlayerUserItemFinishEventHandler ev);
	 * 
	 * void onPlayerChat(IPlayerChatEventHandler ev);
	 * 
	 * void onTimerSingle(int millis, ITimerEventHandler ev);
	 * 
	 * void onTimerRepeat(int millis, ITimerEventHandler ev);
	 * 
	 * void onEntityJoinWorld(IEntityJoinWorldEventHandler ev);
	 * 
	 * void
	 * onEntityStruckByLightning(IEntityStruckByLightningEventHandler ev);
	 * 
	 * void
	 * onLivingEnderTeleport(ILivingEnderTeleportEventHandler ev);
	 * 
	 * void onLivingAttackEvent(ILivingAttackEventHandler ev);
	 * 
	 * void onLivingDeathEvent(ILivingDeathEventHandler ev);
	 * 
	 * void onLivingJumpEvent(ILivingJumpEventHandler ev);
	 * 
	 * void onLivingFallEvent(ILivingFallEventHandler ev);
	 * 
	 * void onLivingHurtEvent(ILivingHurtEventHandler ev);
	 * 
	 * void onLivingDeathDropsEvent(ILivingDeathDropsEventHandler
	 * ev);
	 * 
	 * void onItemTossed(IItemTossedEventHandler ev);
	 * 
	 * void onItemExpired(IItemExpiredEventHandler ev);
	 * 
	 * void onMinecartCollision(IMinecartCollisionEventHandler
	 * ev);
	 * 
	 * void onMinecartInteract(IMinecartInteractEventHandler ev);
	 * 
	 * void onCommand(ICommandEventHandler ev);
	 */
}
