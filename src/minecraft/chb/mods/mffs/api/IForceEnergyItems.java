package chb.mods.mffs.api;

import net.minecraft.item.ItemStack;

public abstract interface IForceEnergyItems
{
  public abstract int getAvailablePower(ItemStack paramItemStack);

  public abstract int getMaximumPower(ItemStack paramItemStack);

  public abstract boolean consumePower(ItemStack paramItemStack, int paramInt, boolean paramBoolean);

  public abstract void setAvailablePower(ItemStack paramItemStack, int paramInt);

  public abstract int getPowerTransferrate();

  public abstract int getItemDamage(ItemStack paramItemStack);
}