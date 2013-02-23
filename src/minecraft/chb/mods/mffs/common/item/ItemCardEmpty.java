/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemCardEmpty extends ItemMFFSBase
/*     */ {
/*     */   public ItemCardEmpty(int i)
/*     */   {
/*  38 */     super(i);
/*  39 */     setIconIndex(16);
/*  40 */     setMaxStackSize(16);
/*     */   }
/*     */ 
/*     */   public String getTextureFile() {
/*  44 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
/*     */   {
/*  56 */     if (world.isRemote) {
/*  57 */       return false;
/*     */     }
/*  59 */     TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
/*     */ 
/*  62 */     if ((tileEntity instanceof TileEntityAdvSecurityStation))
/*     */     {
/*  65 */       if (((TileEntityAdvSecurityStation)tileEntity).isActive())
/*     */       {
/*  67 */         if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.CSR))
/*     */         {
/*  69 */           ItemStack newcard = new ItemStack(ModularForceFieldSystem.MFFSItemSecLinkCard);
/*  70 */           ((ItemCardSecurityLink)newcard.getItem()).setInformation(newcard, new PointXYZ(i, j, k, world), "Secstation_ID", ((TileEntityAdvSecurityStation)tileEntity).getDeviceID());
/*  71 */           ((ItemCardSecurityLink)newcard.getItem()); ItemCardSecurityLink.setforArea(newcard, ((TileEntityAdvSecurityStation)tileEntity).getDeviceName());
/*     */ 
/*  73 */           if (--itemstack.stackSize <= 0)
/*  74 */             entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, newcard);
/*  75 */           else if (!entityplayer.inventory.addItemStackToInventory(newcard)) {
/*  76 */             entityplayer.dropPlayerItem(newcard);
/*     */           }
/*  78 */           entityplayer.inventoryContainer.detectAndSendChanges();
/*  79 */           Functions.ChattoPlayer(entityplayer, "[Security Station] Success: <Security Station Link>  Card create");
/*     */ 
/*  81 */           return true;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  86 */         Functions.ChattoPlayer(entityplayer, "[Security Station] Fail: Security Station must be Active  for create");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  93 */     if ((tileEntity instanceof TileEntityCapacitor))
/*     */     {
/*  96 */       if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB))
/*     */       {
/*  98 */         ItemStack newcard = new ItemStack(ModularForceFieldSystem.MFFSitemfc);
/*  99 */         ((ItemCardPowerLink)newcard.getItem()).setInformation(newcard, new PointXYZ(i, j, k, world), "CapacitorID", ((TileEntityCapacitor)tileEntity).getPowerStorageID());
/* 100 */         ((ItemCardPowerLink)newcard.getItem()); ItemCardPowerLink.setforArea(newcard, ((TileEntityCapacitor)tileEntity).getDeviceName());
/*     */ 
/* 102 */         if (--itemstack.stackSize <= 0)
/* 103 */           entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, newcard);
/* 104 */         else if (!entityplayer.inventory.addItemStackToInventory(newcard)) {
/* 105 */           entityplayer.dropPlayerItem(newcard);
/*     */         }
/* 107 */         entityplayer.inventoryContainer.detectAndSendChanges();
/*     */ 
/* 109 */         entityplayer.addChatMessage("[Capacitor] Success: <Power-Link> Card create");
/*     */ 
/* 111 */         return true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 123 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCardEmpty
 * JD-Core Version:    0.6.2
 */