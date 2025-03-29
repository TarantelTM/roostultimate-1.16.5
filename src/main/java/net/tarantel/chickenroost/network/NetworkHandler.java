package net.tarantel.chickenroost.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.tarantel.chickenroost.ChickenRoostMod;

public class NetworkHandler  {


    public static SimpleChannel INSTANCE;
    private static int ID = 0;
    // Only needs to be changed when protocol changes but registry objects don't (because forge checks that)
    private static final String VERSION = "1.0";  // Not a semver!

    public static void registerMessages(){
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ChickenRoostMod.MODID, "packets"), () -> VERSION, VERSION::equals, VERSION::equals);

        // TODO: AnimationUpdatePacket and TileInfoPacket are the same thing.
        INSTANCE.registerMessage(ID++, AnimationUpdatePacket.class, AnimationUpdatePacket::encode, AnimationUpdatePacket::decode, AnimationUpdatePacket::handle);
    }
}