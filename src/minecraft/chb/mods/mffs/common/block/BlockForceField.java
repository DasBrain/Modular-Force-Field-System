/*     */ package chb.mods.mffs.common.block;
/*     */ 
/*     */ import chb.mods.mffs.api.IForceFieldBlock;
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.CommonProxy;
/*     */ import chb.mods.mffs.common.ForceFieldBlockStack;
/*     */ import chb.mods.mffs.common.ForceFieldTyps;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.MFFSDamageSource;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.WorldMap;
/*     */ import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityForceField;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockContainer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockForceField extends BlockContainer
/*     */   implements IForceFieldBlock
/*     */ {
/*     */   public static int renderer;
/*     */   public int posx;
/*     */   public int posy;
/*     */   public int posz;
/*     */ 
/*     */   public BlockForceField(int i)
/*     */   {
/*  58 */     super(i, i, Material.glass);
/*  59 */     setBlockUnbreakable();
/*  60 */     setResistance(999.0F);
/*  61 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  67 */     return "/chb/mods/mffs/sprites/blocks.png";
/*     */   }
/*     */ 
/*     */   public void onBlockAdded(World world, int i, int j, int k)
/*     */   {
/*  73 */     this.posx = i;
/*  74 */     this.posy = j;
/*  75 */     this.posz = k;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getRenderBlockPass()
/*     */   {
/*  82 */     if (ModularForceFieldSystem.proxy.getClientWorld().getBlockMetadata(this.posx, this.posy, this.posz) == ForceFieldTyps.Camouflage.ordinal())
/*     */     {
/*  84 */       TileEntityForceField ForceField = (TileEntityForceField)ModularForceFieldSystem.proxy.getClientWorld().getBlockTileEntity(this.posx, this.posy, this.posz);
/*     */ 
/*  86 */       if (ForceField != null)
/*     */       {
/*  88 */         if ((ForceField.getTexturid(1) == 67) || (ForceField.getTexturid(1) == 205))
/*     */         {
/*  90 */           return 1;
/*     */         }
/*  92 */         return 0;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  97 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/* 103 */     return ModularForceFieldSystem.MFFSRENDER_ID;
/*     */   }
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean renderAsNormalBlock()
/*     */   {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean canSilkHarvest()
/*     */   {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   public void onNeighborBlockChange(World world, int x, int y, int z, int blockid)
/*     */   {
/* 127 */     if (blockid != ModularForceFieldSystem.MFFSFieldblock.blockID)
/*     */     {
/* 129 */       for (int x1 = -1; x1 <= 1; x1++)
/* 130 */         for (int y1 = -1; y1 <= 1; y1++)
/* 131 */           for (int z1 = -1; z1 <= 1; z1++)
/* 132 */             if (world.getBlockId(x + x1, y + y1, z + z1) != ModularForceFieldSystem.MFFSFieldblock.blockID)
/*     */             {
/* 134 */               if (world.getBlockId(x + x1, y + y1, z + z1) == 0)
/*     */               {
/* 136 */                 breakBlock(world, x + x1, y + y1, z + z1, 0, 0);
/*     */               }
/*     */             }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void breakBlock(World world, int i, int j, int k, int a, int b)
/*     */   {
/* 147 */     ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(Integer.valueOf(new PointXYZ(i, j, k, world).hashCode()));
/*     */ 
/* 149 */     if ((ffworldmap != null) && 
/* 150 */       (!ffworldmap.isEmpty())) {
/* 151 */       TileEntityProjector Projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/* 152 */       if (Projector != null)
/* 153 */         if (!Projector.isActive()) {
/* 154 */           ffworldmap.removebyProjector(ffworldmap.getProjectorID());
/*     */         } else {
/* 156 */           world.setBlockAndMetadataWithNotify(i, j, k, ModularForceFieldSystem.MFFSFieldblock.blockID, ffworldmap.getTyp());
/* 157 */           world.markBlockForUpdate(i, j, k);
/* 158 */           ffworldmap.setSync(true);
/*     */ 
/* 160 */           if (ffworldmap.getTyp() == 1)
/* 161 */             Projector.consumePower(ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier, false);
/*     */           else
/* 163 */             Projector.consumePower(ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier * ModularForceFieldSystem.forcefieldblockzappermodifier, false);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
/*     */   {
/* 175 */     if (par1World.isRemote) {
/* 176 */       return;
/*     */     }
/* 178 */     ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(par1World).getForceFieldStackMap(Integer.valueOf(new PointXYZ(par2, par3, par4, par1World).hashCode()));
/*     */ 
/* 180 */     if ((ffworldmap != null) && (!ModularForceFieldSystem.adventuremap.booleanValue()))
/*     */     {
/* 183 */       TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(par1World).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/* 184 */       if (projector != null) {
/* 185 */         switch (projector.getaccesstyp())
/*     */         {
/*     */         case 0:
/* 188 */           par5EntityPlayer.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
/* 189 */           Functions.ChattoPlayer(par5EntityPlayer, "[Force Field] Attention High Energy Field");
/* 190 */           break;
/*     */         case 2:
/*     */         case 3:
/* 194 */           if (!SecurityHelper.isAccessGranted(projector, par5EntityPlayer, par1World, SecurityRight.SR))
/*     */           {
/* 196 */             par5EntityPlayer.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
/* 197 */             Functions.ChattoPlayer(par5EntityPlayer, "[Force Field] Attention High Energy Field");
/*     */           }
/*     */           break;
/*     */         case 1:
/*     */         }
/*     */       }
/* 203 */       if (!SecurityHelper.isAccessGranted(projector, par5EntityPlayer, par1World, SecurityRight.SR))
/*     */       {
/* 205 */         par5EntityPlayer.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
/* 206 */         Functions.ChattoPlayer(par5EntityPlayer, "[Force Field] Attention High Energy Field");
/*     */       }
/*     */     }
/*     */ 
/* 210 */     Random random = null;
/* 211 */     updateTick(par1World, par2, par3, par4, random);
/*     */   }
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
/*     */   {
/* 218 */     if (world.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal()) {
/* 219 */       float f = 0.0625F;
/* 220 */       return AxisAlignedBB.getBoundingBox(i + f, j + f, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
/*     */     }
/*     */ 
/* 223 */     return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
/*     */   }
/*     */ 
/*     */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
/*     */   {
/* 229 */     return AxisAlignedBB.getBoundingBox(i, j, k, i + 0, j + 0, k + 0);
/*     */   }
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
/*     */   {
/* 238 */     if (world.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal()) {
/* 239 */       if ((entity instanceof EntityLiving)) {
/* 240 */         entity.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
/*     */       }
/*     */     }
/* 243 */     else if ((entity instanceof EntityPlayer)) {
/* 244 */       ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getorcreateFFStackMap(i, j, k, world);
/*     */ 
/* 246 */       if (ffworldmap != null)
/*     */       {
/* 248 */         TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/*     */ 
/* 250 */         if (projector != null)
/*     */         {
/* 253 */           boolean passtrue = false;
/*     */ 
/* 255 */           switch (projector.getaccesstyp())
/*     */           {
/*     */           case 0:
/* 258 */             passtrue = false;
/* 259 */             if (ModularForceFieldSystem.Admin.equals(((EntityPlayer)entity).username))
/* 260 */               passtrue = true; break;
/*     */           case 1:
/* 263 */             passtrue = true;
/* 264 */             break;
/*     */           case 2:
/* 266 */             TileEntityCapacitor generator = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(ffworldmap.getGenratorID()));
/* 267 */             passtrue = SecurityHelper.isAccessGranted(generator, (EntityPlayer)entity, world, SecurityRight.FFB);
/* 268 */             break;
/*     */           case 3:
/* 270 */             passtrue = SecurityHelper.isAccessGranted(projector, (EntityPlayer)entity, world, SecurityRight.FFB);
/*     */           }
/*     */ 
/* 274 */           if (!passtrue)
/*     */           {
/* 276 */             ((EntityPlayer)entity).attackEntityFrom(MFFSDamageSource.fieldShock, 20);
/*     */           }
/*     */           else {
/* 279 */             ((EntityPlayer)entity).attackEntityFrom(MFFSDamageSource.fieldShock, 1);
/*     */           }
/* 281 */           Functions.ChattoPlayer((EntityPlayer)entity, "[Force Field] Attention High Energy Field");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int quantityDropped(Random random)
/*     */   {
/* 292 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side)
/*     */   {
/* 297 */     int xCord = x;
/* 298 */     int yCord = y;
/* 299 */     int zCord = z;
/*     */ 
/* 301 */     switch (side) { case 0:
/* 302 */       yCord++;
/* 303 */       break;
/*     */     case 1:
/* 304 */       yCord--;
/* 305 */       break;
/*     */     case 2:
/* 306 */       zCord++;
/* 307 */       break;
/*     */     case 3:
/* 308 */       zCord--;
/* 309 */       break;
/*     */     case 4:
/* 310 */       xCord++;
/* 311 */       break;
/*     */     case 5:
/* 312 */       xCord--;
/*     */     }
/*     */ 
/* 316 */     if ((this.blockID == iblockaccess.getBlockId(x, y, z)) && (iblockaccess.getBlockMetadata(x, y, z) == iblockaccess.getBlockMetadata(xCord, yCord, zCord))) {
/* 317 */       return false;
/*     */     }
/* 319 */     return super.shouldSideBeRendered(iblockaccess, x, y, z, side);
/*     */   }
/*     */ 
/*     */   public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
/*     */   {
/* 326 */     TileEntity tileEntity = iblockaccess.getBlockTileEntity(i, j, k);
/*     */ 
/* 328 */     if ((tileEntity != null) && ((tileEntity instanceof TileEntityForceField)))
/*     */     {
/* 330 */       if ((l < 0) || (l > 5)) return 0;
/*     */ 
/* 332 */       return ((TileEntityForceField)tileEntity).getTexturid(l);
/*     */     }
/* 334 */     if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Camouflage.ordinal())
/*     */     {
/* 336 */       return 180;
/*     */     }
/*     */ 
/* 339 */     if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Default.ordinal()) return 0;
/* 340 */     if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal()) return 1;
/* 341 */     if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Area.ordinal()) return 2;
/* 342 */     if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Containment.ordinal()) return 3;
/*     */ 
/* 344 */     return 5;
/*     */   }
/*     */ 
/*     */   public float getExplosionResistance(Entity entity, World world, int i, int j, int k, double d, double d1, double d2)
/*     */   {
/* 352 */     ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(Integer.valueOf(new PointXYZ(i, j, k, world).hashCode()));
/*     */ 
/* 354 */     if ((ffworldmap != null) && (!ffworldmap.isEmpty()))
/*     */     {
/* 356 */       TileEntity tileEntity = (TileEntity)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/*     */ 
/* 359 */       if (((tileEntity instanceof TileEntityProjector)) && (tileEntity != null)) {
/* 360 */         ((TileEntityProjector)tileEntity).consumePower(ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier, false);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 366 */     return 999.0F;
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(World world, int i, int j, int k, Random random)
/*     */   {
/* 372 */     if ((ModularForceFieldSystem.showZapperParticles) && (world.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal())) {
/* 373 */       double d = i + Math.random() + 0.2D;
/* 374 */       double d1 = j + Math.random() + 0.2D;
/* 375 */       double d2 = k + Math.random() + 0.2D;
/*     */ 
/* 377 */       world.spawnParticle("townaura", d, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir)
/*     */   {
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */   public void updateTick(World world, int x, int y, int z, Random random)
/*     */   {
/* 388 */     ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(Integer.valueOf(new PointXYZ(x, y, z, world).hashCode()));
/*     */ 
/* 390 */     if (ffworldmap != null)
/*     */     {
/* 392 */       if (!ffworldmap.isEmpty()) {
/* 393 */         TileEntityProjector Projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/* 394 */         if ((Projector != null) && 
/* 395 */           (!Projector.isActive())) {
/* 396 */           ffworldmap.removebyProjector(ffworldmap.getProjectorID());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 402 */     if ((ffworldmap == null) || (ffworldmap.isEmpty()))
/*     */     {
/* 404 */       world.removeBlockTileEntity(x, y, z);
/* 405 */       world.setBlockWithNotify(x, y, z, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public TileEntity createTileEntity(World world, int meta)
/*     */   {
/* 411 */     if (meta == ForceFieldTyps.Camouflage.ordinal())
/*     */     {
/* 414 */       return new TileEntityForceField();
/*     */     }
/*     */ 
/* 418 */     return null;
/*     */   }
/*     */ 
/*     */   public TileEntity createNewTileEntity(World world)
/*     */   {
/* 423 */     return createTileEntity(world, 0);
/*     */   }
/*     */ 
/*     */   public void weakenForceField(World world, int x, int y, int z)
/*     */   {
/* 431 */     if (ModularForceFieldSystem.influencedbyothermods.booleanValue())
/*     */     {
/* 433 */       world.setBlockWithNotify(x, y, z, 0);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockForceField
 * JD-Core Version:    0.6.2
 */