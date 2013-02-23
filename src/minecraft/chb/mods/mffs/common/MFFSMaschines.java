package chb.mods.mffs.common;

import ic2.api.ExplosionWhitelist;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum MFFSMaschines
{
  Projector(1, "MFFSProjector", "Projector", "TileEntityProjector", "GuiProjector", "ContainerProjector", ModularForceFieldSystem.MFFSProjector, 0, "KyKyFyKJK", "ByByKyBaB"), 
  Extractor(2, "MFFSExtractor", "Extractor", "TileEntityExtractor", "GuiExtractor", "ContainerForceEnergyExtractor", ModularForceFieldSystem.MFFSExtractor, 0, " C xFx G ", " E xKx J "), 
  Capacitor(3, "MFFSCapacitor", "Capacitor", "TileEntityCapacitor", "GuiCapacitor", "ContainerCapacitor", ModularForceFieldSystem.MFFSCapacitor, 0, "xJxCFCxJx", "xaxEKExax"), 
  Converter(4, "MFFSForceEnergyConverter", "Converter", "TileEntityConverter", "GuiConverter", "ContainerConverter", ModularForceFieldSystem.MFFSForceEnergyConverter, 0, "ANAJOMAPA", "AKAaJIAMA"), 
  DefenceStation(5, "MFFSDefenceStation", "Defence Station", "TileEntityAreaDefenseStation", "GuiAreaDefenseStation", "ContainerAreaDefenseStation", ModularForceFieldSystem.MFFSDefenceStation, 0, " J aFa E ", " a EKE C "), 
  SecurityStation(6, "MFFSSecurtyStation", "Security Station", "TileEntityAdvSecurityStation", "GuiAdvSecurityStation", "ContainerAdvSecurityStation", ModularForceFieldSystem.MFFSSecurtyStation, 0, "KCKCFCKJK", "CECEKECaC"), 
  SecurityStorage(7, "MFFSSecurtyStorage", "Security Storage", "TileEntitySecStorage", "GuiSecStorage", "ContainerSecStorage", ModularForceFieldSystem.MFFSSecurtyStorage, 0, "AAAACAAAA", "AAAAEAAAA"), 
  ControlSystem(8, "MFFSControlSystem", "Control System", "TileEntityControlSystem", "GuiControlSystem", "ContainerControlSystem", ModularForceFieldSystem.MFFSControlSystem, 0, "aCaAFAACA", "aEaAKAAEA");

  public int index;
  public String inCodeName;
  public String displayName;
  public Class clazz;
  public String Gui;
  public Class Container;
  public Block block;
  public String recipeic;
  public String recipeue;
  public int baseTex;

  private MFFSMaschines(int index, String nm, String dispNm, String cls, String gui, String container, Block block, int baseTex, String recipeic, String recipeue) { this.index = index;
    this.inCodeName = nm;
    this.displayName = dispNm;
    this.Gui = gui;
    try { this.clazz = Class.forName("chb.mods.mffs.common.tileentity." + cls); } catch (ClassNotFoundException ex) { this.clazz = null; } try {
      this.Container = Class.forName("chb.mods.mffs.common.container." + container); } catch (ClassNotFoundException ex) { this.Container = null; }
    this.recipeic = recipeic;
    this.recipeue = recipeue;
    this.block = block;
    this.baseTex = baseTex;
  }

  public static MFFSMaschines fromdisplayName(String displayName)
  {
    for (MFFSMaschines mach : values()) {
      if (mach.displayName.equals(displayName))
      {
        return mach;
      }
    }
    return null;
  }

  public static MFFSMaschines fromTE(TileEntity tem)
  {
    for (MFFSMaschines mach : values()) {
      if (mach.clazz.isInstance(tem))
      {
        return mach;
      }
    }
    return null;
  }

  public static void initialize()
  {
    for (MFFSMaschines mach : values())
    {
      GameRegistry.registerBlock(mach.block, mach.inCodeName);
      GameRegistry.registerTileEntity(mach.clazz, mach.inCodeName);

      if (ModularForceFieldSystem.ic2found.booleanValue()) RecipesFactory.addRecipe(mach.recipeic, 1, 1, mach.block, null);
      if (ModularForceFieldSystem.uefound.booleanValue()) RecipesFactory.addRecipe(mach.recipeue, 1, 2, mach.block, null);

      LanguageRegistry.instance().addNameForObject(mach.block, "en_US", "MFFS " + mach.displayName);
      ExplosionWhitelist.addWhitelistedBlock(mach.block);
    }
  }
}