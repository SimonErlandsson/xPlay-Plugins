package se.simonsigge.xplaypvp.kitselector;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import se.simonsigge.xplaypvp.Main;

public class GuiListener implements Listener {

	@EventHandler
	public void onSelectInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName()
				.equals("Välj eller köp ett kit!"))
			return;

		e.setCancelled(true);

		Player p = (Player) e.getWhoClicked();
		p.updateInventory();
		ItemStack item = e.getCurrentItem();

		if (item.equals(Main.getGuiManager().getGuiGenerator()
				.getCloseItemStack())) {
			Main.getChatUtilities()
					.sendPlayerMessage(
							p,
							ChatColor.RED
									+ "Du stängde menyn. Använd /kit för att öppna den igen.");
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.BLOCK_CHEST_CLOSE, 100, 100);
			return;
		}

		if (item.equals(Main.getGuiManager().getGuiGenerator()
				.getShopItemStack())) {
			p.closeInventory();

			Main.getGuiManager().openShopGui(p);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			return;
		}
		
		if (!item.hasItemMeta())
			return;
		if (!item.getItemMeta().hasDisplayName())
			return;

		Kits kit = getKitByName(item.getItemMeta().getDisplayName());

		if (kit == null) {
			Main.getChatUtilities().sendErrorMessage(p);
			p.closeInventory();
			return;
		}

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				"pvpkit " + kit.getName() + " " + p.getName());
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
		Main.getSoupManager().checkPlayerSoup(p);
		p.closeInventory();
		p.setLevel(0);
		p.setExp(0);

	}

	@EventHandler
	public void onBuyInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName().equals("Välj ett kit att köpa!"))
			return;

		e.setCancelled(true);

		Player p = (Player) e.getWhoClicked();
		p.updateInventory();
		ItemStack item = e.getCurrentItem();

		if (item.equals(Main.getGuiManager().getGuiGenerator()
				.getCloseItemStack())) {
			p.closeInventory();
			Main.getGuiManager().openSelectGui(p);
			return;
		}

		Kits kit = getKitByName(item.getItemMeta().getDisplayName());

		if (kit == null) {
			Main.getChatUtilities().sendErrorMessage(p);
			p.closeInventory();
			return;
		}

		if (Main.getEconomy().getBalance(p) < kit.getPrice()) {
			Main.getChatUtilities().sendPlayerMessage(p,
					ChatColor.RED + "Du har inte råd!");
			p.playSound(p.getLocation(), Sound.BLOCK_CHEST_CLOSE, 100, 100);
			return;
		}

		Main.getEconomy().withdrawPlayer(p, kit.getPrice());
		Main.getGuiManager().getDataManager().addKit(p, kit);
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
		p.closeInventory();
		Main.getGuiManager().openSelectGui(p);
		Main.getChatUtilities().sendPlayerMessage(p,
				ChatColor.GREEN + "Köpte " + kit.getName() + "! Grattis!");

	}

	private Kits getKitByName(String name) {
		for (Kits kit : Kits.values()) {
			if (name.contains(kit.getName()))
				return kit;
		}
		return null;
	}

}
