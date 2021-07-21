package se.simonsigge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import se.simonsigge.xplaypvp.Main;


public class Kit implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel,
			String[] args) {
		Player p = (Player) sender;
		Main.getGuiManager().openSelectGui(p);
		
		return false;
	}

}
