package net.maartin.plotsystem.Listeners.Chunk;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class InteractChunkListener implements Listener {

	Main main;
	public InteractChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	private final List<Material> deniedClickMaterials = Arrays.asList(Material.CHEST, Material.TRAPPED_CHEST, Material.ANVIL, Material.BREWING_STAND, Material.BED_BLOCK, 
			Material.DISPENSER, Material.DROPPER, Material.HOPPER, Material.FURNACE, Material.JUKEBOX, Material.NOTE_BLOCK, Material.TNT, Material.DAYLIGHT_DETECTOR,
			Material.DAYLIGHT_DETECTOR_INVERTED, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON,
			Material.BEACON, Material.CAULDRON, Material.FLOWER_POT, Material.ENDER_PORTAL_FRAME, Material.LEVER, Material.STONE_BUTTON, Material.BURNING_FURNACE);

	private final List<Material> minecarts = Arrays.asList(Material.MINECART, Material.COMMAND_MINECART, Material.EXPLOSIVE_MINECART, Material.HOPPER_MINECART, 
			Material.POWERED_MINECART, Material.STORAGE_MINECART);

	private final List<Material> boats = Arrays.asList(Material.BOAT, Material.BOAT_ACACIA, Material.BOAT_BIRCH, Material.BOAT_DARK_OAK, 
			Material.BOAT_JUNGLE, Material.BOAT_SPRUCE);

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (player.hasPermission("chunksystem.bypass.interact")) return;
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Block block = event.getClickedBlock();

			PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(block.getLocation());
			
			if (playerchunk != null) {

				if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
				if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
				if (playerchunk.getTrustedPlayers().contains("*")) return;

				if (player.getInventory().getItemInMainHand().getType() == Material.ARMOR_STAND || player.getInventory().getItemInOffHand().getType() == Material.ARMOR_STAND) {

					if (event.getHand() == EquipmentSlot.HAND)
						player.sendMessage(Messages.CLAIMED_DENY.getMessage());

					event.setCancelled(true);
					return;
				}

				for (Material mat : boats) {

					if (player.getInventory().getItemInMainHand().getType() == mat || player.getInventory().getItemInOffHand().getType() == mat) {

						if (event.getHand() == EquipmentSlot.HAND)
							player.sendMessage(Messages.CLAIMED_DENY.getMessage());

						event.setCancelled(true);
						return;
					}
				}

				for (Material mat : minecarts) {

					if (player.getInventory().getItemInMainHand().getType() == mat || player.getInventory().getItemInOffHand().getType() == mat) {

						Block lookingAt = ((LivingEntity)player).getTargetBlock((Set<Material>) null, 10);

						if (lookingAt.getType() == Material.RAILS || lookingAt.getType() == Material.ACTIVATOR_RAIL || lookingAt.getType() == Material.DETECTOR_RAIL ||
								lookingAt.getType() == Material.POWERED_RAIL) {

							if (event.getHand() == EquipmentSlot.HAND)
								player.sendMessage(Messages.CLAIMED_DENY.getMessage());

							event.setCancelled(true);
							return;
						}
					}
				}

				for (Material mat : deniedClickMaterials) {

					if (block.getType() == mat || block.getType().name().contains("SHULKER_BOX")) {

						event.setCancelled(true);

						if (event.getHand() == EquipmentSlot.HAND)
							player.sendMessage(Messages.CLAIMED_DENY.getMessage());
						return;
					}
				}
				return;
			}

			if (player.getInventory().getItemInMainHand().getType() == Material.ARMOR_STAND || player.getInventory().getItemInOffHand().getType() == Material.ARMOR_STAND) {

				if (event.getHand() == EquipmentSlot.HAND)
					player.sendMessage(Messages.WILDERNESS_DENY.getMessage());

				event.setCancelled(true);
				return;
			}

			for (Material mat : boats) {

				if (player.getInventory().getItemInMainHand().getType() == mat || player.getInventory().getItemInOffHand().getType() == mat) {

					if (event.getHand() == EquipmentSlot.HAND)
						player.sendMessage(Messages.WILDERNESS_DENY.getMessage());

					event.setCancelled(true);
					return;
				}
			}

			for (Material mat : minecarts) {

				if (player.getInventory().getItemInMainHand().getType() == mat || player.getInventory().getItemInOffHand().getType() == mat) {

					Block lookingAt = ((LivingEntity)player).getTargetBlock((Set<Material>) null, 10);

					if (lookingAt.getType() == Material.RAILS || lookingAt.getType() == Material.ACTIVATOR_RAIL || lookingAt.getType() == Material.DETECTOR_RAIL ||
							lookingAt.getType() == Material.POWERED_RAIL) {

						if (event.getHand() == EquipmentSlot.HAND)
							player.sendMessage(Messages.WILDERNESS_DENY.getMessage());

						event.setCancelled(true);
						return;
					}
				}
			}

			for (Material mat : deniedClickMaterials) {

				if (block.getType() == mat || block.getType().name().contains("SHULKER_BOX")) {

					event.setCancelled(true);

					if (event.getHand() == EquipmentSlot.HAND)
						player.sendMessage(Messages.WILDERNESS_DENY.getMessage());
					return;
				}
			}
		}
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			
			Block clicked = event.getClickedBlock();
			Block faced = clicked.getRelative(event.getBlockFace());
			
			if (faced != null && faced.getType() == Material.FIRE) {
				PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(faced.getLocation());
				
				if (playerchunk != null) {
					
					if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
					if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
					if (playerchunk.getTrustedPlayers().contains("*")) return;
					
					player.sendMessage(Messages.CLAIMED_DENY.getMessage());
					event.setCancelled(true);
					player.sendBlockChange(faced.getLocation(), Material.FIRE, (byte)faced.getData());
					return;
				}
				player.sendMessage(Messages.WILDERNESS_DENY.getMessage());
				event.setCancelled(true);
			}
		}
		
		if (event.getAction() == Action.PHYSICAL) {
			
			Block block = event.getClickedBlock();
			
			if (block == null) return; 
			
			PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(block.getLocation());
			
			if (playerchunk != null) {
				
				if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
				if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
				if (playerchunk.getTrustedPlayers().contains("*")) return;
				if (block.getType() != Material.SOIL) return;
				
				player.sendMessage(Messages.CLAIMED_DENY.getMessage());
				event.setCancelled(true);
				return;
			}
			
			player.sendMessage(Messages.WILDERNESS_DENY.getMessage());
			event.setCancelled(true);
		}
	}
}
