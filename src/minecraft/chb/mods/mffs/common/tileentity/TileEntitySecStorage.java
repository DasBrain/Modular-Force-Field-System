package chb.mods.mffs.common.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.container.ContainerSecStorage;
import chb.mods.mffs.common.item.ItemCardSecurityLink;

public class TileEntitySecStorage extends TileEntityMachines
  implements ISidedInventory, IInventory
{
  private ItemStack[] inventory;

  public TileEntitySecStorage()
  {
    this.inventory = new ItemStack[60];
  }

  public void dropplugins()
  {
    for (int a = 0; a < this.inventory.length; a++)
      dropplugins(a, this);
  }

  public TileEntityAdvSecurityStation getLinkedSecurityStation()
  {
    return ItemCardSecurityLink.getLinkedSecurityStation(this, 0, this.worldObj);
  }

  public void invalidate()
  {
    Linkgrid.getWorldMap(this.worldObj).getSecStorage().remove(Integer.valueOf(getDeviceID()));
    super.invalidate();
  }

  public int getSecStation_ID()
  {
    TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
    if (sec != null)
      return sec.getDeviceID();
    return 0;
  }

  public short getmaxSwitchModi()
  {
    return 3;
  }

  public short getminSwitchModi() {
    return 2;
  }

  public int getfreeslotcount()
  {
    int count = 0;

    for (int a = 1; a < this.inventory.length; a++) {
      if (getStackInSlot(a) == null) {
        count++;
      }
    }
    return count;
  }

  public void updateEntity()
  {
    if (!this.worldObj.isRemote) {
      if ((getLinkedSecurityStation() != null) && (!isActive()) && (getSwitchValue()))
        setActive(true);
      if (((getLinkedSecurityStation() == null) || (!getSwitchValue())) && (isActive()))
        setActive(false);
    }
    super.updateEntity();
  }

  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readFromNBT(nbttagcompound);
    NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
    this.inventory = new ItemStack[getSizeInventory()];
    for (int i = 0; i < nbttaglist.tagCount(); i++) {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);

      byte byte0 = nbttagcompound1.getByte("Slot");
      if ((byte0 >= 0) && (byte0 < this.inventory.length))
        this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
    }
  }

  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeToNBT(nbttagcompound);
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.inventory.length; i++) {
      if (this.inventory[i] != null) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        this.inventory[i].writeToNBT(nbttagcompound1);
        nbttaglist.appendTag(nbttagcompound1);
      }
    }

    nbttagcompound.setTag("Items", nbttaglist);
  }

  public ItemStack getStackInSlot(int i)
  {
    return this.inventory[i];
  }

  public String getInvName() {
    return "SecStation";
  }

  public int getSizeInventory()
  {
    return this.inventory.length;
  }

  public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
  {
    this.inventory[par1] = par2ItemStack;

    if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
    {
      par2ItemStack.stackSize = getInventoryStackLimit();
    }

    onInventoryChanged();
  }

  public ItemStack decrStackSize(int i, int j) {
    if (this.inventory[i] != null) {
      if (this.inventory[i].stackSize <= j) {
        ItemStack itemstack = this.inventory[i];
        this.inventory[i] = null;
        return itemstack;
      }
      ItemStack itemstack1 = this.inventory[i].splitStack(j);
      if (this.inventory[i].stackSize == 0) {
        this.inventory[i] = null;
      }
      return itemstack1;
    }
    return null;
  }

  public int getStartInventorySide(ForgeDirection side)
  {
    if (isActive())
      return 0;
    return 1;
  }

  public int getSizeInventorySide(ForgeDirection side)
  {
    if (isActive())
      return 0;
    return 54;
  }

  public Container getContainer(InventoryPlayer inventoryplayer)
  {
    return new ContainerSecStorage(inventoryplayer.player, this);
  }

  public boolean isItemValid(ItemStack par1ItemStack, int Slot)
  {
    switch (Slot) {
    case 0:
      if (!(par1ItemStack.getItem() instanceof ItemCardSecurityLink)) {
        return false;
      }
      break;
    }
    return true;
  }

  public int getSlotStackLimit(int slt)
  {
    if (slt == 0)
      return 1;
    return 64;
  }
}