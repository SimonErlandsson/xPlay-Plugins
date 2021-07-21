package net.maartin.plotsystem.Listeners.Chunk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class StructureGrowChunkListener implements Listener {
	
	Main main;
	public StructureGrowChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onGrow(StructureGrowEvent event) {
		
		List<BlockState> blockStates = event.getBlocks();
		Map<Location, Material> original = new HashMap<>();
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(event.getLocation());
		
		for (int i = 0; i < blockStates.size(); i++) {
			
			PlayerChunk pc = PlayerChunk.getPlayerChunkAt(blockStates.get(i).getLocation());
			
			if (pc != playerchunk) {
				
				if (pc != null && playerchunk != null && pc.getOwnerUUID().toString().equalsIgnoreCase(playerchunk.getOwnerUUID().toString())) continue;
				
				Material originalMaterial = blockStates.get(i).getBlock().getType();
				Location loc = blockStates.get(i).getLocation();
				
				original.put(loc, originalMaterial);
			}
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Location loc : original.keySet())
					loc.getBlock().setType(original.get(loc));
				original.clear();
			}
		}.runTaskLater(main, 1);
	}
}
