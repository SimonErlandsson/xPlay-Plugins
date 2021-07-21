package me.Simonsigge.xPlayDonator.Cosmetics;

import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;

public class CosmeticsUtilities {
	
	public Cloth getOrgCloth(String string) {
		Cloth cloth = Cloth.NONE;
		
		if (string.contains("DISCO"))
			cloth = Cloth.DISCO;
		
		if (string.contains("CHANGEING"))
			cloth = Cloth.CHANGEING;
		
		return cloth;
	}
	
	public Armour getOrgArmour(String string) {
		Armour armour = null;
		
		if (string.contains("HELMET"))
			armour = Armour.HELMET;
		
		if (string.contains("CHESTPLATE"))
			armour = Armour.CHESTPLATE;
		
		if (string.contains("LEGGINGS"))
			armour = Armour.LEGGINGS;
		
		if (string.contains("BOOTS"))
			armour = Armour.BOOTS;
		
		return armour;
	}
	
	public String getSummedEnum(Cloth cloth, Armour armour) {
		return cloth.name() + armour.name();
	}

}
