/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class MFFSCreativeTab extends CreativeTabs
/*    */ {
/*    */   public MFFSCreativeTab(int par1, String par2Str)
/*    */   {
/* 33 */     super(par1, par2Str);
/*    */   }
/*    */ 
/*    */   public ItemStack getIconItemStack() {
/* 37 */     return new ItemStack(ModularForceFieldSystem.MFFSCapacitor);
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.MFFSCreativeTab
 * JD-Core Version:    0.6.2
 */