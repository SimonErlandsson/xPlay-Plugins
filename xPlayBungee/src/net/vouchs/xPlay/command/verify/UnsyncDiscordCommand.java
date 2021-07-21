package net.vouchs.xPlay.command.verify;

import net.dv8tion.jda.core.entities.Guild;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.vouchs.xPlay.Main;

public class UnsyncDiscordCommand extends Command
{

	Main main;
	
	public UnsyncDiscordCommand(Main main)
	{
		super("unsyncDiscord");
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

		if (!main.getConfig().contains("Synced." + player.getUniqueId().toString()))
		{
			main.getMessageUtil().sendMessage(player, "&cDet ser ut som att du inte har ett synkroniserat konto. Använd /syncDiscord för av synkronisera ditt konto.");
			return; 
		}
		
		if (args.length >= 0)
		{
			
			Guild guild = main.getJDA().getGuildById(main.OFFICIAL_DISCORD);
			long id = main.getConfig().getLong("Synced." + player.getUniqueId().toString() + ".ID");
			String roles = main.getConfig().getString("Synced." + player.getUniqueId().toString() + ".Rank");

			guild.getController().removeRolesFromMember(guild.getMemberById(id), guild.getRolesByName(roles, true)).queue();

			main.getConfig().set("Synced." + player.getUniqueId().toString(), null);
			main.saveConfig();
			main.getMessageUtil().sendMessage(player, "&cDu har nu av-synkroniserat discord.");
		}

		return;
	}
}
