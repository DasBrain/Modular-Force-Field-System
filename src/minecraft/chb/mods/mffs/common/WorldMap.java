/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import com.google.common.collect.MapMaker;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public final class WorldMap
/*     */ {
/*  35 */   private static Map ForceFieldWorlds = new MapMaker().weakKeys().makeMap();
/*     */ 
/*     */   public static ForceFieldWorld getForceFieldWorld(World world)
/*     */   {
/*  99 */     if (world != null) {
/* 100 */       if (!ForceFieldWorlds.containsKey(world)) {
/* 101 */         ForceFieldWorlds.put(world, new ForceFieldWorld());
/*     */       }
/* 103 */       return (ForceFieldWorld)ForceFieldWorlds.get(world);
/*     */     }
/*     */ 
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */   public static class ForceFieldWorld
/*     */   {
/*  38 */     private static Map ForceFieldStackMap = new Hashtable();
/*     */ 
/*     */     public ForceFieldBlockStack getorcreateFFStackMap(int x, int y, int z, World world)
/*     */     {
/*  42 */       PointXYZ png = new PointXYZ(x, y, z, world);
/*  43 */       if (ForceFieldStackMap.get(Integer.valueOf(png.hashCode())) == null) {
/*  44 */         ForceFieldStackMap.put(Integer.valueOf(png.hashCode()), new ForceFieldBlockStack(png));
/*     */       }
/*  46 */       return (ForceFieldBlockStack)ForceFieldStackMap.get(Integer.valueOf(png.hashCode()));
/*     */     }
/*     */ 
/*     */     public ForceFieldBlockStack getForceFieldStackMap(Integer hasher) {
/*  50 */       return (ForceFieldBlockStack)ForceFieldStackMap.get(hasher);
/*     */     }
/*     */ 
/*     */     public ForceFieldBlockStack getForceFieldStackMap(PointXYZ png)
/*     */     {
/*  55 */       return (ForceFieldBlockStack)ForceFieldStackMap.get(Integer.valueOf(png.hashCode()));
/*     */     }
/*     */ 
/*     */     public int isExistForceFieldStackMap(int x, int y, int z, int counter, int typ, World world)
/*     */     {
/*  61 */       switch (typ)
/*     */       {
/*     */       case 0:
/*  64 */         y += counter;
/*  65 */         break;
/*     */       case 1:
/*  67 */         y -= counter;
/*  68 */         break;
/*     */       case 2:
/*  70 */         z += counter;
/*  71 */         break;
/*     */       case 3:
/*  73 */         z -= counter;
/*  74 */         break;
/*     */       case 4:
/*  76 */         x += counter;
/*  77 */         break;
/*     */       case 5:
/*  79 */         x -= counter;
/*     */       }
/*     */ 
/*  83 */       ForceFieldBlockStack Map = (ForceFieldBlockStack)ForceFieldStackMap.get(Integer.valueOf(new PointXYZ(x, y, z, world).hashCode()));
/*     */ 
/*  85 */       if (Map == null) {
/*  86 */         return 0;
/*     */       }
/*  88 */       if (Map.isEmpty())
/*     */       {
/*  90 */         return 0;
/*     */       }
/*     */ 
/*  93 */       return Map.getGenratorID();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.WorldMap
 * JD-Core Version:    0.6.2
 */