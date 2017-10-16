package tsuteto.tofu.wrapper;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;

public enum SideWrapper {
	CLIENT(Side.CLIENT),
	SERVER(Side.SERVER),
	;

	public final Side side;

	private SideWrapper(final Side side) {
		this.side = side;
	}

	public static SideWrapper getSide(final Side side) {
		switch (side) {
			default:
			case CLIENT:
				return SideWrapper.CLIENT;
			case SERVER:
				return SideWrapper.SERVER;
		}
	}

	public static SideWrapper getLauncherSide() {
		return getSide(FMLLaunchHandler.side());
	}
}
