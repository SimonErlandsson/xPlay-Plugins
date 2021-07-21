package me.Simonsigge.xPlayCasino;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Que {
	
	Player p;
	int timeBeforeTimeout;
	int elapsedTime;
	boolean shouldBeRemoved;
	String gameMode;
	
	public Que(Player p, String gameMode){
		shouldBeRemoved = false;
		this.p = p;
		timeBeforeTimeout = 30;
		Statics.playersInQue.add(p);
		this.gameMode = gameMode;
	}
	
	public void UpdateQue(){
		elapsedTime++;
		if(elapsedTime >= 20){
			timeBeforeTimeout--;
			elapsedTime = 0;
		}
		if(p.isOnline() && shouldBeRemoved == false){
		p.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino" + ChatColor.BLACK + "]------------------->");
		p.sendMessage(ChatColor.AQUA + "Du har gått med i en kö till " + gameMode + ". Skriv hur mycket pengar du vill satsa i chatten. För att avbryta skriv 'avbryt' eller vänta i " + timeBeforeTimeout + " sekunder.");
		p.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
		}
		
		if(timeBeforeTimeout <= 1){
			if(p.isOnline()){
			p.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino" + ChatColor.BLACK + "]------------------->");
			p.sendMessage(ChatColor.AQUA + "Du svarade inte på din satsning. Vill du göra en ny satsning klicka på skylten igen.");
			p.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
			}
			if(Statics.playersInQue.contains(p))
				Statics.playersInQue.remove(p);
			shouldBeRemoved = true;
		}
	}

}