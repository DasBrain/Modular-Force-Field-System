/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.container.ContainerForceEnergyExtractor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
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
/*     */ public class GuiExtractor extends GuiContainer
/*     */ {
/*     */   private TileEntityExtractor Extractor;
/*  37 */   private boolean editMode = false;
/*     */ 
/*     */   public GuiExtractor(EntityPlayer player, TileEntityExtractor tileentity)
/*     */   {
/*  41 */     super(new ContainerForceEnergyExtractor(player, tileentity));
/*  42 */     this.Extractor = tileentity;
/*  43 */     this.xSize = 176;
/*  44 */     this.ySize = 186;
/*     */   }
/*     */ 
/*     */   protected void keyTyped(char c, int i)
/*     */   {
/*  50 */     if ((i != 1) && (this.editMode)) {
/*  51 */       if (c == '\r') {
/*  52 */         this.editMode = false;
/*  53 */         return;
/*     */       }
/*     */ 
/*  56 */       if (i == 14) {
/*  57 */         NetworkHandlerClient.fireTileEntityEvent(this.Extractor, 12, "");
/*     */       }
/*  59 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  60 */         NetworkHandlerClient.fireTileEntityEvent(this.Extractor, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  63 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/*  69 */     super.mouseClicked(i, j, k);
/*     */ 
/*  71 */     int xMin = (this.width - this.xSize) / 2;
/*  72 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/*  74 */     int x = i - xMin;
/*  75 */     int y = j - yMin;
/*     */ 
/*  77 */     if (this.editMode) {
/*  78 */       this.editMode = false;
/*  79 */     } else if ((x >= 10) && (y >= 5) && (x <= 141) && (y <= 19)) {
/*  80 */       NetworkHandlerClient.fireTileEntityEvent(this.Extractor, 10, "null");
/*  81 */       this.editMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton) {
/*  86 */     NetworkHandlerClient.fireTileEntityEvent(this.Extractor, guibutton.id, "");
/*     */   }
/*     */ 
/*     */   public void initGui() {
/*  90 */     this.controlList.add(new GraphicButton(0, this.width / 2 + 60, this.height / 2 - 88, this.Extractor, 0));
/*  91 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  97 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiExtractor.png");
/*     */ 
/*  99 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 100 */     this.mc.renderEngine.bindTexture(textur);
/* 101 */     int w = (this.width - this.xSize) / 2;
/* 102 */     int k = (this.height - this.ySize) / 2;
/* 103 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*     */ 
/* 105 */     int Workpowerslider = 79 * this.Extractor.getWorkdone() / 100;
/* 106 */     drawTexturedModalRect(w + 49, k + 89, 176, 0, Workpowerslider, 6);
/*     */ 
/* 109 */     int WorkCylce = 32 * this.Extractor.getWorkCylce() / ModularForceFieldSystem.ForceciumWorkCylce;
/*     */ 
/* 112 */     drawTexturedModalRect(w + 73, k + 50, 179, 81, WorkCylce, 32);
/*     */ 
/* 114 */     int ForceEnergy = 24 * this.Extractor.getForceEnergybuffer() / this.Extractor.getMaxForceEnergyBuffer();
/* 115 */     drawTexturedModalRect(w + 137, k + 60, 219, 80, 32, ForceEnergy);
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
/* 119 */     this.fontRenderer.drawString("Force Energy", 5, 25, 4210752);
/* 120 */     this.fontRenderer.drawString("Upgrades", 10, 50, 4210752);
/* 121 */     this.fontRenderer.drawString(this.Extractor.getDeviceName(), 8, 9, 4210752);
/* 122 */     this.fontRenderer.drawString("Extractor", 5, 35, 4210752);
/* 123 */     this.fontRenderer.drawString(String.valueOf(this.Extractor.getForceEnergybuffer() / 1000).concat("k"), 140, 89, 4210752);
/*     */ 
/* 125 */     this.fontRenderer.drawString(String.valueOf(this.Extractor.getWorkdone()).concat("%"), 23, 89, 4210752);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiExtractor
 * JD-Core Version:    0.6.2
 */