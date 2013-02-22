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

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import chb.mods.mffs.api.IPersonalIDCard;



public class ItemCardPersonalID extends Item implements IPersonalIDCard{
	
	public ItemCardPersonalID(int i) {
		super(i);
		setIconIndex(18);
		setMaxStackSize(1);
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
	
	

    public static  void setOwner(ItemStack itemStack, String username)
    {
       
       NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
       nbtTagCompound.setString("name", username);

    }

    public  static void setSeclevel(ItemStack itemStack,int SecLevel)
    {
        NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
        nbtTagCompound.setInteger("SecLevel", SecLevel);
    }

    public int getSecLevel(ItemStack itemstack)
    {
   
    	NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    	if(nbtTagCompound != null)
    	{
    		return nbtTagCompound.getInteger("SecLevel");
    	}
       return 0;
    }
    
    public String getUsername(ItemStack itemstack)
    {
    	NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
    	if(nbtTagCompound != null)
    	{
    		return nbtTagCompound.getString("name") ;
    	}
       return "nobody";
    	
    }

    @Override
    public void addInformation(ItemStack itemStack, List info)
    {
            String tooltip = String.format( "Owner: %s ", NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getString("name") );
            info.add(tooltip);

            int SecLevel = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getInteger("SecLevel");
            String SecLeveldesc ="";

            switch(SecLevel)
            {
            case ModularForceFieldSystem.PERSONALID_LIMITEDACCESS:
            	SecLeveldesc = "Restricted ForceField Enter Only";
            break;
            case ModularForceFieldSystem.PERSONALID_FULLACCESS:
            	SecLeveldesc = "Full Access";
            break;
            default:
            	SecLeveldesc = "ERROR Please re-coding";
            break;
            }
            tooltip = String.format( "Security Level: %s ", SecLeveldesc );
            info.add(tooltip);
    }
}
