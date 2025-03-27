package net.tarantel.chickenroost.item.renderer;


import net.tarantel.chickenroost.item.base.ChickenStickItem;
import net.tarantel.chickenroost.item.model.AnimatedChickenStickModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
public class AnimatedChickenStickRenderer extends GeoItemRenderer<ChickenStickItem> {
    public AnimatedChickenStickRenderer() {
        super(new AnimatedChickenStickModel());
    }
}