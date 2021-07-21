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
			main.getMessageUtil().sendMessage(player, "&cDet ser ut som att du har ett synkroniserat konto. Använd /unSyncDiscord för att av-synkroniserat ditt konto.");
			return; 
		}
		
		if (main.discordID.containsKey(player) || main.discordKey.containsKey(player))
		{
			main.getMessageUtil().sendMessage(player, "&cEn synkronisering är redan aktiv.");
			return;
		}

		if (Delays.haveToWait(delayKey, 60))
		{
			main.getMessageUtil().sendMessage(player, "&cDu måste vänta " + Delays.getRemainingTime(delayKey, 60) + " innan du kan utföra detta kommando igen.");
			return;
		}

		if (args.length == 1)
		{
			if (userID(args[0]) != 0)
			{
				int generatedKey = (int) Math.floor(Math.random() * (999999999 - 1 + 1)) + 1;
				User finalUser = main.getJDA().getUserById(userID(args[0]));

				List<String> message = Arrays.asList("Hej, " + finalUser.getAsMention() + "!", "", "Är det inte du som har påbörjat den här syncen, ignorera då det här meddelandet.", "", "För att fullfölja din identifiering var vänligen att ange kommandot nedan på xPlay Server.", "`/verifiera " + generatedKey + "`");

				main.getJDA().getUserById(userID(args[0])).openPrivateChannel().complete().sendMessage(String.join("\n", message)).queue();
				main.getMessageUtil().sendMessage(player, "&aAktiveringen är nu påbörjad, vi har skickat ett meddelande till dig på Discord.");
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
							main.getMessageUtil().sendMessage(player, "&cDu har inte färdigställt synkroniseringen, avbryter.");
							main.discordKey.remove(player);
							main.discordID.remove(player);
							Delays.removeDelay(delayKey);
						}
					}
				}, 5, TimeUnit.MINUTES);
			}
			else
			{
				main.getMessageUtil().sendMessage(player, "&cDen specificerade användaren är inte giltig, är du säker på att du har gått med i vår Discord?");
				return;
			}
		}
		else
		{
			main.getMessageUtil().sendMessage(player, "&cKorrekt anvädning: /syncDiscord [Discord Namn#0000]");
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