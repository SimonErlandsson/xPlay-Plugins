package net.vouchs.arbete.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.vouchs.arbete.Core;
import net.vouchs.arbete.enums.BlockValue;

public class BlockBreak implements Listener
{

	@EventHandler
	public void onEvent(BlockBreakEvent event)
	{
		if (event.isCancelled())
			return;

		Block block = event.getBlock();

		ApplicableRegionSet wgRegSet = WGBukkit.getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation());

		for (ProtectedRegion region : wgRegSet)
		{
			if (region.getId().equalsIgnoreCase("lumberjack") && (block.getType().equals(Material.LOG) || block.getType().equals(Material.LOG_2) || block.getType().equals(Material.LEAVES)))
			{
				payAndBreak(event);
				return;
			}

			if (region.getId().equalsIgnoreCase("digger") && (block.getType().equals(Material.DIRT) || block.getType().equals(Material.GRAVEL) || block.getType().equals(Material.SAND)))
			{
				payAndBreak(event);
				return;
			}

			if (region.getId().equalsIgnoreCase("farm") && (block.getType().equals(Material.PUMPKIN) || block.getType().equals(Material.MELON_BLOCK)))
			{
				payAndBreak(event);
				return;
			}

			if (region.getId().equalsIgnoreCase("mine") && (block.getType().equals(Material.STONE) || block.getType().equals(Material.COBBLESTONE) || block.getType().equals(Material.COAL_ORE) || block.getType().equals(Material.IRON_ORE) || block.getType().equals(Material.GOLD_ORE) || block.getType().equals(Material.DIAMOND_ORE) || block.getType().equals(Material.LAPIS_ORE) || block.getType().equals(Material.EMERALD_ORE)))
			{
				payAndBreak(event);
				return;
			}
		}
		
		if (!event.getPlayer().hasPermission("xPlayArbete.Admin"))
			event.setCancelled(true);
	}

	private void payAndBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		event.setExpToDrop(0);

		if (isValidBlock(block))
		{
			double coefficient = 0;

			if (getJob(block).equals("miner"))
				coefficient = 0.2240761169;

			if (getJob(block).equals("miner"))
				coefficient = 0.2240761169;

			if (getJob(block).equals("lumberjack"))
				coefficient = 0.6134390336;

			if (getJob(block).equals("farmer"))
				coefficient = 0.1124567474;

			if (getJob(block).equals("digger"))
				coefficient = 1.128518322;

			Core.getInstance().addMoney(event.getPlayer(), coefficient * getAmount(block)); // blockMap.get(block.getType().name()));
		}
		block.setType(Material.AIR);
	}

	private boolean isValidBlock(Block block)
	{
		for (BlockValue value : BlockValue.values())
		{
			if (value.getMaterial() == block.getType())
				return true;
		}
		return false;
	}

	private int getAmount(Block block)
	{
		for (BlockValue value : BlockValue.values())
		{
			if (value.getMaterial() == block.getType()) { return value.getAmount(); }
		}
		return 0;
	}

	private String getJob(Block block)
	{
		for (BlockValue value : BlockValue.values())
		{
			if (value.getMaterial() == block.getType())
				return value.getJob();
		}
		return "null";
	}

}
