package net.maartin.plotsystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.maartin.plotsystem.Handlers.ChunkHandler;
import net.maartin.plotsystem.Objects.PlayerConfig;
import net.maartin.plotsystem.Objects.PlayerData;
import net.maartin.plotsystem.Utilities.ChunkUtilities;
import net.maartin.plotsystem.Utilities.MessageUtilities;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private ChunkHandler chunkHandler;

	private PlayerConfig playerConfig;
	private Configuration.RConfig chunkData;
	
	private MessageUtilities messageutils;
	private ChunkUtilities chunkutils;
	
	public Economy econ = null;
	
	@Override
	public void onEnable() {
		initializeConfigurations();
		
		this.playerConfig = new PlayerConfig();
		this.chunkHandler = new ChunkHandler();
		this.messageutils = new MessageUtilities();
		this.chunkutils = new ChunkUtilities();
		
		if (Bukkit.getOnlinePlayers().size() >= 1)
			for (Player player : Bukkit.getOnlinePlayers())
				new PlayerData(player.getUniqueId());
		
		this.chunkHandler.load();
		
		new InitializePlugin(this);
		
		setupEconomy();
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		
		if (economyProvider != null)
			econ = economyProvider.getProvider();
		
		return (econ != null);
	}

	public PlayerConfig getPlayerConfig() {
		return this.playerConfig;
	}

	public FileConfiguration getChunkData() {
		return this.chunkData;
	}
	
	public ChunkHandler getChunkHandler() {
		return chunkHandler;
	}

	private void initializeConfigurations() {
		try {
			Configuration.registerConfig("chunkData", "ChunkData.yml", this);

			Configuration.loadAll();
			Configuration.saveAll();

			this.chunkData = Configuration.getConfig("chunkData");
		}catch (Exception exception) {
			getServer().getConsoleSender().sendMessage("§6ChunkSystem §cAn error occured whilst loading the configurationfiles...");
			exception.printStackTrace();
		}
	}

	public MessageUtilities getMessageutils() {
		return messageutils;
	}

	public ChunkUtilities getChunkutils() {
		return chunkutils;
	}

	public Economy getEconomy() {
		return this.econ;
	}
}
