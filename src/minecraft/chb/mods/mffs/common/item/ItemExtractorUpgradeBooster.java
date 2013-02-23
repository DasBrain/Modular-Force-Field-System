/*    */ package chb.mods.mffs.common.item;
/*    */ 
/*    */ import chb.mods.mffs.common.MFFSMaschines;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class ItemExtractorUpgradeBooster extends ItemMFFSBase
/*    */ {
/*    */   public ItemExtractorUpgradeBooster(int i)
/*    */   {
/* 35 */     super(i);
/* 36 */     setIconIndex(37);
/* 37 */     setMaxStackSize(19);
/*    */   }
/*    */ 
/*    */   public String getTextureFile() {
/* 41 */     return "/chb/mods/mffs/sprites/items.png";
/*    */   }
/*    */ 
/*    */   public boolean isRepairable() {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*    */   {
/* 51 */     if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
/* 52 */       info.add("compatible with:");
/* 53 */       info.add("MFFS " + MFFSMaschines.Extractor.displayName);
/*    */     }
/*    */     else {
/* 56 */       info.add("compatible with: (Hold Shift)");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemExtractorUpgradeBooster
 * JD-Core Version:    0.6.2
 */