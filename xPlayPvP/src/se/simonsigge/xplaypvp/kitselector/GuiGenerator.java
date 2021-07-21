package se.simonsigge.xplaypvp.kitselector;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.PluginSettings;

public class GuiGenerator {

	private ItemStack close;
	private ItemStack shop;

	public GuiGenerator() {
		initItemStacks();
	}

	public Inventory getSelectInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 45,
				"Välj eller köp ett kit!");
		for (int i = 0; i < PluginSettings.FREE_KITS.length; i++) {
			inv.addItem(getKitItemStack(PluginSettings.FREE_KITS[i]));
		}

		Kits[] playerKits = Main.getGuiManager().getDataManager()
				.getPlayerKits(p);
		if (playerKits != null) {
			for (int i = 0; i < playerKits.length; i++) { // testa att ha
															// gratiskit sparat
				if (!Arrays.asList(PluginSettings.FREE_KITS).contains(
						playerKits[i])) {
					inv.addItem(getKitItemStack(playerKits[i]));
				}
			}
		}

		inv.setItem(36, shop);
		inv.setItem(44, close);
		return inv;
	}

	public Inventory getShopInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 45,
				"Välj ett kit att köpa!");

		Kits[] kits = Kits.values();
		Kits[] playerKits = Main.getGuiManager().getDataManager()
				.getPlayerKits(p);

		for (int i = 0; i < kits.length; i++) {
			if (playerKits == null
					&& !Arrays.asList(PluginSettings.FREE_KITS).contains(
							kits[i]))
				inv.addItem(getBuyItemStack(kits[i]));
			else if (!Arrays.asList(PluginSettings.FREE_KITS).contains(kits[i])
					&& !Arrays.asList(playerKits).contains(kits[i]))
				inv.addItem(getBuyItemStack(kits[i]));
		}

		inv.setItem(44, close);
		return inv;
	}

	public ItemStack getShopItemStack() {
		return shop;
	}

	public ItemStack getCloseItemStack() {
		return close;
	}

	private ItemStack getKitItemStack(Kits kit) {
		ItemStack item = new ItemStack(kit.getMaterial(), 1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + kit.getName());
		itemMeta.setLore(Arrays.asList(kit.getDescription().split("\n")));
		item.setItemMeta(itemMeta);

		return item;
	}

	private ItemStack getBuyItemStack(Kits kit) {
		ItemStack item = new ItemStack(kit.getMaterial(), 1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD
				+ kit.getName() + " - " + kit.getPrice() + " PlayMynt");
		itemMeta.setLore(Arrays.asList((kit.getDescription() + "\n\nKöpet går\ninte att ångra.")
				.split("\n")));
		item.setItemMeta(itemMeta);

		return item;
	}

	private void initItemStacks() {
		close = new ItemStack(Material.BARRIER, 1);
		ItemMeta closeMeta = close.getItemMeta();
		closeMeta.setDisplayName(ChatColor.RED + "Stäng menyn");
		close.setItemMeta(closeMeta);

		shop = new ItemStack(Material.DOUBLE_PLANT, 1);
		ItemMeta shopMeta = shop.getItemMeta();
		shopMeta.setDisplayName(ChatColor.GREEN + "Affär");
		shopMeta.setLore(Arrays.asList("Handla nya", "kits här!"));
		shop.setItemMeta(shopMeta);
	}

}
