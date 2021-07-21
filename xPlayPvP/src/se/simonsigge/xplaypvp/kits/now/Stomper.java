package se.simonsigge.xplaypvp.kits.now;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class Stomper implements Listener {

	int stompRadius = 3;

	@EventHandler(priority = EventPriority.MONITOR)
	public void onStomperDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;

		if ((event.getEntity() != null) && ((event.getEntity() instanceof Player))
				&& (event.getCause() == EntityDamageEvent.DamageCause.FALL)) {
			Player player = (Player) event.getEntity();

			if ((player.getFallDistance() >= 6.0F) && (Main.getKitManager().isUsingKit(player, Kits.STOMPER))) {
				boolean hurtPlayer = false;
				Location pLoc = player.getLocation().clone();
				pLoc.setY(0.0D);
				if (event.getDamage() < 2.0D) {
				}
				List<Player> playerList = player.getWorld().getPlayers();
				for (Player target : playerList) {
					if ((target != null) && (target.isValid())
							&& (!player.getUniqueId().equals(target.getUniqueId()))) {
						Location tLoc = target.getLocation().clone();
						tLoc.setY(0.0D);
						if ((target.getGameMode() != GameMode.CREATIVE)
								&& (player.getLocation().getY() >= target.getLocation().getY())
								&& (pLoc.distance(tLoc) <= stompRadius) && (target.getGameMode() != GameMode.SPECTATOR)) {
							if (player.getLocation().getBlockY() - target.getLocation().getBlockY() <= stompRadius) {
								if ((!target.isSneaking()) && (!target.isBlocking())) {
									hurtPlayer = true;
									Main.getKillListener().onAnyDeath(target, player);
									target.setHealth(0.0D);
									Main.getChatUtilities().sendServerMessage(ChatColor.RED + target.getName()
											+ " blev stompad till döds av " + player.getName() + ".");

								} else if ((!target.isSneaking()) || (!target.isBlocking())) {
									hurtPlayer = true;
									if (target.getHealth() - 6.0D > 0.0D) {
										target.setHealth(target.getHealth() - 6.0D);
									} else {
										Main.getKillListener().onAnyDeath(target, player);
										target.setHealth(0.0D);
										Main.getChatUtilities().sendServerMessage(ChatColor.RED + target.getName()
												+ " blev stompad till döds av " + player.getName() + ".");
									}
								}
							}
						}
					}
				}
				if (hurtPlayer) {
					event.setDamage(1.0D);
				} else {
					event.setDamage(0);
					player.setHealth(1);
				}
			}
		}
	}

}
