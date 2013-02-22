package chb.mods.mffs.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SecurityHelper {
	
	
	public static boolean isAccessGranted(TileEntity tileEntity,EntityPlayer entityplayer,World world, int accessmode)
	{
		
		
		
		
		if (tileEntity instanceof TileEntitySecurityStation) {
			
				if (!(((TileEntitySecurityStation)tileEntity).isAccessGranted(entityplayer.username,accessmode))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}

		
		
		
		
		
		
		if (tileEntity instanceof TileEntityConverter) {
			
			if(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityConverter)tileEntity).getLinkCapacitors_ID())!= null)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityConverter)tileEntity).getLinkCapacitors_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityConverter)tileEntity).getLinkCapacitors_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,accessmode))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
		}
		

		
		if (tileEntity instanceof TileEntityCapacitor){


			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityCapacitor)tileEntity).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityCapacitor)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,accessmode))) {
				
						Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					
					return false;
				}
			}
		}

		if (tileEntity instanceof TileEntityExtractor) {
			
			if(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityExtractor)tileEntity).getLinkCapacitors_ID())!= null)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityExtractor)tileEntity).getLinkCapacitors_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityExtractor)tileEntity).getLinkCapacitors_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,accessmode))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
		}
		
		


		if (tileEntity instanceof TileEntityProjector) {
			
			if(((TileEntityProjector)tileEntity).getaccesstyp()== 2)
			{
			if(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityProjector)tileEntity).getLinkCapacitor_ID())!= null)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityProjector)tileEntity).getLinkCapacitor_ID()).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(Linkgrid.getWorldMap(world).getCapacitor().get(((TileEntityProjector)tileEntity).getLinkCapacitor_ID()).getSecStation_ID()).isAccessGranted(entityplayer.username,accessmode))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
			}
			if(((TileEntityProjector)tileEntity).getaccesstyp()== 3)
			{
			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityProjector)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,accessmode))) {
					Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					return false;
				}
			}
		}
			
		}
		
		if (tileEntity instanceof TileEntityAreaDefenseStation) {
			
			if(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityAreaDefenseStation)tileEntity).getSecStation_ID()) != null)
			{
				if (!(Linkgrid.getWorldMap(world).getSecStation().get(((TileEntityAreaDefenseStation)tileEntity).getSecStation_ID()).isAccessGranted(entityplayer.username,accessmode))) {
				
						Functions.ChattoPlayer(entityplayer,"[Field Security] Fail: access denied");
					
					return false;
				}
			}
		       
		}
	

		return true;
	}

}
