package chb.mods.mffs.common;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.Ic2Recipes;
import ic2.api.Items;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import thermalexpansion.api.crafting.CraftingManagers;
import thermalexpansion.api.crafting.IPulverizerManager;

public class MFFSRecipes
{
  public static void init()
  {
    OreDictionary.registerOre("ForciciumItem", ModularForceFieldSystem.MFFSitemForcicium);
    OreDictionary.registerOre("MonazitOre", ModularForceFieldSystem.MFFSMonazitOre);

    RecipesFactory.addRecipe("uuuuiuuuu", 1, 0, null, ModularForceFieldSystem.MFFSitemForcePowerCrystal);
    RecipesFactory.addRecipe("vvvvvvvvv", 1, 0, null, ModularForceFieldSystem.MFFSProjectorFFStrenght);
    RecipesFactory.addRecipe("vvv   vvv", 1, 0, null, ModularForceFieldSystem.MFFSProjectorFFDistance);

    CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSitemfc) });
    CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemIDCard) });
    CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard) });
    CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSAccessCard) });
    CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSitemDataLinkCard) });

    GameRegistry.addSmelting(ModularForceFieldSystem.MFFSMonazitOre.blockID, new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 4), 0.5F);

    if (ModularForceFieldSystem.ThermalExpansionfound.booleanValue())
    {
      CraftingManagers.pulverizerManager.addRecipe(100, new ItemStack(ModularForceFieldSystem.MFFSMonazitOre, 1), new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 8));
    }

    if (ModularForceFieldSystem.ic2found.booleanValue())
    {
      Ic2Recipes.addMaceratorRecipe(new ItemStack(ModularForceFieldSystem.MFFSMonazitOre, 1), new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 8));
      Ic2Recipes.addMatterAmplifier(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 1), 5000);

      if (ModularForceFieldSystem.uumatterForcicium) {
        Ic2Recipes.addCraftingRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 8), new Object[] { " RR", "R  ", " R ", Character.valueOf('R'), Items.getItem("matter") });
      }

      RecipesFactory.addRecipe("AAAAxAADA", 1, 1, null, ModularForceFieldSystem.MFFSitemForcicumCell);
      RecipesFactory.addRecipe(" E EBE E ", 4, 1, null, ModularForceFieldSystem.MFFSitemupgradeexctractorboost);
      RecipesFactory.addRecipe(" E ExE E ", 1, 1, null, ModularForceFieldSystem.MFFSitemupgradecapcap);
      RecipesFactory.addRecipe("HHHEIEEDE", 1, 1, null, ModularForceFieldSystem.MFFSitemupgradecaprange);
      RecipesFactory.addRecipe("AlAlilAlA", 64, 1, null, ModularForceFieldSystem.MFFSitemFocusmatix);
      RecipesFactory.addRecipe("ooooCoooo", 1, 1, null, ModularForceFieldSystem.MFFSitemcardempty);
      RecipesFactory.addRecipe("mSnExEEDE", 1, 1, null, ModularForceFieldSystem.MFFSitemWrench);
    }

    if (ModularForceFieldSystem.uefound.booleanValue())
    {
      RecipesFactory.addRecipe("AAAAxAAHA", 1, 2, null, ModularForceFieldSystem.MFFSitemForcicumCell);
      RecipesFactory.addRecipe("C C G C C", 9, 2, null, ModularForceFieldSystem.MFFSitemupgradeexctractorboost);
      RecipesFactory.addRecipe(" C CxC C ", 1, 2, null, ModularForceFieldSystem.MFFSitemupgradecapcap);
      RecipesFactory.addRecipe("NNNCICCEC", 1, 2, null, ModularForceFieldSystem.MFFSitemupgradecaprange);
      RecipesFactory.addRecipe("BlBlilBlB", 64, 2, null, ModularForceFieldSystem.MFFSitemFocusmatix);
      RecipesFactory.addRecipe("ooooEoooo", 1, 2, null, ModularForceFieldSystem.MFFSitemcardempty);
      RecipesFactory.addRecipe("mOnDxDDED", 1, 2, null, ModularForceFieldSystem.MFFSitemWrench);
    }
  }
}