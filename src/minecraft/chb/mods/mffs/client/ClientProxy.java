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

package chb.mods.mffs.client;

import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import chb.mods.mffs.common.CommonProxy;
import chb.mods.mffs.common.TileEntityCapacitor;
import chb.mods.mffs.common.TileEntityExtractor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override

	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/DefenceStation.png");
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/projector.png");
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/SecurtyStation.png");
    	MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Capacitor.png");
		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/items.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/blocks.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/forciciumore.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Extractor.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/forciciumblock.png");
 		MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Converter.png");
	}

	@Override
     public void registerTileEntitySpecialRenderer() {
     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCapacitor.class, new TECapacitorRenderer());
     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtractor.class, new TEExtractorRenderer());

     RenderingRegistry.registerBlockHandler(new ForceFieldRenderer());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public boolean isClient()
	{
		return true;
	}
}