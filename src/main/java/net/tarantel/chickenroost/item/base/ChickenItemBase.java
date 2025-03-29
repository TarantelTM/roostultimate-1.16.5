package net.tarantel.chickenroost.item.base;


import net.minecraft.item.Item;

public class ChickenItemBase extends RoostUltimateItem {

    public int currentchickena;
    public ChickenItemBase(Properties properties, int currentchicken) {
        super(properties);
        this.currentchickena = currentchicken;
    }
}
