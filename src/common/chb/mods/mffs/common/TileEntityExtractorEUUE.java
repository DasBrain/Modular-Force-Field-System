package chb.mods.mffs.common;

import java.util.LinkedList;

import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;
import universalelectricity.electricity.ElectricityManager;
import universalelectricity.implement.IElectricityReceiver;
import universalelectricity.implement.IElectricityStorage;


public class TileEntityExtractorEUUE extends TileEntityExtractor implements 
IEnergySink,IElectricityReceiver,IElectricityStorage{

	
	private double wattHours = 0;
	private boolean addedToEnergyNet;
	
public 	TileEntityExtractorEUUE()
{
	super();
	addedToEnergyNet = false;
	ElectricityManager.instance.registerElectricUnit(this);
}

@Override
public void updateEntity() {
	
	if (!addedToEnergyNet) {
		EnergyNet.getForWorld(worldObj).addTileEntity(this);
		addedToEnergyNet = true;
		}
	super.updateEntity();

}

@Override
public boolean demandsEnergy() {
	if(this.getWorkEnergy()< this.MaxWorkEnergy)
	{
		return true;
	}
	
	return false;
}


@Override
public int injectEnergy(Direction directionFrom, int amount) {
if(this.getWorkEnergy()< this.MaxWorkEnergy)
{

if((MaxWorkEnergy - WorkEnergy) > amount)
{
WorkEnergy = WorkEnergy + amount;
return 0;
}else{

WorkEnergy = WorkEnergy + (MaxWorkEnergy - WorkEnergy);
return 0 ; //amount- (MaxWorkEnergy - WorkEnergy);

}
}
return amount;
}

@Override
public void invalidate() {
	if (addedToEnergyNet) {
		EnergyNet.getForWorld(worldObj).removeTileEntity(this);
		addedToEnergyNet = false;
	}

	super.invalidate();
}

@Override
public boolean isAddedToEnergyNet() {
	return addedToEnergyNet;
}

@Override
public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction) {
	return true;
}

// EU end


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
public double wattRequest() {
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
