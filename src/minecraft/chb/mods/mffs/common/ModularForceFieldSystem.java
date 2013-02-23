/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.common.block.BlockAdvSecurtyStation;
/*     */ import chb.mods.mffs.common.block.BlockAreaDefenseStation;
/*     */ import chb.mods.mffs.common.block.BlockCapacitor;
/*     */ import chb.mods.mffs.common.block.BlockControlSystem;
/*     */ import chb.mods.mffs.common.block.BlockConverter;
/*     */ import chb.mods.mffs.common.block.BlockExtractor;
/*     */ import chb.mods.mffs.common.block.BlockForceField;
/*     */ import chb.mods.mffs.common.block.BlockMonazitOre;
/*     */ import chb.mods.mffs.common.block.BlockProjector;
/*     */ import chb.mods.mffs.common.block.BlockSecurtyStorage;
/*     */ import chb.mods.mffs.common.event.EE3Event;
/*     */ import chb.mods.mffs.common.item.ItemAccessCard;
/*     */ import chb.mods.mffs.common.item.ItemCapacitorUpgradeCapacity;
/*     */ import chb.mods.mffs.common.item.ItemCapacitorUpgradeRange;
/*     */ import chb.mods.mffs.common.item.ItemCardDataLink;
/*     */ import chb.mods.mffs.common.item.ItemCardEmpty;
/*     */ import chb.mods.mffs.common.item.ItemCardPersonalID;
/*     */ import chb.mods.mffs.common.item.ItemCardPower;
/*     */ import chb.mods.mffs.common.item.ItemCardPowerLink;
/*     */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*     */ import chb.mods.mffs.common.item.ItemExtractorUpgradeBooster;
/*     */ import chb.mods.mffs.common.item.ItemForcePowerCrystal;
/*     */ import chb.mods.mffs.common.item.ItemForcicium;
/*     */ import chb.mods.mffs.common.item.ItemForcicumCell;
/*     */ import chb.mods.mffs.common.item.ItemProjectorFieldModulatorDistance;
/*     */ import chb.mods.mffs.common.item.ItemProjectorFieldModulatorStrength;
/*     */ import chb.mods.mffs.common.item.ItemProjectorFocusMatrix;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleAdvCube;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleContainment;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleCube;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleDeflector;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleSphere;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleTube;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModuleWall;
/*     */ import chb.mods.mffs.common.modules.ItemProjectorModulediagonallyWall;
/*     */ import chb.mods.mffs.common.multitool.ItemDebugger;
/*     */ import chb.mods.mffs.common.multitool.ItemFieldtransporter;
/*     */ import chb.mods.mffs.common.multitool.ItemManuelBook;
/*     */ import chb.mods.mffs.common.multitool.ItemPersonalIDWriter;
/*     */ import chb.mods.mffs.common.multitool.ItemSwitch;
/*     */ import chb.mods.mffs.common.multitool.ItemWrench;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionBlockBreaker;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldManipulator;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionSponge;
/*     */ import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityForceField;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityMachines;
/*     */ import chb.mods.mffs.network.client.ForceFieldClientUpdatehandler;
/*     */ import chb.mods.mffs.network.client.NetworkHandlerClient;
/*     */ import chb.mods.mffs.network.server.ForceFieldServerUpdatehandler;
/*     */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*     */ import com.google.common.collect.Lists;
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.Init;
/*     */ import cpw.mods.fml.common.Mod.Instance;
/*     */ import cpw.mods.fml.common.Mod.PostInit;
/*     */ import cpw.mods.fml.common.Mod.PreInit;
/*     */ import cpw.mods.fml.common.SidedProxy;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.network.NetworkMod;
/*     */ import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*     */ import cpw.mods.fml.common.registry.TickRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.Configuration;
/*     */ import net.minecraftforge.common.ForgeChunkManager;
/*     */ import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
/*     */ import net.minecraftforge.common.ForgeChunkManager.Ticket;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.Property;
/*     */ import net.minecraftforge.event.EventBus;
/*     */ import org.modstats.IModstatsReporter;
/*     */ import org.modstats.ModstatInfo;
/*     */ import org.modstats.Modstats;
/*     */ 
/*     */ @Mod(modid="ModularForceFieldSystem", name="Modular ForceField System", version="2.2.8.3.6", dependencies="after:ThermalExpansion")
/*     */ @NetworkMod(versionBounds="[2.2.8.3.6]", clientSideRequired=true, serverSideRequired=false, clientPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"MFFS"}, packetHandler=NetworkHandlerClient.class), serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"MFFS"}, packetHandler=NetworkHandlerServer.class))
/*     */ @ModstatInfo(prefix="mffs")
/*     */ public class ModularForceFieldSystem
/*     */ {
/*     */   public static CreativeTabs MFFSTab;
/* 127 */   public static int MFFSRENDER_ID = 2908;
/*     */   public static Block MFFSCapacitor;
/*     */   public static Block MFFSProjector;
/*     */   public static Block MFFSDefenceStation;
/*     */   public static Block MFFSFieldblock;
/*     */   public static Block MFFSExtractor;
/*     */   public static Block MFFSMonazitOre;
/*     */   public static Block MFFSForceEnergyConverter;
/*     */   public static Block MFFSSecurtyStorage;
/*     */   public static Block MFFSSecurtyStation;
/*     */   public static Block MFFSControlSystem;
/*     */   public static Item MFFSitemForcicumCell;
/*     */   public static Item MFFSitemForcicium;
/*     */   public static Item MFFSitemForcePowerCrystal;
/*     */   public static Item MFFSitemdensifiedForcicium;
/*     */   public static Item MFFSitemdepletedForcicium;
/*     */   public static Item MFFSitemFocusmatix;
/*     */   public static Item MFFSitemSwitch;
/*     */   public static Item MFFSitemWrench;
/*     */   public static Item MFFSitemFieldTeleporter;
/*     */   public static Item MFFSitemMFDidtool;
/*     */   public static Item MFFSitemMFDdebugger;
/*     */   public static Item MFFSitemcardempty;
/*     */   public static Item MFFSitemfc;
/*     */   public static Item MFFSItemIDCard;
/*     */   public static Item MFFSAccessCard;
/*     */   public static Item MFFSItemSecLinkCard;
/*     */   public static Item MFFSitemManuelBook;
/*     */   public static Item MFFSitemInfinitePowerCard;
/*     */   public static Item MFFSitemDataLinkCard;
/*     */   public static Item MFFSitemupgradeexctractorboost;
/*     */   public static Item MFFSitemupgradecaprange;
/*     */   public static Item MFFSitemupgradecapcap;
/*     */   public static Item MFFSProjectorTypsphere;
/*     */   public static Item MFFSProjectorTypkube;
/*     */   public static Item MFFSProjectorTypwall;
/*     */   public static Item MFFSProjectorTypdeflector;
/*     */   public static Item MFFSProjectorTyptube;
/*     */   public static Item MFFSProjectorTypcontainment;
/*     */   public static Item MFFSProjectorTypAdvCube;
/*     */   public static Item MFFSProjectorTypdiagowall;
/*     */   public static Item MFFSProjectorOptionZapper;
/*     */   public static Item MFFSProjectorOptionSubwater;
/*     */   public static Item MFFSProjectorOptionDome;
/*     */   public static Item MFFSProjectorOptionCutter;
/*     */   public static Item MFFSProjectorOptionMoobEx;
/*     */   public static Item MFFSProjectorOptionDefenceStation;
/*     */   public static Item MFFSProjectorOptionForceFieldJammer;
/*     */   public static Item MFFSProjectorOptionCamouflage;
/*     */   public static Item MFFSProjectorOptionFieldFusion;
/*     */   public static Item MFFSProjectorFFDistance;
/*     */   public static Item MFFSProjectorFFStrenght;
/* 191 */   public static int MonazitOreworldamount = 4;
/*     */   public static int forcefieldblockcost;
/*     */   public static int forcefieldblockcreatemodifier;
/*     */   public static int forcefieldblockzappermodifier;
/*     */   public static int forcefieldtransportcost;
/*     */   public static int forcefieldmaxblockpeerTick;
/*     */   public static Boolean forcefieldremoveonlywaterandlava;
/*     */   public static Boolean influencedbyothermods;
/*     */   public static Boolean adventuremap;
/* 204 */   public static Boolean ic2found = Boolean.valueOf(false);
/* 205 */   public static Boolean uefound = Boolean.valueOf(false);
/* 206 */   public static Boolean ee3found = Boolean.valueOf(false);
/* 207 */   public static Boolean buildcraftfound = Boolean.valueOf(false);
/* 208 */   public static Boolean ThermalExpansionfound = Boolean.valueOf(false);
/*     */   public static boolean showZapperParticles;
/*     */   public static boolean uumatterForcicium;
/* 211 */   public static boolean chunckloader = true;
/*     */   public static int ForceciumWorkCylce;
/*     */   public static int ForceciumCellWorkCylce;
/*     */   public static int ExtractorPassForceEnergyGenerate;
/*     */   public static int DefenceStationKillForceEnergy;
/*     */   public static int DefenceStationSearchForceEnergy;
/*     */   public static int DefenceStationScannForceEnergy;
/*     */   public static boolean DefenceStationNPCScannsuppressnotification;
/*     */   public static int graphicstyle;
/*     */   public static Configuration MFFSconfig;
/*     */   public static String Admin;
/*     */   public static String Versionlocal;
/*     */   public static String Versionremote;
/*     */ 
/*     */   @SidedProxy(clientSide="chb.mods.mffs.client.ClientProxy", serverSide="chb.mods.mffs.common.CommonProxy")
/*     */   public static CommonProxy proxy;
/*     */ 
/*     */   @Mod.Instance("ModularForceFieldSystem")
/*     */   public static ModularForceFieldSystem instance;
/*     */ 
/*     */   @Mod.PreInit
/*     */   public void preInit(FMLPreInitializationEvent event)
/*     */   {
/* 244 */     initIC2Plugin();
/* 245 */     inituEPlugin();
/* 246 */     initbuildcraftPlugin();
/* 247 */     initEE3Plugin();
/* 248 */     ThermalExpansionPlugin();
/*     */ 
/* 250 */     Versionlocal = Versioninfo.curentversion();
/* 251 */     Versionremote = Versioninfo.newestversion();
/*     */ 
/* 253 */     MinecraftForge.EVENT_BUS.register(this);
/* 254 */     MinecraftForge.EVENT_BUS.register(proxy);
/*     */ 
/* 256 */     Modstats.instance().getReporter().registerMod(this);
/*     */ 
/* 258 */     if (ee3found.booleanValue()) {
/* 259 */       MinecraftForge.EVENT_BUS.register(new EE3Event());
/*     */     }
/* 261 */     TickRegistry.registerScheduledTickHandler(new ForceFieldClientUpdatehandler(), Side.CLIENT);
/*     */ 
/* 263 */     TickRegistry.registerScheduledTickHandler(new ForceFieldServerUpdatehandler(), Side.SERVER);
/*     */ 
/* 267 */     MFFSconfig = new Configuration(event.getSuggestedConfigurationFile());
/* 268 */     event.getModMetadata().version = Versioninfo.curentversion();
/*     */     try {
/* 270 */       MFFSconfig.load();
/* 271 */       MFFSTab = new MFFSCreativeTab(CreativeTabs.getNextID(), "MFFS");
/*     */ 
/* 273 */       Property prop_graphicstyle = MFFSconfig.get("general", "GraphicStyle", 1);
/* 274 */       prop_graphicstyle.comment = "Set Graphic Style for Blocks 0: Default(ugly ;-)) 1: UE-Thema";
/* 275 */       graphicstyle = prop_graphicstyle.getInt(1);
/*     */ 
/* 277 */       Property chunckloader_prop = MFFSconfig.get("general", "Chunkloader", true);
/* 278 */       chunckloader_prop.comment = "Set this to false to turn off the MFFS Chuncloader ability";
/* 279 */       chunckloader = chunckloader_prop.getBoolean(true);
/*     */ 
/* 281 */       Property DefSationNPCScannoti = MFFSconfig.get("general", "DefenceStationNPCScannnotification", false);
/* 282 */       DefSationNPCScannoti.comment = "Set this to true to turn off the DefenceStation notification is in NPC Mode";
/* 283 */       DefenceStationNPCScannsuppressnotification = DefSationNPCScannoti.getBoolean(false);
/*     */ 
/* 285 */       Property zapperParticles = MFFSconfig.get("general", "renderZapperParticles", true);
/* 286 */       zapperParticles.comment = "Set this to false to turn off the small smoke particles present around TouchDamage enabled ForceFields.";
/* 287 */       showZapperParticles = zapperParticles.getBoolean(true);
/*     */ 
/* 289 */       Property uumatterForciciumprop = MFFSconfig.get("general", "uumatterForcicium", true);
/* 290 */       uumatterForciciumprop.comment = "Add IC2 UU-Matter Recipes for Forcicium";
/* 291 */       uumatterForcicium = uumatterForciciumprop.getBoolean(true);
/*     */ 
/* 293 */       Property monazitWorldAmount = MFFSconfig.get("general", "MonazitOreWorldGen", 4);
/* 294 */       monazitWorldAmount.comment = "Controls the size of the ore node that Monazit Ore will generate in";
/* 295 */       MonazitOreworldamount = monazitWorldAmount.getInt(4);
/*     */ 
/* 297 */       Property adminList = MFFSconfig.get("general", "ForceFieldMaster", "nobody");
/* 298 */       adminList.comment = "Add users to this list to give them admin permissions split by ;";
/* 299 */       Admin = adminList.value;
/*     */ 
/* 301 */       Property influencedByOther = MFFSconfig.get("general", "influencedbyothermods", true);
/* 302 */       influencedByOther.comment = "Should MFFS be influenced by other mods. e.g. ICBM's EMP";
/* 303 */       influencedbyothermods = Boolean.valueOf(influencedByOther.getBoolean(true));
/*     */ 
/* 305 */       Property ffRemoveWaterLavaOnly = MFFSconfig.get("general", "forcefieldremoveonlywaterandlava", false);
/* 306 */       ffRemoveWaterLavaOnly.comment = "Should forcefields only remove water and lava when sponge is enabled?";
/* 307 */       forcefieldremoveonlywaterandlava = Boolean.valueOf(ffRemoveWaterLavaOnly.getBoolean(false));
/*     */ 
/* 310 */       Property feTransportCost = MFFSconfig.get("general", "forcefieldtransportcost", 10000);
/* 311 */       feTransportCost.comment = "How much FE should it cost to transport through a field?";
/* 312 */       forcefieldtransportcost = feTransportCost.getInt(10000);
/*     */ 
/* 314 */       Property feFieldBlockCost = MFFSconfig.get("general", "forcefieldblockcost", 1);
/* 315 */       feFieldBlockCost.comment = "How much upkeep FE cost a default ForceFieldblock per second";
/* 316 */       forcefieldblockcost = feFieldBlockCost.getInt(1);
/*     */ 
/* 318 */       Property BlockCreateMod = MFFSconfig.get("general", "forcefieldblockcreatemodifier", 10);
/* 319 */       BlockCreateMod.comment = "Energy need for create a ForceFieldblock (forcefieldblockcost*forcefieldblockcreatemodifier)";
/* 320 */       forcefieldblockcreatemodifier = BlockCreateMod.getInt(10);
/*     */ 
/* 322 */       Property ffZapperMod = MFFSconfig.get("general", "forcefieldblockzappermodifier", 2);
/* 323 */       ffZapperMod.comment = "Energy need multiplier used when the zapper option is installed";
/* 324 */       forcefieldblockzappermodifier = ffZapperMod.getInt(2);
/*     */ 
/* 326 */       Property maxFFGenPerTick = MFFSconfig.get("general", "forcefieldmaxblockpeerTick", 5000);
/* 327 */       maxFFGenPerTick.comment = "How many field blocks can be generated per tick?";
/* 328 */       forcefieldmaxblockpeerTick = maxFFGenPerTick.getInt(5000);
/*     */ 
/* 331 */       Property fcWorkCycle = MFFSconfig.get("general", "ForceciumWorkCylce", 250);
/* 332 */       fcWorkCycle.comment = "WorkCycle amount of Forcecium inside a Extractor";
/* 333 */       ForceciumWorkCylce = fcWorkCycle.getInt(250);
/*     */ 
/* 335 */       Property fcCellWorkCycle = MFFSconfig.get("general", "ForceciumCellWorkCylce", 230);
/* 336 */       fcCellWorkCycle.comment = "WorkCycle amount of Forcecium Cell inside a Extractor";
/* 337 */       ForceciumCellWorkCylce = fcCellWorkCycle.getInt(230);
/*     */ 
/* 339 */       Property extractorPassFEGen = MFFSconfig.get("general", "ExtractorPassForceEnergyGenerate", 12000);
/* 340 */       extractorPassFEGen.comment = "How many ForceEnergy generate per WorkCycle";
/* 341 */       ExtractorPassForceEnergyGenerate = extractorPassFEGen.getInt(12000);
/*     */ 
/* 343 */       ExtractorPassForceEnergyGenerate = ExtractorPassForceEnergyGenerate / 4000 * 4000;
/*     */ 
/* 345 */       Property defStationKillCost = MFFSconfig.get("general", "DefenceStationKillForceEnergy", 10000);
/* 346 */       defStationKillCost.comment = "How much FE does the AreaDefenseStation when killing someone";
/* 347 */       DefenceStationKillForceEnergy = defStationKillCost.getInt(10000);
/*     */ 
/* 349 */       Property defStationSearchCost = MFFSconfig.get("general", "DefenceStationSearchForceEnergy", 1000);
/* 350 */       defStationSearchCost.comment = "How much FE does the AreaDefenseStation when searching someone for contraband";
/* 351 */       DefenceStationSearchForceEnergy = defStationSearchCost.getInt(1000);
/*     */ 
/* 353 */       Property defStationScannCost = MFFSconfig.get("general", "DefenceStationScannForceEnergy", 10);
/* 354 */       defStationScannCost.comment = "How much FE does the AreaDefenseStation when Scann for Targets (amount * range / tick)";
/* 355 */       DefenceStationScannForceEnergy = defStationScannCost.getInt(10);
/*     */ 
/* 357 */       Property Adventuremap = MFFSconfig.get("general", "adventuremap", false);
/* 358 */       Adventuremap.comment = "Set MFFS to AdventureMap Mode Extractor need no Forcecium and ForceField have no click damage";
/* 359 */       adventuremap = Boolean.valueOf(Adventuremap.getBoolean(false));
/*     */ 
/* 363 */       MFFSForceEnergyConverter = new BlockConverter(MFFSconfig.getBlock("MFFSForceEnergyConverter", DefaultProps.block_Converter_ID).getInt(DefaultProps.block_Converter_ID)).setBlockName("MFFSForceEnergyConverter");
/* 364 */       MFFSExtractor = new BlockExtractor(MFFSconfig.getBlock("MFFSExtractor", DefaultProps.block_Extractor_ID).getInt(DefaultProps.block_Extractor_ID)).setBlockName("MFFSExtractor");
/* 365 */       MFFSMonazitOre = new BlockMonazitOre(MFFSconfig.getBlock("MFFSMonazitOre", DefaultProps.block_MonazitOre_ID).getInt(DefaultProps.block_MonazitOre_ID)).setBlockName("MFFSMonazitOre");
/* 366 */       MFFSDefenceStation = new BlockAreaDefenseStation(MFFSconfig.getBlock("MFFSDefenceStation", DefaultProps.block_DefenseStation_ID).getInt(DefaultProps.block_DefenseStation_ID)).setBlockName("MFFSDefenceStation");
/* 367 */       MFFSCapacitor = new BlockCapacitor(MFFSconfig.getBlock("MFFSCapacitor", DefaultProps.block_Capacitor_ID).getInt(DefaultProps.block_Capacitor_ID)).setBlockName("MFFSCapacitor");
/* 368 */       MFFSProjector = new BlockProjector(MFFSconfig.getBlock("MFFSProjector", DefaultProps.block_Projector_ID).getInt(DefaultProps.block_Projector_ID)).setBlockName("MFFSProjector");
/* 369 */       MFFSFieldblock = new BlockForceField(MFFSconfig.getBlock("MFFSFieldblock", DefaultProps.block_Field_ID).getInt(DefaultProps.block_Field_ID));
/* 370 */       MFFSSecurtyStorage = new BlockSecurtyStorage(MFFSconfig.getBlock("MFFSSecurtyStorage", DefaultProps.block_SecureStorage_ID).getInt(DefaultProps.block_SecureStorage_ID)).setBlockName("MFFSSecurtyStorage");
/* 371 */       MFFSSecurtyStation = new BlockAdvSecurtyStation(MFFSconfig.getBlock("MFFSSecurtyStation", DefaultProps.block_SecurityStation_ID).getInt(DefaultProps.block_SecurityStation_ID)).setBlockName("MFFSSecurtyStation");
/* 372 */       MFFSControlSystem = new BlockControlSystem(MFFSconfig.getBlock("MFFSControlSystem", DefaultProps.block_ControlSystem).getInt(DefaultProps.block_ControlSystem)).setBlockName("MFFSControlSystem");
/*     */ 
/* 375 */       MFFSProjectorFFDistance = new ItemProjectorFieldModulatorDistance(MFFSconfig.getItem("item", "itemProjectorFFDistance", DefaultProps.item_AltDistance_ID).getInt(DefaultProps.item_AltDistance_ID)).setItemName("itemProjectorFFDistance");
/* 376 */       MFFSProjectorFFStrenght = new ItemProjectorFieldModulatorStrength(MFFSconfig.getItem("item", "itemProjectorFFStrength", DefaultProps.item_AltStrength_ID).getInt(DefaultProps.item_AltStrength_ID)).setItemName("itemProjectorFFStrength");
/* 377 */       MFFSitemFocusmatix = new ItemProjectorFocusMatrix(MFFSconfig.getItem("item", "itemPorjectorFocusmatrix", DefaultProps.item_FocusMatrix_ID).getInt(DefaultProps.item_FocusMatrix_ID)).setItemName("itemPorjectorFocusmatrix");
/* 378 */       MFFSitemForcePowerCrystal = new ItemForcePowerCrystal(MFFSconfig.getItem("item", "itemForcePowerCrystal", DefaultProps.item_FPCrystal_ID).getInt(DefaultProps.item_FPCrystal_ID)).setItemName("itemForcePowerCrystal");
/* 379 */       MFFSitemForcicium = new ItemForcicium(MFFSconfig.getItem("item", "itemForcicium", DefaultProps.item_Forcicium_ID).getInt(DefaultProps.item_Forcicium_ID)).setItemName("itemForcicium");
/* 380 */       MFFSitemForcicumCell = new ItemForcicumCell(MFFSconfig.getItem("item", "itemForcicumCell", DefaultProps.item_ForciciumCell_ID).getInt(DefaultProps.item_ForciciumCell_ID)).setItemName("itemForcicumCell");
/*     */ 
/* 383 */       MFFSProjectorTypdiagowall = new ItemProjectorModulediagonallyWall(MFFSconfig.getItem("item", "itemProjectorModulediagonallyWall", DefaultProps.item_ModDiag_ID).getInt(DefaultProps.item_ModDiag_ID)).setItemName("itemProjectorModulediagonallyWall");
/* 384 */       MFFSProjectorTypsphere = new ItemProjectorModuleSphere(MFFSconfig.getItem("item", "itemProjectorTypsphere", DefaultProps.item_ModSphere_ID).getInt(DefaultProps.item_ModSphere_ID)).setItemName("itemProjectorTypsphere");
/* 385 */       MFFSProjectorTypkube = new ItemProjectorModuleCube(MFFSconfig.getItem("item", "itemProjectorTypkube", DefaultProps.item_ModCube_ID).getInt(DefaultProps.item_ModCube_ID)).setItemName("itemProjectorTypkube");
/* 386 */       MFFSProjectorTypwall = new ItemProjectorModuleWall(MFFSconfig.getItem("item", "itemProjectorTypwall", DefaultProps.item_ModWall_ID).getInt(DefaultProps.item_ModWall_ID)).setItemName("itemProjectorTypwall");
/* 387 */       MFFSProjectorTypdeflector = new ItemProjectorModuleDeflector(MFFSconfig.getItem("item", "itemProjectorTypdeflector", DefaultProps.item_ModDeflector_ID).getInt(DefaultProps.item_ModDeflector_ID)).setItemName("itemProjectorTypdeflector");
/* 388 */       MFFSProjectorTyptube = new ItemProjectorModuleTube(MFFSconfig.getItem("item", "itemProjectorTyptube", DefaultProps.item_ModTube_ID).getInt(DefaultProps.item_ModTube_ID)).setItemName("itemProjectorTyptube");
/* 389 */       MFFSProjectorTypcontainment = new ItemProjectorModuleContainment(MFFSconfig.getItem("item", "itemProjectorModuleContainment", DefaultProps.item_ModContainment_ID).getInt(DefaultProps.item_ModContainment_ID)).setItemName("itemProjectorModuleContainment");
/* 390 */       MFFSProjectorTypAdvCube = new ItemProjectorModuleAdvCube(MFFSconfig.getItem("item", "itemProjectorModuleAdvCube", DefaultProps.item_ModAdvCube_ID).getInt(DefaultProps.item_ModAdvCube_ID)).setItemName("itemProjectorModuleAdvCube");
/*     */ 
/* 393 */       MFFSProjectorOptionZapper = new ItemProjectorOptionTouchDamage(MFFSconfig.getItem("item", "itemupgradeprozapper", DefaultProps.item_OptTouchHurt_ID).getInt(DefaultProps.item_OptTouchHurt_ID)).setItemName("itemupgradeprozapper");
/* 394 */       MFFSProjectorOptionSubwater = new ItemProjectorOptionSponge(MFFSconfig.getItem("item", "itemupgradeprosubwater", DefaultProps.item_OptSponge_ID).getInt(DefaultProps.item_OptSponge_ID)).setItemName("itemupgradeprosubwater");
/* 395 */       MFFSProjectorOptionDome = new ItemProjectorOptionFieldManipulator(MFFSconfig.getItem("item", "itemupgradeprodome", DefaultProps.item_OptManipulator_ID).getInt(DefaultProps.item_OptManipulator_ID)).setItemName("itemupgradeprodome");
/* 396 */       MFFSProjectorOptionCutter = new ItemProjectorOptionBlockBreaker(MFFSconfig.getItem("item", "itemUpgradeprocutter", DefaultProps.item_OptBlockBreaker_ID).getInt(DefaultProps.item_OptBlockBreaker_ID)).setItemName("itemUpgradeprocutter");
/* 397 */       MFFSProjectorOptionDefenceStation = new ItemProjectorOptionDefenseStation(MFFSconfig.getItem("item", "itemProjectorOptiondefencestation", DefaultProps.item_OptDefense_ID).getInt(DefaultProps.item_OptDefense_ID)).setItemName("itemProjectorOptiondefencestation");
/* 398 */       MFFSProjectorOptionMoobEx = new ItemProjectorOptionMobDefence(MFFSconfig.getItem("item", "itemProjectorOptionMoobEx", DefaultProps.item_OptMobDefense_ID).getInt(DefaultProps.item_OptMobDefense_ID)).setItemName("itemProjectorOptionMoobEx");
/* 399 */       MFFSProjectorOptionForceFieldJammer = new ItemProjectorOptionForceFieldJammer(MFFSconfig.getItem("item", "itemProjectorOptionFFJammer", DefaultProps.item_OptJammer_ID).getInt(DefaultProps.item_OptJammer_ID)).setItemName("itemProjectorOptionFFJammer");
/* 400 */       MFFSProjectorOptionCamouflage = new ItemProjectorOptionCamoflage(MFFSconfig.getItem("item", "itemProjectorOptionCamoflage", DefaultProps.item_OptCamouflage_ID).getInt(DefaultProps.item_OptCamouflage_ID)).setItemName("itemProjectorOptionCamoflage");
/* 401 */       MFFSProjectorOptionFieldFusion = new ItemProjectorOptionFieldFusion(MFFSconfig.getItem("item", "itemProjectorOptionFieldFusion", DefaultProps.item_OptFusion_ID).getInt(DefaultProps.item_OptFusion_ID)).setItemName("itemProjectorOptionFieldFusion");
/*     */ 
/* 404 */       MFFSitemcardempty = new ItemCardEmpty(MFFSconfig.getItem("item", "itemcardempty", DefaultProps.item_BlankCard_ID).getInt(DefaultProps.item_BlankCard_ID)).setItemName("itemcardempty");
/* 405 */       MFFSitemfc = new ItemCardPowerLink(MFFSconfig.getItem("item", "itemfc", DefaultProps.item_CardPowerLink_ID).getInt(DefaultProps.item_CardPowerLink_ID)).setItemName("itemfc");
/* 406 */       MFFSItemIDCard = new ItemCardPersonalID(MFFSconfig.getItem("item", "itemIDCard", DefaultProps.item_CardPersonalID_ID).getInt(DefaultProps.item_CardPersonalID_ID)).setItemName("itemIDCard");
/* 407 */       MFFSItemSecLinkCard = new ItemCardSecurityLink(MFFSconfig.getItem("item", "itemSecLinkCard", DefaultProps.item_CardSecurityLink_ID).getInt(DefaultProps.item_CardSecurityLink_ID)).setItemName("itemSecLinkCard");
/* 408 */       MFFSitemInfinitePowerCard = new ItemCardPower(MFFSconfig.getItem("item", "itemInfinitePower", DefaultProps.item_infPowerCard_ID).getInt(DefaultProps.item_infPowerCard_ID)).setItemName("itemInfPowerCard");
/* 409 */       MFFSAccessCard = new ItemAccessCard(MFFSconfig.getItem("item", "itemAccessCard", DefaultProps.item_CardAccess_ID).getInt(DefaultProps.item_CardAccess_ID)).setItemName("itemAccessCard");
/* 410 */       MFFSitemDataLinkCard = new ItemCardDataLink(MFFSconfig.getItem("item", "itemCardDataLink", DefaultProps.item_CardDataLink_ID).getInt(DefaultProps.item_CardDataLink_ID)).setItemName("itemCardDataLink");
/*     */ 
/* 413 */       MFFSitemWrench = new ItemWrench(MFFSconfig.getItem("item", "itemWrench", DefaultProps.item_MTWrench_ID).getInt(DefaultProps.item_MTWrench_ID)).setItemName("itemWrench");
/* 414 */       MFFSitemSwitch = new ItemSwitch(MFFSconfig.getItem("item", "itemSwitch", DefaultProps.item_MTSwitch_ID).getInt(DefaultProps.item_MTSwitch_ID)).setItemName("itemSwitch");
/* 415 */       MFFSitemFieldTeleporter = new ItemFieldtransporter(MFFSconfig.getItem("item", "itemForceFieldsync", DefaultProps.item_MTFieldTransporter_ID).getInt(DefaultProps.item_MTFieldTransporter_ID)).setItemName("itemForceFieldsync");
/* 416 */       MFFSitemMFDidtool = new ItemPersonalIDWriter(MFFSconfig.getItem("item", "ItemMFDIDwriter", DefaultProps.item_MTIDWriter_ID).getInt(DefaultProps.item_MTIDWriter_ID)).setItemName("ItemMFDIDwriter");
/* 417 */       MFFSitemMFDdebugger = new ItemDebugger(MFFSconfig.getItem("item", "itemMFDdebugger", DefaultProps.item_MTDebugger_ID).getInt(DefaultProps.item_MTDebugger_ID)).setItemName("itemMFDdebugger");
/* 418 */       MFFSitemManuelBook = new ItemManuelBook(MFFSconfig.getItem("item", "itemManuelBook", DefaultProps.item_MTManual_ID).getInt(DefaultProps.item_MTManual_ID)).setItemName("itemManuelBook");
/*     */ 
/* 421 */       MFFSitemupgradeexctractorboost = new ItemExtractorUpgradeBooster(MFFSconfig.getItem("item", "itemextractorbooster", DefaultProps.item_upgradeBoost_ID).getInt(DefaultProps.item_upgradeBoost_ID)).setItemName("itemextractorbooster");
/* 422 */       MFFSitemupgradecaprange = new ItemCapacitorUpgradeRange(MFFSconfig.getItem("item", "itemupgradecaprange", DefaultProps.item_upgradeRange_ID).getInt(DefaultProps.item_upgradeRange_ID)).setItemName("itemupgradecaprange");
/* 423 */       MFFSitemupgradecapcap = new ItemCapacitorUpgradeCapacity(MFFSconfig.getItem("item", "itemupgradecapcap", DefaultProps.item_upgradeCap_ID).getInt(DefaultProps.item_upgradeCap_ID)).setItemName("itemupgradecapcap");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 429 */       FMLLog.log(Level.SEVERE, e, "ModularForceFieldSystem has a problem loading it's configuration", new Object[0]);
/*     */ 
/* 431 */       System.out.println(e.getMessage());
/*     */     } finally {
/* 433 */       MFFSconfig.save();
/*     */     }
/*     */   }
/*     */ 
/*     */   @Mod.Init
/*     */   public void load(FMLInitializationEvent evt)
/*     */   {
/* 441 */     GameRegistry.registerBlock(MFFSMonazitOre, "MFFSMonazitOre");
/* 442 */     GameRegistry.registerBlock(MFFSFieldblock, "MFFSFieldblock");
/* 443 */     GameRegistry.registerTileEntity(TileEntityForceField.class, "MFFSForceField");
/*     */ 
/* 445 */     MFFSMaschines.initialize();
/* 446 */     ProjectorTyp.initialize();
/* 447 */     ProjectorOptions.initialize();
/*     */ 
/* 449 */     NetworkRegistry.instance().registerGuiHandler(instance, proxy);
/*     */ 
/* 451 */     proxy.registerRenderInformation();
/* 452 */     proxy.registerTileEntitySpecialRenderer();
/*     */ 
/* 454 */     GameRegistry.registerWorldGenerator(new MFFSWorldGenerator());
/*     */ 
/* 456 */     LanguageRegistry.instance().addNameForObject(MFFSitemInfinitePowerCard, "en_US", "MFFS Infinite Power Card");
/*     */ 
/* 458 */     LanguageRegistry.instance().addNameForObject(MFFSitemupgradeexctractorboost, "en_US", "MFFS Extractor Booster");
/*     */ 
/* 461 */     LanguageRegistry.instance().addNameForObject(MFFSMonazitOre, "en_US", "Monazit Ore");
/*     */ 
/* 463 */     LanguageRegistry.instance().addNameForObject(MFFSitemForcicumCell, "en_US", "MFFS compact Forcicium Cell");
/*     */ 
/* 465 */     LanguageRegistry.instance().addNameForObject(MFFSitemForcicium, "en_US", "Forcicium");
/*     */ 
/* 467 */     LanguageRegistry.instance().addNameForObject(MFFSitemForcePowerCrystal, "en_US", "MFFS Force Energy Crystal");
/*     */ 
/* 469 */     LanguageRegistry.instance().addNameForObject(MFFSitemSwitch, "en_US", "MFFS MultiTool <Switch>");
/*     */ 
/* 471 */     LanguageRegistry.instance().addNameForObject(MFFSitemWrench, "en_US", "MFFS MultiTool <Wrench>");
/*     */ 
/* 473 */     LanguageRegistry.instance().addNameForObject(MFFSitemManuelBook, "en_US", "MFFS MultiTool <Guide>");
/*     */ 
/* 475 */     LanguageRegistry.instance().addNameForObject(MFFSitemFocusmatix, "en_US", "MFFS Projector Focus Matrix");
/*     */ 
/* 477 */     LanguageRegistry.instance().addNameForObject(MFFSitemFieldTeleporter, "en_US", "MFFS MultiTool <Field Teleporter>");
/*     */ 
/* 480 */     LanguageRegistry.instance().addNameForObject(MFFSitemDataLinkCard, "en_US", "MFFS Card <Data Link> ");
/*     */ 
/* 483 */     LanguageRegistry.instance().addNameForObject(MFFSAccessCard, "en_US", "MFFS Card <Access license> ");
/*     */ 
/* 485 */     LanguageRegistry.instance().addNameForObject(MFFSitemcardempty, "en_US", "MFFS Card <blank> ");
/*     */ 
/* 487 */     LanguageRegistry.instance().addNameForObject(MFFSitemfc, "en_US", "MFFS Card <Power Link>");
/*     */ 
/* 489 */     LanguageRegistry.instance().addNameForObject(MFFSItemIDCard, "en_US", "MFFS Card <Personal ID>");
/*     */ 
/* 491 */     LanguageRegistry.instance().addNameForObject(MFFSItemSecLinkCard, "en_US", "MFFS Card <Security Station Link> ");
/*     */ 
/* 493 */     LanguageRegistry.instance().addNameForObject(MFFSitemMFDdebugger, "en_US", "MFFS MultiTool <Debugger>");
/*     */ 
/* 495 */     LanguageRegistry.instance().addNameForObject(MFFSitemMFDidtool, "en_US", "MFFS MultiTool <PersonalID & Data Link  Coder>");
/*     */ 
/* 497 */     LanguageRegistry.instance().addNameForObject(MFFSitemupgradecaprange, "en_US", "MFFS Capacitor Upgrade <Range> ");
/*     */ 
/* 499 */     LanguageRegistry.instance().addNameForObject(MFFSitemupgradecapcap, "en_US", "MFFS Capacitor Upgrade <Capacity> ");
/*     */ 
/* 502 */     LanguageRegistry.instance().addNameForObject(MFFSProjectorFFDistance, "en_US", "MFFS Projector Field Modulator <distance>");
/*     */ 
/* 504 */     LanguageRegistry.instance().addNameForObject(MFFSProjectorFFStrenght, "en_US", "MFFS Projector Field Modulator <strength>");
/*     */ 
/* 507 */     LanguageRegistry.instance().addStringLocalization("itemGroup.MFFS", "en_US", "Modular Force Field System");
/*     */ 
/* 510 */     LanguageRegistry.instance().addStringLocalization("death.areaDefense", "en_US", "%1$s disregarded warnings and was fried");
/* 511 */     LanguageRegistry.instance().addStringLocalization("death.fieldShock", "en_US", "%1$s was fried by a forcefield");
/* 512 */     LanguageRegistry.instance().addStringLocalization("death.fieldDefense", "en_US", "%1$s was fried");
/*     */   }
/*     */ 
/*     */   @Mod.PostInit
/*     */   public void postInit(FMLPostInitializationEvent evt)
/*     */   {
/* 519 */     MFFSRecipes.init();
/* 520 */     ForgeChunkManager.setForcedChunkLoadingCallback(instance, new MFFSChunkloadCallback());
/*     */   }
/*     */ 
/*     */   public void initbuildcraftPlugin()
/*     */   {
/* 566 */     System.out.println("[ModularForceFieldSystem] Loading module for Buildcraft");
/*     */     try
/*     */     {
/* 570 */       Class.forName("buildcraft.core.Version");
/* 571 */       buildcraftfound = Boolean.valueOf(true);
/*     */     }
/*     */     catch (Throwable t) {
/* 574 */       System.out.println("[ModularForceFieldSystem] Module not loaded: Buildcraft not found");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ThermalExpansionPlugin()
/*     */   {
/* 582 */     System.out.println("[ModularForceFieldSystem] Loading module for ThermalExpansion");
/*     */     try
/*     */     {
/* 586 */       Class.forName("thermalexpansion.ThermalExpansion");
/* 587 */       ThermalExpansionfound = Boolean.valueOf(true);
/*     */     }
/*     */     catch (Throwable t) {
/* 590 */       System.out.println("[ModularForceFieldSystem] Module not loaded: ThermalExpansion not found");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initEE3Plugin()
/*     */   {
/* 597 */     System.out.println("[ModularForceFieldSystem] Loading module for EE3");
/*     */     try
/*     */     {
/* 601 */       Class.forName("com.pahimar.ee3.event.ActionRequestEvent");
/* 602 */       ee3found = Boolean.valueOf(true);
/*     */     }
/*     */     catch (Throwable t) {
/* 605 */       System.out.println("[ModularForceFieldSystem] Module not loaded: EE3 not found");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void inituEPlugin()
/*     */   {
/* 613 */     System.out.println("[ModularForceFieldSystem] Loading module for Universal Electricity");
/*     */     try
/*     */     {
/* 617 */       Class.forName("basiccomponents.common.item.ItemBasic");
/* 618 */       uefound = Boolean.valueOf(true);
/*     */     }
/*     */     catch (Throwable t) {
/* 621 */       System.out.println("[ModularForceFieldSystem] Module not loaded: Universal Electricity not found");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initIC2Plugin()
/*     */   {
/* 630 */     System.out.println("[ModularForceFieldSystem] Loading module for IC2");
/*     */     try
/*     */     {
/* 634 */       Class.forName("ic2.core.IC2");
/* 635 */       ic2found = Boolean.valueOf(true);
/*     */     }
/*     */     catch (Throwable t) {
/* 638 */       System.out.println("[ModularForceFieldSystem] Module not loaded: IC2 not found");
/*     */     }
/*     */   }
/*     */ 
/*     */   public class MFFSChunkloadCallback
/*     */     implements ForgeChunkManager.OrderedLoadingCallback
/*     */   {
/*     */     public MFFSChunkloadCallback()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void ticketsLoaded(List tickets, World world)
/*     */     {
/* 529 */       for (ForgeChunkManager.Ticket ticket : tickets) {
/* 530 */         int MaschineX = ticket.getModData().getInteger("MaschineX");
/* 531 */         int MaschineY = ticket.getModData().getInteger("MaschineY");
/* 532 */         int MaschineZ = ticket.getModData().getInteger("MaschineZ");
/* 533 */         TileEntityMachines Machines = (TileEntityMachines)world.getBlockTileEntity(MaschineX, MaschineY, MaschineZ);
/*     */ 
/* 535 */         Machines.forceChunkLoading(ticket);
/*     */       }
/*     */     }
/*     */ 
/*     */     public List ticketsLoaded(List tickets, World world, int maxTicketCount)
/*     */     {
/* 543 */       List validTickets = Lists.newArrayList();
/* 544 */       for (ForgeChunkManager.Ticket ticket : tickets) {
/* 545 */         int MaschineX = ticket.getModData().getInteger("MaschineX");
/* 546 */         int MaschineY = ticket.getModData().getInteger("MaschineY");
/* 547 */         int MaschineZ = ticket.getModData().getInteger("MaschineZ");
/*     */ 
/* 549 */         TileEntity tileEntity = world.getBlockTileEntity(MaschineX, MaschineY, MaschineZ);
/*     */ 
/* 551 */         if ((tileEntity instanceof TileEntityMachines)) {
/* 552 */           validTickets.add(ticket);
/*     */         }
/*     */       }
/* 555 */       return validTickets;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.ModularForceFieldSystem
 * JD-Core Version:    0.6.2
 */