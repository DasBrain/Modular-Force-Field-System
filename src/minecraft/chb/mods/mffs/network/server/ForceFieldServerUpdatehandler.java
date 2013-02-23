/*     */ package chb.mods.mffs.network.server;
/*     */ 
/*     */ import com.google.common.collect.MapMaker;
/*     */ import cpw.mods.fml.common.IScheduledTickHandler;
/*     */ import cpw.mods.fml.common.TickType;
/*     */ import cpw.mods.fml.common.network.PacketDispatcher;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import net.minecraft.network.packet.Packet250CustomPayload;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ public class ForceFieldServerUpdatehandler
/*     */   implements IScheduledTickHandler
/*     */ {
/*  21 */   private static Map WorldForcedield = new MapMaker().weakKeys().makeMap();
/*     */ 
/*     */   public void tickEnd(EnumSet type, Object[] tickData)
/*     */   {
/*  27 */     for (World world : DimensionManager.getWorlds())
/*     */     {
/*  29 */       StringBuilder str = new StringBuilder();
/*     */ 
/*  31 */       while (!getWorldMap(world).queue.isEmpty())
/*     */       {
/*  34 */         str.append(getWorldMap(world).queue.pop());
/*  35 */         str.append("/");
/*  36 */         str.append(getWorldMap(world).queue.pop());
/*  37 */         str.append("/");
/*  38 */         str.append(getWorldMap(world).queue.pop());
/*  39 */         str.append("!");
/*  40 */         str.append(getWorldMap(world).queue.pop());
/*  41 */         str.append("<");
/*  42 */         str.append(getWorldMap(world).queue.pop());
/*  43 */         str.append("/");
/*  44 */         str.append(getWorldMap(world).queue.pop());
/*  45 */         str.append("/");
/*  46 */         str.append(getWorldMap(world).queue.pop());
/*  47 */         str.append(">");
/*     */ 
/*  49 */         if (str.length() > 7500) {
/*  50 */           break;
/*     */         }
/*     */       }
/*  53 */       if (str.length() > 0) {
/*     */         try
/*     */         {
/*  56 */           ByteArrayOutputStream bos = new ByteArrayOutputStream(63000);
/*  57 */           DataOutputStream dos = new DataOutputStream(bos);
/*  58 */           int typ = 100;
/*     */ 
/*  60 */           dos.writeInt(0);
/*  61 */           dos.writeInt(0);
/*  62 */           dos.writeInt(0);
/*  63 */           dos.writeInt(typ);
/*  64 */           dos.writeUTF(str.toString());
/*     */ 
/*  66 */           Packet250CustomPayload pkt = new Packet250CustomPayload();
/*  67 */           pkt.channel = "MFFS";
/*  68 */           pkt.data = bos.toByteArray();
/*  69 */           pkt.length = bos.size();
/*  70 */           pkt.isChunkDataPacket = true;
/*     */ 
/*  72 */           PacketDispatcher.sendPacketToAllInDimension(pkt, world.provider.dimensionId);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  76 */           System.out.println(e.getLocalizedMessage());
/*     */         }
/*     */       }
/*     */ 
/*  80 */       str.setLength(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void tickStart(EnumSet type, Object[] tickData)
/*     */   {
/*     */   }
/*     */ 
/*     */   public EnumSet ticks()
/*     */   {
/*  94 */     return EnumSet.of(TickType.PLAYER);
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/*  99 */     return "ForceField Server Ticker";
/*     */   }
/*     */ 
/*     */   public int nextTickSpacing()
/*     */   {
/* 104 */     return 1;
/*     */   }
/*     */ 
/*     */   public static ForceFieldpacket getWorldMap(World world) {
/* 108 */     if (world != null) {
/* 109 */       if (!WorldForcedield.containsKey(world)) {
/* 110 */         WorldForcedield.put(world, new ForceFieldpacket());
/*     */       }
/* 112 */       return (ForceFieldpacket)WorldForcedield.get(world);
/*     */     }
/*     */ 
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   public static class ForceFieldpacket
/*     */   {
/* 121 */     protected Stack queue = new Stack();
/*     */ 
/*     */     public void addto(int x, int y, int z, int dimensionId, int px, int py, int pz)
/*     */     {
/* 127 */       this.queue.push(Integer.valueOf(z));
/* 128 */       this.queue.push(Integer.valueOf(y));
/* 129 */       this.queue.push(Integer.valueOf(x));
/* 130 */       this.queue.push(Integer.valueOf(dimensionId));
/* 131 */       this.queue.push(Integer.valueOf(px));
/* 132 */       this.queue.push(Integer.valueOf(py));
/* 133 */       this.queue.push(Integer.valueOf(pz));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.network.server.ForceFieldServerUpdatehandler
 * JD-Core Version:    0.6.2
 */