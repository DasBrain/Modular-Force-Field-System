/*    */ package chb.mods.mffs.common.multitool;
/*    */ 
/*    */ import chb.mods.mffs.api.ISwitchabel;
/*    */ import chb.mods.mffs.common.Functions;
/*    */ import chb.mods.mffs.common.SecurityHelper;
/*    */ import chb.mods.mffs.common.SecurityRight;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSwitch extends ItemMultitool
/*    */ {
/*    */   public ItemSwitch(int id)
/*    */   {
/* 37 */     super(id, 1);
/*    */   }
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*    */   {
/* 47 */     if (world.isRemote) {
/* 48 */       return false;
/*    */     }
/*    */ 
/* 51 */     TileEntity tileentity = world.getBlockTileEntity(x, y, z);
/*    */ 
/* 55 */     if ((tileentity instanceof ISwitchabel))
/*    */     {
/* 58 */       if (SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.EB))
/*    */       {
/* 61 */         if (((ISwitchabel)tileentity).isSwitchabel())
/*    */         {
/* 63 */           if (consumePower(itemstack, 1000, true))
/*    */           {
/* 65 */             consumePower(itemstack, 1000, false);
/* 66 */             ((ISwitchabel)tileentity).toggelSwitchValue();
/* 67 */             return true;
/*    */           }
/*    */ 
/* 70 */           Functions.ChattoPlayer(entityplayer, "[MultiTool] Fail: not enough FP please charge");
/* 71 */           return false;
/*    */         }
/*    */ 
/* 75 */         Functions.ChattoPlayer(entityplayer, "[MultiTool] Fail: Object not in switch enable mode");
/* 76 */         return false;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 85 */     return false;
/*    */   }
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*    */   {
/* 92 */     return super.onItemRightClick(itemstack, world, entityplayer);
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemSwitch
 * JD-Core Version:    0.6.2
 */