package me.Simonsigge.xPlayDonator.Events;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Permissions;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

	public PlayerListener() {

	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (!e.getPlayer().hasPermission(Permissions.DONATOR_PERM))
			return;

		Main.getCosmeticsHandler().removePlayerFromUpdate(e.getPlayer());
		Main.getGUIHandler().removeFromCurrentBrowsers(e.getPlayer());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!e.getWhoClicked().hasPermission(Permissions.DONATOR_PERM))
			return;

		if (((e.getCurrentItem() == null))
				|| (!e.getCurrentItem().hasItemMeta())
				|| !e.getCurrentItem().getItemMeta().hasDisplayName())
			return;

		if (e.getInventory().getName().equals("xPlayDonator - Kläder")
				|| e.getInventory().getName().equals("xPlayDonator - Huvuden"))
			return;

		for (Entry<String, ItemStack> item : Main.getCosmeticsHandler().currentClothes
				.entrySet())
			if (item.getValue().getItemMeta().getDisplayName()
					.equals(e.getCurrentItem().getItemMeta().getDisplayName())) {
				Player p = (Player) e.getWhoClicked();
				Armour armour = Main.getCosmeticsHandler().cosmeticsUtilities
						.getOrgArmour(item.getKey());
				Main.getCosmeticsHandler().setEmptyPlayerCloth(p, armour);

				if (armour == Armour.HELMET)
					Main.getDataHandler().editPlayerCloth(p, 0);
				else
					Main.getDataHandler()
							.editPlayerCloth(p, Cloth.NONE, armour);
				e.setCancelled(true);
				return;
			}

		for (ItemStack item : Main.getCosmeticsHandler().currentHeads)
			if (item.getItemMeta().getDisplayName()
					.equals(e.getCurrentItem().getItemMeta().getDisplayName())) {
				Player p = (Player) e.getWhoClicked();
				Main.getCosmeticsHandler()
						.setEmptyPlayerCloth(p, Armour.HELMET);
				Main.getDataHandler().editPlayerCloth(p, 0);
				e.setCancelled(true);
				return;
			}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		if (!e.getPlayer().hasPermission(Permissions.DONATOR_PERM))
			return;

		for (ItemStack item : Main.getCosmeticsHandler().currentClothes
				.values())
			if (item.getItemMeta()
					.getDisplayName()
					.equals(e.getItemDrop().getItemStack().getItemMeta()
							.getDisplayName()))
				e.setCancelled(true);

		for (ItemStack item : Main.getCosmeticsHandler().currentHeads)
			if (item.getItemMeta()
					.getDisplayName()
					.equals(e.getItemDrop().getItemStack().getItemMeta()
							.getDisplayName()))
				e.setCancelled(true);
	}


	@EventHandler
	public void onPlayerDeath(EntityDeathEvent e) {
		if (e.getEntityType() != EntityType.PLAYER)
			return;

		ArrayList<ItemStack> itemRemove = new ArrayList<ItemStack>();

		for (Entry<String, ItemStack> item : Main.getCosmeticsHandler().currentClothes
				.entrySet())
			for (ItemStack dropped : e.getDrops())
				if (((dropped.hasItemMeta())
						&& (dropped.getItemMeta().hasDisplayName()) && item
						.getValue().getItemMeta().getDisplayName()
						.equals(dropped.getItemMeta().getDisplayName()))) {
					itemRemove.add(dropped);

					Player p = (Player) e.getEntity();
					Armour armour = Main.getCosmeticsHandler().cosmeticsUtilities
							.getOrgArmour(item.getKey());

					if (armour == Armour.HELMET)
						Main.getDataHandler().editPlayerCloth(p, 0);
					else
						Main.getDataHandler().editPlayerCloth(p, Cloth.NONE,
								armour);
				}

		for (ItemStack item : Main.getCosmeticsHandler().currentHeads)
			for (ItemStack dropped : e.getDrops())
				if (((dropped.hasItemMeta())
						&& (dropped.getItemMeta().hasDisplayName()) && item
						.getItemMeta().getDisplayName()
						.equals(dropped.getItemMeta().getDisplayName()))) {
					itemRemove.add(dropped);

					Player p = (Player) e.getEntity();
					Main.getDataHandler().editPlayerCloth(p, 0);
				}

		for (ItemStack remove : itemRemove) {
			e.getDrops().remove(remove);

		}
	}

}
