package mffs.client.gui;

import java.util.ArrayList;
import java.util.List;

import mffs.common.ModularForceFieldSystem;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class GuiManuelScreen extends GuiContainer
{
	private int page = 0;
	private int maxpage;
	private List pages = new ArrayList();

	public GuiManuelScreen(Container par1Container)
	{
		super(par1Container);
		generateIndex();
		this.maxpage = (this.pages.size() - 1);
		this.xSize = 256;
		this.ySize = 216;
	}

	public void initGui()
	{
		this.controlList.add(new GuiButton(0, this.width / 2 + 90, this.height / 2 + 80, 22, 16, "-->"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 110, this.height / 2 + 80, 22, 16, "<--"));
		super.initGui();
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			if (this.page < this.maxpage)
				this.page += 1;
			else
				this.page = 0;
		}
		if (guibutton.id == 1)
		{
			if (this.page > 0)
				this.page -= 1;
			else
				this.page = (this.pages.size() - 1);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int textur = this.mc.renderEngine.getTexture("/chb/mods/mffs/sprites/GuiManuel.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(textur);
		int w = (this.width - 256) / 2;
		int k = (this.height - 216) / 2;
		drawTexturedModalRect(w, k, 0, 0, 256, 216);
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString("ModularForceFieldSystem Guide", 20, 15, 16777215);
		getcontent(this.page);
		this.fontRenderer.drawString("Page [" + this.page + "] :" + (String) this.pages.get(this.page), 45, 193, 16777215);
	}

	private void generateIndex()
	{
		this.pages.clear();
		this.pages.add("Table of Content");
		this.pages.add("Changelog");
		this.pages.add("Version Check");
		this.pages.add("Monazit/Forcicium/-Cell");
		this.pages.add("Card Overview(1)");
		this.pages.add("Card Overview(2)");
	}

	private void getcontent(int page)
	{
		RenderItem renderItem = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();

		switch (page)
		{
			case 0:
				this.fontRenderer.drawString("Table of Contents", 90, 45, 16777215);
				for (int p = 0; p < this.pages.size(); p++)
				{
					this.fontRenderer.drawString("[" + p + "]: " + (String) this.pages.get(p), 20, 65 + p * 10, 16777215);
				}
				break;
			case 1:
				this.fontRenderer.drawString("Changelog V2.2.8.3.6", 90, 45, 16777215);
				this.fontRenderer.drawString("fix Coverter Powerloop", 20, 65, 16777215);
				this.fontRenderer.drawString("fix Textur Bug ", 20, 75, 16777215);
				this.fontRenderer.drawString("change ForceField -> ", 20, 85, 16777215);
				this.fontRenderer.drawString("touch damage system", 20, 95, 16777215);

				break;
			case 2:
				this.fontRenderer.drawString("Versions Check", 90, 45, 16777215);
				this.fontRenderer.drawString("Current Version: " + ModularForceFieldSystem.Versionlocal, 20, 65, 16777215);
				this.fontRenderer.drawString("Newest Version : " + ModularForceFieldSystem.Versionremote, 20, 75, 16777215);
				break;
			case 3:
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSMonazitOre), 30, 45);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemForcicium), 30, 65);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemForcicumCell), 30, 85);

				this.fontRenderer.drawString("Monazit Ore (Block/WorldGen)", 60, 50, 16777215);
				this.fontRenderer.drawString("Forcicium (Item/for Crafting)", 60, 70, 16777215);
				this.fontRenderer.drawString("Forcicium Cell (Item/from Crafting)", 60, 90, 16777215);

				this.fontRenderer.drawString("Monazite can be found between 80 and 0", 20, 105, 16777215);
				this.fontRenderer.drawString("Use furnace to get 4 Forcicium", 20, 115, 16777215);
				this.fontRenderer.drawString("Use IC Macerator to get 8 Forcicium", 20, 125, 16777215);
				this.fontRenderer.drawString("Forcicium Cell can store 1kForcicium", 20, 135, 16777215);
				this.fontRenderer.drawString("if in hand right click to activate", 20, 145, 16777215);
				this.fontRenderer.drawString("when active remove Forcicium from  ", 20, 155, 16777215);
				this.fontRenderer.drawString("Player Inventory and stores it", 20, 165, 16777215);
				break;
			case 4:
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 15, 45);
				this.fontRenderer.drawString("Card <blank> get from Crafting stackable ", 35, 45, 16777215);
				this.fontRenderer.drawString("up to 16 need for create all Cards ", 35, 55, 16777215);

				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemfc), 15, 95);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSCapacitor), 35, 105);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 140, 105);
				this.fontRenderer.drawString("Card <Power Link> get from right click", 35, 95, 16777215);
				this.fontRenderer.drawString("Capacitor with a ", 55, 110, 16777215);

				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard), 15, 145);
				this.fontRenderer.drawString("Card <Security Station Link> get from", 35, 145, 16777215);
				this.fontRenderer.drawString("right click", 35, 160, 16777215);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSSecurtyStation), 90, 155);
				this.fontRenderer.drawString("SecurityStation with a ", 110, 160, 16777215);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 220, 155);

				break;
			case 5:
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSAccessCard), 15, 45);
				this.fontRenderer.drawString("Card <Access license> create inside a  ", 35, 45, 16777215);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSSecurtyStation), 35, 55);
				this.fontRenderer.drawString("SecurityStation with a", 55, 60, 16777215);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), 170, 55);

				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSItemIDCard), 15, 85);
				this.fontRenderer.drawString("Card <Personal ID> create with help  ", 35, 85, 16777215);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemMFDidtool), 35, 100);
				this.fontRenderer.drawString("MultiTool right click create self", 55, 98, 16777215);
				this.fontRenderer.drawString("or left click for another Player", 55, 110, 16777215);

				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemDataLinkCard), 15, 125);
				this.fontRenderer.drawString("Card <Data Link> create with help  ", 35, 125, 16777215);
				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemMFDidtool), 35, 140);
				this.fontRenderer.drawString("MultiTool right click any ", 55, 138, 16777215);
				this.fontRenderer.drawString("MFFS Maschine ", 55, 150, 16777215);

				renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, new ItemStack(ModularForceFieldSystem.MFFSitemInfinitePowerCard), 15, 165);
				this.fontRenderer.drawString("Admin Infinite Force Energy Card", 35, 170, 16777215);
		}

		RenderHelper.disableStandardItemLighting();
	}
}