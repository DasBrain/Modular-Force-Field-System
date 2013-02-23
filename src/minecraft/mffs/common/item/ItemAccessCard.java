package mffs.common.item;

import java.util.List;

import mffs.common.Linkgrid;
import mffs.common.NBTTagCompoundHelper;
import mffs.common.SecurityRight;
import mffs.common.tileentity.TileEntityAdvSecurityStation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class ItemAccessCard extends ItemCardPersonalID
{
	private int Tick;

	public ItemAccessCard(int i)
	{
		super(i);
		setIconIndex(20);
		setMaxStackSize(1);
	}

	public String getTextureFile()
	{
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable()
	{
		return false;
	}

	public boolean isDamageable()
	{
		return true;
	}

	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
	{
		if (this.Tick > 1200)
		{
			if (getvalidity(itemStack) > 0)
			{
				setvalidity(itemStack, getvalidity(itemStack) - 1);

				int SEC_ID = getlinkID(itemStack);
				if (SEC_ID != 0)
				{
					TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation) Linkgrid.getWorldMap(world).getSecStation().get(Integer.valueOf(SEC_ID));
					if (sec != null)
					{
						if (!sec.getDeviceName().equals(getforAreaname(itemStack)))
						{
							setforArea(itemStack, sec);
						}
					}
				}
			}

			this.Tick = 0;
		}
		this.Tick += 1;
	}

	public static void setvalidity(ItemStack itemStack, int min)
	{
		NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
		nbtTagCompound.setInteger("validity", min);
	}

	public static int getvalidity(ItemStack itemStack)
	{
		NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
		if (nbtTagCompound != null)
		{
			return nbtTagCompound.getInteger("validity");
		}
		return 0;
	}

	public static boolean hasRight(ItemStack itemStack, SecurityRight sr)
	{
		NBTTagCompound itemTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
		NBTTagCompound rightsTag = itemTag.getCompoundTag("rights");

		if (itemTag.hasKey(sr.rightKey))
		{
			setRight(itemStack, sr, itemTag.getBoolean(sr.rightKey));
			itemTag.removeTag(sr.rightKey);
		}
		return rightsTag.getBoolean(sr.rightKey);
	}

	public static void setRight(ItemStack itemStack, SecurityRight sr, boolean value)
	{
		NBTTagCompound rightsTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getCompoundTag("rights");
		rightsTag.setBoolean(sr.rightKey, value);
		NBTTagCompoundHelper.getTAGfromItemstack(itemStack).setCompoundTag("rights", rightsTag);
	}

	public static int getlinkID(ItemStack itemstack)
	{
		NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
		if (nbtTagCompound != null)
		{
			return nbtTagCompound.getInteger("linkID");
		}
		return 0;
	}

	public static void setlinkID(ItemStack itemStack, TileEntityAdvSecurityStation sec)
	{
		if (sec != null)
		{
			NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
			nbtTagCompound.setInteger("linkID", sec.getDeviceID());
		}
	}

	public static void setforArea(ItemStack itemStack, TileEntityAdvSecurityStation sec)
	{
		if (sec != null)
		{
			NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
			nbtTagCompound.setString("Areaname", sec.getDeviceName());
		}
	}

	public static String getforAreaname(ItemStack itemstack)
	{
		NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
		if (nbtTagCompound != null)
		{
			return nbtTagCompound.getString("Areaname");
		}
		return "not set";
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		String SecurityArea = String.format("Security Area: %s ", new Object[] { getforAreaname(itemStack) });
		info.add(SecurityArea);

		String validity = String.format("period of validity: %s min", new Object[] { Integer.valueOf(getvalidity(itemStack)) });
		info.add(validity);
		NBTTagCompound rightsTag;
		if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)))
		{
			rightsTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getCompoundTag("rights");
			info.add("Rights:");
			for (SecurityRight sr : SecurityRight.rights.values())
				if (rightsTag.getBoolean(sr.rightKey))
					info.add("-" + sr.name);
		}
		else
		{
			info.add("Rights: (Hold Shift)");
		}
	}
}