/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*    */ import ic2.api.ExplosionWhitelist;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public enum MFFSMaschines
/*    */ {
/* 36 */   Projector(1, "MFFSProjector", "Projector", "TileEntityProjector", "GuiProjector", "ContainerProjector", ModularForceFieldSystem.MFFSProjector, 0, "KyKyFyKJK", "ByByKyBaB"), 
/* 37 */   Extractor(2, "MFFSExtractor", "Extractor", "TileEntityExtractor", "GuiExtractor", "ContainerForceEnergyExtractor", ModularForceFieldSystem.MFFSExtractor, 0, " C xFx G ", " E xKx J "), 
/* 38 */   Capacitor(3, "MFFSCapacitor", "Capacitor", "TileEntityCapacitor", "GuiCapacitor", "ContainerCapacitor", ModularForceFieldSystem.MFFSCapacitor, 0, "xJxCFCxJx", "xaxEKExax"), 
/* 39 */   Converter(4, "MFFSForceEnergyConverter", "Converter", "TileEntityConverter", "GuiConverter", "ContainerConverter", ModularForceFieldSystem.MFFSForceEnergyConverter, 0, "ANAJOMAPA", "AKAaJIAMA"), 
/* 40 */   DefenceStation(5, "MFFSDefenceStation", "Defence Station", "TileEntityAreaDefenseStation", "GuiAreaDefenseStation", "ContainerAreaDefenseStation", ModularForceFieldSystem.MFFSDefenceStation, 0, " J aFa E ", " a EKE C "), 
/* 41 */   SecurityStation(6, "MFFSSecurtyStation", "Security Station", "TileEntityAdvSecurityStation", "GuiAdvSecurityStation", "ContainerAdvSecurityStation", ModularForceFieldSystem.MFFSSecurtyStation, 0, "KCKCFCKJK", "CECEKECaC"), 
/* 42 */   SecurityStorage(7, "MFFSSecurtyStorage", "Security Storage", "TileEntitySecStorage", "GuiSecStorage", "ContainerSecStorage", ModularForceFieldSystem.MFFSSecurtyStorage, 0, "AAAACAAAA", "AAAAEAAAA"), 
/* 43 */   ControlSystem(8, "MFFSControlSystem", "Control System", "TileEntityControlSystem", "GuiControlSystem", "ContainerControlSystem", ModularForceFieldSystem.MFFSControlSystem, 0, "aCaAFAACA", "aEaAKAAEA");
/*    */ 
/*    */   public int index;
/*    */   public String inCodeName;
/*    */   public String displayName;
/*    */   public Class clazz;
/*    */   public String Gui;
/*    */   public Class Container;
/*    */   public Block block;
/*    */   public String recipeic;
/*    */   public String recipeue;
/*    */   public int baseTex;
/*    */ 
/* 60 */   private MFFSMaschines(int index, String nm, String dispNm, String cls, String gui, String container, Block block, int baseTex, String recipeic, String recipeue) { this.index = index;
/* 61 */     this.inCodeName = nm;
/* 62 */     this.displayName = dispNm;
/* 63 */     this.Gui = gui;
/*    */     try { this.clazz = Class.forName("chb.mods.mffs.common.tileentity." + cls); } catch (ClassNotFoundException ex) { this.clazz = null; } try {
/* 65 */       this.Container = Class.forName("chb.mods.mffs.common.container." + container); } catch (ClassNotFoundException ex) { this.Container = null; }
/* 66 */     this.recipeic = recipeic;
/* 67 */     this.recipeue = recipeue;
/* 68 */     this.block = block;
/* 69 */     this.baseTex = baseTex;
/*    */   }
/*    */ 
/*    */   public static MFFSMaschines fromdisplayName(String displayName)
/*    */   {
/* 74 */     for (MFFSMaschines mach : values()) {
/* 75 */       if (mach.displayName.equals(displayName))
/*    */       {
/* 77 */         return mach;
/*    */       }
/*    */     }
/* 80 */     return null;
/*    */   }
/*    */ 
/*    */   public static MFFSMaschines fromTE(TileEntity tem)
/*    */   {
/* 85 */     for (MFFSMaschines mach : values()) {
/* 86 */       if (mach.clazz.isInstance(tem))
/*    */       {
/* 88 */         return mach;
/*    */       }
/*    */     }
/* 91 */     return null;
/*    */   }
/*    */ 
/*    */   public static void initialize()
/*    */   {
/* 97 */     for (MFFSMaschines mach : values())
/*    */     {
/* 99 */       GameRegistry.registerBlock(mach.block, mach.inCodeName);
/* 100 */       GameRegistry.registerTileEntity(mach.clazz, mach.inCodeName);
/*    */ 
/* 102 */       if (ModularForceFieldSystem.ic2found.booleanValue()) RecipesFactory.addRecipe(mach.recipeic, 1, 1, mach.block, null);
/* 103 */       if (ModularForceFieldSystem.uefound.booleanValue()) RecipesFactory.addRecipe(mach.recipeue, 1, 2, mach.block, null);
/*    */ 
/* 105 */       LanguageRegistry.instance().addNameForObject(mach.block, "en_US", "MFFS " + mach.displayName);
/* 106 */       ExplosionWhitelist.addWhitelistedBlock(mach.block);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.MFFSMaschines
 * JD-Core Version:    0.6.2
 */