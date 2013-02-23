package mffs.common.modules;

import java.util.Set;

import mffs.api.PointXYZ;
import mffs.common.IModularProjector;
import mffs.common.ModularForceFieldSystem;
import mffs.common.options.ItemProjectorOptionBase;
import mffs.common.options.ItemProjectorOptionBlockBreaker;
import mffs.common.options.ItemProjectorOptionCamoflage;
import mffs.common.options.ItemProjectorOptionDefenseStation;
import mffs.common.options.ItemProjectorOptionFieldFusion;
import mffs.common.options.ItemProjectorOptionFieldManipulator;
import mffs.common.options.ItemProjectorOptionForceFieldJammer;
import mffs.common.options.ItemProjectorOptionMobDefence;
import mffs.common.options.ItemProjectorOptionSponge;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class ItemProjectorModuleCube extends Module3DBase
{
	public ItemProjectorModuleCube(int i)
	{
		super(i);
		setIconIndex(53);
	}

	public boolean supportsDistance()
	{
		return true;
	}

	public boolean supportsStrength()
	{
		return false;
	}

	public boolean supportsMatrix()
	{
		return false;
	}

	public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
	{
		int radius = projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4;
		TileEntity te = (TileEntity) projector;

		int yDown = radius;
		int yTop = radius;
		if (te.yCoord + radius > 255)
		{
			yTop = 255 - te.yCoord;
		}

		if (((TileEntityProjector) te).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true))
		{
			yDown = 0;
		}

		for (int y1 = -yDown; y1 <= yTop; y1++)
			for (int x1 = -radius; x1 <= radius; x1++)
				for (int z1 = -radius; z1 <= radius; z1++)
				{
					if ((x1 == -radius) || (x1 == radius) || (y1 == -radius) || (y1 == yTop) || (z1 == -radius) || (z1 == radius))
						ffLocs.add(new PointXYZ(x1, y1, z1, 0));
					else
						ffInterior.add(new PointXYZ(x1, y1, z1, 0));
				}
	}

	public static boolean supportsOption(ItemProjectorOptionBase item)
	{
		if ((item instanceof ItemProjectorOptionCamoflage))
			return true;
		if ((item instanceof ItemProjectorOptionDefenseStation))
			return true;
		if ((item instanceof ItemProjectorOptionFieldFusion))
			return true;
		if ((item instanceof ItemProjectorOptionFieldManipulator))
			return true;
		if ((item instanceof ItemProjectorOptionForceFieldJammer))
			return true;
		if ((item instanceof ItemProjectorOptionMobDefence))
			return true;
		if ((item instanceof ItemProjectorOptionSponge))
			return true;
		if ((item instanceof ItemProjectorOptionBlockBreaker))
			return true;

		return false;
	}

	public boolean supportsOption(Item item)
	{
		if ((item instanceof ItemProjectorOptionCamoflage))
			return true;
		if ((item instanceof ItemProjectorOptionDefenseStation))
			return true;
		if ((item instanceof ItemProjectorOptionFieldFusion))
			return true;
		if ((item instanceof ItemProjectorOptionFieldManipulator))
			return true;
		if ((item instanceof ItemProjectorOptionForceFieldJammer))
			return true;
		if ((item instanceof ItemProjectorOptionMobDefence))
			return true;
		if ((item instanceof ItemProjectorOptionSponge))
			return true;
		if ((item instanceof ItemProjectorOptionBlockBreaker))
			return true;

		return false;
	}
}