package me.Simonsigge.xPlayDonator.MySQL;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import me.Simonsigge.xPlayDonator.Main.Main;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Armour;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Booster;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Cloth;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Feature;
import me.Simonsigge.xPlayDonator.Nodes.Enums.Rank;
import me.Simonsigge.xPlayDonator.Nodes.Misc;
import me.Simonsigge.xPlayDonator.Nodes.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataHandler {

	public DataUtilities dataUtilities;
	private HashMap<String, DonatorPlayer> donPlayers;

	public DataHandler() {
		dataUtilities = new DataUtilities();
		donPlayers = new HashMap<String, DonatorPlayer>();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(),
				new Runnable() {

					@Override
					public void run() {
						if (donPlayers.size() > 0) {
							System.out
									.println("[xPlayDonator] -> Sparade all data");
							saveAllPlayerData();
						}
					}

				}, Misc.AUTO_BACKUP_DATA, Misc.AUTO_BACKUP_DATA);
	}

	public void tryLoadPlayerData(Player p) {
		if (p.hasPermission(Permissions.DONATOR_PERM)) {
			DonatorPlayer donator = dataUtilities.loadPlayerData(p);

			donPlayers.put(donator.getPlayer().getName(), donator);
		}
	}

	public void trySavePlayerData(Player p) {
		if (donPlayers.containsKey(p.getName())) {
			dataUtilities.savePlayerData(donPlayers.get(p.getName()));
		}
	}

	public void saveAllPlayerData() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(),
				new Runnable() {
					public void run() {
						for (DonatorPlayer donator : donPlayers.values()) {
							trySavePlayerData(donator.getPlayer());
						}
					}
				});
	}

	public void removePlayerFromCache(Player p) {
		if (donPlayers.containsKey(p.getName()))
			donPlayers.remove(p.getName());
	}

	public boolean isDonor(Player p) {
		return donPlayers.containsKey(p.getName());
	}

	public boolean hasRank(Player p, Rank rank) {
		if (!donPlayers.containsKey(p.getName()))
			return false;

		Rank playerRank = donPlayers.get(p.getName()).getRank();

		switch (playerRank) {
		case LEGEND:
			return true;
		case ILOVEXP:
			if (rank != Rank.LEGEND)
				return true;
		case VIP:
			if (rank != Rank.LEGEND && rank != Rank.ILOVEXP)
				return true;
		case NONE:
			return false;
		default:
			break;
		}
		return false;
	}

	public void editPlayerCloth(Player p, Cloth cloth, Armour armour) {
		DonatorPlayer donator = donPlayers.get(p.getName());
		switch (armour) {
		case BOOTS:
			donator.setBoots(cloth);
			break;
		case CHESTPLATE:
			donator.setChest(cloth);
			break;
		case LEGGINGS:
			donator.setLeggings(cloth);
			break;
		case HELMET:
			break;
		default:
			break;
		}
	}

	public void editPlayerCloth(Player p, int helmetIndex) {
		DonatorPlayer donator = donPlayers.get(p.getName());
		donator.setHelmet(helmetIndex);
	}

	public Cloth getPlayerCloth(Player p, Armour armour) {
		switch (armour) {
		case BOOTS:
			return donPlayers.get(p.getName()).getBoots();
		case CHESTPLATE:
			return donPlayers.get(p.getName()).getChest();
		case LEGGINGS:
			return donPlayers.get(p.getName()).getLeggings();
		default:
			break;
		}
		return null;
	}

	public int getPlayerHelmet(Player p) {
		return donPlayers.get(p.getName()).getHelmet();
	}

	public int getBooster(Player p, Booster booster) {
		switch (booster) {
		case ARBETE:
			return donPlayers.get(p.getName()).getArbeteBoost();
		case DROP:
			return donPlayers.get(p.getName()).getDropBoost();
		case PVP:
			return donPlayers.get(p.getName()).getPvPBoost();
		}

		return 0;
	}

	public void setBooster(Player p, Booster booster, int number) {
		DonatorPlayer donator = donPlayers.get(p.getName());
		switch (booster) {
		case ARBETE:
			donator.setArbeteBoost(number);
			break;
		case DROP:
			donator.setDropBoost(number);
			break;
		case PVP:
			donator.setPvPBoost(number);
			break;
		}

		donPlayers.put(p.getName(), donator);
	}

	public long getTimeSinceFeature(Player p, Feature feature) {
		switch (feature) {
		case CHATCOLOR:
			return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()
					- donPlayers.get(p.getName()).getLastChatColor().getTime());
		case CHATSHOUT:
			return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()
					- donPlayers.get(p.getName()).getLastChatShout().getTime());
		default:
			return 0;
		}
	}

	public void setTimeSinceFeature(Player p, Feature feature) {
		DonatorPlayer donator = donPlayers.get(p.getName());
		switch (feature) {
		case CHATCOLOR:
			donator.setLastChatColor(new Timestamp(System.currentTimeMillis()));
			break;
		case CHATSHOUT:
			donator.setLastChatShout(new Timestamp(System.currentTimeMillis()));
			break;
		default:
			return;
		}

		donPlayers.put(p.getName(), donator);
	}

}
