package chb.mods.mffs.common;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockConverter extends BlockMFFSBase {
	
	public BlockConverter(int i, int texturindex) {
		super(i, texturindex);
		setRequiresSelfNotify();
	}

	
	@Override
	public String getTextureFile() {
		return "/chb/mods/mffs/sprites/Converter.png";
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer, int par6, float par7, float par8,
			float par9){
		
		
		if (entityplayer.isSneaking())
        {
			return false;
        }

		TileEntityConverter tileentity = (TileEntityConverter) world.getBlockTileEntity(i, j, k);

		if(!SecurityHelper.isAccessGranted(tileentity, entityplayer, world,ModularForceFieldSystem.PERSONALID_FULLACCESS))
		{return false;}
	
		
		

		if (entityplayer.getCurrentEquippedItem() != null
				&& entityplayer.getCurrentEquippedItem().itemID == Block.lever.blockID) {
			return false;
		}
		

		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMultitool)) {
			return false;
		}

		
		if (entityplayer.getCurrentEquippedItem() != null
				&& (entityplayer.getCurrentEquippedItem().getItem() instanceof ItemCardPowerLink)) {
			return false;
		}
		
		if (!world.isRemote)
		entityplayer.openGui(ModularForceFieldSystem.instance, ModularForceFieldSystem.GUI_CONVERTER, world,
				i, j, k);
		
	
		return true;
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		
		try {
			return (TileEntity) ModularForceFieldSystem.Converter.newInstance();
		} catch (Exception e) {
			
			System.out.println("[ModularForceFieldSystem][Error] Converter TileEntity fail");
			return null;
		} 
		
	}

}
