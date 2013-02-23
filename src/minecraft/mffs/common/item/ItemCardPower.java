package mffs.common.item;

import java.util.List;

import mffs.api.IForceEnergyItems;
import mffs.api.IPowerLinkItem;
import mffs.common.tileentity.TileEntityMachines;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCardPower extends ItemMFFSBase implements IPowerLinkItem, IForceEnergyItems
{
	public ItemCardPower(int id)
	{
		super(id);
		setMaxStackSize(1);
		setIconIndex(21);
	}

	public String getTextureFile()
	{
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable()
	{
		return false;
	}

	public int getPercentageCapacity(ItemStack itemStack, TileEntityMachines tem, World world)
	{
		return 100;
	}

	public int getAvailablePower(ItemStack itemStack, TileEntityMachines tem, World world)
	{
		return 10000000;
	}

	public int getMaximumPower(ItemStack itemStack, TileEntityMachines tem, World world)
	{
		return 10000000;
	}

	public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
	{
		return true;
	}

	public boolean insertPower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
	{
		return false;
	}

	public int getPowersourceID(ItemStack itemStack, TileEntityMachines tem, World world)
	{
		return 0;
	}

	public int getfreeStorageAmount(ItemStack itemStack, TileEntityMachines tem, World world)
	{
		return 0;
	}

	public boolean isPowersourceItem()
	{
		return true;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		info.add("Admin Card to Power Maschines");
		info.add("or use to infinit charge Capactior");
	}

	public int getAvailablePower(ItemStack itemStack)
	{
		return getAvailablePower(itemStack, null, null);
	}

	public int getMaximumPower(ItemStack itemStack)
	{
		return getMaximumPower(itemStack, null, null);
	}

	public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation)
	{
		return true;
	}

	public void setAvailablePower(ItemStack itemStack, int amount)
	{
	}

	public int getPowerTransferrate()
	{
		return 1000000;
	}

	public int getItemDamage(ItemStack stackInSlot)
	{
		return 0;
	}
}