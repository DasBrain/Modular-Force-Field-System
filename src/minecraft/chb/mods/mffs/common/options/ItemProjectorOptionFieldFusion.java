/*    */ package chb.mods.mffs.common.options;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.ForceFieldBlockStack;
/*    */ import chb.mods.mffs.common.Linkgrid;
/*    */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.WorldMap;
/*    */ import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemProjectorOptionFieldFusion extends ItemProjectorOptionBase
/*    */   implements IInteriorCheck
/*    */ {
/*    */   public ItemProjectorOptionFieldFusion(int i)
/*    */   {
/* 37 */     super(i);
/* 38 */     setIconIndex(43);
/*    */   }
/*    */ 
/*    */   public boolean checkFieldFusioninfluence(PointXYZ png, World world, TileEntityProjector Proj)
/*    */   {
/* 44 */     Map InnerMap = null;
/* 45 */     InnerMap = Linkgrid.getWorldMap(world).getFieldFusion();
/* 46 */     for (TileEntityProjector tileentity : InnerMap.values())
/*    */     {
/* 48 */       boolean logicswitch = false;
/* 49 */       if (!Proj.isPowersourceItem()) {
/* 50 */         logicswitch = (tileentity.getPowerSourceID() == Proj.getPowerSourceID()) && (tileentity.getDeviceID() != Proj.getDeviceID());
/*    */       }
/* 52 */       if ((logicswitch) && (tileentity.isActive())) {
/* 53 */         for (PointXYZ tpng : tileentity.getInteriorPoints()) {
/* 54 */           if ((tpng.X == png.X) && (tpng.Y == png.Y) && (tpng.Z == png.Z))
/* 55 */             return true;
/*    */         }
/*    */       }
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   public void checkInteriorBlock(PointXYZ png, World world, TileEntityProjector Proj)
/*    */   {
/* 68 */     ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getorcreateFFStackMap(png.X, png.Y, png.Z, world);
/*    */ 
/* 72 */     if (!ffworldmap.isEmpty())
/*    */     {
/* 74 */       if (ffworldmap.getGenratorID() == Proj.getPowerSourceID())
/*    */       {
/* 76 */         TileEntityProjector Projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/*    */ 
/* 78 */         if (Projector != null)
/*    */         {
/* 80 */           if (Projector.hasOption(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion, true))
/*    */           {
/* 82 */             Projector.getfield_queue().remove(png);
/* 83 */             ffworldmap.removebyProjector(Projector.getDeviceID());
/*    */ 
/* 85 */             PointXYZ ffpng = ffworldmap.getPoint();
/*    */ 
/* 87 */             if (world.getBlockId(ffpng.X, ffpng.Y, ffpng.Z) == ModularForceFieldSystem.MFFSFieldblock.blockID)
/*    */             {
/* 89 */               world.removeBlockTileEntity(ffpng.X, ffpng.Y, ffpng.Z);
/* 90 */               world.setBlockWithNotify(ffpng.X, ffpng.Y, ffpng.Z, 0);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion
 * JD-Core Version:    0.6.2
 */