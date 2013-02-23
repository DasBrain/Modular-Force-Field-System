/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockAdvSecurtyStation extends BlockMFFSBase
/*    */ {
/*    */   public BlockAdvSecurtyStation(int i)
/*    */   {
/* 32 */     super(i);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 37 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 38 */       return "/chb/mods/mffs/sprites/AdvSecurtyStation_ue.png";
/*    */     }
/* 40 */     return "/chb/mods/mffs/sprites/AdvSecurtyStation.png";
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 46 */     return new TileEntityAdvSecurityStation();
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockAdvSecurtyStation
 * JD-Core Version:    0.6.2
 */