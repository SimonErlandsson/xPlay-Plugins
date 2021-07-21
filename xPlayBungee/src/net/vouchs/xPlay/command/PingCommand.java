package net.vouchs.xPlay.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vouchs.xPlay.Main;

public class PingCommand extends Command
{

	Main main;
	
	public PingCommand(Main main)
	{
		super("ping");
		this.main = main;
		ProxyServer.getInstance().getPluginManager().registerCommand(main, this);
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (sender instanceof ProxiedPlayer)
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if (args.length == 0)
			{
				Main.getInstance().getMessageUtil().sendMessage(player, "Ping: §2" + player.getPing() + " ms§a.");
			}
			else
			{
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if (target == null)
				{
					Main.getInstance().getMessageUtil().sendMessage(player, "§cSpelaren du söker är inte online.");
				}
				else
				{
					Main.getInstance().getMessageUtil().sendMessage(player, target.getName() + "'s ping: §2" + target.getPing() + "§ams.");
				}
			}
		}
		else
		{
			Main.getInstance().getMessageUtil().executionError(sender);
		}
	}
}
