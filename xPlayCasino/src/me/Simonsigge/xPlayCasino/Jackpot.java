package me.Simonsigge.xPlayCasino;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.connorlinfoot.titleapi.TitleAPI;

public class Jackpot {

	boolean isActiveGame;
	boolean forceJackpot;
	int countDownTime;
	int totalPotSize;
	ArrayList<Location> playerSigns;
	ArrayList<Location> infoSigns;
	ArrayList<Location> jackpotSizeSigns;
	ArrayList<Location> countDownLetters;
	ArrayList<Que> playersInQue;
	ArrayList<Location> fireWorkLocations;
	ArrayList<String> playersInRound;
	PrintNumber printNumber;
	World w;
	Random r;
	int[] numberArray;
	Player[] playerArray;
	int currentBetters;
	boolean winnerHasBeenDrawn;
	int winnerIndex;
	int timeAfterRound;

	int minBet;
	int maxBet;
	
	Location print1, print2;

	public Jackpot(Location sign1, Location sign2, int minBet, int maxBet) {
		this.minBet = minBet;
		this.maxBet = maxBet;
		playerSigns = new ArrayList<Location>();
		infoSigns = new ArrayList<Location>();
		jackpotSizeSigns = new ArrayList<Location>();
		countDownLetters = new ArrayList<Location>();
		playersInQue = new ArrayList<Que>();
		fireWorkLocations = new ArrayList<Location>();
		playersInRound = new ArrayList<String>();
		printNumber = new PrintNumber();
		numberArray = new int[5];
		playerArray = new Player[5];
		r = new Random();
		w = sign1.getWorld();
		winnerHasBeenDrawn = false;

		playerSigns.add(sign1.clone().add(-2, 0, 1));
		playerSigns.add(sign1.clone().add(-3, 0, 1));
		playerSigns.add(sign1.clone().add(-4, 0, 1));
		playerSigns.add(sign1.clone().add(-5, 0, 1));
		playerSigns.add(sign1.clone().add(-6, 0, 1));
		playerSigns.add(sign2.clone().add(2, 0, -1));
		playerSigns.add(sign2.clone().add(3, 0, -1));
		playerSigns.add(sign2.clone().add(4, 0, -1));
		playerSigns.add(sign2.clone().add(5, 0, -1));
		playerSigns.add(sign2.clone().add(6, 0, -1));

		infoSigns.add(sign1.clone().add(-4, -1, 1));
		infoSigns.add(sign2.clone().add(4, -1, -1));

		jackpotSizeSigns.add(sign1.clone().add(-4, 1, 1));
		jackpotSizeSigns.add(sign2.clone().add(4, 1, -1));

		countDownLetters.add(sign1.clone().add(4, 3, 2));
		countDownLetters.add(sign1.clone().add(3, 3, 2));
		countDownLetters.add(sign1.clone().add(2, 3, 2));
		countDownLetters.add(sign1.clone().add(-10, 3, 2));
		countDownLetters.add(sign1.clone().add(-11, 3, 2));
		countDownLetters.add(sign1.clone().add(-12, 3, 2));
		countDownLetters.add(sign2.clone().add(-4, 3, -2));
		countDownLetters.add(sign2.clone().add(-3, 3, -2));
		countDownLetters.add(sign2.clone().add(-2, 3, -2));
		countDownLetters.add(sign2.clone().add(10, 3, -2));
		countDownLetters.add(sign2.clone().add(11, 3, -2));
		countDownLetters.add(sign2.clone().add(12, 3, -2));

		fireWorkLocations.add(sign1.clone().add(-4, -1, 5));
		fireWorkLocations.add(sign2.clone().add(4, -1, -5));
		
		print1 = new Location(w, 4, 54, -49);
		print2 = new Location(w, 25, 42, -69);
	}
	
	public boolean isWithinLocation(Location playerLoc){
		if(playerLoc.getBlockX() >= print1.getBlockX() && playerLoc.getBlockX() <= print2.getBlockX())
			if (playerLoc.getBlockY() <= print1.getBlockY() && playerLoc.getBlockY() >= print2.getBlockY())
				if (playerLoc.getBlockZ() <= print1.getBlockZ() && playerLoc.getBlockZ() >= print2.getBlockZ())
					return true;
		return false;
	}
	
