/*     */ package chb.mods.mffs.network.client;
/*     */ 
/*     */ import chb.mods.mffs.common.CommonProxy;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityForceField;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*     */ import chb.mods.mffs.network.INetworkHandlerEventListener;
/*     */ import chb.mods.mffs.network.INetworkHandlerListener;
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import cpw.mods.fml.common.network.IPacketHandler;
/*     */ import cpw.mods.fml.common.network.PacketDispatcher;
/*     */ import cpw.mods.fml.common.network.Player;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.network.INetworkManager;
/*     */ import net.minecraft.network.packet.Packet;
/*     */ import net.minecraft.network.packet.Packet250CustomPayload;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class NetworkHandlerClient
/*     */   implements IPacketHandler
/*     */ {
/*     */   private static final boolean DEBUG = false;
/*     */ 
/*     */   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
/*     */   {
/*  66 */     ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
/*  67 */     int x = dat.readInt();
/*  68 */     int y = dat.readInt();
/*  69 */     int z = dat.readInt();
/*  70 */     int typ = dat.readInt();
/*  71 */     World world = ModularForceFieldSystem.proxy.getClientWorld();
/*     */ 
/*  73 */     switch (typ)
/*     */     {
/*     */     case 100:
/*  77 */       String DataPacket = dat.readUTF();
/*     */ 
/*  79 */       for (String blockupdate : DataPacket.split(">"))
/*     */       {
/*  81 */         if (blockupdate.length() > 0)
/*     */         {
/*  84 */           String[] projector = blockupdate.split("<");
/*  85 */           String[] Corrdinaten = projector[1].split("/");
/*  86 */           String[] temp = projector[0].split("!");
/*  87 */           String[] Dim = temp[1].split("/");
/*  88 */           String[] ProjectorCorr = temp[0].split("/");
/*     */ 
/*  91 */           if (Integer.parseInt(Dim[0].trim()) == world.provider.dimensionId)
/*     */           {
/*  93 */             if (world.getChunkFromBlockCoords(Integer.parseInt(Corrdinaten[0].trim()), Integer.parseInt(Corrdinaten[2].trim())).isChunkLoaded)
/*     */             {
/*  95 */               TileEntity te = world.getBlockTileEntity(Integer.parseInt(Corrdinaten[0].trim()), Integer.parseInt(Corrdinaten[1].trim()), Integer.parseInt(Corrdinaten[2].trim()));
/*  96 */               if ((te instanceof TileEntityForceField))
/*     */               {
/*  99 */                 TileEntity proj = world.getBlockTileEntity(Integer.parseInt(ProjectorCorr[2].trim()), Integer.parseInt(ProjectorCorr[1].trim()), Integer.parseInt(ProjectorCorr[0].trim()));
/* 100 */                 if ((proj instanceof TileEntityProjector))
/*     */                 {
/* 102 */                   ((TileEntityForceField)te).setTexturfile(((TileEntityProjector)proj).getForceFieldTexturfile());
/* 103 */                   ((TileEntityForceField)te).setTexturid(((TileEntityProjector)proj).getForceFieldTexturID());
/* 104 */                   ((TileEntityForceField)te).setForcefieldCamoblockid(((TileEntityProjector)proj).getForcefieldCamoblockid());
/* 105 */                   ((TileEntityForceField)te).setForcefieldCamoblockmeta(((TileEntityProjector)proj).getForcefieldCamoblockmeta());
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 116 */       break;
/*     */     case 1:
/* 121 */       String fieldname = dat.readUTF();
/*     */ 
/* 124 */       TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
/*     */ 
/* 127 */       if ((tileEntity instanceof TileEntityMachines)) {
/*     */         try
/*     */         {
/* 130 */           Field f = ReflectionHelper.findField(TileEntityMachines.class, new String[] { fieldname });
/* 131 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 139 */       if ((tileEntity instanceof TileEntityCapacitor)) {
/*     */         try
/*     */         {
/* 142 */           Field f = ReflectionHelper.findField(TileEntityCapacitor.class, new String[] { fieldname });
/* 143 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 151 */       if ((tileEntity instanceof TileEntityExtractor)) {
/*     */         try
/*     */         {
/* 154 */           Field f = ReflectionHelper.findField(TileEntityExtractor.class, new String[] { fieldname });
/* 155 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 164 */       if ((tileEntity instanceof TileEntityConverter)) {
/*     */         try
/*     */         {
/* 167 */           Field f = ReflectionHelper.findField(TileEntityConverter.class, new String[] { fieldname });
/* 168 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 178 */       if ((tileEntity instanceof TileEntityProjector)) {
/*     */         try
/*     */         {
/* 181 */           Field f = ReflectionHelper.findField(TileEntityProjector.class, new String[] { fieldname });
/* 182 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 190 */       if ((tileEntity instanceof TileEntityAreaDefenseStation)) {
/*     */         try
/*     */         {
/* 193 */           Field f = ReflectionHelper.findField(TileEntityAreaDefenseStation.class, new String[] { fieldname });
/* 194 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 202 */       if ((tileEntity instanceof TileEntityAdvSecurityStation)) {
/*     */         try
/*     */         {
/* 205 */           Field f = ReflectionHelper.findField(TileEntityAdvSecurityStation.class, new String[] { fieldname });
/* 206 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 214 */       if ((tileEntity instanceof TileEntitySecStorage)) {
/*     */         try
/*     */         {
/* 217 */           Field f = ReflectionHelper.findField(TileEntitySecStorage.class, new String[] { fieldname });
/* 218 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 226 */       if ((tileEntity instanceof TileEntityControlSystem))
/*     */         try
/*     */         {
/* 229 */           Field f = ReflectionHelper.findField(TileEntityControlSystem.class, new String[] { fieldname });
/* 230 */           reflectionsetvalue(f, tileEntity, dat, fieldname);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void reflectionsetvalue(Field f, TileEntity tileEntity, ByteArrayDataInput dat, String fieldname)
/*     */   {
/*     */     try
/*     */     {
/* 251 */       if (f.getType().equals(Integer.TYPE)) f.setInt(tileEntity, Integer.parseInt(dat.readUTF()));
/* 252 */       if (f.getType().equals(Boolean.TYPE)) f.setBoolean(tileEntity, Boolean.parseBoolean(dat.readUTF()));
/* 253 */       if (f.getType().equals(Short.TYPE)) f.setShort(tileEntity, Short.parseShort(dat.readUTF()));
/* 254 */       if (f.getType().equals(Float.TYPE)) f.setFloat(tileEntity, Float.parseFloat(dat.readUTF()));
/* 255 */       if (f.getType().equals(String.class)) f.set(tileEntity, dat.readUTF());
/*     */ 
/* 258 */       if ((tileEntity instanceof INetworkHandlerListener))
/*     */       {
/* 260 */         ((INetworkHandlerListener)tileEntity).onNetworkHandlerUpdate(fieldname);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Packet requestInitialData(TileEntity tileEntity)
/*     */   {
/* 272 */     return requestInitialData(tileEntity, false);
/*     */   }
/*     */ 
/*     */   public static void requestForceFieldInitialData(int Dimension, String corridnaten)
/*     */   {
/*     */     try
/*     */     {
/* 281 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(63000);
/* 282 */       DataOutputStream dos = new DataOutputStream(bos);
/*     */ 
/* 284 */       dos.writeInt(0);
/* 285 */       dos.writeInt(0);
/* 286 */       dos.writeInt(0);
/* 287 */       dos.writeInt(10);
/* 288 */       dos.writeInt(Dimension);
/* 289 */       dos.writeUTF(corridnaten);
/*     */ 
/* 291 */       Packet250CustomPayload pkt = new Packet250CustomPayload();
/* 292 */       pkt.channel = "MFFS";
/* 293 */       pkt.data = bos.toByteArray();
/* 294 */       pkt.length = bos.size();
/* 295 */       pkt.isChunkDataPacket = false;
/*     */ 
/* 297 */       PacketDispatcher.sendPacketToServer(pkt);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 303 */       System.out.println(e.getLocalizedMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Packet requestInitialData(TileEntity tileEntity, boolean senddirekt)
/*     */   {
/* 318 */     ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
/* 319 */     DataOutputStream dos = new DataOutputStream(bos);
/* 320 */     int x = tileEntity.xCoord;
/* 321 */     int y = tileEntity.yCoord;
/* 322 */     int z = tileEntity.zCoord;
/* 323 */     int typ = 2;
/*     */ 
/* 325 */     int Dimension = tileEntity.worldObj.provider.dimensionId;
/*     */ 
/* 327 */     StringBuilder str = new StringBuilder();
/*     */ 
/* 329 */     for (String fields : ((INetworkHandlerListener)tileEntity).getFieldsforUpdate())
/*     */     {
/* 331 */       str.append(fields);
/* 332 */       str.append("/");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 337 */       dos.writeInt(x);
/* 338 */       dos.writeInt(y);
/* 339 */       dos.writeInt(z);
/* 340 */       dos.writeInt(typ);
/* 341 */       dos.writeInt(Dimension);
/* 342 */       dos.writeUTF(str.toString());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 350 */     Packet250CustomPayload pkt = new Packet250CustomPayload();
/* 351 */     pkt.channel = "MFFS";
/* 352 */     pkt.data = bos.toByteArray();
/* 353 */     pkt.length = bos.size();
/* 354 */     pkt.isChunkDataPacket = false;
/*     */ 
/* 356 */     if (senddirekt) {
/* 357 */       PacketDispatcher.sendPacketToServer(pkt);
/*     */     }
/* 359 */     return pkt;
/*     */   }
/*     */ 
/*     */   public static void fireTileEntityEvent(TileEntity tileEntity, int key, String vaule)
/*     */   {
/* 367 */     if ((tileEntity instanceof INetworkHandlerEventListener))
/*     */     {
/* 371 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
/* 372 */       DataOutputStream dos = new DataOutputStream(bos);
/* 373 */       int x = tileEntity.xCoord;
/* 374 */       int y = tileEntity.yCoord;
/* 375 */       int z = tileEntity.zCoord;
/* 376 */       int typ = 3;
/*     */ 
/* 380 */       int Dimension = tileEntity.worldObj.provider.dimensionId;
/*     */       try
/*     */       {
/* 383 */         dos.writeInt(x);
/* 384 */         dos.writeInt(y);
/* 385 */         dos.writeInt(z);
/* 386 */         dos.writeInt(typ);
/* 387 */         dos.writeInt(Dimension);
/* 388 */         dos.writeInt(key);
/* 389 */         dos.writeUTF(vaule);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */ 
/* 396 */       Packet250CustomPayload pkt = new Packet250CustomPayload();
/* 397 */       pkt.channel = "MFFS";
/* 398 */       pkt.data = bos.toByteArray();
/* 399 */       pkt.length = bos.size();
/* 400 */       pkt.isChunkDataPacket = true;
/*     */ 
/* 402 */       PacketDispatcher.sendPacketToServer(pkt);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.network.client.NetworkHandlerClient
 * JD-Core Version:    0.6.2
 */