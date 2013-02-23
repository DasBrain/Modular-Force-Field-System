/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockProjector extends BlockMFFSBase
/*    */ {
/*    */   public BlockProjector(int i)
/*    */   {
/* 36 */     super(i);
/* 37 */     setRequiresSelfNotify();
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 42 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 43 */       return "/chb/mods/mffs/sprites/projector_ue.png";
/*    */     }
/* 45 */     return "/chb/mods/mffs/sprites/projector.png";
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 51 */     return new TileEntityProjector();
/*    */   }
/*    */ 
/*    */   public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
/*    */   {
/* 59 */     TileEntityProjector tileentity = (TileEntityProjector)world.getBlockTileEntity(i, j, k);
/*    */ 
/* 61 */     if (tileentity.isBurnout())
/*    */     {
/* 63 */       return false;
/*    */     }
/*    */ 
/* 66 */     return super.onBlockActivated(world, i, j, k, entityplayer, par6, par7, par8, par9);
/*    */   }
/*    */ 
/*    */   public void randomDisplayTick(World world, int i, int j, int k, Random random)
/*    */   {
/* 73 */     TileEntityProjector tileentity = (TileEntityProjector)world.getBlockTileEntity(i, j, k);
/*    */ 
/* 76 */     if (tileentity.isBurnout()) {
/* 77 */       double d = i + Math.random();
/* 78 */       double d1 = j + Math.random();
/* 79 */       double d2 = k + Math.random();
/*    */ 
/* 81 */       world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockProjector
 * JD-Core Version:    0.6.2
 */