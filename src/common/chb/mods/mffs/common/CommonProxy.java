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
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import chb.mods.mffs.client.GuiAreaDefenseStation;
import chb.mods.mffs.client.GuiCapacitor;
import chb.mods.mffs.client.GuiExtractor;
import chb.mods.mffs.client.GuiProjector;
import chb.mods.mffs.client.GuiSecurityStation;
import chb.mods.mffs.client.GuiConverter;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	
	public static final int GUI_CAPACITOR = 1;
	public static final int GUI_PROJECTOR = 2;
	public static final int GUI_SECSTATION = 3;
	public static final int GUI_DEFSTATION = 4;
	public static final int GUI_EXTRACTOR = 5;
	public static final int GUI_CONVERTER = 6;
	

	public void registerRenderInformation()
{
}
	public void registerTileEntitySpecialRenderer()
{
}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null)
		{
			return null;
		}

		switch (ID) {
		case GUI_CAPACITOR:
			return new GuiCapacitor(player,
					te == null ? new TileEntityCapacitor()
							: ((TileEntityCapacitor) te));
		case GUI_PROJECTOR:
			return new GuiProjector(player,
					te == null ? new TileEntityProjector()
							: ((TileEntityProjector) te));
		case GUI_SECSTATION:
			return new GuiSecurityStation(player,
					te == null ? new TileEntitySecurityStation()
							: ((TileEntitySecurityStation) te));
		case GUI_DEFSTATION:
			return new GuiAreaDefenseStation(player,
					te == null ? new TileEntityAreaDefenseStation()
							: ((TileEntityAreaDefenseStation) te));
			
		case GUI_EXTRACTOR:
			return new GuiExtractor(player,
					te == null ? new TileEntityExtractor()
							: ((TileEntityExtractor) te));
			
		case GUI_CONVERTER:
			return new GuiConverter(player,
					te == null ? new TileEntityConverter()
							: ((TileEntityConverter) te));
		
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
 {			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te == null)
			{
				return null;
			}

			switch (ID) {
			case GUI_CAPACITOR:
				return new ContainerCapacitor(player,
						te == null ? new TileEntityCapacitor()
								: ((TileEntityCapacitor) te));
			case GUI_PROJECTOR:
				return new ContainerProjector(player,
						te == null ? new TileEntityProjector()
								: ((TileEntityProjector) te));
			case GUI_SECSTATION:
				return new ContainerSecurityStation(player,
						te == null ? new TileEntitySecurityStation()
								: ((TileEntitySecurityStation) te));

			case GUI_DEFSTATION:
					return new ContainerAreaDefenseStation(player,
							te == null ? new TileEntityAreaDefenseStation()
									: ((TileEntityAreaDefenseStation) te));
			case GUI_EXTRACTOR:
				return new ContainerForceEnergyExtractor(player,
						te == null ? new TileEntityExtractor()
								: ((TileEntityExtractor) te));
			case GUI_CONVERTER:
				return new ContainerConverter(player,
						te == null ? new TileEntityConverter()
								: ((TileEntityConverter) te));
			}
			return null;
		}
	}

	public World getClientWorld() {
		return null;
	}
	
	public boolean isClient()
	{
		return false;
	}
	
	
}
