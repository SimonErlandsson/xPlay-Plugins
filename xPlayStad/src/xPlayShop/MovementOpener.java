package xPlayShop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Simonsigge.xPlayStad.Main;

public class MovementOpener implements Listener {
	
	ArrayList<Location> locations;
	
	public MovementOpener() {
		locations = new ArrayList<Location>();
		World w = Bukkit.getWorlds().get(0);
		locations.add(new Location(w, -11, 50, -35));
		locations.add(new Location(w, -11, 50, -26));
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if ((e.getFrom().getBlockX() == e.getTo().getBlockX()) && (e.getFrom().getBlockY() == e.getTo().getBlockY())
				&& (e.getFrom().getBlockZ() == e.getTo().getBlockZ()))
			return;
		Location roundedLoc = new Location(e.getPlayer().getWorld(), e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());

		boolean isDestTrigger = false;
		for (Location tr : locations)
			if (tr.equals(roundedLoc)) {
				isDestTrigger = true;
				break;
			}

		if (!isDestTrigger)
			return;
		
		Player p = e.getPlayer();
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
		Main.getInstance().guiManager.openDefaultGui(p);
	}

}
