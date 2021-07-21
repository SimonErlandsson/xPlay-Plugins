package net.vouchs.xPlay.command.enums;

import java.util.Arrays;
import java.util.Optional;

public enum DiscordRanks
{

	VIP("VIP","VIP"),
	ILOVEXP("ILovexP","I<3xP"),
	LEGEND("Legend","Legend");

	private String perm;
	private String name;

	DiscordRanks(String perm, String name)
	{
		this.perm = perm;
		this.name = name;
	}

	public String getRankID()
	{
		return this.name;
	}

	public String getPerm()
	{
		return this.perm;
	}

	public static Optional<DiscordRanks> getByRank(String perm)
	{
		return Arrays.stream(values()).filter(all -> all.perm == perm).findFirst();
	}

}