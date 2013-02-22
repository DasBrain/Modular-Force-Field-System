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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;

public class Versioninfo {
	      private static boolean create;
		  private static String Major;
		  private static String Minor;
		  private static String Revision;
		  private static String betabuild;

		  public static final String version() {
			    if (!create) {
			    create();
			    }
			    return Major+"."+Minor+"."+Revision+"."+betabuild;
			  }

		  private static void create() {
		    InputStream inputstream = Versioninfo.class.getClassLoader().getResourceAsStream("versioninfo");
		    Properties properties = new Properties();

		    if (inputstream != null) {
		      try {
		        properties.load(inputstream);
		        Major = properties.getProperty("mffs.version.major.number");
		        Minor = properties.getProperty("mffs.version.minor.number");
		        Revision = properties.getProperty("mffs.version.revision.number");
		        betabuild = properties.getProperty("mffs.version.betabuild.number");
		      } catch (IOException ex) {
		        FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, " Modual ForceField System V2 broken Installation detected!", ex);
		        throw new RuntimeException(ex);
		      }
		    }
		    create = true;
		  }
		}
