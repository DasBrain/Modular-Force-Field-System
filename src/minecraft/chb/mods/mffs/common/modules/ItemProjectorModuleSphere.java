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
/*     */ public class ItemProjectorModuleSphere extends Module3DBase
/*     */ {
/*     */   public ItemProjectorModuleSphere(int i)
/*     */   {
/*  43 */     super(i);
/*  44 */     setIconIndex(52);
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
/*  65 */     int radius = projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4;
/*     */ 
/*  67 */     int yDown = radius;
/*     */ 
/*  69 */     if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
/*  70 */       yDown = 0;
/*     */     }
/*     */ 
/*  74 */     for (int y1 = -yDown; y1 <= radius; y1++)
/*  75 */       for (int x1 = -radius; x1 <= radius; x1++)
/*  76 */         for (int z1 = -radius; z1 <= radius; z1++) {
/*  77 */           int dx = x1;
/*  78 */           int dy = y1;
/*  79 */           int dz = z1;
/*     */ 
/*  81 */           int dist = (int)Math.round(Math.sqrt(dx * dx + dy * dy + dz * dz));
/*     */ 
/*  83 */           if ((dist <= radius) && (dist > radius - (projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1)))
/*  84 */             ffLocs.add(new PointXYZ(x1, y1, z1, 0));
/*  85 */           else if (dist <= radius)
/*  86 */             ffInterior.add(new PointXYZ(x1, y1, z1, 0));
/*     */         }
/*     */   }
/*     */ 
/*     */   public static boolean supportsOption(ItemProjectorOptionBase item)
/*     */   {
/*  95 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/*  96 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/*  97 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/*  98 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/*  99 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 100 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 101 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 102 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsOption(Item item)
/*     */   {
/* 111 */     if ((item instanceof ItemProjectorOptionCamoflage)) return true;
/* 112 */     if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
/* 113 */     if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
/* 114 */     if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
/* 115 */     if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
/* 116 */     if ((item instanceof ItemProjectorOptionMobDefence)) return true;
/* 117 */     if ((item instanceof ItemProjectorOptionSponge)) return true;
/* 118 */     if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
/*     */ 
/* 120 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModuleSphere
 * JD-Core Version:    0.6.2
 */