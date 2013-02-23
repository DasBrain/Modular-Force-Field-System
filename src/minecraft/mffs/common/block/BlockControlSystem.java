package mffs.common.block;

import mffs.common.ModularForceFieldSystem;
import mffs.common.tileentity.TileEntityControlSystem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockControlSystem extends BlockMFFSBase
{
	public BlockControlSystem(int i)
	{
		super(i);
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityControlSystem();
	}

	public String getTextureFile()
	{
		if (ModularForceFieldSystem.graphicstyle == 1)
		{
			return "/chb/mods/mffs/sprites/ControlSystem_ue.png";
		}
		return "/chb/mods/mffs/sprites/ControlSystem.png";
	}
}