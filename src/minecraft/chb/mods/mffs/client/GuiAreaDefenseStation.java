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

import chb.mods.mffs.common.ContainerAreaDefenseStation;
import chb.mods.mffs.common.TileEntityAreaDefenseStation;

public class GuiAreaDefenseStation extends GuiContainer {
	private TileEntityAreaDefenseStation DefenceStation;

	public GuiAreaDefenseStation(EntityPlayer player,
			TileEntityAreaDefenseStation tileentity) {
		super(new ContainerAreaDefenseStation(player, tileentity));
		DefenceStation = tileentity;
	}
@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int textur = mc.renderEngine
				.getTexture("/chb/mods/mffs/sprites/GuiDefStation.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(textur);
		int w = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
		int i1 = (79 * DefenceStation.getLinkPower()) / DefenceStation.getMaxlinkPower();
		drawTexturedModalRect(w + 8, k + 71, 176, 0, i1 + 1, 79);
	}
@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("MFFS Defence Station", 5, 5, 0x404040);
		fontRenderer.drawString((new StringBuilder()).append("FP: ").append(DefenceStation.getLinkPower()).toString(), 30, 50, 0x404040);

		fontRenderer.drawString("Typ", 120, 30, 0x404040);
		fontRenderer.drawString("distance", 110, 65, 0x404040);

		if(DefenceStation.islinkSecStation())
		{
			fontRenderer.drawString("connected", 30, 20, 0x404040);
		}
	}
}
