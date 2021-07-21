package me.Simonsigge.xPlayDonator.Cosmetics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadGenerator {
	
	CosmeticsHandler cosmeticsHandler;
	
	public HeadGenerator(CosmeticsHandler cosmeticsHandler) {
		this.cosmeticsHandler = cosmeticsHandler;
		initHeads();
	}
	
	@SuppressWarnings("deprecation")
	private void initHeads() {
		
		for (int i = 0; i < 27; i++) {
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta s = (SkullMeta) item.getItemMeta();
			String sO = null;
			String dN = null;
			switch (i) {
			case 0:
				sO = "MHF_Slime";
				dN = "Slime";
				break;
			case 1:
				sO = "MHF_Pig";
				dN = "Gris";
				break;
			case 2:
				sO = "MHF_LavaSlime";
				dN = "Lava Slime";
				break;
			case 3:
				sO = "MHF_Cow";
				dN = "Ko";
				break;
			case 4:
				sO = "MHF_CaveSpider";
				dN = "Cave Spider";
				break;
			case 5:
				sO = "MHF_Creeper";
				dN = "Creeper";
				break;
			case 6:
				sO = "MHF_Blaze";
				dN = "Blaze";
				break;
			case 7:
				sO = "MHF_Villager";
				dN = "Villager";
				break;
			case 8:
				sO = "MHF_Ghast";
				dN = "Ghast";
				break;
			case 9:
				sO = "ZachWarnerHD";
				dN = "Popcorn";
				break;
			case 10:
				sO = "food";
				dN = "Hamburgare";
				break;
			case 11:
				sO = "zEl3M3nTz";
				dN = "Brödrost";
				break;
			case 12:
				sO = "apanpapan3";
				dN = "Öga";
				break;
			case 13:
				sO = "Pikachubutler";
				dN = "Pikachu-butler";
				break;
			case 14:
				sO = "theCino";
				dN = "Chewbacca";
				break;
			case 15:
				sO = "Pr0SkyNesis";
				dN = "Cool kub";
				break;
			case 16:
				sO = "Langdon";
				dN = "Apa";
				break;
			case 17:
				sO = "Shrek";
				dN = "Shrek";
				break;
			case 18:
				sO = "Clear";
				dN = "Spöke";
				break;
			case 19:
				sO = "Yoshi";
				dN = "Yoshi";
				break;
			case 20:
				sO = "Luigi";
				dN = "Luigi";
				break;
			case 21:
				sO = "kongHD";
				dN = "Donkey Kong";
				break;
			case 22:
				sO = "Mercury777";
				dN = "Coolare kub";
				break;
			case 23:
				sO = "pave12345";
				dN = "pave12345";
				break;
			case 24:
				sO = "xStreetz";
				dN = "xStreetz";
				break;
			case 25:
				sO = "AntonSca";
				dN = "AntonSca";
				break;
			case 26:
				sO = "Simonsigge";
				dN = "Simonsigge";
				break;
			}
			
			//s.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(UUID.fromString(sO)));
			s.setOwner(sO);
			s.setDisplayName(ChatColor.GREEN + dN + "-hatt");
			item.setItemMeta(s);
			cosmeticsHandler.currentHeads.add(item);
		}
	}
}
