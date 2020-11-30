package org.eirinncraft.CreatureCatcher;

import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;

import org.eirinncraft.CreatureCatcher.Creatures.CaughtCreature;
import org.eirinncraft.CreatureCatcher.Creatures.CaughtCreatureFactory;
import org.eirinncraft.CreatureCatcher.Util.Database;
import org.eirinncraft.CreatureCatcher.Util.SQLite;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CreatureCatcher extends JavaPlugin{

	private CreatureCatcherListener ccListener;
	private List<String> lore;
	private Database db;

	private int cmodelEmpty;
	private int cmodelFull;
	
	private TestPlayerHandler tph;
	
	@Override
	public void onEnable() {

		// some default config stuff before database stuff
		this.cmodelEmpty = getConfig().getInt("creaturecatcher.CustomModelData.creature_catcher_empty");
		this.cmodelFull = getConfig().getInt("creaturecatcher.CustomModelData.creature_catcher_full");
		if( this.cmodelEmpty == 0 )
			this.cmodelEmpty = 110401;
		if( this.cmodelFull == 0 )
			this.cmodelFull = 110402;
		getConfig().set("creaturecatcher.CustomModelData.creature_catcher_empty", this.cmodelEmpty);
		getConfig().set("creaturecatcher.CustomModelData.creature_catcher_full", this.cmodelFull);
		saveConfig();

		// database stuff
		db = new SQLite(this);

		getServer().getPluginManager().registerEvents(getCreatureCatcherListener(), this);
		getCommand("getcreaturecatcher").setExecutor(new CreatureCatcherCommand(this));
			
		log("enabled");
	}
	
	@Override
	public void onDisable() {

		getConfig().set("creaturecatcher.testplayers", getTestPlayerHandler().getTestPlayers());
		saveConfig();

		getCreatureCatcherListener().unregisterEvents();
		
		log("disabled");
	}
	
	
	public TestPlayerHandler getTestPlayerHandler(){ 
		if( tph == null )
			tph = new TestPlayerHandler(this);
		return tph; 
	}
	
	public CreatureCatcherListener getCreatureCatcherListener() {
		if( ccListener == null )
			ccListener = new CreatureCatcherListener(this);
		return ccListener;
	}
	
	public void log(String msg) { getLogger().info("CreatureCatcher: " + msg);}
	
	
	public List<String> getLore() {
		if( lore == null ){
			lore = new ArrayList<String>();
			lore.add("Catch and Release");
			lore.add("them creatures!");
		}
		return lore;
	}
	
	
	public boolean isCreatureCaptureItem(ItemStack item) {
		if( item.getType().equals(Material.WOODEN_HOE) )
			if( item.hasItemMeta() )
				if( item.getItemMeta().hasLore() )					
					if( getLore().get(0).equals(item.getItemMeta().getLore().get(0)) )
						return true;
		return false;
	}
	

	public boolean isEmptyCaptureItem(ItemStack item){
		if( item.getType().equals(Material.WOODEN_HOE) )
			if( item.hasItemMeta() )
				if( item.getItemMeta().hasLore() ) {
					// If we have an extra line in our lore, it should be our token
					if( item.getItemMeta().getLore().size() > getLore().size() )
						return false;
					else if( item.getItemMeta().getLore().size() == getLore().size() )
						return true;
				}
		return false;
	}

	
	public ItemStack getCreatureCatcherItem() {
		ItemStack item = new ItemStack(Material.WOODEN_HOE);
		ItemMeta meta = item.getItemMeta();

		meta.setLore( getLore() );
		
		meta.setDisplayName("CreatureCatcher - Empty");

		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

		meta.setCustomModelData(this.cmodelEmpty);

		item.setItemMeta(meta);

		return item;
	}
	
	//OVERLOAD with displayname and token
	public ItemStack getCreatureCatcherItem(String displayname, String token){
		ItemStack item = new ItemStack(Material.WOODEN_HOE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		for( String line : getLore() )
			lore.add( line );
		
		lore.add("");
		lore.add( token );
		meta.setLore( lore );
		
		meta.setDisplayName("Caught: " + displayname);

		meta.setUnbreakable(true);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

		meta.setCustomModelData(this.cmodelFull);

		item.setItemMeta(meta);
		
		return item;
	}

	
	public void captureEntity(Player player, Entity entity) {
		
		if ( !(entity instanceof Creature) )
			return;

		//Tameable handling
		if( entity instanceof Tameable ){
			
			if( ((Tameable) entity).isTamed() )
				if( ((Tameable) entity).getOwner() != null )
					if( !((Tameable) entity).getOwner().getUniqueId().equals( player.getUniqueId() ) )
						if( !player.hasPermission("creaturecatcher.catchothers") ){
							player.sendMessage(((Tameable) entity).getOwner().getName() + "'s creature.  You may not capture it.");
							return;
						} 

			((Tameable) entity).setOwner(player);
						
		}
		
		CaughtCreature creature = CaughtCreatureFactory.catchEntity( entity );
		db.updateCreature(creature);
		
		entity.remove();

		player.getInventory().setItemInMainHand( getCreatureCatcherItem(creature.getDisplayName(), creature.getToken()) );
		
		log(player.getName() + " made token: " + creature.getToken() + " (" + creature.getType() + "): " + creature.getDisplayName());

		
		
	}

	
	public void restoreEntity(Player player, String token, Location location) {
		
		CaughtCreature creature = db.getCreature(token);
		
		if( creature != null ) {
			
			Entity entity = creature.spawn(location);
			
			if( creature.isTamed() )
				((Tameable) entity).setOwner( player.getServer().getOfflinePlayer( creature.getOwnerUUID() ));
			
			log(player.getName() + " used token: " + token + " (" + creature.getType() + "): " + creature.getDisplayName());
			
			db.deleteCreature(token);
			player.getInventory().setItemInMainHand(getCreatureCatcherItem());
			
		} else {
			log("CREATURE RESTORE ISSUE: " + player.getName() + " tried to restore token: " + token);
		}
		
	}

}
