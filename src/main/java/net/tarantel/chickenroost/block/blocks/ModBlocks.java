package net.tarantel.chickenroost.block.blocks;


import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.block.blocks.crops.*;
import net.tarantel.chickenroost.item.ModItemGroup;
import net.tarantel.chickenroost.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChickenRoostMod.MODID);

    public static final RegistryObject<Block> SOUL_EXTRACTOR = registerBlock("soul_extractor",
            () -> new Soul_Extractor_Block(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1)));
    public static final RegistryObject<Block> ROOST = registerBlock("roost",
            () -> new Roost_Block(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1)));
    public static final RegistryObject<Block> TRAINER = registerBlock("trainer",
            () -> new Trainer_Block(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1)));
    public static final RegistryObject<Block> BREEDER = registerBlock("breeder",
            () -> new Breeder_Block(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1)));

    public static final RegistryObject<Block> SEED_CROP_1 = registerBlock("seed_crop_1",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_2 = registerBlock("seed_crop_2",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_3 = registerBlock("seed_crop_3",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_4 = registerBlock("seed_crop_4",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_5 = registerBlock("seed_crop_5",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_6 = registerBlock("seed_crop_6",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_7 = registerBlock("seed_crop_7",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_8 = registerBlock("seed_crop_8",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SEED_CROP_9 = registerBlock("seed_crop_9",
            () -> new CropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMSS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItemGroup.ROOST)));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}