package net.vouchs.arbete.enums;

import org.bukkit.Material;

public enum BlockValue
{
	STONE(Material.STONE, "miner", 2),
	COBBLE_STONE(Material.COBBLESTONE, "miner", 2),
	COAL_ORE(Material.COAL_ORE, "miner", 5),
	IRON_ORE(Material.IRON_ORE, "miner", 7),
	GOLD_ORE(Material.GOLD_ORE, "miner", 13),
	DIAMOND_ORE(Material.DIAMOND_ORE, "miner", 30),
	LAPIS_ORE(Material.LAPIS_ORE, "miner", 8),
	EMERALD_ORE(Material.EMERALD_ORE, "miner", 60),
	
	LOG(Material.LOG, "lumberjack", 3),
	LOG_2(Material.LOG_2, "lumberjack", 3),
	
	MELON_BLOCK(Material.MELON_BLOCK, "farmer", 10),
	PUMPKIN(Material.PUMPKIN, "farmer", 10),
	
	DIRT(Material.DIRT, "digger", 1),
	GRAVEL(Material.GRAVEL, "digger", 1),
	SAND(Material.SAND, "digger", 1);
	
	private Material material;
	private String job;
	private int amount;
	
	BlockValue(Material material, String job, Integer amount) {
		this.material = material;
		this.job = job;
		this.amount = amount;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public int getAmount() {
		return this.amount;
	}	
	
	public String getJob() {
		return this.job;
	}
	
}
