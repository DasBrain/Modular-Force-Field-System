package chb.mods.mffs.client.gui;

import chb.mods.mffs.client.GraphicButton;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.container.ContainerControlSystem;
import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
import chb.mods.mffs.network.client.NetworkHandlerClient;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiControlSystem extends GuiContainer
{
  private TileEntityControlSystem ControlSystem;
  private boolean editMode = false;
  private EntityPlayer player;

  public GuiControlSystem(EntityPlayer player, TileEntityControlSystem tileentity)
  {
    super(new ContainerControlSystem(player, tileentity));
    this.ControlSystem = tileentity;
    this.xSize = 256;
    this.ySize = 216;
    this.player = player;
  }

  protected void keyTyped(char c, int i)
  {
    if ((i != 1) && (this.editMode)) {
      if (c == '\r') {
        this.editMode = false;
        return;
      }

      if (i == 14) {
        NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, 12, "");
      }
      if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
        NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, 11, String.valueOf(c));
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
    } else if ((x >= 115) && (y >= 5) && (x <= 234) && (y <= 19)) {
      NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, 10, "null");
      this.editMode = true;
    }
  }

  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
  {
    int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiControlSystem.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(textur);
    int w = (this.width - this.xSize) / 2;
    int k = (this.height - this.ySize) / 2;
    drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
  }

  protected void actionPerformed(GuiButton guibutton)
  {
    if (guibutton.id == 103)
      NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, guibutton.id, this.player.username);
    else
      NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, guibutton.id, "");
  }

  public void initGui()
  {
    this.controlList.add(new GraphicButton(100, this.width / 2 - 115, this.height / 2 - 45, this.ControlSystem, 1));
    this.controlList.add(new GraphicButton(101, this.width / 2 - 115, this.height / 2 - 25, this.ControlSystem, 2));
    this.controlList.add(new GraphicButton(102, this.width / 2 - 115, this.height / 2 - 5, this.ControlSystem, 3));
    this.controlList.add(new GuiButton(103, this.width / 2 + -65, this.height / 2 - 8, 100, 20, "Open Remote Gui"));
    super.initGui();
  }

  protected void drawGuiContainerForegroundLayer(int par1, int par2)
  {
    this.fontRenderer.drawString("MFFS Control System", 8, 9, 4210752);
    this.fontRenderer.drawString(this.ControlSystem.getDeviceName(), 123, 9, 4210752);

    this.fontRenderer.drawString("DataLink", 190, 54, 4210752);
    this.fontRenderer.drawString("Reader", 190, 65, 4210752);

    this.fontRenderer.drawString("Name: " + this.ControlSystem.getRemoteDeviceName(), 15, 30, 4210752);
    this.fontRenderer.drawString("Type:  " + this.ControlSystem.getRemoteDeviceTyp(), 15, 45, 4210752);
    if (this.ControlSystem.getStackInSlot(1) != null) {
      RenderItem renderItem = new RenderItem();
      RenderHelper.enableGUIStandardItemLighting();
      renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard), 40, 59);
      renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemfc), 100, 59);
      RenderHelper.disableStandardItemLighting();
      if (this.ControlSystem.getRemoteSecurityStationlink())
        this.fontRenderer.drawString("linked", 60, 64, 2263842);
      else this.fontRenderer.drawString("linked", 60, 64, 9116186);
      if (this.ControlSystem.getRemotehasPowersource()) {
        this.fontRenderer.drawString("linked", 120, 64, 2263842);
        this.fontRenderer.drawString("Power left: " + this.ControlSystem.getRemotePowerleft() + "%", 40, 80, 4210752);
      } else {
        this.fontRenderer.drawString("linked", 120, 64, 9116186);
      }
      if (this.ControlSystem.getRemoteGUIinRange())
        this.fontRenderer.drawString("OK", 40, 107, 2263842);
      else this.fontRenderer.drawString("OOR", 40, 107, 9116186);
    }
  }
}