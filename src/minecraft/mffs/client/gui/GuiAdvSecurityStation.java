package mffs.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mffs.common.SecurityRight;
import mffs.common.container.ContainerAdvSecurityStation;
import mffs.common.item.ItemCardPersonalID;
import mffs.common.tileentity.TileEntityAdvSecurityStation;
import mffs.network.client.NetworkHandlerClient;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class GuiAdvSecurityStation extends GuiContainer
{
	private TileEntityAdvSecurityStation tileEntity;
	private SecurityRight hoverSR;
	private boolean editMode = false;

	public GuiAdvSecurityStation(EntityPlayer player, TileEntityAdvSecurityStation tileentity)
	{
		super(new ContainerAdvSecurityStation(player, tileentity));
		this.tileEntity = tileentity;
		this.xSize = 256;
		this.ySize = 216;
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
				NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 12, "");
			}
			if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
				NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 11, String.valueOf(c));
		}
		else
		{
			super.keyTyped(c, i);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiAdvSecstation.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(textur);
		int w = (this.width - this.xSize) / 2;
		int k = (this.height - this.ySize) / 2;
		drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);

		this.hoverSR = null;
		int scale = 18;
		int ct = 0;

		ItemStack modCard = this.tileEntity.getModCardStack();
		if ((modCard != null) && ((modCard.getItem() instanceof ItemCardPersonalID)))
		{
			List<String> srKeys = new ArrayList();
			srKeys.addAll(SecurityRight.rights.keySet());
			Collections.sort(srKeys);
			for (String srKey : srKeys)
			{
				SecurityRight sr = (SecurityRight) SecurityRight.rights.get(srKey);

				int x = ct % 7 * (scale + 2) + 18;
				int y = ct / 7 * (scale + 2) + 54;

				if (ItemCardPersonalID.hasRight(modCard, sr))
					drawSprite(this.guiLeft + x, this.guiTop + y, 0, 0, sr);
				else
				{
					drawSprite(this.guiLeft + x, this.guiTop + y, 0, scale, sr);
				}
				if ((mouseX >= x + this.guiLeft) && (mouseX <= x + this.guiLeft + scale) && (mouseY >= this.guiTop + y) && (mouseY <= this.guiTop + y + scale))
				{
					this.hoverSR = sr;
				}

				ct++;
			}
		}
	}

	private void drawSprite(int par1, int par2, int par3, int par4, SecurityRight sr)
	{
		int var5 = this.mc.renderEngine.getTexture(sr.texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var5);

		if (sr.texIndex <= 6)
			par3 += sr.texIndex * 18;
		else
			par3 += (sr.texIndex - 7) * 18;

		if (sr.texIndex > 6)
		{
			par4 += 36;
		}
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.addVertexWithUV(par1 + 0, par2 + 18, this.zLevel, (par3 + 0) * 0.007813F, (par4 + 18) * 0.007813F);
		var10.addVertexWithUV(par1 + 18, par2 + 18, this.zLevel, (par3 + 18) * 0.007813F, (par4 + 18) * 0.007813F);
		var10.addVertexWithUV(par1 + 18, par2 + 0, this.zLevel, (par3 + 18) * 0.007813F, (par4 + 0) * 0.007813F);
		var10.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * 0.007813F, (par4 + 0) * 0.007813F);
		var10.draw();
	}

	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);

		int xMin = (this.width - this.xSize) / 2;
		int yMin = (this.height - this.ySize) / 2;

		int x = i - xMin;
		int y = j - yMin;

		if ((x >= 12) && (y >= 103) && (x <= 27) && (y <= 118))
		{
			NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 101, "null");
		}

		if ((x >= 68) && (y >= 103) && (x <= 83) && (y <= 118))
		{
			NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 102, "null");
		}

		if (this.editMode)
		{
			this.editMode = false;
		}
		else if ((x >= 120) && (y >= 4) && (x <= 250) && (y <= 18))
		{
			NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 10, "null");
			this.editMode = true;
		}
		if (this.hoverSR != null)
			NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 100, this.hoverSR.rightKey);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString("MFFS Security Station:", 8, 8, 4210752);
		this.fontRenderer.drawString(this.tileEntity.getDeviceName(), 125, 8, 4210752);
		this.fontRenderer.drawString("Master", 200, 38, 4210752);
		this.fontRenderer.drawString("Rights Allocation", 52, 35, 4210752);
		this.fontRenderer.drawString("Copy->", 109, 106, 4210752);
		this.fontRenderer.drawString("validity", 31, 106, 4210752);

		if (this.hoverSR != null)
		{
			List list = new ArrayList();
			list.add(this.hoverSR.name);
			if (list.size() > 0)
			{
				GL11.glDisable(32826);
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(2896);
				GL11.glDisable(2929);

				int j2 = 0;
				for (int k2 = 0; k2 < list.size(); k2++)
				{
					int i3 = this.fontRenderer.getStringWidth((String) list.get(k2));
					if (i3 > j2)
					{
						j2 = i3;
					}
				}

				int l2 = mouseX - this.guiLeft + 12;
				int j3 = mouseY - this.guiTop - 12;
				int k3 = j2;
				int l3 = 8;
				if (list.size() > 1)
				{
					l3 += 2 + (list.size() - 1) * 10;
				}

				this.zLevel = 300.0F;
				itemRenderer.zLevel = 300.0F;
				int i4 = -267386864;
				drawGradientRect(l2 - 3, j3 - 4, l2 + k3 + 3, j3 - 3, i4, i4);
				drawGradientRect(l2 - 3, j3 + l3 + 3, l2 + k3 + 3, j3 + l3 + 4, i4, i4);
				drawGradientRect(l2 - 3, j3 - 3, l2 + k3 + 3, j3 + l3 + 3, i4, i4);
				drawGradientRect(l2 - 4, j3 - 3, l2 - 3, j3 + l3 + 3, i4, i4);
				drawGradientRect(l2 + k3 + 3, j3 - 3, l2 + k3 + 4, j3 + l3 + 3, i4, i4);
				int j4 = 1347420415;
				int k4 = (j4 & 0xFEFEFE) >> 1 | j4 & 0xFF000000;
				drawGradientRect(l2 - 3, j3 - 3 + 1, l2 - 3 + 1, j3 + l3 + 3 - 1, j4, k4);
				drawGradientRect(l2 + k3 + 2, j3 - 3 + 1, l2 + k3 + 3, j3 + l3 + 3 - 1, j4, k4);
				drawGradientRect(l2 - 3, j3 - 3, l2 + k3 + 3, j3 - 3 + 1, j4, j4);
				drawGradientRect(l2 - 3, j3 + l3 + 2, l2 + k3 + 3, j3 + l3 + 3, k4, k4);
				for (int l4 = 0; l4 < list.size(); l4++)
				{
					String s = (String) list.get(l4);
					if (l4 == 0)
						s = "§F" + s;
					else
					{
						s = "§7" + s;
					}
					this.fontRenderer.drawStringWithShadow(s, l2, j3, -1);
					if (l4 == 0)
					{
						j3 += 2;
					}
					j3 += 10;
				}

				this.zLevel = 0.0F;
				itemRenderer.zLevel = 0.0F;
				GL11.glEnable(2896);
				GL11.glEnable(2929);
			}
		}
	}
}