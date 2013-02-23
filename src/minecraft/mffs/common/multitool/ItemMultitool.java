package mffs.common.multitool;

import java.util.ArrayList;
import java.util.List;

import mffs.api.IForceEnergyItems;
import mffs.common.ForceEnergyItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemMultitool extends ForceEnergyItems implements IForceEnergyItems
{
	private int typ;
	private static List MTTypes = new ArrayList();

	protected ItemMultitool(int id, int typ, boolean addToList)
	{
		super(id);
		this.typ = typ;
		setIconIndex(typ);
		setMaxStackSize(1);
		setMaxDamage(100);
		if (addToList)
			MTTypes.add(this);
	}

	protected ItemMultitool(int id, int typ)
	{
		this(id, typ, true);
	}

	public String getTextureFile()
	{
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable()
	{
		return false;
	}

	public boolean isDamageable()
	{
		return true;
	}

	public abstract boolean onItemUseFirst(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3);

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (entityplayer.isSneaking())
		{
			int modeNum = 0;
			for (int i = 0; i < MTTypes.size(); i++)
			{
				ItemMultitool MT = (ItemMultitool) MTTypes.get(i);
				if (MT.itemID == itemstack.getItem().itemID)
				{
					if (i + 1 < MTTypes.size())
						modeNum = i + 1;
					else
					{
						modeNum = 0;
					}
				}
			}
			int powerleft = getAvailablePower(itemstack);
			ItemStack hand = entityplayer.inventory.getCurrentItem();
			hand = new ItemStack((Item) MTTypes.get(modeNum), 1);
			chargeItem(hand, powerleft, false);
			return hand;
		}
		return itemstack;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		par1ItemStack.setItemDamage(getItemDamage(par1ItemStack));
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		String tooltip = String.format("%d FE/%d FE ", new Object[] { Integer.valueOf(getAvailablePower(itemStack)), Integer.valueOf(getMaximumPower(itemStack)) });
		info.add(tooltip);
	}

	public int getPowerTransferrate()
	{
		return 50000;
	}

	public int getMaximumPower(ItemStack itemStack)
	{
		return 1000000;
	}

	public int getItemDamage(ItemStack itemStack)
	{
		return 101 - getAvailablePower(itemStack) * 100 / getMaximumPower(itemStack);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		ItemStack charged = new ItemStack(this, 1);
		charged.setItemDamage(1);
		setAvailablePower(charged, getMaximumPower(null));
		itemList.add(charged);
	}
}