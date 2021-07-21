package net.vouchs.arbete.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.vouchs.arbete.Core;
import net.vouchs.arbete.object.Account;
import net.vouchs.arbete.utilities.ItemBuilder;

public class PlayerJoin implements Listener
{
	private Core core;
	private Location location;

	public PlayerJoin(Core core)
	{
		this.core = core;
		this.location = new Location(Bukkit.getServer().getWorlds().get(0), 0.5D, 50.0D, 0.5D);
	}

	@EventHandler
	public void onEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		core.getAccountManager().addAccount(player);
		Account account = core.getAccountManager().getAccount(player);

		player.getInventory().clear();

		if (player.hasPlayedBefore())
			player.teleport(location);
		else
		{
			new BukkitRunnable()
			{
				public void run()
				{
					player.teleport(location);
				}
			}.runTaskLater(core, 3L);
		}
		player.setLevel(0);
		player.setExp(0);

		switch (account.getSwordUpgrades())
		{
			case 0:
				player.getInventory().setItem(0, new ItemBuilder(Material.WOOD_SWORD).toItemStack());
				break;
			case 1:
				player.getInventory().setItem(0, new ItemBuilder(Material.STONE_SWORD).toItemStack());
				break;
			case 2:
				player.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).toItemStack());
				break;
			case 3:
				player.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).toItemStack());
				break;
		}

		switch (account.getPickaxeUpgrades())
		{
			case 0:
				player.getInventory().setItem(1, new ItemBuilder(Material.WOOD_PICKAXE).toItemStack());
				break;
			case 1:
				player.getInventory().setItem(1, new ItemBuilder(Material.STONE_PICKAXE).toItemStack());
				break;
			case 2:
				player.getInventory().setItem(1, new ItemBuilder(Material.IRON_PICKAXE).toItemStack());
				break;
			case 3:
				player.getInventory().setItem(1, new ItemBuilder(Material.DIAMOND_PICKAXE).toItemStack());
				break;
		}

		switch (account.getAxeUpgrades())
		{
			case 0:
				player.getInventory().setItem(2, new ItemBuilder(Material.WOOD_AXE).toItemStack());
				break;
			case 1:
				player.getInventory().setItem(2, new ItemBuilder(Material.STONE_AXE).toItemStack());
				break;
			case 2:
				player.getInventory().setItem(2, new ItemBuilder(Material.IRON_AXE).toItemStack());
				break;
			case 3:
				player.getInventory().setItem(2, new ItemBuilder(Material.DIAMOND_AXE).toItemStack());
				break;
		}

		switch (account.getShovelUpgrades())
		{
			case 0:
				player.getInventory().setItem(3, new ItemBuilder(Material.WOOD_SPADE).toItemStack());
				break;
			case 1:
				player.getInventory().setItem(3, new ItemBuilder(Material.STONE_SPADE).toItemStack());
				break;
			case 2:
				player.getInventory().setItem(3, new ItemBuilder(Material.IRON_SPADE).toItemStack());
				break;
			case 3:
				player.getInventory().setItem(3, new ItemBuilder(Material.DIAMOND_SPADE).toItemStack());
				break;
		}
		
		player.getInventory().setItem(4, new ItemBuilder(Material.FISHING_ROD).toItemStack());

	}
}