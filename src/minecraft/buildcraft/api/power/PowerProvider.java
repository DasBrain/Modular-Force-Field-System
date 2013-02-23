package buildcraft.api.power;

import buildcraft.api.core.SafeTimeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class PowerProvider
  implements IPowerProvider
{
  protected int latency;
  protected int minEnergyReceived;
  protected int maxEnergyReceived;
  protected int maxEnergyStored;
  protected int minActivationEnergy;
  protected float energyStored = 0.0F;

  protected int powerLoss = 1;
  protected int powerLossRegularity = 100;

  public SafeTimeTracker timeTracker = new SafeTimeTracker();
  public SafeTimeTracker energyLossTracker = new SafeTimeTracker();

  public int[] powerSources = { 0, 0, 0, 0, 0, 0 };

  public SafeTimeTracker getTimeTracker() { return this.timeTracker; } 
  public int getLatency() {
    return this.latency; } 
  public int getMinEnergyReceived() { return this.minEnergyReceived; } 
  public int getMaxEnergyReceived() { return this.maxEnergyReceived; } 
  public int getMaxEnergyStored() { return this.maxEnergyStored; } 
  public int getActivationEnergy() { return this.minActivationEnergy; } 
  public float getEnergyStored() { return this.energyStored; }

  public void configure(int latency, int minEnergyReceived, int maxEnergyReceived, int minActivationEnergy, int maxStoredEnergy)
  {
    this.latency = latency;
    this.minEnergyReceived = minEnergyReceived;
    this.maxEnergyReceived = maxEnergyReceived;
    this.maxEnergyStored = maxStoredEnergy;
    this.minActivationEnergy = minActivationEnergy;
  }

  public void configurePowerPerdition(int powerLoss, int powerLossRegularity)
  {
    this.powerLoss = powerLoss;
    this.powerLossRegularity = powerLossRegularity;
  }

  public boolean update(IPowerReceptor receptor)
  {
    if (!preConditions(receptor)) {
      return false;
    }

    TileEntity tile = (TileEntity)receptor;
    boolean result = false;

    if (this.energyStored >= this.minActivationEnergy) {
      if (this.latency == 0) {
        receptor.doWork();
        result = true;
      }
      else if (this.timeTracker.markTimeIfDelay(tile.worldObj, this.latency)) {
        receptor.doWork();
        result = true;
      }

    }

    if ((this.powerLoss > 0) && (this.energyLossTracker.markTimeIfDelay(tile.worldObj, this.powerLossRegularity)))
    {
      this.energyStored -= this.powerLoss;
      if (this.energyStored < 0.0F) {
        this.energyStored = 0.0F;
      }
    }

    for (int i = 0; i < 6; i++) {
      if (this.powerSources[i] > 0) {
        this.powerSources[i] -= 1;
      }
    }

    return result;
  }

  public boolean preConditions(IPowerReceptor receptor)
  {
    return true;
  }

  public float useEnergy(float min, float max, boolean doUse)
  {
    float result = 0.0F;

    if (this.energyStored >= min) {
      if (this.energyStored <= max) {
        result = this.energyStored;
        if (doUse)
          this.energyStored = 0.0F;
      }
      else {
        result = max;
        if (doUse) {
          this.energyStored -= max;
        }
      }
    }

    return result;
  }

  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    this.latency = nbttagcompound.getInteger("latency");
    this.minEnergyReceived = nbttagcompound.getInteger("minEnergyReceived");
    this.maxEnergyReceived = nbttagcompound.getInteger("maxEnergyReceived");
    this.maxEnergyStored = nbttagcompound.getInteger("maxStoreEnergy");
    this.minActivationEnergy = nbttagcompound.getInteger("minActivationEnergy");
    try
    {
      this.energyStored = nbttagcompound.getFloat("storedEnergy");
    } catch (Throwable c) {
      this.energyStored = 0.0F;
    }
  }

  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setInteger("latency", this.latency);
    nbttagcompound.setInteger("minEnergyReceived", this.minEnergyReceived);
    nbttagcompound.setInteger("maxEnergyReceived", this.maxEnergyReceived);
    nbttagcompound.setInteger("maxStoreEnergy", this.maxEnergyStored);
    nbttagcompound.setInteger("minActivationEnergy", this.minActivationEnergy);
    nbttagcompound.setFloat("storedEnergy", this.energyStored);
  }

  public void receiveEnergy(float quantity, ForgeDirection from)
  {
    this.powerSources[from.ordinal()] = 2;

    this.energyStored += quantity;

    if (this.energyStored > this.maxEnergyStored)
      this.energyStored = this.maxEnergyStored;
  }

  public boolean isPowerSource(ForgeDirection from)
  {
    return this.powerSources[from.ordinal()] != 0;
  }
}