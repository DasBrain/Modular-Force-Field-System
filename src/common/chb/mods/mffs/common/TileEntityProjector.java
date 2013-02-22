/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
*/

package chb.mods.mffs.common;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

import chb.mods.mffs.api.IModularProjector;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandler;

public class TileEntityProjector extends TileEntityMachines implements IModularProjector,
ISidedInventory,INetworkHandlerEventListener,INetworkHandlerListener{
	private ItemStack ProjektorItemStacks[];

	private boolean[] projektoroption = { false, false, false, false, false,false,false,true,false,false,false,false,false};
	private int[] focusmatrix = { 0, 0, 0, 0 }; // Up 7,Down 8,Right 9,Left 10
	private int[] nullgrafik = { 180, 180, 180, 180, 180, 180 };
	private int[] watergrafik = { 205, 205, 207, 222, 223, 223 }; // Vanilla Water Textur
	private int[] lavagrafik = { 237, 237, 239, 254, 255, 255 };  // Vanilla Lava  Textur
	private int[] forcefieldtextur_id= { -10,-10,-10,-10,-10,-10};

	private boolean LinkedSecStation;
	private int SecStation_ID;
	private int switchdelay;
	private short forcefieldblock_meta;
	private int ProjektorTyp;
	private int Projektor_ID;
	private int linkCapacitor_ID;
	private int linkPower;
	private int blockcounter;
	private int ForceField_strength;
	private int ForceField_distance;
	private boolean burnout;
	private boolean camoflage;
	private int accesstyp;
	private int SwitchTyp;
	private boolean OnOffSwitch;
	private int capacity;

	protected Stack<Integer> field_queue = new Stack<Integer>();

	public TileEntityProjector() {
		Random random = new Random();

		ProjektorItemStacks = new ItemStack[13];
		linkCapacitor_ID = 0;
		Projektor_ID = 0;
		linkPower = 0;
		forcefieldblock_meta = ModularForceFieldSystem.FORCEFIELBOCKMETA_DEFAULT;
		ProjektorTyp = 0;
		switchdelay = 0;
		burnout = false;
		camoflage = false;
		ForceField_strength = 0;
		ForceField_distance = 0;
		accesstyp = 0;
		SecStation_ID = 0;
		LinkedSecStation = false;
		SwitchTyp = 0;
		OnOffSwitch = false;
		capacity = 0;
	}

	// Start Getter AND Setter

	
	public int getCapacity(){
		return capacity;
	}

	
	public void setCapacity(int Capacity){
		this.capacity = Capacity;
	}
	

	public boolean getOnOffSwitch() {
		return OnOffSwitch;
	}

	public void setOnOffSwitch(boolean a) {
	   this.OnOffSwitch = a;
	}

	public int getswitchtyp() {
		return SwitchTyp;
	}

	public void setswitchtyp(int a) {
	   this.SwitchTyp = a;

	}

	public boolean isLinkedSecStation() {
		return LinkedSecStation;
	}

	public void setLinkedSecStation(boolean linkedSecStation) {
		LinkedSecStation = linkedSecStation;
	}

	public int getSecStation_ID() {
		return SecStation_ID;
	}

	public int getaccesstyp() {
		return accesstyp;
	}

	public void setaccesstyp(int accesstyp) {
		this.accesstyp = accesstyp;

	}

	public int getForcefieldtextur_id(int l) {
		return forcefieldtextur_id[l];
	}

	public int[] getForcefieldtextur_id() {
		return forcefieldtextur_id;
	}

	public void setForcefieldtextur_id(int[] forcefieldtextur_id) {
		this.forcefieldtextur_id = forcefieldtextur_id;
	}

	public int getProjektor_Typ() {
		return ProjektorTyp;
	}

	public int getFocusup() {
		return focusmatrix[0];
	}

	public void setFocusup(int i) {
		focusmatrix[0] = i;
	}

	public int getFocusdown() {
		return focusmatrix[1];
	}

	public void setFocusdown(int i) {
		focusmatrix[1] = i;
	}

	public int getFocusright() {
		return focusmatrix[2];
	}

	public void setFocusright(int i) {
		focusmatrix[2] = i;
	}

	public int getFocusleft() {
		return focusmatrix[3];
	}

	public void setForcusleft(int i) {
		focusmatrix[3] = i;
	}

	public void setProjektor_Typ(int ProjektorTyp) {
		this.ProjektorTyp = ProjektorTyp;

		NetworkHandler.updateTileEntityField(this, "ProjektorTyp");
	}

	public int getForceField_strength() {
		return ForceField_strength;
	}

	public int getForceField_distance() {
		return ForceField_distance;
	}

	public void setForceField_distance(int distance) {
		this.ForceField_distance = distance;
	}

	public void setForceField_strength(int length) {
		this.ForceField_strength = length;
	}
	public int getBlockcounter() {
		return blockcounter;
	}

	public boolean isCreate() {
		return projektoroption[7];
	}

	public void setCreate(boolean create) {
		this.projektoroption[7] = create;
	}

	public int getforcefieldblock_meta() {
		return forcefieldblock_meta;
	}

	public void setforcefieldblock_meta(int ffmeta) {
		this.forcefieldblock_meta =  (short) ffmeta;
	}

	public boolean isLinkCapacitor() {
		return projektoroption[8];
	}

	public void setLinkGenerator(boolean linkGenerator) {
		projektoroption[8] = linkGenerator;
	}

	public int getLinkPower() {
		return linkPower;
	}

	public void setLinkPower(int linkPower) {
		this.linkPower = linkPower;
	}

	public int getLinkCapacitor_ID() {
		return linkCapacitor_ID;
	}

	public void setLinkCapacitor_ID(int i) {
		this.linkCapacitor_ID = i;
	}

	public boolean isOptionDamage() {
		return projektoroption[0];
	}

	public void setOptionDamage(boolean b) {
		projektoroption[0] = b;
	}

	public boolean isOptionSubwater() {
		return projektoroption[1];
	}

	public void setOptionSubwater(boolean b) {
		projektoroption[1] = b;
	}

	public boolean isOptionFieldcut() {
		return projektoroption[2];
	}

	public void setOptionFieldcut(boolean b) {
		projektoroption[2] = b;
	}

	public boolean isOptionBlockcutter() {
		return projektoroption[3];
	}

	public void setOptionBlockcutter(boolean b) {
		projektoroption[3] = b;
	}

	public int getProjektor_ID() {
		return Projektor_ID;
	}

	public boolean isOptionBlockdropper() {
		return projektoroption[4];
	}

	public void setOptionBlockdropper(boolean b) {
		projektoroption[4] = b;
	}

	public boolean isOptionFFJammer() {
		return projektoroption[5];
	}

	public void setOptionFFJammer(boolean b) {
		projektoroption[5] = b;
	}

	public boolean isOptionDefenceStation() {
		return projektoroption[9];
	}

	public void setOptionDefenceStation(boolean b) {
		projektoroption[9] = b;
	}

	public boolean isOptionMobDefense() {
		return projektoroption[10];
	}

	public void setOptionMobDefense(boolean b) {
		projektoroption[10] = b;
	}

	public boolean isBurnout() {
		return burnout;
	}

	public void setBurnout(boolean b) {
		burnout = b;
		NetworkHandler.updateTileEntityField(this, "burnout");
	}

	public boolean isJammeractive() {
		return projektoroption[6];
	}

	public void setJammer(boolean b) {
		if (b) {
			projektoroption[6] = true;
			Linkgrid.getWorldMap(worldObj).getJammer()
					.put(getProjektor_ID(), this);
		}
		if (!b) {
			projektoroption[6] = false;
			Linkgrid.getWorldMap(worldObj).getJammer()
					.remove(getProjektor_ID());
		}
	}

	public boolean isOptionFieldFusion() {
		return projektoroption[12];
	}

	public void setOptionFieldFusion(boolean b) {
		projektoroption[12] = b;
	}

	public boolean isFieldFusion() {
		return projektoroption[11];
	}

	public void setFieldFusion(boolean b) {
		if (b) {
			projektoroption[11] = true;
			Linkgrid.getWorldMap(worldObj).getFieldFusion()
					.put(getProjektor_ID(), this);
		}
		if (!b) {
			projektoroption[11] = false;
			Linkgrid.getWorldMap(worldObj).getFieldFusion()
					.remove(getProjektor_ID());
		}
	}

	public void ProjektorBurnout() {
		this.setBurnout(true);
		dropplugins();
	}

	public boolean isOptioncamouflage() {
		return camoflage;
	}

	public void setOptioncamouflage(boolean b) {
		camoflage = b;
	}

	// End Getter AND Setter

	// Start NBT Read/ Save

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		SwitchTyp = nbttagcompound.getInteger("SwitchTyp");
		OnOffSwitch = nbttagcompound.getBoolean("OnOffSwitch");
		accesstyp = nbttagcompound.getInteger("accesstyp");
		burnout = nbttagcompound.getBoolean("burnout");
		Projektor_ID = nbttagcompound.getInteger("Projektor_ID");
		ProjektorTyp = nbttagcompound.getInteger("Projektor_Typ");
		forcefieldblock_meta = nbttagcompound.getShort("forcefieldblockmeta");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		ProjektorItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < ProjektorItemStacks.length) {
				ProjektorItemStacks[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("SwitchTyp", SwitchTyp);
		nbttagcompound.setBoolean("OnOffSwitch", OnOffSwitch);
		nbttagcompound.setInteger("accesstyp", accesstyp);
		nbttagcompound.setBoolean("burnout", burnout);
		nbttagcompound.setInteger("Projektor_ID", Projektor_ID);
		nbttagcompound.setInteger("Projektor_Typ", ProjektorTyp);
		nbttagcompound.setShort("forcefieldblockmeta", forcefieldblock_meta);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < ProjektorItemStacks.length; i++) {
			if (ProjektorItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				ProjektorItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	// Stop NBT Read/ Save

	// Start Slot / Upgrades System

	public void dropplugins() {
		for (int a = 0; a < this.ProjektorItemStacks.length; a++) {
			dropplugins(a,this);
		}
	}

	public void checkslots(boolean init) {
		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemfc) {
				if (getLinkCapacitor_ID() != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(0)).getInteger("CapacitorID")) {
					setLinkCapacitor_ID(NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(0)).getInteger("CapacitorID"));
				}

				if (Linkgrid.getWorldMap(worldObj).getCapacitor()
						.get(this.getLinkCapacitor_ID()) != null) {
					int transmit = Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitor_ID())
							.getTransmitRange();
					int gen_x = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitor_ID()).xCoord
							- this.xCoord;
					int gen_y = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitor_ID()).yCoord
							- this.yCoord;
					int gen_z = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitor_ID()).zCoord
							- this.zCoord;

					if (Math.sqrt(gen_x * gen_x + gen_y * gen_y + gen_z * gen_z) <= transmit) {
						setLinkGenerator(true);
					} else {
						setLinkCapacitor_ID(0);
						setLinkGenerator(false);
					}
				} else {
					setLinkCapacitor_ID(0);
					setLinkGenerator(false);
					if (!init) {
						this.setInventorySlotContents(0, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
					}
				}
			} else {
				if (getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemcardempty) {
					dropplugins(0,this);
				}
			}
		} else {
			setLinkCapacitor_ID(0);
			setLinkGenerator(false);
		}

		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() instanceof ItemProjectorModuleBase) {
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTypwall) {
					if(getProjektor_Typ()!= 1) {setProjektor_Typ(1);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_DEFAULT);
					setOptionBlockdropper(true);
					
				}
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTypdeflector) {
					if(getProjektor_Typ()!= 2) {setProjektor_Typ(2);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_DEFAULT);
					setOptionBlockdropper(true);
				}
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTyptube) {
					if(getProjektor_Typ()!= 3) {setProjektor_Typ(3);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_DEFAULT);
					setOptionBlockdropper(false);
				}
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTypkube) {
					if(getProjektor_Typ()!= 4) {setProjektor_Typ(4);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_AREA);
					setOptionBlockdropper(false);
				}
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTypsphere) {
					if(getProjektor_Typ()!= 5) {setProjektor_Typ(5);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_AREA);
					setOptionBlockdropper(false);
				}
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTypcontainment) {
					if(getProjektor_Typ()!= 6) {setProjektor_Typ(6);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_CONTAIMENT);
					setOptionBlockdropper(true);
				}
				if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSProjectorTypAdvCube) {
					if(getProjektor_Typ()!= 7) {setProjektor_Typ(7);}
					setforcefieldblock_meta(ModularForceFieldSystem.FORCEFIELBOCKMETA_AREA);
					setOptionBlockdropper(false);
				}

				worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
			} else {
				if(getProjektor_Typ()!= 0) {setProjektor_Typ(0);}
				dropplugins(1,this);
			}
		} else {
			if(getProjektor_Typ()!= 0) {setProjektor_Typ(0);}
			worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
		}

		if (getStackInSlot(5) != null) {
			if (getStackInSlot(5).getItem() == ModularForceFieldSystem.MFFSProjectorFFDistance) {
				switch(getProjektor_Typ())
				{
				case 1:setForceField_distance(getStackInSlot(5).stackSize);break;
				case 2:setForceField_distance(getStackInSlot(5).stackSize);break;
				case 3:setForceField_distance(getStackInSlot(5).stackSize+2);break;
				case 4:setForceField_distance(getStackInSlot(5).stackSize+4);break;
				case 5:setForceField_distance(getStackInSlot(5).stackSize+4);break;
				case 6:setForceField_distance(getStackInSlot(5).stackSize);break;
				case 7:setForceField_distance(getStackInSlot(5).stackSize);break;
				}
			} else {
				dropplugins(5,this);
			}
		} else {
			switch(getProjektor_Typ())
			{
			case 1:setForceField_distance(0);break;
			case 2:setForceField_distance(0);break;
			case 3:setForceField_distance(2);break;
			case 4:setForceField_distance(4);break;
			case 5:setForceField_distance(4);break;
			case 6:setForceField_distance(0);break;
			case 7:setForceField_distance(0);break;
			}
		}

		if (getStackInSlot(6) != null) {
			if (getStackInSlot(6).getItem() == ModularForceFieldSystem.MFFSProjectorFFStrenght) {
				switch(getProjektor_Typ())
				{
				case 1:setForceField_strength(getStackInSlot(6).stackSize+1);break;
				case 2:setForceField_strength(getStackInSlot(6).stackSize+1);break;
				case 3:setForceField_strength(getStackInSlot(6).stackSize);break;
				case 4:setForceField_strength(getStackInSlot(6).stackSize+1);break;
				case 5:setForceField_strength(getStackInSlot(6).stackSize+1);break;
				case 6:setForceField_strength(getStackInSlot(6).stackSize+2);break;
				case 7:setForceField_strength(getStackInSlot(6).stackSize+3);break;
				}
			} else {
				dropplugins(6,this);
			}
		} else {
			switch(getProjektor_Typ())
			{
			case 1:setForceField_strength(1);break;
			case 2:setForceField_strength(1);break;
			case 3:setForceField_strength(0);break;
			case 4:setForceField_strength(1);break;
			case 5:setForceField_strength(1);break;
			case 6:setForceField_strength(2);break;
			case 7:setForceField_strength(3);break;
			}
		}

		// Focus function

		for (int place = 7; place < 11; place++) {
		if (getStackInSlot(place) != null) {
			if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSitemFocusmatix) {
				switch(this.getProjektor_Typ())
				{
				case 6:
					focusmatrix[place-7] = getStackInSlot(place).stackSize+1;
				break;
				case 7:
					focusmatrix[place-7] = getStackInSlot(place).stackSize+2;
				break;
				default:
					focusmatrix[place-7] = getStackInSlot(place).stackSize;
				break;
				}
			} else {
				dropplugins(place,this);
			}
		} else {
			switch(this.getProjektor_Typ())
			{
			case 6:
				focusmatrix[place-7] = 1;
			break;
			case 7:
				focusmatrix[place-7] = 2;
			break;
			default:
				focusmatrix[place-7] = 0;
			break;
			}
		}
		}

		if (getStackInSlot(11) != null) {
			int[] grafikindex = null;

		if (getStackInSlot(11).getItem() == Item.bucketLava)
				{
			grafikindex=lavagrafik;
				}
		if (getStackInSlot(11).getItem() == Item.bucketWater)
		{
			grafikindex=watergrafik;
		}
		if(grafikindex == null)
		{
			grafikindex = ModularForceFieldSystem.idmetatotextur.get(getStackInSlot(11).itemID
					+ (getStackInSlot(11).getItemDamage() * 1000));
		}

				if(grafikindex != null)
				{
					if(grafikindex != this.getForcefieldtextur_id())
					{
						this.setForcefieldtextur_id(grafikindex);
						UpdateForcefieldTexttur();
					}
				}else{
					dropplugins(11,this);
				}
		} else {
			if(this.getForcefieldtextur_id()!= nullgrafik)
			{
				this.setForcefieldtextur_id(nullgrafik);
				UpdateForcefieldTexttur();
		    }
		}

		if (getStackInSlot(12) != null) {
			if (getStackInSlot(12).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard) {
				if (SecStation_ID != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(12)).getInteger("Secstation_ID")) {
					SecStation_ID = NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(12)).getInteger("Secstation_ID");
				}
				if (SecStation_ID == 0) {
					dropplugins(12,this);
				}
				if(Linkgrid.getWorldMap(worldObj)
				.getSecStation().get(this.getSecStation_ID())!=null)
				{
				setLinkedSecStation(true);
				this.setaccesstyp(3);
				}
				else
				{
				setLinkedSecStation(false);
				dropplugins(12,this);
				if(getaccesstyp()==3)
				{setaccesstyp(0);}
				}
			} else {
			    	SecStation_ID = 0;
				    setLinkedSecStation(false);
					if(getaccesstyp() ==3)
					{setaccesstyp(0);}
					dropplugins(12,this);
			}
		} else {
			SecStation_ID = 0;
			setLinkedSecStation(false);
			if(getaccesstyp() ==3)
				{setaccesstyp(0);}
		}

		if (getStackInSlot(2) != null || getStackInSlot(3) != null
				|| getStackInSlot(4) != null) {
			boolean OptionFieldcut = false;

			if (isOptionBlockcutter()) {
				setOptionBlockcutter(false);
			}
			if (isOptionDamage()) {
				setOptionDamage(false);
				setforcefieldblock_meta((short) 0);
			}

			if (isOptionSubwater()) {
				setOptionSubwater(false);
			}

			if (isOptionFFJammer()) {
				setOptionFFJammer(false);
			}

			if (this.isOptionFieldFusion()) {
				setOptionFieldFusion(false);
			}

			if (isOptioncamouflage()) {
				setOptioncamouflage(false);
			}

			if(isOptionDefenceStation()){
				setOptionDefenceStation(false);
			}

			if(isOptionMobDefense()){
				setOptionMobDefense(false);
			}

			for (int place = 2; place < 5; place++) {
				if (getStackInSlot(place) != null) {
					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionFieldFusion) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6 ) {
							dropplugins(place,this);
						} else {
							setOptionFieldFusion(true);

							if (!isFieldFusion()) {
								this.setFieldFusion(true);
							}
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6 ) {
							dropplugins(place,this);
						} else {
							setOptionFFJammer(true);

							if (!isJammeractive()) {
								this.setJammer(true);
							}
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionSubwater) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6) {
							dropplugins(place,this);
						} else {
							setOptionSubwater(true);
						}
						continue;
					}
					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionDome) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 6) {
							dropplugins(place,this);
						} else {
							OptionFieldcut=true;
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionZapper) {
						if (this.getProjektor_Typ() == 4 || this.getProjektor_Typ() == 5 || this.getProjektor_Typ() == 6 || this.getProjektor_Typ() == 7) {
							dropplugins(place,this);
						} else {
							setOptionDamage(true);
							setforcefieldblock_meta((short) 1);
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionCutter) {
						setOptionBlockcutter(true);
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionCamouflage) {
	                     if(!this.isOptionDamage())
	                     {
	 						setforcefieldblock_meta((short) ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE);
	 						this.setOptioncamouflage(true);
	                     }else{
	                    	 dropplugins(place,this);
	                     }
							continue;
						}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionDefenceStation) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 3 || this.getProjektor_Typ() == 6  ) {
							dropplugins(place,this);
							continue;
						} else {
							this.setOptionDefenceStation(true);
						}
						continue;
					}

					if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSProjectorOptionMoobEx) {
						if (this.getProjektor_Typ() == 1 || this.getProjektor_Typ() == 2 || this.getProjektor_Typ() == 3 || this.getProjektor_Typ() == 6  ) {
							dropplugins(place,this);
							continue;
						} else {
							this.setOptionMobDefense(true);
						}
						continue;
					}

					if (getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionSubwater
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionDome
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionZapper
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionCutter
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionDefenceStation
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionMoobEx
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer
							&& getStackInSlot(place).getItem() != ModularForceFieldSystem.MFFSProjectorOptionCamouflage

							) {
						dropplugins(place,this);
						continue;
					}

					if (getProjektor_Typ() == 0) {
						dropplugins(place,this);
					}
				}
			}

			if (isJammeractive() && !isOptionFFJammer()) {
				this.setJammer(false);
			}

			if (isFieldFusion() && !isOptionFieldFusion()) {
				this.setFieldFusion(false);
			}

			if (isOptionFieldcut() != OptionFieldcut) {
				setOptionFieldcut(OptionFieldcut);
			}
		} else {
			if(isOptionDefenceStation()){
				setOptionDefenceStation(false);
			}

			if (isOptioncamouflage()) {
				setOptioncamouflage(false);
			}

			if(isOptionMobDefense()){
				setOptionMobDefense(false);
			}

			if (isOptionBlockcutter()) {
				setOptionBlockcutter(false);
			}
			if (isOptionDamage()) {
				setOptionDamage(false);
				setforcefieldblock_meta((short) 0);
			}
			if (isOptionFieldcut()) {
				setOptionFieldcut(false);
			}
			if (isOptionSubwater()) {
				setOptionSubwater(false);
			}

			if (isOptionFFJammer()) {
				setOptionFFJammer(false);
				if (isJammeractive()) {
					this.setJammer(false);
				}
			}

			if (isOptionFieldFusion()) {
				setOptionFieldFusion(false);
				if (isFieldFusion()) {
					this.setFieldFusion(false);
				}
			}
		}

		// --------------------------
		// Drop plugin by typ

		switch (getProjektor_Typ()) {
		case 0:

			dropplugins(2,this);
			dropplugins(3,this);
			dropplugins(4,this);
			dropplugins(5,this);
			dropplugins(6,this);
			dropplugins(7,this);
			dropplugins(8,this);
			dropplugins(9,this);
			dropplugins(10,this);

		case 1:

			break;
		case 2:
			dropplugins(6,this);

			break;
		case 3:

			dropplugins(7,this);
			dropplugins(8,this);
			dropplugins(9,this);
			dropplugins(10,this);

			break;
		case 4:
			dropplugins(6,this);

		case 5:

			dropplugins(7,this);
			dropplugins(8,this);
			dropplugins(9,this);
			dropplugins(10,this);

			break;

		case 7:
			dropplugins(5,this);
			break;
		}

		if(!this.isOptioncamouflage())
		{
			dropplugins(11,this);
		}

		// ---------------------------

	}

	private void UpdateForcefieldTexttur() {
		if(this.isActive() && this.isOptioncamouflage())
		{
		for (Integer hasher : field_queue) {
			ForceFieldBlockStack ffb = WorldMap.getForceFieldWorld(worldObj).getForceFieldStackMap(hasher);

			if(ffb!=null){
		     if (worldObj.getChunkFromBlockCoords(ffb.getX(), ffb.getZ()).isChunkLoaded) {
			if (ffb.getProjectorID() == getProjektor_ID()){
				TileEntity tileEntity = worldObj.getBlockTileEntity(ffb.getX(), ffb.getY(), ffb.getZ());

				if(tileEntity != null && tileEntity instanceof TileEntityForceField )
				{
					((TileEntityForceField)tileEntity).UpdateTextur();
				}
			}
		    }
	    	}
		}
	  }
	}

	// Stop Slot / Upgrades System

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (this.isCreate() && this.getLinkCapacitor_ID() != 0) {
				addtogrid();
				checkslots(true);
				if (this.isActive()) {
					fieldcalculation(getProjektor_Typ(),true);
				}
				this.setCreate(false);
			}

			if (this.getLinkCapacitor_ID() != 0) {
				this.setLinkGenerator(true);
				try {
					this.setLinkPower(Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitor_ID())
							.getForcePower());
					this.setCapacity(Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitor_ID())
							.getCapacity());
				} catch (java.lang.NullPointerException ex) {
					this.setLinkGenerator(false);
					this.setLinkPower(0);
					this.setCapacity(0);
				}
			} else {
				this.setLinkGenerator(false);
				this.setLinkPower(0);
				this.setCapacity(0);
			}

			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord,
					yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(
					xCoord, yCoord, zCoord);

			if(this.getswitchtyp()==0)
			{
		    this.setOnOffSwitch((powerdirekt || powerindrekt));
			}

			if ((getOnOffSwitch() && (switchdelay >= 40))
					&& getProjektor_Typ() != 0 && this.isLinkCapacitor()
					&& this.getLinkPower() > Forcepowerneed(5)) {
				if (isActive() != true) {
					setActive(true);
					switchdelay = 0;
					if(fieldcalculation(getProjektor_Typ(),true))
					{FieldGenerate(true);}
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}
			if ((!getOnOffSwitch() && switchdelay >= 40)
					|| getProjektor_Typ() == 0 || !this.isLinkCapacitor() || burnout
					|| this.getLinkPower() <= Forcepowerneed(1)) {
				if (isActive() != false) {
					setActive(false);
					switchdelay = 0;
					destroyField();
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}


			if (this.getTicker() == 20) {
				checkslots(false);

				if (isActive()) {
					FieldGenerate(false);
					if(this.isOptionDefenceStation())
					{ ForceFieldOptions.DefenseStation(this, worldObj,"projector","human");}

					if(this.isOptionMobDefense())
					{ForceFieldOptions.DefenseStation(this, worldObj,"projector","mobs");}
				}

				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));
		}

		switchdelay++;
	}

	public boolean fieldcalculation(int typ, boolean addtoMap) {
		if (!field_queue.isEmpty()) {
			field_queue.clear();
		}

		int tpx = 0;
		int tpy = 0;
		int tpz = 0;
		int x_offset_s = 0;
		int y_offset_s = 0;
		int z_offset_s = 0;
		int x_offset_e = 0;
		int y_offset_e = 0;
		int z_offset_e = 0;

		switch (typ) {
		// 1 Wall
		// 2 Deflect
		// 3 Tube
		// 4 Cube
		// 5 Sphere
		// 6 Conataiment
		// 7 Advance Cube

		case 1: // Wall

			for (int x1 = 0 - getFocusleft(); x1 < getFocusright() + 1; x1++) {
				for (int z1 = 0 - getFocusdown(); z1 < getFocusup() + 1; z1++) {
					for (int y1 = 1; y1 < getForceField_strength() + 1; y1++) {
						if (this.getSide() == 0) {
							tpy = y1 - y1 - y1 - getForceField_distance();
							tpx = x1;
							tpz = z1 - z1 - z1;
						}

						if (this.getSide() == 1) {
							tpy = y1 + getForceField_distance();
							tpx = x1;
							tpz = z1 - z1 - z1;
						}

						if (this.getSide() == 2) {
							tpz = y1 - y1 - y1 - getForceField_distance();
							tpx = x1 - x1 - x1;
							tpy = z1;
						}

						if (this.getSide() == 3) {
							tpz = y1 + getForceField_distance();
							tpx = x1;
							tpy = z1;
						}

						if (this.getSide() == 4) {
							tpx = y1 - y1 - y1 - getForceField_distance();
							tpz = x1;
							tpy = z1;
						}
						if (this.getSide() == 5) {
							tpx = y1 + getForceField_distance();
							tpz = x1 - x1 - x1;
							tpy = z1;
						}

						if ((this.getSide() == 0 || this.getSide() == 1)
								&& ((tpx == 0 && tpz != 0)
										|| (tpz == 0 && tpx != 0) || (tpz == 0 && tpx == 0))
								|| (this.getSide() == 2 || this.getSide() == 3)
								&& ((tpx == 0 && tpy != 0)
										|| (tpy == 0 && tpx != 0) || (tpy == 0 && tpx == 0))
								|| (this.getSide() == 4 || this.getSide() == 5)
								&& ((tpz == 0 && tpy != 0)
										|| (tpy == 0 && tpz != 0) || (tpy == 0 && tpz == 0)))

						{
						     if(!Forcefielddefine(xCoord + tpx, yCoord + tpy, zCoord + tpz,addtoMap))
						     {return false;}
						}
					}
				}
			}
			break;
		case 2: // Deflect

			for (int x1 = 0 - getFocusleft(); x1 < getFocusright() + 1; x1++) {
				for (int z1 = 0 - getFocusup(); z1 < getFocusdown() + 1; z1++) {
					if (this.getSide() == 0) {
						tpy = 0 - getForceField_distance() - 1;
						tpx = x1;
						tpz = z1;
					}

					if (this.getSide() == 1) {
						tpy = 0 + getForceField_distance() + 1;
						tpx = x1;
						tpz = z1;
					}

					if (this.getSide() == 2) {
						tpz = 0 - getForceField_distance() - 1;
						tpy = z1 - z1 - z1;
						tpx = x1 - x1 - x1;
					}

					if (this.getSide() == 3) {
						tpz = 0 + getForceField_distance() + 1;
						tpy = z1 - z1 - z1;
						tpx = x1;
					}

					if (this.getSide() == 4) {
						tpx = 0 - getForceField_distance() - 1;
						tpy = z1 - z1 - z1;
						tpz = x1;
					}
					if (this.getSide() == 5) {
						tpx = 0 + getForceField_distance() + 1;
						tpy = z1 - z1 - z1;
						tpz = x1 - x1 - x1;
					}

				     if(!Forcefielddefine(xCoord + tpx, yCoord + tpy, zCoord + tpz,addtoMap))
				     {return false;}
				}
			}
			break;
		case 3: // Tube

			if (this.getSide() == 0 || this.getSide() == 1) {
				tpy = getForceField_strength();
				tpx = getForceField_distance();
				tpz = getForceField_distance();

				y_offset_s = getForceField_strength() - getForceField_strength();
				if (this.isOptionFieldcut()) {
					if (this.getSide() == 0) {
						y_offset_e = getForceField_strength();
					}
					if (this.getSide() == 1) {
						y_offset_s = getForceField_strength();
					}
				}
			}

			if (this.getSide() == 2 || this.getSide() == 3) {
				tpy = getForceField_distance();
				tpz = getForceField_strength();
				tpx = getForceField_distance();

				z_offset_s = getForceField_strength() - getForceField_strength();
				if (this.isOptionFieldcut()) {
					if (this.getSide() == 2) {
						z_offset_e = getForceField_strength();
					}
					if (this.getSide() == 3) {
						z_offset_s = getForceField_strength();
					}
				}
			}
			if (this.getSide() == 4 || this.getSide() == 5) {
				tpy = getForceField_distance();
				tpz = getForceField_distance();
				tpx = getForceField_strength();

				x_offset_s = getForceField_strength() - getForceField_strength();
				if (this.isOptionFieldcut()) {
					if (this.getSide() == 4) {
						x_offset_e = getForceField_strength();
					}
					if (this.getSide() == 5) {
						x_offset_s = getForceField_strength();
					}
				}
			}

			for (int z1 = 0 - tpz + z_offset_s; z1 <= tpz - z_offset_e; z1++) {
				for (int x1 = 0 - tpx + x_offset_s; x1 <= tpx - x_offset_e; x1++) {
					for (int y1 = 0 - tpy + y_offset_s; y1 <= tpy - y_offset_e; y1++) {
						int tpx_temp = tpx;
						int tpy_temp = tpy;
						int tpz_temp = tpz;

						if (tpx == getForceField_strength()
								&& (this.getSide() == 4 || this.getSide() == 5)) {
							tpx_temp += 1;
						}
						if (tpy == getForceField_strength()
								&& (this.getSide() == 0 || this.getSide() == 1)) {
							tpy_temp += 1;
						}
						if (tpz == getForceField_strength()
								&& (this.getSide() == 2 || this.getSide() == 3)) {
							tpz_temp += 1;
						}

						if ((x1 == 0 - tpx_temp || x1 == tpx_temp
								|| y1 == 0 - tpy_temp || y1 == tpy_temp
								|| z1 == 0 - tpz_temp || z1 == tpz_temp)
								&& ((yCoord + y1) >= 0)) {
						     if(!Forcefielddefine(xCoord + x1, yCoord + y1, zCoord + z1,addtoMap))
						     {return false;}
						} else {
							if(this.isFieldFusion())
							{
								ForceFieldFusion(xCoord + x1, yCoord + y1, zCoord + z1);
							}

							if (isOptionSubwater()) {
								removeLiquid(xCoord + x1, yCoord + y1, zCoord
										+ z1);
							}
						}
					}
				}
			}
			break;
		case 4:// cube

			int radius_temp = 0;
			int yradius_cube = getForceField_distance();

			if (yCoord + getForceField_distance() > 255) {
				radius_temp = 255 - (yCoord + getForceField_distance());
			}

			if (isOptionFieldcut()) {
				yradius_cube = 0;
			}

			for (int y1 = 0 - yradius_cube; y1 <= getForceField_distance() + radius_temp; y1++) {
				for (int x1 = 0 - getForceField_distance(); x1 <= getForceField_distance(); x1++) {
					for (int z1 = 0 - getForceField_distance(); z1 <= getForceField_distance(); z1++) {
						if ((x1 == 0 - getForceField_distance() || x1 == getForceField_distance()
								|| y1 == 0 - getForceField_distance()
								|| y1 == getForceField_distance() + radius_temp
								|| z1 == 0 - getForceField_distance() || z1 == getForceField_distance())
								&& ((yCoord + y1) >= 0)) {
						     if(!Forcefielddefine(xCoord + x1, yCoord + y1, zCoord + z1,addtoMap))
						     {return false;}
						} else {
							if(this.isFieldFusion())
							{
								ForceFieldFusion(xCoord + x1, yCoord + y1, zCoord + z1);
							}

							if (isOptionSubwater()) {
								removeLiquid(xCoord + x1, yCoord + y1, zCoord
										+ z1);
							}
						}
					}
				}
			}
			break;
		case 5: // Sphere

			int yradius_sphere = this.getForceField_distance();

			if (isOptionFieldcut()) {
				yradius_sphere = 0;
			}
			for (int y1 = 0 - yradius_sphere; y1 <= getForceField_distance(); y1++) {
				for (int x1 = 0 - getForceField_distance(); x1 <= getForceField_distance(); x1++) {
					for (int z1 = 0 - getForceField_distance(); z1 <= getForceField_distance(); z1++) {
						int dx = (xCoord + x1) - xCoord;
						int dy = (yCoord + y1) - yCoord;
						int dz = (zCoord + z1) - zCoord;

						double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

						if (dist <= getForceField_distance() && dist > (getForceField_distance() - getForceField_strength())
								&& ((yCoord + y1) >= 0)) {
						     if(!Forcefielddefine(xCoord + x1, yCoord + y1, zCoord + z1,addtoMap))
						     {return false;}
						}else if(dist <= getForceField_distance() && yCoord + y1 >= 0){
							if(this.isFieldFusion())
							{
								ForceFieldFusion(xCoord + x1, yCoord + y1, zCoord + z1);
							}

							if (isOptionSubwater()) {
								removeLiquid(xCoord + x1, yCoord + y1, zCoord
										+ z1);
							}
						}
					}
				}
			}
			break;
		case 6: //Conataiment
		case 7: // Adv.Cube

			for (int y1 = 0; y1 <= getForceField_strength(); y1++) {
				for (int x1 = 0 - getFocusleft(); x1 < getFocusright() + 1; x1++) {
			    	for (int z1 = 0 - getFocusup(); z1 < getFocusdown() + 1; z1++) {
						if (this.getSide() == 0) {
							if(this.ProjektorTyp==6)
							{
								tpy = y1 - y1 - y1 - getForceField_distance()-1;
							}else{
								tpy = y1 - y1 - y1+1;
							}
							tpx = x1;
							tpz = z1;
						}

						if (this.getSide() == 1) {
							if(this.ProjektorTyp==6)
							{
								tpy = y1 + getForceField_distance()+1;
							}else{
								tpy = y1-1;
							}
							tpx = x1;
							tpz = z1;
						}

						if (this.getSide() == 2) {
							if(this.ProjektorTyp==6)
							{
								tpz = y1 - y1 - y1 - getForceField_distance()-1;
							}else{
								tpz = y1 - y1 - y1+1;
							}

							tpy = z1 - z1 - z1;
							tpx = x1 - x1 - x1;
						}

						if (this.getSide() == 3) {
							if(this.ProjektorTyp==6)
							{
								tpz = y1 + getForceField_distance()+1;
							}else{
								tpz = y1-1;
							}

							tpy = z1 - z1 - z1;
							tpx = x1;
						}

						if (this.getSide() == 4) {
							if(this.ProjektorTyp==6)
							{
								tpx = y1 - y1 - y1 - getForceField_distance()-1;
							}else{
								tpx = y1 - y1 - y1+1;
							}

							tpy = z1 - z1 - z1;
							tpz = x1;
						}
						if (this.getSide() == 5) {
							if(this.ProjektorTyp==6)
							{
								tpx = y1 + getForceField_distance()+1;
							}else{
								tpx = y1-1;
							}
							tpy = z1 - z1 - z1;
							tpz = x1 -x1 - x1;
						}

						if(y1==0 || y1 == getForceField_strength() || x1== 0 - getFocusleft() || x1==  getFocusright()  || z1 == 0 - getFocusup() || z1 == getFocusdown())
						{
							if(this.isOptionFieldcut() && this.getProjektor_Typ() == 7)
	                        {
								switch(this.getSide())
								{
									case 0:
										if((yCoord + tpy) > this.yCoord )
										continue;
									break;
									case 1:
										if((yCoord + tpy) < this.yCoord )
										continue;
									break;
									case 2:
										if((zCoord + tpz) > this.zCoord )
										continue;
									break;
									case 3:
										if((zCoord + tpz) < this.zCoord )
										continue;
									break;
									case 4:
										if((xCoord + tpx) > this.xCoord )
										continue;
									break;
									case 5:
										if((xCoord + tpx) < this.xCoord )
										continue;
									break;
								}
							}

					     if(!Forcefielddefine(xCoord + tpx, yCoord + tpy, zCoord + tpz,addtoMap))
					     {return false;}
						}else {
							if(this.isFieldFusion())
							{
								ForceFieldFusion(xCoord + tpx, yCoord + tpy, zCoord + tpz);
							}

							if (isOptionSubwater()) {
								removeLiquid(xCoord + tpx, yCoord + tpy, zCoord + tpz);
							}
						}
			    	}
				}
			}

		break;
		}
		return true;
	}

	private void ForceFieldFusion(int x, int y, int z) {
		ForceFieldBlockStack ffworldmap = WorldMap
				.getForceFieldWorld(worldObj)
				.getorcreateFFStackMap(x, y,
						z);

		if(!ffworldmap.isEmpty())
		{
		 if(ffworldmap.getGenratorID()== this.getLinkCapacitor_ID())
		 {
			TileEntityProjector Projector =  Linkgrid.getWorldMap(worldObj).getProjektor().get(ffworldmap.getProjectorID());

			if(Projector.isOptionFieldFusion())
			{
    			Projector.field_queue.removeElement(WorldMap.Cordhash(x, y, z));
				ffworldmap.removebyProjector(Projector.getProjektor_ID());

				if(worldObj.getBlockId(ffworldmap.getX(),ffworldmap.getY(), ffworldmap.getZ()) == ModularForceFieldSystem.MFFSFieldblock.blockID)
				{
				worldObj.removeBlockTileEntity(ffworldmap.getX(),
						ffworldmap.getY(), ffworldmap.getZ());
				worldObj.setBlockWithNotify(ffworldmap.getX(),
						ffworldmap.getY(), ffworldmap.getZ(), 0);
				}
			}
		 }
		}
	}

	public boolean Forcefielddefine(int x, int y, int z,boolean addtoMap)
	{
		if (ForceFieldOptions.CheckInnerSpace(x, y, z,this,worldObj,"jammer")) {
			ProjektorBurnout();
			return false;
		}

		if(isFieldFusion())
		{
			if(ForceFieldOptions.CheckInnerSpace(x, y, z,this,worldObj,"fieldfuser"))
			{
				return true;
			}
		}

		ForceFieldBlockStack ffworldmap = WorldMap
				.getForceFieldWorld(worldObj)
				.getorcreateFFStackMap(x, y,
						z);

		if(!ffworldmap.isEmpty())
		{
			if(ffworldmap.getProjectorID() != getProjektor_ID()){
			    ffworldmap.removebyProjector(getProjektor_ID());
				ffworldmap.add(getLinkCapacitor_ID(), getProjektor_ID(), getforcefieldblock_meta());
			    }
		}else{
			ffworldmap.add(getLinkCapacitor_ID(), getProjektor_ID(), getforcefieldblock_meta());
			ffworldmap.setSync(false);
		}

		field_queue.push(WorldMap.Cordhash(x, y, z));

		return true;
	}

	private void removeLiquid(int x, int y, int z) {
		if (worldObj.getBlockMaterial(x, y, z).isLiquid()) {
			if (!ModularForceFieldSystem.forcefieldremoveonlywaterandlava) {
				worldObj.setBlockWithNotify(x, y, z, 0);
			} else if (worldObj.getBlockId(x, y, z) == 8
					|| worldObj.getBlockId(x, y, z) == 9
					|| worldObj.getBlockId(x, y, z) == 10
					|| worldObj.getBlockId(x, y, z) == 11

			)

			{
				worldObj.setBlockWithNotify(x, y, z, 0);
			}
		}
	}

	public void FieldGenerate(boolean init) {
			TileEntity tileEntity = Linkgrid.getWorldMap(worldObj)
					.getCapacitor().get(this.linkCapacitor_ID);
			if (tileEntity instanceof TileEntityCapacitor && tileEntity != null) {
				int cost = 0;

				if (init) {
					cost = ModularForceFieldSystem.forcefieldblockcost
							* ModularForceFieldSystem.forcefieldblockcreatemodifier;
				} else {
					cost = ModularForceFieldSystem.forcefieldblockcost;
				}

				if (getforcefieldblock_meta() == 1) {
					cost *= ModularForceFieldSystem.forcefieldblockzappermodifier;
				}

				((TileEntityCapacitor) tileEntity).Energylost(cost
						* field_queue.size());
			}

		blockcounter = 0;

		for (Integer hasher : field_queue) {
			if (blockcounter == ModularForceFieldSystem.forcefieldmaxblockpeerTick) {
				break;
			}
			ForceFieldBlockStack ffb = WorldMap.getForceFieldWorld(worldObj).getForceFieldStackMap(hasher);

			if(ffb!=null){
		     if (worldObj.getChunkFromBlockCoords(ffb.getX(), ffb.getZ()).isChunkLoaded) {
		    	 if(!ffb.isEmpty()){
		            	if (ffb.getProjectorID() == getProjektor_ID()){
					if (isOptionBlockcutter()) {
						int blockid = worldObj.getBlockId(ffb.getX(),
								ffb.getY(), ffb.getZ());
						if (blockid != ModularForceFieldSystem.MFFSFieldblock.blockID
								&& blockid != 0
								&& blockid != Block.bedrock.blockID) {
							ArrayList<ItemStack> stacks = Functions
									.getItemStackFromBlock(worldObj,
											ffb.getX(), ffb.getY(), ffb.getZ());
							worldObj.setBlockWithNotify(ffb.getX(), ffb.getY(),
									ffb.getZ(), 0);

							if (isOptionBlockdropper() && stacks != null) {
								IInventory inventory = Functions
										.searchinventory(this, worldObj, false);
								if (inventory != null) {
									if (inventory.getSizeInventory() > 0) {
										addtoinventory(inventory, stacks);
									}
								}
							}
						}
					}

						if (worldObj.getBlockMaterial(ffb.getX(), ffb.getY(),
								ffb.getZ()).isLiquid()
								|| worldObj.getBlockId(ffb.getX(), ffb.getY(),
										ffb.getZ()) == 0 || worldObj.getBlockId(ffb.getX(), ffb.getY(),
												ffb.getZ()) == ModularForceFieldSystem.MFFSFieldblock.blockID) {
						if (worldObj.getBlockId(ffb.getX(), ffb.getY(),
								ffb.getZ()) != ModularForceFieldSystem.MFFSFieldblock.blockID) {
							worldObj.setBlockAndMetadataWithNotify(
									ffb.getX(),
									ffb.getY(),
									ffb.getZ(),
									ModularForceFieldSystem.MFFSFieldblock.blockID,
									ffb.getTyp());
							blockcounter++; // Count create blocks...
						}
						ffb.setSync(true);
					}
				}
			}
			}
		}
		}
	}

	public boolean addtoinventory(IInventory inventory,
			ArrayList<ItemStack> itemstacks) {
		for (int a = 0; a <= inventory.getSizeInventory() - 1; a++) {
			ItemStack inventorystack = inventory.getStackInSlot(a);

			for (ItemStack items : itemstacks) {
				if (items != null) {
					if (inventorystack != null) {
						if (inventorystack.getItem() == items.getItem()
								&& inventorystack.getItemDamage() == items
										.getItemDamage()
								&& inventorystack.stackSize + 1 <= inventorystack
										.getMaxStackSize()
								&& inventorystack.stackSize + 1 <= inventory
										.getInventoryStackLimit()) {
							inventorystack.stackSize++;

							items.stackSize--;
							return true;
						}
					} else {
						inventorystack = items.copy();
						inventorystack.stackSize = 1;
						items.stackSize--;
						inventory.setInventorySlotContents(a, inventorystack);

						return true;
					}
				}
			}
		}
		return false;
	}

	public void destroyField() {
		while(!field_queue.isEmpty()){
			ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(
					worldObj).getForceFieldStackMap(field_queue.pop());

			if(!ffworldmap.isEmpty())
			{
				if (ffworldmap.getProjectorID() == getProjektor_ID()) {
					ffworldmap.removebyProjector(getProjektor_ID());

					if(ffworldmap.isSync())
					{
						worldObj.removeBlockTileEntity(ffworldmap.getX(),
								ffworldmap.getY(), ffworldmap.getZ());
						worldObj.setBlockWithNotify(ffworldmap.getX(),
								ffworldmap.getY(), ffworldmap.getZ(), 0);
					}

					ffworldmap.setSync(false);
				}else{
					ffworldmap.removebyProjector(getProjektor_ID());
				}
		  }
		}

		Map<Integer, TileEntityProjector> FieldFusion = Linkgrid.getWorldMap(worldObj).getFieldFusion();
		for (TileEntityProjector tileentity : FieldFusion.values()) {
		 if(tileentity.getLinkCapacitor_ID() == this.linkCapacitor_ID)
		 {
			 if(tileentity.isActive())
			 {
				 tileentity.fieldcalculation(tileentity.getProjektor_Typ(),false);
			 }
		 }
	  }
	}

	public void addtogrid() {
		
		if (Projektor_ID == 0) {
			Projektor_ID = Linkgrid.getWorldMap(worldObj)
					.newID(this);
		}
		Linkgrid.getWorldMap(worldObj).getProjektor().put(Projektor_ID, this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getProjektor().remove(getProjektor_ID());
		dropplugins();
		destroyField();
	}

	public int Forcepowerneed(int factor) {
		if (!field_queue.isEmpty()) {
			return field_queue.size()
					* ModularForceFieldSystem.forcefieldblockcost;
		}

		int forcepower = 0;
		int blocks = 0;

		int tmplength = 1;

		if (this.getForceField_strength() != 0) {
			tmplength = this.getForceField_strength();
		}

		switch (this.getProjektor_Typ()) {
		case 1:
			blocks = ((this.getFocusdown() + this.getFocusleft()
					+ this.getFocusright() + this.getFocusup()) + 1)
					* tmplength;
			break;
		case 2:
			blocks = (this.getFocusdown() + this.getFocusup() + 1)
					* (this.getFocusleft() + this.getFocusright() + 1);
			break;
		case 3:
			blocks = (((this.getForceField_distance() + 2 + this.getForceField_distance() + 2) * 4) + 4)
					* (this.getForceField_strength() + 1);
			break;
		case 4:
		case 5:
			blocks = (this.getForceField_distance() * this.getForceField_distance()) * 6;
			break;
		}

		forcepower = blocks * ModularForceFieldSystem.forcefieldblockcost;
		if (factor != 1) {
			forcepower = (forcepower * ModularForceFieldSystem.forcefieldblockcreatemodifier)
					+ (forcepower * 5);
		}
		return forcepower;
	}

	public ItemStack decrStackSize(int i, int j) {
		if (ProjektorItemStacks[i] != null) {
			if (ProjektorItemStacks[i].stackSize <= j) {
				ItemStack itemstack = ProjektorItemStacks[i];
				ProjektorItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = ProjektorItemStacks[i].splitStack(j);
			if (ProjektorItemStacks[i].stackSize == 0) {
				ProjektorItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		ProjektorItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistanceSq((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public ItemStack getStackInSlot(int i) {
		return ProjektorItemStacks[i];
	}

	public String getInvName() {
		return "Projektor";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getSizeInventory() {
		return ProjektorItemStacks.length;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerProjector(inventoryplayer.player, this);
	}



	public ItemStack[] getContents() {
		return ProjektorItemStacks;
	}

	public void setMaxStackSize(int arg0) {
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 11;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	public void onNetworkHandlerEvent(int event) {
		
		   switch(event)
		   {
		   case 0:

			   if(getaccesstyp()!=3)
			   {
			   if(getaccesstyp() == 2)
			   {
				   setaccesstyp(0);
		       }else{
		    	   setaccesstyp(getaccesstyp()+1);
		       }
			   }

		   break;
		   case 1:
			if(this.getswitchtyp() == 0)
			{
				this.setswitchtyp(1);
			}else{
				this.setswitchtyp(0);
			}

		   break;
		   }
		
	}

	@Override
	public void onNetworkHandlerUpdate(String field) {
		
		if (field.equals("side")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("ProjektorTyp")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		
	}

	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("ProjektorTyp");
		NetworkedFields.add("active");
		NetworkedFields.add("side");
		NetworkedFields.add("burnout");
		NetworkedFields.add("camoflage");
	
		return NetworkedFields;
	}
	
	  @Override
	  public Packet getDescriptionPacket() {
	    return NetworkHandler.requestInitialData(this);
	  }
	
}
