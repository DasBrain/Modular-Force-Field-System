package chb.mods.mffs.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;

public class InventoryHelper
{
  public static IInventory findAttachedInventory(World worldObj, int x, int y, int z)
  {
    List tes = new ArrayList();

    tes.add(worldObj.getBlockTileEntity(x + 1, y, z));
    tes.add(worldObj.getBlockTileEntity(x - 1, y, z));
    tes.add(worldObj.getBlockTileEntity(x, y + 1, z));
    tes.add(worldObj.getBlockTileEntity(x, y - 1, z));
    tes.add(worldObj.getBlockTileEntity(x, y, z + 1));
    tes.add(worldObj.getBlockTileEntity(x, y, z - 1));

    IInventory inv = null;

    for (TileEntity te : tes) {
      if (((te instanceof IInventory)) && (
        (inv == null) || (inv.getSizeInventory() < ((IInventory)te).getSizeInventory())))
        inv = (IInventory)te;
    }
    return inv;
  }

  public static boolean addStacksToInventory(IInventory inventory, ArrayList itemstacks)
  {
    int count = 0;

    if ((inventory instanceof TileEntitySecStorage))
      count = 1;
    ItemStack inventorystack;
    for (int a = count; a <= inventory.getSizeInventory() - 1; a++) {
      inventorystack = inventory.getStackInSlot(a);

      for (ItemStack items : itemstacks) {
        if (items != null) {
          if (inventorystack != null) {
            if ((inventorystack.getItem() == items.getItem()) && (inventorystack.getItemDamage() == items.getItemDamage()) && (inventorystack.stackSize + 1 <= inventorystack.getMaxStackSize()) && (inventorystack.stackSize + 1 <= inventory.getInventoryStackLimit()))
            {
              inventorystack.stackSize += 1;

              items.stackSize -= 1;
              return true;
            }
          } else {
            inventorystack = items.copy();
            inventorystack.stackSize = 1;
            items.stackSize -= 1;
            inventory.setInventorySlotContents(a, inventorystack);

            return true;
          }
        }
      }
    }
    return false;
  }
}