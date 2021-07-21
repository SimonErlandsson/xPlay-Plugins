package xPlayShop;

import org.bukkit.Material;

public enum Buyables {
	
	FLYTOWNY (Material.FEATHER, "Fly i towny", 75000, "FlyTowny", "Ger dig tillg�ng till /fly p� towny.\nBygg och utforska enklare."),
	FLYMARK (Material.FEATHER, "Fly i mark", 75000, "FlyMark", "Ger dig tillg�ng till /fly p� mark.\nBygg enklare."),
	FEED (Material.COOKED_BEEF, "Feed �verallt", 50000, "Feed", "Ger dig tillg�ng till /feed\np� alla delservrar. Slipp att �ta."),
	HOMETOWNY (Material.BED, "Fem hem i towny", 35000, "HomeTowny", "Du kan /sethome fem g�nger p� towny.\nTransportera dig enklare."),
	HOMEMARK (Material.BED, "Fem hem i mark", 35000, "HomeMark", "Du kan /sethome fem g�nger p� mark.\nTransportera dig enklare."),
	ENDERCHEST (Material.ENDER_CHEST, "Enderchest", 10000, "Enderchest", "Tillg�ng till /enderchest.\nG�ller p� Mark, Towny, Resurs och Stad.");
	
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
