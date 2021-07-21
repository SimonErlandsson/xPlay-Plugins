package net.vouchs.arbete.listeners;

import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHit implements Listener
{
	@EventHandler
	public void onEvent(ProjectileHitEvent event)
	{
		if (event.getEntity() instanceof FishHook && event.getHitEntity() instanceof Player)
			event.getEntity().remove();
	}	
}