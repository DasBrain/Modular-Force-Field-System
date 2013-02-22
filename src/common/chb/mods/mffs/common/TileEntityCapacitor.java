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



import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

import chb.mods.mffs.api.IForceEnergyCapacitor;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandler;

public class TileEntityCapacitor extends TileEntityMachines implements
ISidedInventory,INetworkHandlerEventListener,INetworkHandlerListener, IForceEnergyCapacitor{
	
	private ItemStack inventory[];
	private int forcePower;
	private int maxforcepower;
	private int transmitrange;
	private int Capacitor_ID;
	private int Remote_Capacitor_ID;
	private int SecStation_ID;
	private boolean create;
	private boolean LinkedSecStation;
	private short linketprojektor;
	private int capacity;
	private int SwitchTyp;
	private int Powerlinkmode;
	private boolean OnOffSwitch;

	public TileEntityCapacitor() {
		inventory = new ItemStack[5];
		transmitrange = 8;
		SecStation_ID = 0;
		forcePower = 0;
		maxforcepower = 10000000;
		Capacitor_ID = 0;
		Remote_Capacitor_ID = 0;
		linketprojektor = 0;
		create = true;
		LinkedSecStation = false;
		capacity = 0;
		SwitchTyp = 0;
		OnOffSwitch = false;
		Powerlinkmode= 0;
	
	}
	
	
	public boolean isCreate() {
		return create;
	}


	public void setCreate(boolean create) {
		this.create = create;
	}


	public int getRemote_Capacitor_ID() {
		return Remote_Capacitor_ID;
	}

	private void setRemote_Capacitor_ID(int remote_Capacitor_ID) {
		Remote_Capacitor_ID = remote_Capacitor_ID;
	}

	public int getPowerlinkmode() {
		return Powerlinkmode;
	}


	public void setPowerlinkmode(int powerlinkmode) {
		Powerlinkmode = powerlinkmode;
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

	@Override
	public int getCapacity(){
		return capacity;
	}

	
	public void setCapacity(int Capacity){
		this.capacity = Capacity;
		NetworkHandler.updateTileEntityField(this, "capacity");
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerCapacitor(inventoryplayer.player, this);
	}

	public boolean isLinkedSecStation() {
		return LinkedSecStation;
	}

	public void setLinkedSecStation(boolean linkedSecStation) {
		LinkedSecStation = linkedSecStation;
	}

	public void setMaxforcepower(int maxforcepower) {
		this.maxforcepower = maxforcepower;
	}

	@Override
	public int getMaxForcePower() {
		return maxforcepower;
	}
	
	public Short getLinketProjektor() {
		return linketprojektor;
	}

	public void setLinketprojektor(Short linketprojektor) {
		this.linketprojektor = linketprojektor;
		NetworkHandler.updateTileEntityField(this, "linketprojektor");
	}

	@Override
	public int getForcePower() {
		return forcePower;
	}
	

	public void setForcePower(int f) {
		forcePower = f;
	}

	public int getSecStation_ID() {
		return SecStation_ID;
	}

	public void setTransmitrange(short transmitrange) {
		this.transmitrange = transmitrange;
		NetworkHandler.updateTileEntityField(this, "transmitrange");
	}

	public int getTransmitRange() {
		return transmitrange;
	}

	public int getCapacitor_ID() {
		return Capacitor_ID;
	}

	public int getSizeInventory() {
		return inventory.length;
	}

	private void checkslots() {
		int stacksize = 0;
		short temp_transmitrange = 8;
		int temp_maxforcepower = 10000000;

		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap) {
				temp_maxforcepower += (2000000 * getStackInSlot(0).stackSize);
			}
			if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemupgradecaprange) {
				stacksize = getStackInSlot(0).stackSize;
			}

			if (getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemupgradecapcap
					&& getStackInSlot(0).getItem() != ModularForceFieldSystem.MFFSitemupgradecaprange) {
				dropplugins(0,this);
			}
		}

		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap) {
				temp_maxforcepower += (2000000 * getStackInSlot(1).stackSize);
			}
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemupgradecaprange) {
				stacksize += getStackInSlot(1).stackSize;
			}

			if (getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSitemupgradecapcap
					&& getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSitemupgradecaprange) {
				dropplugins(1,this);
			}
		}
		
		if (getStackInSlot(2) != null) {
			
			if (getStackInSlot(2).getItem() instanceof IForceEnergyItems) {
				
				IForceEnergyItems ForceEnergyItem = (IForceEnergyItems) getStackInSlot(2).getItem();
				
				if(ForceEnergyItem.getForceEnergy(getStackInSlot(2)) < ForceEnergyItem.getMaxForceEnergy())
				{
					
					int maxtransfer = ForceEnergyItem.getforceEnergyTransferMax();
					int freeeamount = ForceEnergyItem.getMaxForceEnergy() - ForceEnergyItem.getForceEnergy(getStackInSlot(2));
					
					if(this.getForcePower() > 0)
					{

					  if(this.getForcePower() > maxtransfer)
					  {
						    if(freeeamount > maxtransfer)
						    {
						    	ForceEnergyItem.setForceEnergy(getStackInSlot(2), ForceEnergyItem.getForceEnergy(getStackInSlot(2))+maxtransfer);
				                this.setForcePower(this.getForcePower() - maxtransfer);		    
						    }else{
						    	ForceEnergyItem.setForceEnergy(getStackInSlot(2), ForceEnergyItem.getForceEnergy(getStackInSlot(2))+freeeamount);
				                this.setForcePower(this.getForcePower() - freeeamount);	
						    }
			                
					  }else{
						  
						    if(freeeamount > this.getForcePower())
						    {
						    	ForceEnergyItem.setForceEnergy(getStackInSlot(2), ForceEnergyItem.getForceEnergy(getStackInSlot(2))+this.getForcePower());
				                this.setForcePower(this.getForcePower() - this.getForcePower());		    
						    }else{
						    	ForceEnergyItem.setForceEnergy(getStackInSlot(2), ForceEnergyItem.getForceEnergy(getStackInSlot(2))+freeeamount);
				                this.setForcePower(this.getForcePower() - freeeamount);	
						    }
						  
						  
					  }
					  
					  getStackInSlot(2).setItemDamage(ForceEnergyItem.getItemDamage(getStackInSlot(2)));
					}
			
				}
			
			}
			
			
			
			if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemfc) {
				if (this.getCapacitor_ID()!= NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(2)).getInteger("CapacitorID")) {
				
					if (this.getRemote_Capacitor_ID()!= NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(2)).getInteger("CapacitorID")) {
						
						this.setRemote_Capacitor_ID(NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(2)).getInteger("CapacitorID"));
						
						if (Linkgrid.getWorldMap(worldObj).getCapacitor()
								.get(this.getRemote_Capacitor_ID()) == null) {
							
							this.setInventorySlotContents(2, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
							this.setRemote_Capacitor_ID(0);
						}
		
					}
				
				}else{
					dropplugins(2,this);
					this.setRemote_Capacitor_ID(0);
				}
				
			}else{
				this.setRemote_Capacitor_ID(0);
				if (getStackInSlot(2).getItem() != ModularForceFieldSystem.MFFSitemcardempty && !(getStackInSlot(2).getItem() instanceof IForceEnergyItems)) {
					dropplugins(2,this);
				}
			}
		}
		
		
		


		if (getStackInSlot(4) != null) {
			if (getStackInSlot(4).getItem() == ModularForceFieldSystem.MFFSItemSecLinkCard) {
				if (SecStation_ID != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(4)).getInteger("Secstation_ID")) {
					SecStation_ID = NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(4)).getInteger("Secstation_ID");
				}
				if (SecStation_ID == 0) {
					dropplugins(4,this);
				}
				if(Linkgrid.getWorldMap(worldObj)
				.getSecStation().get(this.getSecStation_ID())!=null)
				{
				setLinkedSecStation(true);
				}
				else
				{
				setLinkedSecStation(false);
				dropplugins(4,this);
				}
			} else {
			    	SecStation_ID = 0;
				    setLinkedSecStation(false);
					dropplugins(4,this);
			}
		} else {
			SecStation_ID = 0;
			setLinkedSecStation(false);
		}

		temp_transmitrange *= (stacksize + 1);

		if (this.getTransmitRange() != temp_transmitrange) {
			this.setTransmitrange(temp_transmitrange);
		}
		if (this.getMaxForcePower() != temp_maxforcepower) {
			this.setMaxforcepower(temp_maxforcepower);
		}
		if (this.getForcePower() > this.maxforcepower) {
			this.setForcePower(maxforcepower);
		}
	}

	public void dropplugins() {
		for (int a = 0; a < this.inventory.length; a++) {
			dropplugins(a,this);
		}
	}

	public void addtogrid() {
		
		if (Capacitor_ID == 0) {
			Capacitor_ID = Linkgrid.getWorldMap(worldObj)
					.newID(this);
		}
		Linkgrid.getWorldMap(worldObj).getCapacitor().put(Capacitor_ID, this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getCapacitor().remove(getCapacitor_ID());
		dropplugins();
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		SwitchTyp = nbttagcompound.getInteger("SwitchTyp");
		OnOffSwitch = nbttagcompound.getBoolean("OnOffSwitch");
		forcePower = nbttagcompound.getInteger("forcepower");
		maxforcepower = nbttagcompound.getInteger("maxforcepower");
		transmitrange = nbttagcompound.getInteger("transmitrange");
		Capacitor_ID = nbttagcompound.getInteger("Capacitor_ID");
		Powerlinkmode = nbttagcompound.getInteger("Powerlinkmode");
	
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < inventory.length) {
				inventory[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("SwitchTyp", SwitchTyp);
		nbttagcompound.setBoolean("OnOffSwitch", OnOffSwitch);
		nbttagcompound.setInteger("forcepower", forcePower);
		nbttagcompound.setInteger("maxforcepower", maxforcepower);
		nbttagcompound.setInteger("transmitrange", transmitrange);
		nbttagcompound.setInteger("Capacitor_ID", Capacitor_ID);
		nbttagcompound.setInteger("Powerlinkmode", Powerlinkmode);
	

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void Energylost(int fpcost) {
		if (this.getForcePower() >= 0) {
			this.setForcePower(this.getForcePower() - fpcost);
		}
		if (this.getForcePower() < 0) {
			this.setForcePower(0);
		}
	}

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			
			if (this.isCreate()) {
				addtogrid();
                checkslots();
                setCreate(false);
            }
			

			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord,
					yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(
					xCoord, yCoord, zCoord);

			if(this.getswitchtyp()==0)
			{
		    this.setOnOffSwitch((powerdirekt || powerindrekt));
			}

			if (getOnOffSwitch()) {
				if (isActive() != true) {
					setActive(true);
				}
			} else {
				if (isActive() != false) {
					setActive(false);
				}
			}

			if (this.getTicker() == 10) {
				
				if(this.getLinketProjektor() != (short) Linkgrid.getWorldMap(worldObj).condevisec(getCapacitor_ID(), xCoord, yCoord, zCoord,getTransmitRange()))
				setLinketprojektor((short) Linkgrid.getWorldMap(worldObj).condevisec(getCapacitor_ID(), xCoord, yCoord, zCoord,getTransmitRange()));
				
				
				if(this.getCapacity() != ((getForcePower()/1000)*100)/(getMaxForcePower()/1000))
				   setCapacity(((getForcePower()/1000)*100)/(getMaxForcePower()/1000));
				
		
				checkslots();
				if(isActive())
				{
				powertransfer();
				}
				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));


		} 
	}
	
	private void powertransfer()
	{
		
		if(getRemote_Capacitor_ID()!= 0)
		{
	    TileEntityCapacitor RemoteCap = Linkgrid.getWorldMap(worldObj).getCapacitor().get(getRemote_Capacitor_ID());	
	    
	    if(RemoteCap != null)
	    {	    	
	    	
	      int maxtrasferrate = this.getMaxForcePower() / 120;
	      int forceenergyspace = RemoteCap.getMaxForcePower() - RemoteCap.getForcePower();
	      	    	
		switch(this.getPowerlinkmode())
		{
		case 0:
		if(getCapacity() >= 90 && RemoteCap.getCapacity() != 100)
		{
			
		    if(forceenergyspace > maxtrasferrate)
		    {
		    	RemoteCap.setForcePower(RemoteCap.getForcePower() + maxtrasferrate);
                this.setForcePower(this.getForcePower() - maxtrasferrate);		    
		    }else{
		    	RemoteCap.setForcePower(RemoteCap.getForcePower() + forceenergyspace);
                this.setForcePower(this.getForcePower() - forceenergyspace);	
		    }
			
		}
		break;
		case 1:
		if(RemoteCap.getCapacity() < this.getCapacity())	
		{
			int balancevaue = this.getForcePower()- RemoteCap.getForcePower();
			
		    if(balancevaue > maxtrasferrate)
		    {
		    	RemoteCap.setForcePower(RemoteCap.getForcePower() + maxtrasferrate);
                this.setForcePower(this.getForcePower() - maxtrasferrate);		    
		    }else{
		    	RemoteCap.setForcePower(RemoteCap.getForcePower() + balancevaue);
                this.setForcePower(this.getForcePower() - balancevaue);	
		    }
			
		}
		break;		
		case 2:
		if(getCapacity() > 0 && RemoteCap.getCapacity() != 100)
		{

		  if(this.getForcePower() > maxtrasferrate)
		  {
			    if(forceenergyspace > maxtrasferrate)
			    {
			    	RemoteCap.setForcePower(RemoteCap.getForcePower() + maxtrasferrate);
	                this.setForcePower(this.getForcePower() - maxtrasferrate);		    
			    }else{
			    	RemoteCap.setForcePower(RemoteCap.getForcePower() + forceenergyspace);
	                this.setForcePower(this.getForcePower() - forceenergyspace);	
			    }
                
		  }else{
			  
			    if(forceenergyspace > this.getForcePower())
			    {
			    	RemoteCap.setForcePower(RemoteCap.getForcePower() + this.getForcePower());
	                this.setForcePower(this.getForcePower() - this.getForcePower());		    
			    }else{
			    	RemoteCap.setForcePower(RemoteCap.getForcePower() + forceenergyspace);
	                this.setForcePower(this.getForcePower() - forceenergyspace);	
			    }
			  
			  
		  }

		}		
		break;
		}	
		}
		}
	}

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public int getInventoryStackLimit() {
		return 9;
	}

	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return "Generator";
	}

	public void closeChest() {
	}

	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}



	@Override
	public void openChest() {
	}
	
	
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}
	


	public ItemStack[] getContents() {
		return inventory;
	}

	public void setMaxStackSize(int arg0) {
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public void onNetworkHandlerEvent(int event) {
		switch(event)
		{
		case 0:
			if(this.getswitchtyp() == 0)
			{
				this.setswitchtyp(1);
			}else{
				this.setswitchtyp(0);
			}
		break;
		
		case 1:
			if(this.getPowerlinkmode() != 2)
			{
				this.setPowerlinkmode(this.getPowerlinkmode() +1);
			}else{
				this.setPowerlinkmode(0);
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
		if (field.equals("linketprojektor")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("transmitrange")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("capacity")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		
	}


	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("active");
		NetworkedFields.add("side");
		NetworkedFields.add("SwitchTyp");
		NetworkedFields.add("linketprojektor");
		NetworkedFields.add("transmitrange");
		NetworkedFields.add("capacity");
		return NetworkedFields;
	}


	@Override
	public void EMPulse(int magnitude){
		
		if(magnitude < 0)
			magnitude= 0;
		
		if(magnitude > 100)
		   magnitude = 100;
		
		this.setForcePower(getForcePower() / 100 * magnitude);
	}
	
	  @Override
	  public Packet getDescriptionPacket() {
	    return NetworkHandler.requestInitialData(this);
	  }

	
}
