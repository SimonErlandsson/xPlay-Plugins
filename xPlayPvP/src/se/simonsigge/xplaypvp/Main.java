package se.simonsigge.xplaypvp;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import se.simonsigge.commands.Booster;
import se.simonsigge.commands.Kit;
import se.simonsigge.xplaypvp.killandstreak.KillListener;
import se.simonsigge.xplaypvp.kits.OldKits;
import se.simonsigge.xplaypvp.kits.now.KitManager;
import se.simonsigge.xplaypvp.kitselector.GuiListener;
import se.simonsigge.xplaypvp.kitselector.GuiManager;
import se.simonsigge.xplaypvp.soup.SoupManager;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static String prefix = "&0[&f&lxPlay&0] &f-> ";
	private static Economy economy;
	
	private static ChatUtilities chatUtilities;
	private static GuiManager guiManager;
	private static SoupManager soupManager;
	private static KitManager kitManager;
	private static KillListener killListener;
	
	public void onEnable() {
		instance = this;
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		chatUtilities = new ChatUtilities();
		guiManager = new GuiManager();
		soupManager = new SoupManager();
		kitManager = new KitManager();
		killListener = new KillListener();
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		economy = rsp.getProvider();
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new OldKits(), this);
		getServer().getPluginManager().registerEvents(killListener, this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(soupManager, this);
		
		getCommand("kit").setExecutor(new Kit());
		getCommand("booster").setExecutor(new Booster());
		
		 Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					if (PluginSettings.BOOSTERACTIVE) {
						for (Player p : Bukkit.getOnlinePlayers())
						p.sendMessage(getPrefix() + "§aTack vare §2" + PluginSettings.BOOSTERHOST + "§as booster får du just nu dubbel lön i PvP!");
					}
					
				}}, 20, 20 * 30);
	}
	
	public void onDisable() {
		instance = null;
		economy = null;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static Economy getEconomy() {
		return economy;
	}
	
	public static ChatUtilities getChatUtilities() {
		return chatUtilities;
	}
	
	public static GuiManager getGuiManager() {
		return guiManager;
	}
	
	public static SoupManager getSoupManager() {
		return soupManager;
	}
	
	public static KitManager getKitManager() {
		return kitManager;
	}
	
	public static KillListener getKillListener() {
		return killListener;
	}

}
