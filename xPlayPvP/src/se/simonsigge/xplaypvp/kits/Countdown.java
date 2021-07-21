package se.simonsigge.xplaypvp.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Countdown implements Runnable {

	public Player p1 = null;
	public List<Player> rpcd1 = new ArrayList<Player>();

	public void setPlayer(Player player) {
		this.p1 = player;
	}

	public void setList(List<Player> list) {
		this.rpcd1 = list;
	}

	public List<Player> getList() {
		return rpcd1;
	}

	public void run() {
		try {
			Thread.sleep(30000);
			rpcd1.remove(p1);
		} catch (Exception ignored) {
		}
	}
}
