/*      */ package chb.mods.mffs.common.tileentity;
/*      */ 
/*      */ import chb.mods.mffs.api.IPowerLinkItem;
/*      */ import chb.mods.mffs.api.PointXYZ;
/*      */ import chb.mods.mffs.common.ForceFieldBlockStack;
/*      */ import chb.mods.mffs.common.ForceFieldTyps;
/*      */ import chb.mods.mffs.common.Functions;
/*      */ import chb.mods.mffs.common.IModularProjector;
/*      */ import chb.mods.mffs.common.IModularProjector.Slots;
/*      */ import chb.mods.mffs.common.InventoryHelper;
/*      */ import chb.mods.mffs.common.Linkgrid;
/*      */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*      */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*      */ import chb.mods.mffs.common.ProjectorTyp;
/*      */ import chb.mods.mffs.common.WorldMap;
/*      */ import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
/*      */ import chb.mods.mffs.common.container.ContainerProjector;
/*      */ import chb.mods.mffs.common.item.ItemCardSecurityLink;
/*      */ import chb.mods.mffs.common.item.ItemProjectorFieldModulatorDistance;
/*      */ import chb.mods.mffs.common.item.ItemProjectorFieldModulatorStrength;
/*      */ import chb.mods.mffs.common.item.ItemProjectorFocusMatrix;
/*      */ import chb.mods.mffs.common.modules.Module3DBase;
/*      */ import chb.mods.mffs.common.modules.ModuleBase;
/*      */ import chb.mods.mffs.common.options.IChecksOnAll;
/*      */ import chb.mods.mffs.common.options.IInteriorCheck;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionBase;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionCamoflage;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionFieldFusion;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionForceFieldJammer;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionMobDefence;
/*      */ import chb.mods.mffs.common.options.ItemProjectorOptionTouchDamage;
/*      */ import chb.mods.mffs.network.server.NetworkHandlerServer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraftforge.common.ForgeDirection;
/*      */ 
/*      */ public class TileEntityProjector extends TileEntityFEPoweredMachine
/*      */   implements IModularProjector
/*      */ {
/*      */   private ItemStack[] ProjektorItemStacks;
/*   77 */   private int[] focusmatrix = { 0, 0, 0, 0 };
/*   78 */   private String ForceFieldTexturids = "-76/-76/-76/-76/-76/-76";
/*   79 */   private String ForceFieldTexturfile = "/terrain.png";
/*      */   private int ForcefieldCamoblockid;
/*      */   private int ForcefieldCamoblockmeta;
/*      */   private int switchdelay;
/*      */   private short forcefieldblock_meta;
/*      */   private int ProjektorTyp;
/*      */   private int linkPower;
/*      */   private int blockcounter;
/*      */   private boolean burnout;
/*      */   private int accesstyp;
/*      */   private int capacity;
/*   92 */   protected Stack field_queue = new Stack();
/*   93 */   protected Set field_interior = new HashSet();
/*   94 */   protected Set field_def = new HashSet();
/*      */ 
/*      */   public TileEntityProjector() {
/*   97 */     Random random = new Random();
/*      */ 
/*   99 */     this.ProjektorItemStacks = new ItemStack[13];
/*  100 */     this.linkPower = 0;
/*  101 */     this.forcefieldblock_meta = ((short)ForceFieldTyps.Default.ordinal());
/*  102 */     this.ProjektorTyp = 0;
/*  103 */     this.switchdelay = 0;
/*  104 */     this.burnout = false;
/*  105 */     this.accesstyp = 0;
/*  106 */     this.capacity = 0;
/*      */   }
/*      */ 
/*      */   public int getCapacity()
/*      */   {
/*  112 */     return this.capacity;
/*      */   }
/*      */ 
/*      */   public void setCapacity(int Capacity) {
/*  116 */     this.capacity = Capacity;
/*      */   }
/*      */ 
/*      */   public int getaccesstyp()
/*      */   {
/*  121 */     return this.accesstyp;
/*      */   }
/*      */ 
/*      */   public void setaccesstyp(int accesstyp) {
/*  125 */     this.accesstyp = accesstyp;
/*      */   }
/*      */ 
/*      */   public int getForcefieldCamoblockmeta()
/*      */   {
/*  130 */     return this.ForcefieldCamoblockmeta;
/*      */   }
/*      */ 
/*      */   public void setForcefieldCamoblockmeta(int forcefieldCamoblockmeta) {
/*  134 */     this.ForcefieldCamoblockmeta = forcefieldCamoblockmeta;
/*  135 */     NetworkHandlerServer.updateTileEntityField(this, "ForcefieldCamoblockmeta");
/*      */   }
/*      */ 
/*      */   public int getForcefieldCamoblockid() {
/*  139 */     return this.ForcefieldCamoblockid;
/*      */   }
/*      */ 
/*      */   public void setForcefieldCamoblockid(int forcefieldCamoblockid) {
/*  143 */     this.ForcefieldCamoblockid = forcefieldCamoblockid;
/*  144 */     NetworkHandlerServer.updateTileEntityField(this, "ForcefieldCamoblockid");
/*      */   }
/*      */ 
/*      */   public String getForceFieldTexturfile() {
/*  148 */     return this.ForceFieldTexturfile;
/*      */   }
/*      */ 
/*      */   public void setForceFieldTexturfile(String forceFieldTexturfile) {
/*  152 */     this.ForceFieldTexturfile = forceFieldTexturfile;
/*  153 */     NetworkHandlerServer.updateTileEntityField(this, "ForceFieldTexturfile");
/*      */   }
/*      */ 
/*      */   public String getForceFieldTexturID() {
/*  157 */     return this.ForceFieldTexturids;
/*      */   }
/*      */ 
/*      */   public void setForceFieldTexturID(String forceFieldTexturids) {
/*  161 */     this.ForceFieldTexturids = forceFieldTexturids;
/*  162 */     NetworkHandlerServer.updateTileEntityField(this, "ForceFieldTexturids");
/*      */   }
/*      */ 
/*      */   public int getProjektor_Typ()
/*      */   {
/*  167 */     return this.ProjektorTyp;
/*      */   }
/*      */ 
/*      */   public void setProjektor_Typ(int ProjektorTyp)
/*      */   {
/*  172 */     this.ProjektorTyp = ProjektorTyp;
/*      */ 
/*  174 */     NetworkHandlerServer.updateTileEntityField(this, "ProjektorTyp");
/*      */   }
/*      */ 
/*      */   public int getBlockcounter()
/*      */   {
/*  179 */     return this.blockcounter;
/*      */   }
/*      */ 
/*      */   public int getforcefieldblock_meta() {
/*  183 */     return this.forcefieldblock_meta;
/*      */   }
/*      */ 
/*      */   public void setforcefieldblock_meta(int ffmeta) {
/*  187 */     this.forcefieldblock_meta = ((short)ffmeta);
/*      */   }
/*      */ 
/*      */   public int getLinkPower()
/*      */   {
/*  192 */     return this.linkPower;
/*      */   }
/*      */ 
/*      */   public void setLinkPower(int linkPower) {
/*  196 */     this.linkPower = linkPower;
/*      */   }
/*      */ 
/*      */   public void ProjektorBurnout()
/*      */   {
/*  201 */     setBurnedOut(true);
/*  202 */     dropplugins();
/*      */   }
/*      */ 
/*      */   public boolean isBurnout()
/*      */   {
/*  207 */     return this.burnout;
/*      */   }
/*      */ 
/*      */   public void setBurnedOut(boolean b) {
/*  211 */     this.burnout = b;
/*  212 */     NetworkHandlerServer.updateTileEntityField(this, "burnout");
/*      */   }
/*      */ 
/*      */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*      */   {
/*  222 */     super.readFromNBT(nbttagcompound);
/*      */ 
/*  224 */     this.accesstyp = nbttagcompound.getInteger("accesstyp");
/*  225 */     this.burnout = nbttagcompound.getBoolean("burnout");
/*  226 */     this.ProjektorTyp = nbttagcompound.getInteger("Projektor_Typ");
/*  227 */     this.forcefieldblock_meta = nbttagcompound.getShort("forcefieldblockmeta");
/*      */ 
/*  229 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
/*  230 */     this.ProjektorItemStacks = new ItemStack[getSizeInventory()];
/*  231 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  232 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
/*      */ 
/*  234 */       byte byte0 = nbttagcompound1.getByte("Slot");
/*  235 */       if ((byte0 >= 0) && (byte0 < this.ProjektorItemStacks.length))
/*  236 */         this.ProjektorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*      */   {
/*  243 */     super.writeToNBT(nbttagcompound);
/*      */ 
/*  245 */     nbttagcompound.setInteger("accesstyp", this.accesstyp);
/*  246 */     nbttagcompound.setBoolean("burnout", this.burnout);
/*  247 */     nbttagcompound.setInteger("Projektor_Typ", this.ProjektorTyp);
/*  248 */     nbttagcompound.setShort("forcefieldblockmeta", this.forcefieldblock_meta);
/*      */ 
/*  250 */     NBTTagList nbttaglist = new NBTTagList();
/*  251 */     for (int i = 0; i < this.ProjektorItemStacks.length; i++) {
/*  252 */       if (this.ProjektorItemStacks[i] != null) {
/*  253 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  254 */         nbttagcompound1.setByte("Slot", (byte)i);
/*  255 */         this.ProjektorItemStacks[i].writeToNBT(nbttagcompound1);
/*  256 */         nbttaglist.appendTag(nbttagcompound1);
/*      */       }
/*      */     }
/*      */ 
/*  260 */     nbttagcompound.setTag("Items", nbttaglist);
/*      */   }
/*      */ 
/*      */   public void dropplugins()
/*      */   {
/*  268 */     for (int a = 0; a < this.ProjektorItemStacks.length; a++)
/*  269 */       dropplugins(a, this);
/*      */   }
/*      */ 
/*      */   public void onInventoryChanged()
/*      */   {
/*  277 */     getLinkedSecurityStation();
/*  278 */     checkslots();
/*      */   }
/*      */ 
/*      */   public void checkslots()
/*      */   {
/*  285 */     if (hasValidTypeMod())
/*      */     {
/*  287 */       if (getProjektor_Typ() != ProjectorTyp.TypfromItem(get_type()).ProTyp) {
/*  288 */         setProjektor_Typ(ProjectorTyp.TypfromItem(get_type()).ProTyp);
/*      */       }
/*  290 */       if (getforcefieldblock_meta() != get_type().getForceFieldTyps().ordinal()) {
/*  291 */         setforcefieldblock_meta(get_type().getForceFieldTyps().ordinal());
/*      */       }
/*  293 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*      */     }
/*      */     else {
/*  296 */       if (getProjektor_Typ() != 0) setProjektor_Typ(0);
/*  297 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*      */     }
/*      */ 
/*  304 */     if (hasValidTypeMod()) {
/*  305 */       for (int place = 7; place < 11; place++) {
/*  306 */         if (getStackInSlot(place) != null) {
/*  307 */           if (getStackInSlot(place).getItem() == ModularForceFieldSystem.MFFSitemFocusmatix)
/*  308 */             switch (ProjectorTyp.TypfromItem(get_type()).ProTyp)
/*      */             {
/*      */             case 6:
/*  311 */               this.focusmatrix[(place - 7)] = (getStackInSlot(place).stackSize + 1);
/*  312 */               break;
/*      */             case 7:
/*  314 */               this.focusmatrix[(place - 7)] = (getStackInSlot(place).stackSize + 2);
/*  315 */               break;
/*      */             default:
/*  317 */               this.focusmatrix[(place - 7)] = getStackInSlot(place).stackSize;
/*  318 */               break;
/*      */             }
/*      */           else
/*  321 */             dropplugins(place, this);
/*      */         }
/*      */         else {
/*  324 */           switch (ProjectorTyp.TypfromItem(get_type()).ProTyp)
/*      */           {
/*      */           case 6:
/*  327 */             this.focusmatrix[(place - 7)] = 1;
/*  328 */             break;
/*      */           case 7:
/*  330 */             this.focusmatrix[(place - 7)] = 2;
/*  331 */             break;
/*      */           default:
/*  333 */             this.focusmatrix[(place - 7)] = 0;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  341 */     if (getStackInSlot(11) != null)
/*      */     {
/*  343 */       if (getStackInSlot(11).itemID < 4095)
/*      */       {
/*  345 */         String ForcefieldTexturtemp = "180/180/180/180/180/180";
/*  346 */         String Texturfile = "/terrain.png";
/*      */ 
/*  348 */         int[] index = new int[6];
/*  349 */         for (int side = 0; side < 6; side++) {
/*  350 */           index[side] = Block.blocksList[getStackInSlot(11).itemID].getBlockTextureFromSideAndMetadata(side, getStackInSlot(11).getItemDamage());
/*      */         }
/*  352 */         ForcefieldTexturtemp = index[0] + "/" + index[1] + "/" + index[2] + "/" + index[3] + "/" + index[4] + "/" + index[5];
/*  353 */         Texturfile = Block.blocksList[getStackInSlot(11).itemID].getTextureFile();
/*      */ 
/*  356 */         if ((!ForcefieldTexturtemp.equalsIgnoreCase(this.ForceFieldTexturids)) || (!this.ForceFieldTexturfile.equalsIgnoreCase(getForceFieldTexturfile())))
/*      */         {
/*  358 */           if (getStackInSlot(11).getItem() == Item.bucketLava) {
/*  359 */             setForceFieldTexturID("237/237/239/254/255/255");
/*      */           }
/*  361 */           if (getStackInSlot(11).getItem() == Item.bucketWater) {
/*  362 */             setForceFieldTexturID("205/205/207/222/223/223");
/*      */           }
/*  364 */           if ((getStackInSlot(11).getItem() != Item.bucketLava) && (getStackInSlot(11).getItem() != Item.bucketWater)) {
/*  365 */             setForceFieldTexturID(ForcefieldTexturtemp);
/*      */           }
/*  367 */           setForcefieldCamoblockmeta(getStackInSlot(11).getItemDamage());
/*  368 */           setForcefieldCamoblockid(getStackInSlot(11).itemID);
/*  369 */           setForceFieldTexturfile(Texturfile);
/*  370 */           UpdateForcefieldTexttur();
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  376 */         dropplugins(11, this);
/*      */       }
/*      */ 
/*      */     }
/*  381 */     else if ((!this.ForceFieldTexturids.equalsIgnoreCase("-76/-76/-76/-76/-76/-76")) || (getForcefieldCamoblockid() != -1))
/*      */     {
/*  383 */       setForcefieldCamoblockmeta(0);
/*  384 */       setForcefieldCamoblockid(-1);
/*  385 */       setForceFieldTexturID("-76/-76/-76/-76/-76/-76");
/*  386 */       setForceFieldTexturfile("/terrain.png");
/*  387 */       UpdateForcefieldTexttur();
/*      */     }
/*      */ 
/*  392 */     if ((hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true)) && 
/*  393 */       (getforcefieldblock_meta() != ForceFieldTyps.Camouflage.ordinal())) {
/*  394 */       setforcefieldblock_meta((short)ForceFieldTyps.Camouflage.ordinal());
/*      */     }
/*      */ 
/*  398 */     if ((hasOption(ModularForceFieldSystem.MFFSProjectorOptionZapper, true)) && 
/*  399 */       (getforcefieldblock_meta() != ForceFieldTyps.Zapper.ordinal())) {
/*  400 */       setforcefieldblock_meta((short)ForceFieldTyps.Zapper.ordinal());
/*      */     }
/*      */ 
/*  404 */     if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionFieldFusion, true)) {
/*  405 */       if (!Linkgrid.getWorldMap(this.worldObj).getFieldFusion().containsKey(Integer.valueOf(getDeviceID())))
/*  406 */         Linkgrid.getWorldMap(this.worldObj).getFieldFusion().put(Integer.valueOf(getDeviceID()), this);
/*      */     }
/*  408 */     else if (Linkgrid.getWorldMap(this.worldObj).getFieldFusion().containsKey(Integer.valueOf(getDeviceID()))) {
/*  409 */       Linkgrid.getWorldMap(this.worldObj).getFieldFusion().remove(Integer.valueOf(getDeviceID()));
/*      */     }
/*      */ 
/*  412 */     if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionForceFieldJammer, false)) {
/*  413 */       if (!Linkgrid.getWorldMap(this.worldObj).getJammer().containsKey(Integer.valueOf(getDeviceID())))
/*  414 */         Linkgrid.getWorldMap(this.worldObj).getJammer().put(Integer.valueOf(getDeviceID()), this);
/*      */     }
/*  416 */     else if (Linkgrid.getWorldMap(this.worldObj).getJammer().containsKey(Integer.valueOf(getDeviceID()))) {
/*  417 */       Linkgrid.getWorldMap(this.worldObj).getJammer().remove(Integer.valueOf(getDeviceID()));
/*      */     }
/*      */ 
/*  422 */     if (hasValidTypeMod()) {
/*  423 */       ModuleBase modTyp = get_type();
/*      */ 
/*  425 */       if (!modTyp.supportsStrength())
/*  426 */         dropplugins(6, this);
/*  427 */       if (!modTyp.supportsDistance()) {
/*  428 */         dropplugins(5, this);
/*      */       }
/*  430 */       if (!modTyp.supportsMatrix()) {
/*  431 */         dropplugins(7, this);
/*  432 */         dropplugins(8, this);
/*  433 */         dropplugins(9, this);
/*  434 */         dropplugins(10, this);
/*      */       }
/*      */ 
/*  439 */       for (int spot = 2; spot <= 4; spot++) {
/*  440 */         if ((getStackInSlot(spot) != null) && 
/*  441 */           (!modTyp.supportsOption(getStackInSlot(spot).getItem()))) {
/*  442 */           dropplugins(spot, this);
/*      */         }
/*      */ 
/*  445 */         if ((getStackInSlot(spot) != null) && 
/*  446 */           ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionForceFieldJammer)) && (isPowersourceItem())) {
/*  447 */           dropplugins(spot, this);
/*      */         }
/*      */ 
/*  451 */         if ((getStackInSlot(spot) != null) && 
/*  452 */           ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionFieldFusion)) && (isPowersourceItem())) {
/*  453 */           dropplugins(spot, this);
/*      */         }
/*      */ 
/*  457 */         if ((getStackInSlot(spot) != null) && 
/*  458 */           ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionDefenseStation)) && (isPowersourceItem())) {
/*  459 */           dropplugins(spot, this);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  465 */       if ((getStackInSlot(12) != null) && 
/*  466 */         ((getStackInSlot(12).getItem() instanceof ItemCardSecurityLink)) && (isPowersourceItem())) {
/*  467 */         dropplugins(12, this);
/*      */       }
/*      */ 
/*  471 */       if (!hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true))
/*  472 */         dropplugins(11, this);
/*      */     }
/*      */     else {
/*  475 */       for (int spot = 2; spot <= 10; spot++)
/*  476 */         dropplugins(spot, this);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void UpdateForcefieldTexttur()
/*      */   {
/*  486 */     if ((isActive()) && (hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true)))
/*      */     {
/*  488 */       for (PointXYZ png : this.field_def)
/*      */       {
/*  490 */         if (this.worldObj.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded)
/*      */         {
/*  493 */           TileEntity tileEntity = this.worldObj.getBlockTileEntity(png.X, png.Y, png.Z);
/*      */ 
/*  495 */           if ((tileEntity != null) && ((tileEntity instanceof TileEntityForceField)))
/*      */           {
/*  497 */             ((TileEntityForceField)tileEntity).UpdateTextur();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateEntity()
/*      */   {
/*  509 */     if (!this.worldObj.isRemote)
/*      */     {
/*  512 */       if (this.init) {
/*  513 */         checkslots();
/*  514 */         if (isActive()) {
/*  515 */           calculateField(true);
/*      */         }
/*      */       }
/*      */ 
/*  519 */       if (hasPowerSource())
/*      */       {
/*  521 */         setLinkPower(getAvailablePower());
/*      */ 
/*  523 */         if ((isPowersourceItem()) && (getaccesstyp() != 0))
/*  524 */           setaccesstyp(0);
/*      */       }
/*      */       else
/*      */       {
/*  528 */         setLinkPower(0);
/*      */       }
/*      */ 
/*  531 */       if ((getSwitchModi() == 1) && 
/*  532 */         (!getSwitchValue()) && (isRedstoneSignal())) {
/*  533 */         toggelSwitchValue();
/*      */       }
/*  535 */       if ((getSwitchModi() == 1) && 
/*  536 */         (getSwitchValue()) && (!isRedstoneSignal())) {
/*  537 */         toggelSwitchValue();
/*      */       }
/*      */ 
/*  540 */       if ((getSwitchValue()) && (this.switchdelay >= 40) && (hasValidTypeMod()) && (hasPowerSource()) && (getLinkPower() > Forcepowerneed(5)))
/*      */       {
/*  543 */         if (isActive() != true) {
/*  544 */           setActive(true);
/*  545 */           this.switchdelay = 0;
/*  546 */           if (calculateField(true))
/*      */           {
/*  548 */             FieldGenerate(true);
/*      */           }
/*  550 */           this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*      */         }
/*      */       }
/*  553 */       if (((!getSwitchValue()) && (this.switchdelay >= 40)) || (!hasValidTypeMod()) || (!hasPowerSource()) || (this.burnout) || (getLinkPower() <= Forcepowerneed(1)))
/*      */       {
/*  556 */         if (isActive()) {
/*  557 */           setActive(false);
/*  558 */           this.switchdelay = 0;
/*  559 */           destroyField();
/*  560 */           this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*      */         }
/*      */       }
/*      */ 
/*  564 */       if (getTicker() == 20)
/*      */       {
/*  566 */         if (isActive()) {
/*  567 */           FieldGenerate(false);
/*      */ 
/*  569 */           if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionMoobEx, true)) {
/*  570 */             ItemProjectorOptionMobDefence.ProjectorNPCDefence(this, this.worldObj);
/*      */           }
/*      */ 
/*  573 */           if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionDefenceStation, true)) {
/*  574 */             ItemProjectorOptionDefenseStation.ProjectorPlayerDefence(this, this.worldObj);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  579 */         setTicker((short)0);
/*      */       }
/*      */ 
/*  582 */       setTicker((short)(getTicker() + 1));
/*      */     }
/*  584 */     this.switchdelay += 1;
/*  585 */     super.updateEntity();
/*      */   }
/*      */ 
/*      */   private boolean calculateField(boolean addtoMap) {
/*  589 */     this.field_def.clear();
/*  590 */     this.field_interior.clear();
/*  591 */     if (hasValidTypeMod()) {
/*  592 */       Set tField = new HashSet();
/*  593 */       Set tFieldInt = new HashSet();
/*      */ 
/*  595 */       if ((get_type() instanceof Module3DBase))
/*  596 */         ((Module3DBase)get_type()).calculateField(this, tField, tFieldInt);
/*      */       else {
/*  598 */         get_type().calculateField(this, tField);
/*      */       }
/*      */ 
/*  601 */       for (PointXYZ pnt : tField)
/*      */       {
/*  603 */         if (pnt.Y + this.yCoord < 255) {
/*  604 */           PointXYZ tp = new PointXYZ(pnt.X + this.xCoord, pnt.Y + this.yCoord, pnt.Z + this.zCoord, this.worldObj);
/*      */ 
/*  606 */           if (Forcefielddefine(tp, addtoMap))
/*      */           {
/*  608 */             this.field_def.add(tp);
/*      */           } else return false;
/*      */         }
/*      */       }
/*  612 */       for (PointXYZ pnt : tFieldInt)
/*      */       {
/*  615 */         if (pnt.Y + this.yCoord < 255) {
/*  616 */           PointXYZ tp = new PointXYZ(pnt.X + this.xCoord, pnt.Y + this.yCoord, pnt.Z + this.zCoord, this.worldObj);
/*      */ 
/*  618 */           if (calculateBlock(tp))
/*  619 */             this.field_interior.add(tp); else return false;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  624 */       return true;
/*      */     }
/*  626 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean calculateBlock(PointXYZ pnt)
/*      */   {
/*  634 */     for (ItemProjectorOptionBase opt : getOptions(true))
/*      */     {
/*  636 */       if ((opt instanceof IInteriorCheck)) {
/*  637 */         ((IInteriorCheck)opt).checkInteriorBlock(pnt, this.worldObj, this);
/*      */       }
/*      */     }
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean Forcefielddefine(PointXYZ png, boolean addtoMap)
/*      */   {
/*  649 */     for (ItemProjectorOptionBase opt : getOptions(true))
/*      */     {
/*  652 */       if (((opt instanceof ItemProjectorOptionForceFieldJammer)) && 
/*  653 */         (((ItemProjectorOptionForceFieldJammer)opt).CheckJammerinfluence(png, this.worldObj, this))) {
/*  654 */         return false;
/*      */       }
/*      */ 
/*  658 */       if (((opt instanceof ItemProjectorOptionFieldFusion)) && 
/*  659 */         (((ItemProjectorOptionFieldFusion)opt).checkFieldFusioninfluence(png, this.worldObj, this))) {
/*  660 */         return true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  666 */     ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(this.worldObj).getorcreateFFStackMap(png.X, png.Y, png.Z, this.worldObj);
/*      */ 
/*  670 */     if (!ffworldmap.isEmpty())
/*      */     {
/*  672 */       if (ffworldmap.getProjectorID() != getDeviceID()) {
/*  673 */         ffworldmap.removebyProjector(getDeviceID());
/*  674 */         ffworldmap.add(getPowerSourceID(), getDeviceID(), getforcefieldblock_meta());
/*      */       }
/*      */     } else {
/*  677 */       ffworldmap.add(getPowerSourceID(), getDeviceID(), getforcefieldblock_meta());
/*  678 */       ffworldmap.setSync(false);
/*      */     }
/*      */ 
/*  681 */     this.field_queue.push(Integer.valueOf(png.hashCode()));
/*      */ 
/*  683 */     return true;
/*      */   }
/*      */ 
/*      */   public void FieldGenerate(boolean init)
/*      */   {
/*  690 */     int cost = 0;
/*      */ 
/*  692 */     if (init) {
/*  693 */       cost = ModularForceFieldSystem.forcefieldblockcost * ModularForceFieldSystem.forcefieldblockcreatemodifier;
/*      */     }
/*      */     else {
/*  696 */       cost = ModularForceFieldSystem.forcefieldblockcost;
/*      */     }
/*      */ 
/*  699 */     if (getforcefieldblock_meta() == 1) {
/*  700 */       cost *= ModularForceFieldSystem.forcefieldblockzappermodifier;
/*      */     }
/*      */ 
/*  703 */     consumePower(cost * this.field_def.size(), false);
/*      */ 
/*  705 */     this.blockcounter = 0;
/*      */ 
/*  707 */     for (PointXYZ pnt : this.field_def)
/*      */     {
/*  710 */       if (this.blockcounter == ModularForceFieldSystem.forcefieldmaxblockpeerTick) {
/*      */         break;
/*      */       }
/*  713 */       ForceFieldBlockStack ffb = WorldMap.getForceFieldWorld(this.worldObj).getForceFieldStackMap(Integer.valueOf(pnt.hashCode()));
/*      */ 
/*  715 */       if (ffb != null)
/*      */       {
/*  717 */         if (!ffb.isSync())
/*      */         {
/*  719 */           PointXYZ png = ffb.getPoint();
/*      */ 
/*  721 */           if ((this.worldObj.getChunkFromBlockCoords(png.X, png.Z).isChunkLoaded) && 
/*  722 */             (!ffb.isEmpty()) && 
/*  723 */             (ffb.getProjectorID() == getDeviceID())) {
/*  724 */             if (hasOption(ModularForceFieldSystem.MFFSProjectorOptionCutter, true)) {
/*  725 */               int blockid = this.worldObj.getBlockId(png.X, png.Y, png.Z);
/*  726 */               TileEntity entity = this.worldObj.getBlockTileEntity(png.X, png.Y, png.Z);
/*      */ 
/*  728 */               if ((blockid != ModularForceFieldSystem.MFFSFieldblock.blockID) && (blockid != 0) && (blockid != Block.bedrock.blockID) && (entity == null))
/*      */               {
/*  734 */                 ArrayList stacks = Functions.getItemStackFromBlock(this.worldObj, png.X, png.Y, png.Z);
/*      */ 
/*  736 */                 this.worldObj.setBlockWithNotify(png.X, png.Y, png.Z, 0);
/*      */ 
/*  738 */                 if ((ProjectorTyp.TypfromItem(get_type()).Blockdropper) && (stacks != null)) {
/*  739 */                   IInventory inventory = InventoryHelper.findAttachedInventory(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
/*  740 */                   if ((inventory != null) && 
/*  741 */                     (inventory.getSizeInventory() > 0)) {
/*  742 */                     InventoryHelper.addStacksToInventory(inventory, stacks);
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  749 */             if ((this.worldObj.getBlockMaterial(png.X, png.Y, png.Z).isLiquid()) || (this.worldObj.isAirBlock(png.X, png.Y, png.Z)) || (this.worldObj.getBlockId(png.X, png.Y, png.Z) == ModularForceFieldSystem.MFFSFieldblock.blockID)) {
/*  750 */               if (this.worldObj.getBlockId(png.X, png.Y, png.Z) != ModularForceFieldSystem.MFFSFieldblock.blockID) {
/*  751 */                 this.worldObj.setBlockAndMetadataWithNotify(png.X, png.Y, png.Z, ModularForceFieldSystem.MFFSFieldblock.blockID, ffb.getTyp());
/*      */ 
/*  754 */                 this.blockcounter += 1;
/*      */               }
/*  756 */               ffb.setSync(true);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void destroyField()
/*      */   {
/*  768 */     while (!this.field_queue.isEmpty()) {
/*  769 */       ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(this.worldObj).getForceFieldStackMap((Integer)this.field_queue.pop());
/*      */ 
/*  772 */       if (!ffworldmap.isEmpty())
/*      */       {
/*  774 */         if (ffworldmap.getProjectorID() == getDeviceID()) {
/*  775 */           ffworldmap.removebyProjector(getDeviceID());
/*      */ 
/*  777 */           if (ffworldmap.isSync())
/*      */           {
/*  779 */             PointXYZ png = ffworldmap.getPoint();
/*  780 */             this.worldObj.removeBlockTileEntity(png.X, png.Y, png.Z);
/*  781 */             this.worldObj.setBlockWithNotify(png.X, png.Y, png.Z, 0);
/*      */           }
/*      */ 
/*  784 */           ffworldmap.setSync(false);
/*      */         } else {
/*  786 */           ffworldmap.removebyProjector(getDeviceID());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  791 */     Map FieldFusion = Linkgrid.getWorldMap(this.worldObj).getFieldFusion();
/*  792 */     for (TileEntityProjector tileentity : FieldFusion.values())
/*      */     {
/*  794 */       if (tileentity.getPowerSourceID() == getPowerSourceID())
/*      */       {
/*  796 */         if (tileentity.isActive())
/*      */         {
/*  798 */           tileentity.calculateField(false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void invalidate()
/*      */   {
/*  807 */     Linkgrid.getWorldMap(this.worldObj).getProjektor().remove(Integer.valueOf(getDeviceID()));
/*  808 */     destroyField();
/*  809 */     super.invalidate();
/*      */   }
/*      */ 
/*      */   public int Forcepowerneed(int factor) {
/*  813 */     if (!this.field_def.isEmpty()) {
/*  814 */       return this.field_def.size() * ModularForceFieldSystem.forcefieldblockcost;
/*      */     }
/*      */ 
/*  817 */     int forcepower = 0;
/*  818 */     int blocks = 0;
/*      */ 
/*  820 */     int tmplength = 1;
/*      */ 
/*  822 */     if (countItemsInSlot(IModularProjector.Slots.Strength) != 0) {
/*  823 */       tmplength = countItemsInSlot(IModularProjector.Slots.Strength);
/*      */     }
/*      */ 
/*  826 */     switch (getProjektor_Typ()) {
/*      */     case 1:
/*  828 */       blocks = (countItemsInSlot(IModularProjector.Slots.FocusDown) + countItemsInSlot(IModularProjector.Slots.FocusLeft) + countItemsInSlot(IModularProjector.Slots.FocusRight) + countItemsInSlot(IModularProjector.Slots.FocusUp) + 1) * tmplength;
/*      */ 
/*  831 */       break;
/*      */     case 2:
/*  833 */       blocks = (countItemsInSlot(IModularProjector.Slots.FocusDown) + countItemsInSlot(IModularProjector.Slots.FocusUp) + 1) * (countItemsInSlot(IModularProjector.Slots.FocusLeft) + countItemsInSlot(IModularProjector.Slots.FocusRight) + 1);
/*      */ 
/*  835 */       break;
/*      */     case 3:
/*  837 */       blocks = ((countItemsInSlot(IModularProjector.Slots.Distance) + 2 + countItemsInSlot(IModularProjector.Slots.Distance) + 2) * 4 + 4) * (countItemsInSlot(IModularProjector.Slots.Strength) + 1);
/*      */ 
/*  839 */       break;
/*      */     case 4:
/*      */     case 5:
/*  842 */       blocks = countItemsInSlot(IModularProjector.Slots.Distance) * countItemsInSlot(IModularProjector.Slots.Distance) * 6;
/*      */     }
/*      */ 
/*  846 */     forcepower = blocks * ModularForceFieldSystem.forcefieldblockcost;
/*  847 */     if (factor != 1) {
/*  848 */       forcepower = forcepower * ModularForceFieldSystem.forcefieldblockcreatemodifier + forcepower * 5;
/*      */     }
/*      */ 
/*  851 */     return forcepower;
/*      */   }
/*      */ 
/*      */   public ItemStack decrStackSize(int i, int j)
/*      */   {
/*  856 */     if (this.ProjektorItemStacks[i] != null) {
/*  857 */       if (this.ProjektorItemStacks[i].stackSize <= j) {
/*  858 */         ItemStack itemstack = this.ProjektorItemStacks[i];
/*  859 */         this.ProjektorItemStacks[i] = null;
/*  860 */         return itemstack;
/*      */       }
/*  862 */       ItemStack itemstack1 = this.ProjektorItemStacks[i].splitStack(j);
/*  863 */       if (this.ProjektorItemStacks[i].stackSize == 0) {
/*  864 */         this.ProjektorItemStacks[i] = null;
/*      */       }
/*  866 */       return itemstack1;
/*      */     }
/*  868 */     return null;
/*      */   }
/*      */ 
/*      */   public void setInventorySlotContents(int i, ItemStack itemstack)
/*      */   {
/*  874 */     this.ProjektorItemStacks[i] = itemstack;
/*  875 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit()))
/*  876 */       itemstack.stackSize = getInventoryStackLimit();
/*      */   }
/*      */ 
/*      */   public ItemStack getStackInSlot(int i)
/*      */   {
/*  883 */     return this.ProjektorItemStacks[i];
/*      */   }
/*      */ 
/*      */   public String getInvName()
/*      */   {
/*  888 */     return "Projektor";
/*      */   }
/*      */ 
/*      */   public int getSizeInventory()
/*      */   {
/*  893 */     return this.ProjektorItemStacks.length;
/*      */   }
/*      */ 
/*      */   public Container getContainer(InventoryPlayer inventoryplayer)
/*      */   {
/*  900 */     return new ContainerProjector(inventoryplayer.player, this);
/*      */   }
/*      */ 
/*      */   public int getStartInventorySide(ForgeDirection side)
/*      */   {
/*  906 */     return 11;
/*      */   }
/*      */ 
/*      */   public int getSizeInventorySide(ForgeDirection side)
/*      */   {
/*  911 */     return 1;
/*      */   }
/*      */ 
/*      */   public void onNetworkHandlerEvent(int key, String value)
/*      */   {
/*  917 */     if (!isActive())
/*      */     {
/*  919 */       switch (key)
/*      */       {
/*      */       case 1:
/*  923 */         if (!isPowersourceItem())
/*      */         {
/*  925 */           if (getaccesstyp() != 3)
/*      */           {
/*  927 */             if (getaccesstyp() == 2)
/*      */             {
/*  929 */               setaccesstyp(0);
/*      */             }
/*  931 */             else setaccesstyp(getaccesstyp() + 1);
/*      */           }
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  939 */     super.onNetworkHandlerEvent(key, value);
/*      */   }
/*      */ 
/*      */   public List getFieldsforUpdate()
/*      */   {
/*  945 */     List NetworkedFields = new LinkedList();
/*  946 */     NetworkedFields.clear();
/*      */ 
/*  948 */     NetworkedFields.addAll(super.getFieldsforUpdate());
/*      */ 
/*  950 */     NetworkedFields.add("ProjektorTyp");
/*  951 */     NetworkedFields.add("burnout");
/*  952 */     NetworkedFields.add("camoflage");
/*  953 */     NetworkedFields.add("ForceFieldTexturfile");
/*  954 */     NetworkedFields.add("ForceFieldTexturids");
/*  955 */     NetworkedFields.add("ForcefieldCamoblockid");
/*  956 */     NetworkedFields.add("ForcefieldCamoblockmeta");
/*      */ 
/*  958 */     return NetworkedFields;
/*      */   }
/*      */ 
/*      */   public boolean isItemValid(ItemStack par1ItemStack, int Slot)
/*      */   {
/*  964 */     if ((Slot == 1) && ((par1ItemStack.getItem() instanceof ModuleBase))) return true;
/*  965 */     if ((Slot == 0) && ((par1ItemStack.getItem() instanceof IPowerLinkItem))) return true;
/*  966 */     if ((Slot == 11) && (par1ItemStack.itemID < 4096) && (hasOption(ModularForceFieldSystem.MFFSProjectorOptionCamouflage, true))) return true;
/*      */ 
/*  968 */     if (hasValidTypeMod())
/*      */     {
/*  970 */       ModuleBase modTyp = get_type();
/*      */ 
/*  972 */       switch (Slot)
/*      */       {
/*      */       case 12:
/*  975 */         if (((par1ItemStack.getItem() instanceof ItemProjectorOptionDefenseStation)) && (isPowersourceItem())) {
/*  976 */           return false;
/*      */         }
/*  978 */         if (((par1ItemStack.getItem() instanceof ItemCardSecurityLink)) && (isPowersourceItem())) {
/*  979 */           return false;
/*      */         }
/*  981 */         if ((par1ItemStack.getItem() instanceof ItemCardSecurityLink)) {
/*  982 */           return true;
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 5:
/*  987 */         if ((par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorDistance)) return modTyp.supportsDistance();
/*      */         break;
/*      */       case 6:
/*  990 */         if ((par1ItemStack.getItem() instanceof ItemProjectorFieldModulatorStrength)) return modTyp.supportsStrength();
/*      */ 
/*      */         break;
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*  997 */         if ((par1ItemStack.getItem() instanceof ItemProjectorFocusMatrix)) return modTyp.supportsMatrix();
/*      */ 
/*      */         break;
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/* 1005 */         if (isActive()) return false;
/*      */ 
/* 1007 */         if ((par1ItemStack.getItem() instanceof ItemProjectorOptionTouchDamage)) {
/* 1008 */           for (int spot = 2; spot <= 4; spot++) {
/* 1009 */             if ((getStackInSlot(spot) != null) && 
/* 1010 */               ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionCamoflage))) {
/* 1011 */               return false;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1016 */         if ((par1ItemStack.getItem() instanceof ItemProjectorOptionCamoflage)) {
/* 1017 */           for (int spot = 2; spot <= 4; spot++) {
/* 1018 */             if ((getStackInSlot(spot) != null) && 
/* 1019 */               ((getStackInSlot(spot).getItem() instanceof ItemProjectorOptionTouchDamage))) {
/* 1020 */               return false;
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1026 */         if (!hasPowerSource()) {
/* 1027 */           return false;
/*      */         }
/* 1029 */         if (((par1ItemStack.getItem() instanceof ItemProjectorOptionDefenseStation)) && (isPowersourceItem())) {
/* 1030 */           return false;
/*      */         }
/* 1032 */         if (((par1ItemStack.getItem() instanceof ItemProjectorOptionFieldFusion)) && (isPowersourceItem())) {
/* 1033 */           return false;
/*      */         }
/* 1035 */         if (((par1ItemStack.getItem() instanceof ItemProjectorOptionForceFieldJammer)) && (isPowersourceItem())) {
/* 1036 */           return false;
/*      */         }
/* 1038 */         if ((par1ItemStack.getItem() instanceof ItemProjectorOptionBase)) {
/* 1039 */           return modTyp.supportsOption(par1ItemStack.getItem());
/*      */         }
/*      */         break;
/*      */       case 11:
/*      */       }
/*      */     }
/*      */ 
/* 1046 */     return false;
/*      */   }
/*      */ 
/*      */   public int getSlotStackLimit(int Slot) {
/* 1050 */     switch (Slot) {
/*      */     case 5:
/*      */     case 6:
/* 1053 */       return 64;
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/* 1059 */       return 64;
/*      */     }
/*      */ 
/* 1062 */     return 1;
/*      */   }
/*      */ 
/*      */   public boolean hasValidTypeMod()
/*      */   {
/* 1067 */     if ((getStackInSlot(1) != null) && ((getStackInSlot(1).getItem() instanceof ModuleBase)))
/* 1068 */       return true;
/* 1069 */     return false;
/*      */   }
/*      */ 
/*      */   public ModuleBase get_type() {
/* 1073 */     if (hasValidTypeMod()) {
/* 1074 */       return (ModuleBase)getStackInSlot(1).getItem();
/*      */     }
/* 1076 */     return null;
/*      */   }
/*      */ 
/*      */   public Set getInteriorPoints()
/*      */   {
/* 1082 */     return this.field_interior;
/*      */   }
/*      */ 
/*      */   public Set getfield_queue()
/*      */   {
/* 1087 */     return this.field_def;
/*      */   }
/*      */ 
/*      */   public TileEntityAdvSecurityStation getLinkedSecurityStation()
/*      */   {
/* 1093 */     TileEntityAdvSecurityStation sec = ItemCardSecurityLink.getLinkedSecurityStation(this, 12, this.worldObj);
/* 1094 */     if (sec != null)
/*      */     {
/* 1096 */       if ((getaccesstyp() != 3) && (!isPowersourceItem()))
/* 1097 */         setaccesstyp(3);
/* 1098 */       return sec;
/*      */     }
/*      */ 
/* 1101 */     if (getaccesstyp() == 3)
/* 1102 */       setaccesstyp(0);
/* 1103 */     return null;
/*      */   }
/*      */ 
/*      */   public int getSecStation_ID()
/*      */   {
/* 1109 */     TileEntityAdvSecurityStation sec = getLinkedSecurityStation();
/* 1110 */     if (sec != null)
/* 1111 */       return sec.getDeviceID();
/* 1112 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean hasOption(Item item, boolean includecheckall)
/*      */   {
/* 1119 */     for (ItemProjectorOptionBase opt : getOptions(includecheckall)) {
/* 1120 */       if (opt == item)
/* 1121 */         return true;
/*      */     }
/* 1123 */     return false;
/*      */   }
/*      */ 
/*      */   public List getOptions(boolean includecheckall)
/*      */   {
/* 1129 */     List ret = new ArrayList();
/* 1130 */     for (int place = 2; place < 5; place++) {
/* 1131 */       if ((getStackInSlot(place) != null) && 
/* 1132 */         ((getStackInSlot(place).getItem() instanceof ItemProjectorOptionBase))) {
/* 1133 */         ret.add((ItemProjectorOptionBase)getStackInSlot(place).getItem());
/*      */       }
/*      */ 
/* 1136 */       if (includecheckall)
/*      */       {
/* 1138 */         for (ItemProjectorOptionBase opt : ItemProjectorOptionBase.get_instances()) {
/* 1139 */           if (((opt instanceof IChecksOnAll)) && (!ret.contains(opt))) {
/* 1140 */             ret.add(opt);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1146 */     return ret;
/*      */   }
/*      */ 
/*      */   public short getmaxSwitchModi()
/*      */   {
/* 1152 */     return 3;
/*      */   }
/*      */ 
/*      */   public short getminSwitchModi() {
/* 1156 */     return 1;
/*      */   }
/*      */ 
/*      */   public ItemStack getPowerLinkStack()
/*      */   {
/* 1161 */     return getStackInSlot(getPowerlinkSlot());
/*      */   }
/*      */ 
/*      */   public int getPowerlinkSlot()
/*      */   {
/* 1166 */     return 0;
/*      */   }
/*      */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityProjector
 * JD-Core Version:    0.6.2
 */