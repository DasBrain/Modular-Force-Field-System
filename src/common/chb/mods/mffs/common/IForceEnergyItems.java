package chb.mods.mffs.common;

import net.minecraft.src.ItemStack;

public interface IForceEnergyItems {
	
	int getforceEnergyTransferMax();

	int getItemDamage(ItemStack itemStack);

	int getMaxForceEnergy();

	void setForceEnergy(ItemStack itemStack, int ForceEnergy);

	int getForceEnergy(ItemStack itemstack);
	
}
