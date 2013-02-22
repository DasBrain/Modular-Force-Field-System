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

import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import chb.mods.mffs.api.IMFFS_Wrench;
import chb.mods.mffs.network.NetworkHandler;

public abstract class TileEntityMachines extends TileEntity implements IMFFS_Wrench{
	
	private boolean active;
	private int side;
	private short ticker;

	public TileEntityMachines()

	{
		active = false;
		side = -1;
		ticker = 0;
	}


	public  void dropplugins(int slot ,IInventory inventory ) {
		if (inventory.getStackInSlot(slot) != null) {
			if(inventory.getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink
		     || inventory.getStackInSlot(slot).getItem() instanceof ItemCardPowerLink
		     || inventory.getStackInSlot(slot).getItem() instanceof ItemCardPersonalID)
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj,
						(float) this.xCoord, (float) this.yCoord,
						(float) this.zCoord, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty,1)));
			}else
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj,
						(float) this.xCoord, (float) this.yCoord,
						(float) this.zCoord, inventory.getStackInSlot(slot)));
			}

			inventory.setInventorySlotContents(slot, null);
			this.onInventoryChanged();
		}
	}

	public abstract Container getContainer(InventoryPlayer inventoryplayer);

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		side = nbttagcompound.getInteger("side");
		active = nbttagcompound.getBoolean("active");

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("side", side);
		nbttagcompound.setBoolean("active", active);

	}
	
	@Override
	public boolean wrenchCanManipulate(EntityPlayer entityPlayer, int side) {
		
		   if(!SecurityHelper.isAccessGranted(this, entityPlayer, worldObj,ModularForceFieldSystem.PERSONALID_FULLACCESS))
		   {return false;}
		   
		return true;
	}

	public short getTicker() {
		return ticker;
	}

	public void setTicker(short ticker) {
		this.ticker = ticker;
	}
	@Override
	public void setSide(int i) {
		side = i;
		NetworkHandler.updateTileEntityField(this, "side");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean flag) {
		active = flag;
		NetworkHandler.updateTileEntityField(this, "active");
	}
	
	@Override
	public int getSide() {
		return side;
	}
	

}

