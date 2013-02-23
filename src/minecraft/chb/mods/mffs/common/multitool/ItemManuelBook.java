package chb.mods.mffs.common.multitool;

import chb.mods.mffs.common.ModularForceFieldSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemManuelBook extends ItemMultitool
{
  public ItemManuelBook(int par1)
  {
    super(par1, 5);
  }

  public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
  {
    return false;
  }

  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
  {
    if (entityplayer.isSneaking())
    {
      return super.onItemRightClick(itemstack, world, entityplayer);
    }

    if (world.isRemote) {
      entityplayer.openGui(ModularForceFieldSystem.instance, 1, world, 0, 0, 0);
    }

    return itemstack;
  }
}