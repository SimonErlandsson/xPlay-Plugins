package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class PlayerLeashEntityChunkListener implements Listener {

	Main main;
	public PlayerLeashEntityChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerLeashEntityEvent event) {
		
		Player player = event.getPlayer();
		Entity entity = event.getEntity();
		
		if (player.hasPermission("chunksystem.bypass.interactentity")) return;
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(entity.getLocation());
		
		if (entity.getType() == EntityType.BOAT || entity.getType() == EntityType.MINECART) return;
		
		if (playerchunk != null) {
			
			if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains("*")) return;
			
			event.setCancelled(true);
			return;
		}
	}
}
