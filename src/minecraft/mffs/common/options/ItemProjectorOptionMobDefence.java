package mffs.common.options;

import java.util.List;

import mffs.api.PointXYZ;
import mffs.common.IModularProjector;
import mffs.common.MFFSDamageSource;
import mffs.common.modules.ItemProjectorModuleSphere;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemProjectorOptionMobDefence extends ItemProjectorOptionBase
{
	public ItemProjectorOptionMobDefence(int i)
	{
		super(i);
		setIconIndex(40);
	}

	public static void ProjectorNPCDefence(TileEntityProjector projector, World world)
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

				if (((entityLiving instanceof EntityMob)) || ((entityLiving instanceof EntitySlime)) || ((entityLiving instanceof EntityGhast)))
				{
					if ((!(projector.get_type() instanceof ItemProjectorModuleSphere)) || (PointXYZ.distance(new PointXYZ((int) entityLiving.posX, (int) entityLiving.posY, (int) entityLiving.posZ, world), projector.getMaschinePoint()) <= projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4))
					{
						if (projector.getLinkPower() < 10000)
						{
							break;
						}
						if (projector.consumePower(10000, true))
						{
							entityLiving.attackEntityFrom(MFFSDamageSource.fieldDefense, 10);
							projector.consumePower(10000, false);
						}
					}
				}
			}
		}
	}
}