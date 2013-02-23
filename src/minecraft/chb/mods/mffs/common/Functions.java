package chb.mods.mffs.common;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Functions
{
  public static Block getBlock(int x, int y, int z, World world)
  {
    return Block.blocksList[world.getBlockId(x, y, z)];
  }

  public static ArrayList getItemStackFromBlock(World world, int i, int j, int k)
  {
    Block block = Block.blocksList[world.getBlockId(i, j, k)];

    if (block == null) {
      return null;
    }
    int meta = world.getBlockMetadata(i, j, k);

    return block.getBlockDropped(world, i, j, k, meta, 0);
  }

  public static void ChattoPlayer(EntityPlayer player, String Message)
  {
    player.addChatMessage(Message);
  }

  public static boolean setIteminSlot(ItemStack itemstack, EntityPlayer entityplayer, TileEntity tileEntity, int Slot, String Cardname)
  {
    if (((IInventory)tileEntity).getStackInSlot(Slot) == null)
    {
      ((IInventory)tileEntity).setInventorySlotContents(Slot, itemstack);
      entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
      ChattoPlayer(entityplayer, "Success: " + Cardname + " Card installed");
      ((IInventory)tileEntity).onInventoryChanged();
      return true;
    }

    if (((IInventory)tileEntity).getStackInSlot(Slot).getItem() == ModularForceFieldSystem.MFFSitemcardempty)
    {
      ItemStack itemstackcopy = itemstack.copy();
      ((IInventory)tileEntity).setInventorySlotContents(Slot, itemstackcopy);
      ChattoPlayer(entityplayer, "Success: " + Cardname + " Card data copied ");
      ((IInventory)tileEntity).onInventoryChanged();
      return true;
    }
    ChattoPlayer(entityplayer, "Fail: Slot is not empty");
    return false;
  }
}