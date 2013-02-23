/*    */ package chb.mods.mffs.common.container;
/*    */ 
/*    */ import chb.mods.mffs.common.SlotHelper;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerAdvSecurityStation extends Container
/*    */ {
/*    */   private TileEntityAdvSecurityStation SecStation;
/*    */   private EntityPlayer player;
/*    */ 
/*    */   public ContainerAdvSecurityStation(EntityPlayer player, TileEntityAdvSecurityStation tileentity)
/*    */   {
/* 38 */     this.SecStation = tileentity;
/* 39 */     this.player = player;
/*    */ 
/* 42 */     addSlotToContainer(new SlotHelper(this.SecStation, 0, 177, 33));
/* 43 */     addSlotToContainer(new SlotHelper(this.SecStation, 1, 15, 30));
/*    */ 
/* 45 */     addSlotToContainer(new SlotHelper(this.SecStation, 39, 88, 102));
/* 46 */     addSlotToContainer(new SlotHelper(this.SecStation, 38, 146, 102));
/*    */ 
/* 51 */     for (int var3 = 0; var3 < 8; var3++) {
/* 52 */       for (int var4 = 0; var4 < 4; var4++) {
/* 53 */         addSlotToContainer(new SlotHelper(this.SecStation, var4 + var3 * 4 + 2, 176 + var4 * 18, 62 + var3 * 18));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 58 */     for (var3 = 0; var3 < 3; var3++) {
/* 59 */       for (int var4 = 0; var4 < 9; var4++) {
/* 60 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 134 + var3 * 18));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 65 */     for (var3 = 0; var3 < 9; var3++)
/* 66 */       addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 192));
/*    */   }
/*    */ 
/*    */   public EntityPlayer getPlayer()
/*    */   {
/* 71 */     return this.player;
/*    */   }
/*    */ 
/*    */   public boolean canInteractWith(EntityPlayer entityplayer) {
/* 75 */     return this.SecStation.isUseableByPlayer(entityplayer);
/*    */   }
/*    */ 
/*    */   public ItemStack transferStackInSlot(EntityPlayer p, int i)
/*    */   {
/* 80 */     ItemStack itemstack = null;
/* 81 */     Slot slot = (Slot)this.inventorySlots.get(i);
/* 82 */     if ((slot != null) && (slot.getHasStack())) {
/* 83 */       ItemStack itemstack1 = slot.getStack();
/* 84 */       itemstack = itemstack1.copy();
/* 85 */       if (itemstack1.stackSize == 0)
/* 86 */         slot.putStack(null);
/*    */       else {
/* 88 */         slot.onSlotChanged();
/*    */       }
/* 90 */       if (itemstack1.stackSize != itemstack.stackSize)
/* 91 */         slot.onSlotChanged();
/*    */       else {
/* 93 */         return null;
/*    */       }
/*    */     }
/* 96 */     return itemstack;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerAdvSecurityStation
 * JD-Core Version:    0.6.2
 */