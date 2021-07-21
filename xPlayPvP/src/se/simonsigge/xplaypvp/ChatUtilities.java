package se.simonsigge.xplaypvp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;

public class ChatUtilities {

	public void sendPlayerMessage(Player p, String message) {
		p.sendMessage(Main.getPrefix()
				+ ChatColor.translateAlternateColorCodes('&', message));
	}

	public void sendActionBarMessage(Player p, String message) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
				new TextComponent(Main.getPrefix() + ChatColor.translateAlternateColorCodes('&', message)));
	}

	public void sendServerMessage(String message) {
		Main.getInstance()
				.getServer()
				.broadcastMessage(
						Main.getPrefix()
								+ ChatColor.translateAlternateColorCodes('&',
										message));
	}
	
	public void sendErrorMessage(Player p) {
		sendPlayerMessage(p, ChatColor.RED + "Kontakta admin! Det är fel här!");
	}

}
