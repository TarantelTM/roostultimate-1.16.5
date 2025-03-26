package net.tarantel.chickenroost.item;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
//import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.*;
import net.tarantel.chickenroost.item.renderer.*;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;

import java.util.List;
import java.util.concurrent.Callable;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ChickenRoostMod.MODID);

    public static final DeferredRegister<Item> ITEMSS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ChickenRoostMod.MODID);
    ///Ingots
    public static final RegistryObject<Item> INGOT_ELECTRUM = ITEMS.register("ingot_electrum", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_SILVER = ITEMS.register("ingot_silver", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_ZINC = ITEMS.register("ingot_zinc", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));

    public static final RegistryObject<Item> INGOT_BRONZE = ITEMS.register("ingot_bronze", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_LEAD = ITEMS.register("ingot_lead", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_STEEL = ITEMS.register("ingot_steel", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_TIN = ITEMS.register("ingot_tin", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_URANIUM = ITEMS.register("ingot_uranium", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));

    public static final RegistryObject<Item> INGOT_ALUMINUM = ITEMS.register("ingot_aluminum", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_CHROME = ITEMS.register("ingot_chrome", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));

    public static final RegistryObject<Item> INGOT_INVAR = ITEMS.register("ingot_invar", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_IRIDIUM = ITEMS.register("ingot_iridium", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));

    public static final RegistryObject<Item> INGOT_NICKEL = ITEMS.register("ingot_nickel", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_PLATINUM = ITEMS.register("ingot_platinum", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));

    public static final RegistryObject<Item> INGOT_TITANUM = ITEMS.register("ingot_titanum", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_TUNGSTEN = ITEMS.register("ingot_tungsten", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_TUNGSTENSTEEL = ITEMS.register("ingot_tungstensteel", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));


    public static final RegistryObject<Item> INGOT_ENDERIUM = ITEMS.register("ingot_enderium", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_ADAMANTIUM = ITEMS.register("ingot_adamantium", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_LUMIUM = ITEMS.register("ingot_lumium", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> INGOT_SIGNALUM = ITEMS.register("ingot_signalum", () -> new Item(new Item.Properties().tab(ModItemGroup.ROOST)));


    public static final RegistryObject<Item> LAVA_EGG = ITEMS.register("lava_egg", () -> new FilledLavaEggItem((new Item.Properties()).stacksTo(64).tab(ModItemGroup.ROOST), Fluids.LAVA));
    public static final RegistryObject<Item> WATER_EGG = ITEMS.register("water_egg", () -> new FilledWaterEggItem((new Item.Properties()).stacksTo(64).tab(ModItemGroup.ROOST), Fluids.LAVA));
    public static final RegistryObject<Item> STONE_ESSENCE = ITEMS.register("stone_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> WOOD_ESSENCE = ITEMS.register("wood_essence", () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    //region Color Eggs
    public static final RegistryObject<Item> RED_EGG = ITEMS.register("red_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_red"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> BLUE_EGG = ITEMS.register("blue_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_lapis"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> YELLOW_EGG = ITEMS.register("yellow_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_yellow"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> WHITE_EGG = ITEMS.register("white_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_white"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> ORANGE_EGG = ITEMS.register("orange_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_orange"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> MAGENTA_EGG = ITEMS.register("magenta_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_magenta"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> LIGHT_BLUE_EGG = ITEMS.register("light_blue_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_light_blue"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> LIME_EGG = ITEMS.register("lime_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_lime"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> PINK_EGG = ITEMS.register("pink_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_pink"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> GRAY_EGG = ITEMS.register("gray_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> LIGHT_GRAY_EGG = ITEMS.register("light_gray_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_light_gray"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CYAN_EGG = ITEMS.register("cyan_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_cyan"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> PURPLE_EGG = ITEMS.register("purple_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_purple"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> BROWN_EGG = ITEMS.register("brown_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_brown"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> GREEN_EGG = ITEMS.register("green_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_green"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> BLACK_EGG = ITEMS.register("black_egg", () -> new RoostEgg(new ResourceLocation(ChickenRoostMod.MODID, "c_black"), new RoostEgg.Properties().stacksTo(64).rarity(Rarity.COMMON).tab(ModItemGroup.ROOST)));

    //endregion

    public static final RegistryObject<Item> CHICKENCHICKEN = ITEMS.register("c_vanilla", () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
        private final AnimatedChickenRenderer_1 renderer = new AnimatedChickenRenderer_1();

        @Override public AnimatedChickenRenderer_1 call() throws Exception {
            return this.renderer;
        }
    }).tab(ModItemGroup.ROOST), "saltchicken", 0));

    //region Chicken - Tier 2
    public static final RegistryObject<Item> E_CHICKEN_LAVA = ITEMS.register("c_lava", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
        private final AnimatedChickenRenderer_2 renderer = new AnimatedChickenRenderer_2();

        @Override public AnimatedChickenRenderer_2 call() throws Exception {
            return this.renderer;
        }
    }).tab(ModItemGroup.ROOST), "lavachicken", 2));
    public static final RegistryObject<Item> E_CHICKEN_WATER = ITEMS.register("c_water", () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
        private final AnimatedChickenRenderer_2 renderer = new AnimatedChickenRenderer_2();

        @Override public AnimatedChickenRenderer_2 call() throws Exception {
            return this.renderer;
        }
    }).tab(ModItemGroup.ROOST), "waterchicken", 2));

    //region Tools
    //public static final RegistryObject<Item> CHICKEN_SCANNER = ITEMS.register("chicken_scanner", () -> new ChickenScannerItem());
    //public static final RegistryObject<Item> CHICKEN_STICK = ITEMS.register("chicken_stick", () -> new AnimatedChickenStick(new Item.Properties()));
    //endregion

    //region Essence
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_1 = ITEMS.register("chicken_essence_tier_1", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_2 = ITEMS.register("chicken_essence_tier_2", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_3 = ITEMS.register("chicken_essence_tier_3", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_4 = ITEMS.register("chicken_essence_tier_4", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_5 = ITEMS.register("chicken_essence_tier_5", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_6 = ITEMS.register("chicken_essence_tier_6", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_7 = ITEMS.register("chicken_essence_tier_7", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_8 = ITEMS.register("chicken_essence_tier_8", () -> new Essence_Soul());
    public static final RegistryObject<Item> CHICKEN_ESSENCE_TIER_9 = ITEMS.register("chicken_essence_tier_9", () -> new Essence_Soul());
    //endregion


   // //region SEEDS
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_1 = ITEMS.register("chicken_food_tier_1", () -> new BlockNamedItem(ModBlocks.SEED_CROP_1.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_2 = ITEMS.register("chicken_food_tier_2", () -> new BlockNamedItem(ModBlocks.SEED_CROP_2.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_3 = ITEMS.register("chicken_food_tier_3", () -> new BlockNamedItem(ModBlocks.SEED_CROP_3.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_4 = ITEMS.register("chicken_food_tier_4", () -> new BlockNamedItem(ModBlocks.SEED_CROP_4.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_5 = ITEMS.register("chicken_food_tier_5", () -> new BlockNamedItem(ModBlocks.SEED_CROP_5.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_6 = ITEMS.register("chicken_food_tier_6", () -> new BlockNamedItem(ModBlocks.SEED_CROP_6.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_7 = ITEMS.register("chicken_food_tier_7", () -> new BlockNamedItem(ModBlocks.SEED_CROP_7.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_8 = ITEMS.register("chicken_food_tier_8", () -> new BlockNamedItem(ModBlocks.SEED_CROP_8.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
    public static final RegistryObject<Item> CHICKEN_FOOD_TIER_9 = ITEMS.register("chicken_food_tier_9", () -> new BlockNamedItem(ModBlocks.SEED_CROP_9.get(), (new Item.Properties()).tab(ModItemGroup.ROOST)));
   // //endregion


    /*public static final RegistryObject<BlockItem> TRAINER = ITEMSS.register("trainer",
            () -> new AnimatedTrainerBlockItem(ModBlocks.TRAINER.get(),
                    new Item.Properties()));*/


    public static void readthis() {

        List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        if(!readItems.isEmpty()){
            for(ChickenData etherItem : readItems){

                String id = etherItem.getId();
                String itemtexture = etherItem.getItemtexture();
                String mobtexture = etherItem.getMobtexture();
                String dropitem = etherItem.getDropitem();
                float eggtime = etherItem.getEggtime();
                int tierr = etherItem.getTier();

                extrachickens(id, itemtexture, tierr);
            }
        }
    }

    private static RegistryObject<Item> extrachickens(String idd, String texturee, int tierr) {
        //return switch (tierr) {
            if(tierr == 1){
               return ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                   private final AnimatedChickenRenderer_1 renderer = new AnimatedChickenRenderer_1();

                   @Override public AnimatedChickenRenderer_1 call() throws Exception {
                       return this.renderer;
                   }
               }).tab(ModItemGroup.ROOST), texturee, 0));
            } else if (tierr == 2) {
                return ITEMS.register(idd, () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_2 renderer = new AnimatedChickenRenderer_2();

                    @Override public AnimatedChickenRenderer_2 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 1));
            } else if (tierr == 3) {
                return ITEMS.register(idd, () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_3 renderer = new AnimatedChickenRenderer_3();

                    @Override public AnimatedChickenRenderer_3 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 2));
            } else if (tierr == 4) {
                return ITEMS.register(idd, () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_4 renderer = new AnimatedChickenRenderer_4();

                    @Override public AnimatedChickenRenderer_4 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 3));
            } else if (tierr == 5) {
                return ITEMS.register(idd, () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_5 renderer = new AnimatedChickenRenderer_5();

                    @Override public AnimatedChickenRenderer_5 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 4));
            } else if (tierr == 6) {
                return ITEMS.register(idd, () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_6 renderer = new AnimatedChickenRenderer_6();

                    @Override public AnimatedChickenRenderer_6 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 5));
            } else if (tierr == 7) {
                return ITEMS.register(idd, () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_7 renderer = new AnimatedChickenRenderer_7();

                    @Override public AnimatedChickenRenderer_7 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 6));
            } else if (tierr == 8) {
                return ITEMS.register(idd, () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_8 renderer = new AnimatedChickenRenderer_8();

                    @Override public AnimatedChickenRenderer_8 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 7));
            } else if (tierr == 9) {
                return ITEMS.register(idd, () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_9 renderer = new AnimatedChickenRenderer_9();

                    @Override public AnimatedChickenRenderer_9 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 8));
            } else {
                return ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON).setISTER(() -> new Callable() {
                    private final AnimatedChickenRenderer_1 renderer = new AnimatedChickenRenderer_1();

                    @Override public AnimatedChickenRenderer_1 call() throws Exception {
                        return this.renderer;
                    }
                }).tab(ModItemGroup.ROOST), texturee, 0));
            }
            /*case 1 -> ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee, 0));
            case 2 -> ITEMS.register(idd, () -> new AnimatedChicken_2(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,1));
            case 3 -> ITEMS.register(idd, () -> new AnimatedChicken_3(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,2));
            case 4 -> ITEMS.register(idd, () -> new AnimatedChicken_4(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,3));
            case 5 -> ITEMS.register(idd, () -> new AnimatedChicken_5(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,4));
            case 6 -> ITEMS.register(idd, () -> new AnimatedChicken_6(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,5));
            case 7 -> ITEMS.register(idd, () -> new AnimatedChicken_7(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,6));
            case 8 -> ITEMS.register(idd, () -> new AnimatedChicken_8(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,7));
            case 9 -> ITEMS.register(idd, () -> new AnimatedChicken_9(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,8));
            default -> ITEMS.register(idd, () -> new AnimatedChicken_1(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.COMMON), texturee,0));
        };*/

    }

    public static void register(IEventBus eventBus) {
        readthis();
        ITEMS.register(eventBus);
        ITEMSS.register(eventBus);
    }
}