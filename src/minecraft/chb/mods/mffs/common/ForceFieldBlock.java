package chb.mods.mffs.common;

public class ForceFieldBlock
{
  public int typ;
  public int Projektor_ID;
  public int Generator_Id;

  public ForceFieldBlock(int Generator_Id, int Projektor_ID, int typ)
  {
    this.Projektor_ID = Projektor_ID;
    this.Generator_Id = Generator_Id;
    this.typ = typ;
  }

  public String toString()
  {
    return this.Projektor_ID + "-" + this.Generator_Id + "-" + this.typ;
  }
}