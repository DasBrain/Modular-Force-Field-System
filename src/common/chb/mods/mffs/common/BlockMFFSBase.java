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

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class BlockMFFSBase extends BlockContainer {
	private int blockid;
	private int texturindex;

	public BlockMFFSBase(int i, int texturindex) {
		super(i, Material.iron);
		this.texturindex = texturindex;
		blockid = i;
		setHardness(25F);
		setResistance(25F);
		setStepSound(soundMetalFootstep);
		setRequiresSelfNotify();
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World world);

	@Override
	public abstract String getTextureFile();

	@Override
	public abstract boolean onBlockActivated(World world, int i, int j, int k,EntityPlayer entityplayer, int par6, float par7, float par8, float par9);

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		
		world.removeBlockTileEntity(i, j, k);
		
		TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

		if(tileEntity instanceof TileEntityCapacitor)
		{
			((TileEntityCapacitor)tileEntity).addtogrid();
		}
		if(tileEntity instanceof TileEntityAreaDefenseStation)
		{
			((TileEntityAreaDefenseStation)tileEntity).addtogrid();
		}
		if(tileEntity instanceof TileEntityProjector)
		{
			((TileEntityProjector)tileEntity).addtogrid();
		}
		if(tileEntity instanceof TileEntitySecurityStation)
		{
			((TileEntitySecurityStation)tileEntity).addtogrid();
		}
		if(tileEntity instanceof TileEntityExtractor)
		{
			((TileEntityExtractor)tileEntity).addtogrid();
		}
		if(tileEntity instanceof TileEntityConverter)
		{
			((TileEntityConverter)tileEntity).addtogrid();
		}
	}
	@Override
	public void breakBlock(World world, int i, int j, int k,int a,int b) {
		TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

		if(tileEntity instanceof TileEntityCapacitor)
		{
			((TileEntityCapacitor)tileEntity).removefromgrid();
		}
		if(tileEntity instanceof TileEntityAreaDefenseStation)
		{
			((TileEntityAreaDefenseStation)tileEntity).removefromgrid();
		}
		if(tileEntity instanceof TileEntityProjector)
		{
			((TileEntityProjector)tileEntity).removefromgrid();
		}
		if(tileEntity instanceof TileEntitySecurityStation)
		{
			((TileEntitySecurityStation)tileEntity).removefromgrid();
		}
		if(tileEntity instanceof TileEntityExtractor)
		{
			((TileEntityExtractor)tileEntity).removefromgrid();
		}
		if(tileEntity instanceof TileEntityConverter)
		{
			((TileEntityConverter)tileEntity).removefromgrid();
		}
		
		world.removeBlockTileEntity(i, j, k);
	}

	@Override
	protected int damageDropped(int i) {
		return i;
	}

	public int idDropped(int i, Random random) {
		return blockID;
	}

	public static boolean isActive(IBlockAccess iblockaccess, int i, int j,
			int k) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);
		if (tileentity instanceof TileEntityMachines) {
			return ((TileEntityMachines) tileentity).isActive();
		} else {
			return false;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k,
			EntityLiving entityliving) {
		
		TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
		if(tileEntity instanceof TileEntityMachines)
		{


		int l = MathHelper
				.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		int i1 = Math.round(entityliving.rotationPitch);
		if (i1 >= 65) {
			((TileEntityMachines)tileEntity).setSide( (short) 1);
		} else if (i1 <= -65) {
			((TileEntityMachines)tileEntity).setSide((short) 0);
		} else if (l == 0) {
			((TileEntityMachines)tileEntity).setSide( (short) 2);
		} else if (l == 1) {
			((TileEntityMachines)tileEntity).setSide( (short) 5);
		} else if (l == 2) {
			((TileEntityMachines)tileEntity).setSide((short) 3);
		} else if (l == 3) {
			((TileEntityMachines)tileEntity).setSide((short) 4);
		}
		}
	}
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);

		int facing = (tileentity instanceof TileEntityMachines) ? ((TileEntityMachines) tileentity)
				.getSide() : 1;
		int typ = (tileentity instanceof TileEntityProjector) ? ((TileEntityProjector) tileentity)
				.getProjektor_Typ() : 0;

		if (isActive(iblockaccess, i, j, k)) {
			if (facing == l ) {
				return ((texturindex + typ) * 16) + 7 +1;
			}
			
			return ((texturindex + typ) * 16) + 7 ;
			
		} else {
			
			if (facing == l) {
				return ((texturindex + typ) * 16) +1;
			}
			
			return ((texturindex + typ) * 16);
		}
	}

	@Override
	public float getExplosionResistance(Entity entity,World world, int i, int j,
			int k, double d, double d1, double d2) {
		if (world.getBlockTileEntity(i, j, k) instanceof TileEntityMachines) {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (((TileEntityMachines) tileentity).isActive()) {
				return 999F;
			} else {
				return 25F;
			}
		}
		return 25F;
	}
}
