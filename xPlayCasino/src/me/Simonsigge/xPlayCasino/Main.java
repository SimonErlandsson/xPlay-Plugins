package me.Simonsigge.xPlayCasino;

import java.util.ArrayList;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	int elapsedTimeFunction;
	ArrayList<DoubleOrNothing> DONList;
	ArrayList<Jackpot> jackpotList;
	RockPaperScissors rockPaperScissors;
	Roulette roulette;
	World w;

	Location DONSign1, DONSign2, DONSign3, DONSign4;
	Location JACKSign1, JACKSign2, JACKSign3, JACKSign4, JACKSign5, JACKSign6, JACKSign7, JACKSign8;
	Location RPSJoinSign1, RPSJoinSign2, RPSJoinSign3, RPSJoinSign4;
	ArrayList<Location> rouletteJoinLoc;

	public void onEnable() {
		System.out.println("xPlayCasino -> Enabled.");
		InitCasino();
		getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				oncePerTick();
			}
		}, 0, 1);
	}

	public void onDisable() {
		System.out.println("xPlayCasino -> Disabled.");
	}

	public void InitCasino() {
		w = getServer().getWorlds().get(0);
		Statics.w = w;
		Statics.plugin = this;
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		Statics.economy = rsp.getProvider();
		Statics.playersInQue = new ArrayList<Player>();
		Statics.server = getServer();

		DONSign1 = new Location(w, -18, 50, -62);
		DONSign2 = new Location(w, -18, 50, -57);
		DONSign3 = new Location(w, -18, 44, -62);
		DONSign4 = new Location(w, -18, 44, -57);

		DONList = new ArrayList<DoubleOrNothing>();
		DONList.add(new DoubleOrNothing(DONSign1));
		DONList.add(new DoubleOrNothing(DONSign2));
		DONList.add(new DoubleOrNothing(DONSign3));
		DONList.add(new DoubleOrNothing(DONSign4));

		JACKSign1 = new Location(w, 19, 50, -56);
		JACKSign2 = new Location(w, 11, 50, -63);
		JACKSign3 = new Location(w, 11, 50, -56);
		JACKSign4 = new Location(w, 19, 50, -63);
		
		JACKSign5 = new Location(w, 19, 44, -56);
		JACKSign6 = new Location(w, 11, 44, -63);
		JACKSign7 = new Location(w, 11, 44, -56);
		JACKSign8 = new Location(w, 19, 44, -63);

		RPSJoinSign1 = new Location(w, -21, 57, -53);
		RPSJoinSign2 = new Location(w, -21, 57, -66);
		RPSJoinSign3 = new Location(w, -14, 57, -66);
		RPSJoinSign4 = new Location(w, -14, 57, -53);
		
		rouletteJoinLoc = new ArrayList<Location>();
		rouletteJoinLoc.add(new Location(w, 16, 59, -56));
		rouletteJoinLoc.add(new Location(w, 17, 59, -56));
		rouletteJoinLoc.add(new Location(w, 20, 59, -59));
		rouletteJoinLoc.add(new Location(w, 20, 59, -60));
		rouletteJoinLoc.add(new Location(w, 17, 59, -63));
		rouletteJoinLoc.add(new Location(w, 16, 59, -63));
		rouletteJoinLoc.add(new Location(w, 13, 59, -60));
		rouletteJoinLoc.add(new Location(w, 13, 59, -59));

		jackpotList = new ArrayList<Jackpot>();
		jackpotList.add(new Jackpot(JACKSign1, JACKSign2, 300, 10000));
		jackpotList.add(new Jackpot(JACKSign5, JACKSign6, 10000, 1000000));
		rockPaperScissors = new RockPaperScissors(RPSJoinSign1, RPSJoinSign2, RPSJoinSign3, RPSJoinSign4);
		roulette = new Roulette(rouletteJoinLoc);
		
		Statics.prefix = ChatColor.translateAlternateColorCodes('&', Statics.prefix);
	}

	public void oncePerTick() {
		elapsedTimeFunction++;
		if (elapsedTimeFunction >= 20)
			oncePerSecond();

		for (int i = 0; i < DONList.size(); i++)
			DONList.get(i).perTickUpdate();
		for (int i = 0; i < jackpotList.size(); i++)
			jackpotList.get(i).perTickUpdate();
		rockPaperScissors.perTickUpdate();
		roulette.perTickUpdate();
	}

	public void oncePerSecond() {
		elapsedTimeFunction = 0;

		for (int i = 0; i < DONList.size(); i++)
			DONList.get(i).perSecondUpdate();
		for (int i = 0; i < jackpotList.size(); i++)
			jackpotList.get(i).perSecondUpdate();
		rockPaperScissors.perSecondUpdate();
		roulette.perSecondUpdate();
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (commandLabel.equalsIgnoreCase("casino")) {
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "xPlayCasino ->");
			p.sendMessage(ChatColor.AQUA + "Skriv /x2 för att komma till x2 eller inget.");
			p.sendMessage(ChatColor.AQUA + "Skriv /jackpot för att komma till Jackpot.");
			p.sendMessage(ChatColor.AQUA + "Skriv /sten för att komma till Sten, sax, påse.");
			p.sendMessage(ChatColor.AQUA + "Skriv /roulette för att komma till Roulette.");
			p.sendMessage(ChatColor.AQUA + "Plugin av: Simonsigge.");
			return false;
		}
		if (commandLabel.equalsIgnoreCase("jackpot")) {
			if (args.length < 2|| args.length > 2) {
				p.sendMessage(ChatColor.AQUA + "Teleporterade dig till Jackpot...");
				p.teleport(new Location(w, 15, 49, -60));
				return false;
			}
			if (args[0].equalsIgnoreCase("start") && (sender.hasPermission("xPlayCasino.Admin") || sender.isOp()) && args.length == 2) {
				int arena = 0;
				if (tryParseInt(args[1]))  
					   arena = Integer.parseInt(args[1]);
				if (arena == 1 || arena == 2){
					jackpotList.get(arena - 1).forceJackpot = true;
					jackpotList.get(arena - 1).countDownTime = 15;
					p.sendMessage(ChatColor.AQUA + "Startade Jackpot i förväg...");
				} else {
					sender.sendMessage(ChatColor.RED + "Du glömde skriva vilken arena... (1/2)");
				}
			} else {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "Ett fel inträffade...");
			}
			return false;
		}
		if (commandLabel.equalsIgnoreCase("x2")) {
			p.sendMessage(ChatColor.AQUA + "Teleporterade dig till x2 eller inget.");
			p.teleport(new Location(w, -18, 49, -60));
			return false;
		}
		if (commandLabel.equalsIgnoreCase("sten")) {
			p.sendMessage(ChatColor.AQUA + "Teleporterade dig till Sten, sax, påse.");
			p.teleport(new Location(w, -17, 56, -58));
			return false;
		}
		if (commandLabel.equalsIgnoreCase("roulette")) {
			p.sendMessage(ChatColor.AQUA + "Teleporterade dig till Roulette.");
			p.teleport(new Location(w, 17, 58, -53));
			return false;
		}
		return false;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign s = (Sign) e.getClickedBlock().getState();
				if (s.getLine(1).equalsIgnoreCase("x2 eller inget")) {
					Location signLock = e.getClickedBlock().getLocation();
					if (signLock.equals(DONSign1) || signLock.equals(DONSign2) || signLock.equals(DONSign3) || signLock.equals(DONSign4))
						if (!(Statics.playersInQue.contains(e.getPlayer()))) {
							if (signLock.equals(DONSign1))
								DONList.get(0).onRightSign(e);
							if (signLock.equals(DONSign2))
								DONList.get(1).onRightSign(e);
							if (signLock.equals(DONSign3))
								DONList.get(2).onRightSign(e);
							if (signLock.equals(DONSign4))
								DONList.get(3).onRightSign(e);
						}
				}
				if (s.getLine(1).equalsIgnoreCase("Jackpot")) {
					Location signLock = e.getClickedBlock().getLocation();
					if (signLock.equals(JACKSign1) || signLock.equals(JACKSign2) || signLock.equals(JACKSign3)
							|| signLock.equals(JACKSign4))
						if (!(Statics.playersInQue.contains(e.getPlayer()))) {
							jackpotList.get(0).onRightSign(e);
						}
					if (signLock.equals(JACKSign5) || signLock.equals(JACKSign6) || signLock.equals(JACKSign7)
							|| signLock.equals(JACKSign8))
						if (!(Statics.playersInQue.contains(e.getPlayer()))) {
							jackpotList.get(1).onRightSign(e);
						}
				}
				if (s.getLine(1).equalsIgnoreCase("Sten, sax, påse") || s.getLine(0).equals(ChatColor.GREEN + "<---------->") || s.getLine(0).equals(ChatColor.DARK_GREEN + "<---------->")) {
					Location signLock = e.getClickedBlock().getLocation();
					if (signLock.equals(RPSJoinSign1) || signLock.equals(RPSJoinSign2) || signLock.equals(RPSJoinSign3)
							|| signLock.equals(RPSJoinSign4)){
						if (!(Statics.playersInQue.contains(e.getPlayer())))
							rockPaperScissors.onRightSign(e);
					return;
					}
					rockPaperScissors.onRightSignChallenge(e);
				}
				if (s.getLine(1).equalsIgnoreCase("Roulette")) {
					Location signLock = e.getClickedBlock().getLocation();
					if(rouletteJoinLoc.contains(signLock))
						roulette.onRightSign(e);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (Statics.playersInQue.contains(e.getPlayer())) {
			for (int i = 0; i < DONList.size(); i++)
				DONList.get(i).onChat(e);
			for (int i = 0; i < jackpotList.size(); i++)
				jackpotList.get(i).onChat(e);
			rockPaperScissors.onChat(e);
			roulette.onChat(e);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		rockPaperScissors.onInventoryClose(e);
		roulette.onInventoryClose(e);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
	}
 
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		rockPaperScissors.onInventoryClick(e);
		roulette.onInventoryClick(e);
	}
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
}