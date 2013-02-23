package mffs.common.item;

import mffs.common.ModularForceFieldSystem;
import net.minecraft.item.Item;

public class ItemMFFSBase extends Item
{
	public ItemMFFSBase(int i)
	{
		super(i);
		setCreativeTab(ModularForceFieldSystem.MFFSTab);
	}
}