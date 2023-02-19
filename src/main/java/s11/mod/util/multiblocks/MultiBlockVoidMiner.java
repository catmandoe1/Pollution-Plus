package s11.mod.util.multiblocks;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import s11.mod.init.BlockInit;
import s11.mod.util.interfaces.MultiBlock;

public class MultiBlockVoidMiner implements MultiBlock {
	// all code in class was auto-generated with MultiBlockCreater.py

//	private static Block air() {
//		return Blocks.AIR;
//	}
//
//
//
//	private static Block steelFrame() {
//		return BlockInit.STEEL_FRAME;
//	}
//
//
//
//	private static Block steelBlock() {
//		return BlockInit.STEEL_BLOCK;
//	}
//
//
//
//	private static Block energyComp() {
//		return BlockInit.VOID_MINER_ENERGY_COMPONENT;
//	}
//
//
//
//	private static Block heatSink() {
//		return BlockInit.VOID_MINER_HEAT_SINK;
//	}
//
//
//
//	private static Block laserCore() {
//		return BlockInit.VOID_MINER_LASER_CORE;
//	}
//
//
//
//	private static final Block[][][] STRUCTURE = {
//		{
//			{
//				air(),
//				air(),
//				steelBlock(),
//				steelBlock(),
//				steelBlock(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				steelBlock(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				steelBlock()
//			},
//			{
//				steelBlock(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				steelBlock()
//			},
//			{
//				steelBlock(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				steelBlock()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				steelBlock(),
//				steelBlock(),
//				steelBlock(),
//				air(),
//				air()
//			}
//		},
//		{
//			{
//				air(),
//				air(),
//				steelFrame(),
//				energyComp(),
//				steelFrame(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				steelFrame(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				steelFrame()
//			},
//			{
//				energyComp(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				steelFrame(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				steelFrame()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				steelFrame(),
//				energyComp(),
//				steelFrame(),
//				air(),
//				air()
//			}
//		},
//		{
//			{
//				air(),
//				air(),
//				steelFrame(),
//				steelFrame(),
//				steelFrame(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				steelFrame(),
//				steelFrame(),
//				steelFrame(),
//				air(),
//				air()
//			},
//			{
//				steelFrame(),
//				steelFrame(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				steelFrame(),
//				steelFrame()
//			},
//			{
//				steelFrame(),
//				steelFrame(),
//				energyComp(),
//				laserCore(),
//				energyComp(),
//				steelFrame(),
//				steelFrame()
//			},
//			{
//				steelFrame(),
//				steelFrame(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				steelFrame(),
//				steelFrame()
//			},
//			{
//				air(),
//				air(),
//				steelFrame(),
//				steelFrame(),
//				steelFrame(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				steelFrame(),
//				steelFrame(),
//				steelFrame(),
//				air(),
//				air()
//			}
//		},
//		{
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				steelFrame(),
//				energyComp(),
//				laserCore(),
//				energyComp(),
//				steelFrame(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			}
//		},
//		{
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				steelFrame(),
//				energyComp(),
//				laserCore(),
//				energyComp(),
//				steelFrame(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			}
//		},
//		{
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				steelFrame(),
//				energyComp(),
//				laserCore(),
//				energyComp(),
//				steelFrame(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				heatSink(),
//				energyComp(),
//				heatSink(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			}
//		},
//		{
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				steelFrame(),
//				steelFrame(),
//				heatSink(),
//				steelFrame(),
//				steelFrame(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				steelFrame(),
//				air(),
//				air(),
//				air()
//			},
//			{
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air(),
//				air()
//			}
//		}
//	};
//	


	@Override
	public Boolean compareStructure(Block[][][] structureIn) {
		return false;//Arrays.deepEquals(STRUCTURE, structureIn);
	}



	@Override
	public Block[][][] getStructure() {
		return new Block[1][1][1];//STRUCTURE;
	}
}
