package xPlayShop;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Simonsigge.xPlayStad.Main;

public class GUIManager implements Listener {

	public Inventory accept;
	private GUIGenerator guiGenerator;

	HashMap<Player, Inventory> currentBrowsers;
	HashMap<Player, Buyables> currentConfirms;

	public GUIManager() {
		guiGenerator = new GUIGenerator();
		currentBrowsers = new HashMap<Player, Inventory>();
		currentConfirms = new HashMap<Player, Buyables>();
	}

	public void openDefaultGui(Player p) {
		Inventory inv = guiGenerator.getDefaultGUI(p);
		currentBrowsers.put(p, inv);
		p.openInventory(inv);
	}

	@EventHandler
	public void onInventoryClickBrowser(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		ItemStack clickedItem = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();

		if (!currentBrowsers.containsKey(p) || !guiGenerator.actionsToDo.containsKey(clickedItem))
			return;

		e.setCancelled(true);
		p.updateInventory();
		
		ActionObject acOb = guiGenerator.actionsToDo.get(clickedItem);
		Buyables buy = acOb.getBuyables();

		if (acOb.getBought()) {
			Main.getInstance().messageHandler.alreadyHasItem(p);
			currentBrowsers.remove(p);
			e.getWhoClicked().closeInventory();
		} else {
			currentBrowsers.remove(p);
			p.openInventory(guiGenerator.getAcceptGUI(buy));
			currentConfirms.put(p, buy);
		}
	}

	@EventHandler
	public void onInventoryClickConfirm(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		ItemStack clickedItem = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();

		if (currentConfirms.containsKey(p)) {
			e.setCancelled(true);
			Buyables buy = currentConfirms.get(p);

			if (clickedItem.equals(guiGenerator.getAcceptGUIItemStack(buy, true))) {
				if (Main.getEconomy().getBalance(p) < buy.getPrice()) {
					Main.getInstance().messageHandler.notEnoughMoney(p);
					cancelCurrentConfirm(p);
					return;
				}

				Main.getEconomy().withdrawPlayer(p, buy.getPrice());
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"pex user " + p.getName() + " add xPlayShop." + currentConfirms.get(p).getShortname());

				switch (buy) {
				case FEED:
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.feed");
					break;
				case FLYMARK:
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.fly xPlayMark");
					break;
				case FLYTOWNY:
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.fly xPlayTowny");
					break;
				case HOMEMARK:
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.sethome.multiple.stad xPlayMark");
					break;
				case HOMETOWNY:
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.sethome.multiple.stad xPlayTowny");
					break;
				case ENDERCHEST:
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.enderchest xPlayTowny");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.enderchest xPlayResurs");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.enderchest xPlayMark");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + p.getName() + " add essentials.enderchest xPlayStad");
					break;
				}

				Main.getInstance().messageHandler.successItem(p);
				cancelCurrentConfirm(p);
			}

			if (clickedItem.equals(guiGenerator.getAcceptGUIItemStack(currentConfirms.get(p), false))) {
				Main.getInstance().messageHandler.cancelPayment(p);
				cancelCurrentConfirm(p);
			}
		}
	}

	private void cancelCurrentConfirm(Player p) {
		currentConfirms.remove(p);
		p.updateInventory();
		p.closeInventory();
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		clearPlayerArray((Player) e.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		clearPlayerArray(e.getPlayer());
	}

	private void clearPlayerArray(Player p) {
		if (currentBrowsers.containsKey(p))
			currentBrowsers.remove(p);

		if (currentConfirms.containsKey(p))
			currentConfirms.remove(p);
	}

}
