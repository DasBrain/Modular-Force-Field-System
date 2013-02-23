/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.client.gui.GuiManuelScreen;
/*     */ import chb.mods.mffs.common.container.ContainerDummy;
/*     */ import cpw.mods.fml.common.network.IGuiHandler;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommonProxy
/*     */   implements IGuiHandler
/*     */ {
/*     */   public void registerRenderInformation()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void registerTileEntitySpecialRenderer()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
/*     */   {
/*  46 */     if (ID != 0)
/*     */     {
/*  48 */       return new GuiManuelScreen(new ContainerDummy());
/*     */     }
/*     */ 
/*  51 */     TileEntity te = world.getBlockTileEntity(x, y, z);
/*  52 */     if (te == null)
/*     */     {
/*  54 */       return null;
/*     */     }
/*     */ 
/*  57 */     MFFSMaschines machType = MFFSMaschines.fromTE(te);
/*     */     try
/*     */     {
/*  60 */       Constructor mkGui = Class.forName("chb.mods.mffs.client.gui." + machType.Gui).getConstructor(new Class[] { EntityPlayer.class, machType.clazz });
/*  61 */       return mkGui.newInstance(new Object[] { player, machType.clazz.cast(te) });
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  66 */       System.out.println(ex.getLocalizedMessage());
/*     */     }
/*     */ 
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
/*     */   {
/*  75 */     TileEntity te = world.getBlockTileEntity(x, y, z);
/*  76 */     if (te == null)
/*     */     {
/*  78 */       return null;
/*     */     }
/*     */ 
/*  81 */     MFFSMaschines machType = MFFSMaschines.fromTE(te);
/*     */     try
/*     */     {
/*  85 */       Constructor mkGui = machType.Container.getConstructor(new Class[] { EntityPlayer.class, machType.clazz });
/*  86 */       return mkGui.newInstance(new Object[] { player, machType.clazz.cast(te) });
/*     */     }
/*     */     catch (Exception ex) {
/*  89 */       System.out.println(ex.getLocalizedMessage());
/*     */     }
/*     */ 
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   public World getClientWorld()
/*     */   {
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isClient()
/*     */   {
/* 103 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.CommonProxy
 * JD-Core Version:    0.6.2
 */