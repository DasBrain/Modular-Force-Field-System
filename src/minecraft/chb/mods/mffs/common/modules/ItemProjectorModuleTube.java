package chb.mods.mffs.common.modules;

import java.util.Set;

import net.minecraft.item.Item;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.options.ItemProjectorOptionBase;
import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldManipulator;
import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
import chb.mods.mffs.common.tileentity.TileEntityProjector;

public class ItemProjectorModuleTube extends Module3DBase
{
  public ItemProjectorModuleTube(int i)
  {
    super(i);
    setIconIndex(51);
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
    return false;
  }

  public void calculateField(IModularProjector projector, Set ffLocs, Set ffInterior)
  {
    int tpx = 0;
    int tpy = 0;
    int tpz = 0;
    int x_offset_s = 0;
    int y_offset_s = 0;
    int z_offset_s = 0;
    int x_offset_e = 0;
    int y_offset_e = 0;
    int z_offset_e = 0;

    int distance = projector.countItemsInSlot(IModularProjector.Slots.Distance) + 2;
    int Strength = projector.countItemsInSlot(IModularProjector.Slots.Strength);

    if ((projector.getSide() == 0) || (projector.getSide() == 1)) {
      tpy = Strength;
      tpx = distance;
      tpz = distance;

      y_offset_s = Strength - Strength;
      if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
        if (projector.getSide() == 0) {
          y_offset_e = Strength;
        }
        if (projector.getSide() == 1) {
          y_offset_s = Strength;
        }
      }
    }

    if ((projector.getSide() == 2) || (projector.getSide() == 3)) {
      tpy = distance;
      tpz = Strength;
      tpx = distance;

      z_offset_s = Strength - Strength;
      if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
        if (projector.getSide() == 2) {
          z_offset_e = Strength;
        }
        if (projector.getSide() == 3) {
          z_offset_s = Strength;
        }
      }
    }
    if ((projector.getSide() == 4) || (projector.getSide() == 5)) {
      tpy = distance;
      tpz = distance;
      tpx = Strength;

      x_offset_s = Strength - Strength;
      if (((TileEntityProjector)projector).hasOption(ModularForceFieldSystem.MFFSProjectorOptionDome, true)) {
        if (projector.getSide() == 4) {
          x_offset_e = Strength;
        }
        if (projector.getSide() == 5) {
          x_offset_s = Strength;
        }
      }

    }

    for (int z1 = 0 - tpz + z_offset_s; z1 <= tpz - z_offset_e; z1++)
      for (int x1 = 0 - tpx + x_offset_s; x1 <= tpx - x_offset_e; x1++)
        for (int y1 = 0 - tpy + y_offset_s; y1 <= tpy - y_offset_e; y1++) {
          int tpx_temp = tpx;
          int tpy_temp = tpy;
          int tpz_temp = tpz;

          if ((tpx == Strength) && ((projector.getSide() == 4) || (projector.getSide() == 5)))
          {
            tpx_temp++;
          }
          if ((tpy == Strength) && ((projector.getSide() == 0) || (projector.getSide() == 1)))
          {
            tpy_temp++;
          }
          if ((tpz == Strength) && ((projector.getSide() == 2) || (projector.getSide() == 3)))
          {
            tpz_temp++;
          }

          if (((x1 == 0 - tpx_temp) || (x1 == tpx_temp) || (y1 == 0 - tpy_temp) || (y1 == tpy_temp) || (z1 == 0 - tpz_temp) || (z1 == tpz_temp)) && (((TileEntityProjector)projector).yCoord + y1 >= 0))
          {
            ffLocs.add(new PointXYZ(x1, y1, z1, 0));
          }
          else ffInterior.add(new PointXYZ(x1, y1, z1, 0));
        }
  }

  public static boolean supportsOption(ItemProjectorOptionBase item)
  {
    if ((item instanceof ItemProjectorOptionCamoflage)) return true;
    if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
    if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
    if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
    if ((item instanceof ItemProjectorOptionSponge)) return true;
    if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
    if ((item instanceof ItemProjectorOptionTouchDamage)) return true;

    return false;
  }

  public boolean supportsOption(Item item)
  {
    if ((item instanceof ItemProjectorOptionCamoflage)) return true;
    if ((item instanceof ItemProjectorOptionFieldFusion)) return true;
    if ((item instanceof ItemProjectorOptionFieldManipulator)) return true;
    if ((item instanceof ItemProjectorOptionForceFieldJammer)) return true;
    if ((item instanceof ItemProjectorOptionSponge)) return true;
    if ((item instanceof ItemProjectorOptionBlockBreaker)) return true;
    if ((item instanceof ItemProjectorOptionTouchDamage)) return true;

    return false;
  }
}