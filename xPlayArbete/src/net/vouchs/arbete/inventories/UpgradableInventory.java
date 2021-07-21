package net.vouchs.arbete.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.vouchs.arbete.Core;
import net.vouchs.arbete.object.Account;
import net.vouchs.arbete.utilities.ItemBuilder;

public class UpgradableInventory implements Listener
{
	private Core core;

	public UpgradableInventory(Core core)
	{
		this.core = core;
	}

	private String title()
	{
		return "§8Uppgradera verktyg";
	}

	public Inventory getInventory(Account account)
	{
		Inventory inventory = Bukkit.createInventory(null, 27, title());

		switch (account.getSwordUpgrades())
		{
			case 0:
				inventory.setItem(10, new ItemBuilder(Material.STONE_SWORD).setName("§bUppgradera Svärd").addLoreLine("").addLoreLine("§eKostar: §73000").toItemStack());
				break;
			case 1:
				inventory.setItem(10, new ItemBuilder(Material.IRON_SWORD).setName("§bUppgradera Svärd").addLoreLine("").addLoreLine("§eKostar: §710000").toItemStack());
				break;
			case 2:
				inventory.setItem(10, new ItemBuilder(Material.DIAMOND_SWORD).setName("§bUppgradera Svärd").addLoreLine("").addLoreLine("§eKostar: §715000").toItemStack());
				break;
			case 3:
				inventory.setItem(10, new ItemBuilder(Material.BARRIER).setName("§aFulluppgraderad!").toItemStack());
				break;
		}

		switch (account.getPickaxeUpgrades())
		{
			case 0:
				inventory.setItem(12, new ItemBuilder(Material.STONE_PICKAXE).setName("§bUppgradera Pickaxe").addLoreLine("").addLoreLine("§eKostar: §73000").toItemStack());
				break;
			case 1:
				inventory.setItem(12, new ItemBuilder(Material.IRON_PICKAXE).setName("§bUppgradera Svärd").addLoreLine("").addLoreLine("§eKostar: §710000").toItemStack());
				break;
			case 2:
				inventory.setItem(12, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§bUppgradera Pickaxe").addLoreLine("").addLoreLine("§eKostar: §715000").toItemStack());
				break;
			case 3:
				inventory.setItem(12, new ItemBuilder(Material.BARRIER).setName("§aFulluppgraderad!").toItemStack());
				break;
		}

		switch (account.getAxeUpgrades())
		{
			case 0:
				inventory.setItem(14, new ItemBuilder(Material.STONE_AXE).setName("§bUppgradera Yxa").addLoreLine("").addLoreLine("§eKostar: §73000").toItemStack());
				break;
			case 1:
				inventory.setItem(14, new ItemBuilder(Material.IRON_AXE).setName("§bUppgradera Yxa").addLoreLine("").addLoreLine("§eKostar: §710000").toItemStack());
				break;
			case 2:
				inventory.setItem(14, new ItemBuilder(Material.DIAMOND_AXE).setName("§bUppgradera Yxa").addLoreLine("").addLoreLine("§eKostar: §715000").toItemStack());
				break;
			case 3:
				inventory.setItem(14, new ItemBuilder(Material.BARRIER).setName("§aFulluppgraderad!").toItemStack());
				break;
		}

		switch (account.getShovelUpgrades())
		{
			case 0:
				inventory.setItem(16, new ItemBuilder(Material.STONE_SPADE).setName("§bUppgradera Spade").addLoreLine("").addLoreLine("§eKostar: §73000").toItemStack());
				break;
			case 1:
				inventory.setItem(16, new ItemBuilder(Material.IRON_SPADE).setName("§bUppgradera Spade").addLoreLine("").addLoreLine("§eKostar: §710000").toItemStack());
				break;
			case 2:
				inventory.setItem(16, new ItemBuilder(Material.DIAMOND_SPADE).setName("§bUppgradera Spade").addLoreLine("").addLoreLine("§eKostar: §715000").toItemStack());
				break;
			case 3:
				inventory.setItem(16, new ItemBuilder(Material.BARRIER).setName("§aFulluppgraderad!").toItemStack());
				break;
		}
		return inventory;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		Account account = core.getAccountManager().getAccount(player);

		if (account == null)
			return;
		
		if (event.getClickedInventory() == null)
			return;
		
		if (event.getCurrentItem() == null)
			return;
		
		if (!event.getClickedInventory().getTitle().equals(title()))
			return;

		if (event.getCurrentItem().getType().toString().contains("SWORD"))
		{
			player.openInventory(core.getConfirmationInventory().getInventory("SWORD - " + (account.getSwordUpgrades() + 1)));
		}
		else if (event.getCurrentItem().getType().toString().contains("PICKAXE"))
		{
			player.openInventory(core.getConfirmationInventory().getInventory("PICKAXE - " + (account.getPickaxeUpgrades() + 1)));
		}
		else if (event.getCurrentItem().getType().toString().contains("SPADE"))
		{
			player.openInventory(core.getConfirmationInventory().getInventory("SPADE - " + (account.getShovelUpgrades() + 1)));
		}
		else if (event.getCurrentItem().getType().toString().contains("AXE"))
		{
			player.openInventory(core.getConfirmationInventory().getInventory("AXE - " + (account.getAxeUpgrades() + 1)));
		}
		event.setCancelled(true);
	}
}
