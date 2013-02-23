package chb.mods.mffs.common.tileentity;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import chb.mods.mffs.api.PointXYZ;
import chb.mods.mffs.common.ForceFieldBlockStack;
import chb.mods.mffs.common.Linkgrid;
import chb.mods.mffs.common.WorldMap;
import chb.mods.mffs.network.client.ForceFieldClientUpdatehandler;
import chb.mods.mffs.network.server.ForceFieldServerUpdatehandler;

public class TileEntityForceField extends TileEntity
{
  private Random random = new Random();
  private int[] texturid = { -76, -76, -76, -76, -76, -76 };
  private String texturfile;
  private int Ticker = 0;
  private int ForcefieldCamoblockid;
  private int ForcefieldCamoblockmeta;

  public int getTicker()
  {
    return this.Ticker;
  }

  public void setTicker(int ticker) {
    this.Ticker = ticker;
  }

  public int getForcefieldCamoblockmeta()
  {
    return this.ForcefieldCamoblockmeta;
  }

  public void setForcefieldCamoblockmeta(int forcefieldCamoblockmeta) {
    this.ForcefieldCamoblockmeta = forcefieldCamoblockmeta;
  }

  public int getForcefieldCamoblockid() {
    return this.ForcefieldCamoblockid;
  }

  public void setForcefieldCamoblockid(int forcefieldCamoblockid) {
    this.ForcefieldCamoblockid = forcefieldCamoblockid;
  }

  public String getTexturfile() {
    return this.texturfile;
  }

  public void setTexturfile(String texturfile) {
    this.texturfile = texturfile;
  }

  public int[] getTexturid()
  {
    return this.texturid;
  }

  public int getTexturid(int l)
  {
    return this.texturid[l];
  }

  public void updateEntity()
  {
    if (!this.worldObj.isRemote) {
      if (getTicker() >= 20) {
        if ((this.texturid[0] == -76) || (this.texturfile == null))
        {
          UpdateTextur();
        }

        setTicker(0);
      }

      setTicker((short)(getTicker() + 1));
    } else {
      if (getTicker() >= 20 + this.random.nextInt(20)) {
        if ((this.texturid[0] == -76) || (this.texturfile == null))
        {
          ForceFieldClientUpdatehandler.addto(this.xCoord, this.yCoord, this.zCoord);
        }

        setTicker(0);
      }

      setTicker((short)(getTicker() + 1));
    }
  }

  public void setTexturid(String remotetextu)
  {
    String[] textur = remotetextu.split("/");

    this.texturid[0] = Integer.parseInt(textur[0].trim());
    this.texturid[1] = Integer.parseInt(textur[1].trim());
    this.texturid[2] = Integer.parseInt(textur[2].trim());
    this.texturid[3] = Integer.parseInt(textur[3].trim());
    this.texturid[4] = Integer.parseInt(textur[4].trim());
    this.texturid[5] = Integer.parseInt(textur[5].trim());

    this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
    setTicker(0);
  }

  public void setTexturid(String texturid, TileEntityProjector proj)
  {
    try
    {
      if (!texturid.equalsIgnoreCase(this.texturid[0] + "/" + this.texturid[1] + "/" + this.texturid[2] + "/" + this.texturid[3] + "/" + this.texturid[4] + "/" + this.texturid[5]))
      {
        String[] textur = texturid.split("/");
        this.texturid[0] = Integer.parseInt(textur[0].trim());
        this.texturid[1] = Integer.parseInt(textur[1].trim());
        this.texturid[2] = Integer.parseInt(textur[2].trim());
        this.texturid[3] = Integer.parseInt(textur[3].trim());
        this.texturid[4] = Integer.parseInt(textur[4].trim());
        this.texturid[5] = Integer.parseInt(textur[5].trim());

        ForceFieldServerUpdatehandler.getWorldMap(this.worldObj).addto(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId, proj.xCoord, proj.yCoord, proj.zCoord);
      }
    }
    catch (Exception ex)
    {
    }
  }

  public void UpdateTextur()
  {
    if (!this.worldObj.isRemote) {
      ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(this.worldObj).getForceFieldStackMap(Integer.valueOf(new PointXYZ(this.xCoord, this.yCoord, this.zCoord, this.worldObj).hashCode()));

      if (ffworldmap != null)
      {
        if (!ffworldmap.isEmpty())
        {
          TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(this.worldObj).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));

          if (projector != null)
          {
            setTexturid(projector.getForceFieldTexturID(), projector);
            setTexturfile(projector.getForceFieldTexturfile());
            setForcefieldCamoblockid(projector.getForcefieldCamoblockid());
            setForcefieldCamoblockmeta(projector.getForcefieldCamoblockmeta());
          }
        }
      }
    }
  }

  public ItemStack[] getContents() {
    return null;
  }

  public void setMaxStackSize(int arg0)
  {
  }
}