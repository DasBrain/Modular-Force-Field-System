/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class Versioninfo
/*    */ {
/*    */   public static String curentversion()
/*    */   {
/* 35 */     InputStream inputstream = Versioninfo.class.getClassLoader().getResourceAsStream("versioninfo");
/* 36 */     Properties properties = new Properties();
/*    */ 
/* 38 */     if (inputstream != null) {
/*    */       try {
/* 40 */         properties.load(inputstream);
/* 41 */         String Major = properties.getProperty("mffs.version.major.number");
/* 42 */         String Minor = properties.getProperty("mffs.version.minor.number");
/* 43 */         String Revision = properties.getProperty("mffs.version.revision.number");
/* 44 */         String betabuild = properties.getProperty("mffs.version.betabuild.number");
/*    */ 
/* 46 */         return Major + "." + Minor + "." + Revision + "." + betabuild;
/*    */       }
/*    */       catch (IOException ex) {
/* 49 */         FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, "[Modual ForceField System] cannot read local Version file!", ex);
/*    */       }
/*    */     }
/* 52 */     return "0.0.0.0";
/*    */   }
/*    */ 
/*    */   public static String newestversion()
/*    */   {
/* 57 */     Properties properties = new Properties();
/*    */     try
/*    */     {
/* 60 */       URL versionFile = new URL("https://raw.github.com/Thunderdark/ModularForceFieldSystem/master/src/minecraft/versioninfo");
/* 61 */       InputStreamReader inputstream = new InputStreamReader(versionFile.openStream());
/*    */ 
/* 63 */       if (inputstream != null)
/*    */       {
/* 65 */         properties.load(inputstream);
/*    */ 
/* 67 */         String Major = properties.getProperty("mffs.version.major.number");
/* 68 */         String Minor = properties.getProperty("mffs.version.minor.number");
/* 69 */         String Revision = properties.getProperty("mffs.version.revision.number");
/* 70 */         String betabuild = properties.getProperty("mffs.version.betabuild.number");
/*    */ 
/* 72 */         return Major + "." + Minor + "." + Revision + "." + betabuild;
/*    */       }
/*    */     }
/*    */     catch (Exception ex) {
/* 76 */       FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, "[Modual ForceField System] cannot read remote Version file!", ex);
/*    */     }
/*    */ 
/* 79 */     return "0.0.0.0";
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.Versioninfo
 * JD-Core Version:    0.6.2
 */