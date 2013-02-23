package chb.mods.mffs.common.item;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.Functions;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.Linkgrid.Worldlinknet;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ISidedInventory;

public class ItemCardSecurityLink extends ItemCard
{
  public ItemCardSecurityLink(int i)
  {
    super(i);
    setIconIndex(19);
  }

  public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
  {
    super.onUpdate(itemStack, world, entity, par4, par5);

    if (this.Tick > 600)
    {
      int Sec_ID = getValuefromKey("Secstation_ID", itemStack);
      if (Sec_ID != 0)
      {
        TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)Linkgrid.getWorldMap(world).getSecStation().get(Integer.valueOf(Sec_ID));
        if (sec != null)
        {
          if (!sec.getDeviceName().equals(getforAreaname(itemStack)))
          {
            setforArea(itemStack, sec.getDeviceName());
          }
        }
      }

      this.Tick = 0;
    }
    this.Tick += 1;
  }

  public static TileEntityAdvSecurityStation getLinkedSecurityStation(ISidedInventory inventiory, int slot, World world)
  {
    if (inventiory.getStackInSlot(slot) != null)
    {
      if ((inventiory.getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink))
      {
        ItemCardSecurityLink card = (ItemCardSecurityLink)inventiory.getStackInSlot(slot).getItem();
        PointXYZ png = card.getCardTargetPoint(inventiory.getStackInSlot(slot));
        if (png != null)
        {
          if (png.dimensionId != world.provider.dimensionId) return null;

          if ((world.getBlockTileEntity(png.X, png.Y, png.Z) instanceof TileEntityAdvSecurityStation))
          {
            TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)world.getBlockTileEntity(png.X, png.Y, png.Z);
            if (sec != null)
            {
              if ((sec.getDeviceID() == card.getValuefromKey("Secstation_ID", inventiory.getStackInSlot(slot))) && (card.getValuefromKey("Secstation_ID", inventiory.getStackInSlot(slot)) != 0))
              {
                if (!sec.getDeviceName().equals(getforAreaname(inventiory.getStackInSlot(slot))))
                {
                  setforArea(inventiory.getStackInSlot(slot), sec.getDeviceName());
                }
                return sec;
              }
            }
          }
          else {
            int Sec_ID = card.getValuefromKey("Secstation_ID", inventiory.getStackInSlot(slot));
            if (Sec_ID != 0)
            {
              TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)Linkgrid.getWorldMap(world).getSecStation().get(Integer.valueOf(Sec_ID));
              if (sec != null)
              {
                card.setInformation(inventiory.getStackInSlot(slot), sec.getMaschinePoint(), "Secstation_ID", Sec_ID);
                return sec;
              }
            }
          }

          if (world.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded) {
            inventiory.setInventorySlotContents(slot, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
          }
        }
      }
    }
    return null;
  }

  public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
  {
    TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

    if (!world.isRemote)
    {
      if ((tileEntity instanceof TileEntityControlSystem))
      {
        if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB))
        {
          return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Security Station Link>");
        }

      }

      if ((tileEntity instanceof TileEntityCapacitor))
      {
        if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB))
        {
          return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 4, "<Security Station Link>");
        }

      }

      if (((tileEntity instanceof TileEntityAreaDefenseStation)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 1, "<Security Station Link>");
      }

      if (((tileEntity instanceof TileEntitySecStorage)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Security Station Link>");
      }

      if (((tileEntity instanceof TileEntityProjector)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 12, "<Security Station Link>");
      }

    }

    return false;
  }
}