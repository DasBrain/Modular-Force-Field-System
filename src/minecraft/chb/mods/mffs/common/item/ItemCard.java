package chb.mods.mffs.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.NBTTagCompoundHelper;

public class ItemCard extends Item
{
  private StringBuffer info = new StringBuffer();
  protected int Tick;

  public ItemCard(int id)
  {
    super(id);
    setMaxStackSize(1);
    this.Tick = 0;
  }

  public String getTextureFile()
  {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable() {
    return false;
  }

  public static void setforArea(ItemStack itemStack, String areaname)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    nbtTagCompound.setString("Areaname", areaname);
  }

  public static String getforAreaname(ItemStack itemstack)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    if (nbtTagCompound != null)
    {
      return nbtTagCompound.getString("Areaname");
    }
    return "not set";
  }

  public boolean isvalid(ItemStack itemStack) {
    NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    if (tag.hasKey("valid"))
      return tag.getBoolean("valid");
    return false;
  }

  public void setinvalid(ItemStack itemStack) {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    nbtTagCompound.setBoolean("valid", false);
  }

  public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
  {
    NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    info.add("Linkto: " + getforAreaname(itemStack));
    if (tag.hasKey("worldname"))
      info.add("World: " + tag.getString("worldname"));
    if (tag.hasKey("linkTarget"))
      info.add("Coords: " + new PointXYZ(tag.getCompoundTag("linkTarget")).toString());
    if (tag.hasKey("valid"))
      info.add(tag.getBoolean("valid") ? "§2Valid" : "§4Invalid");
  }

  public void setInformation(ItemStack itemStack, PointXYZ png, String key, int value)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);

    nbtTagCompound.setInteger(key, value);
    nbtTagCompound.setString("worldname", DimensionManager.getWorld(png.dimensionId).getWorldInfo().getWorldName());
    nbtTagCompound.setTag("linkTarget", png.asNBT());
    nbtTagCompound.setBoolean("valid", true);
  }

  public int getValuefromKey(String key, ItemStack itemStack)
  {
    NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    if (tag.hasKey(key))
      return tag.getInteger(key);
    return 0;
  }

  public PointXYZ getCardTargetPoint(ItemStack itemStack) {
    NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    if (tag.hasKey("linkTarget")) {
      return new PointXYZ(tag.getCompoundTag("linkTarget"));
    }
    tag.setBoolean("valid", false);

    return null;
  }
}