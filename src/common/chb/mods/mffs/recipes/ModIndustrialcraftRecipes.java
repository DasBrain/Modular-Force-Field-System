package chb.mods.mffs.recipes;

import chb.mods.mffs.common.ModularForceFieldSystem;
import ic2.api.ExplosionWhitelist;
import ic2.api.Items;
import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ModIndustrialcraftRecipes {

	public static void register()
	{
		ExplosionWhitelist.addWhitelistedBlock(ModularForceFieldSystem.MFFSDefenceStation);
		ExplosionWhitelist.addWhitelistedBlock(ModularForceFieldSystem.MFFSCapacitor);
		ExplosionWhitelist.addWhitelistedBlock(ModularForceFieldSystem.MFFSProjector);
		ExplosionWhitelist.addWhitelistedBlock(ModularForceFieldSystem.MFFSSecurtyStation);
		ExplosionWhitelist.addWhitelistedBlock(ModularForceFieldSystem.MFFSExtractor);
		ExplosionWhitelist.addWhitelistedBlock(ModularForceFieldSystem.MFFSForceEnergyConverter);
		
	}
	
	public static void init()
	{

		if(Items.getItem("carbonPlate")!=null){
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemupgradeexctractorboost,2),
				new Object[] { " B ", "BAB", " B ",
			'A',Items.getItem("overclockerUpgrade"), 'B',Items.getItem("carbonPlate")
		});
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSExtractor,1),
				new Object[] { " B ", "CDC", " E ",
			'B',Items.getItem("advancedCircuit"), 'C',ModularForceFieldSystem.MFFSitemForcePowerCrystal, 
			'D',Items.getItem("advancedMachine"), 'E',Items.getItem("extractor")
		});
		
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemupgradecapcap),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("carbonPlate"), 'B',
						ModularForceFieldSystem.MFFSitemForcePowerCrystal });
		
		
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemupgradecaprange),
				new Object[] { "AAA", "BCB", "BDB",
			'A',Items.getItem("copperCableItem"), 
			'B',Items.getItem("carbonPlate"),
			'C',Items.getItem("insulatedCopperCableItem"), 
			'D',Items.getItem("advancedCircuit") 				
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemFocusmatix, 64),
				new Object[] { "ACA", "CBC", "ACA", 'A',
						Items.getItem("carbonPlate"), 'B',
						Item.diamond, 'C', Block.glass });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemcardempty), new Object[] {
				"AAA", "ABA", "AAA", 'A', Item.paper,
				'B', Items.getItem("electronicCircuit") });


	
		CraftingManager.getInstance().addRecipe(
				new ItemStack(ModularForceFieldSystem.MFFSCapacitor, 1),
				new Object[] { "ABA", "CDC", "ABA", 'A',
					ModularForceFieldSystem.MFFSitemForcePowerCrystal,
						'B',
						Items.getItem("frequencyTransmitter"),
						'C',
						Items.getItem("electronicCircuit"),
						'D',
						Items.getItem("advancedMachine") });

		CraftingManager.getInstance()
				.addRecipe(
						new ItemStack(ModularForceFieldSystem.MFFSProjector, 1),
						new Object[] { "DCD", "CAC", "DBD",
								'A',
								Items.getItem("advancedMachine"),
								'B',
								Items.getItem("frequencyTransmitter"),
								'C', ModularForceFieldSystem.MFFSitemFocusmatix,
								'D',
								Items.getItem("advancedAlloy") });
		CraftingManager.getInstance()
				.addRecipe(
						new ItemStack(ModularForceFieldSystem.MFFSSecurtyStation, 1),
						new Object[] { "DCD", "CAC", "DBD",
								'A',
								Items.getItem("advancedMachine"),
								'B',
								Items.getItem("teleporter"),
								'C',
								Items.getItem("electronicCircuit"),
								'D',
								Items.getItem("advancedAlloy") });
		
		CraftingManager.getInstance()
		.addRecipe(
				new ItemStack(ModularForceFieldSystem.MFFSForceEnergyConverter, 1),
				new Object[] { "ABA", "CDE", "AAA",
						'A',
						Items.getItem("refinedIronIngot"),
						'B',
						Items.getItem("frequencyTransmitter"),
						'C',
						ModularForceFieldSystem.MFFSitemForcePowerCrystal,
						'D',
						Items.getItem("advancedMachine"),
						'E',
						Items.getItem("glassFiberCableItem"),
				
				});
		
		
		
		

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemFocusmatix, 64),
				new Object[] { "ACA", "CBC", "ACA", 'A',
						Items.getItem("carbonPlate"), 'B',
						Item.diamond, 'C', Block.glass });

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionZapper),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("teslaCoil") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionSubwater),
				new Object[] { "BAB", "ABA", "BAB", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Item.bucketEmpty });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionDome),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Items.getItem("electronicCircuit") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionCutter),
				new Object[] { " A ", "ABA", " A ", 'A',
						Items.getItem("advancedAlloy"), 'B',
						Item.pickaxeSteel });

		CraftingManager.getInstance().addRecipe(
				new ItemStack(ModularForceFieldSystem.MFFSSecStationexidreader),
				new Object[] { "A  ", " B ", "  C", 'A',
					ModularForceFieldSystem.MFFSitemcardempty, 'B',
						Items.getItem("electronicCircuit"),
						'C', Item.redstone });



		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSitemWrench),
				new Object[] { "ADE", "CFC", "CBC", 'A',
			Item.redstone, 'B',Items.getItem("advancedCircuit") ,
			'C',Items.getItem("carbonPlate"),
			'D',Items.getItem("wrench"),
			'E',Block.lever,'F',ModularForceFieldSystem.MFFSitemForcePowerCrystal
		});


		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer), new Object[] { " A ", "ABA", " A ", 'A', Items.getItem("frequencyTransmitter"),'B', ModularForceFieldSystem.MFFSitemFocusmatix });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionCamouflage), new Object[] { " A ", "ABA", " A ", 'B', Items.getItem("matter"),'A', Items.getItem("advancedAlloy") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion), new Object[] { " A ", "ABA", " A ", 'B', Items.getItem("advancedCircuit"),'A', Items.getItem("advancedAlloy") });
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSDefenceStation,1),new Object[] { " B ", "CAC", " D ",'A',Items.getItem("advancedMachine"),
			'B',Items.getItem("frequencyTransmitter"),
			'C',Item.enderPearl,
			'D',Items.getItem("carbonPlate")
		});

		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionDefenceStation),new Object[] { " B ", "CAC", " B ", 'A', Items.getItem("teslaCoil"), 'B',ModularForceFieldSystem.MFFSItemIDCard , 'C',Items.getItem("electronicCircuit")});
		CraftingManager.getInstance().addRecipe(new ItemStack(ModularForceFieldSystem.MFFSProjectorOptionMoobEx), new Object[] { "BCB", "DAD", "ECE", 'A', Items.getItem("teslaCoil"), 'B', Item.bone, 'C', Item.blazeRod, 'D', Item.rottenFlesh, 'E', Item.spiderEye});
	

		}
	}
	
}
