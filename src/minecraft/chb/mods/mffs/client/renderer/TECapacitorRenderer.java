/*    */ package chb.mods.mffs.client.renderer;
/*    */ 
/*    */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class TECapacitorRenderer extends TileEntitySpecialRenderer
/*    */ {
/*    */   public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
/*    */   {
/* 37 */     if ((tileEntity instanceof TileEntityCapacitor))
/*    */     {
/* 39 */       TileEntityCapacitor topview = (TileEntityCapacitor)tileEntity;
/* 40 */       GL11.glPushMatrix();
/* 41 */       GL11.glPolygonOffset(-10.0F, -10.0F);
/* 42 */       GL11.glEnable(32823);
/* 43 */       int side = topview.getSide();
/* 44 */       float dx = 0.0625F;
/* 45 */       float dz = 0.0625F;
/* 46 */       float displayWidth = 0.875F;
/* 47 */       float displayHeight = 0.875F;
/* 48 */       GL11.glTranslatef((float)x, (float)y, (float)z);
/* 49 */       switch (side)
/*    */       {
/*    */       case 1:
/* 53 */         break;
/*    */       case 0:
/* 55 */         GL11.glTranslatef(1.0F, 1.0F, 0.0F);
/* 56 */         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 57 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*    */ 
/* 59 */         break;
/*    */       case 3:
/* 61 */         GL11.glTranslatef(0.0F, 1.0F, 0.0F);
/* 62 */         GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
/* 63 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*    */ 
/* 65 */         break;
/*    */       case 2:
/* 67 */         GL11.glTranslatef(1.0F, 1.0F, 1.0F);
/* 68 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 69 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*    */ 
/* 71 */         break;
/*    */       case 5:
/* 73 */         GL11.glTranslatef(0.0F, 1.0F, 1.0F);
/* 74 */         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 75 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*    */ 
/* 77 */         break;
/*    */       case 4:
/* 79 */         GL11.glTranslatef(1.0F, 1.0F, 0.0F);
/* 80 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 81 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*    */       }
/*    */ 
/* 85 */       GL11.glTranslatef(dx + displayWidth / 2.0F, 1.0F, dz + displayHeight / 2.0F);
/* 86 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 87 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 88 */       FontRenderer fontRenderer = getFontRenderer();
/* 89 */       int maxWidth = 1;
/* 90 */       String header = "MFFS Capacitor";
/* 91 */       maxWidth = Math.max(fontRenderer.getStringWidth(header), maxWidth);
/* 92 */       maxWidth += 4;
/* 93 */       int lineHeight = fontRenderer.FONT_HEIGHT + 2;
/* 94 */       int requiredHeight = lineHeight * 1;
/* 95 */       float scaleX = displayWidth / maxWidth;
/* 96 */       float scaleY = displayHeight / requiredHeight;
/* 97 */       float scale = Math.min(scaleX, scaleY);
/* 98 */       GL11.glScalef(scale, -scale, scale);
/* 99 */       GL11.glDepthMask(false);
/*    */ 
/* 102 */       int realHeight = (int)Math.floor(displayHeight / scale);
/* 103 */       int realWidth = (int)Math.floor(displayWidth / scale);
/*    */       int offsetY;
/*    */       int offsetX;
/*    */       int offsetY;
/* 105 */       if (scaleX < scaleY)
/*    */       {
/* 107 */         int offsetX = 2;
/* 108 */         offsetY = (realHeight - requiredHeight) / 2;
/*    */       }
/*    */       else
/*    */       {
/* 112 */         offsetX = (realWidth - maxWidth) / 2 + 2;
/* 113 */         offsetY = 0;
/*    */       }
/* 115 */       GL11.glDisable(2896);
/* 116 */       fontRenderer.drawString(header, offsetX - realWidth / 2, 1 + offsetY - realHeight / 2 + -2 * lineHeight, 1);
/* 117 */       fontRenderer.drawString("capacity: ", offsetX - realWidth / 2, 1 + offsetY - realHeight / 2 + 0 * lineHeight, 1);
/* 118 */       fontRenderer.drawString(String.valueOf(topview.getPercentageStorageCapacity()).concat(" % "), offsetX + realWidth / 2 - offsetX - fontRenderer.getStringWidth(String.valueOf(topview.getPercentageStorageCapacity()).concat(" % ")), offsetY - realHeight / 2 - 0 * lineHeight, 1);
/* 119 */       fontRenderer.drawString("range: ", offsetX - realWidth / 2, 1 + offsetY - realHeight / 2 + 1 * lineHeight, 1);
/* 120 */       fontRenderer.drawString(String.valueOf(topview.getTransmitRange()), offsetX + realWidth / 2 - offsetX - fontRenderer.getStringWidth(String.valueOf(topview.getTransmitRange())), offsetY - realHeight / 2 + 1 * lineHeight, 1);
/* 121 */       fontRenderer.drawString("linked device: ", offsetX - realWidth / 2, 1 + offsetY - realHeight / 2 + 2 * lineHeight, 1);
/* 122 */       fontRenderer.drawString(String.valueOf(topview.getLinketProjektor()), offsetX + realWidth / 2 - offsetX - fontRenderer.getStringWidth(String.valueOf(topview.getLinketProjektor())), offsetY - realHeight / 2 + 2 * lineHeight, 1);
/* 123 */       GL11.glEnable(2896);
/* 124 */       GL11.glDepthMask(true);
/* 125 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 126 */       GL11.glDisable(32823);
/* 127 */       GL11.glPopMatrix();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.renderer.TECapacitorRenderer
 * JD-Core Version:    0.6.2
 */