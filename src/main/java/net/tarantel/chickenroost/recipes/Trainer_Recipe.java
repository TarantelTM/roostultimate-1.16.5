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

public class Trainer_Recipe implements ITrainer_Recipe {
    public final ResourceLocation recipeId;
    public final ItemStack output;
    public final Ingredient ingredient0;
    public final Ingredient ingredient1;
    public final int time;

    public Trainer_Recipe(ResourceLocation recipeId, ItemStack output, Ingredient ingredient0, Ingredient ingredient1, int time) {
        this.recipeId = recipeId;
        this.output = output;
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.time = time;
    }



    @Override
    public boolean matches(IInventory inv, World level) {
        if (level.isClientSide()) {
            return false;
        }
        return ingredient0.test(inv.getItem(1)) && ingredient1.test(inv.getItem(0));
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
        return ModRecipeTypes.TRAINER_SERIALIZER.get();
    }

    public static class Trainer_RecipeType implements IRecipeType<Trainer_Recipe> {
        @Override
        public String toString() {
            return Trainer_Recipe.TYPE_ID.toString();
        }
    }




    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<Trainer_Recipe> {

        @Override
        public Trainer_Recipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(json.getAsJsonObject("output"));
            Ingredient ingredient0 = Ingredient.fromJson(json.getAsJsonObject("food"));
            Ingredient ingredient1 = Ingredient.fromJson(json.getAsJsonObject("chicken"));
            int time;
            if( !json.has( "time" ) ){
                time = 20;
            }
            else{
                time = json.get("time").getAsInt();
            }
            return new Trainer_Recipe(recipeId, output, ingredient0, ingredient1, time);
        }
        @Nullable
        @Override
        public Trainer_Recipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient0 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int time = buffer.readVarInt();
            return new Trainer_Recipe(recipeId, output, ingredient0, ingredient1, time);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, Trainer_Recipe recipe) {
            recipe.ingredient0.toNetwork(buffer);
            recipe.ingredient1.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeVarInt(recipe.time);
        }
    }
}