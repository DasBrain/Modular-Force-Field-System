package chb.mods.mffs.network;

import java.util.List;

public abstract interface INetworkHandlerListener
{
  public abstract void onNetworkHandlerUpdate(String paramString);

  public abstract List getFieldsforUpdate();
}