	public void PrintMessageRoom(String msg){
		for(Player p : Bukkit.getOnlinePlayers())
			if (isWithinLocation(p.getLocation()))
				p.sendMessage(msg);
	}
	
	public void perSecondUpdate() {
		if (countDownTime != 0 && forceJackpot == false)
			countDownTime--;

		if (forceJackpot == false && countDownTime == 15) {
			PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + "15" + ChatColor.GREEN
					+ " sekunder tills dragning i Jackpot!");
			PrintMessageRoom(ChatColor.DARK_BLUE
					+ "      Jackpot: " + Integer.toString(currentBetters)
					+ "/5 deltagare, " + Integer.toString(totalPotSize)
					+ "$ i potten.");
		}

		if (countDownTime == 15 || forceJackpot) {
			isActiveGame = true;
			forceJackpot = false;
			for (int i = playersInQue.size() - 1; i > -1; i--) {
				for (int i2 = Statics.playersInQue.size() - 1; i2 > -1; i2--) {
					if (playersInQue.get(i).p.getName() == Statics.playersInQue
							.get(i2).getName()) {
						Statics.playersInQue.remove(i2);
					}
				}
			}
			playersInQue = new ArrayList<Que>();
		}

		if (isActiveGame) {
			for (int i = 0; i < playerArray.length; i++) {
				if (playerArray[i] != null && countDownTime != 15
						&& countDownTime != 0)
					playerArray[i].sendMessage(ChatColor.RED
							+ Integer.toString(countDownTime) + "...");
			}
			if (countDownTime <= 9) {
				printNumber.DoPrint(countDownTime + 1, Material.WOOL,
						countDownLetters.get(0).clone(), countDownLetters
								.get(1).clone(), countDownLetters.get(2)
								.clone());
				printNumber.DoPrint(countDownTime + 1, Material.WOOL,
						countDownLetters.get(3).clone(), countDownLetters
								.get(4).clone(), countDownLetters.get(5)
								.clone());
				printNumber.DoPrint(countDownTime + 1, Material.WOOL,
						countDownLetters.get(6).clone(), countDownLetters
								.get(7).clone(), countDownLetters.get(8)
								.clone());
				printNumber.DoPrint(countDownTime + 1, Material.WOOL,
						countDownLetters.get(9).clone(),
						countDownLetters.get(10).clone(),
						countDownLetters.get(11).clone());
			}

			if (countDownTime == 0 && winnerHasBeenDrawn == false) {
				System.out.println("Jackpot: Drawing winner...");
				int winningTicket = r.nextInt(totalPotSize) + 1;
				System.out.println("Winning ticket: "
						+ Integer.toString(winningTicket));

				int[] fixedNumberArray = new int[5];
				fixedNumberArray[0] = numberArray[0];
				for (int i = 0; i < fixedNumberArray.length; i++) {
					if (i != 0) {
						fixedNumberArray[i] = numberArray[i]
								+ fixedNumberArray[i - 1];
					}
					System.out.println("Spelare: " + Integer.toString(i)
							+ " har lotter: "
							+ Integer.toString(fixedNumberArray[i]));
				}

				for (int i = 0; i < fixedNumberArray.length
						&& winnerHasBeenDrawn == false; i++) {
					if (winningTicket <= fixedNumberArray[i]) {
						winnerHasBeenDrawn = true;
						winnerIndex = i;
						System.out.println("Spelare som vann: "
								+ Integer.toString(i) + " ("
								+ playerArray[winnerIndex].getName() + ")");
					}
				}
				TitleAPI.sendTitle(playerArray[winnerIndex], 10, 40, 10,
						ChatColor.GREEN + "Du vann! Grattis!", null);
				playerArray[winnerIndex].playSound(
						playerArray[winnerIndex].getLocation(), Sound.ENTITY_PLAYER_LEVELUP,
						100, 100);
				Statics.economy.depositPlayer(playerArray[winnerIndex],
						totalPotSize);

				float winningChance = (float) numberArray[winnerIndex]
						/ (float) totalPotSize;
				winningChance *= 100;
				winningChance = (float) (Math.round(winningChance * 100.0f) / 100.0f);
				PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN
						+ playerArray[winnerIndex].getName() + ChatColor.GREEN
						+ " vann precis " + ChatColor.DARK_GREEN + totalPotSize
						+ ChatColor.GREEN + "$ i Jackpot. ("
						+ ChatColor.DARK_GREEN + Float.toString(winningChance)
						+ ChatColor.GREEN + "% chans)" + " (Biljett: #"
						+ ChatColor.DARK_GREEN
						+ Integer.toString(winningTicket) + ChatColor.GREEN
						+ ")");

				for (int i = 0; i < fireWorkLocations.size(); i++) {
					Firework fw = (Firework) w.spawnEntity(
							fireWorkLocations.get(i), EntityType.FIREWORK);
					FireworkMeta fwm = fw.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder()
							.flicker(r.nextBoolean()).withColor(Color.GREEN)
							.withFade(Color.RED).with(Type.BALL_LARGE)
							.trail(true).build();
					fwm.addEffect(effect);
					fwm.setPower(1);
					fw.setFireworkMeta(fwm);
				}

				for (int i = 0; i < currentBetters; i++) {
					if (i != winnerIndex && playerArray[i].isOnline()) {
						TitleAPI.sendTitle(playerArray[i], 10, 40, 10,
								ChatColor.RED + "Du förlorade! Synd! :(", null);
						playerArray[i].playSound(playerArray[i].getLocation(),
								Sound.ENTITY_COW_HURT, 100, 100);
					}
				}
			}
			if (winnerHasBeenDrawn)
				timeAfterRound++;
			if (timeAfterRound > 10) {
				isActiveGame = false;
				winnerHasBeenDrawn = false;
				timeAfterRound = 0;
				totalPotSize = 0;
				numberArray = new int[5];
				playerArray = new Player[5];
				currentBetters = 0;
				winnerIndex = 0;
				playersInRound = new ArrayList<String>();
			}
		}
	}

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
			for (int i = 0; i < infoSigns.size(); i++) {
				org.bukkit.block.Sign infoSignState = (org.bukkit.block.Sign) infoSigns
						.get(i).getBlock().getState();
				if (!isActiveGame) {
					infoSignState.setLine(1, ChatColor.BLUE + ""
							+ ChatColor.BOLD + "DU KAN SATSA!");
				} else {
					infoSignState.setLine(1, ChatColor.BLUE + ""
							+ ChatColor.BOLD + "DRAR VINNARE!");
				}
				if (countDownTime == 0 && isActiveGame == false) {
					infoSignState.setLine(2, "");

				} else {
					infoSignState.setLine(2, ChatColor.RED + ""
							+ ChatColor.BOLD + Integer.toString(countDownTime));
				}
				infoSignState.setLine(0, changingColor + "<---------->");
				infoSignState.setLine(3, changingColor + "<---------->");
				infoSignState.update();
			}

			for (int i = 0; i < jackpotSizeSigns.size(); i++) {
				org.bukkit.block.Sign jackpotSizeSignState = (org.bukkit.block.Sign) jackpotSizeSigns
						.get(i).getBlock().getState();

				jackpotSizeSignState.setLine(1, ChatColor.BOLD + "JACKPOT:");
				jackpotSizeSignState.setLine(2, Integer.toString(totalPotSize)
						+ "$");

				jackpotSizeSignState.setLine(0, changingColor + "<---------->");
				jackpotSizeSignState.setLine(3, changingColor + "<---------->");
				jackpotSizeSignState.update();
			}

			for (int i = 0; i < playerSigns.size(); i++) {
				int fixedInt = 0;
				if (i > 4)
					fixedInt = -5;

				org.bukkit.block.Sign playerSignState = (org.bukkit.block.Sign) playerSigns
						.get(i).getBlock().getState();
				if (numberArray[i + fixedInt] != 0) {
					playerSignState.setLine(1, ChatColor.BOLD
							+ playerArray[i + fixedInt].getName());
					playerSignState
							.setLine(
									2,
									ChatColor.BOLD
											+ Integer.toString(numberArray[i
													+ fixedInt]) + "$");
				} else {
					playerSignState.setLine(1, "");
					playerSignState.setLine(2, "");
				}
				playerSignState.setLine(0, changingColor + "<---------->");
				playerSignState.setLine(3, changingColor + "<---------->");
				playerSignState.update();
			}
		}

		for (int i = playersInQue.size() - 1; i > -1; i--) {
			playersInQue.get(i).UpdateQue();
			if (playersInQue.get(i).shouldBeRemoved)
				playersInQue.remove(i);
		}
	}

	public void onRightSign(PlayerInteractEvent e) {
		Player clicker = e.getPlayer();
		if (isActiveGame) {
			clicker.sendMessage(ChatColor.BLACK + "<-------------------["
					+ ChatColor.WHITE + "xPlayCasino" + ChatColor.BLACK
					+ "]------------------->");
			clicker.sendMessage(ChatColor.AQUA
					+ "Ett spel pågår redan eller så är det fullt i kön. Vänta 15 sekunder och försök sedan igen.");
			clicker.sendMessage(ChatColor.BLACK
					+ "<------------------------------------------------->");
			return;
		}
		playersInQue.add(new Que(clicker, "Jackpot"));
	}

	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		boolean startGame = false;
		for (int i = 0; i < playersInQue.size(); i++) {
			if (p == playersInQue.get(i).p) {
				startGame = true;
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
			}
		}
		if (currentBetters > 4) {
			startGame = false;
			return;
		}

		if (startGame) {
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
			if (minBet != -1) {
				if (moneyTyped < minBet) {
					TitleAPI.sendTitle(
							p,
							10,
							40,
							10,
							ChatColor.RED + "Du måste satsa minst "
									+ Integer.toString(minBet) + "$ här!", null);
					p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
					return;
				}
			}

			if (maxBet != -1) {
				if (moneyTyped > maxBet) {
					TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
							+ "Du kan max satsa " + Integer.toString(maxBet)
							+ "$ här!", null);
					p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
					return;
				}
			}

			if (playersInRound.contains(p.getName())) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
						+ "Du kan inte satsa igen!", null);
				return;
			}

			// Add new player to bet
			for (int i = 0; i < playersInQue.size(); i++) {
				if (p == playersInQue.get(i).p) {
					playersInQue.get(i).shouldBeRemoved = true;
				}
			}
			playersInRound.add(p.getName());
			currentBetters++;
			totalPotSize += moneyTyped;
			numberArray[currentBetters - 1] = moneyTyped;
			playerArray[currentBetters - 1] = p;

			TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.GREEN + "Lycka till!",
					null);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			Statics.economy.withdrawPlayer(p, moneyTyped);

			if (Statics.playersInQue.contains(p))
				Statics.playersInQue.remove(p);

			PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + p.getName()
					+ ChatColor.GREEN + " satsade precis "
					+ ChatColor.DARK_GREEN + Integer.toString(moneyTyped)
					+ ChatColor.GREEN + "$ i Jackpot. Lycka till!");
			PrintMessageRoom(ChatColor.DARK_BLUE
					+ "      Jackpot: " + Integer.toString(currentBetters)
					+ "/5 deltagare, " + Integer.toString(totalPotSize)
					+ "$ i potten.");

			// Spelmekanik
			if (currentBetters == 3) {
				countDownTime = 120;
				PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + "Tre"
						+ ChatColor.GREEN + " har nu satsat i Jackpot. "
						+ ChatColor.DARK_GREEN + "Två" + ChatColor.GREEN
						+ " minuter tills dragning!");
			}
			if (currentBetters == 5) {
				forceJackpot = true;
				countDownTime = 15;
				PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + "Fem"
						+ ChatColor.GREEN + " har nu satsat i Jackpot. "
						+ ChatColor.DARK_GREEN + "15" + ChatColor.GREEN
						+ " sekunder tills dragning!");
			}
		}
	}
}