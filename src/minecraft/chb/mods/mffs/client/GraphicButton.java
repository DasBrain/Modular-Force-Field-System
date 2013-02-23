/*    */ package chb.mods.mffs.client;
/*    */ 
/*    */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.RenderEngine;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GraphicButton extends GuiButton
/*    */ {
/*    */   private TileEntity tileEntity;
/*    */   private int typ;
/*    */ 
/*    */   public GraphicButton(int par1, int par2, int par3, TileEntity tileEntity, int typ)
/*    */   {
/* 44 */     super(par1, par2, par3, 16, 16, "");
/* 45 */     this.tileEntity = tileEntity;
/* 46 */     this.typ = typ;
/*    */   }
/*    */ 
/*    */   public void drawButton(Minecraft par1Minecraft, int par2, int par3)
/*    */   {
/* 54 */     if (this.drawButton)
/*    */     {
/* 56 */       GL11.glBindTexture(3553, par1Minecraft.renderEngine.getTexture("/chb/mods/mffs/sprites/items.png"));
/* 57 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */ 
/* 59 */       if (((this.tileEntity instanceof TileEntityMachines)) && (this.typ == 0))
/*    */       {
/* 61 */         drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityMachines)this.tileEntity).getSwitchModi() * 16, 112, this.width, this.height);
/*    */       }
/*    */ 
/* 64 */       if ((this.tileEntity instanceof TileEntityConverter))
/*    */       {
/* 66 */         if (this.typ == 1) {
/* 67 */           drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityConverter)this.tileEntity).getIC_Output() * 16, 128, this.width, this.height);
/*    */         }
/* 69 */         if (this.typ == 2) {
/* 70 */           drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityConverter)this.tileEntity).getUE_Output() * 16, 128, this.width, this.height);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 77 */       if ((this.tileEntity instanceof TileEntityControlSystem))
/*    */       {
/* 79 */         if (((TileEntityControlSystem)this.tileEntity).getStackInSlot(1) != null) {
/* 80 */           if (this.typ == 1) {
/* 81 */             if (((TileEntityControlSystem)this.tileEntity).getRemoteActive()) {
/* 82 */               drawTexturedModalRect(this.xPosition, this.yPosition, 176, 80, this.width, this.height);
/*    */             }
/* 84 */             if (!((TileEntityControlSystem)this.tileEntity).getRemoteActive())
/* 85 */               drawTexturedModalRect(this.xPosition, this.yPosition, 192, 80, this.width, this.height);
/*    */           }
/* 87 */           if ((this.typ == 2) && 
/* 88 */             (((TileEntityControlSystem)this.tileEntity).getRemoteSwitchModi() > 0)) {
/* 89 */             drawTexturedModalRect(this.xPosition, this.yPosition, 80 + ((TileEntityControlSystem)this.tileEntity).getRemoteSwitchModi() * 16, 112, this.width, this.height);
/*    */           }
/* 91 */           if ((this.typ == 3) && 
/* 92 */             (((TileEntityControlSystem)this.tileEntity).getRemoteSwitchModi() == 3)) {
/* 93 */             if (((TileEntityControlSystem)this.tileEntity).getRemoteSwitchValue())
/* 94 */               drawTexturedModalRect(this.xPosition, this.yPosition, 208, 80, this.width, this.height);
/*    */             else {
/* 96 */               drawTexturedModalRect(this.xPosition, this.yPosition, 224, 80, this.width, this.height);
/*    */             }
/*    */           }
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 103 */       if ((this.tileEntity instanceof TileEntityAreaDefenseStation))
/*    */       {
/* 105 */         if (this.typ == 1)
/* 106 */           drawTexturedModalRect(this.xPosition, this.yPosition, 176 + ((TileEntityAreaDefenseStation)this.tileEntity).getcontratyp() * 16, 80, this.width, this.height);
/* 107 */         if (this.typ == 2)
/* 108 */           drawTexturedModalRect(this.xPosition, this.yPosition, 64 + ((TileEntityAreaDefenseStation)this.tileEntity).getActionmode() * 16, 96, this.width, this.height);
/* 109 */         if (this.typ == 3) {
/* 110 */           drawTexturedModalRect(this.xPosition, this.yPosition, 160 + ((TileEntityAreaDefenseStation)this.tileEntity).getScanmode() * 16, 96, this.width, this.height);
/*    */         }
/*    */       }
/* 113 */       if ((this.tileEntity instanceof TileEntityCapacitor))
/*    */       {
/* 115 */         if (this.typ == 1) {
/* 116 */           drawTexturedModalRect(this.xPosition, this.yPosition, 96 + ((TileEntityCapacitor)this.tileEntity).getPowerlinkmode() * 16, 80, this.width, this.height);
/*    */         }
/*    */       }
/* 119 */       if ((this.tileEntity instanceof TileEntityProjector))
/*    */       {
/* 121 */         if (this.typ == 1)
/* 122 */           drawTexturedModalRect(this.xPosition, this.yPosition, 0 + ((TileEntityProjector)this.tileEntity).getaccesstyp() * 16, 80, this.width, this.height);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.GraphicButton
 * JD-Core Version:    0.6.2
 */