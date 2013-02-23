package ic2.api.energy.tile;

public abstract interface IEnergySource extends IEnergyEmitter
{
  public abstract int getMaxEnergyOutput();
}