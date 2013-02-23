/*    */ package chb.mods.mffs.common.modules;
/*    */ 
/*    */ import chb.mods.mffs.common.ForceFieldTyps;
/*    */ import chb.mods.mffs.common.IModularProjector;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class Module3DBase extends ModuleBase
/*    */ {
/*    */   public Module3DBase(int i)
/*    */   {
/* 12 */     super(i);
/*    */   }
/*    */ 
/*    */   public void calculateField(IModularProjector projector, Set points)
/*    */   {
/*    */   }
/*    */ 
/*    */   public abstract void calculateField(IModularProjector paramIModularProjector, Set paramSet1, Set paramSet2);
/*    */ 
/*    */   public ForceFieldTyps getForceFieldTyps()
/*    */   {
/* 26 */     if ((this instanceof ItemProjectorModuleContainment)) {
/* 27 */       return ForceFieldTyps.Containment;
/*    */     }
/* 29 */     return ForceFieldTyps.Area;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.Module3DBase
 * JD-Core Version:    0.6.2
 */