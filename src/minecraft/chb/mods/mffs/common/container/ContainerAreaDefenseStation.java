package chb.mods.mffs.common.container;

import chb.mods.mffs.common.SlotHelper;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAreaDefenseStation extends Container
{
  private TileEntityAreaDefenseStation defstation;
  private int capacity;
  private int SwitchTyp;
  private int contratyp;
  private int actionmode;
  private int scanmode;
  private EntityPlayer player;

  public ContainerAreaDefenseStation(EntityPlayer player, TileEntityAreaDefenseStation tileentity)
  {
    this.capacity = -1;
    this.SwitchTyp = -1;
    this.contratyp = -1;
    this.actionmode = -1;
    this.scanmode = -1;

    this.defstation = tileentity;
    this.player = player;

    addSlotToContainer(new SlotHelper(this.defstation, 0, 13, 27));
    addSlotToContainer(new SlotHelper(this.defstation, 1, 97, 27));

    addSlotToContainer(new SlotHelper(this.defstation, 2, 14, 51));
    addSlotToContainer(new SlotHelper(this.defstation, 3, 14, 88));

    for (int var3 = 0; var3 < 2; var3++) {
      for (int var4 = 0; var4 < 4; var4++) {
        addSlotToContainer(new SlotHelper(this.defstation, var4 + var3 * 4 + 5, 176 + var4 * 18, 26 + var3 * 18));
      }

    }

    for (var3 = 0; var3 < 5; var3++) {
      for (int var4 = 0; var4 < 4; var4++) {
        addSlotToContainer(new SlotHelper(this.defstation, var4 + var3 * 4 + 15, 176 + var4 * 18, 98 + var3 * 18));
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

  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();

    for (int i = 0; i < this.crafters.size(); i++) {
      ICrafting icrafting = (ICrafting)this.crafters.get(i);

      if (this.contratyp != this.defstation.getcontratyp()) {
        icrafting.sendProgressBarUpdate(this, 1, this.defstation.getcontratyp());
      }

      if (this.actionmode != this.defstation.getActionmode()) {
        icrafting.sendProgressBarUpdate(this, 2, this.defstation.getActionmode());
      }

      if (this.scanmode != this.defstation.getScanmode()) {
        icrafting.sendProgressBarUpdate(this, 3, this.defstation.getScanmode());
      }

      if (this.capacity != this.defstation.getPercentageCapacity()) {
        icrafting.sendProgressBarUpdate(this, 4, this.defstation.getPercentageCapacity());
      }

    }

    this.scanmode = this.defstation.getScanmode();
    this.actionmode = this.defstation.getActionmode();
    this.contratyp = this.defstation.getcontratyp();
    this.capacity = this.defstation.getPercentageCapacity();
  }

  public void updateProgressBar(int i, int j) {
    switch (i)
    {
    case 1:
      this.defstation.setcontratyp(j);
      break;
    case 2:
      this.defstation.setActionmode(j);
      break;
    case 3:
      this.defstation.setScanmode(j);
      break;
    case 4:
      this.defstation.setCapacity(j);
    }
  }

  public boolean canInteractWith(EntityPlayer entityplayer)
  {
    return this.defstation.isUseableByPlayer(entityplayer);
  }

  public ItemStack transferStackInSlot(EntityPlayer p, int i)
  {
    return null;
  }
}