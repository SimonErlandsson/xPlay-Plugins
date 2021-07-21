package net.maartin.plotsystem.Listeners.Chunk;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.maartin.plotsystem.Main;
import net.maartin.plotsystem.Messages;
import net.maartin.plotsystem.Objects.PlayerChunk;

public class EntityDamageEntityChunkListener implements Listener {

	Main main;
	public EntityDamageEntityChunkListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {

		Entity entity = event.getEntity();
		PlayerChunk playerchunk = PlayerChunk.getPlayerChunkAt(entity.getLocation());
		Player player;

		if (event.getDamager() instanceof Player) {
			
			player = (Player) event.getDamager();
			
		}else if (event.getDamager() instanceof Projectile) {
			
			Projectile p = (Projectile)event.getDamager();
			
			if (!(p.getShooter() instanceof Player)) return;
			player = (Player)p.getShooter();
			
		}else return;
		

		if (player.hasPermission("chunksystem.bypass.damageentities")) return;

		if (playerchunk != null) {

			if (playerchunk.getOwnerUUID().toString().equals(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains(player.getUniqueId().toString())) return;
			if (playerchunk.getTrustedPlayers().contains("*")) return;

			event.setCancelled(true);
			if (!(entity instanceof Player))
				player.sendMessage(Messages.CLAIMED_DENY.getMessage());
			return;
		}
	}
}
