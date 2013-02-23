package chb.mods.mffs.common.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;

public class BlockAreaDefenseStation extends BlockMFFSBase
{
  public BlockAreaDefenseStation(int i)
  {
    super(i);
  }

  public String getTextureFile()
  {
    if (ModularForceFieldSystem.graphicstyle == 1) {
      return "/chb/mods/mffs/sprites/DefenceStation_ue.png";
    }
    return "/chb/mods/mffs/sprites/DefenceStation.png";
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityAreaDefenseStation();
  }
}