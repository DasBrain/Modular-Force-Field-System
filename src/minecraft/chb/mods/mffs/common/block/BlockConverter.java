/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockConverter extends BlockMFFSBase
/*    */ {
/*    */   public BlockConverter(int i)
/*    */   {
/* 36 */     super(i);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 43 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 44 */       return "/chb/mods/mffs/sprites/Converter_ue.png";
/*    */     }
/* 46 */     return "/chb/mods/mffs/sprites/Converter.png";
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 54 */     return new TileEntityConverter();
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockConverter
 * JD-Core Version:    0.6.2
 */