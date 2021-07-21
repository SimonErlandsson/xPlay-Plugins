package se.simonsigge.xplaypvp.kits.now;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class Viper implements Listener {

	@EventHandler  (priority = EventPriority.MONITOR)
	public void onViperDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		
		if ((event.getDamager() != null)
				&& ((event.getEntity() instanceof Player))
				&& ((event.getDamager() instanceof Player))) {
			Player damaged = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			if ((damager.getInventory().getItemInMainHand() != null)
					&& (damager.getInventory().getItemInMainHand().getType() == Material.STONE_SWORD)
					&& (Main.getKitManager().isUsingKit(damager, Kits.VIPER))
					&& (!Main.getKitManager()
							.isUsingKit(damaged, Kits.VIPER)
							&& (Math.random() > 0.4D) && (Math.random() < 0.6D))) {
				damaged.addPotionEffect(new PotionEffect(
						PotionEffectType.POISON, 5 * 20, 0));
				Main.getKillListener().setLastDamage(damaged, damager);
			}
		}
	}

}
