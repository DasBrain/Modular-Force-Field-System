package chb.mods.mffs.common;

import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
import chb.mods.mffs.common.tileentity.TileEntityConverter;
import chb.mods.mffs.common.tileentity.TileEntityExtractor;
import chb.mods.mffs.common.tileentity.TileEntityMachines;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
import com.google.common.collect.MapMaker;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import net.minecraft.world.World;

public final class Linkgrid
{
  private static Map WorldpowernetMap = new MapMaker().weakKeys().makeMap();

  public static Worldlinknet getWorldMap(World world)
  {
    if (world != null) {
      if (!WorldpowernetMap.containsKey(world)) {
        WorldpowernetMap.put(world, new Worldlinknet());
      }
      return (Worldlinknet)WorldpowernetMap.get(world);
    }

    return null;
  }

  public static class Worldlinknet
  {
    private Map Projektor = new Hashtable();
    private Map Capacitors = new Hashtable();
    private Map SecStation = new Hashtable();
    private Map DefStation = new Hashtable();
    private Map Extractor = new Hashtable();
    private Map Converter = new Hashtable();
    private Map Jammer = new Hashtable();
    private Map FieldFusion = new Hashtable();
    private Map SecStorage = new Hashtable();
    private Map ControlSystem = new Hashtable();

    public Map getSecStorage() {
      return this.SecStorage;
    }

    public Map getControlSystem() {
      return this.ControlSystem;
    }

    public Map getConverter() {
      return this.Converter;
    }

    public Map getExtractor() {
      return this.Extractor;
    }

    public Map getProjektor() {
      return this.Projektor;
    }

    public Map getCapacitor() {
      return this.Capacitors;
    }

    public Map getSecStation() {
      return this.SecStation;
    }

    public Map getDefStation() {
      return this.DefStation;
    }

    public Map getJammer() {
      return this.Jammer;
    }

    public Map getFieldFusion() {
      return this.FieldFusion;
    }

    public int refreshID(TileEntityMachines tileEntity, int remDeviceID)
    {
      Random random = new Random();
      int DeviceID = random.nextInt();
      if ((tileEntity instanceof TileEntitySecStorage)) {
        if (remDeviceID == 0) {
          while (this.SecStorage.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.SecStorage.put(Integer.valueOf(DeviceID), (TileEntitySecStorage)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityControlSystem)) {
        if (remDeviceID == 0) {
          while (this.ControlSystem.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.ControlSystem.put(Integer.valueOf(DeviceID), (TileEntityControlSystem)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityAdvSecurityStation)) {
        if (remDeviceID == 0) {
          while (this.SecStation.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.SecStation.put(Integer.valueOf(DeviceID), (TileEntityAdvSecurityStation)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityAreaDefenseStation)) {
        if (remDeviceID == 0) {
          while (this.DefStation.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.DefStation.put(Integer.valueOf(DeviceID), (TileEntityAreaDefenseStation)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityCapacitor)) {
        if (remDeviceID == 0) {
          while (this.Capacitors.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.Capacitors.put(Integer.valueOf(DeviceID), (TileEntityCapacitor)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityConverter)) {
        if (remDeviceID == 0) {
          while (this.Converter.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.Converter.put(Integer.valueOf(DeviceID), (TileEntityConverter)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityExtractor)) {
        if (remDeviceID == 0) {
          while (this.Extractor.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.Extractor.put(Integer.valueOf(DeviceID), (TileEntityExtractor)tileEntity);
        return DeviceID;
      }
      if ((tileEntity instanceof TileEntityProjector)) {
        if (remDeviceID == 0) {
          while (this.Projektor.get(Integer.valueOf(DeviceID)) != null)
            DeviceID = random.nextInt();
        }
        DeviceID = remDeviceID;
        this.Projektor.put(Integer.valueOf(DeviceID), (TileEntityProjector)tileEntity);
        return DeviceID;
      }
      return 0;
    }

    public int connectedtoCapacitor(TileEntityCapacitor Cap, int range)
    {
      int counter = 0;
      for (TileEntityProjector tileentity : this.Projektor.values()) {
        if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
          (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
          counter++;
        }

      }

      for (TileEntityCapacitor tileentity : this.Capacitors.values()) {
        if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
          (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
          counter++;
        }

      }

      for (TileEntityAreaDefenseStation tileentity : this.DefStation.values()) {
        if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
          (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
          counter++;
        }

      }

      for (TileEntityExtractor tileentity : this.Extractor.values()) {
        if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
          (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
          counter++;
        }

      }

      for (TileEntityConverter tileentity : this.Converter.values()) {
        if ((tileentity.getPowerSourceID() == Cap.getPowerStorageID()) && 
          (range >= PointXYZ.distance(tileentity.getMaschinePoint(), Cap.getMaschinePoint()))) {
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
          return (TileEntityMachines)getProjektor().get(Integer.valueOf(key));
        case 2:
          return (TileEntityMachines)getExtractor().get(Integer.valueOf(key));
        case 3:
          return (TileEntityMachines)getCapacitor().get(Integer.valueOf(key));
        case 4:
          return (TileEntityMachines)getConverter().get(Integer.valueOf(key));
        case 5:
          return (TileEntityMachines)getDefStation().get(Integer.valueOf(key));
        case 6:
          return (TileEntityMachines)getSecStation().get(Integer.valueOf(key));
        case 7:
          return (TileEntityMachines)getSecStorage().get(Integer.valueOf(key));
        case 8:
          return (TileEntityMachines)getControlSystem().get(Integer.valueOf(key));
        }
      }
      return null;
    }
  }
}