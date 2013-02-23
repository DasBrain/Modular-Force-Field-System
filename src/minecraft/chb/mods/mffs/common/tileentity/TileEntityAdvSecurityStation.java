package chb.mods.mffs.common.tileentity;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.NBTTagCompoundHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.container.ContainerAdvSecurityStation;
import chb.mods.mffs.common.item.ItemAccessCard;
import chb.mods.mffs.common.item.ItemCardEmpty;
import chb.mods.mffs.common.item.ItemCardPersonalID;
import chb.mods.mffs.common.item.ItemCardPowerLink;
import chb.mods.mffs.common.item.ItemCardSecurityLink;
import chb.mods.mffs.common.multitool.ItemDebugger;
import chb.mods.mffs.network.server.NetworkHandlerServer;

public class TileEntityAdvSecurityStation extends TileEntityMachines
{
	private String MainUser;
	private ItemStack[] inventory;

	public TileEntityAdvSecurityStation()
	{
		this.inventory = new ItemStack[40];
		this.MainUser = "";
	}

	public void dropplugins()
	{
		for (int a = 0; a < this.inventory.length; a++)
			dropplugins(a);
	}

	public String getMainUser()
	{
		return this.MainUser;
	}

	public void setMainUser(String s)
	{
		if (!this.MainUser.equals(s))
		{
			this.MainUser = s;
			NetworkHandlerServer.updateTileEntityField(this, "MainUser");
		}
	}

