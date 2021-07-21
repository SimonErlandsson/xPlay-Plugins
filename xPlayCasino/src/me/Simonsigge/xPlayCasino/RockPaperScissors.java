package me.Simonsigge.xPlayCasino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.connorlinfoot.titleapi.TitleAPI;

import me.Simonsigge.xPlayCasino.RockPaperScissors.Alternatives;

public class RockPaperScissors {

	Location RPSJoinSign1, RPSJoinSign2, RPSJoinSign3, RPSJoinSign4;
	ArrayList<Que> playersInQue;
	ArrayList<RPSGame> activeGames;

	ArrayList<Location> leftSigns;
	ArrayList<Location> rightSigns;
	ArrayList<Location> fireWorkLocations;
	World w;
	Random r;

	HashMap<Player, Integer> inChoosePlayers;
	HashMap<Player, RPSGame> inChallengePlayers;
	ArrayList<String> playersInGame;

	Inventory chooseInv;
	Inventory challengeInv;

	ItemStack rock;
	ItemStack paper;
	ItemStack scissors;
	ItemStack cancel;
	
	Location print1, print2;

	enum Alternatives {
		ROCK, PAPER, SCISSORS
	};

	public RockPaperScissors(Location RPSJoinSign1, Location RPSJoinSign2, Location RPSJoinSign3,
			Location RPSJoinSign4) {
		w = RPSJoinSign1.getWorld();
		r = new Random();
		playersInQue = new ArrayList<Que>();
		activeGames = new ArrayList<RPSGame>();
		leftSigns = new ArrayList<Location>();
		rightSigns = new ArrayList<Location>();
		fireWorkLocations = new ArrayList<Location>();
		inChoosePlayers = new HashMap<Player, Integer>();
		inChallengePlayers = new HashMap<Player, RPSGame>();
		playersInGame = new ArrayList<String>();

		this.RPSJoinSign1 = RPSJoinSign1;
		this.RPSJoinSign2 = RPSJoinSign2;
		this.RPSJoinSign3 = RPSJoinSign3;
		this.RPSJoinSign4 = RPSJoinSign4;

		leftSigns.add(new Location(w, -21, 57, -55));
		leftSigns.add(new Location(w, -21, 57, -56));
		leftSigns.add(new Location(w, -21, 57, -57));
		leftSigns.add(new Location(w, -21, 57, -58));
		leftSigns.add(new Location(w, -21, 57, -59));
		leftSigns.add(new Location(w, -21, 57, -60));
		leftSigns.add(new Location(w, -21, 57, -61));
		leftSigns.add(new Location(w, -21, 57, -62));
		leftSigns.add(new Location(w, -21, 57, -63));
		leftSigns.add(new Location(w, -21, 57, -64));

		leftSigns.add(new Location(w, -21, 56, -55));
		leftSigns.add(new Location(w, -21, 56, -56));
		leftSigns.add(new Location(w, -21, 56, -57));
		leftSigns.add(new Location(w, -21, 56, -58));
		leftSigns.add(new Location(w, -21, 56, -59));
		leftSigns.add(new Location(w, -21, 56, -60));
		leftSigns.add(new Location(w, -21, 56, -61));
		leftSigns.add(new Location(w, -21, 56, -62));
		leftSigns.add(new Location(w, -21, 56, -63));
		leftSigns.add(new Location(w, -21, 56, -64));

		rightSigns.add(new Location(w, -14, 57, -64));
		rightSigns.add(new Location(w, -14, 57, -63));
		rightSigns.add(new Location(w, -14, 57, -62));
		rightSigns.add(new Location(w, -14, 57, -61));
		rightSigns.add(new Location(w, -14, 57, -60));
		rightSigns.add(new Location(w, -14, 57, -59));
		rightSigns.add(new Location(w, -14, 57, -58));
		rightSigns.add(new Location(w, -14, 57, -57));
		rightSigns.add(new Location(w, -14, 57, -56));
		rightSigns.add(new Location(w, -14, 57, -55));

		rightSigns.add(new Location(w, -14, 56, -64));
		rightSigns.add(new Location(w, -14, 56, -63));
		rightSigns.add(new Location(w, -14, 56, -62));
		rightSigns.add(new Location(w, -14, 56, -61));
		rightSigns.add(new Location(w, -14, 56, -60));
		rightSigns.add(new Location(w, -14, 56, -59));
		rightSigns.add(new Location(w, -14, 56, -58));
		rightSigns.add(new Location(w, -14, 56, -57));
		rightSigns.add(new Location(w, -14, 56, -56));
		rightSigns.add(new Location(w, -14, 56, -55));

		fireWorkLocations.add(new Location(w, -25, 56, -53));
		fireWorkLocations.add(new Location(w, -25, 56, -66));
		fireWorkLocations.add(new Location(w, -10, 56, -66));
		fireWorkLocations.add(new Location(w, -10, 56, -53));

		chooseInv = Bukkit.createInventory(null, 27,
				ChatColor.BLACK + "" + ChatColor.BOLD + "Välj vad du vill spela med!");
		challengeInv = Bukkit.createInventory(null, 27,
				ChatColor.BLACK + "" + ChatColor.BOLD + "Välj vad du vill utmana med!");

		rock = new ItemStack(Material.STONE, 1);
		paper = new ItemStack(Material.PAPER, 1);
		scissors = new ItemStack(Material.SHEARS, 1);
		cancel = new ItemStack(Material.BARRIER, 1);

		ItemMeta rockMeta = rock.getItemMeta();
		rockMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Sten");
		rockMeta.setLore(Arrays.asList(ChatColor.BLUE + "Tar ut: " + ChatColor.GREEN + "sax.",
				ChatColor.BLUE + "Förlorar mot: " + ChatColor.RED + "påse."));
		rock.setItemMeta(rockMeta);

		ItemMeta paperMeta = paper.getItemMeta();
		paperMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Påse");
		paperMeta.setLore(Arrays.asList(ChatColor.BLUE + "Tar ut: " + ChatColor.GREEN + "sten.",
				ChatColor.BLUE + "Förlorar mot: " + ChatColor.RED + "sax."));
		paper.setItemMeta(paperMeta);

		ItemMeta scissorsMeta = scissors.getItemMeta();
		scissorsMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Sax");
		scissorsMeta.setLore(Arrays.asList(ChatColor.BLUE + "Tar ut: " + ChatColor.GREEN + "påse.",
				ChatColor.BLUE + "Förlorar mot: " + ChatColor.RED + "sten."));
		scissors.setItemMeta(scissorsMeta);

		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Avbryt");
		cancelMeta.setLore(
				Arrays.asList(ChatColor.BLUE + "Avbryt utmaningen och", ChatColor.BLUE + "få pengarna tillbaka."));
		cancel.setItemMeta(cancelMeta);

		chooseInv.setItem(10, rock);
		chooseInv.setItem(13, scissors);
		chooseInv.setItem(16, paper);

		challengeInv.setItem(10, rock);
		challengeInv.setItem(13, scissors);
		challengeInv.setItem(16, paper);
		challengeInv.setItem(26, cancel);
		
		print1 = new Location(w, -26, 65, -51);
		print2 = new Location(w, -9, 55, -69);
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
			ArrayList<ArrayList<Location>> signs = new ArrayList<ArrayList<Location>>();
			signs.add(leftSigns);
			signs.add(rightSigns);

			for (ArrayList<Location> signList : signs) {
				int signIndex = 0;
				int offset = 0;
				for (Location loc : signList) {
					if (activeGames.size() > signIndex - offset) {
						if (signIndex < 10) {
							org.bukkit.block.Sign signState = (org.bukkit.block.Sign) loc.getBlock().getState();
							signState.setLine(1, ChatColor.BOLD + activeGames.get(signIndex).hostP.getName());
							signState.setLine(2,
									ChatColor.BOLD + Integer.toString(activeGames.get(signIndex).money) + "$");

							signState.setLine(0, changingColor + "<---------->");
							signState.setLine(3, changingColor + "<---------->");
							signState.update();
						} else {
							org.bukkit.block.Sign signState = (org.bukkit.block.Sign) loc.getBlock().getState();
							signState.setLine(1, ChatColor.RED + "" + ChatColor.BOLD
									+ Integer.toString(activeGames.get(signIndex - offset).timeBeforeTimeout));

							signState.setLine(0, changingColor + "<---------->");
							signState.setLine(3, changingColor + "<---------->");
							signState.update();
						}
					} else {
						org.bukkit.block.Sign signState = (org.bukkit.block.Sign) loc.getBlock().getState();
						signState.setLine(0, changingColor + "<---------->");
						signState.setLine(1, "");
						signState.setLine(2, "");
						signState.setLine(3, changingColor + "<---------->");
						signState.update();
					}
					if (signIndex == 9)
						offset = 10;
					signIndex++;
				}
			}
		}

