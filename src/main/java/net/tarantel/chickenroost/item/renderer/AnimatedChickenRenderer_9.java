package net.tarantel.chickenroost.item.renderer;

import net.tarantel.chickenroost.item.base.AnimatedChicken_9;
import net.tarantel.chickenroost.item.model.AnimatedChickenModel_9;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedChickenRenderer_9 extends GeoItemRenderer<AnimatedChicken_9> {
    public AnimatedChickenRenderer_9() {
        super(new AnimatedChickenModel_9());
    }
}
