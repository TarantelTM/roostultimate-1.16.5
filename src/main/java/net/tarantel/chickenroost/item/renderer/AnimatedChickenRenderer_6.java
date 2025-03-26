package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChicken_6;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_6;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedChickenRenderer_6 extends GeoItemRenderer<AnimatedChicken_6> {
    public AnimatedChickenRenderer_6() {
        super(new AnimatedChickenModel_6());
    }
}
