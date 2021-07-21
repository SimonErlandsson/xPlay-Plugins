package me.Simonsigge.xPlayJump;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main
  extends JavaPlugin
  implements Listener
{
  public final Logger logger = Logger.getLogger("Minecraft");
  public static Main plugin;
  ArrayList<String> nofalldamage = new ArrayList();
  ArrayList<String> nofalldamagewait = new ArrayList();
  
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    PluginDescriptionFile pdfFile = getDescription();
    getConfig().options().copyDefaults(true);
    saveConfig();
    this.logger.info("###############################");
    this.logger.info("##  " + pdfFile.getName() + " | " + 
      pdfFile.getAuthors());
    this.logger.info("##  " + pdfFile.getName() + " has been enabled.");
    this.logger.info("##  Version " + pdfFile.getVersion());
    this.logger.info("###############################");
  }
  
  public void onDisable()
  {
    PluginDescriptionFile pdfFile = getDescription();
    this.logger.info("###############################");
    this.logger.info("##  " + pdfFile.getName() + " | " + 
      pdfFile.getAuthors());
    this.logger.info("##  " + pdfFile.getName() + " has been disabled.");
    this.logger.info("##  Version " + pdfFile.getVersion());
    this.logger.info("###############################");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    PluginDescriptionFile pdfFile = getDescription();
    if (commandLabel.equalsIgnoreCase("jump"))
    {
      sender.sendMessage(ChatColor.GOLD + ""  + ChatColor.BOLD + 
        "Jump - SoSDylan " + ChatColor.YELLOW + "Version: " + 
        pdfFile.getVersion());
      sender.sendMessage(ChatColor.YELLOW + 
        "Come to our server: Coming Soon");
    }
    return false;
  }
  
  @EventHandler
  public void onPlayerPlaceBlock(BlockPlaceEvent event)
  {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if ((block.getType() == Material.SPONGE) && 
      (!player.hasPermission("jump.make")))
    {
      block.breakNaturally();
      player.sendMessage(ChatColor.RED + 
        "You can't create a jumping platform!");
    }
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event)
  {
    if ((event.getEntity() instanceof Player))
    {
      Player player = (Player)event.getEntity();
      if ((this.nofalldamage.contains(player.getName())) && 
        (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)))
      {
        event.setCancelled(true);
        this.nofalldamage.remove(player.getName());
      }
    }
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
    Player player = event.getPlayer();
    Location standBlock = player.getWorld()
      .getBlockAt(player.getLocation().add(0.0D, -0.01D, 0.0D))
      .getLocation();
    if (standBlock.getBlock().getTypeId() == getConfig().getInt("block.launch"))
    {
      int xblock = 0;
      double xvel = 0.0D;
      int yblock = -1;
      double yvel = 0.0D;
      int zblock = 0;
      double zvel = 0.0D;
      while (standBlock.getBlock().getLocation()
        .add(xblock - 1, -1.0D, 0.0D).getBlock().getTypeId() == getConfig().getInt("block.trigger"))
      {
        xblock--;
        xvel += 1.0D;
      }
      while (standBlock.getBlock().getLocation().add(0.0D, yblock, 0.0D)
        .getBlock().getTypeId() == getConfig().getInt("block.trigger"))
      {
        yblock--;
        yvel += 0.7D;
      }
      while (standBlock.getBlock().getLocation()
        .add(0.0D, -1.0D, zblock - 1).getBlock().getTypeId() == getConfig().getInt("block.trigger"))
      {
        zblock--;
        zvel += 1.0D;
      }
      xblock = 0;
      zblock = 0;
      while (standBlock.getBlock().getLocation()
        .add(xblock + 1, -1.0D, 0.0D).getBlock().getTypeId() == getConfig().getInt("block.trigger"))
      {
        xblock++;
        xvel -= 1.0D;
      }
      while (standBlock.getBlock().getLocation()
        .add(0.0D, -1.0D, zblock + 1).getBlock().getTypeId() == getConfig().getInt("block.trigger"))
      {
        zblock++;
        zvel -= 1.0D;
      }
      if ((player.hasPermission("jump.launch")) && (
        (xvel != 0.0D) || (yvel != 0.0D) || (zvel != 0.0D)))
      {
        player.setVelocity(new Vector(xvel, yvel, zvel));
        if (getConfig().getBoolean("launch.sound")) {
          player.playSound(player.getLocation(), 
            Sound.ENTITY_WITHER_SHOOT, 1.0F, -5.0F);
        }
        if (getConfig().getBoolean("launch.nofalldamage")) {
          if ((standBlock.getBlock().getType().equals(Material.SPONGE)) && 
            (!this.nofalldamage.contains(player.getName()))) {
            this.nofalldamage.add(player.getName());
          }
        }
      }
    }
  }
}
