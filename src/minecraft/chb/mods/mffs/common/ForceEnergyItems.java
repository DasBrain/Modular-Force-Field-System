/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import chb.mods.mffs.api.IForceEnergyItems;
/*    */ import chb.mods.mffs.common.item.ItemMFFSBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public abstract class ForceEnergyItems extends ItemMFFSBase
/*    */   implements IForceEnergyItems
/*    */ {
/*    */   public ForceEnergyItems(int i)
/*    */   {
/* 35 */     super(i);
/*    */   }
/*    */ 
/*    */   public boolean consumePower(ItemStack itemStack, int amount, boolean simulation)
/*    */   {
/* 41 */     if ((itemStack.getItem() instanceof IForceEnergyItems))
/*    */     {
/* 43 */       if (getAvailablePower(itemStack) >= amount)
/*    */       {
/* 45 */         if (!simulation)
/*    */         {
/* 47 */           setAvailablePower(itemStack, getAvailablePower(itemStack) - amount);
/*    */         }
/* 49 */         return true;
/*    */       }
/*    */     }
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean chargeItem(ItemStack itemStack, int amount, boolean simulation)
/*    */   {
/* 59 */     if ((itemStack.getItem() instanceof IForceEnergyItems))
/*    */     {
/* 61 */       if (getAvailablePower(itemStack) + amount <= getMaximumPower(itemStack))
/*    */       {
/* 63 */         if (!simulation)
/*    */         {
/* 65 */           setAvailablePower(itemStack, getAvailablePower(itemStack) + amount);
/*    */         }
/* 67 */         return true;
/*    */       }
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   public void setAvailablePower(ItemStack itemStack, int ForceEnergy)
/*    */   {
/* 76 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 77 */     nbtTagCompound.setInteger("ForceEnergy", ForceEnergy);
/*    */   }
/*    */ 
/*    */   public int getAvailablePower(ItemStack itemstack)
/*    */   {
/* 84 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/* 85 */     if (nbtTagCompound != null)
/*    */     {
/* 87 */       return nbtTagCompound.getInteger("ForceEnergy");
/*    */     }
/* 89 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ForceEnergyItems
 * JD-Core Version:    0.6.2
 */