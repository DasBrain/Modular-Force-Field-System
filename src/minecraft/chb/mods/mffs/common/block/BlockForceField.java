package chb.mods.mffs.common.block;

import chb.mods.mffs.api.IForceFieldBlock;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.CommonProxy;
import chb.mods.mffs.common.ForceFieldBlockStack;
import chb.mods.mffs.common.ForceFieldTyps;
import chb.mods.mffs.common.Functions;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.Linkgrid.Worldlinknet;
import chb.mods.mffs.common.MFFSDamageSource;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.WorldMap;
import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityForceField;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockForceField extends BlockContainer
  implements IForceFieldBlock
{
  public static int renderer;
  public int posx;
  public int posy;
  public int posz;

  public BlockForceField(int i)
  {
    super(i, i, Material.glass);
    setBlockUnbreakable();
    setResistance(999.0F);
    setTickRandomly(true);
  }

  public String getTextureFile()
  {
    return "/chb/mods/mffs/sprites/blocks.png";
  }

  public void onBlockAdded(World world, int i, int j, int k)
  {
    this.posx = i;
    this.posy = j;
    this.posz = k;
  }

  @SideOnly(Side.CLIENT)
  public int getRenderBlockPass()
  {
    if (ModularForceFieldSystem.proxy.getClientWorld().getBlockMetadata(this.posx, this.posy, this.posz) == ForceFieldTyps.Camouflage.ordinal())
    {
      TileEntityForceField ForceField = (TileEntityForceField)ModularForceFieldSystem.proxy.getClientWorld().getBlockTileEntity(this.posx, this.posy, this.posz);

      if (ForceField != null)
      {
        if ((ForceField.getTexturid(1) == 67) || (ForceField.getTexturid(1) == 205))
        {
          return 1;
        }
        return 0;
      }

    }

    return 0;
  }

  public int getRenderType()
  {
    return ModularForceFieldSystem.MFFSRENDER_ID;
  }

  public boolean isOpaqueCube()
  {
    return false;
  }

  public boolean renderAsNormalBlock()
  {
    return false;
  }

  protected boolean canSilkHarvest()
  {
    return false;
  }

  public void onNeighborBlockChange(World world, int x, int y, int z, int blockid)
  {
    if (blockid != ModularForceFieldSystem.MFFSFieldblock.blockID)
    {
      for (int x1 = -1; x1 <= 1; x1++)
        for (int y1 = -1; y1 <= 1; y1++)
          for (int z1 = -1; z1 <= 1; z1++)
            if (world.getBlockId(x + x1, y + y1, z + z1) != ModularForceFieldSystem.MFFSFieldblock.blockID)
            {
              if (world.getBlockId(x + x1, y + y1, z + z1) == 0)
              {
                breakBlock(world, x + x1, y + y1, z + z1, 0, 0);
              }
            }
    }
  }

  public void breakBlock(World world, int i, int j, int k, int a, int b)
  {
    ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(Integer.valueOf(new PointXYZ(i, j, k, world).hashCode()));

    if ((ffworldmap != null) && 
      (!ffworldmap.isEmpty())) {
      TileEntityProjector Projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
      if (Projector != null)
        if (!Projector.isActive()) {
          ffworldmap.removebyProjector(ffworldmap.getProjectorID());
        } else {
          world.setBlockAndMetadataWithNotify(i, j, k, ModularForceFieldSystem.MFFSFieldblock.blockID, ffworldmap.getTyp());
          world.markBlockForUpdate(i, j, k);
          ffworldmap.setSync(true);

          if (ffworldmap.getTyp() == 1)
            Projector.consumePower(ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier, false);
          else
            Projector.consumePower(ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier * ModularForceFieldSystem.forcefieldblockzappermodifier, false);
        }
    }
  }

  public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
  {
    if (par1World.isRemote) {
      return;
    }
    ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(par1World).getForceFieldStackMap(Integer.valueOf(new PointXYZ(par2, par3, par4, par1World).hashCode()));

    if ((ffworldmap != null) && (!ModularForceFieldSystem.adventuremap.booleanValue()))
    {
      TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(par1World).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
      if (projector != null) {
        switch (projector.getaccesstyp())
        {
        case 0:
          par5EntityPlayer.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
          Functions.ChattoPlayer(par5EntityPlayer, "[Force Field] Attention High Energy Field");
          break;
        case 2:
        case 3:
          if (!SecurityHelper.isAccessGranted(projector, par5EntityPlayer, par1World, SecurityRight.SR))
          {
            par5EntityPlayer.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
            Functions.ChattoPlayer(par5EntityPlayer, "[Force Field] Attention High Energy Field");
          }
          break;
        case 1:
        }
      }
      if (!SecurityHelper.isAccessGranted(projector, par5EntityPlayer, par1World, SecurityRight.SR))
      {
        par5EntityPlayer.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
        Functions.ChattoPlayer(par5EntityPlayer, "[Force Field] Attention High Energy Field");
      }
    }

    Random random = null;
    updateTick(par1World, par2, par3, par4, random);
  }

  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
  {
    if (world.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal()) {
      float f = 0.0625F;
      return AxisAlignedBB.getBoundingBox(i + f, j + f, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
    }

    return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
  }

  public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
  {
    return AxisAlignedBB.getBoundingBox(i, j, k, i + 0, j + 0, k + 0);
  }

  public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
  {
    if (world.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal()) {
      if ((entity instanceof EntityLiving)) {
        entity.attackEntityFrom(MFFSDamageSource.fieldShock, 10);
      }
    }
    else if ((entity instanceof EntityPlayer)) {
      ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getorcreateFFStackMap(i, j, k, world);

      if (ffworldmap != null)
      {
        TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));

        if (projector != null)
        {
          boolean passtrue = false;

          switch (projector.getaccesstyp())
          {
          case 0:
            passtrue = false;
            if (ModularForceFieldSystem.Admin.equals(((EntityPlayer)entity).username))
              passtrue = true; break;
          case 1:
            passtrue = true;
            break;
          case 2:
            TileEntityCapacitor generator = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(ffworldmap.getGenratorID()));
            passtrue = SecurityHelper.isAccessGranted(generator, (EntityPlayer)entity, world, SecurityRight.FFB);
            break;
          case 3:
            passtrue = SecurityHelper.isAccessGranted(projector, (EntityPlayer)entity, world, SecurityRight.FFB);
          }

          if (!passtrue)
          {
            ((EntityPlayer)entity).attackEntityFrom(MFFSDamageSource.fieldShock, 20);
          }
          else {
            ((EntityPlayer)entity).attackEntityFrom(MFFSDamageSource.fieldShock, 1);
          }
          Functions.ChattoPlayer((EntityPlayer)entity, "[Force Field] Attention High Energy Field");
        }
      }
    }
  }

  public int quantityDropped(Random random)
  {
    return 0;
  }

  public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side)
  {
    int xCord = x;
    int yCord = y;
    int zCord = z;

    switch (side) { case 0:
      yCord++;
      break;
    case 1:
      yCord--;
      break;
    case 2:
      zCord++;
      break;
    case 3:
      zCord--;
      break;
    case 4:
      xCord++;
      break;
    case 5:
      xCord--;
    }

    if ((this.blockID == iblockaccess.getBlockId(x, y, z)) && (iblockaccess.getBlockMetadata(x, y, z) == iblockaccess.getBlockMetadata(xCord, yCord, zCord))) {
      return false;
    }
    return super.shouldSideBeRendered(iblockaccess, x, y, z, side);
  }

  public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
  {
    TileEntity tileEntity = iblockaccess.getBlockTileEntity(i, j, k);

    if ((tileEntity != null) && ((tileEntity instanceof TileEntityForceField)))
    {
      if ((l < 0) || (l > 5)) return 0;

      return ((TileEntityForceField)tileEntity).getTexturid(l);
    }
    if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Camouflage.ordinal())
    {
      return 180;
    }

    if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Default.ordinal()) return 0;
    if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal()) return 1;
    if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Area.ordinal()) return 2;
    if (iblockaccess.getBlockMetadata(i, j, k) == ForceFieldTyps.Containment.ordinal()) return 3;

    return 5;
  }

  public float getExplosionResistance(Entity entity, World world, int i, int j, int k, double d, double d1, double d2)
  {
    ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(Integer.valueOf(new PointXYZ(i, j, k, world).hashCode()));

    if ((ffworldmap != null) && (!ffworldmap.isEmpty()))
    {
      TileEntity tileEntity = (TileEntity)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));

      if (((tileEntity instanceof TileEntityProjector)) && (tileEntity != null)) {
        ((TileEntityProjector)tileEntity).consumePower(ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier, false);
      }

    }

    return 999.0F;
  }

  public void randomDisplayTick(World world, int i, int j, int k, Random random)
  {
    if ((ModularForceFieldSystem.showZapperParticles) && (world.getBlockMetadata(i, j, k) == ForceFieldTyps.Zapper.ordinal())) {
      double d = i + Math.random() + 0.2D;
      double d1 = j + Math.random() + 0.2D;
      double d2 = k + Math.random() + 0.2D;

      world.spawnParticle("townaura", d, d1, d2, 0.0D, 0.0D, 0.0D);
    }
  }

  public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir)
  {
    return false;
  }

  public void updateTick(World world, int x, int y, int z, Random random)
  {
    ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(world).getForceFieldStackMap(Integer.valueOf(new PointXYZ(x, y, z, world).hashCode()));

    if (ffworldmap != null)
    {
      if (!ffworldmap.isEmpty()) {
        TileEntityProjector Projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
        if ((Projector != null) && 
          (!Projector.isActive())) {
          ffworldmap.removebyProjector(ffworldmap.getProjectorID());
        }
      }

    }

    if ((ffworldmap == null) || (ffworldmap.isEmpty()))
    {
      world.removeBlockTileEntity(x, y, z);
      world.setBlockWithNotify(x, y, z, 0);
    }
  }

  public TileEntity createTileEntity(World world, int meta)
  {
    if (meta == ForceFieldTyps.Camouflage.ordinal())
    {
      return new TileEntityForceField();
    }

    return null;
  }

  public TileEntity createNewTileEntity(World world)
  {
    return createTileEntity(world, 0);
  }

  public void weakenForceField(World world, int x, int y, int z)
  {
    if (ModularForceFieldSystem.influencedbyothermods.booleanValue())
    {
      world.setBlockWithNotify(x, y, z, 0);
    }
  }
}