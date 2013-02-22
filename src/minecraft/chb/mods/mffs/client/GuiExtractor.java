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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;

import org.lwjgl.opengl.GL11;

import chb.mods.mffs.common.ContainerForceEnergyExtractor;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.TileEntityExtractor;

public class GuiExtractor extends GuiContainer {
	private TileEntityExtractor tileEntity;

	public GuiExtractor(EntityPlayer player,
			TileEntityExtractor tileentity) {
		super(new ContainerForceEnergyExtractor(player, tileentity));
		this.tileEntity = tileentity;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int textur = mc.renderEngine
				.getTexture("/chb/mods/mffs/sprites/GuiExtractor.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);

		int Workpowerslider = (79 * tileEntity.getWorkdone() / 100);
		drawTexturedModalRect(w + 49, k + 69, 176, 0, Workpowerslider, 6);
		
		int WorkCylce;
		
		if(tileEntity.getWorkCylce()>ModularForceFieldSystem.ForceciumWorkCylce)
		{
			 WorkCylce =  (32 * tileEntity.getWorkCylce()) / ModularForceFieldSystem.ForceciumBlockWorkCylce;
		}else{
			 WorkCylce =  (32 * tileEntity.getWorkCylce()) / ModularForceFieldSystem.ForceciumWorkCylce;
		}
		
		drawTexturedModalRect(w + 73,k + 30, 179,81,WorkCylce,32);

		int ForceEnergy  = (24 * tileEntity.getForceEnergybuffer() / tileEntity.getMaxForceEnergyBuffer());
		drawTexturedModalRect(w + 137,k + 40,219,80,32,ForceEnergy);
	}
	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Force Energy", 5, 5, 0x404040);

		fontRenderer.drawString("Upgrades", 10, 30, 0x404040);

		fontRenderer.drawString("Extractor", 5, 15, 0x404040);
		fontRenderer.drawString(String.valueOf(tileEntity.getForceEnergybuffer()/1000).concat("k"), 140, 69, 0x404040);

		fontRenderer.drawString(String.valueOf(tileEntity.getWorkdone()).concat("%"), 23, 69, 0x404040);
	}
}
