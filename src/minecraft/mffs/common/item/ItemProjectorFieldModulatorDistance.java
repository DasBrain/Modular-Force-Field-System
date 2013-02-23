package mffs.common.item;

import java.util.List;

import mffs.common.MFFSMaschines;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

public class ItemProjectorFieldModulatorDistance extends ItemMFFSBase
{
	public ItemProjectorFieldModulatorDistance(int i)
	{
		super(i);
		setIconIndex(64);
		setMaxStackSize(64);
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
			info.add("MFFS " + MFFSMaschines.Projector.displayName);
			info.add("MFFS " + MFFSMaschines.DefenceStation.displayName);
		}
		else
		{
			info.add("compatible with: (Hold Shift)");
		}
	}
}