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
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerConverter extends Container {

    private int linkPower;
    private int SwitchTyp;
    private int capacity;
    private int output;
	private EntityPlayer player;
	private TileEntityConverter Capacitor;	
	
	public ContainerConverter(EntityPlayer player,
			TileEntityConverter tileentity) {

		Capacitor = tileentity;
		this.player = player;
        linkPower = -1;
        SwitchTyp = -1;
        capacity = -1;
        output = -1;
	
		addSlotToContainer(new Slot(Capacitor, 0, 11, 25));
		addSlotToContainer(new Slot(Capacitor, 1, 125, 38));
	
		int var3;

		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9,
						8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 142));
		}
	}
	
	
	
    public void updateCraftingResults() {
        super.updateCraftingResults();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

            if(linkPower != Capacitor.getLinkPower()) {
            	icrafting.updateCraftingInventoryInfo(this, 0, Capacitor.getLinkPower() & 0xffff);
            	icrafting.updateCraftingInventoryInfo(this, 1,Capacitor.getLinkPower() >>> 16);
            }

            if(capacity != Capacitor.getCapacity())
            	icrafting.updateCraftingInventoryInfo(this, 2, Capacitor.getCapacity());

            if(SwitchTyp != Capacitor.getswitchtyp())
            	icrafting.updateCraftingInventoryInfo(this, 3,Capacitor.getswitchtyp());

            if(output != Capacitor.getOutput())
            	icrafting.updateCraftingInventoryInfo(this, 4, Capacitor.getOutput());
        }

        linkPower = Capacitor.getLinkPower();
        SwitchTyp = Capacitor.getswitchtyp();
        capacity = Capacitor.getCapacity();
        output = Capacitor.getOutput();
    }
	
	

    public void updateProgressBar(int i, int j) {
        switch(i) {
        case 0: 
            Capacitor.setLinkPower(Capacitor.getLinkPower() & 0xffff0000 | j);
            break;
        case 1: 
            Capacitor.setLinkPower(Capacitor.getLinkPower() & 0xffff | j << 16);
            break;
        case 2: 
            Capacitor.setCapacity(j);
            break;
        case 3: 
            Capacitor.setswitchtyp(j);
            break;
        case 4: 
            Capacitor.setOutput(j);
            break;
        }
    }
	


    public EntityPlayer getPlayer() {
    	return player;
    }
	
	
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return Capacitor.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}

}
