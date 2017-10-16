package tsuteto.tofu.eventhandler;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.CoreEvent;
import tsuteto.tofu.EventResultHandler;
import tsuteto.tofu.EventResultHandler.EventResult;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.block.BlockLeek;
import tsuteto.tofu.block.BlockTcSapling;
import tsuteto.tofu.block.BlockTofuBase;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.wrapper.BlockPosWrapper;
import tsuteto.tofu.wrapper.BlockStateWrapper;
import tsuteto.tofu.wrapper.WorldWrapper;

public class EventBonemeal {
	@CoreEvent
	public void onBonemeal(final EventResultHandler res, final EntityPlayer player, final WorldWrapper world, final BlockStateWrapper block, final BlockPosWrapper pos) {
		final int var11;
		int var12;
		int var13;

		final Random rand = world.rand();

		// Saplings
		if (block.getBlock()==TcBlocks.tcSapling) {
			if (!world.isRemote())
				((BlockTcSapling) TcBlocks.tcSapling).growTree(world.world, pos.x, pos.y, pos.z, rand);
			res.setResult(EventResult.ALLOW);
		}

		// Leek
		if (block.getBlock() instanceof BlockTofuBase) {
			if (!world.isRemote())
				label133:

				for (var12 = 0; var12<32; ++var12) {
					var13 = pos.x;
					int var14 = pos.y+1;
					int var15 = pos.z;

					for (int var16 = 0; var16<var12/16; ++var16) {
						var13 += rand.nextInt(3)-1;
						var14 += (rand.nextInt(3)-1)*rand.nextInt(3)/2;
						var15 += rand.nextInt(3)-1;

						if (!(world.getBlockState(new BlockPosWrapper(var13, var14-1, var15)).getBlock() instanceof BlockTofuBase)||world.getBlockState(new BlockPosWrapper(var13, var14, var15)).getBlock().isNormalCube())
							continue label133;
					}

					if (world.isAirBlock(new BlockPosWrapper(var13, var14, var15)))
						if (rand.nextInt(10)<5) {
							if (TcBlocks.leek.canBlockStay(world, new BlockPosWrapper(var13, var14, var15).getBlockPos())) {
								world.setBlock(var13, var14, var15, TcBlocks.leek, BlockLeek.META_NATURAL, 3);
								TcAchievementMgr.achieve(player, Key.leek);
							}
						} else
							world.getBiomeGenForCoords(var13, var15).plantFlower(world, rand, var13, var14, var15);
				}
			res.setResult(EventResult.ALLOW);
		}

		//event.setCanceled(false);
	}
}
