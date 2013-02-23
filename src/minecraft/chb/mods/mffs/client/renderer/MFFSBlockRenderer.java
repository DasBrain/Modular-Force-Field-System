/*    */ package chb.mods.mffs.client.renderer;
/*    */ 
/*    */ import chb.mods.mffs.common.ForceFieldTyps;
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityForceField;
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.client.ForgeHooksClient;
/*    */ 
/*    */ public class MFFSBlockRenderer
/*    */   implements ISimpleBlockRenderingHandler
/*    */ {
/*    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
/*    */   {
/* 38 */     if (block == ModularForceFieldSystem.MFFSFieldblock) {
/* 39 */       if (world.getBlockMetadata(x, y, z) == ForceFieldTyps.Camouflage.ordinal())
/*    */       {
/* 41 */         TileEntity te = world.getBlockTileEntity(x, y, z);
/* 42 */         if ((te instanceof TileEntityForceField))
/*    */         {
/* 44 */           if (((TileEntityForceField)te).getForcefieldCamoblockid() != -1)
/*    */           {
/* 46 */             if ((ForceFieldTyps.Camouflage.ordinal() == ((TileEntityForceField)te).getForcefieldCamoblockmeta()) && (((TileEntityForceField)te).getForcefieldCamoblockid() != 327) && (((TileEntityForceField)te).getForcefieldCamoblockid() != 326))
/*    */             {
/* 49 */               Block customblock = Block.blocksList[((TileEntityForceField)te).getForcefieldCamoblockid()];
/* 50 */               if (customblock != null)
/*    */               {
/* 52 */                 ForgeHooksClient.bindTexture(customblock.getTextureFile(), 1);
/* 53 */                 renderer.renderBlockByRenderType(customblock, x, y, z);
/* 54 */                 return true;
/*    */               }
/*    */             }
/*    */           }
/* 58 */           if (((TileEntityForceField)te).getTexturfile() != null)
/*    */           {
/* 60 */             ForgeHooksClient.bindTexture(((TileEntityForceField)te).getTexturfile(), 0);
/* 61 */             renderer.renderStandardBlock(block, x, y, z);
/* 62 */             return true;
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/* 67 */         ForgeHooksClient.bindTexture("/terrain.png", 0);
/* 68 */         renderer.renderStandardBlock(block, x, y, z);
/*    */       }
/*    */       else {
/* 71 */         ForgeHooksClient.bindTexture("/chb/mods/mffs/sprites/blocks.png", 0);
/* 72 */         renderer.renderStandardBlock(block, x, y, z);
/*    */       }
/*    */ 
/* 75 */       return true;
/*    */     }
/*    */ 
/* 78 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean shouldRender3DInInventory()
/*    */   {
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */   public int getRenderId()
/*    */   {
/* 88 */     return ModularForceFieldSystem.MFFSRENDER_ID;
/*    */   }
/*    */ 
/*    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.renderer.MFFSBlockRenderer
 * JD-Core Version:    0.6.2
 */