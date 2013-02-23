package chb.mods.mffs.common.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import chb.mods.mffs.api.IPowerLinkItem;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.ForceFieldBlockStack;
import chb.mods.mffs.common.ForceFieldTyps;
import chb.mods.mffs.common.Functions;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.InventoryHelper;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.ProjectorTyp;
import chb.mods.mffs.common.WorldMap;
import chb.mods.mffs.common.container.ContainerProjector;
import chb.mods.mffs.common.item.ItemCardSecurityLink;
import chb.mods.mffs.common.item.ItemProjectorFieldModulatorDistance;
import chb.mods.mffs.common.item.ItemProjectorFieldModulatorStrength;
import chb.mods.mffs.common.item.ItemProjectorFocusMatrix;
import chb.mods.mffs.common.modules.Module3DBase;
import chb.mods.mffs.common.modules.ModuleBase;
import chb.mods.mffs.common.options.IChecksOnAll;
import chb.mods.mffs.common.options.IInteriorCheck;
import chb.mods.mffs.common.options.ItemProjectorOptionBase;
import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
import chb.mods.mffs.network.server.NetworkHandlerServer;

public class TileEntityProjector extends TileEntityFEPoweredMachine implements IModularProjector
{
	private ItemStack[] ProjektorItemStacks;
	private int[] focusmatrix = { 0, 0, 0, 0 };
	private String ForceFieldTexturids = "-76/-76/-76/-76/-76/-76";
	private String ForceFieldTexturfile = "/terrain.png";
	private int ForcefieldCamoblockid;
	private int ForcefieldCamoblockmeta;
	private int switchdelay;
	private short forcefieldblock_meta;
	private int ProjektorTyp;
	private int linkPower;
	private int blockcounter;
	private boolean burnout;
	private int accesstyp;
	private int capacity;
	protected Stack field_queue = new Stack();
	protected Set field_interior = new HashSet();
	protected Set<PointXYZ> field_def = new HashSet();

	public TileEntityProjector()
	{
		Random random = new Random();

		this.ProjektorItemStacks = new ItemStack[13];
		this.linkPower = 0;
		this.forcefieldblock_meta = ((short) ForceFieldTyps.Default.ordinal());
		this.ProjektorTyp = 0;
		this.switchdelay = 0;
		this.burnout = false;
		this.accesstyp = 0;
		this.capacity = 0;
	}

	public int getCapacity()
	{
		return this.capacity;
	}

	public void setCapacity(int Capacity)
	{
		this.capacity = Capacity;
	}

	public int getaccesstyp()
	{
		return this.accesstyp;
	}

	public void setaccesstyp(int accesstyp)
	{
		this.accesstyp = accesstyp;
	}

	public int getForcefieldCamoblockmeta()
	{
		return this.ForcefieldCamoblockmeta;
	}

	public void setForcefieldCamoblockmeta(int forcefieldCamoblockmeta)
	{
		this.ForcefieldCamoblockmeta = forcefieldCamoblockmeta;
		NetworkHandlerServer.updateTileEntityField(this, "ForcefieldCamoblockmeta");
	}

	public int getForcefieldCamoblockid()
	{
		return this.ForcefieldCamoblockid;
	}

	public void setForcefieldCamoblockid(int forcefieldCamoblockid)
	{
		this.ForcefieldCamoblockid = forcefieldCamoblockid;
		NetworkHandlerServer.updateTileEntityField(this, "ForcefieldCamoblockid");
	}

	public String getForceFieldTexturfile()
	{
		return this.ForceFieldTexturfile;
	}

	public void setForceFieldTexturfile(String forceFieldTexturfile)
	{
		this.ForceFieldTexturfile = forceFieldTexturfile;
		NetworkHandlerServer.updateTileEntityField(this, "ForceFieldTexturfile");
	}

	public String getForceFieldTexturID()
	{
		return this.ForceFieldTexturids;
	}

	public void setForceFieldTexturID(String forceFieldTexturids)
	{
		this.ForceFieldTexturids = forceFieldTexturids;
		NetworkHandlerServer.updateTileEntityField(this, "ForceFieldTexturids");
	}

