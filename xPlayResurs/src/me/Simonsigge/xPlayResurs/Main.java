package me.Simonsigge.xPlayResurs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	boolean hasBeenRun = false;
	ItemStack bookItem;
	ArrayList<String> chestPositions = new ArrayList<String>();

	public void onEnable() {
		System.out.println("xPlayResurs körs nu!");
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		System.out.println("xPlayResurs körs nu inte!");
	}

	public boolean onCommand(CommandSender sender, Command command, String cmdlabel, String[] args) {
		if (cmdlabel.equalsIgnoreCase("kistor")) {
			if ((sender.hasPermission("xPlayResurs.Chest")) || (sender.isOp())) {
				sender.sendMessage(ChatColor.DARK_RED + "< -- Här är alla kistors positioner: -- >");
				for (int i = 0; i < this.chestPositions.size(); i++) {
					sender.sendMessage(ChatColor.RED + "      " + (String) this.chestPositions.get(i));
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Inte tillåtelse!");
			}
		}
		return false;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (!this.hasBeenRun) {
			SetSpawn(e.getPlayer());
			PlaceChests(e.getPlayer().getWorld());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"gamerule keepInventory true");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"gamerule announceAdvancements false");
			this.hasBeenRun = true;
		}
		e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
	}

	public void SetSpawn(Player p) {
		World w = p.getWorld();

		Location loc = new Location(w, 0.5D, 65.0D, 0.5D);
		Location locOneUp = loc;
		locOneUp.setY(locOneUp.getY() + 1.0D);
		while ((!loc.getBlock().getType().equals(Material.AIR))
				&& (!locOneUp.getBlock().getType().equals(Material.AIR))) {
			double locOne = loc.getY() + 1.0D;
			loc.setY(locOne);
			locOneUp = loc;
			locOneUp.setY(locOneUp.getY() + 1.0D);
		}
		w.setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		System.out.println("xPlayMAINResurs - Satte spawn till: " + loc + "!");
	}

	public void PlaceChests(World w) {
		int numberOfChestsPerWorld = 1500;
		int minNumberOfItemsInChest = 3;
		int maxNumberOfItemsInChest = 7;
		Location smallestLocation = new Location(w, -2000.0D, 50.0D, -2000.0D);
		Location biggestLocation = new Location(w, 2000.0D, 50.0D, 2000.0D);

		this.bookItem = new ItemStack(Material.BOOK, 1);
		ItemMeta bookItemMeta = this.bookItem.getItemMeta();
		bookItemMeta.setDisplayName(ChatColor.GREEN + "Grattis!");
		String[] bookItemStringLore = { "Du hittade en lyckokista!", "Här finns lite roliga grejer.",
				"Är den tom - då har någon redan tagit den. :C" };
		bookItemMeta.setLore(Arrays.asList(bookItemStringLore));
		this.bookItem.setItemMeta(bookItemMeta);

		ItemStack[] itemArray = { new ItemStack(Material.LOG, 32), new ItemStack(Material.BROWN_MUSHROOM, 16),
				new ItemStack(Material.DIAMOND, 3), new ItemStack(Material.APPLE, 4),
				new ItemStack(Material.BAKED_POTATO, 10), new ItemStack(Material.IRON_ORE, 12),
				new ItemStack(Material.GOLD_SWORD, 1), new ItemStack(Material.COBBLESTONE, 64),
				new ItemStack(Material.ARROW, 32), new ItemStack(Material.BRICK, 10),
				new ItemStack(Material.STONE_SPADE, 1), new ItemStack(Material.BED, 1),
				new ItemStack(Material.GLASS, 16), new ItemStack(Material.WORKBENCH, 1),
				new ItemStack(Material.RAILS, 16), new ItemStack(Material.BUCKET, 1), new ItemStack(Material.COAL, 8),
				new ItemStack(Material.GOLD_ORE, 10), new ItemStack(Material.DIAMOND_PICKAXE, 1),
				new ItemStack(Material.WATCH, 1), new ItemStack(Material.DIAMOND_BLOCK, 1),
				new ItemStack(Material.RED_MUSHROOM, 16), new ItemStack(Material.BOW, 1),
				new ItemStack(Material.SUGAR, 32), new ItemStack(Material.BOOKSHELF, 3),
				new ItemStack(Material.ARMOR_STAND, 2), new ItemStack(Material.COMPASS, 1),
				new ItemStack(Material.EXP_BOTTLE, 32), new ItemStack(Material.GHAST_TEAR, 2),
				new ItemStack(Material.ICE, 10), new ItemStack(Material.FENCE, 32) };
		for (int i = 0; i < numberOfChestsPerWorld; i++) {
			Location loc = new Location(w, 0.0D, 50.0D, 0.0D);
			Random r = new Random();
			loc.setX(r.nextInt(biggestLocation.getBlockX() - smallestLocation.getBlockX() + 1)
					+ smallestLocation.getBlockX());
			loc.setZ(r.nextInt(biggestLocation.getBlockZ() - smallestLocation.getBlockZ() + 1)
					+ smallestLocation.getBlockZ());

			Location locOneUp = loc;
			locOneUp.setY(locOneUp.getY() + 1.0D);
			while ((!loc.getBlock().getType().equals(Material.AIR))
					|| (!locOneUp.getBlock().getType().equals(Material.AIR))) {
				double locOne = loc.getY() + 1.0D;
				loc.setY(locOne);
				locOneUp = loc;
				locOneUp.setY(locOneUp.getY() + 1.0D);
			}
			Location locOneDown = loc;
			locOneDown.setY(locOneDown.getY() - 1.0D);
			if (locOneDown.getBlock().getType() == Material.AIR) {
				loc = locOneDown;
			}
			loc.getBlock().setType(Material.CHEST);
			Chest c = (Chest) loc.getBlock().getState();
			Inventory inv = c.getInventory();
			inv.setItem(13, this.bookItem);

			int numberOfTimes = r.nextInt(maxNumberOfItemsInChest - minNumberOfItemsInChest + 1)
					+ minNumberOfItemsInChest;
			for (int p = 0; p < numberOfTimes; p++) {
				int slot = 13;
				while ((slot == 13) || (inv.getItem(slot) != null)) {
					slot = r.nextInt(27);
				}
				inv.setItem(slot, itemArray[r.nextInt(itemArray.length)]);

				this.chestPositions.add(loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
			}
		}
		System.out.println("Placerade alla " + numberOfChestsPerWorld + " kistor.");
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if ((e.getCurrentItem() != null) && (e.getCurrentItem().equals(this.bookItem))) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Material blockType = e.getBlock().getType();
		if ((blockType == Material.CHEST) || (blockType == Material.TRAPPED_CHEST) || (blockType == Material.FURNACE)
				|| (blockType == Material.ENDER_CHEST)) {
			e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE
					+ "VARNING! Resursvärlden resettas varje natt. Spara inte saker här, använd mark eller towny-världen istället!");
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.CHEST) {
			Inventory inv = ((InventoryHolder) e.getBlock().getState()).getInventory();
			if (inv.contains(this.bookItem)) {
				inv.remove(this.bookItem);
			}
		}
	}
}