/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityAreaDefenseStation;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityControlSystem;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityConverter;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityExtractor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class SecurityHelper
/*     */ {
/*     */   public static boolean isAccessGranted(TileEntity tileEntity, EntityPlayer entityplayer, World world, SecurityRight right)
/*     */   {
/*  47 */     return isAccessGranted(tileEntity, entityplayer, world, right, false);
/*     */   }
/*     */ 
/*     */   public static boolean isAccessGranted(TileEntity tileEntity, EntityPlayer entityplayer, World world, SecurityRight right, boolean suppresswarning)
/*     */   {
/*  57 */     if ((tileEntity instanceof TileEntitySecStorage))
/*     */     {
/*  60 */       TileEntityAdvSecurityStation sec = ((TileEntitySecStorage)tileEntity).getLinkedSecurityStation();
/*     */ 
/*  62 */       if (sec != null) {
/*  63 */         if (sec.isAccessGranted(entityplayer.username, right)) {
/*  64 */           return true;
/*     */         }
/*     */ 
/*  67 */         if (!suppresswarning)
/*  68 */           entityplayer.sendChatToPlayer("[Field Security] Fail: access denied");
/*  69 */         return false;
/*     */       }
/*     */ 
/*  72 */       if (world.isRemote) {
/*  73 */         return false;
/*     */       }
/*  75 */       return true;
/*     */     }
/*     */ 
/*  82 */     if ((tileEntity instanceof TileEntityControlSystem))
/*     */     {
/*  84 */       TileEntityAdvSecurityStation sec = ((TileEntityControlSystem)tileEntity).getLinkedSecurityStation();
/*  85 */       if (sec != null) {
/*  86 */         if (sec.isAccessGranted(entityplayer.username, right)) {
/*  87 */           return true;
/*     */         }
/*     */ 
/*  90 */         if (!suppresswarning)
/*  91 */           entityplayer.sendChatToPlayer("[Field Security] Fail: access denied");
/*  92 */         return false;
/*     */       }
/*     */ 
/*  95 */       if (world.isRemote) {
/*  96 */         return false;
/*     */       }
/*  98 */       return true;
/*     */     }
/*     */ 
/* 102 */     if ((tileEntity instanceof TileEntityAdvSecurityStation))
/*     */     {
/* 104 */       if (!((TileEntityAdvSecurityStation)tileEntity).isAccessGranted(entityplayer.username, right))
/*     */       {
/* 107 */         if (!suppresswarning) {
/* 108 */           Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */         }
/* 110 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 117 */     if ((tileEntity instanceof TileEntityConverter))
/*     */     {
/* 119 */       TileEntityAdvSecurityStation sec = ((TileEntityConverter)tileEntity).getLinkedSecurityStation();
/* 120 */       if (sec != null)
/*     */       {
/* 122 */         if (sec.isAccessGranted(entityplayer.username, right))
/*     */         {
/* 124 */           return true;
/*     */         }
/* 126 */         if (!suppresswarning) {
/* 127 */           Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */         }
/* 129 */         return false;
/*     */       }
/*     */ 
/* 133 */       return true;
/*     */     }
/*     */ 
/* 138 */     if ((tileEntity instanceof TileEntityCapacitor))
/*     */     {
/* 140 */       TileEntityAdvSecurityStation sec = ((TileEntityCapacitor)tileEntity).getLinkedSecurityStation();
/* 141 */       if (sec != null)
/*     */       {
/* 143 */         if (sec.isAccessGranted(entityplayer.username, right))
/*     */         {
/* 145 */           return true;
/*     */         }
/* 147 */         if (!suppresswarning) {
/* 148 */           Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */         }
/* 150 */         return false;
/*     */       }
/*     */ 
/* 154 */       return true;
/*     */     }
/*     */ 
/* 161 */     if ((tileEntity instanceof TileEntityExtractor))
/*     */     {
/* 163 */       TileEntityAdvSecurityStation sec = ((TileEntityExtractor)tileEntity).getLinkedSecurityStation();
/* 164 */       if (sec != null)
/*     */       {
/* 166 */         if (sec.isAccessGranted(entityplayer.username, right))
/*     */         {
/* 168 */           return true;
/*     */         }
/* 170 */         if (!suppresswarning) {
/* 171 */           Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */         }
/* 173 */         return false;
/*     */       }
/*     */ 
/* 178 */       return true;
/*     */     }
/*     */ 
/* 183 */     if ((tileEntity instanceof TileEntityAreaDefenseStation))
/*     */     {
/* 185 */       TileEntityAdvSecurityStation sec = ((TileEntityAreaDefenseStation)tileEntity).getLinkedSecurityStation();
/*     */ 
/* 187 */       if (sec != null)
/*     */       {
/* 189 */         if (sec.isAccessGranted(entityplayer.username, right))
/*     */         {
/* 191 */           return true;
/*     */         }
/* 193 */         if (!suppresswarning) {
/* 194 */           Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */         }
/* 196 */         return false;
/*     */       }
/*     */ 
/* 201 */       return true;
/*     */     }
/*     */ 
/* 207 */     if ((tileEntity instanceof TileEntityProjector))
/*     */     {
/* 210 */       switch (((TileEntityProjector)tileEntity).getaccesstyp())
/*     */       {
/*     */       case 2:
/* 213 */         TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(((TileEntityProjector)tileEntity).getPowerSourceID()));
/* 214 */         if (cap != null)
/*     */         {
/* 216 */           TileEntityAdvSecurityStation sec = cap.getLinkedSecurityStation();
/* 217 */           if (sec != null)
/*     */           {
/* 219 */             if (sec.isAccessGranted(entityplayer.username, right))
/*     */             {
/* 221 */               return true;
/*     */             }
/* 223 */             if (!suppresswarning) {
/* 224 */               Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */             }
/* 226 */             return false;
/*     */           }
/*     */         }
/*     */ 
/* 230 */         break;
/*     */       case 3:
/* 236 */         TileEntityAdvSecurityStation sec = ((TileEntityProjector)tileEntity).getLinkedSecurityStation();
/* 237 */         if (sec != null)
/*     */         {
/* 239 */           if (sec.isAccessGranted(entityplayer.username, right))
/*     */           {
/* 241 */             return true;
/*     */           }
/* 243 */           if (!suppresswarning) {
/* 244 */             Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */           }
/* 246 */           return false;
/*     */         }
/*     */ 
/*     */         break;
/*     */       }
/*     */ 
/* 253 */       return true;
/*     */     }
/*     */ 
/* 257 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.SecurityHelper
 * JD-Core Version:    0.6.2
 */