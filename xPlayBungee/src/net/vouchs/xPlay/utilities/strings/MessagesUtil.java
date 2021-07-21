package net.vouchs.xPlay.utilities.strings;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@SuppressWarnings("deprecation")
public class MessagesUtil
{

	public String colorize(String string)
	{
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public String getPrefix()
	{
		return colorize("&0[&f&lxPlay&0] &f-> &a");
	}

	public String getStaffPrefix()
	{
		return colorize("&f[&c&lxPS&f] &0-> &a");
	}

	public void insufficientRank(ProxiedPlayer player)
	{
		player.sendMessage(getPrefix() + "ßcßo≈tkomst nekad");
	}

	public void sendMessage(ProxiedPlayer player, String message)
	{
		player.sendMessage(getPrefix() + colorize(message));
	}

	public void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(getPrefix() + colorize(message));
	}

	public void broadcast(String message)
	{
		ProxyServer.getInstance().broadcast(getPrefix() + colorize(message));
	}

	public void sendConsoleMessage(String message)
	{
		ProxyServer.getInstance().getConsole().sendMessage(getPrefix() + colorize(message));
	}
	
	public void executionError(CommandSender sender)
	{
		if (sender instanceof ProxiedPlayer)
		{
			sender.sendMessage(getPrefix() + "You may not execute this command as player.");
		}
		else
		{
			sender.sendMessage(getPrefix() + "You may not execute this command as console.");
		}
	}

	private final static int CENTER_PX = 154;

	public String sendCenteredMessage(ProxiedPlayer player, String message)
	{
		if (message == null || message.equals(""))
			player.sendMessage("");
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray())
		{
			if (c == 'ß')
			{
				previousCode = true;
				continue;
			}
			else if (previousCode == true)
			{
				previousCode = false;
				if (c == 'l' || c == 'L')
				{
					isBold = true;
					continue;
				}
				else isBold = false;
			}
			else
			{
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate)
		{
			sb.append(" ");
			compensated += spaceLength;
		}
		return (sb.toString() + message);
	}

}
