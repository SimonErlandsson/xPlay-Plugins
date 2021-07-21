package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class PlayerBucketEmptyChunkListener implements Listener {

	Main main;
	public PlayerBucketEmptyChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onBlockBreak(PlayerBucketEmptyEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());
		
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
}
