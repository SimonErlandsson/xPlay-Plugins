package net.maartin.plotsystem;

public enum Messages {
	
	INVALID_SUBCOMMAND("�cFel vid utf�rning av kommando. G�r /chunk f�r hj�lp"),
	COULD_NOT_FIND_PLAYER("�7Kunde inte hitta spelaren"),
	NO_PERMISSSION("�cDu har inte till�telse att utf�ra detta kommando!"),
	
	ALREADY_CLAIMED("�cDenna chunk har redan en �gare och �r inte till salu"),
	NOT_CLAIMED("�cDenna chunk har ingen �gare"),
	NO_INFORMATION("�7Denna chunk �gs inte av n�gon och har d�rf�r ingen information"),
	CANNOT_AFFORD("�cDu har inte tillr�ckligt med pengar f�r att k�pa denna chunk.\n�7Chunks kostar %price% PlayMynt"),
	REACHED_MAX_CHUNKS("�cDu har n�tt gr�nsen f�r maximalt antal �gda chunks"),
	
	CLAIMED_CHUNK("�7Du k�pte chunken �a%chunkid% �7f�r �a%price% PlayMynt"),
	CLAIMED_FREE_CHUNK("�7Du k�pte chunken �a%chunkid% �7gratis. �7Notera att det �r bara din f�rsta chunk som �r gratis, chunks kostar i vanliga fall �a%price% PlayMynt"),
	CLAIMED_SERVERCHUNK("�7Du tog �ver �5%chunkid% �7till servern"),
	FORCE_CLAIMED_CHUNK("�7Du tog �ver chunken �a%chunkid%"),
	FORCE_CLAIMED_CHUNK_TO_PLAYER("�7Du tog �ver chunken �a%chunkid% �7till �2%target%"),
	FORCE_UNCLAIMED_CHUNK("�7Tog bort all information om chunken �a%chunkid%"),
	UNCLAIMED_CHUNK("�7Du �r nu inte l�ngre �gare �ver denna chunk.\n�aLade till %price% PlayMynt till ditt konto"),
	UNCLAIMED_FREE_CHUNK("�7Du �r nu inte l�ngre �gare �ver denna chunk.\n�aDu kan nu claima en gratischunk igen"),
	
	ALREADY_FOR_SALE("�7Denna chunken �r redan till salu, du kan anv�nda �a/chunk forsale cancel �7f�r att avbryta din s�ljning"),
	PRICE_IS_TOO_HIGH("�7Du kan inte s�tta ett pris som �r h�gre �n �a10 000 000 PlayMynt"),
	PRICE_IS_TOO_LOW("�7Du kan inte s�tta ett pris som �r l�gre �n �a5000 PlayMynt"),
	CANCELED_SELLING("�7Denna chunken �r nu l�ngre inte till salu!"),
	NOT_FOR_SALE("�7Denna chunken �r inte till salu"),
	NOW_SELLING("�7Du har satt din chunk till salu f�r �a%price% PlayMynt"),
	HAS_TO_BE_NUMBER("�7Summan du angav var ogiltlig. F�rs�k igen!"),
	NOT_ENOUGH_CHUNKS("�cDu beh�ver �ga minst 2 chunks f�r att kunna s�lja chunks till andra!"),
	
	CANNOT_AFFORD_SALECHUNK("�cDu har inte tillr�ckligt med pengar f�r att k�pa denna chunk"),
	BOUGHT_CHUNK_FROM("�7Du k�pte chunken �a%chunkid% �7av �a%target% �7f�r �a%price% PlayMynt"),
	PLAYER_BOUGHT_YOUR_CHUNK("�6En av dina chunks blev precis s�ld till �a%target% �6f�r �a%price% PlayMynt"),
	CANNOT_BUY_FROM_YOURSELF("�7Du kan inte k�pa din egna chunk!"),
	SELLER_HAS_NOT_ENOUGH_CHUNKS("�cKunde inte k�pa chunken eftersom s�ljaren inte �ger nog m�nga chunks"),
	
	CONFIRM_PURCHASE("�7�r du s�ker p� att du vill k�pa chunken �a%chunkid% �7f�r �a%price% PlayMynt�7? �6Anv�nd �a/chunk confirm �6f�r att bekr�fta ditt k�p"),
	NOTHING_TO_CONFIRM("�7Du har ingenting att bekr�fta"),
	NOT_FOR_SALE_ANYMORE("�7Chunken du f�rs�kte k�pa �r inte l�ngre till salu!"),
	
	ADDED_TRUSTED("�7Lade till �2%target% �7till medlemslistan"),
	REMOVED_TRUSTED("�7Tog bort �c%target% �7fr�n medlemslistan"),
	
	ENTER_CLAIMED("�2�l%chunkowner%"),
	ENTER_SELLING("�2�l%chunkowner% �7| �6Till salu: �a%price% �6�lPlayMynt"),
	ENTER_WILDERNESS("�7�o�lVildmarken"),
	ENTER_SERVER_CLAIMED("�5�lServer"),
	
	NOT_OWNER("�cDu �r inte �garen �ver denna chunk"),
	CANNOT_REMOVE_ON_OTHERS("�cDu har inte till�telse att ta bort medlemmar fr�n denna chunk"),
	CANNOT_TRUST_ON_OTHERS("�cDu har inte till�telse att l�gga till medlemmar i denna chunk"),
	CANNOT_TRUST_YOURSELF("�7Du kan inte l�gga till dig sj�lv som medlem i chunken"),
	ALREADY_TRUSTED("�7Spelaren �r redan tillagd som medlem i den h�r chunken"),
	IS_NOT_TRUSTED("�7Den spelaren �r inte tillagd i denna chunken"),
	
	CLAIMED_DENY("�7Du har inte till�telse att g�ra det d�r inom den chunken!"),
	WILDERNESS_DENY("�7Du har inte till�telse att g�ra det d�r i vildmarken!");
	
	private String message;
	
	Messages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
