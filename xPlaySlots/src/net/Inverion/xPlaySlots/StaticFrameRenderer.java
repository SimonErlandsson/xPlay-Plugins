package net.Inverion.xPlaySlots;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.Inverion.xPlaySlots.Animation.Images;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class StaticFrameRenderer extends MapRenderer {

	public String img;

	@Override
	public void render(MapView arg0, MapCanvas arg1, Player arg2) {
		if (img != null) {
			try {
				arg1.drawImage(
						0,
						0,
						ImageIO.read(new File(Images.getInstance().imgDirectory
								+ img)));
				img = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}