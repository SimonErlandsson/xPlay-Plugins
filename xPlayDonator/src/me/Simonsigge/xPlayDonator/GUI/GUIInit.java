package me.Simonsigge.xPlayDonator.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Booster;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Enums.GUI;
import me.Simonsigge.xPlayDonator.Nodes.Permissions;
import net.md_5.bungee.api.ChatColor;

public class GUIInit {

	public ItemStack closeGUI;
	public ItemStack website;
	public ItemStack infoBook;
	public ItemStack clothes;
	public ItemStack boosters;
	public ItemStack hats;
	public ItemStack misc;
	public ItemStack noOwn;
	private ItemStack blocked;
	private ItemStack warningClothes;
	
	public ItemStack chatShout;
	public ItemStack chatColor;

	public GUIInit() {
		initStaticItemStacks();
	}

	public Inventory getMainInv() {
		Inventory mainGUI = Bukkit.createInventory(null, 45, "xPlayDonator - Huvudmeny");

		mainGUI.setItem(19, clothes);
		mainGUI.setItem(21, boosters);
		mainGUI.setItem(23, hats);
		mainGUI.setItem(25, misc);

		mainGUI.setItem(44, closeGUI);
		mainGUI.setItem(36, infoBook);
		mainGUI.setItem(4, website);
		return mainGUI;
	}

	public Inventory generateCustomInv(Player p, GUI gui) {

		Inventory inv = null;

		switch (gui) {
		case BOOSTERS:
			inv = generateBoostersInv(p);
			break;
		case CLOTHES:
			inv = generateClothesInv(p);
			break;
		case HATS:
			inv = generateHatsInv(p);
			break;
		case MISC:
			inv = generateMiscInv(p);
			break;
		default:
			break;
		}

		boolean onlyNull = true;
		for (ItemStack item : inv.getContents())
			if ((item != null) && !item.equals(blocked))
				onlyNull = false;

		if (onlyNull)
			inv.setItem(inv.getSize() - 9, noOwn);
		else if (gui == GUI.CLOTHES || gui == GUI.HATS)
			inv.setItem(inv.getSize() - 5, warningClothes);

		inv.setItem(inv.getSize() - 1, closeGUI);

		return inv;
	}

