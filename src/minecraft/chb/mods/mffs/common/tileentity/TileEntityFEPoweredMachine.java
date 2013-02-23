/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public abstract class TileEntityFEPoweredMachine extends TileEntityMachines
/*     */ {
/*     */   public abstract ItemStack getPowerLinkStack();
/*     */ 
/*     */   public abstract int getPowerlinkSlot();
/*     */ 
/*     */   public int getPercentageCapacity()
/*     */   {
/*  38 */     ItemStack linkCard = getPowerLinkStack();
/*  39 */     if (hasPowerSource()) {
/*  40 */       return ((IPowerLinkItem)linkCard.getItem()).getPercentageCapacity(linkCard, this, this.worldObj);
/*     */     }
/*  42 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean hasPowerSource()
/*     */   {
/*  47 */     ItemStack linkCard = getPowerLinkStack();
/*  48 */     if ((linkCard != null) && ((linkCard.getItem() instanceof IPowerLinkItem)))
/*  49 */       return true;
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   public int getAvailablePower() {
/*  54 */     ItemStack linkCard = getPowerLinkStack();
/*  55 */     if (hasPowerSource()) {
/*  56 */       return ((IPowerLinkItem)linkCard.getItem()).getAvailablePower(linkCard, this, this.worldObj);
/*     */     }
/*  58 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getPowerSourceID()
/*     */   {
/*  63 */     ItemStack linkCard = getPowerLinkStack();
/*  64 */     if (hasPowerSource()) {
/*  65 */       return ((IPowerLinkItem)linkCard.getItem()).getPowersourceID(linkCard, this, this.worldObj);
/*     */     }
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getMaximumPower() {
/*  71 */     ItemStack linkCard = getPowerLinkStack();
/*  72 */     if (hasPowerSource()) {
/*  73 */       return ((IPowerLinkItem)linkCard.getItem()).getMaximumPower(linkCard, this, this.worldObj);
/*     */     }
/*  75 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean consumePower(int powerAmount, boolean simulation) {
/*  79 */     ItemStack linkCard = getPowerLinkStack();
/*  80 */     if (hasPowerSource()) {
/*  81 */       return ((IPowerLinkItem)linkCard.getItem()).consumePower(linkCard, powerAmount, simulation, this, this.worldObj);
/*     */     }
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean emitPower(int powerAmount, boolean simulation)
/*     */   {
/*  88 */     ItemStack linkCard = getPowerLinkStack();
/*  89 */     if (hasPowerSource()) {
/*  90 */       return ((IPowerLinkItem)linkCard.getItem()).insertPower(linkCard, powerAmount, simulation, this, this.worldObj);
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isPowersourceItem()
/*     */   {
/*  97 */     ItemStack linkCard = getPowerLinkStack();
/*  98 */     if (hasPowerSource()) {
/*  99 */       return ((IPowerLinkItem)linkCard.getItem()).isPowersourceItem();
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityFEPoweredMachine
 * JD-Core Version:    0.6.2
 */