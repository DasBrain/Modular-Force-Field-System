/*     */ package chb.mods.mffs.common.container;
/*     */ 
/*     */ import chb.mods.mffs.common.SlotHelper;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerProjector extends Container
/*     */ {
/*     */   private TileEntityProjector projectorentity;
/*     */   private int linkPower;
/*     */   private int maxlinkPower;
/*     */   private int accesstyp;
/*     */   private int capacity;
/*     */   private EntityPlayer player;
/*     */ 
/*     */   public ContainerProjector(EntityPlayer player, TileEntityProjector tileentity)
/*     */   {
/*  41 */     this.player = player;
/*  42 */     this.projectorentity = tileentity;
/*  43 */     this.linkPower = -1;
/*  44 */     this.maxlinkPower = -1;
/*  45 */     this.accesstyp = -1;
/*  46 */     this.capacity = -1;
/*     */ 
/*  48 */     addSlotToContainer(new SlotHelper(this.projectorentity, 0, 11, 61));
/*  49 */     addSlotToContainer(new SlotHelper(this.projectorentity, 1, 11, 38));
/*     */ 
/*  51 */     addSlotToContainer(new SlotHelper(this.projectorentity, 2, 120, 82));
/*  52 */     addSlotToContainer(new SlotHelper(this.projectorentity, 3, 138, 82));
/*  53 */     addSlotToContainer(new SlotHelper(this.projectorentity, 4, 156, 82));
/*     */ 
/*  55 */     addSlotToContainer(new SlotHelper(this.projectorentity, 6, 155, 64));
/*  56 */     addSlotToContainer(new SlotHelper(this.projectorentity, 5, 119, 64));
/*     */ 
/*  58 */     addSlotToContainer(new SlotHelper(this.projectorentity, 7, 137, 28));
/*  59 */     addSlotToContainer(new SlotHelper(this.projectorentity, 8, 137, 62));
/*  60 */     addSlotToContainer(new SlotHelper(this.projectorentity, 9, 154, 45));
/*  61 */     addSlotToContainer(new SlotHelper(this.projectorentity, 10, 120, 45));
/*     */ 
/*  63 */     addSlotToContainer(new SlotHelper(this.projectorentity, 11, 137, 45));
/*     */ 
/*  65 */     addSlotToContainer(new SlotHelper(this.projectorentity, 12, 92, 38));
/*     */ 
/*  69 */     for (int var3 = 0; var3 < 3; var3++) {
/*  70 */       for (int var4 = 0; var4 < 9; var4++) {
/*  71 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 104 + var3 * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  76 */     for (var3 = 0; var3 < 9; var3++)
/*  77 */       addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 162));
/*     */   }
/*     */ 
/*     */   public EntityPlayer getPlayer()
/*     */   {
/*  82 */     return this.player;
/*     */   }
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer entityplayer) {
/*  86 */     return this.projectorentity.isUseableByPlayer(entityplayer);
/*     */   }
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer p, int i) {
/*  90 */     ItemStack itemstack = null;
/*  91 */     Slot slot = (Slot)this.inventorySlots.get(i);
/*  92 */     if ((slot != null) && (slot.getHasStack())) {
/*  93 */       ItemStack itemstack1 = slot.getStack();
/*  94 */       itemstack = itemstack1.copy();
/*  95 */       if (itemstack1.stackSize == 0)
/*  96 */         slot.putStack(null);
/*     */       else {
/*  98 */         slot.onSlotChanged();
/*     */       }
/* 100 */       if (itemstack1.stackSize != itemstack.stackSize)
/* 101 */         slot.onSlotChanged();
/*     */       else {
/* 103 */         return null;
/*     */       }
/*     */     }
/* 106 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/* 111 */     super.detectAndSendChanges();
/*     */ 
/* 113 */     for (int i = 0; i < this.crafters.size(); i++) {
/* 114 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */ 
/* 116 */       if (this.linkPower != this.projectorentity.getLinkPower()) {
/* 117 */         icrafting.sendProgressBarUpdate(this, 0, this.projectorentity.getLinkPower() & 0xFFFF);
/*     */ 
/* 119 */         icrafting.sendProgressBarUpdate(this, 1, this.projectorentity.getLinkPower() >>> 16);
/*     */       }
/*     */ 
/* 122 */       if (this.capacity != this.projectorentity.getPercentageCapacity()) {
/* 123 */         icrafting.sendProgressBarUpdate(this, 2, this.projectorentity.getPercentageCapacity());
/*     */       }
/*     */ 
/* 127 */       if (this.accesstyp != this.projectorentity.getaccesstyp()) {
/* 128 */         icrafting.sendProgressBarUpdate(this, 4, this.projectorentity.getaccesstyp());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 134 */     this.linkPower = this.projectorentity.getLinkPower();
/* 135 */     this.accesstyp = this.projectorentity.getaccesstyp();
/* 136 */     this.capacity = this.projectorentity.getPercentageCapacity();
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int i, int j) {
/* 140 */     switch (i) {
/*     */     case 0:
/* 142 */       this.projectorentity.setLinkPower(this.projectorentity.getLinkPower() & 0xFFFF0000 | j);
/*     */ 
/* 145 */       break;
/*     */     case 1:
/* 147 */       this.projectorentity.setLinkPower(this.projectorentity.getLinkPower() & 0xFFFF | j << 16);
/*     */ 
/* 150 */       break;
/*     */     case 2:
/* 152 */       this.projectorentity.setCapacity(j);
/* 153 */       break;
/*     */     case 4:
/* 156 */       this.projectorentity.setaccesstyp(j);
/*     */     case 3:
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerProjector
 * JD-Core Version:    0.6.2
 */