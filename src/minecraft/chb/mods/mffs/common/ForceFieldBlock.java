/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ public class ForceFieldBlock
/*    */ {
/*    */   public int typ;
/*    */   public int Projektor_ID;
/*    */   public int Generator_Id;
/*    */ 
/*    */   public ForceFieldBlock(int Generator_Id, int Projektor_ID, int typ)
/*    */   {
/* 29 */     this.Projektor_ID = Projektor_ID;
/* 30 */     this.Generator_Id = Generator_Id;
/* 31 */     this.typ = typ;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 36 */     return this.Projektor_ID + "-" + this.Generator_Id + "-" + this.typ;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ForceFieldBlock
 * JD-Core Version:    0.6.2
 */