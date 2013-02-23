package chb.mods.mffs.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class PointXYZ
{
  public int X = 0;
  public int Y = 0;
  public int Z = 0;
  public int dimensionId;

  public PointXYZ(int x, int y, int z)
  {
    this(x, y, z, 2147483647);
  }

  public PointXYZ(int x, int y, int z, World worldObj) {
    this(x, y, z, worldObj.provider.dimensionId);
  }

  public PointXYZ(int x, int y, int z, int dimensionId) {
    this.X = x;
    this.Y = y;
    this.Z = z;
    this.dimensionId = dimensionId;
  }

  public PointXYZ(NBTTagCompound nbt) {
    this(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getInteger("dim"));
  }

  public NBTTagCompound asNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("x", this.X);
    nbt.setInteger("y", this.Y);
    nbt.setInteger("z", this.Z);
    nbt.setInteger("dim", this.dimensionId);
    return nbt;
  }

  public World getPointWorld()
  {
    if (this.dimensionId != 2147483647)
      return DimensionManager.getWorld(this.dimensionId);
    return null;
  }

  public static double distance(PointXYZ png1, PointXYZ png2)
  {
    if (png1.dimensionId == png2.dimensionId)
    {
      int dx = png1.X - png2.X;
      int dy = png1.Y - png2.Y;
      int dz = png1.Z - png2.Z;
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    return 2147483647.0D;
  }

  public boolean equals(Object pnt2)
  {
    if ((pnt2 instanceof PointXYZ)) {
      PointXYZ p = (PointXYZ)pnt2;
      return (this.X == p.X) && (this.Y == p.Y) && (this.Z == p.Z) && (this.dimensionId == p.dimensionId);
    }
    return false;
  }

  public int hashCode()
  {
    return ("X: " + this.X + " Y: " + this.Y + " Z: " + this.Z + "D: " + this.dimensionId).hashCode();
  }

  public String toString()
  {
    return "X: " + this.X + " Y: " + this.Y + " Z: " + this.Z;
  }
}