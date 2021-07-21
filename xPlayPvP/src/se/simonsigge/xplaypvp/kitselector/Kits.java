package se.simonsigge.xplaypvp.kitselector;

import org.bukkit.Material;

public enum Kits {
	
	ARCHER("Archer", 5000, Material.BOW, "Pil och b�ge;\nprecis som p� den gamla onda tiden."),
	BERSERKER("Berserker", 5000, Material.ROTTEN_FLESH, "Blir starkare av att d�da.\nAkta dig."),
	COOKIEMONSTER("CookieMonster", 5000, Material.COOKIE, "Kakor �r gott.\nMen man blir l�tt v�ldsam."),
	FISHERMAN("Fisherman", 10000, Material.FISHING_ROD, "V�r egen favorit.\n\n�r spelaren l�ngt iv�g?\nH�gerklicka f�r att dra den hit!"),
	FLYGARE("Flygare", 5000, Material.FIREWORK,"Vi har alla dr�mt om det.\nH�r kan du g�ra det."),
	GRANDPA("Grandpa", 7500, Material.STICK, "N�gon har tagit morfars stav..."),
	JUMPER("Jumper", 5000, Material.ENDER_PEARL, "Teleportera dig,\noch fly f�r ditt liv."),
	MONK("Monk", 5000, Material.BLAZE_ROD, "Akta dig f�r ficktjuven!\nEller bli en sj�lv..."),
	NINJA("Ninja", 5000, Material.REDSTONE, "Nu kan du vara osynlig!"),
	POSEIDON("Poseidon", 7500, Material.WATER_BUCKET, "a.k.a. vattenguden."),
	PVP("PvP", 2500, Material.IRON_SWORD, "Simpelt och enkelt."),
	SNAIL("Snail", 5000, Material.ANVIL, "Det �r sv�rt att\nspringa n�r man �r tung."),
	STOMPER("Stomper", 10000, Material.SPONGE, "Hoppa p� folk,\nd� d�r folk. LOL."),
	STONE("Stone", 7500, Material.STONE_SWORD, "Ett op-J*DRA sv�rd,\nkan rasera n�gons v�rld."),
	SWITCHER("Switcher", 7500, Material.SNOW_BALL, "Vem visste att\nsn�bollar kunde vara s� farliga?"),
	URGAL("Urgal", 5000, Material.POTION, "Styrka �r farligt,\ni alla fall i fel h�nder (muskler?)."),
	VIPER("Viper", 5000, Material.POISONOUS_POTATO, "Du �r ett f*ck*ng gift,\nkommer aldrig f� min f*ck*ng sympati,\naldrig, aldrig n�gonsin..."),
	XREAPER("xReaper", 5000, Material.DIAMOND_HOE, "Tr�dg�rdslandet �r ibland giftigt.\nSpeciellt n�r Simonsigge odlar.");
	
	
	
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
