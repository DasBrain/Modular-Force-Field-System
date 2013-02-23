/*     */ package chb.mods.mffs.client.gui;
/*     */ 
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderEngine;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiManuelScreen extends GuiContainer
/*     */ {
/*  21 */   private int page = 0;
/*     */   private int maxpage;
/*  24 */   private List pages = new ArrayList();
/*     */ 
/*     */   public GuiManuelScreen(Container par1Container) {
/*  27 */     super(par1Container);
/*  28 */     generateIndex();
/*  29 */     this.maxpage = (this.pages.size() - 1);
/*  30 */     this.xSize = 256;
/*  31 */     this.ySize = 216;
/*     */   }
/*     */ 
/*     */   public void initGui()
/*     */   {
/*  37 */     this.controlList.add(new GuiButton(0, this.width / 2 + 90, this.height / 2 + 80, 22, 16, "-->"));
/*  38 */     this.controlList.add(new GuiButton(1, this.width / 2 - 110, this.height / 2 + 80, 22, 16, "<--"));
/*  39 */     super.initGui();
/*     */   }
/*     */ 
/*     */   protected void actionPerformed(GuiButton guibutton)
/*     */   {
/*  46 */     if (guibutton.id == 0)
/*     */     {
/*  48 */       if (this.page < this.maxpage) this.page += 1; else
/*  49 */         this.page = 0;
/*     */     }
/*  51 */     if (guibutton.id == 1)
/*     */     {
/*  53 */       if (this.page > 0) this.page -= 1; else
/*  54 */         this.page = (this.pages.size() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
/*     */   {
/*  61 */     int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiManuel.png");
/*  62 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  63 */     this.mc.renderEngine.bindTexture(textur);
/*  64 */     int w = (this.width - 256) / 2;
/*  65 */     int k = (this.height - 216) / 2;
/*  66 */     drawTexturedModalRect(w, k, 0, 0, 256, 216);
/*     */   }
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/*     */   {
/*  72 */     this.fontRenderer.drawString("ModularForceFieldSystem Guide", 20, 15, 16777215);
/*  73 */     getcontent(this.page);
/*  74 */     this.fontRenderer.drawString("Page [" + this.page + "] :" + (String)this.pages.get(this.page), 45, 193, 16777215);
/*     */   }
/*     */ 
/*     */   private void generateIndex()
/*     */   {
/*  80 */     this.pages.clear();
/*  81 */     this.pages.add("Table of Content");
/*  82 */     this.pages.add("Changelog");
/*  83 */     this.pages.add("Version Check");
/*  84 */     this.pages.add("Monazit/Forcicium/-Cell");
/*  85 */     this.pages.add("Card Overview(1)");
/*  86 */     this.pages.add("Card Overview(2)");
/*     */   }
/*     */ 
/*     */   private void getcontent(int page)
/*     */   {
/*  91 */     RenderItem renderItem = new RenderItem();
/*  92 */     RenderHelper.enableGUIStandardItemLighting();
/*     */ 
/*  94 */     switch (page)
/*     */     {
/*     */     case 0:
/*  97 */       this.fontRenderer.drawString("Table of Contents", 90, 45, 16777215);
/*  98 */       for (int p = 0; p < this.pages.size(); p++)
/*     */       {
/* 100 */         this.fontRenderer.drawString("[" + p + "]: " + (String)this.pages.get(p), 20, 65 + p * 10, 16777215);
/*     */       }
/* 102 */       break;
/*     */     case 1:
/* 104 */       this.fontRenderer.drawString("Changelog V2.2.8.3.6", 90, 45, 16777215);
/* 105 */       this.fontRenderer.drawString("fix Coverter Powerloop", 20, 65, 16777215);
/* 106 */       this.fontRenderer.drawString("fix Textur Bug ", 20, 75, 16777215);
/* 107 */       this.fontRenderer.drawString("change ForceField -> ", 20, 85, 16777215);
/* 108 */       this.fontRenderer.drawString("touch damage system", 20, 95, 16777215);
/*     */ 
/* 110 */       break;
/*     */     case 2:
/* 112 */       this.fontRenderer.drawString("Versions Check", 90, 45, 16777215);
/* 113 */       this.fontRenderer.drawString("Current Version: " + ModularForceFieldSystem.Versionlocal, 20, 65, 16777215);
/* 114 */       this.fontRenderer.drawString("Newest Version : " + ModularForceFieldSystem.Versionremote, 20, 75, 16777215);
/* 115 */       break;
/*     */     case 3:
/* 117 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSMonazitOre), 30, 45);
/* 118 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemForcicium), 30, 65);
/* 119 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemForcicumCell), 30, 85);
/*     */ 
/* 121 */       this.fontRenderer.drawString("Monazit Ore (Block/WorldGen)", 60, 50, 16777215);
/* 122 */       this.fontRenderer.drawString("Forcicium (Item/for Crafting)", 60, 70, 16777215);
/* 123 */       this.fontRenderer.drawString("Forcicium Cell (Item/from Crafting)", 60, 90, 16777215);
/*     */ 
/* 125 */       this.fontRenderer.drawString("Monazite can be found between 80 and 0", 20, 105, 16777215);
/* 126 */       this.fontRenderer.drawString("Use furnace to get 4 Forcicium", 20, 115, 16777215);
/* 127 */       this.fontRenderer.drawString("Use IC Macerator to get 8 Forcicium", 20, 125, 16777215);
/* 128 */       this.fontRenderer.drawString("Forcicium Cell can store 1kForcicium", 20, 135, 16777215);
/* 129 */       this.fontRenderer.drawString("if in hand right click to activate", 20, 145, 16777215);
/* 130 */       this.fontRenderer.drawString("when active remove Forcicium from  ", 20, 155, 16777215);
/* 131 */       this.fontRenderer.drawString("Player Inventory and stores it", 20, 165, 16777215);
/* 132 */       break;
/*     */     case 4:
/* 135 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 15, 45);
/* 136 */       this.fontRenderer.drawString("Card <blank> get from Crafting stackable ", 35, 45, 16777215);
/* 137 */       this.fontRenderer.drawString("up to 16 need for create all Cards ", 35, 55, 16777215);
/*     */ 
/* 139 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemfc), 15, 95);
/* 140 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSCapacitor), 35, 105);
/* 141 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 140, 105);
/* 142 */       this.fontRenderer.drawString("Card <Power Link> get from right click", 35, 95, 16777215);
/* 143 */       this.fontRenderer.drawString("Capacitor with a ", 55, 110, 16777215);
/*     */ 
/* 145 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard), 15, 145);
/* 146 */       this.fontRenderer.drawString("Card <Security Station Link> get from", 35, 145, 16777215);
/* 147 */       this.fontRenderer.drawString("right click", 35, 160, 16777215);
/* 148 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSSecurtyStation), 90, 155);
/* 149 */       this.fontRenderer.drawString("SecurityStation with a ", 110, 160, 16777215);
/* 150 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 220, 155);
/*     */ 
/* 152 */       break;
/*     */     case 5:
/* 155 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSAccessCard), 15, 45);
/* 156 */       this.fontRenderer.drawString("Card <Access license> create inside a  ", 35, 45, 16777215);
/* 157 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSSecurtyStation), 35, 55);
/* 158 */       this.fontRenderer.drawString("SecurityStation with a", 55, 60, 16777215);
/* 159 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 170, 55);
/*     */ 
/* 162 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSItemIDCard), 15, 85);
/* 163 */       this.fontRenderer.drawString("Card <Personal ID> create with help  ", 35, 85, 16777215);
/* 164 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemMFDidtool), 35, 100);
/* 165 */       this.fontRenderer.drawString("MultiTool right click create self", 55, 98, 16777215);
/* 166 */       this.fontRenderer.drawString("or left click for another Player", 55, 110, 16777215);
/*     */ 
/* 168 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemDataLinkCard), 15, 125);
/* 169 */       this.fontRenderer.drawString("Card <Data Link> create with help  ", 35, 125, 16777215);
/* 170 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemMFDidtool), 35, 140);
/* 171 */       this.fontRenderer.drawString("MultiTool right click any ", 55, 138, 16777215);
/* 172 */       this.fontRenderer.drawString("MFFS Maschine ", 55, 150, 16777215);
/*     */ 
/* 174 */       renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemInfinitePowerCard), 15, 165);
/* 175 */       this.fontRenderer.drawString("Admin Infinite Force Energy Card", 35, 170, 16777215);
/*     */     }
/*     */ 
/* 179 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.gui.GuiManuelScreen
 * JD-Core Version:    0.6.2
 */