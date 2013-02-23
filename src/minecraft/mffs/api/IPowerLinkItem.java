package mffs.api;

import mffs.common.tileentity.TileEntityMachines;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IPowerLinkItem
{
	public abstract int getPercentageCapacity(ItemStack paramItemStack, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract int getAvailablePower(ItemStack paramItemStack, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract int getMaximumPower(ItemStack paramItemStack, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract boolean consumePower(ItemStack paramItemStack, int paramInt, boolean paramBoolean, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract boolean insertPower(ItemStack paramItemStack, int paramInt, boolean paramBoolean, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract int getPowersourceID(ItemStack paramItemStack, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract int getfreeStorageAmount(ItemStack paramItemStack, TileEntityMachines paramTileEntityMachines, World paramWorld);

	public abstract boolean isPowersourceItem();
}