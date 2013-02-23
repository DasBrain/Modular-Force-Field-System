/*     */ package chb.mods.mffs.common.item;
/*     */ 
/*     */ import chb.mods.mffs.api.IForceEnergyStorageBlock;
/*     */ import chb.mods.mffs.api.IPowerLinkItem;
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class ItemCardPowerLink extends ItemCard
/*     */   implements IPowerLinkItem
/*     */ {
/*     */   public IForceEnergyStorageBlock storage;
/*     */ 
/*     */   public ItemCardPowerLink(int i)
/*     */   {
/*  48 */     super(i);
/*  49 */     setIconIndex(17);
/*     */   }
/*     */ 
/*     */   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
/*     */   {
/*  56 */     super.onUpdate(itemStack, world, entity, par4, par5);
/*     */ 
/*  58 */     if (this.Tick > 600)
/*     */     {
/*  60 */       int Cap_ID = getValuefromKey("CapacitorID", itemStack);
/*  61 */       if (Cap_ID != 0)
/*     */       {
/*  63 */         TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(Cap_ID));
/*  64 */         if (cap != null)
/*     */         {
/*  66 */           if (!cap.getDeviceName().equals(getforAreaname(itemStack)))
/*     */           {
/*  68 */             setforArea(itemStack, cap.getDeviceName());
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  73 */       this.Tick = 0;
/*     */     }
/*  75 */     this.Tick += 1;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  82 */     TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
/*     */ 
/*  84 */     if (!world.isRemote)
/*     */     {
/*  87 */       if (((tileEntity instanceof TileEntityConverter)) && 
/*  88 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/*  91 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Power-Link>");
/*     */       }
/*     */ 
/*  98 */       if (((tileEntity instanceof TileEntityProjector)) && 
/*  99 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 102 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Power-Link>");
/*     */       }
/*     */ 
/* 107 */       if (((tileEntity instanceof TileEntityExtractor)) && 
/* 108 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 111 */         if (((TileEntityExtractor)tileEntity).getStackInSlot(1) == null)
/*     */         {
/* 113 */           ((TileEntityExtractor)tileEntity).setInventorySlotContents(1, itemstack);
/* 114 */           entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
/* 115 */           Functions.ChattoPlayer(entityplayer, "Success: <Power-Link> Card installed");
/* 116 */           return true;
/*     */         }
/*     */ 
/* 120 */         if (((TileEntityExtractor)tileEntity).getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemcardempty)
/*     */         {
/* 122 */           ItemStack itemstackcopy = itemstack.copy();
/* 123 */           ((TileEntityExtractor)tileEntity).setInventorySlotContents(1, itemstackcopy);
/* 124 */           Functions.ChattoPlayer(entityplayer, "Success: <Power-Link> Card data copied ");
/* 125 */           return true;
/*     */         }
/* 127 */         Functions.ChattoPlayer(entityplayer, "Fail: Slot is not empty");
/* 128 */         return false;
/*     */       }
/*     */ 
/* 134 */       if (((tileEntity instanceof TileEntityAreaDefenseStation)) && 
/* 135 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 138 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 0, "<Power-Link>");
/*     */       }
/*     */ 
/* 142 */       if (((tileEntity instanceof TileEntityCapacitor)) && 
/* 143 */         (SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)))
/*     */       {
/* 146 */         return Functions.setIteminSlot(itemstack, entityplayer, tileEntity, 2, "<Power-Link>");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   public IForceEnergyStorageBlock getForceEnergyStorageBlock(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 156 */     if ((itemStack != null) && ((itemStack.getItem() instanceof ItemCard)))
/*     */     {
/* 158 */       if (((ItemCard)itemStack.getItem()).isvalid(itemStack))
/*     */       {
/* 160 */         PointXYZ png = getCardTargetPoint(itemStack);
/* 161 */         if (png != null)
/*     */         {
/* 163 */           if (png.dimensionId != world.provider.dimensionId) return null;
/*     */ 
/* 165 */           if ((world.getBlockTileEntity(png.X, png.Y, png.Z) instanceof TileEntityCapacitor))
/*     */           {
/* 167 */             TileEntityCapacitor cap = (TileEntityCapacitor)world.getBlockTileEntity(png.X, png.Y, png.Z);
/* 168 */             if (cap != null)
/*     */             {
/* 170 */               if ((cap.getPowerStorageID() == getValuefromKey("CapacitorID", itemStack)) && (getValuefromKey("CapacitorID", itemStack) != 0))
/*     */               {
/* 173 */                 if (!cap.getDeviceName().equals(getforAreaname(itemStack)))
/*     */                 {
/* 175 */                   setforArea(itemStack, cap.getDeviceName());
/*     */                 }
/*     */ 
/* 178 */                 if (cap.getTransmitRange() >= PointXYZ.distance(tem.getMaschinePoint(), cap.getMaschinePoint()))
/* 179 */                   return cap; return null;
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 184 */             int Cap_ID = getValuefromKey("CapacitorID", itemStack);
/* 185 */             if (Cap_ID != 0)
/*     */             {
/* 187 */               TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(png.getPointWorld()).getCapacitor().get(Integer.valueOf(Cap_ID));
/* 188 */               if (cap != null)
/*     */               {
/* 190 */                 setInformation(itemStack, cap.getMaschinePoint(), "CapacitorID", Cap_ID);
/* 191 */                 if (cap.getTransmitRange() >= PointXYZ.distance(tem.getMaschinePoint(), cap.getMaschinePoint()))
/* 192 */                   return cap; return null;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 197 */           if (world.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded) {
/* 198 */             ((ItemCard)itemStack.getItem()).setinvalid(itemStack);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */   public int getAvailablePower(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 211 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 212 */     if (this.storage != null)
/* 213 */       return this.storage.getStorageAvailablePower();
/* 214 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getMaximumPower(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 219 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 220 */     if (this.storage != null)
/* 221 */       return this.storage.getStorageMaxPower();
/* 222 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getPowersourceID(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 227 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 228 */     if (this.storage != null)
/* 229 */       return this.storage.getPowerStorageID();
/* 230 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getPercentageCapacity(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 235 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 236 */     if (this.storage != null)
/* 237 */       return this.storage.getPercentageStorageCapacity();
/* 238 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean consumePower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
/*     */   {
/* 244 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 245 */     if (this.storage != null)
/* 246 */       return this.storage.consumePowerfromStorage(powerAmount, simulation);
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean insertPower(ItemStack itemStack, int powerAmount, boolean simulation, TileEntityMachines tem, World world)
/*     */   {
/* 253 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 254 */     if (this.storage != null)
/* 255 */       return this.storage.insertPowertoStorage(powerAmount, simulation);
/* 256 */     return false;
/*     */   }
/*     */ 
/*     */   public int getfreeStorageAmount(ItemStack itemStack, TileEntityMachines tem, World world)
/*     */   {
/* 262 */     this.storage = getForceEnergyStorageBlock(itemStack, tem, world);
/* 263 */     if (this.storage != null)
/* 264 */       return this.storage.getfreeStorageAmount();
/* 265 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isPowersourceItem()
/*     */   {
/* 270 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.item.ItemCardPowerLink
 * JD-Core Version:    0.6.2
 */