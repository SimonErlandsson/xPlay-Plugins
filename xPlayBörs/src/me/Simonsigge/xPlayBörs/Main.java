package me.Simonsigge.xPlayB�rs;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	public void onEnable(){
		instance = this;
	}
	
	public void onDisable(){
		instance = null;
	}
	
	public static Main getInstance(){ return instance;	}

}
