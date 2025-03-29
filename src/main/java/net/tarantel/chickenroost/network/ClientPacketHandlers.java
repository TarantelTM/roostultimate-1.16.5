package net.tarantel.chickenroost.network;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;

// Makes sure that client only classes are never loaded on the server.
// Forge seems to load any classes referenced in the packet handling classes even if they aren't actually invoked.
public class ClientPacketHandlers {


    public static void handle(AnimationUpdatePacket packet) {
        if (Minecraft.getInstance().level == null) return;

        TileEntity tile = Minecraft.getInstance().level.getBlockEntity(packet.pos);
        if (tile instanceof Trainer_Tile){
            ((Trainer_Tile) tile).setClientAnimationState(packet.animationState);
        }
    }
}