package net.Inverion.xPlaySlots.Machine;

import java.awt.Image;

import net.Inverion.xPlaySlots.Animation.Images;

public enum Symbol {
	DIAMOND(6, "diamond"), EMERALD(4, "emerald"), GOLD(3, "gold"), IRON(2,
			"iron"), REDSTONE(2, "redstone"), FLINT(1, "flint");

	private int payout;
	private String image;

	private Symbol(int payout, String image) {
		this.payout = payout;
		this.image = image;
	}

	public Image getImage() {
		return Images.getInstance().get(image);
	}

	public void setPayout(int payout) {
		this.payout = payout;
	}

	public int getPayout() {
		return this.payout;
	}
}
