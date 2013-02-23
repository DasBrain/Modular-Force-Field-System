/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public enum ProjectorOptions
/*    */ {
/* 34 */   Zapper("<touch damage>", ModularForceFieldSystem.MFFSProjectorOptionZapper, " K KQK K ", " C CdC C "), 
/* 35 */   Subwater("<Sponge>", ModularForceFieldSystem.MFFSProjectorOptionSubwater, " K KcK K ", " C CcC C "), 
/* 36 */   Dome("<Field Manipulator>", ModularForceFieldSystem.MFFSProjectorOptionDome, " K KCK K ", " C CEC C "), 
/* 37 */   Cutter("<Block Breaker>", ModularForceFieldSystem.MFFSProjectorOptionCutter, " K KbK K ", " C CbC C "), 
/* 38 */   FieldJammer("<Force Field Jammer>", ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer, " J JvJ J ", " a ava a "), 
/* 39 */   Camouflage("<Camouflage>", ModularForceFieldSystem.MFFSProjectorOptionCamouflage, " K KRK K ", " C CGC C "), 
/* 40 */   FieldFusion("<Field Fusion>", ModularForceFieldSystem.MFFSProjectorOptionFieldFusion, " K KDK K ", " C CFC C "), 
/* 41 */   MoobEx("<NPC Defense>", ModularForceFieldSystem.MFFSProjectorOptionMoobEx, "fgfhQhjgj", "fgfhdhjgj"), 
/* 42 */   DefenceStation("<Defense Station>", ModularForceFieldSystem.MFFSProjectorOptionDefenceStation, " z CQC z ", " z EdE z ");
/*    */ 
/*    */   String displayName;
/*    */   Item item;
/*    */   String recipeic;
/*    */   String recipeue;
/*    */ 
/*    */   private ProjectorOptions(String dispNm, Item item, String recipeic, String recipeue) {
/* 53 */     this.displayName = dispNm;
/* 54 */     this.item = item;
/* 55 */     this.recipeic = recipeic;
/* 56 */     this.recipeue = recipeue;
/*    */   }
/*    */ 
/*    */   public static void initialize()
/*    */   {
/* 65 */     for (ProjectorOptions mach : values())
/*    */     {
/* 67 */       LanguageRegistry.instance().addNameForObject(mach.item, "en_US", " MFFS Projector Upgrade " + mach.displayName);
/*    */ 
/* 69 */       if (ModularForceFieldSystem.ic2found.booleanValue()) RecipesFactory.addRecipe(mach.recipeic, 1, 1, null, mach.item);
/* 70 */       if (ModularForceFieldSystem.uefound.booleanValue()) RecipesFactory.addRecipe(mach.recipeue, 1, 2, null, mach.item);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ProjectorOptions
 * JD-Core Version:    0.6.2
 */