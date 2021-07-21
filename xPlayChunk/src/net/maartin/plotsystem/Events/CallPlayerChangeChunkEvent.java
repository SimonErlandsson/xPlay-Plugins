package net.maartin.plotsystem.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class CallPlayerChangeChunkEvent implements Listener {

	Main main;
	public CallPlayerChangeChunkEvent(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlotEnter(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		PlayerChunk playerchunkTo = PlayerChunk.getPlayerChunkAt(event.getTo());
		PlayerChunk playerchunkFrom = PlayerChunk.getPlayerChunkAt(event.getFrom());
		
		if (playerchunkFrom != playerchunkTo) {
			PlayerChangeChunkEvent changePlotEvent = new PlayerChangeChunkEvent(player, playerchunkTo, playerchunkFrom);
			Bukkit.getPluginManager().callEvent(changePlotEvent);
		}
	}
}