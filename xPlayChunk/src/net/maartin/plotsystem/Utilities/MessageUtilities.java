package net.maartin.plotsystem.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.PluginSettings;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class MessageUtilities {
	
	Main main = Main.getPlugin(Main.class);
	
	public void sendHelpMessage(Player player) {
		player.sendMessage(
				"§2§lChunk-Hjälp §f(§aSida 1/1§f)\n" +
				"§f/chunk claim §8- §7Köp den chunken du befinner dig i\n" +
				"§f/chunk forsale §a<§fmynt§7|§fcancel§a> §8- §7Sätt din chunk till att vara till salu\n" +
				"§f/chunk info §8- §7Visar information om chunken du befinner dig i\n" +
				"§f/chunk list §8- §7Ger dig en lista över alla chunks du äger\n" +
				"§f/chunk remove §a<§fspelare§a> §8- §7Ta bort en spelare från din chunk\n" +
				"§f/chunk add §a<§fspelare§a> §8- §7Ge en spelare tillåtelse att ändra inom din chunk\n" +
				"§f/chunk sell §8- §7Sälj chunken du befinner dig i (om du äger den)");
	}
	
	public void sendChunkInformation(Player player, PlayerChunk playerchunk) {
		
		String owner = main.getPlayerConfig().getYaml(playerchunk.getOwnerUUID()).getString("Data.Username");
		
		if (player.getUniqueId().toString().equalsIgnoreCase(playerchunk.getOwnerUUID().toString())) {
			owner = "§a" + owner;
		}
		
		StringBuilder sb = new StringBuilder();
		List<String> trustedPlayers = new ArrayList<>();
		String trusted = "";
		
		if (playerchunk.getTrustedPlayers().size() > 0) {
			for (String uuid : playerchunk.getTrustedPlayers()) {
				if (!uuid.equalsIgnoreCase("*")) {
					YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(uuid);
					if (playerconfig != null) {
						String trustedUser = playerconfig.getString("Data.Username");
						trustedPlayers.add(trustedUser);
					}
				}else trustedPlayers.add("*");
			}
			Collections.sort(trustedPlayers);
			for (String trustedUser : trustedPlayers) {
				if (trustedUser.equalsIgnoreCase(player.getName())) {
					sb.append("§a");
				}else if (trustedUser.equalsIgnoreCase("*")) {
					sb.append("§5");
				}
				sb.append(trustedUser).append("§f, ");
			}
			trusted = sb.toString().substring(0, sb.length() -2);
		}else {
			trusted = "Ingen";
		}
		
		player.sendMessage(
				"§2§lChunk Information\n" +
				"§7ID: §f" + playerchunk.getID() + "\n" +
				"§7Ägare: §f" + owner + "\n" +
				"§7Medlemmar: §f" + trusted
				);
	}
	
	public void sendServerChunkInformation(Player player, PlayerChunk playerchunk) {
		player.sendMessage(
				"§2§lChunk Information\n" +
				"§7ID: §f" + playerchunk.getID() + "\n" +
				"§7Ägare: §5" + "Server");
	}
	
	@SuppressWarnings("deprecation")
	public void sendPlayerInfo(Player sendTo, String player) {
		
		OfflinePlayer offline = Bukkit.getOfflinePlayer(player);
		UUID uuid = offline.getUniqueId();
		
		YamlConfiguration playerconfig = main.getPlayerConfig().getYaml(uuid.toString());
		
		StringBuilder ownedPlotIDS = new StringBuilder();
		String playerName = player;
		
		int ownedPlots = 0;
		
		if (playerconfig != null) {
			playerName = playerconfig.getString("Data.Username");
			
			List<String> plots = playerconfig.getStringList("Data.Chunks");
			ownedPlots = plots.size();
			
			if (plots.size() > 0) {
				for (int i = 0; i < plots.size(); i++) {
					String s = plots.get(i);
					String[] sa = s.split(";");
					boolean isForSale = main.getChunkData().contains("Chunks." + s + ".ForSale");
					
					Chunk chunk = main.getServer().getWorld(PluginSettings.CHUNK_WORLD).getChunkAt(Integer.parseInt(sa[0]), Integer.parseInt(sa[1]));
					Location loc = chunk.getBlock(8, 64, 8).getLocation();
					String locString = "§7(§a" + loc.getBlockX() + "§7, §a" + loc.getBlockY() + "§7, §a" + loc.getBlockZ() + "§7)§f";
					ownedPlotIDS.append(s + " " + locString);
					if (isForSale) ownedPlotIDS.append(" §6(Till salu)§f");
					ownedPlotIDS.append("\n");
				}
			}
			
		}else {
			ownedPlotIDS.append("§cKunde inte hitta någon data för " + player);
		}
		
		int claimed = main.getChunkData().getConfigurationSection("Chunks").getKeys(false).size();
;
		sendTo.sendMessage(
				"§2§lSpelarinfo §7(" + playerName + ")\n" +
		        "§7Ägda chunks: §f" + ownedPlots + "\n" + 
		        ownedPlotIDS.toString() +
		        "§a§lServerinfo\n" + 
		        "§7Totalt claimade chunks: §f" + claimed
		        );
	}
	
	public void sendAdministrativeHelpMessage(Player player) {
		player.sendMessage(
				"§2§lAdministrativ Chunk-Hjälp §f(§aSida 1/1§f)\n" +
				"§f/chunk add §a<§fspelare§a> §8- §7Ge en spelare tillåtelse att ändra inom chunken du befinner dig i\n" +
				"§f/chunk claim §8- §7Köp den chunken du befinner dig i\n" +
				"§f/chunk delete §8- §7Ta bort ägaren av chunken du befinner dig i\n" +
				"§f/chunk forceclaim §a[§fspelare§a] §8- §7Ta över chunken du befinner dig i, antingen till dig eller till en annan spelare\n" +
				"§f/chunk forsale §a<§fmynt§7|§fcancel§a> §8- §7Sätt din chunk till att vara till salu\n" +
				"§f/chunk info §8- §7Visar information om chunken du befinner dig i\n" +
				"§f/chunk list §a[§fspelare§a] §8- §7Ger dig en lista över alla chunks spelaren äger\n" +
				"§f/chunk remove §a<§fspelare§a> §8- §7Ta bort en spelare från din chunk\n" +
				"§f/chunk sell §8- §7Sälj chunken du befinner dig i (om du äger den)\n" +
				"§f/chunk serverclaim §8- §7Ta över chunken du befinner dig i till servern");
	}
}
