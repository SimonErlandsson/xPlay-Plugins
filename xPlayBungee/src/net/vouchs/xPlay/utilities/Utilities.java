package net.vouchs.xPlay.utilities;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.vouchs.xPlay.Main;

@SuppressWarnings("deprecation")
public class Utilities
{

	private Main main;

	public Utilities(Main main)
	{
		this.main = main;
	}

	public String getArgs(String[] args, int num)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = num; i < args.length; i++)
		{
			sb.append(args[i]).append(" ");
		}
		return sb.toString().trim();
	}

	public int getRandomNumber(int min, int max)
	{
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

	public void staff(ProxiedPlayer proxiedPlayer, String message)
	{
		for (ServerInfo server : ProxyServer.getInstance().getServers().values())
		{
			for (ProxiedPlayer target : server.getPlayers())
			{
				if (target.hasPermission("xPlay.Staff.Notify"))
				{
					target.sendMessage(main.getMessageUtil().getStaffPrefix() + "§c§o" + proxiedPlayer.getName() + "§e: " + ChatColor.translateAlternateColorCodes('&', message));
				}
			}
		}
	}

	public String listToString(ArrayList<String> list, String spacer)
	{
		return listToString(list.toString(), spacer);
	}

	public String listToString(String list, String spacer)
	{
		return list.replace("[", "").replace("]", "").replace(", ", spacer);
	}

	public String listToString(List<String> list, String spacer, String lastspacer)
	{
		return listToString((ArrayList<String>) list, spacer, lastspacer);
	}

	public String listToString(ArrayList<String> list, String spacer, String lastspacer)
	{
		String r = "";
		if (list.size() == 1)
		{
			r = listToString(list.toString(), spacer);
		}
		else
		{
			for (int i = 0; i < list.size(); i++)
			{
				if (i == list.size() - 1)
				{
					r = r + list.get(i);
				}
				else if (i == list.size() - 2)
				{
					r = r + list.get(i) + lastspacer;
				}
				else
				{
					r = r + list.get(i) + spacer;
				}
			}
		}
		return r;
	}

}
