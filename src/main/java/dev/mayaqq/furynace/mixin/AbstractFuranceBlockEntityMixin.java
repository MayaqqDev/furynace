package dev.mayaqq.furynace.mixin;

import dev.mayaqq.furynace.extensions.FurnacePrivateMethodAccesser;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFuranceBlockEntityMixin implements FurnacePrivateMethodAccesser {

    @Shadow protected abstract boolean isBurning();

    @Shadow protected DefaultedList<ItemStack> inventory;

    int gunPowderTick = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private static void tickMixin(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        boolean burning = ((FurnacePrivateMethodAccesser) blockEntity).getBurning();
        ItemStack itemStack = ((FurnacePrivateMethodAccesser) blockEntity).getInventory().get(0);
        Item item = itemStack.getItem();
        if (burning && item == Items.GUNPOWDER) {
            ((FurnacePrivateMethodAccesser) blockEntity).increaseGunPowderTick();
            int gunPowderTick = ((FurnacePrivateMethodAccesser) blockEntity).getGunPowderTick();
            world.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 0.0D, 1.0D, 0.0D);
            if (gunPowderTick == 1) {
                world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            if (gunPowderTick == 200) {
                gunPowderTick = 0;
                int count = itemStack.getCount();
                itemStack.setCount(0);
                world.breakBlock(pos, true);
                world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.5F * count, World.ExplosionSourceType.BLOCK);
            }
        }
    }

    @Override
    public boolean getBurning() {
        return isBurning();
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void increaseGunPowderTick() {
        gunPowderTick++;
    }

    @Override
    public int getGunPowderTick() {
        return gunPowderTick;
    }
}
