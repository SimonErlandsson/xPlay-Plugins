package net.vouchs.xPlay.command;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.core.EmbedBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.utilities.Delays;

public class ReportCommand extends Command
{

	Main main;

	public ReportCommand(Main main)
	{
		super("report");
		this.main = main;
		ProxyServer.getInstance().getPluginManager().registerCommand(main, this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (sender instanceof ProxiedPlayer)
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if (args.length > 1)
			{
				
				if (args[0].equalsIgnoreCase("bugg") || args[0].equalsIgnoreCase("bug")) 
				{
					
					String reason = "";
					StringBuilder sb = new StringBuilder();
					for (int x = 1; x < args.length; ++x)
					{
						if (sb.length() != 0)
							sb.append(" ");

						sb.append(args[x]);
					}
					reason = sb.toString();
					
					String date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
					EmbedBuilder em = new EmbedBuilder();
					em.addField("Buggrapport - " + player.getServer().getInfo().getName(), date, false);
					em.addField("Reporter:", player.getName(), false);
					em.addField("Bugg:", reason, false);
					Main.getInstance().getJDA().getTextChannelById(Main.getInstance().DISCORD_BUGG_CHANNEL).sendMessage(em.setColor(Color.RED).build()).queue();
					Main.getInstance().getMessageUtil().sendMessage(player, "Du har skickat in din buggrapport, tack s� mycket!");
					return;
				}
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if (target == null)
				{
					Main.getInstance().getMessageUtil().sendMessage(player, "�cSpelaren du f�rs�ker att rapportera �r inte online just nu.");
					return;
				}

				if (target.getName().equals(player.getName()))
				{
					Main.getInstance().getMessageUtil().sendMessage(player, "�c�r du s�ker p� att du vill rapportera dig sj�lv?");
					return;
				}

				String key = player.getName() + ":Report";
				int seconds = 30;
				if (Delays.haveToWait(key, seconds))
				{
					Main.getInstance().getMessageUtil().sendMessage(player, "�cDu m�ste v�nta ytterligare " + Delays.getRemainingTime(key, seconds) + " sekunder innan du kan rapportera n�gon spelare igen.");
					return;
				}
				Delays.registerDelay(key);
				String reason = "";
				StringBuilder sb = new StringBuilder();
				for (int x = 1; x < args.length; ++x)
				{
					if (sb.length() != 0)
						sb.append(" ");

					sb.append(args[x]);
				}
				reason = sb.toString();
				Main.getInstance().getMessageUtil().sendMessage(player, "�aBra jobbat! Du har nu rapporterat: �7" + target.getName() + " �af�r: �8" + reason + "�a.");

				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
				EmbedBuilder em = new EmbedBuilder();
				em.addField("Ny rapport - " + target.getServer().getInfo().getName(), date, false);
				em.addField("Reporter:", player.getName() + " (" + player.getServer().getInfo().getName() + ")", false);
				em.addField("Rapporterad:", target.getName(), false);
				em.addField("Anledning:", reason, false);
				Main.getInstance().getJDA().getTextChannelById(Main.getInstance().DISCORD_REPORTS_CHANNEL).sendMessage(em.setColor(Color.RED).build()).queue();

				for (ProxiedPlayer team : ProxyServer.getInstance().getPlayers())
				{
					if (team.hasPermission("xPlay.Staff.Notify"))
					{
						team.sendMessage("");
						team.sendMessage("�cNy Rapport! �7(�c�o" + new SimpleDateFormat("dd-MM").format(new Date()) + "�r�7)");
						team.sendMessage("");
						team.sendMessage("�3Reporter �7> �a" + player.getName() + " �7(" + player.getServer().getInfo().getName() + ")�3.");
						team.sendMessage("�3Rapporterad �7> �c�o" + target.getName() + " �7(" + target.getServer().getInfo().getName() + ")�3.");
						team.sendMessage("");
						team.sendMessage("�3Anledning �7> �7" + String.valueOf(sb.toString()).toString() + "�3.");
						team.sendMessage("");
					}
				}
			}
			else
			{
				Main.getInstance().getMessageUtil().sendMessage(player, "Korrekt anv�ndning: /report (spelare/bugg) (anledning/bugg)");
			}
		}
		return;
	}
}
