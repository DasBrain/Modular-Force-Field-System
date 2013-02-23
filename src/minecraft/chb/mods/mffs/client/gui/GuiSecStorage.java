/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.container.ContainerSecStorage;
/*     */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
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
/*     */ public class GuiSecStorage extends GuiContainer
/*     */ {
/*     */   private TileEntitySecStorage SecStorage;
/*  39 */   private boolean editMode = false;
/*     */ 
/*     */   public GuiSecStorage(EntityPlayer player, TileEntitySecStorage tileentity) {
/*  42 */     super(new ContainerSecStorage(player, tileentity));
/*  43 */     this.SecStorage = tileentity;
/*  44 */     this.xSize = 185;
/*  45 */     this.ySize = 238;
/*     */   }
/*     */ 
/*     */   protected void keyTyped(char c, int i)
/*     */   {
/*  51 */     if ((i != 1) && (this.editMode)) {
/*  52 */       if (c == '\r') {
/*  53 */         this.editMode = false;
/*  54 */         return;
/*     */       }
/*     */ 
/*  57 */       if (i == 14) {
/*  58 */         NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, 12, "");
/*     */       }
/*  60 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  61 */         NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  64 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton) {
/*  69 */     NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, guibutton.id, "");
/*     */   }
/*     */ 
/*     */   public void initGui() {
/*  73 */     this.controlList.add(new GraphicButton(0, this.width / 2 + 65, this.height / 2 - 113, this.SecStorage, 0));
/*  74 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/*  79 */     super.mouseClicked(i, j, k);
/*     */ 
/*  81 */     int xMin = (this.width - this.xSize) / 2;
/*  82 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/*  84 */     int x = i - xMin;
/*  85 */     int y = j - yMin;
/*     */ 
/*  87 */     if (this.editMode) {
/*  88 */       this.editMode = false;
/*  89 */     } else if ((x >= 10) && (y >= 5) && (x <= 141) && (y <= 19)) {
/*  90 */       NetworkHandlerClient.fireTileEntityEvent(this.SecStorage, 10, "null");
/*  91 */       this.editMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  97 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiSecStorage.png");
/*  98 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  99 */     this.mc.renderEngine.bindTexture(textur);
/* 100 */     int w = (this.width - this.xSize) / 2;
/* 101 */     int k = (this.height - this.ySize) / 2;
/* 102 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/*     */   {
/* 108 */     this.fontRenderer.drawString(this.SecStorage.getDeviceName(), 12, 9, 4210752);
/* 109 */     this.fontRenderer.drawString("MFFS Security Storage", 38, 28, 4210752);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiSecStorage
 * JD-Core Version:    0.6.2
 */