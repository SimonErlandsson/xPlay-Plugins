package me.Simonsigge.xPlayDonator.Message;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.Simonsigge.xPlayDonator.Main.Main;

public class MessageBungee implements PluginMessageListener {

	private List<String> bungeePlayerList;

	public MessageBungee() {

		Main.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
		Main.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "BungeeCord",
				this);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() == 0)
					return;
				ByteArrayDataOutput playerList;
				bungeePlayerList = new ArrayList<String>();

				playerList = ByteStreams.newDataOutput();
				playerList.writeUTF("PlayerList");
				playerList.writeUTF("ALL");
				Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(Main.getInstance(), "BungeeCord",
						playerList.toByteArray());

			}
		}, 0L, 20L * 30);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord"))
			return;
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		if (!in.readUTF().equals("PlayerList")) return;
		if (!in.readUTF().equals("ALL")) return;
	    String[] players = in.readUTF().split(", ");
		for (String s : players) bungeePlayerList.add(s);
		
	}

	public void broadcastBungee(String message) {
		
		if (Bukkit.getOnlinePlayers().size() == 0)
			return;
		Player send = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		ByteArrayDataOutput out;
		for (String player : bungeePlayerList) {
			out = ByteStreams.newDataOutput();
			out.writeUTF("Message");
			out.writeUTF(player);
			out.writeUTF(message);
			send.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
		}
	}

}
