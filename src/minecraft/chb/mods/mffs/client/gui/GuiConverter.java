package chb.mods.mffs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import chb.mods.mffs.client.GraphicButton;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.container.ContainerConverter;
import chb.mods.mffs.common.tileentity.TileEntityConverter;
import chb.mods.mffs.network.client.NetworkHandlerClient;

public class GuiConverter extends GuiContainer
{
  private TileEntityConverter Converter;
  private boolean NameeditMode = false;

  public GuiConverter(EntityPlayer player, TileEntityConverter tileentity) {
    super(new ContainerConverter(player, tileentity));
    this.Converter = tileentity;
    this.xSize = 256;
    this.ySize = 216;
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiConvertor.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(textur);
    int w = (this.width - this.xSize) / 2;
    int k = (this.height - this.ySize) / 2;
    drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
    int i1 = 76 * this.Converter.getCapacity() / 100;
    drawTexturedModalRect(w + 14, k + 65, 0, 233, i1 + 1, 23);
    if (!ModularForceFieldSystem.ic2found.booleanValue())
      drawTexturedModalRect(w + 99, k + 45, 0, 217, 70, 13);
    if (!ModularForceFieldSystem.uefound.booleanValue())
      drawTexturedModalRect(w + 174, k + 45, 0, 217, 70, 13);
  }

  protected void keyTyped(char c, int i)
  {
    if ((i != 1) && (this.NameeditMode)) {
      if (c == '\r') {
        this.NameeditMode = false;
        return;
      }

      if (i == 14) {
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 12, "");
      }
      if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 11, String.valueOf(c));
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

    if (this.NameeditMode) {
      this.NameeditMode = false;
    } else if ((x >= 97) && (y >= 4) && (x <= 227) && (y <= 18)) {
      NetworkHandlerClient.fireTileEntityEvent(this.Converter, 10, "null");
      this.NameeditMode = true;
    }
    if (ModularForceFieldSystem.ic2found.booleanValue()) {
      if ((x >= 100) && (y >= 46) && (x <= 114) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 200, "+");
      if ((x >= 115) && (y >= 46) && (x <= 128) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 200, "-");
      if ((x >= 140) && (y >= 46) && (x <= 154) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 201, "+");
      if ((x >= 155) && (y >= 46) && (x <= 168) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 201, "-");
    }
    if (ModularForceFieldSystem.uefound.booleanValue())
    {
      if ((x >= 175) && (y >= 46) && (x <= 189) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 202, "+");
      if ((x >= 190) && (y >= 46) && (x <= 203) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 202, "-");
      if ((x >= 215) && (y >= 46) && (x <= 229) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 203, "+");
      if ((x >= 230) && (y >= 46) && (x <= 243) && (y <= 57))
        NetworkHandlerClient.fireTileEntityEvent(this.Converter, 203, "-");
    }
  }

  protected void actionPerformed(GuiButton guibutton)
  {
    NetworkHandlerClient.fireTileEntityEvent(this.Converter, guibutton.id, "null");
  }

  public void initGui() {
    this.controlList.add(new GraphicButton(0, this.width / 2 + 107, this.height / 2 - 104, this.Converter, 0));

    if (ModularForceFieldSystem.ic2found.booleanValue()) {
      this.controlList.add(new GraphicButton(100, this.width / 2 - 25, this.height / 2 - 80, this.Converter, 1));
    }
    if (ModularForceFieldSystem.uefound.booleanValue()) {
      this.controlList.add(new GraphicButton(101, this.width / 2 + 50, this.height / 2 - 80, this.Converter, 2));
    }
    super.initGui();
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    this.fontRenderer.drawString(this.Converter.getDeviceName(), 100, 8, 4210752);
    this.fontRenderer.drawString("MFFS Converter", 8, 8, 4210752);

    if (ModularForceFieldSystem.ic2found.booleanValue()) {
      this.fontRenderer.drawString("IC 2", 125, 33, 0);
      this.fontRenderer.drawString("EU", 110, 62, 0);
      this.fontRenderer.drawString("packets", 130, 62, 0);

      this.fontRenderer.drawString("" + this.Converter.getIC_Outputpacketsize(), 103, 48, 16777215);
      this.fontRenderer.drawString("x", 132, 48, 16777215);
      this.fontRenderer.drawString("" + this.Converter.getIC_Outputpacketamount(), 150, 48, 16777215);
    }
    if (ModularForceFieldSystem.uefound.booleanValue())
    {
      this.fontRenderer.drawString("U.E.", 200, 33, 0);
      this.fontRenderer.drawString("Volt", 180, 62, 0);
      this.fontRenderer.drawString("Amps", 220, 62, 0);

      this.fontRenderer.drawString("" + this.Converter.getUE_Outputvoltage(), 180, 48, 16777215);
      this.fontRenderer.drawString("" + this.Converter.getUE_Outputamp(), 222, 48, 16777215);
    }

    if (this.Converter.getPowerSourceID() != 0)
      this.fontRenderer.drawString("FE: " + this.Converter.getLinkPower(), 17, 54, 4210752);
    else {
      this.fontRenderer.drawString("FE: No Link/OOR", 17, 54, 4210752);
    }

    if (!this.Converter.isActive())
      this.fontRenderer.drawString("OFFLINE", 32, 99, 16711680);
    else
      this.fontRenderer.drawString("ONLINE", 34, 99, 35584);
  }
}