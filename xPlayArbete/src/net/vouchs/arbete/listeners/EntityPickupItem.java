package net.vouchs.arbete.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItem implements Listener
{
	@EventHandler
	public void onEvent(EntityPickupItemEvent event)
	{
		event.setCancelled(true);
	}
}
