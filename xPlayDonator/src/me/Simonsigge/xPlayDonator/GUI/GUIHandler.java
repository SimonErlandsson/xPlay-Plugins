package me.Simonsigge.xPlayDonator.GUI;

import java.util.ArrayList;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIHandler {
	
	public GUIInit guiInit;
	public GUIListener guiListener;
	private ArrayList<Player> currentBrowsers;
	
	public GUIHandler() {
		guiInit = new GUIInit();
		guiListener = new GUIListener();
		
		currentBrowsers = new ArrayList<Player>();
	}
	
	public void displayGUI(Player p, GUI gui) {
		if (gui == GUI.MAIN) {
			p.openInventory(guiInit.getMainInv());
			addToCurrentBrowsers(p);
			return;
		}
		
		if (gui == GUI.CLOTHES) {
			Inventory inv = guiInit.generateCustomInv(p, gui);
			p.openInventory(inv);
			addToCurrentBrowsers(p);
			Main.getCosmeticsHandler().addInventoryToUpdate(inv);
			return;
		}
		
		p.openInventory(guiInit.generateCustomInv(p, gui));
		addToCurrentBrowsers(p);
	}
	
	public ArrayList<Player> getCurrentBrowsers() {
		return currentBrowsers;
	}
	
	public void addToCurrentBrowsers(Player p) {
		if (!currentBrowsers.contains(p))
			currentBrowsers.add(p);
	}
	
	public void removeFromCurrentBrowsers(Player p) {
		if (currentBrowsers.contains(p))
			currentBrowsers.remove(p);
	}
	
}
