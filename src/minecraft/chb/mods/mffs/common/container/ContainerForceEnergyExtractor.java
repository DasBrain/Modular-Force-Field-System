package chb.mods.mffs.common.container;

import chb.mods.mffs.common.SlotHelper;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerForceEnergyExtractor extends Container
{
  private TileEntityExtractor ForceEnergyExtractor;
  private EntityPlayer player;
  private int WorkCylce;
  private int workdone;
  private int ForceEnergybuffer;

  public ContainerForceEnergyExtractor(EntityPlayer player, TileEntityExtractor tileentity)
  {
    this.ForceEnergyExtractor = tileentity;
    this.player = player;
    this.WorkCylce = -1;
    this.workdone = -1;
    this.ForceEnergybuffer = -1;

    addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 0, 82, 26));
    addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 1, 145, 40));
    addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 2, 20, 66));
    addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 3, 39, 66));
    addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 4, 112, 26));

    for (int var3 = 0; var3 < 3; var3++) {
      for (int var4 = 0; var4 < 9; var4++) {
        addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 104 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 9; var3++)
      addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 162));
  }

  public EntityPlayer getPlayer()
  {
    return this.player;
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return this.ForceEnergyExtractor.isUseableByPlayer(entityplayer);
  }

  public ItemStack transferStackInSlot(EntityPlayer p, int i) {
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

  public void updateProgressBar(int i, int j) {
    switch (i) {
    case 0:
      this.ForceEnergyExtractor.setWorkdone(j);
      break;
    case 1:
      this.ForceEnergyExtractor.setWorkCylce(j);
      break;
    case 2:
      this.ForceEnergyExtractor.setForceEnergybuffer(this.ForceEnergyExtractor.getForceEnergybuffer() & 0xFFFF0000 | j);

      break;
    case 3:
      this.ForceEnergyExtractor.setForceEnergybuffer(this.ForceEnergyExtractor.getForceEnergybuffer() & 0xFFFF | j << 16);
    }
  }

  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();

    for (int i = 0; i < this.crafters.size(); i++) {
      ICrafting icrafting = (ICrafting)this.crafters.get(i);

      if (this.workdone != this.ForceEnergyExtractor.getWorkdone()) {
        icrafting.sendProgressBarUpdate(this, 0, this.ForceEnergyExtractor.getWorkdone());
      }

      if (this.WorkCylce != this.ForceEnergyExtractor.getWorkCylce()) {
        icrafting.sendProgressBarUpdate(this, 1, this.ForceEnergyExtractor.getWorkCylce());
      }

      if (this.ForceEnergybuffer != this.ForceEnergyExtractor.getForceEnergybuffer()) {
        icrafting.sendProgressBarUpdate(this, 2, this.ForceEnergyExtractor.getForceEnergybuffer() & 0xFFFF);

        icrafting.sendProgressBarUpdate(this, 3, this.ForceEnergyExtractor.getForceEnergybuffer() >>> 16);
      }

    }

    this.workdone = this.ForceEnergyExtractor.getWorkdone();
    this.WorkCylce = this.ForceEnergyExtractor.getWorkCylce();
    this.ForceEnergybuffer = this.ForceEnergyExtractor.getForceEnergybuffer();
  }
}