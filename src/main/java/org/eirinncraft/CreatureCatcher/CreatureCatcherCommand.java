package org.eirinncraft.CreatureCatcher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreatureCatcherCommand implements CommandExecutor {

	private CreatureCatcher plugin;

	public CreatureCatcherCommand(CreatureCatcher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player = null;
		boolean isOP = false;
		boolean isConsoleCMD = false;

		if( sender instanceof Player) {
			player = (Player) sender;
			if( player.isOp() )
				isOP = true;
		} else if( sender instanceof ConsoleCommandSender)
			isConsoleCMD = true;



		if( isOP || isConsoleCMD ) { 
			if( args.length > 0 ) {
				
				if( args[0].equalsIgnoreCase("reset") )
					if( args.length > 1 ) 
						plugin.getTestPlayerHandler().resetPlayerCommand(sender, args[1]);
					else
						sender.sendMessage("Must supply username to reset");
				
			} else if( isOP ) 
				player.getInventory().addItem( plugin.getCreatureCatcherItem() );

			return true;
		}

		
		if( player == null )
			return true;


		if( plugin.getTestPlayerHandler().isAlreadyTestPlayer( player.getUniqueId().toString() )){
			player.sendMessage("You've already spawned a test creaturecatcher item!");
			return true;
		}

		
		player.sendMessage("REMEMBER: This test creaturecatcher item cannot be spawned again and is subject to change at any time.");
		player.sendMessage("Thank you for helping test it out!");
		player.getInventory().addItem( plugin.getCreatureCatcherItem() );
		plugin.getTestPlayerHandler().addTestplayer(player.getUniqueId());
		
		return true;

	}


}
