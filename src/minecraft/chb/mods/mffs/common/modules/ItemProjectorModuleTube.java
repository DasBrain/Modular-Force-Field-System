/*     */ package chb.mods.mffs.common.modules;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.IModularProjector;
/*     */ import chb.mods.mffs.common.IModularProjector.Slots;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBase;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldManipulator;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class ItemProjectorModuleTube extends Module3DBase
/*     */ {
/*     */   public ItemProjectorModuleTube(int i)
/*     */   {
/*  42 */     super(i);
/*  43 */     setIconIndex(51);
/*     */   }
/*     */ 
/*     */   public boolean supportsDistance()
/*     */   {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsStrength()
/*     */   {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsMatrix()
/*     */   {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
/*     */   {
/*  67 */     int tpx = 0;
/*  68 */     int tpy = 0;
/*  69 */     int tpz = 0;
/*  70 */     int x_offset_s = 0;
/*  71 */     int y_offset_s = 0;
/*  72 */     int z_offset_s = 0;
/*  73 */     int x_offset_e = 0;
/*  74 */     int y_offset_e = 0;
/*  75 */     int z_offset_e = 0;
/*     */ 
/*  77 */     int distance = projector.countItemsInSlot(IModularProjector.Slots.Distance) + 2;
/*  78 */     int Strength = projector.countItemsInSlot(IModularProjector.Slots.Strength);
/*     */ 
/*  80 */     if ((projector.getSide() == 0) || (projector.getSide() == 1)) {
/*  81 */       tpy = Strength;
/*  82 */       tpx = distance;
/*  83 */       tpz = distance;
/*     */ 
/*  85 */       y_offset_s = Strength - Strength;
/*  86 */       if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
/*  87 */         if (projector.getSide() == 0) {
/*  88 */           y_offset_e = Strength;
/*     */         }
/*  90 */         if (projector.getSide() == 1) {
/*  91 */           y_offset_s = Strength;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  96 */     if ((projector.getSide() == 2) || (projector.getSide() == 3)) {
/*  97 */       tpy = distance;
/*  98 */       tpz = Strength;
/*  99 */       tpx = distance;
/*     */ 
/* 101 */       z_offset_s = Strength - Strength;
/* 102 */       if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
/* 103 */         if (projector.getSide() == 2) {
/* 104 */           z_offset_e = Strength;
/*     */         }
/* 106 */         if (projector.getSide() == 3) {
/* 107 */           z_offset_s = Strength;
/*     */         }
/*     */       }
/*     */     }
/* 111 */     if ((projector.getSide() == 4) || (projector.getSide() == 5)) {
/* 112 */       tpy = distance;
/* 113 */       tpz = distance;
/* 114 */       tpx = Strength;
/*     */ 
/* 116 */       x_offset_s = Strength - Strength;
/* 117 */       if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
/* 118 */         if (projector.getSide() == 4) {
/* 119 */           x_offset_e = Strength;
/*     */         }
/* 121 */         if (projector.getSide() == 5) {
/* 122 */           x_offset_s = Strength;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 128 */     for (int z1 = 0 - tpz + z_offset_s; z1 <= tpz - z_offset_e; z1++)
/* 129 */       for (int x1 = 0 - tpx + x_offset_s; x1 <= tpx - x_offset_e; x1++)
/* 130 */         for (int y1 = 0 - tpy + y_offset_s; y1 <= tpy - y_offset_e; y1++) {
/* 131 */           int tpx_temp = tpx;
/* 132 */           int tpy_temp = tpy;
/* 133 */           int tpz_temp = tpz;
/*     */ 
/* 135 */           if ((tpx == Strength) && ((projector.getSide() == 4) || (projector.getSide() == 5)))
/*     */           {
/* 137 */             tpx_temp++;
/*     */           }
/* 139 */           if ((tpy == Strength) && ((projector.getSide() == 0) || (projector.getSide() == 1)))
/*     */           {
/* 141 */             tpy_temp++;
/*     */           }
/* 143 */           if ((tpz == Strength) && ((projector.getSide() == 2) || (projector.getSide() == 3)))
/*     */           {
/* 145 */             tpz_temp++;
/*     */           }
/*     */ 
/* 148 */           if (((x1 == 0 - tpx_temp) || (x1 == tpx_temp) || (y1 == 0 - tpy_temp) || (y1 == tpy_temp) || (z1 == 0 - tpz_temp) || (z1 == tpz_temp)) && (((TileEntityProjector)projector).yCoord + y1 >= 0))
/*     */           {
/* 152 */             ffLocs.add(new PointXYZ(x1, y1, z1, 0));
/*     */           }
/* 154 */           else ffInterior.add(new PointXYZ(x1, y1, z1, 0));
/*     */         }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/* 166 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 167 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 168 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 169 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 170 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 171 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/* 172 */     if ((item instanceof ItemProjectorOptionTouchDamage)) return true;
/*     */ 
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 181 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 182 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 183 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 184 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 185 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 186 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/* 187 */     if ((item instanceof ItemProjectorOptionTouchDamage)) return true;
/*     */ 
/* 189 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleTube
 * JD-Core Version:    0.6.2
 */