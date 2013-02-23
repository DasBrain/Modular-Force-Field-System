/*     */ package chb.mods.mffs.common.modules;
/*     */ 
/*     */ import chb.mods.mffs.common.ForceFieldTyps;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.IModularProjector;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.ProjectorTyp;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class ModuleBase extends Item
/*     */ {
/*  44 */   private static List instances = new ArrayList();
/*     */ 
/*  46 */   public static List get_instances() { return instances; }
/*     */ 
/*     */   public ModuleBase(int i)
/*     */   {
/*  50 */     super(i);
/*  51 */     setMaxStackSize(8);
/*  52 */     instances.add(this);
/*  53 */     setCreativeTab(ModularForceFieldSystem.MFFSTab);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  58 */     return "/chb/mods/mffs/sprites/items.png";
/*     */   }
/*     */ 
/*     */   public boolean isRepairable() {
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract boolean supportsDistance();
/*     */ 
/*     */   public abstract boolean supportsStrength();
/*     */ 
/*     */   public abstract boolean supportsMatrix();
/*     */ 
/*     */   public abstract void calculateField(IModularProjector paramIModularProjector, Set paramSet);
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  83 */     TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
/*     */ 
/*  85 */     if ((!world.isRemote) && 
/*  86 */       ((tileEntity instanceof IModularProjector))) {
/*  87 */       if (!SecurityHelper.isAccessGranted(tileEntity, entityplayer, world, SecurityRight.EB)) {
/*  88 */         return false;
/*     */       }
/*  90 */       if (((IModularProjector)tileEntity).getStackInSlot(1) == null)
/*     */       {
/*  92 */         ((IModularProjector)tileEntity).setInventorySlotContents(1, itemstack.splitStack(1));
/*  93 */         Functions.ChattoPlayer(entityplayer, "[Projector] Success: <Projector Module " + ProjectorTyp.TypfromItem(((IModularProjector)tileEntity).getStackInSlot(1).getItem()).displayName + "> installed");
/*  94 */         ((TileEntityProjector)tileEntity).checkslots();
/*  95 */         return true;
/*     */       }
/*     */ 
/*  99 */       Functions.ChattoPlayer(entityplayer, "[Projector] Fail: Slot is not empty");
/* 100 */       return false;
/*     */     }
/*     */ 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   public String getProjectorTextureFile()
/*     */   {
/* 110 */     return "/chb/mods/mffs/sprites/blocks.png";
/*     */   }
/*     */ 
/*     */   public ForceFieldTyps getForceFieldTyps()
/*     */   {
/* 116 */     return ForceFieldTyps.Default;
/*     */   }
/*     */ 
/*     */   public abstract boolean supportsOption(Item paramItem);
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.modules.ModuleBase
 * JD-Core Version:    0.6.2
 */