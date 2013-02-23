package chb.mods.mffs.common.item;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.Functions;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemCardEmpty extends ItemMFFSBase
{
  public ItemCardEmpty(int i)
  {
    super(i);
    setIconIndex(16);
    setMaxStackSize(16);
  }

  public String getTextureFile() {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable() {
    return false;
  }

  public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
  {
    if (world.isRemote) {
      return false;
    }
    TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

    if ((tileEntity instanceof TileEntityAdvSecurityStation))
    {
      if (((TileEntityAdvSecurityStation)tileEntity).isActive())
      {
        if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.CSR))
        {
          ItemStack newcard = new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard);
          ((ItemCardSecurityLink)newcard.getItem()).setInformation(newcard, new PointXYZ(i, j, k, world), "Secstation_ID", ((TileEntityAdvSecurityStation)tileEntity).getDeviceID());
          ((ItemCardSecurityLink)newcard.getItem()); ItemCardSecurityLink.setforArea(newcard, ((TileEntityAdvSecurityStation)tileEntity).getDeviceName());

          if (--itemstack.stackSize <= 0)
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, newcard);
          else if (!entityplayer.inventory.addItemStackToInventory(newcard)) {
            entityplayer.dropPlayerItem(newcard);
          }
          entityplayer.inventoryContainer.detectAndSendChanges();
          Functions.ChattoPlayer(entityplayer, "[Security Station] Success: <Security Station Link>  Card create");

          return true;
        }
      }
      else
      {
        Functions.ChattoPlayer(entityplayer, "[Security Station] Fail: Security Station must be Active  for create");
      }

    }

    if ((tileEntity instanceof TileEntityCapacitor))
    {
      if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB))
      {
        ItemStack newcard = new ItemStack(ModularForceFieldSystem.MFFSitemfc);
        ((ItemCardPowerLink)newcard.getItem()).setInformation(newcard, new PointXYZ(i, j, k, world), "CapacitorID", ((TileEntityCapacitor)tileEntity).getPowerStorageID());
        ((ItemCardPowerLink)newcard.getItem()); ItemCardPowerLink.setforArea(newcard, ((TileEntityCapacitor)tileEntity).getDeviceName());

        if (--itemstack.stackSize <= 0)
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, newcard);
        else if (!entityplayer.inventory.addItemStackToInventory(newcard)) {
          entityplayer.dropPlayerItem(newcard);
        }
        entityplayer.inventoryContainer.detectAndSendChanges();

        entityplayer.addChatMessage("[Capacitor] Success: <Power-Link> Card create");

        return true;
      }

    }

    return false;
  }
}