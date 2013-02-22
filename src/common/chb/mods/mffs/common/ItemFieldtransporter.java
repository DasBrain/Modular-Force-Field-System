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

import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;



public class ItemFieldtransporter extends ItemMultitool{

	protected ItemFieldtransporter(int id) {
	super(id, 4);
	
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer entityplayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		if (world.isRemote)
			return false;
		
		ForceFieldWorld wff = WorldMap.getForceFieldWorld(world);

		ForceFieldBlockStack ffworldmap = wff.getorcreateFFStackMap(x,y,z);
		if (ffworldmap != null) {
			int Sec_Gen_ID = -1;
			int First_Gen_ID = ffworldmap.getGenratorID();
			int First_Pro_ID = ffworldmap.getProjectorID();

			 TileEntityCapacitor  generator = Linkgrid.getWorldMap(world).getCapacitor().get(First_Gen_ID);
			 TileEntityProjector  projector = Linkgrid.getWorldMap(world).getProjektor().get(First_Pro_ID);

			 if(generator != null && projector!= null)
			 {
				 if(projector.isActive())
				 {
				 boolean passtrue = false;

			switch(projector.getaccesstyp())
			{
			case 0:
			passtrue = false;	
			if(ModularForceFieldSystem.Admin.equals(entityplayer.username))
			passtrue = true;
		
			break;
			case 1:
			passtrue = true;
			break;
			case 2:
				passtrue = SecurityHelper.isAccessGranted(generator, entityplayer, world,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
			break;
			case 3:
				passtrue = SecurityHelper.isAccessGranted(projector, entityplayer, world,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
			break;

			}


				if (passtrue) {
					int typ = 99;
					int ymodi =0;

					int lm = MathHelper
							.floor_double((double) ((entityplayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
					int i1 = Math.round(entityplayer.rotationPitch);

					if (i1 >= 65) { // Facing 1
					typ = 1;
					} else if (i1 <= -65) { // Facing 0
					typ = 0;
					} else if (lm == 0) { // Facing 2
					typ = 2;
					} else if (lm == 1) { // Facing 5
					typ = 5;
					} else if (lm == 2) { // Facing 3
					typ = 3;
					} else if (lm == 3) { // Facing 4
					typ = 4;
					}

					int counter = 0;
					while (Sec_Gen_ID != 0) {
						Sec_Gen_ID = wff.isExistForceFieldStackMap(x, y , z ,counter,typ);
						if(Sec_Gen_ID!=0)
						{
						counter++;
						}
					}

					if(First_Gen_ID != wff.isExistForceFieldStackMap(x, y , z ,counter-1,typ))
					{
					 Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					 return false;
					}

					switch(typ)
					{
					case 0:
						y += counter;
						ymodi  =-1;
						break;
					case 1:
						y -= counter;
						ymodi  = 1;
						break;
					case 2:
						z += counter;
						break;
					case 3:
						z -= counter;
						break;
				    case 4:
				    	x += counter;
				    	break;
				    case 5:
				    	x -= counter;
				    	break;
					}
					
					Functions.ChattoPlayer(entityplayer,"[Field Security] Success: access granted");

					if (counter >= 0 && counter <= 5) {
						if (world.getBlockId(x, y , z) == 0 && world.getBlockId(x, y - ymodi, z) == 0) {
							if(y-ymodi <=0){
							Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: transmission into Void not allowed ");
							}else{
								if(ForceEnergyItems.use(stack, ModularForceFieldSystem.forcefieldtransportcost,false,entityplayer))
								{
									ForceEnergyItems.use(stack, ModularForceFieldSystem.forcefieldtransportcost,true,entityplayer);
									entityplayer.setPositionAndUpdate(x + 0.5, y-ymodi ,z + 0.5);
								
									Functions.ChattoPlayer(entityplayer,"[Field Security] Success: transmission complete");
								}else{
								
									Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: not enough FP please charge  ");
								}
						    	
							}
						} else {
						
							Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: detected obstacle ");
						}
					}else {
					
						Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: Field to Strong >= 5 Blocks");
					}
				} else {
					{
						Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					}
				}
			 }
		}
}

return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		if(entityplayer.isSneaking())
		{
			int powerleft = this.getForceEnergy(itemstack);
			ItemStack hand = entityplayer.inventory.getCurrentItem();
			hand= new ItemStack(ModularForceFieldSystem.MFFSitemWrench, 1);
			ForceEnergyItems.charge(hand, powerleft,entityplayer);
		return hand;
		}
		return itemstack;
		
	}

}
