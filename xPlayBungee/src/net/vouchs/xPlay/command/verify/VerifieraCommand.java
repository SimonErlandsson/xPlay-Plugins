package net.vouchs.xPlay.command.verify;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.command.enums.DiscordRanks;
import net.vouchs.xPlay.utilities.Delays;

public class VerifieraCommand extends Command
{

	Main main;
	
	public VerifieraCommand(Main main)
	{
		super("verifiera");
		this.main = main;
		ProxyServer.getInstance().getPluginManager().registerCommand(main, this);
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (sender instanceof ConsoleCommandSender)
		{
			main.getMessageUtil().executionError(sender);
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) sender;
		String delayKey = player.getName() + ":discordSync";
		
		if (!main.discordKey.containsKey(player))
		{
			main.getMessageUtil().sendMessage(player, "&cDu har inte påbörjat en synkronisering. Påbörja en via /syncDiscord.");
			return;
		}

		if (args.length == 1)
		{
			if (Integer.parseInt(args[0]) == main.discordKey.get(player))
			{
				String toRank = null;
				Guild guild = main.getJDA().getGuildById(main.OFFICIAL_DISCORD);
				Member member = guild.getMemberById(main.discordID.get(player));

				for (DiscordRanks ranks : DiscordRanks.values())
				{

					if (player.hasPermission("xPlayDiscord." + ranks.getPerm()))
					{
						toRank = ranks.getRankID();
					}
				}

				guild.getController().addRolesToMember(member, guild.getRolesByName(toRank, true)).queue();
				
				main.getConfig().set("Synced." + player.getUniqueId().toString() + ".Rank", toRank);
				main.getConfig().set("Synced." + player.getUniqueId().toString() + ".ID", main.discordID.get(player));
				main.saveConfig();
				
				Delays.removeDelay(delayKey);
				main.discordID.remove(player);
				main.discordKey.remove(player);
				
				main.getMessageUtil().sendMessage(player, "&aDu har nu mottagit din Discord rank: §2§o" + toRank + "§r§a!");
			}
		}
		else
		{
			main.getMessageUtil().sendMessage(player, "&cKorrekt anvädning: /verifiera [Discord Key]");
			return;
		}

		return;
	}
}
