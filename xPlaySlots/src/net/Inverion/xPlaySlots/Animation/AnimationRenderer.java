package net.Inverion.xPlaySlots.Animation;

import net.Inverion.xPlaySlots.Machine.SlotMachine;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class AnimationRenderer extends MapRenderer {

	public SlotMachine slotMachine;
	public boolean set;

	@Override
	public void render(MapView arg0, MapCanvas arg1, Player arg2) {
		if (set == false) {
			slotMachine.mapCanvas = arg1;
			slotMachine.draw();
			slotMachine = null;
			set = true;
		}
	}
}
