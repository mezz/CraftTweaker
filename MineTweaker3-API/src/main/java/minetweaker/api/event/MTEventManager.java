package minetweaker.api.event;

import minetweaker.api.event.handlers.*;

import java.util.LinkedList;

public class MTEventManager implements IEventManager {
	
	private LinkedList<PlayerLoggedInHandler> elPlayerLoggedIn = new LinkedList<>();
	private LinkedList<PlayerLoggedOutHandler> elPlayerLoggedOut = new LinkedList<>();
	private LinkedList<PlayerSmeltedHandler> elPlayerSmelted = new LinkedList<>();
	private LinkedList<PlayerCraftedHandler> elPlayerCrafted = new LinkedList<>();
	private LinkedList<PlayerRespawnHandler> elPlayerRespawn= new LinkedList<>();
	private LinkedList<PlayerChangedDimensionHandler> elPlayerChangedDimension= new LinkedList<>();
	
	
	
	@Override
	public void clear() {
		elPlayerLoggedIn.clear();
		elPlayerLoggedOut.clear();
		elPlayerSmelted.clear();
		elPlayerCrafted.clear();
		elPlayerRespawn.clear();
		elPlayerChangedDimension.clear();
	}
	
	@Override
	public void onPlayerLoggedIn(PlayerLoggedInHandler ev) {
		elPlayerLoggedIn.add(ev);
	}
	
	@Override
	public void publishPlayerLoggedIn(PlayerLoggedInEvent ev) {
		elPlayerLoggedIn.forEach(event -> event.handle(ev));
	}
	
	@Override
	public void onPlayerLoggedOut(PlayerLoggedOutHandler ev) {
		elPlayerLoggedOut.add(ev);
	}
	
	@Override
	public void publishPlayerLoggedOut(PlayerLoggedOutEvent ev) {
		elPlayerLoggedOut.forEach(event -> event.handle(ev));
	}
	
	@Override
	public void onPlayerSmelted(PlayerSmeltedHandler ev) {
		elPlayerSmelted.add(ev);
	}
	
	@Override
	public void publishPlayerSmelted(PlayerSmeltedEvent ev) {
		elPlayerSmelted.forEach(event -> event.handle(ev));
	}
	
	@Override
	public void onPlayerCrafted(PlayerCraftedHandler ev) {
		elPlayerCrafted.add(ev);
	}
	
	@Override
	public void publishPlayerCrafted(PlayerCraftedEvent ev) {
		elPlayerCrafted.forEach(event -> event.handle(ev));
	}
	
	@Override
	public void onPlayerRespawn(PlayerRespawnHandler ev) {
		elPlayerRespawn.add(ev);
	}
	
	@Override
	public void publishPlayerRespawn(PlayerRespawnEvent ev) {
		elPlayerRespawn.forEach(event -> event.handle(ev));
	}
	
	@Override
	public void onPlayerChangedDimension(PlayerChangedDimensionHandler ev) {
		elPlayerChangedDimension.add(ev);
	}
	
	@Override
	public void publishPlayerChangedDimension(PlayerChangedDimensionEvent ev) {
		elPlayerChangedDimension.forEach(event -> event.handle(ev));
	}
	//TODO implement the rest
	@Override
	public void onPlayerInteractEntity(PlayerInteractEntityHandler ev) {
		
	}
	
	@Override
	public void publishPlayerInteractEntity(PlayerInteractEntityEvent ev) {
		
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractHandler ev) {
		
	}
	
	@Override
	public void publishPlayerInteract(PlayerInteractEvent ev) {
		
	}
	
	
	
	
	
	@Override
	public void onPlayerAttackEntity(PlayerAttackEntityEvent ev) {
		
	}
	
	@Override
	public void onPlayerBonemeal(PlayerBonemealEvent ev) {
		
	}
	
	@Override
	public void onPlayerPickup(PlayerPickupEvent ev) {
		
	}
	
	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent ev) {
		
	}
	
	@Override
	public void onPlayerFillBucket(PlayerFillBucketEvent ev) {
		
	}
	
	@Override
	public void onPlayerDeathDrops(PlayerDeathDropsEvent ev) {
		
	}
	
	@Override
	public void onPlayerOpenContainer(PlayerOpenContainerEvent ev) {
		
	}
	
	@Override
	public void onPlayerPickupXp(PlayerPickupXpEvent ev) {
		
	}
	
	@Override
	public void onPlayerSleepInBed(PlayerSleepInBedEvent ev) {
		
	}
	
	@Override
	public void onPlayerUseHoe(PlayerUseHoeEvent ev) {
		
	}
	
	@Override
	public void onPlayerUseItemStart(PlayerUseItemStartEvent ev) {
		
	}
	
	@Override
	public void onPlayerUseItemTick(PlayerUseItemTickEvent ev) {
		
	}
}
