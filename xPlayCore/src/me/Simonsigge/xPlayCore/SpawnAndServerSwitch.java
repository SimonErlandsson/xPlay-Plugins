package me.Simonsigge.xPlayCore;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SpawnAndServerSwitch implements CommandExecutor {

	public SpawnAndServerSwitch() {
		Main.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(Main.getPlugin(), "BungeeCord");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("spawn") || label.equalsIgnoreCase("hub")) {

			if (isInWorld(p, "xPlayHub")) {
				tpToSpawn(p);
				return true;
			} else {
				sendPlayerToBungee(p, "hub");
				return true;
			}
		}

		if (label.equalsIgnoreCase("stad") && isInWorld(p, "xPlayStad")) {
			tpToSpawn(p);
			return true;
		}

		if (label.equalsIgnoreCase("mark") && isInWorld(p, "xPlayMark")) {
			p.teleport(p.getWorld().getSpawnLocation());
			return true;
		}

		if (label.equalsIgnoreCase("towny") && isInWorld(p, "xPlayTowny")) {
			//TA BORT I PLUGIN.YML UNDER EXPORT FÖR TOWNY
			return true;
		}

		if (label.equalsIgnoreCase("pvp") && isInWorld(p, "xPlayPvP")) {
			tpToSpawn(p);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.setExp(0);
			p.setLevel(0);
			p.updateInventory();
			return true;
		}

		if (label.equalsIgnoreCase("resurs") && isInWorld(p, "xPlayResurs")) {
			tpToSpawn(p);
			return true;
		}

		if (label.equalsIgnoreCase("arbete") && isInWorld(p, "xPlayArbete")) {
			tpToSpawn(p);
			return true;
		}

		if (label.equalsIgnoreCase("spel") && isInWorld(p, "xPlaySpel")) {
			tpToSpawn(p);
			return true;
		}
		
		if (label.equalsIgnoreCase("event") && isInWorld(p, "xPlayEvent")) {
			p.sendMessage(Main.getPrefix() + ChatColor.RED + "Du är redan i event!");
			return true;
		}
		
		if (label.equalsIgnoreCase("event")) {
			sendPlayerToBungee(p, label);
			return true;
		}

		if (sender.hasPermission("xPlayDonator.VIP")) {
		sendPlayerToBungee(p, label);
		} else {
			p.sendMessage(Main.getPrefix() + ChatColor.RED + "Du måste vara VIP eller högre för att kunna snabbväxla server. Se: https://www.xPlayServer.net/");
		}
		return false;
		
	}

	public void tpToSpawn(Player p) {
		p.teleport(p.getWorld().getSpawnLocation().clone().add(0.5D, 0, 0.5D));
	}

	public void sendPlayerToBungee(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server.toLowerCase().trim());
		} catch (IOException localIOException) {
		}
		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
	}

	public boolean isInWorld(Player p, String world) {
		return p.getServer().getWorlds().get(0).getName().equalsIgnoreCase(world);
	}

}
