package chb.mods.mffs.common.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.multitool.ItemMultitool;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;

public class BlockSecurtyStorage extends BlockMFFSBase
{
  public BlockSecurtyStorage(int i)
  {
    super(i);
    setRequiresSelfNotify();
  }

  public TileEntity createNewTileEntity(World world)
  {
    return new TileEntitySecStorage();
  }

  public String getTextureFile()
  {
    if (ModularForceFieldSystem.graphicstyle == 1) {
      return "/chb/mods/mffs/sprites/SecStorage_ue.png";
    }
    return "/chb/mods/mffs/sprites/SecStorage.png";
  }

  public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
  {
    if (world.isRemote) {
      return true;
    }

    if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMultitool)))
    {
      return false;
    }

    TileEntitySecStorage tileentity = (TileEntitySecStorage)world.getBlockTileEntity(i, j, k);
    if (tileentity != null)
    {
      if (SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.OSS))
      {
        if (!world.isRemote)
          entityplayer.openGui(ModularForceFieldSystem.instance, 0, world, i, j, k);
        return true;
      }
      return true;
    }

    return true;
  }
}