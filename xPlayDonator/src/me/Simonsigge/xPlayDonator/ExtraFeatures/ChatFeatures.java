package me.Simonsigge.xPlayDonator.ExtraFeatures;

import java.util.HashMap;
import java.util.Map.Entry;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Feature;
import me.Simonsigge.xPlayDonator.Nodes.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatFeatures implements Listener {
	
	private HashMap<Player, Feature> currentChatters;

	public ChatFeatures() {
		currentChatters = new HashMap<Player, Feature>();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (currentChatters.size() > 0) {
					for (Entry<Player, Feature> player : currentChatters.entrySet()) {
						if (player.getValue() == Feature.CHATCOLOR)
							Main.getMsgAPI().featureChatColorPrompt(player.getKey());
						else if (player.getValue() == Feature.CHATSHOUT)
							Main.getMsgAPI().featureChatShoutPrompt(player.getKey());
					}
				}
				
			}}, 20, 20);
	}

	public void newChatFeature(Player p, Feature feature) {
		int neededDelay = 0;

		switch (feature) {
		case CHATCOLOR:
			neededDelay = Misc.CHAT_COLOR_DELAY;
			break;
		case CHATSHOUT:
			neededDelay = Misc.CHAT_SHOUT_DELAY;
			break;
		default:
			break;
		}

		if (Main.getDataHandler().getTimeSinceFeature(p, feature) < neededDelay) {
			Main.getMsgAPI().featureTimeLeft(
					p,
					formatTimeLeft(neededDelay
							- Main.getDataHandler().getTimeSinceFeature(p,
									feature)));
			return;
		}
		
		if (currentChatters.containsKey(p)) {
			Main.getMsgAPI().featureAlreadyInQue(p);
			return;
		}
		
		currentChatters.put(p, feature);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (currentChatters.containsKey(e.getPlayer())) {
			Player p = e.getPlayer();
			e.setCancelled(true);
			
			if (e.getMessage().equalsIgnoreCase("avbryt")) {
				Main.getMsgAPI().featureChatCancel(p);
				currentChatters.remove(p);
			} else {
				Main.getDataHandler().setTimeSinceFeature(p, currentChatters.get(p));
				Main.getMsgAPI().featureChatSuccess(p);
				
				String message = e.getMessage();
				message = message.replace("&K", "");
				message = message.replace("&k", "");
				
				final String fmessage = message;
				
				new BukkitRunnable() {

					@Override
					public void run() {
						switch (currentChatters.get(p)) {
						case CHATCOLOR:
							Main.getMsgAPI().getMsgUtil().broadcastCustomMessageNoPrefix("&4[&c" + p.getName() + "&4] &f-> &c" + fmessage);
							break;
						case CHATSHOUT:
							Main.getMsgAPI().getMsgUtil().broadcastBungeeCustomMessageNoPrefix("&cSKRIK: &4[&c" + p.getName() + "&4] &f-> &c" + fmessage);
							break;
						default:
							break;
						}
						
						currentChatters.remove(p);
						
					} }.runTask(Main.getInstance());

			}
			
			
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (currentChatters.containsKey(e.getPlayer()))
			currentChatters.remove(e.getPlayer());
	}

	private String formatTimeLeft(long timeLeft) {
		if (timeLeft > 60)
			return Math.round(timeLeft / 6) / 10 + " timmar";
		else
			return Math.round(timeLeft * 10) / 10 + " minuter";

	}

}
