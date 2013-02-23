package mffs.common.modules;

import java.util.Set;

import mffs.api.PointXYZ;
import mffs.common.IModularProjector;
import mffs.common.options.ItemProjectorOptionBase;
import mffs.common.options.ItemProjectorOptionBlockBreaker;
import mffs.common.options.ItemProjectorOptionCamoflage;
import mffs.common.options.ItemProjectorOptionTouchDamage;
import net.minecraft.item.Item;

public class ItemProjectorModuleDeflector extends ModuleBase
{
	public ItemProjectorModuleDeflector(int i)
	{
		super(i);
		setIconIndex(50);
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
		return true;
	}

	public void calculateField(IModularProjector projector, Set ffLocs)
	{
		int tpx = 0;
		int tpy = 0;
		int tpz = 0;

		for (int x1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusLeft); x1 < projector.countItemsInSlot(IModularProjector.Slots.FocusRight) + 1; x1++)
			for (int z1 = 0 - projector.countItemsInSlot(IModularProjector.Slots.FocusUp); z1 < projector.countItemsInSlot(IModularProjector.Slots.FocusDown) + 1; z1++)
			{
				if (projector.getSide() == 0)
				{
					tpy = 0 - projector.countItemsInSlot(IModularProjector.Slots.Distance) - 1;
					tpx = x1;
					tpz = z1;
				}

				if (projector.getSide() == 1)
				{
					tpy = 0 + projector.countItemsInSlot(IModularProjector.Slots.Distance) + 1;
					tpx = x1;
					tpz = z1;
				}

				if (projector.getSide() == 2)
				{
					tpz = 0 - projector.countItemsInSlot(IModularProjector.Slots.Distance) - 1;
					tpy = z1 - z1 - z1;
					tpx = x1 - x1 - x1;
				}

				if (projector.getSide() == 3)
				{
					tpz = 0 + projector.countItemsInSlot(IModularProjector.Slots.Distance) + 1;
					tpy = z1 - z1 - z1;
					tpx = x1;
				}

				if (projector.getSide() == 4)
				{
					tpx = 0 - projector.countItemsInSlot(IModularProjector.Slots.Distance) - 1;
					tpy = z1 - z1 - z1;
					tpz = x1;
				}
				if (projector.getSide() == 5)
				{
					tpx = 0 + projector.countItemsInSlot(IModularProjector.Slots.Distance) + 1;
					tpy = z1 - z1 - z1;
					tpz = x1 - x1 - x1;
				}

				ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
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