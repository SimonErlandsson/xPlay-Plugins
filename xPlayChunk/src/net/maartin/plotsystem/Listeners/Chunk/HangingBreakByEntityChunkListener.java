package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class HangingBreakByEntityChunkListener implements Listener {
	
	Main main;
	public HangingBreakByEntityChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onPlayerInteractAtEntity(HangingBreakByEntityEvent event) {
		
		if (!(event.getRemover() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getRemover();
		
		if (player.hasPermission("chunksystem.bypass.interact")) return;
		
		Hanging entity = event.getEntity();
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(entity.getLocation());
		
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
