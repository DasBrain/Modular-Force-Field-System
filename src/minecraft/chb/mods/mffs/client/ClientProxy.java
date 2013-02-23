package chb.mods.mffs.client;

import chb.mods.mffs.client.renderer.MFFSBlockRenderer;
import chb.mods.mffs.client.renderer.TECapacitorRenderer;
import chb.mods.mffs.client.renderer.TEExtractorRenderer;
import chb.mods.mffs.common.CommonProxy;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
  public void registerRenderInformation()
  {
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/items.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/blocks.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/forciciumore.png");

    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/DefenceStation.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/projector.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Capacitor.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Extractor.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Converter.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/SecStorage.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/AdvSecurtyStation.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/ControlSystem.png");

    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/DefenceStation_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/projector_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Capacitor_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Extractor_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Converter_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/SecStorage_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/AdvSecurtyStation_ue.png");
    MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/ControlSystem_ue.png");
  }

  public void registerTileEntitySpecialRenderer()
  {
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCapacitor.class, new TECapacitorRenderer());
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtractor.class, new TEExtractorRenderer());

    RenderingRegistry.registerBlockHandler(new MFFSBlockRenderer());
  }

  public World getClientWorld()
  {
    return FMLClientHandler.instance().getClient().theWorld;
  }

  public boolean isClient()
  {
    return true;
  }
}