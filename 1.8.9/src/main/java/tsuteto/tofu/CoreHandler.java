package tsuteto.tofu;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tsuteto.tofu.eventhandler.EventBonemeal;
import tsuteto.tofu.eventhandler.EventEntityLiving;
import tsuteto.tofu.eventhandler.EventFillBucket;
import tsuteto.tofu.eventhandler.EventItemPickup;
import tsuteto.tofu.eventhandler.EventPlayer;
import tsuteto.tofu.eventhandler.EventPlayerInteract;
import tsuteto.tofu.eventhandler.EventWorld;
import tsuteto.tofu.eventhandler.TcCraftingHandler;
import tsuteto.tofu.fishing.TofuFishing;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.world.TcChunkProviderEvent;
import tsuteto.tofu.wrapper.BlockPosWrapper;
import tsuteto.tofu.wrapper.BlockStateWrapper;

public class CoreHandler {
	public static final @Nonnull CoreHandler instance = new CoreHandler();

	private EventBonemeal evBonemeal;
	private EventFillBucket evFillBucket;
	private EventPlayerInteract evPlayerInteract;
	private EventEntityLiving evEntityLiving;
	private TofuFishing evTofuFishing;
	private EventWorld evWorld;
	private EventPlayer evPlayer;
	private TcGuiHandler evTcGuiHandler;
	private TcCraftingHandler evTcCraftingHandler;
	private EventItemPickup evItemPickup;
	private TcChunkProviderEvent evTcChunkProvider;

	public void init() {
		// Register usage of the bone meal
		this.evBonemeal = new EventBonemeal();

		// Register usage of the empty bucket
		this.evFillBucket = new EventFillBucket();

		// Register event on player interact
		this.evPlayerInteract = new EventPlayerInteract();

		// Register event on EntityLiving
		this.evEntityLiving = new EventEntityLiving();

		// Register event on tofu fishing
		this.evTofuFishing = new TofuFishing();

		// Register world event handler
		this.evWorld = new EventWorld();

		// Register event on player
		this.evPlayer = new EventPlayer();

		this.evTcGuiHandler = new TcGuiHandler();

		// Register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(TofuCraft.instance, new IGuiHandler() {
			@Override
			public Object getServerGuiElement(final int guiId, final EntityPlayer player, final World world, final int x, final int y, final int z) {
				return CoreHandler.this.evTcGuiHandler.getServerGuiElement(guiId, player, world, x, y, z);
			}

			@Override
			public Object getClientGuiElement(final int guiId, final EntityPlayer player, final World world, final int x, final int y, final int z) {
				return CoreHandler.this.evTcGuiHandler.getClientGuiElement(guiId, player, world, x, y, z);
			}
		});

		// Register crafting handler
		this.evTcCraftingHandler = new TcCraftingHandler();

		// Register pick-up Handler
		this.evItemPickup = new EventItemPickup();

		this.evTcChunkProvider = new TcChunkProviderEvent();

		// FMLCommonHandler.instance().bus().register(this);

		MinecraftForge.EVENT_BUS.register(this);

		// Ore generation
		MinecraftForge.ORE_GEN_BUS.register(this);
	}

	@SubscribeEvent
	public void onBonemeal(final BonemealEvent event) {
		this.evBonemeal.onBonemeal(new EventResultHandler(event), event.entityPlayer, event.world, event.world.rand, new BlockStateWrapper(event.block), new BlockPosWrapper(event.pos));
	}
}