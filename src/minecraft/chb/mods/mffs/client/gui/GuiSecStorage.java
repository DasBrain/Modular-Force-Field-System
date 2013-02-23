package chb.mods.mffs.client.gui;

import chb.mods.mffs.client.GraphicButton;
import chb.mods.mffs.common.container.ContainerSecStorage;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
import chb.mods.mffs.network.client.NetworkHandlerClient;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiSecStorage extends GuiContainer
{
  private TileEntitySecStorage SecStorage;
  private boolean editMode = false;

  public GuiSecStorage(EntityPlayer player, TileEntitySecStorage tileentity) {
    super(new ContainerSecStorage(player, tileentity));
    this.SecStorage = tileentity;
    this.xSize = 185;
    this.ySize = 238;
  }

  protected void keyTyped(char c, int i)
  {
    if ((i != 1) && (this.editMode)) {
      if (c == '\r') {
        this.editMode = false;
        return;
      }

      if (i == 14) {
        NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, 12, "");
      }
      if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
        NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, 11, String.valueOf(c));
    }
    else {
      super.keyTyped(c, i);
    }
  }

  protected void actionPerformed(GuiButton guibutton) {
    NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, guibutton.id, "");
  }

  public void initGui() {
    this.controlList.add(new GraphicButton(0, this.width / 2 + 65, this.height / 2 - 113, this.SecStorage, 0));
    super.initGui();
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
    } else if ((x >= 10) && (y >= 5) && (x <= 141) && (y <= 19)) {
      NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, 10, "null");
      this.editMode = true;
    }
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiSecStorage.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(textur);
    int w = (this.width - this.xSize) / 2;
    int k = (this.height - this.ySize) / 2;
    drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2)
  {
    this.fontRenderer.drawString(this.SecStorage.getDeviceName(), 12, 9, 4210752);
    this.fontRenderer.drawString("MFFS Security Storage", 38, 28, 4210752);
  }
}