package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChicken_7;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_7;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedChickenRenderer_7 extends GeoItemRenderer<AnimatedChicken_7> {
    public AnimatedChickenRenderer_7() {
        super(new AnimatedChickenModel_7());
    }
}
