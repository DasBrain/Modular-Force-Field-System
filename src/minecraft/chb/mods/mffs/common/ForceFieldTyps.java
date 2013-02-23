/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ public enum ForceFieldTyps
/*    */ {
/* 29 */   Camouflage(2), 
/* 30 */   Default(1), 
/* 31 */   Zapper(3), 
/* 32 */   Area(1), 
/* 33 */   Containment(1);
/*    */ 
/*    */   int costmodi;
/*    */ 
/*    */   private ForceFieldTyps(int costmodi)
/*    */   {
/* 42 */     this.costmodi = costmodi;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ForceFieldTyps
 * JD-Core Version:    0.6.2
 */