/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract interface IModularProjector extends IInventory
/*    */ {
/*    */   public abstract World n();
/*    */ 
/*    */   public abstract int countItemsInSlot(Slots paramSlots);
/*    */ 
/*    */   public abstract int getDeviceID();
/*    */ 
/*    */   public abstract Set getInteriorPoints();
/*    */ 
/*    */   public abstract void setBurnedOut(boolean paramBoolean);
/*    */ 
/*    */   public abstract boolean isActive();
/*    */ 
/*    */   public abstract int getSide();
/*    */ 
/*    */   public static enum Slots
/*    */   {
/* 33 */     Linkcard(0), 
/* 34 */     TypeMod(1), 
/* 35 */     Option1(2), 
/* 36 */     Option2(3), 
/* 37 */     Option3(4), 
/* 38 */     Distance(5), 
/* 39 */     Strength(6), 
/* 40 */     FocusUp(7), 
/* 41 */     FocusDown(8), 
/* 42 */     FocusRight(9), 
/* 43 */     FocusLeft(10), 
/* 44 */     Centerslot(11), 
/* 45 */     SecCard(12);
/*    */ 
/*    */     public int slot;
/*    */ 
/* 49 */     private Slots(int num) { this.slot = num; }
/*    */ 
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.IModularProjector
 * JD-Core Version:    0.6.2
 */