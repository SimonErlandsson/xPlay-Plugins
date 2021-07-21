package net.vouchs.xPlay.command.verify;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.command.enums.DiscordRanks;
import net.vouchs.xPlay.utilities.Delays;

public class SyncDiscordCommand extends Command
{

	Main main;

	public SyncDiscordCommand(Main main)
	{
		super("syncDiscord");
		this.main = main;
		ProxyServer.getInstance().getPluginManager().registerCommand(main, this);
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{

		ProxiedPlayer player = (ProxiedPlayer) sender;

		String delayKey = player.getName() + ":discordSync";

		if (!hasPermission(player))
		{
			main.getMessageUtil().insufficientRank(player);
			return;
		}

		if (main.getConfig().contains("Synced." + player.getUniqueId().toString()))
		{
			main.getMessageUtil().sendMessage(player, "&cDet ser ut som att du har ett synkroniserat konto. Anv�nd /unSyncDiscord f�r att av-synkroniserat ditt konto.");
			return; 
		}
		
		if (main.discordID.containsKey(player) || main.discordKey.containsKey(player))
		{
			main.getMessageUtil().sendMessage(player, "&cEn synkronisering �r redan aktiv.");
			return;
		}

		if (Delays.haveToWait(delayKey, 60))
		{
			main.getMessageUtil().sendMessage(player, "&cDu m�ste v�nta " + Delays.getRemainingTime(delayKey, 60) + " innan du kan utf�ra detta kommando igen.");
			return;
		}

		if (args.length == 1)
		{
			if (userID(args[0]) != 0)
			{
				int generatedKey = (int) Math.floor(Math.random() * (999999999 - 1 + 1)) + 1;
				User finalUser = main.getJDA().getUserById(userID(args[0]));

				List<String> message = Arrays.asList("Hej, " + finalUser.getAsMention() + "!", "", "�r det inte du som har p�b�rjat den h�r syncen, ignorera d� det h�r meddelandet.", "", "F�r att fullf�lja din identifiering var v�nligen att ange kommandot nedan p� xPlay Server.", "`/verifiera " + generatedKey + "`");

				main.getJDA().getUserById(userID(args[0])).openPrivateChannel().complete().sendMessage(String.join("\n", message)).queue();
				main.getMessageUtil().sendMessage(player, "&aAktiveringen �r nu p�b�rjad, vi har skickat ett meddelande till dig p� Discord.");
				Delays.registerDelay(delayKey);
				main.discordKey.put(player, generatedKey);
				main.discordID.put(player, userID(args[0]));

				ProxyServer.getInstance().getScheduler().schedule(main, new Runnable()
				{
					@Override
					public void run()
					{
						if (main.discordID.containsKey(player))
						{
							main.getMessageUtil().sendMessage(player, "&cDu har inte f�rdigst�llt synkroniseringen, avbryter.");
							main.discordKey.remove(player);
							main.discordID.remove(player);
							Delays.removeDelay(delayKey);
						}
					}
				}, 5, TimeUnit.MINUTES);
			}
			else
			{
				main.getMessageUtil().sendMessage(player, "&cDen specificerade anv�ndaren �r inte giltig, �r du s�ker p� att du har g�tt med i v�r Discord?");
				return;
			}
		}
		else
		{
			main.getMessageUtil().sendMessage(player, "&cKorrekt anv�dning: /syncDiscord [Discord Namn#0000]");
		}

		return;
	}

	private long userID(String s)
	{
		for (User user : main.getJDA().getUsers())
		{
			String discordName = user.getName() + "#" + user.getDiscriminator();
			if (discordName.equals(s))
				return user.getIdLong();
		}
		return 0;
	}

	private boolean hasPermission(ProxiedPlayer player)
	{
		for (DiscordRanks ranks : DiscordRanks.values())
		{

			if (player.hasPermission("xPlayDiscord." + ranks.getPerm())) { return true; }
		}
		return false;
	}

}