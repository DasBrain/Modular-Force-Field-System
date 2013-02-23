package ic2.api.energy.event;

import ic2.api.energy.tile.IEnergySource;

public class EnergyTileSourceEvent extends EnergyTileEvent
{
  public int amount;

  public EnergyTileSourceEvent(IEnergySource energySource, int amount)
  {
    super(energySource);

    this.amount = amount;
  }
}