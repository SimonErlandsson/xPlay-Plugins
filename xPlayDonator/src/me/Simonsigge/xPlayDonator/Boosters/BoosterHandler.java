package me.Simonsigge.xPlayDonator.Boosters;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Booster;
import me.Simonsigge.xPlayDonator.Nodes.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BoosterHandler {
	
	private boolean boosterActive;
	private String boosterActivator;
	private DropParty dropParty;
	
	public BoosterListener boosterListener;
	
	public BoosterHandler() {
		boosterListener = new BoosterListener(this);
		boosterActive = false;
		dropParty = new DropParty(this);
		
	}
	
	public void activateBooster(Player p, Booster booster) {
		if (Main.getDataHandler().getBooster(p, booster) <= 0) {
			Main.getMsgAPI().noBooster(p);
			return;
		}
		
		switch (booster) {
		case ARBETE:
			if (!p.getWorld().getName().equals(Misc.ARBETE_WORLD_NAME)) {
				Main.getMsgAPI().wrongServer(p);
				return;
			}
			break;
		case DROP:
			if (!p.getWorld().getName().equals(Misc.STAD_WORLD_NAME)) {
				Main.getMsgAPI().wrongServer(p);
				return;
			}
			break;
		case PVP:
			if (!p.getWorld().getName().equals(Misc.PVP_WORLD_NAME)) {
				Main.getMsgAPI().wrongServer(p);
				return;
			}
			break;
		}
		
		if (boosterActive) {
			Main.getMsgAPI().alreadyActiveBooster(p);
			return;
		}
		
		if (Bukkit.getOnlinePlayers().size() < Misc.BOOSTER_REQUIRED_PLAYERS) {
			Main.getMsgAPI().tooFewPlayers(p);
			return;
		}
		
		Main.getDataHandler().setBooster(p, booster, Main.getDataHandler().getBooster(p, booster) - 1);
		Main.getMsgAPI().boosterActivated(p);
		boosterActivator = p.getName();
		
		succesActivateBooster(booster);
	}
	
	private void succesActivateBooster(Booster booster) {
		boosterActive = true;
		
		if (booster == Booster.ARBETE || booster == Booster.PVP) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "booster on " + boosterActivator);
			Main.getMsgAPI().x2Activated();
			Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "booster off");
					boosterActive = false;
					boosterActivator = "";
					Main.getMsgAPI().x2Deactivated();
				} }, Misc.BOOSTER_TIME);
		} else {
			dropParty.startDrop();
		}
		
		
	}
	
	public void setBoosterActive(boolean boosterActive) {
		this.boosterActive = boosterActive;
	}
	
	public boolean getBoosterActive() {
		return boosterActive;
	}
	
	public String getBoosterActivator() {
		return boosterActivator;
	}

}
