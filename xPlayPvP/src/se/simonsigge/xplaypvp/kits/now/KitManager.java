package se.simonsigge.xplaypvp.kits.now;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.kitselector.Kits;

public class KitManager {

	private Cooldown cooldown;

	public KitManager() {
		cooldown = new Cooldown();
		
		Main.getInstance().getServer().getPluginManager().registerEvents(new Switcher(), Main.getInstance());
		Main.getInstance().getServer().getPluginManager().registerEvents(new Viper(), Main.getInstance());
		Main.getInstance().getServer().getPluginManager().registerEvents(new Fisherman(), Main.getInstance());
		Main.getInstance().getServer().getPluginManager().registerEvents(new Monk(), Main.getInstance());
		Main.getInstance().getServer().getPluginManager().registerEvents(new Stomper(), Main.getInstance());
	}

	public Cooldown getCooldown() {
		return cooldown;
	}

	public boolean isUsingKit(Player p, Kits kit) {
		ItemStack[] inv = p.getInventory().getContents();
		boolean isUsing = false;

		for (ItemStack item : inv) {
			if (item != null)
				if (item.getType() != Material.MUSHROOM_SOUP)
					if (item.hasItemMeta())
						if (item.getItemMeta().hasLore()) {
							List<String> loreList = item.getItemMeta()
									.getLore();
							for (String lore : loreList) {
								if (lore.toLowerCase().contains(
										kit.getName().toLowerCase()))
									isUsing = true;
							}

						}
		}

		return isUsing;
	}

}
