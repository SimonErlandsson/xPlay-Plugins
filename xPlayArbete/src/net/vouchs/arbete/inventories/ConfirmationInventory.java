package net.vouchs.arbete.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.vouchs.arbete.Core;
import net.vouchs.arbete.object.Account;
import net.vouchs.arbete.utilities.ItemBuilder;

public class ConfirmationInventory implements Listener
{
	private Core core;

	public ConfirmationInventory(Core core)
	{
		this.core = core;
		core.getServer().getPluginManager().registerEvents(this, core);
	}

	public Inventory getInventory(String title)
	{
		Inventory inv = Bukkit.createInventory(null, 27, title);

		inv.setItem(11, confirmPurchase());
		inv.setItem(15, declinePurchase());

		return inv;
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
		String t = event.getClickedInventory().getTitle();
		if (t.startsWith("SWORD") || t.startsWith("PICKAXE") || t.startsWith("AXE") || t.startsWith("SPADE"))
		{

			event.setCancelled(true);

			String title = event.getClickedInventory().getTitle();

			if (title.contains("§8Uppgradera verktyg"))
				return;

			Integer newLevel = Integer.valueOf(title.substring(title.length() - 1));

			if (event.getCurrentItem().isSimilar(confirmPurchase()))
			{
				if (title.startsWith("SWORD"))
				{

					if (core.getEconomy().has(player, getPrice(newLevel)))
					{
						account.setSwordUpgrades(newLevel);
						core.getEconomy().withdrawPlayer(player, getPrice(newLevel));
						player.sendMessage(core.getPrefix() + "§aDu har uppgraderat ditt svärd till nivå " + newLevel + ".");
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.f, 0.0f);

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
					}
					else player.sendMessage(core.getPrefix() + "§cDu har inte tillräckligt med PlayMynt för att utföra den här uppgraderingen!");

					player.closeInventory();
				}
				else if (title.startsWith("PICKAXE"))
				{

					if (core.getEconomy().has(player, getPrice(newLevel)))
					{
						account.setPickaxeUpgrades(newLevel);
						core.getEconomy().withdrawPlayer(player, getPrice(newLevel));
						player.sendMessage(core.getPrefix() + "§aDu har uppgraderat din pickaxe till nivå " + newLevel + ".");
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.f, 0.0f);

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
					}
					else player.sendMessage(core.getPrefix() + "§cDu har inte tillräckligt med PlayMynt för att utföra den här uppgraderingen!");

					player.closeInventory();
				}
				else if (title.startsWith("AXE"))
				{

					if (core.getEconomy().has(player, getPrice(newLevel)))
					{
						account.setAxeUpgrades(newLevel);
						core.getEconomy().withdrawPlayer(player, getPrice(newLevel));
						player.sendMessage(core.getPrefix() + "§aDu har uppgraderat din yxa till nivå " + newLevel + ".");
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.f, 0.0f);

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
					}
					else player.sendMessage(core.getPrefix() + "§cDu har inte tillräckligt med PlayMynt för att utföra den här uppgraderingen!");

					player.closeInventory();
				}
				else if (title.startsWith("SPADE"))
				{

					if (core.getEconomy().has(player, getPrice(newLevel)))
					{
						account.setShovelUpgrades(newLevel);
						core.getEconomy().withdrawPlayer(player, getPrice(newLevel));
						player.sendMessage(core.getPrefix() + "§aDu har uppgraderat din spade till nivå " + newLevel + ".");
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.f, 0.0f);

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
					}
					else player.sendMessage(core.getPrefix() + "§cDu har inte tillräckligt med PlayMynt för att utföra den här uppgraderingen!");

					player.closeInventory();
				}
			}

			if (event.getCurrentItem().isSimilar(declinePurchase()))
			{
				player.closeInventory();
				player.sendMessage(core.getPrefix() + "§cDu har avbrutit uppgraderingen.");
				return;
			}
		}
	}

	private ItemStack confirmPurchase()
	{
		return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 5).setName("§aBekräfta uppgradering!").toItemStack();
	}

	private ItemStack declinePurchase()
	{
		return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 14).setName("§cAvbryt uppgradering!").toItemStack();
	}

	private int getPrice(int fromLevel)
	{
		if (fromLevel == 1)
		{
			return 3000;
		}
		else if (fromLevel == 2)
		{
			return 10000;
		}
		else if (fromLevel == 3) { return 15000; }
		return -1;
	}
}