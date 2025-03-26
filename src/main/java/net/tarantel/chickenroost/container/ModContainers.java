package net.tarantel.chickenroost.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, ChickenRoostMod.MODID);

    public static final RegistryObject<ContainerType<Breeder_Container>> BREEDER_CONTAINER
            = CONTAINERS.register("breeder_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new Breeder_Container(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<Roost_Container>> ROOST_CONTAINER
            = CONTAINERS.register("roost_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new Roost_Container(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<Trainer_Container>> TRAINER_CONTAINER
            = CONTAINERS.register("trainer_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new Trainer_Container(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<Extractor_Container>> EXTRACTOR_CONTAINER
            = CONTAINERS.register("extractor_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new Extractor_Container(windowId, world, pos, inv, inv.player);
            })));


    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}