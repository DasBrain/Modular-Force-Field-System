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
import java.util.Map;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public final class ForceFieldOptions {
//----------------------------Player / Mob Attack Function----------------------

	public static void DefenseStation(TileEntity Entity, World world, String Typ,String Target)
	{
		if(Typ.equals("projector") && Entity instanceof TileEntityProjector)
		{
		 TileEntityProjector  projector =  (TileEntityProjector) Entity;

		if(projector.isActive())
		{
			int xmin=0;int xmax=0;int ymin=0;int ymax=0;int zmin=0;int zmax=0;

			if(projector.getProjektor_Typ() == 4 || projector.getProjektor_Typ() == 5)
			{
				 xmin = projector.xCoord - projector.getForceField_distance();
				 xmax = projector.xCoord + projector.getForceField_distance();
				 ymin = projector.yCoord - projector.getForceField_distance();if(ymin<0){ymin = 0;}
		         ymax = projector.yCoord + projector.getForceField_distance(); if(ymax>255){ymax = 255;}
				 zmin = projector.zCoord - projector.getForceField_distance();
				 zmax = projector.zCoord + projector.getForceField_distance();
			}

			if(projector.getProjektor_Typ() == 7)
			{
				switch(projector.getSide())
				{
				case 0:
					ymax = projector.yCoord +1;
					ymin = projector.yCoord - projector.getForceField_strength();
					xmax = projector.xCoord + projector.getFocusleft();
					xmin = projector.xCoord - projector.getFocusright();
					zmin = projector.zCoord - projector.getFocusup();
					zmax = projector.zCoord	+ projector.getFocusdown();
				break;
				case 1:
					ymin = projector.yCoord -1;
					ymax = projector.yCoord + projector.getForceField_strength();
					xmin = projector.xCoord - projector.getFocusleft();
					xmax = projector.xCoord + projector.getFocusright();
					zmin = projector.zCoord - projector.getFocusup();
					zmax = projector.zCoord	+ projector.getFocusdown();
				break;
				case 2:
					zmax = projector.zCoord +1;
					zmin = projector.zCoord - projector.getForceField_strength();
					xmax = projector.xCoord + projector.getFocusleft();
					xmin = projector.xCoord - projector.getFocusright();
					ymax = projector.yCoord + projector.getFocusup();
					ymin = projector.yCoord	- projector.getFocusdown();
				break;
				case 3:
					zmin = projector.zCoord -1;
					zmax = projector.zCoord + projector.getForceField_strength();
					xmax = projector.xCoord + projector.getFocusleft();
					xmin = projector.xCoord - projector.getFocusright();
					ymax = projector.yCoord + projector.getFocusup();
					ymin = projector.yCoord	- projector.getFocusdown();
				break;
				case 4:
					xmax = projector.xCoord +1;
					xmin = projector.xCoord - projector.getForceField_strength();
					zmin = projector.zCoord - projector.getFocusleft();
					zmax = projector.zCoord + projector.getFocusright();
					ymin = projector.yCoord - projector.getFocusup();
					ymax = projector.yCoord	+ projector.getFocusdown();
				break;
				case 5:
					xmin = projector.xCoord -1;
					xmax = projector.xCoord + projector.getForceField_strength();
					zmax = projector.zCoord + projector.getFocusleft();
					zmin = projector.zCoord - projector.getFocusright();
					ymin = projector.yCoord - projector.getFocusup();
					ymax = projector.yCoord	+ projector.getFocusdown();
				break;
				}
			}

			List<EntityLiving> LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xmin, ymin, zmin, xmax, ymax, zmax));

				for (int i = 0; i < LivingEntitylist.size(); i++) {
					EntityLiving entityLiving = LivingEntitylist.get(i);

			if(Target.equals("human") && !(entityLiving instanceof EntityPlayer))
			{continue;}

			if(Target.equals("mobs") && !(entityLiving instanceof EntityMob) && !(entityLiving instanceof EntitySlime) && !(entityLiving instanceof EntityGhast))
			{continue;}

			if (projector.getProjektor_Typ()==5  && entityLiving.getDistance(projector.xCoord, projector.yCoord,
					projector.zCoord) > projector.getForceField_distance()) {
				continue;
			}

			if((projector.getProjektor_Typ()==4 || projector.getProjektor_Typ()==5) && projector.isOptionFieldcut() && entityLiving.posY  < projector.yCoord)
			{
			   continue;
			}

			if(projector.getProjektor_Typ()==7 && projector.isOptionFieldcut())
			{
				switch(projector.getSide())
				{
				case 0:
					if(entityLiving.posY > projector.yCoord+2 )
					{
					continue;}
				break;
				case 1:
					if(entityLiving.posY <  projector.yCoord )
					{
					continue;}
				break;
				case 2:
					if(entityLiving.posZ > projector.zCoord +1 )
					{
					continue;}
				break;
				case 3:
					if(entityLiving.posZ < projector.zCoord )
					{
					continue;}
				break;
				case 4:
					if(entityLiving.posX > projector.xCoord+1 )
					{
					continue;}
				break;
				case 5:
					if(entityLiving.posX < projector.xCoord )
					{
					continue;}
				break;
				}
			}

        		if (projector.getLinkPower() > ModularForceFieldSystem.DefenseStationFPpeerAttack) {
					if(Target.equals("mobs"))
					{
						Linkgrid.getWorldMap(world).getCapacitor().get(projector.getLinkCapacitor_ID())
						.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(projector
							.getLinkCapacitor_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

					
						
					entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.MobDefenseDamage);
					continue;
					}

					if(Target.equals("human"))
					{
						boolean killswitch = false;

						if(projector.getaccesstyp()==2)
						{
							TileEntityCapacitor Generator = Linkgrid.getWorldMap(world).getCapacitor().get(projector.getLinkCapacitor_ID());
							if(Generator != null)
							{
							TileEntitySecurityStation SecurityStation = Linkgrid.getWorldMap(world).getSecStation().get(Generator.getSecStation_ID());

							if(SecurityStation != null)
							{
							killswitch = !SecurityStation.isAccessGranted(((EntityPlayer)entityLiving).username,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
							}
							}
							}
						if(projector.getaccesstyp()==3)
						{
							TileEntitySecurityStation SecurityStation = Linkgrid.getWorldMap(world).getSecStation().get(projector.getSecStation_ID());
							if(SecurityStation != null)
							{
							killswitch = !SecurityStation.isAccessGranted(((EntityPlayer)entityLiving).username,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS);
							}
						}

						if (killswitch)
							{
							Linkgrid.getWorldMap(world).getCapacitor().get(projector.getLinkCapacitor_ID())
							.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(projector
								.getLinkCapacitor_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

							Functions.ChattoPlayer((EntityPlayer)entityLiving,"[Defence Area Station] !!! you  are in a restricted area !!! ");
							entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.DefenseStationDamage);
					
							continue;
							}

					continue;
					}
					}
				}
			}
		}

		if(Typ.equals("areadefense") && Entity instanceof TileEntityAreaDefenseStation)
		{
	      TileEntityAreaDefenseStation  DefenseStation  =  (TileEntityAreaDefenseStation)Entity;

			int xmin = DefenseStation.xCoord - DefenseStation.getDistance();
			int xmax = DefenseStation.xCoord + DefenseStation.getDistance();
			int ymin = DefenseStation.yCoord - DefenseStation.getDistance(); if(ymin<0){ymin = 0;}
	        int ymax = DefenseStation.yCoord + DefenseStation.getDistance(); if(ymax>255){ymax = 255;}
			int zmin = DefenseStation.zCoord - DefenseStation.getDistance();
			int zmax = DefenseStation.zCoord + DefenseStation.getDistance();

			List<EntityLiving> LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xmin, ymin, zmin, xmax, ymax, zmax));

			for (int i = 0; i < LivingEntitylist.size(); i++) {
				EntityLiving entityLiving = LivingEntitylist.get(i);

		if(Target.equals("human") && !(entityLiving instanceof EntityPlayer))
		{continue;}

		if(Target.equals("mobs") && !(entityLiving instanceof EntityMob) && !(entityLiving instanceof EntitySlime) && !(entityLiving instanceof EntityGhast))
		{continue;}

					 if(DefenseStation.getLinkPower() > (ModularForceFieldSystem.DefenseStationFPpeerAttack))
					 {
							if(Target.equals("mobs"))
							{
								Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation.getlinkCapacitors_ID())
								.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation
									.getlinkCapacitors_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

							entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.MobDefenseDamage);
							continue;
							}

							if(Target.equals("human") && Linkgrid.getWorldMap(world).getSecStation().get(DefenseStation.getSecStation_ID()) != null)
							{
								if(!SecurityHelper.isAccessGranted(DefenseStation, (EntityPlayer)entityLiving, world,ModularForceFieldSystem.PERSONALID_LIMITEDACCESS))
								{
									Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation.getlinkCapacitors_ID())
									.setForcePower(Linkgrid.getWorldMap(world).getCapacitor().get(DefenseStation
										.getlinkCapacitors_ID()).getForcePower() - (ModularForceFieldSystem.DefenseStationFPpeerAttack));

									Functions.ChattoPlayer((EntityPlayer)entityLiving,"[Defence Area Station] !!! you  are in a restricted area !!! ");
									entityLiving.attackEntityFrom(DamageSource.generic,ModularForceFieldSystem.DefenseStationDamage);
									continue;
									}

							continue;
							}
					}
			}
		}
	}

	//-----------------------------------CheckInnerSpace Function------------------------------------

	public static boolean CheckInnerSpace (int x, int y , int z, TileEntityProjector tileEntityProjector,World world, String Option)
	{
		Map<Integer, TileEntityProjector> InnerMap = null;

		if(Option.equalsIgnoreCase("jammer"))
		{
			InnerMap = Linkgrid.getWorldMap(world).getJammer();
		}

		if(Option.equalsIgnoreCase("fieldfuser"))
		{
			InnerMap = Linkgrid.getWorldMap(world).getFieldFusion();
		}

		for (TileEntityProjector tileentity : InnerMap.values()) {
			int xmin=0;int xmax=0;int ymin=0;int ymax=0;int zmin=0;int zmax=0;

			boolean logicswitch= false;

			if(Option.equalsIgnoreCase("jammer"))
			{
				logicswitch = tileentity.getLinkCapacitor_ID() != tileEntityProjector.getLinkCapacitor_ID();
			}

			if(Option.equalsIgnoreCase("fieldfuser"))
			{
				logicswitch = tileentity.getLinkCapacitor_ID() == tileEntityProjector.getLinkCapacitor_ID() &&
					          tileentity.getProjektor_ID() != tileEntityProjector.getProjektor_ID();
			}

			if (logicswitch && tileentity.isActive()) {
				switch(tileentity.getProjektor_Typ())
				{
				case 4:

					 xmin = tileentity.xCoord - tileentity.getForceField_distance();
					 xmax = tileentity.xCoord + tileentity.getForceField_distance();
					 ymin = tileentity.yCoord - tileentity.getForceField_distance();if(ymin<0){ymin = 0;}
					 ymax = tileentity.yCoord + tileentity.getForceField_distance(); if(ymax>255){ymax = 255;}
					 zmin = tileentity.zCoord - tileentity.getForceField_distance();
					 zmax = tileentity.zCoord + tileentity.getForceField_distance();

					if(tileentity.isOptionFieldcut())
					{
						ymin = tileentity.yCoord;
					}

					if(xmax >= x && x >= xmin && ymax >= y && y >= ymin && zmax >= z && z >= zmin)
					{
						if(tileentity.isOptionFieldcut() && (tileentity.yCoord < y))
						{
							return false;
						}

						return true;
					}

				break;
				case 7:

					switch(tileentity.getSide())
					{
					case 0:
						ymax = tileentity.yCoord +1;
						ymin = tileentity.yCoord - tileentity.getForceField_strength();
						xmax = tileentity.xCoord + tileentity.getFocusleft();
						xmin = tileentity.xCoord - tileentity.getFocusright();
						zmin = tileentity.zCoord - tileentity.getFocusup();
						zmax = tileentity.zCoord	+ tileentity.getFocusdown();
					break;
					case 1:
						ymin = tileentity.yCoord -1;
						ymax = tileentity.yCoord + tileentity.getForceField_strength();
						xmin = tileentity.xCoord - tileentity.getFocusleft();
						xmax = tileentity.xCoord + tileentity.getFocusright();
						zmin = tileentity.zCoord - tileentity.getFocusup();
						zmax = tileentity.zCoord	+ tileentity.getFocusdown();
					break;
					case 2:
						zmax = tileentity.zCoord +1;
						zmin = tileentity.zCoord - tileentity.getForceField_strength();
						xmax = tileentity.xCoord + tileentity.getFocusleft();
						xmin = tileentity.xCoord - tileentity.getFocusright();
						ymax = tileentity.yCoord + tileentity.getFocusup();
						ymin = tileentity.yCoord	- tileentity.getFocusdown();
					break;
					case 3:
						zmin = tileentity.zCoord -1;
						zmax = tileentity.zCoord + tileentity.getForceField_strength();
						xmax = tileentity.xCoord + tileentity.getFocusleft();
						xmin = tileentity.xCoord - tileentity.getFocusright();
						ymax = tileentity.yCoord + tileentity.getFocusup();
						ymin = tileentity.yCoord	- tileentity.getFocusdown();
					break;
					case 4:
						xmax = tileentity.xCoord +1;
						xmin = tileentity.xCoord - tileentity.getForceField_strength();
						zmin = tileentity.zCoord - tileentity.getFocusleft();
						zmax = tileentity.zCoord + tileentity.getFocusright();
						ymin = tileentity.yCoord - tileentity.getFocusup();
						ymax = tileentity.yCoord	+ tileentity.getFocusdown();
					break;
					case 5:
						xmin = tileentity.xCoord -1;
						xmax = tileentity.xCoord + tileentity.getForceField_strength();
						zmax = tileentity.zCoord + tileentity.getFocusleft();
						zmin = tileentity.zCoord - tileentity.getFocusright();
						ymin = tileentity.yCoord - tileentity.getFocusup();
						ymax = tileentity.yCoord	+ tileentity.getFocusdown();
					break;
					}

					int realxmax = Math.max(xmax, xmin);
					int realxmin = Math.min(xmax, xmin);

					int realymax = Math.max(ymax, ymin);
					int realymin = Math.min(ymax, ymin);

					int realzmax = Math.max(zmax, zmin);
					int realzmin = Math.min(zmax, zmin);

					if(realxmax > x && x > realxmin && realymax > y && y > realymin && realzmax > z && z > realzmin)
					{
						return true;
					}

				break;
				case 5:

					int dx = tileentity.xCoord - x;
					int dy = tileentity.yCoord - y;
					int dz = tileentity.zCoord - z;

					double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

					if (dist <= tileentity.getForceField_distance()) {
						if(tileentity.isOptionFieldcut() && (tileentity.yCoord < dy))
						{
							return false;
						}

					      return true;
						}

				break;
				case 3:

						int xmin2=tileentity.xCoord;
					int xmax2=tileentity.xCoord;
					int ymin2=tileentity.yCoord;
					int ymax2=tileentity.yCoord;
					int zmin2=tileentity.zCoord;
					int zmax2=tileentity.zCoord;

                          switch(tileentity.getSide())
                          {
                          case 0:
                          case 1:

      						 xmin2 = tileentity.xCoord - tileentity.getForceField_distance();
    						 xmax2 = tileentity.xCoord + tileentity.getForceField_distance();
    						 ymin2 = tileentity.yCoord - tileentity.getForceField_strength();;if(ymin2<0){ymin2 = 0;}
    						 ymax2 = tileentity.yCoord + tileentity.getForceField_strength();; if(ymax2>255){ymax2 = 255;}
    						 zmin2 = tileentity.zCoord - tileentity.getForceField_distance();
    						 zmax2 = tileentity.zCoord + tileentity.getForceField_distance();

                          break;
                          case 2:
                          case 3:

        					 xmin2 = tileentity.xCoord - tileentity.getForceField_distance();
        					 xmax2 = tileentity.xCoord + tileentity.getForceField_distance();
        					 ymin2 = tileentity.yCoord - tileentity.getForceField_distance();if(ymin2<0){ymin2 = 0;}
        					 ymax2 = tileentity.yCoord + tileentity.getForceField_distance(); if(ymax2>255){ymax2 = 255;}
        					 zmin2 = tileentity.zCoord - tileentity.getForceField_strength();
        					 zmax2 = tileentity.zCoord + tileentity.getForceField_strength();

                           break;
                          case 4:
                          case 5:

         					 xmin2 = tileentity.xCoord - tileentity.getForceField_strength();
         					 xmax2 = tileentity.xCoord + tileentity.getForceField_strength();
         					 ymin2 = tileentity.yCoord - tileentity.getForceField_distance();if(ymin2<0){ymin2 = 0;}
         					 ymax2 = tileentity.yCoord + tileentity.getForceField_distance(); if(ymax2>255){ymax2 = 255;}
         					 zmin2 = tileentity.zCoord - tileentity.getForceField_distance();
         					 zmax2 = tileentity.zCoord + tileentity.getForceField_distance();

                          break;
                          }

  						if(tileentity.isOptionFieldcut())
						{
                            switch(tileentity.getSide())
                            {
                            case 1:
                            	ymin2 = tileentity.yCoord;
                            break;
                            case 0:
                            	ymax2 = tileentity.yCoord;
                            break;
                            case 3:
                            	 zmin2 = tileentity.zCoord;
                            break;
                            case 2:
                            	zmax2 = tileentity.zCoord;
                            break;
                            case 5:
                            	xmin2 =tileentity.xCoord;
                            break;
                            case 4:
                            	xmax2 =tileentity.xCoord;
                            break;
                            }
						}

						if(xmax2 >= x && x >= xmin2 && ymax2>= y && y >= ymin2 && zmax2 >= z && z >= zmin2)
						{
							return true;
						}

				break;
				default:
				return false;
				}
			}
		}

		return false;
	}
}
