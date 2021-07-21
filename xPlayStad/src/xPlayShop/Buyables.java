package xPlayShop;

import org.bukkit.Material;

public enum Buyables {
	
	FLYTOWNY (Material.FEATHER, "Fly i towny", 75000, "FlyTowny", "Ger dig tillgång till /fly på towny.\nBygg och utforska enklare."),
	FLYMARK (Material.FEATHER, "Fly i mark", 75000, "FlyMark", "Ger dig tillgång till /fly på mark.\nBygg enklare."),
	FEED (Material.COOKED_BEEF, "Feed överallt", 50000, "Feed", "Ger dig tillgång till /feed\npå alla delservrar. Slipp att äta."),
	HOMETOWNY (Material.BED, "Fem hem i towny", 35000, "HomeTowny", "Du kan /sethome fem gånger på towny.\nTransportera dig enklare."),
	HOMEMARK (Material.BED, "Fem hem i mark", 35000, "HomeMark", "Du kan /sethome fem gånger på mark.\nTransportera dig enklare."),
	ENDERCHEST (Material.ENDER_CHEST, "Enderchest", 10000, "Enderchest", "Tillgång till /enderchest.\nGäller på Mark, Towny, Resurs och Stad.");
	
	private Material material;
	private String reward;
	private int price;
	private String shortName;
	private String description;
	
	Buyables(Material material, String reward, int price, String shortName, String description) {
		this.material = material;
		this.reward = reward;
		this.price = price;
		this.shortName = shortName;
		this.description = description;
	}
	
	public Material getMaterial() {
		return material;
	}

	public String getType() {
		return reward;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getShortname() {
		return shortName;
	}
	
	public String getDescription() {
		return description;
	}

}
