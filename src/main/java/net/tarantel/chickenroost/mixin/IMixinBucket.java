//package net.tarantel.chickenroost.mixin;
//
//
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.BucketItem;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.tarantel.chickenroost.item.ModItems;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.injection.At;
//
//@Mixin(BucketItem.class)
//public abstract class IMixinBucket {
//
//
//
//    /**
//     * @author
//     * @reason
//     */
//    @Overwrite
//    protected ItemStack getEmptySuccessItem(ItemStack pStack, PlayerEntity pPlayer) {
//        if(pStack.getItem() == ModItems.LAVA_EGG.get()){
//            System.out.println("Its LAVAEGG");
//            return !pPlayer.abilities.instabuild ? new ItemStack(ItemStack.EMPTY.getItem()) : pStack;
//
//        }
//        else {
//            System.out.println("Its VANILLA BUCKET OR OTHER");
//            return !pPlayer.abilities.instabuild ? new ItemStack(Items.BUCKET) : pStack;
//        }
//
//    }
//}
//