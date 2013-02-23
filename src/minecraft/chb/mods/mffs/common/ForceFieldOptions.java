/*    */ package chb.mods.mffs.common;
/*    */ 
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public final class ForceFieldOptions
/*    */ {
/*    */   public static boolean BlockProtected(World world, int x, int y, int z, EntityPlayer entityplayer)
/*    */   {
/* 40 */     Map ProjectorinrangeMap = Linkgrid.getWorldMap(world).getProjektor();
/* 41 */     for (Iterator i$ = ProjectorinrangeMap.values().iterator(); i$.hasNext(); ) { tileentity = (TileEntityProjector)i$.next();
/*    */ 
/* 43 */       int dx = tileentity.xCoord - x;
/* 44 */       int dy = tileentity.yCoord - y;
/* 45 */       int dz = tileentity.zCoord - z;
/*    */ 
/* 47 */       int dist = (int)Math.round(Math.sqrt(dx * dx + dy * dy + dz * dz));
/*    */ 
/* 50 */       if ((dist <= 64) && (tileentity.isActive()) && (tileentity.getProjektor_Typ() != 1) && (tileentity.getProjektor_Typ() != 2))
/*    */       {
/* 53 */         Map InnerMap = null;
/* 54 */         InnerMap = Linkgrid.getWorldMap(world).getProjektor();
/*    */ 
/* 56 */         for (TileEntityProjector tileentity2 : InnerMap.values())
/*    */         {
/* 58 */           boolean logicswitch = tileentity2.equals(tileentity);
/*    */ 
/* 60 */           if ((logicswitch) && (tileentity2.isActive()))
/*    */           {
/* 62 */             if (entityplayer != null)
/*    */             {
/* 65 */               if (!SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.RPB, true))
/*    */               {
/* 67 */                 return true;
/*    */               }
/*    */             }
/*    */             else
/*    */             {
/* 72 */               return true;
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     TileEntityProjector tileentity;
/* 81 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ForceFieldOptions
 * JD-Core Version:    0.6.2
 */