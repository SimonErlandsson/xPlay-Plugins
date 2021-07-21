package net.vouchs.xPlay.listeners.discord;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.vouchs.xPlay.Main;

public class GuildMessageReceived extends ListenerAdapter
{

	Main main;

	public GuildMessageReceived(Main main)
	{
		this.main = main;
		main.getJDA().addEventListener(this);
	}

	@SuppressWarnings("deprecation")
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{

		Member m = main.getJDA().getGuilds().get(0).getMember(main.getJDA().getSelfUser());

		if (event.getMember() == m)
			return;

		TextChannel textChannel = main.getJDA().getTextChannelById(main.STAFFCHAT_CHANNEL);

		if (event.getChannel().equals(textChannel))
		{
			for (ProxiedPlayer target : ProxyServer.getInstance().getPlayers())
			{
				if (target.hasPermission("xPlay.Staff.Notify"))
				{
					target.sendMessage("§0[§fDiscord§r§0] §7" + event.getMessage().getAuthor().getName() + "§c: " + event.getMessage().getRawContent());
				}
			}
		}
	}
}