	public int getProjektor_Typ()
	{
		return this.ProjektorTyp;
	}

	public void setProjektor_Typ(int ProjektorTyp)
	{
		this.ProjektorTyp = ProjektorTyp;

		NetworkHandlerServer.updateTileEntityField(this, "ProjektorTyp");
	}

	public int getBlockcounter()
	{
		return this.blockcounter;
	}

	public int getforcefieldblock_meta()
	{
		return this.forcefieldblock_meta;
	}

	public void setforcefieldblock_meta(int ffmeta)
	{
		this.forcefieldblock_meta = ((short) ffmeta);
	}

	public int getLinkPower()
	{
		return this.linkPower;
	}

	public void setLinkPower(int linkPower)
	{
		this.linkPower = linkPower;
	}

	public void ProjektorBurnout()
	{
		setBurnedOut(true);
		dropplugins();
	}

	public boolean isBurnout()
	{
		return this.burnout;
	}

	public void setBurnedOut(boolean b)
	{
		this.burnout = b;
		NetworkHandlerServer.updateTileEntityField(this, "burnout");
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		this.accesstyp = nbttagcompound.getInteger("accesstyp");
		this.burnout = nbttagcompound.getBoolean("burnout");
		this.ProjektorTyp = nbttagcompound.getInteger("Projektor_Typ");
		this.forcefieldblock_meta = nbttagcompound.getShort("forcefieldblockmeta");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		this.ProjektorItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);

