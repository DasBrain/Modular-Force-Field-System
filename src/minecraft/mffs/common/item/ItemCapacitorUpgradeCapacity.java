package mffs.common.item;

import java.util.List;

import mffs.common.MFFSMaschines;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

public class ItemCapacitorUpgradeCapacity extends ItemMFFSBase
{
	public ItemCapacitorUpgradeCapacity(int i)
	{
		super(i);
		setIconIndex(32);
		setMaxStackSize(9);
	}

	public String getTextureFile()
	{
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable()
	{
		return false;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)))
		{
			info.add("compatible with:");
			info.add("MFFS " + MFFSMaschines.Extractor.displayName);
			info.add("MFFS " + MFFSMaschines.Capacitor.displayName);
		}
		else
		{
			info.add("compatible with: (Hold Shift)");
		}
	}
}