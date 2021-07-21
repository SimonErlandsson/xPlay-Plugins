package me.Simonsigge.xPlayTowny;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	List<Location> infoLocations;
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		infoLocations = new ArrayList<Location>();
		World w = getServer().getWorlds().get(0);
		infoLocations.add(new Location(w, 20, 70, -4));
		infoLocations.add(new Location(w, 17, 70, 4));
		infoLocations.add(new Location(w, 4, 70, 20));
		infoLocations.add(new Location(w, -4, 70, 17));
		infoLocations.add(new Location(w, -20, 70, 4));
		infoLocations.add(new Location(w, -17, 70, -4));
		infoLocations.add(new Location(w, -4, 70, -20));
		infoLocations.add(new Location(w, 4, 70, -17));
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				if (infoLocations.contains(e.getClickedBlock().getLocation())) {
					Player p = e.getPlayer();
					p.sendMessage(ChatColor.GREEN + "------------------------------------");
					p.sendMessage(ChatColor.AQUA + "   Länk till text: https://xPlayServer.net/Towny");
					p.sendMessage(" ");
					p.sendMessage(ChatColor.AQUA + "   Länk till video: https://youtu.be/Towny");
					p.sendMessage(ChatColor.GREEN + "------------------------------------");
				}
			}
		}
	}

}
