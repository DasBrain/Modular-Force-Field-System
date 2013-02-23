/*    */ package chb.mods.mffs.common.item;
/*    */ 
/*    */ import chb.mods.mffs.common.MFFSMaschines;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class ItemProjectorFocusMatrix extends ItemMFFSBase
/*    */ {
/*    */   public ItemProjectorFocusMatrix(int i)
/*    */   {
/* 34 */     super(i);
/* 35 */     setIconIndex(66);
/* 36 */     setMaxStackSize(64);
/*    */   }
/*    */ 
/*    */   public String getTextureFile() {
/* 40 */     return "/chb/mods/mffs/sprites/items.png";
/*    */   }
/*    */ 
/*    */   public boolean isRepairable() {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*    */   {
/* 50 */     if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
/* 51 */       info.add("compatible with:");
/* 52 */       info.add("MFFS " + MFFSMaschines.Projector.displayName);
/*    */     }
/*    */     else {
/* 55 */       info.add("compatible with: (Hold Shift)");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemProjectorFocusMatrix
 * JD-Core Version:    0.6.2
 */