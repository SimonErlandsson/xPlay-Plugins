package se.simonsigge.xplaypvp.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import se.simonsigge.xplaypvp.Main;

public class OldKits implements Listener {

	// Poseidon
	@EventHandler
	public void onWater(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Material b = e.getPlayer().getLocation().getBlock().getType();
		PotionEffect stre = PotionEffectType.INCREASE_DAMAGE.createEffect(100,
				1);
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if ((p.getInventory().getItem(i) != null)
					&& (p.getInventory().getItem(i).hasItemMeta())
					&& (p.getInventory().getItem(i).getItemMeta().hasLore())
					&& (p.getInventory().getItem(i).getItemMeta().getLore()
							.contains("xPoseidon"))
					&& ((b == Material.WATER) || (b == Material.STATIONARY_WATER))) {
				p.addPotionEffect(stre, true);
			}
		}
	}

	// Flygare
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = e.getPlayer().getInventory();
		ItemStack fw = new ItemStack(Material.FIREWORK, 1);
		Vector dir = p.getLocation().getDirection();
		Location loc = p.getLocation();
		
		if (p.getInventory().getItemInMainHand() == null)
			return;
		
		if ((p.getInventory().getItemInMainHand().getType() == Material.FIREWORK)
				&& ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e
						.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
			Main.getChatUtilities().sendPlayerMessage(p, ChatColor.GREEN + "" + ChatColor.BOLD
					+ ChatColor.ITALIC + "FLYG SOM EN FÅGEL! :D");
			e.setCancelled(true);
			inv.removeItem(new ItemStack[] { fw });
			p.playSound(loc, Sound.BLOCK_ANVIL_LAND, 10.0F, 1.0F);
			Vector vec = new Vector(dir.getX() * 0.8D, 0.8D, dir.getZ() * 0.8D);
			p.setVelocity(vec);
			p.setFallDistance(-3.0F);
			return;
		}
		if (p.getInventory().getItemInMainHand().getType() == Material.REDSTONE) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				p.addPotionEffect(new PotionEffect(
						PotionEffectType.INVISIBILITY, 100, 0));
				Main.getChatUtilities().sendPlayerMessage(p, ChatColor.GREEN + "" + ChatColor.BOLD
						+ "Du är nu osynlig i 5 sekunder!");
				p.getInventory()
						.removeItem(new ItemStack(Material.REDSTONE, 1));
			}
		}
	}

	// Berserker
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		PotionEffect stre = PotionEffectType.INCREASE_DAMAGE.createEffect(300,
				1);
		Location loc = p.getLocation();
		PotionEffect speed = PotionEffectType.SPEED.createEffect(300, 1);
		PotionEffect blind = PotionEffectType.BLINDNESS.createEffect(1200, 0);
		if (((e.getEntity() instanceof Player))
				&& (e.getEntity().getKiller() != null)) {
			for (int i = 0; i < killer.getInventory().getSize(); i++) {
				if ((killer.getInventory().getItem(i) != null)
						&& (killer.getInventory().getItem(i).hasItemMeta())
						&& (killer.getInventory().getItem(i).getItemMeta()
								.hasLore())
						&& (killer.getInventory().getItem(i).getItemMeta()
								.getLore().contains("xBerserker"))) {
					killer.addPotionEffect(stre);
					killer.addPotionEffect(blind);
					killer.addPotionEffect(speed);
					killer.playSound(loc, Sound.BLOCK_PISTON_EXTEND, 10.0F,
							1.0F);
					Main.getChatUtilities().sendPlayerMessage(killer, ChatColor.RED + "" + ChatColor.BOLD
							+ ChatColor.ITALIC
							+ "DU ÄR INFEKTERAD! DÖDA ALLA FIENDER!");
				}
			}
		}
	}

	// CookieMonster
	@EventHandler
	public void onEat(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Location loc = p.getLocation();
		PotionEffect speed = PotionEffectType.SPEED.createEffect(300, 9);
		PotionEffect liv = PotionEffectType.REGENERATION.createEffect(1200, 1);
		ItemStack cookie = new ItemStack(Material.COOKIE, 1);
		if (p.getInventory().getItemInMainHand() == null)
			return;
		
		if ((p.getInventory().getItemInMainHand().getType() == Material.COOKIE)
				&& ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e
						.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
			e.setCancelled(true);
			inv.removeItem(new ItemStack[] { cookie });
			p.playSound(loc, Sound.ENTITY_WITCH_HURT, 10.0F, 1.0F);
			Main.getChatUtilities().sendPlayerMessage(p, ChatColor.GOLD + "" + ChatColor.BOLD
					+ ChatColor.ITALIC + "KAKA! MHM.");
			p.addPotionEffect(liv);
			p.addPotionEffect(speed);
		}
	}

	// Repear
	@EventHandler  (priority = EventPriority.MONITOR)
	public void onDmg(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		
		if (((e.getEntity() instanceof Player))
				&& (e.getDamager().getType() == EntityType.PLAYER)) {
			Player p = (Player) e.getEntity();
			Player pd = (Player) e.getDamager();
			String pn = p.getName();
			PotionEffect liv = PotionEffectType.WITHER.createEffect(600, 1);
			
			if (pd.getInventory().getItemInMainHand() == null)
				return;
			
			ItemStack inv = pd.getInventory().getItemInMainHand();
			if ((inv.getType() == Material.DIAMOND_HOE) && (inv.hasItemMeta())
					&& (inv.getItemMeta().hasLore())
					&& (inv.getItemMeta().getLore().contains("xReaper"))) {
				e.setCancelled(true);
				if (!this.rpcd.contains(pd)) {
					Main.getChatUtilities().sendPlayerMessage(p, ChatColor.RED + "" + ChatColor.BOLD
							+ ChatColor.ITALIC
							+ "Du har blit slagen av en Reaper! AAH!");
					Main.getChatUtilities().sendPlayerMessage(pd, ChatColor.BLUE + "" + ChatColor.BOLD
							+ ChatColor.ITALIC + "Du har använt Reaper på "
							+ pn + ". DÖDA PERSONEN NU!");
					p.addPotionEffect(liv);
					this.rpcd.add(pd);
					this.d.setPlayer(pd);
					this.d.setList(this.rpcd);
					new Thread(this.d).start();
				} else {
					Main.getChatUtilities().sendPlayerMessage(pd, ChatColor.RED
							+ ""
							+ ChatColor.ITALIC
							+ "Du måste vänta 30 sekunder mellan varje använding av REAPER. :(");
				}
			}
		}
	}

	private List<Player> rpcd = new ArrayList<Player>();
	Countdown d = new Countdown();
}
