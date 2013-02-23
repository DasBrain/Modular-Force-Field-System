/*     */ package chb.mods.mffs.common.container;
/*     */ 
/*     */ import chb.mods.mffs.common.SlotHelper;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerForceEnergyExtractor extends Container
/*     */ {
/*     */   private TileEntityExtractor ForceEnergyExtractor;
/*     */   private EntityPlayer player;
/*     */   private int WorkCylce;
/*     */   private int workdone;
/*     */   private int ForceEnergybuffer;
/*     */ 
/*     */   public ContainerForceEnergyExtractor(EntityPlayer player, TileEntityExtractor tileentity)
/*     */   {
/*  40 */     this.ForceEnergyExtractor = tileentity;
/*  41 */     this.player = player;
/*  42 */     this.WorkCylce = -1;
/*  43 */     this.workdone = -1;
/*  44 */     this.ForceEnergybuffer = -1;
/*     */ 
/*  46 */     addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 0, 82, 26));
/*  47 */     addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 1, 145, 40));
/*  48 */     addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 2, 20, 66));
/*  49 */     addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 3, 39, 66));
/*  50 */     addSlotToContainer(new SlotHelper(this.ForceEnergyExtractor, 4, 112, 26));
/*     */ 
/*  54 */     for (int var3 = 0; var3 < 3; var3++) {
/*  55 */       for (int var4 = 0; var4 < 9; var4++) {
/*  56 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 104 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  61 */     for (var3 = 0; var3 < 9; var3++)
/*  62 */       addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 162));
/*     */   }
/*     */ 
/*     */   public EntityPlayer getPlayer()
/*     */   {
/*  67 */     return this.player;
/*     */   }
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer entityplayer) {
/*  71 */     return this.ForceEnergyExtractor.isUseableByPlayer(entityplayer);
/*     */   }
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer p, int i) {
/*  75 */     ItemStack itemstack = null;
/*  76 */     Slot slot = (Slot)this.inventorySlots.get(i);
/*  77 */     if ((slot != null) && (slot.getHasStack())) {
/*  78 */       ItemStack itemstack1 = slot.getStack();
/*  79 */       itemstack = itemstack1.copy();
/*  80 */       if (itemstack1.stackSize == 0)
/*  81 */         slot.putStack(null);
/*     */       else {
/*  83 */         slot.onSlotChanged();
/*     */       }
/*  85 */       if (itemstack1.stackSize != itemstack.stackSize)
/*  86 */         slot.onSlotChanged();
/*     */       else {
/*  88 */         return null;
/*     */       }
/*     */     }
/*  91 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int i, int j) {
/*  95 */     switch (i) {
/*     */     case 0:
/*  97 */       this.ForceEnergyExtractor.setWorkdone(j);
/*  98 */       break;
/*     */     case 1:
/* 101 */       this.ForceEnergyExtractor.setWorkCylce(j);
/* 102 */       break;
/*     */     case 2:
/* 105 */       this.ForceEnergyExtractor.setForceEnergybuffer(this.ForceEnergyExtractor.getForceEnergybuffer() & 0xFFFF0000 | j);
/*     */ 
/* 107 */       break;
/*     */     case 3:
/* 109 */       this.ForceEnergyExtractor.setForceEnergybuffer(this.ForceEnergyExtractor.getForceEnergybuffer() & 0xFFFF | j << 16);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/* 117 */     super.detectAndSendChanges();
/*     */ 
/* 119 */     for (int i = 0; i < this.crafters.size(); i++) {
/* 120 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */ 
/* 122 */       if (this.workdone != this.ForceEnergyExtractor.getWorkdone()) {
/* 123 */         icrafting.sendProgressBarUpdate(this, 0, this.ForceEnergyExtractor.getWorkdone());
/*     */       }
/*     */ 
/* 126 */       if (this.WorkCylce != this.ForceEnergyExtractor.getWorkCylce()) {
/* 127 */         icrafting.sendProgressBarUpdate(this, 1, this.ForceEnergyExtractor.getWorkCylce());
/*     */       }
/*     */ 
/* 131 */       if (this.ForceEnergybuffer != this.ForceEnergyExtractor.getForceEnergybuffer()) {
/* 132 */         icrafting.sendProgressBarUpdate(this, 2, this.ForceEnergyExtractor.getForceEnergybuffer() & 0xFFFF);
/*     */ 
/* 134 */         icrafting.sendProgressBarUpdate(this, 3, this.ForceEnergyExtractor.getForceEnergybuffer() >>> 16);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 139 */     this.workdone = this.ForceEnergyExtractor.getWorkdone();
/* 140 */     this.WorkCylce = this.ForceEnergyExtractor.getWorkCylce();
/* 141 */     this.ForceEnergybuffer = this.ForceEnergyExtractor.getForceEnergybuffer();
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerForceEnergyExtractor
 * JD-Core Version:    0.6.2
 */