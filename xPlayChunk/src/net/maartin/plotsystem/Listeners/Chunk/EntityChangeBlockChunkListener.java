package net.maartin.plotsystem.Listeners.Chunk;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class EntityChangeBlockChunkListener implements Listener {
	
	private static Map<Entity, PlayerChunk> storeFallingBlock = new HashMap<>();
	
	Main main;
	public EntityChangeBlockChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityChange(EntityChangeBlockEvent event) {
		if (event.getEntityType() != EntityType.FALLING_BLOCK) return;
		
		Entity entity = event.getEntity();
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(entity.getLocation());
		
		if (!storeFallingBlock.containsKey(entity)) {
			
			storeFallingBlock.put(entity, playerchunk);
			
		}else {
			PlayerChunk storedPlayerchunk = storeFallingBlock.get(entity);
			storeFallingBlock.remove(entity);
			
			if (playerchunk != null && storedPlayerchunk != null && playerchunk.getOwnerUUID().toString().equalsIgnoreCase(storedPlayerchunk.getOwnerUUID().toString())) return;
			
			FallingBlock fallingBlock = (FallingBlock) entity;
			
			fallingBlock.setDropItem(false);
			
			if (!(fallingBlock.getMaterial() == Material.ANVIL && fallingBlock.getBlockData() > (short)2))
				entity.getWorld().dropItemNaturally(entity.getLocation(), getDrop(fallingBlock));
			
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack getDrop(FallingBlock fallingBlock) {
		if (fallingBlock == null) return null;
		ItemStack i = new ItemStack(fallingBlock.getMaterial(), 1, (short) fallingBlock.getBlockData());
		return i;
	}
}













