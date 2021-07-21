package me.Simonsigge.xPlayDonator.Message;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Misc;
import net.md_5.bungee.api.ChatColor;

public class MessageUtilities {

	private MessageBungee msgBungee;

	public MessageUtilities() {
		msgBungee = new MessageBungee();
	}

	public MessageBungee getMessageBungee() {
		return msgBungee;
	}

	private String colorize(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	private String getPrefix() {
		return colorize(Misc.prefix);
	}

	public void sendCustomMessage(Player p, String msg) {
		p.sendMessage(getPrefix() + colorize(msg));
	}

	public void broadcastCustomMessage(String msg) {
		Main.getInstance().getServer().broadcastMessage(getPrefix() + colorize(msg));
	}

	public void broadcastBungeeCustomMessage(String msg) {
		msgBungee.broadcastBungee(getPrefix() + colorize(msg));
	}

	public void broadcastCustomMessageNoPrefix(String msg) {
		Main.getInstance().getServer().broadcastMessage(colorize(msg));
	}

	public void broadcastBungeeCustomMessageNoPrefix(String msg) {
		msgBungee.broadcastBungee(colorize(msg));
	}

	public void playFailSound(Player p) {
		p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 100, 100);
	}

	public void playSuccessSound(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
	}

}
