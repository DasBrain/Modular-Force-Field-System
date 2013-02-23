package chb.mods.mffs.common.tileentity;

import chb.mods.mffs.api.IMFFS_Wrench;
import chb.mods.mffs.api.ISwitchabel;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.IModularProjector.Slots;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.Linkgrid.Worldlinknet;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.item.ItemCardDataLink;
import chb.mods.mffs.common.item.ItemCardPersonalID;
import chb.mods.mffs.common.item.ItemCardPowerLink;
import chb.mods.mffs.common.item.ItemCardSecurityLink;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.client.NetworkHandlerClient;
import chb.mods.mffs.network.server.NetworkHandlerServer;
import ic2.api.IWrenchable;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.common.ISidedInventory;

public abstract class TileEntityMachines extends TileEntity
  implements INetworkHandlerListener, INetworkHandlerEventListener, ISidedInventory, IMFFS_Wrench, IWrenchable, ISwitchabel
{
  private boolean Active;
  private int Side;
  private short ticker;
  protected boolean init;
  protected String DeviceName;
  protected int DeviceID;
  protected short SwitchModi;
  protected boolean SwitchValue;
  protected Random random = new Random();
  protected ForgeChunkManager.Ticket chunkTicket;

  public TileEntityMachines()
  {
    this.Active = false;
    this.SwitchValue = false;
    this.init = true;
    this.Side = -1;
    this.SwitchModi = 0;
    this.ticker = 0;
    this.DeviceID = 0;
    this.DeviceName = "Please set Name";
  }

  public int getPercentageCapacity()
  {
    return 0;
  }

  public boolean hasPowerSource() {
    return false;
  }

  public abstract TileEntityAdvSecurityStation getLinkedSecurityStation();

  public void onNetworkHandlerEvent(int key, String value)
  {
    switch (key) {
    case 0:
      toogleSwitchModi();
      break;
    case 10:
      setDeviceName("");
      break;
    case 11:
      if (getDeviceName().length() <= 20)
        setDeviceName(getDeviceName() + value); break;
    case 12:
      if (getDeviceName().length() >= 1)
        setDeviceName(getDeviceName().substring(0, getDeviceName().length() - 1));
      break;
    }
  }

  public List getFieldsforUpdate()
  {
    List NetworkedFields = new LinkedList();
    NetworkedFields.clear();

    NetworkedFields.add("Active");
    NetworkedFields.add("Side");
    NetworkedFields.add("DeviceID");
    NetworkedFields.add("DeviceName");
    NetworkedFields.add("SwitchModi");
    NetworkedFields.add("SwitchValue");

    return NetworkedFields;
  }

  public void onNetworkHandlerUpdate(String field)
  {
    this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
  }

  public void updateEntity()
  {
    if ((!this.worldObj.isRemote) && 
      (this.init)) init();

    if ((this.worldObj.isRemote) && 
      (this.DeviceID == 0)) {
      if (getTicker() >= 5 + this.random.nextInt(20)) {
        NetworkHandlerClient.requestInitialData(this, true);
        setTicker((short)0);
      }
      setTicker((short)(getTicker() + 1));
    }
  }

  public void init()
  {
    this.DeviceID = Linkgrid.getWorldMap(this.worldObj).refreshID(this, this.DeviceID);
    if (ModularForceFieldSystem.chunckloader)
      registerChunkLoading();
    this.init = false;
  }

  public short getmaxSwitchModi() {
    return 0;
  }

  public short getminSwitchModi() {
    return 0;
  }

  public void toogleSwitchModi()
  {
    if (getSwitchModi() == getmaxSwitchModi())
    {
      this.SwitchModi = getminSwitchModi();
    }
    else this.SwitchModi = ((short)(this.SwitchModi + 1));

    NetworkHandlerServer.updateTileEntityField(this, "SwitchModi");
  }

  public boolean isRedstoneSignal() {
    if ((this.worldObj.isBlockGettingPowered(this.xCoord, this.yCoord, this.zCoord)) || (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)))
    {
      return true;
    }return false;
  }

  public short getSwitchModi() {
    if (this.SwitchModi < getminSwitchModi())
      this.SwitchModi = getminSwitchModi();
    return this.SwitchModi;
  }

  public boolean getSwitchValue() {
    return this.SwitchValue;
  }

  public boolean isSwitchabel()
  {
    if (getSwitchModi() == 2)
      return true;
    return false;
  }

  public void toggelSwitchValue()
  {
    this.SwitchValue = (!this.SwitchValue);
    NetworkHandlerServer.updateTileEntityField(this, "SwitchValue");
  }

  public void setDeviceName(String DeviceName) {
    this.DeviceName = DeviceName;
    NetworkHandlerServer.updateTileEntityField(this, "DeviceName");
  }

  public int getDeviceID() {
    return this.DeviceID;
  }

  public void setDeviceID(int i) {
    this.DeviceID = i;
  }

  public String getDeviceName() {
    return this.DeviceName;
  }

  public PointXYZ getMaschinePoint()
  {
    return new PointXYZ(this.xCoord, this.yCoord, this.zCoord, this.worldObj);
  }

  public abstract void dropplugins();

  public void dropplugins(int slot, IInventory inventory)
  {
    if (this.worldObj.isRemote) {
      setInventorySlotContents(slot, null);
      return;
    }

    if (inventory.getStackInSlot(slot) != null) {
      if (((inventory.getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink)) || ((inventory.getStackInSlot(slot).getItem() instanceof ItemCardPowerLink)) || ((inventory.getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)) || ((inventory.getStackInSlot(slot).getItem() instanceof ItemCardDataLink)))
      {
        this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1)));
      }
      else
      {
        this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, inventory.getStackInSlot(slot)));
      }

      inventory.setInventorySlotContents(slot, null);
      onInventoryChanged();
    }
  }

  public abstract Container getContainer(InventoryPlayer paramInventoryPlayer);

  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);
    this.Side = nbttagcompound.getInteger("side");
    this.Active = nbttagcompound.getBoolean("active");
    this.SwitchValue = nbttagcompound.getBoolean("SwitchValue");
    this.DeviceID = nbttagcompound.getInteger("DeviceID");
    this.DeviceName = nbttagcompound.getString("DeviceName");
    this.SwitchModi = nbttagcompound.getShort("SwitchModi");
  }

  public void writeToNBT(NBTTagCompound nbttagcompound) {
    super.writeToNBT(nbttagcompound);

    nbttagcompound.setShort("SwitchModi", this.SwitchModi);
    nbttagcompound.setInteger("side", this.Side);
    nbttagcompound.setBoolean("active", this.Active);
    nbttagcompound.setBoolean("SwitchValue", this.SwitchValue);
    nbttagcompound.setInteger("DeviceID", this.DeviceID);
    nbttagcompound.setString("DeviceName", this.DeviceName);
  }

  public boolean wrenchCanManipulate(EntityPlayer entityPlayer, int side)
  {
    if (!SecurityHelper.isAccessGranted(this, entityPlayer, this.worldObj, SecurityRight.EB)) {
      return false;
    }
    return true;
  }

  public short getTicker() {
    return this.ticker;
  }

  public void setTicker(short ticker) {
    this.ticker = ticker;
  }

  public void setSide(int i) {
    this.Side = i;
    NetworkHandlerServer.updateTileEntityField(this, "Side");
  }

  public boolean isActive() {
    return this.Active;
  }

  public void setActive(boolean flag) {
    this.Active = flag;
    NetworkHandlerServer.updateTileEntityField(this, "Active");
  }

  public int getSide()
  {
    return this.Side;
  }

  public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
  {
    if (side == getFacing()) return false;
    if ((this instanceof TileEntitySecStorage)) return false;
    if ((this instanceof TileEntityAdvSecurityStation)) return false;
    if (this.Active) return false;

    return wrenchCanManipulate(entityPlayer, side);
  }

  public short getFacing()
  {
    return (short)getSide();
  }

  public void setFacing(short facing)
  {
    setSide(facing);
  }

  public boolean wrenchCanRemove(EntityPlayer entityPlayer)
  {
    if (this.Active) return false;
    return wrenchCanManipulate(entityPlayer, 0);
  }

  public float getWrenchDropRate()
  {
    return 1.0F;
  }

  public void forceChunkLoading(ForgeChunkManager.Ticket ticket) {
    if (this.chunkTicket == null)
    {
      this.chunkTicket = ticket;
    }
    ChunkCoordIntPair Chunk = new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4);
    ForgeChunkManager.forceChunk(ticket, Chunk);
  }

  protected void registerChunkLoading()
  {
    if (this.chunkTicket == null)
    {
      this.chunkTicket = ForgeChunkManager.requestTicket(ModularForceFieldSystem.instance, this.worldObj, ForgeChunkManager.Type.NORMAL);
    }
    if (this.chunkTicket == null)
    {
      System.out.println("[ModularForceFieldSystem]no free Chunkloaders available");
      return;
    }

    this.chunkTicket.getModData().setInteger("MaschineX", this.xCoord);
    this.chunkTicket.getModData().setInteger("MaschineY", this.yCoord);
    this.chunkTicket.getModData().setInteger("MaschineZ", this.zCoord);
    ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));

    forceChunkLoading(this.chunkTicket);
  }

  public void invalidate()
  {
    ForgeChunkManager.releaseTicket(this.chunkTicket);
    super.invalidate();
  }

  public abstract boolean isItemValid(ItemStack paramItemStack, int paramInt);

  public abstract int getSlotStackLimit(int paramInt);

  public void openChest()
  {
  }

  public void closeChest()
  {
  }

  public boolean isUseableByPlayer(EntityPlayer entityplayer)
  {
    if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
      return false;
    }
    return entityplayer.getDistance(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
  }

  public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
  {
    return new ItemStack(net.minecraft.block.Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)]);
  }

  public ItemStack getStackInSlotOnClosing(int var1)
  {
    return null;
  }

  public int getInventoryStackLimit()
  {
    return 64;
  }

  public int countItemsInSlot(IModularProjector.Slots slt) {
    if (getStackInSlot(slt.slot) != null)
      return getStackInSlot(slt.slot).stackSize;
    return 0;
  }
}