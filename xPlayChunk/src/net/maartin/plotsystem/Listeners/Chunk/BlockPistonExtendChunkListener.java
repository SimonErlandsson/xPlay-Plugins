package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class BlockPistonExtendChunkListener implements Listener {
	
	Main main;
	public BlockPistonExtendChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onExtend(BlockPistonExtendEvent event) {
		
		PlayerChunk playerchunkFrom = PlayerChunk.getPlayerChunkAt(event.getBlock().getLocation());
		PlayerChunk playerchunkTo = PlayerChunk.getPlayerChunkAt(event.getBlock().getRelative(event.getDirection()).getLocation());
		
		if (playerchunkFrom != playerchunkTo) {
			
			if (playerchunkFrom != null && playerchunkTo != null && playerchunkFrom.getOwnerUUID().toString().equalsIgnoreCase(playerchunkTo.getOwnerUUID().toString())) return;
			
			event.setCancelled(true);
		}

		for (Block block : event.getBlocks()) {
			PlayerChunk playerchunkToBlocks = PlayerChunk.getPlayerChunkAt(block.getRelative(event.getDirection()).getLocation());
			
			if (playerchunkFrom != playerchunkToBlocks) {
				
				if (playerchunkFrom != null && playerchunkToBlocks != null && playerchunkFrom.getOwnerUUID().toString().equalsIgnoreCase(playerchunkToBlocks.getOwnerUUID().toString())) continue;
				
				event.setCancelled(true);
				break;
			}
		}
	}
}
