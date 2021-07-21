package net.vouchs.arbete.object;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.vouchs.arbete.backend.FileConfig;

public class Account
{
	private UUID uuid;
	private String playerName;
	private FileConfig fileConfig;
	private int pickaxeUpgrades;
	private int swordUpgrades;
	private int axeUpgrades; 
	private int shovelUpgrades;
	
	public Account(Player player)
	{
		this.uuid = player.getUniqueId();
		this.playerName = player.getName();
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public FileConfig getFileConfig()
	{
		return fileConfig;
	}

	public void setFileConfig(FileConfig fileConfig)
	{
		this.fileConfig = fileConfig;
	}

	public int getPickaxeUpgrades()
	{
		return pickaxeUpgrades;
	}

	public void setPickaxeUpgrades(int pickaxeUpgrades)
	{
		this.pickaxeUpgrades = pickaxeUpgrades;
	}

	public int getSwordUpgrades()
	{
		return swordUpgrades;
	}

	public void setSwordUpgrades(int swordUpgrades)
	{
		this.swordUpgrades = swordUpgrades;
	}

	public int getAxeUpgrades()
	{
		return axeUpgrades;
	}

	public void setAxeUpgrades(int axeUpgrades)
	{
		this.axeUpgrades = axeUpgrades;
	}

	public int getShovelUpgrades()
	{
		return shovelUpgrades;
	}

	public void setShovelUpgrades(int shovelUpgrades)
	{
		this.shovelUpgrades = shovelUpgrades;
	}
}
