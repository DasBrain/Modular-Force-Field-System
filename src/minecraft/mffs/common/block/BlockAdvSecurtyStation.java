package mffs.common.block;

import mffs.common.ModularForceFieldSystem;
import mffs.common.tileentity.TileEntityAdvSecurityStation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvSecurtyStation extends BlockMFFSBase
{
	public BlockAdvSecurtyStation(int i)
	{
		super(i);
	}

	public String getTextureFile()
	{
		if (ModularForceFieldSystem.graphicstyle == 1)
		{
			return "/chb/mods/mffs/sprites/AdvSecurtyStation_ue.png";
		}
		return "/chb/mods/mffs/sprites/AdvSecurtyStation.png";
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityAdvSecurityStation();
	}
}