package ic2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract interface IWrenchable
{
  public abstract boolean wrenchCanSetFacing(EntityPlayer paramEntityPlayer, int paramInt);

  public abstract short getFacing();

  public abstract void setFacing(short paramShort);

  public abstract boolean wrenchCanRemove(EntityPlayer paramEntityPlayer);

  public abstract float getWrenchDropRate();

  public abstract ItemStack getWrenchDrop(EntityPlayer paramEntityPlayer);
}