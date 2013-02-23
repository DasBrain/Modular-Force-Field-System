package chb.mods.mffs.common.modules;

import chb.mods.mffs.common.ForceFieldTyps;
import chb.mods.mffs.common.Functions;
import chb.mods.mffs.common.IModularProjector;
import chb.mods.mffs.common.ModularForceFieldSystem;
import chb.mods.mffs.common.ProjectorTyp;
import chb.mods.mffs.common.SecurityHelper;
import chb.mods.mffs.common.SecurityRight;
import chb.mods.mffs.common.tileentity.TileEntityProjector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class ModuleBase extends Item
{
  private static List instances = new ArrayList();

  public static List get_instances() { return instances; }

  public ModuleBase(int i)
  {
    super(i);
    setMaxStackSize(8);
    instances.add(this);
    setCreativeTab(ModularForceFieldSystem.MFFSTab);
  }

  public String getTextureFile()
  {
    return "/chb/mods/mffs/sprites/items.png";
  }

  public boolean isRepairable() {
    return false;
  }

  public abstract boolean supportsDistance();

  public abstract boolean supportsStrength();

  public abstract boolean supportsMatrix();

  public abstract void calculateField(IModularProjector paramIModularProjector, Set paramSet);

  public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
  {
    TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

    if ((!world.isRemote) && 
      ((tileEntity instanceof IModularProjector))) {
      if (!SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)) {
        return false;
      }
      if (((IModularProjector)tileEntity).getStackInSlot(1) == null)
      {
        ((IModularProjector)tileEntity).setInventorySlotContents(1, itemstack.splitStack(1));
        Functions.ChattoPlayer(entityplayer, "[Projector] Success: <Projector Module " + ProjectorTyp.TypfromItem(((IModularProjector)tileEntity).getStackInSlot(1).getItem()).displayName + "> installed");
        ((TileEntityProjector)tileEntity).checkslots();
        return true;
      }

      Functions.ChattoPlayer(entityplayer, "[Projector] Fail: Slot is not empty");
      return false;
    }

    return false;
  }

  public String getProjectorTextureFile()
  {
    return "/chb/mods/mffs/sprites/blocks.png";
  }

  public ForceFieldTyps getForceFieldTyps()
  {
    return ForceFieldTyps.Default;
  }

  public abstract boolean supportsOption(Item paramItem);
}