package chb.mods.mffs.network;

import java.util.List;

public abstract interface INetworkHandlerListener
{
  public abstract void onNetworkHandlerUpdate(String paramString);

  public abstract List getFieldsforUpdate();
}

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.network.INetworkHandlerListener
 * JD-Core Version:    0.6.2
 */