package se.simonsigge.xplaypvp.kits.now;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class Monk implements Listener {

	int cooldown = 5 * 20;

	@EventHandler
	public void onMonkInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Player) {
			Player clicker = event.getPlayer();
			Player clicked = (Player) event.getRightClicked();
			if (Main.getKitManager().isUsingKit(clicker, Kits.MONK)
					&& (clicker.getInventory().getItemInMainHand() != null)
					&& (clicker.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD)) {
				if (!Main.getKitManager().getCooldown()
						.isInCooldown(clicker, Kits.MONK)) {
					if ((clicked.getInventory().getItemInMainHand() != null)
							&& (clicked.getInventory().getItemInMainHand()
									.getType() != Material.AIR)) {
						int freeSlot = clicked.getInventory().firstEmpty();
						if (freeSlot > 0) {
							Random rand = new Random();
							if (freeSlot > 9) {
								ItemStack itemInHand = clicked.getInventory()
										.getItemInMainHand();
								clicked.getInventory().setItem(freeSlot,
										itemInHand);
								clicked.getInventory().setItemInMainHand(
										new ItemStack(Material.AIR));
							} else {
								int r = rand.nextInt(clicked.getInventory()
										.getSize());
								while (r == clicked.getInventory()
										.getHeldItemSlot()) {
									r = rand.nextInt(clicked.getInventory()
											.getSize());
								}
								ItemStack randomItem = clicked.getInventory()
										.getItem(r);
								if (randomItem == null) {
									randomItem = new ItemStack(Material.AIR);
								}
								ItemStack itemInHand = clicked.getInventory()
										.getItemInMainHand();
								clicked.getInventory().setItem(r, itemInHand);
								clicked.getInventory().setItemInMainHand(
										randomItem);
							}
						}

						Main.getKitManager().getCooldown()
								.addCooldown(clicker, Kits.MONK, cooldown);

						clicked.updateInventory();
						
						Main.getKillListener().setLastDamage(clicked, clicker);
					}
				} else {
					Main.getChatUtilities().sendPlayerMessage(
							clicker,
							ChatColor.RED + "Du måste vänta " + cooldown / 20
									+ " sekunder mellan varje användning.");
				}
			}
		}
	}

}
