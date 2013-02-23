package chb.mods.mffs.api;

public abstract interface IForceEnergyStorageBlock
{
  public abstract int getPercentageStorageCapacity();

  public abstract int getPowerStorageID();

  public abstract int getStorageMaxPower();

  public abstract int getStorageAvailablePower();

  public abstract boolean consumePowerfromStorage(int paramInt, boolean paramBoolean);

  public abstract boolean insertPowertoStorage(int paramInt, boolean paramBoolean);

  public abstract int getfreeStorageAmount();
}