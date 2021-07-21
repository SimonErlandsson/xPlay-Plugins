package se.simonsigge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.PluginSettings;

public class Booster implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel,
			String[] args) {
		
		if (!(sender instanceof ConsoleCommandSender)) {
			Main.getChatUtilities().sendPlayerMessage((Player) sender, "§cEndast konsollen kan köra detta kommandot. Vill du aktivera en booster? Använd §4/donator §cistället!");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("on")) {
			PluginSettings.BOOSTERACTIVE = true;
			PluginSettings.BOOSTERHOST = args[1];
		} else if (args[0].equalsIgnoreCase("off")) {
			PluginSettings.BOOSTERACTIVE = false;
		}
		
		return false;
		
	}

}
