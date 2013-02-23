package mffs.common;

import java.util.Iterator;
import java.util.Map;

import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class ForceFieldOptions
{
	public static boolean BlockProtected(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		Map ProjectorinrangeMap = Linkgrid.getWorldMap(world).getProjektor();
		for (Iterator i$ = ProjectorinrangeMap.values().iterator(); i$.hasNext();)
		{
			TileEntityProjector tileentity = (TileEntityProjector) i$.next();

			int dx = tileentity.xCoord - x;
			int dy = tileentity.yCoord - y;
			int dz = tileentity.zCoord - z;

			int dist = (int) Math.round(Math.sqrt(dx * dx + dy * dy + dz * dz));

			if ((dist <= 64) && (tileentity.isActive()) && (tileentity.getProjektor_Typ() != 1) && (tileentity.getProjektor_Typ() != 2))
			{
				Map<Integer, TileEntityProjector> InnerMap = null;
				InnerMap = Linkgrid.getWorldMap(world).getProjektor();

				for (TileEntityProjector tileentity2 : InnerMap.values())
				{
					boolean logicswitch = tileentity2.equals(tileentity);

					if ((logicswitch) && (tileentity2.isActive()))
					{
						if (entityplayer != null)
						{
							if (!SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.RPB, true))
							{
								return true;
							}
						}
						else
						{
							return true;
						}
					}
				}
			}
		}
		TileEntityProjector tileentity;
		return false;
	}
}