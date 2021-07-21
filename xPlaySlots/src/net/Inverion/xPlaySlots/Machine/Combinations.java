package net.Inverion.xPlaySlots.Machine;

public enum Combinations {
	DIAMOND3X(Symbol.DIAMOND, 2, true), DIAMOND2X(Symbol.DIAMOND, 12, false), 
	EMERALD3X(Symbol.EMERALD, 3, true), EMERALD2X(Symbol.EMERALD, 18, false), 
	GOLD3X(Symbol.GOLD, 4, true), GOLD2X(Symbol.GOLD, 24, false), 
	IRON3X(Symbol.IRON, 6, true), IRON2X(Symbol.IRON, 36, false), 
	REDSTONE3X(Symbol.REDSTONE, 6, true), REDSTONE2X(Symbol.REDSTONE, 36, false), 
	FLINT3X(Symbol.FLINT, 12, true), FLINT2X(Symbol.FLINT, 72, false),
	NO_PAYOUT(null, 633, false);

	public static int total = 0;
	
	public int chance, from, to;
	public Symbol symbol;
	public boolean is3x;

	private Combinations(Symbol symbol, int chance, boolean is3x) {
		this.symbol = symbol;
		this.chance = chance;
		this.is3x = is3x;
		this.from = 0;
		this.to = 0;
	}
}