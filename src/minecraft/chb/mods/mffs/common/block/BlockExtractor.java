package chb.mods.mffs.common.block;

import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockExtractor extends BlockMFFSBase
{
  public BlockExtractor(int i)
  {
    super(i);
  }

  public String getTextureFile()
  {
    if (ModularForceFieldSystem.graphicstyle == 1) {
      return "/chb/mods/mffs/sprites/Extractor_ue.png";
    }
    return "/chb/mods/mffs/sprites/Extractor.png";
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntityExtractor();
  }
}