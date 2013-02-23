package chb.mods.mffs.common.block;

import chb.mods.mffs.common.ModularForceFieldSystem;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockMonazitOre extends Block
{
  private int blockid;

  public BlockMonazitOre(int i)
  {
    super(i, Material.rock);
    setHardness(3.0F);
    setResistance(5.0F);
    setStepSound(soundStoneFootstep);
    this.blockid = i;
    setCreativeTab(ModularForceFieldSystem.MFFSTab);
  }

  public String getTextureFile()
  {
    return "/chb/mods/mffs/sprites/forciciumore.png";
  }

  public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
  {
    return 0;
  }

  public int idDropped(int par1, Random par2Random, int par3)
  {
    return ModularForceFieldSystem.MFFSMonazitOre.blockID;
  }

  public int quantityDropped(Random par1Random)
  {
    return 1;
  }
}