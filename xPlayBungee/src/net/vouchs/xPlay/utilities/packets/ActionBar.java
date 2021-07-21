package net.vouchs.xPlay.utilities.packets;

import com.google.gson.JsonObject;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public class ActionBar
{
	public static void sendActionBar(ProxiedPlayer player, String message)
	{
		if (player.getPendingConnection().getVersion() >= 47)
		{
			JsonObject object = new JsonObject();
			String s = message;
			object.addProperty("text", s);
			String ob = object.toString();
			player.unsafe().sendPacket(new Chat(ob, (byte) 2));
		}
	}
}
