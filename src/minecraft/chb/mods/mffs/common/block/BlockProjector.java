package chb.mods.mffs.common.block;

import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockProjector extends BlockMFFSBase
{
  public BlockProjector(int i)
  {
    super(i);
    setRequiresSelfNotify();
  }

  public String getTextureFile()
  {
    if (ModularForceFieldSystem.graphicstyle == 1) {
      return "/chb/mods/mffs/sprites/projector_ue.png";
    }
    return "/chb/mods/mffs/sprites/projector.png";
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityProjector();
  }

  public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
  {
    TileEntityProjector tileentity = (TileEntityProjector)world.getBlockTileEntity(i, j, k);

    if (tileentity.isBurnout())
    {
      return false;
    }

    return super.onBlockActivated(world, i, j, k, entityplayer, par6, par7, par8, par9);
  }

  public void randomDisplayTick(World world, int i, int j, int k, Random random)
  {
    TileEntityProjector tileentity = (TileEntityProjector)world.getBlockTileEntity(i, j, k);

    if (tileentity.isBurnout()) {
      double d = i + Math.random();
      double d1 = j + Math.random();
      double d2 = k + Math.random();

      world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
    }
  }
}