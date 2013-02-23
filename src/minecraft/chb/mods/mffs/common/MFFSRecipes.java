/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import ic2.api.Ic2Recipes;
/*    */ import ic2.api.Items;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ import thermalexpansion.api.crafting.CraftingManagers;
/*    */ import thermalexpansion.api.crafting.IPulverizerManager;
/*    */ 
/*    */ public class MFFSRecipes
/*    */ {
/*    */   public static void init()
/*    */   {
/* 41 */     OreDictionary.registerOre("ForciciumItem", ModularForceFieldSystem.MFFSitemForcicium);
/* 42 */     OreDictionary.registerOre("MonazitOre", ModularForceFieldSystem.MFFSMonazitOre);
/*    */ 
/* 44 */     RecipesFactory.addRecipe("uuuuiuuuu", 1, 0, null, ModularForceFieldSystem.MFFSitemForcePowerCrystal);
/* 45 */     RecipesFactory.addRecipe("vvvvvvvvv", 1, 0, null, ModularForceFieldSystem.MFFSProjectorFFStrenght);
/* 46 */     RecipesFactory.addRecipe("vvv   vvv", 1, 0, null, ModularForceFieldSystem.MFFSProjectorFFDistance);
/*    */ 
/* 48 */     CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSitemfc) });
/* 49 */     CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemIDCard) });
/* 50 */     CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard) });
/* 51 */     CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSAccessCard) });
/* 52 */     CraftingManager.getInstance().addShapelessRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] { new ItemStack(ModularForceFieldSystem.MFFSitemDataLinkCard) });
/*    */ 
/* 54 */     GameRegistry.addSmelting(ModularForceFieldSystem.MFFSMonazitOre.blockID, new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 4), 0.5F);
/*    */ 
/* 56 */     if (ModularForceFieldSystem.ThermalExpansionfound.booleanValue())
/*    */     {
/* 58 */       CraftingManagers.pulverizerManager.addRecipe(100, new ItemStack(ModularForceFieldSystem.MFFSMonazitOre, 1), new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 8));
/*    */     }
/*    */ 
/* 61 */     if (ModularForceFieldSystem.ic2found.booleanValue())
/*    */     {
/* 63 */       Ic2Recipes.addMaceratorRecipe(new ItemStack(ModularForceFieldSystem.MFFSMonazitOre, 1), new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 8));
/* 64 */       Ic2Recipes.addMatterAmplifier(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 1), 5000);
/*    */ 
/* 66 */       if (ModularForceFieldSystem.uumatterForcicium) {
/* 67 */         Ic2Recipes.addCraftingRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, 8), new Object[] { " RR", "R  ", " R ", Character.valueOf('R'), Items.getItem("matter") });
/*    */       }
/*    */ 
/* 70 */       RecipesFactory.addRecipe("AAAAxAADA", 1, 1, null, ModularForceFieldSystem.MFFSitemForcicumCell);
/* 71 */       RecipesFactory.addRecipe(" E EBE E ", 4, 1, null, ModularForceFieldSystem.MFFSitemupgradeexctractorboost);
/* 72 */       RecipesFactory.addRecipe(" E ExE E ", 1, 1, null, ModularForceFieldSystem.MFFSitemupgradecapcap);
/* 73 */       RecipesFactory.addRecipe("HHHEIEEDE", 1, 1, null, ModularForceFieldSystem.MFFSitemupgradecaprange);
/* 74 */       RecipesFactory.addRecipe("AlAlilAlA", 64, 1, null, ModularForceFieldSystem.MFFSitemFocusmatix);
/* 75 */       RecipesFactory.addRecipe("ooooCoooo", 1, 1, null, ModularForceFieldSystem.MFFSitemcardempty);
/* 76 */       RecipesFactory.addRecipe("mSnExEEDE", 1, 1, null, ModularForceFieldSystem.MFFSitemWrench);
/*    */     }
/*    */ 
/* 79 */     if (ModularForceFieldSystem.uefound.booleanValue())
/*    */     {
/* 81 */       RecipesFactory.addRecipe("AAAAxAAHA", 1, 2, null, ModularForceFieldSystem.MFFSitemForcicumCell);
/* 82 */       RecipesFactory.addRecipe("C C G C C", 9, 2, null, ModularForceFieldSystem.MFFSitemupgradeexctractorboost);
/* 83 */       RecipesFactory.addRecipe(" C CxC C ", 1, 2, null, ModularForceFieldSystem.MFFSitemupgradecapcap);
/* 84 */       RecipesFactory.addRecipe("NNNCICCEC", 1, 2, null, ModularForceFieldSystem.MFFSitemupgradecaprange);
/* 85 */       RecipesFactory.addRecipe("BlBlilBlB", 64, 2, null, ModularForceFieldSystem.MFFSitemFocusmatix);
/* 86 */       RecipesFactory.addRecipe("ooooEoooo", 1, 2, null, ModularForceFieldSystem.MFFSitemcardempty);
/* 87 */       RecipesFactory.addRecipe("mOnDxDDED", 1, 2, null, ModularForceFieldSystem.MFFSitemWrench);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.MFFSRecipes
 * JD-Core Version:    0.6.2
 */