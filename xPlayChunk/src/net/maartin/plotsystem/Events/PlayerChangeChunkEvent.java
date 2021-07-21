package net.maartin.plotsystem.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.maartin.plotsystem.Objects.PlayerChunk;

public class PlayerChangeChunkEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private final Player player;
	private final PlayerChunk enteredPlayerchunk;
	private final PlayerChunk exitedPlayerchunk;
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public PlayerChangeChunkEvent(Player player, PlayerChunk enteredPlayerchunk, PlayerChunk exitedPlayerchunk) {
		this.player = player;
		this.enteredPlayerchunk = enteredPlayerchunk;
		this.exitedPlayerchunk = exitedPlayerchunk;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public PlayerChunk getEnteredPlayerchunk() {
		return enteredPlayerchunk;
	}
	
	public PlayerChunk getExitedPlayerchunk() {
		return exitedPlayerchunk;
	}
}
