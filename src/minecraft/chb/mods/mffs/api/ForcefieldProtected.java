package chb.mods.mffs.api;

import java.io.PrintStream;
import java.lang.reflect.Method;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class ForcefieldProtected
{
  public static boolean BlockProtected(World world, int x, int y, int z, EntityPlayer entityPlayer)
  {
    try
    {
      Method method = Class.forName("chb.mods.mffs.common.ForceFieldOptions").getMethod("BlockProtected", new Class[] { World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, EntityPlayer.class });
      return ((Boolean)method.invoke(null, new Object[] { world, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), entityPlayer })).booleanValue();
    }
    catch (Exception e)
    {
      System.err.println("[ModularForceFieldSystem] API Call Fail: ForcefieldProtected.BlockProtected()" + e.getMessage());
    }return false;
  }
}