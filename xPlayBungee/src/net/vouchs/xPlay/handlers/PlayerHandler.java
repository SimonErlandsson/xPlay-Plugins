package net.vouchs.xPlay.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.objects.Player;

public class PlayerHandler
{

	Main main = Main.getInstance();

	private List<Player> players = new ArrayList<>();

	public List<Player> getPlayers()
	{
		return this.players;
	}

	public void addPlayer(Player player)
	{
		this.players.add(player);
	}

	public void removePlayer(Player player)
	{
		this.players.remove(getPlayer(player.getUUID()));
	}

	public Player getPlayer(UUID uuid)
	{
		for (Player player : getPlayers())
		{
			if (player.getUUID().equals(uuid)) { return player; }
		}
		return null;
	}
}
