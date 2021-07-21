package me.Simonsigge.xPlayDonator.Cosmetics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Misc;

public class ClothesUpdater {

	HashMap<String, List<ItemStack>> allClothes;

	public ClothesUpdater() {
		allClothes = new HashMap<String, List<ItemStack>>();

		initChangeing();
		initDisco();

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run() {
				updateChangeing();
			}
		}, 0, Misc.UPDATE_CHANGING_CLOTHES);

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run() {
				updateDisco();
			}
		}, 0, Misc.UPDATE_DISCO_CLOTHES);
	}

	private void updateChangeing() {
		HashMap<String, ItemStack> oldClothes = new HashMap<String, ItemStack>();
		for (Entry<String, ItemStack> hm : Main.getCosmeticsHandler().currentClothes.entrySet())
			oldClothes.put(hm.getKey(), hm.getValue().clone());

		int index = allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.HELMET))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.CHANGEING, Armour.HELMET)));

		if (index == allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.HELMET)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.CHANGEING, Armour.HELMET),
				allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.HELMET)).get(index + 1));

		index = allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.CHESTPLATE)).indexOf(
				Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.CHANGEING, Armour.CHESTPLATE)));

		if (index == allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.CHESTPLATE)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.CHANGEING, Armour.CHESTPLATE),
				allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.CHESTPLATE)).get(index + 1));

		index = allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.LEGGINGS))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.CHANGEING, Armour.LEGGINGS)));

		if (index == allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.LEGGINGS)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.CHANGEING, Armour.LEGGINGS),
				allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.LEGGINGS)).get(index + 1));

		index = allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.BOOTS))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.CHANGEING, Armour.BOOTS)));

		if (index == allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.BOOTS)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.CHANGEING, Armour.BOOTS),
				allClothes.get(getClothEnum(Cloth.CHANGEING, Armour.BOOTS)).get(index + 1));

		for (Player p : Main.getCosmeticsHandler().updatePlayersChangeing) {
			checkAndSetNewArmour(oldClothes, p, Cloth.CHANGEING);
		}
		
		for (Inventory inv : Main.getCosmeticsHandler().updateInventories) {
			checkAndUpdateInv(oldClothes, inv, Cloth.CHANGEING);
		}
	}

	private void updateDisco() {
		HashMap<String, ItemStack> oldClothes = new HashMap<String, ItemStack>();
		for (Entry<String, ItemStack> hm : Main.getCosmeticsHandler().currentClothes.entrySet())
			oldClothes.put(hm.getKey(), hm.getValue().clone());

		int index = allClothes.get(getClothEnum(Cloth.DISCO, Armour.HELMET))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.DISCO, Armour.HELMET)));

		if (index == allClothes.get(getClothEnum(Cloth.DISCO, Armour.HELMET)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.DISCO, Armour.HELMET),
				allClothes.get(getClothEnum(Cloth.DISCO, Armour.HELMET)).get(index + 1));

		index = allClothes.get(getClothEnum(Cloth.DISCO, Armour.CHESTPLATE))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.DISCO, Armour.CHESTPLATE)));

		if (index == allClothes.get(getClothEnum(Cloth.DISCO, Armour.CHESTPLATE)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.DISCO, Armour.CHESTPLATE),
				allClothes.get(getClothEnum(Cloth.DISCO, Armour.CHESTPLATE)).get(index + 1));

		index = allClothes.get(getClothEnum(Cloth.DISCO, Armour.LEGGINGS))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.DISCO, Armour.LEGGINGS)));

		if (index == allClothes.get(getClothEnum(Cloth.DISCO, Armour.LEGGINGS)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.DISCO, Armour.LEGGINGS),
				allClothes.get(getClothEnum(Cloth.DISCO, Armour.LEGGINGS)).get(index + 1));

		index = allClothes.get(getClothEnum(Cloth.DISCO, Armour.BOOTS))
				.indexOf(Main.getCosmeticsHandler().currentClothes.get(getClothEnum(Cloth.DISCO, Armour.BOOTS)));

		if (index == allClothes.get(getClothEnum(Cloth.DISCO, Armour.BOOTS)).size() - 1)
			index = 0;

		Main.getCosmeticsHandler().currentClothes.put(getClothEnum(Cloth.DISCO, Armour.BOOTS),
				allClothes.get(getClothEnum(Cloth.DISCO, Armour.BOOTS)).get(index + 1));
		
		for (Player p : Main.getCosmeticsHandler().updatePlayersDisco) {
			checkAndSetNewArmour(oldClothes, p, Cloth.DISCO);
		}
		
		for (Inventory inv : Main.getCosmeticsHandler().updateInventories) {
			checkAndUpdateInv(oldClothes, inv, Cloth.DISCO);
			for (HumanEntity hp : inv.getViewers())
				if (hp instanceof Player)
					((Player) hp).updateInventory();
		}
	}
	
	private void checkAndUpdateInv(HashMap<String, ItemStack> oldClothes, Inventory inv, Cloth cloth) {
		for (ItemStack item : inv.getContents()) {
			if (((item != null) && (item.hasItemMeta()) && item.getItemMeta().hasDisplayName())) {
				for (Entry<String, ItemStack> old : oldClothes.entrySet()) {
					if (item.getItemMeta().getDisplayName().equals(old.getValue().getItemMeta().getDisplayName()) && Main.getCosmeticsHandler().cosmeticsUtilities.getOrgCloth(old.getKey()) == cloth)
						inv.setItem(inv.first(item), Main.getCosmeticsHandler().currentClothes.get(getClothEnum(cloth, Main.getCosmeticsHandler().cosmeticsUtilities.getOrgArmour(old.getKey()))));
				}
			}
		}
	}
	
	private void checkAndSetNewArmour(HashMap<String, ItemStack> oldClothes, Player p, Cloth cloth) {
		PlayerInventory inv = p.getInventory();
		
		int helmetIndex = 0;
		if (cloth == Cloth.CHANGEING)
			helmetIndex = 1;
		else if (cloth == Cloth.DISCO)
			helmetIndex = 2;
		
		if ((inv.getHelmet() != null) && oldClothes.get(getClothEnum(cloth, Armour.HELMET)).getItemMeta().getDisplayName().equals(inv.getHelmet().getItemMeta().getDisplayName()))
			Main.getCosmeticsHandler().setPlayerHelmet(p, helmetIndex);

		if ((inv.getChestplate() != null) && oldClothes.get(getClothEnum(cloth, Armour.CHESTPLATE)).getItemMeta().getDisplayName().equals(inv.getChestplate().getItemMeta().getDisplayName()))
			Main.getCosmeticsHandler().setPlayerCloth(p, cloth, Armour.CHESTPLATE);

		if ((inv.getLeggings() != null) && oldClothes.get(getClothEnum(cloth, Armour.LEGGINGS)).getItemMeta().getDisplayName().equals(inv.getLeggings().getItemMeta().getDisplayName()))
			Main.getCosmeticsHandler().setPlayerCloth(p, cloth, Armour.LEGGINGS);

		if ((inv.getBoots() != null) && oldClothes.get(getClothEnum(cloth, Armour.BOOTS)).getItemMeta().getDisplayName().equals(inv.getBoots().getItemMeta().getDisplayName()))
			Main.getCosmeticsHandler().setPlayerCloth(p, cloth, Armour.BOOTS);
		
		//p.updateInventory();
	}

	private void initChangeing() {
		allClothes.put(getClothEnum(Cloth.CHANGEING, Armour.HELMET),
				Arrays.asList(getChangeing(Material.CHAINMAIL_HELMET, Armour.HELMET),
						getChangeing(Material.IRON_HELMET, Armour.HELMET),
						getChangeing(Material.GOLD_HELMET, Armour.HELMET),
						getChangeing(Material.DIAMOND_HELMET, Armour.HELMET)));

		allClothes.put(getClothEnum(Cloth.CHANGEING, Armour.CHESTPLATE),
				Arrays.asList(getChangeing(Material.CHAINMAIL_CHESTPLATE, Armour.CHESTPLATE),
						getChangeing(Material.IRON_CHESTPLATE, Armour.CHESTPLATE),
						getChangeing(Material.GOLD_CHESTPLATE, Armour.CHESTPLATE),
						getChangeing(Material.DIAMOND_CHESTPLATE, Armour.CHESTPLATE)));

		allClothes.put(getClothEnum(Cloth.CHANGEING, Armour.LEGGINGS),
				Arrays.asList(getChangeing(Material.CHAINMAIL_LEGGINGS, Armour.LEGGINGS),
						getChangeing(Material.IRON_LEGGINGS, Armour.LEGGINGS),
						getChangeing(Material.GOLD_LEGGINGS, Armour.LEGGINGS),
						getChangeing(Material.DIAMOND_LEGGINGS, Armour.LEGGINGS)));

		allClothes.put(getClothEnum(Cloth.CHANGEING, Armour.BOOTS), Arrays.asList(
				getChangeing(Material.CHAINMAIL_BOOTS, Armour.BOOTS), getChangeing(Material.IRON_BOOTS, Armour.BOOTS),
				getChangeing(Material.GOLD_BOOTS, Armour.BOOTS), getChangeing(Material.DIAMOND_BOOTS, Armour.BOOTS)));
	}

	private void initDisco() {
		allClothes.put(getClothEnum(Cloth.DISCO, Armour.HELMET),
				Arrays.asList(getDisco(Color.LIME, Armour.HELMET), getDisco(Color.BLUE, Armour.HELMET),
						getDisco(Color.RED, Armour.HELMET), getDisco(Color.FUCHSIA, Armour.HELMET),
						getDisco(Color.ORANGE, Armour.HELMET), getDisco(Color.NAVY, Armour.HELMET),
						getDisco(Color.PURPLE, Armour.HELMET), getDisco(Color.YELLOW, Armour.HELMET),
						getDisco(Color.BLACK, Armour.HELMET)));

		allClothes.put(getClothEnum(Cloth.DISCO, Armour.CHESTPLATE),
				Arrays.asList(getDisco(Color.LIME, Armour.CHESTPLATE), getDisco(Color.BLUE, Armour.CHESTPLATE),
						getDisco(Color.RED, Armour.CHESTPLATE), getDisco(Color.FUCHSIA, Armour.CHESTPLATE),
						getDisco(Color.ORANGE, Armour.CHESTPLATE), getDisco(Color.NAVY, Armour.CHESTPLATE),
						getDisco(Color.PURPLE, Armour.CHESTPLATE), getDisco(Color.YELLOW, Armour.CHESTPLATE),
						getDisco(Color.BLACK, Armour.CHESTPLATE)));

		allClothes.put(getClothEnum(Cloth.DISCO, Armour.LEGGINGS),
				Arrays.asList(getDisco(Color.LIME, Armour.LEGGINGS), getDisco(Color.BLUE, Armour.LEGGINGS),
						getDisco(Color.RED, Armour.LEGGINGS), getDisco(Color.FUCHSIA, Armour.LEGGINGS),
						getDisco(Color.ORANGE, Armour.LEGGINGS), getDisco(Color.NAVY, Armour.LEGGINGS),
						getDisco(Color.PURPLE, Armour.LEGGINGS), getDisco(Color.YELLOW, Armour.LEGGINGS),
						getDisco(Color.BLACK, Armour.LEGGINGS)));

		allClothes.put(getClothEnum(Cloth.DISCO, Armour.BOOTS),
				Arrays.asList(getDisco(Color.LIME, Armour.BOOTS), getDisco(Color.BLUE, Armour.BOOTS),
						getDisco(Color.RED, Armour.BOOTS), getDisco(Color.FUCHSIA, Armour.BOOTS),
						getDisco(Color.ORANGE, Armour.BOOTS), getDisco(Color.NAVY, Armour.BOOTS),
						getDisco(Color.PURPLE, Armour.BOOTS), getDisco(Color.YELLOW, Armour.BOOTS),
						getDisco(Color.BLACK, Armour.BOOTS)));
	}

	// UTIL

	private String getClothEnum(Cloth cloth, Armour armour) {
		return cloth.name() + armour.name();
	}

	private String getNameFromArmour(Armour armour) {

		switch (armour) {
		case HELMET:
			return "Hjälm";
		case CHESTPLATE:
			return "Harnesk";
		case LEGGINGS:
			return "Byxor";
		case BOOTS:
			return "Skor";
		}

		return null;
	}

	private ItemStack getLeather(Armour armour) {

		switch (armour) {
		case HELMET:
			return new ItemStack(Material.LEATHER_HELMET, 1);
		case CHESTPLATE:
			return new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		case LEGGINGS:
			return new ItemStack(Material.LEATHER_LEGGINGS, 1);
		case BOOTS:
			return new ItemStack(Material.LEATHER_BOOTS, 1);
		}

		return null;
	}

	//

	private ItemStack getChangeing(Material mat, Armour armour) {
		ItemStack item = new ItemStack(mat, 1);

		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + "Växlande " + getNameFromArmour(armour).toLowerCase());
		item.setItemMeta(itemMeta);

		return item;
	}

	private ItemStack getDisco(Color color, Armour armour) {
		ItemStack item = getLeather(armour);
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) item.getItemMeta();

		itemMeta.setDisplayName(ChatColor.GREEN + "Disco" + getNameFromArmour(armour).toLowerCase());
		itemMeta.setColor(color);

		item.setItemMeta(itemMeta);

		return item;
	}
}
