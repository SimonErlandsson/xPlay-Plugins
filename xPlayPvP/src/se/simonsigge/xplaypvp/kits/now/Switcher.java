package se.simonsigge.xplaypvp.kits.now;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class Switcher implements Listener {

	int cooldown = 5 * 20;

	@EventHandler  (priority = EventPriority.MONITOR)
	public void onSwitcherDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		
		if ((event.getEntity() instanceof Player)
				&& (event.getDamager() instanceof Snowball)) {

			Snowball snowball = (Snowball) event.getDamager();
			if (snowball.getShooter() instanceof Player) {

				Player entity = (Player) event.getEntity();
				Player shooter = (Player) snowball.getShooter();
				if ((Main.getKitManager().isUsingKit(shooter, Kits.SWITCHER) && (!(Main
						.getKitManager().isUsingKit(entity, Kits.SWITCHER))))) {
					if (!Main.getKitManager().getCooldown()
							.isInCooldown(shooter, Kits.SWITCHER)) {
						Location tLocation = entity.getLocation();
						Location pLocation = shooter.getLocation();
						shooter.teleport(tLocation);
						Main.getChatUtilities().sendPlayerMessage(
								shooter,
								ChatColor.RED + "Du har bytt plats med "
										+ entity.getName() + ".");
						entity.teleport(pLocation);
						Main.getChatUtilities().sendPlayerMessage(
								entity,
								ChatColor.RED
										+ "Du har bytt plats med "
										+ shooter.getName() + ".");

						shooter.sendMessage(ChatColor.RED + "Det finns en "
								+ cooldown / 20
								+ " sekunders cooldown med Switcher.");
						
						Main.getKitManager().getCooldown().addCooldown(shooter, Kits.SWITCHER, cooldown);
						Main.getKillListener().setLastDamage(entity, shooter);
					}
				}
			}
		}
	}

}
