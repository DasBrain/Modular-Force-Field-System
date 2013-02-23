/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class NBTTagCompoundHelper
/*    */ {
/*    */   public static NBTTagCompound getTAGfromItemstack(ItemStack itemStack)
/*    */   {
/* 31 */     if (itemStack != null) {
/* 32 */       NBTTagCompound tag = itemStack.getTagCompound();
/* 33 */       if (tag == null) {
/* 34 */         tag = new NBTTagCompound();
/* 35 */         itemStack.setTagCompound(tag);
/*    */       }
/* 37 */       return tag;
/*    */     }
/* 39 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.NBTTagCompoundHelper
 * JD-Core Version:    0.6.2
 */