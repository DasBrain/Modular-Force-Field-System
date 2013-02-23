/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SecurityRight
/*    */ {
/* 32 */   public static Map rights = new HashMap();
/*    */   public final String rightKey;
/*    */   public final String name;
/*    */   public final String description;
/* 36 */   public String texture = "/chb/mods/mffs/sprites/AdvSecStationButtons.png";
/*    */   public final int texIndex;
/* 39 */   public static SecurityRight FFB = new SecurityRight("FFB", "ForceField Bypass", "", 0);
/* 40 */   public static SecurityRight EB = new SecurityRight("EB", "Edit MFFS Block", "", 1);
/* 41 */   public static SecurityRight CSR = new SecurityRight("CSR", "Config Security Rights", "", 2);
/* 42 */   public static SecurityRight SR = new SecurityRight("SR", "Stay in Area", "", 3);
/* 43 */   public static SecurityRight OSS = new SecurityRight("OSS", "Open Secure Storage", "", 4);
/* 44 */   public static SecurityRight RPB = new SecurityRight("RPB", "Change Protected Block", "", 5);
/* 45 */   public static SecurityRight AAI = new SecurityRight("AAI", "Allow have all Items", "", 6);
/* 46 */   public static SecurityRight UCS = new SecurityRight("UCS", "Use Control System", "", 7);
/*    */ 
/*    */   public SecurityRight(String ID, String name, String description, int txIndex) {
/* 49 */     this.rightKey = ID;
/* 50 */     this.name = name;
/* 51 */     this.description = description;
/* 52 */     this.texIndex = txIndex;
/* 53 */     rights.put(ID, this);
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.SecurityRight
 * JD-Core Version:    0.6.2
 */