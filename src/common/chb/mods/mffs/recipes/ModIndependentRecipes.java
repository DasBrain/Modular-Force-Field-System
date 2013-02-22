package chb.mods.mffs.recipes;

import chb.mods.mffs.common.ModularForceFieldSystem;
import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ModIndependentRecipes {
	
	public static void init()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSForciciumBlock, 1),
				new Object[] { "AAA", "AAA", "AAA", 'A',ModularForceFieldSystem.MFFSitemForcicium});
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium,9),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSForciciumBlock) });
		
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSitemfc) });
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemIDCard) });
		
		CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty),new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard) });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcePowerCrystal), new Object[] { "BBB", "BAB", "BBB", 'A', Item.diamond,'B', ModularForceFieldSystem.MFFSitemForcicium});
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypcontainment), new Object[] {
			"AAA", "ABA", "AAA", 'B',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'A', Block.obsidian });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypAdvCube), new Object[] {
			"AAA", "ABA", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'B', ModularForceFieldSystem.MFFSProjectorTypkube });
			
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypsphere),
				new Object[] { " B ", "BAB", " B ", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'B',
						Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypkube), new Object[] {
				"B B", " A ", "B B", 'A',
				ModularForceFieldSystem.MFFSitemFocusmatix, 'B', Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypwall), new Object[] {
				"AA ", "AA ", "BB ", 'A',
				ModularForceFieldSystem.MFFSitemFocusmatix, 'B', Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTypdeflector),
				new Object[] { "AAA", "ABA", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix, 'B',
						Block.obsidian });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorTyptube), new Object[] {
				"AAA", " B ", "AAA", 'A',
				ModularForceFieldSystem.MFFSitemFocusmatix, 'B', Block.obsidian });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorFFStrenght),
				new Object[] { "AAA", "AAA", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorFFDistance),
				new Object[] { "AAA", "   ", "AAA", 'A',
			ModularForceFieldSystem.MFFSitemFocusmatix });
	}

}
