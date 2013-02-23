package chb.mods.mffs.common.options;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import net.minecraft.world.World;

public abstract interface IInteriorCheck
{
  public abstract void checkInteriorBlock(PointXYZ paramPointXYZ, World paramWorld, TileEntityProjector paramTileEntityProjector);
}

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.IInteriorCheck
 * JD-Core Version:    0.6.2
 */