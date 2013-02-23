/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.IMFFS_Wrench;
/*     */ import chb.mods.mffs.api.ISwitchabel;
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.IModularProjector.Slots;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.item.ItemCardDataLink;
/*     */ import chb.mods.mffs.common.item.ItemCardPersonalID;
/*     */ import chb.mods.mffs.common.item.ItemCardPowerLink;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.network.INetworkHandlerEventListener;
/*     */ import chb.mods.mffs.network.INetworkHandlerListener;
/*     */ import chb.mods.mffs.network.client.NetworkHandlerClient;
/*     */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*     */ import ic2.api.IWrenchable;
/*     */ import java.io.PrintStream;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeChunkManager;
/*     */ import net.minecraftforge.common.ForgeChunkManager.Ticket;
/*     */ import net.minecraftforge.common.ForgeChunkManager.Type;
/*     */ import net.minecraftforge.common.ISidedInventory;
/*     */ 
/*     */ public abstract class TileEntityMachines extends TileEntity
/*     */   implements INetworkHandlerListener, INetworkHandlerEventListener, ISidedInventory, IMFFS_Wrench, IWrenchable, ISwitchabel
/*     */ {
/*     */   private boolean Active;
/*     */   private int Side;
/*     */   private short ticker;
/*     */   protected boolean init;
/*     */   protected String DeviceName;
/*     */   protected int DeviceID;
/*     */   protected short SwitchModi;
/*     */   protected boolean SwitchValue;
/*  70 */   protected Random random = new Random();
/*     */   protected ForgeChunkManager.Ticket chunkTicket;
/*     */ 
/*     */   public TileEntityMachines()
/*     */   {
/*  75 */     this.Active = false;
/*  76 */     this.SwitchValue = false;
/*  77 */     this.init = true;
/*  78 */     this.Side = -1;
/*  79 */     this.SwitchModi = 0;
/*  80 */     this.ticker = 0;
/*  81 */     this.DeviceID = 0;
/*  82 */     this.DeviceName = "Please set Name";
/*     */   }
/*     */ 
/*     */   public int getPercentageCapacity()
/*     */   {
/*  88 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean hasPowerSource() {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract TileEntityAdvSecurityStation getLinkedSecurityStation();
/*     */ 
/*     */   public void onNetworkHandlerEvent(int key, String value)
/*     */   {
/* 100 */     switch (key) {
/*     */     case 0:
/* 102 */       toogleSwitchModi();
/* 103 */       break;
/*     */     case 10:
/* 106 */       setDeviceName("");
/* 107 */       break;
/*     */     case 11:
/* 109 */       if (getDeviceName().length() <= 20)
/* 110 */         setDeviceName(getDeviceName() + value); break;
/*     */     case 12:
/* 113 */       if (getDeviceName().length() >= 1)
/* 114 */         setDeviceName(getDeviceName().substring(0, getDeviceName().length() - 1));
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public List getFieldsforUpdate()
/*     */   {
/* 123 */     List NetworkedFields = new LinkedList();
/* 124 */     NetworkedFields.clear();
/*     */ 
/* 126 */     NetworkedFields.add("Active");
/* 127 */     NetworkedFields.add("Side");
/* 128 */     NetworkedFields.add("DeviceID");
/* 129 */     NetworkedFields.add("DeviceName");
/* 130 */     NetworkedFields.add("SwitchModi");
/* 131 */     NetworkedFields.add("SwitchValue");
/*     */ 
/* 133 */     return NetworkedFields;
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerUpdate(String field)
/*     */   {
/* 138 */     this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
/*     */   }
/*     */ 
/*     */   public void updateEntity()
/*     */   {
/* 143 */     if ((!this.worldObj.isRemote) && 
/* 144 */       (this.init)) init();
/*     */ 
/* 146 */     if ((this.worldObj.isRemote) && 
/* 147 */       (this.DeviceID == 0)) {
/* 148 */       if (getTicker() >= 5 + this.random.nextInt(20)) {
/* 149 */         NetworkHandlerClient.requestInitialData(this, true);
/* 150 */         setTicker((short)0);
/*     */       }
/* 152 */       setTicker((short)(getTicker() + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 158 */     this.DeviceID = Linkgrid.getWorldMap(this.worldObj).refreshID(this, this.DeviceID);
/* 159 */     if (ModularForceFieldSystem.chunckloader)
/* 160 */       registerChunkLoading();
/* 161 */     this.init = false;
/*     */   }
/*     */ 
/*     */   public short getmaxSwitchModi() {
/* 165 */     return 0;
/*     */   }
/*     */ 
/*     */   public short getminSwitchModi() {
/* 169 */     return 0;
/*     */   }
/*     */ 
/*     */   public void toogleSwitchModi()
/*     */   {
/* 174 */     if (getSwitchModi() == getmaxSwitchModi())
/*     */     {
/* 176 */       this.SwitchModi = getminSwitchModi();
/*     */     }
/* 178 */     else this.SwitchModi = ((short)(this.SwitchModi + 1));
/*     */ 
/* 180 */     NetworkHandlerServer.updateTileEntityField(this, "SwitchModi");
/*     */   }
/*     */ 
/*     */   public boolean isRedstoneSignal() {
/* 184 */     if ((this.worldObj.isBlockGettingPowered(this.xCoord, this.yCoord, this.zCoord)) || (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)))
/*     */     {
/* 186 */       return true;
/* 187 */     }return false;
/*     */   }
/*     */ 
/*     */   public short getSwitchModi() {
/* 191 */     if (this.SwitchModi < getminSwitchModi())
/* 192 */       this.SwitchModi = getminSwitchModi();
/* 193 */     return this.SwitchModi;
/*     */   }
/*     */ 
/*     */   public boolean getSwitchValue() {
/* 197 */     return this.SwitchValue;
/*     */   }
/*     */ 
/*     */   public boolean isSwitchabel()
/*     */   {
/* 202 */     if (getSwitchModi() == 2)
/* 203 */       return true;
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   public void toggelSwitchValue()
/*     */   {
/* 209 */     this.SwitchValue = (!this.SwitchValue);
/* 210 */     NetworkHandlerServer.updateTileEntityField(this, "SwitchValue");
/*     */   }
/*     */ 
/*     */   public void setDeviceName(String DeviceName) {
/* 214 */     this.DeviceName = DeviceName;
/* 215 */     NetworkHandlerServer.updateTileEntityField(this, "DeviceName");
/*     */   }
/*     */ 
/*     */   public int getDeviceID() {
/* 219 */     return this.DeviceID;
/*     */   }
/*     */ 
/*     */   public void setDeviceID(int i) {
/* 223 */     this.DeviceID = i;
/*     */   }
/*     */ 
/*     */   public String getDeviceName() {
/* 227 */     return this.DeviceName;
/*     */   }
/*     */ 
/*     */   public PointXYZ getMaschinePoint()
/*     */   {
/* 232 */     return new PointXYZ(this.xCoord, this.yCoord, this.zCoord, this.worldObj);
/*     */   }
/*     */ 
/*     */   public abstract void dropplugins();
/*     */ 
/*     */   public void dropplugins(int slot, IInventory inventory)
/*     */   {
/* 240 */     if (this.worldObj.isRemote) {
/* 241 */       setInventorySlotContents(slot, null);
/* 242 */       return;
/*     */     }
/*     */ 
/* 245 */     if (inventory.getStackInSlot(slot) != null) {
/* 246 */       if (((inventory.getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink)) || ((inventory.getStackInSlot(slot).getItem() instanceof ItemCardPowerLink)) || ((inventory.getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)) || ((inventory.getStackInSlot(slot).getItem() instanceof ItemCardDataLink)))
/*     */       {
/* 251 */         this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1)));
/*     */       }
/*     */       else
/*     */       {
/* 256 */         this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, inventory.getStackInSlot(slot)));
/*     */       }
/*     */ 
/* 261 */       inventory.setInventorySlotContents(slot, null);
/* 262 */       onInventoryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract Container getContainer(InventoryPlayer paramInventoryPlayer);
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 269 */     super.readFromNBT(nbttagcompound);
/* 270 */     this.Side = nbttagcompound.getInteger("side");
/* 271 */     this.Active = nbttagcompound.getBoolean("active");
/* 272 */     this.SwitchValue = nbttagcompound.getBoolean("SwitchValue");
/* 273 */     this.DeviceID = nbttagcompound.getInteger("DeviceID");
/* 274 */     this.DeviceName = nbttagcompound.getString("DeviceName");
/* 275 */     this.SwitchModi = nbttagcompound.getShort("SwitchModi");
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound) {
/* 279 */     super.writeToNBT(nbttagcompound);
/*     */ 
/* 281 */     nbttagcompound.setShort("SwitchModi", this.SwitchModi);
/* 282 */     nbttagcompound.setInteger("side", this.Side);
/* 283 */     nbttagcompound.setBoolean("active", this.Active);
/* 284 */     nbttagcompound.setBoolean("SwitchValue", this.SwitchValue);
/* 285 */     nbttagcompound.setInteger("DeviceID", this.DeviceID);
/* 286 */     nbttagcompound.setString("DeviceName", this.DeviceName);
/*     */   }
/*     */ 
/*     */   public boolean wrenchCanManipulate(EntityPlayer entityPlayer, int side)
/*     */   {
/* 291 */     if (!SecurityHelper.isAccessGranted(this, entityPlayer, this.worldObj, SecurityRight.EB)) {
/* 292 */       return false;
/*     */     }
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */   public short getTicker() {
/* 298 */     return this.ticker;
/*     */   }
/*     */ 
/*     */   public void setTicker(short ticker) {
/* 302 */     this.ticker = ticker;
/*     */   }
/*     */ 
/*     */   public void setSide(int i) {
/* 306 */     this.Side = i;
/* 307 */     NetworkHandlerServer.updateTileEntityField(this, "Side");
/*     */   }
/*     */ 
/*     */   public boolean isActive() {
/* 311 */     return this.Active;
/*     */   }
/*     */ 
/*     */   public void setActive(boolean flag) {
/* 315 */     this.Active = flag;
/* 316 */     NetworkHandlerServer.updateTileEntityField(this, "Active");
/*     */   }
/*     */ 
/*     */   public int getSide()
/*     */   {
/* 321 */     return this.Side;
/*     */   }
/*     */ 
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
/*     */   {
/* 326 */     if (side == getFacing()) return false;
/* 327 */     if ((this instanceof TileEntitySecStorage)) return false;
/* 328 */     if ((this instanceof TileEntityAdvSecurityStation)) return false;
/* 329 */     if (this.Active) return false;
/*     */ 
/* 331 */     return wrenchCanManipulate(entityPlayer, side);
/*     */   }
/*     */ 
/*     */   public short getFacing()
/*     */   {
/* 337 */     return (short)getSide();
/*     */   }
/*     */ 
/*     */   public void setFacing(short facing)
/*     */   {
/* 342 */     setSide(facing);
/*     */   }
/*     */ 
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer)
/*     */   {
/* 347 */     if (this.Active) return false;
/* 348 */     return wrenchCanManipulate(entityPlayer, 0);
/*     */   }
/*     */ 
/*     */   public float getWrenchDropRate()
/*     */   {
/* 353 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   public void forceChunkLoading(ForgeChunkManager.Ticket ticket) {
/* 357 */     if (this.chunkTicket == null)
/*     */     {
/* 359 */       this.chunkTicket = ticket;
/*     */     }
/* 361 */     ChunkCoordIntPair Chunk = new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4);
/* 362 */     ForgeChunkManager.forceChunk(ticket, Chunk);
/*     */   }
/*     */ 
/*     */   protected void registerChunkLoading()
/*     */   {
/* 367 */     if (this.chunkTicket == null)
/*     */     {
/* 369 */       this.chunkTicket = ForgeChunkManager.requestTicket(ModularForceFieldSystem.instance, this.worldObj, ForgeChunkManager.Type.NORMAL);
/*     */     }
/* 371 */     if (this.chunkTicket == null)
/*     */     {
/* 373 */       System.out.println("[ModularForceFieldSystem]no free Chunkloaders available");
/* 374 */       return;
/*     */     }
/*     */ 
/* 377 */     this.chunkTicket.getModData().setInteger("MaschineX", this.xCoord);
/* 378 */     this.chunkTicket.getModData().setInteger("MaschineY", this.yCoord);
/* 379 */     this.chunkTicket.getModData().setInteger("MaschineZ", this.zCoord);
/* 380 */     ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
/*     */ 
/* 382 */     forceChunkLoading(this.chunkTicket);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 387 */     ForgeChunkManager.releaseTicket(this.chunkTicket);
/* 388 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public abstract boolean isItemValid(ItemStack paramItemStack, int paramInt);
/*     */ 
/*     */   public abstract int getSlotStackLimit(int paramInt);
/*     */ 
/*     */   public void openChest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void closeChest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/*     */   {
/* 407 */     if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
/* 408 */       return false;
/*     */     }
/* 410 */     return entityplayer.getDistance(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
/*     */   }
/*     */ 
/*     */   public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
/*     */   {
/* 418 */     return new ItemStack(net.minecraft.block.Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)]);
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlotOnClosing(int var1)
/*     */   {
/* 423 */     return null;
/*     */   }
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 428 */     return 64;
/*     */   }
/*     */ 
/*     */   public int countItemsInSlot(IModularProjector.Slots slt) {
/* 432 */     if (getStackInSlot(slt.slot) != null)
/* 433 */       return getStackInSlot(slt.slot).stackSize;
/* 434 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityMachines
 * JD-Core Version:    0.6.2
 */