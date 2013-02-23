package buildcraft.api.power;

public abstract interface IPowerReceptor
{
  public abstract void setPowerProvider(IPowerProvider paramIPowerProvider);

  public abstract IPowerProvider getPowerProvider();

  public abstract void doWork();

  public abstract int powerRequest();
}