package me.Simonsigge.xPlayDonator.GUI;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Booster;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Feature;
import me.Simonsigge.xPlayDonator.Nodes.Enums.GUI;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (Main.getGUIHandler().getCurrentBrowsers().contains(e.getPlayer()))
			Bukkit.getScheduler().runTaskLaterAsynchronously(
					Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							Main.getGUIHandler().displayGUI(
									(Player) e.getPlayer(), GUI.MAIN);
						}
					}, 1);

		Main.getCosmeticsHandler().removeInventoryFromUpdate(e.getInventory());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (Main.getGUIHandler().getCurrentBrowsers().contains(e.getPlayer()))
			Main.getGUIHandler().getCurrentBrowsers().remove(e.getPlayer());
	}

	@EventHandler
	public void onMainInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName()
				.equals("xPlayDonator - Huvudmeny"))
			return;

		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		Main.getGUIHandler().removeFromCurrentBrowsers(p);

		if (item.equals(Main.getGUIHandler().guiInit.infoBook)) {
			p.closeInventory();
			Main.getMsgAPI().moreInfo(p);
		}

		if (item.equals(Main.getGUIHandler().guiInit.website)) {
			p.closeInventory();
			Main.getMsgAPI().donateLink(p);
		}

		if (item.equals(Main.getGUIHandler().guiInit.clothes)) {
			Main.getGUIHandler().displayGUI(p, GUI.CLOTHES);
		}

		if (item.equals(Main.getGUIHandler().guiInit.boosters)) {
			Main.getGUIHandler().displayGUI(p, GUI.BOOSTERS);
		}

		if (item.equals(Main.getGUIHandler().guiInit.hats)) {
			Main.getGUIHandler().displayGUI(p, GUI.HATS);
		}

		if (item.equals(Main.getGUIHandler().guiInit.misc)) {
			Main.getGUIHandler().displayGUI(p, GUI.MISC);
		}
	}

	@EventHandler
	public void onClothesInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName().equals("xPlayDonator - Kläder"))
			return;

		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if ((!item.hasItemMeta()) || !item.getItemMeta().hasDisplayName())
			return;

		String combinedEnum = null;

		for (Entry<String, ItemStack> hm : Main.getCosmeticsHandler().currentClothes
				.entrySet()) {
			if (hm.getValue().getItemMeta().getDisplayName()
					.equals(item.getItemMeta().getDisplayName()))
				combinedEnum = hm.getKey();
		}

		if (combinedEnum == null)
			return;

		Cloth cloth = Main.getCosmeticsHandler().cosmeticsUtilities
				.getOrgCloth(combinedEnum);
		Armour armour = Main.getCosmeticsHandler().cosmeticsUtilities
				.getOrgArmour(combinedEnum);

		if (armour == null || cloth == null)
			return;

		if (armour == Armour.HELMET) {
			if (cloth == Cloth.CHANGEING)
				Main.getDataHandler().editPlayerCloth(p, 1);
			else if (cloth == Cloth.DISCO)
				Main.getDataHandler().editPlayerCloth(p, 2);
		} else
			Main.getDataHandler().editPlayerCloth(p, cloth, armour);

		Main.getMsgAPI().successClothes(p);
		Main.getCosmeticsHandler().setPlayerAllClothes(p);

	}

	@EventHandler
	public void onBoostersInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName()
				.equals("xPlayDonator - Boosters"))
			return;

		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if ((!item.hasItemMeta()) || !item.getItemMeta().hasDisplayName())
			return;

		Booster booster = null;
		String name = item.getItemMeta().getDisplayName();

		if (name.equals(ChatColor.GREEN + "x2 PlayMynt i Arbete"))
			booster = Booster.ARBETE;

		if (name.equals(ChatColor.GREEN + "Drop-party i Stad"))
			booster = Booster.DROP;

		if (name.equals(ChatColor.GREEN + "x2 PlayMynt i PvP"))
			booster = Booster.PVP;

		if (booster != null) {
			Main.getBoosterHandler().activateBooster(p, booster);
			closeInvWithoutOpen(p);
		}

	}

	@EventHandler
	public void onHatsInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName().equals("xPlayDonator - Huvuden"))
			return;

		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if ((!item.hasItemMeta()) || !item.getItemMeta().hasDisplayName())
			return;

		int helmetIndex = 3;
		for (ItemStack head : Main.getCosmeticsHandler().currentHeads) {
			if (item.getItemMeta().getDisplayName()
					.equals(head.getItemMeta().getDisplayName()))
				break;
			helmetIndex++;
		}

		if (helmetIndex >= Main.getCosmeticsHandler().currentHeads.size() + 3)
			return;

		Main.getMsgAPI().successClothes(p);
		Main.getDataHandler().editPlayerCloth(p, helmetIndex);
		Main.getCosmeticsHandler().setPlayerHelmet(p, helmetIndex);

	}

	@EventHandler
	public void onMiscInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName()
				.equals("xPlayDonator - Kommandon"))
			return;

		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if (item.equals(Main.getGUIHandler().guiInit.chatColor)) {
			Main.getFeatureManager().activateFeature(p, Feature.CHATCOLOR);
			closeInvWithoutOpen(p);
		}

		if (item.equals(Main.getGUIHandler().guiInit.chatShout)) {
			Main.getFeatureManager().activateFeature(p, Feature.CHATSHOUT);
			closeInvWithoutOpen(p);
		}

	}

	@EventHandler
	public void onAllInv(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;

		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getName().contains("xPlayDonator -"))
			return;

		e.setCancelled(true);

		Player p = (Player) e.getWhoClicked();
		p.updateInventory();
		ItemStack item = e.getCurrentItem();

		if (item.equals(Main.getGUIHandler().guiInit.closeGUI)) {
			if (e.getClickedInventory().getName()
					.equals("xPlayDonator - Huvudmeny"))
				Main.getGUIHandler().removeFromCurrentBrowsers(p);
			p.closeInventory();
		}

		if (item.equals(Main.getGUIHandler().guiInit.noOwn)) {
			Main.getGUIHandler().removeFromCurrentBrowsers(p);
			p.closeInventory();
			Main.getMsgAPI().donateLink(p);
		}

	}

	private void closeInvWithoutOpen(Player p) {
		Main.getGUIHandler().removeFromCurrentBrowsers(p);
		p.closeInventory();
	}
}
