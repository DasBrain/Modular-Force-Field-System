package chb.mods.mffs.common;

import chb.mods.mffs.common.tileentity.TileEntityMachines;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHelper extends Slot
{
  private TileEntityMachines te;
  private int Slot;

  public SlotHelper(IInventory par2IInventory, int par3, int par4, int par5)
  {
    super(par2IInventory, par3, par4, par5);
    this.te = ((TileEntityMachines)par2IInventory);
    this.Slot = par3;
  }

  public boolean isItemValid(ItemStack par1ItemStack)
  {
    return this.te.isItemValid(par1ItemStack, this.Slot);
  }

  public int getSlotStackLimit()
  {
    return this.te.getSlotStackLimit(this.Slot);
  }
}