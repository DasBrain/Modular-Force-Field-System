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
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import chb.mods.mffs.api.IForceFieldBlock;
import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class BlockForceField extends BlockContainer implements IForceFieldBlock{
    public static int renderer;
    public int posx;
    public int posy;
    public int posz;

	public BlockForceField(int i) {
		super(i, i, Material.glass);
		setBlockUnbreakable();
		setResistance(999F);
		setTickRandomly(true);
	}
	
	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/blocks.png";
	}
	

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		this.posx = i;
		this.posy = j;
	    this.posz = k;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderBlockPass() {

		if(ModularForceFieldSystem.proxy.getClientWorld().getBlockMetadata(posx , posy, posz) == ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
		{
			TileEntityForceField ForceField   =	(TileEntityForceField) ModularForceFieldSystem.proxy.getClientWorld().getBlockTileEntity(posx , posy, posz);

	        if(ForceField  != null){
	        	if(ForceField.getTexturid(1) == 67 || ForceField.getTexturid(1) == 205)
	        	{
	        		return 1;
	        	}else{
	        		return 0;
	        	}
	        	
	        }
	     }
		return 0;
	}

    @Override
    public int getRenderType()
    {
        return ModularForceFieldSystem.FORCEFIELDRENDER_ID;
    }

	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	@Override
    protected boolean canSilkHarvest()
    {
        return false;
    }


    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
	
    	if (par1World.isRemote)
			return;
    	
	ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(par1World).getForceFieldStackMap(WorldMap.Cordhash(par2, par3, par4));

	if(ffworldmap != null)
	{
		 par5EntityPlayer.attackEntityFrom(DamageSource.generic,10);
		 Functions.ChattoPlayer((EntityPlayer)par5EntityPlayer,"[Force Field] Attention High Energy Field");
	}
	
	  Random random = null;
	  updateTick(par1World, par2, par3, par4,random);
    }
	


	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 1) {
			float f = 0.0625F;
			return AxisAlignedBB.getBoundingBox(i + f, j + f, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
		}
		
		return AxisAlignedBB.getBoundingBox((float) i, j, (float) k, (float) (i + 1), (float) (j + 1), (float) (k + 1));
	
	}

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBox((float) i, j, (float) k, (float) (i + 0), j + 0, (float) (k + 0));
	}
    
    

    
    @Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k,
			Entity entity) {
    	
		if (world.getBlockMetadata(i, j, k) == 1) {
			if (entity instanceof EntityLiving) {
				entity.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.DefenseStationDamage);
			}
		}else{
			if (entity instanceof EntityPlayer) {
			ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getorcreateFFStackMap(i, j, k);
			
			if (ffworldmap != null) {
	
				int First_Gen_ID = ffworldmap.getGenratorID();
				int First_Pro_ID = ffworldmap.getProjectorID();

				 TileEntityCapacitor  generator = Linkgrid.getWorldMap(world).getCapacitor().get(First_Gen_ID);
				 TileEntityProjector  projector = Linkgrid.getWorldMap(world).getProjektor().get(First_Pro_ID);

				 if(generator != null && projector!= null)
				 {

					 boolean passtrue = false;

				switch(projector.getaccesstyp())
				{
				case 0:
					passtrue = false;	
					if(ModularForceFieldSystem.Admin.equals(((EntityPlayer)entity).username))
					passtrue = true;
				break;
				case 1:
				passtrue = true;
				break;
				case 2:
					passtrue = SecurityHelper.isAccessGranted(generator, ((EntityPlayer) entity), world,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
				break;
				case 3:
					passtrue = SecurityHelper.isAccessGranted(projector, ((EntityPlayer) entity), world,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
				break;
				}
			
				if(!passtrue)
				{
					((EntityPlayer) entity).setEntityHealth(0);
				
				}else{
					((EntityPlayer) entity).attackEntityFrom(DamageSource.generic,1);
				}
				Functions.ChattoPlayer((EntityPlayer)entity,"[Force Field] Attention High Energy Field");
			 }
			}
		}
			
     }
				

	}
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side) {
    	int xCord = x;
    	int yCord = y;
    	int zCord = z;

		switch(side) {
		case 0: yCord++;
		break;
    	case 1: yCord--;
		break;
		case 2: zCord++;
		break;
		case 3: zCord--;
		break;
		case 4: xCord++;
		break;
		case 5: xCord--;
		break;
		}

		if(blockID == iblockaccess.getBlockId(x, y, z) && iblockaccess.getBlockMetadata(x, y, z) == iblockaccess.getBlockMetadata(xCord, yCord, zCord))
			return false;

		return super.shouldSideBeRendered(iblockaccess, x, y, z, side);
		
	}

    @Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
		TileEntity tileEntity = iblockaccess.getBlockTileEntity(i, j, k);

		if (tileEntity!=null && tileEntity instanceof TileEntityForceField )
		{
		return  ((TileEntityForceField)tileEntity).getTexturid(l);
	}else{
		if (iblockaccess.getBlockMetadata(i, j, k) == ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
		{
			 return 180;
		}else{
			return iblockaccess.getBlockMetadata(i, j, k);
		}
	}
	}

	@Override
	public float getExplosionResistance(Entity entity,World world, int i, int j,
			int k, double d, double d1, double d2) {
		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world)
				.getForceFieldStackMap(WorldMap.Cordhash(i, j, k));
		if (ffworldmap != null && !ffworldmap.isEmpty()) {
			TileEntity tileEntity = Linkgrid.getWorldMap(world).getCapacitor()
					.get(ffworldmap.getGenratorID());
			if (tileEntity instanceof TileEntityCapacitor && tileEntity != null) {
				((TileEntityCapacitor) tileEntity)
						.Energylost(ModularForceFieldSystem.forcefieldblockcost
								* ModularForceFieldSystem.forcefieldblockcreatemodifier);
			}
		}

		return 999F;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k,
			Random random) {
		if (world.getBlockMetadata(i, j, k) == ModularForceFieldSystem.FORCEFIELBOCKMETA_ZAPPER) {
			double d = i + Math.random()+ 0.2D;
			double d1 = j + Math.random() + 0.2D;
			double d2 = k + Math.random() + 0.2D;

			world.spawnParticle("townaura", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
	@Override
	public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k,
			int dir) {
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(WorldMap.Cordhash(x, y, z));

	if(ffworldmap != null)
	{
		if(!ffworldmap.isEmpty()) {
			TileEntityProjector Projector  =	Linkgrid.getWorldMap(world).getProjektor().get(ffworldmap.getProjectorID());
	if(Projector != null){
		if(!Projector.isActive()){
			ffworldmap.removebyProjector(ffworldmap.getProjectorID());
		}
	}
		}
	}

		if(ffworldmap == null || ffworldmap.isEmpty())
		{
			world.removeBlockTileEntity(x, y, z);
			world.setBlockWithNotify(x, y, z, 0);
		}
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		if(meta == ModularForceFieldSystem.FORCEFIELBOCKMETA_CAMOFLAGE)
		{
			return new TileEntityForceField();
		}

		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return createTileEntity(world,0);
	}
}
