package net.Inverion.xPlaySlots.GUI;

import java.util.Arrays;

import me.nonamesldev.itembuilder.ItemBuilder;
import net.Inverion.xPlaySlots.Core;
import net.Inverion.xPlaySlots.Machine.SlotMachine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BetConfirmation extends BetGUI {

	public BetConfirmation(Player p, int amount, SlotMachine machine) {
		super(p, amount, machine);
	}

	public void open() {
		Inventory i = Bukkit.createInventory(null, 27, "Bekräfta din satsning");

		ItemStack confirm = new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
				.setDurability((short) 13)
				.setName(ChatColor.GREEN + "Bekräfta satsning!")
				.setLore(
						Arrays.asList(ChatColor.GRAY + "" + amount
								+ " PlayMynt")).toItemStack();

		ItemStack cancel = new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
				.setDurability((short) 14)
				.setName(ChatColor.RED + "Avbryt satsning!").toItemStack();

		i.setItem(11, confirm);
		i.setItem(15, cancel);

		Bukkit.getPlayerExact(playerName).openInventory(i);
	}

	public void onClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null
				&& event.getCurrentItem().getItemMeta() != null
				&& event.getCurrentItem().getItemMeta().getDisplayName() != null) {
			if (event.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase(ChatColor.GREEN + "Bekräfta satsning!")) {
				Player p = (Player) event.getWhoClicked();
				machine.placeBet(p, amount);
				p.closeInventory();
			} else {
				event.getWhoClicked().closeInventory();
			}

			Core.getInstance().confirmationGUI.remove(event.getWhoClicked().getName());
		}
	}

}
