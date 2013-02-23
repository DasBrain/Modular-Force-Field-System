package mffs.common.multitool;

import ic2.api.IWrenchable;
import mffs.api.IMFFS_Wrench;
import mffs.common.Functions;
import mffs.common.tileentity.TileEntityAdvSecurityStation;
import mffs.common.tileentity.TileEntityAreaDefenseStation;
import mffs.common.tileentity.TileEntityMachines;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import railcraft.common.api.core.items.ICrowbar;
import buildcraft.api.tools.IToolWrench;

public class ItemWrench extends ItemMultitool implements IToolWrench, ICrowbar
{
	public ItemWrench(int id)
	{
		super(id, 0);
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return false;
		}

		TileEntity tileentity = world.getBlockTileEntity(x, y, z);

		if ((tileentity instanceof TileEntityProjector))
		{
			if (((TileEntityProjector) tileentity).isBurnout())
			{
				if (consumePower(stack, 10000, true))
				{
					consumePower(stack, 10000, false);
					((TileEntityProjector) tileentity).setBurnedOut(false);
					Functions.ChattoPlayer(player, "[MultiTool] Projector repaired");
					return true;
				}

				Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FP please charge need min 10000");
				return false;
			}

		}

		if (((tileentity instanceof IWrenchable)) && (!(tileentity instanceof IMFFS_Wrench)))
		{
			if (consumePower(stack, 1000, true))
			{
				if (((IWrenchable) tileentity).wrenchCanSetFacing(player, side))
				{
					((IWrenchable) tileentity).setFacing((short) side);
					consumePower(stack, 1000, false);
					return true;
				}

			}
			else
			{
				Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FE please charge need min 1000 for change Facing");
			}

			if (consumePower(stack, 25000, true))
			{
				if (((IWrenchable) tileentity).wrenchCanRemove(player))
				{
					world.spawnEntityInWorld(new EntityItem(world, x, y, z, ((IWrenchable) tileentity).getWrenchDrop(player)));
					world.setBlockWithNotify(x, y, z, 0);
					consumePower(stack, 25000, false);
				}

			}
			else
			{
				Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FE please charge need min 25000 for remove");
			}

		}

		if ((tileentity instanceof IMFFS_Wrench))
		{
			if (consumePower(stack, 1000, true))
			{
				if (((IMFFS_Wrench) tileentity).wrenchCanManipulate(player, side))
				{
					if ((tileentity instanceof TileEntityMachines))
					{
						if ((tileentity instanceof TileEntityProjector))
						{
							if (((TileEntityProjector) tileentity).isActive())
							{
								return false;
							}
						}

						if ((tileentity instanceof TileEntityAdvSecurityStation))
						{
							if (((TileEntityAdvSecurityStation) tileentity).isActive())
							{
								return false;
							}
						}
						if ((tileentity instanceof TileEntityAreaDefenseStation))
						{
							if (((TileEntityAreaDefenseStation) tileentity).isActive())
							{
								return false;
							}
						}

					}

					if (((IMFFS_Wrench) tileentity).getSide() != side)
					{
						((IMFFS_Wrench) tileentity).setSide(side);
						consumePower(stack, 1000, false);
						return true;
					}

					world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(net.minecraft.block.Block.blocksList[world.getBlockId(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)])));
					world.setBlockWithNotify(x, y, z, 0);
					consumePower(stack, 1000, false);
				}

			}
			else
			{
				Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FP please charge need min 1000");
			}

		}

		return false;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return super.onItemRightClick(itemstack, world, entityplayer);
	}

	public boolean canWrench(EntityPlayer player, int x, int y, int z)
	{
		if (consumePower(player.inventory.getCurrentItem(), 1000, true))
		{
			return true;
		}
		return false;
	}

	public void wrenchUsed(EntityPlayer player, int x, int y, int z)
	{
		consumePower(player.inventory.getCurrentItem(), 1000, false);
	}
}