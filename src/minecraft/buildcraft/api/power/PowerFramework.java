package buildcraft.api.power;

import net.minecraft.nbt.NBTTagCompound;

public abstract class PowerFramework
{
  private static String baseNBTName = "net.minecraft.src.buildcarft.Power";
  public static PowerFramework currentFramework;

  public abstract IPowerProvider createPowerProvider();

  public void loadPowerProvider(IPowerReceptor receptor, NBTTagCompound compound)
  {
    IPowerProvider provider = createPowerProvider();

    if (compound.hasKey(baseNBTName)) {
      NBTTagCompound cpt = compound.getCompoundTag(baseNBTName);
      if (cpt.getString("class").equals(getClass().getName())) {
        provider.readFromNBT(cpt.getCompoundTag("contents"));
      }
    }

    receptor.setPowerProvider(provider);
  }

  public void savePowerProvider(IPowerReceptor receptor, NBTTagCompound compound)
  {
    IPowerProvider provider = receptor.getPowerProvider();

    if (provider == null) {
      return;
    }

    NBTTagCompound cpt = new NBTTagCompound();

    cpt.setString("class", getClass().getName());

    NBTTagCompound contents = new NBTTagCompound();

    provider.writeToNBT(contents);

    cpt.setTag("contents", contents);
    compound.setTag(baseNBTName, cpt);
  }
}