		for (int i = activeGames.size() - 1; i > -1; i--) {
			activeGames.get(i).UpdateRPSGame();
			if (activeGames.get(i).shouldBeRemoved) {
				if (playersInGame.contains(activeGames.get(i).hostP.getName()))
					playersInGame.remove(activeGames.get(i).hostP.getName());
				for (Entry<Player, RPSGame> hM : inChallengePlayers.entrySet()) {
					if (hM.getValue() == activeGames.get(i)) {
						hM.getKey().closeInventory();
						TitleAPI.sendTitle(hM.getKey(), 10, 40, 10, ChatColor.RED + "Någon annan var snabbare!", null);
						hM.getKey().playSound(hM.getKey().getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
					}
				}
				activeGames.remove(i);
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
		if (activeGames.size() > 9) {
			clicker.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino"
					+ ChatColor.BLACK + "]------------------->");
			clicker.sendMessage(ChatColor.AQUA
					+ "Max antal spel nådd. Gå med i ett spel eller vänta 15 sekunder och försök sedan igen.");
			clicker.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
			return;
		}
		playersInQue.add(new Que(clicker, "Sten, sax, påse"));
	}

	public void onRightSignChallenge(PlayerInteractEvent e) {
		if (e.getClickedBlock().getLocation().getBlockY() == 57
				|| e.getClickedBlock().getLocation().getBlockY() == 56) {
			if (e.getClickedBlock().getLocation().subtract(0, 2, 0).getBlock().getType() == Material.STAINED_CLAY || e
					.getClickedBlock().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.STAINED_CLAY) {
				Location signLock = e.getClickedBlock().getLocation();
				Player p = e.getPlayer();

				if (signLock.getBlockY() == 56)
					signLock.setY(signLock.getBlockY() + 1);

				org.bukkit.block.Sign signState = (org.bukkit.block.Sign) signLock.getBlock().getState();
				String playerName = signState.getLine(1);
				String money = signState.getLine(2);
				if (playerName == null || money == null || playerName == "" || money == "" || playerName.length() < 2
						|| money.length() < 2)
					return;
				playerName = playerName.substring(2);
				money = money.substring(2, money.length() - 1);

				if (playerName.equals(p.getName())) {
					TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Du kan inte möta dig själv!", null);
					p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
					return;
				}

				for (RPSGame rps : activeGames) {
					if (rps.hostP.getName().equals(playerName) && money.equals(Integer.toString(rps.money))) {
						if (rps.canJoin == false) {
							TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Spelet pågår redan!", null);
							p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
							return;
						}
						p.openInventory(challengeInv);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
						inChallengePlayers.put(p, rps);
						return;
					}
				}
			}
		}
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
						p.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino"
								+ ChatColor.BLACK + "]------------------->");
						p.sendMessage(ChatColor.AQUA + "Du har avbrutit din satsning.");
						p.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
					}
					if (Statics.playersInQue.contains(p))
						Statics.playersInQue.remove(p);
					return;
				}
				if (activeGames.size() > 9) {
					playersInQue.get(i).shouldBeRemoved = true;
					if (p.isOnline()) {
						p.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino"
								+ ChatColor.BLACK + "]------------------->");
						p.sendMessage(ChatColor.AQUA + "Max antal spel nått, försök igen senare.");
						p.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
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
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Du har inte råd!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				return;
			}
			if (moneyTyped < 300) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Du måste satsa minst 300$!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				return;
			}
			
			if (moneyTyped > 1000000) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED
						+ "Du kan max satsa 1 000 000$!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				return;
			}

			if (playersInGame.contains(p.getName())) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Du kan inte satsa igen!", null);
				return;
			}

			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			Statics.economy.withdrawPlayer(p, moneyTyped);

			p.openInventory(chooseInv);
			inChoosePlayers.put(p, moneyTyped);

			if (Statics.playersInQue.contains(p))
				Statics.playersInQue.remove(p);

			for (Que q : playersInQue) {
				if (q.p.getName() == p.getName())
					q.shouldBeRemoved = true;
			}
		}
	}

	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		Player p = (Player) e.getWhoClicked();
		if (inChoosePlayers.containsKey(p))
			e.setCancelled(true);
		if (inChallengePlayers.containsKey(p))
			e.setCancelled(true);

		// First inv, "new game"
		if (e.getClickedInventory().getTitle()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD + "Välj vad du vill spela med!")) {
			ItemStack item = e.getCurrentItem();
			if (item == null)
				return;
			e.setCancelled(true);

			Alternatives choice = null;
			if (item.equals(rock))
				choice = Alternatives.ROCK;
			if (item.equals(paper))
				choice = Alternatives.PAPER;
			if (item.equals(scissors))
				choice = Alternatives.SCISSORS;

			if (choice == null)
				return;

			activeGames.add(new RPSGame(p, choice, inChoosePlayers.get(p), this));
			inChoosePlayers.remove(p);
			playersInGame.add(p.getName());
			p.closeInventory();
		}

		if (e.getClickedInventory().getTitle()
				.equals(ChatColor.BLACK + "" + ChatColor.BOLD + "Välj vad du vill utmana med!")) {
			ItemStack item = e.getCurrentItem();
			if (item == null)
				return;
			e.setCancelled(true);

			Alternatives choice = null;
			if (item.equals(rock))
				choice = Alternatives.ROCK;
			if (item.equals(paper))
				choice = Alternatives.PAPER;
			if (item.equals(scissors))
				choice = Alternatives.SCISSORS;
			if (item.equals(cancel))
				p.closeInventory();

			if (choice == null)
				return;

			RPSGame currentGame = inChallengePlayers.get(p);

			if (currentGame.shouldBeRemoved == true || currentGame.canJoin == false) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Någon annan var snabbare!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				p.closeInventory();
				return;
			}

			if (Statics.economy.getBalance(p) < currentGame.money) {
				TitleAPI.sendTitle(p, 10, 40, 10, ChatColor.RED + "Du har inte råd!", null);
				p.playSound(p.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				p.closeInventory();
				return;
			}

			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			Statics.economy.withdrawPlayer(p, currentGame.money);
			currentGame.canJoin = false;
			currentGame.elapsedTime = 0;
			currentGame.timeBeforeTimeout = 5;
			currentGame.challengingP = p;
			currentGame.challengingChoice = choice;
			currentGame.hostP.playSound(currentGame.hostP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			p.closeInventory();
			currentGame.hostP.sendMessage(ChatColor.RED + "5...");
			p.sendMessage(ChatColor.RED + "5...");
		}
	}

	public void onInventoryClose(InventoryCloseEvent e) {
		final Player p = (Player) e.getPlayer();
		if (inChoosePlayers.containsKey(p)) {
			Statics.server.getScheduler().scheduleSyncDelayedTask(Statics.plugin, new Runnable() {
				public void run() {
					p.openInventory(chooseInv);
				}
			}, 1);
		}

		if (inChallengePlayers.containsKey(p))
			inChallengePlayers.remove(p);
	}

	public void DoFireWorks() {
		for (Location fireWorkLocation : fireWorkLocations) {
			Firework fw = (Firework) w.spawnEntity(fireWorkLocation, EntityType.FIREWORK);
			FireworkMeta fwm = fw.getFireworkMeta();
			FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.GREEN)
					.withFade(Color.RED).with(Type.BALL_LARGE).trail(true).build();
			fwm.addEffect(effect);
			fwm.setPower(1);
			fw.setFireworkMeta(fwm);
		}

	}
}

