package net.vouchs.xPlay.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.objects.Player;

public class PostLogin implements Listener
{
	Main main;

	public PostLogin(Main main)
	{
		this.main = main;
		main.getProxy().getPluginManager().registerListener(main, this);
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event)
	{
		ProxiedPlayer proxiedPlayer = event.getPlayer();
		new Player(proxiedPlayer.getUniqueId());
		
		main.getJDA().getGuildById(main.OFFICIAL_DISCORD).getTextChannelById(main.SERVERCHATT_XPLAY).sendMessage("`" + proxiedPlayer.getName() + " anslöt sig till xPlay Server.`").queue();
		
	}

}
