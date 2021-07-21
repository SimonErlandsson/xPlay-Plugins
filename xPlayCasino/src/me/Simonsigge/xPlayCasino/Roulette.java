package me.Simonsigge.xPlayCasino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.connorlinfoot.titleapi.TitleAPI;

import me.Simonsigge.xPlayCasino.Roulette.BET;

public class Roulette {

	enum BET {
		NUMBER, GREEN, RED, BLACK, ONETOTWELVE, THIRTEENTOTWENTYFOUR, TWENTYFIVETOTHIRTYSIX, ONETOEIGHTEEN, NINETEENTOTHIRTYSIX, EVEN, ODD
	};

	World w;
	Random r;

	ArrayList<Location> tableSignLock;
	ArrayList<Location> middleBlocks;
	ArrayList<Location> middleSigns;
	ArrayList<Location> rightSigns;
	ArrayList<Location> leftSigns;
	Inventory tableInv;
	Inventory betInv;

	ArrayList<ItemStack> redWools;
	ArrayList<ItemStack> blackWools;
	ItemStack greenWool;

	ArrayList<ItemStack> redWoolsGuide;
	ArrayList<ItemStack> blackWoolsGuide;
	ArrayList<ItemStack> drawedTable;
	ItemStack greenWoolGuide;
	ItemStack oneToTwelve;
	ItemStack thirteenToTwentyfour;
	ItemStack twentyfiveToThirtysix;
	ItemStack oneToEighteen;
	ItemStack nineteenToThirtysix;
	ItemStack even;
	ItemStack odd;
	ItemStack red;
	ItemStack black;
	ItemStack pointer;
	ItemStack exitTable;
	ItemStack addToBet;
	ItemStack winningItem;

	boolean canJoinGame;
	boolean isCountDown;
	boolean isGameFinished;
	int timeLeft;
	int totalMoneyBet;
	int elapsedTime;

	int totalWon, totalLost, totalWins, totalEntries;
	String latestWin;
	ArrayList<Player> activeWheelLookers;

	ArrayList<Que> playersInQue;
	HashMap<Player, Integer> inBetPlayers;
	HashMap<String, Integer> numberOfEntries;
	ArrayList<RouletteBET> activeBets;

	ArrayList<Integer> updateTimer;
	ArrayList<Location> timer1, timer2, timer3, timer4;
	ArrayList<Location> fireWorkLocations;

	Location print1, print2;

	public Roulette(ArrayList<Location> tableSignLock) {
		this.tableSignLock = tableSignLock;
		canJoinGame = true;
		isCountDown = false;
		timeLeft = 61;
		elapsedTime = 20;
		activeWheelLookers = new ArrayList<Player>();
		drawedTable = new ArrayList<ItemStack>();
		playersInQue = new ArrayList<Que>();
		activeBets = new ArrayList<RouletteBET>();
		inBetPlayers = new HashMap<Player, Integer>();
		numberOfEntries = new HashMap<String, Integer>();
		w = tableSignLock.get(0).getWorld();
		r = new Random();

		updateTimer = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++)
			updateTimer.add(5 * i);

		timer1 = new ArrayList<Location>();
		timer1.add(new Location(w, 11, 63, -70));
		for (int i = 0; i < 11; i++) {
			Location loc = timer1.get(i).clone();
			loc.add(1, 0, 0);
			timer1.add(loc);
		}

		timer2 = new ArrayList<Location>();
		timer2.add(new Location(w, 27, 63, -65));
		for (int i = 0; i < 11; i++) {
			Location loc = timer2.get(i).clone();
			loc.add(0, 0, 1);
			timer2.add(loc);
		}

		timer3 = new ArrayList<Location>();
		timer3.add(new Location(w, 22, 63, -49));
		for (int i = 0; i < 11; i++) {
			Location loc = timer3.get(i).clone();
			loc.add(-1, 0, 0);
			timer3.add(loc);
		}

		timer4 = new ArrayList<Location>();
		timer4.add(new Location(w, 6, 63, -54));
		for (int i = 0; i < 11; i++) {
			Location loc = timer4.get(i).clone();
			loc.add(0, 0, -1);
			timer4.add(loc);
		}

		// Init locations
		middleBlocks = new ArrayList<Location>();
		middleBlocks.add(tableSignLock.get(0).clone().add(0, 2, -3));
		middleBlocks.add(tableSignLock.get(0).clone().add(1, 2, -3));
		middleBlocks.add(tableSignLock.get(0).clone().add(0, 2, -4));
		middleBlocks.add(tableSignLock.get(0).clone().add(1, 2, -4));

		middleSigns = new ArrayList<Location>();
		middleSigns.add(tableSignLock.get(0).clone().add(1, 2, -5));
		middleSigns.add(tableSignLock.get(0).clone().add(0, 2, -5));
		middleSigns.add(tableSignLock.get(0).clone().add(-1, 2, -4));
		middleSigns.add(tableSignLock.get(0).clone().add(-1, 2, -3));
		middleSigns.add(tableSignLock.get(0).clone().add(0, 2, -2));
		middleSigns.add(tableSignLock.get(0).clone().add(1, 2, -2));
		middleSigns.add(tableSignLock.get(0).clone().add(2, 2, -3));
		middleSigns.add(tableSignLock.get(0).clone().add(2, 2, -4));

