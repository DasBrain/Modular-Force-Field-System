package mffs.common.multitool;

import java.util.List;

import mffs.api.PointXYZ;
import mffs.common.ForceFieldBlockStack;
import mffs.common.Functions;
import mffs.common.Linkgrid;
import mffs.common.ModularForceFieldSystem;
import mffs.common.SecurityHelper;
import mffs.common.SecurityRight;
import mffs.common.WorldMap;
import mffs.common.item.ItemCardPowerLink;
import mffs.common.tileentity.TileEntityCapacitor;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemFieldtransporter extends ItemMultitool
{
	public ItemFieldtransporter(int id)
	{
		super(id, 4);
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return false;
		}
		WorldMap.ForceFieldWorld wff = WorldMap.getForceFieldWorld(world);

		ForceFieldBlockStack ffworldmap = wff.getForceFieldStackMap(new PointXYZ(x, y, z, world));
		if (ffworldmap != null)
		{
			int Sec_Gen_ID = -1;
			int First_Gen_ID = ffworldmap.getGenratorID();
			int First_Pro_ID = ffworldmap.getProjectorID();

			TileEntityCapacitor generator = (TileEntityCapacitor) Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(First_Gen_ID));
			TileEntityProjector projector = (TileEntityProjector) Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(First_Pro_ID));

			if ((projector != null) && (generator != null))
			{
				if (projector.isActive())
				{
					boolean passtrue = false;

					switch (projector.getaccesstyp())
					{
						case 0:
							passtrue = false;

							String[] ops = ModularForceFieldSystem.Admin.split(";");
							for (int i = 0; i <= ops.length - 1; i++)
							{
								if (entityplayer.username.equalsIgnoreCase(ops[i]))
								{
									passtrue = true;
								}
							}
							List<Slot> slots = entityplayer.inventoryContainer.inventorySlots;
							for (Slot slot : slots)
							{
								ItemStack playerstack = slot.getStack();
								if ((playerstack != null) && ((playerstack.getItem() instanceof ItemDebugger)))
								{
									passtrue = true;
									break;
								}

							}

							break;
						case 1:
							passtrue = true;
							break;
						case 2:
							passtrue = SecurityHelper.isAccessGranted(generator, entityplayer, world, SecurityRight.FFB);
							break;
						case 3:
							passtrue = SecurityHelper.isAccessGranted(projector, entityplayer, world, SecurityRight.FFB);
					}

					if (passtrue)
					{
						int typ = 99;
						int ymodi = 0;

						int lm = MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

						int i1 = Math.round(entityplayer.rotationPitch);

						if (i1 >= 65)
							typ = 1;
						else if (i1 <= -65)
							typ = 0;
						else if (lm == 0)
							typ = 2;
						else if (lm == 1)
							typ = 5;
						else if (lm == 2)
							typ = 3;
						else if (lm == 3)
						{
							typ = 4;
						}

						int counter = 0;
						while (Sec_Gen_ID != 0)
						{
							Sec_Gen_ID = wff.isExistForceFieldStackMap(x, y, z, counter, typ, world);
							if (Sec_Gen_ID != 0)
							{
								counter++;
							}
						}

						if (First_Gen_ID != wff.isExistForceFieldStackMap(x, y, z, counter - 1, typ, world))
						{
							Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
							return false;
						}

						switch (typ)
						{
							case 0:
								y += counter;
								ymodi = -1;
								break;
							case 1:
								y -= counter;
								ymodi = 1;
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
						}

						Functions.ChattoPlayer(entityplayer, "[Field Security] Success: access granted");

						if ((counter >= 0) && (counter <= 5))
						{
							if (((world.getBlockMaterial(x, y, z).isLiquid()) || (world.isAirBlock(x, y, z))) && ((world.getBlockMaterial(x, y - ymodi, z).isLiquid()) || (world.isAirBlock(x, y - ymodi, z))))
							{
								if (y - ymodi <= 0)
								{
									Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: transmission into Void not allowed ");
								}
								else if (consumePower(stack, ModularForceFieldSystem.forcefieldtransportcost, true))
								{
									consumePower(stack, ModularForceFieldSystem.forcefieldtransportcost, false);
									entityplayer.setPositionAndUpdate(x + 0.5D, y - ymodi, z + 0.5D);

									Functions.ChattoPlayer(entityplayer, "[Field Security] Success: transmission complete");
								}
								else
								{
									Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: not enough FP please charge  ");
								}

							}
							else
							{
								Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: detected obstacle ");
							}
						}
						else
							Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: Field to Strong >= 5 Blocks");
					}
					else
					{
						Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
					}

				}

			}
			else if ((projector != null) && (projector.getStackInSlot(projector.getPowerlinkSlot()) != null) && (!(projector.getStackInSlot(projector.getPowerlinkSlot()).getItem() instanceof ItemCardPowerLink)))
			{
				Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: Projector Powersource not Support this activities");
			}
		}

		return true;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return super.onItemRightClick(itemstack, world, entityplayer);
	}
}