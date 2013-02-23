/*     */ package chb.mods.mffs.common.modules;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.IModularProjector;
/*     */ import chb.mods.mffs.common.IModularProjector.Slots;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBase;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldManipulator;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ public class ItemProjectorModuleAdvCube extends Module3DBase
/*     */ {
/*     */   public ItemProjectorModuleAdvCube(int i)
/*     */   {
/*  43 */     super(i);
/*  44 */     setIconIndex(55);
/*     */   }
/*     */ 
/*     */   public boolean supportsDistance()
/*     */   {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsStrength()
/*     */   {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsMatrix()
/*     */   {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
/*     */   {
/*  66 */     int tpx = 0;
/*  67 */     int tpy = 0;
/*  68 */     int tpz = 0;
/*     */ 
/*  70 */     int xMout = projector.countItemsInSlot(IModularProjector.Slots.FocusLeft);
/*  71 */     int xPout = projector.countItemsInSlot(IModularProjector.Slots.FocusRight);
/*  72 */     int zMout = projector.countItemsInSlot(IModularProjector.Slots.FocusDown);
/*  73 */     int zPout = projector.countItemsInSlot(IModularProjector.Slots.FocusUp);
/*  74 */     int distance = projector.countItemsInSlot(IModularProjector.Slots.Distance);
/*  75 */     int Strength = projector.countItemsInSlot(IModularProjector.Slots.Strength) + 2;
/*     */ 
/*  77 */     for (int y1 = 0; y1 <= Strength; y1++)
/*  78 */       for (int x1 = 0 - xMout; x1 < xPout + 1; x1++)
/*  79 */         for (int z1 = 0 - zPout; z1 < zMout + 1; z1++) {
/*  80 */           if (((TileEntityProjector)projector).getSide() == 0)
/*     */           {
/*  82 */             tpy = y1 - y1 - y1 + 1;
/*  83 */             tpx = x1;
/*  84 */             tpz = z1;
/*     */           }
/*     */ 
/*  87 */           if (((TileEntityProjector)projector).getSide() == 1)
/*     */           {
/*  89 */             tpy = y1 - 1;
/*  90 */             tpx = x1;
/*  91 */             tpz = z1;
/*     */           }
/*     */ 
/*  94 */           if (((TileEntityProjector)projector).getSide() == 2)
/*     */           {
/*  96 */             tpz = y1 - y1 - y1 + 1;
/*  97 */             tpy = z1 - z1 - z1;
/*  98 */             tpx = x1 - x1 - x1;
/*     */           }
/*     */ 
/* 101 */           if (((TileEntityProjector)projector).getSide() == 3)
/*     */           {
/* 103 */             tpz = y1 - 1;
/* 104 */             tpy = z1 - z1 - z1;
/* 105 */             tpx = x1;
/*     */           }
/*     */ 
/* 108 */           if (((TileEntityProjector)projector).getSide() == 4)
/*     */           {
/* 110 */             tpx = y1 - y1 - y1 + 1;
/* 111 */             tpy = z1 - z1 - z1;
/* 112 */             tpz = x1;
/*     */           }
/* 114 */           if (((TileEntityProjector)projector).getSide() == 5)
/*     */           {
/* 116 */             tpx = y1 - 1;
/* 117 */             tpy = z1 - z1 - z1;
/* 118 */             tpz = x1 - x1 - x1;
/*     */           }
/*     */ 
/* 121 */           if ((y1 == 0) || (y1 == Strength) || (x1 == 0 - xMout) || (x1 == xPout) || (z1 == 0 - zPout) || (z1 == zMout))
/*     */           {
/* 123 */             if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true))
/*     */             {
/* 125 */               switch (((TileEntityProjector)projector).getSide())
/*     */               {
/*     */               case 0:
/* 128 */                 if (((TileEntityProjector)projector).yCoord + tpy <= ((TileEntityProjector)projector).yCoord) break;
/* 129 */                 break;
/*     */               case 1:
/* 132 */                 if (((TileEntityProjector)projector).yCoord + tpy >= ((TileEntityProjector)projector).yCoord) break;
/* 133 */                 break;
/*     */               case 2:
/* 136 */                 if (((TileEntityProjector)projector).zCoord + tpz <= ((TileEntityProjector)projector).zCoord) break;
/* 137 */                 break;
/*     */               case 3:
/* 140 */                 if (((TileEntityProjector)projector).zCoord + tpz >= ((TileEntityProjector)projector).zCoord) break;
/* 141 */                 break;
/*     */               case 4:
/*     */               case 5:
/* 144 */                 if ((((TileEntityProjector)projector).xCoord + tpx > ((TileEntityProjector)projector).xCoord) && (
/* 145 */                   (goto 607) || 
/* 148 */                   (((TileEntityProjector)projector).xCoord + tpx < ((TileEntityProjector)projector).xCoord)))
/*     */                 {
/*     */                   continue;
/*     */                 }
/*     */               }
/*     */             }
/* 154 */             ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
/*     */           }
/*     */           else
/*     */           {
/* 158 */             ffInterior.add(new PointXYZ(tpx, tpy, tpz, 0));
/*     */           }
/*     */         }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/* 169 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 170 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 171 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 172 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 173 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 174 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 175 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 176 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 185 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 186 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 187 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 188 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 189 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 190 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 191 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 192 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 194 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleAdvCube
 * JD-Core Version:    0.6.2
 */