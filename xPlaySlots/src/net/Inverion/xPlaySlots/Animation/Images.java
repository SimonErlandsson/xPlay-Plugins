package net.Inverion.xPlaySlots.Animation;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images {

	private static Images instance;

	public static Images getInstance() {
		if (instance == null) {
			instance = new Images();
		}

		return instance;
	}

	public HashMap<String, Image> images;
	public String imgDirectory;

	public Images() {
		images = new HashMap<String, Image>();
		imgDirectory = "plugins/xPlaySlots/img/";
		renderAll();
	}

	public Image get(String key) {
		return images.get(key);
	}

	public void renderAll() {
		images.clear();

		try {
			images.put("diamond",
					ImageIO.read(new File(imgDirectory + "diamond.png")));
			images.put("iron",
					ImageIO.read(new File(imgDirectory + "iron.png")));
			images.put("gold",
					ImageIO.read(new File(imgDirectory + "gold.png")));
			images.put("flint",
					ImageIO.read(new File(imgDirectory + "flint.png")));
			images.put("emerald",
					ImageIO.read(new File(imgDirectory + "emerald.png")));
			images.put("redstone",
					ImageIO.read(new File(imgDirectory + "redstone.png")));
			images.put("layout",
					ImageIO.read(new File(imgDirectory + "layout.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
