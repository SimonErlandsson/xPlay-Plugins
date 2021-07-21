package se.simonsigge.xplaypvp.kitselector;

import org.bukkit.Material;

public enum Kits {
	
	ARCHER("Archer", 5000, Material.BOW, "Pil och båge;\nprecis som på den gamla onda tiden."),
	BERSERKER("Berserker", 5000, Material.ROTTEN_FLESH, "Blir starkare av att döda.\nAkta dig."),
	COOKIEMONSTER("CookieMonster", 5000, Material.COOKIE, "Kakor är gott.\nMen man blir lätt våldsam."),
	FISHERMAN("Fisherman", 10000, Material.FISHING_ROD, "Vår egen favorit.\n\nÄr spelaren långt iväg?\nHögerklicka för att dra den hit!"),
	FLYGARE("Flygare", 5000, Material.FIREWORK,"Vi har alla drömt om det.\nHär kan du göra det."),
	GRANDPA("Grandpa", 7500, Material.STICK, "Någon har tagit morfars stav..."),
	JUMPER("Jumper", 5000, Material.ENDER_PEARL, "Teleportera dig,\noch fly för ditt liv."),
	MONK("Monk", 5000, Material.BLAZE_ROD, "Akta dig för ficktjuven!\nEller bli en själv..."),
	NINJA("Ninja", 5000, Material.REDSTONE, "Nu kan du vara osynlig!"),
	POSEIDON("Poseidon", 7500, Material.WATER_BUCKET, "a.k.a. vattenguden."),
	PVP("PvP", 2500, Material.IRON_SWORD, "Simpelt och enkelt."),
	SNAIL("Snail", 5000, Material.ANVIL, "Det är svårt att\nspringa när man är tung."),
	STOMPER("Stomper", 10000, Material.SPONGE, "Hoppa på folk,\ndå dör folk. LOL."),
	STONE("Stone", 7500, Material.STONE_SWORD, "Ett op-J*DRA svärd,\nkan rasera någons värld."),
	SWITCHER("Switcher", 7500, Material.SNOW_BALL, "Vem visste att\nsnöbollar kunde vara så farliga?"),
	URGAL("Urgal", 5000, Material.POTION, "Styrka är farligt,\ni alla fall i fel händer (muskler?)."),
	VIPER("Viper", 5000, Material.POISONOUS_POTATO, "Du är ett f*ck*ng gift,\nkommer aldrig få min f*ck*ng sympati,\naldrig, aldrig någonsin..."),
	XREAPER("xReaper", 5000, Material.DIAMOND_HOE, "Trädgårdslandet är ibland giftigt.\nSpeciellt när Simonsigge odlar.");
	
	
	
	private String name;
	private int price;
	private Material material;
	private String description;
	
	Kits(String name, int price, Material material, String description) {
		this.name = name;
		this.price = price;
		this.material = material;
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPrice(){
		return price;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public String getDescription(){
		return description;
	}

}
