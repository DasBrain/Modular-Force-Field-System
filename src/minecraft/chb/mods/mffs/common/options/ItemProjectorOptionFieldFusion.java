package chb.mods.mffs.common.options;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.ForceFieldBlockStack;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.Linkgrid.Worldlinknet;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.WorldMap;
import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class ItemProjectorOptionFieldFusion extends ItemProjectorOptionBase
  implements IInteriorCheck
{
  public ItemProjectorOptionFieldFusion(int i)
  {
    super(i);
    setIconIndex(43);
  }

  public boolean checkFieldFusioninfluence(PointXYZ png, World world, TileEntityProjector Proj)
  {
    Map InnerMap = null;
    InnerMap = Linkgrid.getWorldMap(world).getFieldFusion();
    for (TileEntityProjector tileentity : InnerMap.values())
    {
      boolean logicswitch = false;
      if (!Proj.isPowersourceItem()) {
        logicswitch = (tileentity.getPowerSourceID() == Proj.getPowerSourceID()) && (tileentity.getDeviceID() != Proj.getDeviceID());
      }
      if ((logicswitch) && (tileentity.isActive())) {
        for (PointXYZ tpng : tileentity.getInteriorPoints()) {
          if ((tpng.X == png.X) && (tpng.Y == png.Y) && (tpng.Z == png.Z))
            return true;
        }
      }
    }
    return false;
  }

  public void checkInteriorBlock(PointXYZ png, World world, TileEntityProjector Proj)
  {
    ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getorcreateFFStackMap(png.X, png.Y, png.Z, world);

    if (!ffworldmap.isEmpty())
    {
      if (ffworldmap.getGenratorID() == Proj.getPowerSourceID())
      {
        TileEntityProjector Projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));

        if (Projector != null)
        {
          if (Projector.hasOption(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion, true))
          {
            Projector.getfield_queue().remove(png);
            ffworldmap.removebyProjector(Projector.getDeviceID());

            PointXYZ ffpng = ffworldmap.getPoint();

            if (world.getBlockId(ffpng.X, ffpng.Y, ffpng.Z) == ModularForceFieldSystem.MFFSFieldblock.blockID)
            {
              world.removeBlockTileEntity(ffpng.X, ffpng.Y, ffpng.Z);
              world.setBlockWithNotify(ffpng.X, ffpng.Y, ffpng.Z, 0);
            }
          }
        }
      }
    }
  }
}