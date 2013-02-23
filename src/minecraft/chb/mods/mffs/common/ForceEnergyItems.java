package chb.mods.mffs.common;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import chb.mods.mffs.api.IForceEnergyItems;
import chb.mods.mffs.common.item.ItemMFFSBase;

public abstract class ForceEnergyItems extends ItemMFFSBase
  implements IForceEnergyItems
{
  public ForceEnergyItems(int i)
  {
    super(i);
  }

  public boolean consumePower(ItemStack itemStack, int amount, boolean simulation)
  {
    if ((itemStack.getItem() instanceof IForceEnergyItems))
    {
      if (getAvailablePower(itemStack) >= amount)
      {
        if (!simulation)
        {
          setAvailablePower(itemStack, getAvailablePower(itemStack) - amount);
        }
        return true;
      }
    }
    return false;
  }

  public boolean chargeItem(ItemStack itemStack, int amount, boolean simulation)
  {
    if ((itemStack.getItem() instanceof IForceEnergyItems))
    {
      if (getAvailablePower(itemStack) + amount <= getMaximumPower(itemStack))
      {
        if (!simulation)
        {
          setAvailablePower(itemStack, getAvailablePower(itemStack) + amount);
        }
        return true;
      }
    }
    return false;
  }

  public void setAvailablePower(ItemStack itemStack, int ForceEnergy)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    nbtTagCompound.setInteger("ForceEnergy", ForceEnergy);
  }

  public int getAvailablePower(ItemStack itemstack)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    if (nbtTagCompound != null)
    {
      return nbtTagCompound.getInteger("ForceEnergy");
    }
    return 0;
  }
}