package net.maartin.plotsystem.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerData;

public class PlayerLeaveListener implements Listener {
	
	Main main;
	public PlayerLeaveListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (PlayerData.getPlayer(player.getUniqueId()) != null) 
			PlayerData.removePlayer(player);
	}
}
