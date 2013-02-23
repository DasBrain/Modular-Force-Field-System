package chb.mods.mffs.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chb.mods.mffs.api.IForceEnergyStorageBlock;
import chb.mods.mffs.api.IPowerLinkItem;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.Functions;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityConverter;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import chb.mods.mffs.common.tileentity.TileEntityMachines;
import chb.mods.mffs.common.tileentity.TileEntityProjector;

public class ItemCardPowerLink extends ItemCard
  implements IPowerLinkItem
{
  public IForceEnergyStorageBlock storage;

  public ItemCardPowerLink(int i)
  {
    super(i);
    setIconIndex(17);
  }

  public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
  {
    super.onUpdate(itemStack, world, entity, par4, par5);

    if (this.Tick > 600)
    {
      int Cap_ID = getValuefromKey("CapacitorID", itemStack);
      if (Cap_ID != 0)
      {
        TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(Cap_ID));
        if (cap != null)
        {
          if (!cap.getDeviceName().equals(getforAreaname(itemStack)))
          {
            setforArea(itemStack, cap.getDeviceName());
          }
        }
      }

      this.Tick = 0;
    }
    this.Tick += 1;
  }

  public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
  {
    TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

    if (!world.isRemote)
    {
      if (((tileEntity instanceof TileEntityConverter)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Power-Link>");
      }

      if (((tileEntity instanceof TileEntityProjector)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Power-Link>");
      }

      if (((tileEntity instanceof TileEntityExtractor)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        if (((TileEntityExtractor)tileEntity).getStackInSlot(1) == null)
        {
          ((TileEntityExtractor)tileEntity).setInventorySlotContents(1, itemstack);
          entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
          Functions.ChattoPlayer(entityplayer, "Success: <Power-Link> Card installed");
          return true;
        }

        if (((TileEntityExtractor)tileEntity).getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemcardempty)
        {
          ItemStack itemstackcopy = itemstack.copy();
          ((TileEntityExtractor)tileEntity).setInventorySlotContents(1, itemstackcopy);
          Functions.ChattoPlayer(entityplayer, "Success: <Power-Link> Card data copied ");
          return true;
        }
        Functions.ChattoPlayer(entityplayer, "Fail: Slot is not empty");
        return false;
      }

      if (((tileEntity instanceof TileEntityAreaDefenseStation)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Power-Link>");
      }

      if (((tileEntity instanceof TileEntityCapacitor)) && 
        (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
      {
        return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 2, "<Power-Link>");
      }

    }

    return false;
  }

  public IForceEnergyStorageBlock getForceEnergyStorageBlock(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    if ((itemStack != null) && ((itemStack.getItem() instanceof ItemCard)))
    {
      if (((ItemCard)itemStack.getItem()).isvalid(itemStack))
      {
        PointXYZ png = getCardTargetPoint(itemStack);
        if (png != null)
        {
          if (png.dimensionId != world.provider.dimensionId) return null;

          if ((world.getBlockTileEntity(png.X, png.Y, png.Z) instanceof TileEntityCapacitor))
          {
            TileEntityCapacitor cap = (TileEntityCapacitor)world.getBlockTileEntity(png.X, png.Y, png.Z);
            if (cap != null)
            {
              if ((cap.getPowerStorageID() == getValuefromKey("CapacitorID", itemStack)) && (getValuefromKey("CapacitorID", itemStack) != 0))
              {
                if (!cap.getDeviceName().equals(getforAreaname(itemStack)))
                {
                  setforArea(itemStack, cap.getDeviceName());
                }

                if (cap.getTransmitRange() >= PointXYZ.distance(tem.getMaschinePoint(), cap.getMaschinePoint()))
                  return cap; return null;
              }
            }
          }
          else {
            int Cap_ID = getValuefromKey("CapacitorID", itemStack);
            if (Cap_ID != 0)
            {
              TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(png.getPointWorld()).getCapacitor().get(Integer.valueOf(Cap_ID));
              if (cap != null)
              {
                setInformation(itemStack, cap.getMaschinePoint(), "CapacitorID", Cap_ID);
                if (cap.getTransmitRange() >= PointXYZ.distance(tem.getMaschinePoint(), cap.getMaschinePoint()))
                  return cap; return null;
              }
            }
          }

          if (world.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded) {
            ((ItemCard)itemStack.getItem()).setinvalid(itemStack);
          }
        }
      }
    }
    return null;
  }

  public int getAvailablePower(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.getStorageAvailablePower();
    return 0;
  }

  public int getMaximumPower(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.getStorageMaxPower();
    return 1;
  }

  public int getPowersourceID(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.getPowerStorageID();
    return 0;
  }

  public int getPercentageCapacity(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.getPercentageStorageCapacity();
    return 0;
  }

  public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.consumePowerfromStorage(powerAmount, simulation);
    return false;
  }

  public boolean insertPower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.insertPowertoStorage(powerAmount, simulation);
    return false;
  }

  public int getfreeStorageAmount(ItemStack itemStack, TileEntityMachines tem, World world)
  {
    this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
    if (this.storage != null)
      return this.storage.getfreeStorageAmount();
    return 0;
  }

  public boolean isPowersourceItem()
  {
    return false;
  }
}