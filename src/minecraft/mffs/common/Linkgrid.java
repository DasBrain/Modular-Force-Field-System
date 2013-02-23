package mffs.common;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import mffs.api.PointXYZ;
import mffs.common.tileentity.TileEntityAdvSecurityStation;
import mffs.common.tileentity.TileEntityAreaDefenseStation;
import mffs.common.tileentity.TileEntityCapacitor;
import mffs.common.tileentity.TileEntityControlSystem;
import mffs.common.tileentity.TileEntityConverter;
import mffs.common.tileentity.TileEntityExtractor;
import mffs.common.tileentity.TileEntityMachines;
import mffs.common.tileentity.TileEntityProjector;
import mffs.common.tileentity.TileEntitySecStorage;
import net.minecraft.world.World;

import com.google.common.collect.MapMaker;

public final class Linkgrid
{
	private static Map WorldpowernetMap = new MapMaker().weakKeys().makeMap();

	public static Worldlinknet getWorldMap(World world)
	{
		if (world != null)
		{
			if (!WorldpowernetMap.containsKey(world))
			{
				WorldpowernetMap.put(world, new Worldlinknet());
			}
			return (Worldlinknet) WorldpowernetMap.get(world);
		}

		return null;
	}

	public static class Worldlinknet
	{
		private Map<Integer, TileEntityProjector> Projector = new Hashtable();
		private Map<Integer, TileEntityCapacitor> Capacitors = new Hashtable();
		private Map<Integer, TileEntityAdvSecurityStation> SecStation = new Hashtable();
		private Map<Integer, TileEntityAreaDefenseStation> DefStation = new Hashtable();
		private Map<Integer, TileEntityExtractor> Extractor = new Hashtable();
		private Map<Integer, TileEntityConverter> Converter = new Hashtable();
		private Map Jammer = new Hashtable();
		private Map FieldFusion = new Hashtable();
		private Map SecStorage = new Hashtable();
		private Map ControlSystem = new Hashtable();

		public Map getSecStorage()
		{
			return this.SecStorage;
		}

		public Map getControlSystem()
		{
			return this.ControlSystem;
		}

		public Map getConverter()
		{
			return this.Converter;
		}

		public Map getExtractor()
		{
			return this.Extractor;
		}

		public Map getProjektor()
		{
			return this.Projector;
		}

		public Map getCapacitor()
		{
			return this.Capacitors;
		}

		public Map getSecStation()
		{
			return this.SecStation;
		}

		public Map getDefStation()
		{
			return this.DefStation;
		}

		public Map getJammer()
		{
			return this.Jammer;
		}

		public Map getFieldFusion()
		{
			return this.FieldFusion;
		}

		public int refreshID(TileEntityMachines tileEntity, int remDeviceID)
		{
			Random random = new Random();
			int DeviceID = random.nextInt();
			if ((tileEntity instanceof TileEntitySecStorage))
			{
				if (remDeviceID == 0)
				{
					while (this.SecStorage.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.SecStorage.put(Integer.valueOf(DeviceID), (TileEntitySecStorage) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityControlSystem))
			{
				if (remDeviceID == 0)
				{
					while (this.ControlSystem.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.ControlSystem.put(Integer.valueOf(DeviceID), (TileEntityControlSystem) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityAdvSecurityStation))
			{
				if (remDeviceID == 0)
				{
					while (this.SecStation.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.SecStation.put(Integer.valueOf(DeviceID), (TileEntityAdvSecurityStation) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityAreaDefenseStation))
			{
				if (remDeviceID == 0)
				{
					while (this.DefStation.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.DefStation.put(Integer.valueOf(DeviceID), (TileEntityAreaDefenseStation) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityCapacitor))
			{
				if (remDeviceID == 0)
				{
					while (this.Capacitors.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.Capacitors.put(Integer.valueOf(DeviceID), (TileEntityCapacitor) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityConverter))
			{
				if (remDeviceID == 0)
				{
					while (this.Converter.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.Converter.put(Integer.valueOf(DeviceID), (TileEntityConverter) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityExtractor))
			{
				if (remDeviceID == 0)
				{
					while (this.Extractor.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.Extractor.put(Integer.valueOf(DeviceID), (TileEntityExtractor) tileEntity);
				return DeviceID;
			}
			if ((tileEntity instanceof TileEntityProjector))
			{
				if (remDeviceID == 0)
				{
					while (this.Projector.get(Integer.valueOf(DeviceID)) != null)
						DeviceID = random.nextInt();
				}
				DeviceID = remDeviceID;
				this.Projector.put(Integer.valueOf(DeviceID), (TileEntityProjector) tileEntity);
				return DeviceID;
			}
			return 0;
		}

		public int connectedtoCapacitor(TileEntityCapacitor Cap, int range)
		{
			int counter = 0;
			for (TileEntityProjector tileentity : this.Projector.values())
			{
				if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint())))
				{
					counter++;
				}

			}

			for (TileEntityCapacitor tileentity : this.Capacitors.values())
			{
				if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint())))
				{
					counter++;
				}

			}

			for (TileEntityAreaDefenseStation tileentity : this.DefStation.values())
			{
				if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint())))
				{
					counter++;
				}

			}

			for (TileEntityExtractor tileentity : this.Extractor.values())
			{
				if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint())))
				{
					counter++;
				}

			}

			for (TileEntityConverter tileentity : this.Converter.values())
			{
				if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint())))
				{
					counter++;
				}

			}

			return counter;
		}

		public TileEntityMachines getTileEntityMachines(String displayname, int key)
		{
			MFFSMaschines tem = MFFSMaschines.fromdisplayName(displayname);

			if (tem != null)
			{
				switch (tem.index)
				{
					case 1:
						return (TileEntityMachines) getProjektor().get(Integer.valueOf(key));
					case 2:
						return (TileEntityMachines) getExtractor().get(Integer.valueOf(key));
					case 3:
						return (TileEntityMachines) getCapacitor().get(Integer.valueOf(key));
					case 4:
						return (TileEntityMachines) getConverter().get(Integer.valueOf(key));
					case 5:
						return (TileEntityMachines) getDefStation().get(Integer.valueOf(key));
					case 6:
						return (TileEntityMachines) getSecStation().get(Integer.valueOf(key));
					case 7:
						return (TileEntityMachines) getSecStorage().get(Integer.valueOf(key));
					case 8:
						return (TileEntityMachines) getControlSystem().get(Integer.valueOf(key));
				}
			}
			return null;
		}
	}
}