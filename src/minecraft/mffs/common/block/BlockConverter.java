package mffs.common.block;

import mffs.common.ModularForceFieldSystem;
import mffs.common.tileentity.TileEntityConverter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConverter extends BlockMFFSBase
{
	public BlockConverter(int i)
	{
		super(i);
	}

	public String getTextureFile()
	{
		if (ModularForceFieldSystem.graphicstyle == 1)
		{
			return "/chb/mods/mffs/sprites/Converter_ue.png";
		}
		return "/chb/mods/mffs/sprites/Converter.png";
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityConverter();
	}
}