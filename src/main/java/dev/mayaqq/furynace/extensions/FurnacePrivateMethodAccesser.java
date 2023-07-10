package dev.mayaqq.furynace.extensions;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface FurnacePrivateMethodAccesser {
    public boolean getBurning();
    public DefaultedList<ItemStack> getInventory();

    public void increaseGunPowderTick();
    public int getGunPowderTick();
}
