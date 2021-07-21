package se.simonsigge.xplaypvp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	Location spawnLoc;

	public PlayerListener() {
		spawnLoc = new Location(Main.getInstance().getServer().getWorlds()
				.get(0), 0.5D, 50.0D, 0.5D);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(spawnLoc);
		p.setLevel(0);
		p.setExp(0);
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.updateInventory();
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getItemDrop() != null)
			if (e.getItemDrop().getItemStack().getType() == Material.BOWL || e.getItemDrop().getItemStack().getType() == Material.MUSHROOM_SOUP) {}
				
			else 
				e.setCancelled(true);
	}

}
