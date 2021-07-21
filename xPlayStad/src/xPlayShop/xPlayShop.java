package xPlayShop;

import me.Simonsigge.xPlayStad.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class xPlayShop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel,
			String[] args) {
		if (args.length != 1) 
			Main.getInstance().guiManager.openDefaultGui((Player) sender);
		else {
			if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.AQUA + "xPlayShop Info:");
				sender.sendMessage(ChatColor.AQUA + "CurrentBrowsers: " + Main.getInstance().guiManager.currentBrowsers.size());
				sender.sendMessage(ChatColor.AQUA + "CurrentConfirms: " + Main.getInstance().guiManager.currentConfirms.size());
			} else
				Main.getInstance().guiManager.openDefaultGui((Player) sender);
		}
		return false;
	}
}
