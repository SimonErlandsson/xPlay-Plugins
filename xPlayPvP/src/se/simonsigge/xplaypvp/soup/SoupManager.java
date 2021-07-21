package se.simonsigge.xplaypvp.soup;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import se.simonsigge.xplaypvp.Main;

public class SoupManager implements Listener {
	
	private Location loc1, loc2;
	private HashMap<Player, Boolean> soupMap;
	
	public SoupManager() {
		loc1 = new Location(Main.getInstance().getServer().getWorlds()
				.get(0), -3, 54, 9);
		loc2 = new Location(Main.getInstance().getServer().getWorlds()
				.get(0), 2, 49, 5);
		soupMap = new HashMap<Player, Boolean>();
	}
	
	public void checkPlayerSoup(Player p) {
		if (!soupMap.containsKey(p))
			return;
		
		if (soupMap.get(p) == false)
			return;
		else {
			for (int i = 0; i < 36; i++) {
				PlayerInventory inv = p.getInventory();
				if (inv.getItem(i) == null)
					inv.setItem(i, new ItemStack(Material.MUSHROOM_SOUP, 1));
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign s = (Sign) e.getClickedBlock().getState();
				if (s.getLine(0).contains("[Warp]") && isWithinSpawn(e.getClickedBlock().getLocation())) {
					Main.getGuiManager().openSelectGui(e.getPlayer());
					Location loc = e.getClickedBlock().getLocation().clone().add(0, 0, 2);
					Sign infoSign = (Sign) loc.getBlock().getState();
					
					if (infoSign.getLine(0).contains("true"))
						soupMap.put(e.getPlayer(), true);
					else
						soupMap.put(e.getPlayer(), false);
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (soupMap.containsKey(e.getPlayer()))
			soupMap.remove(e.getPlayer());
	}
	
	private boolean isWithinSpawn(Location playerLoc) {
		if (playerLoc.getBlockX() >= loc1.getBlockX() && playerLoc.getBlockX() <= loc2.getBlockX())
			if (playerLoc.getBlockY() <= loc1.getBlockY() && playerLoc.getBlockY() >= loc2.getBlockY())
				if (playerLoc.getBlockZ() <= loc1.getBlockZ() && playerLoc.getBlockZ() >= loc2.getBlockZ())
					return true;
		return false;
	}

}
