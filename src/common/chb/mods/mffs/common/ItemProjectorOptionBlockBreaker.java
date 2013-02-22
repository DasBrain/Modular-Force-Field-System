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
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemProjectorOptionBlockBreaker extends Item  {
	public ItemProjectorOptionBlockBreaker(int i) {
		super(i);
		setIconIndex(38);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMaterials);
	}

	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable() {
		return false;
	}

    public void addInformation(ItemStack itemStack, List info)
    {
            String tooltip = "compatible to: <ALL>";
            info.add(tooltip);
    }
}
