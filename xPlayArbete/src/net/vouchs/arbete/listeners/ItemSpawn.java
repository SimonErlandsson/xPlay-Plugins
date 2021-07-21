package net.vouchs.arbete.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawn implements Listener
{
	@EventHandler
	public void onEvent(ItemSpawnEvent event)
	{
		event.setCancelled(true);
	}
}