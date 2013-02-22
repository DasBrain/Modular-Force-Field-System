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


import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.World;



public class ItemPersonalIDWriter extends ItemMultitool{
	
	public ItemPersonalIDWriter(int i) {
		super(i,2);
	}


	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if(entityplayer.isSneaking())
		{
	
		int powerleft = this.getForceEnergy(itemstack);
		ItemStack hand = entityplayer.inventory.getCurrentItem();
		hand= new ItemStack(ModularForceFieldSystem.MFFSitemFieldTeleporter, 1);
		ForceEnergyItems.charge(hand, powerleft,entityplayer);
		
		return hand;
		}
	
			List<Slot> slots = entityplayer.inventorySlots.inventorySlots;
			for (Slot slot : slots) {
				if (slot.getStack() != null) {
					if (slot.getStack().getItem() == ModularForceFieldSystem.MFFSitemcardempty) {
						if(ForceEnergyItems.use(itemstack, 1000, false,entityplayer))
						{
					     ForceEnergyItems.use(itemstack, 1000, true,entityplayer);
                            ItemStack IDCard= new ItemStack(ModularForceFieldSystem.MFFSItemIDCard, 1);
                            ItemCardPersonalID.setOwner(IDCard, entityplayer.username);
                            ItemCardPersonalID.setSeclevel(IDCard, 1);
		                    slot.putStack(IDCard);
							
	                        if(world.isRemote)
							Functions.ChattoPlayer(entityplayer,"[MultiTool] Success: ID-Card create");

							return itemstack;
						}else{
							 if(world.isRemote)
							Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
							return itemstack;
						}
					}
				}
			}
			if(world.isRemote)
			Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: need MFFS Card <blank> in  Inventory");

		return itemstack;
	}


	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		return false;
	}


}
