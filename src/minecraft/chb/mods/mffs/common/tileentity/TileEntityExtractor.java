package chb.mods.mffs.common.tileentity;

import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import chb.mods.mffs.api.IPowerLinkItem;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.Linkgrid.Worldlinknet;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.container.ContainerForceEnergyExtractor;
import chb.mods.mffs.common.item.ItemCapacitorUpgradeCapacity;
import chb.mods.mffs.common.item.ItemExtractorUpgradeBooster;
import chb.mods.mffs.common.item.ItemForcicium;
import chb.mods.mffs.common.item.ItemForcicumCell;
import chb.mods.mffs.network.server.NetworkHandlerServer;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import universalelectricity.core.electricity.ElectricityConnections;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.implement.IConductor;
import universalelectricity.core.vector.Vector3;

public class TileEntityExtractor extends TileEntityFEPoweredMachine
  implements IPowerReceptor, IEnergySink
{
  private ItemStack[] inventory;
  private int workmode = 0;
  protected int WorkEnergy;
  protected int MaxWorkEnergy;
  private int ForceEnergybuffer;
  private int MaxForceEnergyBuffer;
  private int WorkCylce;
  private int workTicker;
  private int workdone;
  private int maxworkcylce;
  private int capacity;
  private IPowerProvider powerProvider;
  private boolean addedToEnergyNet;

  public TileEntityExtractor()
  {
    this.inventory = new ItemStack[5];
    this.WorkEnergy = 0;
    this.MaxWorkEnergy = 4000;
    this.ForceEnergybuffer = 0;
    this.MaxForceEnergyBuffer = 1000000;
    this.WorkCylce = 0;
    this.workTicker = 20;
    this.maxworkcylce = 125;
    this.capacity = 0;
    this.addedToEnergyNet = false;

    if (ModularForceFieldSystem.buildcraftfound.booleanValue()) {
      this.powerProvider = PowerFramework.currentFramework.createPowerProvider();
      this.powerProvider.configure(10, 2, (int)(getMaxWorkEnergy() / 2.5D), (int)(getMaxWorkEnergy() / 2.5D), (int)(getMaxWorkEnergy() / 2.5D));
    }
  }

  public void setSide(int i)
  {
    super.setSide(i);
    setUEwireConnection();
  }

  public int getCapacity() {
    return this.capacity;
  }

  public void setCapacity(int Capacity) {
    if (this.capacity != Capacity)
    {
      this.capacity = Capacity;
      NetworkHandlerServer.updateTileEntityField(this, "capacity");
    }
  }

  public int getMaxworkcylce() {
    return this.maxworkcylce;
  }

  public void setMaxworkcylce(int maxworkcylce) {
    this.maxworkcylce = maxworkcylce;
  }

  public int getWorkdone() {
    return this.workdone;
  }

  public void setWorkdone(int workdone) {
    if (this.workdone != workdone) {
      this.workdone = workdone;
      NetworkHandlerServer.updateTileEntityField(this, "workdone");
    }
  }

  public int getWorkTicker() {
    return this.workTicker;
  }

  public void setWorkTicker(int workTicker) {
    this.workTicker = workTicker;
  }

  public int getMaxForceEnergyBuffer()
  {
    return this.MaxForceEnergyBuffer;
  }

  public void setMaxForceEnergyBuffer(int maxForceEnergyBuffer) {
    this.MaxForceEnergyBuffer = maxForceEnergyBuffer;
  }

  public int getForceEnergybuffer() {
    return this.ForceEnergybuffer;
  }

  public void setForceEnergybuffer(int forceEnergybuffer) {
    this.ForceEnergybuffer = forceEnergybuffer;
  }

  public void setWorkCylce(int i)
  {
    if (this.WorkCylce != i) {
      this.WorkCylce = i;
      NetworkHandlerServer.updateTileEntityField(this, "WorkCylce");
    }
  }

  public int getWorkCylce() {
    return this.WorkCylce;
  }

  public int getWorkEnergy() {
    return this.WorkEnergy;
  }

  public void setWorkEnergy(int workEnergy) {
    this.WorkEnergy = workEnergy;
  }

  public int getMaxWorkEnergy() {
    return this.MaxWorkEnergy;
  }

  public void setMaxWorkEnergy(int maxWorkEnergy) {
    this.MaxWorkEnergy = maxWorkEnergy;
  }

  public void dropplugins()
  {
    for (int a = 0; a < this.inventory.length; a++)
      dropplugins(a, this);
  }

  public boolean isUseableByPlayer(EntityPlayer entityplayer)
  {
    if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
      return false;
    }
    return entityplayer.getDistance(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
  }

  public void checkslots(boolean init)
  {
    if (getStackInSlot(2) != null) {
      if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap)
        setMaxForceEnergyBuffer(1000000 + getStackInSlot(2).stackSize * 100000);
      else
        setMaxForceEnergyBuffer(1000000);
    }
    else {
      setMaxForceEnergyBuffer(1000000);
    }

    if (getStackInSlot(3) != null) {
      if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSitemupgradeexctractorboost)
        setWorkTicker(20 - getStackInSlot(3).stackSize);
      else
        setWorkTicker(20);
    }
    else {
      setWorkTicker(20);
    }

    if (getStackInSlot(4) != null) {
      if (getStackInSlot(4).getItem() == ModularForceFieldSystem.MFFSitemForcicumCell) {
        this.workmode = 1;
        setMaxWorkEnergy(200000);
      }
    } else {
      this.workmode = 0;
      setMaxWorkEnergy(4000);
    }
  }

  private boolean hasPowertoConvert()
  {
    if (this.WorkEnergy >= this.MaxWorkEnergy - 1)
    {
      setWorkEnergy(0);
      return true;
    }
    return false;
  }

  private boolean hasfreeForceEnergyStorage()
  {
    if (this.MaxForceEnergyBuffer > this.ForceEnergybuffer)
      return true;
    return false;
  }

  private boolean hasStufftoConvert()
  {
    if (this.WorkCylce > 0)
    {
      return true;
    }
    if (ModularForceFieldSystem.adventuremap.booleanValue())
    {
      setMaxworkcylce(ModularForceFieldSystem.ForceciumCellWorkCylce);
      setWorkCylce(getMaxworkcylce());
      return true;
    }

    if (getStackInSlot(0) != null) {
      if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemForcicium) {
        setMaxworkcylce(ModularForceFieldSystem.ForceciumWorkCylce);
        setWorkCylce(getMaxworkcylce());
        decrStackSize(0, 1);
        return true;
      }

      if ((getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemForcicumCell) && 
        (((ItemForcicumCell)getStackInSlot(0).getItem()).useForcecium(1, getStackInSlot(0))))
      {
        setMaxworkcylce(ModularForceFieldSystem.ForceciumCellWorkCylce);
        setWorkCylce(getMaxworkcylce());
        return true;
      }

    }

    return false;
  }

  public void transferForceEnergy()
  {
    if (getForceEnergybuffer() > 0)
    {
      if (hasPowerSource())
      {
        int PowerTransferrate = getMaximumPower() / 120;
        int freeAmount = getMaximumPower() - getAvailablePower();

        if (getForceEnergybuffer() > freeAmount)
        {
          if (freeAmount > PowerTransferrate)
          {
            emitPower(PowerTransferrate, false);
            setForceEnergybuffer(getForceEnergybuffer() - PowerTransferrate);
          }
          else {
            emitPower(freeAmount, false);
            setForceEnergybuffer(getForceEnergybuffer() - freeAmount);
          }
        }
        else if (freeAmount > getForceEnergybuffer())
        {
          emitPower(getForceEnergybuffer(), false);
          setForceEnergybuffer(getForceEnergybuffer() - getForceEnergybuffer());
        } else {
          emitPower(freeAmount, false);
          setForceEnergybuffer(getForceEnergybuffer() - freeAmount);
        }
      }
    }
  }

  public short getmaxSwitchModi()
  {
    return 3;
  }

  public short getminSwitchModi() {
    return 1;
  }

  public void updateEntity() {
    if (!this.worldObj.isRemote)
    {
      if (this.init) {
        checkslots(true);
        setUEwireConnection();
      }

      if ((!this.addedToEnergyNet) && (ModularForceFieldSystem.ic2found.booleanValue())) {
        MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        this.addedToEnergyNet = true;
      }

      if ((getSwitchModi() == 1) && 
        (!getSwitchValue()) && (isRedstoneSignal())) {
        toggelSwitchValue();
      }
      if ((getSwitchModi() == 1) && 
        (getSwitchValue()) && (!isRedstoneSignal())) {
        toggelSwitchValue();
      }

      if ((!isActive()) && (getSwitchValue())) {
        setActive(true);
      }
      if ((isActive()) && (!getSwitchValue())) {
        setActive(false);
      }

      if (isActive()) {
        if (ModularForceFieldSystem.buildcraftfound.booleanValue()) {
          converMJtoWorkEnergy();
        }
        if (ModularForceFieldSystem.uefound.booleanValue()) {
          converUEtoWorkEnergy();
        }
      }

      if (getTicker() >= getWorkTicker()) {
        checkslots(false);

        if ((this.workmode == 0) && (isActive()))
        {
          if (getWorkdone() != getWorkEnergy() * 100 / getMaxWorkEnergy()) {
            setWorkdone(getWorkEnergy() * 100 / getMaxWorkEnergy());
          }
          if (getWorkdone() > 100) setWorkdone(100);

          if (getCapacity() != getForceEnergybuffer() * 100 / getMaxForceEnergyBuffer()) {
            setCapacity(getForceEnergybuffer() * 100 / getMaxForceEnergyBuffer());
          }

          if ((hasfreeForceEnergyStorage()) && (hasStufftoConvert()))
          {
            if (hasPowertoConvert()) {
              setWorkCylce(getWorkCylce() - 1);
              setForceEnergybuffer(getForceEnergybuffer() + ModularForceFieldSystem.ExtractorPassForceEnergyGenerate);
            }

          }

          transferForceEnergy();

          setTicker((short)0);
        }

        if ((this.workmode == 1) && (isActive()))
        {
          if (getWorkdone() != getWorkEnergy() * 100 / getMaxWorkEnergy()) {
            setWorkdone(getWorkEnergy() * 100 / getMaxWorkEnergy());
          }
          if (((ItemForcicumCell)getStackInSlot(4).getItem()).getForceciumlevel(getStackInSlot(4)) < ((ItemForcicumCell)getStackInSlot(4).getItem()).getMaxForceciumlevel())
          {
            if ((hasPowertoConvert()) && (isActive())) {
              ((ItemForcicumCell)getStackInSlot(4).getItem()).setForceciumlevel(getStackInSlot(4), ((ItemForcicumCell)getStackInSlot(4).getItem()).getForceciumlevel(getStackInSlot(4)) + 1);
            }
          }

          setTicker((short)0);
        }
      }

      setTicker((short)(getTicker() + 1));
    }
    super.updateEntity();
  }

  public Container getContainer(InventoryPlayer inventoryplayer)
  {
    return new ContainerForceEnergyExtractor(inventoryplayer.player, this);
  }

  public void readFromNBT(NBTTagCompound nbttagcompound) {
    super.readFromNBT(nbttagcompound);

    this.ForceEnergybuffer = nbttagcompound.getInteger("ForceEnergybuffer");
    this.WorkEnergy = nbttagcompound.getInteger("WorkEnergy");
    this.WorkCylce = nbttagcompound.getInteger("WorkCylce");

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

    nbttagcompound.setInteger("WorkCylce", this.WorkCylce);
    nbttagcompound.setInteger("WorkEnergy", this.WorkEnergy);
    nbttagcompound.setInteger("ForceEnergybuffer", this.ForceEnergybuffer);

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

  public ItemStack getStackInSlot(int i) {
    return this.inventory[i];
  }

  public String getInvName() {
    return "Extractor";
  }

  public int getSizeInventory()
  {
    return this.inventory.length;
  }

  public void setInventorySlotContents(int i, ItemStack itemstack) {
    this.inventory[i] = itemstack;
    if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
      itemstack.stackSize = getInventoryStackLimit();
  }

  public ItemStack decrStackSize(int i, int j)
  {
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
    return 0;
  }

  public int getSizeInventorySide(ForgeDirection side)
  {
    return 1;
  }

  public List getFieldsforUpdate()
  {
    List NetworkedFields = new LinkedList();
    NetworkedFields.clear();

    NetworkedFields.addAll(super.getFieldsforUpdate());
    NetworkedFields.add("capacity");
    NetworkedFields.add("WorkCylce");
    NetworkedFields.add("WorkEnergy");
    NetworkedFields.add("workdone");

    return NetworkedFields;
  }

  public boolean isItemValid(ItemStack par1ItemStack, int Slot)
  {
    switch (Slot)
    {
    case 0:
      if ((((par1ItemStack.getItem() instanceof ItemForcicium)) || ((par1ItemStack.getItem() instanceof ItemForcicumCell))) && (getStackInSlot(4) == null)) {
        return true;
      }
      break;
    case 1:
      if ((par1ItemStack.getItem() instanceof IPowerLinkItem)) {
        return true;
      }
      break;
    case 2:
      if ((par1ItemStack.getItem() instanceof ItemCapacitorUpgradeCapacity)) {
        return true;
      }
      break;
    case 3:
      if ((par1ItemStack.getItem() instanceof ItemExtractorUpgradeBooster)) {
        return true;
      }
      break;
    case 4:
      if (((par1ItemStack.getItem() instanceof ItemForcicumCell)) && (getStackInSlot(0) == null))
        return true;
      break;
    }
    return false;
  }

  public int getSlotStackLimit(int Slot)
  {
    switch (Slot) {
    case 0:
      return 64;
    case 1:
      return 1;
    case 2:
      return 9;
    case 3:
      return 19;
    case 4:
      return 1;
    }
    return 1;
  }

  public int demandsEnergy()
  {
    if (!isActive())
      return 0;
    return getMaxWorkEnergy() - getWorkEnergy();
  }

  public int injectEnergy(Direction directionFrom, int amount)
  {
    int freespace = getMaxWorkEnergy() - getWorkEnergy();

    if (freespace >= amount) {
      setWorkEnergy(getWorkEnergy() + amount);
      return 0;
    }

    setWorkEnergy(getMaxWorkEnergy());
    return amount - freespace;
  }

  public void invalidate()
  {
    if (this.addedToEnergyNet)
    {
      MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
      this.addedToEnergyNet = false;
    }

    Linkgrid.getWorldMap(this.worldObj).getExtractor().remove(Integer.valueOf(getDeviceID()));

    super.invalidate();
  }

  public boolean isAddedToEnergyNet()
  {
    return this.addedToEnergyNet;
  }

  public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction)
  {
    return true;
  }

  public void converMJtoWorkEnergy() {
    if (getWorkEnergy() < getMaxWorkEnergy())
    {
      float use = this.powerProvider.useEnergy(1.0F, (float)(getMaxWorkEnergy() - getWorkEnergy() / 2.5D), true);

      if (getWorkEnergy() + use * 2.5D > getMaxWorkEnergy())
      {
        setWorkEnergy(getMaxWorkEnergy());
      }
      else setWorkEnergy((int)(getWorkEnergy() + use * 2.5D));
    }
  }

  public void setUEwireConnection()
  {
    if (ModularForceFieldSystem.uefound.booleanValue())
    {
      ElectricityConnections.registerConnector(this, EnumSet.of(ForgeDirection.getOrientation(getFacing()).getOpposite()));
      this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
    }
  }

  public void setPowerProvider(IPowerProvider provider)
  {
    this.powerProvider = provider;
  }

  public IPowerProvider getPowerProvider()
  {
    return this.powerProvider;
  }

  public void doWork()
  {
  }

  public int powerRequest() {
    double workEnergyinMJ = getWorkEnergy() / 2.5D;
    double MaxWorkEnergyinMj = getMaxWorkEnergy() / 2.5D;

    return (int)Math.round(MaxWorkEnergyinMj - workEnergyinMJ);
  }

  public void converUEtoWorkEnergy()
  {
    ForgeDirection inputDirection = ForgeDirection.getOrientation(getFacing()).getOpposite();

    TileEntity inputTile = Vector3.getTileEntityFromSide(this.worldObj, new Vector3(this), inputDirection);

    if (inputTile != null)
    {
      if ((inputTile instanceof IConductor))
      {
        if (getWorkEnergy() >= getMaxWorkEnergy())
        {
          ((IConductor)inputTile).getNetwork().stopRequesting(this);
        }
        else
        {
          ((IConductor)inputTile).getNetwork().startRequesting(this, 10.0D, 120.0D);

          setWorkEnergy((int)(getWorkEnergy() + ((IConductor)inputTile).getNetwork().consumeElectricity(this).getWatts() / 50.0D));
        }
      }
    }
  }

  public ItemStack getPowerLinkStack()
  {
    return getStackInSlot(getPowerlinkSlot());
  }

  public int getPowerlinkSlot()
  {
    return 1;
  }

  public int getMaxSafeInput()
  {
    return 2048;
  }

  public TileEntityAdvSecurityStation getLinkedSecurityStation()
  {
    TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(this.worldObj).getCapacitor().get(Integer.valueOf(getPowerSourceID()));
    if (cap != null)
    {
      TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
      if (sec != null)
      {
        return sec;
      }
    }

    return null;
  }
}