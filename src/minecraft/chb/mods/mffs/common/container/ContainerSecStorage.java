/*    */ package chb.mods.mffs.common.container;
/*    */ 
/*    */ import chb.mods.mffs.common.SlotHelper;
/*    */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerSecStorage extends Container
/*    */ {
/*    */   private EntityPlayer player;
/*    */   private TileEntitySecStorage SecStorage;
/*    */ 
/*    */   public ContainerSecStorage(EntityPlayer player, TileEntitySecStorage tileentity)
/*    */   {
/* 36 */     this.SecStorage = tileentity;
/* 37 */     this.player = player;
/*    */ 
/* 39 */     addSlotToContainer(new SlotHelper(this.SecStorage, 0, 12, 24));
/*    */ 
/* 44 */     for (int var3 = 0; var3 < 6; var3++) {
/* 45 */       for (int var4 = 0; var4 < 9; var4++) {
/* 46 */         addSlotToContainer(new SlotHelper(this.SecStorage, var4 + var3 * 9 + 1, 12 + var4 * 18, 43 + var3 * 18));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 51 */     for (var3 = 0; var3 < 3; var3++) {
/* 52 */       for (int var4 = 0; var4 < 9; var4++) {
/* 53 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 12 + var4 * 18, 155 + var3 * 18));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 58 */     for (var3 = 0; var3 < 9; var3++)
/* 59 */       addSlotToContainer(new Slot(player.inventory, var3, 12 + var3 * 18, 213));
/*    */   }
/*    */ 
/*    */   public EntityPlayer getPlayer()
/*    */   {
/* 64 */     return this.player;
/*    */   }
/*    */ 
/*    */   public boolean canInteractWith(EntityPlayer entityplayer) {
/* 68 */     return this.SecStorage.isUseableByPlayer(entityplayer);
/*    */   }
/*    */ 
/*    */   public ItemStack transferStackInSlot(EntityPlayer p, int i)
/*    */   {
/* 74 */     ItemStack itemstack = null;
/* 75 */     Slot slot = (Slot)this.inventorySlots.get(i);
/* 76 */     if ((slot != null) && (slot.getHasStack()))
/*    */     {
/* 78 */       ItemStack itemstack1 = slot.getStack();
/* 79 */       itemstack = itemstack1.copy();
/* 80 */       if (i < this.SecStorage.getSizeInventory())
/*    */       {
/* 82 */         if (!mergeItemStack(itemstack1, this.SecStorage.getSizeInventory(), this.inventorySlots.size(), true))
/*    */         {
/* 84 */           return null;
/*    */         }
/* 86 */       } else if (!mergeItemStack(itemstack1, 0, this.SecStorage.getSizeInventory(), false))
/*    */       {
/* 88 */         return null;
/*    */       }
/* 90 */       if (itemstack1.stackSize == 0)
/*    */       {
/* 92 */         slot.putStack(null);
/*    */       }
/*    */       else {
/* 95 */         slot.onSlotChanged();
/*    */       }
/*    */     }
/* 98 */     return itemstack;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerSecStorage
 * JD-Core Version:    0.6.2
 */