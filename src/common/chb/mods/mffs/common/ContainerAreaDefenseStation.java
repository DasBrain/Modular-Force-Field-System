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

public class ContainerAreaDefenseStation extends Container {
	private TileEntityAreaDefenseStation defstation;

	private int forcepower;
	private int maxforcepower;
	private EntityPlayer player;

	public ContainerAreaDefenseStation(EntityPlayer player,
			TileEntityAreaDefenseStation tileentity) {
		forcepower = -1;
		maxforcepower = -1;

		defstation = tileentity;
		this.player = player;

		addSlotToContainer(new Slot(defstation, 0, 10, 44));
		addSlotToContainer(new Slot(defstation, 1, 10, 19));

		addSlotToContainer(new Slot(defstation, 2, 128, 13));
		addSlotToContainer(new Slot(defstation, 3, 128, 44));

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

    public EntityPlayer getPlayer() {
    	return player;
    }

	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			if (forcepower != defstation.getLinkPower()) {
				icrafting.updateCraftingInventoryInfo(this, 0,
						defstation.getLinkPower() & 0xffff);
				icrafting.updateCraftingInventoryInfo(this, 1,
						defstation.getLinkPower() >>> 16);
			}

			if (maxforcepower != defstation.getMaxlinkPower()) {
				icrafting.updateCraftingInventoryInfo(this, 2,
						defstation.getMaxlinkPower() & 0xffff);
				icrafting.updateCraftingInventoryInfo(this, 3,
						defstation.getMaxlinkPower() >>> 16);
			}
		}

		forcepower = defstation.getLinkPower();
		maxforcepower = defstation.getMaxlinkPower();
	}

	public void updateProgressBar(int i, int j) {
		switch (i) {
		case 0:
			defstation
					.setLinkPower((defstation.getLinkPower() & 0xffff0000)
							| j);
			break;
		case 1:
			defstation
					.setLinkPower((defstation.getLinkPower() & 0xffff)
							| (j << 16));
			break;

		case 2:
			defstation.setMaxlinkPower((defstation
					.getMaxlinkPower() & 0xffff0000) | j);
			break;
		case 3:
			defstation.setMaxlinkPower((defstation
					.getMaxlinkPower() & 0xffff) | (j << 16));
			break;
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return defstation.isUseableByPlayer(entityplayer);
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