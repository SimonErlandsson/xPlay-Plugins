package me.Simonsigge.xPlayDonator.MySQL;

import me.Simonsigge.xPlayDonator.Main.Main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
				new Runnable() {

					@Override
					public void run() {
						Bukkit.getScheduler().runTaskAsynchronously(
								Main.getInstance(), new Runnable() {
									public void run() {
										Main.getDataHandler()
												.tryLoadPlayerData(
														e.getPlayer());
										if (Main.getDataHandler().isDonor(
												e.getPlayer()))
											Main.getCosmeticsHandler()
													.setPlayerAllClothes(
															e.getPlayer());
									}
								});
					}
				}, 20);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(),
				new Runnable() {
					public void run() {
						Main.getDataHandler().trySavePlayerData(e.getPlayer());
						Main.getDataHandler().removePlayerFromCache(
								e.getPlayer());
					}
				});
	}

}
