package net.vouchs.arbete.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener
{	
	@EventHandler
	public void onEvent(PlayerDropItemEvent event)
	{
		event.setCancelled(true);
	}
}
