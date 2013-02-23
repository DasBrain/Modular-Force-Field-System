package chb.mods.mffs.common;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class MFFSWorldGenerator
  implements IWorldGenerator
{
  public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
  {
    chunkX <<= 4;
    chunkZ <<= 4;

    WorldGenMinable worldGenMinable = new WorldGenMinable(ModularForceFieldSystem.MFFSMonazitOre.blockID, 0, ModularForceFieldSystem.MonazitOreworldamount + 1);

    for (int i = 0; i < ModularForceFieldSystem.MonazitOreworldamount + 1; i++)
    {
      int x = chunkX + rand.nextInt(16);
      int y = rand.nextInt(80) + 0;
      int z = chunkZ + rand.nextInt(16);

      int randAmount = rand.nextInt(ModularForceFieldSystem.MonazitOreworldamount + 1);

      worldGenMinable.generate(world, rand, x, y, z);
    }
  }
}