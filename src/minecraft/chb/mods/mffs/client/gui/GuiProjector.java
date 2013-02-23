/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.ProjectorTyp;
/*     */ import chb.mods.mffs.common.container.ContainerProjector;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
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
/*     */ public class GuiProjector extends GuiContainer
/*     */ {
/*     */   private TileEntityProjector projector;
/*  38 */   private boolean editMode = false;
/*     */ 
/*     */   public GuiProjector(EntityPlayer player, TileEntityProjector tileentity)
/*     */   {
/*  42 */     super(new ContainerProjector(player, tileentity));
/*  43 */     this.projector = tileentity;
/*  44 */     this.xSize = 176;
/*  45 */     this.ySize = 186;
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
/*  58 */         NetworkHandlerClient.fireTileEntityEvent(this.projector, 12, "");
/*     */       }
/*  60 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  61 */         NetworkHandlerClient.fireTileEntityEvent(this.projector, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  64 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/*  70 */     super.mouseClicked(i, j, k);
/*     */ 
/*  72 */     int xMin = (this.width - this.xSize) / 2;
/*  73 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/*  75 */     int x = i - xMin;
/*  76 */     int y = j - yMin;
/*     */ 
/*  78 */     if (this.editMode) {
/*  79 */       this.editMode = false;
/*  80 */     } else if ((x >= 10) && (y >= 5) && (x <= 141) && (y <= 19)) {
/*  81 */       NetworkHandlerClient.fireTileEntityEvent(this.projector, 10, "null");
/*  82 */       this.editMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  88 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiProjector.png");
/*     */ 
/*  90 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  91 */     this.mc.renderEngine.bindTexture(textur);
/*     */ 
/*  93 */     int w = (this.width - this.xSize) / 2;
/*  94 */     int k = (this.height - this.ySize) / 2;
/*     */ 
/*  96 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*  97 */     int i1 = 79 * this.projector.getCapacity() / 100;
/*  98 */     drawTexturedModalRect(w + 8, k + 91, 176, 0, i1 + 1, 79);
/*     */ 
/* 100 */     if (this.projector.hasValidTypeMod())
/*     */     {
/* 102 */       if (ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp != 7) {
/* 103 */         drawTexturedModalRect(w + 119, k + 63, 177, 143, 16, 16);
/*     */       }
/*     */ 
/* 106 */       if ((ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp != 4) && (ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp != 2)) {
/* 107 */         drawTexturedModalRect(w + 155, k + 63, 177, 143, 16, 16);
/*     */       }
/*     */ 
/* 110 */       if ((ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp == 1) || (ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp == 2) || (ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp == 6) || (ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp == 7) || (ProjectorTyp.TypfromItem(this.projector.get_type()).ProTyp == 8)) {
/* 111 */         drawTexturedModalRect(w + 137, k + 28, 177, 143, 16, 16);
/*     */ 
/* 113 */         drawTexturedModalRect(w + 137, k + 62, 177, 143, 16, 16);
/*     */ 
/* 115 */         drawTexturedModalRect(w + 154, k + 45, 177, 143, 16, 16);
/*     */ 
/* 117 */         drawTexturedModalRect(w + 120, k + 45, 177, 143, 16, 16);
/*     */       }
/*     */ 
/* 120 */       if (this.projector.hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true))
/* 121 */         drawTexturedModalRect(w + 137, k + 45, 177, 143, 16, 16);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton)
/*     */   {
/* 127 */     NetworkHandlerClient.fireTileEntityEvent(this.projector, guibutton.id, "");
/*     */   }
/*     */ 
/*     */   public void initGui() {
/* 131 */     this.controlList.add(new GraphicButton(1, this.width / 2 + 4, this.height / 2 - 37, this.projector, 1));
/* 132 */     this.controlList.add(new GraphicButton(0, this.width / 2 + 67, this.height / 2 - 88, this.projector, 0));
/*     */ 
/* 134 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/*     */   {
/* 139 */     this.fontRenderer.drawString(this.projector.getDeviceName(), 12, 9, 4210752);
/* 140 */     this.fontRenderer.drawString("MFFS Projector", 12, 24, 4210752);
/* 141 */     this.fontRenderer.drawString("Typ-Mode", 34, 44, 4210752);
/* 142 */     this.fontRenderer.drawString("PowerLink", 34, 66, 4210752);
/* 143 */     if (this.projector.hasPowerSource())
/* 144 */       this.fontRenderer.drawString(String.valueOf(this.projector.getLinkPower()), 30, 80, 4210752);
/*     */     else
/* 146 */       this.fontRenderer.drawString("No Link/OOR", 30, 80, 4210752);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiProjector
 * JD-Core Version:    0.6.2
 */