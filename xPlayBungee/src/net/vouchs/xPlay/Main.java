package net.vouchs.xPlay;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.HashMap;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.vouchs.xPlay.command.NewBuyerCommand;
import net.vouchs.xPlay.command.PingCommand;
import net.vouchs.xPlay.command.ReportCommand;
import net.vouchs.xPlay.command.StaffChatCommand;
import net.vouchs.xPlay.command.verify.SyncDiscordCommand;
import net.vouchs.xPlay.command.verify.UnsyncDiscordCommand;
import net.vouchs.xPlay.command.verify.VerifieraCommand;
import net.vouchs.xPlay.handlers.PlayerHandler;
import net.vouchs.xPlay.listeners.PostLogin;
import net.vouchs.xPlay.listeners.StaffChat;
import net.vouchs.xPlay.listeners.discord.GuildMessageReceived;
import net.vouchs.xPlay.objects.Player;
import net.vouchs.xPlay.utilities.Utilities;
import net.vouchs.xPlay.utilities.strings.MessagesUtil;

public class Main extends Plugin
{
	
	public final String OFFICIAL_DISCORD = "390198427486650380";
	public final String SERVERCHATT_XPLAY = "390215827808583690";
	public final String DONATIONS_XPLAY = "391038763943460865";

	public final String STAFFCHAT_CHANNEL = "391263807672025088";
	public final String GENERAL_LOGS_CHANNEL = "391263807672025088";
	public final String DISCORD_REPORTS_CHANNEL = "391263902085808128";
	public final String DISCORD_BUGG_CHANNEL = "391262911781142529";

	private static Main instance;

	private MessagesUtil messagesUtil;

	private JDA jda;

	private File configFile;
	private Configuration config;

	private PlayerHandler playerHandler;
	private Utilities utilities;

	public HashMap<ProxiedPlayer, Integer> discordKey = new HashMap<>();
	public HashMap<ProxiedPlayer, Long> discordID = new HashMap<>();
	
	@Override
	public void onEnable()
	{
		instance = this;
		initializeDiscord();
		
		messagesUtil = new MessagesUtil();
		utilities = new Utilities(this);
		playerHandler = new PlayerHandler();

		loadConfigurations();

		new GuildMessageReceived(this);
		
		new PostLogin(this);
		new StaffChat(this);
		
		new ReportCommand(this);
		new PingCommand(this);
		new NewBuyerCommand(this);
		new StaffChatCommand(this);
		
		new SyncDiscordCommand(this);
		new UnsyncDiscordCommand(this);
		new VerifieraCommand(this);
		
		for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers())
			new Player(proxiedPlayer.getUniqueId());

		
	}

	public static Main getInstance()
	{
		return instance;
	}

	public JDA getJDA()
	{
		return jda;
	}

	private void initializeDiscord()
	{
		try
		{
			jda = new JDABuilder(AccountType.BOT).setToken("Mzg4MDgyMzc1MDY3Njk3MTY1.DQn2Mw._sBv4iihhyssYHy5xLwsJz53oxY").buildAsync();
		}
		catch (LoginException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (RateLimitedException e)
		{
			e.printStackTrace();
		}
	}
	
	public PlayerHandler getPlayerHandler()
	{
		return playerHandler;
	}

	public Utilities getUtilities()
	{
		return utilities;
	}

	public Configuration getConfig()
	{
		return this.config;
	}
	
	private void loadConfigurations()
	{

		if (!getDataFolder().exists())
		{
			getDataFolder().mkdir();
		}

		try
		{
			configFile = new File(getDataFolder(), "config.yml");
			if (!configFile.exists())
			{
				Files.copy(getResourceAsStream("config.yml"), configFile.toPath(), new CopyOption[0]);
				this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
				saveConfig();
			}
			this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void reloadConfig()
	{
		try
		{
			ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
		}
	}

	public void saveConfig()
	{
		try
		{
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, configFile);
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
		}
	}

	public MessagesUtil getMessageUtil()
	{
		return messagesUtil;
	}

}
