/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.api.IForceEnergyItems;
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemCardPower extends ItemMFFSBase
/*     */   implements IPowerLinkItem, IForceEnergyItems
/*     */ {
/*     */   public ItemCardPower(int id)
/*     */   {
/*  16 */     super(id);
/*  17 */     setMaxStackSize(1);
/*  18 */     setIconIndex(21);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  26 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable() {
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPercentageCapacity(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/*  36 */     return 100;
/*     */   }
/*     */ 
/*     */   public int getAvailablePower(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/*  42 */     return 10000000;
/*     */   }
/*     */ 
/*     */   public int getMaximumPower(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/*  48 */     return 10000000;
/*     */   }
/*     */ 
/*     */   public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
/*     */   {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean insertPower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
/*     */   {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPowersourceID(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getfreeStorageAmount(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/*  72 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isPowersourceItem()
/*     */   {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/*  84 */     info.add("Admin Card to Power Maschines");
/*  85 */     info.add("or use to infinit charge Capactior");
/*     */   }
/*     */ 
/*     */   public int getAvailablePower(ItemStack itemStack)
/*     */   {
/*  95 */     return getAvailablePower(itemStack, null, null);
/*     */   }
/*     */ 
/*     */   public int getMaximumPower(ItemStack itemStack)
/*     */   {
/* 103 */     return getMaximumPower(itemStack, null, null);
/*     */   }
/*     */ 
/*     */   public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation)
/*     */   {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   public void setAvailablePower(ItemStack itemStack, int amount)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int getPowerTransferrate()
/*     */   {
/* 121 */     return 1000000;
/*     */   }
/*     */ 
/*     */   public int getItemDamage(ItemStack stackInSlot)
/*     */   {
/* 127 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCardPower
 * JD-Core Version:    0.6.2
 */