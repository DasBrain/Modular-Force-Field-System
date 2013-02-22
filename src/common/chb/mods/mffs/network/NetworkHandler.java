/*  
Copyright (C) 2012 Thunderdark

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
Thunderdark - initial implementation
*/

package chb.mods.mffs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.TileEntityAreaDefenseStation;
import chb.mods.mffs.common.TileEntityCapacitor;
import chb.mods.mffs.common.TileEntityExtractor;
import chb.mods.mffs.common.TileEntityMachines;
import chb.mods.mffs.common.TileEntityProjector;
import chb.mods.mffs.common.TileEntitySecurityStation;
import chb.mods.mffs.common.TileEntityForceField;
import chb.mods.mffs.common.TileEntityConverter;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.ReflectionHelper;



public class NetworkHandler implements IPacketHandler{

private static final boolean DEBUG = false;

@Override
public void onPacketData(NetworkManager manager,Packet250CustomPayload packet, Player player) {
	

	ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
	int x = dat.readInt();
	int y = dat.readInt();
	int z = dat.readInt();
	int typ = dat.readInt(); 
	

	switch(typ)
	{
	case 1:
		
		String fieldname = dat.readUTF();
		
		World world = ModularForceFieldSystem.proxy.getClientWorld();
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		 if(tileEntity instanceof TileEntityMachines)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityMachines.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }

		 if(tileEntity instanceof TileEntityCapacitor)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityCapacitor.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }
		 
		 if(tileEntity instanceof TileEntityExtractor)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityExtractor.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }
		 
		 
		 if(tileEntity instanceof TileEntityConverter)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityConverter.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }

		 

		 if(tileEntity instanceof TileEntityProjector)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityProjector.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }
		 
		 if(tileEntity instanceof TileEntityAreaDefenseStation)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityAreaDefenseStation.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }
		 
		 if(tileEntity instanceof TileEntitySecurityStation)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntitySecurityStation.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }
		 
		 if(tileEntity instanceof TileEntityForceField)
		 {
			 try{
				 Field f = ReflectionHelper.findField(TileEntityForceField.class, fieldname);
				 reflectionsetvalue(f, tileEntity,dat,fieldname);
			 }catch(Exception e)
			 {
				 if(DEBUG)
				 System.out.println(e.getLocalizedMessage());
			 } 
		 }
		 
		
		 
	break;
	case 2:
		
		int dimension = dat.readInt() ;
		String daten = dat.readUTF(); 
		World serverworld = DimensionManager.getWorld(dimension);
		TileEntity servertileEntity = serverworld.getBlockTileEntity(x, y, z);
		
		for(String varname : daten.split("/"))
		{
			updateTileEntityField(servertileEntity,  varname);
		}
	break;
	
	case 3:
		int dimension2 = dat.readInt() ;
		int evt = dat.readInt() ;
		
		World serverworld2 = DimensionManager.getWorld(dimension2);
		TileEntity servertileEntity2 = serverworld2.getBlockTileEntity(x, y, z);
		
		if(servertileEntity2 instanceof INetworkHandlerEventListener)
		{
			((INetworkHandlerEventListener)servertileEntity2).onNetworkHandlerEvent(evt);
			
		}
		
		
	break;		
	}
	

	 
}

public static void reflectionsetvalue(Field f,TileEntity tileEntity,ByteArrayDataInput dat,String fieldname) 
{
	try{
		
		 if(f.getType().equals(java.lang.Integer.TYPE)){f.setInt(tileEntity, Integer.parseInt(dat.readUTF()));}
		 if(f.getType().equals(java.lang.Boolean.TYPE)){f.setBoolean(tileEntity, Boolean.parseBoolean(dat.readUTF()));}
		 if(f.getType().equals(java.lang.Short.TYPE)){f.setShort(tileEntity, Short.parseShort(dat.readUTF()));}
		 if(f.getType().equals(java.lang.Float.TYPE)){f.setFloat(tileEntity, Float.parseFloat(dat.readUTF()));}

		 if(tileEntity instanceof TileEntityForceField)
		 {
			 ((TileEntityForceField)tileEntity).setTexturid(dat.readUTF());
		 }
		 
		 if(tileEntity instanceof INetworkHandlerListener )
		 {
			 ((INetworkHandlerListener)tileEntity).onNetworkHandlerUpdate(fieldname);
		 }
	 }catch(Exception e)
	 {
		 if(DEBUG)
		 System.out.println(e.getMessage());
	 }
}







