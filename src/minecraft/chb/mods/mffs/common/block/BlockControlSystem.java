package chb.mods.mffs.common.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityControlSystem;

public class BlockControlSystem extends BlockMFFSBase
{
  public BlockControlSystem(int i)
  {
    super(i);
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityControlSystem();
  }

  public String getTextureFile()
  {
    if (ModularForceFieldSystem.graphicstyle == 1) {
      return "/chb/mods/mffs/sprites/ControlSystem_ue.png";
    }
    return "/chb/mods/mffs/sprites/ControlSystem.png";
  }
}