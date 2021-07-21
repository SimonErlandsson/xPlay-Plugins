package net.maartin.plotsystem.Commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.maartin.plotsystem.Configuration;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class AddCommand implements CommandExecutor {
	
	Main main;
	public AddCommand(Main main) {
		this.main = main;
		main.getCommand("add").setExecutor(this);
	}
	
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
		
		if (args.length > 1 || args.length == 0) {
			player.sendMessage(Messages.INVALID_SUBCOMMAND.getMessage());
			return true;
		}
		
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(player.getLocation());
		
		if (playerchunk == null) {
			player.sendMessage(Messages.NOT_OWNER.getMessage());
			return true;
		}
		
		if (!playerchunk.getOwnerUUID().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
			player.sendMessage(Messages.CANNOT_TRUST_ON_OTHERS.getMessage());
			return true;
		}
		
		String trust = args[0];
		
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
}
