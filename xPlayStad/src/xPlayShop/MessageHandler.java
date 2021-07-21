package xPlayShop;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessageHandler {
	
	private String prefix = "&0[&f&lxPlayShop&0] &f-> ";
	
	public MessageHandler() {
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
	}
	
	public void alreadyHasItem(Player p) {
		p.sendMessage(prefix + ChatColor.RED + "Du äger redan det kommandot!");
		playFailSound(p);
	}
	
	public void cancelPayment(Player p) {
		p.sendMessage(prefix + ChatColor.RED + "Ditt köp avbröts!");
		playFailSound(p);
	}
	
	public void notEnoughMoney(Player p) {
		p.sendMessage(prefix + ChatColor.RED + "Du har inte tillräckligt med pengar!");
		playFailSound(p);
	}
	
	public void successItem(Player p) {
		p.sendMessage(prefix + ChatColor.GREEN + "Ditt köp gick igenom. Hoppas du får nytta av ditt nya kommando!");
		playSuccessSound(p);
	}
	
	private void playFailSound(Player p) {
		p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 100, 100);
	}
	
	private void playSuccessSound(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
	}

}
