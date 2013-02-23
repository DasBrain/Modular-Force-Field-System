/*    */ package chb.mods.mffs.common.multitool;
/*    */ 
/*    */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*    */ import java.io.PrintStream;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemDebugger extends ItemMultitool
/*    */ {
/* 30 */   protected StringBuffer info = new StringBuffer();
/*    */ 
/*    */   public ItemDebugger(int i) {
/* 33 */     super(i, 3, false);
/*    */   }
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*    */   {
/* 42 */     TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
/*    */ 
/* 44 */     if (world.isRemote)
/*    */     {
/* 47 */       if ((tileEntity instanceof TileEntityMachines)) {
/* 48 */         System.out.println("client" + ((TileEntityMachines)tileEntity).isActive());
/*    */       }
/*    */ 
/*    */     }
/* 55 */     else if ((tileEntity instanceof TileEntityMachines)) {
/* 56 */       System.out.println("server" + ((TileEntityMachines)tileEntity).isActive());
/*    */     }
/*    */ 
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*    */   {
/* 70 */     return itemstack;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemDebugger
 * JD-Core Version:    0.6.2
 */