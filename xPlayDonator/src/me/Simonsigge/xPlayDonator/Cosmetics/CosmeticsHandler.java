package me.Simonsigge.xPlayDonator.Cosmetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;

public class CosmeticsHandler {

	public HashMap<String, ItemStack> currentClothes;
	public List<ItemStack> currentHeads;
	public List<Player> updatePlayersChangeing;
	public List<Player> updatePlayersDisco;
	public CosmeticsUtilities cosmeticsUtilities;
	public List<Inventory> updateInventories;

	public CosmeticsHandler() {
		cosmeticsUtilities = new CosmeticsUtilities();
		currentClothes = new HashMap<String, ItemStack>();
		currentHeads = new ArrayList<ItemStack>();
		updatePlayersChangeing = new ArrayList<Player>();
		updatePlayersDisco = new ArrayList<Player>();
		updateInventories = new ArrayList<Inventory>();
		new ClothesUpdater();
		new HeadGenerator(this);
	}

	public void setPlayerAllClothes(Player p) {
		if (!Main.getDataHandler().isDonor(p))
			return;

		setPlayerHelmet(p, Main.getDataHandler().getPlayerHelmet(p));
		setPlayerCloth(p,
				Main.getDataHandler().getPlayerCloth(p, Armour.CHESTPLATE),
				Armour.CHESTPLATE);
		setPlayerCloth(p,
				Main.getDataHandler().getPlayerCloth(p, Armour.LEGGINGS),
				Armour.LEGGINGS);
		setPlayerCloth(p,
				Main.getDataHandler().getPlayerCloth(p, Armour.BOOTS),
				Armour.BOOTS);

		p.updateInventory();
	}

	public void setPlayerHelmet(Player p, int helmet) {
		PlayerInventory inv = p.getInventory();

		if (helmet != 0) {
			if (helmet == 1) {
				inv.setHelmet(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.CHANGEING, Armour.HELMET)));
				addPlayerToUpdateChangeing(p);
			} else if (helmet == 2) {
				inv.setHelmet(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.DISCO, Armour.HELMET)));
				addPlayerToUpdateDisco(p);
			} else {
				inv.setHelmet(currentHeads.get(helmet
						- (1 + Cloth.values().length - 1)));
			}
		} else if (isCustomItem(inv.getHelmet(), true)) {
			inv.setHelmet(null);
		}
	}
	
	private boolean isCustomItem(ItemStack item, boolean isHead) {
		if (item == null)
			return false;
		
		if ((!item.hasItemMeta()) || !item.getItemMeta().hasDisplayName())
			return false;
		
		if (isHead) {
		for (ItemStack head : currentHeads) {
			if (head.getItemMeta().getDisplayName()
					.equals(item.getItemMeta().getDisplayName())) {
				return true;
			}
		}
		}

		for (ItemStack cloths : currentClothes.values()) {
			if (cloths.getItemMeta().getDisplayName()
					.equals(item.getItemMeta().getDisplayName())) {
				return true;
			}
		}
		
		return false;
	}

	public void setPlayerCloth(Player p, Cloth cloth, Armour armour) {
		PlayerInventory inv = p.getInventory();

		switch (armour) {
		case HELMET:
			return;

		case CHESTPLATE:
			switch (cloth) {
			case CHANGEING:
				inv.setChestplate(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.CHANGEING, Armour.CHESTPLATE)));
				addPlayerToUpdateChangeing(p);
				break;
			case DISCO:
				inv.setChestplate(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.DISCO, Armour.CHESTPLATE)));
				addPlayerToUpdateDisco(p);
				break;
			case NONE:
				if (isCustomItem(inv.getChestplate(), false))
					inv.setChestplate(null);
				break;
			}
			break;

		case LEGGINGS:
			switch (cloth) {
			case CHANGEING:
				inv.setLeggings(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.CHANGEING, Armour.LEGGINGS)));
				addPlayerToUpdateChangeing(p);
				break;
			case DISCO:
				inv.setLeggings(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.DISCO, Armour.LEGGINGS)));
				addPlayerToUpdateDisco(p);
				break;
			case NONE:
				if (isCustomItem(inv.getLeggings(), false))
					inv.setLeggings(null);
				break;
			}
			break;

		case BOOTS:
			switch (cloth) {
			case CHANGEING:
				inv.setBoots(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.CHANGEING, Armour.BOOTS)));
				addPlayerToUpdateChangeing(p);
				break;
			case DISCO:
				inv.setBoots(currentClothes.get(cosmeticsUtilities
						.getSummedEnum(Cloth.DISCO, Armour.BOOTS)));
				addPlayerToUpdateDisco(p);
				break;
			case NONE:
				if (isCustomItem(inv.getBoots(), false))
					inv.setBoots(null);
				break;
			}
			break;
		}
	}

	public void setEmptyPlayerCloth(Player p, Armour armour) {
		PlayerInventory inv = p.getInventory();

		switch (armour) {
		case HELMET:
			inv.setHelmet(null);
			break;
		case CHESTPLATE:
			inv.setChestplate(null);
			break;
		case LEGGINGS:
			inv.setLeggings(null);
			break;
		case BOOTS:
			inv.setBoots(null);
			break;
		default:
			break;
		}

		Main.getMsgAPI().removeCloth(p);
	}

	public void addInventoryToUpdate(Inventory inv) {
		if (!updateInventories.contains(inv))
			updateInventories.add(inv);
	}

	public void removeInventoryFromUpdate(Inventory inv) {
		if (updateInventories.contains(inv))
			updateInventories.remove(inv);
	}

	public void removePlayerFromUpdate(Player p) {
		removePlayerFromUpdateChangeing(p);
		removePlayerFromUpdateDisco(p);
	}

	private void addPlayerToUpdateChangeing(Player p) {
		if (!updatePlayersChangeing.contains(p))
			updatePlayersChangeing.add(p);
	}

	private void addPlayerToUpdateDisco(Player p) {
		if (!updatePlayersDisco.contains(p))
			updatePlayersDisco.add(p);
	}

	private void removePlayerFromUpdateChangeing(Player p) {
		if (updatePlayersChangeing.contains(p))
			updatePlayersChangeing.remove(p);
	}

	private void removePlayerFromUpdateDisco(Player p) {
		if (updatePlayersDisco.contains(p))
			updatePlayersDisco.remove(p);
	}

}
