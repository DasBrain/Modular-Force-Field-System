/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class ItemCardPersonalID extends Item
/*     */ {
/*     */   public ItemCardPersonalID(int i)
/*     */   {
/*  39 */     super(i);
/*  40 */     setIconIndex(18);
/*  41 */     setMaxStackSize(1);
/*     */   }
/*     */ 
/*     */   public String getTextureFile() {
/*  45 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable()
/*     */   {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDamageable()
/*     */   {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean hasRight(ItemStack itemStack, SecurityRight sr) {
/*  60 */     NBTTagCompound itemTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  61 */     NBTTagCompound rightsTag = itemTag.getCompoundTag("rights");
/*     */ 
/*  63 */     if (itemTag.hasKey(sr.rightKey)) {
/*  64 */       setRight(itemStack, sr, itemTag.getBoolean(sr.rightKey));
/*  65 */       itemTag.removeTag(sr.rightKey);
/*     */     }
/*  67 */     return rightsTag.getBoolean(sr.rightKey);
/*     */   }
/*     */ 
/*     */   public static void setRight(ItemStack itemStack, SecurityRight sr, boolean value) {
/*  71 */     NBTTagCompound rightsTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getCompoundTag("rights");
/*  72 */     rightsTag.setBoolean(sr.rightKey, value);
/*  73 */     NBTTagCompoundHelper.getTAGfromItemstack(itemStack).setCompoundTag("rights", rightsTag);
/*     */   }
/*     */ 
/*     */   public static void setOwner(ItemStack itemStack, String username)
/*     */   {
/*  79 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  80 */     nbtTagCompound.setString("name", username);
/*     */   }
/*     */ 
/*     */   public String getUsername(ItemStack itemstack)
/*     */   {
/*  86 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/*  87 */     if (nbtTagCompound != null)
/*     */     {
/*  89 */       return nbtTagCompound.getString("name");
/*     */     }
/*  91 */     return "nobody";
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/*  97 */     String tooltip = String.format("Owner: %s ", new Object[] { NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getString("name") });
/*  98 */     info.add(tooltip);
/*     */ 
/* 100 */     if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
/* 101 */       info.add("Rights:");
/* 102 */       for (SecurityRight sr : SecurityRight.rights.values())
/*     */       {
/* 104 */         if (hasRight(itemStack, sr))
/* 105 */           info.add("-" + sr.name);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 110 */       info.add("Rights: (Hold Shift)");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCardPersonalID
 * JD-Core Version:    0.6.2
 */