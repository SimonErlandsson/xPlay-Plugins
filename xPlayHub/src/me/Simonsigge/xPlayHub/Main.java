package me.Simonsigge.xPlayHub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {
	
	public static String prefix = "&0[&f&lxPlayHub&0] &f-> ";
	Location spawnLoc;
	Main instance;
	BookManager bookManager;

	public void onEnable() {
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		System.out.println("xPlayHub laddat.");
		getServer().getPluginManager().registerEvents(this, this);
		spawnLoc = new Location(Bukkit.getServer().getWorlds().get(0), 0.5D, 50.0D, 0.5D);
		instance = this;
		bookManager = new BookManager();
	}

	public void onDisable() {
		System.out.println("xPlayHub inte laddat.");
		instance = null;
	}
	
	public Main getInstance() {
		return instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PotionEffect speed = PotionEffectType.SPEED.createEffect(999999999, 2);
		p.teleport(spawnLoc);
		p.addPotionEffect(speed, true);
		p.getInventory().clear();
		p.getInventory().addItem(bookManager.getBook());
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setCancelled(true);
		p.sendMessage(prefix + ChatColor.RED
				+ " Gå in i en portal för att kunna chatta!");
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity().getType() == EntityType.PLAYER) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
				e.setCancelled(true);
				p.teleport(spawnLoc);
			}
		}
		if ((e.getEntity().getType() == EntityType.PLAYER)
				&& (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
			e.setCancelled(true);
		}
	}
}