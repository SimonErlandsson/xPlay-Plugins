package net.maartin.plotsystem.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import net.maartin.plotsystem.PluginSettings;

public class PlayerChunk {
	
	private static final List<PlayerChunk> claimedChunks = new ArrayList<>();
	
	private boolean forSale = false;
	
	private final String ownerUUID;
	private final String ID;
	private final PlayerChunkType type;
	private final List<String> trustedPlayers = new ArrayList<String>();
	
	public PlayerChunk(String ID, String ownerUUID, PlayerChunkType type) {
		
		this.ID = ID;
		this.ownerUUID = ownerUUID;
		this.type = type;
		
		claimedChunks.add(this);
	}
	
	public PlayerChunk(Chunk chunk, String ownerUUID, PlayerChunkType type) {
		
		this.ID = getIDOfChunk(chunk);
		this.ownerUUID = ownerUUID;
		this.type = type;
		
		claimedChunks.add(this);
	}

	public String getOwnerUUID() {
		return this.ownerUUID;
	}

	public List<String> getTrustedPlayers() {
		return this.trustedPlayers;
	}
	
	public void addTrustedPlayer(String uuid) {
		this.trustedPlayers.add(uuid);
	}
	
	public void removeTrustedPlayer(String uuid) {
		this.trustedPlayers.remove(uuid);
	}
	
	public static boolean isPlayerChunk(Chunk chunk) {
		if (getPlayerChunkAt(chunk.getBlock(0, 0, 0).getLocation()) != null) return true;
		return false;
	}
	
	public static List<PlayerChunk> getClaimedChunks() {
		return claimedChunks;
	}
	
	public static PlayerChunk getPlayerChunkAt(Location loc) {
		for (PlayerChunk c : claimedChunks)
			if (c.getID().equals(getIDOfChunk(loc.getChunk()))) return c;
		return null;
	}
	
	public static Chunk getChunkFromPlayerChunk(PlayerChunk playerchunk) {
		String[] splitter = playerchunk.getID().split(";");
		return Bukkit.getServer().getWorld(PluginSettings.CHUNK_WORLD).getChunkAt(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]));
	}
	
	public static PlayerChunk getPlayerChunkFromID(String ID) {
		for (PlayerChunk playerchunk : claimedChunks)
			if (playerchunk.getID().equalsIgnoreCase(ID)) return playerchunk;
		return null;
	}
	
	private static String getIDOfChunk(Chunk chunk) {
		return chunk.getX() + ";" + chunk.getZ();
	}
	
	public void remove() {
		claimedChunks.remove(this);
	}

	public String getID() {
		return this.ID;
	}
	
	public PlayerChunkType getType() {
		return this.type;
	}

	public boolean isForSale() {
		return forSale;
	}

	public void setForSale(boolean isForSale) {
		this.forSale = isForSale;
	}

	public static enum PlayerChunkType {
		PLAYER,
		SERVER,
		UNKNOWN;
	}
}