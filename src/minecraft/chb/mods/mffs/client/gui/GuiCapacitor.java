/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.container.ContainerCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.network.client.NetworkHandlerClient;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderEngine;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiCapacitor extends GuiContainer
/*     */ {
/*     */   private TileEntityCapacitor Core;
/*  39 */   private boolean editMode = false;
/*     */ 
/*     */   public GuiCapacitor(EntityPlayer player, TileEntityCapacitor tileentity)
/*     */   {
/*  43 */     super(new ContainerCapacitor(player, tileentity));
/*  44 */     this.Core = tileentity;
/*  45 */     this.xSize = 176;
/*  46 */     this.ySize = 207;
/*     */   }
/*     */ 
/*     */   protected void keyTyped(char c, int i)
/*     */   {
/*  52 */     if ((i != 1) && (this.editMode)) {
/*  53 */       if (c == '\r') {
/*  54 */         this.editMode = false;
/*  55 */         return;
/*     */       }
/*     */ 
/*  58 */       if (i == 14) {
/*  59 */         NetworkHandlerClient.fireTileEntityEvent(this.Core, 12, "");
/*     */       }
/*  61 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  62 */         NetworkHandlerClient.fireTileEntityEvent(this.Core, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  65 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/*  71 */     super.mouseClicked(i, j, k);
/*     */ 
/*  73 */     int xMin = (this.width - this.xSize) / 2;
/*  74 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/*  76 */     int x = i - xMin;
/*  77 */     int y = j - yMin;
/*     */ 
/*  79 */     if (this.editMode) {
/*  80 */       this.editMode = false;
/*  81 */     } else if ((x >= 5) && (y >= 4) && (x <= 135) && (y <= 18)) {
/*  82 */       NetworkHandlerClient.fireTileEntityEvent(this.Core, 10, "null");
/*  83 */       this.editMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  89 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiCapacitor.png");
/*     */ 
/*  91 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  92 */     this.mc.renderEngine.bindTexture(textur);
/*  93 */     int w = (this.width - this.xSize) / 2;
/*  94 */     int k = (this.height - this.ySize) / 2;
/*  95 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*  96 */     int i1 = 79 * this.Core.getPercentageStorageCapacity() / 100;
/*  97 */     drawTexturedModalRect(w + 8, k + 112, 176, 0, i1 + 1, 79);
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
/* 101 */     this.fontRenderer.drawString("Force Energy Capacitor", 8, 25, 4210752);
/* 102 */     this.fontRenderer.drawString(this.Core.getDeviceName(), 8, 8, 4210752);
/* 103 */     this.fontRenderer.drawString("FE: " + String.valueOf(this.Core.getStorageAvailablePower()), 8, 100, 4210752);
/* 104 */     this.fontRenderer.drawString("Power Uplink: ", 8, 80, 4210752);
/*     */ 
/* 106 */     this.fontRenderer.drawString("transmit range:", 8, 60, 4210752);
/* 107 */     this.fontRenderer.drawString(" " + this.Core.getTransmitRange(), 90, 60, 4210752);
/*     */ 
/* 111 */     this.fontRenderer.drawString("linked device:", 8, 43, 4210752);
/* 112 */     this.fontRenderer.drawString(" " + this.Core.getLinketProjektor(), 90, 45, 4210752);
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton)
/*     */   {
/* 119 */     NetworkHandlerClient.fireTileEntityEvent(this.Core, guibutton.id, "");
/*     */   }
/*     */ 
/*     */   public void initGui() {
/* 123 */     this.controlList.add(new GraphicButton(0, this.width / 2 + 65, this.height / 2 - 100, this.Core, 0));
/* 124 */     this.controlList.add(new GraphicButton(1, this.width / 2 + 20, this.height / 2 - 28, this.Core, 1));
/*     */ 
/* 126 */     super.initGui();
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiCapacitor
 * JD-Core Version:    0.6.2
 */