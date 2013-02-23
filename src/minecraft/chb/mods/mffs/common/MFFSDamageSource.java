package chb.mods.mffs.common;

import net.minecraft.util.DamageSource;

public class MFFSDamageSource extends DamageSource
{
  public static DamageSource fieldShock = new MFFSDamageSource("fieldShock").setDamageBypassesArmor();
  public static DamageSource areaDefense = new MFFSDamageSource("areaDefense").setDamageBypassesArmor();
  public static DamageSource fieldDefense = new MFFSDamageSource("fieldDefense").setDamageBypassesArmor();

  public MFFSDamageSource(String dmgId) {
    super(dmgId);
  }
}