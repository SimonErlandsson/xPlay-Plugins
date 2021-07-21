package net.vouchs.xPlay.command;

import java.util.Random;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vouchs.xPlay.Main;
import net.vouchs.xPlay.utilities.packets.ActionBar;
import net.vouchs.xPlay.utilities.packets.Titles;
import net.vouchs.xPlay.utilities.strings.ConvertString;

public class NewBuyerCommand extends Command
{

	Main main;

	public NewBuyerCommand(Main main)
	{
		super("newBuyer");
		this.main = main;
		ProxyServer.getInstance().getPluginManager().registerCommand(main, this);
	}

	final String[] messages = { 
			"xPlay -> Alla tacka {player}, han donerade till {rank} för att hålla uppe servern!",
			"xPlay -> {player} donerade precis till {rank}. Tack!",
			"xPlay -> Tack till {player} som donerade till {rank}. We <3 U",
			"xPlay -> Buga och bocka, {player} är nu {rank}. <3",
			"xPlay -> {player} hjälpte oss alla genom att donera till {rank}. Tack!"};
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{

		if (sender instanceof ProxiedPlayer)
		{
			Main.getInstance().getMessageUtil().executionError(sender);
			return;
		}

		if (args.length < 2)
		{
			Main.getInstance().getMessageUtil().sendMessage(sender, "Korrekt användning: /newBuyer (spelare) (package)");
			return;
		}

		ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
		if (target != null)
		{
			BaseComponent[] title = ConvertString.getComponent("§9§l" + target.getName());
			BaseComponent[] subTitle = ConvertString.getComponent("§a§lDONERADE!");

			Titles.sendAllFullTitle(10, 45, 10, title, subTitle);

			for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers())
				ActionBar.sendActionBar(players, "§9§l" + target.getName() + " §a§ldonerade!");
			
			String whichPackage = args[1];
			if (whichPackage.contains("_"))
				whichPackage = whichPackage.replace("_", " ");
			
			Random random = new Random();
			int nextMessage = random.nextInt(messages.length);
			
			String finalMessage = messages[nextMessage].replace("{player}", target.getName()).replace("{rank}", whichPackage).replace("<3", ":heart:");
			
			main.getJDA().getTextChannelById(main.DONATIONS_XPLAY).sendMessage(finalMessage).queue();
			//main.getJDA().getTextChannelById(main.DONATIONS_XPLAY).sendMessage("@everyone \n" + finalMessage).queue();
			
		}
	}
}
