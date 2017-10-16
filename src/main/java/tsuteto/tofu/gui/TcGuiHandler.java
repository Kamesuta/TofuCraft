package tsuteto.tofu.gui;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import tsuteto.tofu.util.ModLog;

public class TcGuiHandler {
	public static final int GUIID_SALT_FURNACE = 0;
	public static final int GUIID_TF_STORAGE = 1;
	public static final int GUIID_TF_ANTENNA = 2;
	public static final int GUIID_TF_CONDENSER = 3;
	public static final int GUIID_TF_OVEN = 4;
	public static final int GUIID_TF_REFORMER = 5;
	public static final int GUIID_TF_REFORMER2 = 6;
	public static final int GUIID_TF_SATURATOR = 7;

	private HashMap<Integer, GuiEntry> guiRegistry = new HashMap<Integer, GuiEntry>();

	public TcGuiHandler() {
		registerGuiEntry(createEntry(GUIID_SALT_FURNACE).withName("SaltFurnace"));
		registerGuiEntry(createEntry(GUIID_TF_STORAGE).withName("TfStorage"));
		registerGuiEntry(createEntry(GUIID_TF_ANTENNA).withName("TfAntenna"));
		registerGuiEntry(createEntry(GUIID_TF_CONDENSER).withName("TfCondenser"));
		registerGuiEntry(createEntry(GUIID_TF_OVEN).withName("TfOven"));
		registerGuiEntry(createEntry(GUIID_TF_REFORMER).withName("TfReformer"));
		registerGuiEntry(createEntry(GUIID_TF_REFORMER2).withName("TfReformer2", "TfReformer", "TfReformer2"));
		registerGuiEntry(createEntry(GUIID_TF_SATURATOR).withName("TfSaturator"));
	}

	private GuiEntry createEntry(final int id) {
		if (FMLLaunchHandler.side()==Side.CLIENT)
			return new GuiEntryClient(id);
		else
			return new GuiEntryServer(id);
	}

	public void registerGuiEntry(final GuiEntry entry) {
		this.guiRegistry.put(entry.id, entry);
	}

	@Override
	public Object getServerGuiElement(final int guiId, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		final GuiEntry entry = this.guiRegistry.get(guiId);

		if (entry!=null) {
			final TileEntity tile = world.getTileEntity(x, y, z);
			if (entry.getTileEntityClass().isAssignableFrom(tile.getClass()))
				try {
					final Constructor constructor = entry.getContainerClass().getConstructor(InventoryPlayer.class, entry.getTileEntityClass());
					return constructor.newInstance(player.inventory, tile);
				} catch (final Exception e) {
					ModLog.log(Level.WARN, e, "Failed to open a gui screen!");
				}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(final int guiId, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		final GuiEntry entry = this.guiRegistry.get(guiId);

		if (entry!=null) {
			final TileEntity tile = world.getTileEntity(x, y, z);
			if (entry.getTileEntityClass().isAssignableFrom(tile.getClass()))
				try {
					final Constructor constructor = entry.getGuiClass().getConstructor(InventoryPlayer.class, entry.getTileEntityClass());
					return constructor.newInstance(player.inventory, tile);
				} catch (final Exception e) {
					ModLog.log(Level.WARN, e, "Failed to open a gui screen!");
				}
		}

		return null;
	}
}
