package net.maartin.plotsystem.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
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

public class ChunkCommand implements CommandExecutor {
	
	Main main;
	public ChunkCommand(Main main) {
		this.main = main;
		main.getCommand("chunk").setExecutor(this);
	}
	
	public static final Map<Player, String> inConfirmation = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if (!(sender instanceof Player)) 
			return true;
		
		Player player = (Player) sender;
		
		if (!player.hasPermission("chunksystem.default")) {
			player.sendMessage(Messages.NO_PERMISSSION.getMessage());
			return true;
		}
		
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
			
			if (player.hasPermission("chunksystem.admin")) {
				main.getMessageutils().sendAdministrativeHelpMessage(player);
				return true;
			}
			
			main.getMessageutils().sendHelpMessage(player);
			return true;
		}
		
		if (args.length >= 1) {
			
			PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(player.getLocation());
			
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("information")) {
					
					if (playerchunk == null) {
						player.sendMessage(Messages.NO_INFORMATION.getMessage());
						return true;
					}
					
					if (playerchunk.getType() == PlayerChunkType.SERVER) {
						main.getMessageutils().sendServerChunkInformation(player, playerchunk);
						return true;
					}
					
					main.getMessageutils().sendChunkInformation(player, playerchunk);
					return true;
				}
				
				if (args[0].equalsIgnoreCase("list")) {
					main.getMessageutils().sendPlayerInfo(player, player.getName());
					return true;
				}
				
				if (args[0].equalsIgnoreCase("claim") || args[0].equalsIgnoreCase("buy")) {
					
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
							
							if (inConfirmation.containsKey(player))
								inConfirmation.remove(player);
							
							inConfirmation.put(player, playerchunk.getID());
							player.sendMessage(Messages.CONFIRM_PURCHASE.getMessage().replace("%chunkid%", playerchunk.getID()).replace("%price%", price + ""));
							
							new BukkitRunnable() {
								
								@Override
								public void run() {
									if (inConfirmation.containsKey(player))
										inConfirmation.remove(player);
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
					
					}else if (!main.getEconomy().has(player, PluginSettings.CHUNK_PRICE)) {
						
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
				
				if (args[0].equalsIgnoreCase("confirm")) {
					
					if (!inConfirmation.containsKey(player)) {
						player.sendMessage(Messages.NOTHING_TO_CONFIRM.getMessage());
						return true;
					}
					
					playerchunk = PlayerChunk.getPlayerChunkFromID(inConfirmation.get(player));
					
					if (playerchunk == null || !playerchunk.isForSale()) {
						player.sendMessage(Messages.NOT_FOR_SALE_ANYMORE.getMessage());
						return true;
					}
					
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
					
					YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(player.getUniqueId().toString());
					List<String> ownedChunks = playerconfig.getStringList("Data.Chunks");
					
					if (ownedChunks.size() >= PluginSettings.MAX_CHUNKS) {
						player.sendMessage(Messages.REACHED_MAX_CHUNKS.getMessage());
						return true;
					}
					
					try {
						main.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), price);
						main.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(playerchunk.getOwnerUUID())), price);
						
					}catch (Exception e) {
						player.sendMessage("§cError uppstod. Var snäll kontakta en server administrator så fort som möjligt!");
						return true;
					}
					unclaimChunk(playerchunk, false);
					
					PlayerChunk pc = new PlayerChunk(player.getLocation().getChunk(), player.getUniqueId().toString(), PlayerChunkType.PLAYER);
					claimChunk(pc, false);
					
					String previousOwner = main.getPlayerConfig().getYaml(playerchunk.getOwnerUUID()).getString("Data.Username");
					
					player.sendMessage(Messages.BOUGHT_CHUNK_FROM.getMessage().replace("%chunkid%", pc.getID()).replace("%price%", price + "").replace("%target%", previousOwner));
					
					if (Bukkit.getOfflinePlayer(UUID.fromString(playerchunk.getOwnerUUID())).isOnline())
						Bukkit.getPlayer(UUID.fromString(playerchunk.getOwnerUUID())).sendMessage(Messages.PLAYER_BOUGHT_YOUR_CHUNK.getMessage().replace("%price%", price + "").replace("%target%", player.getName()));
					
					return true;
				}
				
				if (args[0].equalsIgnoreCase("forceclaim")) {
					
					if (!player.hasPermission("chunksystem.admin")) {
						player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
						return true;
					}
					
					if (playerchunk != null)
						unclaimChunk(playerchunk, true);
					
					Chunk chunk = player.getLocation().getChunk();
					PlayerChunk pc = new PlayerChunk(chunk, player.getUniqueId().toString(), PlayerChunkType.PLAYER);
					
					claimChunk(pc, false);
					
					main.getChunkutils().setTorchesInCorners(chunk);
					
					player.sendMessage(Messages.FORCE_CLAIMED_CHUNK.getMessage().replace("%chunkid%", pc.getID()));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("serverclaim")) {
					
					if (!player.hasPermission("chunksystem.admin")) {
						player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
						return true;
					}
					
					if (playerchunk != null)
						unclaimChunk(playerchunk, true);
					
					Chunk chunk = player.getLocation().getChunk();
					PlayerChunk pc = new PlayerChunk(chunk, "SERVER", PlayerChunkType.SERVER);
					
					claimServerChunk(pc);
					
					main.getChunkutils().setTorchesInCorners(chunk);
					
					player.sendMessage(Messages.CLAIMED_SERVERCHUNK.getMessage().replace("%chunkid%", pc.getID()));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("unclaim") || args[0].equalsIgnoreCase("sell")) {
					
					if (playerchunk == null) {
						player.sendMessage(Messages.NOT_OWNER.getMessage());
						return true;
					}
					
					if (!playerchunk.getOwnerUUID().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
						player.sendMessage(Messages.NOT_OWNER.getMessage());
						return true;
					}
					
					boolean isFirstChunk = false;
					
					YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(player.getUniqueId().toString());
					List<String> ownedChunks = playerconfig.getStringList("Data.Chunks");
					
					if (ownedChunks.size() == 1)
						isFirstChunk = true;
					
					main.getChunkData().set("Chunks." + playerchunk.getID(), null);
					Configuration.save("chunkData");

					ownedChunks.remove(playerchunk.getID());
					playerconfig.set("Data.Chunks", ownedChunks);
					main.getPlayerConfig().saveYaml(player.getUniqueId().toString(), playerconfig);
					
					if (isFirstChunk) {
						player.sendMessage(Messages.UNCLAIMED_FREE_CHUNK.getMessage().replace("%price%", PluginSettings.CHUNK_PRICE/2 + ""));
					}else {
						main.getEconomy().depositPlayer(player, PluginSettings.CHUNK_PRICE/2);
						player.sendMessage(Messages.UNCLAIMED_CHUNK.getMessage().replace("%price%", PluginSettings.CHUNK_PRICE/2 + ""));
					}
					playerchunk.remove();
					return true;
				}
				
				if (args[0].equalsIgnoreCase("delete")) {
					
					if (!player.hasPermission("chunksystem.admin")) {
						player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
						return true;
					}
					
					if (playerchunk == null) {
						player.sendMessage(Messages.NOT_CLAIMED.getMessage());
						return true;
					}
					
					player.sendMessage(Messages.FORCE_UNCLAIMED_CHUNK.getMessage().replace("%chunkid%", playerchunk.getID()));
					
					unclaimChunk(playerchunk, true);
					return true;
				}
				
				player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
				return true;
			}
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("trust") || args[0].equalsIgnoreCase("add")) {
					
					if (playerchunk == null) {
						player.sendMessage(Messages.NOT_OWNER.getMessage());
						return true;
					}
					
					if (!playerchunk.getOwnerUUID().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
						player.sendMessage(Messages.CANNOT_TRUST_ON_OTHERS.getMessage());
						return true;
					}
					
					String trust = args[1];
					
					if (!trust.equalsIgnoreCase("*")) {
						OfflinePlayer offline = Bukkit.getOfflinePlayer(trust);
						YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(offline.getUniqueId().toString());

						if (playerconfig == null) {
							player.sendMessage(Messages.COULD_NOT_FIND_PLAYER.getMessage());
							return true;
						}

						UUID uuid = offline.getUniqueId();
						
						trust = uuid.toString();
						
						if (uuid.toString().equalsIgnoreCase(player.getUniqueId().toString())) {
							player.sendMessage(Messages.CANNOT_TRUST_YOURSELF.getMessage());
							return true;
						}
					}
					
					List<String> trustedPlayers = main.getChunkData().getStringList("Chunks." + playerchunk.getID() + ".Trusted");
					
					if (trustedPlayers.contains(trust)) {
						player.sendMessage(Messages.ALREADY_TRUSTED.getMessage());
						return true;
					}
					
					playerchunk.addTrustedPlayer(trust);
					trustedPlayers.add(trust);
					main.getChunkData().set("Chunks." + playerchunk.getID() + ".Trusted", trustedPlayers);
					Configuration.save("chunkData");
					
					String trusted = "null";
					if (!trust.equalsIgnoreCase("*")) {
						trusted = main.getPlayerConfig().getYaml(trust).getString("Data.Username");
					}else trusted = "*";
					
					player.sendMessage(Messages.ADDED_TRUSTED.getMessage().replace("%target%", trusted));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("untrust")) {
					
					if (playerchunk == null) {
						player.sendMessage(Messages.NOT_OWNER.getMessage());
						return true;
					}
					
					if (!playerchunk.getOwnerUUID().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
						player.sendMessage(Messages.CANNOT_REMOVE_ON_OTHERS.getMessage());
						return true;
					}
					
					String remove = args[1];
					
					if (!remove.equalsIgnoreCase("*")) {
						OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
						UUID uuid = offline.getUniqueId();
						remove = uuid.toString();
					}
					
					List<String> trustedPlayers = main.getChunkData().getStringList("Chunks." + playerchunk.getID() + ".Trusted");
					
					if (!trustedPlayers.contains(remove)) {
						player.sendMessage(Messages.IS_NOT_TRUSTED.getMessage());
						return true;
					}
					
					playerchunk.removeTrustedPlayer(remove);
					trustedPlayers.remove(remove);
					
					main.getChunkData().set("Chunks." + playerchunk.getID() + ".Trusted", trustedPlayers);
					Configuration.save("chunkData");
					
					String removed = "null";
					if (!remove.equalsIgnoreCase("*")) {
						removed = main.getPlayerConfig().getYaml(remove).getString("Data.Username");
					}else removed = "*";
					
					player.sendMessage(Messages.REMOVED_TRUSTED.getMessage().replace("%target%", removed));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("forceclaim")) {
					
					if (!player.hasPermission("chunksystem.admin")) {
						player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
						return true;
					}
					
					OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
					YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(offline.getUniqueId().toString());
					
					if (playerconfig == null) {
						player.sendMessage(Messages.COULD_NOT_FIND_PLAYER.getMessage());
						return true;
					}
					
					if (playerchunk != null)
						unclaimChunk(playerchunk, true);
					
					Chunk chunk = player.getLocation().getChunk();
					PlayerChunk pc = new PlayerChunk(chunk, offline.getUniqueId().toString(), PlayerChunkType.PLAYER);
					
					claimChunk(pc, false);
					
					main.getChunkutils().setTorchesInCorners(chunk);
					
					String target = main.getPlayerConfig().getYaml(offline.getUniqueId().toString()).getString("Data.Username");
					
					player.sendMessage(Messages.FORCE_CLAIMED_CHUNK_TO_PLAYER.getMessage().replace("%chunkid%", pc.getID()).replace("%target%", target));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("list")) {
					
					if (!player.hasPermission("chunksystem.admin")) {
						player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
						return true;
					}
					
					OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
					YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(offline.getUniqueId().toString());
					
					if (playerconfig == null) {
						player.sendMessage(Messages.COULD_NOT_FIND_PLAYER.getMessage());
						return true;
					}
					
					main.getMessageutils().sendPlayerInfo(player, playerconfig.getString("Data.Username"));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("forsale")) {
					
					if (playerchunk == null) {
						player.sendMessage(Messages.NOT_OWNER.getMessage());
						return true;
					}
					
					if (!playerchunk.getOwnerUUID().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
						player.sendMessage(Messages.NOT_OWNER.getMessage());
						return true;
					}
					
					if (playerchunk.isForSale()) {
						
						if (args[1].equalsIgnoreCase("cancel")) {
							player.sendMessage(Messages.CANCELED_SELLING.getMessage());
							playerchunk.setForSale(false);
							main.getChunkData().set("Chunks." + playerchunk.getID() + ".ForSale", null);
							Configuration.save("chunkData");
							return true;
						}
						player.sendMessage(Messages.ALREADY_FOR_SALE.getMessage());
						return true;
					}
					
					YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(player.getUniqueId().toString());
					List<String> ownedChunks = playerconfig.getStringList("Data.Chunks");
					
					if (ownedChunks.size() <= 1) {
						player.sendMessage(Messages.NOT_ENOUGH_CHUNKS.getMessage());
						return true;
					}
					
					try {
						Integer.parseInt(args[1]);
					}catch(Exception e) {
						
						if (args[1].equalsIgnoreCase("cancel")) {
							player.sendMessage(Messages.NOT_FOR_SALE.getMessage());
							return true;
						}
						player.sendMessage(Messages.HAS_TO_BE_NUMBER.getMessage());
						return true;
					}
					
					int price = (int) Integer.parseInt(args[1]);
					
					if (price > 10000000) {
						player.sendMessage(Messages.PRICE_IS_TOO_HIGH.getMessage());
						return true;
					}
					
					if (price < 5000) {
						player.sendMessage(Messages.PRICE_IS_TOO_LOW.getMessage());
						return true;
					}
					
					player.sendMessage(Messages.NOW_SELLING.getMessage().replace("%price%", price + ""));
					playerchunk.setForSale(true);
					main.getChunkData().set("Chunks." + playerchunk.getID() + ".ForSale", price);
					Configuration.save("chunkData");
					return true;
				}
			}
			player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
		}
		return true;
	}
	
	public void claimServerChunk(PlayerChunk playerchunk) {
		
		main.getChunkData().set("Chunks." + playerchunk.getID() + ".Owner", "SERVER");
		Configuration.save("chunkData");
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
	
	public void unclaimChunk(PlayerChunk playerchunk, boolean returnMoney) {
		
		YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(playerchunk.getOwnerUUID());
		
		if (playerconfig != null) {
			List<String> ownedChunks = playerconfig.getStringList("Data.Chunks");
			ownedChunks.remove(playerchunk.getID());
			playerconfig.set("Data.Chunks", ownedChunks);
			main.getPlayerConfig().saveYaml(playerchunk.getOwnerUUID(), playerconfig);
		}
		
		main.getChunkData().set("Chunks." + playerchunk.getID(), null);
		Configuration.save("chunkData");
		
		if (returnMoney && playerconfig != null)
			main.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(playerchunk.getOwnerUUID())), PluginSettings.CHUNK_PRICE/2);
		
		playerchunk.remove();
	}
}
