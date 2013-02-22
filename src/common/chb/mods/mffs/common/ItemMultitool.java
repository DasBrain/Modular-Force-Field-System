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

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public abstract class ItemMultitool extends  Item  implements IForceEnergyItems{
	private int typ;

	protected ItemMultitool(int id,int typ) {
		super(id);
		this.typ = typ;
		setIconIndex(typ);
		setMaxStackSize(1);
		setMaxDamage(100);
		setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}


	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean isDamageable()
	{
	return true;
	}


	@Override
	public abstract boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);

	@Override
	public abstract ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer);
	
	@Override
    public   void setForceEnergy(ItemStack itemStack, int  ForceEnergy)
    {
       
       NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
       nbtTagCompound.setInteger("ForceEnergy", ForceEnergy);

    }

    @Override
    public  int getForceEnergy(ItemStack itemstack)
    {
   
    	NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    	if(nbtTagCompound != null)
    	{
    		return nbtTagCompound.getInteger("ForceEnergy");
    	}
       return 0;
    }
		
	    
	    @Override
	    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	    {
	    	par1ItemStack.setItemDamage(getItemDamage(par1ItemStack));
	    }
	    

	    @Override
	    public void addInformation(ItemStack itemStack, List info)
	    {
	        String tooltip = String.format( "%d FE/%d FE ",getForceEnergy(itemStack),getMaxForceEnergy());
	        info.add(tooltip);
	    }
	    
		@Override
		public int getforceEnergyTransferMax() {
			
			return 50000;
		}
		
		@Override
		public  int getMaxForceEnergy() {
			
			return 1000000;
		}
		

		
		@Override
		public int getItemDamage(ItemStack itemStack)
		{
			return 101-((getForceEnergy(itemStack)*100)/getMaxForceEnergy());
			
		}
}
