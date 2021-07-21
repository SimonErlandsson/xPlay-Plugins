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
		p.sendMessage(ChatColor.AQUA + "Du har g�tt med i en k� till " + gameMode + ". Skriv hur mycket pengar du vill satsa i chatten. F�r att avbryta skriv 'avbryt' eller v�nta i " + timeBeforeTimeout + " sekunder.");
		p.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
		}
		
		if(timeBeforeTimeout <= 1){
			if(p.isOnline()){
			p.sendMessage(ChatColor.BLACK + "<-------------------[" + ChatColor.WHITE + "xPlayCasino" + ChatColor.BLACK + "]------------------->");
			p.sendMessage(ChatColor.AQUA + "Du svarade inte p� din satsning. Vill du g�ra en ny satsning klicka p� skylten igen.");
			p.sendMessage(ChatColor.BLACK + "<------------------------------------------------->");
			}
			if(Statics.playersInQue.contains(p))
				Statics.playersInQue.remove(p);
			shouldBeRemoved = true;
		}
	}

}