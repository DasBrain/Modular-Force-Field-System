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

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;

public class BlockMonazitOre extends Block {
	public BlockMonazitOre(int i) {
		super(i, Material.rock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundStoneFootstep);
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/forciciumore.png";
	}

    @Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
    	return 0;
	}
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ModularForceFieldSystem.MFFSitemForcicium.shiftedIndex;
    }
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 4 + par1Random.nextInt(2);
    }
}
