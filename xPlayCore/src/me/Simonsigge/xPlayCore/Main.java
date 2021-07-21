package me.Simonsigge.xPlayCore;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	private static Plugin plugin;
	Timer timer;

	private static String prefix = "&0[&f&lxPlay&0] &f-> ";

	public void onEnable() {
		plugin = this;
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		System.out.println("xPlayCore -> Körs nu");

		registerEvents(this, new KlassSetup());
		timer = new Timer();
		
		getCommand("hub").setExecutor(new SpawnAndServerSwitch());
		getCommand("spawn").setExecutor(new SpawnAndServerSwitch());
		getCommand("stad").setExecutor(new SpawnAndServerSwitch());
		getCommand("mark").setExecutor(new SpawnAndServerSwitch());
		getCommand("spel").setExecutor(new SpawnAndServerSwitch());
		getCommand("arbete").setExecutor(new SpawnAndServerSwitch());
		getCommand("pvp").setExecutor(new SpawnAndServerSwitch());
		getCommand("resurs").setExecutor(new SpawnAndServerSwitch());
		getCommand("event").setExecutor(new SpawnAndServerSwitch());
		getCommand("towny").setExecutor(new SpawnAndServerSwitch());
	}

	public void onDisable() {
		System.out.println("xPlayCore -> Körs inte längre");
		plugin = null;
	}

	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static String getPrefix() {
		return prefix;
	}

	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}	
}


