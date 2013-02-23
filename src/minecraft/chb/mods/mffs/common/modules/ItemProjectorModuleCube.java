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
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class ItemProjectorModuleCube extends Module3DBase
/*     */ {
/*     */   public ItemProjectorModuleCube(int i)
/*     */   {
/*  45 */     super(i);
/*  46 */     setIconIndex(53);
/*     */   }
/*     */ 
/*     */   public boolean supportsDistance()
/*     */   {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportsStrength()
/*     */   {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsMatrix()
/*     */   {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
/*     */   {
/*  72 */     int radius = projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4;
/*  73 */     TileEntity te = (TileEntity)projector;
/*     */ 
/*  76 */     int yDown = radius;
/*  77 */     int yTop = radius;
/*  78 */     if (te.yCoord + radius > 255) {
/*  79 */       yTop = 255 - te.yCoord;
/*     */     }
/*     */ 
/*  82 */     if (((TileEntityProjector)te).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
/*  83 */       yDown = 0;
/*     */     }
/*     */ 
/*  88 */     for (int y1 = -yDown; y1 <= yTop; y1++)
/*  89 */       for (int x1 = -radius; x1 <= radius; x1++)
/*  90 */         for (int z1 = -radius; z1 <= radius; z1++)
/*     */         {
/*  92 */           if ((x1 == -radius) || (x1 == radius) || (y1 == -radius) || (y1 == yTop) || (z1 == -radius) || (z1 == radius))
/*  93 */             ffLocs.add(new PointXYZ(x1, y1, z1, 0));
/*     */           else
/*  95 */             ffInterior.add(new PointXYZ(x1, y1, z1, 0));
/*     */         }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/* 105 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 106 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 107 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 108 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 109 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 110 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 111 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 112 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 122 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 123 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 124 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 125 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 126 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 127 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 128 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 129 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 132 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleCube
 * JD-Core Version:    0.6.2
 */