package chb.mods.mffs.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import chb.mods.mffs.common.SlotHelper;
import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;

public class ContainerAdvSecurityStation extends Container
{
  private TileEntityAdvSecurityStation SecStation;
  private EntityPlayer player;

  public ContainerAdvSecurityStation(EntityPlayer player, TileEntityAdvSecurityStation tileentity)
  {
    this.SecStation = tileentity;
    this.player = player;

    addSlotToContainer(new SlotHelper(this.SecStation, 0, 177, 33));
    addSlotToContainer(new SlotHelper(this.SecStation, 1, 15, 30));

    addSlotToContainer(new SlotHelper(this.SecStation, 39, 88, 102));
    addSlotToContainer(new SlotHelper(this.SecStation, 38, 146, 102));

    for (int var3 = 0; var3 < 8; var3++) {
      for (int var4 = 0; var4 < 4; var4++) {
        addSlotToContainer(new SlotHelper(this.SecStation, var4 + var3 * 4 + 2, 176 + var4 * 18, 62 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 3; var3++) {
      for (int var4 = 0; var4 < 9; var4++) {
        addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 134 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 9; var3++)
      addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 192));
  }

  public EntityPlayer getPlayer()
  {
    return this.player;
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return this.SecStation.isUseableByPlayer(entityplayer);
  }

  public ItemStack transferStackInSlot(EntityPlayer p, int i)
  {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.inventorySlots.get(i);
    if ((slot != null) && (slot.getHasStack())) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (itemstack1.stackSize == 0)
        slot.putStack(null);
      else {
        slot.onSlotChanged();
      }
      if (itemstack1.stackSize != itemstack.stackSize)
        slot.onSlotChanged();
      else {
        return null;
      }
    }
    return itemstack;
  }
}