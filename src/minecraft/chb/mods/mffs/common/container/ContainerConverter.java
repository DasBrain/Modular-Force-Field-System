package chb.mods.mffs.common.container;

import chb.mods.mffs.common.SlotHelper;
import chb.mods.mffs.common.tileentity.TileEntityConverter;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerConverter extends Container
{
  private int linkPower;
  private int capacity;
  private EntityPlayer player;
  private TileEntityConverter Convertor;
  private int IC_Outputpacketsize;
  private int IC_Outputpacketamount;
  private int IC_Output;
  private int UE_Outputvoltage;
  private int UE_Outputamp;
  private int UE_Output;

  public ContainerConverter(EntityPlayer player, TileEntityConverter tileentity)
  {
    this.Convertor = tileentity;
    this.player = player;
    this.linkPower = -1;
    this.capacity = -1;
    this.IC_Outputpacketamount = -1;
    this.IC_Outputpacketsize = -1;
    this.IC_Output = -1;
    this.UE_Outputvoltage = -1;
    this.UE_Outputamp = -1;
    this.UE_Output = -1;

    addSlotToContainer(new SlotHelper(this.Convertor, 0, 44, 28));

    for (int var3 = 0; var3 < 3; var3++) {
      for (int var4 = 0; var4 < 9; var4++) {
        addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 50 + var4 * 18, 133 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 9; var3++)
      addSlotToContainer(new Slot(player.inventory, var3, 50 + var3 * 18, 191));
  }

  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();

    for (int i = 0; i < this.crafters.size(); i++) {
      ICrafting icrafting = (ICrafting)this.crafters.get(i);

      if (this.linkPower != this.Convertor.getLinkPower()) {
        icrafting.sendProgressBarUpdate(this, 0, this.Convertor.getLinkPower() & 0xFFFF);
        icrafting.sendProgressBarUpdate(this, 1, this.Convertor.getLinkPower() >>> 16);
      }

      if (this.capacity != this.Convertor.getPercentageCapacity()) {
        icrafting.sendProgressBarUpdate(this, 3, this.Convertor.getPercentageCapacity());
      }
      if (this.IC_Outputpacketamount != this.Convertor.getIC_Outputpacketamount()) {
        icrafting.sendProgressBarUpdate(this, 5, this.Convertor.getIC_Outputpacketamount());
      }
      if (this.IC_Outputpacketsize != this.Convertor.getIC_Outputpacketsize()) {
        icrafting.sendProgressBarUpdate(this, 4, this.Convertor.getIC_Outputpacketsize());
      }
      if (this.IC_Output != this.Convertor.getIC_Output()) {
        icrafting.sendProgressBarUpdate(this, 6, this.Convertor.getIC_Output());
      }
      if (this.UE_Output != this.Convertor.getUE_Output()) {
        icrafting.sendProgressBarUpdate(this, 7, this.Convertor.getUE_Output());
      }
      if (this.UE_Outputvoltage != this.Convertor.getUE_Outputvoltage()) {
        icrafting.sendProgressBarUpdate(this, 8, this.Convertor.getUE_Outputvoltage());
      }
      if (this.UE_Outputamp != this.Convertor.getUE_Outputamp()) {
        icrafting.sendProgressBarUpdate(this, 9, this.Convertor.getUE_Outputamp());
      }
    }

    this.linkPower = this.Convertor.getLinkPower();
    this.capacity = this.Convertor.getPercentageCapacity();
    this.IC_Outputpacketamount = this.Convertor.getIC_Outputpacketamount();
    this.IC_Outputpacketsize = this.Convertor.getIC_Outputpacketsize();
    this.IC_Output = this.Convertor.getIC_Output();
    this.UE_Outputvoltage = this.Convertor.getUE_Outputvoltage();
    this.UE_Outputamp = this.Convertor.getUE_Outputamp();
    this.UE_Output = this.Convertor.getUE_Output();
  }

  public void updateProgressBar(int i, int j) {
    switch (i) {
    case 0:
      this.Convertor.setLinkPower(this.Convertor.getLinkPower() & 0xFFFF0000 | j);
      break;
    case 1:
      this.Convertor.setLinkPower(this.Convertor.getLinkPower() & 0xFFFF | j << 16);
      break;
    case 3:
      this.Convertor.setCapacity(j);
      break;
    case 4:
      this.Convertor.setIC_Outputpacketsize(j);
      break;
    case 5:
      this.Convertor.setIC_Outputpacketamount(j);
      break;
    case 6:
      this.Convertor.setIC_Output(j);
      break;
    case 7:
      this.Convertor.setUE_Output(j);
      break;
    case 8:
      this.Convertor.setUE_Outputvoltage(j);
      break;
    case 9:
      this.Convertor.setUE_Outputamp(j);
    case 2:
    }
  }

  public EntityPlayer getPlayer()
  {
    return this.player;
  }

  public boolean canInteractWith(EntityPlayer entityplayer) {
    return this.Convertor.isUseableByPlayer(entityplayer);
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