		leftSigns = new ArrayList<Location>();
		leftSigns.add(tableSignLock.get(0).clone().add(-9, 0, -2));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, 0, -3));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, 0, -4));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, 0, -5));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, -1, -2));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, -1, -3));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, -1, -4));
		leftSigns.add(tableSignLock.get(0).clone().add(-9, -1, -5));

		rightSigns = new ArrayList<Location>();
		rightSigns.add(tableSignLock.get(0).clone().add(10, 0, -5));
		rightSigns.add(tableSignLock.get(0).clone().add(10, 0, -4));
		rightSigns.add(tableSignLock.get(0).clone().add(10, 0, -3));
		rightSigns.add(tableSignLock.get(0).clone().add(10, 0, -2));
		rightSigns.add(tableSignLock.get(0).clone().add(10, -1, -5));
		rightSigns.add(tableSignLock.get(0).clone().add(10, -1, -4));
		rightSigns.add(tableSignLock.get(0).clone().add(10, -1, -3));
		rightSigns.add(tableSignLock.get(0).clone().add(10, -1, -2));

		fireWorkLocations = new ArrayList<Location>();
		fireWorkLocations.add(new Location(w, 25, 57, -51));
		fireWorkLocations.add(new Location(w, 8, 57, -51));
		fireWorkLocations.add(new Location(w, 8, 57, -68));
		fireWorkLocations.add(new Location(w, 25, 57, -68));

		tableInv = Bukkit.createInventory(null, 27, ChatColor.BLACK + ""
				+ ChatColor.BOLD + "Roulette-bord:");
		betInv = Bukkit.createInventory(null, 54, ChatColor.BLACK + ""
				+ ChatColor.BOLD + "Hur vill du satsa?");

		// Initialize items and metadata
		pointer = new ItemStack(Material.HOPPER, 1);
		ItemMeta pointerMeta = pointer.getItemMeta();
		pointerMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD
				+ "Roulette - Välkommen!");
		pointer.setItemMeta(pointerMeta);

		exitTable = new ItemStack(Material.BARRIER, 1);
		ItemMeta exitTableMeta = exitTable.getItemMeta();
		exitTableMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD
				+ "Stäng hjulet");
		exitTable.setItemMeta(exitTableMeta);

		addToBet = new ItemStack(Material.DOUBLE_PLANT, 1);
		ItemMeta addToBetMeta = addToBet.getItemMeta();
		addToBetMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD
				+ "Satsa");
		addToBetMeta.setLore(Arrays.asList(ChatColor.BLUE
				+ "Klicka här för att satsa", ChatColor.BLUE
				+ "spelpengar i Roulette."));
		addToBet.setItemMeta(addToBetMeta);

		greenWool = new ItemStack(Material.WOOL, 1, (byte) 5);
		ItemMeta greenWoolMeta = greenWool.getItemMeta();
		greenWoolMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD
				+ "GRÖN: 0");
		greenWool.setItemMeta(greenWoolMeta);

		redWools = new ArrayList<ItemStack>();
		for (int i = 0; i < 18; i++) {
			ItemStack is = new ItemStack(Material.WOOL, i * 2 + 1, (byte) 14);
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD
					+ "Nummer: " + Integer.toString(i * 2 + 1));
			is.setItemMeta(isMeta);
			redWools.add(is);
		}

		blackWools = new ArrayList<ItemStack>();
		for (int i = 0; i < 18; i++) {
			ItemStack is = new ItemStack(Material.WOOL, i * 2 + 2, (byte) 15);
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD
					+ "Nummer: " + Integer.toString(i * 2 + 2));
			is.setItemMeta(isMeta);
			blackWools.add(is);
		}

		// Initialize guide
		redWoolsGuide = new ArrayList<ItemStack>();
		for (ItemStack is : redWools)
			redWoolsGuide.add(is.clone());
		for (ItemStack is : redWoolsGuide) {
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setLore(Arrays.asList(ChatColor.BLUE + "Nummer.",
					ChatColor.BLUE + "Du får 35 gånger insats."));
			is.setItemMeta(isMeta);
		}

		blackWoolsGuide = new ArrayList<ItemStack>();
		for (ItemStack is : blackWools)
			blackWoolsGuide.add(is.clone());
		for (ItemStack is : blackWoolsGuide) {
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setLore(Arrays.asList(ChatColor.BLUE + "Nummer.",
					ChatColor.BLUE + "Du får 35 gånger insats."));
			is.setItemMeta(isMeta);
		}

		greenWoolGuide = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta greenWoolGuideMeta = greenWoolGuide.getItemMeta();
		greenWoolGuideMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD
				+ "Grön färg");
		greenWoolGuideMeta.setLore(Arrays.asList(ChatColor.BLUE + "Färg.",
				ChatColor.BLUE + "Du får 35 gånger insats."));
		greenWoolGuide.setItemMeta(greenWoolGuideMeta);

		oneToTwelve = new ItemStack(Material.WOOL, 1, (short) 1);
		ItemMeta oneToTwelveMeta = oneToTwelve.getItemMeta();
		oneToTwelveMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD
				+ "1 - 12");
		oneToTwelveMeta.setLore(Arrays.asList(ChatColor.BLUE + "Nummer.",
				ChatColor.BLUE + "Du får 3 gånger insats."));
		oneToTwelve.setItemMeta(oneToTwelveMeta);

		thirteenToTwentyfour = new ItemStack(Material.WOOL, 1, (short) 6);
		ItemMeta thirteenToTwentyfourMeta = thirteenToTwentyfour.getItemMeta();
		thirteenToTwentyfourMeta.setDisplayName(ChatColor.LIGHT_PURPLE + ""
				+ ChatColor.BOLD + "13 - 24");
		thirteenToTwentyfourMeta.setLore(Arrays.asList(ChatColor.BLUE
				+ "Nummer.", ChatColor.BLUE + "Du får 3 gånger insats."));
		thirteenToTwentyfour.setItemMeta(thirteenToTwentyfourMeta);

		twentyfiveToThirtysix = new ItemStack(Material.WOOL, 1, (short) 3);
		ItemMeta twentyfiveToThirtysixMeta = twentyfiveToThirtysix
				.getItemMeta();
		twentyfiveToThirtysixMeta.setDisplayName(ChatColor.AQUA + ""
				+ ChatColor.BOLD + "25 - 36");
		twentyfiveToThirtysixMeta.setLore(Arrays.asList(ChatColor.BLUE
				+ "Nummer.", ChatColor.BLUE + "Du får 3 gånger insats."));
		twentyfiveToThirtysix.setItemMeta(twentyfiveToThirtysixMeta);

		oneToEighteen = new ItemStack(Material.WOOD, 1);
		ItemMeta oneToEighteenMeta = oneToEighteen.getItemMeta();
		oneToEighteenMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD
				+ "1 - 18");
		oneToEighteenMeta.setLore(Arrays.asList(ChatColor.BLUE + "Nummer.",
				ChatColor.BLUE + "Du får 2 gånger insats."));
		oneToEighteen.setItemMeta(oneToEighteenMeta);

		nineteenToThirtysix = new ItemStack(Material.WOOD, 1, (short) 1);
		ItemMeta nineteenToThirtysixMeta = nineteenToThirtysix.getItemMeta();
		nineteenToThirtysixMeta.setDisplayName(ChatColor.WHITE + ""
				+ ChatColor.BOLD + "19 - 36");
		nineteenToThirtysixMeta.setLore(Arrays.asList(ChatColor.BLUE
				+ "Nummer.", ChatColor.BLUE + "Du får 2 gånger insats."));
		nineteenToThirtysix.setItemMeta(nineteenToThirtysixMeta);

		even = new ItemStack(Material.WOOL, 2);
		ItemMeta evenMeta = even.getItemMeta();
		evenMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD
				+ "Jämna nummer");
		evenMeta.setLore(Arrays.asList(ChatColor.BLUE + "Nummer.",
				ChatColor.BLUE + "Du får 2 gånger insats."));
		even.setItemMeta(evenMeta);

		odd = new ItemStack(Material.WOOL, 3);
		ItemMeta oddMeta = odd.getItemMeta();
		oddMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD
				+ "Udda nummer");
		oddMeta.setLore(Arrays.asList(ChatColor.BLUE + "Nummer.",
				ChatColor.BLUE + "Du får 2 gånger insats."));
		odd.setItemMeta(oddMeta);

		red = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta redMeta = red.getItemMeta();
		redMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Röd färg");
		redMeta.setLore(Arrays.asList(ChatColor.BLUE + "Färg.", ChatColor.BLUE
				+ "Du får 2 gånger insats."));
		red.setItemMeta(redMeta);

		black = new ItemStack(Material.COAL_BLOCK, 1);
		ItemMeta blackMeta = black.getItemMeta();
		blackMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD
				+ "Svart färg");
		blackMeta.setLore(Arrays.asList(ChatColor.BLUE + "Färg.",
				ChatColor.BLUE + "Du får 2 gånger insats."));
		black.setItemMeta(blackMeta);

		// Initialize inventories
		tableInv.setItem(4, pointer);
		tableInv.setItem(18, addToBet);
		tableInv.setItem(26, exitTable);

		for (int i = 0; i < 36; i++) {
			// If even
			if (((i + 1) & 1) == 0) {
				betInv.setItem(i, blackWoolsGuide.get((i - 1) / 2));
			} else {
				betInv.setItem(i, redWoolsGuide.get((i) / 2));
			}
		}

		betInv.setItem(37, oneToTwelve);
		betInv.setItem(40, thirteenToTwentyfour);
		betInv.setItem(43, twentyfiveToThirtysix);
		betInv.setItem(45, oneToEighteen);
		betInv.setItem(47, even);
		betInv.setItem(48, red);
		betInv.setItem(49, greenWoolGuide);
		betInv.setItem(50, black);
		betInv.setItem(51, odd);
		betInv.setItem(53, nineteenToThirtysix);

		print1 = new Location(w, 7, 65, -50);
		print2 = new Location(w, 26, 57, -69);
	}

	public boolean isWithinLocation(Location playerLoc) {
		if (playerLoc.getBlockX() >= print1.getBlockX()
				&& playerLoc.getBlockX() <= print2.getBlockX())
			if (playerLoc.getBlockY() <= print1.getBlockY()
					&& playerLoc.getBlockY() >= print2.getBlockY())
				if (playerLoc.getBlockZ() <= print1.getBlockZ()
						&& playerLoc.getBlockZ() >= print2.getBlockZ())
					return true;
		return false;
	}

	public void PrintMessageRoom(String msg) {
		for (Player p : Bukkit.getOnlinePlayers())
			if (isWithinLocation(p.getLocation()))
				p.sendMessage(msg);
	}

	public void perSecondUpdate() {

	}

	int latestIndex, doneTenth, doneFifth, doneHalf, doneWhole, maxTenth,
			maxFifth, maxHalf, maxWhole;

	ChatColor changingColor;
	int colorTime;

	public void perTickUpdate() {
		colorTime++;
		changingColor = ChatColor.GREEN;
		if (colorTime > 5)
			changingColor = ChatColor.DARK_GREEN;
		if (colorTime > 9)
			colorTime = 0;

		if (colorTime == 1 || colorTime == 6) {
			ArrayList<ArrayList<Location>> lList = new ArrayList<ArrayList<Location>>();
			lList.add(leftSigns);
			lList.add(rightSigns);
			for (int i = 0; i < lList.size(); i++) {
				for (int i2 = 0; i2 < lList.get(i).size(); i2++) {
					org.bukkit.block.Sign signState = (org.bukkit.block.Sign) lList
							.get(i).get(i2).getBlock().getState();
					signState.setLine(0, changingColor + "<---------->");
					signState.setLine(3, changingColor + "<---------->");
					if (i2 == 4) {
						if (timeLeft <= 60 && timeLeft >= 0)
							signState.setLine(1,
									ChatColor.RED + "" + ChatColor.BOLD
											+ Integer.toString(timeLeft));
						if (timeLeft < 0)
							signState.setLine(1, ChatColor.RED + ""
									+ ChatColor.BOLD + "Dragning!");
						if (timeLeft > 60)
							signState.setLine(1, ChatColor.RED + ""
									+ ChatColor.BOLD + "Satsa!");
					}
					if (i2 == 5)
						signState.setLine(1, ChatColor.RED + ""
								+ ChatColor.BOLD + numberOfEntries.size());
					if (i2 == 6)
						signState
								.setLine(1, ChatColor.RED + "" + ChatColor.BOLD
										+ Integer.toString(totalMoneyBet) + "$");
					if (i2 == 7)
						signState.setLine(1, latestWin);
					signState.update();
				}
			}

		}

		for (int i = playersInQue.size() - 1; i > -1; i--) {
			playersInQue.get(i).UpdateQue();
			if (playersInQue.get(i).shouldBeRemoved)
				playersInQue.remove(i);
		}

		if (isCountDown) {
			elapsedTime++;
			if (elapsedTime >= 20) {
				timeLeft--;

				if (timeLeft == 60 || timeLeft == 45 || timeLeft == 30
						|| timeLeft == 15) {
					PrintMessageRoom(Statics.prefix
							+ ChatColor.DARK_GREEN
							+ Integer.toString(timeLeft)
							+ ChatColor.GREEN
							+ " sekunder tills dragning i Roulette! Lycka till!");
				}

				if (updateTimer.contains(timeLeft)) {
					BlockState bs = timer1.get(11 - timeLeft / 5).getBlock()
							.getState();
					Wool wool = (Wool) bs.getData();
					wool.setColor(DyeColor.BLACK);
					bs.update();

					bs = timer2.get(11 - timeLeft / 5).getBlock().getState();
					wool = (Wool) bs.getData();
					wool.setColor(DyeColor.BLACK);
					bs.update();

					bs = timer3.get(11 - timeLeft / 5).getBlock().getState();
					wool = (Wool) bs.getData();
					wool.setColor(DyeColor.BLACK);
					bs.update();

					bs = timer4.get(11 - timeLeft / 5).getBlock().getState();
					wool = (Wool) bs.getData();
					wool.setColor(DyeColor.BLACK);
					bs.update();
				}

				if (timeLeft == 0) {
					PrintMessageRoom(Statics.prefix
							+ ChatColor.GREEN
							+ "Dragning i Roulette! Totalt satsat: "
							+ ChatColor.DARK_GREEN
							+ Integer.toString(totalMoneyBet) + ChatColor.GREEN
							+ "$. Lycka till!");
					canJoinGame = false;

					for (Que q : playersInQue) {
						if (Statics.playersInQue.contains(q.p))
							Statics.playersInQue.remove(q.p);
						q.shouldBeRemoved = true;
						TitleAPI.sendTitle(q.p, 10, 40, 10, ChatColor.RED
								+ "Dragning pågår!", null);
					}

					ArrayList<ItemStack> redWoolsCloned = new ArrayList<ItemStack>();
					ArrayList<ItemStack> blackWoolsCloned = new ArrayList<ItemStack>();
					ArrayList<Integer> alreadyDoneNumbers = new ArrayList<Integer>();

					boolean redNext = true;
					for (int i = 36; i > 0; i--) {
						int currentNumber = r.nextInt(36) + 1;
						while (alreadyDoneNumbers.contains(currentNumber))
							currentNumber = r.nextInt(36) + 1;
						alreadyDoneNumbers.add(currentNumber);

						if (redNext) {
							ItemStack is = new ItemStack(Material.WOOL,
									currentNumber, (byte) 14);
							ItemMeta isMeta = is.getItemMeta();
							isMeta.setDisplayName(ChatColor.RED + ""
									+ ChatColor.BOLD + "RÖD: "
									+ Integer.toString(currentNumber));
							is.setItemMeta(isMeta);
							redWoolsCloned.add(is);
							redNext = false;
						} else {
							ItemStack is = new ItemStack(Material.WOOL,
									currentNumber, (byte) 15);
							ItemMeta isMeta = is.getItemMeta();
							isMeta.setDisplayName(ChatColor.BLACK + ""
									+ ChatColor.BOLD + "SVART: "
									+ Integer.toString(currentNumber));
							is.setItemMeta(isMeta);
							blackWoolsCloned.add(is);
							redNext = true;
						}
					}

					boolean hasGreenBeenAdded = false;
					redNext = true;
					int woolLeft = 18;
					// Create wheel
					for (int i = 36; i > 0; i--) {
						int random = r.nextInt(i);

						if (random == 0 && hasGreenBeenAdded == false) {
							drawedTable.add(greenWool);
							hasGreenBeenAdded = true;
						}

						int randomNumber = r.nextInt(woolLeft);
						if (redNext) {
							drawedTable.add(redWoolsCloned.get(randomNumber));
							redWoolsCloned.remove(randomNumber);
							redNext = false;
						} else {
							drawedTable.add(blackWoolsCloned.get(randomNumber));
							blackWoolsCloned.remove(randomNumber);
							redNext = true;
							woolLeft--;
						}
					}

					maxTenth = r.nextInt(20) + 20;
					maxFifth = r.nextInt(10) + 10;
					maxHalf = r.nextInt(5) + 5;
					maxWhole = r.nextInt(3) + 2;
					System.out.println("Roulette -> Genererade bord");

					for (Player pI : inBetPlayers.keySet()) {
						TitleAPI.sendTitle(pI, 10, 40, 10, ChatColor.RED
								+ "Dragning pågår!", ChatColor.GREEN
								+ "Du fick pengarna tillbaka!");
						Statics.economy.depositPlayer(pI, inBetPlayers.get(pI));
						pI.closeInventory();
					}
					inBetPlayers = new HashMap<Player, Integer>();
				}
				elapsedTime = 0;
			}

			// Själva animationen samt vinst
			if (timeLeft <= 0 && !isGameFinished && !canJoinGame) {
				if ((elapsedTime == 0 || elapsedTime == 2 || elapsedTime == 4
						|| elapsedTime == 6 || elapsedTime == 8
						|| elapsedTime == 10 || elapsedTime == 12
						|| elapsedTime == 14 || elapsedTime == 16 || elapsedTime == 18)
						&& doneTenth < maxTenth) {
					MoveWheel();
					doneTenth++;
				}

				else if ((elapsedTime == 0 || elapsedTime == 5
						|| elapsedTime == 10 || elapsedTime == 15)
						&& doneFifth < maxFifth) {
					MoveWheel();
					doneFifth++;
				}

				else if ((elapsedTime == 0 || elapsedTime == 10)
						&& doneHalf < maxHalf) {
					MoveWheel();
					doneHalf++;
				}

				else if ((elapsedTime == 0) && doneWhole < maxWhole) {
					MoveWheel();
					doneWhole++;
				} else if (elapsedTime == 0) {

					for (Player p : activeWheelLookers)
						p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100,
								100);
					winningItem = tableInv.getItem(13);

					PrintMessageRoom(Statics.prefix
							+ ChatColor.GREEN + "Hjulet stannade på: "
							+ winningItem.getItemMeta().getDisplayName()
							+ ChatColor.GREEN + ". Hoppas du vann!");
					timeLeft = -80;
					isGameFinished = true;

					boolean isGreen = winningItem
							.getItemMeta()
							.getDisplayName()
							.equals(ChatColor.GREEN + "" + ChatColor.BOLD
									+ "GRÖN: 0");
					boolean isEven = ((winningItem.getAmount() + 1) & 1) != 0;
					boolean isBlack = winningItem
							.getItemMeta()
							.getDisplayName()
							.substring(0, 10)
							.equals(ChatColor.BLACK + "" + ChatColor.BOLD
									+ "SVART:");
					boolean isUnderNineteen = winningItem.getAmount() < 19;
					boolean isUnderThirteen = winningItem.getAmount() < 13;
					boolean isOverTwentyfour = winningItem.getAmount() > 24;
					int number = winningItem.getAmount();

					DyeColor color = null;
					if (isGreen)
						color = DyeColor.GREEN;
					else if (isBlack)
						color = DyeColor.BLACK;
					else
						color = DyeColor.RED;
					for (Location loc : middleBlocks) {
						loc.getBlock().setType(winningItem.getType());
						BlockState bs = loc.getBlock().getState();
						Wool wool = (Wool) bs.getData();
						wool.setColor(color);
						bs.update();
					}

					latestWin = winningItem.getItemMeta().getDisplayName();
					if (isBlack)
						latestWin = ChatColor.BLACK + "" + ChatColor.BOLD
								+ "SVART: " + Integer.toString(number);

					for (Location loc : middleSigns) {
						org.bukkit.block.Sign sign = (org.bukkit.block.Sign) loc
								.getBlock().getState();
						sign.setLine(1, latestWin);
						sign.setLine(2, latestWin);
						sign.update();
					}

					for (Location fireWorkLocation : fireWorkLocations) {
						Firework fw = (Firework) w.spawnEntity(
								fireWorkLocation, EntityType.FIREWORK);
						FireworkMeta fwm = fw.getFireworkMeta();
						FireworkEffect effect = FireworkEffect.builder()
								.flicker(r.nextBoolean())
								.withColor(Color.GREEN).withFade(Color.RED)
								.with(Type.BALL_LARGE).trail(true).build();
						fwm.addEffect(effect);
						fwm.setPower(1);
						fw.setFireworkMeta(fwm);
					}

					System.out.println("Roulette -> Drar vinnare");

					for (RouletteBET bet : activeBets) {
						totalEntries++;
						int win = -1;
						if (isGreen) {
							if (bet.bet == BET.GREEN)
								win = 35;
						} else {

							if (bet.bet == BET.NUMBER && number == bet.number)
								win = 35;

							if ((isEven && bet.bet == BET.EVEN)
									|| (!isEven && bet.bet == BET.ODD))
								win = 2;

							if ((isBlack && bet.bet == BET.BLACK)
									|| (!isBlack && bet.bet == BET.RED))
								win = 2;

							if ((isUnderNineteen && bet.bet == BET.ONETOEIGHTEEN)
									|| (!isUnderNineteen && bet.bet == BET.NINETEENTOTHIRTYSIX))
								win = 2;

							if ((isUnderThirteen && bet.bet == BET.ONETOTWELVE)
									|| (isOverTwentyfour && bet.bet == BET.TWENTYFIVETOTHIRTYSIX))
								win = 3;

							if (!isUnderThirteen && !isOverTwentyfour
									&& bet.bet == BET.THIRTEENTOTWENTYFOUR)
								win = 3;
						}
						if (win == 35) {
							PrintMessageRoom(Statics.prefix
									+ ChatColor.DARK_GREEN + bet.p.getName()
									+ ChatColor.GREEN + " vann precis "
									+ ChatColor.DARK_GREEN
									+ Integer.toString(bet.moneyTyped * 35)
									+ ChatColor.GREEN + "$ i Roulette ("
									+ ChatColor.DARK_GREEN + "35 ggr"
									+ ChatColor.GREEN + "). Grattis!");
						}

						if (win == -1) {
							totalLost += bet.moneyTyped;
							TitleAPI.sendTitle(bet.p, 10, 40, 10, ChatColor.RED
									+ "Du förlorade! Synd! :(", null);
							bet.p.playSound(bet.p.getLocation(),
									Sound.ENTITY_COW_HURT, 100, 100);
							bet.p.sendMessage(ChatColor.RED
									+ "Du förlorade! Synd! :(");
						} else {
							totalWon += bet.moneyTyped * win;
							totalWins++;
							TitleAPI.sendTitle(bet.p, 10, 40, 10,
									ChatColor.GREEN + "Du vann! Grattis!", null);
							bet.p.playSound(bet.p.getLocation(),
									Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
							Statics.economy.depositPlayer(bet.p, bet.moneyTyped
									* win);
							bet.p.sendMessage(ChatColor.GREEN
									+ "Du vann! Grattis!");
						}
					}
					float winningChance = (float) totalWins
							/ (float) totalEntries;
					winningChance *= 100;
					winningChance = (float) (Math.round(winningChance * 100.0f) / 100.0f);
					PrintMessageRoom(ChatColor.GREEN + "Resultat ("
							+ ChatColor.DARK_GREEN + "Roulette"
							+ ChatColor.GREEN + "): Pengar utdelade i vinst: "
							+ ChatColor.DARK_GREEN + Integer.toString(totalWon)
							+ ChatColor.GREEN + "$. Förlorade: "
							+ ChatColor.DARK_GREEN
							+ Integer.toString(totalLost) + ChatColor.GREEN
							+ "$. Procent vunna bets: " + ChatColor.DARK_GREEN
							+ Float.toString(winningChance) + ChatColor.GREEN
							+ "%.");
				}
			}
			if (isGameFinished && timeLeft == -90) {
				for (int i = 0; i < 12; i++) {
					BlockState bs = timer1.get(i).getBlock().getState();
					Wool wool = (Wool) bs.getData();
					wool.setColor(DyeColor.WHITE);
					bs.update();

					bs = timer2.get(i).getBlock().getState();
					wool = (Wool) bs.getData();
					wool.setColor(DyeColor.WHITE);
					bs.update();

					bs = timer3.get(i).getBlock().getState();
					wool = (Wool) bs.getData();
					wool.setColor(DyeColor.WHITE);
					bs.update();

					bs = timer4.get(i).getBlock().getState();
					wool = (Wool) bs.getData();
					wool.setColor(DyeColor.WHITE);
					bs.update();
				}

				isCountDown = false;
				isGameFinished = false;
				numberOfEntries = new HashMap<String, Integer>();
				playersInQue = new ArrayList<Que>();
				inBetPlayers = new HashMap<Player, Integer>();
				activeBets = new ArrayList<RouletteBET>();
				timeLeft = 61;
				totalMoneyBet = 0;
				latestIndex = 0;
				doneTenth = 0;
				doneFifth = 0;
				doneHalf = 0;
				doneWhole = 0;
				totalEntries = 0;
				totalLost = 0;
				totalWins = 0;
				totalWon = 0;
				drawedTable = new ArrayList<ItemStack>();
				elapsedTime = 20;
				canJoinGame = true;
				System.out.println("Roulette -> Reset");

				for (Location loc : middleBlocks) {
					loc.getBlock().setType(Material.BARRIER);
				}

				for (Location loc : middleSigns) {
					org.bukkit.block.Sign sign = (org.bukkit.block.Sign) loc
							.getBlock().getState();
					sign.setLine(1, "");
					sign.setLine(2, "");
					sign.update();
				}
			}
		}
	}

	public void MoveWheel() {
		if (drawedTable.size() != 0) {
			for (int i = 0; i < 9; i++) {
				if (latestIndex + i > 36)
					tableInv.setItem(9 + i,
							drawedTable.get(latestIndex + i - 37));
				else
					tableInv.setItem(9 + i, drawedTable.get(latestIndex + i));
			}
			latestIndex++;
			if (latestIndex == 37)
				latestIndex = 0;

			for (Player p : activeWheelLookers)
				p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 100, 100);
		}
	}

	public void onRightSign(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		p.openInventory(tableInv);
		if (!activeWheelLookers.contains(p))
			activeWheelLookers.add(p);
	}

	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		boolean doContinue = false;
		for (int i = 0; i < playersInQue.size(); i++) {
			if (p == playersInQue.get(i).p) {
				doContinue = true;
				if (e.getMessage().equalsIgnoreCase("avbryt")) {
					playersInQue.get(i).shouldBeRemoved = true;
					if (p.isOnline()) {
						p.sendMessage(ChatColor.BLACK + "<-------------------["
								+ ChatColor.WHITE + "xPlayCasino"
								+ ChatColor.BLACK + "]------------------->");
						p.sendMessage(ChatColor.AQUA
								+ "Du har avbrutit din satsning.");
						p.sendMessage(ChatColor.BLACK
								+ "<------------------------------------------------->");
					}
					if (Statics.playersInQue.contains(p))
						Statics.playersInQue.remove(p);
					return;
				}

				if (!canJoinGame) {
					playersInQue.get(i).shouldBeRemoved = true;
					if (p.isOnline()) {
						p.sendMessage(ChatColor.BLACK + "<-------------------["
								+ ChatColor.WHITE + "xPlayCasino"
								+ ChatColor.BLACK + "]------------------->");
						p.sendMessage(ChatColor.AQUA
								+ "Dragning pågår, försök igen senare.");
						p.sendMessage(ChatColor.BLACK
								+ "<------------------------------------------------->");
					}
					if (Statics.playersInQue.contains(p))
						Statics.playersInQue.remove(p);
					return;
				}

				if (numberOfEntries.containsKey(p.getName()))
					if (numberOfEntries.get(p.getName()) > 2) {
						playersInQue.get(i).shouldBeRemoved = true;
						if (p.isOnline()) {
							p.sendMessage(ChatColor.BLACK
									+ "<-------------------[" + ChatColor.WHITE
									+ "xPlayCasino" + ChatColor.BLACK
									+ "]------------------->");
							p.sendMessage(ChatColor.AQUA
									+ "Du kan max satsa tre gånger, kör igen nästa omgång.");
							p.sendMessage(ChatColor.BLACK
									+ "<------------------------------------------------->");
							TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
									+ "Du kan max satsa tre gånger!", null);
						}
						if (Statics.playersInQue.contains(p))
							Statics.playersInQue.remove(p);
						return;
					}
			}
		}
		if (doContinue) {
			for (int i2 = 0; i2 < e.getMessage().length(); i2++)
				if (!Character.isDigit(e.getMessage().charAt(i2)))
					return;
			int moneyTyped = Integer.parseInt(e.getMessage());
			if (Statics.economy.getBalance(p) < moneyTyped) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
						+ "Du har inte råd!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				return;
			}
			if (moneyTyped < 300) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
						+ "Du måste satsa minst 300$!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				return;
			}
			
			if (moneyTyped > 1000000) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
						+ "Du kan max satsa 1 000 000$!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				return;
			}

			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			Statics.economy.withdrawPlayer(p, moneyTyped);

			p.openInventory(betInv);
			inBetPlayers.put(p, moneyTyped);

			if (Statics.playersInQue.contains(p))
				Statics.playersInQue.remove(p);

			for (Que q : playersInQue) {
				if (q.p.getName() == p.getName())
					q.shouldBeRemoved = true;
			}
		}
	}

	public void onInventoryClose(InventoryCloseEvent e) {
		final Player p = (Player) e.getPlayer();
		if (inBetPlayers.containsKey(p)) {
			Statics.server.getScheduler().scheduleSyncDelayedTask(
					Statics.plugin, new Runnable() {
						public void run() {
							if (inBetPlayers.containsKey(p))
								p.openInventory(betInv);
						}
					}, 1);
		}

		if (e.getInventory()
				.getTitle()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD
						+ "Roulette-bord:"))
			if (activeWheelLookers.contains(p))
				activeWheelLookers.remove(p);

	}

	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		Player p = (Player) e.getWhoClicked();

		if (e.getInventory()
				.getName()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD
						+ "Roulette-bord:"))
			e.setCancelled(true);

		if (e.getInventory()
				.getName()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD
						+ "Hur vill du satsa?"))
			e.setCancelled(true);

		if (e.getClickedInventory()
				.getTitle()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD
						+ "Roulette-bord:")) {
			ItemStack item = e.getCurrentItem();
			if (item == null)
				return;
			e.setCancelled(true);

			if (item.equals(exitTable)) {
				p.closeInventory();
				return;
			}

			if (item.equals(addToBet)) {
				if (!canJoinGame) {
					TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
							+ "Dragning pågår!", null);
					p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
					p.closeInventory();
					return;
				}
				if (!Statics.playersInQue.contains(p))
					playersInQue.add(new Que(p, "Roulette"));
				p.closeInventory();
			}
		}

		if (e.getClickedInventory()
				.getTitle()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD
						+ "Hur vill du satsa?")) {
			ItemStack item = e.getCurrentItem();
			if (item == null)
				return;
			e.setCancelled(true);
			int number = -1;
			BET bet = null;

			if (e.getSlot() < 36) {
				number = e.getSlot() + 1;
				bet = BET.NUMBER;
			} else {

				if (item.equals(oneToTwelve))
					bet = BET.ONETOTWELVE;
				if (item.equals(thirteenToTwentyfour))
					bet = BET.THIRTEENTOTWENTYFOUR;
				if (item.equals(twentyfiveToThirtysix))
					bet = BET.TWENTYFIVETOTHIRTYSIX;

				if (item.equals(oneToEighteen))
					bet = BET.ONETOEIGHTEEN;
				if (item.equals(nineteenToThirtysix))
					bet = BET.NINETEENTOTHIRTYSIX;

				if (item.equals(even))
					bet = BET.EVEN;
				if (item.equals(odd))
					bet = BET.ODD;

				if (item.equals(red))
					bet = BET.RED;
				if (item.equals(greenWoolGuide))
					bet = BET.GREEN;
				if (item.equals(black))
					bet = BET.BLACK;
			}

			if (bet == null)
				return;

			activeBets.add(new RouletteBET(p, bet, number, inBetPlayers.get(p),
					this));
			totalMoneyBet += inBetPlayers.get(p);
			inBetPlayers.remove(p);
			if (numberOfEntries.containsKey(p.getName()))
				numberOfEntries.put(p.getName(), numberOfEntries.get(p.getName()) + 1);
			else
				numberOfEntries.put(p.getName(), 1);
			p.closeInventory();
			isCountDown = true;
		}
	}
}

class RouletteBET {

	Player p;
	BET bet;
	int number;
	int moneyTyped;
	Roulette roulette;

	public RouletteBET(Player p, BET bet, int number, int moneyTyped,
			Roulette roulette) {
		this.p = p;
		this.bet = bet;
		this.number = number;
		this.moneyTyped = moneyTyped;
		this.roulette = roulette;

		TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.GREEN + "Lycka till!", null);
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);

		roulette.PrintMessageRoom(Statics.prefix
				+ ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN
				+ " satsade precis " + ChatColor.DARK_GREEN
				+ Integer.toString(moneyTyped) + ChatColor.GREEN
				+ "$ i Roulette. Lycka till!");
	}
}