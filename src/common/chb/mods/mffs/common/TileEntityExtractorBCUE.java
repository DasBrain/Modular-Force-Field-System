package chb.mods.mffs.common;

import java.util.LinkedList;

import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.electricity.ElectricityManager;
import universalelectricity.implement.IElectricityReceiver;
import universalelectricity.implement.IElectricityStorage;
import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

public class TileEntityExtractorBCUE extends TileEntityExtractor implements 
IPowerReceptor,IOverrideDefaultTriggers,IElectricityReceiver,IElectricityStorage{

	private IPowerProvider powerProvider;
	private double wattHours = 0;
	
public 	TileEntityExtractorBCUE()
{
	super();
	ElectricityManager.instance.registerElectricUnit(this);
	powerProvider = PowerFramework.currentFramework.createPowerProvider();
	powerProvider.configure(10, 2, (int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5),(int) (super.getMaxWorkEnergy() / 2.5));
	
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

// End UE

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
