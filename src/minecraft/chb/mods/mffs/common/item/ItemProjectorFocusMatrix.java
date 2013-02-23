package chb.mods.mffs.common.item;

import chb.mods.mffs.common.MFFSMaschines;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class ItemProjectorFocusMatrix extends ItemMFFSBase
{
  public ItemProjectorFocusMatrix(int i)
  {
    super(i);
    setIconIndex(66);
    setMaxStackSize(64);
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
      info.add("MFFS " + MFFSMaschines.Projector.displayName);
    }
    else {
      info.add("compatible with: (Hold Shift)");
    }
  }
}