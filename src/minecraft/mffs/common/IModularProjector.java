package mffs.common;

import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public abstract interface IModularProjector extends IInventory
{
	public abstract World n();

	public abstract int countItemsInSlot(Slots paramSlots);

	public abstract int getDeviceID();

	public abstract Set getInteriorPoints();

	public abstract void setBurnedOut(boolean paramBoolean);

	public abstract boolean isActive();

	public abstract int getSide();

	public static enum Slots
	{
		Linkcard(0), TypeMod(1), Option1(2), Option2(3), Option3(4), Distance(5), Strength(6),
		FocusUp(7), FocusDown(8), FocusRight(9), FocusLeft(10), Centerslot(11), SecCard(12);

		public int slot;

		private Slots(int num)
		{
			this.slot = num;
		}

	}
}