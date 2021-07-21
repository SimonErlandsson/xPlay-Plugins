package net.Inverion.xPlaySlots;

import net.Inverion.xPlaySlots.GUI.BetSelection;
import net.Inverion.xPlaySlots.Machine.SlotMachine;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK
                || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.WALL_SIGN) {
                org.bukkit.block.Sign s = (org.bukkit.block.Sign) event
                        .getClickedBlock().getState();

                if (s.getLine(1).equals(
                        ChatColor.GREEN + "" + ChatColor.BOLD + "Slot Machine")) {
                    for (SlotMachine machine : Core.getInstance().slotMachines) {
                        if (machine.machineLocation.getX() == event
                                .getClickedBlock().getLocation().getX()
                                && machine.machineLocation.getY() == event
                                        .getClickedBlock().getLocation().getY()
                                && machine.machineLocation.getZ() == event
                                        .getClickedBlock().getLocation().getZ()) {
                            new BetSelection(event.getPlayer(),
                                    machine.unitWorth, machine);
                            break;
                        }
                    }
                }
            }
        }
    }

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (Core.getInstance().confirmationGUI.containsKey(event.getPlayer()
				.getName())) {
			Core.getInstance().confirmationGUI.remove(event.getPlayer()
					.getName());
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (Core.getInstance().confirmationGUI.containsKey(event.getPlayer()
				.getName())) {
			Core.getInstance().confirmationGUI.remove(event.getPlayer()
					.getName());
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getView().getTopInventory().getName()
				.equalsIgnoreCase("Bekräfta din satsning")
				|| event.getView().getTopInventory().getName()
						.equalsIgnoreCase("Hur mycket vill du satsa?")) {
			Core.getInstance().confirmationGUI.get(
					event.getWhoClicked().getName()).onClick(event);
			event.setCancelled(true);
		}
	}

	@EventHandler
    public void onItemFrameRotate(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
            if (Core.getInstance().isInRegion(
                    event.getRightClicked().getLocation())
                    && !event.getPlayer().hasPermission("xplaycore.admin")) {
                event.setCancelled(true);
            }
        }
    }

	@EventHandler
	public void onSignPlacement(SignChangeEvent event) {
		if (event.getPlayer().hasPermission("xplaycore.admin")) {
			if (event.getLine(0).equalsIgnoreCase("[xPlaySlots]")) {
				if (event.getLine(1).equalsIgnoreCase("create")) {
					int id = Core.getInstance().random.nextInt(10000);
					String key = "machines." + id + ".";
					Core.getInstance().getConfig()
							.set(key + "x", event.getBlock().getX());
					Core.getInstance().getConfig()
							.set(key + "y", event.getBlock().getY());
					Core.getInstance().getConfig()
							.set(key + "z", event.getBlock().getZ());
					Core.getInstance().getConfig().set(key + "unitworth", 1);
					Core.getInstance()
							.getConfig()
							.set(key + "world",
									event.getPlayer().getWorld().getName());
					Core.getInstance()
							.getConfig()
							.set(key + "facing",
									((org.bukkit.material.Sign) event
											.getBlock().getState().getData())
											.getFacing().toString());
					Core.getInstance().saveConfig();

					event.getPlayer()
							.sendMessage(
									Core.getInstance().chatTag
											+ ChatColor.GREEN
											+ "Du har skapat en slot machine och har nu lagts till!");

					SlotMachine slotMachine = new SlotMachine(event.getBlock()
							.getLocation(), ((org.bukkit.material.Sign) event
							.getBlock().getState().getData()).getFacing(), 1,
							id);

					slotMachine.doMachineCreate();

					Core.getInstance().slotMachines.add(slotMachine);

					return;
				} else if (event.getLine(1).equalsIgnoreCase("delete")) {
					boolean deleted = false;

					for (SlotMachine machine : Core.getInstance().slotMachines) {
						if (machine.machineLocation.getX() == event.getBlock()
								.getLocation().getX()
								&& machine.machineLocation.getY() == event
										.getBlock().getLocation().getY()
								&& machine.machineLocation.getZ() == event
										.getBlock().getLocation().getZ()) {
							event.getBlock().setType(Material.AIR);
							Core.getInstance().getConfig()
									.set("machines." + machine.machineId, null);
							Core.getInstance().slotMachines.remove(machine);
							Core.getInstance().saveConfig();
							deleted = true;
						}
					}

					if (deleted) {
						event.getPlayer()
								.sendMessage(
										Core.getInstance().chatTag
												+ ChatColor.GREEN
												+ "Du tog precis bort en slot machine!");
					} else {
						event.getPlayer()
								.sendMessage(
										Core.getInstance().chatTag
												+ ChatColor.RED
												+ "Hoppsan! Här fanns det ingen slot machine att ta bort!");
					}
				}
			}
		}
	}
	
	@EventHandler
    public void onSignBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("xplaycore.admin")
                && event.getBlock().getType() == Material.WALL_SIGN
                && event.getPlayer().isSneaking()) {
            org.bukkit.block.Sign s = (org.bukkit.block.Sign) event.getBlock()
                    .getState();

            if (s.getLine(1).equals(
                    ChatColor.GREEN + "" + ChatColor.BOLD + "Slot Machine")) {
                for (SlotMachine machine : Core.getInstance().slotMachines) {
                    if (machine.machineLocation.getX() == event.getBlock()
                            .getLocation().getX()
                            && machine.machineLocation.getY() == event
                                    .getBlock().getLocation().getY()
                            && machine.machineLocation.getZ() == event
                                    .getBlock().getLocation().getZ()) {
                        event.getBlock().setType(Material.AIR);

                        Core.getInstance().getConfig()
                                .set("machines." + machine.machineId, null);
                        Core.getInstance().slotMachines.remove(machine);
                        Core.getInstance().saveConfig();

                        event.getPlayer()
                                .sendMessage(
                                        Core.getInstance().chatTag
                                                + ChatColor.GREEN
                                                + "Du tog precis bort en slot machine!");
                    }
                }
            }
        }
    }

	public boolean stringIsInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
