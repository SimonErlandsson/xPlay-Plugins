package me.Simonsigge.xPlayCore;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

import net.milkbowl.vault.economy.Economy;

public class KlassSetup implements Listener {
	
	Economy economy = null;
	HashMap<String, Integer> klasser;
	
	public KlassSetup() {
		RegisteredServiceProvider<Economy> rsp = Main.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
		economy = rsp.getProvider();
		klasser = new HashMap<String, Integer>();

		klasser.put("Nyfödd", 0);
		klasser.put("Uteliggare", 5000);
		klasser.put("Fattig", 10000);
		klasser.put("Underklass", 25000);
		klasser.put("Student", 50000);
		klasser.put("Arbetare", 125000);
		klasser.put("Medelklass", 250000);
		klasser.put("Överklass", 500000);
		klasser.put("Miljonär", 1000000);
		klasser.put("Rik", 10000000);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				onceEvery();
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(), 0, 20 * 30);
	
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		updateClass(e.getPlayer());
		
		if (!e.getPlayer().hasPermission("xPlayCore.Admin") && e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
			e.getPlayer().getInventory().clear();
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign s = (Sign) e.getClickedBlock().getState();
				if (s.getLine(0).equalsIgnoreCase("[xPlayCore]")) {
					if (economy.getBalance(e.getPlayer()) < klasser.get(s.getLine(1)) &! e.getPlayer().hasPermission("xPlayCore.Staff")) {
						e.getPlayer().sendMessage(Main.getPrefix() + ChatColor.RED + "Du måste vara klassen " + s.getLine(1)
								+ ". Tjäna mer pengar!");
						return;
					}
					String[] parts = s.getLine(2).split(",");
					Location loc = new Location(e.getPlayer().getWorld(), Integer.parseInt(parts[0].trim()),
							Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()));
					
					Location oLoc = e.getPlayer().getLocation();
					loc.setPitch(oLoc.getPitch());
					loc.setYaw(oLoc.getYaw());
					
					e.getPlayer().teleport(loc);
					e.getPlayer().sendMessage(Main.getPrefix() + ChatColor.GREEN + "Du teleporteras!");
				}
			}
		}
	}

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[xPlayCore]") & !e.getPlayer().hasPermission("xPlayCore.Admin")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.getPrefix() + ChatColor.RED + "Du måste vara admin!");
			e.getBlock().breakNaturally();
		}
	}

	public void onceEvery() {
		for (Player p : Main.getPlugin().getServer().getOnlinePlayers()) {
			updateClass(p);
		}
	}

	public void updateClass(Player p) {
		double playerBal = economy.getBalance(p);

		if (p.hasPermission("xPlayCore.Staff")) {
			p.setDisplayName(ChatColor.YELLOW + p.getName());
			p.setPlayerListName(ChatColor.YELLOW + p.getName());
			return;
		}

		if (playerBal < 5000) {
			setDisplayName("Nyfödd", p);
			return;
		}

		if (playerBal < 10000) {
			setDisplayName("Uteliggare", p);
			return;
		}

		if (playerBal < 25000) {
			setDisplayName("Fattig", p);
			return;
		}

		if (playerBal < 50000) {
			setDisplayName("Underklass", p);
			return;
		}

		if (playerBal < 125000) {
			setDisplayName("Student", p);
			return;
		}

		if (playerBal < 250000) {
			setDisplayName("Arbetare", p);
			return;
		}

		if (playerBal < 500000) {
			setDisplayName("Medelklass", p);
			return;
		}

		if (playerBal < 1000000) {
			setDisplayName("Överklass", p);
			return;
		}

		if (playerBal < 10000000) {
			setDisplayName("Miljonär", p);
			return;
		}

		setDisplayName("Rik", p);
		return;
	}

	public void setDisplayName(String playerClass, Player p) {
		p.setDisplayName(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " [" + ChatColor.GREEN + playerClass
				+ ChatColor.WHITE + "]" + ChatColor.YELLOW);
		p.setPlayerListName(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " [" + ChatColor.GREEN + playerClass
				+ ChatColor.WHITE + "]");
	}
}
