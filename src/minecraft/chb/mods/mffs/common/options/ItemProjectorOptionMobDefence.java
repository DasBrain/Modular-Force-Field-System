/*    */ package chb.mods.mffs.common.options;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.IModularProjector.Slots;
/*    */ import chb.mods.mffs.common.MFFSDamageSource;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleSphere;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import net.minecraft.entity.monster.EntityMob;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemProjectorOptionMobDefence extends ItemProjectorOptionBase
/*    */ {
/*    */   public ItemProjectorOptionMobDefence(int i)
/*    */   {
/* 42 */     super(i);
/* 43 */     setIconIndex(40);
/*    */   }
/*    */ 
/*    */   public static void ProjectorNPCDefence(TileEntityProjector projector, World world)
/*    */   {
/* 49 */     if (projector.isActive())
/*    */     {
/* 52 */       int fieldxmin = projector.xCoord;
/* 53 */       int fieldxmax = projector.xCoord;
/* 54 */       int fieldymin = projector.yCoord;
/* 55 */       int fieldymax = projector.yCoord;
/* 56 */       int fieldzmin = projector.zCoord;
/* 57 */       int fieldzmax = projector.zCoord;
/*    */ 
/* 59 */       for (PointXYZ png : projector.getfield_queue()) {
/* 60 */         fieldxmax = Math.max(fieldxmax, png.X);
/* 61 */         fieldxmin = Math.min(fieldxmin, png.X);
/* 62 */         fieldymax = Math.max(fieldymax, png.Y);
/* 63 */         fieldymin = Math.min(fieldymin, png.Y);
/* 64 */         fieldzmax = Math.max(fieldzmax, png.Z);
/* 65 */         fieldzmin = Math.min(fieldzmin, png.Z);
/*    */       }
/*    */ 
/* 68 */       List LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(fieldxmin, fieldymin, fieldzmin, fieldxmax, fieldymax, fieldzmax));
/*    */ 
/* 70 */       for (int i = 0; i < LivingEntitylist.size(); i++) {
/* 71 */         EntityLiving entityLiving = (EntityLiving)LivingEntitylist.get(i);
/*    */ 
/* 74 */         if (((entityLiving instanceof EntityMob)) || ((entityLiving instanceof EntitySlime)) || ((entityLiving instanceof EntityGhast)))
/*    */         {
/* 77 */           if ((!(projector.get_type() instanceof ItemProjectorModuleSphere)) || 
/* 78 */             (PointXYZ.distance(new PointXYZ((int)entityLiving.posX, (int)entityLiving.posY, (int)entityLiving.posZ, world), projector.getMaschinePoint()) <= projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4))
/*    */           {
/* 81 */             if (projector.getLinkPower() < 10000) {
/*    */               break;
/*    */             }
/* 84 */             if (projector.consumePower(10000, true))
/*    */             {
/* 86 */               entityLiving.attackEntityFrom(MFFSDamageSource.fieldDefense, 10);
/* 87 */               projector.consumePower(10000, false);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.ItemProjectorOptionMobDefence
 * JD-Core Version:    0.6.2
 */