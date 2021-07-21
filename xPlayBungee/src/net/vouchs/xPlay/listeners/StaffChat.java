package net.vouchs.xPlay.listeners;

import java.text.ParseException;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.objects.Player;

public class StaffChat implements Listener
{
	Main main;

	public StaffChat(Main main)
	{
		this.main = main;
		main.getProxy().getPluginManager().registerListener(main, this);
	}

	@EventHandler
	public void onChat(ChatEvent event) throws ParseException
	{
		if (!(event.getSender() instanceof ProxiedPlayer)) { return; }

		ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
		Player player = Main.getInstance().getPlayerHandler().getPlayer(proxiedPlayer.getUniqueId());

		String message = event.getMessage();

		if (proxiedPlayer.hasPermission("xPlay.Command.StaffChat"))
		{
			if (message.startsWith("!") && message.length() > 1)
			{
				event.setCancelled(true);
				Main.getInstance().getUtilities().staff(proxiedPlayer, message.substring(1));
				sendLogToDiscord(player, message.substring(1));
				return;
			}
		}

		if (player.isInStaffChat())
		{
			if (message.startsWith("/"))
			{
				event.setCancelled(false);
			}
			else
			{
				event.setCancelled(true);
				Main.getInstance().getUtilities().staff(proxiedPlayer, message);
				sendLogToDiscord(player, message);
				return;
			}
		}
		
		if (player.isInStaffChat())
			return;
		
		if (proxiedPlayer.hasPermission("xPlay.Command.StaffChat") && event.getMessage().startsWith("!"))
			return;
		
		if (event.getMessage().toLowerCase().contains("@everyone") || event.getMessage().toLowerCase().contains("@here"))
		{
			main.getMessageUtil().sendMessage(proxiedPlayer, "&cDu har inte tillstånd till att tagga spelare.");
			event.setCancelled(true);
			return;
		}
		
		if (event.getMessage().startsWith("/"))
			return;
			
		sendLogToOfficialDiscord(player, message);
		
	}

	private void sendLogToDiscord(Player player, String message)
	{
		try
		{
			main.getJDA().getTextChannelById(main.STAFFCHAT_CHANNEL).sendMessage("`" + player.getPlayer().getServer().getInfo().getName() + "` **" + player.getUsername() + "** " + message).queue();
		}
		catch (Exception e)
		{

		}
	}
	
	private void sendLogToOfficialDiscord(Player player, String message)
	{
		try
		{
			main.getJDA().getGuildById(main.OFFICIAL_DISCORD).getTextChannelById(main.SERVERCHATT_XPLAY).sendMessage("`" + player.getUsername() + " | " + player.getPlayer().getServer().getInfo().getName() + "` " + message).queue();
		}
		catch (Exception e)
		{

		}
	}

}
