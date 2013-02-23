/*    */ package chb.mods.mffs.common.options;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.ProjectorTyp;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleAdvCube;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleContainment;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleCube;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleDeflector;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleSphere;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleTube;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleWall;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModulediagonallyWall;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public abstract class ItemProjectorOptionBase extends Item
/*    */ {
/* 30 */   private static List instances = new ArrayList();
/*    */ 
/*    */   public ItemProjectorOptionBase(int i)
/*    */   {
/* 25 */     super(i);
/* 26 */     setMaxStackSize(8);
/* 27 */     setCreativeTab(ModularForceFieldSystem.MFFSTab);
/* 28 */     instances.add(this);
/*    */   }
/*    */ 
/*    */   public static List get_instances() {
/* 32 */     return instances;
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 37 */     return "/chb/mods/mffs/sprites/items.png";
/*    */   }
/*    */ 
/*    */   public boolean isRepairable()
/*    */   {
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*    */   {
/* 48 */     if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
/* 49 */       info.add("compatible with:");
/*    */ 
/* 51 */       if (ItemProjectorModuleWall.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.wall));
/* 52 */       if (ItemProjectorModulediagonallyWall.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.diagonallywall));
/* 53 */       if (ItemProjectorModuleDeflector.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.deflector));
/* 54 */       if (ItemProjectorModuleTube.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.tube));
/* 55 */       if (ItemProjectorModuleSphere.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.sphere));
/* 56 */       if (ItemProjectorModuleCube.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.cube));
/* 57 */       if (ItemProjectorModuleAdvCube.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.AdvCube));
/* 58 */       if (ItemProjectorModuleContainment.supportsOption(this)) info.add(ProjectorTyp.getdisplayName(ProjectorTyp.containment)); 
/*    */     }
/*    */     else
/*    */     {
/* 61 */       info.add("compatible with: (Hold Shift)");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.ItemProjectorOptionBase
 * JD-Core Version:    0.6.2
 */