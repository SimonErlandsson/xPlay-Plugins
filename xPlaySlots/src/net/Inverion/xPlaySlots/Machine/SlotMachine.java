package net.Inverion.xPlaySlots.Machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.Inverion.xPlaySlots.Core;
import net.Inverion.xPlaySlots.Animation.AnimationRenderer;
import net.Inverion.xPlaySlots.Animation.Images;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Firework;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

public class SlotMachine {

	// Slot machine
	public Location machineLocation;
	public BlockFace signFace;
	public int unitWorth, time, machineId;
	public MachineState machineState;
	public String slotPlayer;

	// Animation saker
	public ArrayList<Reel> reels;
	public ArrayList<Symbol> endResults;
	public int currentRow;
	public ItemFrame itemFrame;
	public MapView mapView;
	public MapCanvas mapCanvas;
	public boolean blink;

	public SlotMachine(Location machineLocation, BlockFace signFace,
            int unitWorth, int machineId) {
        this.machineLocation = machineLocation;
        this.signFace = signFace;
        this.unitWorth = unitWorth;
        this.time = 0;
        this.machineState = MachineState.WAITING;
        this.slotPlayer = "";
        this.reels = new ArrayList<Reel>();
        this.currentRow = 0;
        this.machineId = machineId;
        this.blink = false;
        this.endResults = new ArrayList<Symbol>();

        reset();
    }


