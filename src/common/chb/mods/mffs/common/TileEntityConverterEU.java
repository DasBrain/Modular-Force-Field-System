
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

import net.minecraft.src.Block;
import net.minecraft.src.TileEntity;
import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergyAcceptor;
import ic2.api.IEnergySource;
import ic2.api.Items;



public class TileEntityConverterEU extends TileEntityConverter
    implements IEnergySource {

    private boolean addedToEnergyNet;

    public TileEntityConverterEU() {
        addedToEnergyNet = false;
    }

    public void updateEntity() {
        if(!addedToEnergyNet) {
        	EnergyNet.getForWorld(worldObj).addTileEntity(this);
            addedToEnergyNet = true;
        }

        super.updateEntity();
    }

    public void Emitpower() {
        if(super.getLinkPower() > (ModularForceFieldSystem.ExtractorPassForceEnergyGenerate / 4000) * super.getOutput()) {
            int a = EnergyNet.getForWorld(worldObj).emitEnergyFrom(((IEnergySource) (this)), super.getOutput());
            TileEntityCapacitor powercource = (TileEntityCapacitor)Linkgrid.getWorldMap(worldObj).getCapacitor().get(((Object) (Integer.valueOf(super.getLinkCapacitors_ID()))));

            if(powercource != null)
                powercource.setForcePower(powercource.getForcePower() - (ModularForceFieldSystem.ExtractorPassForceEnergyGenerate / 4000) * (super.getOutput() - a));
            else
                System.out.println("[MFFS ERROR]Linked Capacitor not found");
        }
    }

    @Override
    public void invalidate() {
    	if (addedToEnergyNet) {
    		EnergyNet.getForWorld(worldObj).removeTileEntity(this);
    		addedToEnergyNet = false;
    	}

    	super.invalidate();
    }

    public boolean isAddedToEnergyNet() {
        return addedToEnergyNet;
    }



    public int getMaxEnergyOutput() {
        return Integer.MAX_VALUE;
    }

    public void checkslots(boolean init) {
        if(super.getStackInSlot(1) != null) {
        	if(super.getStackInSlot(1).itemID<255)
        	{
        	if(Block.blocksList[super.getStackInSlot(1).itemID] == Block.blocksList[Items.getItem("lvTransformer").itemID])
        	{
            if(super.getStackInSlot(1).getItemDamage() == 3) //lvTransformer
                super.setOutput(32);

            if(super.getStackInSlot(1).getItemDamage() == 4) // mvTransformer
                super.setOutput(128);

            if(super.getStackInSlot(1).getItemDamage() == 5) // hvTransformer
                super.setOutput(512);
        	}else {
                super.setOutput(1);
            }
        } else {
            super.setOutput(1);
        }
    }else {
        super.setOutput(1);
    }
        super.checkslots(init);
    }

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		return  receiver instanceof IEnergyAcceptor;
	}


}
