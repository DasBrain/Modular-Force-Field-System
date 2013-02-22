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

import java.util.LinkedList;

import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

public class TileEntityExtractorBC extends TileEntityExtractor implements IPowerReceptor,IOverrideDefaultTriggers{

	private IPowerProvider powerProvider;

public TileEntityExtractorBC(){
	
	super();
	
	powerProvider = PowerFramework.currentFramework.createPowerProvider();
	powerProvider.configure(10, 2, (int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5));
	
}
	
	
	@Override
	public void converMJtoWorkEnergy(){
		
		if(this.getWorkEnergy() < this.getMaxWorkEnergy())
		{
	      float use = powerProvider.useEnergy(1, (float) (this.getMaxWorkEnergy() - this.getWorkEnergy() / 2.5), true);
		  
	      if(getWorkEnergy() + (use *2.5) > super.getMaxWorkEnergy())
	      {
	    	     setWorkEnergy(super.getMaxWorkEnergy()); 
	      }else{
	             setWorkEnergy((int) (getWorkEnergy() + (use *2.5)));
	      }
		  

		}
		
	}
	
	@Override
	public void setPowerProvider(IPowerProvider provider) {
		this.powerProvider = provider;
	}

	@Override
	public IPowerProvider getPowerProvider() {
		return powerProvider;
	}


	@Override
	public void doWork() {}


	@Override
	public int powerRequest() {
		
		double workEnergyinMJ = super.getWorkEnergy()  / 2.5;
		double MaxWorkEnergyinMj = super.getMaxWorkEnergy()  / 2.5;
		
		return (int) Math.round(MaxWorkEnergyinMj - workEnergyinMJ) ;
	}

	@Override
	public LinkedList<ITrigger> getTriggers() {
		return null;
	}
	
}
