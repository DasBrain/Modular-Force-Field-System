package chb.mods.mffs.common.options;

import net.minecraft.world.World;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.tileentity.TileEntityProjector;

public abstract interface IInteriorCheck
{
  public abstract void checkInteriorBlock(PointXYZ paramPointXYZ, World paramWorld, TileEntityProjector paramTileEntityProjector);
}