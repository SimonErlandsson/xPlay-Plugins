package me.Simonsigge.xPlayDonator.MySQL;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import me.Simonsigge.xPlayDonator.Main.Main;

import org.bukkit.scheduler.BukkitRunnable;

public class MySQLHandler {

	public Connection connection;
	private String host, database, username, password;
	private int port;

	public MySQLHandler() throws SQLException {
		
		String name = null;
		
		try {
			name = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		if (name.equals("ns3058240"))
			host = "164.132.203.80";
		else
			host = "localhost";
		
		port = 3306;
		database = "xplaydonator";
		username = "root";
		password = "rzeSfKxHEu6La7jY";

		open();

		// PING TO FEED CONNECTION
		(new BukkitRunnable() {
			@Override
			public void run() {
				try {
					if (connection != null && !connection.isClosed()) {
						connection.createStatement().execute("SELECT 1");
					}
				} catch (SQLException e) {
					connection = getNewConnection();
				}
			}
		}).runTaskTimerAsynchronously(Main.getInstance(), 60 * 20, 60 * 20);
	}

	private Connection getNewConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	public boolean checkConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = getNewConnection();

			if (connection == null || connection.isClosed()) {
				return false;
			}
		}
		return true;
	}

	private boolean open() throws SQLException {
		try {
			return checkConnection();
		} catch (SQLException e) {
			return false;
		}
	}

	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
		connection = null;
	}

	public Connection getConnection() {
		return connection;
	}

}
