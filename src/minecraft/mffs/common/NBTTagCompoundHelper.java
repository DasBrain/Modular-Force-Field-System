package mffs.common;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTTagCompoundHelper
{
	public static NBTTagCompound getTAGfromItemstack(ItemStack itemStack)
	{
		if (itemStack != null)
		{
			NBTTagCompound tag = itemStack.getTagCompound();
			if (tag == null)
			{
				tag = new NBTTagCompound();
				itemStack.setTagCompound(tag);
			}
			return tag;
		}
		return null;
	}
}