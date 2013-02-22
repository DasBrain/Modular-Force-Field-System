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

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockExtractor extends BlockMFFSBase {


	public BlockExtractor(int i, int texturindex) {
		super(i, texturindex);
		setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/Extractor.png";
	}

	
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9){
		
		
		if (entityplayer.isSneaking())
        {
			return false;
        }

		TileEntityExtractor tileentity = (TileEntityExtractor) world
				.getBlockTileEntity(i, j, k);

		if(!SecurityHelper.isAccessGranted(tileentity, entityplayer, world,ModularForceFieldSystem.PERSONALID_FULLACCESS))
		{return false;}
	
		
		

		if (entityplayer.getCurrentEquippedItem() != null
				&& entityplayer.getCurrentEquippedItem().itemID == Block.lever.blockID) {
			return false;
		}
		

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMultitool)) {
			return false;
		}

		
		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardPowerLink)) {
			return false;
		}
		
		if (!world.isRemote)
		entityplayer.openGui(ModularForceFieldSystem.instance, ModularForceFieldSystem.GUI_EXTRACTOR, world,
				i, j, k);
		
	
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		
		try {
			return (TileEntity) ModularForceFieldSystem.Extractor.newInstance();
		} catch (Exception e) {
			
			System.out.println("[ModularForceFieldSystem][Error] Extractor TileEntity fail");
			return null;
		} 
		
	}
	
}
