/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.InventoryHelper;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.MFFSDamageSource;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.container.ContainerAreaDefenseStation;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.common.item.ItemProjectorFieldModulatorDistance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.passive.EntityAmbientCreature;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ import net.minecraftforge.common.ISidedInventory;
/*     */ 
/*     */ public class TileEntityAreaDefenseStation extends TileEntityFEPoweredMachine
/*     */   implements ISidedInventory
/*     */ {
/*     */   private ItemStack[] Inventory;
/*     */   private int capacity;
/*     */   private int distance;
/*     */   private int contratyp;
/*     */   private int actionmode;
/*     */   private int scanmode;
/*  66 */   protected List warnlist = new ArrayList();
/*  67 */   protected List actionlist = new ArrayList();
/*  68 */   protected List NPClist = new ArrayList();
/*  69 */   private ArrayList ContraList = new ArrayList();
/*     */ 
/*     */   public TileEntityAreaDefenseStation() {
/*  72 */     Random random = new Random();
/*     */ 
/*  74 */     this.Inventory = new ItemStack[36];
/*  75 */     this.capacity = 0;
/*  76 */     this.contratyp = 1;
/*  77 */     this.actionmode = 0;
/*  78 */     this.scanmode = 1;
/*     */   }
/*     */ 
/*     */   public int getScanmode()
/*     */   {
/*  85 */     return this.scanmode;
/*     */   }
/*     */ 
/*     */   public void setScanmode(int scanmode) {
/*  89 */     this.scanmode = scanmode;
/*     */   }
/*     */ 
/*     */   public int getActionmode()
/*     */   {
/*  94 */     return this.actionmode;
/*     */   }
/*     */ 
/*     */   public void setActionmode(int actionmode)
/*     */   {
/*  99 */     this.actionmode = actionmode;
/*     */   }
/*     */ 
/*     */   public int getcontratyp() {
/* 103 */     return this.contratyp;
/*     */   }
/*     */ 
/*     */   public void setcontratyp(int a) {
/* 107 */     this.contratyp = a;
/*     */   }
/*     */ 
/*     */   public int getCapacity() {
/* 111 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   public void setCapacity(int Capacity) {
/* 115 */     this.capacity = Capacity;
/*     */   }
/*     */ 
/*     */   public int getActionDistance()
/*     */   {
/* 120 */     if ((getStackInSlot(3) != null) && 
/* 121 */       (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance)) {
/* 122 */       return getStackInSlot(3).stackSize;
/*     */     }
/*     */ 
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getInfoDistance()
/*     */   {
/* 132 */     if ((getStackInSlot(2) != null) && 
/* 133 */       (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance)) {
/* 134 */       return getActionDistance() + (getStackInSlot(2).stackSize + 3);
/*     */     }
/*     */ 
/* 137 */     return getActionDistance() + 3;
/*     */   }
/*     */ 
/*     */   public boolean hasSecurityCard()
/*     */   {
/* 144 */     if ((getStackInSlot(1) != null) && 
/* 145 */       (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard)) {
/* 146 */       return true;
/*     */     }
/*     */ 
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/* 154 */     return ItemCardSecurityLink.getLinkedSecurityStation(this, 1, this.worldObj);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 161 */     Linkgrid.getWorldMap(this.worldObj).getDefStation().remove(Integer.valueOf(getDeviceID()));
/* 162 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 168 */     super.readFromNBT(nbttagcompound);
/* 169 */     this.contratyp = nbttagcompound.getInteger("contratyp");
/* 170 */     this.actionmode = nbttagcompound.getInteger("actionmode");
/* 171 */     this.scanmode = nbttagcompound.getInteger("scanmode");
/* 172 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/* 173 */     this.Inventory = new ItemStack[getSizeInventory()];
/* 174 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 175 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*     */ 
/* 177 */       byte byte0 = nbttagcompound1.getByte("Slot");
/* 178 */       if ((byte0 >= 0) && (byte0 < this.Inventory.length))
/* 179 */         this.Inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 186 */     super.writeToNBT(nbttagcompound);
/* 187 */     nbttagcompound.setInteger("contratyp", this.contratyp);
/* 188 */     nbttagcompound.setInteger("actionmode", this.actionmode);
/* 189 */     nbttagcompound.setInteger("scanmode", this.scanmode);
/* 190 */     NBTTagList nbttaglist = new NBTTagList();
/* 191 */     for (int i = 0; i < this.Inventory.length; i++) {
/* 192 */       if (this.Inventory[i] != null) {
/* 193 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 194 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 195 */         this.Inventory[i].writeToNBT(nbttagcompound1);
/* 196 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 200 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public void dropplugins()
/*     */   {
/* 205 */     for (int a = 0; a < this.Inventory.length; a++)
/* 206 */       dropplugins(a, this);
/*     */   }
/*     */ 
/*     */   public void scanner()
/*     */   {
/*     */     try
/*     */     {
/* 216 */       TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
/*     */ 
/* 218 */       if (sec != null)
/*     */       {
/* 220 */         int xmininfo = this.xCoord - getInfoDistance();
/* 221 */         int xmaxinfo = this.xCoord + getInfoDistance() + 1;
/* 222 */         int ymininfo = this.yCoord - getInfoDistance();
/* 223 */         int ymaxinfo = this.yCoord + getInfoDistance() + 1;
/* 224 */         int zmininfo = this.zCoord - getInfoDistance();
/* 225 */         int zmaxinfo = this.zCoord + getInfoDistance() + 1;
/*     */ 
/* 227 */         int xminaction = this.xCoord - getActionDistance();
/* 228 */         int xmaxaction = this.xCoord + getActionDistance() + 1;
/* 229 */         int yminaction = this.yCoord - getActionDistance();
/* 230 */         int ymaxaction = this.yCoord + getActionDistance() + 1;
/* 231 */         int zminaction = this.zCoord - getActionDistance();
/* 232 */         int zmaxaction = this.zCoord + getActionDistance() + 1;
/*     */ 
/* 234 */         List infoLivinglist = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xmininfo, ymininfo, zmininfo, xmaxinfo, ymaxinfo, zmaxinfo));
/* 235 */         List actionLivinglist = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xminaction, yminaction, zminaction, xmaxaction, ymaxaction, zmaxaction));
/*     */ 
/* 238 */         for (EntityLiving Living : infoLivinglist)
/*     */         {
/* 240 */           if ((Living instanceof EntityPlayer))
/*     */           {
/* 242 */             EntityPlayer player = (EntityPlayer)Living;
/* 243 */             int distance = (int)PointXYZ.distance(getMaschinePoint(), new PointXYZ((int)Living.posX, (int)Living.posY, (int)Living.posZ, this.worldObj));
/*     */ 
/* 245 */             if ((distance <= getInfoDistance()) || (getScanmode() != 1))
/*     */             {
/* 250 */               if (!this.warnlist.contains(player))
/*     */               {
/* 252 */                 this.warnlist.add(player);
/* 253 */                 if (!sec.isAccessGranted(player.username, SecurityRight.SR))
/*     */                 {
/* 255 */                   if ((!ModularForceFieldSystem.DefenceStationNPCScannsuppressnotification) || (getActionmode() < 3)) {
/* 256 */                     player.addChatMessage("!!! [Security Station][" + sec.getDeviceName() + "] Warning you now in me Scanning range!!!");
/* 257 */                     player.attackEntityFrom(MFFSDamageSource.areaDefense, 1);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 263 */         for (EntityLiving Living : actionLivinglist)
/*     */         {
/* 265 */           if ((Living instanceof EntityPlayer))
/*     */           {
/* 267 */             EntityPlayer player = (EntityPlayer)Living;
/*     */ 
/* 269 */             int distance = (int)Math.round(PointXYZ.distance(getMaschinePoint(), new PointXYZ((int)Living.posX, (int)Living.posY, (int)Living.posZ, this.worldObj)));
/* 270 */             if ((distance <= getActionDistance()) || (getScanmode() != 1))
/*     */             {
/* 273 */               if (!this.actionlist.contains(player))
/*     */               {
/* 275 */                 this.actionlist.add(player);
/* 276 */                 DefenceAction(player);
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 281 */             int distance = (int)Math.round(PointXYZ.distance(getMaschinePoint(), new PointXYZ((int)Living.posX, (int)Living.posY, (int)Living.posZ, this.worldObj)));
/* 282 */             if ((distance <= getActionDistance()) || (getScanmode() != 1))
/*     */             {
/* 285 */               if (!this.NPClist.contains(Living)) {
/* 286 */                 this.NPClist.add(Living);
/* 287 */                 DefenceAction(Living);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 298 */         for (int i = 0; i < this.actionlist.size(); i++)
/*     */         {
/* 300 */           if (!actionLivinglist.contains(this.actionlist.get(i))) {
/* 301 */             this.actionlist.remove(this.actionlist.get(i));
/*     */           }
/*     */         }
/*     */ 
/* 305 */         for (int i = 0; i < this.warnlist.size(); i++)
/*     */         {
/* 308 */           if (!infoLivinglist.contains(this.warnlist.get(i))) {
/* 309 */             this.warnlist.remove(this.warnlist.get(i));
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 318 */       System.err.println("[ModularForceFieldSystem] catch  Crash <TileEntityAreaDefenseStation:scanner> ");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void DefenceAction()
/*     */   {
/* 327 */     for (int i = 0; i < this.actionlist.size(); i++)
/*     */     {
/* 329 */       DefenceAction((EntityPlayer)this.actionlist.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean StacksToInventory(IInventory inventory, ItemStack itemstacks, boolean loop)
/*     */   {
/* 339 */     int count = 0;
/*     */ 
/* 341 */     if ((inventory instanceof TileEntitySecStorage)) {
/* 342 */       count = 1;
/*     */     }
/* 344 */     if ((inventory instanceof TileEntityAreaDefenseStation)) {
/* 345 */       count = 15;
/*     */     }
/* 347 */     for (int a = count; a <= inventory.getSizeInventory() - 1; a++) {
/* 348 */       if (inventory.getStackInSlot(a) == null) {
/* 349 */         inventory.setInventorySlotContents(a, itemstacks);
/* 350 */         return true;
/*     */       }
/* 352 */       if ((inventory.getStackInSlot(a).getItem() == itemstacks.getItem()) && (inventory.getStackInSlot(a).getItemDamage() == itemstacks.getItemDamage()) && (inventory.getStackInSlot(a).stackSize < inventory.getStackInSlot(a).getMaxStackSize()))
/*     */       {
/* 356 */         int free = inventory.getStackInSlot(a).getMaxStackSize() - inventory.getStackInSlot(a).stackSize;
/*     */ 
/* 358 */         if (free > itemstacks.stackSize)
/*     */         {
/* 360 */           inventory.getStackInSlot(a).stackSize += itemstacks.stackSize;
/* 361 */           return true;
/*     */         }
/* 363 */         inventory.getStackInSlot(a).stackSize = inventory.getStackInSlot(a).getMaxStackSize();
/* 364 */         itemstacks.stackSize -= free;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 372 */     if (loop) {
/* 373 */       addremoteInventory(itemstacks);
/*     */     }
/* 375 */     return false;
/*     */   }
/*     */ 
/*     */   public void addremoteInventory(ItemStack itemstacks)
/*     */   {
/* 381 */     IInventory inv = InventoryHelper.findAttachedInventory(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
/* 382 */     if (inv != null)
/*     */     {
/* 384 */       StacksToInventory(inv, itemstacks, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void DefenceAction(EntityLiving Living) {
/* 389 */     if ((Living instanceof EntityPlayer)) {
/* 390 */       return;
/*     */     }
/* 392 */     TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
/*     */ 
/* 394 */     if (hasPowerSource())
/*     */     {
/* 397 */       if (sec != null)
/*     */       {
/* 399 */         if (consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, true));
/* 401 */         switch (getActionmode())
/*     */         {
/*     */         case 3:
/* 404 */           consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false);
/* 405 */           Living.setEntityHealth(0);
/* 406 */           this.NPClist.remove(Living);
/* 407 */           break;
/*     */         case 4:
/* 409 */           if (((Living instanceof EntityMob)) || ((Living instanceof EntityAmbientCreature)) || ((Living instanceof EntitySlime)) || ((Living instanceof EntityGhast))) {
/* 410 */             Living.setEntityHealth(0);
/* 411 */             this.NPClist.remove(Living);
/* 412 */             consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false); } break;
/*     */         case 5:
/* 418 */           if ((!(Living instanceof EntityMob)) && (!(Living instanceof EntitySlime)) && (!(Living instanceof EntityGhast))) {
/* 419 */             Living.setEntityHealth(0);
/* 420 */             this.NPClist.remove(Living);
/* 421 */             consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false);
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void DefenceAction(EntityPlayer player)
/*     */   {
/* 432 */     TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
/*     */ 
/* 434 */     if (hasPowerSource())
/*     */     {
/* 437 */       if (sec != null)
/*     */       {
/* 440 */         switch (getActionmode())
/*     */         {
/*     */         case 0:
/* 443 */           if (!sec.isAccessGranted(player.username, SecurityRight.SR))
/*     */           {
/* 445 */             player.addChatMessage("!!! [Area Defence]  get out immediately you have no right to be here!!!"); } break;
/*     */         case 1:
/* 451 */           if (consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, true))
/*     */           {
/* 453 */             if (!sec.isAccessGranted(player.username, SecurityRight.SR))
/*     */             {
/* 455 */               player.addChatMessage("!!! [Area Defence] you have been warned BYE BYE!!!");
/*     */ 
/* 458 */               for (int i = 0; i < 4; i++) {
/* 459 */                 if (player.inventory.armorInventory[i] != null) {
/* 460 */                   StacksToInventory(this, player.inventory.armorInventory[i], true);
/* 461 */                   player.inventory.armorInventory[i] = null;
/*     */                 }
/*     */               }
/*     */ 
/* 465 */               for (int i = 0; i < 36; i++)
/*     */               {
/* 467 */                 if (player.inventory.mainInventory[i] != null) {
/* 468 */                   StacksToInventory(this, player.inventory.mainInventory[i], true);
/* 469 */                   player.inventory.mainInventory[i] = null;
/*     */                 }
/*     */               }
/*     */ 
/* 473 */               this.actionlist.remove(player);
/* 474 */               player.setEntityHealth(0);
/* 475 */               player.attackEntityFrom(MFFSDamageSource.areaDefense, 20);
/* 476 */               consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false); }  } break;
/*     */         case 2:
/* 487 */           if (consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, true))
/*     */           {
/* 489 */             if (!sec.isAccessGranted(player.username, SecurityRight.AAI))
/*     */             {
/* 491 */               this.ContraList.clear();
/*     */ 
/* 493 */               for (int place = 5; place < 15; place++) {
/* 494 */                 if (getStackInSlot(place) != null)
/*     */                 {
/* 496 */                   this.ContraList.add(getStackInSlot(place).getItem());
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 501 */               switch (getcontratyp())
/*     */               {
/*     */               case 0:
/* 507 */                 for (int i = 0; i < 4; i++) {
/* 508 */                   if (player.inventory.armorInventory[i] != null)
/*     */                   {
/* 510 */                     if (!this.ContraList.contains(player.inventory.armorInventory[i].getItem()))
/*     */                     {
/* 512 */                       player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.armorInventory[i].getItem().getItemDisplayName(player.inventory.armorInventory[i]) + "> will be confiscated!!!");
/* 513 */                       StacksToInventory(this, player.inventory.armorInventory[i], true);
/* 514 */                       player.inventory.armorInventory[i] = null;
/* 515 */                       consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
/*     */                     }
/*     */                   }
/*     */ 
/*     */                 }
/*     */ 
/* 521 */                 for (int i = 0; i < 36; i++)
/*     */                 {
/* 523 */                   if (player.inventory.mainInventory[i] != null)
/*     */                   {
/* 525 */                     if (!this.ContraList.contains(player.inventory.mainInventory[i].getItem()))
/*     */                     {
/* 527 */                       player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.mainInventory[i].getItem().getItemDisplayName(player.inventory.mainInventory[i]) + "> will be confiscated!!!");
/* 528 */                       StacksToInventory(this, player.inventory.mainInventory[i], true);
/* 529 */                       player.inventory.mainInventory[i] = null;
/* 530 */                       consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
/*     */                     }
/*     */                   }
/*     */ 
/*     */                 }
/*     */ 
/* 536 */                 break;
/*     */               case 1:
/* 540 */                 for (int i = 0; i < 4; i++) {
/* 541 */                   if (player.inventory.armorInventory[i] != null)
/*     */                   {
/* 543 */                     if (this.ContraList.contains(player.inventory.armorInventory[i].getItem()))
/*     */                     {
/* 545 */                       player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.armorInventory[i].getItem().getItemDisplayName(player.inventory.armorInventory[i]) + "> will be confiscated!!!");
/* 546 */                       StacksToInventory(this, player.inventory.armorInventory[i], true);
/* 547 */                       player.inventory.armorInventory[i] = null;
/* 548 */                       consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
/*     */                     }
/*     */                   }
/*     */ 
/*     */                 }
/*     */ 
/* 554 */                 for (int i = 0; i < 36; i++)
/* 555 */                   if (player.inventory.mainInventory[i] != null)
/*     */                   {
/* 557 */                     if (this.ContraList.contains(player.inventory.mainInventory[i].getItem()))
/*     */                     {
/* 559 */                       player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.mainInventory[i].getItem().getItemDisplayName(player.inventory.mainInventory[i]) + "> will be confiscated!!!");
/* 560 */                       StacksToInventory(this, player.inventory.mainInventory[i], true);
/* 561 */                       player.inventory.mainInventory[i] = null;
/* 562 */                       consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
/*     */                     }
/*     */                   }
/*     */               }
/*     */             }
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateEntity()
/*     */   {
/* 583 */     if (!this.worldObj.isRemote)
/*     */     {
/* 586 */       if ((getSwitchModi() == 1) && 
/* 587 */         (!getSwitchValue()) && (isRedstoneSignal())) {
/* 588 */         toggelSwitchValue();
/*     */       }
/* 590 */       if ((getSwitchModi() == 1) && 
/* 591 */         (getSwitchValue()) && (!isRedstoneSignal())) {
/* 592 */         toggelSwitchValue();
/*     */       }
/* 594 */       if ((getSwitchValue()) && (hasPowerSource()) && (getAvailablePower() > 0) && (getLinkedSecurityStation() != null) && (!isActive()))
/*     */       {
/* 596 */         setActive(true);
/*     */       }
/* 598 */       if (((!getSwitchValue()) || (!hasPowerSource()) || (getAvailablePower() < ModularForceFieldSystem.DefenceStationScannForceEnergy * getInfoDistance()) || (getLinkedSecurityStation() == null)) && (isActive())) {
/* 599 */         setActive(false);
/*     */       }
/* 601 */       if (isActive())
/*     */       {
/* 603 */         if (consumePower(ModularForceFieldSystem.DefenceStationScannForceEnergy * getInfoDistance(), true)) {
/* 604 */           consumePower(ModularForceFieldSystem.DefenceStationScannForceEnergy * getInfoDistance(), false);
/* 605 */           scanner();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 610 */       if (getTicker() == 100) {
/* 611 */         if (isActive())
/*     */         {
/* 613 */           DefenceAction();
/*     */         }
/* 615 */         setTicker((short)0);
/*     */       }
/* 617 */       setTicker((short)(getTicker() + 1));
/*     */     }
/* 619 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j)
/*     */   {
/* 624 */     if (this.Inventory[i] != null) {
/* 625 */       if (this.Inventory[i].stackSize <= j) {
/* 626 */         ItemStack itemstack = this.Inventory[i];
/* 627 */         this.Inventory[i] = null;
/* 628 */         return itemstack;
/*     */       }
/* 630 */       ItemStack itemstack1 = this.Inventory[i].splitStack(j);
/* 631 */       if (this.Inventory[i].stackSize == 0) {
/* 632 */         this.Inventory[i] = null;
/*     */       }
/* 634 */       return itemstack1;
/*     */     }
/* 636 */     return null;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack)
/*     */   {
/* 641 */     this.Inventory[i] = itemstack;
/* 642 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/* 643 */       itemstack.stackSize = getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i)
/*     */   {
/* 651 */     return this.Inventory[i];
/*     */   }
/*     */ 
/*     */   public String getInvName() {
/* 655 */     return "Defstation";
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 660 */     return this.Inventory.length;
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer)
/*     */   {
/* 666 */     return new ContainerAreaDefenseStation(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 672 */     return 15;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 677 */     return 20;
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerEvent(int key, String value)
/*     */   {
/* 685 */     if (!isActive()) {
/* 686 */       switch (key)
/*     */       {
/*     */       case 100:
/* 690 */         if (getcontratyp() == 0)
/* 691 */           setcontratyp(1);
/*     */         else {
/* 693 */           setcontratyp(0);
/*     */         }
/* 695 */         break;
/*     */       case 101:
/* 697 */         if (getActionmode() == 5)
/*     */         {
/* 699 */           setActionmode(0);
/*     */         }
/* 701 */         else setActionmode(getActionmode() + 1);
/*     */ 
/* 703 */         break;
/*     */       case 102:
/* 705 */         if (getScanmode() == 0)
/* 706 */           setScanmode(1);
/*     */         else {
/* 708 */           setScanmode(0);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/* 714 */     super.onNetworkHandlerEvent(key, value);
/*     */   }
/*     */ 
/*     */   public short getmaxSwitchModi()
/*     */   {
/* 720 */     return 3;
/*     */   }
/*     */ 
/*     */   public short getminSwitchModi() {
/* 724 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 730 */     switch (Slot)
/*     */     {
/*     */     case 0:
/* 733 */       if ((par1ItemStack.getItem() instanceof IPowerLinkItem))
/* 734 */         return true;
/*     */       break;
/*     */     case 1:
/* 737 */       if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink))
/* 738 */         return true;
/*     */       break;
/*     */     case 2:
/*     */     case 3:
/* 742 */       if ((par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorDistance)) {
/* 743 */         return true;
/*     */       }
/*     */       break;
/*     */     }
/* 747 */     if ((Slot >= 5) && (Slot <= 14)) {
/* 748 */       return true;
/*     */     }
/*     */ 
/* 751 */     return false;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int Slot)
/*     */   {
/* 757 */     switch (Slot)
/*     */     {
/*     */     case 0:
/*     */     case 1:
/* 762 */       return 1;
/*     */     case 2:
/*     */     case 3:
/* 766 */       return 64;
/*     */     }
/*     */ 
/* 769 */     if ((Slot >= 5) && (Slot <= 14)) {
/* 770 */       return 1;
/*     */     }
/* 772 */     if ((Slot >= 5) && (Slot <= 14)) {
/* 773 */       return 1;
/*     */     }
/* 775 */     return 64;
/*     */   }
/*     */ 
/*     */   public ItemStack getPowerLinkStack()
/*     */   {
/* 780 */     return getStackInSlot(getPowerlinkSlot());
/*     */   }
/*     */ 
/*     */   public int getPowerlinkSlot()
/*     */   {
/* 785 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation
 * JD-Core Version:    0.6.2
 */