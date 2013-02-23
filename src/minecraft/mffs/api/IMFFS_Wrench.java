package mffs.api;

import net.minecraft.entity.player.EntityPlayer;

public abstract interface IMFFS_Wrench
{
	public abstract int getSide();

	public abstract void setSide(int paramInt);

	public abstract boolean wrenchCanManipulate(EntityPlayer paramEntityPlayer, int paramInt);
}