/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.api.IForceEnergyItems;
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemForcePowerCrystal extends ItemMFFSBase
/*     */   implements IPowerLinkItem, IForceEnergyItems
/*     */ {
/*     */   public ItemForcePowerCrystal(int i)
/*     */   {
/*  44 */     super(i);
/*  45 */     setIconIndex(96);
/*  46 */     setMaxStackSize(1);
/*  47 */     setMaxDamage(100);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  52 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable() {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDamageable()
/*     */   {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   public int getPowerTransferrate() {
/*  66 */     return 100000;
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int dmg)
/*     */   {
/*  71 */     if (dmg == 0)
/*  72 */       return 96;
/*  73 */     return 112 + (100 - dmg) / 20;
/*     */   }
/*     */ 
/*     */   public int getItemDamage(ItemStack itemStack)
/*     */   {
/*  79 */     return 101 - getAvailablePower(itemStack, null, null) * 100 / getMaximumPower(itemStack);
/*     */   }
/*     */ 
/*     */   public int getMaximumPower(ItemStack itemStack)
/*     */   {
/*  84 */     return 5000000;
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/*  91 */     String tooltip = String.format("%d FE/%d FE ", new Object[] { Integer.valueOf(getAvailablePower(itemStack, null, null)), Integer.valueOf(getMaximumPower(itemStack)) });
/*  92 */     info.add(tooltip);
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(int i, CreativeTabs tabs, List itemList)
/*     */   {
/* 100 */     ItemStack charged = new ItemStack(this, 1);
/* 101 */     charged.setItemDamage(1);
/* 102 */     setAvailablePower(charged, getMaximumPower(null));
/* 103 */     itemList.add(charged);
/*     */ 
/* 105 */     ItemStack empty = new ItemStack(this, 1);
/* 106 */     empty.setItemDamage(100);
/* 107 */     setAvailablePower(empty, 0);
/* 108 */     itemList.add(empty);
/*     */   }
/*     */ 
/*     */   public boolean isPowersourceItem()
/*     */   {
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */   public int getAvailablePower(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 118 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 119 */     if (nbtTagCompound != null)
/*     */     {
/* 121 */       return nbtTagCompound.getInteger("ForceEnergy");
/*     */     }
/* 123 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getMaximumPower(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 129 */     return getMaximumPower(itemStack);
/*     */   }
/*     */ 
/*     */   public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
/*     */   {
/* 135 */     if (getAvailablePower(itemStack, null, null) >= powerAmount)
/*     */     {
/* 137 */       if (!simulation)
/*     */       {
/* 139 */         setAvailablePower(itemStack, getAvailablePower(itemStack, null, null) - powerAmount);
/*     */       }
/* 141 */       return true;
/*     */     }
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPowersourceID(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 150 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getPercentageCapacity(ItemStack itemStack, TileEntityMachines tem, World world) {
/* 154 */     return getAvailablePower(itemStack, null, null) / 1000 * 100 / (getMaximumPower(itemStack) / 1000);
/*     */   }
/*     */ 
/*     */   public boolean insertPower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
/*     */   {
/* 162 */     if (getAvailablePower(itemStack) + powerAmount <= getMaximumPower(itemStack))
/*     */     {
/* 164 */       if (!simulation)
/*     */       {
/* 166 */         setAvailablePower(itemStack, getAvailablePower(itemStack, null, null) + powerAmount);
/*     */       }
/* 168 */       return true;
/*     */     }
/*     */ 
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   public int getfreeStorageAmount(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 176 */     return getMaximumPower(itemStack) - getAvailablePower(itemStack, null, null);
/*     */   }
/*     */ 
/*     */   public void setAvailablePower(ItemStack itemStack, int ForceEnergy)
/*     */   {
/* 182 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 183 */     nbtTagCompound.setInteger("ForceEnergy", ForceEnergy);
/*     */ 
/* 185 */     itemStack.setItemDamage(getItemDamage(itemStack));
/*     */   }
/*     */ 
/*     */   public int getAvailablePower(ItemStack itemStack)
/*     */   {
/* 190 */     return getAvailablePower(itemStack, null, null);
/*     */   }
/*     */ 
/*     */   public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation)
/*     */   {
/* 196 */     return consumePower(itemStack, powerAmount, simulation, null, null);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemForcePowerCrystal
 * JD-Core Version:    0.6.2
 */