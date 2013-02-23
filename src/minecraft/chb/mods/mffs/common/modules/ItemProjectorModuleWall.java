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
/*     */ public class ItemProjectorModuleWall extends ModuleBase
/*     */ {
/*     */   public ItemProjectorModuleWall(int i)
/*     */   {
/*  36 */     super(i);
/*  37 */     setIconIndex(49);
/*     */   }
/*     */ 
/*     */   public boolean supportsDistance()
/*     */   {
/*  43 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsStrength()
/*     */   {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsMatrix()
/*     */   {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   public void calculateField(IModularProjector projector, Set ffLocs)
/*     */   {
/*  59 */     int tpx = 0;
/*  60 */     int tpy = 0;
/*  61 */     int tpz = 0;
/*     */ 
/*  63 */     for (int x1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusLeft); x1 < projector.countItemsInSlot(IModularProjector.Slots.FocusRight) + 1; x1++)
/*  64 */       for (int z1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusDown); z1 < projector.countItemsInSlot(IModularProjector.Slots.FocusUp) + 1; z1++)
/*  65 */         for (int y1 = 1; y1 < projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1 + 1; y1++) {
/*  66 */           if (projector.getSide() == 0) {
/*  67 */             tpy = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  68 */             tpx = x1;
/*  69 */             tpz = z1 - z1 - z1;
/*     */           }
/*     */ 
/*  72 */           if (projector.getSide() == 1) {
/*  73 */             tpy = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  74 */             tpx = x1;
/*  75 */             tpz = z1 - z1 - z1;
/*     */           }
/*     */ 
/*  78 */           if (projector.getSide() == 2) {
/*  79 */             tpz = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  80 */             tpx = x1 - x1 - x1;
/*  81 */             tpy = z1;
/*     */           }
/*     */ 
/*  84 */           if (projector.getSide() == 3) {
/*  85 */             tpz = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  86 */             tpx = x1;
/*  87 */             tpy = z1;
/*     */           }
/*     */ 
/*  90 */           if (projector.getSide() == 4) {
/*  91 */             tpx = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  92 */             tpz = x1;
/*  93 */             tpy = z1;
/*     */           }
/*  95 */           if (projector.getSide() == 5) {
/*  96 */             tpx = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  97 */             tpz = x1 - x1 - x1;
/*  98 */             tpy = z1;
/*     */           }
/*     */ 
/* 101 */           if (((projector.getSide() != 0) && (projector.getSide() != 1)) || (((tpx == 0) && (tpz != 0)) || ((tpz == 0) && (tpx != 0)) || ((tpz == 0) && (tpx == 0)) || (((projector.getSide() != 2) && (projector.getSide() != 3)) || (((tpx == 0) && (tpy != 0)) || ((tpy == 0) && (tpx != 0)) || ((tpy == 0) && (tpx == 0)) || (((projector.getSide() == 4) || (projector.getSide() == 5)) && (((tpz == 0) && (tpy != 0)) || ((tpy == 0) && (tpz != 0)) || ((tpy == 0) && (tpz == 0))))))))
/*     */           {
/* 112 */             ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
/*     */           }
/*     */         }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/* 123 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/* 124 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 125 */     if ((item instanceof ItemProjectorOptionTouchDamage)) return true;
/*     */ 
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 134 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/* 135 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 136 */     if ((item instanceof ItemProjectorOptionTouchDamage)) return true;
/*     */ 
/* 138 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleWall
 * JD-Core Version:    0.6.2
 */