class RPSGame {
	Player hostP;
	Player challengingP;
	Alternatives hostChoice;
	Alternatives challengingChoice;
	int money;
	int timeBeforeTimeout;
	int elapsedTime;
	boolean shouldBeRemoved;
	boolean canJoin;
	RockPaperScissors RPS;

	public RPSGame(Player hostP, Alternatives hostChoice, int money, RockPaperScissors RPS) {
		shouldBeRemoved = false;
		canJoin = true;
		this.hostP = hostP;
		this.hostChoice = hostChoice;
		this.money = money;
		this.RPS = RPS;
		timeBeforeTimeout = 180;

		TitleAPI.sendTitle(hostP, 10, 40, 10, ChatColor.GREEN + "Lycka till!", null);
		hostP.playSound(hostP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);

		RPS.PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + hostP.getName() + ChatColor.GREEN + " satsade precis "
				+ ChatColor.DARK_GREEN + Integer.toString(money) + ChatColor.GREEN
				+ "$ i Sten, sax, påse. Lycka till!");
	}

	public void UpdateRPSGame() {
		elapsedTime++;
		if (elapsedTime >= 20) {
			timeBeforeTimeout--;
			elapsedTime = 0;

			if (canJoin == false && timeBeforeTimeout != 0) {
				hostP.sendMessage(ChatColor.RED + Integer.toString(timeBeforeTimeout) + "...");
				challengingP.sendMessage(ChatColor.RED + Integer.toString(timeBeforeTimeout) + "...");
			}
		}

		if (timeBeforeTimeout < 1 && shouldBeRemoved == false && canJoin == true) {
			if (hostP.isOnline()) {
				hostP.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino"
						+ ChatColor.BLACK + "]------------------->");
				hostP.sendMessage(ChatColor.AQUA + "Ingen svarade på din satsning. Du har fått pengarna tillbaka.");
				hostP.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
			}
			Statics.economy.depositPlayer(hostP, money);
			shouldBeRemoved = true;
		}

		if (canJoin == false && timeBeforeTimeout < 1 && shouldBeRemoved == false) {
			Player winner = DrawWinner(hostP, challengingP, hostChoice, challengingChoice);

			if (winner == null) {
				RPS.PrintMessageRoom(Statics.prefix + ChatColor.GREEN + "Lika mellan " + ChatColor.DARK_GREEN
						+ hostP.getName() + ChatColor.GREEN + " och " + ChatColor.DARK_GREEN + challengingP.getName()
						+ ChatColor.GREEN + " om " + ChatColor.DARK_GREEN + Integer.toString(money) + ChatColor.GREEN
						+ "$ i Sten, sax, påse. Rättvist!");
				TitleAPI.sendTitle(hostP, 10, 40, 10, ChatColor.YELLOW + "Lika! Kör om igen!", null);
				TitleAPI.sendTitle(challengingP, 10, 40, 10, ChatColor.YELLOW + "Lika! Kör om igen!", null);
				hostP.playSound(hostP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				challengingP.playSound(challengingP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				Statics.economy.depositPlayer(hostP, money);
				Statics.economy.depositPlayer(challengingP, money);
				shouldBeRemoved = true;
				return;
			}

			if (winner.getName() == hostP.getName()) {
				RPS.PrintMessageRoom(Statics.prefix + ChatColor.DARK_GREEN + hostP.getName() + ChatColor.GREEN
						+ " vann över " + ChatColor.RED + challengingP.getName() + ChatColor.GREEN + " om "
						+ ChatColor.DARK_GREEN + Integer.toString(money) + ChatColor.GREEN
						+ "$ i Sten, sax, påse. Grattis!");
				TitleAPI.sendTitle(hostP, 10, 40, 10, ChatColor.GREEN + "Du vann! Grattis!", null);
				hostP.playSound(hostP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				Statics.economy.depositPlayer(hostP, money * 2);

				TitleAPI.sendTitle(challengingP, 10, 40, 10, ChatColor.RED + "Du förlorade! Synd! :(", null);
				challengingP.playSound(challengingP.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				RPS.DoFireWorks();
				shouldBeRemoved = true;
				return;
			}

			if (winner.getName() == challengingP.getName()) {
				RPS.PrintMessageRoom(
						Statics.prefix
								+ ChatColor.DARK_GREEN + challengingP.getName() + ChatColor.GREEN + " vann över "
								+ ChatColor.RED + hostP.getName() + ChatColor.GREEN + " om " + ChatColor.DARK_GREEN
								+ Integer.toString(money) + ChatColor.GREEN + "$ i Sten, sax, påse. Grattis!");
				TitleAPI.sendTitle(challengingP, 10, 40, 10, ChatColor.GREEN + "Du vann! Grattis!", null);
				challengingP.playSound(challengingP.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				Statics.economy.depositPlayer(challengingP, money * 2);

				TitleAPI.sendTitle(hostP, 10, 40, 10, ChatColor.RED + "Du förlorade! Synd! :(", null);
				hostP.playSound(hostP.getLocation(), Sound.ENTITY_COW_HURT, 100, 100);
				RPS.DoFireWorks();
				shouldBeRemoved = true;
				return;
			}
			shouldBeRemoved = true;
		}
	}

	public Player DrawWinner(Player p1, Player p2, Alternatives a1, Alternatives a2) {
		if (a1 == a2)
			return null;
		if ((a1 == Alternatives.PAPER && a2 == Alternatives.ROCK)
				|| (a1 == Alternatives.ROCK && a2 == Alternatives.SCISSORS)
				|| (a1 == Alternatives.SCISSORS && a2 == Alternatives.PAPER))
			return p1;
		else
			return p2;
	}
}
