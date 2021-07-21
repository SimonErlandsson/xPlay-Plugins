package me.Simonsigge.xPlayDonator.Message;

import me.Simonsigge.xPlayDonator.Nodes.Misc;

import org.bukkit.entity.Player;

public class MessageAPI {
	
	private MessageUtilities msgUtil;
	
	public MessageAPI() {
		msgUtil = new MessageUtilities();
	}
	
	public MessageUtilities getMsgUtil() {
		return msgUtil;
	}
	
	public void noPerm(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu har inte donerat. G� in p� &4https://xPlayServer.net/donera &cf�r mer information.");
		msgUtil.playFailSound(p);
	}
	
	public void consoleOnly(Player p) {
		msgUtil.sendCustomMessage(p, "&cDet g�r enbart att k�ra det kommandot fr�n konsollen.");
		msgUtil.playFailSound(p);
	}
	
	public void successOpenGUI(Player p) {
		msgUtil.sendCustomMessage(p, "Din skr�ddarsydda meny �ppnas. Tack f�r din donation!");
		msgUtil.playSuccessSound(p);
	}
	
	public void removeCloth(Player p) {
		msgUtil.sendCustomMessage(p, "Plagget togs av. Anv�nd &2/donator &af�r att s�tta p� det igen.");
		msgUtil.playSuccessSound(p);
	}
	
	public void donateLink(Player p) {
		msgUtil.sendCustomMessage(p, "Klicka p� l�nken -> &2https://xPlayServer.net/donera");
		msgUtil.playSuccessSound(p);
	}
	
	public void moreInfo(Player p) {
		msgUtil.sendCustomMessage(p, "F�r att f� den b�sta �verblicken �ver dina tillg�ngar rekommenderar vi att du g�r in p� hemsidan. L�nken �r -> &2https://xPlayServer.net/donera");
		msgUtil.playSuccessSound(p);
	}
	
	public void successClothes(Player p) {
		msgUtil.sendCustomMessage(p, "Ditt plagg har nu satts p�.");
		msgUtil.playSuccessSound(p);
	}
	
	public void noBooster(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu har inga boosters av denna typ.");
		msgUtil.playFailSound(p);
	}
	
	public void wrongServer(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu �r inne p� fel delserver f�r aktivering av denna booster.");
		msgUtil.playFailSound(p);
	}
	
	public void tooFewPlayers(Player p) {
		msgUtil.sendCustomMessage(p, "&cDet �r f�r f� spelare inne p� din delserver f�r aktivering av booster. Samla ihop ett g�ng p� minst &4" + Misc.BOOSTER_REQUIRED_PLAYERS + " &cspelare och aktivera igen.");
		msgUtil.playFailSound(p);
	}
	
	public void donatorIsDisabled(Player p) {
		msgUtil.sendCustomMessage(p, "&cDonator-kommandot �r avst�ngt p� din delserver.");
		msgUtil.playFailSound(p);
	}
	
	public void donatorIsDisabledOutsideOfSpawn(Player p) {
		msgUtil.sendCustomMessage(p, "&cDonator-kommandot �r avst�ngt n�r du inte �r i spawnomr�det.");
		msgUtil.playFailSound(p);
	}
	
	public void alreadyActiveBooster(Player p) {
		msgUtil.sendCustomMessage(p, "&cEn booster �r redan aktiv p� denna delserver. V�nta tills den har g�tt ut och aktivera sedan igen.");
		msgUtil.playFailSound(p);
	}
	
	public void boosterActivated(Player p) {
		msgUtil.sendCustomMessage(p, "Din booster aktiverades.");
		msgUtil.broadcastBungeeCustomMessage("&6" + p.getName() + " &eaktiverade precis en booster i &6" + p.getWorld().getName() + "&e. Skynda dig dit!");
		msgUtil.playSuccessSound(p);
	}
	
	public void featureTimeLeft(Player p, String timeLeft) {
		msgUtil.sendCustomMessage(p, "&cDu m�ste v�nta i &4" + timeLeft + " &cinnan du kan aktivera denna funktionen igen.");
		msgUtil.playFailSound(p);
	}
	
	public void featureAlreadyInQue(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu h�ller redan p� att anv�nda en chatt-funktion. Skriv 'avbryt' i chatten f�r att avbryta.");
		msgUtil.playFailSound(p);
	}
	
	public void featureChatColorPrompt(Player p) {
		p.sendMessage("");
		p.sendMessage("");
		msgUtil.sendCustomMessage(p, "&aSkriv �nskat f�rg-meddelande nu. Anv�nd '&' f�ljt av en siffra eller bokstav f�r att v�lja f�rg. Skriv 'avbryt' i chatten f�r att avbryta.");
		p.sendMessage("");
		p.sendMessage("");
	}
	
	public void featureChatShoutPrompt(Player p) {
		p.sendMessage("");
		p.sendMessage("");
		msgUtil.sendCustomMessage(p, "&aSkriv �nskat skrik-meddelande nu. Anv�nd '&' f�ljt av en siffra eller bokstav f�r att v�lja f�rg. Skriv 'avbryt' i chatten f�r att avbryta.");
		p.sendMessage("");
		p.sendMessage("");
	}
	
	public void featureChatSuccess(Player p) {
		msgUtil.sendCustomMessage(p, "Ditt meddelande skickas.");
		msgUtil.playSuccessSound(p);
	}
	
	public void featureChatCancel(Player p) {
		msgUtil.sendCustomMessage(p, "&cAvbr�t chatt-funktion.");
		msgUtil.playFailSound(p);
	}
	
	public void boosterIsActiveX2(Player p, String host) {
		msgUtil.sendCustomMessage(p, "&eEn booster �r aktiv p� denna servern (&6" + host + "&e). Du tj�nar just nu dubbelt s� mycket pengar!");
	}
	
	public void boosterIsActiveDrop(Player p, String host) {
		msgUtil.sendCustomMessage(p, "&eEn booster �r aktiv p� denna servern (&6" + host + "&e). Stanna h�r i spawnen f�r att f� awesome items!");
	}
	
	public void x2Activated() {
		msgUtil.broadcastCustomMessage("&eEn booster aktiverades p� din delserver. Du tj�nar nu dubbelt s� mycket pengar i &6tio &eminuter!");
	}
	
	public void x2Deactivated() {
		msgUtil.broadcastCustomMessage("&eBoostern har nu tagit slut. Vill du aktivera en booster f�r hela servern kan du donera. Se: &6https://www.xPlayServer.net/donera&e.");
	}
	
	public void dropActivated() {
		msgUtil.broadcastCustomMessage("&eDrop-partyt har aktiverats i Stad. G� till &6/stad &ef�r att f� awesome items!");
	}
	
	public void dropped(String player, String item, String dropper) {
		msgUtil.broadcastCustomMessage("&dDROP: &5" + player + " &dfick precis &5" + item + " &di &5" + dropper + "&ds drop-party. Skriv &5/stad &df�r att vara med i partyt!");
	}
	
	public void dropDeactivated() {
		msgUtil.broadcastCustomMessage("&eDrop-partyt �r nu slut. Vill du aktivera drop-partyt f�r hela servern kan du donera. Se: &6https://www.xPlayServer.net/donera&e.");
	}
	
	public void dropNoOneIsInZone() {
		msgUtil.broadcastCustomMessage("&dDROP: &cEtt f�rem�l skulle ha droppats i partyt, men ingen var i drop-zonen! G� till &4/stad &cf�r gratis f�rem�l!");
	}
}
