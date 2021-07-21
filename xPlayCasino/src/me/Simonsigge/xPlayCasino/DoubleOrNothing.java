package me.Simonsigge.xPlayCasino;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.Sign;
import org.bukkit.material.Wool;

import com.connorlinfoot.titleapi.TitleAPI;

public class DoubleOrNothing {

	boolean isActiveGame;
	int depositedMoney;
	Player activePlayer;
	int timeLeftOnRound;
	boolean isWinner;
	boolean hasBeenDrawn;
	int timeAfterRound;

	ArrayList<Location> infoSigns;
	ArrayList<Location> countDownSigns;
	ArrayList<Location> countDownLetters;
	ArrayList<Location> countDownWoolFloor;
	ArrayList<Location> countDownWoolWall;
	ArrayList<Que> playersInQue;
	Location fireWorkLocation;
	PrintNumber printNumber;
	World w;
	Random r;
	
	Location print1, print2;

	public DoubleOrNothing(Location signLoc) {
		infoSigns = new ArrayList<Location>();
		countDownSigns = new ArrayList<Location>();
		countDownLetters = new ArrayList<Location>();
		countDownWoolFloor = new ArrayList<Location>();
		countDownWoolWall = new ArrayList<Location>();
		playersInQue = new ArrayList<Que>();
		printNumber = new PrintNumber();
		r = new Random();
		w = signLoc.getWorld();
		Sign sign = (Sign) signLoc.getBlock().getState().getData();
		BlockFace signFace = sign.getFacing();

		if (signFace == BlockFace.SOUTH) {
			infoSigns.add(signLoc.clone().add(1, 0, 0));
			infoSigns.add(signLoc.clone().add(-1, 0, 0));
			countDownSigns.add(signLoc.clone().add(-1, -1, 0));
			countDownSigns.add(signLoc.clone().add(1, -1, 0));
			countDownLetters.add(signLoc.clone().add(-5, 3, -7));
			countDownLetters.add(signLoc.clone().add(-4, 3, -7));
			countDownLetters.add(signLoc.clone().add(-3, 3, -7));
			countDownLetters.add(signLoc.clone().add(3, 3, -7));
			countDownLetters.add(signLoc.clone().add(4, 3, -7));
			countDownLetters.add(signLoc.clone().add(5, 3, -7));
			countDownWoolFloor.add(signLoc.clone().add(-5, -2, -5));
			countDownWoolFloor.add(signLoc.clone().add(5, -2, -3));
			countDownWoolWall.add(signLoc.clone().add(-1, -1, -7));
			countDownWoolWall.add(signLoc.clone().add(1, 2, -7));
			fireWorkLocation = signLoc.clone().add(0, -1, -4);
		}

		if (signFace == BlockFace.NORTH) {
			infoSigns.add(signLoc.clone().add(-1, 0, 0));
			infoSigns.add(signLoc.clone().add(1, 0, 0));
			countDownSigns.add(signLoc.clone().add(1, -1, 0));
			countDownSigns.add(signLoc.clone().add(-1, -1, 0));
			countDownLetters.add(signLoc.clone().add(5, 3, 7));
			countDownLetters.add(signLoc.clone().add(4, 3, 7));
			countDownLetters.add(signLoc.clone().add(3, 3, 7));
			countDownLetters.add(signLoc.clone().add(-3, 3, 7));
			countDownLetters.add(signLoc.clone().add(-4, 3, 7));
			countDownLetters.add(signLoc.clone().add(-5, 3, 7));
			countDownWoolFloor.add(signLoc.clone().add(-5, -2, 3));
			countDownWoolFloor.add(signLoc.clone().add(5, -2, 5));
			countDownWoolWall.add(signLoc.clone().add(-1, -1, 7));
			countDownWoolWall.add(signLoc.clone().add(1, 2, 7));
			fireWorkLocation = signLoc.clone().add(0, -1, 4);
		}
		
		print1 = new Location(w, -28, 54, -49);
		print2 = new Location(w, -6, 42, -70);
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
		if (isActiveGame) {
			if (timeLeftOnRound > 1)
				activePlayer.sendMessage(ChatColor.RED
						+ Integer.toString(timeLeftOnRound - 1) + "...");
			if (timeLeftOnRound <= 10 && timeLeftOnRound != 0) {
				printNumber.DoPrint(timeLeftOnRound, Material.WOOL,
						countDownLetters.get(0).clone(), countDownLetters
								.get(1).clone(), countDownLetters.get(2)
								.clone());
				printNumber.DoPrint(timeLeftOnRound, Material.WOOL,
						countDownLetters.get(3).clone(), countDownLetters
								.get(4).clone(), countDownLetters.get(5)
								.clone());
			}
			if (timeLeftOnRound >= 1)
				timeLeftOnRound--;
			if (timeLeftOnRound == 0 && hasBeenDrawn == false) {
				isWinner = false;
				hasBeenDrawn = true;
				if (r.nextInt(2) == 1)
					isWinner = true;

				// Om spelaren vinner, gör detta
				if (isWinner) {
					TitleAPI.sendTitle(activePlayer, 10, 40, 10,
							ChatColor.GREEN + "Du vann! Grattis!", null);
					FillArea(Material.WOOL, countDownWoolFloor.get(0),
							countDownWoolFloor.get(1), DyeColor.GREEN);
					FillArea(Material.WOOL, countDownWoolWall.get(0),
							countDownWoolWall.get(1), DyeColor.GREEN);
					PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN
							+ activePlayer.getName() + ChatColor.GREEN
							+ " vann precis " + ChatColor.DARK_GREEN
							+ Integer.toString(depositedMoney * 2)
							+ ChatColor.GREEN + "$ i x2 eller inget. Grattis!");
					activePlayer.playSound(activePlayer.getLocation(),
							Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
					Statics.economy.depositPlayer(activePlayer,
							depositedMoney * 2);

					Firework fw = (Firework) w.spawnEntity(fireWorkLocation,
							EntityType.FIREWORK);
					FireworkMeta fwm = fw.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder()
							.flicker(r.nextBoolean()).withColor(Color.GREEN)
							.withFade(Color.RED).with(Type.BALL_LARGE)
							.trail(true).build();
					fwm.addEffect(effect);
					fwm.setPower(1);
					fw.setFireworkMeta(fwm);

				} else {
					TitleAPI.sendTitle(activePlayer, 10, 40, 10, ChatColor.RED
							+ "Du förlorade! Synd! :(", null);
					activePlayer.playSound(activePlayer.getLocation(),
							Sound.ENTITY_COW_HURT, 100, 100);
					FillArea(Material.WOOL, countDownWoolFloor.get(0),
							countDownWoolFloor.get(1), DyeColor.RED);
					FillArea(Material.WOOL, countDownWoolWall.get(0),
							countDownWoolWall.get(1), DyeColor.RED);
					PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN
							+ activePlayer.getName() + ChatColor.GREEN
							+ " förlorade precis " + ChatColor.DARK_GREEN
							+ Integer.toString(depositedMoney)
							+ ChatColor.GREEN + "$ i x2 eller inget. Synd! :(");
				}
			}
			if (hasBeenDrawn)
				timeAfterRound++;
			if (timeAfterRound > 10) {
				FillArea(Material.WOOL, countDownWoolFloor.get(0),
						countDownWoolFloor.get(1), DyeColor.WHITE);
				FillArea(Material.WOOL, countDownWoolWall.get(0),
						countDownWoolWall.get(1), DyeColor.WHITE);
				isActiveGame = false;
				hasBeenDrawn = false;
				timeAfterRound = 0;
			}
		}
	}

	public void FillArea(Material mat, Location loc1, Location loc2,
			DyeColor woolColor) {
		int loc1X = 0;
		int loc2X = 0;
		if (loc1.getBlockX() < 0)
			loc1X = -loc1.getBlockX();
		if (loc2.getBlockX() < 0)
			loc2X = -loc2.getBlockX();
		int locXDifference = loc1X - loc2X;
		if (locXDifference < 0)
			locXDifference = -locXDifference;

		int loc1Z = 0;
		int loc2Z = 0;
		if (loc1.getBlockZ() < 0)
			loc1Z = -loc1.getBlockZ();
		if (loc2.getBlockZ() < 0)
			loc2Z = -loc2.getBlockZ();
		int locZDifference = loc1Z - loc2Z;
		if (locZDifference < 0)
			locZDifference = -locZDifference;

		int loc1Y = loc1.getBlockY();
		int loc2Y = loc2.getBlockY();
		int locYDifference = loc1Y - loc2Y;
		if (locYDifference < 0)
			locYDifference = -locYDifference;

		for (int i = 0; i <= locXDifference; i++) {
			for (int i2 = 0; i2 <= locZDifference; i2++) {
				for (int i3 = 0; i3 <= locYDifference; i3++) {
					Location loc = loc1.clone();
					loc.add(i, i3, i2);
					loc.getBlock().setType(mat);
					if (mat == Material.WOOL) {
						BlockState bs = loc.getBlock().getState();
						Wool wool = (Wool) bs.getData();
						wool.setColor(woolColor);
						bs.update();
					}
				}
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
					infoSignState.setLine(1, ChatColor.BLUE + "INTE I SPEL");
					infoSignState.setLine(2, "");
				} else {
					infoSignState.setLine(1,
							ChatColor.BOLD + activePlayer.getName());
					infoSignState.setLine(2,
							ChatColor.BOLD + Integer.toString(depositedMoney)
									+ "$");

				}
				infoSignState.setLine(0, changingColor + "<---------->");
				infoSignState.setLine(3, changingColor + "<---------->");
				infoSignState.update();
			}

			for (int i = 0; i < countDownSigns.size(); i++) {
				org.bukkit.block.Sign countDownSignState = (org.bukkit.block.Sign) countDownSigns
						.get(i).getBlock().getState();
				if (!isActiveGame) {
					countDownSignState.setLine(1, "");
					countDownSignState.setLine(2, "");
				} else {
					countDownSignState.setLine(
							1,
							ChatColor.RED + "" + ChatColor.BOLD
									+ Integer.toString(timeLeftOnRound));
					countDownSignState.setLine(
							2,
							ChatColor.RED + "" + ChatColor.BOLD
									+ Integer.toString(timeLeftOnRound));
				}
				countDownSignState.setLine(0, changingColor + "<---------->");
				countDownSignState.setLine(3, changingColor + "<---------->");
				countDownSignState.update();
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
					+ "Ett spel pågår redan här. Vänta 15 sekunder och försök sedan igen.");
			clicker.sendMessage(ChatColor.BLACK
					+ "<------------------------------------------------->");
			return;
		}
		playersInQue.add(new Que(clicker, "x2 eller inget"));
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
			if (isActiveGame)
				return;

			// Start new game
			isActiveGame = true;
			timeLeftOnRound = 15;
			activePlayer = p;
			depositedMoney = moneyTyped;
			TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.GREEN + "Lycka till!",
					null);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			Statics.economy.withdrawPlayer(p, depositedMoney);

			for (int i2 = playersInQue.size() - 1; i2 > -1; i2--) {
				for (int i3 = Statics.playersInQue.size() - 1; i3 > -1; i3--) {
					if (playersInQue.get(i2).p.getName() == Statics.playersInQue
							.get(i3).getName()) {
						Statics.playersInQue.remove(i3);
					}
				}
			}
			playersInQue = new ArrayList<Que>();
			PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + p.getName()
					+ ChatColor.GREEN + " satsade precis "
					+ ChatColor.DARK_GREEN + Integer.toString(depositedMoney)
					+ ChatColor.GREEN + "$ i x2 eller inget. Lycka till!");
		}
	}
}