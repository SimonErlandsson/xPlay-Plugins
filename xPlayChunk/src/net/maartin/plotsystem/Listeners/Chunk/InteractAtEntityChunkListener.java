package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class InteractAtEntityChunkListener implements Listener {

	Main main;
	public InteractAtEntityChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		if (player.hasPermission("chunksystem.bypass.interactentities")) return;
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(entity.getLocation());
		
		if (entity.getType() == EntityType.BOAT || entity.getType() == EntityType.MINECART) return;
		
		if (playerchunk != null) {
			
			if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains("*")) return;
			
			event.setCancelled(true);
			if (event.getHand() == EquipmentSlot.HAND)
				player.sendMessage(Messages.CLAIMED_DENY.getMessage());
			return;
		}
		
		event.setCancelled(true);
		if (event.getHand() == EquipmentSlot.HAND)
			player.sendMessage(Messages.WILDERNESS_DENY.getMessage());
	}
}
