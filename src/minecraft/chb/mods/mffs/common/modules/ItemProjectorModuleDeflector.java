/*     */ package chb.mods.mffs.common.modules;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.IModularProjector;
/*     */ import chb.mods.mffs.common.IModularProjector.Slots;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBase;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class ItemProjectorModuleDeflector extends ModuleBase
/*     */ {
/*     */   public ItemProjectorModuleDeflector(int i)
/*     */   {
/*  36 */     super(i);
/*  37 */     setIconIndex(50);
/*     */   }
/*     */ 
/*     */   public boolean supportsDistance()
/*     */   {
/*  42 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsStrength()
/*     */   {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsMatrix()
/*     */   {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   public void calculateField(IModularProjector projector, Set ffLocs)
/*     */   {
/*  63 */     int tpx = 0;
/*  64 */     int tpy = 0;
/*  65 */     int tpz = 0;
/*     */ 
/*  68 */     for (int x1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusLeft); x1 < projector.countItemsInSlot(IModularProjector.Slots.FocusRight) + 1; x1++)
/*  69 */       for (int z1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusUp); z1 < projector.countItemsInSlot(IModularProjector.Slots.FocusDown) + 1; z1++) {
/*  70 */         if (projector.getSide() == 0) {
/*  71 */           tpy = 0 - projector.countItemsInSlot(IModularProjector.Slots.Distance) - 1;
/*  72 */           tpx = x1;
/*  73 */           tpz = z1;
/*     */         }
/*     */ 
/*  76 */         if (projector.getSide() == 1) {
/*  77 */           tpy = 0 + projector.countItemsInSlot(IModularProjector.Slots.Distance) + 1;
/*  78 */           tpx = x1;
/*  79 */           tpz = z1;
/*     */         }
/*     */ 
/*  82 */         if (projector.getSide() == 2) {
/*  83 */           tpz = 0 - projector.countItemsInSlot(IModularProjector.Slots.Distance) - 1;
/*  84 */           tpy = z1 - z1 - z1;
/*  85 */           tpx = x1 - x1 - x1;
/*     */         }
/*     */ 
/*  88 */         if (projector.getSide() == 3) {
/*  89 */           tpz = 0 + projector.countItemsInSlot(IModularProjector.Slots.Distance) + 1;
/*  90 */           tpy = z1 - z1 - z1;
/*  91 */           tpx = x1;
/*     */         }
/*     */ 
/*  94 */         if (projector.getSide() == 4) {
/*  95 */           tpx = 0 - projector.countItemsInSlot(IModularProjector.Slots.Distance) - 1;
/*  96 */           tpy = z1 - z1 - z1;
/*  97 */           tpz = x1;
/*     */         }
/*  99 */         if (projector.getSide() == 5) {
/* 100 */           tpx = 0 + projector.countItemsInSlot(IModularProjector.Slots.Distance) + 1;
/* 101 */           tpy = z1 - z1 - z1;
/* 102 */           tpz = x1 - x1 - x1;
/*     */         }
/*     */ 
/* 105 */         ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
/*     */       }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/* 113 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/* 114 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 115 */     if ((item instanceof ItemProjectorOptionTouchDamage)) return true;
/*     */ 
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 124 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/* 125 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 126 */     if ((item instanceof ItemProjectorOptionTouchDamage)) return true;
/*     */ 
/* 128 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleDeflector
 * JD-Core Version:    0.6.2
 */