package me.Simonsigge.xPlayDonator.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Permissions;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Rank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataUtilities {

	public MySQLHandler mySQLHandler;

	public DataUtilities() {
		try {
			mySQLHandler = new MySQLHandler();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
				new Runnable() {

					@Override
					public void run() {
						try {
							if (mySQLHandler.checkConnection()) {
								Statement st = (Statement) mySQLHandler
										.getConnection().createStatement();

								st.executeUpdate("CREATE TABLE IF NOT EXISTS donatortables (id INT UNSIGNED NOT NULL AUTO_INCREMENT, uuid VARCHAR(36) NOT NULL, playername VARCHAR(16) NOT NULL, helmet INT NOT NULL, chest VARCHAR(16) NOT NULL, leggings VARCHAR(16) NOT NULL, boots VARCHAR(16) NOT NULL, arbeteboost INT NOT NULL, pvpboost INT NOT NULL, dropboost INT NOT NULL, lastchatcolor TIMESTAMP NULL DEFAULT NULL, lastchatshout TIMESTAMP NULL DEFAULT NULL, PRIMARY KEY (id))");
								System.out.println("[xPlayDonator] -> MySQL fungerar! :D");
							}
							else
								System.out.println("[xPlayDonator] -> Problem med MySQL... =(");
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				}, 60);

	}

	public void savePlayerData(DonatorPlayer donator) {
		try {
			if (!mySQLHandler.checkConnection()) {
				donator.getPlayer().kickPlayer(
						"Problem med databasen. Kontakta oss genom Discord!");
			}
		} catch (SQLException e) {
			donator.getPlayer().kickPlayer(
					"Problem med databasen. Kontakta oss genom Discord!");
			e.printStackTrace();
		}

		try {

			PreparedStatement preparedStatement = mySQLHandler
					.getConnection()
					.prepareStatement(
							"UPDATE donatortables SET playername = ?, helmet = ?, chest = ?, leggings = ?, boots = ?, arbeteboost = ?, pvpboost = ?, dropboost = ?, lastchatcolor = ?, lastchatshout = ? WHERE uuid = ?");
			preparedStatement.setString(1, donator.getPlayer().getName());
			preparedStatement.setInt(2, donator.getHelmet());
			preparedStatement.setString(3, stringFromCloth(donator.getChest()));
			preparedStatement.setString(4,
					stringFromCloth(donator.getLeggings()));
			preparedStatement.setString(5, stringFromCloth(donator.getBoots()));

			preparedStatement.setInt(6, donator.getArbeteBoost());
			preparedStatement.setInt(7, donator.getPvPBoost());
			preparedStatement.setInt(8, donator.getDropBoost());

			preparedStatement.setTimestamp(9, donator.getLastChatColor());
			preparedStatement.setTimestamp(10, donator.getLastChatShout());

			preparedStatement.setString(11, donator.getPlayer().getUniqueId()
					.toString());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public DonatorPlayer loadPlayerData(Player p) {
		DonatorPlayer donator = new DonatorPlayer();

		try {
			if (!mySQLHandler.checkConnection()) {
				p.kickPlayer("Problem med databasen. Kontakta oss genom Discord!");
			}
		} catch (SQLException e) {
			p.kickPlayer("Problem med databasen. Kontakta oss genom Discord!");
			e.printStackTrace();
		}

		if (playerExists(p)) {
			try {
				PreparedStatement preparedStatement = mySQLHandler
						.getConnection()
						.prepareStatement(
								"SELECT helmet, chest, leggings, boots, arbeteboost, pvpboost, dropboost, lastchatcolor, lastchatshout FROM donatortables WHERE uuid = ?");
				preparedStatement.setString(1, p.getUniqueId().toString());

				ResultSet result = preparedStatement.executeQuery();

				while (result.next()) {

					donator.setHelmet(result.getInt("helmet"));
					donator.setChest(clothFromString(result.getString("chest")));
					donator.setLeggings(clothFromString(result
							.getString("leggings")));
					donator.setBoots(clothFromString(result.getString("boots")));

					donator.setArbeteBoost(result.getInt("arbeteboost"));
					donator.setPvPBoost(result.getInt("pvpboost"));
					donator.setDropBoost(result.getInt("dropboost"));

					donator.setLastChatColor(result
							.getTimestamp("lastchatcolor"));
					donator.setLastChatShout(result
							.getTimestamp("lastchatshout"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			donator = getDefaultDonator();
		}

		donator.setPlayer(p);
		donator.setRank(calculateRank(p));

		return donator;
	}

	private Cloth clothFromString(String string) {
		if (string.toLowerCase().contains("changeing"))
			return Cloth.CHANGEING;

		if (string.toLowerCase().contains("none"))
			return Cloth.NONE;

		if (string.toLowerCase().contains("disco"))
			return Cloth.DISCO;
		return null;
	}

	private String stringFromCloth(Cloth cloth) {
		return cloth.name();
	}

	private DonatorPlayer getDefaultDonator() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		try {
			date = dateFormat.parse("01/01/2018");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long defaultTime = date.getTime();

		DonatorPlayer donator = new DonatorPlayer();
		donator.setHelmet(0);
		donator.setChest(Cloth.NONE);
		donator.setLeggings(Cloth.NONE);
		donator.setBoots(Cloth.NONE);
		donator.setArbeteBoost(0);
		donator.setPvPBoost(0);
		donator.setDropBoost(0);
		donator.setLastChatColor(new Timestamp(defaultTime));
		donator.setLastChatShout(new Timestamp(defaultTime));

		return donator;
	}

	private boolean playerExists(Player p) {

		try {
			PreparedStatement preparedStatement = mySQLHandler.getConnection()
					.prepareStatement(
							"SELECT * FROM donatortables WHERE uuid = ?");
			preparedStatement.setString(1, p.getUniqueId().toString());

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				if (result != null) {
					return true;
				}
			}

			DonatorPlayer donator = getDefaultDonator();

			preparedStatement = mySQLHandler
					.getConnection()
					.prepareStatement(
							"INSERT INTO donatortables (uuid, playername, helmet, chest, leggings, boots, arbeteboost, pvpboost, dropboost, lastchatcolor, lastchatshout) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setString(1, p.getUniqueId().toString());
			preparedStatement.setString(2, p.getName());
			preparedStatement.setInt(3, donator.getHelmet());
			preparedStatement.setString(4, stringFromCloth(donator.getChest()));
			preparedStatement.setString(5,
					stringFromCloth(donator.getLeggings()));
			preparedStatement.setString(6, stringFromCloth(donator.getBoots()));

			preparedStatement.setInt(7, donator.getArbeteBoost());
			preparedStatement.setInt(8, donator.getPvPBoost());
			preparedStatement.setInt(9, donator.getDropBoost());

			preparedStatement.setTimestamp(10, donator.getLastChatColor());
			preparedStatement.setTimestamp(11, donator.getLastChatShout());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private Rank calculateRank(Player p) {
		if (p.hasPermission(Permissions.LEGEND_PERM))
			return Rank.LEGEND;

		if (p.hasPermission(Permissions.I_LOVE_XP_PERM))
			return Rank.ILOVEXP;

		if (p.hasPermission(Permissions.VIP_PERM))
			return Rank.VIP;

		return Rank.NONE;
	}

}
