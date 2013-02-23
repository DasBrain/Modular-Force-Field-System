package chb.mods.mffs.common;

import basiccomponents.common.BasicComponents;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.Items;
import java.io.PrintStream;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class RecipesFactory
{
  public static boolean addRecipe(String Recipe, int count, int forMod, Block block, Item item)
  {
    if (((forMod > 2) && (forMod < 0)) || (count < 0) || ((block == null) && (item == null)) || ((block != null) && (item != null)) || (Recipe.length() != 9))
    {
      System.out.println("[ModularForceFieldSystem] Recipes generating Fail for :" + block + "/" + item);
      return false;
    }

    ItemStack itemstack = null;

    if ((block != null) && (item == null)) itemstack = new ItemStack(block, count);
    if ((block == null) && (item != null)) itemstack = new ItemStack(item, count);

    String[] recipeSplit = { Recipe.substring(0, 3), Recipe.substring(3, 6), Recipe.substring(6, 9) };

    switch (forMod)
    {
    case 0:
      GameRegistry.addRecipe(itemstack, new Object[] { recipeSplit, Character.valueOf('a'), Item.enderPearl, Character.valueOf('b'), Item.pickaxeSteel, Character.valueOf('c'), Item.bucketEmpty, Character.valueOf('d'), Item.bucketLava, Character.valueOf('e'), Item.bucketWater, Character.valueOf('f'), Item.bone, Character.valueOf('g'), Item.blazeRod, Character.valueOf('h'), Item.rottenFlesh, Character.valueOf('i'), Item.diamond, Character.valueOf('j'), Item.spiderEye, Character.valueOf('k'), Block.obsidian, Character.valueOf('l'), Block.glass, Character.valueOf('m'), Item.redstone, Character.valueOf('n'), Block.lever, Character.valueOf('o'), Item.paper, Character.valueOf('u'), ModularForceFieldSystem.MFFSitemForcicium, Character.valueOf('v'), ModularForceFieldSystem.MFFSitemFocusmatix, Character.valueOf('w'), ModularForceFieldSystem.MFFSProjectorTypkube, Character.valueOf('x'), new ItemStack(ModularForceFieldSystem.MFFSitemForcePowerCrystal, 1, -1), Character.valueOf('y'), ModularForceFieldSystem.MFFSitemFocusmatix, Character.valueOf('z'), ModularForceFieldSystem.MFFSItemIDCard });

      return true;
    case 1:
      if (ModularForceFieldSystem.ic2found.booleanValue())
      {
        GameRegistry.addRecipe(itemstack, new Object[] { recipeSplit, Character.valueOf('a'), Item.enderPearl, Character.valueOf('b'), Item.pickaxeSteel, Character.valueOf('c'), Item.bucketEmpty, Character.valueOf('d'), Item.bucketLava, Character.valueOf('e'), Item.bucketWater, Character.valueOf('f'), Item.bone, Character.valueOf('g'), Item.blazeRod, Character.valueOf('h'), Item.rottenFlesh, Character.valueOf('i'), Item.diamond, Character.valueOf('j'), Item.spiderEye, Character.valueOf('k'), Block.obsidian, Character.valueOf('l'), Block.glass, Character.valueOf('m'), Item.redstone, Character.valueOf('n'), Block.lever, Character.valueOf('o'), Item.paper, Character.valueOf('u'), ModularForceFieldSystem.MFFSitemForcicium, Character.valueOf('v'), ModularForceFieldSystem.MFFSitemFocusmatix, Character.valueOf('w'), ModularForceFieldSystem.MFFSProjectorTypkube, Character.valueOf('x'), new ItemStack(ModularForceFieldSystem.MFFSitemForcePowerCrystal, 1, -1), Character.valueOf('y'), ModularForceFieldSystem.MFFSitemFocusmatix, Character.valueOf('z'), ModularForceFieldSystem.MFFSItemIDCard, Character.valueOf('A'), Items.getItem("refinedIronIngot"), Character.valueOf('B'), Items.getItem("overclockerUpgrade"), Character.valueOf('C'), Items.getItem("electronicCircuit"), Character.valueOf('D'), Items.getItem("advancedCircuit"), Character.valueOf('E'), Items.getItem("carbonPlate"), Character.valueOf('F'), Items.getItem("advancedMachine"), Character.valueOf('G'), Items.getItem("extractor"), Character.valueOf('H'), Items.getItem("copperCableItem"), Character.valueOf('I'), Items.getItem("insulatedCopperCableItem"), Character.valueOf('J'), Items.getItem("frequencyTransmitter"), Character.valueOf('K'), Items.getItem("advancedAlloy"), Character.valueOf('M'), Items.getItem("glassFiberCableItem"), Character.valueOf('N'), Items.getItem("lvTransformer"), Character.valueOf('O'), Items.getItem("mvTransformer"), Character.valueOf('P'), Items.getItem("hvTransformer"), Character.valueOf('Q'), Items.getItem("teslaCoil"), Character.valueOf('R'), Items.getItem("matter"), Character.valueOf('S'), Items.getItem("wrench") });

        return true;
      }
      break;
    case 2:
      if (ModularForceFieldSystem.uefound.booleanValue())
      {
        GameRegistry.addRecipe(itemstack, new Object[] { recipeSplit, Character.valueOf('a'), Item.enderPearl, Character.valueOf('b'), Item.pickaxeSteel, Character.valueOf('c'), Item.bucketEmpty, Character.valueOf('d'), Item.bucketLava, Character.valueOf('e'), Item.bucketWater, Character.valueOf('f'), Item.bone, Character.valueOf('g'), Item.blazeRod, Character.valueOf('h'), Item.rottenFlesh, Character.valueOf('i'), Item.diamond, Character.valueOf('j'), Item.spiderEye, Character.valueOf('k'), Block.obsidian, Character.valueOf('l'), Block.glass, Character.valueOf('m'), Item.redstone, Character.valueOf('n'), Block.lever, Character.valueOf('o'), Item.paper, Character.valueOf('u'), ModularForceFieldSystem.MFFSitemForcicium, Character.valueOf('v'), ModularForceFieldSystem.MFFSitemFocusmatix, Character.valueOf('w'), ModularForceFieldSystem.MFFSProjectorTypkube, Character.valueOf('x'), new ItemStack(ModularForceFieldSystem.MFFSitemForcePowerCrystal, 1, -1), Character.valueOf('y'), ModularForceFieldSystem.MFFSitemFocusmatix, Character.valueOf('z'), ModularForceFieldSystem.MFFSItemIDCard, Character.valueOf('A'), BasicComponents.itemSteelIngot, Character.valueOf('B'), BasicComponents.itemBronzePlate, Character.valueOf('C'), BasicComponents.itemSteelPlate, Character.valueOf('D'), BasicComponents.itemTinPlate, Character.valueOf('E'), new ItemStack(BasicComponents.itemCircuit, 1, 0), Character.valueOf('F'), new ItemStack(BasicComponents.itemCircuit, 1, 1), Character.valueOf('G'), new ItemStack(BasicComponents.itemCircuit, 1, 2), Character.valueOf('H'), BasicComponents.itemMotor, Character.valueOf('I'), new ItemStack(BasicComponents.blockCopperWire, 1, 0), Character.valueOf('J'), BasicComponents.batteryBox, Character.valueOf('K'), BasicComponents.coalGenerator, Character.valueOf('M'), BasicComponents.electricFurnace, Character.valueOf('N'), BasicComponents.itemCopperIngot, Character.valueOf('O'), BasicComponents.itemWrench });

        return true;
      }

      break;
    }

    return false;
  }
}