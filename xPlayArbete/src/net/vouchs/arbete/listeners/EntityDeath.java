package net.vouchs.arbete.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.vouchs.arbete.Core;

public class EntityDeath implements Listener
{
	private Core core;

	public EntityDeath(Core core)
	{
		this.core = core;
	}

	@EventHandler
	public void onEvent(EntityDeathEvent event)
	{
		event.getDrops().clear();
		event.setDroppedExp(0);

		int money = 0;
		LivingEntity entity = event.getEntity();

		Player player = null;

		if (entity.getKiller() != null)
		{
			player = entity.getKiller();

			if (entity instanceof Animals)
				money = 10;
			else if (entity instanceof Monster)
				money = 20;
		}

		if (player != null)
			core.addMoney(player, 0.5586592179 * money);
	}
}