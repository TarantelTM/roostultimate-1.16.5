package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChicken_5;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_5;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedChickenRenderer_5 extends GeoItemRenderer<AnimatedChicken_5> {
    public AnimatedChickenRenderer_5() {
        super(new AnimatedChickenModel_5());
    }
}
