package net.Inverion.xPlaySlots.GUI;

import me.nonamesldev.itembuilder.ItemBuilder;
import net.Inverion.xPlaySlots.Core;
import net.Inverion.xPlaySlots.Machine.SlotMachine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class BetSelection extends BetGUI {

	public BetSelection(Player p, int amount, SlotMachine machine) {
		super(p, amount, machine);
	}

	public void open() {
		Inventory i = Bukkit
				.createInventory(
						null,
						(Core.getInstance().betAmounts.size() > 7 ? (Core
								.getInstance().betAmounts.size() > 14 ? 27 : 18)
								: 9), "Hur mycket vill du satsa?");

		int current = 0;

		for (int amount : Core.getInstance().betAmounts) {
			short color = 13;

			if (current == 7 || current == 16) {
				current += 2;
			}

			if (current >= 9 && current <= 15) {
				color = 3;
			} else if (current > 15) {
				color = 11;
			}

			current++;

			i.setItem(
					current,
					new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
							.setDurability(color)
							.setName(
									ChatColor.GREEN + "" + amount + " PlayMynt")
							.toItemStack());
		}

		Bukkit.getPlayerExact(playerName).openInventory(i);
	}

	public void onClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null
				&& event.getCurrentItem().getItemMeta() != null
				&& event.getCurrentItem().getItemMeta().getDisplayName() != null) {
			if (event.getCurrentItem().getItemMeta().getDisplayName()
					.contains("PlayMynt")) {
				new BetConfirmation((Player) event.getWhoClicked(),
						Integer.parseInt(ChatColor.stripColor(
								event.getCurrentItem().getItemMeta()
										.getDisplayName()).replaceAll(
								" PlayMynt", "")), this.machine);
			}
		}
	}

}
