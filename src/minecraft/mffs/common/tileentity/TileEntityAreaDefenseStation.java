package mffs.common.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mffs.api.IPowerLinkItem;
import mffs.api.PointXYZ;
import mffs.common.InventoryHelper;
import mffs.common.Linkgrid;
import mffs.common.MFFSDamageSource;
import mffs.common.ModularForceFieldSystem;
import mffs.common.SecurityRight;
import mffs.common.container.ContainerAreaDefenseStation;
import mffs.common.item.ItemCardSecurityLink;
import mffs.common.item.ItemProjectorFieldModulatorDistance;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityAreaDefenseStation extends TileEntityFEPoweredMachine implements ISidedInventory
{
	private ItemStack[] Inventory;
	private int capacity;
	private int distance;
	private int contratyp;
	private int actionmode;
	private int scanmode;
	protected List warnlist = new ArrayList();
	protected List actionlist = new ArrayList();
	protected List NPClist = new ArrayList();
	private ArrayList ContraList = new ArrayList();

	public TileEntityAreaDefenseStation()
	{
		Random random = new Random();

		this.Inventory = new ItemStack[36];
		this.capacity = 0;
		this.contratyp = 1;
		this.actionmode = 0;
		this.scanmode = 1;
	}

	public int getScanmode()
	{
		return this.scanmode;
	}

	public void setScanmode(int scanmode)
	{
		this.scanmode = scanmode;
	}

	public int getActionmode()
	{
		return this.actionmode;
	}

	public void setActionmode(int actionmode)
	{
		this.actionmode = actionmode;
	}

	public int getcontratyp()
	{
		return this.contratyp;
	}

	public void setcontratyp(int a)
	{
		this.contratyp = a;
	}

	public int getCapacity()
	{
		return this.capacity;
	}

	public void setCapacity(int Capacity)
	{
		this.capacity = Capacity;
	}

	public int getActionDistance()
	{
		if ((getStackInSlot(3) != null) && (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance))
		{
			return getStackInSlot(3).stackSize;
		}

		return 0;
	}

	public int getInfoDistance()
	{
		if ((getStackInSlot(2) != null) && (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance))
		{
			return getActionDistance() + (getStackInSlot(2).stackSize + 3);
		}

		return getActionDistance() + 3;
	}

	public boolean hasSecurityCard()
	{
		if ((getStackInSlot(1) != null) && (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard))
		{
			return true;
		}

		return false;
	}

	public TileEntityAdvSecurityStation getLinkedSecurityStation()
	{
		return ItemCardSecurityLink.getLinkedSecurityStation(this, 1, this.worldObj);
	}

	public void invalidate()
	{
		Linkgrid.getWorldMap(this.worldObj).getDefStation().remove(Integer.valueOf(getDeviceID()));
		super.invalidate();
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		this.contratyp = nbttagcompound.getInteger("contratyp");
		this.actionmode = nbttagcompound.getInteger("actionmode");
		this.scanmode = nbttagcompound.getInteger("scanmode");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		this.Inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);

			byte byte0 = nbttagcompound1.getByte("Slot");
			if ((byte0 >= 0) && (byte0 < this.Inventory.length))
				this.Inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("contratyp", this.contratyp);
		nbttagcompound.setInteger("actionmode", this.actionmode);
		nbttagcompound.setInteger("scanmode", this.scanmode);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.Inventory.length; i++)
		{
			if (this.Inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.Inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void dropplugins()
	{
		for (int a = 0; a < this.Inventory.length; a++)
			dropplugins(a, this);
	}

	public void scanner()
	{
		try
		{
			TileEntityAdvSecurityStation sec = getLinkedSecurityStation();

			if (sec != null)
			{
				int xmininfo = this.xCoord - getInfoDistance();
				int xmaxinfo = this.xCoord + getInfoDistance() + 1;
				int ymininfo = this.yCoord - getInfoDistance();
				int ymaxinfo = this.yCoord + getInfoDistance() + 1;
				int zmininfo = this.zCoord - getInfoDistance();
				int zmaxinfo = this.zCoord + getInfoDistance() + 1;

				int xminaction = this.xCoord - getActionDistance();
				int xmaxaction = this.xCoord + getActionDistance() + 1;
				int yminaction = this.yCoord - getActionDistance();
				int ymaxaction = this.yCoord + getActionDistance() + 1;
				int zminaction = this.zCoord - getActionDistance();
				int zmaxaction = this.zCoord + getActionDistance() + 1;

				List<EntityLiving> infoLivinglist = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xmininfo, ymininfo, zmininfo, xmaxinfo, ymaxinfo, zmaxinfo));
				List<EntityLiving> actionLivinglist = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xminaction, yminaction, zminaction, xmaxaction, ymaxaction, zmaxaction));

				for (EntityLiving Living : infoLivinglist)
				{
					if ((Living instanceof EntityPlayer))
					{
						EntityPlayer player = (EntityPlayer) Living;
						int distance = (int) PointXYZ.distance(getMaschinePoint(), new PointXYZ((int) Living.posX, (int) Living.posY, (int) Living.posZ, this.worldObj));

						if ((distance <= getInfoDistance()) || (getScanmode() != 1))
						{
							if (!this.warnlist.contains(player))
							{
								this.warnlist.add(player);
								if (!sec.isAccessGranted(player.username, SecurityRight.SR))
								{
									if ((!ModularForceFieldSystem.DefenceStationNPCScannsuppressnotification) || (getActionmode() < 3))
									{
										player.addChatMessage("!!! [Security Station][" + sec.getDeviceName() + "] Warning you now in me Scanning range!!!");
										player.attackEntityFrom(MFFSDamageSource.areaDefense, 1);
									}
								}
							}
						}
					}
				}
				for (EntityLiving Living : actionLivinglist)
				{
					if ((Living instanceof EntityPlayer))
					{
						EntityPlayer player = (EntityPlayer) Living;

						int distance = (int) Math.round(PointXYZ.distance(getMaschinePoint(), new PointXYZ((int) Living.posX, (int) Living.posY, (int) Living.posZ, this.worldObj)));
						if ((distance <= getActionDistance()) || (getScanmode() != 1))
						{
							if (!this.actionlist.contains(player))
							{
								this.actionlist.add(player);
								DefenceAction(player);
							}
						}
					}
					else
					{
						int distance = (int) Math.round(PointXYZ.distance(getMaschinePoint(), new PointXYZ((int) Living.posX, (int) Living.posY, (int) Living.posZ, this.worldObj)));
						if ((distance <= getActionDistance()) || (getScanmode() != 1))
						{
							if (!this.NPClist.contains(Living))
							{
								this.NPClist.add(Living);
								DefenceAction(Living);
							}

						}

					}

				}

				for (int i = 0; i < this.actionlist.size(); i++)
				{
					if (!actionLivinglist.contains(this.actionlist.get(i)))
					{
						this.actionlist.remove(this.actionlist.get(i));
					}
				}

				for (int i = 0; i < this.warnlist.size(); i++)
				{
					if (!infoLivinglist.contains(this.warnlist.get(i)))
					{
						this.warnlist.remove(this.warnlist.get(i));
					}
				}

			}

		}
		catch (Exception ex)
		{
			System.err.println("[ModularForceFieldSystem] catch  Crash <TileEntityAreaDefenseStation:scanner> ");
		}
	}

	public void DefenceAction()
	{
		for (int i = 0; i < this.actionlist.size(); i++)
		{
			DefenceAction((EntityPlayer) this.actionlist.get(i));
		}
	}

	public boolean StacksToInventory(IInventory inventory, ItemStack itemstacks, boolean loop)
	{
		int count = 0;

		if ((inventory instanceof TileEntitySecStorage))
		{
			count = 1;
		}
		if ((inventory instanceof TileEntityAreaDefenseStation))
		{
			count = 15;
		}
		for (int a = count; a <= inventory.getSizeInventory() - 1; a++)
		{
			if (inventory.getStackInSlot(a) == null)
			{
				inventory.setInventorySlotContents(a, itemstacks);
				return true;
			}
			if ((inventory.getStackInSlot(a).getItem() == itemstacks.getItem()) && (inventory.getStackInSlot(a).getItemDamage() == itemstacks.getItemDamage()) && (inventory.getStackInSlot(a).stackSize < inventory.getStackInSlot(a).getMaxStackSize()))
			{
				int free = inventory.getStackInSlot(a).getMaxStackSize() - inventory.getStackInSlot(a).stackSize;

				if (free > itemstacks.stackSize)
				{
					inventory.getStackInSlot(a).stackSize += itemstacks.stackSize;
					return true;
				}
				inventory.getStackInSlot(a).stackSize = inventory.getStackInSlot(a).getMaxStackSize();
				itemstacks.stackSize -= free;
			}

		}

		if (loop)
		{
			addremoteInventory(itemstacks);
		}
		return false;
	}

	public void addremoteInventory(ItemStack itemstacks)
	{
		IInventory inv = InventoryHelper.findAttachedInventory(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		if (inv != null)
		{
			StacksToInventory(inv, itemstacks, false);
		}
	}

	public void DefenceAction(EntityLiving Living)
	{
		if ((Living instanceof EntityPlayer))
		{
			return;
		}
		TileEntityAdvSecurityStation sec = getLinkedSecurityStation();

		if (hasPowerSource())
		{
			if (sec != null)
			{
				if (consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, true))
					;
				switch (getActionmode())
				{
					case 3:
						consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false);
						Living.setEntityHealth(0);
						this.NPClist.remove(Living);
						break;
					case 4:
						if (((Living instanceof EntityMob)) || ((Living instanceof EntityAmbientCreature)) || ((Living instanceof EntitySlime)) || ((Living instanceof EntityGhast)))
						{
							Living.setEntityHealth(0);
							this.NPClist.remove(Living);
							consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false);
						}
						break;
					case 5:
						if ((!(Living instanceof EntityMob)) && (!(Living instanceof EntitySlime)) && (!(Living instanceof EntityGhast)))
						{
							Living.setEntityHealth(0);
							this.NPClist.remove(Living);
							consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false);
						}
						break;
				}
			}
		}
	}

	public void DefenceAction(EntityPlayer player)
	{
		TileEntityAdvSecurityStation sec = getLinkedSecurityStation();

		if (hasPowerSource())
		{
			if (sec != null)
			{
				switch (getActionmode())
				{
					case 0:
						if (!sec.isAccessGranted(player.username, SecurityRight.SR))
						{
							player.addChatMessage("!!! [Area Defence]  get out immediately you have no right to be here!!!");
						}
						break;
					case 1:
						if (consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, true))
						{
							if (!sec.isAccessGranted(player.username, SecurityRight.SR))
							{
								player.addChatMessage("!!! [Area Defence] you have been warned BYE BYE!!!");

								for (int i = 0; i < 4; i++)
								{
									if (player.inventory.armorInventory[i] != null)
									{
										StacksToInventory(this, player.inventory.armorInventory[i], true);
										player.inventory.armorInventory[i] = null;
									}
								}

								for (int i = 0; i < 36; i++)
								{
									if (player.inventory.mainInventory[i] != null)
									{
										StacksToInventory(this, player.inventory.mainInventory[i], true);
										player.inventory.mainInventory[i] = null;
									}
								}

								this.actionlist.remove(player);
								player.setEntityHealth(0);
								player.attackEntityFrom(MFFSDamageSource.areaDefense, 20);
								consumePower(ModularForceFieldSystem.DefenceStationKillForceEnergy, false);
							}
						}
						break;
					case 2:
						if (consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, true))
						{
							if (!sec.isAccessGranted(player.username, SecurityRight.AAI))
							{
								this.ContraList.clear();

								for (int place = 5; place < 15; place++)
								{
									if (getStackInSlot(place) != null)
									{
										this.ContraList.add(getStackInSlot(place).getItem());
									}

								}

								switch (getcontratyp())
								{
									case 0:
										for (int i = 0; i < 4; i++)
										{
											if (player.inventory.armorInventory[i] != null)
											{
												if (!this.ContraList.contains(player.inventory.armorInventory[i].getItem()))
												{
													player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.armorInventory[i].getItem().getItemDisplayName(player.inventory.armorInventory[i]) + "> will be confiscated!!!");
													StacksToInventory(this, player.inventory.armorInventory[i], true);
													player.inventory.armorInventory[i] = null;
													consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
												}
											}

										}

										for (int i = 0; i < 36; i++)
										{
											if (player.inventory.mainInventory[i] != null)
											{
												if (!this.ContraList.contains(player.inventory.mainInventory[i].getItem()))
												{
													player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.mainInventory[i].getItem().getItemDisplayName(player.inventory.mainInventory[i]) + "> will be confiscated!!!");
													StacksToInventory(this, player.inventory.mainInventory[i], true);
													player.inventory.mainInventory[i] = null;
													consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
												}
											}

										}

										break;
									case 1:
										for (int i = 0; i < 4; i++)
										{
											if (player.inventory.armorInventory[i] != null)
											{
												if (this.ContraList.contains(player.inventory.armorInventory[i].getItem()))
												{
													player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.armorInventory[i].getItem().getItemDisplayName(player.inventory.armorInventory[i]) + "> will be confiscated!!!");
													StacksToInventory(this, player.inventory.armorInventory[i], true);
													player.inventory.armorInventory[i] = null;
													consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
												}
											}

										}

										for (int i = 0; i < 36; i++)
											if (player.inventory.mainInventory[i] != null)
											{
												if (this.ContraList.contains(player.inventory.mainInventory[i].getItem()))
												{
													player.addChatMessage("!!! [Area Defence] You  have illegal goods <" + player.inventory.mainInventory[i].getItem().getItemDisplayName(player.inventory.mainInventory[i]) + "> will be confiscated!!!");
													StacksToInventory(this, player.inventory.mainInventory[i], true);
													player.inventory.mainInventory[i] = null;
													consumePower(ModularForceFieldSystem.DefenceStationSearchForceEnergy, false);
												}
											}
								}
							}
						}
						break;
				}
			}
		}
	}

	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			if ((getSwitchModi() == 1) && (!getSwitchValue()) && (isRedstoneSignal()))
			{
				toggelSwitchValue();
			}
			if ((getSwitchModi() == 1) && (getSwitchValue()) && (!isRedstoneSignal()))
			{
				toggelSwitchValue();
			}
			if ((getSwitchValue()) && (hasPowerSource()) && (getAvailablePower() > 0) && (getLinkedSecurityStation() != null) && (!isActive()))
			{
				setActive(true);
			}
			if (((!getSwitchValue()) || (!hasPowerSource()) || (getAvailablePower() < ModularForceFieldSystem.DefenceStationScannForceEnergy * getInfoDistance()) || (getLinkedSecurityStation() == null)) && (isActive()))
			{
				setActive(false);
			}
			if (isActive())
			{
				if (consumePower(ModularForceFieldSystem.DefenceStationScannForceEnergy * getInfoDistance(), true))
				{
					consumePower(ModularForceFieldSystem.DefenceStationScannForceEnergy * getInfoDistance(), false);
					scanner();
				}

			}

			if (getTicker() == 100)
			{
				if (isActive())
				{
					DefenceAction();
				}
				setTicker((short) 0);
			}
			setTicker((short) (getTicker() + 1));
		}
		super.updateEntity();
	}

	public ItemStack decrStackSize(int i, int j)
	{
		if (this.Inventory[i] != null)
		{
			if (this.Inventory[i].stackSize <= j)
			{
				ItemStack itemstack = this.Inventory[i];
				this.Inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = this.Inventory[i].splitStack(j);
			if (this.Inventory[i].stackSize == 0)
			{
				this.Inventory[i] = null;
			}
			return itemstack1;
		}
		return null;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.Inventory[i] = itemstack;
		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
			itemstack.stackSize = getInventoryStackLimit();
	}

	public ItemStack getStackInSlot(int i)
	{
		return this.Inventory[i];
	}

	public String getInvName()
	{
		return "Defstation";
	}

	public int getSizeInventory()
	{
		return this.Inventory.length;
	}

	public Container getContainer(InventoryPlayer inventoryplayer)
	{
		return new ContainerAreaDefenseStation(inventoryplayer.player, this);
	}

	public int getStartInventorySide(ForgeDirection side)
	{
		return 15;
	}

	public int getSizeInventorySide(ForgeDirection side)
	{
		return 20;
	}

	public void onNetworkHandlerEvent(int key, String value)
	{
		if (!isActive())
		{
			switch (key)
			{
				case 100:
					if (getcontratyp() == 0)
						setcontratyp(1);
					else
					{
						setcontratyp(0);
					}
					break;
				case 101:
					if (getActionmode() == 5)
					{
						setActionmode(0);
					}
					else
						setActionmode(getActionmode() + 1);

					break;
				case 102:
					if (getScanmode() == 0)
						setScanmode(1);
					else
					{
						setScanmode(0);
					}
					break;
			}
		}

		super.onNetworkHandlerEvent(key, value);
	}

	public short getmaxSwitchModi()
	{
		return 3;
	}

	public short getminSwitchModi()
	{
		return 1;
	}

	public boolean isItemValid(ItemStack par1ItemStack, int Slot)
	{
		switch (Slot)
		{
			case 0:
				if ((par1ItemStack.getItem() instanceof IPowerLinkItem))
					return true;
				break;
			case 1:
				if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink))
					return true;
				break;
			case 2:
			case 3:
				if ((par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorDistance))
				{
					return true;
				}
				break;
		}
		if ((Slot >= 5) && (Slot <= 14))
		{
			return true;
		}

		return false;
	}

	public int getSlotStackLimit(int Slot)
	{
		switch (Slot)
		{
			case 0:
			case 1:
				return 1;
			case 2:
			case 3:
				return 64;
		}

		if ((Slot >= 5) && (Slot <= 14))
		{
			return 1;
		}
		if ((Slot >= 5) && (Slot <= 14))
		{
			return 1;
		}
		return 64;
	}

	public ItemStack getPowerLinkStack()
	{
		return getStackInSlot(getPowerlinkSlot());
	}

	public int getPowerlinkSlot()
	{
		return 0;
	}
}