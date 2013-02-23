package chb.mods.mffs.common;

import chb.mods.mffs.api.PointXYZ;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ForceFieldBlockStack
{
  private PointXYZ png;
  private boolean sync;
  public Queue blocks = new LinkedList();

  public ForceFieldBlockStack(PointXYZ png)
  {
    this.png = png;
    this.sync = false;
  }

  public int getsize()
  {
    return this.blocks.size();
  }

  public void removeBlock()
  {
    this.blocks.poll();
  }

  public synchronized void removebyProjector(int projectorid)
  {
    ArrayList tempblock = new ArrayList();

    for (ForceFieldBlock ffblock : this.blocks)
    {
      if (ffblock.Projektor_ID == projectorid) {
        tempblock.add(ffblock);
      }
    }
    if (!tempblock.isEmpty())
      this.blocks.removeAll(tempblock);
  }

  public int getGenratorID() {
    ForceFieldBlock ffblock = (ForceFieldBlock)this.blocks.peek();
    if (ffblock != null) {
      return ffblock.Generator_Id;
    }
    return 0;
  }

  public int getProjectorID() {
    ForceFieldBlock ffblock = (ForceFieldBlock)this.blocks.peek();
    if (ffblock != null) {
      return ffblock.Projektor_ID;
    }
    return 0;
  }

  public int getTyp() {
    ForceFieldBlock ffblock = (ForceFieldBlock)this.blocks.peek();
    if (ffblock != null) {
      return ffblock.typ;
    }
    return -1;
  }

  public void setSync(boolean sync) {
    this.sync = sync;
  }

  public boolean isSync() {
    return this.sync;
  }

  public boolean isEmpty() {
    return this.blocks.isEmpty();
  }

  public ForceFieldBlock get()
  {
    return (ForceFieldBlock)this.blocks.peek();
  }

  public void add(int Generator_Id, int Projektor_ID, int typ)
  {
    this.blocks.offer(new ForceFieldBlock(Generator_Id, Projektor_ID, typ));
  }

  public PointXYZ getPoint() {
    return this.png;
  }
}