	public void dropplugins(int slot)
	{
		if (getStackInSlot(slot) != null)
		{
			if (((getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink)) || ((getStackInSlot(slot).getItem() instanceof ItemCardPowerLink)) || ((getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)))
			{
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1)));
			}
			else
			{
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, getStackInSlot(slot)));
			}

			setInventorySlotContents(slot, null);
			onInventoryChanged();
		}
	}

	public Container getContainer(InventoryPlayer inventoryplayer)
	{
		return new ContainerAdvSecurityStation(inventoryplayer.player, this);
	}

	public void invalidate()
	{
		Linkgrid.getWorldMap(this.worldObj).getSecStation().remove(Integer.valueOf(getDeviceID()));
		super.invalidate();
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		this.inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);

			byte byte0 = nbttagcompound1.getByte("Slot");
			if ((byte0 >= 0) && (byte0 < this.inventory.length))
				this.inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++)
		{
			if (this.inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			if (getTicker() == 10)
			{
				if (!getMainUser().equals(""))
				{
					if (isActive() != true)
					{
						setActive(true);
					}
				}
				else if (isActive())
				{
					setActive(false);
				}

				checkslots();
				setTicker((short) 0);
			}
			setTicker((short) (getTicker() + 1));
		}

		super.updateEntity();
	}

	public void checkslots()
	{
		if (getStackInSlot(0) != null)
		{
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSItemIDCard)
			{
				ItemCardPersonalID Card = (ItemCardPersonalID) getStackInSlot(0).getItem();

				String name = Card.getUsername(getStackInSlot(0));

				if (!getMainUser().equals(name))
				{
					setMainUser(name);
				}

				if (ItemCardPersonalID.hasRight(getStackInSlot(0), SecurityRight.CSR) != true)
					ItemCardPersonalID.setRight(getStackInSlot(0), SecurityRight.CSR, true);
			}
			else
			{
				setMainUser("");
			}
		}
		else
		{
			setMainUser("");
		}

		if ((getStackInSlot(39) != null) && (getStackInSlot(38) != null) && ((getStackInSlot(38).getItem() instanceof ItemCardEmpty)) && ((getStackInSlot(39).getItem() instanceof ItemCardPersonalID)))
		{
			setInventorySlotContents(38, ItemStack.copyItemStack(getStackInSlot(39)));
		}
	}

	public int getSizeInventory()
	{
		return this.inventory.length;
	}

	public ItemStack getStackInSlot(int i)
	{
		return this.inventory[i];
	}

	public int getInventoryStackLimit()
	{
		return 1;
	}

	public ItemStack decrStackSize(int i, int j)
	{
		if (this.inventory[i] != null)
		{
			if (this.inventory[i].stackSize <= j)
			{
				ItemStack itemstack = this.inventory[i];
				this.inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = this.inventory[i].splitStack(j);
			if (this.inventory[i].stackSize == 0)
			{
				this.inventory[i] = null;
			}
			return itemstack1;
		}
		return null;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.inventory[i] = itemstack;
		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
			itemstack.stackSize = getInventoryStackLimit();
	}

	public String getInvName()
	{
		return "Secstation";
	}

	public boolean RemoteInventory(String username, SecurityRight right)
	{
		for (int a = 35; a >= 1; a--)
		{
			if ((getStackInSlot(a) != null) && (getStackInSlot(a).getItem() == ModularForceFieldSystem.MFFSItemIDCard))
			{
				String username_invtory = NBTTagCompoundHelper.getTAGfromItemstack(getStackInSlot(a)).getString("name");

				ItemCardPersonalID Card = (ItemCardPersonalID) getStackInSlot(a).getItem();

				boolean access = ItemCardPersonalID.hasRight(getStackInSlot(a), right);

				if (username_invtory.equals(username))
				{
					if (access)
					{
						return true;
					}
					return false;
				}

			}

		}

		return false;
	}

	public boolean RemotePlayerInventory(String username, SecurityRight right)
	{
		EntityPlayer player = this.worldObj.getPlayerEntityByName(username);
		if (player != null)
		{
			List<Slot> slots = player.inventoryContainer.inventorySlots;
			for (Slot slot : slots)
			{
				ItemStack stack = slot.getStack();
				if (stack != null)
				{
					if ((stack.getItem() instanceof ItemAccessCard))
					{
						if (ItemAccessCard.getvalidity(stack) > 0)
						{
							if (ItemAccessCard.getlinkID(stack) == getDeviceID())
							{
								if (ItemAccessCard.hasRight(stack, right))
								{
									if (!ItemAccessCard.getforAreaname(stack).equals(getDeviceName()))
									{
										ItemAccessCard.setforArea(stack, this);
									}
									return true;
								}
							}
						}
						else
						{
							player.sendChatToPlayer("[Security Station] expired validity <Access license>");
							ItemStack Card = new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1);
							slot.putStack(Card);
							NetworkHandlerServer.syncClientPlayerinventorySlot(player, slot, Card);
						}
					}

					if ((stack.getItem() instanceof ItemDebugger))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAccessGranted(String username, SecurityRight sr)
	{
		if (!isActive())
		{
			return true;
		}
		String[] ops = ModularForceFieldSystem.Admin.split(";");

		for (int i = 0; i <= ops.length - 1; i++)
		{
			if (username.equalsIgnoreCase(ops[i]))
			{
				return true;
			}
		}
		if (this.MainUser.equals(username))
		{
			return true;
		}
		if (RemoteInventory(username, sr))
		{
			return true;
		}
		if (RemotePlayerInventory(username, sr))
		{
			return true;
		}

		return false;
	}

	public ItemStack[] getContents()
	{
		return this.inventory;
	}

	public int getStartInventorySide(ForgeDirection side)
	{
		return 0;
	}

	public int getSizeInventorySide(ForgeDirection side)
	{
		return 0;
	}

	public List getFieldsforUpdate()
	{
		List NetworkedFields = new LinkedList();
		NetworkedFields.clear();

		NetworkedFields.addAll(super.getFieldsforUpdate());
		NetworkedFields.add("MainUser");

		return NetworkedFields;
	}

	public boolean isItemValid(ItemStack par1ItemStack, int Slot)
	{
		switch (Slot)
		{
			case 38:
				if (!(par1ItemStack.getItem() instanceof ItemCardEmpty))
					return false;
				break;
			case 39:
				if (((par1ItemStack.getItem() instanceof ItemAccessCard)) || (!(par1ItemStack.getItem() instanceof ItemCardPersonalID)))
					return false;

				break;
		}

		if (((par1ItemStack.getItem() instanceof ItemCardPersonalID)) || ((par1ItemStack.getItem() instanceof ItemCardEmpty)))
		{
			return true;
		}
		return false;
	}

	public void onNetworkHandlerEvent(int key, String value)
	{
		switch (key)
		{
			case 100:
				if (getStackInSlot(1) != null)
				{
					SecurityRight sr = (SecurityRight) SecurityRight.rights.get(value);
					if ((sr != null) && ((getStackInSlot(1).getItem() instanceof ItemCardPersonalID)))
						ItemCardPersonalID.setRight(getStackInSlot(1), sr, !ItemCardPersonalID.hasRight(getStackInSlot(1), sr));
				}
				break;
			case 101:
				if ((getStackInSlot(1) != null) && ((getStackInSlot(1).getItem() instanceof ItemAccessCard)))
					if (ItemAccessCard.getvalidity(getStackInSlot(1)) <= 5)
						setInventorySlotContents(1, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty, 1));
					else
						ItemAccessCard.setvalidity(getStackInSlot(1), ItemAccessCard.getvalidity(getStackInSlot(1)) - 5);
				break;
			case 102:
				if (getStackInSlot(1) != null)
				{
					if ((getStackInSlot(1).getItem() instanceof ItemCardEmpty))
					{
						setInventorySlotContents(1, new ItemStack(ModularForceFieldSystem.MFFSAccessCard, 1));
						if ((getStackInSlot(1).getItem() instanceof ItemAccessCard))
						{
							ItemAccessCard.setforArea(getStackInSlot(1), this);
							ItemAccessCard.setvalidity(getStackInSlot(1), 5);
							ItemAccessCard.setlinkID(getStackInSlot(1), this);
						}

					}
					else if ((getStackInSlot(1).getItem() instanceof ItemAccessCard))
					{
						ItemAccessCard.setvalidity(getStackInSlot(1), ItemAccessCard.getvalidity(getStackInSlot(1)) + 5);
					}
				}
				break;
		}

		super.onNetworkHandlerEvent(key, value);
	}

	public ItemStack getModCardStack()
	{
		if (getStackInSlot(1) != null)
		{
			return getStackInSlot(1);
		}
		return null;
	}

	public int getSlotStackLimit(int slt)
	{
		return 1;
	}

	public TileEntityAdvSecurityStation getLinkedSecurityStation()
	{
		return this;
	}
}