package chb.mods.mffs.common;

import cpw.mods.fml.common.FMLCommonHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Versioninfo
{
  public static String curentversion()
  {
    InputStream inputstream = Versioninfo.class.getClassLoader().getResourceAsStream("versioninfo");
    Properties properties = new Properties();

    if (inputstream != null) {
      try {
        properties.load(inputstream);
        String Major = properties.getProperty("mffs.version.major.number");
        String Minor = properties.getProperty("mffs.version.minor.number");
        String Revision = properties.getProperty("mffs.version.revision.number");
        String betabuild = properties.getProperty("mffs.version.betabuild.number");

        return Major + "." + Minor + "." + Revision + "." + betabuild;
      }
      catch (IOException ex) {
        FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, "[Modual ForceField System] cannot read local Version file!", ex);
      }
    }
    return "0.0.0.0";
  }

  public static String newestversion()
  {
    Properties properties = new Properties();
    try
    {
      URL versionFile = new URL("https://raw.github.com/Thunderdark/ModularForceFieldSystem/master/src/minecraft/versioninfo");
      InputStreamReader inputstream = new InputStreamReader(versionFile.openStream());

      if (inputstream != null)
      {
        properties.load(inputstream);

        String Major = properties.getProperty("mffs.version.major.number");
        String Minor = properties.getProperty("mffs.version.minor.number");
        String Revision = properties.getProperty("mffs.version.revision.number");
        String betabuild = properties.getProperty("mffs.version.betabuild.number");

        return Major + "." + Minor + "." + Revision + "." + betabuild;
      }
    }
    catch (Exception ex) {
      FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, "[Modual ForceField System] cannot read remote Version file!", ex);
    }

    return "0.0.0.0";
  }
}