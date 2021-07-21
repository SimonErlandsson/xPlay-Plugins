package net.vouchs.arbete.listeners;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import net.vouchs.arbete.Core;

public class PlayerFish implements Listener
{
	private Core core;

	public PlayerFish(Core core)
	{
		this.core = core;
	}

	@EventHandler
	public void onEvent(PlayerFishEvent event)
	{
		if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY || event.getState() == PlayerFishEvent.State.CAUGHT_FISH)
		{
			if (event.getCaught() instanceof Item)
			{
				event.getCaught().remove();
				event.setExpToDrop(0);

				int max = 90;
				int min = 10;

				core.addMoney(event.getPlayer(), 1.348112642 * ThreadLocalRandom.current().nextInt(min, max + 1));
			}
		}
	}
}