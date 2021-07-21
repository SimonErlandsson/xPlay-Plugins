package me.Simonsigge.xPlayDonator.MySQL;

import java.sql.Timestamp;

import org.bukkit.entity.Player;

import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Rank;

public class DonatorPlayer {
	
	private Player p;
	private Rank rank;
	private int helmet;
	private Cloth chest;
	private Cloth leggings;
	private Cloth boots;
	private int arbeteBoost;
	private int pvpBoost;
	private int dropBoost;
	private Timestamp lastChatColor;
	private Timestamp lastChatShout;
	
	//GETS
	
	public Player getPlayer() {
		return this.p;
	}
	
	public Rank getRank() {
		return this.rank;
	}
	
	public int getHelmet() {
		return this.helmet;
	}
	
	public Cloth getChest() {
		return this.chest;
	}
	
	public Cloth getLeggings() {
		return this.leggings;
	}
	
	public Cloth getBoots() {
		return this.boots;
	}
	
	public int getArbeteBoost() {
		return this.arbeteBoost;
	}
	
	public int getDropBoost() {
		return this.dropBoost;
	}
	
	public int getPvPBoost() {
		return this.pvpBoost;
	}
	
	public Timestamp getLastChatColor() {
		return lastChatColor;
	}
	
	public Timestamp getLastChatShout() {
		return lastChatShout;
	}
	
	//SETS
	
	public void setPlayer(Player p) {
		this.p = p;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public void setHelmet(int helmet) {
		this.helmet = helmet;
	}
	
	public void setChest(Cloth chest) {
		this.chest = chest;
	}
	
	public void setLeggings(Cloth leggings) {
		this.leggings = leggings;
	}
	
	public void setBoots(Cloth boots) {
		this.boots = boots;
	}
	
	public void setArbeteBoost(int arbeteBoost) {
		this.arbeteBoost = arbeteBoost;
	}
	
	public void setDropBoost(int resursBoost) {
		this.dropBoost = resursBoost;
	}
	
	public void setPvPBoost(int stadBoost) {
		this.pvpBoost = stadBoost;
	}
	
	public void setLastChatColor(Timestamp lastChatColor) {
		this.lastChatColor = lastChatColor;
	}
	
	public void setLastChatShout(Timestamp lastChatShout) {
		this.lastChatShout = lastChatShout;
	}
}
