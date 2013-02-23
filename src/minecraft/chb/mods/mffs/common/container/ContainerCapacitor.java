/*     */ package chb.mods.mffs.common.container;
/*     */ 
/*     */ import chb.mods.mffs.common.SlotHelper;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerCapacitor extends Container
/*     */ {
/*     */   private TileEntityCapacitor generatorentity;
/*     */   private int capacity;
/*     */   private int forcepower;
/*     */   private int Powerlinkmode;
/*     */   private short linketprojektor;
/*     */   private EntityPlayer player;
/*     */ 
/*     */   public ContainerCapacitor(EntityPlayer player, TileEntityCapacitor tileentity)
/*     */   {
/*  42 */     this.forcepower = -1;
/*  43 */     this.linketprojektor = -1;
/*  44 */     this.capacity = -1;
/*  45 */     this.Powerlinkmode = -1;
/*  46 */     this.generatorentity = tileentity;
/*  47 */     this.player = player;
/*     */ 
/*  49 */     addSlotToContainer(new SlotHelper(this.generatorentity, 4, 154, 88));
/*  50 */     addSlotToContainer(new SlotHelper(this.generatorentity, 0, 154, 47));
/*  51 */     addSlotToContainer(new SlotHelper(this.generatorentity, 1, 154, 67));
/*  52 */     addSlotToContainer(new SlotHelper(this.generatorentity, 2, 87, 76));
/*     */ 
/*  56 */     for (int var3 = 0; var3 < 3; var3++) {
/*  57 */       for (int var4 = 0; var4 < 9; var4++) {
/*  58 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 125 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  63 */     for (var3 = 0; var3 < 9; var3++)
/*  64 */       addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 183));
/*     */   }
/*     */ 
/*     */   public EntityPlayer getPlayer()
/*     */   {
/*  69 */     return this.player;
/*     */   }
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  74 */     super.detectAndSendChanges();
/*     */ 
/*  76 */     for (int i = 0; i < this.crafters.size(); i++) {
/*  77 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */ 
/*  79 */       if (this.linketprojektor != this.generatorentity.getLinketProjektor().shortValue()) {
/*  80 */         icrafting.sendProgressBarUpdate(this, 1, this.generatorentity.getLinketProjektor().shortValue());
/*     */       }
/*     */ 
/*  84 */       if (this.forcepower != this.generatorentity.getStorageAvailablePower()) {
/*  85 */         icrafting.sendProgressBarUpdate(this, 2, this.generatorentity.getStorageAvailablePower() & 0xFFFF);
/*     */ 
/*  87 */         icrafting.sendProgressBarUpdate(this, 3, this.generatorentity.getStorageAvailablePower() >>> 16);
/*     */       }
/*     */ 
/*  90 */       if (this.capacity != this.generatorentity.getPercentageStorageCapacity()) {
/*  91 */         icrafting.sendProgressBarUpdate(this, 4, this.generatorentity.getPercentageStorageCapacity());
/*     */       }
/*     */ 
/*  94 */       if (this.Powerlinkmode != this.generatorentity.getPowerlinkmode()) {
/*  95 */         icrafting.sendProgressBarUpdate(this, 6, this.generatorentity.getPowerlinkmode());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 100 */     this.linketprojektor = this.generatorentity.getLinketProjektor().shortValue();
/* 101 */     this.forcepower = this.generatorentity.getStorageAvailablePower();
/* 102 */     this.capacity = this.generatorentity.getPercentageStorageCapacity();
/* 103 */     this.Powerlinkmode = this.generatorentity.getPowerlinkmode();
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int i, int j) {
/* 107 */     switch (i)
/*     */     {
/*     */     case 1:
/* 110 */       this.generatorentity.setLinketprojektor(Short.valueOf((short)j));
/* 111 */       break;
/*     */     case 2:
/* 114 */       this.generatorentity.setForcePower(this.generatorentity.getStorageAvailablePower() & 0xFFFF0000 | j);
/*     */ 
/* 116 */       break;
/*     */     case 3:
/* 118 */       this.generatorentity.setForcePower(this.generatorentity.getStorageAvailablePower() & 0xFFFF | j << 16);
/*     */ 
/* 120 */       break;
/*     */     case 4:
/* 123 */       this.generatorentity.setCapacity(j);
/* 124 */       break;
/*     */     case 6:
/* 127 */       this.generatorentity.setPowerlinkmode(j);
/*     */     case 5:
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer entityplayer) {
/* 133 */     return this.generatorentity.isUseableByPlayer(entityplayer);
/*     */   }
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer p, int i)
/*     */   {
/* 138 */     ItemStack itemstack = null;
/* 139 */     Slot slot = (Slot)this.inventorySlots.get(i);
/* 140 */     if ((slot != null) && (slot.getHasStack())) {
/* 141 */       ItemStack itemstack1 = slot.getStack();
/* 142 */       itemstack = itemstack1.copy();
/* 143 */       if (itemstack1.stackSize == 0)
/* 144 */         slot.putStack(null);
/*     */       else {
/* 146 */         slot.onSlotChanged();
/*     */       }
/* 148 */       if (itemstack1.stackSize != itemstack.stackSize)
/* 149 */         slot.onSlotChanged();
/*     */       else {
/* 151 */         return null;
/*     */       }
/*     */     }
/* 154 */     return itemstack;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerCapacitor
 * JD-Core Version:    0.6.2
 */