package chb.mods.mffs.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
import chb.mods.mffs.common.tileentity.TileEntityConverter;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;

public class SecurityHelper
{
  public static boolean isAccessGranted(TileEntity tileEntity, EntityPlayer entityplayer, World world, SecurityRight right)
  {
    return isAccessGranted(tileEntity, entityplayer, world, right, false);
  }

  public static boolean isAccessGranted(TileEntity tileEntity, EntityPlayer entityplayer, World world, SecurityRight right, boolean suppresswarning)
  {
    if ((tileEntity instanceof TileEntitySecStorage))
    {
      TileEntityAdvSecurityStation sec = ((TileEntitySecStorage)tileEntity).getLinkedSecurityStation();

      if (sec != null) {
        if (sec.isAccessGranted(entityplayer.username, right)) {
          return true;
        }

        if (!suppresswarning)
          entityplayer.sendChatToPlayer("[Field Security] Fail: access denied");
        return false;
      }

      if (world.isRemote) {
        return false;
      }
      return true;
    }

    if ((tileEntity instanceof TileEntityControlSystem))
    {
      TileEntityAdvSecurityStation sec = ((TileEntityControlSystem)tileEntity).getLinkedSecurityStation();
      if (sec != null) {
        if (sec.isAccessGranted(entityplayer.username, right)) {
          return true;
        }

        if (!suppresswarning)
          entityplayer.sendChatToPlayer("[Field Security] Fail: access denied");
        return false;
      }

      if (world.isRemote) {
        return false;
      }
      return true;
    }

    if ((tileEntity instanceof TileEntityAdvSecurityStation))
    {
      if (!((TileEntityAdvSecurityStation)tileEntity).isAccessGranted(entityplayer.username, right))
      {
        if (!suppresswarning) {
          Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
        }
        return false;
      }

    }

    if ((tileEntity instanceof TileEntityConverter))
    {
      TileEntityAdvSecurityStation sec = ((TileEntityConverter)tileEntity).getLinkedSecurityStation();
      if (sec != null)
      {
        if (sec.isAccessGranted(entityplayer.username, right))
        {
          return true;
        }
        if (!suppresswarning) {
          Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
        }
        return false;
      }

      return true;
    }

    if ((tileEntity instanceof TileEntityCapacitor))
    {
      TileEntityAdvSecurityStation sec = ((TileEntityCapacitor)tileEntity).getLinkedSecurityStation();
      if (sec != null)
      {
        if (sec.isAccessGranted(entityplayer.username, right))
        {
          return true;
        }
        if (!suppresswarning) {
          Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
        }
        return false;
      }

      return true;
    }

    if ((tileEntity instanceof TileEntityExtractor))
    {
      TileEntityAdvSecurityStation sec = ((TileEntityExtractor)tileEntity).getLinkedSecurityStation();
      if (sec != null)
      {
        if (sec.isAccessGranted(entityplayer.username, right))
        {
          return true;
        }
        if (!suppresswarning) {
          Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
        }
        return false;
      }

      return true;
    }

    if ((tileEntity instanceof TileEntityAreaDefenseStation))
    {
      TileEntityAdvSecurityStation sec = ((TileEntityAreaDefenseStation)tileEntity).getLinkedSecurityStation();

      if (sec != null)
      {
        if (sec.isAccessGranted(entityplayer.username, right))
        {
          return true;
        }
        if (!suppresswarning) {
          Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
        }
        return false;
      }

      return true;
    }

    if ((tileEntity instanceof TileEntityProjector))
    {
      switch (((TileEntityProjector)tileEntity).getaccesstyp())
      {
      case 2:
        TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(((TileEntityProjector)tileEntity).getPowerSourceID()));
        if (cap != null)
        {
          TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
          if (sec != null)
          {
            if (sec.isAccessGranted(entityplayer.username, right))
            {
              return true;
            }
            if (!suppresswarning) {
              Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
            }
            return false;
          }
        }

        break;
      case 3:
        TileEntityAdvSecurityStation sec = ((TileEntityProjector)tileEntity).getLinkedSecurityStation();
        if (sec != null)
        {
          if (sec.isAccessGranted(entityplayer.username, right))
          {
            return true;
          }
          if (!suppresswarning) {
            Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
          }
          return false;
        }

        break;
      }

      return true;
    }

    return true;
  }
}