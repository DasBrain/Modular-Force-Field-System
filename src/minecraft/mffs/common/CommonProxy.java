package mffs.common;

import java.lang.reflect.Constructor;

import mffs.client.gui.GuiManuelScreen;
import mffs.common.container.ContainerDummy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public void registerRenderInformation()
	{
	}

	public void registerTileEntitySpecialRenderer()
	{
	}

	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID != 0)
		{
			return new GuiManuelScreen(new ContainerDummy());
		}

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
		{
			return null;
		}

		MFFSMachines machType = MFFSMachines.fromTE(te);
		try
		{
			Constructor mkGui = Class.forName("mffs.client.gui." + machType.Gui).getConstructor(new Class[] { EntityPlayer.class, machType.clazz });
			return mkGui.newInstance(new Object[] { player, machType.clazz.cast(te) });
		}
		catch (Exception ex)
		{
			System.out.println("Failed to open GUI: " + ex.getLocalizedMessage());
		}

		return null;
	}

	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
		{
			return null;
		}

		MFFSMachines machType = MFFSMachines.fromTE(te);
		try
		{
			Constructor mkGui = machType.Container.getConstructor(new Class[] { EntityPlayer.class, machType.clazz });
			return mkGui.newInstance(new Object[] { player, machType.clazz.cast(te) });
		}
		catch (Exception ex)
		{
			System.out.println("Failed to open GUI: " + ex.getLocalizedMessage());
		}

		return null;
	}

	public World getClientWorld()
	{
		return null;
	}

	public boolean isClient()
	{
		return false;
	}
}