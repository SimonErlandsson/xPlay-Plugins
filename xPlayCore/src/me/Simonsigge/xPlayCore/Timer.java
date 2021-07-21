package me.Simonsigge.xPlayCore;

import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
	
	public Timer () {
		new BukkitRunnable() {

			@Override
			public void run() {
			 
				oncePerMinute(); 
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(), 0, 20 * 60);
	}
	
	public void oncePerMinute() {
		
		new BukkitRunnable() {

			@Override
			public void run() {
				
				Main.getPlugin().getServer().dispatchCommand(Main.getPlugin().getServer().getConsoleSender(), "save-all");
				
			}
		}.runTask(Main.getPlugin());
	}
}
