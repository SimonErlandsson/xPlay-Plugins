package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class WaterAndLavaFlowChunkListener implements Listener {
	
	Main main;
	public WaterAndLavaFlowChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onFlow(BlockFromToEvent event) {
		
		PlayerChunk playerchunkFrom = PlayerChunk.getPlayerChunkAt(event.getBlock().getLocation());
		PlayerChunk playerchunkTo = PlayerChunk.getPlayerChunkAt(event.getToBlock().getLocation());
		
		if (playerchunkFrom != playerchunkTo) {
			
			if (playerchunkFrom != null && playerchunkTo != null) {
				
				if (playerchunkFrom.getOwnerUUID().toString().equals(playerchunkTo.getOwnerUUID())) return;
				
				for (String uuid : playerchunkTo.getTrustedPlayers())
					if (uuid.equals(playerchunkFrom.getOwnerUUID())) return;
			}
			
			event.setCancelled(true);
		}
	}
}
