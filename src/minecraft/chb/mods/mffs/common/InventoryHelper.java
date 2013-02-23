/*     */ package chb.mods.mffs.common;
/*     */ 
/*     */ import chb.mods.mffs.common.tileentity.TileEntitySecStorage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class InventoryHelper
/*     */ {
/*     */   public static IInventory findAttachedInventory(World worldObj, int x, int y, int z)
/*     */   {
/*  41 */     List tes = new ArrayList();
/*     */ 
/*  43 */     tes.add(worldObj.getBlockTileEntity(x + 1, y, z));
/*  44 */     tes.add(worldObj.getBlockTileEntity(x - 1, y, z));
/*  45 */     tes.add(worldObj.getBlockTileEntity(x, y + 1, z));
/*  46 */     tes.add(worldObj.getBlockTileEntity(x, y - 1, z));
/*  47 */     tes.add(worldObj.getBlockTileEntity(x, y, z + 1));
/*  48 */     tes.add(worldObj.getBlockTileEntity(x, y, z - 1));
/*     */ 
/*  50 */     IInventory inv = null;
/*     */ 
/*  52 */     for (TileEntity te : tes) {
/*  53 */       if (((te instanceof IInventory)) && (
/*  54 */         (inv == null) || (inv.getSizeInventory() < ((IInventory)te).getSizeInventory())))
/*  55 */         inv = (IInventory)te;
/*     */     }
/*  57 */     return inv;
/*     */   }
/*     */ 
/*     */   public static boolean addStacksToInventory(IInventory inventory, ArrayList itemstacks)
/*     */   {
/*  67 */     int count = 0;
/*     */ 
/*  69 */     if ((inventory instanceof TileEntitySecStorage))
/*  70 */       count = 1;
/*     */     ItemStack inventorystack;
/*  72 */     for (int a = count; a <= inventory.getSizeInventory() - 1; a++) {
/*  73 */       inventorystack = inventory.getStackInSlot(a);
/*     */ 
/*  75 */       for (ItemStack items : itemstacks) {
/*  76 */         if (items != null) {
/*  77 */           if (inventorystack != null) {
/*  78 */             if ((inventorystack.getItem() == items.getItem()) && (inventorystack.getItemDamage() == items.getItemDamage()) && (inventorystack.stackSize + 1 <= inventorystack.getMaxStackSize()) && (inventorystack.stackSize + 1 <= inventory.getInventoryStackLimit()))
/*     */             {
/*  85 */               inventorystack.stackSize += 1;
/*     */ 
/*  87 */               items.stackSize -= 1;
/*  88 */               return true;
/*     */             }
/*     */           } else {
/*  91 */             inventorystack = items.copy();
/*  92 */             inventorystack.stackSize = 1;
/*  93 */             items.stackSize -= 1;
/*  94 */             inventory.setInventorySlotContents(a, inventorystack);
/*     */ 
/*  96 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.InventoryHelper
 * JD-Core Version:    0.6.2
 */