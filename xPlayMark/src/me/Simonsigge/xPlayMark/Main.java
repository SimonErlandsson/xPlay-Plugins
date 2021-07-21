package me.Simonsigge.xPlayMark;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	Location spawnLoc;
	
	public void onEnable() {
		System.out.println("xPlayMark körs nu!");
		getServer().getPluginManager().registerEvents(this, this);
		spawnLoc = new Location(Bukkit.getServer().getWorlds().get(0), 0.0D, 54.0D, 0.0D);
		getServer().getWorlds().get(0).setSpawnLocation(spawnLoc);
	}

	public void onDisable() {
		System.out.println("xPlayMark körs nu inte!");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(spawnLoc);
	}
}