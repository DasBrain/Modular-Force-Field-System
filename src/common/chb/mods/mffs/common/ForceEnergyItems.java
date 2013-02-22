package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public class ForceEnergyItems {
	
	public static boolean use(ItemStack itemStack, int amount,boolean trial,EntityPlayer entityplayer)
	{
		if (itemStack.getItem() instanceof IForceEnergyItems) {
			
			IForceEnergyItems ForceEnergyItem = (IForceEnergyItems) itemStack.getItem();
			if(ForceEnergyItem.getForceEnergy(itemStack) >= amount)
			{
				if(trial)
				{
					ForceEnergyItem.setForceEnergy(itemStack, ForceEnergyItem.getForceEnergy(itemStack) - amount);
				}
				return true;
			}
			
		}
		
		return false;
	}
	
	
	public static void charge(ItemStack itemStack, int amount,EntityPlayer entityplayer)
	{
		if (itemStack.getItem() instanceof IForceEnergyItems) {
			
			IForceEnergyItems ForceEnergyItem = (IForceEnergyItems) itemStack.getItem();
			ForceEnergyItem.setForceEnergy(itemStack, amount);
		}

	}

}
