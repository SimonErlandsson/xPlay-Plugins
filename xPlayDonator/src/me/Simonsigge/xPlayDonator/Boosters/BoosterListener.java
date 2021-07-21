package me.Simonsigge.xPlayDonator.Boosters;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BoosterListener implements Listener {

	BoosterHandler boosterHandler;

	public BoosterListener(BoosterHandler boosterHandler) {
		this.boosterHandler = boosterHandler;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (boosterHandler.getBoosterActive())

			new BukkitRunnable() {

				@Override
				public void run() {

					if (e.getPlayer().getWorld().getName().equals(Misc.STAD_WORLD_NAME))
						Main.getMsgAPI().boosterIsActiveDrop(e.getPlayer(), boosterHandler.getBoosterActivator());
					else
						Main.getMsgAPI().boosterIsActiveX2(e.getPlayer(), boosterHandler.getBoosterActivator());
				}

			}.runTaskLater(Main.getInstance(), 5);
	}

}
