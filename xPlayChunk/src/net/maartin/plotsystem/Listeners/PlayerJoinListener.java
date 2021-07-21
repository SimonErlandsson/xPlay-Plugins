package net.maartin.plotsystem.Listeners;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Objects.PlayerData;

public class PlayerJoinListener implements Listener {
	
	Main main;
	public PlayerJoinListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();

		if (!main.getPlayerConfig().hasYaml(player.getUniqueId().toString())) {
			
			YamlConfiguration config = main.getPlayerConfig().createYaml(player.getUniqueId().toString());
			config.set("Data.Username", player.getName());
			config.set("Data.Chunks", new String[0]);
			
			main.getPlayerConfig().saveYaml(player.getUniqueId().toString(), config);
		
		}else {
			
			YamlConfiguration config = main.getPlayerConfig().getYaml(player.getUniqueId().toString());
			if (!config.getString("Data.Username").equalsIgnoreCase(player.getName())) {
				config.set("Data.Username", player.getName());
				main.getPlayerConfig().saveYaml(player.getUniqueId().toString(), config);
			}
		}
		new PlayerData(player.getUniqueId());	
	}
}