public static void updateTileEntityField(TileEntity tileEntity, String varname)
{
	if(tileEntity != null)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		int typ = 1; // Server -> Client

		 try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(typ);
			dos.writeUTF(varname);
			
			} catch (Exception e) {
				if(DEBUG)
				System.out.println(e.getLocalizedMessage());
			}
		
 if(tileEntity instanceof TileEntityMachines)
  {
	 try {
	        Field f = ReflectionHelper.findField(TileEntityMachines.class, varname);
	        f.get(tileEntity);
	    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
		} catch (Exception e) {
			if(DEBUG)
			System.out.println(e.getLocalizedMessage());
		}
  }
		
		
 if(tileEntity instanceof TileEntityProjector)
 {
	
	 try {	
        Field f = ReflectionHelper.findField(TileEntityProjector.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getLocalizedMessage());
	}
 }
 
 if(tileEntity instanceof TileEntityCapacitor)
 {
	 try {	
        Field f = ReflectionHelper.findField(TileEntityCapacitor.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getLocalizedMessage());
	}
 }
 
 if(tileEntity instanceof TileEntityExtractor)
 {
	 try {	
        Field f = ReflectionHelper.findField(TileEntityExtractor.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getLocalizedMessage());
	}
 }
 
 if(tileEntity instanceof TileEntityConverter)
 {
	 try {	
        Field f = ReflectionHelper.findField(TileEntityConverter.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getLocalizedMessage());
	}
 }
 
 
 if(tileEntity instanceof TileEntityAreaDefenseStation)
 {
	 try {	
        Field f = ReflectionHelper.findField(TileEntityAreaDefenseStation.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getLocalizedMessage());
	}
 }
 
 if(tileEntity instanceof TileEntitySecurityStation)
 {
	 try {	
        Field f = ReflectionHelper.findField(TileEntitySecurityStation.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(String.valueOf(f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getLocalizedMessage());
	}
 }
 
 if(tileEntity instanceof TileEntityForceField)
 {
	 try {	
        Field f = ReflectionHelper.findField(TileEntityForceField.class, varname);
        f.get(tileEntity);
    	dos.writeUTF(Arrays.toString((int[]) f.get(tileEntity)));
	} catch (Exception e) {
		if(DEBUG)
		System.out.println(e.getStackTrace());
	}
 }
 
 

		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MFFS";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;

		PacketDispatcher.sendPacketToAllAround(x, y, z, 60, tileEntity.worldObj.provider.dimensionId, pkt);
	}
	
}

public static Packet requestInitialData(TileEntity tileEntity){
	

		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		int typ = 2; // Client -> Server
		int Dimension = tileEntity.worldObj.provider.dimensionId;
	   
		StringBuilder str = new StringBuilder();
		
		for(String fields : ((INetworkHandlerListener)tileEntity).geFieldsforUpdate())
		{
			str.append(fields);
			str.append("/");
		}
	
		
		 try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(typ);
			dos.writeInt(Dimension);
			dos.writeUTF(str.toString());
	
			
			} catch (Exception e) {
				if(DEBUG)
				System.out.println(e.getLocalizedMessage());
			}
		
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MFFS";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		
		return pkt;

}


public static void fireTileEntityEvent(TileEntity tileEntity,int event){
	
	
	if(tileEntity instanceof INetworkHandlerEventListener)
	{
		
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		int typ = 3; // Client -> Server
		
		
		
		int Dimension = tileEntity.worldObj.provider.dimensionId;
		
		 try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(typ);
			dos.writeInt(Dimension);
			dos.writeInt(event);
		
			} catch (Exception e) {
				if(DEBUG)
				System.out.println(e.getLocalizedMessage());
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