/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.container.ContainerConverter;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileSourceEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
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
/*     */ import universalelectricity.core.vector.Vector3;
/*     */ 
/*     */ public class TileEntityConverter extends TileEntityFEPoweredMachine
/*     */   implements IEnergySource
/*     */ {
/*     */   private ItemStack[] inventory;
/*     */   private int IC_Outputpacketsize;
/*     */   private int IC_Outputpacketamount;
/*  52 */   private int IC_Output = 0;
/*     */   private int UE_Outputvoltage;
/*     */   private int UE_Outputamp;
/*  55 */   private int UE_Output = 0;
/*     */   private boolean addedToEnergyNet;
/*  59 */   private boolean Industriecraftfound = true;
/*     */   private int linkPower;
/*     */   private int capacity;
/*     */ 
/*     */   public TileEntityConverter()
/*     */   {
/*  64 */     this.inventory = new ItemStack[4];
/*  65 */     this.capacity = 0;
/*  66 */     this.IC_Outputpacketsize = 1;
/*  67 */     this.IC_Outputpacketamount = 1;
/*  68 */     this.UE_Outputvoltage = 120;
/*  69 */     this.UE_Outputamp = 10;
/*  70 */     this.addedToEnergyNet = false;
/*  71 */     this.linkPower = 0;
/*     */   }
/*     */ 
/*     */   public int getUE_Outputvoltage()
/*     */   {
/*  76 */     return this.UE_Outputvoltage;
/*     */   }
/*     */ 
/*     */   public void setUE_Outputvoltage(int uE_Outputvoltage) {
/*  80 */     this.UE_Outputvoltage = uE_Outputvoltage;
/*     */   }
/*     */ 
/*     */   public int getUE_Outputamp() {
/*  84 */     return this.UE_Outputamp;
/*     */   }
/*     */ 
/*     */   public void setUE_Outputamp(int uE_Outputamp) {
/*  88 */     this.UE_Outputamp = uE_Outputamp;
/*     */   }
/*     */ 
/*     */   public int getUE_Output() {
/*  92 */     return this.UE_Output;
/*     */   }
/*     */ 
/*     */   public void setUE_Output(int uE_Output) {
/*  96 */     this.UE_Output = uE_Output;
/*     */   }
/*     */ 
/*     */   public int getIC_Output() {
/* 100 */     return this.IC_Output;
/*     */   }
/*     */ 
/*     */   public void setIC_Output(int iC_Output) {
/* 104 */     this.IC_Output = iC_Output;
/*     */   }
/*     */ 
/*     */   public int getIC_Outputpacketsize() {
/* 108 */     return this.IC_Outputpacketsize;
/*     */   }
/*     */ 
/*     */   public void setIC_Outputpacketsize(int iC_Outputpacketsize) {
/* 112 */     this.IC_Outputpacketsize = iC_Outputpacketsize;
/*     */   }
/*     */ 
/*     */   public int getIC_Outputpacketamount() {
/* 116 */     return this.IC_Outputpacketamount;
/*     */   }
/*     */ 
/*     */   public void setIC_Outputpacketamount(int iC_Outputpacketamount) {
/* 120 */     this.IC_Outputpacketamount = iC_Outputpacketamount;
/*     */   }
/*     */ 
/*     */   public void setSide(int i)
/*     */   {
/* 126 */     super.setSide(i);
/* 127 */     setUEwireConnection();
/*     */   }
/*     */ 
/*     */   public int getLinkPower()
/*     */   {
/* 132 */     return this.linkPower;
/*     */   }
/*     */ 
/*     */   public void setLinkPower(int linkPower) {
/* 136 */     this.linkPower = linkPower;
/*     */   }
/*     */ 
/*     */   public int getCapacity() {
/* 140 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   public void setCapacity(int Capacity) {
/* 144 */     this.capacity = Capacity;
/*     */   }
/*     */ 
/*     */   public void updateEntity() {
/* 148 */     if (!this.worldObj.isRemote) {
/* 149 */       if ((!this.addedToEnergyNet) && (this.Industriecraftfound)) {
/*     */         try {
/* 151 */           MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
/* 152 */           this.addedToEnergyNet = true;
/*     */         } catch (Exception ex) {
/* 154 */           this.Industriecraftfound = false;
/*     */         }
/*     */       }
/*     */ 
/* 158 */       if (this.init) {
/* 159 */         setUEwireConnection();
/*     */       }
/*     */ 
/* 162 */       if (hasPowerSource())
/*     */       {
/* 164 */         setLinkPower(getAvailablePower());
/*     */       }
/*     */       else {
/* 167 */         setLinkPower(0);
/*     */       }
/*     */ 
/* 170 */       if ((getSwitchModi() == 1) && 
/* 171 */         (!getSwitchValue()) && (isRedstoneSignal())) {
/* 172 */         toggelSwitchValue();
/*     */       }
/* 174 */       if ((getSwitchModi() == 1) && 
/* 175 */         (getSwitchValue()) && (!isRedstoneSignal())) {
/* 176 */         toggelSwitchValue();
/*     */       }
/*     */ 
/* 179 */       if ((getSwitchValue()) && (hasPowerSource()) && (!isActive()))
/*     */       {
/* 181 */         setActive(true);
/*     */       }
/* 183 */       if (((!getSwitchValue()) || (!hasPowerSource())) && (isActive())) {
/* 184 */         setActive(false);
/*     */       }
/* 186 */       if ((isActive()) && 
/* 187 */         (getIC_Output() == 1)) {
/* 188 */         EmitICpower(getIC_Outputpacketsize(), getIC_Outputpacketamount());
/*     */       }
/*     */ 
/* 191 */       EmitUEPower(getUE_Outputvoltage(), getUE_Outputamp());
/*     */     }
/*     */ 
/* 194 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   public void dropplugins()
/*     */   {
/* 200 */     for (int a = 0; a < this.inventory.length; a++)
/* 201 */       dropplugins(a, this);
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 207 */     super.readFromNBT(nbttagcompound);
/*     */     try
/*     */     {
/* 211 */       this.IC_Outputpacketamount = nbttagcompound.getShort("ICOutputpacketamount");
/*     */     } catch (Exception e) {
/* 213 */       this.IC_Outputpacketamount = nbttagcompound.getInteger("ICOutputpacketamount");
/*     */     }
/* 215 */     this.IC_Output = nbttagcompound.getInteger("ICOutput");
/* 216 */     this.IC_Outputpacketsize = nbttagcompound.getInteger("ICOutputpacketsize");
/* 217 */     this.UE_Output = nbttagcompound.getInteger("UEOutput");
/* 218 */     this.UE_Outputvoltage = nbttagcompound.getInteger("UEOutputvoltage");
/* 219 */     this.UE_Outputamp = nbttagcompound.getInteger("UEOutputamp");
/*     */ 
/* 221 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/* 222 */     this.inventory = new ItemStack[getSizeInventory()];
/* 223 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 224 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*     */ 
/* 226 */       byte byte0 = nbttagcompound1.getByte("Slot");
/* 227 */       if ((byte0 >= 0) && (byte0 < this.inventory.length))
/* 228 */         this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 235 */     super.writeToNBT(nbttagcompound);
/*     */ 
/* 237 */     nbttagcompound.setInteger("ICOutput", this.IC_Output);
/* 238 */     nbttagcompound.setInteger("ICOutputpacketsize", this.IC_Outputpacketsize);
/* 239 */     nbttagcompound.setInteger("ICOutputpacketamount", this.IC_Outputpacketamount);
/*     */ 
/* 241 */     nbttagcompound.setInteger("UEOutput", this.UE_Output);
/* 242 */     nbttagcompound.setInteger("UEOutputvoltage", this.UE_Outputvoltage);
/* 243 */     nbttagcompound.setInteger("UEOutputamp", this.UE_Outputamp);
/*     */ 
/* 245 */     NBTTagList nbttaglist = new NBTTagList();
/* 246 */     for (int i = 0; i < this.inventory.length; i++) {
/* 247 */       if (this.inventory[i] != null) {
/* 248 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 249 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 250 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 251 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 255 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i) {
/* 259 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   public String getInvName() {
/* 263 */     return "Extractor";
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 268 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack) {
/* 272 */     this.inventory[i] = itemstack;
/* 273 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/* 274 */       itemstack.stackSize = getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j)
/*     */   {
/* 279 */     if (this.inventory[i] != null) {
/* 280 */       if (this.inventory[i].stackSize <= j) {
/* 281 */         ItemStack itemstack = this.inventory[i];
/* 282 */         this.inventory[i] = null;
/* 283 */         return itemstack;
/*     */       }
/* 285 */       ItemStack itemstack1 = this.inventory[i].splitStack(j);
/* 286 */       if (this.inventory[i].stackSize == 0) {
/* 287 */         this.inventory[i] = null;
/*     */       }
/* 289 */       return itemstack1;
/*     */     }
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlotOnClosing(int var1)
/*     */   {
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 302 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 307 */     return 1;
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerUpdate(String field)
/*     */   {
/* 312 */     this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerEvent(int key, String value)
/*     */   {
/* 318 */     if (key == 100)
/*     */     {
/* 320 */       if (getIC_Output() == 0)
/* 321 */         setIC_Output(1);
/*     */       else {
/* 323 */         setIC_Output(0);
/*     */       }
/*     */     }
/*     */ 
/* 327 */     if (key == 101)
/*     */     {
/* 329 */       if (getUE_Output() == 0)
/* 330 */         setUE_Output(1);
/*     */       else {
/* 332 */         setUE_Output(0);
/*     */       }
/*     */     }
/*     */ 
/* 336 */     if (getIC_Output() == 0) {
/* 337 */       if (key == 200) {
/* 338 */         if (value.equalsIgnoreCase("+")) {
/* 339 */           switch (this.IC_Outputpacketsize) { case 1:
/* 340 */             this.IC_Outputpacketsize = 32; break;
/*     */           case 32:
/* 341 */             this.IC_Outputpacketsize = 128; break;
/*     */           case 128:
/* 342 */             this.IC_Outputpacketsize = 512; break;
/*     */           case 512:
/* 343 */             this.IC_Outputpacketsize = 2048; break;
/*     */           case 2048:
/* 344 */             this.IC_Outputpacketsize = 1;
/*     */           }
/*     */         }
/* 347 */         if (value.equalsIgnoreCase("-")) {
/* 348 */           switch (this.IC_Outputpacketsize) { case 1:
/* 349 */             this.IC_Outputpacketsize = 2048; break;
/*     */           case 32:
/* 350 */             this.IC_Outputpacketsize = 1; break;
/*     */           case 128:
/* 351 */             this.IC_Outputpacketsize = 32; break;
/*     */           case 512:
/* 352 */             this.IC_Outputpacketsize = 128; break;
/*     */           case 2048:
/* 353 */             this.IC_Outputpacketsize = 512;
/*     */           }
/*     */         }
/*     */       }
/* 357 */       if (key == 201) {
/* 358 */         if (value.equalsIgnoreCase("+")) {
/* 359 */           if (this.IC_Outputpacketamount == 9)
/* 360 */             this.IC_Outputpacketamount = 1;
/* 361 */           else this.IC_Outputpacketamount += 1;
/*     */         }
/* 363 */         if (value.equalsIgnoreCase("-")) {
/* 364 */           if (this.IC_Outputpacketamount == 1)
/* 365 */             this.IC_Outputpacketamount = 9;
/* 366 */           else this.IC_Outputpacketamount -= 1;
/*     */         }
/*     */       }
/*     */     }
/* 370 */     if (getUE_Output() == 0)
/*     */     {
/* 372 */       if (key == 202) {
/* 373 */         if (value.equalsIgnoreCase("+")) {
/* 374 */           switch (this.UE_Outputvoltage) { case 60:
/* 375 */             this.UE_Outputvoltage = 120; break;
/*     */           case 120:
/* 376 */             this.UE_Outputvoltage = 240; break;
/*     */           case 240:
/* 377 */             this.UE_Outputvoltage = 60;
/*     */           }
/*     */         }
/* 380 */         if (value.equalsIgnoreCase("-")) {
/* 381 */           switch (this.UE_Outputvoltage) { case 60:
/* 382 */             this.UE_Outputvoltage = 240; break;
/*     */           case 120:
/* 383 */             this.UE_Outputvoltage = 60; break;
/*     */           case 240:
/* 384 */             this.UE_Outputvoltage = 120;
/*     */           }
/*     */         }
/*     */       }
/* 388 */       if (key == 203) {
/* 389 */         if (value.equalsIgnoreCase("+")) {
/* 390 */           if (this.UE_Outputamp == 50)
/* 391 */             this.UE_Outputamp = 1;
/* 392 */           else this.UE_Outputamp += 1;
/*     */         }
/* 394 */         if (value.equalsIgnoreCase("-")) {
/* 395 */           if (this.UE_Outputamp == 1)
/* 396 */             this.UE_Outputamp = 50;
/* 397 */           else this.UE_Outputamp -= 1;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 404 */     super.onNetworkHandlerEvent(key, value);
/*     */   }
/*     */ 
/*     */   public void EmitUEPower(int volt, int amp)
/*     */   {
/* 409 */     if ((ModularForceFieldSystem.uefound.booleanValue()) && (hasPowerSource()))
/*     */     {
/* 411 */       ForgeDirection outputDirection = ForgeDirection.getOrientation(getSide());
/* 412 */       TileEntity outputTile = Vector3.getTileEntityFromSide(this.worldObj, new Vector3(this), outputDirection);
/* 413 */       ElectricityNetwork outputNetwork = ElectricityNetwork.getNetworkFromTileEntity(outputTile, outputDirection);
/*     */ 
/* 415 */       if (outputNetwork != null)
/*     */       {
/* 417 */         double outputWatts = Math.min(outputNetwork.getRequest().getWatts(), volt * amp);
/*     */ 
/* 419 */         if (consumePower((int)(ModularForceFieldSystem.ExtractorPassForceEnergyGenerate / 4000 * (outputWatts / 50.0D)), true))
/*     */         {
/* 421 */           if ((outputWatts > 0.0D) && (isActive()) && (getUE_Output() == 1)) {
/* 422 */             outputNetwork.startProducing(this, outputWatts / volt, volt);
/* 423 */             consumePower((int)(ModularForceFieldSystem.ExtractorPassForceEnergyGenerate / 4000 * (outputWatts / 50.0D)), false);
/*     */           } else {
/* 425 */             outputNetwork.stopProducing(this);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void EmitICpower(int amount, int packets)
/*     */   {
/* 435 */     if ((this.Industriecraftfound) && (hasPowerSource()))
/*     */     {
/* 437 */       while (packets > 0)
/*     */       {
/* 439 */         if (consumePower(ModularForceFieldSystem.ExtractorPassForceEnergyGenerate / 4000 * amount, true))
/*     */         {
/* 441 */           EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, amount);
/* 442 */           MinecraftForge.EVENT_BUS.post(event);
/* 443 */           consumePower(ModularForceFieldSystem.ExtractorPassForceEnergyGenerate / 4000 * (amount - event.amount), false);
/*     */         }
/* 445 */         packets--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 452 */     if (this.addedToEnergyNet) {
/* 453 */       MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
/* 454 */       this.addedToEnergyNet = false;
/*     */     }
/* 456 */     Linkgrid.getWorldMap(this.worldObj).getConverter().remove(Integer.valueOf(getDeviceID()));
/* 457 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public boolean isAddedToEnergyNet() {
/* 461 */     return this.addedToEnergyNet;
/*     */   }
/*     */ 
/*     */   public int getMaxEnergyOutput() {
/* 465 */     return 2147483647;
/*     */   }
/*     */ 
/*     */   public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
/*     */   {
/* 471 */     return receiver instanceof IEnergyAcceptor;
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer)
/*     */   {
/* 477 */     return new ContainerConverter(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 482 */     switch (Slot) {
/*     */     case 0:
/* 484 */       if (!(par1ItemStack.getItem() instanceof IPowerLinkItem)) {
/* 485 */         return false;
/*     */       }
/*     */       break;
/*     */     }
/* 489 */     return true;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int Slot)
/*     */   {
/* 494 */     return 1;
/*     */   }
/*     */ 
/*     */   public ItemStack getPowerLinkStack()
/*     */   {
/* 499 */     return getStackInSlot(getPowerlinkSlot());
/*     */   }
/*     */ 
/*     */   public int getPowerlinkSlot()
/*     */   {
/* 504 */     return 0;
/*     */   }
/*     */ 
/*     */   public short getmaxSwitchModi()
/*     */   {
/* 509 */     return 3;
/*     */   }
/*     */ 
/*     */   public short getminSwitchModi() {
/* 513 */     return 1;
/*     */   }
/*     */ 
/*     */   public void setUEwireConnection()
/*     */   {
/* 518 */     if (ModularForceFieldSystem.uefound.booleanValue())
/*     */     {
/* 520 */       ElectricityConnections.registerConnector(this, EnumSet.of(ForgeDirection.getOrientation(getFacing())));
/* 521 */       this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
/*     */     }
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/* 528 */     TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(this.worldObj).getCapacitor().get(Integer.valueOf(getPowerSourceID()));
/* 529 */     if (cap != null)
/*     */     {
/* 531 */       TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
/* 532 */       if (sec != null)
/*     */       {
/* 534 */         return sec;
/*     */       }
/*     */     }
/* 537 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityConverter
 * JD-Core Version:    0.6.2
 */