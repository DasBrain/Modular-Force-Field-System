/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*     */ import com.google.common.collect.MapMaker;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public final class Linkgrid
/*     */ {
/*  44 */   private static Map WorldpowernetMap = new MapMaker().weakKeys().makeMap();
/*     */ 
/*     */   public static Worldlinknet getWorldMap(World world)
/*     */   {
/* 262 */     if (world != null) {
/* 263 */       if (!WorldpowernetMap.containsKey(world)) {
/* 264 */         WorldpowernetMap.put(world, new Worldlinknet());
/*     */       }
/* 266 */       return (Worldlinknet)WorldpowernetMap.get(world);
/*     */     }
/*     */ 
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */   public static class Worldlinknet
/*     */   {
/*  49 */     private Map Projektor = new Hashtable();
/*  50 */     private Map Capacitors = new Hashtable();
/*  51 */     private Map SecStation = new Hashtable();
/*  52 */     private Map DefStation = new Hashtable();
/*  53 */     private Map Extractor = new Hashtable();
/*  54 */     private Map Converter = new Hashtable();
/*  55 */     private Map Jammer = new Hashtable();
/*  56 */     private Map FieldFusion = new Hashtable();
/*  57 */     private Map SecStorage = new Hashtable();
/*  58 */     private Map ControlSystem = new Hashtable();
/*     */ 
/*     */     public Map getSecStorage() {
/*  61 */       return this.SecStorage;
/*     */     }
/*     */ 
/*     */     public Map getControlSystem() {
/*  65 */       return this.ControlSystem;
/*     */     }
/*     */ 
/*     */     public Map getConverter() {
/*  69 */       return this.Converter;
/*     */     }
/*     */ 
/*     */     public Map getExtractor() {
/*  73 */       return this.Extractor;
/*     */     }
/*     */ 
/*     */     public Map getProjektor() {
/*  77 */       return this.Projektor;
/*     */     }
/*     */ 
/*     */     public Map getCapacitor() {
/*  81 */       return this.Capacitors;
/*     */     }
/*     */ 
/*     */     public Map getSecStation() {
/*  85 */       return this.SecStation;
/*     */     }
/*     */ 
/*     */     public Map getDefStation() {
/*  89 */       return this.DefStation;
/*     */     }
/*     */ 
/*     */     public Map getJammer() {
/*  93 */       return this.Jammer;
/*     */     }
/*     */ 
/*     */     public Map getFieldFusion() {
/*  97 */       return this.FieldFusion;
/*     */     }
/*     */ 
/*     */     public int refreshID(TileEntityMachines tileEntity, int remDeviceID)
/*     */     {
/* 103 */       Random random = new Random();
/* 104 */       int DeviceID = random.nextInt();
/* 105 */       if ((tileEntity instanceof TileEntitySecStorage)) {
/* 106 */         if (remDeviceID == 0) {
/* 107 */           while (this.SecStorage.get(Integer.valueOf(DeviceID)) != null)
/* 108 */             DeviceID = random.nextInt();
/*     */         }
/* 110 */         DeviceID = remDeviceID;
/* 111 */         this.SecStorage.put(Integer.valueOf(DeviceID), (TileEntitySecStorage)tileEntity);
/* 112 */         return DeviceID;
/*     */       }
/* 114 */       if ((tileEntity instanceof TileEntityControlSystem)) {
/* 115 */         if (remDeviceID == 0) {
/* 116 */           while (this.ControlSystem.get(Integer.valueOf(DeviceID)) != null)
/* 117 */             DeviceID = random.nextInt();
/*     */         }
/* 119 */         DeviceID = remDeviceID;
/* 120 */         this.ControlSystem.put(Integer.valueOf(DeviceID), (TileEntityControlSystem)tileEntity);
/* 121 */         return DeviceID;
/*     */       }
/* 123 */       if ((tileEntity instanceof TileEntityAdvSecurityStation)) {
/* 124 */         if (remDeviceID == 0) {
/* 125 */           while (this.SecStation.get(Integer.valueOf(DeviceID)) != null)
/* 126 */             DeviceID = random.nextInt();
/*     */         }
/* 128 */         DeviceID = remDeviceID;
/* 129 */         this.SecStation.put(Integer.valueOf(DeviceID), (TileEntityAdvSecurityStation)tileEntity);
/* 130 */         return DeviceID;
/*     */       }
/* 132 */       if ((tileEntity instanceof TileEntityAreaDefenseStation)) {
/* 133 */         if (remDeviceID == 0) {
/* 134 */           while (this.DefStation.get(Integer.valueOf(DeviceID)) != null)
/* 135 */             DeviceID = random.nextInt();
/*     */         }
/* 137 */         DeviceID = remDeviceID;
/* 138 */         this.DefStation.put(Integer.valueOf(DeviceID), (TileEntityAreaDefenseStation)tileEntity);
/* 139 */         return DeviceID;
/*     */       }
/* 141 */       if ((tileEntity instanceof TileEntityCapacitor)) {
/* 142 */         if (remDeviceID == 0) {
/* 143 */           while (this.Capacitors.get(Integer.valueOf(DeviceID)) != null)
/* 144 */             DeviceID = random.nextInt();
/*     */         }
/* 146 */         DeviceID = remDeviceID;
/* 147 */         this.Capacitors.put(Integer.valueOf(DeviceID), (TileEntityCapacitor)tileEntity);
/* 148 */         return DeviceID;
/*     */       }
/* 150 */       if ((tileEntity instanceof TileEntityConverter)) {
/* 151 */         if (remDeviceID == 0) {
/* 152 */           while (this.Converter.get(Integer.valueOf(DeviceID)) != null)
/* 153 */             DeviceID = random.nextInt();
/*     */         }
/* 155 */         DeviceID = remDeviceID;
/* 156 */         this.Converter.put(Integer.valueOf(DeviceID), (TileEntityConverter)tileEntity);
/* 157 */         return DeviceID;
/*     */       }
/* 159 */       if ((tileEntity instanceof TileEntityExtractor)) {
/* 160 */         if (remDeviceID == 0) {
/* 161 */           while (this.Extractor.get(Integer.valueOf(DeviceID)) != null)
/* 162 */             DeviceID = random.nextInt();
/*     */         }
/* 164 */         DeviceID = remDeviceID;
/* 165 */         this.Extractor.put(Integer.valueOf(DeviceID), (TileEntityExtractor)tileEntity);
/* 166 */         return DeviceID;
/*     */       }
/* 168 */       if ((tileEntity instanceof TileEntityProjector)) {
/* 169 */         if (remDeviceID == 0) {
/* 170 */           while (this.Projektor.get(Integer.valueOf(DeviceID)) != null)
/* 171 */             DeviceID = random.nextInt();
/*     */         }
/* 173 */         DeviceID = remDeviceID;
/* 174 */         this.Projektor.put(Integer.valueOf(DeviceID), (TileEntityProjector)tileEntity);
/* 175 */         return DeviceID;
/*     */       }
/* 177 */       return 0;
/*     */     }
/*     */ 
/*     */     public int connectedtoCapacitor(TileEntityCapacitor Cap, int range)
/*     */     {
/* 182 */       int counter = 0;
/* 183 */       for (TileEntityProjector tileentity : this.Projektor.values()) {
/* 184 */         if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
/* 185 */           (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
/* 186 */           counter++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 191 */       for (TileEntityCapacitor tileentity : this.Capacitors.values()) {
/* 192 */         if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
/* 193 */           (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
/* 194 */           counter++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 200 */       for (TileEntityAreaDefenseStation tileentity : this.DefStation.values()) {
/* 201 */         if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
/* 202 */           (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
/* 203 */           counter++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 208 */       for (TileEntityExtractor tileentity : this.Extractor.values()) {
/* 209 */         if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
/* 210 */           (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
/* 211 */           counter++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 216 */       for (TileEntityConverter tileentity : this.Converter.values()) {
/* 217 */         if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
/* 218 */           (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
/* 219 */           counter++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 224 */       return counter;
/*     */     }
/*     */ 
/*     */     public TileEntityMachines getTileEntityMachines(String displayname, int key)
/*     */     {
/* 231 */       MFFSMaschines tem = MFFSMaschines.fromdisplayName(displayname);
/*     */ 
/* 233 */       if (tem != null)
/*     */       {
/* 235 */         switch (tem.index)
/*     */         {
/*     */         case 1:
/* 238 */           return (TileEntityMachines)getProjektor().get(Integer.valueOf(key));
/*     */         case 2:
/* 240 */           return (TileEntityMachines)getExtractor().get(Integer.valueOf(key));
/*     */         case 3:
/* 242 */           return (TileEntityMachines)getCapacitor().get(Integer.valueOf(key));
/*     */         case 4:
/* 244 */           return (TileEntityMachines)getConverter().get(Integer.valueOf(key));
/*     */         case 5:
/* 246 */           return (TileEntityMachines)getDefStation().get(Integer.valueOf(key));
/*     */         case 6:
/* 248 */           return (TileEntityMachines)getSecStation().get(Integer.valueOf(key));
/*     */         case 7:
/* 250 */           return (TileEntityMachines)getSecStorage().get(Integer.valueOf(key));
/*     */         case 8:
/* 252 */           return (TileEntityMachines)getControlSystem().get(Integer.valueOf(key));
/*     */         }
/*     */       }
/* 255 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.Linkgrid
 * JD-Core Version:    0.6.2
 */