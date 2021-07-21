package me.Simonsigge.xPlayDonator.Commands;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Booster;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DonatorAdmin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel,
			String[] args) {
		
		if (!(sender instanceof ConsoleCommandSender)) {
			Main.getMsgAPI().consoleOnly((Player) sender);
			return true;
		}
		
		if (args.length != 4) {
			wrongSyntax(sender);
			return false;
		}
		
		sender.sendMessage("§aVäntar en sekund på PEX, utifall...");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				boolean addOrRemove;
				if (args[0].equalsIgnoreCase("add"))
						addOrRemove = true;
				else if (args[0].equalsIgnoreCase("remove"))
					addOrRemove = false;
				else {
					wrongSyntax(sender);
					return;
				}
				
				Player p = null;
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (pl.getName().toLowerCase().equals(args[1].toLowerCase())) {
						p = pl;
						break;
					}
				}
				if (p == null) {
					sender.sendMessage("§cSpelaren är inte online på delservern.");
					return;
				}
				
				Booster booster = null;
				if (args[2].equalsIgnoreCase("pvp"))
					booster = Booster.PVP;
				else if (args[2].equalsIgnoreCase("drop"))
					booster = Booster.DROP;
				else if (args[2].equalsIgnoreCase("arbete"))
					booster = Booster.ARBETE;
				else {
					wrongSyntax(sender);
					return;
				}
				
				for (int i2 = 0; i2 < args[3].length(); i2++)
					if (!Character.isDigit(args[3].charAt(i2))) {
						wrongSyntax(sender);
						return;
					}
				int number = Integer.parseInt(args[3]);
				
				Main.getBoosterAdder().scheduleBoosterAdd(p, booster, number, addOrRemove, sender);
			}} , 20 * 1);
		
		return false;
	}
	
	private void wrongSyntax(CommandSender sender) {
		//donatoradmin add Simonsigge pvp 1
		//donatoradmin remove Simonsigge drop 1
		sender.sendMessage("§cFel användning av kommando. /boosteradmin [add/remove] [spelare] [pvp/arbete/drop] [antal]");
	}

}
