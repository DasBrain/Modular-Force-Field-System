/*    */ package chb.mods.mffs.nei;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.Versioninfo;
/*    */ import codechicken.nei.MultiItemRange;
/*    */ import codechicken.nei.api.API;
/*    */ import codechicken.nei.api.IConfigureNEI;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class NEI_MFFS_Config
/*    */   implements IConfigureNEI
/*    */ {
/*    */   public void loadConfig()
/*    */   {
/* 13 */     API.hideItem(ModularForceFieldSystem.MFFSitemSwitch.itemID);
/* 14 */     API.hideItem(ModularForceFieldSystem.MFFSitemFieldTeleporter.itemID);
/* 15 */     API.hideItem(ModularForceFieldSystem.MFFSitemMFDidtool.itemID);
/* 16 */     API.hideItem(ModularForceFieldSystem.MFFSitemManuelBook.itemID);
/*    */ 
/* 18 */     API.hideItem(ModularForceFieldSystem.MFFSitemfc.itemID);
/* 19 */     API.hideItem(ModularForceFieldSystem.MFFSItemIDCard.itemID);
/* 20 */     API.hideItem(ModularForceFieldSystem.MFFSItemSecLinkCard.itemID);
/* 21 */     API.hideItem(ModularForceFieldSystem.MFFSFieldblock.blockID);
/* 22 */     API.hideItem(ModularForceFieldSystem.MFFSAccessCard.itemID);
/* 23 */     API.hideItem(ModularForceFieldSystem.MFFSitemDataLinkCard.itemID);
/*    */ 
/* 25 */     MultiItemRange blocks = new MultiItemRange();
/* 26 */     MultiItemRange items = new MultiItemRange();
/* 27 */     MultiItemRange upgrades = new MultiItemRange();
/* 28 */     MultiItemRange modules = new MultiItemRange();
/*    */ 
/* 30 */     blocks.add(ModularForceFieldSystem.MFFSForceEnergyConverter);
/* 31 */     blocks.add(ModularForceFieldSystem.MFFSExtractor);
/* 32 */     blocks.add(ModularForceFieldSystem.MFFSDefenceStation);
/* 33 */     blocks.add(ModularForceFieldSystem.MFFSSecurtyStation);
/* 34 */     blocks.add(ModularForceFieldSystem.MFFSCapacitor);
/* 35 */     blocks.add(ModularForceFieldSystem.MFFSProjector);
/* 36 */     blocks.add(ModularForceFieldSystem.MFFSSecurtyStorage);
/* 37 */     blocks.add(ModularForceFieldSystem.MFFSMonazitOre);
/*    */ 
/* 39 */     items.add(ModularForceFieldSystem.MFFSitemWrench);
/* 40 */     items.add(ModularForceFieldSystem.MFFSitemMFDdebugger);
/* 41 */     items.add(ModularForceFieldSystem.MFFSitemcardempty);
/* 42 */     items.add(ModularForceFieldSystem.MFFSitemForcePowerCrystal);
/* 43 */     items.add(ModularForceFieldSystem.MFFSitemForcicium);
/* 44 */     items.add(ModularForceFieldSystem.MFFSitemForcicumCell);
/* 45 */     items.add(ModularForceFieldSystem.MFFSitemFocusmatix);
/* 46 */     items.add(ModularForceFieldSystem.MFFSitemInfinitePowerCard);
/*    */ 
/* 49 */     upgrades.add(ModularForceFieldSystem.MFFSitemupgradeexctractorboost);
/* 50 */     upgrades.add(ModularForceFieldSystem.MFFSitemupgradecaprange);
/* 51 */     upgrades.add(ModularForceFieldSystem.MFFSitemupgradecapcap);
/*    */ 
/* 53 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionZapper);
/* 54 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionSubwater);
/* 55 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionDome);
/* 56 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionCutter);
/* 57 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionDefenceStation);
/* 58 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionMoobEx);
/* 59 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer);
/* 60 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionCamouflage);
/* 61 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion);
/*    */ 
/* 63 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorFFDistance);
/* 64 */     upgrades.add(ModularForceFieldSystem.MFFSProjectorFFStrenght);
/*    */ 
/* 68 */     modules.add(ModularForceFieldSystem.MFFSProjectorTypsphere);
/* 69 */     modules.add(ModularForceFieldSystem.MFFSProjectorTypkube);
/* 70 */     modules.add(ModularForceFieldSystem.MFFSProjectorTypwall);
/* 71 */     modules.add(ModularForceFieldSystem.MFFSProjectorTypdeflector);
/* 72 */     modules.add(ModularForceFieldSystem.MFFSProjectorTyptube);
/* 73 */     modules.add(ModularForceFieldSystem.MFFSProjectorTypcontainment);
/* 74 */     modules.add(ModularForceFieldSystem.MFFSProjectorTypAdvCube);
/*    */ 
/* 76 */     API.addSetRange("MFFS.Items.Upgrades", upgrades);
/* 77 */     API.addSetRange("MFFS.Items.Modules", modules);
/* 78 */     API.addSetRange("MFFS.Items", items);
/* 79 */     API.addSetRange("MFFS.Blocks", blocks);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 87 */     return "MFFS";
/*    */   }
/*    */   public String getVersion() {
/* 90 */     return Versioninfo.curentversion();
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.nei.NEI_MFFS_Config
 * JD-Core Version:    0.6.2
 */