package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class VehicleDestroyChunkListener implements Listener {

	Main main;
	public VehicleDestroyChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerInteract(VehicleDestroyEvent event) {
		
		if (!(event.getAttacker() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getAttacker();
		Entity vehicle = event.getVehicle();
		
		if (player.hasPermission("chunksystem.bypass.damageentities")) return;
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(vehicle.getLocation());
		
		if (playerchunk != null) {

			if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains("*")) return;
			
			player.sendMessage(Messages.CLAIMED_DENY.getMessage());

			event.setCancelled(true);
			return;
		}
		
		player.sendMessage(Messages.WILDERNESS_DENY.getMessage());

		event.setCancelled(true);
	}
}
