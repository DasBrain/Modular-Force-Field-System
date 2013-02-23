/*    */ package chb.mods.mffs.common.options;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemProjectorOptionSponge extends ItemProjectorOptionBase
/*    */   implements IInteriorCheck
/*    */ {
/*    */   public ItemProjectorOptionSponge(int i)
/*    */   {
/* 32 */     super(i);
/* 33 */     setIconIndex(35);
/*    */   }
/*    */ 
/*    */   public void checkInteriorBlock(PointXYZ png, World world, TileEntityProjector Projector)
/*    */   {
/* 38 */     if (world.getBlockMaterial(png.X, png.Y, png.Z).isLiquid())
/* 39 */       if (!ModularForceFieldSystem.forcefieldremoveonlywaterandlava.booleanValue())
/* 40 */         world.setBlockWithNotify(png.X, png.Y, png.Z, 0);
/* 41 */       else if ((world.getBlockId(png.X, png.Y, png.Z) == 8) || (world.getBlockId(png.X, png.Y, png.Z) == 9) || (world.getBlockId(png.X, png.Y, png.Z) == 10) || (world.getBlockId(png.X, png.Y, png.Z) == 11))
/*    */       {
/* 49 */         world.setBlockWithNotify(png.X, png.Y, png.Z, 0);
/*    */       }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.ItemProjectorOptionSponge
 * JD-Core Version:    0.6.2
 */