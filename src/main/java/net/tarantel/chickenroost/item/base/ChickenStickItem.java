
package net.tarantel.chickenroost.item.base;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tarantel.chickenroost.util.ChickenStickTool;
import net.tarantel.chickenroost.util.WrenchTool;

public class ChickenStickItem extends Item {
	public ChickenStickItem() {
		super(new Item.Properties().durability(16).rarity(Rarity.COMMON));
	}


	@Override
	public UseAction getUseAnimation(ItemStack itemstack) {
		return UseAction.BLOCK;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		super.useOn(context);
		WrenchTool.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(),
				context.getClickedPos().getZ());
		return ActionResultType.SUCCESS;
	}
	@Override
	public boolean canAttackBlock(BlockState p_41441_, World p_41442_, BlockPos p_41443_, PlayerEntity p_41444_) {
		return false;
	}
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		super.interactLivingEntity(stack, player, entity, hand);
		World level = entity.level;
		ActionResultType retval = ActionResultType.sidedSuccess(entity.level.isClientSide());

		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		ChickenStickTool.execute(level, x, y, z, entity);
		return retval;
	}
	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 0F;
	}
}
