package mffs.common.options;

import java.util.List;

import mffs.api.PointXYZ;
import mffs.common.IModularProjector;
import mffs.common.Linkgrid;
import mffs.common.MFFSDamageSource;
import mffs.common.SecurityRight;
import mffs.common.modules.ItemProjectorModuleSphere;
import mffs.common.tileentity.TileEntityAdvSecurityStation;
import mffs.common.tileentity.TileEntityCapacitor;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemProjectorOptionDefenseStation extends ItemProjectorOptionBase
{
	public ItemProjectorOptionDefenseStation(int i)
	{
		super(i);
		setIconIndex(39);
	}

	public static void ProjectorPlayerDefence(TileEntityProjector projector, World world)
	{
		if (projector.isActive())
		{
			int fieldxmin = projector.xCoord;
			int fieldxmax = projector.xCoord;
			int fieldymin = projector.yCoord;
			int fieldymax = projector.yCoord;
			int fieldzmin = projector.zCoord;
			int fieldzmax = projector.zCoord;

			for (PointXYZ png : projector.getfield_queue())
			{
				fieldxmax = Math.max(fieldxmax, png.X);
				fieldxmin = Math.min(fieldxmin, png.X);
				fieldymax = Math.max(fieldymax, png.Y);
				fieldymin = Math.min(fieldymin, png.Y);
				fieldzmax = Math.max(fieldzmax, png.Z);
				fieldzmin = Math.min(fieldzmin, png.Z);
			}

			List LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(fieldxmin, fieldymin, fieldzmin, fieldxmax, fieldymax, fieldzmax));

			for (int i = 0; i < LivingEntitylist.size(); i++)
			{
				EntityLiving entityLiving = (EntityLiving) LivingEntitylist.get(i);

				if ((entityLiving instanceof EntityPlayer))
				{
					if ((!(projector.get_type() instanceof ItemProjectorModuleSphere)) || (PointXYZ.distance(new PointXYZ((int) entityLiving.posX, (int) entityLiving.posY, (int) entityLiving.posZ, world), projector.getMaschinePoint()) <= projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4))
					{
						if (projector.getLinkPower() < 10000)
						{
							break;
						}
						if (projector.getLinkPower() > 10000)
						{
							boolean killswitch = false;

							if (projector.getaccesstyp() == 2)
							{
								TileEntityCapacitor cap = (TileEntityCapacitor) Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(projector.getPowerSourceID()));
								if (cap != null)
								{
									TileEntityAdvSecurityStation SecurityStation = cap.getLinkedSecurityStation();

									if (SecurityStation != null)
									{
										killswitch = !SecurityStation.isAccessGranted(((EntityPlayer) entityLiving).username, SecurityRight.SR);
									}
								}
							}
							if (projector.getaccesstyp() == 3)
							{
								TileEntityAdvSecurityStation SecurityStation = projector.getLinkedSecurityStation();
								if (SecurityStation != null)
								{
									killswitch = !SecurityStation.isAccessGranted(((EntityPlayer) entityLiving).username, SecurityRight.SR);
								}
							}

							if (killswitch)
							{
								if (projector.consumePower(10000, true))
								{
									((EntityPlayer) entityLiving).addChatMessage("!!! [Area Defence] leave or die !!!");
									((EntityPlayer) entityLiving).attackEntityFrom(MFFSDamageSource.fieldDefense, 10);
									projector.consumePower(10000, false);
								}
							}
						}
					}
				}
			}
		}
	}
}