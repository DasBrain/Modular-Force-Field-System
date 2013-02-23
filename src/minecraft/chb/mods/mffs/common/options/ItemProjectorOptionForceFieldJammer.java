/*    */ package chb.mods.mffs.common.options;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.Linkgrid;
/*    */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemProjectorOptionForceFieldJammer extends ItemProjectorOptionBase
/*    */   implements IChecksOnAll
/*    */ {
/*    */   public ItemProjectorOptionForceFieldJammer(int i)
/*    */   {
/* 33 */     super(i);
/* 34 */     setIconIndex(41);
/*    */   }
/*    */ 
/*    */   public boolean CheckJammerinfluence(PointXYZ png, World world, TileEntityProjector Projector)
/*    */   {
/* 41 */     Map InnerMap = null;
/* 42 */     InnerMap = Linkgrid.getWorldMap(world).getJammer();
/*    */ 
/* 44 */     for (TileEntityProjector tileentity : InnerMap.values()) {
/* 45 */       boolean logicswitch = false;
/*    */ 
/* 47 */       logicswitch = tileentity.getPowerSourceID() != Projector.getPowerSourceID();
/*    */ 
/* 49 */       if ((logicswitch) && (tileentity.isActive()))
/*    */       {
/* 51 */         for (PointXYZ tpng : tileentity.getInteriorPoints())
/*    */         {
/* 53 */           if ((tpng.X == png.X) && (tpng.Y == png.Y) && (tpng.Z == png.Z)) {
/* 54 */             Projector.ProjektorBurnout();
/* 55 */             return true;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 61 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer
 * JD-Core Version:    0.6.2
 */