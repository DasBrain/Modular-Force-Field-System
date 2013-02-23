/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.container.ContainerAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
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
/*     */ public class GuiAreaDefenseStation extends GuiContainer
/*     */ {
/*     */   private TileEntityAreaDefenseStation DefenceStation;
/*  38 */   private boolean editMode = false;
/*     */ 
/*     */   public GuiAreaDefenseStation(EntityPlayer player, TileEntityAreaDefenseStation tileentity)
/*     */   {
/*  42 */     super(new ContainerAreaDefenseStation(player, tileentity));
/*  43 */     this.DefenceStation = tileentity;
/*  44 */     this.xSize = 256;
/*  45 */     this.ySize = 216;
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
/*  58 */         NetworkHandlerClient.fireTileEntityEvent(this.DefenceStation, 12, "");
/*     */       }
/*  60 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  61 */         NetworkHandlerClient.fireTileEntityEvent(this.DefenceStation, 11, String.valueOf(c));
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
/*  80 */     } else if ((x >= 115) && (y >= 5) && (x <= 233) && (y <= 19)) {
/*  81 */       NetworkHandlerClient.fireTileEntityEvent(this.DefenceStation, 10, "null");
/*  82 */       this.editMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  88 */     int xSize = 256;
/*  89 */     int ySize = 216;
/*  90 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiDefStation.png");
/*  91 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  92 */     this.mc.renderEngine.bindTexture(textur);
/*  93 */     int w = (this.width - xSize) / 2;
/*  94 */     int k = (this.height - ySize) / 2;
/*  95 */     drawTexturedModalRect(w, k, 0, 0, xSize, ySize);
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton)
/*     */   {
/* 101 */     NetworkHandlerClient.fireTileEntityEvent(this.DefenceStation, guibutton.id, "");
/*     */   }
/*     */ 
/*     */   public void initGui() {
/* 105 */     this.controlList.add(new GraphicButton(0, this.width / 2 + 107, this.height / 2 - 104, this.DefenceStation, 0));
/* 106 */     this.controlList.add(new GraphicButton(100, this.width / 2 + 47, this.height / 2 - 38, this.DefenceStation, 1));
/* 107 */     this.controlList.add(new GraphicButton(101, this.width / 2 - 36, this.height / 2 - 58, this.DefenceStation, 2));
/* 108 */     this.controlList.add(new GraphicButton(102, this.width / 2 + 20, this.height / 2 - 58, this.DefenceStation, 3));
/* 109 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/*     */   {
/* 114 */     this.fontRenderer.drawString("MFFS Defence Station", 7, 9, 4210752);
/* 115 */     this.fontRenderer.drawString(this.DefenceStation.getDeviceName(), 120, 9, 4210752);
/*     */ 
/* 117 */     switch (this.DefenceStation.getActionmode())
/*     */     {
/*     */     case 0:
/* 120 */       this.fontRenderer.drawString("inform", 110, 55, 4210752);
/*     */ 
/* 122 */       this.fontRenderer.drawString(" send Info", 95, 85, 4210752);
/* 123 */       this.fontRenderer.drawString(" to player ", 95, 95, 4210752);
/* 124 */       this.fontRenderer.drawString(" without (SR)", 95, 105, 4210752);
/* 125 */       this.fontRenderer.drawString(" Stay Right", 95, 115, 4210752);
/*     */ 
/* 128 */       break;
/*     */     case 1:
/* 130 */       this.fontRenderer.drawString("kill", 110, 55, 4210752);
/*     */ 
/* 132 */       this.fontRenderer.drawString(" kill player", 95, 85, 4210752);
/* 133 */       this.fontRenderer.drawString(" without (SR)", 95, 95, 4210752);
/* 134 */       this.fontRenderer.drawString(" gathers his", 95, 105, 4210752);
/* 135 */       this.fontRenderer.drawString(" equipment", 95, 115, 4210752);
/*     */ 
/* 137 */       break;
/*     */     case 2:
/* 139 */       this.fontRenderer.drawString("search", 110, 55, 4210752);
/*     */ 
/* 141 */       this.fontRenderer.drawString("scans player", 95, 85, 4210752);
/* 142 */       this.fontRenderer.drawString("without (AAI)", 95, 95, 4210752);
/* 143 */       this.fontRenderer.drawString("and remove", 95, 105, 4210752);
/* 144 */       this.fontRenderer.drawString("banned items", 95, 115, 4210752);
/* 145 */       break;
/*     */     case 3:
/* 148 */       this.fontRenderer.drawString("NPC kill", 110, 55, 4210752);
/*     */ 
/* 150 */       this.fontRenderer.drawString("kill any NPC", 95, 85, 4210752);
/* 151 */       this.fontRenderer.drawString("friendly or", 95, 95, 4210752);
/* 152 */       this.fontRenderer.drawString("hostile", 95, 105, 4210752);
/* 153 */       break;
/*     */     case 4:
/* 156 */       this.fontRenderer.drawString("NPC kill", 110, 55, 4210752);
/*     */ 
/* 158 */       this.fontRenderer.drawString("kill only", 95, 85, 4210752);
/* 159 */       this.fontRenderer.drawString("hostile NPCs", 95, 95, 4210752);
/*     */ 
/* 161 */       break;
/*     */     case 5:
/* 165 */       this.fontRenderer.drawString("NPC kill", 110, 55, 4210752);
/*     */ 
/* 167 */       this.fontRenderer.drawString("kill only", 95, 85, 4210752);
/* 168 */       this.fontRenderer.drawString("friendly NPCs", 95, 95, 4210752);
/*     */     }
/*     */ 
/* 175 */     this.fontRenderer.drawString("Action desc:", 95, 73, 139);
/*     */ 
/* 178 */     this.fontRenderer.drawString("items", 205, 68, 2263842);
/*     */ 
/* 180 */     if (this.DefenceStation.getcontratyp() == 0)
/*     */     {
/* 182 */       this.fontRenderer.drawString("allowed", 200, 82, 2263842);
/*     */     }
/*     */ 
/* 185 */     if (this.DefenceStation.getcontratyp() == 1)
/*     */     {
/* 187 */       this.fontRenderer.drawString("banned", 200, 82, 16711680);
/*     */     }
/*     */ 
/* 191 */     if (this.DefenceStation.getPowerSourceID() != 0)
/* 192 */       this.fontRenderer.drawString("FE: " + this.DefenceStation.getCapacity() + " %", 35, 31, 4210752);
/*     */     else {
/* 194 */       this.fontRenderer.drawString("No Link/OOR", 35, 31, 16711680);
/*     */     }
/*     */ 
/* 197 */     if (this.DefenceStation.hasSecurityCard())
/*     */     {
/* 199 */       this.fontRenderer.drawString("linked", 120, 31, 2263842);
/*     */     }
/*     */ 
/* 203 */     this.fontRenderer.drawString("warning", 35, 55, 139);
/* 204 */     this.fontRenderer.drawString("perimeter: " + this.DefenceStation.getInfoDistance(), 12, 73, 4210752);
/*     */ 
/* 207 */     this.fontRenderer.drawString("action", 35, 91, 15612731);
/* 208 */     this.fontRenderer.drawString("perimeter: " + this.DefenceStation.getActionDistance(), 12, 111, 4210752);
/*     */ 
/* 212 */     this.fontRenderer.drawString("inventory ", 180, 195, 4210752);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiAreaDefenseStation
 * JD-Core Version:    0.6.2
 */