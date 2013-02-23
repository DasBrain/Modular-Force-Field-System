package mffs.network.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;
import java.util.Map;
import java.util.Stack;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.google.common.collect.MapMaker;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ForceFieldServerUpdatehandler implements IScheduledTickHandler
{
	private static Map WorldForcedield = new MapMaker().weakKeys().makeMap();

	public void tickEnd(EnumSet type, Object[] tickData)
	{
		for (World world : DimensionManager.getWorlds())
		{
			StringBuilder str = new StringBuilder();

			while (!getWorldMap(world).queue.isEmpty())
			{
				str.append(getWorldMap(world).queue.pop());
				str.append("/");
				str.append(getWorldMap(world).queue.pop());
				str.append("/");
				str.append(getWorldMap(world).queue.pop());
				str.append("!");
				str.append(getWorldMap(world).queue.pop());
				str.append("<");
				str.append(getWorldMap(world).queue.pop());
				str.append("/");
				str.append(getWorldMap(world).queue.pop());
				str.append("/");
				str.append(getWorldMap(world).queue.pop());
				str.append(">");

				if (str.length() > 7500)
				{
					break;
				}
			}
			if (str.length() > 0)
			{
				try
				{
					ByteArrayOutputStream bos = new ByteArrayOutputStream(63000);
					DataOutputStream dos = new DataOutputStream(bos);
					int typ = 100;

					dos.writeInt(0);
					dos.writeInt(0);
					dos.writeInt(0);
					dos.writeInt(typ);
					dos.writeUTF(str.toString());

					Packet250CustomPayload pkt = new Packet250CustomPayload();
					pkt.channel = "MFFS";
					pkt.data = bos.toByteArray();
					pkt.length = bos.size();
					pkt.isChunkDataPacket = true;

					PacketDispatcher.sendPacketToAllInDimension(pkt, world.provider.dimensionId);
				}
				catch (Exception e)
				{
					System.out.println(e.getLocalizedMessage());
				}
			}

			str.setLength(0);
		}
	}

	public void tickStart(EnumSet type, Object[] tickData)
	{
	}

	public EnumSet ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	public String getLabel()
	{
		return "ForceField Server Ticker";
	}

	public int nextTickSpacing()
	{
		return 1;
	}

	public static ForceFieldpacket getWorldMap(World world)
	{
		if (world != null)
		{
			if (!WorldForcedield.containsKey(world))
			{
				WorldForcedield.put(world, new ForceFieldpacket());
			}
			return (ForceFieldpacket) WorldForcedield.get(world);
		}

		return null;
	}

	public static class ForceFieldpacket
	{
		protected Stack queue = new Stack();

		public void addto(int x, int y, int z, int dimensionId, int px, int py, int pz)
		{
			this.queue.push(Integer.valueOf(z));
			this.queue.push(Integer.valueOf(y));
			this.queue.push(Integer.valueOf(x));
			this.queue.push(Integer.valueOf(dimensionId));
			this.queue.push(Integer.valueOf(px));
			this.queue.push(Integer.valueOf(py));
			this.queue.push(Integer.valueOf(pz));
		}
	}
}