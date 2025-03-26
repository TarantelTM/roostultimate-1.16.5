package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChicken_4;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_4;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedChickenRenderer_4 extends GeoItemRenderer<AnimatedChicken_4> {
    public AnimatedChickenRenderer_4() {
        super(new AnimatedChickenModel_4());
    }
}
