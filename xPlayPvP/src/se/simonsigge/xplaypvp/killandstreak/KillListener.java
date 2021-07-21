package se.simonsigge.xplaypvp.killandstreak;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import se.simonsigge.xplaypvp.Main;
import se.simonsigge.xplaypvp.PluginSettings;

public class KillListener implements Listener {
	
	HashMap<Player, Player> lastDamage;
	
	public KillListener() {
		lastDamage = new HashMap<Player, Player>();
	}
	
	public void onAnyDeath(Player killed, Player killer) {
		
		//Announce death killstreak
		double pxp = killed.getLevel();
		DecimalFormat df = new DecimalFormat("###.#");
		if ((killer != null) && (pxp > 9.0D)) {
			Main.getChatUtilities().sendServerMessage(
					ChatColor.RED + "" + ChatColor.BOLD + ChatColor.ITALIC
							+ killed.getName() + ChatColor.AQUA + " blev dödad av: "
							+ ChatColor.LIGHT_PURPLE + ChatColor.ITALIC
							+ killer.getName() + ChatColor.AQUA
							+ " och hade en killstreak på: " + df.format(pxp)
							+ ".");
		}
		
		if (killer == null || killer.getName() == killed.getName())
			return;
		
		killer.setHealth(20);
		
		//Add and announce killstreak
		int killstreak = killer.getLevel();
		killstreak++;
		if ((killstreak == 5) || (killstreak == 10) || (killstreak == 15)
				|| (killstreak == 20) || (killstreak == 25)
				|| (killstreak == 30) || (killstreak == 35)
				|| (killstreak == 40) || (killstreak == 45)
				|| (killstreak == 50)) {
			Main.getChatUtilities().sendServerMessage(
					"&a&l" + killer.getName() + "&6 har &c" + killstreak
							+ "&6 i killstreak!");
		}
		killer.setLevel(killstreak);
		
		//Pay and announce
		if (killstreak == 3) {
			Main.getChatUtilities().sendPlayerMessage(
					killer,
					"&2Du har en killstreak. Du får nu &c"
							+ PluginSettings.PAYOUT_STREAK
							+ "&a PlayMynt per kill!");
			killer.playSound(killer.getLocation(), Sound.ENTITY_COW_HURT, 1.0F, 1.0F);
		} else
			killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 1.0F);

		int amount = 0;
		if (killstreak <= PluginSettings.REQUIRED_KILLS_STREAK)
			amount = PluginSettings.PAYOUT_NONE_STREAK;
		else
			amount = PluginSettings.PAYOUT_STREAK;
		
		if (PluginSettings.BOOSTERACTIVE) {
			amount *= 2;
			Main.getChatUtilities().sendPlayerMessage(killer, "§aTack vare §2" + PluginSettings.BOOSTERHOST + "§as booster fick du dubbelt betalt för din kill!");
		}
		
		Main.getEconomy().depositPlayer(killer, amount);

		Main.getChatUtilities().sendActionBarMessage(
				killer,
				"&7Du fick &a" + amount + " &6&lPlayMynt! &7(&e"
						+ Main.getEconomy().getBalance(killer) + "&7)");
		
		if (lastDamage.containsKey(killed))
			lastDamage.remove(killed);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		Player killer = null;
		
		if (lastDamage.containsKey(killed))
			killer = lastDamage.get(killed);
		
		if (e.getEntity().getKiller() != null) {
			if (e.getEntity().getKiller() instanceof Player)
				killer = e.getEntity().getKiller();
		}

		onAnyDeath(killed, killer);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (lastDamage.containsKey(e.getPlayer()))
			lastDamage.remove(e.getPlayer());
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		
		if (e.getDamager() == null)
			return;
		
		if (!(e.getEntity() instanceof Player))
			return;
		
		if (!(e.getDamager() instanceof Player))
			return;
		
		setLastDamage((Player) e.getEntity(), (Player) e.getDamager());
	}
	
	public void setLastDamage(Player damaged, Player damager) {
		lastDamage.put(damaged, damager);
	}

}
