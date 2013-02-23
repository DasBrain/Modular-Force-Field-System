package chb.mods.mffs.common.options;

import java.util.Map;

import net.minecraft.world.World;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.tileentity.TileEntityProjector;

public class ItemProjectorOptionForceFieldJammer extends ItemProjectorOptionBase implements IChecksOnAll
{
	public ItemProjectorOptionForceFieldJammer(int i)
	{
		super(i);
		setIconIndex(41);
	}

	public boolean CheckJammerinfluence(PointXYZ png, World world, TileEntityProjector Projector)
	{
		Map<Integer, TileEntityProjector> InnerMap = null;
		InnerMap = Linkgrid.getWorldMap(world).getJammer();

		for (TileEntityProjector tileentity : InnerMap.values())
		{
			boolean logicswitch = false;

			logicswitch = tileentity.getPowerSourceID() != Projector.getPowerSourceID();

			if ((logicswitch) && (tileentity.isActive()))
			{
				for (PointXYZ tpng : tileentity.getInteriorPoints())
				{
					if ((tpng.X == png.X) && (tpng.Y == png.Y) && (tpng.Z == png.Z))
					{
						Projector.ProjektorBurnout();
						return true;
					}
				}
			}
		}

		return false;
	}
}