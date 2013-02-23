package chb.mods.mffs.common.block;

import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCapacitor extends BlockMFFSBase
{
  public BlockCapacitor(int i)
  {
    super(i);
  }

  public String getTextureFile()
  {
    if (ModularForceFieldSystem.graphicstyle == 1) {
      return "/chb/mods/mffs/sprites/Capacitor_ue.png";
    }
    return "/chb/mods/mffs/sprites/Capacitor.png";
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityCapacitor();
  }
}