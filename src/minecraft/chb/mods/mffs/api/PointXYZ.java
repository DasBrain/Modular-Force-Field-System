/*     */ package chb.mods.mffs.api;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ public class PointXYZ
/*     */ {
/*  33 */   public int X = 0;
/*  34 */   public int Y = 0;
/*  35 */   public int Z = 0;
/*     */   public int dimensionId;
/*     */ 
/*     */   public PointXYZ(int x, int y, int z)
/*     */   {
/*  39 */     this(x, y, z, 2147483647);
/*     */   }
/*     */ 
/*     */   public PointXYZ(int x, int y, int z, World worldObj) {
/*  43 */     this(x, y, z, worldObj.provider.dimensionId);
/*     */   }
/*     */ 
/*     */   public PointXYZ(int x, int y, int z, int dimensionId) {
/*  47 */     this.X = x;
/*  48 */     this.Y = y;
/*  49 */     this.Z = z;
/*  50 */     this.dimensionId = dimensionId;
/*     */   }
/*     */ 
/*     */   public PointXYZ(NBTTagCompound nbt) {
/*  54 */     this(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getInteger("dim"));
/*     */   }
/*     */ 
/*     */   public NBTTagCompound asNBT() {
/*  58 */     NBTTagCompound nbt = new NBTTagCompound();
/*  59 */     nbt.setInteger("x", this.X);
/*  60 */     nbt.setInteger("y", this.Y);
/*  61 */     nbt.setInteger("z", this.Z);
/*  62 */     nbt.setInteger("dim", this.dimensionId);
/*  63 */     return nbt;
/*     */   }
/*     */ 
/*     */   public World getPointWorld()
/*     */   {
/*  68 */     if (this.dimensionId != 2147483647)
/*  69 */       return DimensionManager.getWorld(this.dimensionId);
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   public static double distance(PointXYZ png1, PointXYZ png2)
/*     */   {
/*  75 */     if (png1.dimensionId == png2.dimensionId)
/*     */     {
/*  77 */       int dx = png1.X - png2.X;
/*  78 */       int dy = png1.Y - png2.Y;
/*  79 */       int dz = png1.Z - png2.Z;
/*  80 */       return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     }
/*  82 */     return 2147483647.0D;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object pnt2)
/*     */   {
/*  87 */     if ((pnt2 instanceof PointXYZ)) {
/*  88 */       PointXYZ p = (PointXYZ)pnt2;
/*  89 */       return (this.X == p.X) && (this.Y == p.Y) && (this.Z == p.Z) && (this.dimensionId == p.dimensionId);
/*     */     }
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  96 */     return ("X: " + this.X + " Y: " + this.Y + " Z: " + this.Z + "D: " + this.dimensionId).hashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 101 */     return "X: " + this.X + " Y: " + this.Y + " Z: " + this.Z;
/*     */   }
/*     */ }

/* Location:           C:\Users\Calclavia\Desktop\Decompiler\Mods\MFFS.zip
 * Qualified Name:     chb.mods.mffs.api.PointXYZ
 * JD-Core Version:    0.6.2
 */