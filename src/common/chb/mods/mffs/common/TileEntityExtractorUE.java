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

import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.electricity.ElectricityManager;
import universalelectricity.implement.IElectricityReceiver;
import universalelectricity.implement.IElectricityStorage;

public class TileEntityExtractorUE extends TileEntityExtractor implements IElectricityReceiver,IElectricityStorage{

	private double wattHours = 0;
	

public TileEntityExtractorUE(){
	
	
	super();
	ElectricityManager.instance.registerElectricUnit(this);
	

}
	
	@Override
	public void converUEtoWorkEnergy(){
		
		if(this.getWorkEnergy() < this.getMaxWorkEnergy())
		{
		  
	      if(getWorkEnergy() + (wattHours/30) > super.getMaxWorkEnergy())
	      {
	    	     setWorkEnergy(super.getMaxWorkEnergy()); 
	    	     wattHours = 0;
	      }else{
	             setWorkEnergy((int) (getWorkEnergy() + (wattHours/30)));
	             wattHours = 0;
	      }
		  

		}
	
	}

	@Override
	public void onDisable(int duration) {
		}


	@Override
	public boolean isDisabled() {
		return false;
	}


	@Override
	public boolean canConnect(ForgeDirection side) {
		return true;
	}


	@Override
	public double getVoltage() {
		return 120;
	}


	@Override
	public void onReceive(TileEntity sender, double amps, double voltage,
			ForgeDirection side) {
	
		wattHours = wattHours +(amps*voltage);
		
	}	


	@Override
	public double wattRequest(){
		return Math.ceil(this.getMaxWattHours() - this.wattHours);
	}


	@Override
	public boolean canReceiveFromSide(ForgeDirection side) {
		return true;
	}

	@Override
	public double getWattHours(Object... data) {
		return wattHours;
	}

	@Override
	public void setWattHours(double wattHours, Object... data) {
		this.wattHours = wattHours;
	}

	@Override
	public double getMaxWattHours() {
		return 120000;
	}
}
