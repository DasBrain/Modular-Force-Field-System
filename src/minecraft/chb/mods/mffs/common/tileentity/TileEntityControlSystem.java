/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.MFFSMaschines;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import chb.mods.mffs.common.container.ContainerControlSystem;
/*     */ import chb.mods.mffs.common.item.ItemCardDataLink;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ import net.minecraftforge.common.ISidedInventory;
/*     */ 
/*     */ public class TileEntityControlSystem extends TileEntityMachines
/*     */   implements ISidedInventory
/*     */ {
/*  30 */   private TileEntityMachines remote = null;
/*  31 */   protected String RemoteDeviceName = "";
/*  32 */   protected String RemoteDeviceTyp = "";
/*  33 */   protected boolean RemoteActive = false;
/*  34 */   protected boolean RemoteSwitchValue = false;
/*  35 */   protected short RemoteSwitchModi = 0;
/*  36 */   protected boolean RemoteSecurityStationlink = false;
/*  37 */   protected boolean RemotehasPowersource = false;
/*  38 */   protected boolean RemoteGUIinRange = false;
/*  39 */   protected int RemotePowerleft = 0;
/*     */   private ItemStack[] inventory;
/*     */ 
/*     */   public TileEntityControlSystem()
/*     */   {
/*  44 */     this.inventory = new ItemStack[40];
/*     */   }
/*     */ 
/*     */   public List getFieldsforUpdate()
/*     */   {
/*  49 */     List NetworkedFields = new LinkedList();
/*  50 */     NetworkedFields.clear();
/*     */ 
/*  52 */     NetworkedFields.addAll(super.getFieldsforUpdate());
/*  53 */     NetworkedFields.add("RemoteDeviceName");
/*  54 */     NetworkedFields.add("RemoteDeviceTyp");
/*  55 */     NetworkedFields.add("RemoteActive");
/*  56 */     NetworkedFields.add("RemoteSwitchModi");
/*  57 */     NetworkedFields.add("RemoteSwitchValue");
/*  58 */     NetworkedFields.add("RemoteSecurityStationlink");
/*  59 */     NetworkedFields.add("RemotehasPowersource");
/*  60 */     NetworkedFields.add("RemotePowerleft");
/*  61 */     NetworkedFields.add("RemoteGUIinRange");
/*     */ 
/*  63 */     return NetworkedFields;
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/*  68 */     Linkgrid.getWorldMap(this.worldObj).getControlSystem().remove(Integer.valueOf(getDeviceID()));
/*  69 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public void dropplugins() {
/*  73 */     for (int a = 0; a < this.inventory.length; a++)
/*  74 */       dropplugins(a, this);
/*     */   }
/*     */ 
/*     */   public void updateEntity()
/*     */   {
/*  80 */     if (!this.worldObj.isRemote)
/*     */     {
/*  82 */       if (getTicker() == 20)
/*     */       {
/*  84 */         if ((getLinkedSecurityStation() != null) && (!isActive()))
/*  85 */           setActive(true);
/*  86 */         if ((getLinkedSecurityStation() == null) && (isActive())) {
/*  87 */           setActive(false);
/*     */         }
/*  89 */         refreshRemoteData();
/*     */ 
/*  91 */         setTicker((short)0);
/*     */       }
/*  93 */       setTicker((short)(getTicker() + 1));
/*     */     }
/*     */ 
/*  96 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   public TileEntityMachines getRemote() {
/* 100 */     return this.remote;
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer) {
/* 104 */     return new ContainerControlSystem(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/* 109 */     return ItemCardSecurityLink.getLinkedSecurityStation(this, 0, this.worldObj);
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 114 */     super.readFromNBT(nbttagcompound);
/* 115 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/* 116 */     this.inventory = new ItemStack[getSizeInventory()];
/* 117 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 118 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*     */ 
/* 120 */       byte byte0 = nbttagcompound1.getByte("Slot");
/* 121 */       if ((byte0 >= 0) && (byte0 < this.inventory.length))
/* 122 */         this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 129 */     super.writeToNBT(nbttagcompound);
/* 130 */     NBTTagList nbttaglist = new NBTTagList();
/* 131 */     for (int i = 0; i < this.inventory.length; i++) {
/* 132 */       if (this.inventory[i] != null) {
/* 133 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 134 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 135 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 136 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 140 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 146 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i) {
/* 150 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   public int getInventoryStackLimit() {
/* 154 */     return 1;
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j) {
/* 158 */     if (this.inventory[i] != null) {
/* 159 */       if (this.inventory[i].stackSize <= j) {
/* 160 */         ItemStack itemstack = this.inventory[i];
/* 161 */         this.inventory[i] = null;
/* 162 */         return itemstack;
/*     */       }
/* 164 */       ItemStack itemstack1 = this.inventory[i].splitStack(j);
/* 165 */       if (this.inventory[i].stackSize == 0) {
/* 166 */         this.inventory[i] = null;
/*     */       }
/* 168 */       return itemstack1;
/*     */     }
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack)
/*     */   {
/* 175 */     this.inventory[i] = itemstack;
/* 176 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/* 177 */       itemstack.stackSize = getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   public String getInvName()
/*     */   {
/* 182 */     return "ControlSystem";
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 188 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 193 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int slt)
/*     */   {
/* 199 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 207 */     switch (Slot)
/*     */     {
/*     */     case 0:
/* 210 */       if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink)) return true;
/*     */ 
/*     */       break;
/*     */     }
/*     */ 
/* 215 */     if ((par1ItemStack.getItem() instanceof ItemCardDataLink)) return true;
/*     */ 
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerEvent(int key, String value)
/*     */   {
/* 224 */     if ((key == 103) && 
/* 225 */       (this.remote != null) && 
/* 226 */       (getRemoteGUIinRange())) {
/* 227 */       EntityPlayer player = this.worldObj.getPlayerEntityByName(value);
/* 228 */       if (player != null)
/*     */       {
/* 231 */         player.openGui(ModularForceFieldSystem.instance, 0, this.worldObj, this.remote.xCoord, this.remote.yCoord, this.remote.zCoord);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     if ((key == 102) && 
/* 238 */       (this.remote != null)) {
/* 239 */       this.remote.toggelSwitchValue();
/*     */     }
/*     */ 
/* 242 */     if ((key == 101) && 
/* 243 */       (this.remote != null)) {
/* 244 */       this.remote.toogleSwitchModi();
/*     */     }
/*     */ 
/* 249 */     super.onNetworkHandlerEvent(key, value);
/*     */   }
/*     */ 
/*     */   private void refreshRemoteData()
/*     */   {
/* 256 */     refreshRemoteData(1);
/*     */   }
/*     */ 
/*     */   private void refreshRemoteData(int slot)
/*     */   {
/* 262 */     this.remote = getTargetMaschine(slot);
/*     */ 
/* 264 */     if (this.remote != null)
/*     */     {
/* 266 */       if ((!this.remote.isActive()) == getRemoteActive()) {
/* 267 */         setRemoteActive(this.remote.isActive());
/*     */       }
/* 269 */       if (!this.remote.getDeviceName().equalsIgnoreCase(getRemoteDeviceName())) {
/* 270 */         setRemoteDeviceName(this.remote.getDeviceName());
/*     */       }
/* 272 */       if (this.remote.getSwitchModi() != getRemoteSwitchModi()) {
/* 273 */         setRemoteSwitchModi(this.remote.getSwitchModi());
/*     */       }
/* 275 */       if ((!this.remote.getSwitchValue()) == getRemoteSwitchValue()) {
/* 276 */         setRemoteSwitchValue(this.remote.getSwitchValue());
/*     */       }
/* 278 */       if (this.remote.getLinkedSecurityStation() == null)
/* 279 */         setRemoteSecurityStationlink(false);
/* 280 */       else setRemoteSecurityStationlink(true);
/*     */ 
/* 283 */       if ((!this.remote.hasPowerSource()) == getRemotehasPowersource()) {
/* 284 */         setRemotehasPowersource(this.remote.hasPowerSource());
/*     */       }
/* 286 */       if (this.remote.getPercentageCapacity() != getRemotePowerleft()) {
/* 287 */         setRemotePowerleft(this.remote.getPercentageCapacity());
/*     */       }
/* 289 */       if (!MFFSMaschines.fromTE(this.remote).displayName.equalsIgnoreCase(getRemoteDeviceTyp())) {
/* 290 */         setRemoteDeviceTyp(MFFSMaschines.fromTE(this.remote).displayName);
/*     */       }
/*     */ 
/* 293 */       if ((PointXYZ.distance(getMaschinePoint(), this.remote.getMaschinePoint()) > 61.0D) && (getRemoteGUIinRange())) {
/* 294 */         setRemoteGUIinRange(false);
/*     */       }
/*     */ 
/* 297 */       if ((PointXYZ.distance(getMaschinePoint(), this.remote.getMaschinePoint()) <= 61.0D) && (!getRemoteGUIinRange())) {
/* 298 */         setRemoteGUIinRange(true);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 304 */       if (getRemoteActive()) {
/* 305 */         setRemoteActive(false);
/*     */       }
/* 307 */       if (getRemoteSwitchModi() != 0) {
/* 308 */         setRemoteSwitchModi((short)0);
/*     */       }
/* 310 */       if (!getRemoteDeviceName().equalsIgnoreCase("-")) {
/* 311 */         setRemoteDeviceName("-");
/*     */       }
/* 313 */       if (!getRemoteDeviceTyp().equalsIgnoreCase("-"))
/* 314 */         setRemoteDeviceTyp("-");
/*     */     }
/*     */   }
/*     */ 
/*     */   private TileEntityMachines getTargetMaschine(int slot)
/*     */   {
/* 324 */     if ((getStackInSlot(slot) != null) && 
/* 325 */       ((getStackInSlot(slot).getItem() instanceof ItemCardDataLink)))
/*     */     {
/* 327 */       int DeviceID = 0;
/* 328 */       NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(getStackInSlot(slot));
/* 329 */       if (tag.hasKey("DeviceID")) {
/* 330 */         DeviceID = tag.getInteger("DeviceID");
/*     */       }
/* 332 */       if (DeviceID != 0)
/*     */       {
/* 334 */         TileEntityMachines device = Linkgrid.getWorldMap(this.worldObj).getTileEntityMachines(ItemCardDataLink.getDeviceTyp(getStackInSlot(slot)), DeviceID);
/* 335 */         if (device != null)
/* 336 */           return device;
/*     */       }
/* 338 */       setInventorySlotContents(slot, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
/*     */     }
/* 340 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean getRemoteGUIinRange()
/*     */   {
/* 347 */     return this.RemoteGUIinRange;
/*     */   }
/*     */ 
/*     */   public void setRemoteGUIinRange(boolean b) {
/* 351 */     this.RemoteGUIinRange = b;
/* 352 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteGUIinRange");
/*     */   }
/*     */ 
/*     */   public int getRemotePowerleft() {
/* 356 */     return this.RemotePowerleft;
/*     */   }
/*     */ 
/*     */   public void setRemotePowerleft(int i) {
/* 360 */     this.RemotePowerleft = i;
/* 361 */     NetworkHandlerServer.updateTileEntityField(this, "RemotePowerleft");
/*     */   }
/*     */ 
/*     */   public boolean getRemotehasPowersource() {
/* 365 */     return this.RemotehasPowersource;
/*     */   }
/*     */ 
/*     */   public void setRemotehasPowersource(boolean b) {
/* 369 */     this.RemotehasPowersource = b;
/* 370 */     NetworkHandlerServer.updateTileEntityField(this, "RemotehasPowersource");
/*     */   }
/*     */ 
/*     */   public boolean getRemoteSecurityStationlink() {
/* 374 */     return this.RemoteSecurityStationlink;
/*     */   }
/*     */ 
/*     */   public void setRemoteSecurityStationlink(boolean b) {
/* 378 */     this.RemoteSecurityStationlink = b;
/* 379 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteSecurityStationlink");
/*     */   }
/*     */ 
/*     */   public boolean getRemoteSwitchValue()
/*     */   {
/* 384 */     return this.RemoteSwitchValue;
/*     */   }
/*     */ 
/*     */   public void setRemoteSwitchValue(boolean b) {
/* 388 */     this.RemoteSwitchValue = b;
/* 389 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteSwitchValue");
/*     */   }
/*     */ 
/*     */   public short getRemoteSwitchModi()
/*     */   {
/* 394 */     return this.RemoteSwitchModi;
/*     */   }
/*     */ 
/*     */   public void setRemoteSwitchModi(short s) {
/* 398 */     this.RemoteSwitchModi = s;
/* 399 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteSwitchModi");
/*     */   }
/*     */ 
/*     */   public boolean getRemoteActive()
/*     */   {
/* 404 */     return this.RemoteActive;
/*     */   }
/*     */ 
/*     */   public void setRemoteActive(boolean b) {
/* 408 */     this.RemoteActive = b;
/* 409 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteActive");
/*     */   }
/*     */ 
/*     */   public String getRemoteDeviceTyp()
/*     */   {
/* 414 */     return this.RemoteDeviceTyp;
/*     */   }
/*     */ 
/*     */   public void setRemoteDeviceTyp(String s) {
/* 418 */     this.RemoteDeviceTyp = s;
/* 419 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteDeviceTyp");
/*     */   }
/*     */ 
/*     */   public String getRemoteDeviceName()
/*     */   {
/* 424 */     return this.RemoteDeviceName;
/*     */   }
/*     */ 
/*     */   public void setRemoteDeviceName(String s) {
/* 428 */     this.RemoteDeviceName = s;
/* 429 */     NetworkHandlerServer.updateTileEntityField(this, "RemoteDeviceName");
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityControlSystem
 * JD-Core Version:    0.6.2
 */