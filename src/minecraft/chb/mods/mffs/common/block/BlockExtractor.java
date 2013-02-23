/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockExtractor extends BlockMFFSBase
/*    */ {
/*    */   public BlockExtractor(int i)
/*    */   {
/* 34 */     super(i);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 39 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 40 */       return "/chb/mods/mffs/sprites/Extractor_ue.png";
/*    */     }
/* 42 */     return "/chb/mods/mffs/sprites/Extractor.png";
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 49 */     return new TileEntityExtractor();
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockExtractor
 * JD-Core Version:    0.6.2
 */