/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockMonazitOre extends Block
/*    */ {
/*    */   private int blockid;
/*    */ 
/*    */   public BlockMonazitOre(int i)
/*    */   {
/* 37 */     super(i, Material.rock);
/* 38 */     setHardness(3.0F);
/* 39 */     setResistance(5.0F);
/* 40 */     setStepSound(soundStoneFootstep);
/* 41 */     this.blockid = i;
/* 42 */     setCreativeTab(ModularForceFieldSystem.MFFSTab);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 47 */     return "/chb/mods/mffs/sprites/forciciumore.png";
/*    */   }
/*    */ 
/*    */   public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
/*    */   {
/* 53 */     return 0;
/*    */   }
/*    */ 
/*    */   public int idDropped(int par1, Random par2Random, int par3)
/*    */   {
/* 58 */     return ModularForceFieldSystem.MFFSMonazitOre.blockID;
/*    */   }
/*    */ 
/*    */   public int quantityDropped(Random par1Random)
/*    */   {
/* 63 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockMonazitOre
 * JD-Core Version:    0.6.2
 */