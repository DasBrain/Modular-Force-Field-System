package chb.mods.mffs.common;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;


public class BlockForcicium extends Block {
	public BlockForcicium(int i) {
		super(i, Material.rock);
		setHardness(1.0F);
		setResistance(5.0F);
		setStepSound(soundStoneFootstep);
	}

	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/forciciumblock.png";
	}

    @Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int l) {
    	return 0;
	}
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ModularForceFieldSystem.MFFSitemForcicium.shiftedIndex;
    }
    
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 9;
    }
    
    
}