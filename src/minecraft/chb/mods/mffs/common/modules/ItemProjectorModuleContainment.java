/*     */ package chb.mods.mffs.common.modules;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.IModularProjector;
/*     */ import chb.mods.mffs.common.IModularProjector.Slots;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBase;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class ItemProjectorModuleContainment extends Module3DBase
/*     */ {
/*     */   public ItemProjectorModuleContainment(int i)
/*     */   {
/*  41 */     super(i);
/*  42 */     setIconIndex(54);
/*     */   }
/*     */ 
/*     */   public boolean supportsDistance()
/*     */   {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsStrength()
/*     */   {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsMatrix()
/*     */   {
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */   public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
/*     */   {
/*  64 */     int tpx = 0;
/*  65 */     int tpy = 0;
/*  66 */     int tpz = 0;
/*     */ 
/*  68 */     int xMout = projector.countItemsInSlot(IModularProjector.Slots.FocusLeft);
/*  69 */     int xPout = projector.countItemsInSlot(IModularProjector.Slots.FocusRight);
/*  70 */     int zMout = projector.countItemsInSlot(IModularProjector.Slots.FocusDown);
/*  71 */     int zPout = projector.countItemsInSlot(IModularProjector.Slots.FocusUp);
/*  72 */     int distance = projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  73 */     int Strength = projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1;
/*     */ 
/*  75 */     for (int y1 = 0; y1 <= Strength; y1++)
/*  76 */       for (int x1 = 0 - xMout; x1 < xPout + 1; x1++)
/*  77 */         for (int z1 = 0 - zPout; z1 < zMout + 1; z1++) {
/*  78 */           if (((TileEntityProjector)projector).getSide() == 0)
/*     */           {
/*  80 */             tpy = y1 - y1 - y1 - distance - 1;
/*  81 */             tpx = x1;
/*  82 */             tpz = z1;
/*     */           }
/*     */ 
/*  85 */           if (((TileEntityProjector)projector).getSide() == 1)
/*     */           {
/*  87 */             tpy = y1 + distance + 1;
/*  88 */             tpx = x1;
/*  89 */             tpz = z1;
/*     */           }
/*     */ 
/*  92 */           if (((TileEntityProjector)projector).getSide() == 2)
/*     */           {
/*  94 */             tpz = y1 - y1 - y1 - distance - 1;
/*  95 */             tpy = z1 - z1 - z1;
/*  96 */             tpx = x1 - x1 - x1;
/*     */           }
/*     */ 
/*  99 */           if (((TileEntityProjector)projector).getSide() == 3)
/*     */           {
/* 101 */             tpz = y1 + distance + 1;
/* 102 */             tpy = z1 - z1 - z1;
/* 103 */             tpx = x1;
/*     */           }
/*     */ 
/* 106 */           if (((TileEntityProjector)projector).getSide() == 4)
/*     */           {
/* 108 */             tpx = y1 - y1 - y1 - distance - 1;
/* 109 */             tpy = z1 - z1 - z1;
/* 110 */             tpz = x1;
/*     */           }
/* 112 */           if (((TileEntityProjector)projector).getSide() == 5)
/*     */           {
/* 114 */             tpx = y1 + distance + 1;
/* 115 */             tpy = z1 - z1 - z1;
/* 116 */             tpz = x1 - x1 - x1;
/*     */           }
/*     */ 
/* 119 */           if ((y1 == 0) || (y1 == Strength) || (x1 == 0 - xMout) || (x1 == xPout) || (z1 == 0 - zPout) || (z1 == zMout))
/*     */           {
/* 121 */             ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
/*     */           }
/*     */           else
/*     */           {
/* 125 */             ffInterior.add(new PointXYZ(tpx, tpy, tpz, 0));
/*     */           }
/*     */         }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/* 135 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 136 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 137 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 138 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 139 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 140 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 141 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 151 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 152 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 153 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 154 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 155 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 156 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 157 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 160 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleContainment
 * JD-Core Version:    0.6.2
 */