package tsuteto.tofu.wrapper;

import net.minecraft.util.BlockPos;

public class BlockPosWrapper {
	public final int x;
	public final int y;
	public final int z;

	public BlockPosWrapper(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPosWrapper(final BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}

	public BlockPos getBlockPos() {
		return new BlockPos(this.x, this.y, this.z);
	}
}
