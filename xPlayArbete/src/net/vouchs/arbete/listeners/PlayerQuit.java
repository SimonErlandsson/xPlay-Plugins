package net.vouchs.arbete.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.vouchs.arbete.Core;
import net.vouchs.arbete.object.Account;

public class PlayerQuit implements Listener
{

	private Core core;

	public PlayerQuit(Core core)
	{
		this.core = core;

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		Account account = core.getAccountManager().getAccount(player);
		if (account == null)
			return;

		core.getAccountManager().saveAccount(account);
		core.getAccountManager().removeAccount(account);
	}
}