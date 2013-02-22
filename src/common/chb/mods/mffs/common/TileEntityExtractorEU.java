package chb.mods.mffs.common;

import net.minecraft.src.TileEntity;
import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;

public class TileEntityExtractorEU extends TileEntityExtractor implements IEnergySink{

	private boolean addedToEnergyNet;
	
	public TileEntityExtractorEU(){
		
		super();
		addedToEnergyNet = false;

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
}
