/*    */ package chb.mods.mffs.common.event;
/*    */ 
/*    */ import chb.mods.mffs.common.ForceFieldOptions;
/*    */ import com.pahimar.ee3.event.ActionRequestEvent;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.event.ForgeSubscribe;
/*    */ 
/*    */ public class EE3Event
/*    */ {
/*    */   @ForgeSubscribe
/*    */   public void onModActionEvent(ActionRequestEvent event)
/*    */   {
/* 16 */     boolean blockprotected = ForceFieldOptions.BlockProtected(event.entityPlayer.worldObj, event.x, event.y, event.z, event.entityPlayer);
/* 17 */     if (blockprotected) {
/* 18 */       event.entityPlayer.addChatMessage("[Field Security] Fail: Block transmute need <Change Protected Block> SecurityRight");
/*    */     }
/* 20 */     event.setCanceled(blockprotected);
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.event.EE3Event
 * JD-Core Version:    0.6.2
 */