package se.simonsigge.xplaypvp.kits.now;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class Cooldown {

	private HashMap<Kits, ArrayList<String>> cooldowns;

	public Cooldown() {
		cooldowns = new HashMap<Kits, ArrayList<String>>();
	}

	public void addCooldown(Player p, Kits kit, int cooldownTicks) {
		String playerName = p.getName();
		ArrayList<String> list; 
		
		if (cooldowns.containsKey(kit))
			list = cooldowns.get(kit);
		else
			list = new ArrayList<String>();
		
		if (!list.contains(playerName))
			list.add(playerName);
		
		Main.getInstance().getServer().getScheduler()
				.runTaskLater(Main.getInstance(), new Runnable() {
					public void run() {
						if (list.contains(playerName))
							list.remove(playerName);
					}
				}, cooldownTicks);

		cooldowns.put(kit, list);
	}

	public boolean isInCooldown(Player p, Kits kit) {
		if (!cooldowns.containsKey(kit))
			return false;
		
		if (cooldowns.get(kit).contains(p.getName()))
			return true;
		else
			return false;
	}

}
