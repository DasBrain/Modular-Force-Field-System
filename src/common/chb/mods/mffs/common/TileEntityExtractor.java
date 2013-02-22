package chb.mods.mffs.common;


import java.util.LinkedList;
import java.util.List;

import chb.mods.mffs.network.INetworkHandlerListener;
import chb.mods.mffs.network.NetworkHandler;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityExtractor extends TileEntityMachines implements ISidedInventory
,INetworkHandlerListener{
	

	private ItemStack inventory[];
	
    private boolean create;
	private int Extractor_ID;
	protected int WorkEnergy;
	protected int MaxWorkEnergy;
	private int ForceEnergybuffer;
	private int MaxForceEnergyBuffer;
	private int WorkCylce;
	private int LinkCapacitor_ID;
	private int workTicker;
	private int workdone;
	private int maxworkcylce;
	
	
	public TileEntityExtractor() {
		
		inventory = new ItemStack[5];
		create = true;
		Extractor_ID = 0;
		LinkCapacitor_ID = 0;
		WorkEnergy = 0;
		MaxWorkEnergy = 4000;
		ForceEnergybuffer = 0;
		MaxForceEnergyBuffer = 1000000;
		WorkCylce = 0;
		workTicker = 20;
		maxworkcylce = 125;
	}
	
	
	
	
	
	public int getMaxworkcylce() {
		return maxworkcylce;
	}

	public void setMaxworkcylce(int maxworkcylce) {
		this.maxworkcylce = maxworkcylce;
	}

	public int getWorkdone() {
		return workdone;
	}

	public void setWorkdone(int workdone) {
		this.workdone = workdone;
		NetworkHandler.updateTileEntityField(this,"workdone");
	}

	
	public int getWorkTicker() {
		return workTicker;
	}


	public void setWorkTicker(int workTicker) {
		this.workTicker = workTicker;
	}




	public int getLinkCapacitors_ID() {
		return LinkCapacitor_ID;
	}
	
	public void setLinkCapacitor_ID(int id){
		this.LinkCapacitor_ID = id;
	}
	
	public int getExtractor_ID() {
		return Extractor_ID;
	}




	public int getMaxForceEnergyBuffer() {
		return MaxForceEnergyBuffer;
	}



	public void setMaxForceEnergyBuffer(int maxForceEnergyBuffer) {
		MaxForceEnergyBuffer = maxForceEnergyBuffer;
	}
	
	public int getForceEnergybuffer() {
		return ForceEnergybuffer;
	}



	public void setForceEnergybuffer(int forceEnergybuffer) {
		ForceEnergybuffer = forceEnergybuffer;
		NetworkHandler.updateTileEntityField(this,"ForceEnergybuffer");
	}



	public void setWorkCylce(int i)
	{
		this.WorkCylce = i;
		NetworkHandler.updateTileEntityField(this,"WorkCylce");
	}
	
	public int getWorkCylce(){
		return WorkCylce;
	}
	
	public int getWorkEnergy() {
		return WorkEnergy;
	}

	public void setWorkEnergy(int workEnergy) {
		WorkEnergy = workEnergy;
	}

	public int getMaxWorkEnergy() {
		return MaxWorkEnergy;
	}


	public void setMaxWorkEnergy(int maxWorkEnergy) {
		MaxWorkEnergy = maxWorkEnergy;
	}
	
	
	public void addtogrid() {
		
		if (Extractor_ID == 0) {
			Extractor_ID = Linkgrid.getWorldMap(worldObj)
					.newID(this);
		}
		Linkgrid.getWorldMap(worldObj).getExtractor().put(Extractor_ID, this);
	}
	

	public void removefromgrid() {
		Linkgrid.getWorldMap(worldObj).getExtractor().remove(getExtractor_ID());
		dropplugins();
	}
	
	public void dropplugins() {
		for (int a = 0; a < this.inventory.length; a++) {
			dropplugins(a,this);
		}
	}
	
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D,
					(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}
	
	
	public void checkslots(boolean init) {
		if (getStackInSlot(1) != null) {
			if (getStackInSlot(1).getItem() == ModularForceFieldSystem.MFFSitemfc) {
				if (getLinkCapacitors_ID() != NBTTagCompoundHelper.getTAGfromItemstack(
						getStackInSlot(1)).getInteger("CapacitorID")) {
					setLinkCapacitor_ID(NBTTagCompoundHelper.getTAGfromItemstack(
							getStackInSlot(1)).getInteger("CapacitorID"));
				}

				if (Linkgrid.getWorldMap(worldObj).getCapacitor()
						.get(this.getLinkCapacitors_ID()) != null) {
					int transmit = Linkgrid.getWorldMap(worldObj)
							.getCapacitor().get(this.getLinkCapacitors_ID())
							.getTransmitRange();
					int gen_x = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitors_ID()).xCoord
							- this.xCoord;
					int gen_y = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitors_ID()).yCoord
							- this.yCoord;
					int gen_z = Linkgrid.getWorldMap(worldObj).getCapacitor()
							.get(this.getLinkCapacitors_ID()).zCoord
							- this.zCoord;

					if (Math.sqrt(gen_x * gen_x + gen_y * gen_y + gen_z * gen_z) <= transmit) {

					} else {
						setLinkCapacitor_ID(0);
					}
				} else {
					setLinkCapacitor_ID(0);
					if (!init) {
						this.setInventorySlotContents(1, new ItemStack(ModularForceFieldSystem.MFFSitemcardempty));
					}
				}
			} else {
				if (getStackInSlot(1).getItem() != ModularForceFieldSystem.MFFSitemcardempty) {
					dropplugins(1,this);
				}
			}
		} else {
		    this.setLinkCapacitor_ID(0);
		}
		
		if (getStackInSlot(2) != null) {
			if (getStackInSlot(2).getItem() == ModularForceFieldSystem.MFFSitemupgradecapcap) {
				
				setMaxForceEnergyBuffer(1000000 + (getStackInSlot(2).stackSize * 100000));
				
			 }else{
				 setMaxForceEnergyBuffer(1000000);
				 dropplugins(2,this);
			 }
			}else{
				setMaxForceEnergyBuffer(1000000);
			}
		
		if (getStackInSlot(3) != null) {
			if (getStackInSlot(3).getItem() == ModularForceFieldSystem.MFFSitemupgradeexctractorboost) {
		
				setWorkTicker(20 - getStackInSlot(3).stackSize);
			
			
				
			}else{
				setWorkTicker(20);
				dropplugins(3,this);
			}
		}else{
			setWorkTicker(20);
		}
		
	}
	
	
	private boolean hasPowertoConvert()
	{
		if(WorkEnergy >= MaxWorkEnergy-1)
		{
		 setWorkEnergy(0);
		 return true;
		}
		return false;
	}
	
	private boolean hasfreeForceEnergyStorage()
	{
		if(this.MaxForceEnergyBuffer > this.ForceEnergybuffer)
		 return true;
		return false;
	}
	
	
	private boolean hasStufftoConvert()
	{
		if (WorkCylce > 0)
		{
			return true;
			
		}else{
		
		if (getStackInSlot(0) != null) {
				if (getStackInSlot(0).getItem() == ModularForceFieldSystem.MFFSitemForcicium) {
		    	  setMaxworkcylce(ModularForceFieldSystem.ForceciumWorkCylce);
				  setWorkCylce(getMaxworkcylce());
			      decrStackSize(0, 1);
			      return true;
				}
				
		
			
				if (inventory[0].itemID == ModularForceFieldSystem.MFFSForciciumBlock.blockID ){
			    	  setMaxworkcylce(ModularForceFieldSystem.ForceciumBlockWorkCylce);
					  setWorkCylce(getMaxworkcylce());
				      decrStackSize(0, 1);
				      return true;
					}
			}
		}
		
		return false;
	}
	
	public void transferForceEnergy()
	{
		if(this.getForceEnergybuffer() > 0)
		{	
		if(LinkCapacitor_ID!=0)
		{
			TileEntityCapacitor RemoteCap =Linkgrid.getWorldMap(worldObj).getCapacitor().get(LinkCapacitor_ID);	
			if(RemoteCap != null)
			{
			      int maxtrasferrate = ModularForceFieldSystem.ExtractorPassForceEnergyGenerate*2;
			      int forceenergyspace = RemoteCap.getMaxForcePower() - RemoteCap.getForcePower();
			      
				  if(this.getForceEnergybuffer() > maxtrasferrate)
				  {
					    if(forceenergyspace > maxtrasferrate)
					    {
					    	RemoteCap.setForcePower(RemoteCap.getForcePower() + maxtrasferrate);
			                this.setForceEnergybuffer(this.getForceEnergybuffer() - maxtrasferrate);		    
					    }else{
					    	RemoteCap.setForcePower(RemoteCap.getForcePower() + forceenergyspace);
			                this.setForceEnergybuffer(this.getForceEnergybuffer() - forceenergyspace);	
					    }
		                
				  }else{
					  
					    if(forceenergyspace > this.getForceEnergybuffer())
					    {
					    	RemoteCap.setForcePower(RemoteCap.getForcePower() + this.getForceEnergybuffer());
			                this.setForceEnergybuffer(this.getForceEnergybuffer() - this.getForceEnergybuffer());		    
					    }else{
					    	RemoteCap.setForcePower(RemoteCap.getForcePower() + forceenergyspace);
			                this.setForceEnergybuffer(this.getForceEnergybuffer() - forceenergyspace);	
					    }
					  
					  
				  }
			}
		}
		}
	}
	
	public void converMJtoWorkEnergy(){}
	public void converUEtoWorkEnergy(){}
	
	public void updateEntity() {
		if (worldObj.isRemote == false) {
			
			
			if (create && this.getLinkCapacitors_ID() != 0) {
				addtogrid();
				checkslots(true);
				create = false;
			}

		
			if (this.getTicker() >= getWorkTicker()) {
				
				converMJtoWorkEnergy();
				converUEtoWorkEnergy();	
				
				if(this.getWorkdone() != getWorkEnergy() * 100 / getMaxWorkEnergy())
				setWorkdone( getWorkEnergy() * 100 / getMaxWorkEnergy());
				
				checkslots(false);
				if(this.hasfreeForceEnergyStorage() && this.hasStufftoConvert())
				{
							
				 if (isActive() != true) {
					 setActive(true);
					}

					if(this.hasPowertoConvert()){
						
						  setWorkCylce(getWorkCylce()-1);
						  setForceEnergybuffer(getForceEnergybuffer()+ ModularForceFieldSystem.ExtractorPassForceEnergyGenerate);	
					}
				}else{
					
					if (isActive() != false) {
						setActive(false);
					}
					
				}
			
				transferForceEnergy();
				
				this.setTicker((short) 0);
			}
		
			this.setTicker((short) (this.getTicker() + 1));
			
				

		}
	}
	
	
	@Override
	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerForceEnergyExtractor(inventoryplayer.player,this);
	}

	

	
	
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		 Extractor_ID = nbttagcompound.getInteger("Extractor_ID");
		 ForceEnergybuffer = nbttagcompound.getInteger("ForceEnergybuffer");
		 WorkEnergy = nbttagcompound.getInteger("WorkEnergy");
		 WorkCylce = nbttagcompound.getInteger("WorkCylce");
		 
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < inventory.length) {
				inventory[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}


	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("WorkCylce", WorkCylce);
		nbttagcompound.setInteger("WorkEnergy", WorkEnergy);
		nbttagcompound.setInteger("ForceEnergybuffer",ForceEnergybuffer);
		nbttagcompound.setInteger("Extractor_ID", Extractor_ID);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}
	
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public String getInvName() {
		return "Extractor";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getSizeInventory() {
		return inventory.length;
	}
	
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}
	
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}
	
	
	public ItemStack[] getContents() {
		return inventory;
	}
	
	@Override
	public void openChest() {
	}

	@Override
	public void closeChest(){ 
	}





	@Override
	public void onNetworkHandlerUpdate(String field) {
		if (field.equals("side")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("active")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("WorkCylce")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("ForceEnergybuffer")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
		if (field.equals("workdone")) {
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
	}





	@Override
	public List<String> geFieldsforUpdate() {
		List<String> NetworkedFields = new LinkedList<String>();
		NetworkedFields.clear();

		NetworkedFields.add("active");
		NetworkedFields.add("side");
		NetworkedFields.add("ForceEnergybuffer");
		NetworkedFields.add("WorkCylce");
		NetworkedFields.add("WorkEnergy");
		NetworkedFields.add("workdone");

		return NetworkedFields;
	}
	
	  @Override
	  public Packet getDescriptionPacket() {
	    return NetworkHandler.requestInitialData(this);
	  }



}
