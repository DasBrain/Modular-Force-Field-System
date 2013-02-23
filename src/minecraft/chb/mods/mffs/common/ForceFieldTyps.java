package chb.mods.mffs.common;

public enum ForceFieldTyps
{
  Camouflage(2), 
  Default(1), 
  Zapper(3), 
  Area(1), 
  Containment(1);

  int costmodi;

  private ForceFieldTyps(int costmodi)
  {
    this.costmodi = costmodi;
  }
}