/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public enum ProjectorTyp
/*    */ {
/* 37 */   wall(1, "Wall", "AA AA BB ", ModularForceFieldSystem.MFFSProjectorTypwall, true), 
/* 38 */   deflector(2, "Deflector", "AAAABAAAA", ModularForceFieldSystem.MFFSProjectorTypdeflector, true), 
/* 39 */   tube(3, "Tube", "AAA B AAA", ModularForceFieldSystem.MFFSProjectorTyptube, false), 
/* 40 */   cube(4, "Cube", "B B A B B", ModularForceFieldSystem.MFFSProjectorTypkube, false), 
/* 41 */   sphere(5, "Sphere", " B BAB B ", ModularForceFieldSystem.MFFSProjectorTypsphere, false), 
/* 42 */   containment(6, "Containment", "BBBBABBBB", ModularForceFieldSystem.MFFSProjectorTypcontainment, false), 
/* 43 */   AdvCube(7, "Adv.Cube", "AAAACAAAA", ModularForceFieldSystem.MFFSProjectorTypAdvCube, false), 
/* 44 */   diagonallywall(8, "diagonal Wall", "A A B A A", ModularForceFieldSystem.MFFSProjectorTypdiagowall, true);
/*    */ 
/*    */   public String displayName;
/*    */   public String recipe;
/*    */   public Item item;
/*    */   public int ProTyp;
/*    */   public boolean Blockdropper;
/*    */ 
/* 55 */   private ProjectorTyp(int ProTyp, String dispNm, String recipe, Item item, boolean Blockdropper) { this.displayName = dispNm;
/* 56 */     this.recipe = recipe;
/* 57 */     this.item = item;
/* 58 */     this.ProTyp = ProTyp;
/* 59 */     this.Blockdropper = Blockdropper; }
/*    */ 
/*    */   public static ProjectorTyp TypfromItem(Item item)
/*    */   {
/* 63 */     for (ProjectorTyp mach : values()) {
/* 64 */       if (mach.item == item)
/*    */       {
/* 66 */         return mach;
/*    */       }
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */   public static void initialize()
/*    */   {
/* 76 */     for (ProjectorTyp mach : values()) {
/* 77 */       generateRecipesFor(mach);
/* 78 */       addNameForObject(mach);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void addNameForObject(ProjectorTyp mach)
/*    */   {
/* 84 */     LanguageRegistry.instance().addNameForObject(mach.item, "en_US", " MFFS Projector Module  " + mach.displayName);
/*    */   }
/*    */ 
/*    */   public static String getdisplayName(ProjectorTyp mach)
/*    */   {
/* 90 */     return "MFFS Projector Module  " + mach.displayName;
/*    */   }
/*    */ 
/*    */   public static void generateRecipesFor(ProjectorTyp mach)
/*    */   {
/* 95 */     String[] recipeSplit = { mach.recipe.substring(0, 3), mach.recipe.substring(3, 6), mach.recipe.substring(6, 9) };
/*    */ 
/* 99 */     GameRegistry.addRecipe(new ItemStack(mach.item, 1), new Object[] { recipeSplit, Character.valueOf('C'), ModularForceFieldSystem.MFFSProjectorTypkube, Character.valueOf('B'), Block.obsidian, Character.valueOf('A'), ModularForceFieldSystem.MFFSitemFocusmatix });
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ProjectorTyp
 * JD-Core Version:    0.6.2
 */