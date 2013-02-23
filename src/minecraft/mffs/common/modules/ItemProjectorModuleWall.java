package mffs.common.modules;

import java.util.Set;

import mffs.api.PointXYZ;
import mffs.common.IModularProjector;
import mffs.common.options.ItemProjectorOptionBase;
import mffs.common.options.ItemProjectorOptionBlockBreaker;
import mffs.common.options.ItemProjectorOptionCamoflage;
import mffs.common.options.ItemProjectorOptionTouchDamage;
import net.minecraft.item.Item;

public class ItemProjectorModuleWall extends ModuleBase
{
	public ItemProjectorModuleWall(int i)
	{
		super(i);
		setIconIndex(49);
	}

	public boolean supportsDistance()
	{
		return true;
	}

	public boolean supportsStrength()
	{
		return true;
	}

	public boolean supportsMatrix()
	{
		return true;
	}

	public void calculateField(IModularProjector projector, Set ffLocs)
	{
		int tpx = 0;
		int tpy = 0;
		int tpz = 0;

		for (int x1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusLeft); x1 < projector.countItemsInSlot(IModularProjector.Slots.FocusRight) + 1; x1++)
			for (int z1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusDown); z1 < projector.countItemsInSlot(IModularProjector.Slots.FocusUp) + 1; z1++)
				for (int y1 = 1; y1 < projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1 + 1; y1++)
				{
					if (projector.getSide() == 0)
					{
						tpy = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
						tpx = x1;
						tpz = z1 - z1 - z1;
					}

					if (projector.getSide() == 1)
					{
						tpy = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
						tpx = x1;
						tpz = z1 - z1 - z1;
					}

					if (projector.getSide() == 2)
					{
						tpz = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
						tpx = x1 - x1 - x1;
						tpy = z1;
					}

					if (projector.getSide() == 3)
					{
						tpz = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
						tpx = x1;
						tpy = z1;
					}

					if (projector.getSide() == 4)
					{
						tpx = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
						tpz = x1;
						tpy = z1;
					}
					if (projector.getSide() == 5)
					{
						tpx = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
						tpz = x1 - x1 - x1;
						tpy = z1;
					}

					if (((projector.getSide() != 0) && (projector.getSide() != 1)) || (((tpx == 0) && (tpz != 0)) || ((tpz == 0) && (tpx != 0)) || ((tpz == 0) && (tpx == 0)) || (((projector.getSide() != 2) && (projector.getSide() != 3)) || (((tpx == 0) && (tpy != 0)) || ((tpy == 0) && (tpx != 0)) || ((tpy == 0) && (tpx == 0)) || (((projector.getSide() == 4) || (projector.getSide() == 5)) && (((tpz == 0) && (tpy != 0)) || ((tpy == 0) && (tpz != 0)) || ((tpy == 0) && (tpz == 0))))))))
					{
						ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
					}
				}
	}

	public static boolean supportsOption(ItemProjectorOptionBase item)
	{
		if ((item instanceof ItemProjectorOptionBlockBreaker))
			return true;
		if ((item instanceof ItemProjectorOptionCamoflage))
			return true;
		if ((item instanceof ItemProjectorOptionTouchDamage))
			return true;

		return false;
	}

	public boolean supportsOption(Item item)
	{
		if ((item instanceof ItemProjectorOptionBlockBreaker))
			return true;
		if ((item instanceof ItemProjectorOptionCamoflage))
			return true;
		if ((item instanceof ItemProjectorOptionTouchDamage))
			return true;

		return false;
	}
}