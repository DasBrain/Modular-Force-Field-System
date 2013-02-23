/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import net.minecraft.util.DamageSource;
/*    */ 
/*    */ public class MFFSDamageSource extends DamageSource
/*    */ {
/* 30 */   public static DamageSource fieldShock = new MFFSDamageSource("fieldShock").setDamageBypassesArmor();
/* 31 */   public static DamageSource areaDefense = new MFFSDamageSource("areaDefense").setDamageBypassesArmor();
/* 32 */   public static DamageSource fieldDefense = new MFFSDamageSource("fieldDefense").setDamageBypassesArmor();
/*    */ 
/*    */   public MFFSDamageSource(String dmgId) {
/* 35 */     super(dmgId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.MFFSDamageSource
 * JD-Core Version:    0.6.2
 */