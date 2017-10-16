package tsuteto.tofu;

import java.util.Map;

import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy extends MainProxy implements IProxy {
	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		load(event.getModMetadata());
		preInit(event.getSuggestedConfigurationFile());
	}

	@Override
	public void init(final FMLInitializationEvent event) {
		init();
	}

	@Override
	public void postInit(final FMLPostInitializationEvent event) {
		postInit();
	}

	@SuppressWarnings("deprecation")
	public static void load(final ModMetadata meta) {
		meta.modId = Reference.MODID;
		meta.name = Reference.NAME;
		meta.description = Reference.DESCRIPTION;
		meta.version = Reference.VERSION;
		meta.credits = Reference.CREDITS;
		meta.logoFile = Reference.LOGO_FILE;
		meta.url = Reference.URL;
		meta.updateUrl = Reference.UPDATE_URL;
		meta.authorList.addAll(Reference.AUTHORS);
		meta.autogenerated = false;
	}

	@Override
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void serverStarting(final FMLServerStartingEvent event) {
		serverStarting();
	}
}
