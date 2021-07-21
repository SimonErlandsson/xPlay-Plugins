package xPlayShop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIGenerator {

	public HashMap<ItemStack, ActionObject> actionsToDo;

	public GUIGenerator() {
		actionsToDo = new HashMap<ItemStack, ActionObject>();

		initBrowser();

	}

	private void initBrowser() {
		for (int i = 0; i < 2; i++) {
			for (Buyables buy : Buyables.values()) {
				if (i == 0) {
					ActionObject actionObject = new ActionObject(true, buy);
					actionsToDo.put(getDefaultGUIItemStack(buy, true), actionObject);
				} else {
					ActionObject actionObject = new ActionObject(false, buy);
					actionsToDo.put(getDefaultGUIItemStack(buy, false), actionObject);
				}
			}
		}
	}

	public Inventory getAcceptGUI(Buyables buy) {
		Inventory accept = Bukkit.createInventory(null, 27, ChatColor.GREEN + "" + ChatColor.BOLD + "Är du säker?");

		accept.setItem(12, getAcceptGUIItemStack(buy, true));
		accept.setItem(14, getAcceptGUIItemStack(buy, false));

		return accept;
	}

	public ItemStack getAcceptGUIItemStack(Buyables buy, boolean typeOfAccept) {

		if (typeOfAccept) {
			ItemStack yes = new ItemStack(Material.WOOL, 1, (byte) 5);
			ItemMeta yesMeta = yes.getItemMeta();
			yesMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Ja, köp");
			yesMeta.setLore(Arrays.asList(("\nKöp " + buy.getType().toLowerCase() + " för " + buy.getPrice()
					+ " PlayMynt." + ChatColor.ITALIC + "\nDetta går inte att ångra.").split("\n")));
			yes.setItemMeta(yesMeta);
			return yes;
		} else {
			ItemStack no = new ItemStack(Material.WOOL, 1, (byte) 14);
			ItemMeta noMeta = no.getItemMeta();
			noMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Nej, avbryt");
			no.setItemMeta(noMeta);
			return no;
		}
	}

	public Inventory getDefaultGUI(Player p) {
		Inventory defaultInv = Bukkit.createInventory(null, 27,
				ChatColor.AQUA + "" + ChatColor.BOLD + "xPlayShop -> Köp fördelar!");
		List<ItemStack> buyStacks = new ArrayList<ItemStack>();

		for (Buyables buy : Buyables.values())
			buyStacks.add(getDefaultGUIItemStack(buy, p.hasPermission("xPlayShop." + buy.getShortname())));

		int i = 4;
		for (ItemStack item : buyStacks) {
			defaultInv.setItem(i, item);
			i += 2;
			if (i == 6)
				i = 12;
			if (i == 16)
				i = 20;
		}

		return defaultInv;
	}

	public ItemStack getDefaultGUIItemStack(Buyables buy, boolean hasBought) {
		ItemStack iS = new ItemStack(buy.getMaterial(), 1);
		ItemMeta iSMeta = iS.getItemMeta();

		if (!hasBought) {
			iSMeta.setDisplayName(
					ChatColor.RED + "" + ChatColor.BOLD + buy.getType() + " - " + buy.getPrice() + " PlayMynt");
			iSMeta.setLore(Arrays.asList(("\n" + buy.getDescription()).split("\n")));
		} else {
			iSMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + buy.getType() + " - Äger");
			iSMeta.setLore(Arrays.asList(("\n" + "Du äger redan detta.\n" + "\n" + buy.getDescription()).split("\n")));
		}

		iS.setItemMeta(iSMeta);
		return iS;
	}

}

class ActionObject {

	private boolean isBought;
	private Buyables buyables;

	public ActionObject(boolean isBought, Buyables buyables) {
		this.isBought = isBought;
		this.buyables = buyables;
	}
	
	public boolean getBought() { return isBought; }
	public Buyables getBuyables() { return buyables; }
}