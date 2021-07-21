package net.maartin.plotsystem.Listeners.Chunk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class BlockPlaceChunkListener implements Listener {

	Main main;
	public BlockPlaceChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if (player.hasPermission("chunksystem.bypass.blockplace")) return;
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(block.getLocation());
		
		if (playerchunk != null) {
			
			if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains("*")) return;
			
			event.setCancelled(true);
			player.sendMessage(Messages.CLAIMED_DENY.getMessage());
			return;
		}
		
		event.setCancelled(true);
		player.sendMessage(Messages.WILDERNESS_DENY.getMessage());
	}
	
	@EventHandler
	public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
		
		Player player = event.getPlayer();
		
		List<Block> blocks = new ArrayList<Block>();
		
		if (player.hasPermission("chunksystem.bypass.blockplace")) return;
		
		for (int i = 0; i < event.getReplacedBlockStates().size(); i++)
			blocks.add(event.getReplacedBlockStates().get(i).getBlock());
		
		Block block = blocks.get(0);
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(block.getLocation());
		
		for (int i = 1; i < blocks.size(); i++) {
			
			PlayerChunk pc = PlayerChunk.getPlayerChunkAt(blocks.get(i).getLocation());
			
			if (pc != playerchunk && (pc != null && playerchunk != null)) {
				
				if (pc.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
				if (pc.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
				if (pc.getTrustedPlayers().contains("*")) return;
				
				event.setCancelled(true);
				player.sendMessage(Messages.CLAIMED_DENY.getMessage());
				return;
				
			}else if (pc == null || playerchunk == null) {
				if (pc == null && playerchunk != null)
					player.sendMessage(Messages.WILDERNESS_DENY.getMessage());
				event.setCancelled(true);
				return;
			}
		}
	}
}
