/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.container.ContainerAdvSecurityStation;
/*     */ import chb.mods.mffs.common.item.ItemCardPersonalID;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.network.client.NetworkHandlerClient;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderEngine;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiAdvSecurityStation extends GuiContainer
/*     */ {
/*     */   private TileEntityAdvSecurityStation tileEntity;
/*     */   private SecurityRight hoverSR;
/*  45 */   private boolean editMode = false;
/*     */ 
/*     */   public GuiAdvSecurityStation(EntityPlayer player, TileEntityAdvSecurityStation tileentity)
/*     */   {
/*  49 */     super(new ContainerAdvSecurityStation(player, tileentity));
/*  50 */     this.tileEntity = tileentity;
/*  51 */     this.xSize = 256;
/*  52 */     this.ySize = 216;
/*     */   }
/*     */ 
/*     */   protected void keyTyped(char c, int i)
/*     */   {
/*  58 */     if ((i != 1) && (this.editMode)) {
/*  59 */       if (c == '\r') {
/*  60 */         this.editMode = false;
/*  61 */         return;
/*     */       }
/*     */ 
/*  64 */       if (i == 14) {
/*  65 */         NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 12, "");
/*     */       }
/*  67 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  68 */         NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  71 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
/*     */   {
/*  79 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiAdvSecstation.png");
/*  80 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  81 */     this.mc.renderEngine.bindTexture(textur);
/*  82 */     int w = (this.width - this.xSize) / 2;
/*  83 */     int k = (this.height - this.ySize) / 2;
/*  84 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*     */ 
/*  86 */     this.hoverSR = null;
/*  87 */     int scale = 18;
/*  88 */     int ct = 0;
/*     */ 
/*  90 */     ItemStack modCard = this.tileEntity.getModCardStack();
/*  91 */     if ((modCard != null) && 
/*  92 */       ((modCard.getItem() instanceof ItemCardPersonalID))) {
/*  93 */       List srKeys = new ArrayList();
/*  94 */       srKeys.addAll(SecurityRight.rights.keySet());
/*  95 */       Collections.sort(srKeys);
/*  96 */       for (String srKey : srKeys) {
/*  97 */         SecurityRight sr = (SecurityRight)SecurityRight.rights.get(srKey);
/*     */ 
/*  99 */         int x = ct % 7 * (scale + 2) + 18;
/* 100 */         int y = ct / 7 * (scale + 2) + 54;
/*     */ 
/* 102 */         if (ItemCardPersonalID.hasRight(modCard, sr))
/* 103 */           drawSprite(this.guiLeft + x, this.guiTop + y, 0, 0, sr);
/*     */         else {
/* 105 */           drawSprite(this.guiLeft + x, this.guiTop + y, 0, scale, sr);
/*     */         }
/* 107 */         if ((mouseX >= x + this.guiLeft) && (mouseX <= x + this.guiLeft + scale) && (mouseY >= this.guiTop + y) && (mouseY <= this.guiTop + y + scale)) {
/* 108 */           this.hoverSR = sr;
/*     */         }
/*     */ 
/* 111 */         ct++;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawSprite(int par1, int par2, int par3, int par4, SecurityRight sr)
/*     */   {
/* 120 */     int var5 = this.mc.renderEngine.getTexture(sr.texture);
/* 121 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 122 */     this.mc.renderEngine.bindTexture(var5);
/*     */ 
/* 124 */     if (sr.texIndex <= 6)
/* 125 */       par3 += sr.texIndex * 18;
/* 126 */     else par3 += (sr.texIndex - 7) * 18;
/*     */ 
/* 128 */     if (sr.texIndex > 6) {
/* 129 */       par4 += 36;
/*     */     }
/* 131 */     Tessellator var10 = Tessellator.instance;
/* 132 */     var10.startDrawingQuads();
/* 133 */     var10.addVertexWithUV(par1 + 0, par2 + 18, this.zLevel, (par3 + 0) * 0.007813F, (par4 + 18) * 0.007813F);
/* 134 */     var10.addVertexWithUV(par1 + 18, par2 + 18, this.zLevel, (par3 + 18) * 0.007813F, (par4 + 18) * 0.007813F);
/* 135 */     var10.addVertexWithUV(par1 + 18, par2 + 0, this.zLevel, (par3 + 18) * 0.007813F, (par4 + 0) * 0.007813F);
/* 136 */     var10.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * 0.007813F, (par4 + 0) * 0.007813F);
/* 137 */     var10.draw();
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/* 142 */     super.mouseClicked(i, j, k);
/*     */ 
/* 144 */     int xMin = (this.width - this.xSize) / 2;
/* 145 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/* 147 */     int x = i - xMin;
/* 148 */     int y = j - yMin;
/*     */ 
/* 150 */     if ((x >= 12) && (y >= 103) && (x <= 27) && (y <= 118)) {
/* 151 */       NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 101, "null");
/*     */     }
/*     */ 
/* 154 */     if ((x >= 68) && (y >= 103) && (x <= 83) && (y <= 118)) {
/* 155 */       NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 102, "null");
/*     */     }
/*     */ 
/* 158 */     if (this.editMode) {
/* 159 */       this.editMode = false;
/* 160 */     } else if ((x >= 120) && (y >= 4) && (x <= 250) && (y <= 18)) {
/* 161 */       NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 10, "null");
/* 162 */       this.editMode = true;
/*     */     }
/* 164 */     if (this.hoverSR != null)
/* 165 */       NetworkHandlerClient.fireTileEntityEvent(this.tileEntity, 100, this.hoverSR.rightKey);
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
/*     */   {
/* 172 */     this.fontRenderer.drawString("MFFS Security Station:", 8, 8, 4210752);
/* 173 */     this.fontRenderer.drawString(this.tileEntity.getDeviceName(), 125, 8, 4210752);
/* 174 */     this.fontRenderer.drawString("Master", 200, 38, 4210752);
/* 175 */     this.fontRenderer.drawString("Rights Allocation", 52, 35, 4210752);
/* 176 */     this.fontRenderer.drawString("Copy->", 109, 106, 4210752);
/* 177 */     this.fontRenderer.drawString("validity", 31, 106, 4210752);
/*     */ 
/* 179 */     if (this.hoverSR != null) {
/* 180 */       List list = new ArrayList();
/* 181 */       list.add(this.hoverSR.name);
/* 182 */       if (list.size() > 0) {
/* 183 */         GL11.glDisable(32826);
/* 184 */         RenderHelper.disableStandardItemLighting();
/* 185 */         GL11.glDisable(2896);
/* 186 */         GL11.glDisable(2929);
/*     */ 
/* 188 */         int j2 = 0;
/* 189 */         for (int k2 = 0; k2 < list.size(); k2++) {
/* 190 */           int i3 = this.fontRenderer.getStringWidth((String)list.get(k2));
/* 191 */           if (i3 > j2) {
/* 192 */             j2 = i3;
/*     */           }
/*     */         }
/*     */ 
/* 196 */         int l2 = mouseX - this.guiLeft + 12;
/* 197 */         int j3 = mouseY - this.guiTop - 12;
/* 198 */         int k3 = j2;
/* 199 */         int l3 = 8;
/* 200 */         if (list.size() > 1) {
/* 201 */           l3 += 2 + (list.size() - 1) * 10;
/*     */         }
/*     */ 
/* 204 */         this.zLevel = 300.0F;
/* 205 */         itemRenderer.zLevel = 300.0F;
/* 206 */         int i4 = -267386864;
/* 207 */         drawGradientRect(l2 - 3, j3 - 4, l2 + k3 + 3, j3 - 3, i4, i4);
/* 208 */         drawGradientRect(l2 - 3, j3 + l3 + 3, l2 + k3 + 3, j3 + l3 + 4, i4, i4);
/* 209 */         drawGradientRect(l2 - 3, j3 - 3, l2 + k3 + 3, j3 + l3 + 3, i4, i4);
/* 210 */         drawGradientRect(l2 - 4, j3 - 3, l2 - 3, j3 + l3 + 3, i4, i4);
/* 211 */         drawGradientRect(l2 + k3 + 3, j3 - 3, l2 + k3 + 4, j3 + l3 + 3, i4, i4);
/* 212 */         int j4 = 1347420415;
/* 213 */         int k4 = (j4 & 0xFEFEFE) >> 1 | j4 & 0xFF000000;
/* 214 */         drawGradientRect(l2 - 3, j3 - 3 + 1, l2 - 3 + 1, j3 + l3 + 3 - 1, j4, k4);
/* 215 */         drawGradientRect(l2 + k3 + 2, j3 - 3 + 1, l2 + k3 + 3, j3 + l3 + 3 - 1, j4, k4);
/* 216 */         drawGradientRect(l2 - 3, j3 - 3, l2 + k3 + 3, j3 - 3 + 1, j4, j4);
/* 217 */         drawGradientRect(l2 - 3, j3 + l3 + 2, l2 + k3 + 3, j3 + l3 + 3, k4, k4);
/* 218 */         for (int l4 = 0; l4 < list.size(); l4++) {
/* 219 */           String s = (String)list.get(l4);
/* 220 */           if (l4 == 0)
/* 221 */             s = "§F" + s;
/*     */           else {
/* 223 */             s = "§7" + s;
/*     */           }
/* 225 */           this.fontRenderer.drawStringWithShadow(s, l2, j3, -1);
/* 226 */           if (l4 == 0) {
/* 227 */             j3 += 2;
/*     */           }
/* 229 */           j3 += 10;
/*     */         }
/*     */ 
/* 232 */         this.zLevel = 0.0F;
/* 233 */         itemRenderer.zLevel = 0.0F;
/* 234 */         GL11.glEnable(2896);
/* 235 */         GL11.glEnable(2929);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiAdvSecurityStation
 * JD-Core Version:    0.6.2
 */