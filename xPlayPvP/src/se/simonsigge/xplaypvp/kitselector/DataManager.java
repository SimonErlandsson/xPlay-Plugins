package se.simonsigge.xplaypvp.kitselector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import se.simonsigge.xplaypvp.Main;

public class DataManager {

	public DataManager() {
		try {
			if (!Main.getInstance().getDataFolder().exists())
				Main.getInstance().getDataFolder().mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Kits[] getPlayerKits(Player p) {
		if (p.hasPermission("xPlayCore.Staff")) 
			return getAllKits();
		
		File playerfile = new File(Main.getInstance().getDataFolder(), p
				.getUniqueId().toString() + ".yml");

		if (!playerfile.exists()) {
			return null;
		}

		FileConfiguration playerconfig = new YamlConfiguration();

		try {
			playerconfig.load(playerfile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		List<String> currentKits = playerconfig.getStringList("kits");
		List<Kits> listCurrentKits = new ArrayList<Kits>();

		for (String kit : currentKits)
			listCurrentKits.add(getKitByName(kit));

		Kits[] arrayCurrentKits = new Kits[listCurrentKits.size()];
		int index = 0;
		for (Kits value : listCurrentKits) {
			arrayCurrentKits[index] = value;
			index++;
		}

		return arrayCurrentKits;
	}

	private Kits[] getAllKits() {
		Kits[] kits = new Kits[Kits.values().length];

		int i = 0;
		for (Kits kit : Kits.values()) {
			kits[i] = kit;
			i++;
		}

		return kits;
	}

	private Kits getKitByName(String name) {
		for (Kits kit : Kits.values()) {
			if (name.contains(kit.getName()))
				return kit;
		}
		return null;
	}

	public void addKit(Player p, Kits kit) {
		File playerfile = new File(Main.getInstance().getDataFolder(), p
				.getUniqueId().toString() + ".yml");

		if (!playerfile.exists()) {
			try {
				playerfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration playerconfig = new YamlConfiguration();

		try {
			playerconfig.load(playerfile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		List<String> currentKits = playerconfig.getStringList("kits");
		currentKits.add(kit.getName());

		playerconfig.set("kits", currentKits);

		try {
			playerconfig.save(playerfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
