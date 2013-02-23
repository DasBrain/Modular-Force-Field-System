/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockAreaDefenseStation extends BlockMFFSBase
/*    */ {
/*    */   public BlockAreaDefenseStation(int i)
/*    */   {
/* 31 */     super(i);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 37 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 38 */       return "/chb/mods/mffs/sprites/DefenceStation_ue.png";
/*    */     }
/* 40 */     return "/chb/mods/mffs/sprites/DefenceStation.png";
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 45 */     return new TileEntityAreaDefenseStation();
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockAreaDefenseStation
 * JD-Core Version:    0.6.2
 */