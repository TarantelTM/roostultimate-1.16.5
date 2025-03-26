package net.tarantel.chickenroost.item.base;

//import net.minecraft.item.Item;
//import net.minecraft.item.ItemUseContext;
//import net.tarantel.chickenroost.item.renderer.AnimatedChickenStickRenderer;
//import net.tarantel.chickenroost.util.ChickenStickTool;
//import net.tarantel.chickenroost.util.WrenchTool;
//
//import java.util.function.Consumer;
//public class AnimatedChickenStick extends Item implements GeoItem {
//    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
//
//    public AnimatedChickenStick(Properties properties) {
//        super(properties);
//    }
//
//    private PlayState predicate(AnimationState animationState) {
//        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
//        return PlayState.CONTINUE;
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
//        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
//    }
//    @Override
//    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
//        return false;
//    }
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return cache;
//    }
//
//    @Override
//    public double getTick(Object itemStack) {
//        return RenderUtils.getCurrentTick();
//    }
//
//    @Override
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            private AnimatedChickenStickRenderer renderer;
//
//            @Override
//            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
//                if(this.renderer == null) {
//                    renderer = new AnimatedChickenStickRenderer();
//                }
//
//                return this.renderer;
//            }
//        });
//    }
//
//    @Override
//    public InteractionResult useOn(ItemUseContext context) {
//        super.useOn(context);
//        WrenchTool.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(),
//                context.getClickedPos().getZ());
//
//        /*if (context.getLevel().isClientSide()) {
//            BlockPos positionClicked = context.getClickedPos();
//            Player player = context.getPlayer();
//            boolean foundBlock = false;
//            spawnFoundParticles(context, positionClicked);
//
//            return InteractionResult.SUCCESS;
//        }*/
//        return InteractionResult.SUCCESS;
//    }
//
//    private void spawnFoundParticles(ItemUseContext pContext, BlockP positionClicked) {
//        for(int i = 0; i < 360; i++) {
//            if(i % 20 == 0) {
//                pContext.getLevel().addParticle(ModParticles.SOUL_PARTICLES.get(),
//                        positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d,
//                        Math.cos(i) * 0.15d, 0.15d, Math.sin(i) * 0.15d);
//            }
//        }
//    }
//
//    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
//        super.interactLivingEntity(stack, player, entity, hand);
//        Level level = entity.level();
//        InteractionResult retval = InteractionResult.sidedSuccess(entity.level().isClientSide());
//
//        double x = entity.getX();
//        double y = entity.getY();
//        double z = entity.getZ();
//        ChickenStickTool.execute(level, x, y, z, entity);
//        return retval;
//    }
//    @Override
//    public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
//        return 0F;
//    }
//}