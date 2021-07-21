package se.simonsigge.xplaypvp.kits.now;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class Fisherman implements Listener {

	@EventHandler  (priority = EventPriority.MONITOR)
	public void onFish(PlayerFishEvent event) {
		if (event.isCancelled())
			return;
		
		Player playerA = event.getPlayer();
		Location locationA = playerA.getLocation();
		if (event.getCaught() != null) {
			Entity entityB = event.getCaught();
			if ((entityB instanceof Player)) {
				Player playerB = ((Player) entityB).getPlayer();
				if (Main.getKitManager().isUsingKit(playerA, Kits.FISHERMAN)
						&& !(Main.getKitManager().isUsingKit(playerB,
								Kits.FISHERMAN))) {
					playerB.teleport(locationA);
					Main.getKillListener().setLastDamage(playerB, playerA);
				}
			}
		}
	}

}
