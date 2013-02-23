package mffs.client;

import mffs.common.tileentity.TileEntityAreaDefenseStation;
import mffs.common.tileentity.TileEntityCapacitor;
import mffs.common.tileentity.TileEntityControlSystem;
import mffs.common.tileentity.TileEntityConverter;
import mffs.common.tileentity.TileEntityMachines;
import mffs.common.tileentity.TileEntityProjector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

public class GraphicButton extends GuiButton
{
	private TileEntity tileEntity;
	private int typ;

	public GraphicButton(int par1, int par2, int par3, TileEntity tileEntity, int typ)
	{
		super(par1, par2, par3, 16, 16, "");
		this.tileEntity = tileEntity;
		this.typ = typ;
	}

	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
			GL11.glBindTexture(3553, par1Minecraft.renderEngine.getTexture("/chb/mods/mffs/sprites/items.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (((this.tileEntity instanceof TileEntityMachines)) && (this.typ == 0))
			{
				drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityMachines) this.tileEntity).getSwitchModi() * 16, 112, this.width, this.height);
			}

			if ((this.tileEntity instanceof TileEntityConverter))
			{
				if (this.typ == 1)
				{
					drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityConverter) this.tileEntity).getIC_Output() * 16, 128, this.width, this.height);
				}
				if (this.typ == 2)
				{
					drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityConverter) this.tileEntity).getUE_Output() * 16, 128, this.width, this.height);
				}

			}

			if ((this.tileEntity instanceof TileEntityControlSystem))
			{
				if (((TileEntityControlSystem) this.tileEntity).getStackInSlot(1) != null)
				{
					if (this.typ == 1)
					{
						if (((TileEntityControlSystem) this.tileEntity).getRemoteActive())
						{
							drawTexturedModalRect(this.xPosition, this.yPosition, 176, 80, this.width, this.height);
						}
						if (!((TileEntityControlSystem) this.tileEntity).getRemoteActive())
							drawTexturedModalRect(this.xPosition, this.yPosition, 192, 80, this.width, this.height);
					}
					if ((this.typ == 2) && (((TileEntityControlSystem) this.tileEntity).getRemoteSwitchModi() > 0))
					{
						drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityControlSystem) this.tileEntity).getRemoteSwitchModi() * 16, 112, this.width, this.height);
					}
					if ((this.typ == 3) && (((TileEntityControlSystem) this.tileEntity).getRemoteSwitchModi() == 3))
					{
						if (((TileEntityControlSystem) this.tileEntity).getRemoteSwitchValue())
							drawTexturedModalRect(this.xPosition, this.yPosition, 208, 80, this.width, this.height);
						else
						{
							drawTexturedModalRect(this.xPosition, this.yPosition, 224, 80, this.width, this.height);
						}
					}
				}

			}

			if ((this.tileEntity instanceof TileEntityAreaDefenseStation))
			{
				if (this.typ == 1)
					drawTexturedModalRect(this.xPosition, this.yPosition, 176 + ((TileEntityAreaDefenseStation) this.tileEntity).getcontratyp() * 16, 80, this.width, this.height);
				if (this.typ == 2)
					drawTexturedModalRect(this.xPosition, this.yPosition, 64 + ((TileEntityAreaDefenseStation) this.tileEntity).getActionmode() * 16, 96, this.width, this.height);
				if (this.typ == 3)
				{
					drawTexturedModalRect(this.xPosition, this.yPosition, 160 + ((TileEntityAreaDefenseStation) this.tileEntity).getScanmode() * 16, 96, this.width, this.height);
				}
			}
			if ((this.tileEntity instanceof TileEntityCapacitor))
			{
				if (this.typ == 1)
				{
					drawTexturedModalRect(this.xPosition, this.yPosition, 96 + ((TileEntityCapacitor) this.tileEntity).getPowerlinkmode() * 16, 80, this.width, this.height);
				}
			}
			if ((this.tileEntity instanceof TileEntityProjector))
			{
				if (this.typ == 1)
					drawTexturedModalRect(this.xPosition, this.yPosition, 0 + ((TileEntityProjector) this.tileEntity).getaccesstyp() * 16, 80, this.width, this.height);
			}
		}
	}
}