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
		bookMeta.setTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Välkommen till xPlay!");

		bookMeta.addPage(ChatColor.BOLD + "xPlay Server" + ChatColor.RESET + "\nRikedomar föds med gemenskap" + ChatColor.BOLD + "\n\n\nHemsida:"
				+ ChatColor.RESET + "\nxPlayServer.net" + ChatColor.BOLD
				+ "\n\nDonera:" + ChatColor.RESET + "\nxPlayServer.net/donera"
				+ ChatColor.BOLD + "\n\nDiscord:" + ChatColor.RESET
				+ "\ndiscord.gg/zzzsSYc");

		bookMeta.addPage(ChatColor.BOLD
				+ "Välkommen till xPlay!"
				+ ChatColor.RESET
				+ "\n\n\nFör din egen, och andras, trivsel är det viktigt att du läser igenom denna bok. \n\nBoken innehåller regler och information om xPlays olika delservrar.");
		bookMeta.addPage(ChatColor.BOLD
				+ "Regler"
				+ ChatColor.RESET
				+ "\n1. Fuskklienter är helt förbjudna (fly, xray, kill aura, auto-fish) \n\n2. Håll en trevlig ton i chatten och gör inte reklam för streams/videor eller andra servrar \n\n3. Anmäl buggar med /report BUGG <info>");
		bookMeta.addPage("4. Det är HELT förbjudet att sälja PlayMynt för riktiga pengar och tjänster utanför xPlay \n\n5. Bedrägeri är inte okej, var ärlig med affärer; skapa inte fällor \n\n6. Anmäl spelare med /report <spelare> <anledning>");
		bookMeta.addPage("7. AFK-maskiner är förbjudna \n\n8. Att boosta kills i PvP är förbjudet \n\n9. Extremt stora farmer och annat som kan bidra till ökat lagg på servern är förbjudet (max en farm-chunk per person)");
		bookMeta.addPage("10. Macros och andra scripts är förbjudna");
		bookMeta.addPage(ChatColor.BOLD
				+ "Information"
				+ ChatColor.RESET
				+ "\n\nGemensamt för alla xPlays delservrar är dina PlayMynt (/money). \n\nPlayMynt kan bland annat användas till att köpa marker, affärer, pvpkits och grunda städer.");
		bookMeta.addPage("Din klass på servern (som syns i chatten när du skriver) grundar sig även på ditt innehav av PlayMynt. \n\nDu kan tjäna PlayMynt genom att bland annat pvpa, spela minispel, äga städer, äga affärer, spela i kasino och arbeta i xPlayArbete.");
		bookMeta.addPage(ChatColor.BOLD
				+ "Beskrivning av delservrar"
				+ ChatColor.RESET
				+ "\n\nResten av denna bok består av beskrivningar av alla serverns delservrar. \n\nOrkar du är det en klar fördel att läsa igenom all information då du slipper att leta reda på informationen senare.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayArbete"
				+ ChatColor.RESET
				+ "\n\nArbete har som syfte att vara en arbetsplats för serverns spelare. \n\nHär krävs ingen tidigare erfarenhet, utan det är bara att logga in och börja tjäna PlayMynt.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayPvP"
				+ ChatColor.RESET
				+ "\n\nI xPlayPvP krigar du mot andra spelare. \n\nDu har möjlighet att använda en rad olika kits, som du låser upp genom att lösa in PlayMynt. \n\nDu tjänar dessutom PlayMynt genom att besegra motståndare.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlaySpel"
				+ ChatColor.RESET
				+ "\n\nI xPlaySpel finns minispel som parkour, mobarena och the dropper. \n\nHär har du möjlighet att vila från arbete och tjäna PlayMynt på lite äventyrligare sätt.");
		bookMeta.addPage("\n\n\n\nDe följande fyra servrarna har synkroniserat inventory (du har samma saker på servrarna).");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayStad"
				+ ChatColor.RESET
				+ "\n\nI stadsvärlden finns funktioner som brukar finnas i verkliga städer. \n\nBland annat finns det ett kasino, spelar-affärer, en PvP-arena och exklusiva marker.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayResurs"
				+ ChatColor.RESET
				+ "\n\nResursvärlden återgenereras varje natt och är helt enkelt en värld där spelare kan skaffa resurser. \n\nFör att göra utforskningen roligare används en skräddarsydd världsgenerator.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayTowny"
				+ ChatColor.RESET
				+ "\n\nTownyvärlden använder ett speciellt system för markhantering, där användare kan grunda eller gå med i städer. \n\nFör nybörjaren rekommenderas istället markvärlden.");
		bookMeta.addPage(ChatColor.BOLD
				+ "xPlayMark"
				+ ChatColor.RESET
				+ "\n\nMarkvärlden är en platt värld där spelare kan claima områden som sina egna. \n\nAlla spelare får en gratis mark, därefter kostar markerna 5000 PlayMynt styck.");
		bookMeta.addPage("\n\n\nDet var faktiskt allt. Hoppas att du nu är införstådd i hur servern fungerar. \n\n\nMed vänliga hälsningar, Simonsigge");
		
		book.setItemMeta(bookMeta);
	}
}
