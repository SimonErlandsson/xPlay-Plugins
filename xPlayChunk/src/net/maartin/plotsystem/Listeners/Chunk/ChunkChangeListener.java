package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Events.PlayerChangeChunkEvent;
import net.maartin.plotsystem.Objects.PlayerChunk;
import net.maartin.plotsystem.Objects.PlayerChunk.PlayerChunkType;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class ChunkChangeListener implements Listener {

	Main main;
	public ChunkChangeListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onPlayerChangePlot(PlayerChangeChunkEvent event) {
		Player player = event.getPlayer();

		PlayerChunk enter = event.getEnteredPlayerchunk();
		PlayerChunk exit = event.getExitedPlayerchunk();

		if (enter != exit && enter != null) {
			
			if (exit != null && exit.getOwnerUUID().equalsIgnoreCase(enter.getOwnerUUID()) && !(enter.isForSale() || exit.isForSale())) return;
			
			if (enter.getType() == PlayerChunkType.SERVER) {
				PacketPlayOutChat packet = new PacketPlayOutChat((IChatBaseComponent)ChatSerializer.a("\"" + Messages.ENTER_SERVER_CLAIMED.getMessage() + "\""), ChatMessageType.GAME_INFO);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
				return;
			}
			
			String owner = main.getPlayerConfig().getYaml(enter.getOwnerUUID()).getString("Data.Username");
			
			if (player.getUniqueId().toString().equalsIgnoreCase(enter.getOwnerUUID())) owner = "§a§l" + owner;
			
			int price = 0;
			if (enter.isForSale()) {
				ConfigurationSection section = main.getChunkData().getConfigurationSection("Chunks");
				if (section == null) return;
				price = section.getInt(enter.getID() + ".ForSale");
				
				player.sendMessage(Messages.ENTER_SELLING.getMessage().replace("%chunkowner%", owner).replace("%price%", price + ""));
				PacketPlayOutChat packet = new PacketPlayOutChat((IChatBaseComponent)ChatSerializer.a(
						"\"" + Messages.ENTER_SELLING.getMessage().replace("%chunkowner%", owner).replace("%price%", price + "") + "\""), ChatMessageType.GAME_INFO);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
				return;
			}
			
			PacketPlayOutChat packet = new PacketPlayOutChat((IChatBaseComponent)ChatSerializer.a("\"" + Messages.ENTER_CLAIMED.getMessage().replace("%chunkowner%", owner) + "\""), ChatMessageType.GAME_INFO);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
			
		}else if (exit != null && enter == null) {
			
			PacketPlayOutChat packet = new PacketPlayOutChat((IChatBaseComponent)ChatSerializer.a("\"" + Messages.ENTER_WILDERNESS.getMessage() + "\""), ChatMessageType.GAME_INFO);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
