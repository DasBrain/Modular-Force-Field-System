/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.IForceEnergyItems;
/*     */ import chb.mods.mffs.api.IForceEnergyStorageBlock;
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.container.ContainerCapacitor;
/*     */ import chb.mods.mffs.common.item.ItemCapacitorUpgradeCapacity;
/*     */ import chb.mods.mffs.common.item.ItemCapacitorUpgradeRange;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.network.INetworkHandlerEventListener;
/*     */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ 
/*     */ public class TileEntityCapacitor extends TileEntityFEPoweredMachine
/*     */   implements INetworkHandlerEventListener, IForceEnergyStorageBlock
/*     */ {
/*     */   private ItemStack[] inventory;
/*     */   private int forcePower;
/*     */   private short linketprojektor;
/*     */   private int capacity;
/*     */   private int Powerlinkmode;
/*     */   private int TransmitRange;
/*     */ 
/*     */   public TileEntityCapacitor()
/*     */   {
/*  56 */     this.inventory = new ItemStack[5];
/*  57 */     this.forcePower = 0;
/*  58 */     this.linketprojektor = 0;
/*  59 */     this.TransmitRange = 8;
/*  60 */     this.capacity = 0;
/*  61 */     this.Powerlinkmode = 0;
/*     */   }
/*     */ 
/*     */   public int getPowerStorageID()
/*     */   {
/*  68 */     return getDeviceID();
/*     */   }
/*     */ 
/*     */   public void setTransmitRange(int transmitRange)
/*     */   {
/*  73 */     this.TransmitRange = transmitRange;
/*  74 */     NetworkHandlerServer.updateTileEntityField(this, "TransmitRange");
/*     */   }
/*     */ 
/*     */   public int getTransmitRange()
/*     */   {
/*  79 */     return this.TransmitRange;
/*     */   }
/*     */ 
/*     */   public int getPowerlinkmode() {
/*  83 */     return this.Powerlinkmode;
/*     */   }
/*     */ 
/*     */   public void setPowerlinkmode(int powerlinkmode) {
/*  87 */     this.Powerlinkmode = powerlinkmode;
/*     */   }
/*     */ 
/*     */   public int getPercentageStorageCapacity()
/*     */   {
/*  92 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   public void setCapacity(int Capacity) {
/*  96 */     if (getPercentageStorageCapacity() != Capacity)
/*     */     {
/*  98 */       this.capacity = Capacity;
/*  99 */       NetworkHandlerServer.updateTileEntityField(this, "capacity");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer) {
/* 104 */     return new ContainerCapacitor(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public Short getLinketProjektor() {
/* 108 */     return Short.valueOf(this.linketprojektor);
/*     */   }
/*     */ 
/*     */   public void setLinketprojektor(Short linketprojektor) {
/* 112 */     if (this.linketprojektor != linketprojektor.shortValue()) {
/* 113 */       this.linketprojektor = linketprojektor.shortValue();
/* 114 */       NetworkHandlerServer.updateTileEntityField(this, "linketprojektor");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getStorageAvailablePower()
/*     */   {
/* 120 */     return this.forcePower;
/*     */   }
/*     */ 
/*     */   public void setForcePower(int f)
/*     */   {
/* 125 */     this.forcePower = f;
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 130 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/* 135 */     return ItemCardSecurityLink.getLinkedSecurityStation(this, 4, this.worldObj);
/*     */   }
/*     */ 
/*     */   public int getSecStation_ID()
/*     */   {
/* 141 */     TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
/* 142 */     if (sec != null)
/* 143 */       return sec.getDeviceID();
/* 144 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getStorageMaxPower()
/*     */   {
/* 153 */     if ((getStackInSlot(0) != null) && 
/* 154 */       (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap))
/*     */     {
/* 156 */       if (this.forcePower > 10000000 + 2000000 * getStackInSlot(0).stackSize) {
/* 157 */         setForcePower(10000000 + 2000000 * getStackInSlot(0).stackSize);
/*     */       }
/* 159 */       return 10000000 + 2000000 * getStackInSlot(0).stackSize;
/*     */     }
/*     */ 
/* 162 */     if (this.forcePower > 10000000)
/* 163 */       setForcePower(10000000);
/* 164 */     return 10000000;
/*     */   }
/*     */ 
/*     */   private void checkslots(boolean init)
/*     */   {
/* 172 */     if (getStackInSlot(1) != null) {
/* 173 */       if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemupgradecaprange)
/*     */       {
/* 175 */         setTransmitRange(8 * (getStackInSlot(1).stackSize + 1));
/*     */       }
/*     */     }
/*     */     else {
/* 179 */       setTransmitRange(8);
/*     */     }
/*     */ 
/* 182 */     if (getStackInSlot(2) != null) {
/* 183 */       if ((getStackInSlot(2).getItem() instanceof IForceEnergyItems)) {
/* 184 */         if ((getPowerlinkmode() != 3) && (getPowerlinkmode() != 4)) setPowerlinkmode(3);
/* 185 */         IForceEnergyItems ForceEnergyItem = (IForceEnergyItems)getStackInSlot(2).getItem();
/*     */ 
/* 187 */         switch (getPowerlinkmode())
/*     */         {
/*     */         case 3:
/* 191 */           if (ForceEnergyItem.getAvailablePower(getStackInSlot(2)) < ForceEnergyItem.getMaximumPower(null))
/*     */           {
/* 193 */             int maxtransfer = ForceEnergyItem.getPowerTransferrate();
/* 194 */             int freeeamount = ForceEnergyItem.getMaximumPower(null) - ForceEnergyItem.getAvailablePower(getStackInSlot(2));
/*     */ 
/* 196 */             if (getStorageAvailablePower() > 0)
/*     */             {
/* 198 */               if (getStorageAvailablePower() > maxtransfer)
/*     */               {
/* 200 */                 if (freeeamount > maxtransfer)
/*     */                 {
/* 202 */                   ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + maxtransfer);
/* 203 */                   setForcePower(getStorageAvailablePower() - maxtransfer);
/*     */                 } else {
/* 205 */                   ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + freeeamount);
/* 206 */                   setForcePower(getStorageAvailablePower() - freeeamount);
/*     */                 }
/*     */               }
/* 209 */               else if (freeeamount > getStorageAvailablePower())
/*     */               {
/* 211 */                 ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + getStorageAvailablePower());
/* 212 */                 setForcePower(getStorageAvailablePower() - getStorageAvailablePower());
/*     */               } else {
/* 214 */                 ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + freeeamount);
/* 215 */                 setForcePower(getStorageAvailablePower() - freeeamount);
/*     */               }
/*     */ 
/* 219 */               getStackInSlot(2).setItemDamage(ForceEnergyItem.getItemDamage(getStackInSlot(2)));
/*     */             }
/*     */           }
/* 221 */           break;
/*     */         case 4:
/* 226 */           if (ForceEnergyItem.getAvailablePower(getStackInSlot(2)) > 0)
/*     */           {
/* 229 */             int maxtransfer = ForceEnergyItem.getPowerTransferrate();
/* 230 */             int freeeamount = getStorageMaxPower() - getStorageAvailablePower();
/* 231 */             int amountleft = ForceEnergyItem.getAvailablePower(getStackInSlot(2));
/*     */ 
/* 234 */             if (freeeamount >= amountleft)
/*     */             {
/* 236 */               if (amountleft >= maxtransfer)
/*     */               {
/* 238 */                 ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) - maxtransfer);
/* 239 */                 setForcePower(getStorageAvailablePower() + maxtransfer);
/*     */               }
/*     */               else {
/* 242 */                 ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) - amountleft);
/* 243 */                 setForcePower(getStorageAvailablePower() + amountleft);
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 248 */               ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) - freeeamount);
/* 249 */               setForcePower(getStorageAvailablePower() + freeeamount);
/*     */             }
/*     */ 
/* 254 */             getStackInSlot(2).setItemDamage(ForceEnergyItem.getItemDamage(getStackInSlot(2)));
/*     */           }
/*     */ 
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 264 */       if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemfc)
/*     */       {
/* 266 */         if ((getPowerlinkmode() != 0) && (getPowerlinkmode() != 1) && (getPowerlinkmode() != 2)) setPowerlinkmode(0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dropplugins()
/*     */   {
/* 275 */     for (int a = 0; a < this.inventory.length; a++)
/* 276 */       dropplugins(a, this);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 283 */     Linkgrid.getWorldMap(this.worldObj).getCapacitor().remove(Integer.valueOf(getDeviceID()));
/* 284 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 288 */     super.readFromNBT(nbttagcompound);
/*     */ 
/* 290 */     this.forcePower = nbttagcompound.getInteger("forcepower");
/* 291 */     this.Powerlinkmode = nbttagcompound.getInteger("Powerlinkmode");
/*     */ 
/* 293 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/* 294 */     this.inventory = new ItemStack[getSizeInventory()];
/* 295 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 296 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*     */ 
/* 298 */       byte byte0 = nbttagcompound1.getByte("Slot");
/* 299 */       if ((byte0 >= 0) && (byte0 < this.inventory.length))
/* 300 */         this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 307 */     super.writeToNBT(nbttagcompound);
/*     */ 
/* 309 */     nbttagcompound.setInteger("forcepower", this.forcePower);
/* 310 */     nbttagcompound.setInteger("Powerlinkmode", this.Powerlinkmode);
/*     */ 
/* 312 */     NBTTagList nbttaglist = new NBTTagList();
/* 313 */     for (int i = 0; i < this.inventory.length; i++) {
/* 314 */       if (this.inventory[i] != null) {
/* 315 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 316 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 317 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 318 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 322 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public void updateEntity()
/*     */   {
/* 327 */     if (!this.worldObj.isRemote)
/*     */     {
/* 329 */       if (this.init) {
/* 330 */         checkslots(true);
/*     */       }
/*     */ 
/* 333 */       if ((getSwitchModi() == 1) && 
/* 334 */         (!getSwitchValue()) && (isRedstoneSignal())) {
/* 335 */         toggelSwitchValue();
/*     */       }
/* 337 */       if ((getSwitchModi() == 1) && 
/* 338 */         (getSwitchValue()) && (!isRedstoneSignal())) {
/* 339 */         toggelSwitchValue();
/*     */       }
/*     */ 
/* 342 */       if (getSwitchValue()) {
/* 343 */         if (isActive() != true) {
/* 344 */           setActive(true);
/*     */         }
/*     */       }
/* 347 */       else if (isActive()) {
/* 348 */         setActive(false);
/*     */       }
/*     */ 
/* 352 */       if (getTicker() == 10)
/*     */       {
/* 354 */         if (getLinketProjektor().shortValue() != (short)Linkgrid.getWorldMap(this.worldObj).connectedtoCapacitor(this, getTransmitRange())) {
/* 355 */           setLinketprojektor(Short.valueOf((short)Linkgrid.getWorldMap(this.worldObj).connectedtoCapacitor(this, getTransmitRange())));
/*     */         }
/* 357 */         if (getPercentageStorageCapacity() != getStorageAvailablePower() / 1000 * 100 / (getStorageMaxPower() / 1000)) {
/* 358 */           setCapacity(getStorageAvailablePower() / 1000 * 100 / (getStorageMaxPower() / 1000));
/*     */         }
/* 360 */         checkslots(false);
/* 361 */         if (isActive())
/*     */         {
/* 363 */           powertransfer();
/*     */         }
/* 365 */         setTicker((short)0);
/*     */       }
/* 367 */       setTicker((short)(getTicker() + 1));
/*     */     }
/* 369 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   private void powertransfer()
/*     */   {
/* 374 */     if (hasPowerSource())
/*     */     {
/* 376 */       int PowerTransferrate = getMaximumPower() / 120;
/* 377 */       int freeStorageAmount = getMaximumPower() - getAvailablePower();
/* 378 */       int balancelevel = getStorageAvailablePower() - getAvailablePower();
/*     */ 
/* 380 */       switch (getPowerlinkmode())
/*     */       {
/*     */       case 0:
/* 383 */         if ((getPercentageStorageCapacity() >= 95) && (getPercentageCapacity() != 100))
/*     */         {
/* 385 */           if (freeStorageAmount > PowerTransferrate)
/*     */           {
/* 387 */             emitPower(PowerTransferrate, false);
/* 388 */             consumePowerfromStorage(PowerTransferrate, false);
/*     */           }
/*     */           else {
/* 391 */             emitPower(freeStorageAmount, false);
/* 392 */             consumePowerfromStorage(freeStorageAmount, false); }  } break;
/*     */       case 1:
/* 397 */         if (getPercentageCapacity() < getPercentageStorageCapacity())
/*     */         {
/* 400 */           if (balancelevel > PowerTransferrate)
/*     */           {
/* 402 */             emitPower(PowerTransferrate, false);
/* 403 */             consumePowerfromStorage(PowerTransferrate, false);
/*     */           } else {
/* 405 */             emitPower(balancelevel, false);
/* 406 */             consumePowerfromStorage(balancelevel, false); }  } break;
/*     */       case 2:
/* 412 */         if ((getStorageAvailablePower() > 0) && (getPercentageCapacity() != 100))
/*     */         {
/* 414 */           if (getStorageAvailablePower() > PowerTransferrate)
/*     */           {
/* 416 */             if (freeStorageAmount > PowerTransferrate)
/*     */             {
/* 418 */               emitPower(PowerTransferrate, false);
/* 419 */               consumePowerfromStorage(PowerTransferrate, false);
/*     */             } else {
/* 421 */               emitPower(freeStorageAmount, false);
/* 422 */               consumePowerfromStorage(freeStorageAmount, false);
/*     */             }
/*     */           }
/* 425 */           else if (freeStorageAmount > getStorageAvailablePower())
/*     */           {
/* 427 */             emitPower(getStorageAvailablePower(), false);
/* 428 */             consumePowerfromStorage(getStorageAvailablePower(), false);
/*     */           } else {
/* 430 */             emitPower(freeStorageAmount, false);
/* 431 */             consumePowerfromStorage(freeStorageAmount, false);
/*     */           }
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i)
/*     */   {
/* 443 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j)
/*     */   {
/* 448 */     if (this.inventory[i] != null) {
/* 449 */       if (this.inventory[i].stackSize <= j) {
/* 450 */         ItemStack itemstack = this.inventory[i];
/* 451 */         this.inventory[i] = null;
/* 452 */         return itemstack;
/*     */       }
/* 454 */       ItemStack itemstack1 = this.inventory[i].splitStack(j);
/* 455 */       if (this.inventory[i].stackSize == 0) {
/* 456 */         this.inventory[i] = null;
/*     */       }
/* 458 */       return itemstack1;
/*     */     }
/* 460 */     return null;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack)
/*     */   {
/* 465 */     this.inventory[i] = itemstack;
/* 466 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/* 467 */       itemstack.stackSize = getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   public String getInvName()
/*     */   {
/* 472 */     return "Generator";
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 477 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 482 */     return 0;
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerEvent(int key, String value)
/*     */   {
/* 489 */     switch (key)
/*     */     {
/*     */     case 1:
/* 492 */       if (getStackInSlot(2) != null)
/*     */       {
/* 496 */         if ((getStackInSlot(2).getItem() instanceof IForceEnergyItems))
/*     */         {
/* 498 */           if (getPowerlinkmode() == 4)
/*     */           {
/* 500 */             setPowerlinkmode(3);
/*     */           }
/* 502 */           else setPowerlinkmode(4);
/*     */ 
/* 505 */           return;
/*     */         }
/* 507 */         if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemfc)
/*     */         {
/* 510 */           if (getPowerlinkmode() < 2)
/*     */           {
/* 512 */             setPowerlinkmode(getPowerlinkmode() + 1);
/*     */           }
/* 514 */           else setPowerlinkmode(0);
/*     */ 
/* 517 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 521 */       if (getPowerlinkmode() != 4)
/*     */       {
/* 523 */         setPowerlinkmode(getPowerlinkmode() + 1);
/*     */       }
/* 525 */       else setPowerlinkmode(0);
/*     */       break;
/*     */     }
/*     */ 
/* 529 */     super.onNetworkHandlerEvent(key, value);
/*     */   }
/*     */ 
/*     */   public List getFieldsforUpdate()
/*     */   {
/* 536 */     List NetworkedFields = new LinkedList();
/* 537 */     NetworkedFields.clear();
/*     */ 
/* 539 */     NetworkedFields.addAll(super.getFieldsforUpdate());
/*     */ 
/* 541 */     NetworkedFields.add("linketprojektor");
/* 542 */     NetworkedFields.add("capacity");
/* 543 */     NetworkedFields.add("TransmitRange");
/*     */ 
/* 545 */     return NetworkedFields;
/*     */   }
/*     */ 
/*     */   public int getfreeStorageAmount()
/*     */   {
/* 551 */     return getStorageMaxPower() - getStorageAvailablePower();
/*     */   }
/*     */ 
/*     */   public boolean insertPowertoStorage(int powerAmount, boolean simulation)
/*     */   {
/* 558 */     if (simulation)
/*     */     {
/* 560 */       if (getStorageAvailablePower() + powerAmount <= getStorageMaxPower())
/* 561 */         return true;
/* 562 */       return false;
/*     */     }
/* 564 */     setForcePower(getStorageAvailablePower() + powerAmount);
/* 565 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean consumePowerfromStorage(int powerAmount, boolean simulation)
/*     */   {
/* 573 */     if (simulation)
/*     */     {
/* 575 */       if (getStorageAvailablePower() >= powerAmount)
/* 576 */         return true;
/* 577 */       return false;
/*     */     }
/* 579 */     if (getStorageAvailablePower() - powerAmount >= 0)
/* 580 */       setForcePower(getStorageAvailablePower() - powerAmount);
/* 581 */     else setForcePower(0);
/* 582 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 589 */     switch (Slot)
/*     */     {
/*     */     case 0:
/* 592 */       if ((par1ItemStack.getItem() instanceof ItemCapacitorUpgradeCapacity))
/* 593 */         return true;
/*     */       break;
/*     */     case 1:
/* 596 */       if ((par1ItemStack.getItem() instanceof ItemCapacitorUpgradeRange))
/* 597 */         return true;
/*     */       break;
/*     */     case 2:
/* 600 */       if (((par1ItemStack.getItem() instanceof IForceEnergyItems)) || ((par1ItemStack.getItem() instanceof IPowerLinkItem)))
/* 601 */         return true;
/*     */       break;
/*     */     case 4:
/* 604 */       if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink))
/* 605 */         return true;
/*     */       break;
/*     */     case 3:
/*     */     }
/* 609 */     return false;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int Slot)
/*     */   {
/* 614 */     switch (Slot) {
/*     */     case 0:
/* 616 */       return 9;
/*     */     case 1:
/* 618 */       return 9;
/*     */     case 2:
/* 620 */       return 64;
/*     */     }
/* 622 */     return 1;
/*     */   }
/*     */ 
/*     */   public short getmaxSwitchModi()
/*     */   {
/* 627 */     return 3;
/*     */   }
/*     */ 
/*     */   public short getminSwitchModi() {
/* 631 */     return 1;
/*     */   }
/*     */ 
/*     */   public ItemStack getPowerLinkStack()
/*     */   {
/* 636 */     return getStackInSlot(getPowerlinkSlot());
/*     */   }
/*     */ 
/*     */   public int getPowerlinkSlot()
/*     */   {
/* 641 */     return 2;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityCapacitor
 * JD-Core Version:    0.6.2
 */