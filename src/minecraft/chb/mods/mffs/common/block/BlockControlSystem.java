/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockControlSystem extends BlockMFFSBase
/*    */ {
/*    */   public BlockControlSystem(int i)
/*    */   {
/* 11 */     super(i);
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 16 */     return new TileEntityControlSystem();
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 22 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 23 */       return "/chb/mods/mffs/sprites/ControlSystem_ue.png";
/*    */     }
/* 25 */     return "/chb/mods/mffs/sprites/ControlSystem.png";
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockControlSystem
 * JD-Core Version:    0.6.2
 */