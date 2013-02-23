package mffs.common.options;

import mffs.api.PointXYZ;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.world.World;

public abstract interface IInteriorCheck
{
	public abstract void checkInteriorBlock(PointXYZ paramPointXYZ, World paramWorld, TileEntityProjector paramTileEntityProjector);
}