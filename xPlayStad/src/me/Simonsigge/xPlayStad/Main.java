package me.Simonsigge.xPlayStad;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import xPlayShop.GUIManager;
import xPlayShop.MessageHandler;
import xPlayShop.MovementOpener;
import xPlayShop.xPlayShop;

public class Main extends JavaPlugin implements Listener {
	
	Location spawnLoc;
	private static Main instance;
	public GUIManager guiManager;
	public MessageHandler messageHandler;
	
	static Economy economy = null;
	
	public void onEnable() {
		System.out.println("xPlayStad körs nu!");
		getServer().getPluginManager().registerEvents(this, this);
		spawnLoc = new Location(Bukkit.getServer().getWorlds().get(0), 0.5D, 49.0D, 0.5D);
		spawnLoc.setYaw(180f);
		getServer().getWorlds().get(0).setSpawnLocation(spawnLoc);
		
		instance = this;
		getCommand("xplayshop").setExecutor(new xPlayShop());
		guiManager = new GUIManager();
		getServer().getPluginManager().registerEvents(guiManager, this);
		getServer().getPluginManager().registerEvents(new MovementOpener(), this);
		messageHandler = new MessageHandler();
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		economy = rsp.getProvider();
	}

	public void onDisable() {
		System.out.println("xPlayStad körs nu inte!");
		instance = null;
		economy = null;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static Economy getEconomy() {
		return economy;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(spawnLoc);
	}
}