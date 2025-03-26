package net.tarantel.chickenroost.block.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.block.blocks.ModBlocks;


public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ChickenRoostMod.MODID);

    public static RegistryObject<TileEntityType<Breeder_Tile>> BREEDER_TILE =
            TILE_ENTITIES.register("breeder", () -> TileEntityType.Builder.of(
                    Breeder_Tile::new, ModBlocks.BREEDER.get()).build(null));

    public static RegistryObject<TileEntityType<Roost_Tile>> ROOST_TILE =
            TILE_ENTITIES.register("roost", () -> TileEntityType.Builder.of(
                    Roost_Tile::new, ModBlocks.ROOST.get()).build(null));

    public static RegistryObject<TileEntityType<Trainer_Tile>> TRAINER_TILE =
            TILE_ENTITIES.register("trainer", () -> TileEntityType.Builder.of(
                    Trainer_Tile::new, ModBlocks.TRAINER.get()).build(null));

    public static RegistryObject<TileEntityType<Extractor_Tile>> EXTRACTOR_TILE =
            TILE_ENTITIES.register("extractor", () -> TileEntityType.Builder.of(
                    Extractor_Tile::new, ModBlocks.SOUL_EXTRACTOR.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }



}