/*     */ package chb.mods.mffs.common.tileentity;
/*     */ 
/*     */ import chb.mods.mffs.api.PointXYZ;
/*     */ import chb.mods.mffs.common.ForceFieldBlockStack;
/*     */ import chb.mods.mffs.common.Linkgrid;
/*     */ import chb.mods.mffs.common.Linkgrid.Worldlinknet;
/*     */ import chb.mods.mffs.common.WorldMap;
/*     */ import chb.mods.mffs.common.WorldMap.ForceFieldWorld;
/*     */ import chb.mods.mffs.network.client.ForceFieldClientUpdatehandler;
/*     */ import chb.mods.mffs.network.server.ForceFieldServerUpdatehandler;
/*     */ import chb.mods.mffs.network.server.ForceFieldServerUpdatehandler.ForceFieldpacket;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ 
/*     */ public class TileEntityForceField extends TileEntity
/*     */ {
/*  37 */   private Random random = new Random();
/*  38 */   private int[] texturid = { -76, -76, -76, -76, -76, -76 };
/*     */   private String texturfile;
/*  40 */   private int Ticker = 0;
/*     */   private int ForcefieldCamoblockid;
/*     */   private int ForcefieldCamoblockmeta;
/*     */ 
/*     */   public int getTicker()
/*     */   {
/*  45 */     return this.Ticker;
/*     */   }
/*     */ 
/*     */   public void setTicker(int ticker) {
/*  49 */     this.Ticker = ticker;
/*     */   }
/*     */ 
/*     */   public int getForcefieldCamoblockmeta()
/*     */   {
/*  58 */     return this.ForcefieldCamoblockmeta;
/*     */   }
/*     */ 
/*     */   public void setForcefieldCamoblockmeta(int forcefieldCamoblockmeta) {
/*  62 */     this.ForcefieldCamoblockmeta = forcefieldCamoblockmeta;
/*     */   }
/*     */ 
/*     */   public int getForcefieldCamoblockid() {
/*  66 */     return this.ForcefieldCamoblockid;
/*     */   }
/*     */ 
/*     */   public void setForcefieldCamoblockid(int forcefieldCamoblockid) {
/*  70 */     this.ForcefieldCamoblockid = forcefieldCamoblockid;
/*     */   }
/*     */ 
/*     */   public String getTexturfile() {
/*  74 */     return this.texturfile;
/*     */   }
/*     */ 
/*     */   public void setTexturfile(String texturfile) {
/*  78 */     this.texturfile = texturfile;
/*     */   }
/*     */ 
/*     */   public int[] getTexturid()
/*     */   {
/*  83 */     return this.texturid;
/*     */   }
/*     */ 
/*     */   public int getTexturid(int l)
/*     */   {
/*  88 */     return this.texturid[l];
/*     */   }
/*     */ 
/*     */   public void updateEntity()
/*     */   {
/*  93 */     if (!this.worldObj.isRemote) {
/*  94 */       if (getTicker() >= 20) {
/*  95 */         if ((this.texturid[0] == -76) || (this.texturfile == null))
/*     */         {
/*  97 */           UpdateTextur();
/*     */         }
/*     */ 
/* 100 */         setTicker(0);
/*     */       }
/*     */ 
/* 103 */       setTicker((short)(getTicker() + 1));
/*     */     } else {
/* 105 */       if (getTicker() >= 20 + this.random.nextInt(20)) {
/* 106 */         if ((this.texturid[0] == -76) || (this.texturfile == null))
/*     */         {
/* 108 */           ForceFieldClientUpdatehandler.addto(this.xCoord, this.yCoord, this.zCoord);
/*     */         }
/*     */ 
/* 111 */         setTicker(0);
/*     */       }
/*     */ 
/* 114 */       setTicker((short)(getTicker() + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTexturid(String remotetextu)
/*     */   {
/* 123 */     String[] textur = remotetextu.split("/");
/*     */ 
/* 125 */     this.texturid[0] = Integer.parseInt(textur[0].trim());
/* 126 */     this.texturid[1] = Integer.parseInt(textur[1].trim());
/* 127 */     this.texturid[2] = Integer.parseInt(textur[2].trim());
/* 128 */     this.texturid[3] = Integer.parseInt(textur[3].trim());
/* 129 */     this.texturid[4] = Integer.parseInt(textur[4].trim());
/* 130 */     this.texturid[5] = Integer.parseInt(textur[5].trim());
/*     */ 
/* 132 */     this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
/* 133 */     setTicker(0);
/*     */   }
/*     */ 
/*     */   public void setTexturid(String texturid, TileEntityProjector proj)
/*     */   {
/*     */     try
/*     */     {
/* 142 */       if (!texturid.equalsIgnoreCase(this.texturid[0] + "/" + this.texturid[1] + "/" + this.texturid[2] + "/" + this.texturid[3] + "/" + this.texturid[4] + "/" + this.texturid[5]))
/*     */       {
/* 145 */         String[] textur = texturid.split("/");
/* 146 */         this.texturid[0] = Integer.parseInt(textur[0].trim());
/* 147 */         this.texturid[1] = Integer.parseInt(textur[1].trim());
/* 148 */         this.texturid[2] = Integer.parseInt(textur[2].trim());
/* 149 */         this.texturid[3] = Integer.parseInt(textur[3].trim());
/* 150 */         this.texturid[4] = Integer.parseInt(textur[4].trim());
/* 151 */         this.texturid[5] = Integer.parseInt(textur[5].trim());
/*     */ 
/* 153 */         ForceFieldServerUpdatehandler.getWorldMap(this.worldObj).addto(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId, proj.xCoord, proj.yCoord, proj.zCoord);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void UpdateTextur()
/*     */   {
/* 163 */     if (!this.worldObj.isRemote) {
/* 164 */       ForceFieldBlockStack ffworldmap = WorldMap.getForceFieldWorld(this.worldObj).getForceFieldStackMap(Integer.valueOf(new PointXYZ(this.xCoord, this.yCoord, this.zCoord, this.worldObj).hashCode()));
/*     */ 
/* 166 */       if (ffworldmap != null)
/*     */       {
/* 168 */         if (!ffworldmap.isEmpty())
/*     */         {
/* 171 */           TileEntityProjector projector = (TileEntityProjector)Linkgrid.getWorldMap(this.worldObj).getProjektor().get(Integer.valueOf(ffworldmap.getProjectorID()));
/*     */ 
/* 173 */           if (projector != null)
/*     */           {
/* 175 */             setTexturid(projector.getForceFieldTexturID(), projector);
/* 176 */             setTexturfile(projector.getForceFieldTexturfile());
/* 177 */             setForcefieldCamoblockid(projector.getForcefieldCamoblockid());
/* 178 */             setForcefieldCamoblockmeta(projector.getForcefieldCamoblockmeta());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents() {
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int arg0)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.common.tileentity.TileEntityForceField
 * JD-Core Version:    0.6.2
 */