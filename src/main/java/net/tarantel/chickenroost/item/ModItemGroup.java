package net.tarantel.chickenroost.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup ROOST = new ItemGroup("tutorialModTab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.E_CHICKEN_LAVA.get());
        }
    };
}
