package ic2.api.energy.tile;

import ic2.api.Direction;
import net.minecraft.tileentity.TileEntity;

public abstract interface IEnergyAcceptor extends IEnergyTile
{
  public abstract boolean acceptsEnergyFrom(TileEntity paramTileEntity, Direction paramDirection);
}