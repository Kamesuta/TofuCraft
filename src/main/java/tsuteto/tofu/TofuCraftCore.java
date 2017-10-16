package tsuteto.tofu;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;
import tsuteto.tofu.command.CommandTofuCreeperSpawn;
import tsuteto.tofu.command.CommandTofuSlimeCheck;
import tsuteto.tofu.data.MorijioManager;
import tsuteto.tofu.data.TcSaveHandler;
import tsuteto.tofu.enchantment.TcEnchantment;
import tsuteto.tofu.entity.TofuCreeperSeed;
import tsuteto.tofu.eventhandler.EventBonemeal;
import tsuteto.tofu.eventhandler.EventEntityLiving;
import tsuteto.tofu.eventhandler.EventFillBucket;
import tsuteto.tofu.eventhandler.EventItemPickup;
import tsuteto.tofu.eventhandler.EventPlayer;
import tsuteto.tofu.eventhandler.EventPlayerInteract;
import tsuteto.tofu.eventhandler.EventWorld;
import tsuteto.tofu.eventhandler.GameScreenHandler;
import tsuteto.tofu.eventhandler.TcCraftingHandler;
import tsuteto.tofu.fishing.TofuFishing;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcEntity;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.init.TcVillages;
import tsuteto.tofu.init.block.LoaderDecorationBlock;
import tsuteto.tofu.network.PacketManager;
import tsuteto.tofu.potion.TcPotion;
import tsuteto.tofu.recipe.Recipes;
import tsuteto.tofu.recipe.craftguide.CraftGuideLoader;
import tsuteto.tofu.texture.TcTextures;
import tsuteto.tofu.tileentity.TileEntityMorijio;
import tsuteto.tofu.world.TcChunkProviderEvent;
import tsuteto.tofu.world.WorldProviderTofu;
import tsuteto.tofu.world.biome.TcBiomes;

public class TofuCraftCore {
	@SidedProxy(clientSide = "tsuteto.tofu.TofuCraftCore$ClientProxy", serverSide = "tsuteto.tofu.TofuCraftCore$ServerProxy")
	public static ISidedProxy sidedProxy;

	@Mod.EventHandler
	public void preload(final FMLPreInitializationEvent event) {
	}

	@Mod.EventHandler
	public void load(final FMLInitializationEvent event) {
		// Register usage of the bone meal
		MinecraftForge.EVENT_BUS.register(new EventBonemeal());

		// Register usage of the empty bucket
		MinecraftForge.EVENT_BUS.register(new EventFillBucket());

		// Register event on player interact
		MinecraftForge.EVENT_BUS.register(new EventPlayerInteract());

		// Register event on EntityLiving
		MinecraftForge.EVENT_BUS.register(new EventEntityLiving());

		// Register event on tofu fishing
		MinecraftForge.EVENT_BUS.register(new TofuFishing());

		// Register world event handler
		MinecraftForge.EVENT_BUS.register(new EventWorld());

		// Register event on player
		FMLCommonHandler.instance().bus().register(new EventPlayer());

		// Register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new TcGuiHandler());

		// Register crafting handler
		FMLCommonHandler.instance().bus().register(new TcCraftingHandler());

		// Register pick-up Handler
		FMLCommonHandler.instance().bus().register(new EventItemPickup());

		{
			final TcChunkProviderEvent eventhandler = new TcChunkProviderEvent();

			// Nether populating
			MinecraftForge.EVENT_BUS.register(eventhandler);

			// Ore generation
			MinecraftForge.ORE_GEN_BUS.register(eventhandler);
		}

		// Register biomes
		TcBiomes.register(this.conf);

		// Register the Tofu World
		DimensionManager.registerProviderType(Settings.tofuDimNo, WorldProviderTofu.class, false);
		DimensionManager.registerDimension(Settings.tofuDimNo, Settings.tofuDimNo);

		// Village and Villager Related
		TcVillages.register();

		// Register Packets
		PacketManager.init(modid);

		// Add chest loot
		registerChestLoot(new ItemStack(TcItems.defattingPotion), 1, 1, 8);
		registerChestLoot(new ItemStack(TcBlocks.tcSapling, 1, 0), 1, 4, 8);
		registerChestLoot(new ItemStack(TcItems.doubanjiang), 1, 1, 4);

		// Register recipes
		Recipes.unifyOreDicItems();
		Recipes.register();
		Recipes.registerExternalModRecipes();

		// CraftGuide
		if (Loader.isModLoaded("craftguide"))
			CraftGuideLoader.load();

		// Register sided components
		sidedProxy.registerComponents();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		TcEntity.addSpawns();

		// Register potion effects
		TcPotion.register(this.conf);
		// Register enchantments
		TcEnchantment.register(this.conf);

		this.conf.save();
	}

	@Mod.EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {
		// Register commands
		event.registerServerCommand(new CommandTofuSlimeCheck());
		event.registerServerCommand(new CommandTofuCreeperSpawn());

		// Initialize world save handler
		final SaveHandler saveHandler = (SaveHandler) event.getServer().worldServerForDimension(0).getSaveHandler();
		this.saveHandler = new TcSaveHandler(saveHandler.getWorldDirectory());

		// Load Morijio info
		this.morijioManager = this.saveHandler.loadMorijioData();

		// To handle spawn of Tofu Creeper ;)
		TofuCreeperSeed.initialize(12L);
		TofuCreeperSeed.instance().initSeed(event.getServer().worldServerForDimension(0).getSeed());

		// Notify if update is available
		if (update!=null&&event.getSide()==Side.SERVER)
			update.notifyUpdate(event.getServer(), event.getSide());
	}

	@Mod.EventHandler
	public void serverStopping(final FMLServerStoppingEvent event) {
	}

	public void registerChestLoot(final ItemStack loot, final int min, final int max, final int rarity) {
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(loot, min, max, rarity));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR,
				new WeightedRandomChestContent(loot, min, max, rarity));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR,
				new WeightedRandomChestContent(loot, min, max, rarity));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING,
				new WeightedRandomChestContent(loot, min, max, rarity));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
				new WeightedRandomChestContent(loot, min, max, rarity));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST,
				new WeightedRandomChestContent(loot, min, max, rarity));
	}

	public static TcSaveHandler getSaveHandler() {
		return instance.saveHandler;
	}

	public static MorijioManager getMorijioManager() {
		return instance.morijioManager;
	}

	@SideOnly(Side.CLIENT)
	public static class ClientProxy implements ISidedProxy {
		@Override
		public void registerComponents() {
			MinecraftForge.EVENT_BUS.register(new TcTextures());
			MinecraftForge.EVENT_BUS.register(new GameScreenHandler());

			LoaderDecorationBlock.registerBlockRenderer();
			TcEntity.registerEntityRenderer();

			MinecraftForgeClient.registerItemRenderer(TcItems.zundaBow, (IItemRenderer) TcItems.zundaBow);
			MinecraftForgeClient.registerItemRenderer(TcItems.bugle, (IItemRenderer) TcItems.bugle);

			final VillagerRegistry vill = VillagerRegistry.instance();
			vill.registerVillagerSkin(Settings.professionIdTofucook, new ResourceLocation("tofucraft", "textures/mob/tofucook.png"));
		}
	}

	@SideOnly(Side.SERVER)
	public static class ServerProxy implements ISidedProxy {
		@Override
		public void registerComponents() {
			GameRegistry.registerTileEntity(TileEntityMorijio.class, "TmMorijio");
		}
	}

	public static interface ISidedProxy {
		public void registerComponents();
	}
}
