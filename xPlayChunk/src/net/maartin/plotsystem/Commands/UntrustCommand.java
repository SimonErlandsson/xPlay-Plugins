package net.maartin.plotsystem.Commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maartin.plotsystem.Configuration;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class UntrustCommand implements CommandExecutor {
	
	Main main;
	public UntrustCommand(Main main) {
		this.main = main;
		main.getCommand("untrust").setExecutor(this);
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
			player.sendMessage(Messages.CANNOT_REMOVE_ON_OTHERS.getMessage());
			return true;
		}
		
		String remove = args[0];
		
		if (!remove.equalsIgnoreCase("*")) {
			OfflinePlayer offline = Bukkit.getOfflinePlayer(remove);
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
}
