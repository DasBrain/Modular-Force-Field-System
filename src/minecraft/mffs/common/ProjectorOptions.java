package mffs.common;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum ProjectorOptions
{
	Zapper("<touch damage>", ModularForceFieldSystem.MFFSProjectorOptionZapper, " K KQK K ", " C CdC C "),
	Subwater("<Sponge>", ModularForceFieldSystem.MFFSProjectorOptionSubwater, " K KcK K ", " C CcC C "),
	Dome("<Field Manipulator>", ModularForceFieldSystem.MFFSProjectorOptionDome, " K KCK K ", " C CEC C "),
	Cutter("<Block Breaker>", ModularForceFieldSystem.MFFSProjectorOptionCutter, " K KbK K ", " C CbC C "),
	FieldJammer("<Force Field Jammer>", ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer, " J JvJ J ", " a ava a "),
	Camouflage("<Camouflage>", ModularForceFieldSystem.MFFSProjectorOptionCamouflage, " K KRK K ", " C CGC C "),
	FieldFusion("<Field Fusion>", ModularForceFieldSystem.MFFSProjectorOptionFieldFusion, " K KDK K ", " C CFC C "),
	MoobEx("<NPC Defense>", ModularForceFieldSystem.MFFSProjectorOptionMoobEx, "fgfhQhjgj", "fgfhdhjgj"),
	DefenceStation("<Defense Station>", ModularForceFieldSystem.MFFSProjectorOptionDefenceStation, " z CQC z ", " z EdE z ");

	String displayName;
	Item item;
	String recipeic;
	String recipeue;

	private ProjectorOptions(String dispNm, Item item, String recipeic, String recipeue)
	{
		this.displayName = dispNm;
		this.item = item;
		this.recipeic = recipeic;
		this.recipeue = recipeue;
	}

	public static void initialize()
	{
		for (ProjectorOptions mach : values())
		{
			LanguageRegistry.instance().addNameForObject(mach.item, "en_US", " MFFS Projector Upgrade " + mach.displayName);

			if (ModularForceFieldSystem.ic2found.booleanValue())
				RecipesFactory.addRecipe(mach.recipeic, 1, 1, null, mach.item);
			if (ModularForceFieldSystem.uefound.booleanValue())
				RecipesFactory.addRecipe(mach.recipeue, 1, 2, null, mach.item);
		}
	}
}