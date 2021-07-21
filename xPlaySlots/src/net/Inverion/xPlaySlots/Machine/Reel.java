package net.Inverion.xPlaySlots.Machine;

import java.util.ArrayList;

public class Reel {
	public int row;
	public ArrayList<ReelItem> items;

	public Reel(int row, ArrayList<ReelItem> items) {
		this.row = row;
		this.items = items;
	}

	public Reel(int row) {
		this.row = row;
		this.items = new ArrayList<ReelItem>();
	}
}
