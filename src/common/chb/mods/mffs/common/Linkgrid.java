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

package chb.mods.mffs.common;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public final class Linkgrid {
	private static Map WorldpowernetMap = new HashMap();

	static class Worldlinknet {
		private Map<Integer, TileEntityProjector> Projektor = new Hashtable<Integer, TileEntityProjector>();
		private Map<Integer, TileEntityCapacitor> Capacitors = new Hashtable<Integer, TileEntityCapacitor>();
		private Map<Integer, TileEntitySecurityStation> SecStation = new Hashtable<Integer, TileEntitySecurityStation>();
		private Map<Integer, TileEntityAreaDefenseStation> DefStation = new Hashtable<Integer, TileEntityAreaDefenseStation>();
		private Map<Integer, TileEntityExtractor> Extractor = new Hashtable<Integer, TileEntityExtractor>();
		private Map<Integer, TileEntityConverter> Converter = new Hashtable<Integer, TileEntityConverter>();
		private Map<Integer, TileEntityProjector> Jammer = new Hashtable<Integer, TileEntityProjector>();
		private Map<Integer, TileEntityProjector> FieldFusion = new Hashtable<Integer, TileEntityProjector>();

		
		
		public Map<Integer, TileEntityConverter> getConverter() {
			return Converter;
		}
		

		public Map<Integer, TileEntityExtractor> getExtractor() {
			return Extractor;
		}
		
		public Map<Integer, TileEntityProjector> getProjektor() {
			return Projektor;
		}

		public Map<Integer, TileEntityCapacitor> getCapacitor() {
			return Capacitors;
		}

		public Map<Integer, TileEntitySecurityStation> getSecStation() {
			return SecStation;
		}

		public Map<Integer, TileEntityAreaDefenseStation> getDefStation() {
			return DefStation;
		}

		public Map<Integer, TileEntityProjector> getJammer() {
			return Jammer;
		}

		public Map<Integer, TileEntityProjector> getFieldFusion() {
			return FieldFusion;
		}
		
		public int newID(TileEntity tileEntity)
		{
			Random random = new Random();
			int tempID = random.nextInt();
			
			if(tileEntity instanceof TileEntityAreaDefenseStation){
				
				while (DefStation.get(tempID) != null) {
					tempID = random.nextInt();
				}
				DefStation.put(tempID, (TileEntityAreaDefenseStation) tileEntity);
				
			}
			
			if(tileEntity instanceof TileEntityProjector){
				
				while (Projektor.get(tempID) != null) {
					tempID = random.nextInt();
				}
				Projektor.put(tempID, (TileEntityProjector) tileEntity);
				
			}
			
			if(tileEntity instanceof TileEntityCapacitor){
				
				while (Capacitors.get(tempID) != null) {
					tempID = random.nextInt();
				}
				Capacitors.put(tempID, (TileEntityCapacitor) tileEntity);
				
			}
			
			if(tileEntity instanceof TileEntitySecurityStation){
				
				while (SecStation.get(tempID) != null) {
					tempID = random.nextInt();
				}
				SecStation.put(tempID, (TileEntitySecurityStation) tileEntity);
				
			}
			
			if(tileEntity instanceof TileEntityExtractor){
				
				while (Extractor.get(tempID) != null) {
					tempID = random.nextInt();
				}
				Extractor.put(tempID, (TileEntityExtractor) tileEntity);
				
			}
			
			if(tileEntity instanceof TileEntityConverter){
				
				while (Converter.get(tempID) != null) {
					tempID = random.nextInt();
				}
				Converter.put(tempID, (TileEntityConverter) tileEntity);
				
			}
			
			return tempID;
		}
		
		
		

		public static int myRandom(int low, int high) {
			return (int) (Math.random() * (high - low) + low);
		}

		public int condevisec(int Capacitors_ID, int xCoordr, int yCoordr,
				int zCoordr, int i) {
			int counter = 0;
			for (TileEntityProjector tileentity : Projektor.values()) {
				if (tileentity.getLinkCapacitor_ID() == Capacitors_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (i >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}
				}
			}

			for (TileEntityAreaDefenseStation tileentity : DefStation.values()) {
				if (tileentity.getlinkCapacitors_ID() == Capacitors_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (i >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}
				}
			}
			
			for (TileEntityExtractor tileentity : Extractor.values()) {
				if (tileentity.getLinkCapacitors_ID() == Capacitors_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (i >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}
				}
			}
			
			for (TileEntityConverter tileentity : Converter.values()) {
				if (tileentity.getLinkCapacitors_ID() == Capacitors_ID) {
					int dx = tileentity.xCoord - xCoordr;
					int dy = tileentity.yCoord - yCoordr;
					int dz = tileentity.zCoord - zCoordr;

					if (i >= Math.sqrt(dx * dx + dy * dy + dz * dz)) {
						counter++;
					}
				}
			}

			return counter;
		}
	}

	public static Worldlinknet getWorldMap(World world) {
		if (world != null) {
			if (!WorldpowernetMap.containsKey(world)) {
				WorldpowernetMap.put(world, new Worldlinknet());
			}
			return (Worldlinknet) WorldpowernetMap.get(world);
		}

		return null;
	}
}