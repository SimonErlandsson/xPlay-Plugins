package net.Inverion.xPlaySlots.GUI;

import net.Inverion.xPlaySlots.Core;
import net.Inverion.xPlaySlots.Machine.SlotMachine;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BetGUI {

	public int amount;
	public String playerName;
	public SlotMachine machine;

	public BetGUI(Player p, int amount, SlotMachine machine) {
		this.playerName = p.getName();
		this.amount = amount;
		this.machine = machine;
		
		open();
		
		Core.getInstance().confirmationGUI.put(p.getName(), this);
	}

	public void open() {

	}

	public void onClick(InventoryClickEvent e) {
		
	}

}
