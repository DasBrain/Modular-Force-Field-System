/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class Functions
/*    */ {
/*    */   public static Block getBlock(int x, int y, int z, World world)
/*    */   {
/* 38 */     return Block.blocksList[world.getBlockId(x, y, z)];
/*    */   }
/*    */ 
/*    */   public static ArrayList getItemStackFromBlock(World world, int i, int j, int k)
/*    */   {
/* 45 */     Block block = Block.blocksList[world.getBlockId(i, j, k)];
/*    */ 
/* 47 */     if (block == null) {
/* 48 */       return null;
/*    */     }
/* 50 */     int meta = world.getBlockMetadata(i, j, k);
/*    */ 
/* 52 */     return block.getBlockDropped(world, i, j, k, meta, 0);
/*    */   }
/*    */ 
/*    */   public static void ChattoPlayer(EntityPlayer player, String Message)
/*    */   {
/* 57 */     player.addChatMessage(Message);
/*    */   }
/*    */ 
/*    */   public static boolean setIteminSlot(ItemStack itemstack, EntityPlayer entityplayer, TileEntity tileEntity, int Slot, String Cardname)
/*    */   {
/* 65 */     if (((IInventory)tileEntity).getStackInSlot(Slot) == null)
/*    */     {
/* 67 */       ((IInventory)tileEntity).setInventorySlotContents(Slot, itemstack);
/* 68 */       entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
/* 69 */       ChattoPlayer(entityplayer, "Success: " + Cardname + " Card installed");
/* 70 */       ((IInventory)tileEntity).onInventoryChanged();
/* 71 */       return true;
/*    */     }
/*    */ 
/* 75 */     if (((IInventory)tileEntity).getStackInSlot(Slot).getItem() == ModularForceFieldSystem.MFFSitemcardempty)
/*    */     {
/* 77 */       ItemStack itemstackcopy = itemstack.copy();
/* 78 */       ((IInventory)tileEntity).setInventorySlotContents(Slot, itemstackcopy);
/* 79 */       ChattoPlayer(entityplayer, "Success: " + Cardname + " Card data copied ");
/* 80 */       ((IInventory)tileEntity).onInventoryChanged();
/* 81 */       return true;
/*    */     }
/* 83 */     ChattoPlayer(entityplayer, "Fail: Slot is not empty");
/* 84 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.Functions
 * JD-Core Version:    0.6.2
 */