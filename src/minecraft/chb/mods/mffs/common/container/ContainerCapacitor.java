package chb.mods.mffs.common.container;

import chb.mods.mffs.common.SlotHelper;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCapacitor extends Container
{
  private TileEntityCapacitor generatorentity;
  private int capacity;
  private int forcepower;
  private int Powerlinkmode;
  private short linketprojektor;
  private EntityPlayer player;

  public ContainerCapacitor(EntityPlayer player, TileEntityCapacitor tileentity)
  {
    this.forcepower = -1;
    this.linketprojektor = -1;
    this.capacity = -1;
    this.Powerlinkmode = -1;
    this.generatorentity = tileentity;
    this.player = player;

    addSlotToContainer(new SlotHelper(this.generatorentity, 4, 154, 88));
    addSlotToContainer(new SlotHelper(this.generatorentity, 0, 154, 47));
    addSlotToContainer(new SlotHelper(this.generatorentity, 1, 154, 67));
    addSlotToContainer(new SlotHelper(this.generatorentity, 2, 87, 76));

    for (int var3 = 0; var3 < 3; var3++) {
      for (int var4 = 0; var4 < 9; var4++) {
        addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 125 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 9; var3++)
      addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 183));
  }

  public EntityPlayer getPlayer()
  {
    return this.player;
  }

  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();

    for (int i = 0; i < this.crafters.size(); i++) {
      ICrafting icrafting = (ICrafting)this.crafters.get(i);

      if (this.linketprojektor != this.generatorentity.getLinketProjektor().shortValue()) {
        icrafting.sendProgressBarUpdate(this, 1, this.generatorentity.getLinketProjektor().shortValue());
      }

      if (this.forcepower != this.generatorentity.getStorageAvailablePower()) {
        icrafting.sendProgressBarUpdate(this, 2, this.generatorentity.getStorageAvailablePower() & 0xFFFF);

        icrafting.sendProgressBarUpdate(this, 3, this.generatorentity.getStorageAvailablePower() >>> 16);
      }

      if (this.capacity != this.generatorentity.getPercentageStorageCapacity()) {
        icrafting.sendProgressBarUpdate(this, 4, this.generatorentity.getPercentageStorageCapacity());
      }

      if (this.Powerlinkmode != this.generatorentity.getPowerlinkmode()) {
        icrafting.sendProgressBarUpdate(this, 6, this.generatorentity.getPowerlinkmode());
      }

    }

    this.linketprojektor = this.generatorentity.getLinketProjektor().shortValue();
    this.forcepower = this.generatorentity.getStorageAvailablePower();
    this.capacity = this.generatorentity.getPercentageStorageCapacity();
    this.Powerlinkmode = this.generatorentity.getPowerlinkmode();
  }

  public void updateProgressBar(int i, int j) {
    switch (i)
    {
    case 1:
      this.generatorentity.setLinketprojektor(Short.valueOf((short)j));
      break;
    case 2:
      this.generatorentity.setForcePower(this.generatorentity.getStorageAvailablePower() & 0xFFFF0000 | j);

      break;
    case 3:
      this.generatorentity.setForcePower(this.generatorentity.getStorageAvailablePower() & 0xFFFF | j << 16);

      break;
    case 4:
      this.generatorentity.setCapacity(j);
      break;
    case 6:
      this.generatorentity.setPowerlinkmode(j);
    case 5:
    }
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return this.generatorentity.isUseableByPlayer(entityplayer);
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