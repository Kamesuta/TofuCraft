package tsuteto.tofu.wrapper;

import java.util.Random;

import net.minecraft.world.World;

public class WorldWrapper {
	public final World world;

	public WorldWrapper(final World world) {
		this.world = world;
	}

	public boolean isRemote() {
		return this.world.isRemote;
	}

	public Random rand() {
		return this.world.rand;
	}

	public boolean canMineBlockBody(final PlayerWrapper player, final BlockPosWrapper pos) {
		return this.world.canMineBlockBody(player.player, pos.getBlockPos());
	}

	public boolean setBlockState(final BlockPosWrapper pos, final BlockStateWrapper state) {
		return this.world.setBlockState(pos.getBlockPos(), state.state);
	}

	public BlockStateWrapper getBlockState(final BlockPosWrapper pos) {
		return new BlockStateWrapper(this.world.getBlockState(pos.getBlockPos()));
	}

	public boolean isAirBlock(final BlockPosWrapper pos) {
		return this.world.isAirBlock(pos.getBlockPos());
	}
}
