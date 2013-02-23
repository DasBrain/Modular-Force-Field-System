/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemForcicumCell extends ItemMFFSBase
/*     */ {
/*  46 */   private boolean aktiv = false;
/*     */ 
/*     */   public ItemForcicumCell(int id) {
/*  49 */     super(id);
/*  50 */     setIconIndex(98);
/*  51 */     setMaxStackSize(1);
/*  52 */     setMaxDamage(100);
/*     */   }
/*     */ 
/*     */   public boolean isRepairable()
/*     */   {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDamageable()
/*     */   {
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  70 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public int getItemDamage(ItemStack itemStack)
/*     */   {
/*  77 */     return 101 - getForceciumlevel(itemStack) * 100 / getMaxForceciumlevel();
/*     */   }
/*     */ 
/*     */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
/*     */   {
/*  85 */     if (!world.isRemote)
/*     */     {
/*  88 */       if (this.aktiv)
/*     */       {
/*  91 */         if (getForceciumlevel(itemStack) < getMaxForceciumlevel())
/*     */         {
/*  93 */           if ((entity instanceof EntityPlayer))
/*     */           {
/*  96 */             List slots = ((EntityPlayer)entity).inventoryContainer.inventorySlots;
/*  97 */             for (Slot slot : slots) {
/*  98 */               if ((slot.getStack() != null) && 
/*  99 */                 (slot.getStack().getItem() == ModularForceFieldSystem.MFFSitemForcicium))
/*     */               {
/* 101 */                 setForceciumlevel(itemStack, getForceciumlevel(itemStack) + 1);
/*     */ 
/* 103 */                 if (slot.getStack().stackSize > 1)
/*     */                 {
/* 105 */                   ItemStack forcecium = new ItemStack(ModularForceFieldSystem.MFFSitemForcicium, slot.getStack().stackSize - 1);
/* 106 */                   slot.putStack(forcecium);
/* 107 */                   break;
/*     */                 }
/* 109 */                 slot.putStack(null);
/*     */ 
/* 112 */                 break;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 124 */         itemStack.setItemDamage(getItemDamage(itemStack));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/* 132 */     String tooltip = String.format("%d / %d  Forcicum  ", new Object[] { Integer.valueOf(getForceciumlevel(itemStack)), Integer.valueOf(getMaxForceciumlevel()) });
/* 133 */     info.add(tooltip);
/*     */   }
/*     */ 
/*     */   public boolean useForcecium(int count, ItemStack itemstack)
/*     */   {
/* 139 */     if (count > getForceciumlevel(itemstack))
/*     */     {
/* 141 */       return false;
/*     */     }
/*     */ 
/* 144 */     setForceciumlevel(itemstack, getForceciumlevel(itemstack) - count);
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   public int getMaxForceciumlevel()
/*     */   {
/* 152 */     return 1000;
/*     */   }
/*     */ 
/*     */   public void setForceciumlevel(ItemStack itemStack, int Forceciumlevel)
/*     */   {
/* 159 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 160 */     nbtTagCompound.setInteger("Forceciumlevel", Forceciumlevel);
/*     */   }
/*     */ 
/*     */   public int getForceciumlevel(ItemStack itemstack)
/*     */   {
/* 168 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/* 169 */     if (nbtTagCompound != null)
/*     */     {
/* 171 */       return nbtTagCompound.getInteger("Forceciumlevel");
/*     */     }
/* 173 */     return 0;
/*     */   }
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*     */   {
/* 179 */     if (!world.isRemote)
/*     */     {
/* 181 */       if (!this.aktiv)
/*     */       {
/* 183 */         this.aktiv = true;
/* 184 */         entityplayer.addChatMessage("[Forcicum Cell] Active");
/*     */       } else {
/* 186 */         this.aktiv = false;
/* 187 */         entityplayer.addChatMessage("[Forcicum Cell] Inactive");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 193 */     return itemstack;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(int i, CreativeTabs tabs, List itemList)
/*     */   {
/* 199 */     ItemStack charged = new ItemStack(this, 1);
/* 200 */     charged.setItemDamage(1);
/* 201 */     setForceciumlevel(charged, getMaxForceciumlevel());
/* 202 */     itemList.add(charged);
/*     */ 
/* 205 */     ItemStack empty = new ItemStack(this, 1);
/* 206 */     empty.setItemDamage(100);
/* 207 */     setForceciumlevel(empty, 0);
/* 208 */     itemList.add(empty);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemForcicumCell
 * JD-Core Version:    0.6.2
 */