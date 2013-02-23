package chb.mods.mffs.common.modules;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.IModularProjector.Slots;
import chb.mods.mffs.common.options.ItemProjectorOptionBase;
import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import java.util.Set;
import net.minecraft.item.Item;

public class ItemProjectorModuleContainment extends Module3DBase
{
  public ItemProjectorModuleContainment(int i)
  {
    super(i);
    setIconIndex(54);
  }

  public boolean supportsDistance()
  {
    return true;
  }

  public boolean supportsStrength()
  {
    return true;
  }

  public boolean supportsMatrix()
  {
    return true;
  }

  public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
  {
    int tpx = 0;
    int tpy = 0;
    int tpz = 0;

    int xMout = projector.countItemsInSlot(IModularProjector.Slots.FocusLeft);
    int xPout = projector.countItemsInSlot(IModularProjector.Slots.FocusRight);
    int zMout = projector.countItemsInSlot(IModularProjector.Slots.FocusDown);
    int zPout = projector.countItemsInSlot(IModularProjector.Slots.FocusUp);
    int distance = projector.countItemsInSlot(IModularProjector.Slots.Distance);
    int Strength = projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1;

    for (int y1 = 0; y1 <= Strength; y1++)
      for (int x1 = 0 - xMout; x1 < xPout + 1; x1++)
        for (int z1 = 0 - zPout; z1 < zMout + 1; z1++) {
          if (((TileEntityProjector)projector).getSide() == 0)
          {
            tpy = y1 - y1 - y1 - distance - 1;
            tpx = x1;
            tpz = z1;
          }

          if (((TileEntityProjector)projector).getSide() == 1)
          {
            tpy = y1 + distance + 1;
            tpx = x1;
            tpz = z1;
          }

          if (((TileEntityProjector)projector).getSide() == 2)
          {
            tpz = y1 - y1 - y1 - distance - 1;
            tpy = z1 - z1 - z1;
            tpx = x1 - x1 - x1;
          }

          if (((TileEntityProjector)projector).getSide() == 3)
          {
            tpz = y1 + distance + 1;
            tpy = z1 - z1 - z1;
            tpx = x1;
          }

          if (((TileEntityProjector)projector).getSide() == 4)
          {
            tpx = y1 - y1 - y1 - distance - 1;
            tpy = z1 - z1 - z1;
            tpz = x1;
          }
          if (((TileEntityProjector)projector).getSide() == 5)
          {
            tpx = y1 + distance + 1;
            tpy = z1 - z1 - z1;
            tpz = x1 - x1 - x1;
          }

          if ((y1 == 0) || (y1 == Strength) || (x1 == 0 - xMout) || (x1 == xPout) || (z1 == 0 - zPout) || (z1 == zMout))
          {
            ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
          }
          else
          {
            ffInterior.add(new PointXYZ(tpx, tpy, tpz, 0));
          }
        }
  }

  public static boolean supportsOption(ItemProjectorOptionBase item)
  {
    if ((item instanceof ItemProjectorOptionCamoflage)) return true;
    if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
    if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
    if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
    if ((item instanceof ItemProjectorOptionMobDefence)) return true;
    if ((item instanceof ItemProjectorOptionSponge)) return true;
    if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;

    return false;
  }

  public boolean supportsOption(Item item)
  {
    if ((item instanceof ItemProjectorOptionCamoflage)) return true;
    if ((item instanceof ItemProjectorOptionDefenseStation)) return true;
    if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
    if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
    if ((item instanceof ItemProjectorOptionMobDefence)) return true;
    if ((item instanceof ItemProjectorOptionSponge)) return true;
    if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;

    return false;
  }
}