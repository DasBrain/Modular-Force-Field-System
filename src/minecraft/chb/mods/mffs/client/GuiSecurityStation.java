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

import chb.mods.mffs.common.ContainerSecurityStation;
import chb.mods.mffs.common.TileEntitySecurityStation;

public class GuiSecurityStation extends GuiContainer {
	private TileEntitySecurityStation tileEntity;

	public GuiSecurityStation(EntityPlayer player,
			TileEntitySecurityStation tileentity) {
		super(new ContainerSecurityStation(player, tileentity));
		this.tileEntity = tileentity;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int textur = mc.renderEngine
				.getTexture("/chb/mods/mffs/sprites/GuiSecstation.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
	}
	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("MFFS Security Station. V2", 5, 5, 0x404040);
		fontRenderer.drawString("Master ID-Card", 15,45, 0x404040);


		fontRenderer.drawString("Full Access", 115, 45, 0x404040);
	}
}
