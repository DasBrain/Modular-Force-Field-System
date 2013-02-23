package chb.mods.mffs.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.NBTTagCompoundHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemForcicumCell extends ItemMFFSBase
{
  private boolean aktiv = false;

  public ItemForcicumCell(int id) {
    super(id);
    setIconIndex(98);
    setMaxStackSize(1);
    setMaxDamage(100);
  }

  public boolean isRepairable()
  {
    return false;
  }

  public boolean isDamageable()
  {
    return true;
  }

  public String getTextureFile()
  {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public int getItemDamage(ItemStack itemStack)
  {
    return 101 - getForceciumlevel(itemStack) * 100 / getMaxForceciumlevel();
  }

  public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
  {
    if (!world.isRemote)
    {
      if (this.aktiv)
      {
        if (getForceciumlevel(itemStack) < getMaxForceciumlevel())
        {
          if ((entity instanceof EntityPlayer))
          {
            List slots = ((EntityPlayer)entity).inventoryContainer.inventorySlots;
            for (Slot slot : slots) {
              if ((slot.getStack() != null) && 
                (slot.getStack().getItem() == ModularForceFieldSystem.MFFSitemForcicium))
              {
                setForceciumlevel(itemStack, getForceciumlevel(itemStack) + 1);

                if (slot.getStack().stackSize > 1)
                {
                  ItemStack forcecium = new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, slot.getStack().stackSize - 1);
                  slot.putStack(forcecium);
                  break;
                }
                slot.putStack(null);

                break;
              }

            }

          }

        }

        itemStack.setItemDamage(getItemDamage(itemStack));
      }
    }
  }

  public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
  {
    String tooltip = String.format("%d / %d  Forcicum  ", new Object[] { Integer.valueOf(getForceciumlevel(itemStack)), Integer.valueOf(getMaxForceciumlevel()) });
    info.add(tooltip);
  }

  public boolean useForcecium(int count, ItemStack itemstack)
  {
    if (count > getForceciumlevel(itemstack))
    {
      return false;
    }

    setForceciumlevel(itemstack, getForceciumlevel(itemstack) - count);
    return true;
  }

  public int getMaxForceciumlevel()
  {
    return 1000;
  }

  public void setForceciumlevel(ItemStack itemStack, int Forceciumlevel)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
    nbtTagCompound.setInteger("Forceciumlevel", Forceciumlevel);
  }

  public int getForceciumlevel(ItemStack itemstack)
  {
    NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    if (nbtTagCompound != null)
    {
      return nbtTagCompound.getInteger("Forceciumlevel");
    }
    return 0;
  }

  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
  {
    if (!world.isRemote)
    {
      if (!this.aktiv)
      {
        this.aktiv = true;
        entityplayer.addChatMessage("[Forcicum Cell] Active");
      } else {
        this.aktiv = false;
        entityplayer.addChatMessage("[Forcicum Cell] Inactive");
      }

    }

    return itemstack;
  }

  @SideOnly(Side.CLIENT)
  public void getSubItems(int i, CreativeTabs tabs, List itemList)
  {
    ItemStack charged = new ItemStack(this, 1);
    charged.setItemDamage(1);
    setForceciumlevel(charged, getMaxForceciumlevel());
    itemList.add(charged);

    ItemStack empty = new ItemStack(this, 1);
    empty.setItemDamage(100);
    setForceciumlevel(empty, 0);
    itemList.add(empty);
  }
}