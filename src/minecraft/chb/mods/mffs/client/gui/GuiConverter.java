/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.container.ContainerConverter;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
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
/*     */ public class GuiConverter extends GuiContainer
/*     */ {
/*     */   private TileEntityConverter Converter;
/*  40 */   private boolean NameeditMode = false;
/*     */ 
/*     */   public GuiConverter(EntityPlayer player, TileEntityConverter tileentity) {
/*  43 */     super(new ContainerConverter(player, tileentity));
/*  44 */     this.Converter = tileentity;
/*  45 */     this.xSize = 256;
/*  46 */     this.ySize = 216;
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
/*  50 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiConvertor.png");
/*  51 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  52 */     this.mc.renderEngine.bindTexture(textur);
/*  53 */     int w = (this.width - this.xSize) / 2;
/*  54 */     int k = (this.height - this.ySize) / 2;
/*  55 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*  56 */     int i1 = 76 * this.Converter.getCapacity() / 100;
/*  57 */     drawTexturedModalRect(w + 14, k + 65, 0, 233, i1 + 1, 23);
/*  58 */     if (!ModularForceFieldSystem.ic2found.booleanValue())
/*  59 */       drawTexturedModalRect(w + 99, k + 45, 0, 217, 70, 13);
/*  60 */     if (!ModularForceFieldSystem.uefound.booleanValue())
/*  61 */       drawTexturedModalRect(w + 174, k + 45, 0, 217, 70, 13);
/*     */   }
/*     */ 
/*     */   protected void keyTyped(char c, int i)
/*     */   {
/*  66 */     if ((i != 1) && (this.NameeditMode)) {
/*  67 */       if (c == '\r') {
/*  68 */         this.NameeditMode = false;
/*  69 */         return;
/*     */       }
/*     */ 
/*  72 */       if (i == 14) {
/*  73 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 12, "");
/*     */       }
/*  75 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  76 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  79 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/*  85 */     super.mouseClicked(i, j, k);
/*     */ 
/*  87 */     int xMin = (this.width - this.xSize) / 2;
/*  88 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/*  90 */     int x = i - xMin;
/*  91 */     int y = j - yMin;
/*     */ 
/*  93 */     if (this.NameeditMode) {
/*  94 */       this.NameeditMode = false;
/*  95 */     } else if ((x >= 97) && (y >= 4) && (x <= 227) && (y <= 18)) {
/*  96 */       NetworkHandlerClient.fireTileEntityEvent(this.Converter, 10, "null");
/*  97 */       this.NameeditMode = true;
/*     */     }
/*  99 */     if (ModularForceFieldSystem.ic2found.booleanValue()) {
/* 100 */       if ((x >= 100) && (y >= 46) && (x <= 114) && (y <= 57))
/* 101 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 200, "+");
/* 102 */       if ((x >= 115) && (y >= 46) && (x <= 128) && (y <= 57))
/* 103 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 200, "-");
/* 104 */       if ((x >= 140) && (y >= 46) && (x <= 154) && (y <= 57))
/* 105 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 201, "+");
/* 106 */       if ((x >= 155) && (y >= 46) && (x <= 168) && (y <= 57))
/* 107 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 201, "-");
/*     */     }
/* 109 */     if (ModularForceFieldSystem.uefound.booleanValue())
/*     */     {
/* 111 */       if ((x >= 175) && (y >= 46) && (x <= 189) && (y <= 57))
/* 112 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 202, "+");
/* 113 */       if ((x >= 190) && (y >= 46) && (x <= 203) && (y <= 57))
/* 114 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 202, "-");
/* 115 */       if ((x >= 215) && (y >= 46) && (x <= 229) && (y <= 57))
/* 116 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 203, "+");
/* 117 */       if ((x >= 230) && (y >= 46) && (x <= 243) && (y <= 57))
/* 118 */         NetworkHandlerClient.fireTileEntityEvent(this.Converter, 203, "-");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton)
/*     */   {
/* 125 */     NetworkHandlerClient.fireTileEntityEvent(this.Converter, guibutton.id, "null");
/*     */   }
/*     */ 
/*     */   public void initGui() {
/* 129 */     this.controlList.add(new GraphicButton(0, this.width / 2 + 107, this.height / 2 - 104, this.Converter, 0));
/*     */ 
/* 131 */     if (ModularForceFieldSystem.ic2found.booleanValue()) {
/* 132 */       this.controlList.add(new GraphicButton(100, this.width / 2 - 25, this.height / 2 - 80, this.Converter, 1));
/*     */     }
/* 134 */     if (ModularForceFieldSystem.uefound.booleanValue()) {
/* 135 */       this.controlList.add(new GraphicButton(101, this.width / 2 + 50, this.height / 2 - 80, this.Converter, 2));
/*     */     }
/* 137 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
/* 141 */     this.fontRenderer.drawString(this.Converter.getDeviceName(), 100, 8, 4210752);
/* 142 */     this.fontRenderer.drawString("MFFS Converter", 8, 8, 4210752);
/*     */ 
/* 146 */     if (ModularForceFieldSystem.ic2found.booleanValue()) {
/* 147 */       this.fontRenderer.drawString("IC 2", 125, 33, 0);
/* 148 */       this.fontRenderer.drawString("EU", 110, 62, 0);
/* 149 */       this.fontRenderer.drawString("packets", 130, 62, 0);
/*     */ 
/* 151 */       this.fontRenderer.drawString("" + this.Converter.getIC_Outputpacketsize(), 103, 48, 16777215);
/* 152 */       this.fontRenderer.drawString("x", 132, 48, 16777215);
/* 153 */       this.fontRenderer.drawString("" + this.Converter.getIC_Outputpacketamount(), 150, 48, 16777215);
/*     */     }
/* 155 */     if (ModularForceFieldSystem.uefound.booleanValue())
/*     */     {
/* 157 */       this.fontRenderer.drawString("U.E.", 200, 33, 0);
/* 158 */       this.fontRenderer.drawString("Volt", 180, 62, 0);
/* 159 */       this.fontRenderer.drawString("Amps", 220, 62, 0);
/*     */ 
/* 161 */       this.fontRenderer.drawString("" + this.Converter.getUE_Outputvoltage(), 180, 48, 16777215);
/* 162 */       this.fontRenderer.drawString("" + this.Converter.getUE_Outputamp(), 222, 48, 16777215);
/*     */     }
/*     */ 
/* 168 */     if (this.Converter.getPowerSourceID() != 0)
/* 169 */       this.fontRenderer.drawString("FE: " + this.Converter.getLinkPower(), 17, 54, 4210752);
/*     */     else {
/* 171 */       this.fontRenderer.drawString("FE: No Link/OOR", 17, 54, 4210752);
/*     */     }
/*     */ 
/* 174 */     if (!this.Converter.isActive())
/* 175 */       this.fontRenderer.drawString("OFFLINE", 32, 99, 16711680);
/*     */     else
/* 177 */       this.fontRenderer.drawString("ONLINE", 34, 99, 35584);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiConverter
 * JD-Core Version:    0.6.2
 */