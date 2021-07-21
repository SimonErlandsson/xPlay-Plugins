package net.vouchs.xPlay.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.objects.Player;

public class StaffChatCommand extends Command
{

	Main main;

	public StaffChatCommand(Main main)
	{
		super("staffChat", null, new String[] { "sc", "xps" });
		this.main = main;
		ProxyServer.getInstance().getPluginManager().registerCommand(main, this);
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (sender instanceof ProxiedPlayer)
		{
			ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

			if (!proxiedPlayer.hasPermission("xPlay.Command.StaffChat"))
			{
				Main.getInstance().getMessageUtil().insufficientRank(proxiedPlayer);
				return;
			}

			if (args.length >= 0)
			{
				Player player = Main.getInstance().getPlayerHandler().getPlayer(proxiedPlayer.getUniqueId());

				if (player.isInStaffChat())
				{
					player.setInStaffChat(false);
					Main.getInstance().getMessageUtil().sendMessage(proxiedPlayer, "Du skriver nu i den globala chatten!");
				}
				else
				{
					player.setInStaffChat(true);
					Main.getInstance().getMessageUtil().sendMessage(proxiedPlayer, "Du skriver nu i staff chatten!");
				}
			}
		}
	}
}
