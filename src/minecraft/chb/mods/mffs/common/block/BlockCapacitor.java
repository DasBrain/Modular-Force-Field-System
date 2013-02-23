/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockCapacitor extends BlockMFFSBase
/*    */ {
/*    */   public BlockCapacitor(int i)
/*    */   {
/* 31 */     super(i);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 37 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 38 */       return "/chb/mods/mffs/sprites/Capacitor_ue.png";
/*    */     }
/* 40 */     return "/chb/mods/mffs/sprites/Capacitor.png";
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 47 */     return new TileEntityCapacitor();
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockCapacitor
 * JD-Core Version:    0.6.2
 */