package net.maartin.plotsystem.Objects;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

import net.maartin.plotsystem.Main;

public class PlayerConfig {
	Main main = Main.getPlugin(Main.class);

	private File playerdata = new File(main.getDataFolder() + File.separator + "Data");

	public PlayerConfig() {
		if (!playerdata.exists()) playerdata.mkdirs();
	}

	public boolean hasYaml(String uuid) {
		return getYaml(uuid) != null;
	}

	public YamlConfiguration getYaml(String uuid) {
		File file = new File(playerdata, uuid + ".yml");
		
		if (!file.exists()) return null;
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config;
	}

	public YamlConfiguration createYaml(String uuid) {
		File file = new File(playerdata, uuid + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		saveYaml(uuid, config);
		return config;
	}

	public boolean saveYaml(String uuid, YamlConfiguration config) {
		File file = new File(playerdata, uuid + ".yml");
		try {
			config.save(file);
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
