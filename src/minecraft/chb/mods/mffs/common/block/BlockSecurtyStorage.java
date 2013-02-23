/*    */ package chb.mods.mffs.common.block;
/*    */ 
/*    */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*    */ import chb.mods.mffs.common.SecurityHelper;
/*    */ import chb.mods.mffs.common.SecurityRight;
/*    */ import chb.mods.mffs.common.multitool.ItemMultitool;
/*    */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockSecurtyStorage extends BlockMFFSBase
/*    */ {
/*    */   public BlockSecurtyStorage(int i)
/*    */   {
/* 39 */     super(i);
/* 40 */     setRequiresSelfNotify();
/*    */   }
/*    */ 
/*    */   public TileEntity createNewTileEntity(World world)
/*    */   {
/* 45 */     return new TileEntitySecStorage();
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 51 */     if (ModularForceFieldSystem.graphicstyle == 1) {
/* 52 */       return "/chb/mods/mffs/sprites/SecStorage_ue.png";
/*    */     }
/* 54 */     return "/chb/mods/mffs/sprites/SecStorage.png";
/*    */   }
/*    */ 
/*    */   public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
/*    */   {
/* 64 */     if (world.isRemote) {
/* 65 */       return true;
/*    */     }
/*    */ 
/* 68 */     if ((entityplayer.getCurrentEquippedItem() != null) && ((entityplayer.getCurrentEquippedItem().getItem() instanceof ItemMultitool)))
/*    */     {
/* 70 */       return false;
/*    */     }
/*    */ 
/* 73 */     TileEntitySecStorage tileentity = (TileEntitySecStorage)world.getBlockTileEntity(i, j, k);
/* 74 */     if (tileentity != null)
/*    */     {
/* 77 */       if (SecurityHelper.isAccessGranted(tileentity, entityplayer, world, SecurityRight.OSS))
/*    */       {
/* 79 */         if (!world.isRemote)
/* 80 */           entityplayer.openGui(ModularForceFieldSystem.instance, 0, world, i, j, k);
/* 81 */         return true;
/*    */       }
/* 83 */       return true;
/*    */     }
/*    */ 
/* 88 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.block.BlockSecurtyStorage
 * JD-Core Version:    0.6.2
 */