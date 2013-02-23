/*     */ package chb.mods.mffs.network.server;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.ForceFieldBlockStack;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.WorldMap;
/*     */ import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
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
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import cpw.mods.fml.common.network.IPacketHandler;
/*     */ import cpw.mods.fml.common.network.PacketDispatcher;
/*     */ import cpw.mods.fml.common.network.Player;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetworkManager;
/*     */ import net.minecraft.network.packet.Packet103SetSlot;
/*     */ import net.minecraft.network.packet.Packet250CustomPayload;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ public class NetworkHandlerServer
/*     */   implements IPacketHandler
/*     */ {
/*     */   private static final boolean DEBUG = false;
/*     */ 
/*     */   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
/*     */   {
/*  71 */     ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
/*  72 */     int x = dat.readInt();
/*  73 */     int y = dat.readInt();
/*  74 */     int z = dat.readInt();
/*  75 */     int typ = dat.readInt();
/*     */ 
/*  78 */     switch (typ)
/*     */     {
/*     */     case 2:
/*  82 */       int dimension = dat.readInt();
/*  83 */       String daten = dat.readUTF();
/*  84 */       World serverworld = DimensionManager.getWorld(dimension);
/*  85 */       if (serverworld != null)
/*     */       {
/*  87 */         TileEntity servertileEntity = serverworld.getBlockTileEntity(x, y, z);
/*     */ 
/*  90 */         if (servertileEntity != null)
/*     */         {
/*  92 */           for (String varname : daten.split("/"))
/*     */           {
/*  94 */             updateTileEntityField(servertileEntity, varname);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 100 */       break;
/*     */     case 3:
/* 109 */       int dimension2 = dat.readInt();
/* 110 */       int key = dat.readInt();
/* 111 */       String value = dat.readUTF();
/*     */ 
/* 113 */       World serverworld2 = DimensionManager.getWorld(dimension2);
/* 114 */       TileEntity servertileEntity2 = serverworld2.getBlockTileEntity(x, y, z);
/*     */ 
/* 116 */       if ((servertileEntity2 instanceof INetworkHandlerEventListener))
/*     */       {
/* 118 */         ((INetworkHandlerEventListener)servertileEntity2).onNetworkHandlerEvent(key, value); } break;
/*     */     case 10:
/* 127 */       int Dim = dat.readInt();
/* 128 */       String Corrdinsaten = dat.readUTF();
/*     */ 
/* 130 */       World worldserver = DimensionManager.getWorld(Dim);
/*     */ 
/* 132 */       if (worldserver != null)
/*     */       {
/* 135 */         for (String varname : Corrdinsaten.split("#"))
/*     */         {
/* 138 */           if (!varname.isEmpty())
/*     */           {
/* 140 */             String[] corr = varname.split("/");
/* 141 */             TileEntity servertileEntity = worldserver.getBlockTileEntity(Integer.parseInt(corr[2].trim()), Integer.parseInt(corr[1].trim()), Integer.parseInt(corr[0].trim()));
/* 142 */             if ((servertileEntity instanceof TileEntityForceField))
/*     */             {
/* 144 */               ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(worldserver).getForceFieldStackMap(Integer.valueOf(new PointXYZ(servertileEntity.xCoord, servertileEntity.yCoord, servertileEntity.zCoord, worldserver).hashCode()));
/*     */ 
/* 146 */               if (ffworldmap != null)
/*     */               {
/* 148 */                 if (!ffworldmap.isEmpty())
/*     */                 {
/* 151 */                   TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(worldserver).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/*     */ 
/* 153 */                   if (projector != null)
/*     */                   {
/* 155 */                     ForceFieldServerUpdatehandler.getWorldMap(worldserver).addto(servertileEntity.xCoord, servertileEntity.yCoord, servertileEntity.zCoord, Dim, projector.xCoord, projector.yCoord, projector.zCoord);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void syncClientPlayerinventorySlot(EntityPlayer player, Slot slot, ItemStack itemstack)
/*     */   {
/* 176 */     Packet103SetSlot pkt = new Packet103SetSlot();
/* 177 */     pkt.windowId = 0;
/* 178 */     pkt.itemSlot = slot.slotNumber;
/* 179 */     pkt.myItemStack = itemstack;
/* 180 */     PacketDispatcher.sendPacketToPlayer(pkt, (Player)player);
/*     */   }
/*     */ 
/*     */   public static void updateTileEntityField(TileEntity tileEntity, String varname)
/*     */   {
/* 191 */     if (tileEntity != null)
/*     */     {
/* 193 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
/* 194 */       DataOutputStream dos = new DataOutputStream(bos);
/* 195 */       int x = tileEntity.xCoord;
/* 196 */       int y = tileEntity.yCoord;
/* 197 */       int z = tileEntity.zCoord;
/* 198 */       int typ = 1;
/*     */       try
/*     */       {
/* 201 */         dos.writeInt(x);
/* 202 */         dos.writeInt(y);
/* 203 */         dos.writeInt(z);
/* 204 */         dos.writeInt(typ);
/* 205 */         dos.writeUTF(varname);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */ 
/* 212 */       if ((tileEntity instanceof TileEntityMachines)) {
/*     */         try
/*     */         {
/* 215 */           Field f = ReflectionHelper.findField(TileEntityMachines.class, new String[] { varname });
/* 216 */           f.get(tileEntity);
/* 217 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 225 */       if ((tileEntity instanceof TileEntityProjector))
/*     */       {
/*     */         try
/*     */         {
/* 229 */           Field f = ReflectionHelper.findField(TileEntityProjector.class, new String[] { varname });
/* 230 */           f.get(tileEntity);
/* 231 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/* 238 */       if ((tileEntity instanceof TileEntityCapacitor)) {
/*     */         try
/*     */         {
/* 241 */           Field f = ReflectionHelper.findField(TileEntityCapacitor.class, new String[] { varname });
/* 242 */           f.get(tileEntity);
/* 243 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/* 250 */       if ((tileEntity instanceof TileEntityExtractor)) {
/*     */         try
/*     */         {
/* 253 */           Field f = ReflectionHelper.findField(TileEntityExtractor.class, new String[] { varname });
/* 254 */           f.get(tileEntity);
/* 255 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/* 262 */       if ((tileEntity instanceof TileEntityConverter)) {
/*     */         try
/*     */         {
/* 265 */           Field f = ReflectionHelper.findField(TileEntityConverter.class, new String[] { varname });
/* 266 */           f.get(tileEntity);
/* 267 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 275 */       if ((tileEntity instanceof TileEntityAreaDefenseStation)) {
/*     */         try
/*     */         {
/* 278 */           Field f = ReflectionHelper.findField(TileEntityAreaDefenseStation.class, new String[] { varname });
/* 279 */           f.get(tileEntity);
/* 280 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/* 287 */       if ((tileEntity instanceof TileEntityAdvSecurityStation)) {
/*     */         try
/*     */         {
/* 290 */           Field f = ReflectionHelper.findField(TileEntityAdvSecurityStation.class, new String[] { varname });
/* 291 */           f.get(tileEntity);
/* 292 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 300 */       if ((tileEntity instanceof TileEntitySecStorage)) {
/*     */         try
/*     */         {
/* 303 */           Field f = ReflectionHelper.findField(TileEntitySecStorage.class, new String[] { varname });
/* 304 */           f.get(tileEntity);
/* 305 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 313 */       if ((tileEntity instanceof TileEntityControlSystem)) {
/*     */         try
/*     */         {
/* 316 */           Field f = ReflectionHelper.findField(TileEntityControlSystem.class, new String[] { varname });
/* 317 */           f.get(tileEntity);
/* 318 */           dos.writeUTF(String.valueOf(f.get(tileEntity)));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 327 */       Packet250CustomPayload pkt = new Packet250CustomPayload();
/* 328 */       pkt.channel = "MFFS";
/* 329 */       pkt.data = bos.toByteArray();
/* 330 */       pkt.length = bos.size();
/* 331 */       pkt.isChunkDataPacket = true;
/*     */ 
/* 333 */       PacketDispatcher.sendPacketToAllAround(x, y, z, 80.0D, tileEntity.worldObj.provider.dimensionId, pkt);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.network.server.NetworkHandlerServer
 * JD-Core Version:    0.6.2
 */