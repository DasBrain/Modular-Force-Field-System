/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.container.ContainerSecStorage;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ import net.minecraftforge.common.ISidedInventory;
/*     */ 
/*     */ public class TileEntitySecStorage extends TileEntityMachines
/*     */   implements ISidedInventory, IInventory
/*     */ {
/*     */   private ItemStack[] inventory;
/*     */ 
/*     */   public TileEntitySecStorage()
/*     */   {
/*  44 */     this.inventory = new ItemStack[60];
/*     */   }
/*     */ 
/*     */   public void dropplugins()
/*     */   {
/*  49 */     for (int a = 0; a < this.inventory.length; a++)
/*  50 */       dropplugins(a, this);
/*     */   }
/*     */ 
/*     */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*     */   {
/*  56 */     return ItemCardSecurityLink.getLinkedSecurityStation(this, 0, this.worldObj);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/*  62 */     Linkgrid.getWorldMap(this.worldObj).getSecStorage().remove(Integer.valueOf(getDeviceID()));
/*  63 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   public int getSecStation_ID()
/*     */   {
/*  68 */     TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
/*  69 */     if (sec != null)
/*  70 */       return sec.getDeviceID();
/*  71 */     return 0;
/*     */   }
/*     */ 
/*     */   public short getmaxSwitchModi()
/*     */   {
/*  76 */     return 3;
/*     */   }
/*     */ 
/*     */   public short getminSwitchModi() {
/*  80 */     return 2;
/*     */   }
/*     */ 
/*     */   public int getfreeslotcount()
/*     */   {
/*  85 */     int count = 0;
/*     */ 
/*  87 */     for (int a = 1; a < this.inventory.length; a++) {
/*  88 */       if (getStackInSlot(a) == null) {
/*  89 */         count++;
/*     */       }
/*     */     }
/*  92 */     return count;
/*     */   }
/*     */ 
/*     */   public void updateEntity()
/*     */   {
/*  98 */     if (!this.worldObj.isRemote) {
/*  99 */       if ((getLinkedSecurityStation() != null) && (!isActive()) && (getSwitchValue()))
/* 100 */         setActive(true);
/* 101 */       if (((getLinkedSecurityStation() == null) || (!getSwitchValue())) && (isActive()))
/* 102 */         setActive(false);
/*     */     }
/* 104 */     super.updateEntity();
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 110 */     super.readFromNBT(nbttagcompound);
/* 111 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/* 112 */     this.inventory = new ItemStack[getSizeInventory()];
/* 113 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 114 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*     */ 
/* 116 */       byte byte0 = nbttagcompound1.getByte("Slot");
/* 117 */       if ((byte0 >= 0) && (byte0 < this.inventory.length))
/* 118 */         this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 125 */     super.writeToNBT(nbttagcompound);
/* 126 */     NBTTagList nbttaglist = new NBTTagList();
/* 127 */     for (int i = 0; i < this.inventory.length; i++) {
/* 128 */       if (this.inventory[i] != null) {
/* 129 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 130 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 131 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 132 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */ 
/* 136 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   public ItemStack getStackInSlot(int i)
/*     */   {
/* 141 */     return this.inventory[i];
/*     */   }
/*     */ 
/*     */   public String getInvName() {
/* 145 */     return "SecStation";
/*     */   }
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 150 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 155 */     this.inventory[par1] = par2ItemStack;
/*     */ 
/* 157 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/* 159 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */ 
/* 162 */     onInventoryChanged();
/*     */   }
/*     */ 
/*     */   public ItemStack decrStackSize(int i, int j) {
/* 166 */     if (this.inventory[i] != null) {
/* 167 */       if (this.inventory[i].stackSize <= j) {
/* 168 */         ItemStack itemstack = this.inventory[i];
/* 169 */         this.inventory[i] = null;
/* 170 */         return itemstack;
/*     */       }
/* 172 */       ItemStack itemstack1 = this.inventory[i].splitStack(j);
/* 173 */       if (this.inventory[i].stackSize == 0) {
/* 174 */         this.inventory[i] = null;
/*     */       }
/* 176 */       return itemstack1;
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(ForgeDirection side)
/*     */   {
/* 186 */     if (isActive())
/* 187 */       return 0;
/* 188 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(ForgeDirection side)
/*     */   {
/* 193 */     if (isActive())
/* 194 */       return 0;
/* 195 */     return 54;
/*     */   }
/*     */ 
/*     */   public Container getContainer(InventoryPlayer inventoryplayer)
/*     */   {
/* 201 */     return new ContainerSecStorage(inventoryplayer.player, this);
/*     */   }
/*     */ 
/*     */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*     */   {
/* 206 */     switch (Slot) {
/*     */     case 0:
/* 208 */       if (!(par1ItemStack.getItem() instanceof ItemCardSecurityLink)) {
/* 209 */         return false;
/*     */       }
/*     */       break;
/*     */     }
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */   public int getSlotStackLimit(int slt)
/*     */   {
/* 218 */     if (slt == 0)
/* 219 */       return 1;
/* 220 */     return 64;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntitySecStorage
 * JD-Core Version:    0.6.2
 */