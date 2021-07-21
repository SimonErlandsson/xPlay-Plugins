package me.Simonsigge.xPlaySpel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class PlayerHider implements Listener {

	Location loc1, loc2;
	ArrayList<Player> currentHiders;
	ItemStack hideItem;

	public PlayerHider() {
		hideItem = new ItemStack(Material.WATCH, 1);
		ItemMeta hideMeta = hideItem.getItemMeta();
		hideMeta.setDisplayName(ChatColor.GREEN + "Visa/dölj andra spelare");
		hideMeta.setLore(Arrays.asList("Användbart i parkour"));
		hideItem.setItemMeta(hideMeta);

		World w = Main.getPlugin().getServer().getWorlds().get(0);
		loc1 = new Location(w, -200, 292, 200);
		loc2 = new Location(w, 200, 0, -200);
		
		currentHiders = new ArrayList<Player>();

		Main.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				updateHiders();
			}
		}, 0, 20 * 3);
	}
	
	@EventHandler
	public void onToggle(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getItem() != null)
				if (e.getItem().equals(hideItem)) {
					Player p = e.getPlayer();
					if (currentHiders.contains(p)) {
						p.sendMessage(Main.getPrefix() + ChatColor.GREEN + "Du kan nu se andra spelare igen.");
						applyHide(p, false);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
						currentHiders.remove(p);
					} else {
						currentHiders.add(p);
						p.sendMessage(Main.getPrefix() + ChatColor.RED + "Du kan inte längre se andra spelare.");
						applyHide(p, true);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
					}
				}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().equals(hideItem))
			e.setCancelled(true);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		for (Player p : currentHiders) {
			p.hidePlayer(e.getPlayer());
		}
		if (!e.getPlayer().getInventory().contains(hideItem) && !(e.getPlayer().getInventory().getItemInOffHand().equals(hideItem)))
			e.getPlayer().getInventory().addItem(hideItem);
		e.getPlayer().updateInventory();
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (currentHiders.contains(e.getPlayer()))
			currentHiders.remove(e.getPlayer());
	}

	private void updateHiders() {
		Iterator<Player> i = currentHiders.iterator();
		while (i.hasNext()) {
		   Player p = i.next();
		   if (!isWithinSpawn(p.getLocation())) {
				applyHide(p, false);
				p.sendMessage(Main.getPrefix() + ChatColor.GREEN + "Du kan nu se andra spelare igen.");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				i.remove();
			}
		}
	}

	private void applyHide(Player p, boolean hide) {
		if (hide) {
			for (Player sp : Bukkit.getOnlinePlayers())
				if (sp.getName() != p.getName())
					p.hidePlayer(sp);
		} else {
			for (Player sp : Bukkit.getOnlinePlayers())
				if (sp.getName() != p.getName())
					p.showPlayer(sp);
		}
	}

	private boolean isWithinSpawn(Location playerLoc) {
		if (playerLoc.getBlockX() >= loc1.getBlockX() && playerLoc.getBlockX() <= loc2.getBlockX())
			if (playerLoc.getBlockY() <= loc1.getBlockY() && playerLoc.getBlockY() >= loc2.getBlockY())
				if (playerLoc.getBlockZ() <= loc1.getBlockZ() && playerLoc.getBlockZ() >= loc2.getBlockZ())
					return true;
		return false;
	}

}
