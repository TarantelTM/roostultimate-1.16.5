package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChicken_8;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_8;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedChickenRenderer_8 extends GeoItemRenderer<AnimatedChicken_8> {
    public AnimatedChickenRenderer_8() {
        super(new AnimatedChickenModel_8());
    }
}
