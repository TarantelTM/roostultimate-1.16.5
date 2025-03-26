//package net.tarantel.chickenroost.network;
//
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.fml.network.NetworkDirection;
//import net.minecraftforge.fml.network.NetworkRegistry;
//import net.minecraftforge.fml.network.PacketDistributor;
//import net.minecraftforge.fml.network.simple.SimpleChannel;
//import net.tarantel.chickenroost.ChickenRoostMod;
//
//public class ModMessages {
//    private static SimpleChannel INSTANCE;
//
//    private static int packetId = 0;
//    private static int id() {
//        return packetId++;
//    }
//
//    public static void register() {
//        SimpleChannel net = NetworkRegistry.ChannelBuilder
//                .named(new ResourceLocation(ChickenRoostMod.MODID, "messages"))
//                .networkProtocolVersion(() -> "1.0")
//                .clientAcceptedVersions(s -> true)
//                .serverAcceptedVersions(s -> true)
//                .simpleChannel();
//
//        INSTANCE = net;
//
//
//        /*net.messageBuilder(RoostItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(RoostItemStackSyncS2CPacket::new)
//                .encoder(RoostItemStackSyncS2CPacket::toBytes)
//                .consumerMainThread(RoostItemStackSyncS2CPacket::handle)
//                .add();*/
//
//        net.messageBuilder(BreederItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(BreederItemStackSyncS2CPacket::new)
//                .encoder(BreederItemStackSyncS2CPacket::toBytes)
//                .consumerMainThread(BreederItemStackSyncS2CPacket::handle)
//                .add();
//
//        /*net.messageBuilder(ExtractorStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(ExtractorStackSyncS2CPacket::new)
//                .encoder(ExtractorStackSyncS2CPacket::toBytes)
//                .consumerMainThread(ExtractorStackSyncS2CPacket::handle)
//                .add();*/
//    }
//
//    public static <MSG> void sendToServer(MSG message) {
//        INSTANCE.sendToServer(message);
//    }
//
//    public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
//        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
//    }
//
//    public static <MSG> void sendToClients(MSG message) {
//        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
//    }
//}