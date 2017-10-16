package tsuteto.tofu.wrapper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockStateWrapper {
	public final IBlockState state;

	public BlockStateWrapper(final IBlockState state) {
		this.state = state;
	}

	public Block getBlock() {
		return this.state.getBlock();
	}
}
