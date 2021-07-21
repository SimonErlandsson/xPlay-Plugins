package net.maartin.plotsystem.Listeners.Chunk;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class VehicleMoveChunkListener implements Listener {
	
	Main main;
	public VehicleMoveChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onMove(VehicleMoveEvent event) {
		
		List<Entity> passengers = event.getVehicle().getPassengers();
		
		Entity driver = null;
		Vehicle vehicle = event.getVehicle();
		
		
		if (passengers.size() > 0)
			driver = passengers.get(0);
		
		PlayerChunk playerchunkTo = PlayerChunk.getPlayerChunkAt(event.getTo());
		PlayerChunk playerchunkFrom = PlayerChunk.getPlayerChunkAt(event.getFrom());
		
		if (playerchunkFrom != playerchunkTo) {

			if (driver instanceof Player && playerchunkTo != null) {
				Player player = (Player)driver;
				
				if (playerchunkTo.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
				if (playerchunkTo.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
				if (playerchunkTo.getTrustedPlayers().contains("*")) return;
				
			}
			
			if (playerchunkFrom != null && playerchunkTo != null && playerchunkFrom.getOwnerUUID().toString().equalsIgnoreCase(playerchunkTo.getOwnerUUID().toString())) return;
			
			vehicle.eject();
			vehicle.remove();
			
			if (vehicle instanceof Boat)
				vehicle.getWorld().dropItemNaturally(vehicle.getLocation(), getDrop((Boat)vehicle));
			else if(vehicle instanceof Minecart) {
				vehicle.getWorld().dropItemNaturally(vehicle.getLocation(), new ItemStack(Material.MINECART)); 
				
				MaterialData matData = ((Minecart)vehicle).getDisplayBlock();
				if (matData != null && matData.getItemType() != null && matData.getItemType() != Material.AIR) 
					vehicle.getWorld().dropItemNaturally(vehicle.getLocation(), new ItemStack(matData.getItemType()));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getDrop(Boat boat) {
		Material m;
		
		switch (boat.getWoodType().getData()) {
		case 1:
			m = Material.BOAT_SPRUCE;
			break;
		case 2:
			m = Material.BOAT_BIRCH;
			break;
		case 3:
			m = Material.BOAT_JUNGLE;
			break;
		case 4:
			m = Material.BOAT_ACACIA;
			break;
		case 5:
			m = Material.BOAT_DARK_OAK;
			break;
		default:
			m = Material.BOAT;
		}
		return new ItemStack(m, 1);
	}
}
