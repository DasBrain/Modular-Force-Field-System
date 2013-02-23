/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class ItemAccessCard extends ItemCardPersonalID
/*     */ {
/*     */   private int Tick;
/*     */ 
/*     */   public ItemAccessCard(int i)
/*     */   {
/*  44 */     super(i);
/*  45 */     setIconIndex(20);
/*  46 */     setMaxStackSize(1);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  51 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDamageable()
/*     */   {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
/*     */   {
/*  68 */     if (this.Tick > 1200)
/*     */     {
/*  70 */       if (getvalidity(itemStack) > 0)
/*     */       {
/*  72 */         setvalidity(itemStack, getvalidity(itemStack) - 1);
/*     */ 
/*  74 */         int SEC_ID = getlinkID(itemStack);
/*  75 */         if (SEC_ID != 0)
/*     */         {
/*  77 */           TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)Linkgrid.getWorldMap(world).getSecStation().get(Integer.valueOf(SEC_ID));
/*  78 */           if (sec != null)
/*     */           {
/*  80 */             if (!sec.getDeviceName().equals(getforAreaname(itemStack)))
/*     */             {
/*  82 */               setforArea(itemStack, sec);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  88 */       this.Tick = 0;
/*     */     }
/*  90 */     this.Tick += 1;
/*     */   }
/*     */ 
/*     */   public static void setvalidity(ItemStack itemStack, int min)
/*     */   {
/*  96 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  97 */     nbtTagCompound.setInteger("validity", min);
/*     */   }
/*     */ 
/*     */   public static int getvalidity(ItemStack itemStack)
/*     */   {
/* 104 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 105 */     if (nbtTagCompound != null)
/*     */     {
/* 107 */       return nbtTagCompound.getInteger("validity");
/*     */     }
/* 109 */     return 0;
/*     */   }
/*     */ 
/*     */   public static boolean hasRight(ItemStack itemStack, SecurityRight sr) {
/* 113 */     NBTTagCompound itemTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 114 */     NBTTagCompound rightsTag = itemTag.getCompoundTag("rights");
/*     */ 
/* 116 */     if (itemTag.hasKey(sr.rightKey)) {
/* 117 */       setRight(itemStack, sr, itemTag.getBoolean(sr.rightKey));
/* 118 */       itemTag.removeTag(sr.rightKey);
/*     */     }
/* 120 */     return rightsTag.getBoolean(sr.rightKey);
/*     */   }
/*     */ 
/*     */   public static void setRight(ItemStack itemStack, SecurityRight sr, boolean value)
/*     */   {
/* 125 */     NBTTagCompound rightsTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getCompoundTag("rights");
/* 126 */     rightsTag.setBoolean(sr.rightKey, value);
/* 127 */     NBTTagCompoundHelper.getTAGfromItemstack(itemStack).setCompoundTag("rights", rightsTag);
/*     */   }
/*     */ 
/*     */   public static int getlinkID(ItemStack itemstack)
/*     */   {
/* 133 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/* 134 */     if (nbtTagCompound != null)
/*     */     {
/* 136 */       return nbtTagCompound.getInteger("linkID");
/*     */     }
/* 138 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void setlinkID(ItemStack itemStack, TileEntityAdvSecurityStation sec)
/*     */   {
/* 145 */     if (sec != null)
/*     */     {
/* 147 */       NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 148 */       nbtTagCompound.setInteger("linkID", sec.getDeviceID());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setforArea(ItemStack itemStack, TileEntityAdvSecurityStation sec)
/*     */   {
/* 154 */     if (sec != null)
/*     */     {
/* 156 */       NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 157 */       nbtTagCompound.setString("Areaname", sec.getDeviceName());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getforAreaname(ItemStack itemstack)
/*     */   {
/* 164 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/* 165 */     if (nbtTagCompound != null)
/*     */     {
/* 167 */       return nbtTagCompound.getString("Areaname");
/*     */     }
/* 169 */     return "not set";
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/* 177 */     String SecurityArea = String.format("Security Area: %s ", new Object[] { getforAreaname(itemStack) });
/* 178 */     info.add(SecurityArea);
/*     */ 
/* 180 */     String validity = String.format("period of validity: %s min", new Object[] { Integer.valueOf(getvalidity(itemStack)) });
/* 181 */     info.add(validity);
/*     */     NBTTagCompound rightsTag;
/* 183 */     if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54))) {
/* 184 */       rightsTag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack).getCompoundTag("rights");
/* 185 */       info.add("Rights:");
/* 186 */       for (SecurityRight sr : SecurityRight.rights.values())
/* 187 */         if (rightsTag.getBoolean(sr.rightKey))
/* 188 */           info.add("-" + sr.name);
/*     */     }
/*     */     else
/*     */     {
/* 192 */       info.add("Rights: (Hold Shift)");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemAccessCard
 * JD-Core Version:    0.6.2
 */