	public Inventory generateClothesInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36,
				"xPlayDonator - Kläder");

		if (Main.getDataHandler().hasRank(p, Permissions.CHANGEING_PERM)) {
			inv.setItem(2, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.CHANGEING, Armour.HELMET)));
			inv.setItem(11, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.CHANGEING, Armour.CHESTPLATE)));
			inv.setItem(20, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.CHANGEING, Armour.LEGGINGS)));
			inv.setItem(29, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.CHANGEING, Armour.BOOTS)));

			inv.setItem(6, blocked);
			inv.setItem(15, blocked);
			inv.setItem(24, blocked);
			inv.setItem(33, blocked);
		}

		if (Main.getDataHandler().hasRank(p, Permissions.DISCO_PERM)) {
			inv.setItem(6, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.DISCO, Armour.HELMET)));
			inv.setItem(15, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.DISCO, Armour.CHESTPLATE)));
			inv.setItem(24, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.DISCO, Armour.LEGGINGS)));
			inv.setItem(33, Main.getCosmeticsHandler().currentClothes.get(Main
					.getCosmeticsHandler().cosmeticsUtilities.getSummedEnum(
					Cloth.DISCO, Armour.BOOTS)));
		}

		return inv;
	}

	public Inventory generateBoostersInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36,
				"xPlayDonator - Boosters");

		if (Main.getDataHandler().getBooster(p, Booster.ARBETE) <= 0) {
			inv.setItem(11, blocked);
		} else {
			inv.setItem(11,
					getBooster(Main.getDataHandler().getBooster(p, Booster.ARBETE), Booster.ARBETE));
		}

		if (Main.getDataHandler().getBooster(p, Booster.DROP) <= 0) {
			inv.setItem(13, blocked);
		} else {
			inv.setItem(13, getBooster(Main.getDataHandler().getBooster(p, Booster.DROP), Booster.DROP));
		}

		if (Main.getDataHandler().getBooster(p, Booster.PVP) <= 0) {
			inv.setItem(15, blocked);
		} else {
			inv.setItem(15, getBooster(Main.getDataHandler().getBooster(p, Booster.PVP), Booster.PVP));
		}

		return inv;
	}

	private ItemStack getBooster(int number, Booster booster) {
		ItemStack item = null;
		String name = null;
		String lore = null;

		switch (booster) {
		case ARBETE:
			item = new ItemStack(Material.DIAMOND_PICKAXE, number);
			name = "x2 PlayMynt i Arbete";
			lore = "Ge hela servern x2 PlayMynt i Arbete.\nGäller i tio minuter per aktivering.\n\nDu har "
					+ number + " stycken.";
			break;
		case DROP:
			item = new ItemStack(Material.DIAMOND, number);
			name = "Drop-party i Stad";
			lore = "Aktivera ett drop-party för hela servern i Stad.\n\nDu har "
					+ number + " stycken.";
			break;
		case PVP:
			item = new ItemStack(Material.DIAMOND_SWORD, number);
			name = "x2 PlayMynt i PvP";
			lore = "Ge hela servern x2 PlayMynt i PvP.\nGäller i tio minuter per aktivering.\n\nDu har "
					+ number + " stycken.";
			break;
		}

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + name);
		meta.setLore(Arrays.asList(lore.split("\n")));
		item.setItemMeta(meta);

		return item;
	}

	public Inventory generateHatsInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36,
				"xPlayDonator - Huvuden");

		for (ItemStack item : Main.getCosmeticsHandler().currentHeads) {
			if (Main.getDataHandler().hasRank(p, Permissions.HEADS_PERM))
				inv.addItem(item);
			else
				inv.addItem(blocked);
		}

		return inv;
	}

	public Inventory generateMiscInv(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36,
				"xPlayDonator - Kommandon");
		
		List<ItemStack> donItems = new ArrayList<ItemStack>();
		
		if (Main.getDataHandler().hasRank(p, Permissions.CHATCOLOR_RANK))
			donItems.add(chatColor);
		else
			donItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.CHATSHOUT_RANK))
			donItems.add(chatShout);
		else
			donItems.add(blocked);
		
		List<ItemStack> essItems = new ArrayList<ItemStack>();
		
		if (Main.getDataHandler().hasRank(p, Permissions.SYNC_DISCORD_RANK))
			essItems.add(getMisc(Material.GOLDEN_APPLE, "Rank på discord", "Använd /syncdiscord [namn#0000] för att\nfå din rank på xPlays discord-server."));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.CHANGE_SERVER_RANK))
			essItems.add(getMisc(Material.GRASS, "Växla delserver snabbt", "Använd /[delserver] för att\nsnabbt växla server.\n\nExempel: /stad"));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.ONLINETIME_RANK))
			essItems.add(getMisc(Material.WATCH, "Titta din speltid", "Använd /onlinetime för att få din totala\nspeltid på xPlay."));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.GLIST_RANK))
			essItems.add(getMisc(Material.BOOK, "Få alla spelare", "Använd /glist för att se alla\nspelare som är inne på xPlay."));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.PTIME_RANK))
			essItems.add(getMisc(Material.MELON, "Ändra tid på delserver", "Använd /ptime [tid] för att\nändra tid i världen.\n\nFungerar inte i Towny och Resurs."));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.IGNORE_RANK))
			essItems.add(getMisc(Material.ANVIL, "Ignorera spelare", "Använd /ignore [spelare] för att\nignorera en spelare."));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.ONLINETIME_OTHERS_RANK))
			essItems.add(getMisc(Material.WATCH, "Titta spelares speltid", "Använd /onlinetime get [spelare] för att\nse en annan spelares speltid."));
		else
			essItems.add(blocked);
		
		if (Main.getDataHandler().hasRank(p, Permissions.FLY_HUB_RANK))
			essItems.add(getMisc(Material.FEATHER, "Fly i hub", "Använd /fly för att flyga i hub."));
		else
			essItems.add(blocked);
		
		int i = 0;
		for (ItemStack item : donItems) {
			inv.setItem(i, item);
			i++;
		}
		
		i = 18;
		for (ItemStack item : essItems) {
			inv.setItem(i, item);
			i++;
		}

		return inv;
	}
	
	private ItemStack getMisc(Material mat, String name, String lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + name);
		meta.setLore(Arrays.asList(lore.split("\n")));
		item.setItemMeta(meta);
		
		return item;
	}

	public void initStaticItemStacks() {
		// GLOSEGUI
		closeGUI = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta = closeGUI.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Stäng menyn");
		closeGUI.setItemMeta(meta);

		// WEBSITE
		website = new ItemStack(Material.DOUBLE_PLANT, 1);
		meta = website.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Donera här");
		meta.setLore(Arrays
				.asList(("Om du vill uppgradera rank\neller donera till boosters.")
						.split("\n")));
		website.setItemMeta(meta);

		// INFOBOOK
		infoBook = new ItemStack(Material.BOOK, 1);
		meta = infoBook.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Mer information");
		infoBook.setItemMeta(meta);

		// NOOWN
		noOwn = new ItemStack(Material.WOOL, 1, (byte) 14);
		meta = noOwn.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Äger inga");
		meta.setLore(Arrays
				.asList(("Du äger inga tillgångar ur\ndenna kategori.\n\nKlicka här för en länk\ntill donationssidan.")
						.split("\n")));
		noOwn.setItemMeta(meta);

		// BLOCKED
		blocked = new ItemStack(Material.WOOL, 1, (byte) 15);
		meta = blocked.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Tillgången är blockerad");
		meta.setLore(Arrays.asList(("Du äger inte denna\ntillgång. =(")
				.split("\n")));
		blocked.setItemMeta(meta);

		// CLOTHES
		clothes = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		meta = clothes.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Kläder");
		meta.setLore(Arrays
				.asList(("Välj vilka coola kläder\ndu vill ha på dig.")
						.split("\n")));
		clothes.setItemMeta(meta);

		// BOOSTERS
		boosters = new ItemStack(Material.DIAMOND, 1);
		meta = boosters.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Boosters");
		meta.setLore(Arrays
				.asList(("Aktivera en booster som\ngäller för din delserver.")
						.split("\n")));
		boosters.setItemMeta(meta);

		// HATS
		hats = new ItemStack(Material.SKULL_ITEM, 1);
		meta = hats.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Huvuden");
		meta.setLore(Arrays
				.asList(("Ändra ditt huvud till något\nav xPlays speciella.")
						.split("\n")));
		hats.setItemMeta(meta);

		// MISC
		misc = new ItemStack(Material.NAME_TAG, 1);
		meta = misc.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Kommandon");
		meta.setLore(Arrays.asList(("Övriga kommandon som\ndu kan använda.")
				.split("\n")));
		misc.setItemMeta(meta);
		
		//CHATSHOUT
		chatShout = getMisc(Material.BANNER, "Skrik över hela servern", "Klicka här för att skrika över hela servern.\nKan användas en gång per dygn.");
		
		//CHATCOLOR
		chatColor = getMisc(Material.RED_GLAZED_TERRACOTTA, "Skriv med färg i chatten", "Klicka här för att skriva med färg i chatten.\nKan användas en gång per timme.");
		
		//WARNING CLOTHES
		warningClothes = new ItemStack(Material.WOOL, 1, (byte) 14);
		meta = warningClothes.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "VARNING");
		meta.setLore(Arrays
				.asList(("Att ha ett av xPlayDonators plagg/huvuden ersätter\ndin vanliga armour på alla delservrar.\n\nTänk därför på att ta av värdefulla\nplagg innan du aktiverar ett plagg på denna sida!\n\nVi ersätter inte plagg som försvinner av misstag.")
						.split("\n")));
		warningClothes.setItemMeta(meta);

	}

}
