/*    */ package chb.mods.mffs.common.options;
/*    */ 
/*    */ import chb.mods.mffs.api.PointXYZ;
/*    */ import chb.mods.mffs.common.IModularProjector.Slots;
/*    */ import chb.mods.mffs.common.Linkgrid;
/*    */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*    */ import chb.mods.mffs.common.MFFSDamageSource;
/*    */ import chb.mods.mffs.common.SecurityRight;
/*    */ import chb.mods.mffs.common.modules.ItemProjectorModuleSphere;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityAdvSecurityStation;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityCapacitor;
/*    */ import chb.mods.mffs.common.tileentity.TileEntityProjector;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemProjectorOptionDefenseStation extends ItemProjectorOptionBase
/*    */ {
/*    */   public ItemProjectorOptionDefenseStation(int i)
/*    */   {
/* 45 */     super(i);
/* 46 */     setIconIndex(39);
/*    */   }
/*    */ 
/*    */   public static void ProjectorPlayerDefence(TileEntityProjector projector, World world)
/*    */   {
/* 51 */     if (projector.isActive())
/*    */     {
/* 53 */       int fieldxmin = projector.xCoord;
/* 54 */       int fieldxmax = projector.xCoord;
/* 55 */       int fieldymin = projector.yCoord;
/* 56 */       int fieldymax = projector.yCoord;
/* 57 */       int fieldzmin = projector.zCoord;
/* 58 */       int fieldzmax = projector.zCoord;
/*    */ 
/* 60 */       for (PointXYZ png : projector.getfield_queue()) {
/* 61 */         fieldxmax = Math.max(fieldxmax, png.X);
/* 62 */         fieldxmin = Math.min(fieldxmin, png.X);
/* 63 */         fieldymax = Math.max(fieldymax, png.Y);
/* 64 */         fieldymin = Math.min(fieldymin, png.Y);
/* 65 */         fieldzmax = Math.max(fieldzmax, png.Z);
/* 66 */         fieldzmin = Math.min(fieldzmin, png.Z);
/*    */       }
/*    */ 
/* 69 */       List LivingEntitylist = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(fieldxmin, fieldymin, fieldzmin, fieldxmax, fieldymax, fieldzmax));
/*    */ 
/* 72 */       for (int i = 0; i < LivingEntitylist.size(); i++) {
/* 73 */         EntityLiving entityLiving = (EntityLiving)LivingEntitylist.get(i);
/*    */ 
/* 76 */         if ((entityLiving instanceof EntityPlayer))
/*    */         {
/* 79 */           if ((!(projector.get_type() instanceof ItemProjectorModuleSphere)) || 
/* 80 */             (PointXYZ.distance(new PointXYZ((int)entityLiving.posX, (int)entityLiving.posY, (int)entityLiving.posZ, world), projector.getMaschinePoint()) <= projector.countItemsInSlot(IModularProjector.Slots.Distance) + 4))
/*    */           {
/* 84 */             if (projector.getLinkPower() < 10000) {
/*    */               break;
/*    */             }
/* 87 */             if (projector.getLinkPower() > 10000)
/*    */             {
/* 89 */               boolean killswitch = false;
/*    */ 
/* 91 */               if (projector.getaccesstyp() == 2)
/*    */               {
/* 93 */                 TileEntityCapacitor cap = (TileEntityCapacitor)Linkgrid.getWorldMap(world).getCapacitor().get(Integer.valueOf(projector.getPowerSourceID()));
/* 94 */                 if (cap != null)
/*    */                 {
/* 96 */                   TileEntityAdvSecurityStation SecurityStation = cap.getLinkedSecurityStation();
/*    */ 
/* 98 */                   if (SecurityStation != null)
/*    */                   {
/* 100 */                     killswitch = !SecurityStation.isAccessGranted(((EntityPlayer)entityLiving).username, SecurityRight.SR);
/*    */                   }
/*    */                 }
/*    */               }
/* 104 */               if (projector.getaccesstyp() == 3)
/*    */               {
/* 106 */                 TileEntityAdvSecurityStation SecurityStation = projector.getLinkedSecurityStation();
/* 107 */                 if (SecurityStation != null)
/*    */                 {
/* 109 */                   killswitch = !SecurityStation.isAccessGranted(((EntityPlayer)entityLiving).username, SecurityRight.SR);
/*    */                 }
/*    */               }
/*    */ 
/* 113 */               if (killswitch)
/*    */               {
/* 115 */                 if (projector.consumePower(10000, true))
/*    */                 {
/* 117 */                   ((EntityPlayer)entityLiving).addChatMessage("!!! [Area Defence] leave or die !!!");
/* 118 */                   ((EntityPlayer)entityLiving).attackEntityFrom(MFFSDamageSource.fieldDefense, 10);
/* 119 */                   projector.consumePower(10000, false);
/*    */                 }
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.options.ItemProjectorOptionDefenseStation
 * JD-Core Version:    0.6.2
 */