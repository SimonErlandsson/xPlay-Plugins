package me.Simonsigge.xPlayDonator.Main;

import java.sql.SQLException;

import me.Simonsigge.xPlayDonator.Boosters.BoosterHandler;
import me.Simonsigge.xPlayDonator.Commands.BoosterAdder;
import me.Simonsigge.xPlayDonator.Commands.Donator;
import me.Simonsigge.xPlayDonator.Commands.DonatorAdmin;
import me.Simonsigge.xPlayDonator.Cosmetics.CosmeticsHandler;
import me.Simonsigge.xPlayDonator.Events.PlayerListener;
import me.Simonsigge.xPlayDonator.ExtraFeatures.FeatureManager;
import me.Simonsigge.xPlayDonator.GUI.GUIHandler;
import me.Simonsigge.xPlayDonator.Message.MessageAPI;
import me.Simonsigge.xPlayDonator.MySQL.DataHandler;
import me.Simonsigge.xPlayDonator.MySQL.DataListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static MessageAPI msgAPI;
	private static DataHandler dataHandler;
	private static GUIHandler guiHandler;
	private static CosmeticsHandler cosmeticsHandler;
	private static BoosterHandler boosterHandler;
	private static FeatureManager featureManager;
	private static BoosterAdder boosterAdder;
	
	public void onEnable() {
		instance = this;
		msgAPI = new MessageAPI();
		dataHandler = new DataHandler();
		guiHandler = new GUIHandler();
		cosmeticsHandler = new CosmeticsHandler();
		boosterHandler = new BoosterHandler();
		featureManager = new FeatureManager();
		boosterAdder = new BoosterAdder();
		
		getCommand("donator").setExecutor(new Donator());
		getCommand("donatoradmin").setExecutor(new DonatorAdmin());
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new DataListener(), this);
		getServer().getPluginManager().registerEvents(guiHandler.guiListener, this);
		getServer().getPluginManager().registerEvents(boosterHandler.boosterListener, this);
		getServer().getPluginManager().registerEvents(featureManager.chatFeatures, this);

	}
	
	public void onDisable() {
		try {
			dataHandler.dataUtilities.mySQLHandler.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		instance = null;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static MessageAPI getMsgAPI() {
		return msgAPI;
	}
	
	public static DataHandler getDataHandler() {
		return dataHandler;
	}
	
	public static GUIHandler getGUIHandler() {
		return guiHandler;
	}
	
	public static CosmeticsHandler getCosmeticsHandler() {
		return cosmeticsHandler;
	}
	
	public static BoosterHandler getBoosterHandler() {
		return boosterHandler;
	}
	
	public static FeatureManager getFeatureManager() {
		return featureManager;
	}
	
	public static BoosterAdder getBoosterAdder() {
		return boosterAdder;
	}

}
