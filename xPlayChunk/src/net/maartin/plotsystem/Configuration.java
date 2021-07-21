package net.maartin.plotsystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration
{

	private static List<RConfig> configs = new ArrayList<>();

	public static boolean registerConfig(String String1, String String2, JavaPlugin plugin) {
		File localFile = new File(plugin.getDataFolder(), String2);
		
		if (!localFile.exists()) {
			
			localFile.getParentFile().mkdirs();
			
			try {
				copy(plugin.getResource(String2), localFile);
			} catch (Exception e) { }
		}
		
		RConfig Config1 = new RConfig(String1, localFile);
		
		for (RConfig Config2 : configs)
			if (Config2.equals(Config1)) return false;
		
		configs.add(Config1);
		return true;
	}

	public static boolean unregisterConfig(String String) {
		return configs.remove(getConfig(String));
	}

	public static RConfig getConfig(String String) {
		
		for (RConfig config : configs)
			if (config.getConfigId().equalsIgnoreCase(String)) return config;
		return null;
	}

	public static boolean save(String String) {
		
		RConfig rConfig = getConfig(String);
		
		if (rConfig == null) return false;
		
		try {
			rConfig.save();
		} catch (Exception e) {
			
			print("An error occurred while saving a config with id " + String);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean saveAll() {
		try {
			
			for (RConfig rConfig : configs)
				rConfig.save();
		}
		catch (Exception e) {
			
			print("An error occurred while saving all configs");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean load(String String) {
		
		RConfig rConfig = getConfig(String);
		
		if (rConfig == null) return false;
		
		try {
			rConfig.load();
		} catch (Exception e) {
			
			print("An error occurred while loading a config with id " + String);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean loadAll() {
		
		try {
			for (RConfig rConfig : configs)
				rConfig.load();
			
		} catch (Exception e) {
			print("An error occurred while loading all configs");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void safeStart(String String) {
		RConfig rConfig = getConfig(String);
		if (rConfig == null) return;
		configs.remove(rConfig);
		configs.add(new RConfig(rConfig.getConfigId(), rConfig.getFile()));
	}

	private static void print(String String) {
		System.out.println("Config: " + String);
	}

	public static class RConfig extends YamlConfiguration {
		private String id;
		private File file;

		public String getConfigId() {
			return this.id;
		}

		public File getFile() {
			return this.file;
		}

		private RConfig(String String, File paramFile) {
			this.id = String;
			this.file = paramFile;
		}

		public void save() {
			try {
				save(this.file);
			} catch (Exception e) { }
		}

		public void load() {
			try {
				load(this.file);
			} catch (Exception e) { }
		}

		public boolean equals(RConfig paramRConfig) {
			return paramRConfig.getConfigId().equalsIgnoreCase(this.id);
		}
	}

	private static void copy(InputStream paramInputStream, File paramFile) {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
			
			byte[] arrayOfByte = new byte['`'];
			
			int i;
			while ((i = paramInputStream.read(arrayOfByte)) > 0)
				localFileOutputStream.write(arrayOfByte, 0, i);
			
			localFileOutputStream.close();
			paramInputStream.close();
		}
		catch (Exception e) { }
	}
}
