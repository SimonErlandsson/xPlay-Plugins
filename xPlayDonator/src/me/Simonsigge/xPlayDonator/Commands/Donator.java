package me.Simonsigge.xPlayDonator.Commands;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.GUI;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Donator implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String cmdlabel, String[] args) {

		if (!Main.getDataHandler().isDonor((Player) sender)) {
			Main.getMsgAPI().noPerm((Player) sender);
			return true;
		}

		if (sender.getServer().getWorlds().get(0).getName().equals("xPlayPvP")) {
			World w = Main.getInstance().getServer().getWorlds().get(0);
			if (!isWithinLoc(((Player) sender).getLocation(), new Location(w,
					-30, 292, 30), new Location(w, 30, 0, -30))) {
				Main.getMsgAPI().donatorIsDisabledOutsideOfSpawn(
						(Player) sender);
				return true;
			}
		}

		if (sender.getServer().getWorlds().get(0).getName().equals("xPlaySpel")) {
			World w = Main.getInstance().getServer().getWorlds().get(0);
			if (!isWithinLoc(((Player) sender).getLocation(), new Location(w,
					-200, 292, 200), new Location(w, 200, 0, -200))) {
				Main.getMsgAPI().donatorIsDisabledOutsideOfSpawn(
						(Player) sender);
				return true;
			}
		}

		Main.getMsgAPI().successOpenGUI((Player) sender);
		Main.getGUIHandler().displayGUI((Player) sender, GUI.MAIN);

		return false;
	}

	private boolean isWithinLoc(Location playerLoc, Location loc1, Location loc2) {
		if (playerLoc.getBlockX() >= loc1.getBlockX()
				&& playerLoc.getBlockX() <= loc2.getBlockX())
			if (playerLoc.getBlockY() <= loc1.getBlockY()
					&& playerLoc.getBlockY() >= loc2.getBlockY())
				if (playerLoc.getBlockZ() <= loc1.getBlockZ()
						&& playerLoc.getBlockZ() >= loc2.getBlockZ())
					return true;
		return false;
	}

}
