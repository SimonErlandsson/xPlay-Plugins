package net.vouchs.arbete.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.vouchs.arbete.Core;
import net.vouchs.arbete.object.Account;

public class NPCInteractEvent implements Listener
{
	private Core core;

	public NPCInteractEvent(Core core)
	{
		this.core = core;
	}

	@EventHandler
	public void onNPCRightClick(NPCRightClickEvent event)
	{
		Account account = core.getAccountManager().getAccount(event.getClicker());
		event.getClicker().openInventory(core.getUpgradableInventory().getInventory(account));
	}

	@EventHandler
	public void onNPCLeftClick(NPCLeftClickEvent event)
	{
		Account account = core.getAccountManager().getAccount(event.getClicker());
		event.getClicker().openInventory(core.getUpgradableInventory().getInventory(account));
	}
}
