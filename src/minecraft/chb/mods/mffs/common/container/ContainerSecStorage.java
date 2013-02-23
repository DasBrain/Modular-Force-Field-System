package chb.mods.mffs.common.container;

import chb.mods.mffs.common.SlotHelper;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSecStorage extends Container
{
  private EntityPlayer player;
  private TileEntitySecStorage SecStorage;

  public ContainerSecStorage(EntityPlayer player, TileEntitySecStorage tileentity)
  {
    this.SecStorage = tileentity;
    this.player = player;

    addSlotToContainer(new SlotHelper(this.SecStorage, 0, 12, 24));

    for (int var3 = 0; var3 < 6; var3++) {
      for (int var4 = 0; var4 < 9; var4++) {
        addSlotToContainer(new SlotHelper(this.SecStorage, var4 + var3 * 9 + 1, 12 + var4 * 18, 43 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 3; var3++) {
      for (int var4 = 0; var4 < 9; var4++) {
        addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 12 + var4 * 18, 155 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 9; var3++)
      addSlotToContainer(new Slot(player.inventory, var3, 12 + var3 * 18, 213));
  }

  public EntityPlayer getPlayer()
  {
    return this.player;
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return this.SecStorage.isUseableByPlayer(entityplayer);
  }

  public ItemStack transferStackInSlot(EntityPlayer p, int i)
  {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.inventorySlots.get(i);
    if ((slot != null) && (slot.getHasStack()))
    {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (i < this.SecStorage.getSizeInventory())
      {
        if (!mergeItemStack(itemstack1, this.SecStorage.getSizeInventory(), this.inventorySlots.size(), true))
        {
          return null;
        }
      } else if (!mergeItemStack(itemstack1, 0, this.SecStorage.getSizeInventory(), false))
      {
        return null;
      }
      if (itemstack1.stackSize == 0)
      {
        slot.putStack(null);
      }
      else {
        slot.onSlotChanged();
      }
    }
    return itemstack;
  }
}