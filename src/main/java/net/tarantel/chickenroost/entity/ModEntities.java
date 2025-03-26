package net.tarantel.chickenroost.entity;

import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.ModItems;
import net.tarantel.chickenroost.util.ChickenConfig;
import net.tarantel.chickenroost.util.ChickenData;
import net.tarantel.chickenroost.util.GsonChickenReader;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, ChickenRoostMod.MODID);
    //region color chicken



    /*public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LAVA = registerMonster("c_lava",
            BaseChickenEntity::new,
            0.4f, 0.7f,
            0x302219, 0xACACAC
    );*/

    public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_LAVA = REGISTRY.register("c_lava",
            () -> EntityType.Builder.of(BaseChickenEntity::new, EntityClassification.CREATURE)
                    .sized(0.5F, 0.5F)
                    .build("c_lava"));

    /*public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_WATER = registerMob("c_water",
            BaseChickenEntity::new,
            0.4f, 0.7f,
            0x302219, 0xACACAC
    );
*/
    public static final RegistryObject<EntityType<BaseChickenEntity>> A_CHICKEN_WATER = REGISTRY.register("c_water",
            () -> EntityType.Builder.of(BaseChickenEntity::new, EntityClassification.CREATURE)
                    .sized(0.5F, 0.5F)
                    .build("c_water"));

    private static <T extends Entity> RegistryObject<EntityType<T>> registerMob(
            String name, EntityType.IFactory<T> factory,
            float width, float height, int primaryColor, int secondaryColor
    ) {
        return REGISTRY.register(name, () -> EntityType.Builder.of(factory, EntityClassification.CREATURE)
                .sized(0.4f, 0.7f)
                .clientTrackingRange(8)
                .build(name));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonster(
            String name, EntityType.IFactory<T> factory,
            float width, float height, int primaryColor, int secondaryColor
    ) {
        return REGISTRY.register(name, () -> EntityType.Builder.of(factory, EntityClassification.MONSTER)
                .sized(0.4f, 0.7f)
                .clientTrackingRange(8)
                .build(name));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerMobFireImmun(
            String name, EntityType.IFactory<T> factory,
            float width, float height, int primaryColor, int secondaryColor
    ) {
        return REGISTRY.register(name, () -> EntityType.Builder.of(factory, EntityClassification.CREATURE)
                .sized(0.4f, 0.7f)
                .clientTrackingRange(8)
                .fireImmune()
                .build(name));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerMonsterFireImmun(
            String name, EntityType.IFactory<T> factory,
            float width, float height, int primaryColor, int secondaryColor
    ) {
        return REGISTRY.register(name, () -> EntityType.Builder.of(factory, EntityClassification.MONSTER)
                .sized(0.4f, 0.7f)
                .clientTrackingRange(8)
                .fireImmune()
                .build(name));
    }
    //public static final EntityType<ChickenEntity> CHICKEN = register("chicken", EntityType.Builder.<ChickenEntity>of(ChickenEntity::new, EntityClassification.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));
    public static void readthis() {
        List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        if(!readItems.isEmpty()){
            for(ChickenData etherItem : readItems){
                String id = etherItem.getId();
                String mobormonster = etherItem.getMobOrMonster();
                Boolean IS_FIRE = etherItem.getIsFire();
                extrachickens(id, mobormonster, IS_FIRE);
            }
        }
    }

    private static RegistryObject<EntityType<BaseChickenEntity>> extrachickens(String idd, String mobormonster, Boolean IS_FIRE) {
        if(mobormonster.equals("Mob")){
            if(IS_FIRE){
                return registerMob(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
            }
            else {
                return registerMobFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
            }

        }
        else {
            if(IS_FIRE){
                return registerMonster(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
            }
            else {
                return registerMonsterFireImmun(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
            }

        }
        //return registerMob(idd, BaseChickenEntity::new, 0.4f, 0.7f, 0x302219,0xACACAC);
    }



    /*@SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BaseChickenEntity.init();
        });
    }*/
    public static void initChickenConfig() {

        ItemStack lavaegg = new ItemStack(ModItems.LAVA_EGG.get());
        ItemStack wateregg = new ItemStack(ModItems.WATER_EGG.get());
        CompoundNBT fluidTagLava = new CompoundNBT();
        CompoundNBT fluidTagWater = new CompoundNBT();
        fluidTagLava.putString("FluidName", "minecraft:lava");
        fluidTagLava.putInt("Amount", 1000);
        fluidTagWater.putString("FluidName", "minecraft:water");
        fluidTagWater.putInt("Amount", 1000);
//give Dev chicken_roost:lava_egg{Fluid:{FluidName:"minecraft:lava",Amount:1000}} 100
// Create a new CompoundTag for the root NBT data
        CompoundNBT nbtDataLava = new CompoundNBT();
        CompoundNBT nbtDataWater = new CompoundNBT();
        nbtDataLava.put("Fluid", fluidTagLava);
        nbtDataWater.put("Fluid", fluidTagWater);

// Set the NBT data to the ItemStack
        lavaegg.setTag(nbtDataLava);
        wateregg.setTag(nbtDataWater);

        List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        if(!readItems.isEmpty()){
            for(ChickenData etherItem : readItems){

                String id = etherItem.getId();
                String dropitem = etherItem.getDropitem();
                int eggtime = etherItem.getEggtime();

                boolean IS_FIRE = etherItem.getIsFire();
                boolean IS_PROJECTILE = etherItem.getIsProjectile();
                boolean IS_EXPLOSION = etherItem.getIsExplosion();
                boolean IS_FALL = etherItem.getIsFall();
                boolean IS_DROWNING = etherItem.getIsDrowning();
                boolean IS_FREEZING = etherItem.getIsFreezing();
                boolean IS_LIGHTNING = etherItem.getIsLightning();
                boolean IS_WITHER = etherItem.getIsWither();
                ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);
                EntityType<?> entityType = EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
                ItemStack dropStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(dropitem)));
                System.out.println("Setting drop stack for " + entityType + " to " + dropStack);
                ChickenConfig.setDropStack(entityType, dropStack);
                ChickenConfig.setEggTime(entityType, eggtime);

                ChickenConfig.setIsFire(entityType, IS_FIRE);
                ChickenConfig.setIsProjectile(entityType, IS_PROJECTILE);
                ChickenConfig.setIsExplosion(entityType, IS_EXPLOSION);
                ChickenConfig.setIsFall(entityType, IS_FALL);
                ChickenConfig.setIsDrowning(entityType, IS_DROWNING);
                ChickenConfig.setIsFreezing(entityType, IS_FREEZING);
                ChickenConfig.setIsLightning(entityType, IS_LIGHTNING);
                ChickenConfig.setIsWither(entityType, IS_WITHER);
            }
        }
        ChickenConfig.setDropStack(A_CHICKEN_LAVA.get(), lavaegg);
        ChickenConfig.setEggTime(A_CHICKEN_LAVA.get(), 600);
        ChickenConfig.setDropStack(A_CHICKEN_WATER.get(), wateregg);
        ChickenConfig.setEggTime(A_CHICKEN_WATER.get(), 600);
    }

    /*@SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        if(!readItems.isEmpty()){
            for(ChickenData etherItem : readItems){
                String id = etherItem.getId();
                ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);
                EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) EntityType.byString(resourceLocation.toString()).orElse(EntityType.CHICKEN);
                event.put(entityType, BaseChickenEntity.createAttributes().build());
            }
        }
        event.put(A_CHICKEN_LAVA.get(), BaseChickenEntity.createAttributes().build());
        event.put(A_CHICKEN_WATER.get(), BaseChickenEntity.createAttributes().build());
    }*/

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        List<ChickenData> readItems = GsonChickenReader.readItemsFromFile();
        if (!readItems.isEmpty()) {
            for (ChickenData etherItem : readItems) {
                String id = etherItem.getId();
                ResourceLocation resourceLocation = new ResourceLocation(ChickenRoostMod.MODID, id);

                // Try to get the entity type, but do not fall back to EntityType.CHICKEN
                Optional<EntityType<?>> optionalEntityType = EntityType.byString(resourceLocation.toString());
                if (optionalEntityType.isPresent()) {
                    EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) optionalEntityType.get();
                    event.put(entityType, BaseChickenEntity.createAttributes().build());
                } else {
                    // Log an error or handle the case where the entity type is not found
                    System.out.println("Entity type not found for ID: " + resourceLocation);
                }
            }
        }

        // Register attributes for your custom entities
        event.put(A_CHICKEN_LAVA.get(), BaseChickenEntity.createAttributes().build());
        event.put(A_CHICKEN_WATER.get(), BaseChickenEntity.createAttributes().build());
    }

    public static void register(IEventBus eventBus) {
        readthis();
        REGISTRY.register(eventBus);
    }
}