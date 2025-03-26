package net.tarantel.chickenroost.entity.render;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.entity.ModEntities;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;

import java.util.List;

public class ModEntityRenderers {

    public static void registerEntityRenderers() {
        List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        if(!readItems.isEmpty()){
            for(ChickenData etherItem : readItems){

                String id = etherItem.getId();
                String mobtexture = etherItem.getMobtexture();

                ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);
                EntityType entityType =  EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
                RenderingRegistry.registerEntityRenderingHandler(entityType, context -> new BaseChickenRenderer(context, mobtexture));
            }
        }
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.A_CHICKEN_LAVA.get(), context -> new BaseChickenRenderer(context, "lavachicken"));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.A_CHICKEN_WATER.get(), context -> new BaseChickenRenderer(context, "waterchicken"));
    }
}
