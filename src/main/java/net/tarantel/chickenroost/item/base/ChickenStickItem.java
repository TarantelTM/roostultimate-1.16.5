
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ChickenStickItem extends Item implements IAnimatable {
	public ChickenStickItem(Properties properties) {
		super(properties);
	}
	public AnimationFactory factory = GeckoLibUtil.createFactory(this);
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
	private static final AnimationBuilder IDLE = new AnimationBuilder().loop("idle");
	private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(IDLE);
		return PlayState.CONTINUE;
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
