package net.maartin.plotsystem.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.maartin.plotsystem.PluginSettings;

public class PlayerData {
	
	private static final List<PlayerData> players = new ArrayList<PlayerData>();

	private final UUID uuid;

	public PlayerData(UUID uuid) {
		this.uuid = uuid;
		players.add(this);
	}
	
	public static PlayerData getPlayer(UUID uuid) {
		for (PlayerData playerData : players)
			if (playerData.getUUID().toString().equals(uuid.toString())) return playerData;
		return null;
	}
	
	public static List<PlayerData> getPlayers() {
		return players;
	}
	
	public static void addPlayer(PlayerData playerData) {
		players.add(playerData);
	}
	
	public static void removePlayer(Player player) {
		players.remove(getPlayer(player.getUniqueId()));
	}

	public String getUsername() {
		return Bukkit.getPlayer(this.uuid).getName();
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
	}

	public boolean isInPlotWorld() {
		if (getPlayer().getWorld().getName().equals(PluginSettings.CHUNK_WORLD))
			return true;
		return false;
	}
}
