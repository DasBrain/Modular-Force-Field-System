/*    */ package chb.mods.mffs.common.container;
/*    */ 
/*    */ import chb.mods.mffs.common.SlotHelper;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerControlSystem extends Container
/*    */ {
/*    */   private EntityPlayer player;
/*    */   private TileEntityControlSystem Control;
/*    */ 
/*    */   public ContainerControlSystem(EntityPlayer player, TileEntityControlSystem tileentity)
/*    */   {
/* 36 */     this.Control = tileentity;
/* 37 */     this.player = player;
/*    */ 
/* 40 */     addSlotToContainer(new SlotHelper(this.Control, 0, 236, 4));
/* 41 */     addSlotToContainer(new SlotHelper(this.Control, 1, 203, 30));
/*    */ 
/* 46 */     for (int var3 = 0; var3 < 7; var3++) {
/* 47 */       for (int var4 = 0; var4 < 4; var4++) {
/* 48 */         addSlotToContainer(new SlotHelper(this.Control, var4 + var3 * 4 + 4, 176 + var4 * 18, 80 + var3 * 18));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 53 */     for (var3 = 0; var3 < 3; var3++) {
/* 54 */       for (int var4 = 0; var4 < 9; var4++) {
/* 55 */         addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 134 + var3 * 18));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 60 */     for (var3 = 0; var3 < 9; var3++)
/* 61 */       addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 192));
/*    */   }
/*    */ 
/*    */   public EntityPlayer getPlayer()
/*    */   {
/* 66 */     return this.player;
/*    */   }
/*    */ 
/*    */   public boolean canInteractWith(EntityPlayer entityplayer) {
/* 70 */     return this.Control.isUseableByPlayer(entityplayer);
/*    */   }
/*    */ 
/*    */   public ItemStack transferStackInSlot(EntityPlayer p, int i)
/*    */   {
/* 75 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.container.ContainerControlSystem
 * JD-Core Version:    0.6.2
 */