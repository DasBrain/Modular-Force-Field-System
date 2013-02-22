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

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.TileEntity;

import org.lwjgl.opengl.GL11;

import chb.mods.mffs.common.TileEntityCapacitor;
import chb.mods.mffs.common.TileEntityProjector;
import chb.mods.mffs.common.TileEntityConverter;

public class GuiGraphicButton extends GuiButton

{
	private TileEntity tileEntity;
	private int typ;

    public GuiGraphicButton(int par1, int par2, int par3, TileEntity tileEntity,int typ)
    {
        super(par1, par2, par3, 16, 16, "");
        this.tileEntity = tileEntity;
        this.typ = typ;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/chb/mods/mffs/sprites/items.png"));

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if((tileEntity instanceof TileEntityConverter) && typ == 1)
            {
                switch(((TileEntityConverter)tileEntity).getswitchtyp()) 
                {
                case 0:
                	this.drawTexturedModalRect(this.xPosition, this.yPosition, 80, 80, this.width, this.height);
                break;
                case 1:
                	this.drawTexturedModalRect(this.xPosition, this.yPosition, 64, 80, this.width, this.height);
                break;
                }
                }
            
            
            if(tileEntity instanceof TileEntityProjector)
            {
                if(typ == 1)
                {
                switch(((TileEntityProjector)tileEntity).getaccesstyp())
                {
                case 0:
                	this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 80, this.width, this.height);
                break;
                case 1:
                	this.drawTexturedModalRect(this.xPosition, this.yPosition, 16, 80, this.width, this.height);
                break;
                case 2:
                	this.drawTexturedModalRect(this.xPosition, this.yPosition, 48, 80, this.width, this.height);
                break;
                case 3:
                	this.drawTexturedModalRect(this.xPosition, this.yPosition, 32, 80, this.width, this.height);
                break;
                }
                }
                if(typ == 2)
                {
                    switch(((TileEntityProjector)tileEntity).getswitchtyp())
                    {
                    case 0:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 80, 80, this.width, this.height);
                    break;
                    case 1:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 64, 80, this.width, this.height);
                    break;
                    }
                }
            }

            if(tileEntity instanceof TileEntityCapacitor)
            {
                if(typ == 1)
                {
                    switch(((TileEntityCapacitor)tileEntity).getswitchtyp())
                    {
                    case 0:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 80, 80, this.width, this.height);
                    break;
                    case 1:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 64, 80, this.width, this.height);
                    break;
                    }
                }

                if(typ == 2)
                {
                    switch(((TileEntityCapacitor)tileEntity).getPowerlinkmode())
                    {
                    case 0:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 96, 80, this.width, this.height);
                    break;
                    case 1:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 112, 80, this.width, this.height);
                    break;
                    case 2:
                    	this.drawTexturedModalRect(this.xPosition, this.yPosition, 128, 80, this.width, this.height);
                    break;
                    }
                }
            }
        }
    }
}
