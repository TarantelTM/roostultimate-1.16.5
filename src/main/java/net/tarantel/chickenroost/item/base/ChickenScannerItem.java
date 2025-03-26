
package net.tarantel.chickenroost.item.base;


import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ChickenScannerItem extends Item {
	public ChickenScannerItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
