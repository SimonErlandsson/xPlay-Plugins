package me.Simonsigge.xPlaySpel;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerListener implements Listener {

	Location spawnLoc;

	public PlayerListener() {
		payoutPlayers = new ArrayList<String>();

		spawnLoc = new Location(Main.getPlugin().getServer().getWorlds().get(0), 0.5D, 50.0D, 0.5D);
		Main.getPlugin().getServer().getWorlds().get(0).setSpawnLocation(spawnLoc);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(spawnLoc);
	}
	
	ArrayList<String> payoutPlayers;

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.getPlayer().hasPermission("xPlayCore.Staff")) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (e.getClickedBlock().getState() instanceof Sign) {
					Sign s = (Sign) e.getClickedBlock().getState();
					if (s.getLine(0).equalsIgnoreCase("[xPlaySpelTP]")) {
						
						if (payoutPlayers.contains(e.getPlayer().getName()))
							return;
						
						int money = Integer.parseInt(s.getLine(1).substring(0, s.getLine(1).length() - 1));



						String[] parts = s.getLine(2).split(",");
						Location loc = new Location(e.getPlayer().getWorld(), Integer.parseInt(parts[0].trim()),
								Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()));
						Location oLoc = e.getPlayer().getLocation();
						loc.setPitch(oLoc.getPitch());
						loc.setYaw(oLoc.getYaw());
						e.getPlayer().teleport(loc);
						e.getPlayer().sendMessage(
								Main.getPrefix() + ChatColor.GREEN + "Du teleporteras med vinsten i plånboken!");
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,
								1F);
						Main.getEconomy().depositPlayer(e.getPlayer(), money);
						
						payoutPlayers.add(e.getPlayer().getName());
						
						new BukkitRunnable() {

							@Override
							public void run() {
								payoutPlayers.remove(e.getPlayer().getName());
								
							}
							
						}.runTaskLaterAsynchronously(Main.getPlugin(), 20 * 5);
					}
				}
			}
		}
	}
	
	int mobkillAmount = 5;
	
	@EventHandler
	public void OnEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() != null)
			if (e.getEntity() instanceof Monster || e.getEntity() instanceof Animals)
				if (e.getEntity().getKiller() != null)
					if (e.getEntity().getKiller() instanceof Player) {
						Main.getEconomy().depositPlayer(e.getEntity().getKiller(), 5);
						e.getEntity().getKiller().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Main.getPrefix() + "§7Du fick §a"
								+ mobkillAmount + " §6§lPlayMynt! §7(§e" + Main.getEconomy().getBalance(e.getEntity().getKiller()) + "§7)"));
					}
	}

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[xPlaySpelTP]") & !e.getPlayer().hasPermission("xPlaySpel.Admin")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.getPrefix() + ChatColor.RED + "Du måste vara admin!");
			e.getBlock().breakNaturally();
		}
	}

}
