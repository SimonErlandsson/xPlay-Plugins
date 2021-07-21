package net.Inverion.xPlaySlots;

import io.netty.util.internal.ConcurrentSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.Inverion.xPlaySlots.Animation.Images;
import net.Inverion.xPlaySlots.GUI.BetGUI;
import net.Inverion.xPlaySlots.Machine.Combinations;
import net.Inverion.xPlaySlots.Machine.SlotMachine;
import net.Inverion.xPlaySlots.Machine.Symbol;
import net.Inverion.xPlaySlots.Machine.TickType;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Core extends JavaPlugin {

	private static Core instance;

	public static Core getInstance() {
		return instance;
	}

	public String chatTag = ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.WHITE + "" + ChatColor.BOLD
			+ "xPlayCasino" + ChatColor.BLACK + "" + ChatColor.BOLD + "]" + ChatColor.WHITE + " -> ";

	public Economy economy;
	public Random random;
	public ConcurrentSet<SlotMachine> slotMachines;
	public HashMap<String, BetGUI> confirmationGUI;
	public ArrayList<String> playersInBet;
	public ArrayList<Integer> betAmounts;

	public Location top, low;

	public BukkitRunnable mainTask;

	public void onEnable() {
		random = new Random();
		slotMachines = new ConcurrentSet<SlotMachine>();
		instance = this;
		confirmationGUI = new HashMap<String, BetGUI>();
		playersInBet = new ArrayList<String>();
		betAmounts = new ArrayList<Integer>();

		top = new Location(Bukkit.getWorld("xPlayStad"), -40, 51, -43);
		low = new Location(Bukkit.getWorld("xPlayStad"), -10, 61, -20);

		Bukkit.getPluginManager().registerEvents(new Events(), this);

		getConfig().options().copyDefaults(true);

		saveConfig();

		setupEconomy();
		load();
		startMainTask();
	}

	public void onDisable() {
		instance = null;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;

		if (command.getName().equalsIgnoreCase("slotsreload")) {
			if (p.hasPermission("xplaycore.admin")) {
				for (SlotMachine machine : slotMachines) {
					machine.reset();
				}

				Images.getInstance().renderAll();

				playersInBet.clear();
				betAmounts.clear();

				reloadConfig();

				load();

				p.sendMessage(chatTag + ChatColor.GREEN + "Slots har nu reloadats!");
			} else {
				p.sendMessage(chatTag + ChatColor.RED + "Du har inte tillåtelse till att använda detta kommando!");
			}
		}

		if (command.getName().equalsIgnoreCase("slotsframe")) {
            if (p.hasPermission("xplaycore.admin")) {
                if (args.length == 1) {
                    MapView map = Bukkit.getServer().createMap(p.getWorld());

                    for (MapRenderer renderer : map.getRenderers()) {
                        map.removeRenderer(renderer);
                    }

                    MapRenderer renderer = new StaticFrameRenderer();
                    ((StaticFrameRenderer) renderer).img = args[0];
                    map.addRenderer(renderer);

                    Location loc = p.getTargetBlock(null, 10).getLocation();

                    ItemFrame itemFrame = (ItemFrame) p.getWorld().spawn(loc,
                            ItemFrame.class);
                    itemFrame.setRotation(Rotation.NONE);
                    itemFrame.setFacingDirection(BlockFace.EAST);
                    itemFrame.setItem(new ItemStack(Material.MAP, 1, map.getId()));

                    int id = random.nextInt(10000);

                    getConfig().set("frames." + id + ".image", args[0]);
                    getConfig().set("frames." + id + ".x", loc.getBlockX());
                    getConfig().set("frames." + id + ".y", loc.getBlockY());
                    getConfig().set("frames." + id + ".z", loc.getBlockZ());

                    saveConfig();
                } else {
                    p.sendMessage(chatTag + ChatColor.RED
                            + "/slotsframe <bild>");
                }
            } else {
                p.sendMessage(chatTag
                        + ChatColor.RED
                        + "Du har inte tillåtelse till att använda detta kommando!");
            }
        }
		
		return false;
	}

	public SlotMachine get(int id) {
		for (SlotMachine machine : slotMachines) {
			if (machine.machineId == id) {
				return machine;
			}
		}

		return null;
	}

	public void load() {
		reloadConfig();
		slotMachines.clear();
		
		int total = 0;

		for (Combinations combination : Combinations.values()) {
			combination.from = total;
			total += combination.chance;
			combination.to = total;
		}

		Combinations.total = total;

		if (instance.getConfig().getConfigurationSection("machines") != null) {
			for (String s : instance.getConfig()
					.getConfigurationSection("machines").getKeys(false)) {
				ConfigurationSection section = instance.getConfig()
						.getConfigurationSection("machines." + s);

				int x = section.getInt("x");
				int y = section.getInt("y");
				int z = section.getInt("z");

				slotMachines.add(new SlotMachine(new Location(Bukkit
						.getWorld(section.getString("world")), x, y, z),
						BlockFace.valueOf(section.getString("facing")), section
								.getInt("unitworth"), Integer.parseInt(s)));
			}
		}

		if (instance.getConfig().getConfigurationSection("payouts") != null) {
			for (String s : instance.getConfig()
					.getConfigurationSection("payouts").getKeys(false)) {
				if (Symbol.valueOf(s) != null) {
					Symbol.valueOf(s).setPayout(
							instance.getConfig().getInt("payouts." + s));
				}
			}
		}

		if (instance.getConfig().getIntegerList("betamounts") != null) {
			for (int i : instance.getConfig().getIntegerList("betamounts")) {
				betAmounts.add(i);
			}
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Entity entity : new Location(Bukkit.getWorld("xPlayStad"),
						-19, 58, -25)
						.getBlock()
						.getWorld()
						.getNearbyEntities(
								new Location(Bukkit.getWorld("xPlayStad"), -19,
										58, -25), 30, 30, 30)) {
					if (!(entity instanceof Player)) {
						entity.remove();
					}
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						for (String s : instance.getConfig()
								.getConfigurationSection("frames")
								.getKeys(false)) {
							ConfigurationSection section = instance.getConfig()
									.getConfigurationSection("frames." + s);

							Location loc = new Location(
									Bukkit.getWorld("xPlayStad"),
									section.getInt("x"), section.getInt("y"),
									section.getInt("z"));

							MapView map = Bukkit.getServer().createMap(
									loc.getWorld());

							for (MapRenderer renderer : map.getRenderers()) {
								map.removeRenderer(renderer);
							}

							MapRenderer renderer = new StaticFrameRenderer();
							((StaticFrameRenderer) renderer).img = section
									.getString("image");
							map.addRenderer(renderer);

							loc.getBlock().setType(Material.WALL_SIGN);

							ItemFrame itemFrame = (ItemFrame) loc.getWorld()
									.spawn(loc, ItemFrame.class);
							itemFrame.setRotation(Rotation.NONE);
							itemFrame.setFacingDirection(BlockFace.EAST);
							itemFrame.setItem(new ItemStack(Material.MAP, 1,
									map.getId()));

							loc.getBlock().setType(Material.AIR);
						}
					}
				}.runTaskLater(Core.getInstance(), 100);
			}
		}.runTaskLater(this, 250);
	}

	public void startMainTask() {
		mainTask = new BukkitRunnable() {
			int currentTick = 0;

			public void run() {
				currentTick++;

				for (SlotMachine machine : slotMachines) {
					if (currentTick % 5 == 1) {
						machine.blink = !machine.blink;
						machine.updateSign();
					}
					if (currentTick == 20) {
						machine.onTick(TickType.TWENTY_TICKS);
					}
					if (currentTick % 10 == 1) {
						machine.onTick(TickType.TEN_TICKS);
					}
				}

				if (currentTick == 20) {
					currentTick = 0;
				}
			}
		};
		mainTask.runTaskTimer(this, 1, 1);
	}

	public boolean isInRegion(Location toCheck) {
		return toCheck.getX() >= (Math.min(top.getBlockX(), low.getBlockX()))
				&& toCheck.getX() <= (Math.max(top.getBlockX(), low.getBlockX()))
				&& toCheck.getY() >= (Math.min(top.getBlockY(), low.getBlockY()))
				&& toCheck.getY() <= (Math.max(top.getBlockY(), low.getBlockY()))
				&& toCheck.getZ() >= (Math.min(top.getBlockZ(), low.getBlockZ()))
				&& toCheck.getZ() <= (Math.max(top.getBlockZ(), low.getBlockZ()));
	}

	public void broadcast(String message) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isInRegion(p.getLocation())) {
				p.sendMessage(message);
			}
		}
	}

	public boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null) {
			return false;
		}

		economy = rsp.getProvider();

		return economy != null;
	}

}
