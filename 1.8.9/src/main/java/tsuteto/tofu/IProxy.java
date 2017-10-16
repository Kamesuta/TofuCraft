package tsuteto.tofu;

import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

public interface IProxy {

	boolean checkModList(final @Nonnull Map<String, String> versions, final @Nonnull Side side);

	void preInit(@Nonnull FMLPreInitializationEvent event);

	void init(@Nonnull FMLInitializationEvent event);

	void postInit(@Nonnull FMLPostInitializationEvent event);

	void serverStarting(@Nonnull FMLServerStartingEvent event);

}