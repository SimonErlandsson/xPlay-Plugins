package net.vouchs.xPlay.objects;

import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.vouchs.xPlay.Main;

public class Player
{

	Main main = Main.getInstance();

	private UUID uuid;
	private ProxiedPlayer player;
	
	private boolean inStaffChat = false;

	public Player(UUID uuid)
	{
		this.uuid = uuid;
		this.player = ProxyServer.getInstance().getPlayer(uuid);
	
		main.getPlayerHandler().addPlayer(this);		
	}

	public UUID getUUID()
	{
		return this.uuid;
	}

	public String getUsername()
	{
		return player.getName();
	}

	public ProxiedPlayer getPlayer()
	{
		return player;
	}

	public boolean isInStaffChat()
	{
		return inStaffChat;
	}

	public void setInStaffChat(boolean inStaffChat)
	{
		this.inStaffChat = inStaffChat;
	}

}
