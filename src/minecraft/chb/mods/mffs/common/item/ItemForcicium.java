/*    */ package chb.mods.mffs.common.item;
/*    */ 
/*    */ public class ItemForcicium extends ItemMFFSBase
/*    */ {
/*    */   public ItemForcicium(int i)
/*    */   {
/* 25 */     super(i);
/* 26 */     setIconIndex(97);
/* 27 */     setMaxStackSize(64);
/*    */   }
/*    */ 
/*    */   public String getTextureFile() {
/* 31 */     return "/chb/mods/mffs/sprites/items.png";
/*    */   }
/*    */ 
/*    */   public boolean isRepairable() {
/* 35 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemForcicium
 * JD-Core Version:    0.6.2
 */