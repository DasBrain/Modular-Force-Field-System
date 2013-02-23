package chb.mods.mffs.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import chb.mods.mffs.common.MFFSMaschines;

public class ItemExtractorUpgradeBooster extends ItemMFFSBase
{
  public ItemExtractorUpgradeBooster(int i)
  {
    super(i);
    setIconIndex(37);
    setMaxStackSize(19);
  }

  public String getTextureFile() {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable() {
    return false;
  }

  public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
  {
    if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
      info.add("compatible with:");
      info.add("MFFS " + MFFSMaschines.Extractor.displayName);
    }
    else {
      info.add("compatible with: (Hold Shift)");
    }
  }
}