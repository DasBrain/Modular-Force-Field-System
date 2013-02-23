package mffs.common.tileentity;

import java.util.LinkedList;
import java.util.List;

import mffs.api.IForceEnergyItems;
import mffs.api.IForceEnergyStorageBlock;
import mffs.api.IPowerLinkItem;
import mffs.common.Linkgrid;
import mffs.common.ModularForceFieldSystem;
import mffs.common.container.ContainerCapacitor;
import mffs.common.item.ItemCapacitorUpgradeCapacity;
import mffs.common.item.ItemCapacitorUpgradeRange;
import mffs.common.item.ItemCardSecurityLink;
import mffs.network.INetworkHandlerEventListener;
import mffs.network.server.NetworkHandlerServer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityCapacitor extends TileEntityFEPoweredMachine implements INetworkHandlerEventListener, IForceEnergyStorageBlock
{
	private ItemStack[] inventory;
	private int forcePower;
	private short linketprojektor;
	private int capacity;
	private int Powerlinkmode;
	private int TransmitRange;

	public TileEntityCapacitor()
	{
		this.inventory = new ItemStack[5];
		this.forcePower = 0;
		this.linketprojektor = 0;
		this.TransmitRange = 8;
		this.capacity = 0;
		this.Powerlinkmode = 0;
	}

	public int getPowerStorageID()
	{
		return getDeviceID();
	}

	public void setTransmitRange(int transmitRange)
	{
		this.TransmitRange = transmitRange;
		NetworkHandlerServer.updateTileEntityField(this, "TransmitRange");
	}

	public int getTransmitRange()
	{
		return this.TransmitRange;
	}

	public int getPowerlinkmode()
	{
		return this.Powerlinkmode;
	}

	public void setPowerlinkmode(int powerlinkmode)
	{
		this.Powerlinkmode = powerlinkmode;
	}

	public int getPercentageStorageCapacity()
	{
		return this.capacity;
	}

	public void setCapacity(int Capacity)
	{
		if (getPercentageStorageCapacity() != Capacity)
		{
			this.capacity = Capacity;
			NetworkHandlerServer.updateTileEntityField(this, "capacity");
		}
	}

	public Container getContainer(InventoryPlayer inventoryplayer)
	{
		return new ContainerCapacitor(inventoryplayer.player, this);
	}

	public Short getLinketProjektor()
	{
		return Short.valueOf(this.linketprojektor);
	}

	public void setLinketprojektor(Short linketprojektor)
	{
		if (this.linketprojektor != linketprojektor.shortValue())
		{
			this.linketprojektor = linketprojektor.shortValue();
			NetworkHandlerServer.updateTileEntityField(this, "linketprojektor");
		}
	}

	public int getStorageAvailablePower()
	{
		return this.forcePower;
	}

	public void setForcePower(int f)
	{
		this.forcePower = f;
	}

	public int getSizeInventory()
	{
		return this.inventory.length;
	}

	public TileEntityAdvSecurityStation getLinkedSecurityStation()
	{
		return ItemCardSecurityLink.getLinkedSecurityStation(this, 4, this.worldObj);
	}

	public int getSecStation_ID()
	{
		TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
		if (sec != null)
			return sec.getDeviceID();
		return 0;
	}

	public int getStorageMaxPower()
	{
		if ((getStackInSlot(0) != null) && (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap))
		{
			if (this.forcePower > 10000000 + 2000000 * getStackInSlot(0).stackSize)
			{
				setForcePower(10000000 + 2000000 * getStackInSlot(0).stackSize);
			}
			return 10000000 + 2000000 * getStackInSlot(0).stackSize;
		}

		if (this.forcePower > 10000000)
			setForcePower(10000000);
		return 10000000;
	}

	private void checkslots(boolean init)
	{
		if (getStackInSlot(1) != null)
		{
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemupgradecaprange)
			{
				setTransmitRange(8 * (getStackInSlot(1).stackSize + 1));
			}
		}
		else
		{
			setTransmitRange(8);
		}

		if (getStackInSlot(2) != null)
		{
			if ((getStackInSlot(2).getItem() instanceof IForceEnergyItems))
			{
				if ((getPowerlinkmode() != 3) && (getPowerlinkmode() != 4))
					setPowerlinkmode(3);
				IForceEnergyItems ForceEnergyItem = (IForceEnergyItems) getStackInSlot(2).getItem();

				switch (getPowerlinkmode())
				{
					case 3:
						if (ForceEnergyItem.getAvailablePower(getStackInSlot(2)) < ForceEnergyItem.getMaximumPower(null))
						{
							int maxtransfer = ForceEnergyItem.getPowerTransferrate();
							int freeeamount = ForceEnergyItem.getMaximumPower(null) - ForceEnergyItem.getAvailablePower(getStackInSlot(2));

							if (getStorageAvailablePower() > 0)
							{
								if (getStorageAvailablePower() > maxtransfer)
								{
									if (freeeamount > maxtransfer)
									{
										ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + maxtransfer);
										setForcePower(getStorageAvailablePower() - maxtransfer);
									}
									else
									{
										ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + freeeamount);
										setForcePower(getStorageAvailablePower() - freeeamount);
									}
								}
								else if (freeeamount > getStorageAvailablePower())
								{
									ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + getStorageAvailablePower());
									setForcePower(getStorageAvailablePower() - getStorageAvailablePower());
								}
								else
								{
									ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) + freeeamount);
									setForcePower(getStorageAvailablePower() - freeeamount);
								}

								getStackInSlot(2).setItemDamage(ForceEnergyItem.getItemDamage(getStackInSlot(2)));
							}
						}
						break;
					case 4:
						if (ForceEnergyItem.getAvailablePower(getStackInSlot(2)) > 0)
						{
							int maxtransfer = ForceEnergyItem.getPowerTransferrate();
							int freeeamount = getStorageMaxPower() - getStorageAvailablePower();
							int amountleft = ForceEnergyItem.getAvailablePower(getStackInSlot(2));

							if (freeeamount >= amountleft)
							{
								if (amountleft >= maxtransfer)
								{
									ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) - maxtransfer);
									setForcePower(getStorageAvailablePower() + maxtransfer);
								}
								else
								{
									ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) - amountleft);
									setForcePower(getStorageAvailablePower() + amountleft);
								}
							}
							else
							{
								ForceEnergyItem.setAvailablePower(getStackInSlot(2), ForceEnergyItem.getAvailablePower(getStackInSlot(2)) - freeeamount);
								setForcePower(getStorageAvailablePower() + freeeamount);
							}

							getStackInSlot(2).setItemDamage(ForceEnergyItem.getItemDamage(getStackInSlot(2)));
						}

						break;
				}

			}

			if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemfc)
			{
				if ((getPowerlinkmode() != 0) && (getPowerlinkmode() != 1) && (getPowerlinkmode() != 2))
					setPowerlinkmode(0);
			}
		}
	}

	public void dropplugins()
	{
		for (int a = 0; a < this.inventory.length; a++)
			dropplugins(a, this);
	}

	public void invalidate()
	{
		Linkgrid.getWorldMap(this.worldObj).getCapacitor().remove(Integer.valueOf(getDeviceID()));
		super.invalidate();
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		this.forcePower = nbttagcompound.getInteger("forcepower");
		this.Powerlinkmode = nbttagcompound.getInteger("Powerlinkmode");

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

		nbttagcompound.setInteger("forcepower", this.forcePower);
		nbttagcompound.setInteger("Powerlinkmode", this.Powerlinkmode);

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
			if (this.init)
			{
				checkslots(true);
			}

			if ((getSwitchModi() == 1) && (!getSwitchValue()) && (isRedstoneSignal()))
			{
				toggelSwitchValue();
			}
			if ((getSwitchModi() == 1) && (getSwitchValue()) && (!isRedstoneSignal()))
			{
				toggelSwitchValue();
			}

			if (getSwitchValue())
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

			if (getTicker() == 10)
			{
				if (getLinketProjektor().shortValue() != (short) Linkgrid.getWorldMap(this.worldObj).connectedtoCapacitor(this, getTransmitRange()))
				{
					setLinketprojektor(Short.valueOf((short) Linkgrid.getWorldMap(this.worldObj).connectedtoCapacitor(this, getTransmitRange())));
				}
				if (getPercentageStorageCapacity() != getStorageAvailablePower() / 1000 * 100 / (getStorageMaxPower() / 1000))
				{
					setCapacity(getStorageAvailablePower() / 1000 * 100 / (getStorageMaxPower() / 1000));
				}
				checkslots(false);
				if (isActive())
				{
					powertransfer();
				}
				setTicker((short) 0);
			}
			setTicker((short) (getTicker() + 1));
		}
		super.updateEntity();
	}

	private void powertransfer()
	{
		if (hasPowerSource())
		{
			int PowerTransferrate = getMaximumPower() / 120;
			int freeStorageAmount = getMaximumPower() - getAvailablePower();
			int balancelevel = getStorageAvailablePower() - getAvailablePower();

			switch (getPowerlinkmode())
			{
				case 0:
					if ((getPercentageStorageCapacity() >= 95) && (getPercentageCapacity() != 100))
					{
						if (freeStorageAmount > PowerTransferrate)
						{
							emitPower(PowerTransferrate, false);
							consumePowerfromStorage(PowerTransferrate, false);
						}
						else
						{
							emitPower(freeStorageAmount, false);
							consumePowerfromStorage(freeStorageAmount, false);
						}
					}
					break;
				case 1:
					if (getPercentageCapacity() < getPercentageStorageCapacity())
					{
						if (balancelevel > PowerTransferrate)
						{
							emitPower(PowerTransferrate, false);
							consumePowerfromStorage(PowerTransferrate, false);
						}
						else
						{
							emitPower(balancelevel, false);
							consumePowerfromStorage(balancelevel, false);
						}
					}
					break;
				case 2:
					if ((getStorageAvailablePower() > 0) && (getPercentageCapacity() != 100))
					{
						if (getStorageAvailablePower() > PowerTransferrate)
						{
							if (freeStorageAmount > PowerTransferrate)
							{
								emitPower(PowerTransferrate, false);
								consumePowerfromStorage(PowerTransferrate, false);
							}
							else
							{
								emitPower(freeStorageAmount, false);
								consumePowerfromStorage(freeStorageAmount, false);
							}
						}
						else if (freeStorageAmount > getStorageAvailablePower())
						{
							emitPower(getStorageAvailablePower(), false);
							consumePowerfromStorage(getStorageAvailablePower(), false);
						}
						else
						{
							emitPower(freeStorageAmount, false);
							consumePowerfromStorage(freeStorageAmount, false);
						}
					}
					break;
			}
		}
	}

	public ItemStack getStackInSlot(int i)
	{
		return this.inventory[i];
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
		return "Generator";
	}

	public int getStartInventorySide(ForgeDirection side)
	{
		return 0;
	}

	public int getSizeInventorySide(ForgeDirection side)
	{
		return 0;
	}

	public void onNetworkHandlerEvent(int key, String value)
	{
		switch (key)
		{
			case 1:
				if (getStackInSlot(2) != null)
				{
					if ((getStackInSlot(2).getItem() instanceof IForceEnergyItems))
					{
						if (getPowerlinkmode() == 4)
						{
							setPowerlinkmode(3);
						}
						else
							setPowerlinkmode(4);

						return;
					}
					if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemfc)
					{
						if (getPowerlinkmode() < 2)
						{
							setPowerlinkmode(getPowerlinkmode() + 1);
						}
						else
							setPowerlinkmode(0);

						return;
					}
				}

				if (getPowerlinkmode() != 4)
				{
					setPowerlinkmode(getPowerlinkmode() + 1);
				}
				else
					setPowerlinkmode(0);
				break;
		}

		super.onNetworkHandlerEvent(key, value);
	}

	public List getFieldsforUpdate()
	{
		List NetworkedFields = new LinkedList();
		NetworkedFields.clear();

		NetworkedFields.addAll(super.getFieldsforUpdate());

		NetworkedFields.add("linketprojektor");
		NetworkedFields.add("capacity");
		NetworkedFields.add("TransmitRange");

		return NetworkedFields;
	}

	public int getfreeStorageAmount()
	{
		return getStorageMaxPower() - getStorageAvailablePower();
	}

	public boolean insertPowertoStorage(int powerAmount, boolean simulation)
	{
		if (simulation)
		{
			if (getStorageAvailablePower() + powerAmount <= getStorageMaxPower())
				return true;
			return false;
		}
		setForcePower(getStorageAvailablePower() + powerAmount);
		return true;
	}

	public boolean consumePowerfromStorage(int powerAmount, boolean simulation)
	{
		if (simulation)
		{
			if (getStorageAvailablePower() >= powerAmount)
				return true;
			return false;
		}
		if (getStorageAvailablePower() - powerAmount >= 0)
			setForcePower(getStorageAvailablePower() - powerAmount);
		else
			setForcePower(0);
		return true;
	}

	public boolean isItemValid(ItemStack par1ItemStack, int Slot)
	{
		switch (Slot)
		{
			case 0:
				if ((par1ItemStack.getItem() instanceof ItemCapacitorUpgradeCapacity))
					return true;
				break;
			case 1:
				if ((par1ItemStack.getItem() instanceof ItemCapacitorUpgradeRange))
					return true;
				break;
			case 2:
				if (((par1ItemStack.getItem() instanceof IForceEnergyItems)) || ((par1ItemStack.getItem() instanceof IPowerLinkItem)))
					return true;
				break;
			case 4:
				if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink))
					return true;
				break;
			case 3:
		}
		return false;
	}

	public int getSlotStackLimit(int Slot)
	{
		switch (Slot)
		{
			case 0:
				return 9;
			case 1:
				return 9;
			case 2:
				return 64;
		}
		return 1;
	}

	public short getmaxSwitchModi()
	{
		return 3;
	}

	public short getminSwitchModi()
	{
		return 1;
	}

	public ItemStack getPowerLinkStack()
	{
		return getStackInSlot(getPowerlinkSlot());
	}

	public int getPowerlinkSlot()
	{
		return 2;
	}
}