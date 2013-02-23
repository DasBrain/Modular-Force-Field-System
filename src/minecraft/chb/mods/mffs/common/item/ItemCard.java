/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ public class ItemCard extends Item
/*     */ {
/*  35 */   private StringBuffer info = new StringBuffer();
/*     */   protected int Tick;
/*     */ 
/*     */   public ItemCard(int id)
/*     */   {
/*  39 */     super(id);
/*  40 */     setMaxStackSize(1);
/*  41 */     this.Tick = 0;
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  47 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   public static void setforArea(ItemStack itemStack, String areaname)
/*     */   {
/*  59 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  60 */     nbtTagCompound.setString("Areaname", areaname);
/*     */   }
/*     */ 
/*     */   public static String getforAreaname(ItemStack itemstack)
/*     */   {
/*  66 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/*  67 */     if (nbtTagCompound != null)
/*     */     {
/*  69 */       return nbtTagCompound.getString("Areaname");
/*     */     }
/*  71 */     return "not set";
/*     */   }
/*     */ 
/*     */   public boolean isvalid(ItemStack itemStack) {
/*  75 */     NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  76 */     if (tag.hasKey("valid"))
/*  77 */       return tag.getBoolean("valid");
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   public void setinvalid(ItemStack itemStack) {
/*  82 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  83 */     nbtTagCompound.setBoolean("valid", false);
/*     */   }
/*     */ 
/*     */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*     */   {
/*  88 */     NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*  89 */     info.add("Linkto: " + getforAreaname(itemStack));
/*  90 */     if (tag.hasKey("worldname"))
/*  91 */       info.add("World: " + tag.getString("worldname"));
/*  92 */     if (tag.hasKey("linkTarget"))
/*  93 */       info.add("Coords: " + new PointXYZ(tag.getCompoundTag("linkTarget")).toString());
/*  94 */     if (tag.hasKey("valid"))
/*  95 */       info.add(tag.getBoolean("valid") ? "§2Valid" : "§4Invalid");
/*     */   }
/*     */ 
/*     */   public void setInformation(ItemStack itemStack, PointXYZ png, String key, int value)
/*     */   {
/* 103 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*     */ 
/* 105 */     nbtTagCompound.setInteger(key, value);
/* 106 */     nbtTagCompound.setString("worldname", DimensionManager.getWorld(png.dimensionId).getWorldInfo().getWorldName());
/* 107 */     nbtTagCompound.setTag("linkTarget", png.asNBT());
/* 108 */     nbtTagCompound.setBoolean("valid", true);
/*     */   }
/*     */ 
/*     */   public int getValuefromKey(String key, ItemStack itemStack)
/*     */   {
/* 116 */     NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 117 */     if (tag.hasKey(key))
/* 118 */       return tag.getInteger(key);
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */   public PointXYZ getCardTargetPoint(ItemStack itemStack) {
/* 123 */     NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 124 */     if (tag.hasKey("linkTarget")) {
/* 125 */       return new PointXYZ(tag.getCompoundTag("linkTarget"));
/*     */     }
/* 127 */     tag.setBoolean("valid", false);
/*     */ 
/* 130 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCard
 * JD-Core Version:    0.6.2
 */