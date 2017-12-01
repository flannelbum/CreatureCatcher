package org.eirinncraft.CreatureCatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class TestPlayerHandler {
	
	private List<String> testPlayersList;
	private CreatureCatcher plugin;
	
	public TestPlayerHandler(CreatureCatcher plugin){
		this.plugin = plugin;
		this.testPlayersList = getTestPlayers();
	}
	
	@SuppressWarnings("deprecation")
	public UUID getUUIDforPlayername(String playername){
		OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(playername);
		if (offlinePlayer.hasPlayedBefore()) 
			return offlinePlayer.getUniqueId();
		return null;
	}
	
	public List<String> getTestPlayers(){
		if( testPlayersList == null )
			testPlayersList = plugin.getConfig().getStringList("mobcatcher.testplayers");
		if( testPlayersList == null )
			testPlayersList = new ArrayList<String>();
		return testPlayersList;
	}
	
	public void resetPlayerCommand(CommandSender sender, String playername) {
		UUID uuid = getUUIDforPlayername(playername); 
		if( uuid != null )
			if( testPlayersList.remove( uuid.toString() )){
				plugin.getConfig().set("mobcatcher.testplayers", testPlayersList);
				plugin.saveConfig();
				sender.sendMessage("reset " + playername);
				return;
			}
		sender.sendMessage(playername + " not found");
	}
	
	public void addTestplayer(UUID uuid) { 
		testPlayersList.add( uuid.toString() ); 
		plugin.getConfig().set("mobcatcher.testplayers", testPlayersList);
		plugin.saveConfig();
	}
	
	public boolean isAlreadyTestPlayer(String uuidstring){
		return testPlayersList.contains( uuidstring );
	}	
	////  END testplayer stuff
}
