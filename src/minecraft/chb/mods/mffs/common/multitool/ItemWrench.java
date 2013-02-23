/*     */ package chb.mods.mffs.common.multitool;
/*     */ 
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import chb.mods.mffs.api.IMFFS_Wrench;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import ic2.api.IWrenchable;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import railcraft.common.api.core.items.ICrowbar;
/*     */ 
/*     */ public class ItemWrench extends ItemMultitool
/*     */   implements IToolWrench, ICrowbar
/*     */ {
/*     */   public ItemWrench(int id)
/*     */   {
/*  42 */     super(id, 0);
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  52 */     if (world.isRemote) {
/*  53 */       return false;
/*     */     }
/*     */ 
/*  56 */     TileEntity tileentity = world.getBlockTileEntity(x, y, z);
/*     */ 
/*  58 */     if ((tileentity instanceof TileEntityProjector))
/*     */     {
/*  60 */       if (((TileEntityProjector)tileentity).isBurnout())
/*     */       {
/*  63 */         if (consumePower(stack, 10000, true))
/*     */         {
/*  65 */           consumePower(stack, 10000, false);
/*  66 */           ((TileEntityProjector)tileentity).setBurnedOut(false);
/*  67 */           Functions.ChattoPlayer(player, "[MultiTool] Projector repaired");
/*  68 */           return true;
/*     */         }
/*     */ 
/*  71 */         Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FP please charge need min 10000");
/*  72 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  79 */     if (((tileentity instanceof IWrenchable)) && (!(tileentity instanceof IMFFS_Wrench)))
/*     */     {
/*  81 */       if (consumePower(stack, 1000, true))
/*     */       {
/*  84 */         if (((IWrenchable)tileentity).wrenchCanSetFacing(player, side))
/*     */         {
/*  87 */           ((IWrenchable)tileentity).setFacing((short)side);
/*  88 */           consumePower(stack, 1000, false);
/*  89 */           return true;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  96 */         Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FE please charge need min 1000 for change Facing");
/*     */       }
/*     */ 
/*  99 */       if (consumePower(stack, 25000, true))
/*     */       {
/* 102 */         if (((IWrenchable)tileentity).wrenchCanRemove(player))
/*     */         {
/* 104 */           world.spawnEntityInWorld(new EntityItem(world, x, y, z, ((IWrenchable)tileentity).getWrenchDrop(player)));
/* 105 */           world.setBlockWithNotify(x, y, z, 0);
/* 106 */           consumePower(stack, 25000, false);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 112 */         Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FE please charge need min 25000 for remove");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 125 */     if ((tileentity instanceof IMFFS_Wrench))
/*     */     {
/* 129 */       if (consumePower(stack, 1000, true))
/*     */       {
/* 132 */         if (((IMFFS_Wrench)tileentity).wrenchCanManipulate(player, side))
/*     */         {
/* 135 */           if ((tileentity instanceof TileEntityMachines))
/*     */           {
/* 138 */             if ((tileentity instanceof TileEntityProjector))
/*     */             {
/* 140 */               if (((TileEntityProjector)tileentity).isActive()) {
/* 141 */                 return false;
/*     */               }
/*     */             }
/*     */ 
/* 145 */             if ((tileentity instanceof TileEntityAdvSecurityStation))
/*     */             {
/* 147 */               if (((TileEntityAdvSecurityStation)tileentity).isActive()) {
/* 148 */                 return false;
/*     */               }
/*     */             }
/* 151 */             if ((tileentity instanceof TileEntityAreaDefenseStation))
/*     */             {
/* 153 */               if (((TileEntityAreaDefenseStation)tileentity).isActive()) {
/* 154 */                 return false;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 160 */           if (((IMFFS_Wrench)tileentity).getSide() != side)
/*     */           {
/* 164 */             ((IMFFS_Wrench)tileentity).setSide(side);
/* 165 */             consumePower(stack, 1000, false);
/* 166 */             return true;
/*     */           }
/*     */ 
/* 169 */           world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(net.minecraft.block.Block.blocksList[world.getBlockId(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)])));
/* 170 */           world.setBlockWithNotify(x, y, z, 0);
/* 171 */           consumePower(stack, 1000, false);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 177 */         Functions.ChattoPlayer(player, "[MultiTool] Fail: not enough FP please charge need min 1000");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*     */   {
/* 189 */     return super.onItemRightClick(itemstack, world, entityplayer);
/*     */   }
/*     */ 
/*     */   public boolean canWrench(EntityPlayer player, int x, int y, int z)
/*     */   {
/* 196 */     if (consumePower(player.inventory.getCurrentItem(), 1000, true))
/*     */     {
/* 198 */       return true;
/*     */     }
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   public void wrenchUsed(EntityPlayer player, int x, int y, int z)
/*     */   {
/* 207 */     consumePower(player.inventory.getCurrentItem(), 1000, false);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemWrench
 * JD-Core Version:    0.6.2
 */