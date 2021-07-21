package me.Simonsigge.xPlayCasino;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerTeleport implements Listener {
	Location[] leftDown = { new Location(Statics.w, -5, 49, -44), new Location(Statics.w, -5, 49, -45),
			new Location(Statics.w, -5, 49, -46) },
			leftUp = { new Location(Statics.w, -12, 55, -44), new Location(Statics.w, -12, 55, -45),
					new Location(Statics.w, -12, 55, -46) },
			rightDown = { new Location(Statics.w, 5, 49, -44), new Location(Statics.w, 5, 49, -45),
					new Location(Statics.w, 5, 49, -46) },
			rightUp = { new Location(Statics.w, 13, 56, -44), new Location(Statics.w, 13, 56, -45),
					new Location(Statics.w, 13, 56, -46) };
	Location leftDownTp, leftUpTp, rightDownTp, rightUpTp;
	ArrayList<Location> triggers;
	World w;

	public PlayerTeleport() {
		w = Statics.server.getWorlds().get(0);

		leftDownTp = new Location(w, -3.5, 49, -44.5);
		leftUpTp = new Location(w, -12.5, 56, -44.5);
		rightDownTp = new Location(w, 4.5, 49, -44.5);
		rightUpTp = new Location(w, 14.5, 57, -44.5);

		triggers = new ArrayList<Location>();
		triggers.add(leftDown[0]);
		triggers.add(leftDown[1]);
		triggers.add(leftDown[2]);
		triggers.add(leftUp[0]);
		triggers.add(leftUp[1]);
		triggers.add(leftUp[2]);
		triggers.add(rightDown[0]);
		triggers.add(rightDown[1]);
		triggers.add(rightDown[2]);
		triggers.add(rightUp[0]);
		triggers.add(rightUp[1]);
		triggers.add(rightUp[2]);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if ((e.getFrom().getBlockX() == e.getTo().getBlockX()) && (e.getFrom().getBlockY() == e.getTo().getBlockY())
				&& (e.getFrom().getBlockZ() == e.getTo().getBlockZ()))
			return;
		Location roundedLoc = new Location(w, e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());

		boolean isDestTrigger = false;
		for (Location tr : triggers)
			if (tr.equals(roundedLoc)) {
				isDestTrigger = true;
				break;
			}

		if (!isDestTrigger)
			return;

		Player p = e.getPlayer();
		if (roundedLoc.equals(leftDown[0]) || roundedLoc.equals(leftDown[1]) || roundedLoc.equals(leftDown[2])) {
			TpPlayer(p, leftUpTp);
			return;
		}
		if (roundedLoc.equals(leftUp[0]) || roundedLoc.equals(leftUp[1]) || roundedLoc.equals(leftUp[2])) {
			TpPlayer(p, leftDownTp);
			return;
		}
		if (roundedLoc.equals(rightDown[0]) || roundedLoc.equals(rightDown[1]) || roundedLoc.equals(rightDown[2])) {
			TpPlayer(p, rightUpTp);
			return;
		}
		if (roundedLoc.equals(rightUp[0]) || roundedLoc.equals(rightUp[1]) || roundedLoc.equals(rightUp[2])) {
			TpPlayer(p, rightDownTp);
			return;
		}

	}
	
	public void TpPlayer(Player p, Location nLoc){
		Location oLoc = p.getLocation();
		Location newLoc = nLoc.clone();
		newLoc.setPitch(oLoc.getPitch());
		newLoc.setYaw(oLoc.getYaw());
		p.teleport(newLoc);
		p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT, 100, 100);
	}
}