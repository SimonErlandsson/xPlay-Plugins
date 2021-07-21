package net.maartin.plotsystem.Commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.maartin.plotsystem.Configuration;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.PluginSettings;
import net.maartin.plotsystem.Objects.PlayerChunk;
import net.maartin.plotsystem.Objects.PlayerChunk.PlayerChunkType;

public class ClaimCommand implements CommandExecutor {
	
	Main main;
	public ClaimCommand(Main main) {
		this.main = main;
		main.getCommand("claim").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if (!(sender instanceof Player)) 
			return true;
		
		Player player = (Player) sender;
		
		if (!player.hasPermission("chunksystem.default")) {
			player.sendMessage(Messages.NO_PERMISSSION.getMessage());
			return true;
		}
		
		if (args.length > 0) {
			player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
			return true;
		}
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(player.getLocation());
		
		YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(player.getUniqueId().toString());
		List<String> ownedChunks = playerconfig.getStringList("Data.Chunks");
		
		if (playerchunk != null) {
			
			if (playerchunk.isForSale()) {
				
				int price = main.getChunkData().getInt("Chunks." + playerchunk.getID() + ".ForSale");
				
				if (playerchunk.getOwnerUUID().equals(player.getUniqueId().toString())) {
					player.sendMessage(Messages.CANNOT_BUY_FROM_YOURSELF.getMessage());
					return true;
				}
				
				YamlConfiguration sellerconfig = main.getPlayerConfig().getYaml(playerchunk.getOwnerUUID());
				List<String> sellerOwnedChunks = sellerconfig.getStringList("Data.Chunks");
				
				if (sellerOwnedChunks.size() <= 1) {
					player.sendMessage(Messages.SELLER_HAS_NOT_ENOUGH_CHUNKS.getMessage());
					return true;
				}
				
				if (!main.getEconomy().has(player, price)) {
					player.sendMessage(Messages.CANNOT_AFFORD_SALECHUNK.getMessage());
					return true;
				}
				
				if (ownedChunks.size() >= PluginSettings.MAX_CHUNKS) {
					player.sendMessage(Messages.REACHED_MAX_CHUNKS.getMessage());
					return true;
				}
				
				if (ChunkCommand.inConfirmation.containsKey(player))
					ChunkCommand.inConfirmation.remove(player);
				
				ChunkCommand.inConfirmation.put(player, playerchunk.getID());
				player.sendMessage(Messages.CONFIRM_PURCHASE.getMessage().replace("%chunkid%", playerchunk.getID()).replace("%price%", price + ""));
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (ChunkCommand.inConfirmation.containsKey(player))
							ChunkCommand.inConfirmation.remove(player);
					}
				}.runTaskLater(main, 20 * 60);
				
				return true;
			}
			
			player.sendMessage(Messages.ALREADY_CLAIMED.getMessage());
			return true;
		}
		
		boolean isFirstChunk = false;
		
		if (ownedChunks.size() == 0) {
			isFirstChunk = true;
		}else 
			if (!main.getEconomy().has(player, PluginSettings.CHUNK_PRICE)) {
			
				player.sendMessage(Messages.CANNOT_AFFORD.getMessage().replace("%price%", PluginSettings.CHUNK_PRICE + ""));
				return true;
		}
		
		if (ownedChunks.size() >= PluginSettings.MAX_CHUNKS) {
			player.sendMessage(Messages.REACHED_MAX_CHUNKS.getMessage());
			return true;
		}
		
		Chunk chunk = player.getLocation().getChunk();
		PlayerChunk pc = new PlayerChunk(chunk, player.getUniqueId().toString(), PlayerChunkType.PLAYER);
		
		claimChunk(pc, false);
		
		main.getChunkutils().setTorchesInCorners(chunk);
		
		if (isFirstChunk) {
			player.sendMessage(Messages.CLAIMED_FREE_CHUNK.getMessage().replace("%chunkid%", pc.getID()).replace("%price%", PluginSettings.CHUNK_PRICE + ""));
		}else {
			main.getEconomy().withdrawPlayer(player, PluginSettings.CHUNK_PRICE);
			player.sendMessage(Messages.CLAIMED_CHUNK.getMessage().replace("%chunkid%", pc.getID()).replace("%price%", PluginSettings.CHUNK_PRICE + ""));
		}
		return true;
	}
	
	public void claimChunk(PlayerChunk playerchunk, boolean chargeMoney) {
		
		YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(playerchunk.getOwnerUUID());
		List<String> ownedChunks = playerconfig.getStringList("Data.Chunks");
		ownedChunks.add(playerchunk.getID());
		playerconfig.set("Data.Chunks", ownedChunks);
		main.getPlayerConfig().saveYaml(playerchunk.getOwnerUUID(), playerconfig);
		
		main.getChunkData().set("Chunks." + playerchunk.getID() + ".Owner", playerchunk.getOwnerUUID());
		main.getChunkData().set("Chunks." + playerchunk.getID() + ".Trusted", new String[0]);
		Configuration.save("chunkData");
		
		if (chargeMoney)
			main.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(UUID.fromString(playerchunk.getOwnerUUID())), PluginSettings.CHUNK_PRICE);
	}
}
