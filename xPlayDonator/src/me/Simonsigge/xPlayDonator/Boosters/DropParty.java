package me.Simonsigge.xPlayDonator.Boosters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.Simonsigge.xPlayDonator.Main.Main;

public class DropParty {

	ArrayList<ItemStack> dropItems;
	Location spawnLoc, loc1, loc2;

	int radius = 8;
	int odds = 3;
	int doDrops = 60;
	Random r;

	BoosterHandler boosterHandler;

	public DropParty(BoosterHandler boosterHandler) {
		this.boosterHandler = boosterHandler;
		initDropItems();

		spawnLoc = new Location(Bukkit.getWorlds().get(0), 0, 50, 0);

		loc1 = spawnLoc.clone().add(-radius, 75, radius);
		loc2 = spawnLoc.clone().add(radius, -45, -radius);

		r = new Random();

	}
	
	public void startDrop() {
		Main.getMsgAPI().dropActivated();
		
		new BukkitRunnable() {
			int drops = 0;

			@Override
			public void run() {

				if (r.nextInt(odds) == 0) {
					newDrop();
					drops++;
					if (drops == doDrops) {
						this.cancel();
						Main.getMsgAPI().dropDeactivated();
						boosterHandler.setBoosterActive(false);
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 20);
	}

	private void newDrop() {
		ArrayList<Player> playersInDrop = new ArrayList<Player>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isInDropZone(p)) {
				playersInDrop.add(p);
				p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 100, 100);
			}
		}
		
		if (playersInDrop.size() == 0) {
			Main.getMsgAPI().dropNoOneIsInZone();
			return;
		}
		
		Player dp = playersInDrop.get(r.nextInt(playersInDrop.size()));
		ItemStack item = dropItems.get(r.nextInt(dropItems.size()));
		
		dp.getInventory().addItem(item);
		Main.getMsgAPI().dropped(dp.getName(), item.getItemMeta().getDisplayName(), boosterHandler.getBoosterActivator());
	}

	private boolean isInDropZone(Player p) {
		Location playerLoc = p.getLocation();

		if (playerLoc.getBlockX() >= loc1.getBlockX()
				&& playerLoc.getBlockX() <= loc2.getBlockX())
			if (playerLoc.getBlockY() <= loc1.getBlockY()
					&& playerLoc.getBlockY() >= loc2.getBlockY())
				if (playerLoc.getBlockZ() <= loc1.getBlockZ()
						&& playerLoc.getBlockZ() >= loc2.getBlockZ())
					return true;
		return false;
	}

