/*    */ package chb.mods.mffs.common.multitool;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemManuelBook extends ItemMultitool
/*    */ {
/*    */   public ItemManuelBook(int par1)
/*    */   {
/* 12 */     super(par1, 5);
/*    */   }
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*    */   {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*    */   {
/* 26 */     if (entityplayer.isSneaking())
/*    */     {
/* 28 */       return super.onItemRightClick(itemstack, world, entityplayer);
/*    */     }
/*    */ 
/* 32 */     if (world.isRemote) {
/* 33 */       entityplayer.openGui(ModularForceFieldSystem.instance, 1, world, 0, 0, 0);
/*    */     }
/*    */ 
/* 36 */     return itemstack;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemManuelBook
 * JD-Core Version:    0.6.2
 */