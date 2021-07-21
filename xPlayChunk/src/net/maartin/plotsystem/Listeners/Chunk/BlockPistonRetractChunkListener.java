package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class BlockPistonRetractChunkListener implements Listener {
	
	Main main;
	public BlockPistonRetractChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	
	@EventHandler
	public void onRetract(BlockPistonRetractEvent event) {
		
		Block pistonBlock = event.getBlock();
		PlayerChunk playerchunkFrom = PlayerChunk.getPlayerChunkAt(pistonBlock.getLocation());
		
		
		for (int i = 0; i < event.getBlocks().size(); i++) {
			
			Block block = event.getBlocks().get(i);
			PlayerChunk playerchunkToBlocks = PlayerChunk.getPlayerChunkAt(block.getLocation());
			
			if (playerchunkFrom != playerchunkToBlocks) {
				
				if (playerchunkFrom != null && playerchunkToBlocks != null && playerchunkFrom.getOwnerUUID().toString().equalsIgnoreCase(playerchunkToBlocks.getOwnerUUID().toString())) continue;
				
				event.setCancelled(true);
			}
		}
	}
}
