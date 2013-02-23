package chb.mods.mffs.common.item;

import net.minecraft.item.Item;
import chb.mods.mffs.common.ModularForceFieldSystem;

public class ItemMFFSBase extends Item
{
  public ItemMFFSBase(int i)
  {
    super(i);
    setCreativeTab(ModularForceFieldSystem.MFFSTab);
  }
}