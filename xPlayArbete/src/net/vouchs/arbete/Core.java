package net.vouchs.arbete;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import net.vouchs.arbete.accountManager.AccountManager;
import net.vouchs.arbete.commands.BoosterCommand;
import net.vouchs.arbete.inventories.ConfirmationInventory;
import net.vouchs.arbete.inventories.UpgradableInventory;
import net.vouchs.arbete.listeners.BlockBreak;
import net.vouchs.arbete.listeners.EntityDeath;
import net.vouchs.arbete.listeners.EntityPickupItem;
import net.vouchs.arbete.listeners.ItemSpawn;
import net.vouchs.arbete.listeners.NPCInteractEvent;
import net.vouchs.arbete.listeners.PlayerDropItem;
import net.vouchs.arbete.listeners.PlayerFish;
import net.vouchs.arbete.listeners.PlayerJoin;
import net.vouchs.arbete.listeners.PlayerQuit;
import net.vouchs.arbete.listeners.ProjectileHit;
import net.vouchs.arbete.object.Account;
import net.vouchs.arbete.utilities.ItemBuilder;

public class Core extends JavaPlugin
{
	private static Core instance;
	private AccountManager accountManager;
	
	private UpgradableInventory upgradableInventory;
	private ConfirmationInventory confirmationInventory;

	private boolean boosterEnabled = false;
	private String boosterHost = null;

	private Economy economy;

	public Map<String, Integer> confirmMap = new HashMap<>();

	@Override
	public void onEnable()
	{
		instance = this;

		Long oldTime = System.currentTimeMillis();
		getLogger().info("Initializing...");
		this.accountManager = new AccountManager(this);
		this.upgradableInventory = new UpgradableInventory(this);
		this.confirmationInventory = new ConfirmationInventory(this);
		new BoosterCommand(this);
		registerListeners(new BlockBreak(), new ProjectileHit(), new EntityDeath(this), new EntityPickupItem(), new ItemSpawn(), new PlayerDropItem(), new PlayerFish(this), new PlayerJoin(this), new PlayerQuit(this), new NPCInteractEvent(this), new UpgradableInventory(this));

		if (getServer().getPluginManager().getPlugin("Vault") == null)
		{
			getLogger().warning("Could not find Vault.jar!");
			return;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null || rsp.getProvider() == null)
		{
			getLogger().warning("No economy plugin is connected to Vault");
			return;
		}
		economy = rsp.getProvider();

		new BukkitRunnable()
		{
			public void run()
			{
				if (boosterEnabled)
				{
					Bukkit.getOnlinePlayers().forEach(player -> {
						player.sendMessage("§0[§f§lxPlay§0] §f->§7 §aTack vare §2" + boosterHost + "§as booster får du just nu dubbel lön i Arbete!");
					});
				}
			}
		}.runTaskTimerAsynchronously(this, 20, 20 * 30);
		
		new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD) {
                        player.getInventory().setHeldItemSlot(3);
                        player.sendMessage(getPrefix() + "§aByt tillbaka till fiskespöt för att bevisa att du inte är en tjuvfiskare!");
                    }
                    
                    Random r = new Random();
                    
                    while (player.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD) {
                    	player.getInventory().setHeldItemSlot(r.nextInt(9));
                    }
                    
                    if (player.getInventory().getItemInOffHand().getType() == Material.FISHING_ROD) {
                    	player.getInventory().setItemInOffHand(null);
                    	player.getInventory().addItem(new ItemBuilder(Material.FISHING_ROD).toItemStack());
                    }
                });
            }
        }.runTaskTimerAsynchronously(this, 20 * 60 * 5,  20 * 60 * 5);

		getLogger().info("Initialized! Took " + (System.currentTimeMillis() - oldTime) + " milliseconds.");
	}

	@Override
	public void onDisable()
	{
		for (Account account : accountManager.getAccounts().values())
		{
			accountManager.saveAccount(account);
		}

		instance = null;
	}

	public void addMoney(Player player, double amount)
	{
		if (boosterEnabled)
			amount *= 2;

		amount = Math.round(amount * 100.0) / 100.0;
		economy.depositPlayer(player, amount);

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§0[§f§lxPlay§0] §f-> §7Du fick §a" + amount + " §6§lPlayMynt! §7(§e" + economy.getBalance(player) + "§7)"));
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
	}

	public static Core getInstance()
	{
		return instance;
	}

	private void registerListeners(Listener... listeners)
	{
		for (Listener listener : listeners)
		{
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	public Economy getEconomy()
	{
		return economy;
	}

	public AccountManager getAccountManager()
	{
		return this.accountManager;
	}

	public boolean isBoosterEnabled()
	{
		return boosterEnabled;
	}

	public UpgradableInventory getUpgradableInventory()
	{
		return this.upgradableInventory;
	}

	public ConfirmationInventory getConfirmationInventory()
	{
		return this.confirmationInventory;
	}

	public void setBoosterEnabled(boolean boosterEnabled)
	{
		this.boosterEnabled = boosterEnabled;
	}

	public String getBoosterHost()
	{
		return boosterHost;
	}

	public void setBoosterHost(String boosterHost)
	{
		this.boosterHost = boosterHost;
	}

	public String getPrefix()
	{
		return "§0[§f§lxPlay§0] §f->§7 ";
	}

}