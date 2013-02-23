/*     */ package chb.mods.mffs.common.block;
/*     */ 
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.item.ItemCardEmpty;
/*     */ import chb.mods.mffs.common.item.ItemCardPowerLink;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.common.modules.ModuleBase;
/*     */ import chb.mods.mffs.common.multitool.ItemMultitool;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockContainer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ 
/*     */ public abstract class BlockMFFSBase extends BlockContainer
/*     */ {
/*     */   private int blockid;
/*     */ 
/*     */   public BlockMFFSBase(int i)
/*     */   {
/*  59 */     super(i, Material.iron);
/*  60 */     this.blockid = i;
/*  61 */     setBlockUnbreakable();
/*  62 */     setRequiresSelfNotify();
/*  63 */     setResistance(100.0F);
/*  64 */     setStepSound(soundMetalFootstep);
/*  65 */     setRequiresSelfNotify();
/*  66 */     setCreativeTab(ModularForceFieldSystem.MFFSTab);
/*     */   }
/*     */ 
/*     */   public abstract TileEntity createNewTileEntity(World paramWorld);
/*     */ 
/*     */   public abstract String getTextureFile();
/*     */ 
/*     */   public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
/*     */   {
/*  82 */     if (entityplayer.isSneaking())
/*     */     {
/*  84 */       return false;
/*     */     }
/*     */ 
/*  87 */     if (world.isRemote) {
/*  88 */       return true;
/*     */     }
/*     */ 
/*  91 */     TileEntityMachines tileentity = (TileEntityMachines)world.getBlockTileEntity(i, j, k);
/*     */ 
/*  93 */     if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMultitool)))
/*     */     {
/*  95 */       return false;
/*     */     }
/*     */ 
/*  98 */     if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardEmpty)))
/*     */     {
/* 100 */       return false;
/*     */     }
/*     */ 
/* 103 */     if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ModuleBase)))
/*     */     {
/* 105 */       return false;
/*     */     }
/*     */ 
/* 108 */     if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardPowerLink)))
/*     */     {
/* 110 */       return false;
/*     */     }
/*     */ 
/* 113 */     if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardSecurityLink)))
/*     */     {
/* 115 */       return false;
/*     */     }
/*     */ 
/* 118 */     if ((entityplayer.getCurrentEquippedItem() != null) && (entityplayer.getCurrentEquippedItem().itemID == Block.lever.blockID))
/*     */     {
/* 120 */       return false;
/*     */     }
/*     */ 
/* 123 */     if (((tileentity instanceof TileEntityAdvSecurityStation)) && 
/* 124 */       (tileentity.isActive()))
/*     */     {
/* 126 */       if (!SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.CSR)) {
/* 127 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 131 */     if ((tileentity instanceof TileEntityControlSystem))
/*     */     {
/* 133 */       if (!SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.UCS)) {
/* 134 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 138 */     if (!SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.EB)) {
/* 139 */       return true;
/*     */     }
/*     */ 
/* 142 */     if (!world.isRemote) {
/* 143 */       entityplayer.openGui(ModularForceFieldSystem.instance, 0, world, i, j, k);
/*     */     }
/*     */ 
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   public void breakBlock(World world, int i, int j, int k, int a, int b)
/*     */   {
/* 153 */     TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
/* 154 */     if ((tileEntity instanceof TileEntityMachines))
/* 155 */       ((TileEntityMachines)tileEntity).dropplugins();
/* 156 */     world.removeBlockTileEntity(i, j, k);
/*     */   }
/*     */ 
/*     */   public int idDropped(int i, Random random)
/*     */   {
/* 162 */     return this.blockID;
/*     */   }
/*     */ 
/*     */   public static boolean isActive(IBlockAccess iblockaccess, int i, int j, int k)
/*     */   {
/* 167 */     TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);
/* 168 */     if ((tileentity instanceof TileEntityMachines)) {
/* 169 */       return ((TileEntityMachines)tileentity).isActive();
/*     */     }
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
/*     */   {
/* 179 */     TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
/* 180 */     if ((tileEntity instanceof TileEntityMachines))
/*     */     {
/* 184 */       int l = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*     */ 
/* 186 */       int i1 = Math.round(entityliving.rotationPitch);
/* 187 */       if (i1 >= 65)
/* 188 */         ((TileEntityMachines)tileEntity).setSide(1);
/* 189 */       else if (i1 <= -65)
/* 190 */         ((TileEntityMachines)tileEntity).setSide(0);
/* 191 */       else if (l == 0)
/* 192 */         ((TileEntityMachines)tileEntity).setSide(2);
/* 193 */       else if (l == 1)
/* 194 */         ((TileEntityMachines)tileEntity).setSide(5);
/* 195 */       else if (l == 2)
/* 196 */         ((TileEntityMachines)tileEntity).setSide(3);
/* 197 */       else if (l == 3)
/* 198 */         ((TileEntityMachines)tileEntity).setSide(4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
/*     */   {
/* 209 */     int typ = 0;
/*     */ 
/* 212 */     TileEntity tileentity = iblockaccess.getBlockTileEntity(i, j, k);
/*     */ 
/* 214 */     int facing = (tileentity instanceof TileEntityMachines) ? ((TileEntityMachines)tileentity).getSide() : 1;
/*     */ 
/* 217 */     ForgeDirection blockfacing = ForgeDirection.getOrientation(l);
/* 218 */     ForgeDirection TileEntityfacing = ForgeDirection.getOrientation(facing);
/*     */ 
/* 220 */     if ((tileentity instanceof TileEntityProjector)) {
/* 221 */       typ = ((TileEntityProjector)tileentity).getProjektor_Typ();
/*     */     }
/*     */ 
/* 225 */     if (isActive(iblockaccess, i, j, k)) {
/* 226 */       if (blockfacing.equals(TileEntityfacing)) {
/* 227 */         return typ * 16 + 4 + 1;
/*     */       }
/* 229 */       if (blockfacing.equals(TileEntityfacing.getOpposite())) {
/* 230 */         return typ * 16 + 4 + 2;
/*     */       }
/* 232 */       return typ * 16 + 4;
/*     */     }
/*     */ 
/* 236 */     if (blockfacing.equals(TileEntityfacing)) {
/* 237 */       return typ * 16 + 1;
/*     */     }
/* 239 */     if (blockfacing.equals(TileEntityfacing.getOpposite())) {
/* 240 */       return typ * 16 + 2;
/*     */     }
/* 242 */     return typ * 16;
/*     */   }
/*     */ 
/*     */   public float getExplosionResistance(Entity entity, World world, int i, int j, int k, double d, double d1, double d2)
/*     */   {
/* 251 */     if ((world.getBlockTileEntity(i, j, k) instanceof TileEntityMachines)) {
/* 252 */       TileEntity tileentity = world.getBlockTileEntity(i, j, k);
/* 253 */       if (((TileEntityMachines)tileentity).isActive()) {
/* 254 */         return 999.0F;
/*     */       }
/* 256 */       return 100.0F;
/*     */     }
/*     */ 
/* 259 */     return 100.0F;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockMFFSBase
 * JD-Core Version:    0.6.2
 */