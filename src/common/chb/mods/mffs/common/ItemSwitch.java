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


import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemSwitch extends ItemMultitool {
	
	protected ItemSwitch(int id) {
		super(id, 1);

	}
	

	

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if (world.isRemote)
			return false;
		

		TileEntity tileentity =  world
				.getBlockTileEntity(x,y,z);

		
		if(tileentity instanceof TileEntityConverter)
		{
			
			  if(SecurityHelper.isAccessGranted(tileentity, entityplayer, world,ModularForceFieldSystem.PERSONALID_FULLACCESS))
			  {

					if(((TileEntityConverter)tileentity).getswitchtyp() == 1)
					{
						if(ForceEnergyItems.use(itemstack, 1000, false,entityplayer))
						{
					     ForceEnergyItems.use(itemstack, 1000, true,entityplayer);

							if(((TileEntityConverter)tileentity).getOnOffSwitch())
							{
								((TileEntityConverter)tileentity).setOnOffSwitch(false);
							}else{
								((TileEntityConverter)tileentity).setOnOffSwitch(true);
							}
							
						return true;
						}else{
							
							Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
							return false;
						}
					}else{
					
						Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: Wrong Mode");
						return false;
					}

			  }
			
			
		}
		
		
		
		
		
		
		if(tileentity instanceof TileEntityProjector)

		{
			
			if(!SecurityHelper.isAccessGranted(tileentity, entityplayer, world,ModularForceFieldSystem.PERSONALID_FULLACCESS))
			{return false;}

			if(((TileEntityProjector)tileentity).getswitchtyp() == 1)
			{
				if(ForceEnergyItems.use(itemstack, 1000, false,entityplayer))
				{
			     ForceEnergyItems.use(itemstack, 1000, true,entityplayer);

					if(((TileEntityProjector)tileentity).getOnOffSwitch())
					{
						((TileEntityProjector)tileentity).setOnOffSwitch(false);
					}else{
						((TileEntityProjector)tileentity).setOnOffSwitch(true);
					}
					
				return true;
				}else{
					
					Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
					return false;
				}
			}else{
			
				Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: Wrong Mode");
				return false;
			}
		}

		if(tileentity instanceof TileEntityCapacitor)

		{
			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityCapacitor)tileentity).getSecStation_ID()) != null)
			{
				if(!SecurityHelper.isAccessGranted(tileentity, entityplayer, world,ModularForceFieldSystem.PERSONALID_FULLACCESS))
				{
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}

			if(((TileEntityCapacitor)tileentity).getswitchtyp() == 1)
			{
				if(ForceEnergyItems.use(itemstack, 1000, false,entityplayer))
				{
			     ForceEnergyItems.use(itemstack, 1000, true,entityplayer);
			     
					if(((TileEntityCapacitor)tileentity).getOnOffSwitch())
					{
						((TileEntityCapacitor)tileentity).setOnOffSwitch(false);
					}else{
						((TileEntityCapacitor)tileentity).setOnOffSwitch(true);
					}

				return true;
				}else{
					
					Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: not enough EU please charge");
					return false;
				}
			}else{
			
				Functions.ChattoPlayer(entityplayer,"[MultiTool] Fail: component in wrong mode");
				return false;
			}
		}
		
		return false;
}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		
		
		if(entityplayer.isSneaking())
		{
			int powerleft = this.getForceEnergy(itemstack);
			ItemStack hand = entityplayer.inventory.getCurrentItem();
			hand= new ItemStack(ModularForceFieldSystem.MFFSitemMFDidtool, 1);
			ForceEnergyItems.charge(hand, powerleft,entityplayer);
		return hand;
		}
		return itemstack;
	}


}
