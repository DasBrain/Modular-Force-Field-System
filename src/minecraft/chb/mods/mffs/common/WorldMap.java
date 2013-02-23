package chb.mods.mffs.common;

import java.util.Hashtable;
import java.util.Map;

import net.minecraft.world.World;
import chb.mods.mffs.api.PointXYZ;

import com.google.common.collect.MapMaker;

public final class WorldMap
{
  private static Map ForceFieldWorlds = new MapMaker().weakKeys().makeMap();

  public static ForceFieldWorld getForceFieldWorld(World world)
  {
    if (world != null) {
      if (!ForceFieldWorlds.containsKey(world)) {
        ForceFieldWorlds.put(world, new ForceFieldWorld());
      }
      return (ForceFieldWorld)ForceFieldWorlds.get(world);
    }

    return null;
  }

  public static class ForceFieldWorld
  {
    private static Map ForceFieldStackMap = new Hashtable();

    public ForceFieldBlockStack getorcreateFFStackMap(int x, int y, int z, World world)
    {
      PointXYZ png = new PointXYZ(x, y, z, world);
      if (ForceFieldStackMap.get(Integer.valueOf(png.hashCode())) == null) {
        ForceFieldStackMap.put(Integer.valueOf(png.hashCode()), new ForceFieldBlockStack(png));
      }
      return (ForceFieldBlockStack)ForceFieldStackMap.get(Integer.valueOf(png.hashCode()));
    }

    public ForceFieldBlockStack getForceFieldStackMap(Integer hasher) {
      return (ForceFieldBlockStack)ForceFieldStackMap.get(hasher);
    }

    public ForceFieldBlockStack getForceFieldStackMap(PointXYZ png)
    {
      return (ForceFieldBlockStack)ForceFieldStackMap.get(Integer.valueOf(png.hashCode()));
    }

    public int isExistForceFieldStackMap(int x, int y, int z, int counter, int typ, World world)
    {
      switch (typ)
      {
      case 0:
        y += counter;
        break;
      case 1:
        y -= counter;
        break;
      case 2:
        z += counter;
        break;
      case 3:
        z -= counter;
        break;
      case 4:
        x += counter;
        break;
      case 5:
        x -= counter;
      }

      ForceFieldBlockStack Map = (ForceFieldBlockStack)ForceFieldStackMap.get(Integer.valueOf(new PointXYZ(x, y, z, world).hashCode()));

      if (Map == null) {
        return 0;
      }
      if (Map.isEmpty())
      {
        return 0;
      }

      return Map.getGenratorID();
    }
  }
}