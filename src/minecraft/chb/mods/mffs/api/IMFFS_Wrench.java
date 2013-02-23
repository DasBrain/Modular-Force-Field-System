package chb.mods.mffs.api;

import net.minecraft.entity.player.EntityPlayer;

public abstract interface IMFFS_Wrench
{
  public abstract int getSide();

  public abstract void setSide(int paramInt);

  public abstract boolean wrenchCanManipulate(EntityPlayer paramEntityPlayer, int paramInt);
}

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.api.IMFFS_Wrench
 * JD-Core Version:    0.6.2
 */