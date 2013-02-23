/*     */ package chb.mods.mffs.common.multitool;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.item.ItemCardDataLink;
/*     */ import chb.mods.mffs.common.item.ItemCardPersonalID;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemPersonalIDWriter extends ItemMultitool
/*     */ {
/*     */   public ItemPersonalIDWriter(int i)
/*     */   {
/*  44 */     super(i, 2);
/*     */   }
/*     */ 
/*     */   public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer entityplayer, Entity entity)
/*     */   {
/*  49 */     if ((entity instanceof EntityPlayer))
/*     */     {
/*  51 */       List slots = entityplayer.inventoryContainer.inventorySlots;
/*  52 */       for (Slot slot : slots) {
/*  53 */         ItemStack stack = slot.getStack();
/*  54 */         if ((stack != null) && 
/*  55 */           (stack.getItem() == ModularForceFieldSystem.MFFSitemcardempty)) {
/*  56 */           if (consumePower(itemstack, 1000, true))
/*     */           {
/*  58 */             consumePower(itemstack, 1000, false);
/*  59 */             ItemStack IDCard = new ItemStack(ModularForceFieldSystem.MFFSItemIDCard, 1);
/*  60 */             ItemCardPersonalID.setOwner(IDCard, ((EntityPlayer)entity).username);
/*     */ 
/*  62 */             if (--stack.stackSize <= 0)
/*  63 */               slot.putStack(IDCard);
/*  64 */             else if (!entityplayer.inventory.addItemStackToInventory(IDCard)) {
/*  65 */               entityplayer.dropPlayerItem(IDCard);
/*     */             }
/*  67 */             Functions.ChattoPlayer(entityplayer, "[MultiTool] Success: ID-Card create");
/*  68 */             return true;
/*     */           }
/*  70 */           Functions.ChattoPlayer(entityplayer, "[MultiTool] Fail: not enough FP please charge");
/*  71 */           return true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  77 */       Functions.ChattoPlayer(entityplayer, "[MultiTool] Fail: need MFFS Card <blank> in  Inventory");
/*  78 */       return true;
/*     */     }
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*     */   {
/*  87 */     if (entityplayer.isSneaking())
/*     */     {
/*  89 */       return super.onItemRightClick(itemstack, world, entityplayer);
/*     */     }
/*     */ 
/*  92 */     List slots = entityplayer.inventoryContainer.inventorySlots;
/*  93 */     for (Slot slot : slots) {
/*  94 */       ItemStack stack = slot.getStack();
/*  95 */       if ((stack != null) && 
/*  96 */         (stack.getItem() == ModularForceFieldSystem.MFFSitemcardempty)) {
/*  97 */         if (consumePower(itemstack, 1000, true))
/*     */         {
/*  99 */           consumePower(itemstack, 1000, false);
/* 100 */           ItemStack IDCard = new ItemStack(ModularForceFieldSystem.MFFSItemIDCard, 1);
/* 101 */           ItemCardPersonalID.setOwner(IDCard, entityplayer.username);
/*     */ 
/* 103 */           if (--stack.stackSize <= 0)
/* 104 */             slot.putStack(IDCard);
/* 105 */           else if (!entityplayer.inventory.addItemStackToInventory(IDCard))
/* 106 */             entityplayer.dropPlayerItem(IDCard);
/* 107 */           if (world.isRemote) {
/* 108 */             Functions.ChattoPlayer(entityplayer, "[MultiTool] Success: ID-Card create");
/*     */           }
/* 110 */           return itemstack;
/*     */         }
/* 112 */         if (world.isRemote)
/* 113 */           Functions.ChattoPlayer(entityplayer, "[MultiTool] Fail: not enough FP please charge");
/* 114 */         return itemstack;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     if (world.isRemote) {
/* 120 */       Functions.ChattoPlayer(entityplayer, "[MultiTool] Fail: need MFFS Card <blank> in  Inventory");
/*     */     }
/* 122 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/* 130 */     if (world.isRemote) {
/* 131 */       return true;
/*     */     }
/* 133 */     TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
/* 134 */     if (((tileEntity instanceof TileEntityMachines)) && 
/* 135 */       (SecurityHelper.isAccessGranted(tileEntity, player, world, SecurityRight.UCS)))
/*     */     {
/* 137 */       List slots = player.inventoryContainer.inventorySlots;
/* 138 */       for (Slot slot : slots) {
/* 139 */         ItemStack playerstack = slot.getStack();
/* 140 */         if ((playerstack != null) && 
/* 141 */           (playerstack.getItem() == ModularForceFieldSystem.MFFSitemcardempty)) {
/* 142 */           if (consumePower(stack, 1000, true))
/*     */           {
/* 144 */             consumePower(stack, 1000, false);
/* 145 */             ItemStack IDCard = new ItemStack(ModularForceFieldSystem.MFFSitemDataLinkCard);
/*     */ 
/* 147 */             ((ItemCardDataLink)IDCard.getItem()); ItemCardDataLink.setforArea(IDCard, ((TileEntityMachines)tileEntity).getDeviceName());
/* 148 */             ((ItemCardDataLink)IDCard.getItem()).setInformation(IDCard, new PointXYZ(x, y, z, world), "DeviceID", ((TileEntityMachines)tileEntity).getDeviceID(), tileEntity);
/*     */ 
/* 150 */             if (--playerstack.stackSize <= 0)
/* 151 */               slot.putStack(IDCard);
/* 152 */             else if (!player.inventory.addItemStackToInventory(IDCard)) {
/* 153 */               player.dropPlayerItem(IDCard);
/*     */             }
/* 155 */             player.inventoryContainer.detectAndSendChanges();
/* 156 */             Functions.ChattoPlayer(player, "[MultiTool] Success: DataLink-Card create");
/*     */ 
/* 158 */             return true;
/*     */           }
/*     */ 
/* 161 */           Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FP please charge");
/* 162 */           return false;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/* 178 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemPersonalIDWriter
 * JD-Core Version:    0.6.2
 */