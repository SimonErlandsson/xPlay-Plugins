package me.Simonsigge.xPlayHub;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookManager {

	private ItemStack book;

	public BookManager() {
		InitBook();
	}

	public ItemStack getBook() {
		return book;
	}

	private void InitBook() {
		book = new ItemStack(Material.WRITTEN_BOOK, 1);

		BookMeta bookMeta = (BookMeta) book.getItemMeta();

		bookMeta.setAuthor("xPlay Server");
		bookMeta.setTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "V�lkommen till xPlay!");

		bookMeta.addPage(ChatColor.BOLD + "xPlay Server" + ChatColor.RESET + "\nRikedomar f�ds med gemenskap" + ChatColor.BOLD + "\n\n\nHemsida:"
				+ ChatColor.RESET + "\nxPlayServer.net" + ChatColor.BOLD
				+ "\n\nDonera:" + ChatColor.RESET + "\nxPlayServer.net/donera"
				+ ChatColor.BOLD + "\n\nDiscord:" + ChatColor.RESET
				+ "\ndiscord.gg/zzzsSYc");

		bookMeta.addPage(ChatColor.BOLD
				+ "V�lkommen till xPlay!"
				+ ChatColor.RESET
				+ "\n\n\nF�r din egen, och andras, trivsel �r det viktigt att du l�ser igenom denna bok. \n\nBoken inneh�ller regler och information om xPlays olika delservrar.");
		bookMeta.addPage(ChatColor.BOLD
				+ "Regler"
				+ ChatColor.RESET
				+ "\n1. Fuskklienter �r helt f�rbjudna (fly, xray, kill aura, auto-fish) \n\n2. H�ll en trevlig ton i chatten och g�r inte reklam f�r streams/videor eller andra servrar \n\n3. Anm�l buggar med /report BUGG <info>");
		bookMeta.addPage("4. Det �r HELT f�rbjudet att s�lja PlayMynt f�r riktiga pengar och tj�nster utanf�r xPlay \n\n5. Bedr�geri �r inte okej, var �rlig med aff�rer; skapa inte f�llor \n\n6. Anm�l spelare med /report <spelare> <anledning>");
		bookMeta.addPage("7. AFK-maskiner �r f�rbjudna \n\n8. Att boosta kills i PvP �r f�rbjudet \n\n9. Extremt stora farmer och annat som kan bidra till �kat lagg p� servern �r f�rbjudet (max en farm-chunk per person)");
		bookMeta.addPage("10. Macros och andra scripts �r f�rbjudna");
		bookMeta.addPage(ChatColor.BOLD
				+ "Information"
				+ ChatColor.RESET
				+ "\n\nGemensamt f�r alla xPlays delservrar �r dina PlayMynt (/money). \n\nPlayMynt kan bland annat anv�ndas till att k�pa marker, aff�rer, pvpkits och grunda st�der.");
		bookMeta.addPage("Din klass p� servern (som syns i chatten n�r du skriver) grundar sig �ven p� ditt innehav av PlayMynt. \n\nDu kan tj�na PlayMynt genom att bland annat pvpa, spela minispel, �ga st�der, �ga aff�rer, spela i kasino och arbeta i xPlayArbete.");
		bookMeta.addPage(ChatColor.BOLD
				+ "Beskrivning av delservrar"
				+ ChatColor.RESET
				+ "\n\nResten av denna bok best�r av beskrivningar av alla serverns delservrar. \n\nOrkar du �r det en klar f�rdel att l�sa igenom all information d� du slipper att leta reda p� informationen senare.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayArbete"
				+ ChatColor.RESET
				+ "\n\nArbete har som syfte att vara en arbetsplats f�r serverns spelare. \n\nH�r kr�vs ingen tidigare erfarenhet, utan det �r bara att logga in och b�rja tj�na PlayMynt.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayPvP"
				+ ChatColor.RESET
				+ "\n\nI xPlayPvP krigar du mot andra spelare. \n\nDu har m�jlighet att anv�nda en rad olika kits, som du l�ser upp genom att l�sa in PlayMynt. \n\nDu tj�nar dessutom PlayMynt genom att besegra motst�ndare.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlaySpel"
				+ ChatColor.RESET
				+ "\n\nI xPlaySpel finns minispel som parkour, mobarena och the dropper. \n\nH�r har du m�jlighet att vila fr�n arbete och tj�na PlayMynt p� lite �ventyrligare s�tt.");
		bookMeta.addPage("\n\n\n\nDe f�ljande fyra servrarna har synkroniserat inventory (du har samma saker p� servrarna).");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayStad"
				+ ChatColor.RESET
				+ "\n\nI stadsv�rlden finns funktioner som brukar finnas i verkliga st�der. \n\nBland annat finns det ett kasino, spelar-aff�rer, en PvP-arena och exklusiva marker.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayResurs"
				+ ChatColor.RESET
				+ "\n\nResursv�rlden �tergenereras varje natt och �r helt enkelt en v�rld d�r spelare kan skaffa resurser. \n\nF�r att g�ra utforskningen roligare anv�nds en skr�ddarsydd v�rldsgenerator.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayTowny"
				+ ChatColor.RESET
				+ "\n\nTownyv�rlden anv�nder ett speciellt system f�r markhantering, d�r anv�ndare kan grunda eller g� med i st�der. \n\nF�r nyb�rjaren rekommenderas ist�llet markv�rlden.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayMark"
				+ ChatColor.RESET
				+ "\n\nMarkv�rlden �r en platt v�rld d�r spelare kan claima omr�den som sina egna. \n\nAlla spelare f�r en gratis mark, d�refter kostar markerna 5000 PlayMynt styck.");
		bookMeta.addPage("\n\n\nDet var faktiskt allt. Hoppas att du nu �r inf�rst�dd i hur servern fungerar. \n\n\nMed v�nliga h�lsningar, Simonsigge");
		
		book.setItemMeta(bookMeta);
	}
}
