//package net.tarantel.chickenroost.network;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundSource;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class PlaySoundPacket {
//    private final BlockPos pos;
//    private final SoundEvent sound;
//    private final SoundSource source;
//    private final float volume;
//    private final float pitch;
//
//    public PlaySoundPacket(BlockPos pos, SoundEvent sound, SoundSource source, float volume, float pitch) {
//        this.pos = pos;
//        this.sound = sound;
//        this.source = source;
//        this.volume = volume;
//        this.pitch = pitch;
//    }
//
//    public void encode(FriendlyByteBuf buffer) {
//        buffer.writeBlockPos(pos);
//        buffer.writeResourceLocation(sound.getLocation());
//        buffer.writeEnum(source);
//        buffer.writeFloat(volume);
//        buffer.writeFloat(pitch);
//    }
//
//    public static PlaySoundPacket decode(FriendlyByteBuf buffer) {
//        BlockPos pos = buffer.readBlockPos();
//        SoundEvent sound = SoundEvent.createVariableRangeEvent(buffer.readResourceLocation());
//        SoundSource source = buffer.readEnum(SoundSource.class);
//        float volume = buffer.readFloat();
//        float pitch = buffer.readFloat();
//        return new PlaySoundPacket(pos, sound, source, volume, pitch);
//    }
//
//    public void handle(Supplier<NetworkEvent.Context> context) {
//        context.get().enqueueWork(() -> {
//            // Play the sound on the client
//            if (context.get().getDirection().getReceptionSide().isClient()) {
//                Minecraft.getInstance().level.playLocalSound(pos, sound, source, volume, pitch, false);
//            }
//        });
//        context.get().setPacketHandled(true);
//    }
//}