/*     */ package chb.mods.mffs.common.multitool;
/*     */ 
/*     */ import chb.mods.mffs.api.IForceEnergyItems;
/*     */ import chb.mods.mffs.common.ForceEnergyItems;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class ItemMultitool extends ForceEnergyItems
/*     */   implements IForceEnergyItems
/*     */ {
/*     */   private int typ;
/*  56 */   private static List MTTypes = new ArrayList();
/*     */ 
/*     */   protected ItemMultitool(int id, int typ, boolean addToList)
/*     */   {
/*  43 */     super(id);
/*  44 */     this.typ = typ;
/*  45 */     setIconIndex(typ);
/*  46 */     setMaxStackSize(1);
/*  47 */     setMaxDamage(100);
/*  48 */     if (addToList)
/*  49 */       MTTypes.add(this);
/*     */   }
/*     */ 
/*     */   protected ItemMultitool(int id, int typ) {
/*  53 */     this(id, typ, true);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  60 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable()
/*     */   {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDamageable()
/*     */   {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   public abstract boolean onItemUseFirst(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*     */   {
/*  81 */     if (entityplayer.isSneaking()) {
/*  82 */       int modeNum = 0;
/*  83 */       for (int i = 0; i < MTTypes.size(); i++) {
/*  84 */         ItemMultitool MT = (ItemMultitool)MTTypes.get(i);
/*  85 */         if (MT.itemID == itemstack.getItem().itemID) {
/*  86 */           if (i + 1 < MTTypes.size())
/*  87 */             modeNum = i + 1;
/*     */           else {
/*  89 */             modeNum = 0;
/*     */           }
/*     */         }
/*     */       }
/*  93 */       int powerleft = getAvailablePower(itemstack);
/*  94 */       ItemStack hand = entityplayer.inventory.getCurrentItem();
/*  95 */       hand = new ItemStack((Item)MTTypes.get(modeNum), 1);
/*  96 */       chargeItem(hand, powerleft, false);
/*  97 */       return hand;
/*     */     }
/*  99 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
/*     */   {
/* 108 */     par1ItemStack.setItemDamage(getItemDamage(par1ItemStack));
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/* 115 */     String tooltip = String.format("%d FE/%d FE ", new Object[] { Integer.valueOf(getAvailablePower(itemStack)), Integer.valueOf(getMaximumPower(itemStack)) });
/* 116 */     info.add(tooltip);
/*     */   }
/*     */ 
/*     */   public int getPowerTransferrate()
/*     */   {
/* 122 */     return 50000;
/*     */   }
/*     */ 
/*     */   public int getMaximumPower(ItemStack itemStack)
/*     */   {
/* 128 */     return 1000000;
/*     */   }
/*     */ 
/*     */   public int getItemDamage(ItemStack itemStack)
/*     */   {
/* 136 */     return 101 - getAvailablePower(itemStack) * 100 / getMaximumPower(itemStack);
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(int i, CreativeTabs tabs, List itemList)
/*     */   {
/* 144 */     ItemStack charged = new ItemStack(this, 1);
/* 145 */     charged.setItemDamage(1);
/* 146 */     setAvailablePower(charged, getMaximumPower(null));
/* 147 */     itemList.add(charged);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemMultitool
 * JD-Core Version:    0.6.2
 */