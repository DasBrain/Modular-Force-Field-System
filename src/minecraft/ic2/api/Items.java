package ic2.api;

import net.minecraft.item.ItemStack;

public final class Items
{
  private static Class Ic2Items;

  public static ItemStack getItem(String name)
  {
    try
    {
      if (Ic2Items == null) Ic2Items = Class.forName(getPackage() + ".core.Ic2Items");

      Object ret = Ic2Items.getField(name).get(null);

      if ((ret instanceof ItemStack)) {
        return (ItemStack)ret;
      }
      return null;
    }
    catch (Exception e) {
      System.out.println("IC2 API: Call getItem failed for " + name);
    }
    return null;
  }

  private static String getPackage()
  {
    Package pkg = Items.class.getPackage();
    if (pkg != null) return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
    return "ic2";
  }
}