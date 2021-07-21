package me.Simonsigge.xPlayDonator.ExtraFeatures;

import me.Simonsigge.xPlayDonator.Nodes.Enums.Feature;

import org.bukkit.entity.Player;

public class FeatureManager {
	
	public ChatFeatures chatFeatures;
	
	public FeatureManager() {
		chatFeatures = new ChatFeatures();
		
	}
	
	public void activateFeature(Player p, Feature feature) {
		
		switch (feature) {
		case CHATCOLOR:
			chatFeatures.newChatFeature(p, feature);
			break;
		case CHATSHOUT:
			chatFeatures.newChatFeature(p, feature);
			break;
		default:
			break;
		}
	}

}