			byte byte0 = nbttagcompound1.getByte("Slot");
			if ((byte0 >= 0) && (byte0 < this.ProjektorItemStacks.length))
				this.ProjektorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("accesstyp", this.accesstyp);
		nbttagcompound.setBoolean("burnout", this.burnout);
		nbttagcompound.setInteger("Projektor_Typ", this.ProjektorTyp);
		nbttagcompound.setShort("forcefieldblockmeta", this.forcefieldblock_meta);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.ProjektorItemStacks.length; i++)
		{
			if (this.ProjektorItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.ProjektorItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void dropplugins()
	{
		for (int a = 0; a < this.ProjektorItemStacks.length; a++)
			dropplugins(a, this);
	}

	public void onInventoryChanged()
	{
		getLinkedSecurityStation();
		checkslots();
	}

	public void checkslots()
	{
		if (hasValidTypeMod())
		{
			if (getProjektor_Typ() != ProjectorTyp.TypfromItem(get_type()).ProTyp)
			{
				setProjektor_Typ(ProjectorTyp.TypfromItem(get_type()).ProTyp);
			}
			if (getforcefieldblock_meta() != get_type().getForceFieldTyps().ordinal())
			{
				setforcefieldblock_meta(get_type().getForceFieldTyps().ordinal());
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		else
		{
			if (getProjektor_Typ() != 0)
				setProjektor_Typ(0);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}

		if (hasValidTypeMod())
		{
			for (int place = 7; place < 11; place++)
			{
				if (getStackInSlot(place) != null)
				{
					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSitemFocusmatix)
						switch (ProjectorTyp.TypfromItem(get_type()).ProTyp)
						{
							case 6:
								this.focusmatrix[(place - 7)] = (getStackInSlot(place).stackSize + 1);
								break;
							case 7:
								this.focusmatrix[(place - 7)] = (getStackInSlot(place).stackSize + 2);
								break;
							default:
								this.focusmatrix[(place - 7)] = getStackInSlot(place).stackSize;
								break;
						}
					else
						dropplugins(place, this);
				}
				else
				{
					switch (ProjectorTyp.TypfromItem(get_type()).ProTyp)
					{
						case 6:
							this.focusmatrix[(place - 7)] = 1;
							break;
						case 7:
							this.focusmatrix[(place - 7)] = 2;
							break;
						default:
							this.focusmatrix[(place - 7)] = 0;
					}
				}

			}

		}

		if (getStackInSlot(11) != null)
		{
			if (getStackInSlot(11).itemID < 4095)
			{
				String ForcefieldTexturtemp = "180/180/180/180/180/180";
				String Texturfile = "/terrain.png";

				int[] index = new int[6];
				for (int side = 0; side < 6; side++)
				{
					index[side] = Block.blocksList[getStackInSlot(11).itemID].getBlockTextureFromSideAndMetadata(side, getStackInSlot(11).getItemDamage());
				}
				ForcefieldTexturtemp = index[0] + "/" + index[1] + "/" + index[2] + "/" + index[3] + "/" + index[4] + "/" + index[5];
				Texturfile = Block.blocksList[getStackInSlot(11).itemID].getTextureFile();

				if ((!ForcefieldTexturtemp.equalsIgnoreCase(this.ForceFieldTexturids)) || (!this.ForceFieldTexturfile.equalsIgnoreCase(getForceFieldTexturfile())))
				{
					if (getStackInSlot(11).getItem() == Item.bucketLava)
					{
						setForceFieldTexturID("237/237/239/254/255/255");
					}
					if (getStackInSlot(11).getItem() == Item.bucketWater)
					{
						setForceFieldTexturID("205/205/207/222/223/223");
					}
					if ((getStackInSlot(11).getItem() != Item.bucketLava) && (getStackInSlot(11).getItem() != Item.bucketWater))
					{
						setForceFieldTexturID(ForcefieldTexturtemp);
					}
					setForcefieldCamoblockmeta(getStackInSlot(11).getItemDamage());
					setForcefieldCamoblockid(getStackInSlot(11).itemID);
					setForceFieldTexturfile(Texturfile);
					UpdateForcefieldTexttur();
				}

			}
			else
			{
				dropplugins(11, this);
			}

		}
		else if ((!this.ForceFieldTexturids.equalsIgnoreCase("-76/-76/-76/-76/-76/-76")) || (getForcefieldCamoblockid() != -1))
		{
			setForcefieldCamoblockmeta(0);
			setForcefieldCamoblockid(-1);
			setForceFieldTexturID("-76/-76/-76/-76/-76/-76");
			setForceFieldTexturfile("/terrain.png");
			UpdateForcefieldTexttur();
		}

		if ((hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true)) && (getforcefieldblock_meta() != ForceFieldTyps.Camouflage.ordinal()))
		{
			setforcefieldblock_meta((short) ForceFieldTyps.Camouflage.ordinal());
		}

		if ((hasOption(ModularForceFieldSystem.MFFSProjectorOptionZapper, true)) && (getforcefieldblock_meta() != ForceFieldTyps.Zapper.ordinal()))
		{
			setforcefieldblock_meta((short) ForceFieldTyps.Zapper.ordinal());
		}

		if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion, true))
		{
			if (!Linkgrid.getWorldMap(this.worldObj).getFieldFusion().containsKey(Integer.valueOf(getDeviceID())))
				Linkgrid.getWorldMap(this.worldObj).getFieldFusion().put(Integer.valueOf(getDeviceID()), this);
		}
		else if (Linkgrid.getWorldMap(this.worldObj).getFieldFusion().containsKey(Integer.valueOf(getDeviceID())))
		{
			Linkgrid.getWorldMap(this.worldObj).getFieldFusion().remove(Integer.valueOf(getDeviceID()));
		}

		if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer, false))
		{
			if (!Linkgrid.getWorldMap(this.worldObj).getJammer().containsKey(Integer.valueOf(getDeviceID())))
				Linkgrid.getWorldMap(this.worldObj).getJammer().put(Integer.valueOf(getDeviceID()), this);
		}
		else if (Linkgrid.getWorldMap(this.worldObj).getJammer().containsKey(Integer.valueOf(getDeviceID())))
		{
			Linkgrid.getWorldMap(this.worldObj).getJammer().remove(Integer.valueOf(getDeviceID()));
		}

		if (hasValidTypeMod())
		{
			ModuleBase modTyp = get_type();

			if (!modTyp.supportsStrength())
				dropplugins(6, this);
			if (!modTyp.supportsDistance())
			{
				dropplugins(5, this);
			}
			if (!modTyp.supportsMatrix())
			{
				dropplugins(7, this);
				dropplugins(8, this);
				dropplugins(9, this);
				dropplugins(10, this);
			}

			for (int spot = 2; spot <= 4; spot++)
			{
				if ((getStackInSlot(spot) != null) && (!modTyp.supportsOption(getStackInSlot(spot).getItem())))
				{
					dropplugins(spot, this);
				}

				if ((getStackInSlot(spot) != null) && ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionForceFieldJammer)) && (isPowersourceItem()))
				{
					dropplugins(spot, this);
				}

				if ((getStackInSlot(spot) != null) && ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionFieldFusion)) && (isPowersourceItem()))
				{
					dropplugins(spot, this);
				}

				if ((getStackInSlot(spot) != null) && ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionDefenseStation)) && (isPowersourceItem()))
				{
					dropplugins(spot, this);
				}

			}

			if ((getStackInSlot(12) != null) && ((getStackInSlot(12).getItem() instanceof ItemCardSecurityLink)) && (isPowersourceItem()))
			{
				dropplugins(12, this);
			}

			if (!hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true))
				dropplugins(11, this);
		}
		else
		{
			for (int spot = 2; spot <= 10; spot++)
				dropplugins(spot, this);
		}
	}

	private void UpdateForcefieldTexttur()
	{
		if ((isActive()) && (hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true)))
		{
			for (PointXYZ png : this.field_def)
			{
				if (this.worldObj.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded)
				{
					TileEntity tileEntity = this.worldObj.getBlockTileEntity(png.X, png.Y, png.Z);

					if ((tileEntity != null) && ((tileEntity instanceof TileEntityForceField)))
					{
						((TileEntityForceField) tileEntity).UpdateTextur();
					}
				}
			}
		}
	}

	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			if (this.init)
			{
				checkslots();
				if (isActive())
				{
					calculateField(true);
				}
			}

			if (hasPowerSource())
			{
				setLinkPower(getAvailablePower());

				if ((isPowersourceItem()) && (getaccesstyp() != 0))
					setaccesstyp(0);
			}
			else
			{
				setLinkPower(0);
			}

			if ((getSwitchModi() == 1) && (!getSwitchValue()) && (isRedstoneSignal()))
			{
				toggelSwitchValue();
			}
			if ((getSwitchModi() == 1) && (getSwitchValue()) && (!isRedstoneSignal()))
			{
				toggelSwitchValue();
			}

			if ((getSwitchValue()) && (this.switchdelay >= 40) && (hasValidTypeMod()) && (hasPowerSource()) && (getLinkPower() > Forcepowerneed(5)))
			{
				if (isActive() != true)
				{
					setActive(true);
					this.switchdelay = 0;
					if (calculateField(true))
					{
						FieldGenerate(true);
					}
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
			}
			if (((!getSwitchValue()) && (this.switchdelay >= 40)) || (!hasValidTypeMod()) || (!hasPowerSource()) || (this.burnout) || (getLinkPower() <= Forcepowerneed(1)))
			{
				if (isActive())
				{
					setActive(false);
					this.switchdelay = 0;
					destroyField();
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
			}

			if (getTicker() == 20)
			{
				if (isActive())
				{
					FieldGenerate(false);

					if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionMoobEx, true))
					{
						ItemProjectorOptionMobDefence.ProjectorNPCDefence(this, this.worldObj);
					}

					if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionDefenceStation, true))
					{
						ItemProjectorOptionDefenseStation.ProjectorPlayerDefence(this, this.worldObj);
					}

				}

				setTicker((short) 0);
			}

			setTicker((short) (getTicker() + 1));
		}
		this.switchdelay += 1;
		super.updateEntity();
	}

	private boolean calculateField(boolean addtoMap)
	{
		this.field_def.clear();
		this.field_interior.clear();
		if (hasValidTypeMod())
		{
			Set<PointXYZ> tField = new HashSet();
			Set<PointXYZ> tFieldInt = new HashSet();

			if ((get_type() instanceof Module3DBase))
				((Module3DBase) get_type()).calculateField(this, tField, tFieldInt);
			else
			{
				get_type().calculateField(this, tField);
			}

			for (PointXYZ pnt : tField)
			{
				if (pnt.Y + this.yCoord < 255)
				{
					PointXYZ tp = new PointXYZ(pnt.X + this.xCoord, pnt.Y + this.yCoord, pnt.Z + this.zCoord, this.worldObj);

					if (Forcefielddefine(tp, addtoMap))
					{
						this.field_def.add(tp);
					}
					else
						return false;
				}
			}
			for (PointXYZ pnt : tFieldInt)
			{
				if (pnt.Y + this.yCoord < 255)
				{
					PointXYZ tp = new PointXYZ(pnt.X + this.xCoord, pnt.Y + this.yCoord, pnt.Z + this.zCoord, this.worldObj);

					if (calculateBlock(tp))
						this.field_interior.add(tp);
					else
						return false;
				}

			}

			return true;
		}
		return false;
	}

	public boolean calculateBlock(PointXYZ pnt)
	{
		for (ItemProjectorOptionBase opt : getOptions(true))
		{
			if ((opt instanceof IInteriorCheck))
			{
				((IInteriorCheck) opt).checkInteriorBlock(pnt, this.worldObj, this);
			}
		}
		return true;
	}

	public boolean Forcefielddefine(PointXYZ png, boolean addtoMap)
	{
		for (ItemProjectorOptionBase opt : getOptions(true))
		{
			if (((opt instanceof ItemProjectorOptionForceFieldJammer)) && (((ItemProjectorOptionForceFieldJammer) opt).CheckJammerinfluence(png, this.worldObj, this)))
			{
				return false;
			}

			if (((opt instanceof ItemProjectorOptionFieldFusion)) && (((ItemProjectorOptionFieldFusion) opt).checkFieldFusioninfluence(png, this.worldObj, this)))
			{
				return true;
			}

		}

		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(this.worldObj).getorcreateFFStackMap(png.X, png.Y, png.Z, this.worldObj);

		if (!ffworldmap.isEmpty())
		{
			if (ffworldmap.getProjectorID() != getDeviceID())
			{
				ffworldmap.removebyProjector(getDeviceID());
				ffworldmap.add(getPowerSourceID(), getDeviceID(), getforcefieldblock_meta());
			}
		}
		else
		{
			ffworldmap.add(getPowerSourceID(), getDeviceID(), getforcefieldblock_meta());
			ffworldmap.setSync(false);
		}

		this.field_queue.push(Integer.valueOf(png.hashCode()));

		return true;
	}

	public void FieldGenerate(boolean init)
	{
		int cost = 0;

		if (init)
		{
			cost = ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier;
		}
		else
		{
			cost = ModularForceFieldSystem.forcefieldblockcost;
		}

		if (getforcefieldblock_meta() == 1)
		{
			cost *= ModularForceFieldSystem.forcefieldblockzappermodifier;
		}

		consumePower(cost * this.field_def.size(), false);

		this.blockcounter = 0;

		for (PointXYZ pnt : this.field_def)
		{
			if (this.blockcounter == ModularForceFieldSystem.forcefieldmaxblockpeerTick)
			{
				break;
			}
			ForceFieldBlockStack ffb = WorldMap.getForceFieldWorld(this.worldObj).getForceFieldStackMap(Integer.valueOf(pnt.hashCode()));

			if (ffb != null)
			{
				if (!ffb.isSync())
				{
					PointXYZ png = ffb.getPoint();

					if ((this.worldObj.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded) && (!ffb.isEmpty()) && (ffb.getProjectorID() == getDeviceID()))
					{
						if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionCutter, true))
						{
							int blockid = this.worldObj.getBlockId(png.X, png.Y, png.Z);
							TileEntity entity = this.worldObj.getBlockTileEntity(png.X, png.Y, png.Z);

							if ((blockid != ModularForceFieldSystem.MFFSFieldblock.blockID) && (blockid != 0) && (blockid != Block.bedrock.blockID) && (entity == null))
							{
								ArrayList stacks = Functions.getItemStackFromBlock(this.worldObj, png.X, png.Y, png.Z);

								this.worldObj.setBlockWithNotify(png.X, png.Y, png.Z, 0);

								if ((ProjectorTyp.TypfromItem(get_type()).Blockdropper) && (stacks != null))
								{
									IInventory inventory = InventoryHelper.findAttachedInventory(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
									if ((inventory != null) && (inventory.getSizeInventory() > 0))
									{
										InventoryHelper.addStacksToInventory(inventory, stacks);
									}
								}
							}

						}

						if ((this.worldObj.getBlockMaterial(png.X, png.Y, png.Z).isLiquid()) || (this.worldObj.isAirBlock(png.X, png.Y, png.Z)) || (this.worldObj.getBlockId(png.X, png.Y, png.Z) == ModularForceFieldSystem.MFFSFieldblock.blockID))
						{
							if (this.worldObj.getBlockId(png.X, png.Y, png.Z) != ModularForceFieldSystem.MFFSFieldblock.blockID)
							{
								this.worldObj.setBlockAndMetadataWithNotify(png.X, png.Y, png.Z, ModularForceFieldSystem.MFFSFieldblock.blockID, ffb.getTyp());

								this.blockcounter += 1;
							}
							ffb.setSync(true);
						}
					}
				}
			}
		}
	}

	public void destroyField()
	{
		while (!this.field_queue.isEmpty())
		{
			ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(this.worldObj).getForceFieldStackMap((Integer) this.field_queue.pop());

			if (!ffworldmap.isEmpty())
			{
				if (ffworldmap.getProjectorID() == getDeviceID())
				{
					ffworldmap.removebyProjector(getDeviceID());

					if (ffworldmap.isSync())
					{
						PointXYZ png = ffworldmap.getPoint();
						this.worldObj.removeBlockTileEntity(png.X, png.Y, png.Z);
						this.worldObj.setBlockWithNotify(png.X, png.Y, png.Z, 0);
					}

					ffworldmap.setSync(false);
				}
				else
				{
					ffworldmap.removebyProjector(getDeviceID());
				}
			}
		}

		Map<Integer, TileEntityProjector> FieldFusion = Linkgrid.getWorldMap(this.worldObj).getFieldFusion();
		for (TileEntityProjector tileentity : FieldFusion.values())
		{
			if (tileentity.getPowerSourceID() == getPowerSourceID())
			{
				if (tileentity.isActive())
				{
					tileentity.calculateField(false);
				}
			}
		}
	}

	public void invalidate()
	{
		Linkgrid.getWorldMap(this.worldObj).getProjektor().remove(Integer.valueOf(getDeviceID()));
		destroyField();
		super.invalidate();
	}

	public int Forcepowerneed(int factor)
	{
		if (!this.field_def.isEmpty())
		{
			return this.field_def.size() * ModularForceFieldSystem.forcefieldblockcost;
		}

		int forcepower = 0;
		int blocks = 0;

		int tmplength = 1;

		if (countItemsInSlot(IModularProjector.Slots.Strength) != 0)
		{
			tmplength = countItemsInSlot(IModularProjector.Slots.Strength);
		}

		switch (getProjektor_Typ())
		{
			case 1:
				blocks = (countItemsInSlot(IModularProjector.Slots.FocusDown) + countItemsInSlot(IModularProjector.Slots.FocusLeft) + countItemsInSlot(IModularProjector.Slots.FocusRight) + countItemsInSlot(IModularProjector.Slots.FocusUp) + 1) * tmplength;

				break;
			case 2:
				blocks = (countItemsInSlot(IModularProjector.Slots.FocusDown) + countItemsInSlot(IModularProjector.Slots.FocusUp) + 1) * (countItemsInSlot(IModularProjector.Slots.FocusLeft) + countItemsInSlot(IModularProjector.Slots.FocusRight) + 1);

				break;
			case 3:
				blocks = ((countItemsInSlot(IModularProjector.Slots.Distance) + 2 + countItemsInSlot(IModularProjector.Slots.Distance) + 2) * 4 + 4) * (countItemsInSlot(IModularProjector.Slots.Strength) + 1);

				break;
			case 4:
			case 5:
				blocks = countItemsInSlot(IModularProjector.Slots.Distance) * countItemsInSlot(IModularProjector.Slots.Distance) * 6;
		}

		forcepower = blocks * ModularForceFieldSystem.forcefieldblockcost;
		if (factor != 1)
		{
			forcepower = forcepower * ModularForceFieldSystem.forcefieldblockcreatemodifier + forcepower * 5;
		}

		return forcepower;
	}

	public ItemStack decrStackSize(int i, int j)
	{
		if (this.ProjektorItemStacks[i] != null)
		{
			if (this.ProjektorItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = this.ProjektorItemStacks[i];
				this.ProjektorItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = this.ProjektorItemStacks[i].splitStack(j);
			if (this.ProjektorItemStacks[i].stackSize == 0)
			{
				this.ProjektorItemStacks[i] = null;
			}
			return itemstack1;
		}
		return null;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.ProjektorItemStacks[i] = itemstack;
		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
			itemstack.stackSize = getInventoryStackLimit();
	}

	public ItemStack getStackInSlot(int i)
	{
		return this.ProjektorItemStacks[i];
	}

	public String getInvName()
	{
		return "Projektor";
	}

	public int getSizeInventory()
	{
		return this.ProjektorItemStacks.length;
	}

	public Container getContainer(InventoryPlayer inventoryplayer)
	{
		return new ContainerProjector(inventoryplayer.player, this);
	}

	public int getStartInventorySide(ForgeDirection side)
	{
		return 11;
	}

	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}

	public void onNetworkHandlerEvent(int key, String value)
	{
		if (!isActive())
		{
			switch (key)
			{
				case 1:
					if (!isPowersourceItem())
					{
						if (getaccesstyp() != 3)
						{
							if (getaccesstyp() == 2)
							{
								setaccesstyp(0);
							}
							else
								setaccesstyp(getaccesstyp() + 1);
						}
					}
					break;
			}

		}

		super.onNetworkHandlerEvent(key, value);
	}

	public List getFieldsforUpdate()
	{
		List NetworkedFields = new LinkedList();
		NetworkedFields.clear();

		NetworkedFields.addAll(super.getFieldsforUpdate());

		NetworkedFields.add("ProjektorTyp");
		NetworkedFields.add("burnout");
		NetworkedFields.add("camoflage");
		NetworkedFields.add("ForceFieldTexturfile");
		NetworkedFields.add("ForceFieldTexturids");
		NetworkedFields.add("ForcefieldCamoblockid");
		NetworkedFields.add("ForcefieldCamoblockmeta");

		return NetworkedFields;
	}

	public boolean isItemValid(ItemStack par1ItemStack, int Slot)
	{
		if ((Slot == 1) && ((par1ItemStack.getItem() instanceof ModuleBase)))
			return true;
		if ((Slot == 0) && ((par1ItemStack.getItem() instanceof IPowerLinkItem)))
			return true;
		if ((Slot == 11) && (par1ItemStack.itemID < 4096) && (hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true)))
			return true;

		if (hasValidTypeMod())
		{
			ModuleBase modTyp = get_type();

			switch (Slot)
			{
				case 12:
					if (((par1ItemStack.getItem() instanceof ItemProjectorOptionDefenseStation)) && (isPowersourceItem()))
					{
						return false;
					}
					if (((par1ItemStack.getItem() instanceof ItemCardSecurityLink)) && (isPowersourceItem()))
					{
						return false;
					}
					if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink))
					{
						return true;
					}

					break;
				case 5:
					if ((par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorDistance))
						return modTyp.supportsDistance();
					break;
				case 6:
					if ((par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorStrength))
						return modTyp.supportsStrength();

					break;
				case 7:
				case 8:
				case 9:
				case 10:
					if ((par1ItemStack.getItem() instanceof ItemProjectorFocusMatrix))
						return modTyp.supportsMatrix();

					break;
				case 2:
				case 3:
				case 4:
					if (isActive())
						return false;

					if ((par1ItemStack.getItem() instanceof ItemProjectorOptionTouchDamage))
					{
						for (int spot = 2; spot <= 4; spot++)
						{
							if ((getStackInSlot(spot) != null) && ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionCamoflage)))
							{
								return false;
							}
						}
					}

					if ((par1ItemStack.getItem() instanceof ItemProjectorOptionCamoflage))
					{
						for (int spot = 2; spot <= 4; spot++)
						{
							if ((getStackInSlot(spot) != null) && ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionTouchDamage)))
							{
								return false;
							}
						}

					}

					if (!hasPowerSource())
					{
						return false;
					}
					if (((par1ItemStack.getItem() instanceof ItemProjectorOptionDefenseStation)) && (isPowersourceItem()))
					{
						return false;
					}
					if (((par1ItemStack.getItem() instanceof ItemProjectorOptionFieldFusion)) && (isPowersourceItem()))
					{
						return false;
					}
					if (((par1ItemStack.getItem() instanceof ItemProjectorOptionForceFieldJammer)) && (isPowersourceItem()))
					{
						return false;
					}
					if ((par1ItemStack.getItem() instanceof ItemProjectorOptionBase))
					{
						return modTyp.supportsOption(par1ItemStack.getItem());
					}
					break;
				case 11:
			}
		}

		return false;
	}

	public int getSlotStackLimit(int Slot)
	{
		switch (Slot)
		{
			case 5:
			case 6:
				return 64;
			case 7:
			case 8:
			case 9:
			case 10:
				return 64;
		}

		return 1;
	}

	public boolean hasValidTypeMod()
	{
		if ((getStackInSlot(1) != null) && ((getStackInSlot(1).getItem() instanceof ModuleBase)))
			return true;
		return false;
	}

	public ModuleBase get_type()
	{
		if (hasValidTypeMod())
		{
			return (ModuleBase) getStackInSlot(1).getItem();
		}
		return null;
	}

	public Set<PointXYZ> getInteriorPoints()
	{
		return this.field_interior;
	}

	public Set<PointXYZ> getfield_queue()
	{
		return this.field_def;
	}

	public TileEntityAdvSecurityStation getLinkedSecurityStation()
	{
		TileEntityAdvSecurityStation sec = ItemCardSecurityLink.getLinkedSecurityStation(this, 12, this.worldObj);
		if (sec != null)
		{
			if ((getaccesstyp() != 3) && (!isPowersourceItem()))
				setaccesstyp(3);
			return sec;
		}

		if (getaccesstyp() == 3)
			setaccesstyp(0);
		return null;
	}

	public int getSecStation_ID()
	{
		TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
		if (sec != null)
			return sec.getDeviceID();
		return 0;
	}

	public boolean hasOption(Item item, boolean includecheckall)
	{
		for (ItemProjectorOptionBase opt : getOptions(includecheckall))
		{
			if (opt == item)
				return true;
		}
		return false;
	}

	public List<ItemProjectorOptionBase> getOptions(boolean includecheckall)
	{
		List ret = new ArrayList();
		for (int place = 2; place < 5; place++)
		{
			if ((getStackInSlot(place) != null) && ((getStackInSlot(place).getItem() instanceof ItemProjectorOptionBase)))
			{
				ret.add((ItemProjectorOptionBase) getStackInSlot(place).getItem());
			}

			if (includecheckall)
			{
				for (ItemProjectorOptionBase opt : ItemProjectorOptionBase.get_instances())
				{
					if (((opt instanceof IChecksOnAll)) && (!ret.contains(opt)))
					{
						ret.add(opt);
					}
				}
			}
		}

		return ret;
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
		return 0;
	}

	@Override
	public World n()
	{
		return this.worldObj;
	}
}