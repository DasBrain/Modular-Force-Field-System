package mffs.common.options;

import java.util.ArrayList;
import java.util.List;

import mffs.common.ModularForceFieldSystem;
import mffs.common.ProjectorTyp;
import mffs.common.modules.ItemProjectorModuleAdvCube;
import mffs.common.modules.ItemProjectorModuleContainment;
import mffs.common.modules.ItemProjectorModuleCube;
import mffs.common.modules.ItemProjectorModuleDeflector;
import mffs.common.modules.ItemProjectorModuleSphere;
import mffs.common.modules.ItemProjectorModuleTube;
import mffs.common.modules.ItemProjectorModuleWall;
import mffs.common.modules.ItemProjectorModulediagonallyWall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

public abstract class ItemProjectorOptionBase extends Item
{
	private static List instances = new ArrayList();

	public ItemProjectorOptionBase(int i)
	{
		super(i);
		setMaxStackSize(8);
		setCreativeTab(ModularForceFieldSystem.MFFSTab);
		instances.add(this);
	}

	public static List<ItemProjectorOptionBase> get_instances()
	{
		return instances;
	}

	public String getTextureFile()
	{
		return "/chb/mods/mffs/sprites/items.png";
	}

	public boolean isRepairable()
	{
		return false;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b)
	{
		if ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)))
		{
			info.add("compatible with:");

			if (ItemProjectorModuleWall.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.wall));
			if (ItemProjectorModulediagonallyWall.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.diagonallywall));
			if (ItemProjectorModuleDeflector.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.deflector));
			if (ItemProjectorModuleTube.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.tube));
			if (ItemProjectorModuleSphere.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.sphere));
			if (ItemProjectorModuleCube.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.cube));
			if (ItemProjectorModuleAdvCube.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.AdvCube));
			if (ItemProjectorModuleContainment.supportsOption(this))
				info.add(ProjectorTyp.getdisplayName(ProjectorTyp.containment));
		}
		else
		{
			info.add("compatible with: (Hold Shift)");
		}
	}
}