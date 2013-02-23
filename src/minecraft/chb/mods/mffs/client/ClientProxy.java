/*    */ package chb.mods.mffs.client;
/*    */ 
/*    */ import chb.mods.mffs.client.renderer.MFFSBlockRenderer;
/*    */ import chb.mods.mffs.client.renderer.TECapacitorRenderer;
/*    */ import chb.mods.mffs.client.renderer.TEExtractorRenderer;
/*    */ import chb.mods.mffs.common.CommonProxy;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*    */ import cpw.mods.fml.client.FMLClientHandler;
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.client.MinecraftForgeClient;
/*    */ 
/*    */ public class ClientProxy extends CommonProxy
/*    */ {
/*    */   public void registerRenderInformation()
/*    */   {
/* 40 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/items.png");
/* 41 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/blocks.png");
/* 42 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/forciciumore.png");
/*    */ 
/* 44 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/DefenceStation.png");
/* 45 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/projector.png");
/* 46 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Capacitor.png");
/* 47 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Extractor.png");
/* 48 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Converter.png");
/* 49 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/SecStorage.png");
/* 50 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/AdvSecurtyStation.png");
/* 51 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/ControlSystem.png");
/*    */ 
/* 53 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/DefenceStation_ue.png");
/* 54 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/projector_ue.png");
/* 55 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Capacitor_ue.png");
/* 56 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Extractor_ue.png");
/* 57 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/Converter_ue.png");
/* 58 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/SecStorage_ue.png");
/* 59 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/AdvSecurtyStation_ue.png");
/* 60 */     MinecraftForgeClient.preloadTexture("/chb/mods/mffs/sprites/ControlSystem_ue.png");
/*    */   }
/*    */ 
/*    */   public void registerTileEntitySpecialRenderer()
/*    */   {
/* 67 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCapacitor.class, new TECapacitorRenderer());
/* 68 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtractor.class, new TEExtractorRenderer());
/*    */ 
/* 70 */     RenderingRegistry.registerBlockHandler(new MFFSBlockRenderer());
/*    */   }
/*    */ 
/*    */   public World getClientWorld()
/*    */   {
/* 75 */     return FMLClientHandler.instance().getClient().theWorld;
/*    */   }
/*    */ 
/*    */   public boolean isClient()
/*    */   {
/* 81 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.client.ClientProxy
 * JD-Core Version:    0.6.2
 */