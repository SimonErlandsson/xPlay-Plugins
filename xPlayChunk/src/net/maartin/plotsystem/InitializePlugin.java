package net.maartin.plotsystem;

import net.maartin.plotsystem.Commands.AddCommand;
import net.maartin.plotsystem.Commands.ChunkCommand;
import net.maartin.plotsystem.Commands.ClaimCommand;
import net.maartin.plotsystem.Commands.UntrustCommand;
import net.maartin.plotsystem.Commands.UnclaimCommand;
import net.maartin.plotsystem.Events.CallPlayerChangeChunkEvent;
import net.maartin.plotsystem.Listeners.PlayerJoinListener;
import net.maartin.plotsystem.Listeners.PlayerLeaveListener;
import net.maartin.plotsystem.Listeners.Chunk.BlockBreakChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.BlockDispenseChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.StructureGrowChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.BlockPistonExtendChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.BlockPistonRetractChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.BlockPlaceChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.EntityDamageEntityChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.HangingBreakByEntityChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.HangingPlaceChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.InteractAtEntityChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.InteractEntityChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.PlayerBucketEmptyChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.PlayerBucketFillChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.InteractChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.PlayerLeashEntityChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.VehicleDestroyChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.VehicleMoveChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.ChunkChangeListener;
import net.maartin.plotsystem.Listeners.Chunk.EntityChangeBlockChunkListener;
import net.maartin.plotsystem.Listeners.Chunk.WaterAndLavaFlowChunkListener;

public class InitializePlugin {
	
	Main main;
	public InitializePlugin(Main main) {
		this.main = main;
		
		new AddCommand(main);
		new ChunkCommand(main);
		new ClaimCommand(main);
		new UnclaimCommand(main);
		new UntrustCommand(main);
		
		try {
			new PlayerJoinListener(main);
			new PlayerLeaveListener(main);
			
			new CallPlayerChangeChunkEvent(main);
			
			new BlockPlaceChunkListener(main);
			new BlockBreakChunkListener(main);
			new PlayerBucketFillChunkListener(main);
			new PlayerBucketEmptyChunkListener(main);
			new InteractChunkListener(main);
			new WaterAndLavaFlowChunkListener(main);
			new BlockPistonExtendChunkListener(main);
			new BlockPistonRetractChunkListener(main);
			new EntityChangeBlockChunkListener(main);
			new StructureGrowChunkListener(main);
			new BlockDispenseChunkListener(main);
			
			new InteractAtEntityChunkListener(main);
			new InteractEntityChunkListener(main);
			new PlayerLeashEntityChunkListener(main);
			new EntityDamageEntityChunkListener(main);
			new VehicleDestroyChunkListener(main);
			
			new ChunkChangeListener(main);
			new VehicleMoveChunkListener(main);
			
			new HangingBreakByEntityChunkListener(main);
			new HangingPlaceChunkListener(main);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
