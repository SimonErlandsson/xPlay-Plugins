package net.maartin.plotsystem.Commands;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.maartin.plotsystem.Configuration;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.PluginSettings;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class UnclaimCommand implements CommandExecutor {
	
	Main main;
	public UnclaimCommand(Main main) {
		this.main = main;
		main.getCommand("unclaim").setExecutor(this);
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
}
