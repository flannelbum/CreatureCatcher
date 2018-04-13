package org.eirinncraft.CreatureCatcher;

import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.eirinncraft.CreatureCatcher.Creatures.CaughtCreatureFactory;

public class CreatureCatcherListener implements Listener {

	private CreatureCatcher plugin;
	
	public CreatureCatcherListener(CreatureCatcher mobCatcher) {
		this.plugin = mobCatcher;
	}
	
	
	public void unregisterEvents(){
		plugin.log("Unregistering event listeners");
		PlayerInteractEntityEvent.getHandlerList().unregister(this);
		PlayerInteractEvent.getHandlerList().unregister(this);
	}
	
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e){
		
		if( e.isCancelled() )
			return;
		
		Player player = e.getPlayer();
		Location location = player.getLocation().clone();
		ItemStack item = player.getInventory().getItemInMainHand();
		Entity entity = e.getRightClicked();
		
		if( e.getHand().equals(EquipmentSlot.HAND) )
			if( CaughtCreatureFactory.validEntityType( entity ) ) 
				if( plugin.isCreatureCaptureItem( item ) ) 
					if( plugin.isEmptyCaptureItem( item )) {
						e.setCancelled(true);
						/**
						 * Mounting a horse causes the player's view to change despite canceling 
						 * this event (or testing with EntityMountEvent/interactat and other type events)
						 * 
						 * So, if it's a horse, just tp the player to the postion we cloned above.
						 * It's a little glitchy but it's better than getting spun around.
						 */
						if( entity instanceof AbstractHorse )
							player.teleport(location, TeleportCause.PLUGIN);
						
						plugin.captureEntity(player, entity);
				}
				
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e){
		
		if( e.isCancelled() )
			return;
		
		Player player = e.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();

		if( plugin.isCreatureCaptureItem( item ) ) {
			// prevent any action from this item by canceling this event
			e.setCancelled(true);

			if( !plugin.isEmptyCaptureItem( item )) {
				
				// attempt restoring the entity. Nothing happens if the token is invalid.
				plugin.restoreEntity( player, getLastLoreLine( item ), e.getClickedBlock().getLocation() );
			}
		}
	}
	
	private String getLastLoreLine(ItemStack item) {
		return item.getItemMeta().getLore().get( item.getItemMeta().getLore().size() - 1 );
	}
	
}