	public void draw() {
		if (mapCanvas != null) {
			Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), new Runnable() {
				@Override
				public void run() {
					mapCanvas.drawImage(0, 0, Images.getInstance().get("layout"));

					int x = 6;
					int y = 7;

					for (int i = 1; i <= 3; i++) {
						ArrayList<ReelItem> items = getReelItems(i);

						for (int i2 = 2; i2 <= 5; i2++) {
							if (getReelItemAt(i2, items) != null) {
								mapCanvas.drawImage(y, x, getReelItemAt(i2, items).item.getImage());
								x += 42;
							}
						}

						x = 6;
						y += 41;
					}
				}
			});
		}
	}

	public ArrayList<ReelItem> getReelItems(int row) {
		for (Reel reel : reels) {
			if (reel.row == row) {
				return reel.items;
			}
		}

		return null;
	}

	public ReelItem getReelItemAt(int position, ArrayList<ReelItem> items) {
		for (ReelItem item : items) {
			if (item.position == position) {
				return item;
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	public void build() {
		machineLocation.getBlock().setType(Material.AIR);

		MapView map = Bukkit.getServer().createMap(machineLocation.getWorld());

		mapView = map;

		for (MapRenderer renderer : map.getRenderers()) {
			map.removeRenderer(renderer);
		}

		MapRenderer renderer = new AnimationRenderer();
		((AnimationRenderer) renderer).slotMachine = this;
		((AnimationRenderer) renderer).set = false;
		map.addRenderer(renderer);

		itemFrame = (ItemFrame) machineLocation.getWorld().spawn(machineLocation, ItemFrame.class);
		itemFrame.setRotation(Rotation.NONE);
		itemFrame.setFacingDirection(signFace);
		itemFrame.setItem(new ItemStack(Material.MAP, 1, map.getId()));
	}

	public void roll() {
		for (Reel reel : reels) {
			if (currentRow < reel.row) {
				scroll(reel);
			}
		}

		click();

		draw();
	}
	
	public void click() {
		if (Bukkit.getPlayerExact(slotPlayer) != null) {
			Player p = Bukkit.getPlayerExact(slotPlayer);
			p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
		}
	}


	public void scroll(Reel reel) {
		for (ReelItem reelItem : reel.items) {
			reelItem.position++;

			if (reelItem.position == Symbol.values().length + 1) {
				reelItem.position = 1;
			}
		}
	}

	public void onTick(TickType tickType) {
		if (machineState != MachineState.WAITING) {
			if (machineState == MachineState.PRE_ROLL) {
				if (tickType == TickType.TWENTY_TICKS) {
					time--;
					updateSign();

					if (time == 0) {
						start();
					}
				}
			} else if (machineState == MachineState.POST_ROLL) {
				if (tickType == TickType.TWENTY_TICKS) {
					time--;

					if (time == 0) {
						reset();
					}
				}
			} else if (machineState == MachineState.ROLLING) {
				if (tickType == TickType.TEN_TICKS) {
					if (time <= 0) {
						if (endResults.get(currentRow).equals(
								getSymbolAtRow(currentRow + 1))) {
							if (Bukkit.getPlayerExact(slotPlayer) != null
									&& currentRow < 2) {
								Player p = Bukkit.getPlayerExact(slotPlayer);
								p.playSound(p.getLocation(),
										Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
							}

							currentRow++;
							time = Core.getInstance().random.nextInt(3) + 3;

							if (currentRow > 2) {
								end();
							} else {
								roll();
							}

							return;
						}
					}

					roll();
				}

				if (tickType == TickType.TWENTY_TICKS) {
					time--;
				}
			} else if (machineState == MachineState.END_ROLL) {
				end();
			}
		}
	}


	public Symbol getSymbolAtRow(int row) {
		for (Reel reel : reels) {
			if (reel.row == row) {
				for (ReelItem item : reel.items) {
					if (item.position == 3) {
						return item.item;
					}
				}
			}
		}

		return null;
	}

	public int round(double d) {
		return (int) ((d + 999) / 1000) * 1000;
	}

	public void end() {
		click();

		int payout = 0;
		boolean jackpot = false;

		Symbol one = getSymbolAtRow(1);
		Symbol two = getSymbolAtRow(2);
		Symbol three = getSymbolAtRow(3);

		if (one.equals(two) && two.equals(three)) {
			payout = (int) (unitWorth * one.getPayout() * 6);

			if (one.equals(Symbol.DIAMOND)) {
				jackpot = true;
			}
		} else if (one.equals(two)) {
			payout = (int) (unitWorth * (two.getPayout()));
		} else if (two.equals(three)) {
			payout = (int) (unitWorth * (two.getPayout()));
		}

		if (payout == unitWorth) {
			if (Bukkit.getPlayerExact(slotPlayer) != null) {
				Player p = Bukkit.getPlayerExact(slotPlayer);
				p.sendTitle(ChatColor.YELLOW + "Pengarna tillbaka!", "", 10,
						40, 10);
				p.sendMessage(ChatColor.YELLOW + "Du fick pengarna tillbaka!");
				p.playSound(p.getLocation(), Sound.ENTITY_SLIME_DEATH, 100, 100);
			}

			Core.getInstance().economy.depositPlayer(slotPlayer, payout);
		} else if (payout > 0) {
			if (Bukkit.getPlayerExact(slotPlayer) != null) {
				Player p = Bukkit.getPlayerExact(slotPlayer);
				p.sendTitle((jackpot ? ChatColor.GOLD : ChatColor.GREEN)
						+ "Du vann! Grattis!", ChatColor.AQUA + "Du vann "
						+ payout + " PlayMynt!", 10, 40, 10);
				p.sendMessage((jackpot ? ChatColor.GOLD : ChatColor.GREEN)
						+ "Du vann! "
						+ (jackpot ? "(jackpot vinst - " + (payout / unitWorth)
								+ "x!) " : "") + "Grattis!");

				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100,
						100);
			}

			Core.getInstance().economy.depositPlayer(slotPlayer, payout);

			Core.getInstance()
					.broadcast(
							Core.getInstance().chatTag
									+ (jackpot ? ChatColor.GOLD + ""
											+ ChatColor.BOLD + "JACKPOT! " : "")
									+ ChatColor.DARK_GREEN + slotPlayer + " "
									+ ChatColor.GREEN + "vann precis "
									+ ChatColor.DARK_GREEN + payout
									+ ChatColor.GREEN
									+ "$ i en Slot Machine! Grattis!");

			if (jackpot) {
				Firework fw = (Firework) machineLocation.getWorld().spawn(
						machineLocation, Firework.class);

				FireworkMeta fm = fw.getFireworkMeta();
				fm.addEffect(FireworkEffect.builder().flicker(false)
						.trail(true).with(Type.BALL).withColor(Color.GREEN)
						.withFade(Color.BLUE).build());
				fw.setFireworkMeta(fm);
				fw.detonate();
			}
		} else {
			if (Bukkit.getPlayerExact(slotPlayer) != null) {
				Player p = Bukkit.getPlayerExact(slotPlayer);
				p.sendTitle(ChatColor.RED + "Du förlorade! Synd! :(", "", 10,
						40, 10);
				p.sendMessage(ChatColor.RED + "Du förlorade! Synd! :(");
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
			}
		}

		Core.getInstance().playersInBet.remove(slotPlayer);

		machineState = MachineState.POST_ROLL;
		time = 4;
	}
	
	public Symbol randomSymbol(Symbol toNotInclude) {
		List<Symbol> symbols = Arrays.asList(Symbol.values());
		Collections.shuffle(symbols);

		for (Symbol symbol : symbols) {
			if (toNotInclude == null || symbol != toNotInclude) {
				return symbol;
			}
		}

		return Symbol.DIAMOND;
	}
	
	public Symbol randomSymbol() {
		return randomSymbol(null);
	}

	public void updateSign() {
		if (machineState == MachineState.ROLLING
				|| machineState == MachineState.POST_ROLL
				|| machineState == MachineState.END_ROLL) {
			return;
		}

		if (machineLocation.getBlock().getType() == Material.AIR) {
			placeSign();
		}

		Sign sign = (Sign) machineLocation.getBlock().getState();

		if (machineState != MachineState.WAITING) {
			sign.setLine(0, ChatColor.GREEN + "" + ChatColor.BOLD + "" + time
					+ "...");
			sign.setLine(1, ChatColor.BOLD + slotPlayer);
			sign.setLine(2, ChatColor.BOLD + "" + unitWorth + "$");
			sign.setLine(3, (blink ? ChatColor.GREEN + ""
					: ChatColor.DARK_GREEN + "") + "<---------->");
		} else {
			sign.setLine(0, (blink ? ChatColor.GREEN + ""
					: ChatColor.DARK_GREEN + "") + "<---------->");
			sign.setLine(1, ChatColor.GREEN + "" + ChatColor.BOLD
					+ "Slot Machine");
			sign.setLine(2, "SATSA HÄR!");
			sign.setLine(3, (blink ? ChatColor.GREEN + ""
					: ChatColor.DARK_GREEN + "") + "<---------->");
		}

		sign.update(true);
	}

	public void placeBet(Player bettor, int amount) {
		if (machineState != MachineState.WAITING) {
			bettor.sendMessage(Core.getInstance().chatTag + ChatColor.RED
					+ "Denna Slot Machine används redan av någon annan just nu!");
			return;
		}

		if (Core.getInstance().playersInBet.contains(bettor.getName())) {
			bettor.sendMessage(Core.getInstance().chatTag + ChatColor.RED + "Du har satsat på en annan Slot Machine!");
			return;
		}

		if (!Core.getInstance().economy.has(bettor, amount)) {
			bettor.sendMessage(
					Core.getInstance().chatTag + ChatColor.RED + "Du har inte tillräckligt med pengar att satsa!");
			return;
		}

		Core.getInstance().playersInBet.add(bettor.getName());

		unitWorth = amount;

		Core.getInstance()
				.broadcast(Core.getInstance().chatTag + ChatColor.DARK_GREEN + bettor.getName() + " " + ChatColor.GREEN
						+ "satsade precis " + ChatColor.DARK_GREEN + unitWorth + ChatColor.GREEN
						+ "$ i en Slot Machine! Lycka till!");

		bettor.sendTitle(ChatColor.GREEN + "Lycka till!", "", 10, 40, 10);

		bettor.playSound(bettor.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);

		Core.getInstance().economy.withdrawPlayer(bettor, unitWorth);
		slotPlayer = bettor.getName();

		machineState = MachineState.PRE_ROLL;
		time = 4;
	}

	public void start() {
        machineState = MachineState.ROLLING;
        currentRow = 0;
        time = Core.getInstance().random.nextInt(3) + 3;
        build();
        generateResults();
    }

	public BlockFace correctSignDirection(BlockFace orientation) {
		if (orientation == BlockFace.SOUTH) {
			return BlockFace.SOUTH_SOUTH_EAST;
		} else if (orientation == BlockFace.EAST) {
			return BlockFace.WEST_NORTH_WEST;
		} else {
			return orientation;
		}
	}

	public void placeSign() {
		machineLocation.getBlock().setType(Material.WALL_SIGN);
		Sign sign = (Sign) machineLocation.getBlock().getState();
		org.bukkit.material.Sign matSign = new org.bukkit.material.Sign();
		matSign.setFacingDirection(correctSignDirection(signFace));
		sign.setData(matSign);
		sign.update();
	}

	public void doMachineCreate() {
		new BukkitRunnable() {
			public void run() {
				updateSign();
			}
		}.runTaskLater(Core.getInstance(), 20);
	}

	public void reset() {
		machineState = MachineState.WAITING;
		slotPlayer = "";

		currentRow = 0;
		time = 0;

		mapCanvas = null;

		mapView = null;

		updateSign();

		reels.clear();

		for (int i = 1; i <= 3; i++) {
			Reel reel = new Reel(i);
			List<Symbol> symbols = Arrays.asList(Symbol.values());
			int currentPosition = 0;

			Collections.shuffle(symbols);

			for (Symbol symbol : symbols) {
				currentPosition++;
				reel.items.add(new ReelItem(currentPosition, symbol));
			}

			reels.add(reel);
		}

		if (itemFrame != null) {
			itemFrame.remove();
		}
	}
	
	public void generateResults() {
		endResults.clear();

		int result = Core.getInstance().random.nextInt(Combinations.total);

		for (Combinations combination : Combinations.values()) {
			if (combination.from <= result && combination.to >= result) {
				if (combination == Combinations.NO_PAYOUT) {
					for (int i = 1; i <= 3; i++) {
						endResults.add(randomSymbol());
					}

					for (int i = 1; i <= 3; i++) {
						if (i == 1 || i == 3) {
							if (endResults.get(0).equals(endResults.get(1))
									&& endResults.get(1).equals(
											endResults.get(2))) {
								endResults.set(1,
										randomSymbol(endResults.get(1)));
							} else if (endResults.get(0).equals(
									endResults.get(1))) {
								endResults.set(0,
										randomSymbol(endResults.get(0)));
							} else if (endResults.get(1).equals(
									endResults.get(2))) {
								endResults.set(1,
										randomSymbol(endResults.get(1)));
							}
						}
					}

				} else {
					if (combination.is3x) {
						for (int i2 = 1; i2 <= 3; i2++) {
							endResults.add(combination.symbol);
						}
					} else {
						int pos = (Core.getInstance().random.nextBoolean() ? 1
								: 3);

						for (int i2 = 1; i2 <= 3; i2++) {
							if (pos == i2) {
								endResults
										.add(randomSymbol(combination.symbol));
							} else {
								endResults.add(combination.symbol);
							}
						}
					}
				}

				break;
			}
		}
	}
}
