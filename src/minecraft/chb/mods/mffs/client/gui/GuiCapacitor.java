package chb.mods.mffs.client.gui;

import chb.mods.mffs.client.GraphicButton;
import chb.mods.mffs.common.container.ContainerCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.network.client.NetworkHandlerClient;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiCapacitor extends GuiContainer
{
  private TileEntityCapacitor Core;
  private boolean editMode = false;

  public GuiCapacitor(EntityPlayer player, TileEntityCapacitor tileentity)
  {
    super(new ContainerCapacitor(player, tileentity));
    this.Core = tileentity;
    this.xSize = 176;
    this.ySize = 207;
  }

  protected void keyTyped(char c, int i)
  {
    if ((i != 1) && (this.editMode)) {
      if (c == '\r') {
        this.editMode = false;
        return;
      }

      if (i == 14) {
        NetworkHandlerClient.fireTileEntityEvent(this.Core, 12, "");
      }
      if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
        NetworkHandlerClient.fireTileEntityEvent(this.Core, 11, String.valueOf(c));
    }
    else {
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

    if (this.editMode) {
      this.editMode = false;
    } else if ((x >= 5) && (y >= 4) && (x <= 135) && (y <= 18)) {
      NetworkHandlerClient.fireTileEntityEvent(this.Core, 10, "null");
      this.editMode = true;
    }
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiCapacitor.png");

    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(textur);
    int w = (this.width - this.xSize) / 2;
    int k = (this.height - this.ySize) / 2;
    drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
    int i1 = 79 * this.Core.getPercentageStorageCapacity() / 100;
    drawTexturedModalRect(w + 8, k + 112, 176, 0, i1 + 1, 79);
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    this.fontRenderer.drawString("Force Energy Capacitor", 8, 25, 4210752);
    this.fontRenderer.drawString(this.Core.getDeviceName(), 8, 8, 4210752);
    this.fontRenderer.drawString("FE: " + String.valueOf(this.Core.getStorageAvailablePower()), 8, 100, 4210752);
    this.fontRenderer.drawString("Power Uplink: ", 8, 80, 4210752);

    this.fontRenderer.drawString("transmit range:", 8, 60, 4210752);
    this.fontRenderer.drawString(" " + this.Core.getTransmitRange(), 90, 60, 4210752);

    this.fontRenderer.drawString("linked device:", 8, 43, 4210752);
    this.fontRenderer.drawString(" " + this.Core.getLinketProjektor(), 90, 45, 4210752);
  }

  protected void actionPerformed(GuiButton guibutton)
  {
    NetworkHandlerClient.fireTileEntityEvent(this.Core, guibutton.id, "");
  }

  public void initGui() {
    this.controlList.add(new GraphicButton(0, this.width / 2 + 65, this.height / 2 - 100, this.Core, 0));
    this.controlList.add(new GraphicButton(1, this.width / 2 + 20, this.height / 2 - 28, this.Core, 1));

    super.initGui();
  }
}