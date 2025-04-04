//package net.tarantel.chickenroost.recipes;
//
//import com.google.gson.JsonObject;
//import net.minecraft.core.NonNullList;
//import net.minecraft.core.RegistryAccess;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.*;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.common.crafting.CraftingHelper;
//import net.tarantel.chickenroost.ChickenRoostMod;
//
//public class Soul_Breeder_Recipe implements Recipe<SimpleContainer> {
//    public final ResourceLocation recipeId;
//    public final ItemStack output;
//    public final Ingredient ingredient0;
//    public final Ingredient ingredient1;
//    public final int time;
//
//    public Soul_Breeder_Recipe(ResourceLocation recipeId,ItemStack output, Ingredient ingredient0, Ingredient ingredient1, int time) {
//        this.recipeId = recipeId;
//        this.output = output;
//        this.ingredient0 = ingredient0;
//        this.ingredient1 = ingredient1;
//        this.time = time;
//    }
//
//    @Override
//    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
//        return output.copy();
//    }
//
//    @Override
//    public ResourceLocation getId() {
//        return recipeId;
//    }
//
//    @Override
//    public ItemStack getResultItem(RegistryAccess registryAccess) {
//        return output.copy();
//    }
//
//    @Override
//    public boolean matches(SimpleContainer container, Level level) {
//        if (level.isClientSide()) {
//            return false;
//        }
//        return ingredient0.test(container.getItem(0)) && ingredient1.test(container.getItem(1));
//    }
//
//    @Override
//    public boolean isSpecial() {
//        return true;
//    }
//
//    @Override
//    public NonNullList<Ingredient> getIngredients() {
//        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(2);
//        ingredients.add(0, ingredient0);
//        ingredients.add(1, ingredient1);
//        return ingredients;
//    }
//
//    @Override
//    public boolean canCraftInDimensions(int width, int height) {
//        return true;
//    }
//
//    @Override
//    public String getGroup() {
//        return "soul_breeding";
//    }
//
//    @Override
//    public RecipeSerializer<?> getSerializer() {
//        return ModRecipes.SOUL_BREEDING_SERIALIZER.get();
//    }
//
//    @Override
//    public RecipeType<?> getType() {
//        return ModRecipes.SOUL_BREEDING_TYPE.get();
//    }
//
//    public static final class Type implements RecipeType<Soul_Breeder_Recipe> {
//        private Type() { }
//        public static final Type INSTANCE = new Type();
//        public static final String ID = "soul_breeding";
//    }
//
//    public static final class Serializer implements RecipeSerializer<Soul_Breeder_Recipe> {
//        private Serializer() {}
//        public static final Serializer INSTANCE = new Serializer();
//        public static final ResourceLocation ID = new ResourceLocation(ChickenRoostMod.MODID, "soul_breeding");
//
//        @Override
//        public Soul_Breeder_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
//            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
//            Ingredient ingredient0 = Ingredient.fromJson(json.getAsJsonObject("soul"));
//            Ingredient ingredient1 = Ingredient.fromJson(json.getAsJsonObject("chicken"));
//            int time;
//            if( !json.has( "time" ) ){
//                time = 20;
//            }
//            else{
//                time = json.get("time").getAsInt();
//            }
//            return new Soul_Breeder_Recipe(recipeId, output, ingredient0, ingredient1, time);
//        }
//
//        @Override
//        public Soul_Breeder_Recipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
//            Ingredient input0 = Ingredient.fromNetwork(buffer);
//            Ingredient input1 = Ingredient.fromNetwork(buffer);
//            ItemStack output = buffer.readItem();
//            int time = buffer.readVarInt();
//            return new Soul_Breeder_Recipe(recipeId, output, input0, input1, time);
//        }
//
//        @Override
//        public void toNetwork(FriendlyByteBuf buffer, Soul_Breeder_Recipe recipe) {
//            recipe.ingredient0.toNetwork(buffer);
//            recipe.ingredient1.toNetwork(buffer);
//            buffer.writeItem(recipe.output);
//            buffer.writeVarInt(recipe.time);
//        }
//    }
//}