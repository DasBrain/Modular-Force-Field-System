/*     */ package chb.mods.mffs.common.multitool;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.ForceFieldBlockStack;
/*     */ import chb.mods.mffs.common.Functions;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.ModularForceFieldSystem;
/*     */ import chb.mods.mffs.common.SecurityHelper;
/*     */ import chb.mods.mffs.common.SecurityRight;
/*     */ import chb.mods.mffs.common.WorldMap;
/*     */ import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
/*     */ import chb.mods.mffs.common.item.ItemCardPowerLink;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*     */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemFieldtransporter extends ItemMultitool
/*     */ {
/*     */   public ItemFieldtransporter(int id)
/*     */   {
/*  49 */     super(id, 4);
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  58 */     if (world.isRemote) {
/*  59 */       return false;
/*     */     }
/*  61 */     WorldMap.ForceFieldWorld wff = WorldMap.getForceFieldWorld(world);
/*     */ 
/*  63 */     ForceFieldBlockStack ffworldmap = wff.getForceFieldStackMap(new PointXYZ(x, y, z, world));
/*  64 */     if (ffworldmap != null) {
/*  65 */       int Sec_Gen_ID = -1;
/*  66 */       int First_Gen_ID = ffworldmap.getGenratorID();
/*  67 */       int First_Pro_ID = ffworldmap.getProjectorID();
/*     */ 
/*  69 */       TileEntityCapacitor generator = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(First_Gen_ID));
/*  70 */       TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(world).getProjektor().get(Integer.valueOf(First_Pro_ID));
/*     */ 
/*  72 */       if ((projector != null) && (generator != null))
/*     */       {
/*  75 */         if (projector.isActive())
/*     */         {
/*  77 */           boolean passtrue = false;
/*     */ 
/*  79 */           switch (projector.getaccesstyp())
/*     */           {
/*     */           case 0:
/*  82 */             passtrue = false;
/*     */ 
/*  84 */             String[] ops = ModularForceFieldSystem.Admin.split(";");
/*  85 */             for (int i = 0; i <= ops.length - 1; i++) {
/*  86 */               if (entityplayer.username.equalsIgnoreCase(ops[i])) {
/*  87 */                 passtrue = true;
/*     */               }
/*     */             }
/*  90 */             List slots = entityplayer.inventoryContainer.inventorySlots;
/*  91 */             for (Slot slot : slots) {
/*  92 */               ItemStack playerstack = slot.getStack();
/*  93 */               if ((playerstack != null) && 
/*  94 */                 ((playerstack.getItem() instanceof ItemDebugger))) {
/*  95 */                 passtrue = true;
/*  96 */                 break;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 104 */             break;
/*     */           case 1:
/* 106 */             passtrue = true;
/* 107 */             break;
/*     */           case 2:
/* 109 */             passtrue = SecurityHelper.isAccessGranted(generator, entityplayer, world, SecurityRight.FFB);
/* 110 */             break;
/*     */           case 3:
/* 112 */             passtrue = SecurityHelper.isAccessGranted(projector, entityplayer, world, SecurityRight.FFB);
/*     */           }
/*     */ 
/* 118 */           if (passtrue) {
/* 119 */             int typ = 99;
/* 120 */             int ymodi = 0;
/*     */ 
/* 122 */             int lm = MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*     */ 
/* 124 */             int i1 = Math.round(entityplayer.rotationPitch);
/*     */ 
/* 126 */             if (i1 >= 65)
/* 127 */               typ = 1;
/* 128 */             else if (i1 <= -65)
/* 129 */               typ = 0;
/* 130 */             else if (lm == 0)
/* 131 */               typ = 2;
/* 132 */             else if (lm == 1)
/* 133 */               typ = 5;
/* 134 */             else if (lm == 2)
/* 135 */               typ = 3;
/* 136 */             else if (lm == 3) {
/* 137 */               typ = 4;
/*     */             }
/*     */ 
/* 140 */             int counter = 0;
/* 141 */             while (Sec_Gen_ID != 0) {
/* 142 */               Sec_Gen_ID = wff.isExistForceFieldStackMap(x, y, z, counter, typ, world);
/* 143 */               if (Sec_Gen_ID != 0)
/*     */               {
/* 145 */                 counter++;
/*     */               }
/*     */             }
/*     */ 
/* 149 */             if (First_Gen_ID != wff.isExistForceFieldStackMap(x, y, z, counter - 1, typ, world))
/*     */             {
/* 151 */               Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/* 152 */               return false;
/*     */             }
/*     */ 
/* 155 */             switch (typ)
/*     */             {
/*     */             case 0:
/* 158 */               y += counter;
/* 159 */               ymodi = -1;
/* 160 */               break;
/*     */             case 1:
/* 162 */               y -= counter;
/* 163 */               ymodi = 1;
/* 164 */               break;
/*     */             case 2:
/* 166 */               z += counter;
/* 167 */               break;
/*     */             case 3:
/* 169 */               z -= counter;
/* 170 */               break;
/*     */             case 4:
/* 172 */               x += counter;
/* 173 */               break;
/*     */             case 5:
/* 175 */               x -= counter;
/*     */             }
/*     */ 
/* 179 */             Functions.ChattoPlayer(entityplayer, "[Field Security] Success: access granted");
/*     */ 
/* 181 */             if ((counter >= 0) && (counter <= 5))
/*     */             {
/* 183 */               if (((world.getBlockMaterial(x, y, z).isLiquid()) || (world.isAirBlock(x, y, z))) && ((world.getBlockMaterial(x, y - ymodi, z).isLiquid()) || (world.isAirBlock(x, y - ymodi, z))))
/*     */               {
/* 186 */                 if (y - ymodi <= 0) {
/* 187 */                   Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: transmission into Void not allowed ");
/*     */                 }
/* 189 */                 else if (consumePower(stack, ModularForceFieldSystem.forcefieldtransportcost, true))
/*     */                 {
/* 191 */                   consumePower(stack, ModularForceFieldSystem.forcefieldtransportcost, false);
/* 192 */                   entityplayer.setPositionAndUpdate(x + 0.5D, y - ymodi, z + 0.5D);
/*     */ 
/* 194 */                   Functions.ChattoPlayer(entityplayer, "[Field Security] Success: transmission complete");
/*     */                 }
/*     */                 else {
/* 197 */                   Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: not enough FP please charge  ");
/*     */                 }
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 203 */                 Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: detected obstacle ");
/*     */               }
/*     */             }
/*     */             else
/* 207 */               Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: Field to Strong >= 5 Blocks");
/*     */           }
/*     */           else
/*     */           {
/* 211 */             Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: access denied");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/* 217 */       else if ((projector != null) && 
/* 218 */         (projector.getStackInSlot(projector.getPowerlinkSlot()) != null) && 
/* 219 */         (!(projector.getStackInSlot(projector.getPowerlinkSlot()).getItem() instanceof ItemCardPowerLink))) {
/* 220 */         Functions.ChattoPlayer(entityplayer, "[Field Security] Fail: Projector Powersource not Support this activities");
/*     */       }
/*     */     }
/*     */ 
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
/*     */   {
/* 231 */     return super.onItemRightClick(itemstack, world, entityplayer);
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.multitool.ItemFieldtransporter
 * JD-Core Version:    0.6.2
 */