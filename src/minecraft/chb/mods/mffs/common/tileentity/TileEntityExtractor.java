/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import buildcraft.api.power.IPowerProvider;
/*     */ import buildcraft.api.power.IPowerReceptor;
/*     */ import buildcraft.api.power.PowerFramework;
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.container.ContainerForceEnergyExtractor;
/*     */ import chb.mods.mffs.common.item.ItemCapacitorUpgradeCapacity;
/*     */ import chb.mods.mffs.common.item.ItemExtractorUpgradeBooster;
/*     */ import chb.mods.mffs.common.item.ItemForcicium;
/*     */ import chb.mods.mffs.common.item.ItemForcicumCell;
/*     */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.EventBus;
/*     */ import universalelectricity.core.electricity.ElectricityConnections;
/*     */ import universalelectricity.core.electricity.ElectricityNetwork;
/*     */ import universalelectricity.core.electricity.ElectricityPack;
/*     */ import universalelectricity.core.implement.IConductor;
/*     */ import universalelectricity.core.vector.Vector3;
/*     */ 
/*     */ public class TileEntityExtractor extends TileEntityFEPoweredMachine
/*     */   implements IPowerReceptor, IEnergySink
/*     */ {
/*     */   private ItemStack[] inventory;
/*  65 */   private int workmode = 0;
/*     */   protected int WorkEnergy;
/*     */   protected int MaxWorkEnergy;
/*     */   private int ForceEnergybuffer;
/*     */   private int MaxForceEnergyBuffer;
/*     */   private int WorkCylce;
/*     */   private int workTicker;
/*     */   private int workdone;
/*     */   private int maxworkcylce;
/*     */   private int capacity;
/*     */   private IPowerProvider powerProvider;
/*     */   private boolean addedToEnergyNet;
/*     */ 
/*     */   public TileEntityExtractor()
/*     */   {
/*  80 */     this.inventory = new ItemStack[5];
/*  81 */     this.WorkEnergy = 0;
/*  82 */     this.MaxWorkEnergy = 4000;
/*  83 */     this.ForceEnergybuffer = 0;
/*  84 */     this.MaxForceEnergyBuffer = 1000000;
/*  85 */     this.WorkCylce = 0;
/*  86 */     this.workTicker = 20;
/*  87 */     this.maxworkcylce = 125;
/*  88 */     this.capacity = 0;
/*  89 */     this.addedToEnergyNet = false;
/*     */ 
/*  92 */     if (ModularForceFieldSystem.buildcraftfound.booleanValue()) {
/*  93 */       this.powerProvider = PowerFramework.currentFramework.createPowerProvider();
/*  94 */       this.powerProvider.configure(10, 2, (int)(getMaxWorkEnergy() / 2.5D), (int)(getMaxWorkEnergy() / 2.5D), (int)(getMaxWorkEnergy() / 2.5D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSide(int i)
/*     */   {
/* 101 */     super.setSide(i);
/* 102 */     setUEwireConnection();
/*     */   }
/*     */ 
/*     */   public int getCapacity() {
/* 106 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   public void setCapacity(int Capacity) {
/* 110 */     if (this.capacity != Capacity)
/*     */     {
/* 112 */       this.capacity = Capacity;
/* 113 */       NetworkHandlerServer.updateTileEntityField(this, "capacity");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getMaxworkcylce() {
/* 118 */     return this.maxworkcylce;
/*     */   }
/*     */ 
/*     */   public void setMaxworkcylce(int maxworkcylce) {
/* 122 */     this.maxworkcylce = maxworkcylce;
/*     */   }
/*     */ 
/*     */   public int getWorkdone() {
/* 126 */     return this.workdone;
/*     */   }
/*     */ 
/*     */   public void setWorkdone(int workdone) {
/* 130 */     if (this.workdone != workdone) {
/* 131 */       this.workdone = workdone;
/* 132 */       NetworkHandlerServer.updateTileEntityField(this, "workdone");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getWorkTicker() {
/* 137 */     return this.workTicker;
/*     */   }
/*     */ 
/*     */   public void setWorkTicker(int workTicker) {
/* 141 */     this.workTicker = workTicker;
/*     */   }
/*     */ 
/*     */   public int getMaxForceEnergyBuffer()
/*     */   {
/* 146 */     return this.MaxForceEnergyBuffer;
/*     */   }
/*     */ 
/*     */   public void setMaxForceEnergyBuffer(int maxForceEnergyBuffer) {
/* 150 */     this.MaxForceEnergyBuffer = maxForceEnergyBuffer;
/*     */   }
/*     */ 
/*     */   public int getForceEnergybuffer() {
/* 154 */     return this.ForceEnergybuffer;
/*     */   }
/*     */ 
/*     */   public void setForceEnergybuffer(int forceEnergybuffer) {
/* 158 */     this.ForceEnergybuffer = forceEnergybuffer;
/*     */   }
/*     */ 
/*     */   public void setWorkCylce(int i)
/*     */   {
/* 163 */     if (this.WorkCylce != i) {
/* 164 */       this.WorkCylce = i;
/* 165 */       NetworkHandlerServer.updateTileEntityField(this, "WorkCylce");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getWorkCylce() {
/* 170 */     return this.WorkCylce;
/*     */   }
/*     */ 
/*     */   public int getWorkEnergy() {
/* 174 */     return this.WorkEnergy;
/*     */   }
/*     */ 
/*     */   public void setWorkEnergy(int workEnergy) {
/* 178 */     this.WorkEnergy = workEnergy;
/*     */   }
/*     */ 
/*     */   public int getMaxWorkEnergy() {
/* 182 */     return this.MaxWorkEnergy;
/*     */   }
/*     */ 
/*     */   public void setMaxWorkEnergy(int maxWorkEnergy) {
/* 186 */     this.MaxWorkEnergy = maxWorkEnergy;
/*     */   }
/*     */ 
/*     */   public void dropplugins()
/*     */   {
/* 191 */     for (int a = 0; a < this.inventory.length; a++)
/* 192 */       dropplugins(a, this);
/*     */   }
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/*     */   {
/* 197 */     if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
/* 198 */       return false;
/*     */     }
/* 200 */     return entityplayer.getDistance(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
/*     */   }
/*     */ 
/*     */   public void checkslots(boolean init)
/*     */   {
/* 207 */     if (getStackInSlot(2) != null) {
/* 208 */       if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap)
/* 209 */         setMaxForceEnergyBuffer(1000000 + getStackInSlot(2).stackSize * 100000);
/*     */       else
/* 211 */         setMaxForceEnergyBuffer(1000000);
/*     */     }
/*     */     else {
/* 214 */       setMaxForceEnergyBuffer(1000000);
/*     */     }
/*     */ 
/* 217 */     if (getStackInSlot(3) != null) {
/* 218 */       if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSitemupgradeexctractorboost)
/* 219 */         setWorkTicker(20 - getStackInSlot(3).stackSize);
/*     */       else
/* 221 */         setWorkTicker(20);
/*     */     }
/*     */     else {
/* 224 */       setWorkTicker(20);
/*     */     }
/*     */ 
/* 228 */     if (getStackInSlot(4) != null) {
/* 229 */       if (getStackInSlot(4).getItem() == ModularForceFieldSystem.MFFSitemForcicumCell) {
/* 230 */         this.workmode = 1;
/* 231 */         setMaxWorkEnergy(200000);
/*     */       }
/*     */     } else {
/* 234 */       this.workmode = 0;
/* 235 */       setMaxWorkEnergy(4000);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean hasPowertoConvert()
/*     */   {
/* 242 */     if (this.WorkEnergy >= this.MaxWorkEnergy - 1)
/*     */     {
/* 244 */       setWorkEnergy(0);
/* 245 */       return true;
/*     */     }
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean hasfreeForceEnergyStorage()
/*     */   {
/* 252 */     if (this.MaxForceEnergyBuffer > this.ForceEnergybuffer)
/* 253 */       return true;
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean hasStufftoConvert()
/*     */   {
/* 259 */     if (this.WorkCylce > 0)
/*     */     {
/* 261 */       return true;
/*     */     }
/* 263 */     if (ModularForceFieldSystem.adventuremap.booleanValue())
/*     */     {
/* 265 */       setMaxworkcylce(ModularForceFieldSystem.ForceciumCellWorkCylce);
/* 266 */       setWorkCylce(getMaxworkcylce());
/* 267 */       return true;
/*     */     }
/*     */ 
/* 270 */     if (getStackInSlot(0) != null) {
/* 271 */       if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemForcicium) {
/* 272 */         setMaxworkcylce(ModularForceFieldSystem.ForceciumWorkCylce);
/* 273 */         setWorkCylce(getMaxworkcylce());
/* 274 */         decrStackSize(0, 1);
/* 275 */         return true;
/*     */       }
/*     */ 
/* 278 */       if ((getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemForcicumCell) && 
/* 279 */         (((ItemForcicumCell)getStackInSlot(0).getItem()).useForcecium(1, getStackInSlot(0))))
/*     */       {
/* 281 */         setMaxworkcylce(ModularForceFieldSystem.ForceciumCellWorkCylce);
/* 282 */         setWorkCylce(getMaxworkcylce());
/* 283 */         return true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 289 */     return false;
/*     */   }
/*     */ 
/*     */   public void transferForceEnergy()
/*     */   {
/* 294 */     if (getForceEnergybuffer() > 0)
/*     */     {
/* 296 */       if (hasPowerSource())
/*     */       {
/* 298 */         int PowerTransferrate = getMaximumPower() / 120;
/* 299 */         int freeAmount = getMaximumPower() - getAvailablePower();
/*     */ 
/* 301 */         if (getForceEnergybuffer() > freeAmount)
/*     */         {
/* 303 */           if (freeAmount > PowerTransferrate)
/*     */           {
/* 305 */             emitPower(PowerTransferrate, false);
/* 306 */             setForceEnergybuffer(getForceEnergybuffer() - PowerTransferrate);
/*     */           }
/*     */           else {
/* 309 */             emitPower(freeAmount, false);
/* 310 */             setForceEnergybuffer(getForceEnergybuffer() - freeAmount);
/*     */           }
/*     */         }
/* 313 */         else if (freeAmount > getForceEnergybuffer())
/*     */         {
/* 315 */           emitPower(getForceEnergybuffer(), false);
/* 316 */           setForceEnergybuffer(getForceEnergybuffer() - getForceEnergybuffer());
/*     */         } else {
/* 318 */           emitPower(freeAmount, false);
/* 319 */           setForceEnergybuffer(getForceEnergybuffer() - freeAmount);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public short getmaxSwitchModi()
/*     */   {
/* 328 */     return 3;
/*     */   }
/*     */ 
/*     */   public short getminSwitchModi() {
/* 332 */     return 1;
/*     */   }
/*     */ 
/*     */   public void updateEntity() {
/* 336 */     if (!this.worldObj.isRemote)
/*     */     {
/* 338 */       if (this.init) {
/* 339 */         checkslots(true);
/* 340 */         setUEwireConnection();
/*     */       }
/*     */ 
/* 343 */       if ((!this.addedToEnergyNet) && (ModularForceFieldSystem.ic2found.booleanValue())) {
/* 344 */         MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
/* 345 */         this.addedToEnergyNet = true;
/*     */       }
/*     */ 
/* 349 */       if ((getSwitchModi() == 1) && 
/* 350 */         (!getSwitchValue()) && (isRedstoneSignal())) {
/* 351 */         toggelSwitchValue();
/*     */       }
/* 353 */       if ((getSwitchModi() == 1) && 
/* 354 */         (getSwitchValue()) && (!isRedstoneSignal())) {
/* 355 */         toggelSwitchValue();
/*     */       }
/*     */ 
/* 359 */       if ((!isActive()) && (getSwitchValue())) {
/* 360 */         setActive(true);
/*     */       }
/* 362 */       if ((isActive()) && (!getSwitchValue())) {
/* 363 */         setActive(false);
/*     */       }
/*     */ 
/* 366 */       if (isActive()) {
/* 367 */         if (ModularForceFieldSystem.buildcraftfound.booleanValue()) {
/* 368 */           converMJtoWorkEnergy();
/*     */         }
/* 370 */         if (ModularForceFieldSystem.uefound.booleanValue()) {
/* 371 */           converUEtoWorkEnergy();
/*     */         }
/*     */       }
/*     */ 
/* 375 */       if (getTicker() >= getWorkTicker()) {
/* 376 */         checkslots(false);
/*     */ 
/* 378 */         if ((this.workmode == 0) && (isActive()))
/*     */         {
/* 381 */           if (getWorkdone() != getWorkEnergy() * 100 / getMaxWorkEnergy()) {
/* 382 */             setWorkdone(getWorkEnergy() * 100 / getMaxWorkEnergy());
/*     */           }
/* 384 */           if (getWorkdone() > 100) setWorkdone(100);
/*     */ 
/* 386 */           if (getCapacity() != getForceEnergybuffer() * 100 / getMaxForceEnergyBuffer()) {
/* 387 */             setCapacity(getForceEnergybuffer() * 100 / getMaxForceEnergyBuffer());
/*     */           }
/*     */ 
/* 390 */           if ((hasfreeForceEnergyStorage()) && (hasStufftoConvert()))
/*     */           {
/* 393 */             if (hasPowertoConvert()) {
/* 394 */               setWorkCylce(getWorkCylce() - 1);
/* 395 */               setForceEnergybuffer(getForceEnergybuffer() + ModularForceFieldSystem.ExtractorPassForceEnergyGenerate);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 400 */           transferForceEnergy();
/*     */ 
/* 402 */           setTicker((short)0);
/*     */         }
/*     */ 
/* 405 */         if ((this.workmode == 1) && (isActive()))
/*     */         {
/* 407 */           if (getWorkdone() != getWorkEnergy() * 100 / getMaxWorkEnergy()) {
/* 408 */             setWorkdone(getWorkEnergy() * 100 / getMaxWorkEnergy());
/*     */           }
/* 410 */           if (((ItemForcicumCell)getStackInSlot(4).getItem()).getForceciumlevel(getStackInSlot(4)) < ((ItemForcicumCell)getStackInSlot(4).getItem()).getMaxForceciumlevel())
/*     */           {
/* 413 */             if ((hasPowertoConvert()) && (isActive())) {
/* 414 */               ((ItemForcicumCell)getStackInSlot(4).getItem()).setForceciumlevel(getStackInSlot(4), ((ItemForcicumCell)getStackInSlot(4).getItem()).getForceciumlevel(getStackInSlot(4)) + 1);
/*     */             }
/*     */           }
/*     */ 
/* 418 */           setTicker((short)0);
/*     */         }
/*     */       }
/*     */ 
/* 422 */       setTicker((short)(getTicker() + 1));
/*     */     }
/* 424 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer)
/*     */   {
/* 429 */     return new ContainerForceEnergyExtractor(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 433 */     super.readFromNBT(nbttagcompound);
/*     */ 
/* 435 */     this.ForceEnergybuffer = nbttagcompound.getInteger("ForceEnergybuffer");
/* 436 */     this.WorkEnergy = nbttagcompound.getInteger("WorkEnergy");
/* 437 */     this.WorkCylce = nbttagcompound.getInteger("WorkCylce");
/*     */ 
/* 439 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/* 440 */     this.inventory = new ItemStack[getSizeInventory()];
/* 441 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 442 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*     */ 
/* 444 */       byte byte0 = nbttagcompound1.getByte("Slot");
/* 445 */       if ((byte0 >= 0) && (byte0 < this.inventory.length))
/* 446 */         this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 453 */     super.writeToNBT(nbttagcompound);
/*     */ 
/* 455 */     nbttagcompound.setInteger("WorkCylce", this.WorkCylce);
/* 456 */     nbttagcompound.setInteger("WorkEnergy", this.WorkEnergy);
/* 457 */     nbttagcompound.setInteger("ForceEnergybuffer", this.ForceEnergybuffer);
/*     */ 
/* 459 */     NBTTagList nbttaglist = new NBTTagList();
/* 460 */     for (int i = 0; i < this.inventory.length; i++) {
/* 461 */       if (this.inventory[i] != null) {
/* 462 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 463 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 464 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 465 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 469 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i) {
/* 473 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   public String getInvName() {
/* 477 */     return "Extractor";
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 482 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack) {
/* 486 */     this.inventory[i] = itemstack;
/* 487 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/* 488 */       itemstack.stackSize = getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j)
/*     */   {
/* 493 */     if (this.inventory[i] != null) {
/* 494 */       if (this.inventory[i].stackSize <= j) {
/* 495 */         ItemStack itemstack = this.inventory[i];
/* 496 */         this.inventory[i] = null;
/* 497 */         return itemstack;
/*     */       }
/* 499 */       ItemStack itemstack1 = this.inventory[i].splitStack(j);
/* 500 */       if (this.inventory[i].stackSize == 0) {
/* 501 */         this.inventory[i] = null;
/*     */       }
/* 503 */       return itemstack1;
/*     */     }
/* 505 */     return null;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 513 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 518 */     return 1;
/*     */   }
/*     */ 
/*     */   public List getFieldsforUpdate()
/*     */   {
/* 524 */     List NetworkedFields = new LinkedList();
/* 525 */     NetworkedFields.clear();
/*     */ 
/* 527 */     NetworkedFields.addAll(super.getFieldsforUpdate());
/* 528 */     NetworkedFields.add("capacity");
/* 529 */     NetworkedFields.add("WorkCylce");
/* 530 */     NetworkedFields.add("WorkEnergy");
/* 531 */     NetworkedFields.add("workdone");
/*     */ 
/* 534 */     return NetworkedFields;
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 540 */     switch (Slot)
/*     */     {
/*     */     case 0:
/* 543 */       if ((((par1ItemStack.getItem() instanceof ItemForcicium)) || ((par1ItemStack.getItem() instanceof ItemForcicumCell))) && (getStackInSlot(4) == null)) {
/* 544 */         return true;
/*     */       }
/*     */       break;
/*     */     case 1:
/* 548 */       if ((par1ItemStack.getItem() instanceof IPowerLinkItem)) {
/* 549 */         return true;
/*     */       }
/*     */       break;
/*     */     case 2:
/* 553 */       if ((par1ItemStack.getItem() instanceof ItemCapacitorUpgradeCapacity)) {
/* 554 */         return true;
/*     */       }
/*     */       break;
/*     */     case 3:
/* 558 */       if ((par1ItemStack.getItem() instanceof ItemExtractorUpgradeBooster)) {
/* 559 */         return true;
/*     */       }
/*     */       break;
/*     */     case 4:
/* 563 */       if (((par1ItemStack.getItem() instanceof ItemForcicumCell)) && (getStackInSlot(0) == null))
/* 564 */         return true;
/*     */       break;
/*     */     }
/* 567 */     return false;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int Slot)
/*     */   {
/* 572 */     switch (Slot) {
/*     */     case 0:
/* 574 */       return 64;
/*     */     case 1:
/* 576 */       return 1;
/*     */     case 2:
/* 578 */       return 9;
/*     */     case 3:
/* 580 */       return 19;
/*     */     case 4:
/* 582 */       return 1;
/*     */     }
/* 584 */     return 1;
/*     */   }
/*     */ 
/*     */   public int demandsEnergy()
/*     */   {
/* 589 */     if (!isActive())
/* 590 */       return 0;
/* 591 */     return getMaxWorkEnergy() - getWorkEnergy();
/*     */   }
/*     */ 
/*     */   public int injectEnergy(Direction directionFrom, int amount)
/*     */   {
/* 598 */     int freespace = getMaxWorkEnergy() - getWorkEnergy();
/*     */ 
/* 600 */     if (freespace >= amount) {
/* 601 */       setWorkEnergy(getWorkEnergy() + amount);
/* 602 */       return 0;
/*     */     }
/*     */ 
/* 605 */     setWorkEnergy(getMaxWorkEnergy());
/* 606 */     return amount - freespace;
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 612 */     if (this.addedToEnergyNet)
/*     */     {
/* 614 */       MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
/* 615 */       this.addedToEnergyNet = false;
/*     */     }
/*     */ 
/* 618 */     Linkgrid.getWorldMap(this.worldObj).getExtractor().remove(Integer.valueOf(getDeviceID()));
/*     */ 
/* 620 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public boolean isAddedToEnergyNet()
/*     */   {
/* 625 */     return this.addedToEnergyNet;
/*     */   }
/*     */ 
/*     */   public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction)
/*     */   {
/* 630 */     return true;
/*     */   }
/*     */ 
/*     */   public void converMJtoWorkEnergy() {
/* 634 */     if (getWorkEnergy() < getMaxWorkEnergy())
/*     */     {
/* 636 */       float use = this.powerProvider.useEnergy(1.0F, (float)(getMaxWorkEnergy() - getWorkEnergy() / 2.5D), true);
/*     */ 
/* 638 */       if (getWorkEnergy() + use * 2.5D > getMaxWorkEnergy())
/*     */       {
/* 640 */         setWorkEnergy(getMaxWorkEnergy());
/*     */       }
/* 642 */       else setWorkEnergy((int)(getWorkEnergy() + use * 2.5D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setUEwireConnection()
/*     */   {
/* 650 */     if (ModularForceFieldSystem.uefound.booleanValue())
/*     */     {
/* 652 */       ElectricityConnections.registerConnector(this, EnumSet.of(ForgeDirection.getOrientation(getFacing()).getOpposite()));
/* 653 */       this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPowerProvider(IPowerProvider provider)
/*     */   {
/* 659 */     this.powerProvider = provider;
/*     */   }
/*     */ 
/*     */   public IPowerProvider getPowerProvider()
/*     */   {
/* 664 */     return this.powerProvider;
/*     */   }
/*     */ 
/*     */   public void doWork()
/*     */   {
/*     */   }
/*     */ 
/*     */   public int powerRequest() {
/* 672 */     double workEnergyinMJ = getWorkEnergy() / 2.5D;
/* 673 */     double MaxWorkEnergyinMj = getMaxWorkEnergy() / 2.5D;
/*     */ 
/* 675 */     return (int)Math.round(MaxWorkEnergyinMj - workEnergyinMJ);
/*     */   }
/*     */ 
/*     */   public void converUEtoWorkEnergy()
/*     */   {
/* 682 */     ForgeDirection inputDirection = ForgeDirection.getOrientation(getFacing()).getOpposite();
/*     */ 
/* 684 */     TileEntity inputTile = Vector3.getTileEntityFromSide(this.worldObj, new Vector3(this), inputDirection);
/*     */ 
/* 686 */     if (inputTile != null)
/*     */     {
/* 688 */       if ((inputTile instanceof IConductor))
/*     */       {
/* 690 */         if (getWorkEnergy() >= getMaxWorkEnergy())
/*     */         {
/* 692 */           ((IConductor)inputTile).getNetwork().stopRequesting(this);
/*     */         }
/*     */         else
/*     */         {
/* 696 */           ((IConductor)inputTile).getNetwork().startRequesting(this, 10.0D, 120.0D);
/*     */ 
/* 698 */           setWorkEnergy((int)(getWorkEnergy() + ((IConductor)inputTile).getNetwork().consumeElectricity(this).getWatts() / 50.0D));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack getPowerLinkStack()
/*     */   {
/* 707 */     return getStackInSlot(getPowerlinkSlot());
/*     */   }
/*     */ 
/*     */   public int getPowerlinkSlot()
/*     */   {
/* 712 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getMaxSafeInput()
/*     */   {
/* 718 */     return 2048;
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/* 724 */     TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(this.worldObj).getCapacitor().get(Integer.valueOf(getPowerSourceID()));
/* 725 */     if (cap != null)
/*     */     {
/* 727 */       TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
/* 728 */       if (sec != null)
/*     */       {
/* 730 */         return sec;
/*     */       }
/*     */     }
/*     */ 
/* 734 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityExtractor
 * JD-Core Version:    0.6.2
 */