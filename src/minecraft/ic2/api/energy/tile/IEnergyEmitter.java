package ic2.api.energy.tile;

import ic2.api.Direction;
import net.minecraft.tileentity.TileEntity;

public abstract interface IEnergyEmitter extends IEnergyTile
{
  public abstract boolean emitsEnergyTo(TileEntity paramTileEntity, Direction paramDirection);
}