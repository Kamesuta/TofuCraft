package tsuteto.tofu;

import java.io.File;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.api.recipe.TfCondenserRecipeRegistry;
import tsuteto.tofu.data.MorijioManager;
import tsuteto.tofu.data.TcSaveHandler;
import tsuteto.tofu.init.TcAchievements;
import tsuteto.tofu.init.TcEntity;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.init.block.TcBlockLoader;
import tsuteto.tofu.init.item.TcItemLoader;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.UpdateNotification;

public class MainProxy {
	public static final BiomeDictionary.Type BIOME_TYPE_TOFU;

	public static UpdateNotification update = null;
	protected Configuration conf;
	protected TcSaveHandler saveHandler = null;
	protected MorijioManager morijioManager = null;

	static {
		ModLog.isDebug = Settings.debug;

		BIOME_TYPE_TOFU = EnumHelper.addEnum(BiomeDictionary.Type.class, "TOFU", new Class[] { BiomeDictionary.Type[].class }, new Object[] { new BiomeDictionary.Type[0] });
	}

	public void preInit(final File cfgFile) {
		this.conf = new Configuration(cfgFile);
		this.conf.load();

		Settings.load(this.conf);

		this.conf.save();

		// Register basic features
		TcBlockLoader.loadAll();
		TcItemLoader.loadAll();
		TcEntity.register(this);

		// Register liquid blocks
		TcFluids.register(event.getSide());

		// Prepare Tofu Force Materials
		TfMaterialRegistry.init();

		TfCondenserRecipeRegistry.init();

		// Add Achievements
		if (Settings.achievement)
			TcAchievements.load();

		// Update check!
		if (Settings.updateCheck) {
			update = new UpdateNotification();
			update.checkUpdate();
		}
	}

	public void init() {
	}

	public void postInit() {
	}

	public void serverStarting() {
	}
}
