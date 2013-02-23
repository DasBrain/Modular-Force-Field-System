package chb.mods.mffs.common;

import chb.mods.mffs.client.gui.GuiManuelScreen;
import chb.mods.mffs.common.container.ContainerDummy;
import cpw.mods.fml.common.network.IGuiHandler;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy
  implements IGuiHandler
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

    MFFSMaschines machType = MFFSMaschines.fromTE(te);
    try
    {
      Constructor mkGui = Class.forName("chb.mods.mffs.client.gui." + machType.Gui).getConstructor(new Class[] { EntityPlayer.class, machType.clazz });
      return mkGui.newInstance(new Object[] { player, machType.clazz.cast(te) });
    }
    catch (Exception ex)
    {
      System.out.println(ex.getLocalizedMessage());
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

    MFFSMaschines machType = MFFSMaschines.fromTE(te);
    try
    {
      Constructor mkGui = machType.Container.getConstructor(new Class[] { EntityPlayer.class, machType.clazz });
      return mkGui.newInstance(new Object[] { player, machType.clazz.cast(te) });
    }
    catch (Exception ex) {
      System.out.println(ex.getLocalizedMessage());
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