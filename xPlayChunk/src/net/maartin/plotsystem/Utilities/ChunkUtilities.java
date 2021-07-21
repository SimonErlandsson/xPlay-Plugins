package net.maartin.plotsystem.Utilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;

public class ChunkUtilities {

	public void setTorchesInCorners(Chunk chunk) {
		if (chunk.isLoaded() == false)
			chunk.load();

		int placeYBlockAt00 = chunk.getWorld().getHighestBlockYAt(chunk.getBlock(0, 0, 0).getLocation().getBlockX(), chunk.getBlock(0, 0, 0).getLocation().getBlockZ());
		int placeYBlockAt015 = chunk.getWorld().getHighestBlockYAt(chunk.getBlock(0, 0, 15).getLocation().getBlockX(), chunk.getBlock(0, 0, 15).getLocation().getBlockZ());
		int placeYBlockAt150 = chunk.getWorld().getHighestBlockYAt(chunk.getBlock(15, 0, 0).getLocation().getBlockX(), chunk.getBlock(15, 0, 0).getLocation().getBlockZ());
		int placeYBlockAt1515 = chunk.getWorld().getHighestBlockYAt(chunk.getBlock(15, 0, 15).getLocation().getBlockX(), chunk.getBlock(15, 0, 15).getLocation().getBlockZ());

		List<Location> locs = Arrays.asList(chunk.getBlock(0, placeYBlockAt00, 0).getLocation(), chunk.getBlock(0, placeYBlockAt015, 15).getLocation(),
				chunk.getBlock(15, placeYBlockAt150, 0).getLocation(), chunk.getBlock(15, placeYBlockAt1515, 15).getLocation());

		for (Location loc : locs) {
			loc.getBlock().setType(Material.TORCH);
		}
	}
}
