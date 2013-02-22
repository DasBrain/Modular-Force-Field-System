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

import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandler;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;


public class TileEntitySecurityStation extends TileEntityMachines implements
ISidedInventory, INetworkHandlerListener {
	private boolean Multiusermod;
	private String MainUser;
	private boolean create;
	private int SecurtyStation_ID;
	private ItemStack inventory[];

	public TileEntitySecurityStation() {
		inventory = new ItemStack[4];
		SecurtyStation_ID = 0;
		create = true;
		MainUser = "";
		Multiusermod = false;
	}

	public void dropplugins() {
		for (int a = 0; a < this.inventory.length; a++) {
			dropplugins(a);
		}
	}

	public boolean isMultiusermod() {
		return Multiusermod;
	}

	public void setMultiusermod(boolean multiusermod) {
		Multiusermod = multiusermod;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public String getMainUser() {
		return MainUser;
	}

	public void setMainUser(String s) {
		this.MainUser = s;
	}

	public int getSecurtyStation_ID() {
		return SecurtyStation_ID;
	}

	public void setSecurtyStation_ID(int i){
		this.SecurtyStation_ID = i;
	}

	public void dropplugins(int slot) {
		if (getStackInSlot(slot) != null) {
			if(getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink
		     || getStackInSlot(slot).getItem() instanceof ItemCardPowerLink
		     || getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj,
						(float) this.xCoord, (float) this.yCoord,
						(float) this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty,1)));
			}else
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj,
						(float) this.xCoord, (float) this.yCoord,
						(float) this.zCoord, this.getStackInSlot(slot)));
			}

			this.setInventorySlotContents(slot, null);
			this.onInventoryChanged();
		}
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerSecurityStation(inventoryplayer.player, this);
	}

	public void addtogrid() {
		
		if (SecurtyStation_ID == 0) {
			SecurtyStation_ID = Linkgrid.getWorldMap(worldObj)
					.newID(this);
		}
		Linkgrid.getWorldMap(worldObj).getSecStation().put(SecurtyStation_ID, this);
	}

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getSecStation().remove(SecurtyStation_ID);
		dropplugins();
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		SecurtyStation_ID = nbttagcompound.getInteger("Secstation_ID");
		Multiusermod = nbttagcompound.getBoolean("Secmod_Share");
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

		nbttagcompound.setInteger("Secstation_ID", SecurtyStation_ID);
		nbttagcompound.setBoolean("Secmod_Share", Multiusermod);

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

	public void updateEntity() {
		if (worldObj.isRemote == false) {
			if (this.isCreate()) {
				addtogrid();
				setCreate(false);
			}

			if (!getMainUser().equals("")) {
				if (isActive() != true) {
					setActive(true);
				}
			} else {
				if (isActive() != false) {
					setActive(false);
				}
			}


			if (this.getTicker() == 10) {
				checkslots();
				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));
		}
	}

	public void checkslots() {
		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSItemIDCard) {
				ItemCardPersonalID Card = (ItemCardPersonalID) getStackInSlot(1).getItem();
				
				String name = Card.getUsername(getStackInSlot(1));
				
				if(!getMainUser().equals(name))
				{
				setMainUser(name);
				}
				
				if(Card.getSecLevel(getStackInSlot(1)) != ModularForceFieldSystem.PERSONALID_FULLACCESS)
				{
					Card.setSeclevel(getStackInSlot(1), ModularForceFieldSystem.PERSONALID_FULLACCESS);
				}
			}else{

				setMainUser("");
				dropplugins(1);
			}
		}else{

			setMainUser("");
			
		}

		if (getStackInSlot(2) != null) {
			if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSItemIDCard) {
				ItemCardPersonalID Card = (ItemCardPersonalID) getStackInSlot(2).getItem();
				if(Card.getSecLevel(getStackInSlot(2)) != ModularForceFieldSystem.PERSONALID_FULLACCESS)
				{
					Card.setSeclevel(getStackInSlot(2),ModularForceFieldSystem.PERSONALID_FULLACCESS);
				}
			}else{
				dropplugins(2);
			}
		}

		if (getStackInSlot(3) != null) {
			if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSSecStationexidreader
					&& this.getMainUser()!="") {
				this.setMultiusermod(true);
		}else{
			dropplugins(3);
			this.setMultiusermod(false);
		}
		}else{
			this.setMultiusermod(false);
		}
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}
	
	public int getSizeInventory() {
		return inventory.length;
	}

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public int getInventoryStackLimit() {
		return 1;
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
		return "Secstation";
	}

	public void closeChest() {
	}

	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void openChest() {
	}

	public boolean RemoteInventory(TileEntity inventory, String username, int level) {
		if (((IInventory) inventory).getSizeInventory() > 0) {
			for (int a = ((IInventory) inventory).getSizeInventory() - 1; a >= 0; a--) {
				if (((IInventory) inventory).getStackInSlot(a) != null) {
					if (((IInventory) inventory).getStackInSlot(a).getItem() == ModularForceFieldSystem.MFFSItemIDCard) {
						String username_invtory = NBTTagCompoundHelper
								.getTAGfromItemstack(
										((IInventory) inventory)
												.getStackInSlot(a)).getString(
										"name");
						int SecLevel = NBTTagCompoundHelper
								.getTAGfromItemstack(
										((IInventory) inventory)
												.getStackInSlot(a)).getInteger(
										"SecLevel");

						if (username_invtory.equals(username)) {
							if(SecLevel >= level)
							{
								return true;
							}else{
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isAccessGranted(String username, int Level) {
		if(username.equalsIgnoreCase(ModularForceFieldSystem.Admin))
		{
			return true;
		}

		if (this.MainUser.equals(username)) {
			return true;
		}

		if(this.isMultiusermod())
		{
			IInventory inventory = Functions.searchinventory(this,worldObj,true);
			if(inventory == null)
			{
				return false;
			}else{
				if (RemoteInventory((TileEntity) inventory, username,Level)) {
					return true;
				}
			}
		}

		return false;
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
	public void onNetworkHandlerUpdate(String field) {
		if (field.equals("side")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		
	}

	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("active");
		NetworkedFields.add("side");


		return NetworkedFields;
	}
	
	  @Override
	  public Packet getDescriptionPacket() {
	    return NetworkHandler.requestInitialData(this);
	  }
	


}
