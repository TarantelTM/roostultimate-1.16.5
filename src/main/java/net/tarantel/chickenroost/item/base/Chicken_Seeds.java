
package net.tarantel.chickenroost.item.base;


import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class Chicken_Seeds extends Item {
	public Chicken_Seeds() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

}
