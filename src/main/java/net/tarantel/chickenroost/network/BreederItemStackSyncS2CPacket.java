//package net.tarantel.chickenroost.network;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.item.ItemFrameEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.util.math.BlockPos;
//import net.minecraftforge.fml.network.NetworkEvent;
//import net.minecraftforge.items.ItemStackHandler;
//import net.tarantel.chickenroost.block.tile.Breeder_Tile;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.function.Supplier;
//
//public class BreederItemStackSyncS2CPacket {
//    private final ItemStackHandler itemStackHandler;
//    private final BlockPos pos;
//
//    public BreederItemStackSyncS2CPacket(ItemStackHandler itemStackHandler, BlockPos pos) {
//        this.itemStackHandler = itemStackHandler;
//        this.pos = pos;
//    }
//
//
//    ItemFrameEntity
//    public BreederItemStackSyncS2CPacket(PacketBuffer buf) {
//        List<ItemStack> collection = buf.readLongArray(ArrayList::new, PacketBuffer::readLongArray);
//        itemStackHandler = new ItemStackHandler(collection.size());
//        for (int i = 0; i < collection.size(); i++) {
//            itemStackHandler.insertItem(i, collection.get(i), false);
//        }
//
//        this.pos = buf.readBlockPos();
//    }
//
//    public void toBytes(PacketBuffer buf) {
//        Collection<ItemStack> list = new ArrayList<>();
//        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
//            list.add(itemStackHandler.getStackInSlot(i));
//        }
//
//        NetworkUtil.writeCollection(list, PacketBuffer::r);
//        buf.writeBlockPos(pos);
//    }
//
//    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
//        NetworkEvent.Context context = supplier.get();
//        context.enqueueWork(() -> {
//            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof Breeder_Tile blockEntity) {
//                blockEntity.setHandler(this.itemStackHandler);
//            }
//        });
//        return true;
//    }
//}
//
//
//