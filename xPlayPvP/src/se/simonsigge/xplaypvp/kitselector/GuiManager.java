package se.simonsigge.xplaypvp.kitselector;

import org.bukkit.entity.Player;

public class GuiManager {
	
	private DataManager dataManager;
	private GuiGenerator guiGenerator;
	
	public GuiManager() {
		dataManager = new DataManager();
		guiGenerator = new GuiGenerator();
	}
	
	public void openSelectGui(Player p) {
		p.openInventory(guiGenerator.getSelectInv(p));
	}
	
	public void openShopGui(Player p) {
		p.openInventory(guiGenerator.getShopInv(p));
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}
	
	public GuiGenerator getGuiGenerator() {
		return guiGenerator;
	}

}
