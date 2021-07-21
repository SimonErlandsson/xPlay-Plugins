package me.Simonsigge.xPlayDonator.Commands;

import java.util.ArrayList;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Permissions;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Booster;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoosterAdder {
	
	private ArrayList<BoosterObject> scheduledAdds;
	
	public BoosterAdder() {
		scheduledAdds = new ArrayList<BoosterObject>();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				if (scheduledAdds.size() > 0) {
					BoosterObject bO = scheduledAdds.get(0);
					addBooster(bO.p, bO.booster, bO.number, bO.addOrRemove, bO.sender);
					scheduledAdds.remove(0);
				}
				
			} }, 20, 20);
	}
	
	public void scheduleBoosterAdd(Player p, Booster booster, int number, boolean addOrRemove, CommandSender sender) {
		BoosterObject bO = new BoosterObject();
		bO.p = p;
		bO.booster = booster;
		bO.number = number;
		bO.addOrRemove = addOrRemove;
		bO.sender = sender;
		scheduledAdds.add(bO);
	}
	
	private void addBooster(Player p, Booster booster, int number, boolean addOrRemove, CommandSender sender) {
		if (!p.hasPermission(Permissions.DONATOR_PERM)) {
			sender.sendMessage("§cSpelaren har aldrig tidigare donerat.");
			return;
		}
		
		final Player fp = p;
		final Booster fbooster = booster;
		final int fnumber = number;
		final boolean faddOrRemove = addOrRemove;
		final CommandSender fsender = sender;
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				Main.getDataHandler().trySavePlayerData(fp);
				Main.getDataHandler().tryLoadPlayerData(fp);
				
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                		int currentBoosters = Main.getDataHandler().getBooster(fp, fbooster);
                		
                		if (faddOrRemove)
                			currentBoosters += fnumber;
                		else if (currentBoosters - fnumber >= 0)
                			currentBoosters -= fnumber;
                		else {
                			fsender.sendMessage("§cKan inte sätta antalet boosters till negativt.");
                			return;
                		}
                		
                		Main.getDataHandler().setBooster(p, booster, currentBoosters);
                		fsender.sendMessage("§aVerkställdes!");
                    }
                });
			}});
	}

}

class BoosterObject {
	
	public Player p;
	public Booster booster;
	public int number;
	public boolean addOrRemove;
	public CommandSender sender;
}

