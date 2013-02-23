/*    */ package chb.mods.mffs.common.modules;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.IModularProjector;
/*    */ import chb.mods.mffs.common.IModularProjector.Slots;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ItemProjectorModulediagonallyWall extends ItemProjectorModuleWall
/*    */ {
/*    */   public ItemProjectorModulediagonallyWall(int i)
/*    */   {
/* 31 */     super(i);
/* 32 */     setIconIndex(56);
/*    */   }
/*    */ 
/*    */   public void calculateField(IModularProjector projector, Set ffLocs)
/*    */   {
/* 39 */     int tpx = 0;
/* 40 */     int tpy = 0;
/* 41 */     int tpz = 0;
/*    */ 
/* 43 */     int xstart = 0;
/* 44 */     int xend = 0;
/*    */ 
/* 46 */     int zstart = 0;
/* 47 */     int zend = 0;
/*    */ 
/* 49 */     if (projector.countItemsInSlot(IModularProjector.Slots.FocusUp) > 0)
/*    */     {
/* 51 */       xend = Math.max(xend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
/* 52 */       zend = Math.max(zend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
/*    */     }
/*    */ 
/* 55 */     if (projector.countItemsInSlot(IModularProjector.Slots.FocusDown) > 0)
/*    */     {
/* 57 */       xstart = Math.max(xend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
/* 58 */       zstart = Math.max(zend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
/*    */     }
/*    */ 
/* 61 */     if (projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) > 0)
/*    */     {
/* 63 */       xend = Math.max(xend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
/* 64 */       zstart = Math.max(zstart, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
/*    */     }
/*    */ 
/* 67 */     if (projector.countItemsInSlot(IModularProjector.Slots.FocusRight) > 0)
/*    */     {
/* 69 */       zend = Math.max(zend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
/* 70 */       xstart = Math.max(xstart, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
/*    */     }
/*    */ 
/* 73 */     for (int x1 = 0 - zstart; x1 < zend + 1; x1++)
/* 74 */       for (int z1 = 0 - xstart; z1 < xend + 1; z1++)
/* 75 */         for (int y1 = 1; y1 < projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1 + 1; y1++)
/*    */         {
/* 77 */           if (projector.getSide() == 0) {
/* 78 */             tpy = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
/* 79 */             tpx = x1;
/* 80 */             tpz = z1 - z1 - z1;
/*    */           }
/*    */ 
/* 83 */           if (projector.getSide() == 1) {
/* 84 */             tpy = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
/* 85 */             tpx = x1;
/* 86 */             tpz = z1 - z1 - z1;
/*    */           }
/*    */ 
/* 89 */           if (projector.getSide() == 2) {
/* 90 */             tpz = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
/* 91 */             tpx = x1 - x1 - x1;
/* 92 */             tpy = z1;
/*    */           }
/*    */ 
/* 95 */           if (projector.getSide() == 3) {
/* 96 */             tpz = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
/* 97 */             tpx = x1;
/* 98 */             tpy = z1;
/*    */           }
/*    */ 
/* 101 */           if (projector.getSide() == 4) {
/* 102 */             tpx = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
/* 103 */             tpz = x1;
/* 104 */             tpy = z1;
/*    */           }
/* 106 */           if (projector.getSide() == 5) {
/* 107 */             tpx = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
/* 108 */             tpz = x1 - x1 - x1;
/* 109 */             tpy = z1;
/*    */           }
/*    */ 
/* 113 */           if (((projector.getSide() != 0) && (projector.getSide() != 1)) || (((Math.abs(tpx) == Math.abs(tpz)) && (((tpx == 0) || (tpz == 0)) && ((tpx == 0) && (tpz == 0) && (((projector.countItemsInSlot(IModularProjector.Slots.FocusUp) != 0) && (tpx >= 0) && (tpz <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusDown) != 0) && (tpx <= 0) && (tpz >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusRight) != 0) && (tpx >= 0) && (tpz >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) != 0) && (tpx <= 0) && (tpz <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft))))))) || (((projector.getSide() != 2) && (projector.getSide() != 3)) || (((Math.abs(tpx) == Math.abs(tpy)) && (((tpx == 0) || (tpy == 0)) && ((tpx == 0) && (tpy == 0) && (((projector.countItemsInSlot(IModularProjector.Slots.FocusUp) != 0) && (tpx >= 0) && (tpy >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusDown) != 0) && (tpx <= 0) && (tpy <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusRight) != 0) && (tpx >= 0) && (tpy <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) != 0) && (tpx <= 0) && (tpy >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft))))))) || (((projector.getSide() == 4) || (projector.getSide() == 5)) && (Math.abs(tpz) == Math.abs(tpy)) && (((tpx != 0) && (tpy != 0)) || ((tpz == 0) && (tpy == 0) && (((projector.countItemsInSlot(IModularProjector.Slots.FocusUp) != 0) && (tpz >= 0) && (tpy >= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusDown) != 0) && (tpz <= 0) && (tpy <= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusRight) != 0) && (tpz >= 0) && (tpy <= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) != 0) && (tpz <= 0) && (tpy >= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)))))))))))
/*    */           {
/* 141 */             ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
/*    */           }
/*    */         }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ItemProjectorModulediagonallyWall
 * JD-Core Version:    0.6.2
 */