package chb.mods.mffs.common.event;

import chb.mods.mffs.common.ForceFieldOptions;
import com.pahimar.ee3.event.ActionRequestEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;

public class EE3Event
{
  @ForgeSubscribe
  public void onModActionEvent(ActionRequestEvent event)
  {
    boolean blockprotected = ForceFieldOptions.BlockProtected(event.entityPlayer.worldObj, event.x, event.y, event.z, event.entityPlayer);
    if (blockprotected) {
      event.entityPlayer.addChatMessage("[Field Security] Fail: Block transmute need <Change Protected Block> SecurityRight");
    }
    event.setCanceled(blockprotected);
  }
}