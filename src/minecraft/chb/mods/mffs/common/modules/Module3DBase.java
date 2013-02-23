package chb.mods.mffs.common.modules;

import chb.mods.mffs.common.ForceFieldTyps;
import chb.mods.mffs.common.IModularProjector;
import java.util.Set;

public abstract class Module3DBase extends ModuleBase
{
  public Module3DBase(int i)
  {
    super(i);
  }

  public void calculateField(IModularProjector projector, Set points)
  {
  }

  public abstract void calculateField(IModularProjector paramIModularProjector, Set paramSet1, Set paramSet2);

  public ForceFieldTyps getForceFieldTyps()
  {
    if ((this instanceof ItemProjectorModuleContainment)) {
      return ForceFieldTyps.Containment;
    }
    return ForceFieldTyps.Area;
  }
}