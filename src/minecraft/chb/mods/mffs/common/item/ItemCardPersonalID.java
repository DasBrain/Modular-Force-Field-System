package chb.mods.mffs.common.item;

import chb.mods.mffs.common.NBTTagCompoundHelper;
import chb.mods.mffs.common.SecurityRight;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

public class ItemCardPersonalID extends Item
{
  public ItemCardPersonalID(int i)
  {
    super(i);
    setIconIndex(18);
    setMaxStackSize(1);
  }

  public String getTextureFile() {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable()
  {
    return false;
  }

  public boolean isDamageable()
  {
    return true;
  }

  public static boolean hasRight(ItemStack itemStack, SecurityRight sr) {
    NBTTagCompound itemTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    NBTTagCompound rightsTag = itemTag.getCompoundTag("rights");

    if (itemTag.hasKey(sr.rightKey)) {
      setRight(itemStack, sr, itemTag.getBoolean(sr.rightKey));
      itemTag.removeTag(sr.rightKey);
    }
    return rightsTag.getBoolean(sr.rightKey);
  }

  public static void setRight(ItemStack itemStack, SecurityRight sr, boolean value) {
    NBTTagCompound rightsTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getCompoundTag("rights");
    rightsTag.setBoolean(sr.rightKey, value);
    NBTTagCompoundHelper.getTAGfromItemstack(itemStack).setCompoundTag("rights", rightsTag);
  }

  public static void setOwner(ItemStack itemStack, String username)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    nbtTagCompound.setString("name", username);
  }

  public String getUsername(ItemStack itemstack)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    if (nbtTagCompound != null)
    {
      return nbtTagCompound.getString("name");
    }
    return "nobody";
  }

  public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
  {
    String tooltip = String.format("Owner: %s ", new Object[] { NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getString("name") });
    info.add(tooltip);

    if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
      info.add("Rights:");
      for (SecurityRight sr : SecurityRight.rights.values())
      {
        if (hasRight(itemStack, sr))
          info.add("-" + sr.name);
      }
    }
    else
    {
      info.add("Rights: (Hold Shift)");
    }
  }
}