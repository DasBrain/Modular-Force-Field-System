/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import cpw.mods.fml.common.IWorldGenerator;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*    */ 
/*    */ public class MFFSWorldGenerator
/*    */   implements IWorldGenerator
/*    */ {
/*    */   public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
/*    */   {
/* 34 */     chunkX <<= 4;
/* 35 */     chunkZ <<= 4;
/*    */ 
/* 37 */     WorldGenMinable worldGenMinable = new WorldGenMinable(ModularForceFieldSystem.MFFSMonazitOre.blockID, 0, ModularForceFieldSystem.MonazitOreworldamount + 1);
/*    */ 
/* 39 */     for (int i = 0; i < ModularForceFieldSystem.MonazitOreworldamount + 1; i++)
/*    */     {
/* 41 */       int x = chunkX + rand.nextInt(16);
/* 42 */       int y = rand.nextInt(80) + 0;
/* 43 */       int z = chunkZ + rand.nextInt(16);
/*    */ 
/* 45 */       int randAmount = rand.nextInt(ModularForceFieldSystem.MonazitOreworldamount + 1);
/*    */ 
/* 47 */       worldGenMinable.generate(world, rand, x, y, z);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.MFFSWorldGenerator
 * JD-Core Version:    0.6.2
 */