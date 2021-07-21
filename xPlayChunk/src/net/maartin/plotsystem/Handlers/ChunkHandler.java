package net.maartin.plotsystem.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerChunk;
import net.maartin.plotsystem.Objects.PlayerChunk.PlayerChunkType;

public class ChunkHandler {

	private final Main main = Main.getPlugin(Main.class);

	public void load() {
		ConfigurationSection section = main.getChunkData().getConfigurationSection("Chunks");
		if (section == null) return;
		if (section.getKeys(false).size() == 0) return;

		for (String chunkID : section.getKeys(false)) {
			
			try {
				if (section.getString(chunkID + ".Owner").equals("SERVER")) {
					new PlayerChunk(chunkID, section.getString(chunkID + ".Owner"), PlayerChunkType.SERVER);
				}else {
					PlayerChunk playerchunk = new PlayerChunk(chunkID, section.getString(chunkID + ".Owner"), PlayerChunkType.PLAYER);
					
					for (String s2 : section.getStringList(chunkID + ".Trusted"))
						playerchunk.addTrustedPlayer(s2);
					
					if (section.contains(chunkID + ".ForSale"))
						playerchunk.setForSale(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Bukkit.getServer().getConsoleSender().sendMessage("§aChunkSystem loaded");
	}
}
