package chb.mods.mffs.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import chb.mods.mffs.common.ForceFieldTyps;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityForceField;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MFFSBlockRenderer
  implements ISimpleBlockRenderingHandler
{
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
  {
    if (block == ModularForceFieldSystem.MFFSFieldblock) {
      if (world.getBlockMetadata(x, y, z) == ForceFieldTyps.Camouflage.ordinal())
      {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if ((te instanceof TileEntityForceField))
        {
          if (((TileEntityForceField)te).getForcefieldCamoblockid() != -1)
          {
            if ((ForceFieldTyps.Camouflage.ordinal() == ((TileEntityForceField)te).getForcefieldCamoblockmeta()) && (((TileEntityForceField)te).getForcefieldCamoblockid() != 327) && (((TileEntityForceField)te).getForcefieldCamoblockid() != 326))
            {
              Block customblock = Block.blocksList[((TileEntityForceField)te).getForcefieldCamoblockid()];
              if (customblock != null)
              {
                ForgeHooksClient.bindTexture(customblock.getTextureFile(), 1);
                renderer.renderBlockByRenderType(customblock, x, y, z);
                return true;
              }
            }
          }
          if (((TileEntityForceField)te).getTexturfile() != null)
          {
            ForgeHooksClient.bindTexture(((TileEntityForceField)te).getTexturfile(), 0);
            renderer.renderStandardBlock(block, x, y, z);
            return true;
          }

        }

        ForgeHooksClient.bindTexture("/terrain.png", 0);
        renderer.renderStandardBlock(block, x, y, z);
      }
      else {
        ForgeHooksClient.bindTexture("/chb/mods/mffs/sprites/blocks.png", 0);
        renderer.renderStandardBlock(block, x, y, z);
      }

      return true;
    }

    return true;
  }

  public boolean shouldRender3DInInventory()
  {
    return false;
  }

  public int getRenderId()
  {
    return ModularForceFieldSystem.MFFSRENDER_ID;
  }

  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
  {
  }
}