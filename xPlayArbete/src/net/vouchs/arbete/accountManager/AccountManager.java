package net.vouchs.arbete.accountManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vouchs.arbete.Core;
import net.vouchs.arbete.backend.FileConfig;
import net.vouchs.arbete.object.Account;

public class AccountManager
{
	private Core core;
	private Map<UUID, Account> accounts;

	public AccountManager(Core core)
	{
		this.core = core;
		this.accounts = new HashMap<UUID, Account>();

		for (Player player : Bukkit.getOnlinePlayers())
			addAccount(player);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(core, new Runnable()
		{
			public void run()
			{
				for (Account account : accounts.values())
				{
					saveAccount(account);
				}
			}
		}, 0L, 800L);
	}

	public Account getAccount(Player player)
	{
		return accounts.get(player.getUniqueId());
	}

	public void saveAccount(Account account)
	{
		if (account.getFileConfig() == null)
			account.setFileConfig(new FileConfig(core, "Players", account.getUUID().toString() + ".yml"));

		FileConfiguration config = account.getFileConfig().getConfig();

		config.set("Name", account.getPlayerName());
		config.set("Pickaxe-Upgrades", account.getPickaxeUpgrades());
		config.set("Sword-Upgrades", account.getSwordUpgrades());
		config.set("Axe-Upgrades", account.getAxeUpgrades());
		config.set("Shovel-Upgrades", account.getShovelUpgrades());

		try
		{
			config.save(account.getFileConfig().getConfigFile());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void load(Account account)
	{
		if (account.getFileConfig() == null)
			account.setFileConfig(new FileConfig(core, "Players", account.getUUID().toString() + ".yml"));

		FileConfiguration config = account.getFileConfig().getConfig();
		
		account.setPickaxeUpgrades(config.getInt("Pickaxe-Upgrades"));
		account.setSwordUpgrades(config.getInt("Sword-Upgrades"));
		account.setAxeUpgrades(config.getInt("Axe-Upgrades"));
		account.setShovelUpgrades(config.getInt("Shovel-Upgrades"));
	}

	public void addAccount(Player player)
	{
		if (!accounts.containsKey(player.getUniqueId()))
		{
			Account account = new Account(player);
			accounts.put(player.getUniqueId(), account);
			load(account);
		}
	}
	
	public void removeAccount(Account account) {
		if (accounts.containsKey(account.getUUID())) {
			accounts.remove(account.getUUID());
		}
	}
	
	public Map<UUID, Account> getAccounts()
	{
		return accounts;
	}
}