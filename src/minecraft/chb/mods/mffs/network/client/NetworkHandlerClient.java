package chb.mods.mffs.network.client;

import chb.mods.mffs.common.CommonProxy;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
import chb.mods.mffs.common.tileentity.TileEntityConverter;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import chb.mods.mffs.common.tileentity.TileEntityForceField;
import chb.mods.mffs.common.tileentity.TileEntityMachines;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
import chb.mods.mffs.network.INetworkHandlerEventListener;
import chb.mods.mffs.network.INetworkHandlerListener;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;

public class NetworkHandlerClient
  implements IPacketHandler
{
  private static final boolean DEBUG = false;

  public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
  {
    ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
    int x = dat.readInt();
    int y = dat.readInt();
    int z = dat.readInt();
    int typ = dat.readInt();
    World world = ModularForceFieldSystem.proxy.getClientWorld();

    switch (typ)
    {
    case 100:
      String DataPacket = dat.readUTF();

      for (String blockupdate : DataPacket.split(">"))
      {
        if (blockupdate.length() > 0)
        {
          String[] projector = blockupdate.split("<");
          String[] Corrdinaten = projector[1].split("/");
          String[] temp = projector[0].split("!");
          String[] Dim = temp[1].split("/");
          String[] ProjectorCorr = temp[0].split("/");

          if (Integer.parseInt(Dim[0].trim()) == world.provider.dimensionId)
          {
            if (world.getChunkFromBlockCoords(Integer.parseInt(Corrdinaten[0].trim()), Integer.parseInt(Corrdinaten[2].trim())).isChunkLoaded)
            {
              TileEntity te = world.getBlockTileEntity(Integer.parseInt(Corrdinaten[0].trim()), Integer.parseInt(Corrdinaten[1].trim()), Integer.parseInt(Corrdinaten[2].trim()));
              if ((te instanceof TileEntityForceField))
              {
                TileEntity proj = world.getBlockTileEntity(Integer.parseInt(ProjectorCorr[2].trim()), Integer.parseInt(ProjectorCorr[1].trim()), Integer.parseInt(ProjectorCorr[0].trim()));
                if ((proj instanceof TileEntityProjector))
                {
                  ((TileEntityForceField)te).setTexturfile(((TileEntityProjector)proj).getForceFieldTexturfile());
                  ((TileEntityForceField)te).setTexturid(((TileEntityProjector)proj).getForceFieldTexturID());
                  ((TileEntityForceField)te).setForcefieldCamoblockid(((TileEntityProjector)proj).getForcefieldCamoblockid());
                  ((TileEntityForceField)te).setForcefieldCamoblockmeta(((TileEntityProjector)proj).getForcefieldCamoblockmeta());
                }
              }
            }

          }

        }

      }

      break;
    case 1:
      String fieldname = dat.readUTF();

      TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

      if ((tileEntity instanceof TileEntityMachines)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityMachines.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityCapacitor)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityCapacitor.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityExtractor)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityExtractor.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityConverter)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityConverter.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityProjector)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityProjector.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityAreaDefenseStation)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityAreaDefenseStation.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityAdvSecurityStation)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntityAdvSecurityStation.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntitySecStorage)) {
        try
        {
          Field f = ReflectionHelper.findField(TileEntitySecStorage.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }

      }

      if ((tileEntity instanceof TileEntityControlSystem))
        try
        {
          Field f = ReflectionHelper.findField(TileEntityControlSystem.class, new String[] { fieldname });
          reflectionsetvalue(f, tileEntity, dat, fieldname);
        }
        catch (Exception e)
        {
        }
      break;
    }
  }

  public static void reflectionsetvalue(Field f, TileEntity tileEntity, ByteArrayDataInput dat, String fieldname)
  {
    try
    {
      if (f.getType().equals(Integer.TYPE)) f.setInt(tileEntity, Integer.parseInt(dat.readUTF()));
      if (f.getType().equals(Boolean.TYPE)) f.setBoolean(tileEntity, Boolean.parseBoolean(dat.readUTF()));
      if (f.getType().equals(Short.TYPE)) f.setShort(tileEntity, Short.parseShort(dat.readUTF()));
      if (f.getType().equals(Float.TYPE)) f.setFloat(tileEntity, Float.parseFloat(dat.readUTF()));
      if (f.getType().equals(String.class)) f.set(tileEntity, dat.readUTF());

      if ((tileEntity instanceof INetworkHandlerListener))
      {
        ((INetworkHandlerListener)tileEntity).onNetworkHandlerUpdate(fieldname);
      }
    }
    catch (Exception e)
    {
    }
  }

  public static Packet requestInitialData(TileEntity tileEntity)
  {
    return requestInitialData(tileEntity, false);
  }

  public static void requestForceFieldInitialData(int Dimension, String corridnaten)
  {
    try
    {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(63000);
      DataOutputStream dos = new DataOutputStream(bos);

      dos.writeInt(0);
      dos.writeInt(0);
      dos.writeInt(0);
      dos.writeInt(10);
      dos.writeInt(Dimension);
      dos.writeUTF(corridnaten);

      Packet250CustomPayload pkt = new Packet250CustomPayload();
      pkt.channel = "MFFS";
      pkt.data = bos.toByteArray();
      pkt.length = bos.size();
      pkt.isChunkDataPacket = false;

      PacketDispatcher.sendPacketToServer(pkt);
    }
    catch (Exception e)
    {
      System.out.println(e.getLocalizedMessage());
    }
  }

  public static Packet requestInitialData(TileEntity tileEntity, boolean senddirekt)
  {
    ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
    DataOutputStream dos = new DataOutputStream(bos);
    int x = tileEntity.xCoord;
    int y = tileEntity.yCoord;
    int z = tileEntity.zCoord;
    int typ = 2;

    int Dimension = tileEntity.worldObj.provider.dimensionId;

    StringBuilder str = new StringBuilder();

    for (String fields : ((INetworkHandlerListener)tileEntity).getFieldsforUpdate())
    {
      str.append(fields);
      str.append("/");
    }

    try
    {
      dos.writeInt(x);
      dos.writeInt(y);
      dos.writeInt(z);
      dos.writeInt(typ);
      dos.writeInt(Dimension);
      dos.writeUTF(str.toString());
    }
    catch (Exception e)
    {
    }

    Packet250CustomPayload pkt = new Packet250CustomPayload();
    pkt.channel = "MFFS";
    pkt.data = bos.toByteArray();
    pkt.length = bos.size();
    pkt.isChunkDataPacket = false;

    if (senddirekt) {
      PacketDispatcher.sendPacketToServer(pkt);
    }
    return pkt;
  }

  public static void fireTileEntityEvent(TileEntity tileEntity, int key, String vaule)
  {
    if ((tileEntity instanceof INetworkHandlerEventListener))
    {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
      DataOutputStream dos = new DataOutputStream(bos);
      int x = tileEntity.xCoord;
      int y = tileEntity.yCoord;
      int z = tileEntity.zCoord;
      int typ = 3;

      int Dimension = tileEntity.worldObj.provider.dimensionId;
      try
      {
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(z);
        dos.writeInt(typ);
        dos.writeInt(Dimension);
        dos.writeInt(key);
        dos.writeUTF(vaule);
      }
      catch (Exception e)
      {
      }

      Packet250CustomPayload pkt = new Packet250CustomPayload();
      pkt.channel = "MFFS";
      pkt.data = bos.toByteArray();
      pkt.length = bos.size();
      pkt.isChunkDataPacket = true;

      PacketDispatcher.sendPacketToServer(pkt);
    }
  }
}