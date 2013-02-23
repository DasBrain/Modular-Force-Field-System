package mffs.client.gui;

import mffs.client.GraphicButton;
import mffs.common.ModularForceFieldSystem;
import mffs.common.container.ContainerForceEnergyExtractor;
import mffs.common.tileentity.TileEntityExtractor;
import mffs.network.client.NetworkHandlerClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class GuiExtractor extends GuiContainer
{
	private TileEntityExtractor Extractor;
	private boolean editMode = false;

	public GuiExtractor(EntityPlayer player, TileEntityExtractor tileentity)
	{
		super(new ContainerForceEnergyExtractor(player, tileentity));
		this.Extractor = tileentity;
		this.xSize = 176;
		this.ySize = 186;
	}

	protected void keyTyped(char c, int i)
	{
		if ((i != 1) && (this.editMode))
		{
			if (c == '\r')
			{
				this.editMode = false;
				return;
			}

			if (i == 14)
			{
				NetworkHandlerClient.fireTileEntityEvent(this.Extractor, 12, "");
			}
			if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
				NetworkHandlerClient.fireTileEntityEvent(this.Extractor, 11, String.valueOf(c));
		}
		else
		{
			super.keyTyped(c, i);
		}
	}

	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);

		int xMin = (this.width - this.xSize) / 2;
		int yMin = (this.height - this.ySize) / 2;

		int x = i - xMin;
		int y = j - yMin;

		if (this.editMode)
		{
			this.editMode = false;
		}
		else if ((x >= 10) && (y >= 5) && (x <= 141) && (y <= 19))
		{
			NetworkHandlerClient.fireTileEntityEvent(this.Extractor, 10, "null");
			this.editMode = true;
		}
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		NetworkHandlerClient.fireTileEntityEvent(this.Extractor, guibutton.id, "");
	}

	public void initGui()
	{
		this.controlList.add(new GraphicButton(0, this.width / 2 + 60, this.height / 2 - 88, this.Extractor, 0));
		super.initGui();
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiExtractor.png");

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(textur);
		int w = (this.width - this.xSize) / 2;
		int k = (this.height - this.ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);

		int Workpowerslider = 79 * this.Extractor.getWorkdone() / 100;
		drawTexturedModalRect(w + 49, k + 89, 176, 0, Workpowerslider, 6);

		int WorkCylce = 32 * this.Extractor.getWorkCylce() / ModularForceFieldSystem.ForceciumWorkCylce;

		drawTexturedModalRect(w + 73, k + 50, 179, 81, WorkCylce, 32);

		int ForceEnergy = 24 * this.Extractor.getForceEnergybuffer() / this.Extractor.getMaxForceEnergyBuffer();
		drawTexturedModalRect(w + 137, k + 60, 219, 80, 32, ForceEnergy);
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString("Force Energy", 5, 25, 4210752);
		this.fontRenderer.drawString("Upgrades", 10, 50, 4210752);
		this.fontRenderer.drawString(this.Extractor.getDeviceName(), 8, 9, 4210752);
		this.fontRenderer.drawString("Extractor", 5, 35, 4210752);
		this.fontRenderer.drawString(String.valueOf(this.Extractor.getForceEnergybuffer() / 1000).concat("k"), 140, 89, 4210752);

		this.fontRenderer.drawString(String.valueOf(this.Extractor.getWorkdone()).concat("%"), 23, 89, 4210752);
	}
}