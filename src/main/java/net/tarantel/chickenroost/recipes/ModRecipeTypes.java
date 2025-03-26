package net.tarantel.chickenroost.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;


public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChickenRoostMod.MODID);


    public static final RegistryObject<Breeder_Recipe.Serializer> BREEDER_SERIALIZER
            = RECIPE_SERIALIZER.register("basic_breeding", Breeder_Recipe.Serializer::new);
    public static IRecipeType<Breeder_Recipe> BREEDER_RECIPE
            = new Breeder_Recipe.Breeder_RecipeType();

    public static final RegistryObject<Roost_Recipe.Serializer> ROOST_SERIALIZER
            = RECIPE_SERIALIZER.register("roost_output", Roost_Recipe.Serializer::new);
    public static IRecipeType<Roost_Recipe> ROOST_RECIPE
            = new Roost_Recipe.Roost_RecipeType();

    public static final RegistryObject<Trainer_Recipe.Serializer> TRAINER_SERIALIZER
            = RECIPE_SERIALIZER.register("trainer_output", Trainer_Recipe.Serializer::new);
    public static IRecipeType<Trainer_Recipe> TRAINER_RECIPE
            = new Trainer_Recipe.Trainer_RecipeType();

    public static final RegistryObject<Soul_Extractor_Recipe.Serializer> EXTRACTOR_SERIALIZER
            = RECIPE_SERIALIZER.register("soul_extraction", Soul_Extractor_Recipe.Serializer::new);
    public static IRecipeType<Soul_Extractor_Recipe> EXTRACTOR_RECIPE
            = new Soul_Extractor_Recipe.Soul_Extractor_RecipeType();

    public static final RegistryObject<ThrowEggRecipe.Serializer> THROWEGG_SERIALIZER
            = RECIPE_SERIALIZER.register("throwegg", ThrowEggRecipe.Serializer::new);
    public static IRecipeType<ThrowEggRecipe> THROWEGG_RECIPE
            = new ThrowEggRecipe.ThrowEggRecipeType();


    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, Breeder_Recipe.TYPE_ID, BREEDER_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, Roost_Recipe.TYPE_ID, ROOST_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, Trainer_Recipe.TYPE_ID, TRAINER_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, Soul_Extractor_Recipe.TYPE_ID, EXTRACTOR_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, ThrowEggRecipe.TYPE_ID, THROWEGG_RECIPE);
    }
}