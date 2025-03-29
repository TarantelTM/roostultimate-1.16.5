package net.tarantel.chickenroost;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.tile.ModTileEntities;
import net.tarantel.chickenroost.block.tile.render.AnimatedTrainerRenderer;
import net.tarantel.chickenroost.block.tile.render.AnimatedTrainerRendererGeo;
import net.tarantel.chickenroost.block.tile.render.BreederChickenRender;
import net.tarantel.chickenroost.container.*;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.entity.render.ModEntityRenderers;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.network.NetworkHandler;
import net.tarantel.chickenroost.recipes.ModRecipeTypes;
import net.tarantel.chickenroost.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChickenRoostMod.MODID)
public class ChickenRoostMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "chicken_roost";
    //public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public ChickenRoostMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        GeckoLib.initialize();
        ModItems.register(eventBus);

        ModBlocks.register(eventBus);
        ModTileEntities.register(eventBus);
        ModContainers.register(eventBus);
        ModRecipeTypes.register(eventBus);
        ModEntities.register(eventBus);
        NetworkHandler.registerMessages();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ModEntities.initChickenConfig();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.BREEDER.get(), RenderType.cutoutMipped());
            //RenderTypeLookup.setRenderLayer(ModBlocks.TRAINER.get(), RenderType.cutoutMipped());
            RenderTypeLookup.setRenderLayer(ModBlocks.SOUL_EXTRACTOR.get(), RenderType.cutoutMipped());
            RenderTypeLookup.setRenderLayer(ModBlocks.ROOST.get(), RenderType.cutoutMipped());




            ScreenManager.register(ModContainers.BREEDER_CONTAINER.get(),
                    Breeder_Screen::new);
            ScreenManager.register(ModContainers.TRAINER_CONTAINER.get(),
                    Trainer_Screen::new);
            ScreenManager.register(ModContainers.ROOST_CONTAINER.get(),
                    Roost_Screen::new);
            ScreenManager.register(ModContainers.EXTRACTOR_CONTAINER.get(),
                    Extractor_Screen::new);


            ClientRegistry.bindTileEntityRenderer(ModTileEntities.BREEDER_TILE.get(), BreederChickenRender::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntities.TRAINER_TILE.get(), AnimatedTrainerRenderer::new);
            //ClientRegistry.bindTileEntityRenderer(ModTileEntities.TRAINER_TILE.get(), AnimatedTrainerRendererGeo::new);
            //ClientRegistry.bindTileEntityRenderer(ModTileEntities.EXTRACTOR_TILE.get(), BreederChickenRender::new);
            //ClientRegistry.bindTileEntityRenderer(ModTileEntities.ROOST_TILE.get(), BreederChickenRender::new);
                });
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        ModEntityRenderers.registerEntityRenderers();
        //ClientRegistry.bindTileEntityRenderer(LUNAR_CREATION_TABLE.get(), LunarCreationTableRenderer::new);
    }


    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("chicken_roost", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
