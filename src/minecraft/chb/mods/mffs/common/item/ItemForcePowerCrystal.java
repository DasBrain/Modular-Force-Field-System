package chb.mods.mffs.common.item;

import chb.mods.mffs.api.IForceEnergyItems;
import chb.mods.mffs.api.IPowerLinkItem;
import chb.mods.mffs.common.NBTTagCompoundHelper;
import chb.mods.mffs.common.tileentity.TileEntityMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemForcePowerCrystal extends ItemMFFSBase
  implements IPowerLinkItem, IForceEnergyItems
{
  public ItemForcePowerCrystal(int i)
  {
    super(i);
    setIconIndex(96);
    setMaxStackSize(1);
    setMaxDamage(100);
  }

  public String getTextureFile()
  {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable() {
    return false;
  }

  public boolean isDamageable()
  {
    return true;
  }

  public int getPowerTransferrate() {
    return 100000;
  }

  public int getIconFromDamage(int dmg)
  {
    if (dmg == 0)
      return 96;
    return 112 + (100 - dmg) / 20;
  }

  public int getItemDamage(ItemStack itemStack)
  {
    return 101 - getAvailablePower(itemStack, null, null) * 100 / getMaximumPower(itemStack);
  }

  public int getMaximumPower(ItemStack itemStack)
  {
    return 5000000;
  }

  public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
  {
    String tooltip = String.format("%d FE/%d FE ", new Object[] { Integer.valueOf(getAvailablePower(itemStack, null, null)), Integer.valueOf(getMaximumPower(itemStack)) });
    info.add(tooltip);
  }

  @SideOnly(Side.CLIENT)
  public void getSubItems(int i, CreativeTabs tabs, List itemList)
  {
    ItemStack charged = new ItemStack(this, 1);
    charged.setItemDamage(1);
    setAvailablePower(charged, getMaximumPower(null));
    itemList.add(charged);

    ItemStack empty = new ItemStack(this, 1);
    empty.setItemDamage(100);
    setAvailablePower(empty, 0);
    itemList.add(empty);
  }

  public boolean isPowersourceItem()
  {
    return true;
  }

  public int getAvailablePower(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    if (nbtTagCompound != null)
    {
      return nbtTagCompound.getInteger("ForceEnergy");
    }
    return 0;
  }

  public int getMaximumPower(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    return getMaximumPower(itemStack);
  }

  public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
  {
    if (getAvailablePower(itemStack, null, null) >= powerAmount)
    {
      if (!simulation)
      {
        setAvailablePower(itemStack, getAvailablePower(itemStack, null, null) - powerAmount);
      }
      return true;
    }
    return false;
  }

  public int getPowersourceID(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    return -1;
  }

  public int getPercentageCapacity(ItemStack itemStack, TileEntityMachines tem, World world) {
    return getAvailablePower(itemStack, null, null) / 1000 * 100 / (getMaximumPower(itemStack) / 1000);
  }

  public boolean insertPower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
  {
    if (getAvailablePower(itemStack) + powerAmount <= getMaximumPower(itemStack))
    {
      if (!simulation)
      {
        setAvailablePower(itemStack, getAvailablePower(itemStack, null, null) + powerAmount);
      }
      return true;
    }

    return false;
  }

  public int getfreeStorageAmount(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    return getMaximumPower(itemStack) - getAvailablePower(itemStack, null, null);
  }

  public void setAvailablePower(ItemStack itemStack, int ForceEnergy)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    nbtTagCompound.setInteger("ForceEnergy", ForceEnergy);

    itemStack.setItemDamage(getItemDamage(itemStack));
  }

  public int getAvailablePower(ItemStack itemStack)
  {
    return getAvailablePower(itemStack, null, null);
  }

  public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation)
  {
    return consumePower(itemStack, powerAmount, simulation, null, null);
  }
}