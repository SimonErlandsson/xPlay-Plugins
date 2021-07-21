package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class InteractEntityChunkListener implements Listener {

	Main main;
	public InteractEntityChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
		
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		if (player.hasPermission("chunksystem.bypass.interactentity")) return;
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(entity.getLocation());
		
		if (entity.getType() != EntityType.ITEM_FRAME && entity.getType() != EntityType.LEASH_HITCH && entity.getType() != EntityType.MINECART_CHEST && 
				entity.getType() != EntityType.MINECART_COMMAND && entity.getType() != EntityType.MINECART_FURNACE && entity.getType() != EntityType.MINECART_HOPPER &&
				entity.getType() != EntityType.MINECART_MOB_SPAWNER && entity.getType() != EntityType.MINECART_TNT && entity.getType() != EntityType.BOAT) return;
		
		if (playerchunk != null) {
			
			if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains("*")) return;
			
			event.setCancelled(true);
			return;
		}
		
		event.setCancelled(true);
	}
}
