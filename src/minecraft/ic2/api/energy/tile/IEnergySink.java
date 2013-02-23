package ic2.api.energy.tile;

import ic2.api.Direction;

public abstract interface IEnergySink extends IEnergyAcceptor
{
  public abstract int demandsEnergy();

  public abstract int injectEnergy(Direction paramDirection, int paramInt);

  public abstract int getMaxSafeInput();
}