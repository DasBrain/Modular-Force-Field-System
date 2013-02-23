package chb.mods.mffs.common.modules;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.IModularProjector.Slots;
import java.util.Set;

public class ItemProjectorModulediagonallyWall extends ItemProjectorModuleWall
{
  public ItemProjectorModulediagonallyWall(int i)
  {
    super(i);
    setIconIndex(56);
  }

  public void calculateField(IModularProjector projector, Set ffLocs)
  {
    int tpx = 0;
    int tpy = 0;
    int tpz = 0;

    int xstart = 0;
    int xend = 0;

    int zstart = 0;
    int zend = 0;

    if (projector.countItemsInSlot(IModularProjector.Slots.FocusUp) > 0)
    {
      xend = Math.max(xend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
      zend = Math.max(zend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
    }

    if (projector.countItemsInSlot(IModularProjector.Slots.FocusDown) > 0)
    {
      xstart = Math.max(xend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
      zstart = Math.max(zend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
    }

    if (projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) > 0)
    {
      xend = Math.max(xend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
      zstart = Math.max(zstart, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusUp), projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)));
    }

    if (projector.countItemsInSlot(IModularProjector.Slots.FocusRight) > 0)
    {
      zend = Math.max(zend, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
      xstart = Math.max(xstart, Math.max(projector.countItemsInSlot(IModularProjector.Slots.FocusDown), projector.countItemsInSlot(IModularProjector.Slots.FocusRight)));
    }

    for (int x1 = 0 - zstart; x1 < zend + 1; x1++)
      for (int z1 = 0 - xstart; z1 < xend + 1; z1++)
        for (int y1 = 1; y1 < projector.countItemsInSlot(IModularProjector.Slots.Strength) + 1 + 1; y1++)
        {
          if (projector.getSide() == 0) {
            tpy = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
            tpx = x1;
            tpz = z1 - z1 - z1;
          }

          if (projector.getSide() == 1) {
            tpy = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
            tpx = x1;
            tpz = z1 - z1 - z1;
          }

          if (projector.getSide() == 2) {
            tpz = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
            tpx = x1 - x1 - x1;
            tpy = z1;
          }

          if (projector.getSide() == 3) {
            tpz = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
            tpx = x1;
            tpy = z1;
          }

          if (projector.getSide() == 4) {
            tpx = y1 - y1 - y1 - projector.countItemsInSlot(IModularProjector.Slots.Distance);
            tpz = x1;
            tpy = z1;
          }
          if (projector.getSide() == 5) {
            tpx = y1 + projector.countItemsInSlot(IModularProjector.Slots.Distance);
            tpz = x1 - x1 - x1;
            tpy = z1;
          }

          if (((projector.getSide() != 0) && (projector.getSide() != 1)) || (((Math.abs(tpx) == Math.abs(tpz)) && (((tpx == 0) || (tpz == 0)) && ((tpx == 0) && (tpz == 0) && (((projector.countItemsInSlot(IModularProjector.Slots.FocusUp) != 0) && (tpx >= 0) && (tpz <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusDown) != 0) && (tpx <= 0) && (tpz >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusRight) != 0) && (tpx >= 0) && (tpz >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) != 0) && (tpx <= 0) && (tpz <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft))))))) || (((projector.getSide() != 2) && (projector.getSide() != 3)) || (((Math.abs(tpx) == Math.abs(tpy)) && (((tpx == 0) || (tpy == 0)) && ((tpx == 0) && (tpy == 0) && (((projector.countItemsInSlot(IModularProjector.Slots.FocusUp) != 0) && (tpx >= 0) && (tpy >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusDown) != 0) && (tpx <= 0) && (tpy <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusRight) != 0) && (tpx >= 0) && (tpy <= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) != 0) && (tpx <= 0) && (tpy >= 0) && (tpx <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft))))))) || (((projector.getSide() == 4) || (projector.getSide() == 5)) && (Math.abs(tpz) == Math.abs(tpy)) && (((tpx != 0) && (tpy != 0)) || ((tpz == 0) && (tpy == 0) && (((projector.countItemsInSlot(IModularProjector.Slots.FocusUp) != 0) && (tpz >= 0) && (tpy >= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusUp))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusDown) != 0) && (tpz <= 0) && (tpy <= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusDown))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusRight) != 0) && (tpz >= 0) && (tpy <= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusRight))) || ((projector.countItemsInSlot(IModularProjector.Slots.FocusLeft) != 0) && (tpz <= 0) && (tpy >= 0) && (tpz <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)) && (tpy <= projector.countItemsInSlot(IModularProjector.Slots.FocusLeft)))))))))))
          {
            ffLocs.add(new PointXYZ(tpx, tpy, tpz, 0));
          }
        }
  }
}