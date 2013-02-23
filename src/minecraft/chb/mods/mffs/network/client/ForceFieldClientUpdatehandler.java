/*    */ package chb.mods.mffs.network.client;
/*    */ 
/*    */ import chb.mods.mffs.common.CommonProxy;
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import cpw.mods.fml.common.IScheduledTickHandler;
/*    */ import cpw.mods.fml.common.TickType;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Stack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ 
/*    */ public final class ForceFieldClientUpdatehandler
/*    */   implements IScheduledTickHandler
/*    */ {
/* 19 */   protected static Stack queue = new Stack();
/*    */ 
/*    */   public void tickEnd(EnumSet type, Object[] tickData)
/*    */   {
/* 26 */     StringBuilder str = new StringBuilder();
/*    */ 
/* 28 */     while (!queue.isEmpty())
/*    */     {
/* 30 */       str.append(queue.pop());
/* 31 */       str.append("/");
/* 32 */       str.append(queue.pop());
/* 33 */       str.append("/");
/* 34 */       str.append(queue.pop());
/* 35 */       str.append("#");
/*    */ 
/* 37 */       if (str.length() > 7500) {
/* 38 */         break;
/*    */       }
/*    */     }
/*    */ 
/* 42 */     if (str.length() > 0)
/*    */     {
/* 44 */       NetworkHandlerClient.requestForceFieldInitialData(ModularForceFieldSystem.proxy.getClientWorld().provider.dimensionId, str.toString());
/* 45 */       str.setLength(0);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void tickStart(EnumSet type, Object[] tickData)
/*    */   {
/*    */   }
/*    */ 
/*    */   public EnumSet ticks()
/*    */   {
/* 56 */     return EnumSet.of(TickType.PLAYER);
/*    */   }
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 61 */     return "ForceField Client Ticker";
/*    */   }
/*    */ 
/*    */   public int nextTickSpacing()
/*    */   {
/* 66 */     return 1;
/*    */   }
/*    */ 
/*    */   public static void addto(int x, int y, int z)
/*    */   {
/* 72 */     queue.push(Integer.valueOf(x));
/* 73 */     queue.push(Integer.valueOf(y));
/* 74 */     queue.push(Integer.valueOf(z));
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.network.client.ForceFieldClientUpdatehandler
 * JD-Core Version:    0.6.2
 */