/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.container.ContainerAdvSecurityStation;
/*     */ import chb.mods.mffs.common.item.ItemAccessCard;
/*     */ import chb.mods.mffs.common.item.ItemCardEmpty;
/*     */ import chb.mods.mffs.common.item.ItemCardPersonalID;
/*     */ import chb.mods.mffs.common.item.ItemCardPowerLink;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.common.multitool.ItemDebugger;
/*     */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ 
/*     */ public class TileEntityAdvSecurityStation extends TileEntityMachines
/*     */ {
/*     */   private String MainUser;
/*     */   private ItemStack[] inventory;
/*     */ 
/*     */   public TileEntityAdvSecurityStation()
/*     */   {
/*  57 */     this.inventory = new ItemStack[40];
/*  58 */     this.MainUser = "";
/*     */   }
/*     */ 
/*     */   public void dropplugins()
/*     */   {
/*  64 */     for (int a = 0; a < this.inventory.length; a++)
/*  65 */       dropplugins(a);
/*     */   }
/*     */ 
/*     */   public String getMainUser()
/*     */   {
/*  70 */     return this.MainUser;
/*     */   }
/*     */ 
/*     */   public void setMainUser(String s) {
/*  74 */     if (!this.MainUser.equals(s)) {
/*  75 */       this.MainUser = s;
/*  76 */       NetworkHandlerServer.updateTileEntityField(this, "MainUser");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dropplugins(int slot)
/*     */   {
/*  82 */     if (getStackInSlot(slot) != null) {
/*  83 */       if (((getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink)) || ((getStackInSlot(slot).getItem() instanceof ItemCardPowerLink)) || ((getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)))
/*     */       {
/*  86 */         this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1)));
/*     */       }
/*     */       else
/*     */       {
/*  91 */         this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, getStackInSlot(slot)));
/*     */       }
/*     */ 
/*  96 */       setInventorySlotContents(slot, null);
/*  97 */       onInventoryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer) {
/* 102 */     return new ContainerAdvSecurityStation(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 108 */     Linkgrid.getWorldMap(this.worldObj).getSecStation().remove(Integer.valueOf(getDeviceID()));
/* 109 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 113 */     super.readFromNBT(nbttagcompound);
/*     */ 
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
/*     */ 
/* 131 */     NBTTagList nbttaglist = new NBTTagList();
/* 132 */     for (int i = 0; i < this.inventory.length; i++) {
/* 133 */       if (this.inventory[i] != null) {
/* 134 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 135 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 136 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 137 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 141 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public void updateEntity() {
/* 145 */     if (!this.worldObj.isRemote)
/*     */     {
/* 147 */       if (getTicker() == 10)
/*     */       {
/* 149 */         if (!getMainUser().equals("")) {
/* 150 */           if (isActive() != true) {
/* 151 */             setActive(true);
/*     */           }
/*     */         }
/* 154 */         else if (isActive()) {
/* 155 */           setActive(false);
/*     */         }
/*     */ 
/* 158 */         checkslots();
/* 159 */         setTicker((short)0);
/*     */       }
/* 161 */       setTicker((short)(getTicker() + 1));
/*     */     }
/*     */ 
/* 164 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   public void checkslots() {
/* 168 */     if (getStackInSlot(0) != null) {
/* 169 */       if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSItemIDCard) {
/* 170 */         ItemCardPersonalID Card = (ItemCardPersonalID)getStackInSlot(0).getItem();
/*     */ 
/* 173 */         String name = Card.getUsername(getStackInSlot(0));
/*     */ 
/* 177 */         if (!getMainUser().equals(name)) {
/* 178 */           setMainUser(name);
/*     */         }
/*     */ 
/* 181 */         if (ItemCardPersonalID.hasRight(getStackInSlot(0), SecurityRight.CSR) != true)
/* 182 */           ItemCardPersonalID.setRight(getStackInSlot(0), SecurityRight.CSR, true);
/*     */       }
/*     */       else
/*     */       {
/* 186 */         setMainUser("");
/*     */       }
/*     */     }
/*     */     else {
/* 190 */       setMainUser("");
/*     */     }
/*     */ 
/* 195 */     if ((getStackInSlot(39) != null) && (getStackInSlot(38) != null) && 
/* 196 */       ((getStackInSlot(38).getItem() instanceof ItemCardEmpty)) && ((getStackInSlot(39).getItem() instanceof ItemCardPersonalID)))
/*     */     {
/* 199 */       setInventorySlotContents(38, ItemStack.copyItemStack(getStackInSlot(39)));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 208 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i) {
/* 212 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   public int getInventoryStackLimit() {
/* 216 */     return 1;
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j) {
/* 220 */     if (this.inventory[i] != null) {
/* 221 */       if (this.inventory[i].stackSize <= j) {
/* 222 */         ItemStack itemstack = this.inventory[i];
/* 223 */         this.inventory[i] = null;
/* 224 */         return itemstack;
/*     */       }
/* 226 */       ItemStack itemstack1 = this.inventory[i].splitStack(j);
/* 227 */       if (this.inventory[i].stackSize == 0) {
/* 228 */         this.inventory[i] = null;
/*     */       }
/* 230 */       return itemstack1;
/*     */     }
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack)
/*     */   {
/* 237 */     this.inventory[i] = itemstack;
/* 238 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/* 239 */       itemstack.stackSize = getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   public String getInvName()
/*     */   {
/* 244 */     return "Secstation";
/*     */   }
/*     */ 
/*     */   public boolean RemoteInventory(String username, SecurityRight right)
/*     */   {
/* 250 */     for (int a = 35; a >= 1; a--) {
/* 251 */       if ((getStackInSlot(a) != null) && 
/* 252 */         (getStackInSlot(a).getItem() == ModularForceFieldSystem.MFFSItemIDCard)) {
/* 253 */         String username_invtory = NBTTagCompoundHelper.getTAGfromItemstack(getStackInSlot(a)).getString("name");
/*     */ 
/* 255 */         ItemCardPersonalID Card = (ItemCardPersonalID)getStackInSlot(a).getItem();
/*     */ 
/* 257 */         boolean access = ItemCardPersonalID.hasRight(getStackInSlot(a), right);
/*     */ 
/* 259 */         if (username_invtory.equals(username)) {
/* 260 */           if (access) {
/* 261 */             return true;
/*     */           }
/* 263 */           return false;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean RemotePlayerInventory(String username, SecurityRight right)
/*     */   {
/* 275 */     EntityPlayer player = this.worldObj.getPlayerEntityByName(username);
/* 276 */     if (player != null) {
/* 277 */       List slots = player.inventoryContainer.inventorySlots;
/* 278 */       for (Slot slot : slots) {
/* 279 */         ItemStack stack = slot.getStack();
/* 280 */         if (stack != null) {
/* 281 */           if ((stack.getItem() instanceof ItemAccessCard)) {
/* 282 */             ((ItemAccessCard)stack.getItem()); if (ItemAccessCard.getvalidity(stack) > 0) {
/* 283 */               ((ItemAccessCard)stack.getItem()); if (ItemAccessCard.getlinkID(stack) == getDeviceID()) {
/* 284 */                 ((ItemAccessCard)stack.getItem()); if (ItemAccessCard.hasRight(stack, right))
/*     */                 {
/* 286 */                   ((ItemAccessCard)stack.getItem()); if (!ItemAccessCard.getforAreaname(stack).equals(getDeviceName())) {
/* 287 */                     ((ItemAccessCard)stack.getItem()); ItemAccessCard.setforArea(stack, this);
/*     */                   }
/* 289 */                   return true;
/*     */                 }
/*     */               }
/*     */             } else {
/* 293 */               player.sendChatToPlayer("[Security Station] expired validity <Access license>");
/* 294 */               ItemStack Card = new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1);
/* 295 */               slot.putStack(Card);
/* 296 */               NetworkHandlerServer.syncClientPlayerinventorySlot(player, slot, Card);
/*     */             }
/*     */           }
/*     */ 
/* 300 */           if ((stack.getItem() instanceof ItemDebugger)) {
/* 301 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 307 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isAccessGranted(String username, SecurityRight sr)
/*     */   {
/* 312 */     if (!isActive()) {
/* 313 */       return true;
/*     */     }
/* 315 */     String[] ops = ModularForceFieldSystem.Admin.split(";");
/*     */ 
/* 317 */     for (int i = 0; i <= ops.length - 1; i++) {
/* 318 */       if (username.equalsIgnoreCase(ops[i])) {
/* 319 */         return true;
/*     */       }
/*     */     }
/* 322 */     if (this.MainUser.equals(username)) {
/* 323 */       return true;
/*     */     }
/* 325 */     if (RemoteInventory(username, sr)) {
/* 326 */       return true;
/*     */     }
/* 328 */     if (RemotePlayerInventory(username, sr)) {
/* 329 */       return true;
/*     */     }
/*     */ 
/* 332 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents() {
/* 336 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 341 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 346 */     return 0;
/*     */   }
/*     */ 
/*     */   public List getFieldsforUpdate()
/*     */   {
/* 354 */     List NetworkedFields = new LinkedList();
/* 355 */     NetworkedFields.clear();
/*     */ 
/* 357 */     NetworkedFields.addAll(super.getFieldsforUpdate());
/* 358 */     NetworkedFields.add("MainUser");
/*     */ 
/* 360 */     return NetworkedFields;
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 366 */     switch (Slot)
/*     */     {
/*     */     case 38:
/* 369 */       if (!(par1ItemStack.getItem() instanceof ItemCardEmpty)) return false;
/*     */       break;
/*     */     case 39:
/* 372 */       if (((par1ItemStack.getItem() instanceof ItemAccessCard)) || (!(par1ItemStack.getItem() instanceof ItemCardPersonalID))) return false;
/*     */ 
/*     */       break;
/*     */     }
/*     */ 
/* 377 */     if (((par1ItemStack.getItem() instanceof ItemCardPersonalID)) || ((par1ItemStack.getItem() instanceof ItemCardEmpty))) {
/* 378 */       return true;
/*     */     }
/* 380 */     return false;
/*     */   }
/*     */ 
/*     */   public void onNetworkHandlerEvent(int key, String value)
/*     */   {
/* 388 */     switch (key) {
/*     */     case 100:
/* 390 */       if (getStackInSlot(1) != null) {
/* 391 */         SecurityRight sr = (SecurityRight)SecurityRight.rights.get(value);
/* 392 */         if ((sr != null) && ((getStackInSlot(1).getItem() instanceof ItemCardPersonalID)))
/* 393 */           ItemCardPersonalID.setRight(getStackInSlot(1), sr, !ItemCardPersonalID.hasRight(getStackInSlot(1), sr));
/*     */       }
/* 395 */       break;
/*     */     case 101:
/* 398 */       if ((getStackInSlot(1) != null) && 
/* 399 */         ((getStackInSlot(1).getItem() instanceof ItemAccessCard)))
/* 400 */         if (ItemAccessCard.getvalidity(getStackInSlot(1)) <= 5)
/* 401 */           setInventorySlotContents(1, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1));
/*     */         else
/* 403 */           ItemAccessCard.setvalidity(getStackInSlot(1), ItemAccessCard.getvalidity(getStackInSlot(1)) - 5); 
/* 403 */       break;
/*     */     case 102:
/* 409 */       if (getStackInSlot(1) != null) {
/* 410 */         if ((getStackInSlot(1).getItem() instanceof ItemCardEmpty)) {
/* 411 */           setInventorySlotContents(1, new ItemStack(ModularForceFieldSystem.MFFSAccessCard, 1));
/* 412 */           if ((getStackInSlot(1).getItem() instanceof ItemAccessCard)) {
/* 413 */             ItemAccessCard.setforArea(getStackInSlot(1), this);
/* 414 */             ItemAccessCard.setvalidity(getStackInSlot(1), 5);
/* 415 */             ItemAccessCard.setlinkID(getStackInSlot(1), this);
/*     */           }
/*     */ 
/*     */         }
/* 419 */         else if ((getStackInSlot(1).getItem() instanceof ItemAccessCard))
/*     */         {
/* 421 */           ItemAccessCard.setvalidity(getStackInSlot(1), ItemAccessCard.getvalidity(getStackInSlot(1)) + 5);
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/*     */ 
/* 427 */     super.onNetworkHandlerEvent(key, value);
/*     */   }
/*     */ 
/*     */   public ItemStack getModCardStack() {
/* 431 */     if (getStackInSlot(1) != null) {
/* 432 */       return getStackInSlot(1);
/*     */     }
/* 434 */     return null;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int slt)
/*     */   {
/* 440 */     return 1;
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/* 446 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation
 * JD-Core Version:    0.6.2
 */