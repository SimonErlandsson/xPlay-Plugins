package net.vouchs.arbete.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vouchs.arbete.Core;

public class BoosterCommand implements CommandExecutor
{
	private Core core;

	public BoosterCommand(Core core)
	{
		this.core = core;
		core.getCommand("booster").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{

		if (sender instanceof Player)
		{
			sender.sendMessage("�cEndast konsolen kan utf�ra detta kommando.");
			return false;
		}

		if (args.length < 1)
		{
			sender.sendMessage("�cKorrekt anv�ndning av detta kommando �r f�ljande: /booster [on/off] [player]");
			return false;
		}
		
		if (args[0].equalsIgnoreCase("on"))
		{
			if (core.isBoosterEnabled()) {
				sender.sendMessage("�cEn booster �r redan aktiv.");
				return false;
			}
			
			core.setBoosterEnabled(true);
			core.setBoosterHost(args[1]);
			
			sender.sendMessage("�aDu har aktiverat booster.");
			return true;
		}
		else if (args[0].equalsIgnoreCase("off"))
		{
			
			core.setBoosterEnabled(false);
			core.setBoosterHost(null);
			
			sender.sendMessage("�cDu har avaktiverat booster.");
			return true;
		}
		else
		{
			sender.sendMessage("�cKorrekt anv�ndning av detta kommando �r f�ljande: /booster [on/off]");
			return false;
		}
	}
}
