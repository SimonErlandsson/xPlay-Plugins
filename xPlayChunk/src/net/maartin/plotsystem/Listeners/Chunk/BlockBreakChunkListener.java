package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class BlockBreakChunkListener implements Listener {

	Main main;
	public BlockBreakChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if (player.hasPermission("chunksystem.bypass.blockbreak")) return;
		
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
}
