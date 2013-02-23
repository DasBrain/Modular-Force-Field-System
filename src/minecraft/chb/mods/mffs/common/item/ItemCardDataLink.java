/*    */ package chb.mods.mffs.common.item;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.Linkgrid;
/*    */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*    */ import chb.mods.mffs.common.MFFSMaschines;
/*    */ import chb.mods.mffs.common.NBTTagCompoundHelper;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemCardDataLink extends ItemCard
/*    */ {
/*    */   public ItemCardDataLink(int id)
/*    */   {
/* 20 */     super(id);
/* 21 */     setMaxStackSize(1);
/* 22 */     setIconIndex(22);
/*    */   }
/*    */ 
/*    */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
/*    */   {
/* 29 */     super.onUpdate(itemStack, world, entity, par4, par5);
/*    */ 
/* 31 */     if (this.Tick > 600)
/*    */     {
/* 34 */       int DeviceID = getValuefromKey("DeviceID", itemStack);
/* 35 */       if (DeviceID != 0)
/*    */       {
/* 37 */         TileEntityMachines device = Linkgrid.getWorldMap(world).getTileEntityMachines(getDeviceTyp(itemStack), DeviceID);
/* 38 */         if (device != null)
/*    */         {
/* 41 */           if (!device.getDeviceName().equals(getforAreaname(itemStack)))
/*    */           {
/* 43 */             setforArea(itemStack, device.getDeviceName());
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 50 */       this.Tick = 0;
/*    */     }
/* 52 */     this.Tick += 1;
/*    */   }
/*    */ 
/*    */   public void setInformation(ItemStack itemStack, PointXYZ png, String key, int value, TileEntity tileentity)
/*    */   {
/* 58 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/* 59 */     nbtTagCompound.setString("displayName", MFFSMaschines.fromTE(tileentity).displayName);
/* 60 */     super.setInformation(itemStack, png, key, value);
/*    */   }
/*    */ 
/*    */   public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
/*    */   {
/* 66 */     NBTTagCompound tag = NBTTagCompoundHelper.getTAGfromItemstack(itemStack);
/*    */ 
/* 68 */     info.add("DeviceTyp: " + getDeviceTyp(itemStack));
/* 69 */     info.add("DeviceName: " + getforAreaname(itemStack));
/*    */ 
/* 71 */     if (tag.hasKey("worldname"))
/* 72 */       info.add("World: " + tag.getString("worldname"));
/* 73 */     if (tag.hasKey("linkTarget"))
/* 74 */       info.add("Coords: " + new PointXYZ(tag.getCompoundTag("linkTarget")).toString());
/* 75 */     if (tag.hasKey("valid"))
/* 76 */       info.add(tag.getBoolean("valid") ? "§2Valid" : "§4Invalid");
/*    */   }
/*    */ 
/*    */   public static String getDeviceTyp(ItemStack itemstack)
/*    */   {
/* 81 */     NBTTagCompound nbtTagCompound = NBTTagCompoundHelper.getTAGfromItemstack(itemstack);
/* 82 */     if (nbtTagCompound != null)
/*    */     {
/* 84 */       return nbtTagCompound.getString("displayName");
/*    */     }
/* 86 */     return "-";
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCardDataLink
 * JD-Core Version:    0.6.2
 */