	private void initDropItems() {
		dropItems = new ArrayList<ItemStack>();
		dropItems.add(newItem(Material.NAME_TAG, 1, "&fNamnskylt", ""));
		dropItems.add(newItem(Material.IRON_ORE, 8, "&4Järnmalm", ""));
		dropItems.add(newItem(Material.FURNACE, 1, "&fUgn", ""));
		dropItems.add(newItem(Material.CARROT, 16, "&6Morot", ""));
		dropItems.add(newItem(Material.ICE, 8, "&bIs", ""));
		dropItems.add(newItem(Material.IRON_INGOT, 8, "&4Järn", ""));
		dropItems.add(newItem(Material.IRON_INGOT, 12, "&4Järn", ""));
		dropItems.add(newItem(Material.LEATHER, 8, "&aLäder", ""));
		dropItems.add(newItem(Material.LEATHER, 12, "&aLäder", ""));
		dropItems.add(newItem(Material.GOLD_BOOTS, 1, "&4Alex glittriga skor", "gå runt med äkta\ndiscoskor."));
		dropItems.add(addEnchant(newItem(Material.IRON_HELMET, 1, "&4Järnskor", "akta dig för\nfarliga monster."), Enchantment.PROTECTION_EXPLOSIONS, 1));
		dropItems.add(addEnchant(newItem(Material.IRON_CHESTPLATE, 1, "&4Järnharnesk", "skyddar dig mot\nfarliga monster"), Enchantment.PROTECTION_EXPLOSIONS, 1));
		dropItems.add(newItem(Material.STONE, 32, "&1Sten", ""));
		dropItems.add(newItem(Material.STONE, 16, "&1Sten", ""));
		dropItems.add(newItem(Material.ROTTEN_FLESH, 16, "&1Zombie-kött", ""));
		dropItems.add(newItem(Material.REDSTONE, 8, "&3Rödsten", ""));
		dropItems.add(newItem(Material.BED, 1, "&bSäng", ""));
		dropItems.add(newItem(Material.LOG, 16, "&6LOG", ""));
		dropItems.add(newItem(Material.LOG, 8, "&6LOG", ""));
		dropItems.add(newItem(Material.GLASS, 16, "&fGlas", ""));
		dropItems.add(newItem(Material.SLIME_BALL, 8, "&2Slime", ""));
		dropItems.add(newItem(Material.COOKED_BEEF, 16, "&bBiff", ""));
		dropItems.add(newItem(Material.DIRT, 16, "&6Otursdirt", "alla har inte lika tur.."));
		dropItems.add(newItem(Material.DIRT, 32, "&6Otursdirt", "alla har inte lika tur.."));
		dropItems.add(newItem(Material.LAPIS_BLOCK, 3, "&9Lapis", "nu kan du förtrolla\ndina tools!"));
		dropItems.add(newItem(Material.LAPIS_BLOCK, 8, "&9Lapis", "nu kan du förtrolla\ndina tools!"));
		dropItems.add(newItem(Material.DEAD_BUSH, 1, "&6Kireo's oturs buske!", ""));
		dropItems.add(newItem(Material.WOOD, 16, "&aTrä", "bygg ett fint hus"));
		dropItems.add(newItem(Material.WOOD, 32, "&aTrä", "bygg ett fint hus"));
		dropItems.add(newItem(Material.WOOD, 64, "&aTrä", "bygg ett fint hus"));
		dropItems.add(newItem(Material.CHEST, 8, "&8Kista", "kistor har man aldrig\nför många av"));
		dropItems.add(newItem(Material.CHEST, 16, "&8Kista", "kistor har man aldrig\nför många av"));
		dropItems.add(newItem(Material.EMERALD, 3, "&aEmerald", "är nästa mending\nbok din?"));
		dropItems.add(newItem(Material.EMERALD, 6, "&aEmerald", "är nästa mending\nbok din?"));
		dropItems.add(newItem(Material.DIAMOND, 8, "&bDiamant", "en diamantspade sitter\naldrig fel.."));
		dropItems.add(newItem(Material.DIAMOND, 16, "&bDiamant", "en diamantspade sitter\naldrig fel.."));
		dropItems.add(newItem(Material.COBBLESTONE, 16, "&7Kullersten", ""));
		dropItems.add(newItem(Material.COBBLESTONE, 32, "&7Kullersten", ""));
		dropItems.add(newItem(Material.COBBLESTONE, 64, "&7Kullersten", ""));
		dropItems.add(newItem(Material.STICK, 16, "&cPinne", ""));
		dropItems.add(newItem(Material.STICK, 32, "&cPinne", ""));
		dropItems.add(newItem(Material.STICK, 64, "&cPinne", ""));
		dropItems.add(newItem(Material.COAL, 4, "&0Kol", ""));
		dropItems.add(newItem(Material.COAL, 8, "&0Kol", ""));
		dropItems.add(newItem(Material.IRON_SWORD, 1, "&f&lJärnsvärd", ""));
		dropItems.add(newItem(Material.ARMOR_STAND, 1, "&7Armor Stand", ""));
		dropItems.add(newItem(Material.ARMOR_STAND, 2, "&7Armor Stand", ""));
		dropItems.add(newItem(Material.CAKE, 1, "&aFödelsedagstårta", "grattis grattis.."));
		dropItems.add(newItem(Material.CAKE, 2, "&aFödelsedagstårta", "grattis grattis.."));
		dropItems.add(newItem(Material.BAKED_POTATO, 8, "&6Potatis", ""));
		dropItems.add(newItem(Material.BAKED_POTATO, 16, "&6Potatis", ""));
		dropItems.add(newItem(Material.ACACIA_STAIRS, 32, "&5Acacia trappa", ""));
		dropItems.add(newItem(Material.ACACIA_STAIRS, 64, "&5Acacia trappa", ""));
		dropItems.add(newItem(Material.EXP_BOTTLE, 16, "&6XP Flaska", ""));
		dropItems.add(newItem(Material.EXP_BOTTLE, 32, "&6XP Flaska", ""));
		dropItems.add(newItem(Material.EXP_BOTTLE, 64, "&6XP Flaska", ""));
		dropItems.add(newItem(Material.DIAMOND_BLOCK, 4, "&bDiamantblock", ""));
		dropItems.add(newItem(Material.DIAMOND_BLOCK, 6, "&bDiamantblock", ""));
		dropItems.add(newItem(Material.GOLDEN_APPLE, 10, "&6Guldäpple", ""));
		dropItems.add(addEnchant(newItem(Material.BOW, 1, "&4Oändlighetsbåge", ""), Enchantment.ARROW_INFINITE, 1));
		dropItems.add(addEnchant(newItem(Material.BOW, 1, "&4OP-Båge", ""), Enchantment.ARROW_KNOCKBACK, 2));
		dropItems.add(newItem(Material.ANVIL, 1, "&7Anvil", ""));
		dropItems.add(newItem(Material.PACKED_ICE, 16, "&bPackad is", ""));
		dropItems.add(newItem(Material.ENCHANTMENT_TABLE, 1, "&9Förtrollningsbänk", ""));
		dropItems.add(newItem(Material.ARROW, 16, "&2Pil", ""));
		dropItems.add(newItem(Material.ARROW, 32, "&2Pil", ""));
		dropItems.add(newItem(Material.BONE, 6, "&1Ben", ""));
		dropItems.add(newItem(Material.BONE, 8, "&1Ben", ""));
		dropItems.add(newItem(Material.APPLE, 8, "&bÄpple", ""));
		dropItems.add(newItem(Material.APPLE, 16, "&bÄpple", ""));
		dropItems.add(newItem(Material.BOAT, 1, "&9Båt", ""));
		dropItems.add(addEnchant(newItem(Material.DIAMOND_PICKAXE, 1, "&aRikedom", ""), Enchantment.LOOT_BONUS_BLOCKS, 2));
		dropItems.add(addEnchant(addEnchant(newItem(Material.IRON_SWORD, 1, "&fJärnsvärd", ""), Enchantment.DAMAGE_ALL, 4), Enchantment.DURABILITY, 2));
		dropItems.add(addEnchant(addEnchant(newItem(Material.DIAMOND_PICKAXE, 1, "&9Diamant-hacka", ""), Enchantment.DIG_SPEED, 3), Enchantment.DURABILITY, 3));
		dropItems.add(addEnchant(addEnchant(newItem(Material.DIAMOND_AXE, 1, "&9Diamant-yxa", ""), Enchantment.DIG_SPEED, 3), Enchantment.DURABILITY, 3));
		dropItems.add(addEnchant(newItem(Material.DIAMOND_SWORD, 1, "&4Eskil's op svärd!", "ni andra bör akta\ner, annars..."), Enchantment.DAMAGE_ALL, 5));
	}

	private ItemStack newItem(Material material, int number, String customName,
			String lore) {
		ItemStack item = new ItemStack(material, number);

		ItemMeta meta = item.getItemMeta();
		if (!customName.equals(""))
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					customName));
		if (!lore.equals(""))
			meta.setLore(Arrays.asList((ChatColor.translateAlternateColorCodes(
					'&', lore).split("\n"))));

		item.setItemMeta(meta);
		return item;
	}

	private ItemStack addEnchant(ItemStack item, Enchantment enchantment,
			int level) {
		item.addEnchantment(enchantment, level);
		return item;
	}
}
