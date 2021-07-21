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
		msgUtil.sendCustomMessage(p, "&cDu har inte donerat. Gå in på &4https://xPlayServer.net/donera &cför mer information.");
		msgUtil.playFailSound(p);
	}
	
	public void consoleOnly(Player p) {
		msgUtil.sendCustomMessage(p, "&cDet går enbart att köra det kommandot från konsollen.");
		msgUtil.playFailSound(p);
	}
	
	public void successOpenGUI(Player p) {
		msgUtil.sendCustomMessage(p, "Din skräddarsydda meny öppnas. Tack för din donation!");
		msgUtil.playSuccessSound(p);
	}
	
	public void removeCloth(Player p) {
		msgUtil.sendCustomMessage(p, "Plagget togs av. Använd &2/donator &aför att sätta på det igen.");
		msgUtil.playSuccessSound(p);
	}
	
	public void donateLink(Player p) {
		msgUtil.sendCustomMessage(p, "Klicka på länken -> &2https://xPlayServer.net/donera");
		msgUtil.playSuccessSound(p);
	}
	
	public void moreInfo(Player p) {
		msgUtil.sendCustomMessage(p, "För att få den bästa överblicken över dina tillgångar rekommenderar vi att du går in på hemsidan. Länken är -> &2https://xPlayServer.net/donera");
		msgUtil.playSuccessSound(p);
	}
	
	public void successClothes(Player p) {
		msgUtil.sendCustomMessage(p, "Ditt plagg har nu satts på.");
		msgUtil.playSuccessSound(p);
	}
	
	public void noBooster(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu har inga boosters av denna typ.");
		msgUtil.playFailSound(p);
	}
	
	public void wrongServer(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu är inne på fel delserver för aktivering av denna booster.");
		msgUtil.playFailSound(p);
	}
	
	public void tooFewPlayers(Player p) {
		msgUtil.sendCustomMessage(p, "&cDet är för få spelare inne på din delserver för aktivering av booster. Samla ihop ett gäng på minst &4" + Misc.BOOSTER_REQUIRED_PLAYERS + " &cspelare och aktivera igen.");
		msgUtil.playFailSound(p);
	}
	
	public void donatorIsDisabled(Player p) {
		msgUtil.sendCustomMessage(p, "&cDonator-kommandot är avstängt på din delserver.");
		msgUtil.playFailSound(p);
	}
	
	public void donatorIsDisabledOutsideOfSpawn(Player p) {
		msgUtil.sendCustomMessage(p, "&cDonator-kommandot är avstängt när du inte är i spawnområdet.");
		msgUtil.playFailSound(p);
	}
	
	public void alreadyActiveBooster(Player p) {
		msgUtil.sendCustomMessage(p, "&cEn booster är redan aktiv på denna delserver. Vänta tills den har gått ut och aktivera sedan igen.");
		msgUtil.playFailSound(p);
	}
	
	public void boosterActivated(Player p) {
		msgUtil.sendCustomMessage(p, "Din booster aktiverades.");
		msgUtil.broadcastBungeeCustomMessage("&6" + p.getName() + " &eaktiverade precis en booster i &6" + p.getWorld().getName() + "&e. Skynda dig dit!");
		msgUtil.playSuccessSound(p);
	}
	
	public void featureTimeLeft(Player p, String timeLeft) {
		msgUtil.sendCustomMessage(p, "&cDu måste vänta i &4" + timeLeft + " &cinnan du kan aktivera denna funktionen igen.");
		msgUtil.playFailSound(p);
	}
	
	public void featureAlreadyInQue(Player p) {
		msgUtil.sendCustomMessage(p, "&cDu håller redan på att använda en chatt-funktion. Skriv 'avbryt' i chatten för att avbryta.");
		msgUtil.playFailSound(p);
	}
	
	public void featureChatColorPrompt(Player p) {
		p.sendMessage("");
		p.sendMessage("");
		msgUtil.sendCustomMessage(p, "&aSkriv önskat färg-meddelande nu. Använd '&' följt av en siffra eller bokstav för att välja färg. Skriv 'avbryt' i chatten för att avbryta.");
		p.sendMessage("");
		p.sendMessage("");
	}
	
	public void featureChatShoutPrompt(Player p) {
		p.sendMessage("");
		p.sendMessage("");
		msgUtil.sendCustomMessage(p, "&aSkriv önskat skrik-meddelande nu. Använd '&' följt av en siffra eller bokstav för att välja färg. Skriv 'avbryt' i chatten för att avbryta.");
		p.sendMessage("");
		p.sendMessage("");
	}
	
	public void featureChatSuccess(Player p) {
		msgUtil.sendCustomMessage(p, "Ditt meddelande skickas.");
		msgUtil.playSuccessSound(p);
	}
	
	public void featureChatCancel(Player p) {
		msgUtil.sendCustomMessage(p, "&cAvbröt chatt-funktion.");
		msgUtil.playFailSound(p);
	}
	
	public void boosterIsActiveX2(Player p, String host) {
		msgUtil.sendCustomMessage(p, "&eEn booster är aktiv på denna servern (&6" + host + "&e). Du tjänar just nu dubbelt så mycket pengar!");
	}
	
	public void boosterIsActiveDrop(Player p, String host) {
		msgUtil.sendCustomMessage(p, "&eEn booster är aktiv på denna servern (&6" + host + "&e). Stanna här i spawnen för att få awesome items!");
	}
	
	public void x2Activated() {
		msgUtil.broadcastCustomMessage("&eEn booster aktiverades på din delserver. Du tjänar nu dubbelt så mycket pengar i &6tio &eminuter!");
	}
	
	public void x2Deactivated() {
		msgUtil.broadcastCustomMessage("&eBoostern har nu tagit slut. Vill du aktivera en booster för hela servern kan du donera. Se: &6https://www.xPlayServer.net/donera&e.");
	}
	
	public void dropActivated() {
		msgUtil.broadcastCustomMessage("&eDrop-partyt har aktiverats i Stad. Gå till &6/stad &eför att få awesome items!");
	}
	
	public void dropped(String player, String item, String dropper) {
		msgUtil.broadcastCustomMessage("&dDROP: &5" + player + " &dfick precis &5" + item + " &di &5" + dropper + "&ds drop-party. Skriv &5/stad &dför att vara med i partyt!");
	}
	
	public void dropDeactivated() {
		msgUtil.broadcastCustomMessage("&eDrop-partyt är nu slut. Vill du aktivera drop-partyt för hela servern kan du donera. Se: &6https://www.xPlayServer.net/donera&e.");
	}
	
	public void dropNoOneIsInZone() {
		msgUtil.broadcastCustomMessage("&dDROP: &cEtt föremål skulle ha droppats i partyt, men ingen var i drop-zonen! Gå till &4/stad &cför gratis föremål!");
	}
}
