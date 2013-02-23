package chb.mods.mffs.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MFFSCreativeTab extends CreativeTabs
{
  public MFFSCreativeTab(int par1, String par2Str)
  {
    super(par1, par2Str);
  }

  public ItemStack getIconItemStack() {
    return new ItemStack(ModularForceFieldSystem.MFFSCapacitor);
  }
}