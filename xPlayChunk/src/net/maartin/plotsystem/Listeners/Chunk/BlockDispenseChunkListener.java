package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class BlockDispenseChunkListener implements Listener {

	Main main;
	public BlockDispenseChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event) {
		
		if (event.getBlock().getType() == Material.DISPENSER) {
			
			ItemStack item = event.getItem();
			
			if (item == null) return; 
			
			if (item.getType() == Material.FIREBALL) {
				event.setCancelled(true);
				return;
			}
			
			BlockFace face = ((Dispenser)event.getBlock().getState().getData()).getFacing();
			
			PlayerChunk dispenserPlayerchunk = PlayerChunk.getPlayerChunkAt(event.getBlock().getLocation());
			PlayerChunk facingPlayerchunk = PlayerChunk.getPlayerChunkAt(event.getBlock().getRelative(face).getLocation());
			
			if (item.getType() == Material.LAVA_BUCKET || item.getType() == Material.WATER_BUCKET || item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.SKULL_ITEM ||
					item.getType() == Material.PUMPKIN || item.getType() == Material.TNT || (item.getType() == Material.INK_SACK && item.getDurability() == 15) ||
					item.getType().name().contains("SHULKER_BOX") || item.getType().name().contains("BOAT")) {
				
				if (dispenserPlayerchunk != facingPlayerchunk) {
					
					if (dispenserPlayerchunk != null && facingPlayerchunk != null && dispenserPlayerchunk.getOwnerUUID().toString().equalsIgnoreCase(facingPlayerchunk.getOwnerUUID().toString())) return;
					
					event.setCancelled(true);
				}
				return;
			}
			
			if (item.getType() == Material.BUCKET) {
				
				if (!event.getBlock().getRelative(face).isLiquid()) return;
				
				if (dispenserPlayerchunk != facingPlayerchunk) {
					
					if (dispenserPlayerchunk != null && facingPlayerchunk != null && dispenserPlayerchunk.getOwnerUUID().toString().equalsIgnoreCase(facingPlayerchunk.getOwnerUUID().toString())) return;
					
					event.setCancelled(true);
				}
			}
		}
	}
}
