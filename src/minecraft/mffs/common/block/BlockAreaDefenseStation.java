package mffs.common.block;

import mffs.common.ModularForceFieldSystem;
import mffs.common.tileentity.TileEntityAreaDefenseStation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAreaDefenseStation extends BlockMFFSBase
{
	public BlockAreaDefenseStation(int i)
	{
		super(i);
	}

	public String getTextureFile()
	{
		if (ModularForceFieldSystem.graphicstyle == 1)
		{
			return "/chb/mods/mffs/sprites/DefenceStation_ue.png";
		}
		return "/chb/mods/mffs/sprites/DefenceStation.png";
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityAreaDefenseStation();
	}
}