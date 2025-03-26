package net.tarantel.chickenroost.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class Breeder_Recipe implements IBreeder_Recipe {
    public final ResourceLocation recipeId;
    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Ingredient ingredient1;
    public final Ingredient ingredient2;
    public final int time;

    public Breeder_Recipe(ResourceLocation recipeId, ItemStack output, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, int time) {
        this.recipeId = recipeId;
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.time = time;
    }



    @Override
    public boolean matches(IInventory inv, World level) {
        if (level.isClientSide()) {
            return false;
        }
        return ingredient0.test(inv.getItem(1)) && ingredient1.test(inv.getItem(0)) && ingredient2.test(inv.getItem(2));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return output;
    }


    public ItemStack getResultEmi() {
        return output.copy();
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(3, Ingredient.EMPTY);
        ingredients.add(0, ingredient0);
        ingredients.add(1, ingredient1);
        ingredients.add(2, ingredient2);
        return ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public int getTime(){
        return time;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.BREEDER_SERIALIZER.get();
    }

    public static class Breeder_RecipeType implements IRecipeType<Breeder_Recipe> {
        @Override
        public String toString() {
            return Breeder_Recipe.TYPE_ID.toString();
        }
    }




    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<Breeder_Recipe> {

        @Override
        public Breeder_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(json.getAsJsonObject("output"));
            Ingredient ingredient0 = Ingredient.fromJson(json.getAsJsonObject("food"));
            Ingredient ingredient1 = Ingredient.fromJson(json.getAsJsonObject("left-chicken"));
            Ingredient ingredient2 = Ingredient.fromJson(json.getAsJsonObject("right-chicken"));
            int time;
            if( !json.has( "time" ) ){
                time = 20;
            }
            else{
                time = json.get("time").getAsInt();
            }
            return new Breeder_Recipe(recipeId, output, ingredient0, ingredient1, ingredient2, time);
        }
        @Nullable
        @Override
        public Breeder_Recipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient0 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient2 = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int time = buffer.readVarInt();
            return new Breeder_Recipe(recipeId, output, ingredient0, ingredient1, ingredient2, time);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, Breeder_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            recipe.ingredient1.toNetwork(buffer);
            recipe.ingredient2.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeVarInt(recipe.time);
        }
    }
}