package chb.mods.mffs.common.event;

import net.minecraftforge.event.ForgeSubscribe;
import chb.mods.mffs.common.ForceFieldOptions;

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