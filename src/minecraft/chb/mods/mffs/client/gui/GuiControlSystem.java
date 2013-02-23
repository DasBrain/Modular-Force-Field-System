/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.client.GraphicButton;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.container.ContainerControlSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*     */ import chb.mods.mffs.network.client.NetworkHandlerClient;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderEngine;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiControlSystem extends GuiContainer
/*     */ {
/*     */   private TileEntityControlSystem ControlSystem;
/*  43 */   private boolean editMode = false;
/*     */   private EntityPlayer player;
/*     */ 
/*     */   public GuiControlSystem(EntityPlayer player, TileEntityControlSystem tileentity)
/*     */   {
/*  47 */     super(new ContainerControlSystem(player, tileentity));
/*  48 */     this.ControlSystem = tileentity;
/*  49 */     this.xSize = 256;
/*  50 */     this.ySize = 216;
/*  51 */     this.player = player;
/*     */   }
/*     */ 
/*     */   protected void keyTyped(char c, int i)
/*     */   {
/*  57 */     if ((i != 1) && (this.editMode)) {
/*  58 */       if (c == '\r') {
/*  59 */         this.editMode = false;
/*  60 */         return;
/*     */       }
/*     */ 
/*  63 */       if (i == 14) {
/*  64 */         NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, 12, "");
/*     */       }
/*  66 */       if ((i != 54) && (i != 42) && (i != 58) && (i != 14))
/*  67 */         NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, 11, String.valueOf(c));
/*     */     }
/*     */     else {
/*  70 */       super.keyTyped(c, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void mouseClicked(int i, int j, int k)
/*     */   {
/*  77 */     super.mouseClicked(i, j, k);
/*     */ 
/*  79 */     int xMin = (this.width - this.xSize) / 2;
/*  80 */     int yMin = (this.height - this.ySize) / 2;
/*     */ 
/*  82 */     int x = i - xMin;
/*  83 */     int y = j - yMin;
/*     */ 
/*  85 */     if (this.editMode) {
/*  86 */       this.editMode = false;
/*  87 */     } else if ((x >= 115) && (y >= 5) && (x <= 234) && (y <= 19)) {
/*  88 */       NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, 10, "null");
/*  89 */       this.editMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  96 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiControlSystem.png");
/*  97 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  98 */     this.mc.renderEngine.bindTexture(textur);
/*  99 */     int w = (this.width - this.xSize) / 2;
/* 100 */     int k = (this.height - this.ySize) / 2;
/* 101 */     drawTexturedModalRect(w, k, 0, 0, this.xSize, this.ySize);
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton)
/*     */   {
/* 107 */     if (guibutton.id == 103)
/* 108 */       NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, guibutton.id, this.player.username);
/*     */     else
/* 110 */       NetworkHandlerClient.fireTileEntityEvent(this.ControlSystem, guibutton.id, "");
/*     */   }
/*     */ 
/*     */   public void initGui()
/*     */   {
/* 116 */     this.controlList.add(new GraphicButton(100, this.width / 2 - 115, this.height / 2 - 45, this.ControlSystem, 1));
/* 117 */     this.controlList.add(new GraphicButton(101, this.width / 2 - 115, this.height / 2 - 25, this.ControlSystem, 2));
/* 118 */     this.controlList.add(new GraphicButton(102, this.width / 2 - 115, this.height / 2 - 5, this.ControlSystem, 3));
/* 119 */     this.controlList.add(new GuiButton(103, this.width / 2 + -65, this.height / 2 - 8, 100, 20, "Open Remote Gui"));
/* 120 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/*     */   {
/* 125 */     this.fontRenderer.drawString("MFFS Control System", 8, 9, 4210752);
/* 126 */     this.fontRenderer.drawString(this.ControlSystem.getDeviceName(), 123, 9, 4210752);
/*     */ 
/* 128 */     this.fontRenderer.drawString("DataLink", 190, 54, 4210752);
/* 129 */     this.fontRenderer.drawString("Reader", 190, 65, 4210752);
/*     */ 
/* 132 */     this.fontRenderer.drawString("Name: " + this.ControlSystem.getRemoteDeviceName(), 15, 30, 4210752);
/* 133 */     this.fontRenderer.drawString("Type:  " + this.ControlSystem.getRemoteDeviceTyp(), 15, 45, 4210752);
/* 134 */     if (this.ControlSystem.getStackInSlot(1) != null) {
/* 135 */       RenderItem renderItem = new RenderItem();
/* 136 */       RenderHelper.enableGUIStandardItemLighting();
/* 137 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard), 40, 59);
/* 138 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemfc), 100, 59);
/* 139 */       RenderHelper.disableStandardItemLighting();
/* 140 */       if (this.ControlSystem.getRemoteSecurityStationlink())
/* 141 */         this.fontRenderer.drawString("linked", 60, 64, 2263842);
/* 142 */       else this.fontRenderer.drawString("linked", 60, 64, 9116186);
/* 143 */       if (this.ControlSystem.getRemotehasPowersource()) {
/* 144 */         this.fontRenderer.drawString("linked", 120, 64, 2263842);
/* 145 */         this.fontRenderer.drawString("Power left: " + this.ControlSystem.getRemotePowerleft() + "%", 40, 80, 4210752);
/*     */       } else {
/* 147 */         this.fontRenderer.drawString("linked", 120, 64, 9116186);
/*     */       }
/* 149 */       if (this.ControlSystem.getRemoteGUIinRange())
/* 150 */         this.fontRenderer.drawString("OK", 40, 107, 2263842);
/* 151 */       else this.fontRenderer.drawString("OOR", 40, 107, 9116186);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiControlSystem
 * JD-Core Version:    0.6.2
 */