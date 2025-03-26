
package net.tarantel.chickenroost.item.base;


import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.tarantel.chickenroost.item.ModItemGroup;

public class Essence_Soul extends Item {
	public Essence_Soul() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)
				.food((new Food.Builder()).nutrition(4).saturationMod(0.3f).alwaysEat().build()).tab(ModItemGroup.ROOST));
	}
}
