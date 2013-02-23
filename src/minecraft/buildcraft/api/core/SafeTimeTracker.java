package buildcraft.api.core;

import net.minecraft.world.World;

public class SafeTimeTracker
{
  private long lastMark = 0L;

  public boolean markTimeIfDelay(World world, long delay)
  {
    if (world == null) {
      return false;
    }

    long currentTime = world.getWorldTime();

    if (currentTime < this.lastMark) {
      this.lastMark = currentTime;
      return false;
    }if (this.lastMark + delay <= currentTime) {
      this.lastMark = world.getWorldTime();
      return true;
    }
    return false;
  }

  public void markTime(World world)
  {
    this.lastMark = world.getWorldTime();
  }
}