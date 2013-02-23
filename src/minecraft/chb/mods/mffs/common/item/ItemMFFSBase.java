package chb.mods.mffs.common.item;

import chb.mods.mffs.common.ModularForceFieldSystem;
import net.minecraft.item.Item;

public class ItemMFFSBase extends Item
{
  public ItemMFFSBase(int i)
  {
    super(i);
    setCreativeTab(ModularForceFieldSystem.MFFSTab);
  }
}