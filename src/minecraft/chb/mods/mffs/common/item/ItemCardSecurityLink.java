/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.common.ISidedInventory;
/*     */ 
/*     */ public class ItemCardSecurityLink extends ItemCard
/*     */ {
/*     */   public ItemCardSecurityLink(int i)
/*     */   {
/*  45 */     super(i);
/*  46 */     setIconIndex(19);
/*     */   }
/*     */ 
/*     */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
/*     */   {
/*  54 */     super.onUpdate(itemStack, world, entity, par4, par5);
/*     */ 
/*  56 */     if (this.Tick > 600)
/*     */     {
/*  58 */       int Sec_ID = getValuefromKey("Secstation_ID", itemStack);
/*  59 */       if (Sec_ID != 0)
/*     */       {
/*  61 */         TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)Linkgrid.getWorldMap(world).getSecStation().get(Integer.valueOf(Sec_ID));
/*  62 */         if (sec != null)
/*     */         {
/*  64 */           if (!sec.getDeviceName().equals(getforAreaname(itemStack)))
/*     */           {
/*  66 */             setforArea(itemStack, sec.getDeviceName());
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  71 */       this.Tick = 0;
/*     */     }
/*  73 */     this.Tick += 1;
/*     */   }
/*     */ 
/*     */   public static TileEntityAdvSecurityStation getLinkedSecurityStation(ISidedInventory inventiory, int slot, World world)
/*     */   {
/*  79 */     if (inventiory.getStackInSlot(slot) != null)
/*     */     {
/*  82 */       if ((inventiory.getStackInSlot(slot).getItem() instanceof ItemCardSecurityLink))
/*     */       {
/*  84 */         ItemCardSecurityLink card = (ItemCardSecurityLink)inventiory.getStackInSlot(slot).getItem();
/*  85 */         PointXYZ png = card.getCardTargetPoint(inventiory.getStackInSlot(slot));
/*  86 */         if (png != null)
/*     */         {
/*  88 */           if (png.dimensionId != world.provider.dimensionId) return null;
/*     */ 
/*  90 */           if ((world.getBlockTileEntity(png.X, png.Y, png.Z) instanceof TileEntityAdvSecurityStation))
/*     */           {
/*  92 */             TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)world.getBlockTileEntity(png.X, png.Y, png.Z);
/*  93 */             if (sec != null)
/*     */             {
/*  95 */               if ((sec.getDeviceID() == card.getValuefromKey("Secstation_ID", inventiory.getStackInSlot(slot))) && (card.getValuefromKey("Secstation_ID", inventiory.getStackInSlot(slot)) != 0))
/*     */               {
/*  98 */                 if (!sec.getDeviceName().equals(getforAreaname(inventiory.getStackInSlot(slot))))
/*     */                 {
/* 100 */                   setforArea(inventiory.getStackInSlot(slot), sec.getDeviceName());
/*     */                 }
/* 102 */                 return sec;
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 107 */             int Sec_ID = card.getValuefromKey("Secstation_ID", inventiory.getStackInSlot(slot));
/* 108 */             if (Sec_ID != 0)
/*     */             {
/* 110 */               TileEntityAdvSecurityStation sec = (TileEntityAdvSecurityStation)Linkgrid.getWorldMap(world).getSecStation().get(Integer.valueOf(Sec_ID));
/* 111 */               if (sec != null)
/*     */               {
/* 113 */                 card.setInformation(inventiory.getStackInSlot(slot), sec.getMaschinePoint(), "Secstation_ID", Sec_ID);
/* 114 */                 return sec;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 119 */           if (world.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded) {
/* 120 */             inventiory.setInventorySlotContents(slot, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
/*     */   {
/* 132 */     TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
/*     */ 
/* 134 */     if (!world.isRemote)
/*     */     {
/* 137 */       if ((tileEntity instanceof TileEntityControlSystem))
/*     */       {
/* 139 */         if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB))
/*     */         {
/* 142 */           return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Security Station Link>");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 149 */       if ((tileEntity instanceof TileEntityCapacitor))
/*     */       {
/* 151 */         if (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB))
/*     */         {
/* 154 */           return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 4, "<Security Station Link>");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 160 */       if (((tileEntity instanceof TileEntityAreaDefenseStation)) && 
/* 161 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 164 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 1, "<Security Station Link>");
/*     */       }
/*     */ 
/* 169 */       if (((tileEntity instanceof TileEntitySecStorage)) && 
/* 170 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 173 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Security Station Link>");
/*     */       }
/*     */ 
/* 179 */       if (((tileEntity instanceof TileEntityProjector)) && 
/* 180 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 183 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 12, "<Security Station Link>");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 189 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCardSecurityLink
 * JD-Core Version:    0.6.2
 */