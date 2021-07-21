package net.vouchs.arbete.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger
{

	public static void log(Level level, String message)
	{
		if (message == null)
			return;

		switch (level)
		{
			case ERROR:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lERROR&r&8] &f" + message));
				break;
			case WARNING:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lWARNING&r&8] &f" + message));
				break;
			case INFO:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&e&lINFO&r&8] &f" + message));
				break;
			case SUCCESS:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lSUCCESS&r&8] &f" + message));
				break;
		}
	}

	public static void log(Level level, String message, Exception ex)
	{
		if (message == null)
			return;

		switch (level)
		{
			case ERROR:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lERROR&r&8] &f" + message));
				ex.printStackTrace();
				break;
			case WARNING:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lWARNING&r&8] &f" + message));
				ex.printStackTrace();
				break;
			case INFO:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&e&lINFO&r&8] &f" + message));
				ex.printStackTrace();
				break;
			case SUCCESS:
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lSUCCESS&r&8] &f" + message));
				ex.printStackTrace();
				break;
		}

	}

	public enum Level
	{
		ERROR, WARNING, INFO, SUCCESS
	}
}
