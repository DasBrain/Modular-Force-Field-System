package chb.mods.mffs.common.item;

public class ItemForcicium extends ItemMFFSBase
{
  public ItemForcicium(int i)
  {
    super(i);
    setIconIndex(97);
    setMaxStackSize(64);
  }

  public String getTextureFile() {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable() {
    return false;
  }
}