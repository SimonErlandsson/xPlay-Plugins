package me.Simonsigge.xPlaySpel;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	private static Plugin plugin;
	private static String prefix = "&0[&f&lxPlay&0] &f-> ";
	private static Economy economy;
	
	
	public void onEnable() {
		plugin = this;
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		
		RegisteredServiceProvider<Economy> rsp = Main.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
		economy = rsp.getProvider();		
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerHider(), this);
	}
	
	public void onDisable() {
		plugin = null;
		economy = null;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

	public static String getPrefix() {
		return prefix;
	}
	
	public static Economy getEconomy() {
		return economy;
	}
}
